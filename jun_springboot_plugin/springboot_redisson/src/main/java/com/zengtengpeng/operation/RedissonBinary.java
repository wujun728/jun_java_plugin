package com.zengtengpeng.operation;

import org.redisson.api.*;

import javax.annotation.Resource;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * 操作对象二进制
 */
public class RedissonBinary {

    @Resource
    private RedissonClient redissonClient;

    /**
     * 获取输出流
     * @param name
     * @return
     */
    public  OutputStream getOutputStream(String name) {
        RListMultimap<Object, Object> listMultimap = redissonClient.getListMultimap("");
        RBinaryStream binaryStream = redissonClient.getBinaryStream(name);
        return binaryStream.getOutputStream();
    }
    /**
     * 获取输入流
     * @param name
     * @return
     */
    public InputStream getInputStream(String name) {
        RBinaryStream binaryStream = redissonClient.getBinaryStream(name);
        return binaryStream.getInputStream();
    }
    /**
     * 获取输入流
     * @param name
     * @return
     */
    public InputStream getValue(String name,OutputStream stream) {
        try {
            RBinaryStream binaryStream = redissonClient.getBinaryStream(name);
            InputStream inputStream = binaryStream.getInputStream();
            byte[] buff=new byte[1024];
            int len;
            while ((len=inputStream.read(buff))!=-1){
                stream.write(buff,0,len);
            }
            return binaryStream.getInputStream();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 获取对象空间
     *
     * @param name
     * @return
     */
    public RBinaryStream getBucket(String name) {
        return redissonClient.getBinaryStream(name);
    }

    /**
     * 设置对象的值
     *
     * @param name  键
     * @param value 值
     * @return
     */
    public void setValue(String name, InputStream value) {
        try {
            RBinaryStream binaryStream = redissonClient.getBinaryStream(name);
            binaryStream.delete();
            OutputStream outputStream = binaryStream.getOutputStream();
            byte[] buff = new byte[1024];
            int len;
            while ((len=value.read(buff))!=-1){
                outputStream.write(buff,0,len);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 删除对象
     *
     * @param name 键
     * @return true 删除成功,false 不成功
     */
    public Boolean delete(String name) {
        RBinaryStream binaryStream = redissonClient.getBinaryStream(name);
        return binaryStream.delete();
    }


}
