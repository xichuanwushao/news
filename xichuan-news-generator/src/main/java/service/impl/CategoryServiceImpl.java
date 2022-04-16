package service.impl;

import com.xichuan.database.entity.Category;
import com.xichuan.database.mapper.CategoryMapper;
import service.ICategoryService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 新闻资讯文章的分类（或者称之为领域） 服务实现类
 * </p>
 *
 * @author wuxiao
 * @since 2022-04-16
 */
@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements ICategoryService {

}
