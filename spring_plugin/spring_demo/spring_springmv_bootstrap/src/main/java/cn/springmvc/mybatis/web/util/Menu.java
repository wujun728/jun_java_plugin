package cn.springmvc.mybatis.web.util;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Vincent.wang
 *
 */
public class Menu {

    private String name;

    private String url;

    private List<Menu> menus;

    public Menu() {

    }

    public Menu(String name, String url) {
        this.name = name;
        this.url = url;
    }

    public static List<Menu> loadMenus() {
        List<Menu> menus = new ArrayList<Menu>();

        Menu m1 = new Menu("文件上传", "upload");
        List<Menu> m1s = new ArrayList<Menu>();
        m1s.add(new Menu("ajax上传文件", "upload/ajax"));
        m1s.add(new Menu("spring上传文件", "upload/spring"));
        m1.setMenus(m1s);

        Menu m2 = new Menu("文件下载", "download");
        List<Menu> m2s = new ArrayList<Menu>();
        m2s.add(new Menu("打zip包", "download/zip"));
        m2.setMenus(m2s);

        menus.add(m1);
        menus.add(m2);
        return menus;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public List<Menu> getMenus() {
        return menus;
    }

    public void setMenus(List<Menu> menus) {
        this.menus = menus;
    }

}
