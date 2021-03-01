package designpatterns.结构型模式.享元模式;

/**
 * 共享的实例。type为公共的属性，user是不同的对象实例
 * 
 * 作者: zhoubang 
 * 日期：2015年10月28日 下午3:32:50
 */
public abstract class WebSite {
    
    protected String type;//内部参数

    public WebSite(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public abstract void use(User user);
}