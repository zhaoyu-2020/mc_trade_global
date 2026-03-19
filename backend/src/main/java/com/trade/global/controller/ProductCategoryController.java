package com.trade.global.controller;

import com.trade.global.common.Result;
import com.trade.global.entity.SysProductCategory;
import com.trade.global.service.ProductCategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/productCategory")
@RequiredArgsConstructor
public class ProductCategoryController {

    private final ProductCategoryService categoryService;

    @GetMapping("/list")
    public Result<List<SysProductCategory>> list() {
        return Result.success(categoryService.list());
    }

    @GetMapping("/tree")
    public Result<List<SysProductCategory>> tree() {
        return Result.success(categoryService.tree());
    }

    @GetMapping("/{id}")
    public Result<SysProductCategory> getById(@PathVariable Long id) {
        return Result.success(categoryService.getById(id));
    }

    @PostMapping
    public Result<Void> add(@RequestBody SysProductCategory category) {
        categoryService.add(category);
        return Result.success();
    }

    @PutMapping("/{id}")
    public Result<Void> update(@PathVariable Long id, @RequestBody SysProductCategory category) {
        category.setId(id);
        categoryService.update(category);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<Void> delete(@PathVariable Long id) {
        categoryService.delete(id);
        return Result.success();
    }
}
