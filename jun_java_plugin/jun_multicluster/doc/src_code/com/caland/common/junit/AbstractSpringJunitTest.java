package com.caland.common.junit;

import javax.persistence.MappedSuperclass;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;


@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration("WebRoot")
@ContextHierarchy({
    @ContextConfiguration(name = "parent", locations = {"classpath:application-context.xml","classpath:core-context.xml"}),
    @ContextConfiguration(name = "child", locations = {"classpath:servlet-front.xml"} )
})
@MappedSuperclass
public abstract class AbstractSpringJunitTest {
	
	@Autowired
	protected WebApplicationContext wac;

	protected MockMvc mockMvc;

	@Before
	public void setup() throws Exception {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
		//先登陆 junit专用
//		this.mockMvc.perform(post("/user/login.html")
//			.param("account", "test@163.com")
//			.param("password", "111111"))
//			.andDo(print())
//			.andExpect(MockMvcResultMatchers.redirectedUrl("index.html"));
	}

}
