package com.trade.global.service;

import com.trade.global.entity.SysMessage;
import com.trade.global.mapper.SysMessageMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PublicService {

    private final MessageService messageService;
}
