package com.chentongwei.es.controller;

import com.chentongwei.es.template.ESCreateTemplate;
import com.chentongwei.es.template.ESDeleteTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

/**
 * @author Wujun
 * @Project tucaole
 * @Description:
 */
@RestController
@RequestMapping("/es/create")
public class TestESCreateController {

    @Autowired
    private ESCreateTemplate esCreateTemplate;

    @RequestMapping("/createDoc")
    public void createDoc(String index, String type) {
        Map map = new HashMap();
        map.put("id", 2);
        map.put("name", "q3123哈1231312哈哈");
        esCreateTemplate.createDocument(index, type, map);
    }

    @RequestMapping("/createDoc2")
    public void createDoc(String index, String type, String id) {
        Demo demo = new Demo();
        demo.setId(11);
        demo.setName("测试name");
        demo.setPrice(1.09);
        demo.setCreateTime(new Date());
        esCreateTemplate.createDocument(index, type, id, demo);
    }

    @RequestMapping("/createDoc3")
    public void createDoc3(String index, String type) {
        Demo demo = new Demo();
        demo.setId(1);
        demo.setName("测试name1");
        demo.setPrice(1.091);
        demo.setCreateTime(new Date());
        Demo demo2 = new Demo();
        demo2.setId(2);
        demo2.setName("测试name2");
        demo2.setPrice(1.092);
        demo2.setCreateTime(new Date());
        List list = new ArrayList();
        list.add(demo);
        list.add(demo2);
        esCreateTemplate.createMultiDocument(index, type, list);
    }
}
class Demo {
    private Integer id;
    private String name;
    private Double price;
    private Date createTime;

    public Demo() {
    }

    public Demo(Integer id, String name, Double price, Date createTime) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.createTime = createTime;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}