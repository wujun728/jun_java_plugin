package com.buxiaoxia;

import com.buxiaoxia.controller.DepartmentControllerTest;
import com.buxiaoxia.controller.StaffControllerTest;
import com.buxiaoxia.service.StaffServiceTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * Created by xw on 2017/9/14.
 * 2017-09-14 14:12
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({DepartmentControllerTest.class, StaffControllerTest.class, StaffServiceTest.class})
public class ApplicationTest {
}
