package service.impl;

import com.xichuan.database.entity.AdminUser;
import com.xichuan.database.mapper.AdminUserMapper;
import service.IAdminUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 运营管理平台的admin级别用户 服务实现类
 * </p>
 *
 * @author wuxiao
 * @since 2022-04-09
 */
@Service
public class AdminUserServiceImpl extends ServiceImpl<AdminUserMapper, AdminUser> implements IAdminUserService {

}
