package com.trade.global.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.trade.global.common.Constants;
import com.trade.global.entity.SysProduct;
import com.trade.global.mapper.SysProductMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final SysProductMapper productMapper;

    public Page<SysProduct> list(String keyword, Long categoryId, Integer current, Integer size) {
        LambdaQueryWrapper<SysProduct> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysProduct::getDeleted, Constants.NOT_DELETED)
               .eq(SysProduct::getStatus, Constants.STATUS_ENABLED);
        
        if (keyword != null && !keyword.isEmpty()) {
            wrapper.and(w -> w.like(SysProduct::getProductName, keyword)
                             .or().like(SysProduct::getProductNameEn, keyword)
                             .or().like(SysProduct::getModel, keyword));
        }
        if (categoryId != null) {
            wrapper.eq(SysProduct::getCategoryId, categoryId);
        }
        wrapper.orderByDesc(SysProduct::getIsRecommend)
               .orderByDesc(SysProduct::getCreateTime);
        
        Page<SysProduct> page = new Page<>(current, size);
        return productMapper.selectPage(page, wrapper);
    }

    public List<SysProduct> getRecommend() {
        LambdaQueryWrapper<SysProduct> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysProduct::getDeleted, Constants.NOT_DELETED)
               .eq(SysProduct::getStatus, Constants.STATUS_ENABLED)
               .eq(SysProduct::getIsRecommend, 1)
               .orderByDesc(SysProduct::getSort)
               .last("LIMIT 6");
        return productMapper.selectList(wrapper);
    }

    public SysProduct getById(Long id) {
        return productMapper.selectById(id);
    }

    public void add(SysProduct product) {
        productMapper.insert(product);
    }

    public void update(SysProduct product) {
        productMapper.updateById(product);
    }

    public void delete(Long id) {
        productMapper.deleteById(id);
    }
}
