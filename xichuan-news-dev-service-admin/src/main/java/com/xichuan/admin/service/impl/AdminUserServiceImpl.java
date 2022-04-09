package com.xichuan.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xichuan.admin.mapper.AdminUserMapper;
import com.xichuan.admin.service.IAdminUserService;
import com.xichuan.model.pojo.AdminUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 运营管理平台的admin级别用户 服务实现类
 * </p>
 *
 * @author wuxiao
 * @since 2022-04-09
 */
@Service
public class AdminUserServiceImpl extends ServiceImpl<AdminUserMapper, AdminUser> implements IAdminUserService {

    @Autowired
    public AdminUserMapper adminUserMapper;


    @Override
    public AdminUser queryAdminByUsername(String username) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("username", username);
        AdminUser admin = adminUserMapper.selectOne(queryWrapper);
        return admin;
    }
}
