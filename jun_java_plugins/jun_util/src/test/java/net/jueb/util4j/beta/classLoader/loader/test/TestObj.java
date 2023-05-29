package net.jueb.util4j.beta.classLoader.loader.test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *@author juebanlin
 *@email juebanlin@gmail.com
 *@createTime 2015年4月12日 下午8:03:20
 **/
public class TestObj implements Runnable{

	Logger log=LoggerFactory.getLogger(getClass());
	private int version=1;
	
	@Override
	public void run() {
		String info="version="+version+",classLoader="+getClass().getClassLoader();
		log.info(info);
	}
	
}
