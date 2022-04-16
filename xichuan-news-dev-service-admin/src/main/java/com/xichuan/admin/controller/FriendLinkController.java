package com.xichuan.admin.controller;

import com.xichuan.api.BaseController;
import com.xichuan.api.controller.admin.FriendLinkControllerApi;
import com.xichuan.model.pojo.bo.SaveFriendLinkBO;
import com.xichuan.model.pojo.mo.FriendLinkMO;
import com.xichuan.vommon.result.GraceJSONResult;
import org.springframework.beans.BeanUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Date;
import java.util.Map;

/**
 * @author : wuxiao
 * @date : 13:31 2022/4/16
 */
@RestController
public class FriendLinkController extends BaseController implements FriendLinkControllerApi {

    @Override
    public GraceJSONResult saveOrUpdateFriendLink(
            @Valid SaveFriendLinkBO saveFriendLinkBO,
            BindingResult result) {

        if (result.hasErrors()) {
            Map<String, String> map = getErrors(result);
            return GraceJSONResult.errorMap(map);
        }

//        saveFriendLinkBO -> ***MO

        FriendLinkMO saveFriendLinkMO = new FriendLinkMO();
        BeanUtils.copyProperties(saveFriendLinkBO, saveFriendLinkMO);
        saveFriendLinkMO.setCreateTime(new Date());
        saveFriendLinkMO.setUpdateTime(new Date());

        return GraceJSONResult.ok();
    }
}
