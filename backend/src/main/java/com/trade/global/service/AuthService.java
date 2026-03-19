package com.trade.global.service;

import com.trade.global.common.Constants;
import com.trade.global.entity.SysUser;
import com.trade.global.mapper.SysUserMapper;
import com.trade.global.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final SysUserMapper userMapper;
    private final JwtUtil jwtUtil;

    public Map<String, Object> login(String username, String password) {
        SysUser user = userMapper.findByUsername(username);
        if (user == null) {
            throw new RuntimeException("用户不存在");
        }
        if (user.getStatus() == Constants.STATUS_DISABLED) {
            throw new RuntimeException("账号已被禁用");
        }
        if (!password.equals(user.getPassword())) {
            throw new RuntimeException("密码错误");
        }
        
        String token = jwtUtil.generateToken(user.getId(), user.getUsername(), user.getRoleId());
        
        Map<String, Object> result = new HashMap<>();
        result.put("token", token);
        
        SysUser userInfo = new SysUser();
        userInfo.setId(user.getId());
        userInfo.setUsername(user.getUsername());
        userInfo.setNickname(user.getNickname());
        userInfo.setRoleId(user.getRoleId());
        result.put("userInfo", userInfo);
        
        return result;
    }

    public SysUser getCurrentUser(jakarta.servlet.http.HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        return userMapper.selectById(userId);
    }
}
