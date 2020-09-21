package com.jun.plugin.designpatterns.结构型模式.享元模式;

public class Test {
    /**
     * 
     * 这里我们共享的实例就是WebSite，type为内部参数，也就是公共的属性，User作为外部参数传入，是不同的对象实例。
     * 
     * WebSite 是需要共享的实例对象。
     * 
     */
    public static void main(String[] args) {
        
        /**
         * 
         * 下面的“业务系统”，可以当作一个web系统，在调用WebSiteFactory.createWebSite方法创建的时候，名称一样，则系统是同一个对象
         * 则：【业务系统】是一个对象，，【用户系统】是一个对象，总共2个对象。
         * 
         */
        
        
        WebSite wb1 = WebSiteFactory.createWebSite("业务系统");
        User user1 = new User("root", "root");
        wb1.use(user1);

        WebSite wb2 = WebSiteFactory.createWebSite("业务系统");
        User user2 = new User("admin", "admin");
        wb2.use(user2);

        WebSite wb3 = WebSiteFactory.createWebSite("业务系统");
        User user3 = new User("slave", "slave");
        wb3.use(user3);

        WebSite wb4 = WebSiteFactory.createWebSite("用户系统");
        User user4 = new User("person", "person");
        wb4.use(user4);

        WebSite wb5 = WebSiteFactory.createWebSite("用户系统");
        User user5 = new User("alexis", "alexis");
        wb5.use(user5);

        WebSite wb6 = WebSiteFactory.createWebSite("用户系统");
        User user6 = new User("shadow", "shadow");
        wb6.use(user6);

        System.out.println("网站系统实例的个数: " + WebSiteFactory.webSitesCount());
    }

    /**
     * 最终我们只创建了2个对象实例，却提供给多个对象调用，实现了资源共享，减少系统开销，提高系统性能
     */
}
