package com.trade.global.controller;

import com.trade.global.common.Result;
import com.trade.global.entity.SysConfig;
import com.trade.global.service.ConfigService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/config")
@RequiredArgsConstructor
public class ConfigController {

    private final ConfigService configService;

    @GetMapping("/all")
    public Result<List<SysConfig>> getAll() {
        return Result.success(configService.getAll());
    }

    @PutMapping("/{id}")
    public Result<Void> update(@PathVariable Long id, @RequestBody SysConfig config) {
        config.setId(id);
        configService.update(config);
        return Result.success();
    }
}
