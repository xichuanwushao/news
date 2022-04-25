package com.xichuan.article.service;

import com.xichuan.vommon.util.PagedGridResult;

/**
 * @author : wuxiao
 * @date : 22:00 2022/4/24
 */
public interface ArticlePortalService {
    /**
     * 首页查询文章列表
     */
    public PagedGridResult queryIndexArticleList(String keyword,
                                                 Integer category,
                                                 Integer page,
                                                 Integer pageSize);
}
