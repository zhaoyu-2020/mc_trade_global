package com.trade.global.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.trade.global.common.Constants;
import com.trade.global.entity.SysArticleCategory;
import com.trade.global.mapper.SysArticleCategoryMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ArticleCategoryService {

    private final SysArticleCategoryMapper categoryMapper;

    public List<SysArticleCategory> list() {
        LambdaQueryWrapper<SysArticleCategory> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysArticleCategory::getDeleted, Constants.NOT_DELETED)
               .eq(SysArticleCategory::getStatus, Constants.STATUS_ENABLED)
               .orderByAsc(SysArticleCategory::getSort);
        return categoryMapper.selectList(wrapper);
    }

    public List<SysArticleCategory> tree() {
        LambdaQueryWrapper<SysArticleCategory> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysArticleCategory::getDeleted, Constants.NOT_DELETED)
               .orderByAsc(SysArticleCategory::getSort);
        List<SysArticleCategory> categories = categoryMapper.selectList(wrapper);
        return buildTree(categories);
    }

    public SysArticleCategory getById(Long id) {
        return categoryMapper.selectById(id);
    }

    public void add(SysArticleCategory category) {
        categoryMapper.insert(category);
    }

    public void update(SysArticleCategory category) {
        categoryMapper.updateById(category);
    }

    public void delete(Long id) {
        categoryMapper.deleteById(id);
    }

    private List<SysArticleCategory> buildTree(List<SysArticleCategory> categories) {
        List<SysArticleCategory> result = new ArrayList<>();
        List<SysArticleCategory> rootCategories = categories.stream()
                .filter(c -> c.getParentId() == 0)
                .collect(Collectors.toList());
        
        for (SysArticleCategory category : rootCategories) {
            category.setChildren(getChildren(category.getId(), categories));
            result.add(category);
        }
        return result;
    }

    private List<SysArticleCategory> getChildren(Long parentId, List<SysArticleCategory> categories) {
        return categories.stream()
                .filter(c -> c.getParentId().equals(parentId))
                .collect(Collectors.toList());
    }
}
