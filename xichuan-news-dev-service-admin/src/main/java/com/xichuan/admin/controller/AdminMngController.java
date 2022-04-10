package com.xichuan.admin.controller;

import com.xichuan.admin.service.IAdminUserService;
import com.xichuan.api.BaseController;
import com.xichuan.api.controller.admin.AdminMngControllerApi;
import com.xichuan.model.pojo.AdminUser;
import com.xichuan.model.pojo.bo.AdminLoginBO;
import com.xichuan.model.pojo.bo.NewAdminBO;
import com.xichuan.vommon.exception.GraceException;
import com.xichuan.vommon.result.GraceJSONResult;
import com.xichuan.vommon.result.ResponseStatusEnum;
import com.xichuan.vommon.util.PagedGridResult;
import com.xichuan.vommon.util.RedisOperator;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RestController;

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

    @Override
    public GraceJSONResult adminLogin( AdminLoginBO adminLoginBO,
                                       BindingResult result,
                                       HttpServletRequest request,
                                       HttpServletResponse response) {
        // 0. TODO 验证BO中的用户名和密码不为空
        // 0.判断BindingResult中是否保存了错误的验证信息，如果有，则需要返回
        if (result.hasErrors()) {
            Map<String, String> map = getErrors(result);
            return GraceJSONResult.errorMap(map);
        }
        // 1. 查询admin用户的信息
        AdminUser admin = adminUserService.queryAdminByUsername(adminLoginBO.getUsername());
        // 2. 判断admin不为空，如果为空则登录失败
        if (admin == null) {
            return GraceJSONResult.errorCustom(ResponseStatusEnum.ADMIN_NOT_EXIT_ERROR);
        }

        // 3. 判断密码是否匹配
        boolean isPwdMatch = BCrypt.checkpw(adminLoginBO.getPassword(), admin.getPassword());
        if (isPwdMatch) {
            doLoginSettings(admin, request, response);
            return GraceJSONResult.ok();
        } else {
            return GraceJSONResult.errorCustom(ResponseStatusEnum.ADMIN_NOT_EXIT_ERROR);
        }
    }

    /**
     * 用于admin用户登录过后的基本信息设置
     * @param admin
     * @param request
     * @param response
     */
    private void doLoginSettings(AdminUser admin,
                                 HttpServletRequest request,
                                 HttpServletResponse response) {
        // 保存token放入到redis中
        String token = UUID.randomUUID().toString();
        redis.set(REDIS_ADMIN_TOKEN + ":" + admin.getId(), token);

        // 保存admin登录基本token信息到cookie中
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

        // 0. TODO 验证BO中的用户名和密码不为空
        // 0.判断BindingResult中是否保存了错误的验证信息，如果有，则需要返回
        if (result.hasErrors()) {
            Map<String, String> map = getErrors(result);
            return GraceJSONResult.errorMap(map);
        }
        // 1. base64不为空，则代表人脸入库，否则需要用户输入密码和确认密码
        if (StringUtils.isBlank(newAdminBO.getImg64())) {
            if (StringUtils.isBlank(newAdminBO.getPassword()) ||
                    StringUtils.isBlank(newAdminBO.getConfirmPassword())
            ) {
                return GraceJSONResult.errorCustom(ResponseStatusEnum.ADMIN_PASSWORD_NULL_ERROR);
            }
        }

        // 2. 密码不为空，则必须判断两次输入一致
        if (StringUtils.isNotBlank(newAdminBO.getPassword())) {
            if (!newAdminBO.getPassword()
                    .equalsIgnoreCase(newAdminBO.getConfirmPassword())) {
                return GraceJSONResult.errorCustom(ResponseStatusEnum.ADMIN_PASSWORD_ERROR);
            }
        }

        // 3. 校验用户名唯一
        checkAdminExist(newAdminBO.getUsername());

        // 4. 调用service存入admin信息
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

        // 从redis中删除admin的会话token
        redis.del(REDIS_ADMIN_TOKEN + ":" + adminId);

        // 从cookie中清理adming登录的相关信息
        deleteCookie(request, response, "atoken");
        deleteCookie(request, response, "aid");
        deleteCookie(request, response, "aname");

        return GraceJSONResult.ok();
    }

}
