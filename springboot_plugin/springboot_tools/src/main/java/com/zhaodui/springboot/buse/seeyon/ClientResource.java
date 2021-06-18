package com.zhaodui.springboot.buse.seeyon;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import com.seeyon.client.CTPRestClient;
import com.seeyon.client.CTPServiceClientManager;

public class ClientResource  {

private static ClientResource clientresource = null;
private String userName  = "dycez";
private String password  = "103e53ee-3b19-4ce3-a2a3-8cfcf008fe89";
private String restUrl   = "http://192.168.10.89:8080";
private String binduser  = "dydemo";

public static ClientResource getInstance(){
if(clientresource == null){
 clientresource = new ClientResource();
 }
 return clientresource;
}



protected CTPRestClient resouresClent(){
CTPServiceClientManager clientManager = CTPServiceClientManager.getInstance(restUrl);
CTPRestClient client = clientManager.getRestClient();
client.authenticate(userName, password);
if(!binduser.equals("{loginName}")){
    client.bindUser(binduser);
}
return client;
}
}