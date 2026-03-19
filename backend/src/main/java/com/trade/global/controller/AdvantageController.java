package com.trade.global.controller;

import com.trade.global.common.Result;
import com.trade.global.entity.SysAdvantage;
import com.trade.global.service.AdvantageService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/advantage")
@RequiredArgsConstructor
public class AdvantageController {

    private final AdvantageService advantageService;

    @GetMapping("/list")
    public Result<List<SysAdvantage>> list() {
        return Result.success(advantageService.list());
    }

    @GetMapping("/{id}")
    public Result<SysAdvantage> getById(@PathVariable Long id) {
        return Result.success(advantageService.getById(id));
    }

    @PostMapping
    public Result<Void> add(@RequestBody SysAdvantage advantage) {
        advantageService.add(advantage);
        return Result.success();
    }

    @PutMapping("/{id}")
    public Result<Void> update(@PathVariable Long id, @RequestBody SysAdvantage advantage) {
        advantage.setId(id);
        advantageService.update(advantage);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<Void> delete(@PathVariable Long id) {
        advantageService.delete(id);
        return Result.success();
    }
}
