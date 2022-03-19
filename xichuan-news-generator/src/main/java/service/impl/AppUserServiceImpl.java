package service.impl;

import com.xichuan.database.entity.AppUser;
import com.xichuan.database.mapper.AppUserMapper;
import service.IAppUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 网站用户 服务实现类
 * </p>
 *
 * @author wuxiao
 * @since 2022-03-19
 */
@Service
public class AppUserServiceImpl extends ServiceImpl<AppUserMapper, AppUser> implements IAppUserService {

}
