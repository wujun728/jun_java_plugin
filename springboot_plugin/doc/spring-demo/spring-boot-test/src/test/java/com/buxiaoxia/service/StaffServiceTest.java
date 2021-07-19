package com.buxiaoxia.service;

import com.buxiaoxia.Application;
import com.buxiaoxia.client.AvatarClient;
import com.buxiaoxia.client.JobClient;
import com.buxiaoxia.entity.Staff;
import com.buxiaoxia.repositrory.StaffRepository;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static junit.framework.TestCase.assertNull;
import static org.junit.Assert.assertEquals;

/**
 * Created by xw on 2017/9/14.
 * 2017-09-14 14:37
 */
@RunWith(SpringJUnit4ClassRunner.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@SpringBootTest(classes = {Application.class})
public class StaffServiceTest {

    @Mock
    private AvatarClient avatarClient;
    @Mock
    private JobClient jobClient;

    @InjectMocks
    @Autowired
    private StaffService staffService;
    @Autowired
    private StaffRepository staffRepository;

    private Staff staff;

    private Staff.Job staffJob;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        staff = new Staff();
        staff.setDes("123");
        staff.setName("张三");


        staffJob = new Staff.Job();
        staffJob.setCapacity("good");
        staffJob.setLevel(1);
        staffJob.setSalary(10000.00d);
    }


    /**
     * 获取员工信息用例，不扩展job信息
     */
    @Test
    public void test_1_get_staff() throws Exception {
        staffRepository.save(staff);
        Mockito.when(this.avatarClient.getAvatar(staff.getId())).thenReturn("http://1231231231");

        Staff getStaff = staffService.getOneStaff(staff.getId(), false);
        assertEquals("张三", getStaff.getName());
        assertEquals("123", getStaff.getDes());
        assertEquals("http://1231231231", getStaff.getHeadUrl());
    }


    /**
     * 获取员工信息用例，扩展job信息
     */
    @Test
    public void test_2_get_staff() throws Exception {
        staffRepository.save(staff);
        Mockito.when(this.avatarClient.getAvatar(staff.getId())).thenReturn("http://1231231231");
        Mockito.when(this.jobClient.getJobInfoByStaffId(staff.getId())).thenReturn(staffJob);

        Staff getStaff = staffService.getOneStaff(staff.getId(), true);

        assertEquals("张三", getStaff.getName());
        assertEquals("123", getStaff.getDes());
        assertEquals("http://1231231231", getStaff.getHeadUrl());

        assertEquals("good", getStaff.getJob().getCapacity());
        assertEquals(1, getStaff.getJob().getLevel());
        assertEquals(new Double(10000.00d), getStaff.getJob().getSalary());

    }


    /**
     * 获取员工信息用例，不扩展job信息，获取头像异常
     */
    @Test
    public void test_3_get_staff() throws Exception {
        staffRepository.save(staff);
        Staff getStaff = staffService.getOneStaff(staff.getId(), false);
        assertEquals("张三", getStaff.getName());
        assertEquals("123", getStaff.getDes());
        assertNull(getStaff.getHeadUrl());
    }


    /**
     * 获取员工信息用例，扩展job信息，获取job异常
     */
    @Test
    public void test_4_get_staff() throws Exception {
        staffRepository.save(staff);
        Mockito.when(this.avatarClient.getAvatar(staff.getId())).thenReturn("http://1231231231");

        Staff getStaff = staffService.getOneStaff(staff.getId(), false);
        assertEquals("张三", getStaff.getName());
        assertEquals("123", getStaff.getDes());
        assertEquals("http://1231231231", getStaff.getHeadUrl());
        assertNull(getStaff.getJob());
    }

}
