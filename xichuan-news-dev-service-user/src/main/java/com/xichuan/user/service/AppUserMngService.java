package com.xichuan.user.service;

import com.xichuan.vommon.util.PagedGridResult;

import java.util.Date;

/**
 * @author : wuxiao
 * @date : 21:19 2022/4/18
 */
public interface AppUserMngService {

    /**
     * 查询管理员列表
     */
    public PagedGridResult queryAllUserList(String nickname,
                                            Integer status,
                                            Date startDate,
                                            Date endDate,
                                            Integer page,
                                            Integer pageSize);
}
