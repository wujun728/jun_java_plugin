/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.redis.proxy.sample.vo;

import java.io.Serializable;

/**
 *
 * @author zhanggaofeng
 */
public class TestVo implements Serializable{
        
        private String name;
        private int age;
        private String addr;
        
        public TestVo(String name, int age, String addr) {
                this.name = name;
                this.age = age;
                this.addr = addr;
        }
        
        @Override
        public String toString() {
                return "name：" + name + ", age：" + age + ", addr：" + addr;
        }

        public String getName() {
                return name;
        }

        public void setName(String name) {
                this.name = name;
        }

        public int getAge() {
                return age;
        }

        public void setAge(int age) {
                this.age = age;
        }

        public String getAddr() {
                return addr;
        }

        public void setAddr(String addr) {
                this.addr = addr;
        }
        
}
