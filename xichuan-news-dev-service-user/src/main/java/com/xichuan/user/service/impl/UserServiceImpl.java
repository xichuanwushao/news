package com.xichuan.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xichuan.model.pojo.AppUser;
import com.xichuan.model.pojo.bo.UpdateUserInfoBO;
import com.xichuan.user.mapper.AppUserMapper;
import com.xichuan.user.service.UserService;
import com.xichuan.vommon.enums.Sex;
import com.xichuan.vommon.enums.UserStatus;
import com.xichuan.vommon.exception.GraceException;
import com.xichuan.vommon.result.ResponseStatusEnum;
import com.xichuan.vommon.util.DateUtil;
import com.xichuan.vommon.util.DesensitizationUtil;
import com.xichuan.vommon.util.RedisOperator;
import org.n3r.idworker.Sid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Date;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    public AppUserMapper appUserMapper;

    @Autowired
    public Sid sid;

    @Autowired
    public RedisOperator redis;
    public static final String REDIS_USER_INFO = "redis_user_info";

    private static final String USER_FACE0 = "http://122.152.205.72:88/group1/M00/00/05/CpoxxFw_8_qAIlFXAAAcIhVPdSg994.png";
    private static final String USER_FACE1 = "http://122.152.205.72:88/group1/M00/00/05/CpoxxF6ZUySASMbOAABBAXhjY0Y649.png";
    private static final String USER_FACE2 = "http://122.152.205.72:88/group1/M00/00/05/CpoxxF6ZUx6ANoEMAABTntpyjOo395.png";


    @Override
    public AppUser queryMobileIsExist(String mobile) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("mobile", mobile);
        AppUser appUser = appUserMapper.selectOne(queryWrapper);
        return appUser;
    }

    @Transactional
    @Override
    public AppUser createUser(String mobile) {

        /**
         * 互联网项目都要考虑可扩展性
         * 如果未来的业务激增，那么就需要分库分表
         * 那么数据库表主键id必须保证全局（全库）唯一，不得重复
         */
        String userId = sid.nextShort();

        AppUser user = new AppUser();

        user.setId(userId);
        user.setMobile(mobile);
        user.setNickname("用户：" + DesensitizationUtil.commonDisplay(mobile));
        user.setFace(USER_FACE0);

        user.setBirthday(DateUtil.stringToDate("1900-01-01"));
        user.setSex(Sex.secret.type);
        user.setActiveStatus(UserStatus.INACTIVE.type);

        user.setTotalIncome(0);
        user.setCreatedTime(new Date());
        user.setUpdatedTime(new Date());

        appUserMapper.insert(user);

        return user;
    }
    @Override
    public AppUser getUser(String userId) {
        return appUserMapper.selectById(userId);
    }
    @Override
    public void updateUserInfo(UpdateUserInfoBO updateUserInfoBO) {

        AppUser userInfo = new AppUser();
        BeanUtils.copyProperties(updateUserInfoBO, userInfo);

        userInfo.setUpdatedTime(new Date());
        userInfo.setActiveStatus(UserStatus.ACTIVE.type);

        int result = appUserMapper.updateById(userInfo);
        if (result != 1) {
            GraceException.display(ResponseStatusEnum.USER_UPDATE_ERROR);
        }
    }
}
