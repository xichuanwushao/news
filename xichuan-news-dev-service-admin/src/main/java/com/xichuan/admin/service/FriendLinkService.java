package com.xichuan.admin.service;

import com.xichuan.model.pojo.mo.FriendLinkMO;

/**
 * @author : wuxiao
 * @date : 13:54 2022/4/16
 */
public interface FriendLinkService {

    /**
     * 新增或者更新友情链接
     */
    public void saveOrUpdateFriendLink(FriendLinkMO friendLinkMO);

}
