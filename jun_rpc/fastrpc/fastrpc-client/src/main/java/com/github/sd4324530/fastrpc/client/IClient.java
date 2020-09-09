package com.github.sd4324530.fastrpc.client;

import com.github.sd4324530.fastrpc.core.message.RequestMessage;
import com.github.sd4324530.fastrpc.core.message.ResponseMessage;
import com.github.sd4324530.fastrpc.core.serializer.ISerializer;

import java.io.Closeable;
import java.io.IOException;
import java.net.SocketAddress;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

/**
 * Rpc客户端接口
 *
 * @author peiyu
 */
public interface IClient extends Closeable {

    /**
     * 需要接连的Rpc服务接口地址
     *
     * @param address Rpc接口地址
     * @throws IOException          连接异常
     * @throws InterruptedException
     * @throws ExecutionException
     * @throws TimeoutException
     */
    void connect(SocketAddress address) throws IOException, InterruptedException, ExecutionException, TimeoutException;

    /**
     * 需要接连的Rpc服务接口地址
     *
     * @param address Rpc接口地址
     * @param retry   是否自动重连
     * @throws IOException          连接异常
     * @throws InterruptedException
     * @throws ExecutionException
     * @throws TimeoutException
     */
    void connect(SocketAddress address, boolean retry) throws IOException, InterruptedException, ExecutionException, TimeoutException;

    /**
     * 客户端工作线程数
     *
     * @param threadSize 线程数
     * @return RPC服务端接口对象
     */
    IClient threadSize(int threadSize);


    /**
     * 设置序列化方案,不设置的话,默认使用jdk自带的序列化方案
     *
     * @param serializer 序列化方案
     * @return RPC客户端接口对象
     */
    IClient serializer(ISerializer serializer);

    /**
     * 设置请求超时时间,单位毫秒
     *
     * @param timeout 超时时间
     * @return RPC客户端接口对象
     */
    IClient timeout(long timeout);

    /**
     * 得到RPC接口实例,可以像调用本地方法那样调用RPC接口方法
     * 对应的是Server端注册的name为类名的服务
     *
     * @param clazz 接口类类型
     * @param <T>   泛型
     * @return RPC接口实例
     */
    <T> T getService(Class<T> clazz);

    /**
     * 得到RPC接口实例,可以像调用本地方法那样调用RPC接口方法
     *
     * @param name  server端注册的服务名
     * @param clazz 接口类类型
     * @param <T>   泛型
     * @return RPC接口实例
     */
    <T> T getService(String name, Class<T> clazz);

    /**
     * 调用Rpc接口
     *
     * @param requestMessage 请求对象
     * @return 响应对象
     */
    ResponseMessage invoke(RequestMessage requestMessage);
}
