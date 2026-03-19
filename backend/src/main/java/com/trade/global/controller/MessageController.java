package com.trade.global.controller;

import com.trade.global.common.Result;
import com.trade.global.entity.SysMessage;
import com.trade.global.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/message")
@RequiredArgsConstructor
public class MessageController {

    private final MessageService messageService;

    @GetMapping("/list")
    public Result<List<SysMessage>> list(@RequestParam(defaultValue = "1") Integer current,
                                         @RequestParam(defaultValue = "10") Integer size) {
        return Result.success(messageService.list(current, size));
    }

    @GetMapping("/{id}")
    public Result<SysMessage> getById(@PathVariable Long id) {
        return Result.success(messageService.getById(id));
    }

    @PutMapping("/{id}/reply")
    public Result<Void> reply(@PathVariable Long id, @RequestBody SysMessage message) {
        messageService.reply(id, message);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<Void> delete(@PathVariable Long id) {
        messageService.delete(id);
        return Result.success();
    }
}
