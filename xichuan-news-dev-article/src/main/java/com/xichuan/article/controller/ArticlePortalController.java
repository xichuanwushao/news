package com.xichuan.article.controller;

import com.xichuan.api.BaseController;
import com.xichuan.api.article.ArticlePortalControllerApi;
import com.xichuan.article.service.ArticlePortalService;
import com.xichuan.model.pojo.Article;
import com.xichuan.vommon.result.GraceJSONResult;
import com.xichuan.vommon.util.PagedGridResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
/***
 * 如果网站的并发每天都很大 达到千万甚至上亿级别 对于表查询不应该做多表关联查询 一般来说 公司限定3张以下 甚至所有表都是单表查询 所以如果说你的并发和数据量都非常大的时候 那我们在这里就是对我们的文章表做一个单表查询 然后拿这个用户id在用户表做一个查询 查出来后 在controller 或者service把两个list做一个合并 把他以一个对象放到我们的文章列表里面去
 *
 * 目前我们是用户服务 文章服务2个不同的系统 正因为我们有2个系统 后期我们会做出
 * 微服务的 对于微服务来讲 不同的服务是不同的系统 他们是有边界的 各自的职责是不一样的 在文章系统里 只能对文章表做一些相关的查询 用户相关的就不能在文章系统里去做 我们只能在用户系统里面做相应的查询 所以在微服务的规范里面 我们也不应该做一个多表关联的查询 我们要发起一个新的远程调用 在文章服务里面去请求一个用户服务 把用户服务里面相关的用户数据拿过来 拼接到gridresult 这样由前端做一个对应的渲染就可以 所以这里我们可以使用单表查询 做拼接的这种方式
 */

        // START

        List<Article> list = (List<Article>)gridResult.getRows();

        // 1. 构建发布者id列表
        Set<String> idSet = new HashSet<>();
        for (Article a : list) {
//            System.out.println(a.getPublishUserId());
            idSet.add(a.getPublishUserId());
        }
        System.out.println(idSet.toString());



// END


        return GraceJSONResult.ok(gridResult);
    }
}
