package com.xichuan.article.service.impl;

import com.xichuan.api.service.BaseService;
import com.xichuan.article.mapper.ArticleMapper;
import com.xichuan.article.mapper.ArticleMapperCust;
import com.xichuan.article.service.ArticleService;
import com.xichuan.model.pojo.Article;
import com.xichuan.model.pojo.Category;
import com.xichuan.model.pojo.bo.NewArticleBO;
import com.xichuan.vommon.enums.ArticleAppointType;
import com.xichuan.vommon.enums.ArticleReviewStatus;
import com.xichuan.vommon.enums.YesOrNo;
import com.xichuan.vommon.exception.GraceException;
import com.xichuan.vommon.result.ResponseStatusEnum;
import org.n3r.idworker.Sid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

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
}
