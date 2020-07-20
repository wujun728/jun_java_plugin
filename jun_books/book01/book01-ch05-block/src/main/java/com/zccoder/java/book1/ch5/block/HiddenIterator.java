package com.zccoder.java.book1.ch5.block;

import com.zccoder.java.book1.ch0.basic.GuardedBy;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

/**
 * 标题：清单5.6 迭代隐藏在字符串的拼接中（不要这样做）<br>
 * 描述：<br>
 * 时间：2018/10/26<br>
 *
 * @author zc
 **/
public class HiddenIterator {

    @GuardedBy("this")
    private final Set<Integer> set = new HashSet<>(16);

    public synchronized void add(Integer i) {
        set.add(i);
    }

    public synchronized void remove(Integer i) {
        set.remove(i);
    }

    public void addTenTings() {
        Random random = new Random();
        int ten = 10;
        for (int i = 0; i < ten; i++) {
            add(i);
        }
        // 字符串的拼接进过编译会调用SpringBuilder.append(Object)来完成，它会调用容器的toString方法。
        // 而标准容器中的toString方法会通过迭代容器中的每个元素来实现。
        System.out.println("DEBUG: added ten elements to " + set);
    }

}
