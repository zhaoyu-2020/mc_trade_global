package com.trade.global.controller;

import com.trade.global.common.Result;
import com.trade.global.entity.SysMessage;
import com.trade.global.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class PublicController {

    private final MessageService messageService;

    @PostMapping("/home/message")
    public Result<Void> submitMessage(@Validated @RequestBody SysMessage message) {
        messageService.submit(message);
        return Result.success();
    }
}
