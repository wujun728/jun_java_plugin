package designpatterns.行为型模式.类和类之间的关系.迭代子模式;

/**
 * 定义集合操作的接口方法
 * 
 * 作者: zhoubang 
 * 日期：2015年10月28日 下午5:28:01
 */
public interface Collection {
    
    //集合数据的其他操作，交给Iterator实现类解决
    public Iterator iterator();

    /* 取得集合元素 */
    public Object get(int i);

    /* 取得集合大小 */
    public int size();
}