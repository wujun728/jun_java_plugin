package controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
//import org.springframework.http.converter.StringHttpMessageConverter;
//import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
//import org.springframework.http.converter.json.MappingJacksonHttpMessageConverter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import po.Actor;
import po.MSG;

import com.alibaba.fastjson.JSON;


@Controller
public class RESTController {
	@Autowired
	RestTemplate rest;
	
	@RequestMapping(value="/rest")
	public String rest(){
		return "rest";
	}
	
	@RequestMapping(value="/actors/1",method = RequestMethod.GET)
	@ResponseBody
	public MSG getactor(){
		MSG a=rest.getForObject("http://localhost:8081/Spring-REST-Server/actors/1", MSG.class);
		return new MSG("200",a.getData());
	}
	
	@RequestMapping(value="/actors",method = RequestMethod.GET)
	@ResponseBody
	public MSG getactors(){
		MSG a=rest.getForObject("http://localhost:8081/Spring-REST-Server/actors", MSG.class);
		return new MSG("200",a.getData());
	}
	
	@RequestMapping(value="/actors/{id}",method = RequestMethod.DELETE)
	@ResponseBody
	public MSG delete(@PathVariable("id")String id){
		rest.delete("http://localhost:8081/Spring-REST-Server/actors/{id}", id);
		MSG msg=new MSG();
		msg.setStatus("ok");
		return msg;
	}
	
	@RequestMapping(value="/actors",method = RequestMethod.POST,consumes="application/json")
	@ResponseBody
	public Actor add(@RequestBody Actor actor){
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<Actor> entity = new HttpEntity<Actor>(actor,headers);
		Actor a=rest.postForObject("http://localhost:8081/Spring-REST-Server/actors",entity,Actor.class);
		return a;
	}
	
	@RequestMapping(value="/actors/{id}",method = RequestMethod.PUT)
	@ResponseBody
	public MSG updateactor(@PathVariable("id") int id,@RequestBody Actor actor){
		actor.setId(id);
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<Actor> entity = new HttpEntity<Actor>(actor,headers);
		rest.put("http://localhost:8081/Spring-REST-Server/actors/1", entity);
		MSG msg=new MSG();
		msg.setStatus("ok");
		return msg;
	}
	
}
