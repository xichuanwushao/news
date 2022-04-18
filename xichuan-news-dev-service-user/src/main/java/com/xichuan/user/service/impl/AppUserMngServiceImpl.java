package com.xichuan.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.xichuan.api.service.BaseService;
import com.xichuan.model.pojo.AppUser;
import com.xichuan.user.mapper.AppUserMapper;
import com.xichuan.user.service.AppUserMngService;
import com.xichuan.vommon.enums.UserStatus;
import com.xichuan.vommon.util.PagedGridResult;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @author : wuxiao
 * @date : 21:19 2022/4/18
 */
@Service
public class AppUserMngServiceImpl extends BaseService implements AppUserMngService {

    @Autowired
    public AppUserMapper appUserMapper;

    @Override
    public PagedGridResult queryAllUserList(String nickname,
                                            Integer status,
                                            Date startDate,
                                            Date endDate,
                                            Integer page,
                                            Integer pageSize) {

        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.orderByDesc("created_time");
        if (StringUtils.isNotBlank(nickname)) {
            queryWrapper.like("nickname", "%" + nickname + "%");
        }
        if (UserStatus.isUserStatusValid(status)) {
            queryWrapper.eq("active_status", status);
        }
        if (startDate != null) {
            queryWrapper.ge("created_time", startDate);
        }
        if (endDate != null) {
            queryWrapper.le("created_time", endDate);
        }
        PageHelper.startPage(page, pageSize);
        List<AppUser> list = appUserMapper.selectList(queryWrapper);
        return setterPagedGrid(list, page);
    }

    @Transactional
    @Override
    public void freezeUserOrNot(String userId, Integer doStatus) {
        AppUser user = new AppUser();
        user.setId(userId);
        user.setActiveStatus(doStatus);
        appUserMapper.updateById(user);
    }
}
