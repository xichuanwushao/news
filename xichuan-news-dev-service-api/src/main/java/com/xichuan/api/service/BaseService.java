package com.xichuan.api.service;

import com.github.pagehelper.PageInfo;
import com.xichuan.vommon.util.PagedGridResult;
import com.xichuan.vommon.util.RedisOperator;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author : wuxiao
 * @date : 16:58 2022/4/10
 */
public class BaseService {

    public static final String REDIS_ALL_CATEGORY = "redis_all_category";

    public PagedGridResult setterPagedGrid(List<?> list,
                                           Integer page) {
        PageInfo<?> pageList = new PageInfo<>(list);
        PagedGridResult gridResult = new PagedGridResult();
        gridResult.setRows(list);
        gridResult.setPage(page);
        gridResult.setRecords(pageList.getTotal());
        gridResult.setTotal(pageList.getPages());
        return gridResult;
    }


    @Autowired
    public RedisOperator redis;
}
