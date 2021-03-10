package com.zhaodui.springboot.buse.seeyon;

import com.seeyon.client.CTPRestClient;

public class test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ClientResource client =  ClientResource.getInstance();
		CTPRestClient  clientrest = client.resouresClent();
		System.out.print(client.toString());
		String json = clientrest.get("orgMember/?loginName=dydemo"  , String.class);
		System.out.print(json);

	}

}
