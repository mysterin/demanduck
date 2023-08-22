package com.nib.demanduck.mapper;

import com.nib.demanduck.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 用户表 Mapper 接口
 * </p>
 *
 * @author linxiaobin
 * @since 2023-08-17
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {

}
