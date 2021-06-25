package com.zz.springservlet3.test.controller;

import com.zz.springservlet3.config.AppConfig;
import com.zz.springservlet3.config.web.WebMvcConfig;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {WebMvcConfig.class, AppConfig.class})
@WebAppConfiguration
//@IfProfileValue(name = "java.vendor", value = "Oracle Corporation")
public abstract class BaseControllerTest {

    protected MockMvc mockMvc;
    @Autowired
    private WebApplicationContext wac;
    @Autowired
    private MockHttpServletRequest request;

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }
}
