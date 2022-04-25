package com.xichuan.article.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.xichuan.api.service.BaseService;
import com.xichuan.article.mapper.ArticleMapper;
import com.xichuan.article.service.ArticlePortalService;
import com.xichuan.model.pojo.Article;
import com.xichuan.vommon.enums.ArticleReviewStatus;
import com.xichuan.vommon.enums.YesOrNo;
import com.xichuan.vommon.util.PagedGridResult;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author : wuxiao
 * @date : 22:00 2022/4/24
 */
@Service
public class ArticlePortalServiceImpl extends BaseService implements ArticlePortalService {
    @Autowired
    private ArticleMapper articleMapper;

    @Override
    public PagedGridResult queryIndexArticleList(String keyword,
                                                 Integer category,
                                                 Integer page,
                                                 Integer pageSize) {

        LambdaQueryWrapper<Article> queryWrapper = new QueryWrapper<Article>().lambda();
        queryWrapper.orderByDesc(Article::getPublishTime);
        /**
         * 查询首页文章的自带隐性查询条件：
         * isAppoint=即使发布，表示文章已经直接发布的，或者定时任务到点发布的
         * isDelete=未删除，表示文章只能够显示未删除
         * articleStatus=审核通过，表示只有文章经过机审/人工审核之后才能展示
         */
        queryWrapper.eq(Article::getIsAppoint, YesOrNo.NO.type);
        queryWrapper.eq(Article::getIsDelete, YesOrNo.NO.type);
        queryWrapper.eq(Article::getArticleStatus, ArticleReviewStatus.SUCCESS.type);

        if (StringUtils.isNotBlank(keyword)) {
            queryWrapper.like(Article::getTitle, "%" + keyword + "%");
        }
        if (category != null) {
            queryWrapper.eq(Article::getCategoryId, category);
        }

        PageHelper.startPage(page, pageSize);
        List<Article> list = articleMapper.selectList(queryWrapper);

        return setterPagedGrid(list, page);
    }
}
