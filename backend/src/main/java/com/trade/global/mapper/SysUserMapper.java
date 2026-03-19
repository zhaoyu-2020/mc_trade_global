package com.trade.global.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.trade.global.entity.SysUser;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SysUserMapper extends BaseMapper<SysUser> {
    SysUser findByUsername(String username);
}
