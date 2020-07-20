package com.zccoder.java.book1.ch3.sharing;

import com.zccoder.java.book1.ch0.basic.Immutable;

import java.util.HashSet;
import java.util.Set;

/**
 * 标题：不可变类<br>
 * 描述：构造于底层可变对象之上的不可变类<br>
 * 时间：2018/10/25<br>
 *
 * @author zc
 **/
@Immutable
public final class ThreeStooges {

    private final Set<String> stooges = new HashSet<>(4);

    public ThreeStooges() {
        stooges.add("Moe");
        stooges.add("Larry");
        stooges.add("Curly");
    }

    public boolean isStooge(String name) {
        return stooges.contains(name);
    }

}
