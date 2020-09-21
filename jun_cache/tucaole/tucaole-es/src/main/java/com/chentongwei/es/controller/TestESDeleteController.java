package com.chentongwei.es.controller;

import com.chentongwei.es.template.ESDeleteTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author TongWei.Chen 2017-12-23 16:13:02
 * @Project tucaole
 * @Description:
 */
@RestController
@RequestMapping("/es/delete")
public class TestESDeleteController {

    @Autowired
    private ESDeleteTemplate esDeleteTemplate;

    @RequestMapping("/deleteDoc")
    public void deleteDoc(String index, String type, String id) {
        esDeleteTemplate.deleteDocument(index, type, id);
    }

    @RequestMapping("/deleteIndex")
    public void deleteIndex(String index) {
        esDeleteTemplate.deleteIndex(index);
    }
}
