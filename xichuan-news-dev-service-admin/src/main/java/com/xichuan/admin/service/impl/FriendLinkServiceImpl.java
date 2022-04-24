package com.xichuan.admin.service.impl;

import com.xichuan.admin.repository.FriendLinkRepository;
import com.xichuan.admin.service.FriendLinkService;
import com.xichuan.model.pojo.mo.FriendLinkMO;
import com.xichuan.vommon.enums.YesOrNo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author : wuxiao
 * @date : 13:55 2022/4/16
 */
@Service
public class FriendLinkServiceImpl implements FriendLinkService {


    @Autowired
    private FriendLinkRepository friendLinkRepository;

    @Override
    public void saveOrUpdateFriendLink(FriendLinkMO friendLinkMO) {
        friendLinkRepository.save(friendLinkMO);
    }


    @Override
    public List<FriendLinkMO> queryAllFriendLinkList() {
        return friendLinkRepository.findAll();
    }

    @Override
    public void delete(String linkId) {
        friendLinkRepository.deleteById(linkId);
    }
    @Override
    public List<FriendLinkMO> queryPortalAllFriendLinkList() {
        return friendLinkRepository.getAllByIsDelete(YesOrNo.NO.type);
    }
}
