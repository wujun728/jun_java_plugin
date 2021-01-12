package com.leetcode.middle.design;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

/**
 * 常数时间插入、删除和获取随机元素
 *
 * @author: BaoZhou
 * @date : 2019/5/14 11:47
 */
class RandomizedSet {
    @Test
    public void test() {
        RandomizedSet obj = new RandomizedSet();
        System.out.println(obj.insert(1));
        System.out.println(obj.remove(2));
        System.out.println(obj.insert(2));
        System.out.println(obj.getRandom());
        System.out.println(obj.remove(1));
        System.out.println(obj.insert(2));
        System.out.println(obj.getRandom());
    }

    /**
     * 记录数值是否已经存在 为了返回True False
     *
     * Key：数值 Val:位置
     */
    HashMap<Integer, Integer> valMap;

    /**
     * Key：位置 Val:数值
     *
     * 找到位置后，从这个map里去寻找对应的数值
     */

    HashMap<Integer, Integer> posMap;
    /**
     * Val：位置
     *
     * 记录还有数值的位置，取随机数的时候可以去对应
     */
    List<Integer> posList;

    int pos;

    /**
     * Initialize your data structure here.
     */
    public RandomizedSet() {
        valMap = new HashMap<>();
        posMap = new HashMap<>();
        posList = new ArrayList<>();
        pos = 0;
    }

    /**
     * Inserts a value to the set. Returns true if the set did not already contain the specified element.
     */
    public boolean insert(int val) {
        if (valMap.get(val) == null) {
            posList.add(pos);
            posMap.put(pos, val);
            valMap.put(val, pos++);
            return true;
        } else {
            return false;
        }
    }

    /**
     * Removes a value from the set. Returns true if the set contained the specified element.
     */
    public boolean remove(int val) {
        if (valMap.get(val) != null) {
            Integer pos = valMap.get(val);
            posList.remove(pos);
            valMap.remove(val);
            posMap.remove(pos);
            return true;
        } else {
            return false;
        }
    }

    /**
     * Get a random element from the set.
     */
    public int getRandom() {
        int i = new Random().nextInt(posList.size());
        return posMap.get(posList.get(i));
    }
}

/**
 * Your RandomizedSet object will be instantiated and called as such:
 * RandomizedSet obj = new RandomizedSet();
 * boolean param_1 = obj.insert(val);
 * boolean param_2 = obj.remove(val);
 * int param_3 = obj.getRandom();
 */