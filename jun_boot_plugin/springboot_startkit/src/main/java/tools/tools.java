package tools;

import com.google.gson.Gson;
import org.supercall.domainModel.framework.GridColumnConfig;
import org.supercall.domainModel.framework.SimpleGridConfigModel;
import org.supercall.domainModel.framework.TableFilterConfig;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kira on 16/8/3.
 */
public class tools {
    public static void main(String[] args) {
        dict();
    }

    static void dict(){
        List<GridColumnConfig> columnConfig = new ArrayList<>();
        columnConfig.add(new GridColumnConfig("id"));
        columnConfig.add(new GridColumnConfig("字段分组"));
        columnConfig.add(new GridColumnConfig("字典键"));
        columnConfig.add(new GridColumnConfig("字典值"));

        List<TableFilterConfig> tableFilterConfig = new ArrayList<>();
        tableFilterConfig.add(new TableFilterConfig("dictgroup", "字典分组", "input", null, null));
        tableFilterConfig.add(new TableFilterConfig("dictkey", "字段键", "input", null, null));
        SimpleGridConfigModel simpleGridConfigModel =
                new SimpleGridConfigModel("org.supercall.dao.SysDictMapper", columnConfig, tableFilterConfig);
        System.out.println(new Gson().toJson(simpleGridConfigModel));
    }

    static void menu(){
        List<GridColumnConfig> columnConfig = new ArrayList<>();
        columnConfig.add(new GridColumnConfig("id"));
        columnConfig.add(new GridColumnConfig("上级菜单"));
        columnConfig.add(new GridColumnConfig("菜单名称"));
        columnConfig.add(new GridColumnConfig("菜单排序"));
        columnConfig.add(new GridColumnConfig("菜单图标"));
        columnConfig.add(new GridColumnConfig("菜单地址"));
        columnConfig.add(new GridColumnConfig("是否隐藏"));

        List<TableFilterConfig> tableFilterConfig = new ArrayList<>();
        tableFilterConfig.add(new TableFilterConfig("name", "菜单名称", "input", null, null));
        tableFilterConfig.add(new TableFilterConfig("parentid", "上级菜单", "selector", "org.supercall.plugins.MenuManagerPlugin", "getUpperMenuDataSource"));
        tableFilterConfig.add(new TableFilterConfig("isshow", "是否显示", "selector", "org.supercall.plugins.CommonPlugin", "showOrHideDataSource"));
        SimpleGridConfigModel simpleGridConfigModel = new SimpleGridConfigModel("org.supercall.dao.SysMenuMapper", columnConfig, tableFilterConfig);
        System.out.println(new Gson().toJson(simpleGridConfigModel));
    }
}
