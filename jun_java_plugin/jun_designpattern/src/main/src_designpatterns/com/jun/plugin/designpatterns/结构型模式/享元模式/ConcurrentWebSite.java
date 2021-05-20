package com.jun.plugin.designpatterns.结构型模式.享元模式;

public class ConcurrentWebSite extends WebSite {
    
    /**
     * 公共的部分，交给父类
     * 
     * 作者: zhoubang 
     * 日期：2015年10月28日 下午3:39:09
     * @param type
     */
    public ConcurrentWebSite(String type) {
        super(type);
    }
    
    
    /**
     * 不同的部分
     * 
     * 作者: zhoubang 
     * 日期：2015年10月28日 下午3:39:26
     * @param user
     * (non-Javadoc)
     * @see com.demo.design_pattern.结构型模式.享元模式.WebSite#use(com.jun.web.biz.beans.demo.design_pattern.结构型模式.享元模式.User)
     */
    @Override
    public void use(User user) {
        System.out.println("该网站的类型是： " + type);
        System.out.println("用户名: " + user.getUserName());
        System.out.println("密码: " + user.getPassWd());
        System.out.println();
    }
}