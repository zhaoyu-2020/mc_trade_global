package com.trade.global.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.trade.global.common.Constants;
import com.trade.global.entity.SysUser;
import com.trade.global.mapper.SysUserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final SysUserMapper userMapper;

    public List<SysUser> list() {
        LambdaQueryWrapper<SysUser> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysUser::getDeleted, Constants.NOT_DELETED)
               .orderByDesc(SysUser::getCreateTime);
        return userMapper.selectList(wrapper);
    }

    public SysUser getById(Long id) {
        return userMapper.selectById(id);
    }

    public void add(SysUser user) {
        SysUser existUser = userMapper.findByUsername(user.getUsername());
        if (existUser != null) {
            throw new RuntimeException("用户名已存在");
        }
        user.setStatus(Constants.STATUS_ENABLED);
        userMapper.insert(user);
    }

    public void update(SysUser user) {
        SysUser existUser = userMapper.selectById(user.getId());
        if (existUser == null) {
            throw new RuntimeException("用户不存在");
        }
        userMapper.updateById(user);
    }

    public void updateStatus(Long id, Integer status) {
        SysUser user = new SysUser();
        user.setId(id);
        user.setStatus(status);
        userMapper.updateById(user);
    }

    public void updatePassword(Long id, String password) {
        SysUser user = new SysUser();
        user.setId(id);
        user.setPassword(password);
        userMapper.updateById(user);
    }

    public void delete(Long id) {
        SysUser user = userMapper.selectById(id);
        if (user != null && user.getRoleId() == Constants.ADMIN_ROLE) {
            throw new RuntimeException("不能删除超级管理员");
        }
        userMapper.deleteById(id);
    }
}
