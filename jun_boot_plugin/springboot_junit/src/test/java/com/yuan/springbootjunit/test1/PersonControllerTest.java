/**
 * @Auther: yuanyuan
 * @Date: 2018/8/29 14:28
 * @Description:
 */
package com.yuan.springbootjunit.test1;

import com.yuan.springbootjunit.SpringbootJunitApplication;
import com.yuan.springbootjunit.controller.PersonController;
import com.yuan.springbootjunit.entity.Person;
import com.yuan.springbootjunit.service.PersonService;
import com.yuan.springbootjunit.service.impl.PersonServiceImpl;
import javafx.beans.binding.When;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;


import static org.hamcrest.Matchers.is;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpringbootJunitApplication.class)
@WebAppConfiguration
public class PersonControllerTest {

    private MockMvc mockMvc;


    @Autowired
    private WebApplicationContext wac;



    @Mock
    PersonService personService;

    @InjectMocks
    PersonController personController;

    @Before
    public void setUp() throws Exception {
        this.mockMvc =MockMvcBuilders.webAppContextSetup(wac).build();
    }

    /**
     * Controller 测试
     * MockMvc可以对controller中的一次调用进行模拟，perform就是一次请求，MockMvcRequestBuilders进行url的请求，
     * andExcept方法为对Controller类、调用方法、视图和model的预期设置，andDo进行这次请求的执行，最后andReturn返回。
     */
    @Test
    public void getPersonTest() throws Exception {
        //when().thenReturn()
        int id = 1;
        mockMvc.perform(get("/person/getPerson/"+id))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("data.id").value(1))
                .andExpect(jsonPath("message").value("查询成功"));
    }

    @Test
    public void savePersonTest() throws Exception {
        mockMvc.perform(post("/person/savePerson/").
                contentType(TestUtil.APPLICATION_JSON_UTF8).
        content(TestUtil.convertObjectToJsonBytes(new Person(1,"zhangsan")))
        )
        .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("success",is(true)));
    }

    @Test
    public void deletePersonTest() throws Exception {
        int id = 1;
        mockMvc.perform(delete("/person/deletePerson/"+id))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("success", is(true)))
                ;
    }

    @Test
    public void updatePersonTest() throws Exception {
        mockMvc.perform(
                put("/person/updatePerson")
                        .contentType(TestUtil.APPLICATION_JSON_UTF8)
                        .content(TestUtil.convertObjectToJsonBytes(new Person(1,"zhangsan"))))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("success", is(true)));
        when(personService.getPerson(1)).thenReturn(new Person(1,"lisi"));

         //1. controller mvc test
         mockMvc.perform(get("/person/getPerson/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").value(1))
                .andExpect(jsonPath("name").value("lisi"));

       // verify(personService).getPerson(1);

         //2.service stub test
//        Person stub = new Person(1,"zhangsan");
//        when(personService.getPerson(3)).thenReturn(stub);
//        Assert.assertEquals(stub, personService2.getPerson(3));
//        verify(personService2).getPerson(3);

    }





}
