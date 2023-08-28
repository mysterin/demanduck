package com.nib.demanduck.mapper;

import com.nib.demanduck.entity.Company;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 公司表 Mapper 接口
 * </p>
 *
 * @author linxiaobin
 * @since 2023-08-28 05:30:36
 */
@Mapper
public interface CompanyMapper extends BaseMapper<Company> {

}
