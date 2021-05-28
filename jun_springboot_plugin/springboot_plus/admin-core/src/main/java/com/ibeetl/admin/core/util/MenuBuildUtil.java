package com.ibeetl.admin.core.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.ibeetl.admin.core.entity.CoreMenu;
import com.ibeetl.admin.core.rbac.tree.MenuItem;

public class MenuBuildUtil {
    private MenuBuildUtil() {

    }

    public static MenuItem buildMenuTree(List<CoreMenu> list) {
        CoreMenu root = new CoreMenu();
        root.setId(0L);
        root.setType("");
        root.setName("主菜单");
        MenuItem rootMenu = new MenuItem(root);
        buildTreeNode(rootMenu, list);
        return rootMenu;
    }

    private static void buildTreeNode(MenuItem parent, List<CoreMenu> list) {

        if (parent.getData().getType().equals(CoreMenu.TYPE_MENUITEM)) {
            return;
        }


        long id = parent.getId();
        List<CoreMenu> dels = new ArrayList<>();
        for (CoreMenu sysMenu : list) {
            if (sysMenu.getParentMenuId() == id) {
                MenuItem item = new MenuItem(sysMenu);
                item.setParent(parent);
                dels.add(sysMenu);
            }
        }
        list.removeAll(dels);

        if (list.isEmpty()) {
            return;
        }
        
        sortMenu(parent.getChildren());
        
        for (MenuItem child : parent.getChildren()) {
            buildTreeNode(child, list);
        }

    }

	private static void sortMenu(List<MenuItem> children) {
		Collections.sort(children, new Comparator<MenuItem>() {

			@Override
			public int compare(MenuItem o1, MenuItem o2) {
				return o1.getSeq().compareTo(o2.getSeq());
			}
			
		});
		
	}

}
