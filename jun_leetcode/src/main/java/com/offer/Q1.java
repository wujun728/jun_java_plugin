package com.offer;


import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;


/**
 * 二维数组中查找
 * https://www.nowcoder.com/practice/abc3fe2ce8e146608e868a70efebf62e
 * @author BaoZhou
 * @date 2020-5-23
 */

public class Q1 {

    @Test
    public void result() {
        int[][] array = {

        };
        System.out.println(Find(7, array));
        System.out.println(Find(0, array));
        System.out.println(Find(6, array));
        System.out.println(Find(10, array));
        System.out.println(Find(1, array));
        System.out.println(Find(2, array));
    }


    public boolean Find(int target, int[][] array) {
        int startX = array.length - 1;
        int startY = 0;
        while (startX > -1 && startY < array[0].length) {
            if (array[startX][startY] == target) {
                return true;
            } else if (array[startX][startY] > target) {
                startX--;
            } else if (array[startX][startY] < target) {
                startY++;
            }
        }
        return false;
    }
}



