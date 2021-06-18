package com.jd.ssh.sshdemo.bean;

import java.io.IOException;

import org.springframework.beans.factory.InitializingBean;

public class SpringClassTest  implements InitializingBean{

	private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
	/*public static SpringClassTest init() throws IOException {
		
		return holder;
	}*/
	
	public void init() {
        System.out.println("init() initializing start.....");
        if(name == null)
            System.out.println("configuration failed!");
        System.out.println("init() initializing end!");
    }

	@Override
	public void afterPropertiesSet() throws Exception {
		// TODO Auto-generated method stub
		System.out.println("Implements initializing start.....");
        if(name == null)
            System.out.println("configuration failed!");
        System.out.println("Implements initializing end!");
	}
}
