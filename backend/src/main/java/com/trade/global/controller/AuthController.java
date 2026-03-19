package com.trade.global.controller;

import com.trade.global.common.Result;
import com.trade.global.entity.SysUser;
import com.trade.global.service.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public Result<Map<String, Object>> login(@RequestBody Map<String, String> params) {
        String username = params.get("username");
        String password = params.get("password");
        Map<String, Object> result = authService.login(username, password);
        return Result.success(result);
    }

    @PostMapping("/logout")
    public Result<Void> logout() {
        return Result.success();
    }

    @GetMapping("/currentUser")
    public Result<SysUser> getCurrentUser(HttpServletRequest request) {
        SysUser user = authService.getCurrentUser(request);
        return Result.success(user);
    }
}
