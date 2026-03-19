package com.trade.global.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.trade.global.common.Constants;
import com.trade.global.entity.SysArticle;
import com.trade.global.mapper.SysArticleMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ArticleService {

    private final SysArticleMapper articleMapper;

    public Page<SysArticle> list(Long categoryId, Integer isPublish, Integer current, Integer size) {
        LambdaQueryWrapper<SysArticle> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysArticle::getDeleted, Constants.NOT_DELETED);
        
        if (isPublish != null) {
            wrapper.eq(SysArticle::getIsPublish, isPublish);
        }
        if (categoryId != null) {
            wrapper.eq(SysArticle::getCategoryId, categoryId);
        }
        wrapper.orderByDesc(SysArticle::getIsTop)
               .orderByDesc(SysArticle::getCreateTime);
        
        Page<SysArticle> page = new Page<>(current, size);
        return articleMapper.selectPage(page, wrapper);
    }

    public List<SysArticle> getLatest() {
        LambdaQueryWrapper<SysArticle> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysArticle::getDeleted, Constants.NOT_DELETED)
               .eq(SysArticle::getIsPublish, 1)
               .orderByDesc(SysArticle::getCreateTime)
               .last("LIMIT 5");
        return articleMapper.selectList(wrapper);
    }

    public SysArticle getById(Long id) {
        SysArticle article = articleMapper.selectById(id);
        if (article != null) {
            article.setViewCount(article.getViewCount() + 1);
            articleMapper.updateById(article);
        }
        return article;
    }

    public void add(SysArticle article) {
        articleMapper.insert(article);
    }

    public void update(SysArticle article) {
        articleMapper.updateById(article);
    }

    public void delete(Long id) {
        articleMapper.deleteById(id);
    }
}
