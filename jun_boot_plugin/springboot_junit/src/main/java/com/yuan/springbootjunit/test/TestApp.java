/**
 * @Auther: yuanyuan
 * @Date: 2018/8/8 14:18
 * @Description:
 */
package com.yuan.springbootjunit.test;

public class TestApp {


    public  int  add(int a,int b){
            return a+b;
    }

    public boolean canVote(int i) {
        if (i<=0) {
            throw new IllegalArgumentException("age should be +");
        }else
        if (i<18) {
            return false;
        } else {
            return true;
        }
    }


}
