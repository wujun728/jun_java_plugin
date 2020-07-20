package com.zccoder.java.book1.ch4.composing;

import com.zccoder.java.book1.ch0.basic.GuardedBy;
import com.zccoder.java.book1.ch0.basic.ThreadSafe;

import java.util.HashSet;
import java.util.Set;

/**
 * 标题：使用限制确保线程安全<br>
 * 描述：通过限制与锁协同保证一个类的线程安全性，即使它的状态变量不是线程安全的<br>
 * 时间：2018/10/25<br>
 *
 * @author zc
 **/
@ThreadSafe
public class PersonSet {

    @GuardedBy("this")
    private final Set<Person> personSet = new HashSet<>(16);

    public synchronized void addPerson(Person person) {
        personSet.add(person);
    }

    public synchronized boolean containsPerson(Person person) {
        return personSet.contains(person);
    }

}
