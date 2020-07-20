package com.zccoder.java.book1.ch4.composing;

import com.zccoder.java.book1.ch0.basic.ThreadSafe;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 标题：清单4.15 使用客户端加锁实现“缺少即加入”<br>
 * 描述：使用封装容器的内部锁<br>
 * 时间：2018/10/25<br>
 *
 * @author zc
 **/
@ThreadSafe
public class ListHelper<E> {

    public List<E> list = Collections.synchronizedList(new ArrayList<>());

    public boolean putIfAbsent(E e) {
        synchronized (list) {
            boolean absent = !list.contains(e);
            if (absent) {
                list.add(e);
            }
            return absent;
        }
    }
}
