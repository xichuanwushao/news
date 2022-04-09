package com.xichuan.admin.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xichuan.model.pojo.AdminUser;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * 运营管理平台的admin级别用户 Mapper 接口
 * </p>
 *
 * @author wuxiao
 * @since 2022-04-09
 */
@Repository
public interface AdminUserMapper extends BaseMapper<AdminUser> {

}
