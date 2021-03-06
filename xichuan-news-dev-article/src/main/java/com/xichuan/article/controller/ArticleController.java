package com.xichuan.article.controller;

import com.xichuan.api.BaseController;
import com.xichuan.api.article.ArticleControllerApi;
import com.xichuan.article.service.ArticleService;
import com.xichuan.model.pojo.Category;
import com.xichuan.model.pojo.bo.NewArticleBO;
import com.xichuan.vommon.enums.ArticleCoverType;
import com.xichuan.vommon.enums.ArticleReviewStatus;
import com.xichuan.vommon.enums.YesOrNo;
import com.xichuan.vommon.result.GraceJSONResult;
import com.xichuan.vommon.result.ResponseStatusEnum;
import com.xichuan.vommon.util.JsonUtils;
import com.xichuan.vommon.util.PagedGridResult;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author : wuxiao
 * @date : 23:16 2022/4/20
 */
@RestController
public class ArticleController extends BaseController implements ArticleControllerApi {

    @Autowired
    private ArticleService articleService;


    @Override
    public GraceJSONResult createArticle(@Valid NewArticleBO newArticleBO, BindingResult result) {
        // 判断BindingResult是否保存错误的验证信息，如果有，则直接return
        if (result.hasErrors()) {
            Map<String, String> errorMap = getErrors(result);
            return GraceJSONResult.errorMap(errorMap);
        }

        // 判断文章封面类型，单图必填，纯文字则设置为空
        if (newArticleBO.getArticleType() == ArticleCoverType.ONE_IMAGE.type) {
            if (StringUtils.isBlank(newArticleBO.getArticleCover())) {
                return GraceJSONResult.errorCustom(ResponseStatusEnum.ARTICLE_COVER_NOT_EXIST_ERROR);
            }
        } else if (newArticleBO.getArticleType() == ArticleCoverType.WORDS.type) {
            newArticleBO.setArticleCover("");
        }

        // 判断分类id是否存在
        String allCatJson = redis.get(REDIS_ALL_CATEGORY);
        Category temp = null;
        if (StringUtils.isBlank(allCatJson)) {
            return GraceJSONResult.errorCustom(ResponseStatusEnum.SYSTEM_OPERATION_ERROR);
        } else {
            List<Category> catList =
                    JsonUtils.jsonToList(allCatJson, Category.class);
            for (Category c : catList) {
                if(c.getId() == newArticleBO.getCategoryId()) {
                    temp = c;
                    break;
                }
            }
            if (temp == null) {
                return GraceJSONResult.errorCustom(ResponseStatusEnum.ARTICLE_CATEGORY_NOT_EXIST_ERROR);
            }
        }

//        System.out.println(newArticleBO.toString());

        articleService.createArticle(newArticleBO, temp);

        return GraceJSONResult.ok();
    }

    @Override
    public GraceJSONResult queryMyList(String userId,
                                       String keyword,
                                       Integer status,
                                       Date startDate,
                                       Date endDate,
                                       Integer page,
                                       Integer pageSize) {

        if (StringUtils.isBlank(userId)) {
            return GraceJSONResult.errorCustom(ResponseStatusEnum.ARTICLE_QUERY_PARAMS_ERROR);
        }

        if (page == null) {
            page = COMMON_START_PAGE;
        }
        if (pageSize == null) {
            pageSize = COMMON_PAGE_SIZE;
        }

        // 查询我的列表，调用service
        PagedGridResult grid = articleService.queryMyArticleList(userId,
                keyword,
                status,
                startDate,
                endDate,
                page,
                pageSize);

        return GraceJSONResult.ok(grid);
    }


    @Override
    public GraceJSONResult queryAllList(Integer status, Integer page, Integer pageSize) {

        if (page == null) {
            page = COMMON_START_PAGE;
        }

        if (pageSize == null) {
            pageSize = COMMON_PAGE_SIZE;
        }

        PagedGridResult gridResult = articleService.queryAllArticleListAdmin(status, page, pageSize);

        return GraceJSONResult.ok(gridResult);
    }

    @Override
    public GraceJSONResult doReview(String articleId, Integer passOrNot) {

        Integer pendingStatus;
        if (passOrNot == YesOrNo.YES.type) {
            // 审核成功
            pendingStatus = ArticleReviewStatus.SUCCESS.type;
        } else if (passOrNot == YesOrNo.NO.type) {
            // 审核失败
            pendingStatus = ArticleReviewStatus.FAILED.type;
        } else {
            return GraceJSONResult.errorCustom(ResponseStatusEnum.ARTICLE_REVIEW_ERROR);
        }

        // 保存到数据库，更改文章的状态为审核成功或者失败
        articleService.updateArticleStatus(articleId, pendingStatus);

        return GraceJSONResult.ok();
    }

    @Override
    public GraceJSONResult delete(String userId, String articleId) {
        articleService.deleteArticle(userId, articleId);
        return GraceJSONResult.ok();
    }

    @Override
    public GraceJSONResult withdraw(String userId, String articleId) {
        articleService.withdrawArticle(userId, articleId);
        return GraceJSONResult.ok();
    }
}
