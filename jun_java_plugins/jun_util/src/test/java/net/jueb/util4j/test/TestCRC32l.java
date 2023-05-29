package net.jueb.util4j.test;
import java.util.HashSet;
import java.util.Set;
import java.util.zip.CRC32;

import org.apache.commons.lang.math.RandomUtils;  

public class TestCRC32l {  

	public static String test(byte[] data)
	{
		long time=System.currentTimeMillis();
        CRC32 crc32 = new CRC32();  
        crc32.update(data);
        long value=crc32.getValue();
        time=System.currentTimeMillis()-time;
        String hexValue=Long.toHexString(crc32.getValue()).toUpperCase();
        System.out.println(time+","+value+":"+hexValue);
        return hexValue;
	}
	
    public static void main(String[] args) {  
        byte[] data=new byte[10*1024*1024];
        Set<String> hexs=new HashSet<String>();
        String hex=test(data);
        hexs.add(hex);
        for(int i=0;i<data.length;i++)
        {
        	for(byte j=-128;j<=127;j++)
        	{
        		byte old=data[i];
        		data[i]=j;
        		String str=test(data);
        		if(hexs.contains(str))
        		{
        			System.out.println("error");
        		}else
        		{
        			hexs.add(str);
        			data[i]=old;
        		}
        	}
        }
    }  
}  