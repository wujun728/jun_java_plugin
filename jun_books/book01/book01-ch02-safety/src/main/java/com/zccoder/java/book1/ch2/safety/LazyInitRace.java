package com.zccoder.java.book1.ch2.safety;

import com.zccoder.java.book1.ch0.basic.NotThreadSafe;

/**
 * 标题：惰性初始化（懒加载）中存在竞争条件（不要这样做）<br>
 * 描述：竞争条件：检查再运行<br>
 * 时间：2018/10/24<br>
 *
 * @author zc
 **/
@NotThreadSafe
public class LazyInitRace {

    private ExpensiveObject instance = null;

    public ExpensiveObject getInstance() {
        // 比如线程A和B都执行getInstance()方法，A看到instance是null，并实例化一个新的对象；
        // B看到instance也是null，也实例化一个新的对象。导致两个getInstance的调用者得到不同的对象实例。
        if (instance == null) {
            instance = new ExpensiveObject();
        }
        return instance;
    }
}
