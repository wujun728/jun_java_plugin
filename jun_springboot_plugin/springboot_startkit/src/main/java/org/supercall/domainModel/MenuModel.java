package org.supercall.domainModel;

import java.util.List;

/**
 * Created by kira on 16/8/2.
 */
public class MenuModel {
    private String name;

    private String urls;

    private List<MenuModel> childrens;

    private String active;

    private String classStyle;

    public MenuModel(String name, String urls, List<MenuModel> childrens, String active, String classStyle) {
        this.name = name;
        this.urls = urls;
        this.childrens = childrens;
        this.active = active;
        this.classStyle = classStyle;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrls() {
        return urls;
    }

    public void setUrls(String urls) {
        this.urls = urls;
    }

    public List<MenuModel> getChildrens() {
        return childrens;
    }

    public void setChildrens(List<MenuModel> childrens) {
        this.childrens = childrens;
    }

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }

    public String getClassStyle() {
        return classStyle;
    }

    public void setClassStyle(String classStyle) {
        this.classStyle = classStyle;
    }
}
