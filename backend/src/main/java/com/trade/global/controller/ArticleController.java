package com.trade.global.controller;

import com.trade.global.common.Result;
import com.trade.global.entity.SysArticle;
import com.trade.global.service.ArticleService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/article")
@RequiredArgsConstructor
public class ArticleController {

    private final ArticleService articleService;

    @GetMapping("/list")
    public Result<List<SysArticle>> list(@RequestParam(required = false) Long categoryId,
                                          @RequestParam(required = false) Integer isPublish,
                                          @RequestParam(defaultValue = "1") Integer current,
                                          @RequestParam(defaultValue = "10") Integer size) {
        return Result.success(articleService.list(categoryId, isPublish, current, size));
    }

    @GetMapping("/latest")
    public Result<List<SysArticle>> getLatest() {
        return Result.success(articleService.getLatest());
    }

    @GetMapping("/{id}")
    public Result<SysArticle> getById(@PathVariable Long id) {
        return Result.success(articleService.getById(id));
    }

    @PostMapping
    public Result<Void> add(@RequestBody SysArticle article) {
        articleService.add(article);
        return Result.success();
    }

    @PutMapping("/{id}")
    public Result<Void> update(@PathVariable Long id, @RequestBody SysArticle article) {
        article.setId(id);
        articleService.update(article);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<Void> delete(@PathVariable Long id) {
        articleService.delete(id);
        return Result.success();
    }
}
