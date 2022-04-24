package com.xichuan.admin.service;

import com.xichuan.model.pojo.mo.FriendLinkMO;

import java.util.List;

/**
 * @author : wuxiao
 * @date : 13:54 2022/4/16
 */
public interface FriendLinkService {

    /**
     * 新增或者更新友情链接
     */
    public void saveOrUpdateFriendLink(FriendLinkMO friendLinkMO);

    /**
     * 查询友情链接
     */
    public List<FriendLinkMO> queryAllFriendLinkList();

    /**
     * 删除友情链接
     */
    public void delete(String linkId);

    /**
     * 首页查询友情链接
     */
    public List<FriendLinkMO> queryPortalAllFriendLinkList();
}
