package com.trade.global.controller;

import com.trade.global.common.Result;
import com.trade.global.entity.SysArticleCategory;
import com.trade.global.service.ArticleCategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/articleCategory")
@RequiredArgsConstructor
public class ArticleCategoryController {

    private final ArticleCategoryService categoryService;

    @GetMapping("/list")
    public Result<List<SysArticleCategory>> list() {
        return Result.success(categoryService.list());
    }

    @GetMapping("/tree")
    public Result<List<SysArticleCategory>> tree() {
        return Result.success(categoryService.tree());
    }

    @GetMapping("/{id}")
    public Result<SysArticleCategory> getById(@PathVariable Long id) {
        return Result.success(categoryService.getById(id));
    }

    @PostMapping
    public Result<Void> add(@RequestBody SysArticleCategory category) {
        categoryService.add(category);
        return Result.success();
    }

    @PutMapping("/{id}")
    public Result<Void> update(@PathVariable Long id, @RequestBody SysArticleCategory category) {
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
