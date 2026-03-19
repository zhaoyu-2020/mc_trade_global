package com.trade.global.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.trade.global.common.Constants;
import com.trade.global.entity.SysMenu;
import com.trade.global.mapper.SysMenuMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MenuService {

    private final SysMenuMapper menuMapper;

    public List<SysMenu> getMenuList() {
        LambdaQueryWrapper<SysMenu> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysMenu::getDeleted, Constants.NOT_DELETED)
               .eq(SysMenu::getStatus, Constants.STATUS_ENABLED)
               .orderByAsc(SysMenu::getSort);
        List<SysMenu> menus = menuMapper.selectList(wrapper);
        return buildTree(menus);
    }

    public List<SysMenu> getAllMenus() {
        LambdaQueryWrapper<SysMenu> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysMenu::getDeleted, Constants.NOT_DELETED)
               .orderByAsc(SysMenu::getSort);
        return menuMapper.selectList(wrapper);
    }

    public SysMenu getById(Long id) {
        return menuMapper.selectById(id);
    }

    public void add(SysMenu menu) {
        menuMapper.insert(menu);
    }

    public void update(SysMenu menu) {
        menuMapper.updateById(menu);
    }

    public void delete(Long id) {
        menuMapper.deleteById(id);
        LambdaQueryWrapper<SysMenu> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysMenu::getParentId, id);
        menuMapper.delete(wrapper);
    }

    private List<SysMenu> buildTree(List<SysMenu> menus) {
        List<SysMenu> result = new ArrayList<>();
        List<SysMenu> rootMenus = menus.stream()
                .filter(m -> m.getParentId() == 0)
                .collect(Collectors.toList());
        
        for (SysMenu menu : rootMenus) {
            menu.setChildren(getChildren(menu.getId(), menus));
            result.add(menu);
        }
        return result;
    }

    private List<SysMenu> getChildren(Long parentId, List<SysMenu> menus) {
        return menus.stream()
                .filter(m -> m.getParentId().equals(parentId))
                .collect(Collectors.toList());
    }
}
