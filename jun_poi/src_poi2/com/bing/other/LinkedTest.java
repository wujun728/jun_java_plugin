package com.bing.other;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedList;

import org.apache.commons.lang3.ArrayUtils;
import org.junit.Test;
import org.omg.CORBA.portable.UnknownException;

import com.google.common.primitives.Primitives;

public class LinkedTest {
	@Test
	public void testTime(){
		
		/*System.out.println(getArrayListTime());
		System.out.println("--------------");
		System.out.println(getLinkedArrayTime());*/
		byte a='a';
		byte c=78;
		char b='a';
		System.out.println(a);
		System.out.println(c);
		System.out.println(b);
		int i=23;
		double d=(double)i;
		System.out.println(d);
	}
	
	public   long getArrayListTime(){
        Collection cl = new ArrayList();
        long start = System.currentTimeMillis();
        for(int i = 0; i < 1000000; i++){
            cl.add(new Date());
            cl.add("a");
        }
        
      
        return System.currentTimeMillis() - start;
    }
    public  long  getLinkedArrayTime(){
        
        Collection cl = new LinkedList();
        long start = System.currentTimeMillis();
        for(int i = 0; i < 1000000; i++){
        	 cl.add(new Date());
            cl.add("a");
        }
    
       
        return System.currentTimeMillis() - start;
    }
    
    @Test
    public void arrayTest(){
    	int[] arr={1,12,45,63,25};
    	Object b=arr;
    	
    	//_____-----以上模仿传过来的参数b---------------
    	Class<?> type = b.getClass().getComponentType();
    	Object[] arrObj=null;
    	int length = Array.getLength(b);
    	for(int i=0;i<length;i++){
    		System.out.println(Array.get(b, i));
    	}
    	
    }
}
