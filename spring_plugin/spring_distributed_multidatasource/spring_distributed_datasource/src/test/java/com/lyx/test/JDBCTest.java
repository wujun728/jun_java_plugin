package com.lyx.test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.lyx.People;
import com.lyx.PersonDao;
import com.lyx.PersonDao0;

@Transactional(rollbackFor = Exception.class)
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = false)
public class JDBCTest {

    @Autowired
    private PersonDao personDao;
    @Autowired
    private PersonDao0 personDao0;

    @Test
    public void test12() {
        People people = new People();
        people.setFirstName("sa");
        people.setLastName("asdasfd");
        this.personDao.addPeople(people);
    }

    @Test
    public void test12121() {
        System.out.println(this.personDao.count());
    }

    @Test
    public void test079876() {
        System.out.println(this.personDao.findPeopleById(8));
    }

    @Test
    public void test2323() {
        System.out.println(this.personDao.findPeopleList(2, 3).size());
    }

    @Test
    public void test80990() {
        // sort的第三种使用方法
        List<Student> students = new ArrayList<Student>();
        students.add(new Student("lyx", 12));
        students.add(new Student("lyx", 1));
        students.add(new Student("lyx", 7));
        students.add(new Student("lyx", 4));
        students.add(new Student("lyx", 3));

        // 实现Comparator接口（使用匿名内部类实现Comparator接口）
        Collections.sort(students, new Comparator<Student>() {
            public int compare(Student o1, Student o2) {
                return o1.getAge() - o2.getAge();
            }
        });

        for (Student s : students) {
            System.out.println(s.toString());
        }
    }


    public void test232323() {

    }
}
