package org.supercall.plugins;

import org.springframework.stereotype.Repository;
import org.supercall.common.CommonUtility;
import org.supercall.dao.SysMenuMapper;
import org.supercall.domainModel.SelectorDataSource;
import org.supercall.models.SysMenu;
import org.supercall.models.SysMenuExample;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by kira on 16/8/2.
 */
public class MenuManagerPlugin {

    public MenuManagerPlugin() {

    }

    public List<SelectorDataSource> getUpperMenuDataSource() {
        SysMenuMapper sysMenuMapper = CommonUtility.applicationContext.getBean(SysMenuMapper.class);
        SysMenuExample example = new SysMenuExample();
        example.createCriteria().andParentidIsNull();
        List<SysMenu> menus = sysMenuMapper.selectByExample(example);

        List<SelectorDataSource> selectorDataSources = new ArrayList<>();
        menus.forEach(e -> {
            SelectorDataSource selectorDataSource = new SelectorDataSource(e.getId().toString(), e.getName());
            selectorDataSources.add(selectorDataSource);
        });
        return selectorDataSources;
    }
}
