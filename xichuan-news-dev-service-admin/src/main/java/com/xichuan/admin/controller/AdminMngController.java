package com.xichuan.admin.controller;

import com.xichuan.admin.service.IAdminUserService;
import com.xichuan.api.BaseController;
import com.xichuan.api.controller.admin.AdminMngControllerApi;
import com.xichuan.model.pojo.AdminUser;
import com.xichuan.model.pojo.bo.AdminLoginBO;
import com.xichuan.model.pojo.bo.NewAdminBO;
import com.xichuan.vommon.enums.FaceVerifyType;
import com.xichuan.vommon.exception.GraceException;
import com.xichuan.vommon.result.GraceJSONResult;
import com.xichuan.vommon.result.ResponseStatusEnum;
import com.xichuan.vommon.util.FaceVerifyUtils;
import com.xichuan.vommon.util.PagedGridResult;
import com.xichuan.vommon.util.RedisOperator;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * @author : wuxiao
 * @date : 22:02 2022/4/9
 */
@RestController
public class AdminMngController extends BaseController implements AdminMngControllerApi {
    final static Logger logger = LoggerFactory.getLogger(AdminMngController.class);

    @Autowired
    private RedisOperator redis;

    @Autowired
    private IAdminUserService adminUserService;


    @Autowired
    private RestTemplate restTemplate;


    @Autowired
    private FaceVerifyUtils faceVerifyUtils;

    @Override
    public GraceJSONResult adminLogin( AdminLoginBO adminLoginBO,
                                       BindingResult result,
                                       HttpServletRequest request,
                                       HttpServletResponse response) {
        // 0. TODO ??????BO?????????????????????????????????
        // 0.??????BindingResult?????????????????????????????????????????????????????????????????????
        if (result.hasErrors()) {
            Map<String, String> map = getErrors(result);
            return GraceJSONResult.errorMap(map);
        }
        // 1. ??????admin???????????????
        AdminUser admin = adminUserService.queryAdminByUsername(adminLoginBO.getUsername());
        // 2. ??????admin???????????????????????????????????????
        if (admin == null) {
            return GraceJSONResult.errorCustom(ResponseStatusEnum.ADMIN_NOT_EXIT_ERROR);
        }

        // 3. ????????????????????????
        boolean isPwdMatch = BCrypt.checkpw(adminLoginBO.getPassword(), admin.getPassword());
        if (isPwdMatch) {
            doLoginSettings(admin, request, response);
            return GraceJSONResult.ok();
        } else {
            return GraceJSONResult.errorCustom(ResponseStatusEnum.ADMIN_NOT_EXIT_ERROR);
        }
    }

    /**
     * ??????admin???????????????????????????????????????
     * @param admin
     * @param request
     * @param response
     */
    private void doLoginSettings(AdminUser admin,
                                 HttpServletRequest request,
                                 HttpServletResponse response) {
        // ??????token?????????redis???
        String token = UUID.randomUUID().toString();
        redis.set(REDIS_ADMIN_TOKEN + ":" + admin.getId(), token);

        // ??????admin????????????token?????????cookie???
        setCookie(request, response, "atoken", token, COOKIE_MONTH);
        setCookie(request, response, "aid", admin.getId(), COOKIE_MONTH);
        setCookie(request, response, "aname", admin.getAdminName(), COOKIE_MONTH);
    }


    @Override
    public GraceJSONResult adminIsExist(String username) {
        checkAdminExist(username);
        return GraceJSONResult.ok();
    }

    private void checkAdminExist(String username) {
        AdminUser admin = adminUserService.queryAdminByUsername(username);

        if (admin != null) {
            GraceException.display(ResponseStatusEnum.ADMIN_USERNAME_EXIST_ERROR);
        }
    }
    @Override
    public GraceJSONResult addNewAdmin(NewAdminBO newAdminBO,
                                       BindingResult result,
                                       HttpServletRequest request,
                                       HttpServletResponse response) {

        // 0. TODO ??????BO?????????????????????????????????
        // 0.??????BindingResult?????????????????????????????????????????????????????????????????????
        if (result.hasErrors()) {
            Map<String, String> map = getErrors(result);
            return GraceJSONResult.errorMap(map);
        }
        // 1. base64?????????????????????????????????????????????????????????????????????????????????
        if (StringUtils.isBlank(newAdminBO.getImg64())) {
            if (StringUtils.isBlank(newAdminBO.getPassword()) ||
                    StringUtils.isBlank(newAdminBO.getConfirmPassword())
            ) {
                return GraceJSONResult.errorCustom(ResponseStatusEnum.ADMIN_PASSWORD_NULL_ERROR);
            }
        }

        // 2. ???????????????????????????????????????????????????
        if (StringUtils.isNotBlank(newAdminBO.getPassword())) {
            if (!newAdminBO.getPassword()
                    .equalsIgnoreCase(newAdminBO.getConfirmPassword())) {
                return GraceJSONResult.errorCustom(ResponseStatusEnum.ADMIN_PASSWORD_ERROR);
            }
        }

        // 3. ?????????????????????
        checkAdminExist(newAdminBO.getUsername());

        // 4. ??????service??????admin??????
        adminUserService.createAdminUser(newAdminBO);
        return GraceJSONResult.ok();
    }
    @Override
    public GraceJSONResult getAdminList(Integer page, Integer pageSize) {

        if (page == null) {
            page = COMMON_START_PAGE;
        }

        if (pageSize == null) {
            pageSize = COMMON_PAGE_SIZE;
        }
        startPage();
        List<AdminUser> adminUserList =  adminUserService.queryAdminList(page, pageSize);
        PagedGridResult result = setterPagedGrid(adminUserList, page);
        return GraceJSONResult.ok(result);
    }

    @Override
    public GraceJSONResult adminLogout(String adminId,
                                       HttpServletRequest request,
                                       HttpServletResponse response) {

        // ???redis?????????admin?????????token
        redis.del(REDIS_ADMIN_TOKEN + ":" + adminId);

        // ???cookie?????????adming?????????????????????
        deleteCookie(request, response, "atoken");
        deleteCookie(request, response, "aid");
        deleteCookie(request, response, "aname");

        return GraceJSONResult.ok();
    }

    @Override
    public GraceJSONResult adminFaceLogin(AdminLoginBO adminLoginBO, HttpServletRequest request, HttpServletResponse response) {
        // 0. ??????????????????????????????????????????
        if (StringUtils.isBlank(adminLoginBO.getUsername())) {
            return GraceJSONResult.errorCustom(ResponseStatusEnum.ADMIN_USERNAME_NULL_ERROR);
        }
        String tempFace64 = adminLoginBO.getImg64();
        if (StringUtils.isBlank(tempFace64)) {
            return GraceJSONResult.errorCustom(ResponseStatusEnum.ADMIN_FACE_NULL_ERROR);
        }

        // 1. ????????????????????????faceId
        AdminUser admin = adminUserService.queryAdminByUsername(adminLoginBO.getUsername());
        String adminFaceId = admin.getFaceId();

        if (StringUtils.isBlank(adminFaceId)) {
            return GraceJSONResult.errorCustom(ResponseStatusEnum.ADMIN_FACE_LOGIN_ERROR);
        }

        // 2. ??????????????????????????????????????????base64??????
        String fileServerUrlExecute
                = "http://127.0.0.1:8004/fs/readFace64InGridFS?faceId=" + adminFaceId;
        ResponseEntity<GraceJSONResult> responseEntity
                = restTemplate.getForEntity(fileServerUrlExecute, GraceJSONResult.class);
        GraceJSONResult bodyResult = responseEntity.getBody();
        String base64DB = (String)bodyResult.getData();


        // 3. ????????????ai?????????????????????????????????????????????????????????????????????
        boolean result = faceVerifyUtils.faceVerify(FaceVerifyType.BASE64.type,
                tempFace64,
                base64DB,
                60);

        if (!result) {
            return GraceJSONResult.errorCustom(ResponseStatusEnum.ADMIN_FACE_LOGIN_ERROR);
        }

        return GraceJSONResult.ok();
    }

}
