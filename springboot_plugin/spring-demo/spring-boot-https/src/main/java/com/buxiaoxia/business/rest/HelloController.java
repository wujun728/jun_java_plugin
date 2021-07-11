package com.buxiaoxia.business.rest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by xw on 2017/3/13.
 * 2017-03-13 18:37
 */
@RestController
@RequestMapping("/api/v1.0/hello")
public class HelloController {

	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<String> getHello(){
		return new ResponseEntity<>("hello ~", HttpStatus.OK);
	}

}
