/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.redis.proxy.monitor.vo;

import java.util.Date;

/**
 *
 * @author zhanggaofeng
 */
public class User {

        private double id;
        private String name;
        private String addr;
        private int age;
        private Date date;

        public User(double id) {
                this.id = id;
                name = "zhanggaofeng";
                addr = "中国";
                age = 23;
                date = new Date();
        }

        @Override
        public String toString() {
                return "id = " + id + ", name = " + name + ", addr = " + addr + ", age = " + age;
        }

        public double getId() {
                return id;
        }

        public void setId(double id) {
                this.id = id;
        }

        public String getName() {
                return name;
        }

        public void setName(String name) {
                this.name = name;
        }

        public String getAddr() {
                return addr;
        }

        public void setAddr(String addr) {
                this.addr = addr;
        }

        public int getAge() {
                return age;
        }

        public void setAge(int age) {
                this.age = age;
        }

        public Date getDate() {
                return date;
        }

        public void setDate(Date date) {
                this.date = date;
        }
}
