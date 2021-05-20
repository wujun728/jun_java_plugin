package com.socket.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import com.socket.demo.bean.User;

/**
 * 
 * @author luoweiyi
 *
 */
public class ObjectAndBytes {
	
	/**
	 * @param obj
	 * @return
	 */
	public static byte[] ObjToBytes(Object obj){
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		byte[] bytes = null;
		try {        
            ObjectOutputStream oos = new ObjectOutputStream(bos);       
            oos.writeObject(obj);    
            oos.flush();
            bytes = bos.toByteArray();
            oos.close();     
            bos.close();
        } catch (IOException ex) {    
            ex.printStackTrace();
        }
        return bytes;
	}
	
	/**
	 * @param bytes
	 * @return
	 */
	public static Object BytesToObj(byte[] bytes){
		Object obj = null;
        try {
            ByteArrayInputStream bis = new ByteArrayInputStream (bytes); 
            ObjectInputStream ois = new ObjectInputStream (bis); 
            obj = ois.readObject();
            ois.close();
            bis.close();
        } catch (IOException ex) {   
            ex.printStackTrace();
        } catch (ClassNotFoundException ex) {   
            ex.printStackTrace();
        }
        return obj;
	}
	
	public static void main(String[] args) {
		User user = new User("roger",12);
		byte[] bytes = ObjectAndBytes.ObjToBytes(user);
		
		User us = (User)ObjectAndBytes.BytesToObj(bytes);
		System.out.println(us);
	}

}
