package com.xichuan.api.article;

import com.xichuan.vommon.result.GraceJSONResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author : wuxiao
 * @date : 21:55 2022/4/24
 */
@Api(value = "门户端文章业务的controller", tags = {"门户端文章业务的controller"})
@RequestMapping("portal/article")
public interface ArticlePortalControllerApi {
    @GetMapping("list")
    @ApiOperation(value = "首页查询文章列表", notes = "首页查询文章列表", httpMethod = "GET")
    public GraceJSONResult list(@RequestParam String keyword,
                                @RequestParam Integer category,
                                @ApiParam(name = "page", value = "查询下一页的第几页", required = false)
                                @RequestParam Integer page,
                                @ApiParam(name = "pageSize", value = "分页的每一页显示的条数", required = false)
                                @RequestParam Integer pageSize);
}
