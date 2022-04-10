package com.xichuan.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xichuan.model.pojo.AdminUser;
import com.xichuan.model.pojo.bo.NewAdminBO;

import java.util.List;

/**
 * <p>
 * 运营管理平台的admin级别用户 服务类
 * </p>
 *
 * @author wuxiao
 * @since 2022-04-09
 */
public interface IAdminUserService extends IService<AdminUser> {

    /**
     * 获得管理员的用户信息
     */
    public AdminUser queryAdminByUsername(String username);

    /**
     * 新增管理员
     */
    public void createAdminUser(NewAdminBO newAdminBO);

    /**
     * 分页查询admin列表
     */
    public List<AdminUser> queryAdminList(Integer page, Integer pageSize);
}
