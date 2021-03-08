package com.abc.demo.controller;


import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class Index {
	private static final Logger LOGGER = LoggerFactory.getLogger(Index.class);

	@RequestMapping("/index")
	public String testHello(Model m) {
		SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dateTime = sdf.format(new Date());
		
		m.addAttribute("dateTime",dateTime);
		LOGGER.info("访问了index,时间"+dateTime);
		return "index";
	}
}
