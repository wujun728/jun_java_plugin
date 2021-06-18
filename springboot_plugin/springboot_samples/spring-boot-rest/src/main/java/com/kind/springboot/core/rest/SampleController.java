package com.kind.springboot.core.rest;

import com.kind.springboot.core.dto.ResultMsg;
import com.wordnik.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/sample")
public class SampleController {
    private static final Logger logger = LoggerFactory.getLogger(SampleController.class);

    @RequestMapping(method = RequestMethod.POST)
    @ApiOperation(value = "读取用户")
    public ResponseEntity<ResultMsg> getId(@RequestParam String id) {
        logger.debug("id:" + id);
        return new ResponseEntity<>(ResultMsg.ok(id), HttpStatus.OK);
    }

}
