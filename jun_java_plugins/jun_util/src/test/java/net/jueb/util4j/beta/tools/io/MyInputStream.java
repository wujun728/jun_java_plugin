package net.jueb.util4j.beta.tools.io;

import java.io.IOException;
import java.io.InputStream;

/**
 * 自定义的输入流，对字节数组进行读取
 *
 */
public class MyInputStream extends InputStream {
    //需要处理的字节数组
    protected byte[] data;
    //开始读取的位置
    protected int ptr = 0;
    public MyInputStream(byte[] data){
        this.data = data;
    }
    @Override
    /**
     * 一次读取一个字节
     */
    public synchronized int read() throws IOException {
        if(ptr < data.length)
        {
        	//一个字节是一个8位，即FF，但是由于有符号，所有只能是-128~+127包含-1
        	int value=data[ptr]&0xFF;//为了避免出现8个1，所有都在前面补0,提升为int
        	ptr++;
        	return value;
        }
        else
        {
        	 return -1;
        }      
    }
}