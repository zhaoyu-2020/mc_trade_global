package com.trade.global.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.trade.global.common.Constants;
import com.trade.global.entity.SysMessage;
import com.trade.global.mapper.SysMessageMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class MessageService {

    private final SysMessageMapper messageMapper;

    public Page<SysMessage> list(Integer current, Integer size) {
        LambdaQueryWrapper<SysMessage> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysMessage::getDeleted, Constants.NOT_DELETED)
               .orderByDesc(SysMessage::getCreateTime);
        
        Page<SysMessage> page = new Page<>(current, size);
        return messageMapper.selectPage(page, wrapper);
    }

    public SysMessage getById(Long id) {
        return messageMapper.selectById(id);
    }

    public void reply(Long id, SysMessage message) {
        SysMessage msg = messageMapper.selectById(id);
        msg.setReplyContent(message.getReplyContent());
        msg.setReplyTime(LocalDateTime.now());
        msg.setIsRead(1);
        messageMapper.updateById(msg);
    }

    public void delete(Long id) {
        messageMapper.deleteById(id);
    }

    public void submit(SysMessage message) {
        message.setIsRead(0);
        messageMapper.insert(message);
    }
}
