package com.xichuan.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xichuan.admin.mapper.CategoryMapper;
import com.xichuan.admin.service.CategoryService;
import com.xichuan.api.service.BaseService;
import com.xichuan.model.pojo.Category;
import com.xichuan.vommon.exception.GraceException;
import com.xichuan.vommon.result.ResponseStatusEnum;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author : wuxiao
 * @date : 14:33 2022/4/16
 */
@Service
public class CategoryServiceImpl extends BaseService implements CategoryService {
    @Resource
    public CategoryMapper categoryMapper;

    @Transactional
    @Override
    public void createCategory(Category category) {
        // 分类不会很多，所以id不需要自增，这个表的数据也不会多到几万甚至分表，数据都会集中在一起
        int result = categoryMapper.insert(category);
        if (result != 1) {
            GraceException.display(ResponseStatusEnum.SYSTEM_OPERATION_ERROR);
        }



    }

    @Transactional
    @Override
    public void modifyCategory(Category category) {
        int result = categoryMapper.updateById(category);
        if (result != 1) {
            GraceException.display(ResponseStatusEnum.SYSTEM_OPERATION_ERROR);
        }
    }

    @Override
    public boolean queryCatIsExist(String catName, String oldCatName) {

        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("name", catName);
        if (StringUtils.isNotBlank(oldCatName)) {
            queryWrapper.eq("name", oldCatName);
        }
        List<Category> catList = categoryMapper.selectList(queryWrapper);
        boolean isExist = false;
        if (catList != null && !catList.isEmpty() && catList.size() > 0) {
            isExist = true;
        }
        return isExist;
    }

    @Override
    public List<Category> queryCategoryList() {
        QueryWrapper queryWrapper = new QueryWrapper();
        List<Category> category = categoryMapper.selectList(queryWrapper);
        return category;
    }

}
