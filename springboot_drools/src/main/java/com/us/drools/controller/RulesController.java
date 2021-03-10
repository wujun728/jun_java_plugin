package com.us.drools.controller;

import com.us.drools.service.RulesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 * Created by yangyibo on 16/12/9.
 */
@Controller
@RequestMapping(value = "/rules")
public class RulesController {

    @Autowired
    private RulesService rulesService;

    @RequestMapping(value ="/{id}" ,method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public ResponseEntity<Object> getRule(@PathVariable Integer id) {

        return new ResponseEntity<>(rulesService.getRules(id), HttpStatus.OK);
    }

    @RequestMapping(value ="/write/{id}" ,method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public ResponseEntity<Object> getRuleByWrite(@PathVariable Integer id) {

        return new ResponseEntity<>(rulesService.getRulesWrite(id), HttpStatus.OK);
    }

}
