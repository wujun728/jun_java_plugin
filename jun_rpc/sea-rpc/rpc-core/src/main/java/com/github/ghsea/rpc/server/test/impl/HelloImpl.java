package com.github.ghsea.rpc.server.test.impl;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.github.ghsea.rpc.server.test.Hello;

public class HelloImpl implements Hello {

	@Override
	public String sayHello() {
		Date now = new Date();
		SimpleDateFormat f = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		return "Hello I'm ghsea.Now is" + f.format(now);
	}
}
