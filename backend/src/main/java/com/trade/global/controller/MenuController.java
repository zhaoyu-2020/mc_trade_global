package com.trade.global.controller;

import com.trade.global.common.Result;
import com.trade.global.entity.SysMenu;
import com.trade.global.service.MenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/menu")
@RequiredArgsConstructor
public class MenuController {

    private final MenuService menuService;

    @GetMapping("/list")
    public Result<List<SysMenu>> list() {
        return Result.success(menuService.getMenuList());
    }

    @GetMapping("/all")
    public Result<List<SysMenu>> getAll() {
        return Result.success(menuService.getAllMenus());
    }

    @GetMapping("/{id}")
    public Result<SysMenu> getById(@PathVariable Long id) {
        return Result.success(menuService.getById(id));
    }

    @PostMapping
    public Result<Void> add(@RequestBody SysMenu menu) {
        menuService.add(menu);
        return Result.success();
    }

    @PutMapping("/{id}")
    public Result<Void> update(@PathVariable Long id, @RequestBody SysMenu menu) {
        menu.setId(id);
        menuService.update(menu);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<Void> delete(@PathVariable Long id) {
        menuService.delete(id);
        return Result.success();
    }
}
