package com.leetcode.weekly.weekly138;

import org.junit.jupiter.api.Test;

/**
 * 爱生气的书店老板
 *
 * @author BaoZhou
 * @date 2019/5/27 10:03
 */
public class MaxSatisfied {

    @Test
    public void test() {
        int customers[] = {1, 0, 1, 2, 1, 1, 7, 5};
        int grumpy[] =    {0, 1, 0, 1, 0, 1, 0, 1};
        int X = 3;
        System.out.println(maxSatisfied(customers, grumpy, X));
    }


    public int maxSatisfied(int[] customers, int[] grumpy, int X) {
        int now = 0;
        for(int i=0; i<customers.length; i++){
            if(grumpy[i]==0) {
                now+=customers[i];
            }
        }
        int res = now;
        for(int i=0, j=0; i<customers.length; i++){
            now += grumpy[i]==1 ? customers[i] : 0;
            if(i>=X){
                now -= grumpy[j]==1 ? customers[j] : 0;
                j++;
            }
            res = Math.max(now, res);
        }
        return res;
    }
}
