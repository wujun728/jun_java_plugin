package com.buxiaoxia.business.rest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Created by xw on 2017/2/28.
 * 2017-02-28 10:59
 */
@RestController
@RequestMapping("/api/v1.0/hello")
public class HelloController {

	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<String> addHello(@RequestBody String hello){
		return new ResponseEntity<>(hello, HttpStatus.CREATED);
	}


	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<String> getHello(){
		return new ResponseEntity<>("hello ~", HttpStatus.OK);
	}


	@RequestMapping(value = "/{id}",method = RequestMethod.PUT)
	public ResponseEntity<String> updateHello(@PathVariable Integer id){
		return new ResponseEntity<>("hello ~ update:" + id, HttpStatus.OK);
	}


	@RequestMapping(value = "/{id}",method = RequestMethod.DELETE)
	public ResponseEntity<String> deleteHello(@PathVariable Integer id){
		return new ResponseEntity<>("hello ~ delete:" + id, HttpStatus.OK);
	}

}
