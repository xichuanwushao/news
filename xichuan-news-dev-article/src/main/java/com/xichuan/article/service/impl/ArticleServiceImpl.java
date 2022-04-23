package com.xichuan.article.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.xichuan.api.service.BaseService;
import com.xichuan.article.mapper.ArticleMapper;
import com.xichuan.article.mapper.ArticleMapperCust;
import com.xichuan.article.service.ArticleService;
import com.xichuan.model.pojo.AppUser;
import com.xichuan.model.pojo.Article;
import com.xichuan.model.pojo.Category;
import com.xichuan.model.pojo.bo.NewArticleBO;
import com.xichuan.vommon.enums.ArticleAppointType;
import com.xichuan.vommon.enums.ArticleReviewStatus;
import com.xichuan.vommon.enums.YesOrNo;
import com.xichuan.vommon.exception.GraceException;
import com.xichuan.vommon.result.ResponseStatusEnum;
import com.xichuan.vommon.util.PagedGridResult;
import org.apache.commons.lang3.StringUtils;
import org.n3r.idworker.Sid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.List;

/**
 * @author : wuxiao
 * @date : 13:31 2022/4/23
 */
@Service
public class ArticleServiceImpl extends BaseService implements ArticleService {

    @Autowired
    private ArticleMapper articleMapper;

    @Autowired
    private ArticleMapperCust articleMapperCust;

    @Autowired
    private Sid sid;
    @Transactional
    @Override
    public void createArticle(NewArticleBO newArticleBO, Category category) {

        String articleId = sid.nextShort();

        Article article = new Article();
        BeanUtils.copyProperties(newArticleBO, article);

        article.setId(articleId);
        article.setCategoryId(category.getId());
        article.setArticleStatus(ArticleReviewStatus.REVIEWING.type);
        article.setCommentCounts(0);
        article.setReadCounts(0);

        article.setIsDelete(YesOrNo.NO.type);
        article.setCreateTime(new Date());
        article.setUpdateTime(new Date());

        if (article.getIsAppoint() == ArticleAppointType.TIMING.type) {
            article.setPublishTime(newArticleBO.getPublishTime());
        } else if (article.getIsAppoint() == ArticleAppointType.IMMEDIATELY.type) {
            article.setPublishTime(new Date());
        }

        int res = articleMapper.insert(article);
        if (res != 1) {
            GraceException.display(ResponseStatusEnum.ARTICLE_CREATE_ERROR);
        }
    }

    @Transactional
    @Override
    public void updateAppointToPublish() {
        articleMapperCust.updateAppointToPublish();
    }

    @Override
    public PagedGridResult queryMyArticleList(String userId,
                                              String keyword,
                                              Integer status,
                                              Date startDate,
                                              Date endDate,
                                              Integer page,
                                              Integer pageSize) {
        LambdaQueryWrapper<Article> queryWrapper = new QueryWrapper<Article>().lambda();
//        queryWrapper
//                .eq(Article::getArticleCover, "")
//                .or()
//                .eq(Article::getIsAppoint, "");
//        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.orderByDesc(Article::getCreateTime);
        queryWrapper.eq(Article::getPublishUserId, userId);
        if (StringUtils.isNotBlank(keyword)) {
            queryWrapper.like(Article::getTitle, "%" + keyword + "%");
        }
        if (ArticleReviewStatus.isArticleStatusValid(status)) {
            queryWrapper.eq(Article::getArticleStatus, status);
        }

        if (status != null && status == 12) {
                queryWrapper
                .eq(Article::getArticleStatus,  ArticleReviewStatus.REVIEWING.type)
                .or()
                .eq(Article::getArticleStatus, ArticleReviewStatus.WAITING_MANUAL.type);
        }
        queryWrapper.eq(Article::getIsDelete, YesOrNo.NO.type);
        if (startDate != null) {
            queryWrapper.ge(Article::getPublishTime,startDate);
        }
        if (endDate != null) {
            queryWrapper.le(Article::getPublishTime,endDate);
        }

        PageHelper.startPage(page, pageSize);
        List<Article> list = articleMapper.selectList(queryWrapper);
        return setterPagedGrid(list, page);
    }
}
