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

    /**
     * 冻结用户账号，或者解除冻结状态
     */
    public void freezeUserOrNot(String userId, Integer doStatus);
}
