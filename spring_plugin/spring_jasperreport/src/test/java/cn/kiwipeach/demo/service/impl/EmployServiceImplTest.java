package cn.kiwipeach.demo.service.impl;

import cn.kiwipeach.demo.SpringJunitBase;
import cn.kiwipeach.demo.domain.Employ;
import cn.kiwipeach.demo.service.EmployService;
import com.alibaba.fastjson.JSON;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;

import static org.junit.Assert.*;

/**
 * ${DESCRIPTION}
 *
 * @author Wujun
 * @create 2018/01/08
 **/
public class EmployServiceImplTest extends SpringJunitBase{


    @Autowired
    private EmployService employService;

    @Test
    public void queryEmploy() throws Exception {
        Employ employ = employService.queryEmploy(new BigDecimal("7777"));
        System.out.println(JSON.toJSONString(employ));
    }

}