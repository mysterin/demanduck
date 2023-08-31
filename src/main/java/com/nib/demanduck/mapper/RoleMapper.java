package com.nib.demanduck.mapper;

import com.nib.demanduck.entity.Role;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 用户角色表 Mapper 接口
 * </p>
 *
 * @author linxiaobin
 * @since 2023-08-28 05:30:36
 */
@Mapper
public interface RoleMapper extends BaseMapper<Role> {

}
