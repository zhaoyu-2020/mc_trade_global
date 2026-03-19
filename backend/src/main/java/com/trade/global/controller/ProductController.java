package com.trade.global.controller;

import com.trade.global.common.Result;
import com.trade.global.entity.SysProduct;
import com.trade.global.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping("/list")
    public Result<List<SysProduct>> list(@RequestParam(required = false) String keyword,
                                          @RequestParam(required = false) Long categoryId,
                                          @RequestParam(defaultValue = "1") Integer current,
                                          @RequestParam(defaultValue = "10") Integer size) {
        return Result.success(productService.list(keyword, categoryId, current, size));
    }

    @GetMapping("/recommend")
    public Result<List<SysProduct>> getRecommend() {
        return Result.success(productService.getRecommend());
    }

    @GetMapping("/{id}")
    public Result<SysProduct> getById(@PathVariable Long id) {
        return Result.success(productService.getById(id));
    }

    @PostMapping
    public Result<Void> add(@RequestBody SysProduct product) {
        productService.add(product);
        return Result.success();
    }

    @PutMapping("/{id}")
    public Result<Void> update(@PathVariable Long id, @RequestBody SysProduct product) {
        product.setId(id);
        productService.update(product);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<Void> delete(@PathVariable Long id) {
        productService.delete(id);
        return Result.success();
    }
}
