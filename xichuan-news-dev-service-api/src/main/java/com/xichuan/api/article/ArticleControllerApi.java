package com.xichuan.api.article;

import com.xichuan.model.pojo.bo.NewArticleBO;
import com.xichuan.vommon.result.GraceJSONResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.Date;

/**
 * @author : wuxiao
 * @date : 23:12 2022/4/20
 */
@Api(value = "文章业务的controller", tags = {"文章业务的controller"})
@RequestMapping("article")
public interface ArticleControllerApi {

    @PostMapping("createArticle")
    @ApiOperation(value = "用户发文", notes = "用户发文", httpMethod = "POST")
    public GraceJSONResult createArticle(@RequestBody @Valid NewArticleBO newArticleBO,
                                         BindingResult result);


    @PostMapping("queryMyList")
    @ApiOperation(value = "查询用户的所有文章列表", notes = "查询用户的所有文章列表", httpMethod = "POST")
    public GraceJSONResult queryMyList(@RequestParam String userId,
                                       @RequestParam String keyword,
                                       @RequestParam Integer status,
                                       @RequestParam Date startDate,
                                       @RequestParam Date endDate,
                                       @RequestParam Integer page,
                                       @RequestParam Integer pageSize);

    @PostMapping("queryAllList")
    @ApiOperation(value = "管理员查询用户的所有文章列表", notes = "管理员查询用户的所有文章列表", httpMethod = "POST")
    public GraceJSONResult queryAllList(@RequestParam Integer status,
                                        @ApiParam(name = "page", value = "查询下一页的第几页", required = false)
                                        @RequestParam Integer page,
                                        @ApiParam(name = "pageSize", value = "分页的每一页显示的条数", required = false)
                                        @RequestParam Integer pageSize);
}
