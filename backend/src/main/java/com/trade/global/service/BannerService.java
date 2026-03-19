package com.trade.global.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.trade.global.common.Constants;
import com.trade.global.entity.SysBanner;
import com.trade.global.mapper.SysBannerMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BannerService {

    private final SysBannerMapper bannerMapper;

    public List<SysBanner> list() {
        LambdaQueryWrapper<SysBanner> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysBanner::getDeleted, Constants.NOT_DELETED)
               .eq(SysBanner::getStatus, Constants.STATUS_ENABLED)
               .orderByAsc(SysBanner::getSort);
        return bannerMapper.selectList(wrapper);
    }

    public List<SysBanner> getAll() {
        LambdaQueryWrapper<SysBanner> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysBanner::getDeleted, Constants.NOT_DELETED)
               .orderByAsc(SysBanner::getSort);
        return bannerMapper.selectList(wrapper);
    }

    public SysBanner getById(Long id) {
        return bannerMapper.selectById(id);
    }

    public void add(SysBanner banner) {
        bannerMapper.insert(banner);
    }

    public void update(SysBanner banner) {
        bannerMapper.updateById(banner);
    }

    public void delete(Long id) {
        bannerMapper.deleteById(id);
    }
}
