package com.trade.global.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.trade.global.common.Constants;
import com.trade.global.entity.SysAdvantage;
import com.trade.global.mapper.SysAdvantageMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdvantageService {

    private final SysAdvantageMapper advantageMapper;

    public List<SysAdvantage> list() {
        LambdaQueryWrapper<SysAdvantage> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysAdvantage::getDeleted, Constants.NOT_DELETED)
               .eq(SysAdvantage::getStatus, Constants.STATUS_ENABLED)
               .orderByAsc(SysAdvantage::getSort);
        return advantageMapper.selectList(wrapper);
    }

    public SysAdvantage getById(Long id) {
        return advantageMapper.selectById(id);
    }

    public void add(SysAdvantage advantage) {
        advantageMapper.insert(advantage);
    }

    public void update(SysAdvantage advantage) {
        advantageMapper.updateById(advantage);
    }

    public void delete(Long id) {
        advantageMapper.deleteById(id);
    }
}
