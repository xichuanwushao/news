package com.xichuan.article.service;

import com.xichuan.model.pojo.Category;
import com.xichuan.model.pojo.bo.NewArticleBO;
import com.xichuan.vommon.util.PagedGridResult;

import java.util.Date;

/**
 * @author : wuxiao
 * @date : 13:30 2022/4/23
 */
public interface ArticleService {
    /**
     * 发布文章
     */
    public void createArticle(NewArticleBO newArticleBO, Category category);

    /**
     * 更新定时发布为即时发布
     */
    public void updateAppointToPublish();

    /**
     * 用户中心 - 查询我的文章列表
     */
    public PagedGridResult queryMyArticleList(String userId,
                                              String keyword,
                                              Integer status,
                                              Date startDate,
                                              Date endDate,
                                              Integer page,
                                              Integer pageSize);
    /**
     * 更改文章的状态
     */
    public void updateArticleStatus(String articleId, Integer pendingStatus);


    /**
     * 管理员查询文章列表
     */
    public PagedGridResult queryAllArticleListAdmin(Integer status, Integer page, Integer pageSize);

}
