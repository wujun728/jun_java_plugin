package com.jun.base.map;

import java.util.*;

/*
http://liuyuan418921673.iteye.com/blog/2256120

Collection ：  表示一组数据的类
  List和Set都继承了Collection
         列表List: 有序的 Collection
                  常用的List实现类：       (取出元素可以用迭代器和下标)
          ArrayList, LinkedList,Vector 都是List的实现类
            Stack是Vector的子类
               ArrayList  数组序列
               LinkedList 链表
               Stack      栈
               Vector     向量
          集合Set: 无重复的 Collection
                 常用的Set实现类：   取出元素用迭代器（没有get方法）
              HashSet()
            TreeSet()

映射 Map<K,V>
          将键映射到值的一种结构
          键是一个Set，键不能重复
          每一个键都对应一个值
               常用的Map实现类：
         HashMap():键是一个HashSet
       TreeMap():键是一个TreeSet
 */
public class ReList {

    public static void main(String[] args) {

        List e =  Collections.emptyList();

        Collection  c = new ArrayList<>();

        List l = new ArrayList();

        LinkedList lk = new LinkedList();


        /*
        Vector和 ArrayList一样，都是大小可变的数组的实现
                       区别： ArrayList不是同步的
             Vector是同步的，在多线程中一般采用Vector
           使用方式和 ArrayList一样
         */
        Vector v = new Vector();

        /*
        stack:栈
            它通过五个操作对类 Vector 进行了扩展
            它提供了通常的 push 和 pop 操作，
                    取堆栈顶点的 peek 方法
                    测试堆栈是否为空的 empty 方法
                   在堆栈中查找项并确定到堆栈顶距离的 search 方法。
          后进先出
          最先放入的数据在栈的底部
          最后放入的数据在栈的顶部
          每次取数据都只能取到栈顶的数据

         */

        Stack s = new Stack();



    }
}
