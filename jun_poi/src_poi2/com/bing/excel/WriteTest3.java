package com.bing.excel;

import java.util.List;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Before;
import org.junit.Test;

import com.bing.excel.annotation.CellConfig;
import com.bing.excel.annotation.OutAlias;
import com.bing.excel.core.BingExcelEvent;
import com.bing.excel.core.BingExcelEventBuilder;
import com.bing.excel.core.BingWriterHandler;

import com.google.common.base.MoreObjects;
import com.google.common.collect.Lists;

/**
 * @author shizhongtao
 */
public class WriteTest3 {
    BingExcelEvent bing;

    @Before
    public void before() {
        bing = BingExcelEventBuilder.builderInstance();
    }

    @Test
    public void testWrite() {
        /**
         * 对于数据量非常大时候，注意一点就是数据绝对不能放入到内存，
         * 你如果想初始化一个长多为百万级的list，劝你趁早放弃
         */
        //测试百万级的写出,
        BingWriterHandler writerHandler = bing.writeFile("E:/test/bigdata.xlsx");
       // writerHandler.setMaxLine(100000);
        for (int i = 0; i < 1000000; i++) {
            writerHandler.writeLine(new Person(23, RandomStringUtils.randomAlphanumeric(4), Math.random() * 1000));
        }
        writerHandler.close();
    }

    @OutAlias("xiaoshou")
    public static class Person {

        public Person(int age, String name, Double salary) {
            super();
            this.age = age;
            this.name = name;
            this.salary = salary;
        }

        public Person() {
            super();
        }

        @CellConfig(index = 1, aliasName = "年龄")
        private int age;
        @CellConfig(index = 0)
        private String name;
        @CellConfig(index = 3)
        private Double salary;
        private transient boolean testProperty = false;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getAge() {
            return age;
        }

        public Double getSalary() {
            return salary;
        }

        public String toString() {
            return MoreObjects.toStringHelper(this.getClass()).omitNullValues()
                    .add("name", name).add("age", age).add("salary", salary)
                    .toString();
        }
    }
}
