/**
 * Program  : dd.java
 * Author   : liuyh
 * Create   : 2010-11-26 обнГ04:46:16
 *
 * Copyright 2010 by Embedded Internet Solutions Inc.,
 * All rights reserved.
 *
 * This software is the confidential and proprietary information
 * of Embedded Internet Solutions Inc.("Confidential Information").  
 * You shall not disclose such Confidential Information and shall 
 * use it only in accordance with the terms of the license agreement 
 * you entered into with Embedded Internet Solutions Inc.
 *
 */

package cn.ipanel.app9.rczp.test;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

import cn.ipanel.app9.rczp.Consts.Consts;



/**
 * 
 * @author liuyh
 * @version 1.0.0
 * @2010-11-26 обнГ04:46:16
 */
public class GetHtmlToUrl {
	public static void setServerProxy() {
		System.getProperties().put("proxySet", true);
		System.getProperties().put("proxyHost", "192.168.10.254");
		System.getProperties().put("proxyPort", "3128");
	}
	/**
	 * @param args
	 * @author liuyh
	 * @create 2010-11-26 обнГ04:46:16
	 */
	public static void main(String[] args) {
		try{   
			if(true){
				setServerProxy();
			}
            URL url = new URL("http://www.baidu.com");   
            URLConnection conn = url.openConnection();   
                
            BufferedReader is = new BufferedReader(new InputStreamReader(conn.getInputStream()));   
            StringBuffer buffer = new StringBuffer();   
            String str;   
            while((str = is.readLine()) != null){   
                buffer.append(str);   
                buffer.append("\n");   
                   
            }   
            str = buffer.toString().replaceAll("<script(.|\n)+?</script>", "");//.replaceAll("<(.|\n)+?>", "").replaceAll("&nbsp;", " ");   
            String[] s = str.split("\n");   
            buffer = new StringBuffer();   
            for(int i=0;i<s.length;i++){   
                if(s[i].trim().equals("") ){   
                    continue;   
                }else{   
                    buffer.append(s[i]);   
                    buffer.append("\n");   
                }   
            }   
            System.out.println(buffer.toString());   
               
            is.close();   
               
        }catch (Exception e) {   
            e.printStackTrace();   
        } 

	}

}

