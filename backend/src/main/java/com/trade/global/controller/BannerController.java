package com.trade.global.controller;

import com.trade.global.common.Result;
import com.trade.global.entity.SysBanner;
import com.trade.global.service.BannerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/banner")
@RequiredArgsConstructor
public class BannerController {

    private final BannerService bannerService;

    @GetMapping("/list")
    public Result<List<SysBanner>> list() {
        return Result.success(bannerService.list());
    }

    @GetMapping("/all")
    public Result<List<SysBanner>> getAll() {
        return Result.success(bannerService.getAll());
    }

    @GetMapping("/{id}")
    public Result<SysBanner> getById(@PathVariable Long id) {
        return Result.success(bannerService.getById(id));
    }

    @PostMapping
    public Result<Void> add(@RequestBody SysBanner banner) {
        bannerService.add(banner);
        return Result.success();
    }

    @PutMapping("/{id}")
    public Result<Void> update(@PathVariable Long id, @RequestBody SysBanner banner) {
        banner.setId(id);
        bannerService.update(banner);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<Void> delete(@PathVariable Long id) {
        bannerService.delete(id);
        return Result.success();
    }
}
