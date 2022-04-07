package com.xichuan.user.service;


import com.xichuan.model.pojo.AppUser;

import java.util.List;

public interface UserService {

    /**
     * 判断用户是否存在，如果存在返回user信息
     */
    public AppUser queryMobileIsExist(String mobile);

    /**
     * 创建用户，新增用户记录到数据库
     */
    public AppUser createUser(String mobile);

    /**
     * 根据用户主键id查询用户信息
     */
    public AppUser getUser(String userId);
}
