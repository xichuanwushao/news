package com.xichuan.article.controller;

import com.xichuan.api.BaseController;
import com.xichuan.api.article.ArticlePortalControllerApi;
import com.xichuan.article.service.ArticlePortalService;
import com.xichuan.vommon.result.GraceJSONResult;
import com.xichuan.vommon.util.PagedGridResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * @author : wuxiao
 * @date : 21:58 2022/4/24
 */
@RestController
public class ArticlePortalController extends BaseController implements ArticlePortalControllerApi {


    final static Logger logger = LoggerFactory.getLogger(ArticlePortalController.class);

    @Autowired
    private ArticlePortalService articlePortalService;

    @Override
    public GraceJSONResult list(String keyword, Integer category, Integer page, Integer pageSize) {
        if (page == null) {
            page = COMMON_START_PAGE;
        }

        if (pageSize == null) {
            pageSize = COMMON_PAGE_SIZE;
        }

        PagedGridResult gridResult
                = articlePortalService.queryIndexArticleList(keyword,
                category,
                page,
                pageSize);
        return GraceJSONResult.ok(gridResult);
    }
}
