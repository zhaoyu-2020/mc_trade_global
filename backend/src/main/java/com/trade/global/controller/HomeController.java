package com.trade.global.controller;

import com.trade.global.common.Result;
import com.trade.global.entity.*;
import com.trade.global.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/home")
@RequiredArgsConstructor
public class HomeController {

    private final MenuService menuService;
    private final BannerService bannerService;
    private final ProductService productService;
    private final ArticleService articleService;
    private final ConfigService configService;
    private final AdvantageService advantageService;
    private final ProductCategoryService productCategoryService;
    private final ArticleCategoryService articleCategoryService;

    @GetMapping("/menu")
    public Result<List<SysMenu>> getMenus() {
        return Result.success(menuService.getMenuList());
    }

    @GetMapping("/banner")
    public Result<List<SysBanner>> getBanners() {
        return Result.success(bannerService.list());
    }

    @GetMapping("/product/recommend")
    public Result<List<SysProduct>> getRecommendProducts() {
        return Result.success(productService.getRecommend());
    }

    @GetMapping("/product/category")
    public Result<List<SysProductCategory>> getProductCategories() {
        return Result.success(productCategoryService.tree());
    }

    @GetMapping("/article/latest")
    public Result<List<SysArticle>> getLatestArticles() {
        return Result.success(articleService.getLatest());
    }

    @GetMapping("/article/category")
    public Result<List<SysArticleCategory>> getArticleCategories() {
        return Result.success(articleCategoryService.tree());
    }

    @GetMapping("/advantage")
    public Result<List<SysAdvantage>> getAdvantages() {
        return Result.success(advantageService.list());
    }

    @GetMapping("/config")
    public Result<Map<String, Object>> getConfig() {
        List<SysConfig> configs = configService.getAll();
        Map<String, Object> result = new HashMap<>();
        for (SysConfig config : configs) {
            result.put(config.getConfigKey(), config.getConfigValue());
            result.put(config.getConfigKey() + "_en", config.getConfigValueEn());
        }
        return Result.success(result);
    }
}
