package com.trade.global.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.trade.global.common.Constants;
import com.trade.global.entity.SysConfig;
import com.trade.global.mapper.SysConfigMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ConfigService {

    private final SysConfigMapper configMapper;

    public List<SysConfig> getAll() {
        LambdaQueryWrapper<SysConfig> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysConfig::getDeleted, Constants.NOT_DELETED);
        return configMapper.selectList(wrapper);
    }

    public void update(SysConfig config) {
        configMapper.updateById(config);
    }
}
