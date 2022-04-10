package com.xichuan.vommon.util;

import com.github.pagehelper.PageHelper;
import com.xichuan.vommon.ruoyi.page.PageDomain;
import com.xichuan.vommon.ruoyi.page.TableSupport;
import com.xichuan.vommon.ruoyi.sql.SqlUtil;
import com.xichuan.vommon.ruoyi.util.StringUtils;

/**
 * 分页工具类
 * 
 * @author ruoyi
 */
public class PageUtils extends PageHelper
{
    /**
     * 设置请求分页数据
     */
    public static void startPage()
    {
        PageDomain pageDomain = TableSupport.buildPageRequest();
        Integer pageNum = pageDomain.getPageNum();
        Integer pageSize = pageDomain.getPageSize();
        if (StringUtils.isNotNull(pageNum) && StringUtils.isNotNull(pageSize))
        {
            String orderBy = SqlUtil.escapeOrderBySql(pageDomain.getOrderBy());
            Boolean reasonable = pageDomain.getReasonable();
            PageHelper.startPage(pageNum, pageSize, orderBy).setReasonable(reasonable);
        }
    }
}
