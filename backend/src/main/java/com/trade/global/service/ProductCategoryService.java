package com.trade.global.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.trade.global.common.Constants;
import com.trade.global.entity.SysProductCategory;
import com.trade.global.mapper.SysProductCategoryMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductCategoryService {

    private final SysProductCategoryMapper categoryMapper;

    public List<SysProductCategory> list() {
        LambdaQueryWrapper<SysProductCategory> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysProductCategory::getDeleted, Constants.NOT_DELETED)
               .eq(SysProductCategory::getStatus, Constants.STATUS_ENABLED)
               .orderByAsc(SysProductCategory::getSort);
        return categoryMapper.selectList(wrapper);
    }

    public List<SysProductCategory> tree() {
        LambdaQueryWrapper<SysProductCategory> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysProductCategory::getDeleted, Constants.NOT_DELETED)
               .orderByAsc(SysProductCategory::getSort);
        List<SysProductCategory> categories = categoryMapper.selectList(wrapper);
        return buildTree(categories);
    }

    public SysProductCategory getById(Long id) {
        return categoryMapper.selectById(id);
    }

    public void add(SysProductCategory category) {
        categoryMapper.insert(category);
    }

    public void update(SysProductCategory category) {
        categoryMapper.updateById(category);
    }

    public void delete(Long id) {
        categoryMapper.deleteById(id);
    }

    private List<SysProductCategory> buildTree(List<SysProductCategory> categories) {
        List<SysProductCategory> result = new ArrayList<>();
        List<SysProductCategory> rootCategories = categories.stream()
                .filter(c -> c.getParentId() == 0)
                .collect(Collectors.toList());
        
        for (SysProductCategory category : rootCategories) {
            category.setChildren(getChildren(category.getId(), categories));
            result.add(category);
        }
        return result;
    }

    private List<SysProductCategory> getChildren(Long parentId, List<SysProductCategory> categories) {
        return categories.stream()
                .filter(c -> c.getParentId().equals(parentId))
                .collect(Collectors.toList());
    }
}
