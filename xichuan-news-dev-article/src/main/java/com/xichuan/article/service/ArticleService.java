package com.xichuan.article.service;

import com.xichuan.model.pojo.Category;
import com.xichuan.model.pojo.bo.NewArticleBO;

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
}
