package com.tanghd.thrift.netty.load.test;

import org.apache.thrift.TProcessorFactory;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TBinaryProtocol.Factory;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TThreadPoolServer;
import org.apache.thrift.server.TThreadedSelectorServer;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TNonblockingServerSocket;
import org.apache.thrift.transport.TNonblockingServerTransport;
import org.apache.thrift.transport.TServerSocket;
import org.apache.thrift.transport.TTransportException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tanghd.thrift.netty.load.test.service.TestService;
import com.tanghd.thrift.netty.load.test.service.TestService.Iface;
import com.tanghd.thrift.netty.load.test.service.TestServiceProcessor;
import com.tanghd.thrift.netty.server.server.TNettyThriftServer;

public class TestServer {

    private static final Logger log = LoggerFactory.getLogger(TestServer.class);

    private static Object lock = new Object();

    public static void main(String[] args) throws TTransportException {

        TestService.Processor<Iface> processor = new TestService.Processor<TestService.Iface>(
                new TestServiceProcessor());
        Factory protFactory = new TBinaryProtocol.Factory(true, true);

        TNettyThriftServer.Args nettyArg = new TNettyThriftServer.Args(9090);
        nettyArg.setMaxReadBuffer(1024 * 100);
        nettyArg.protocolFactory(protFactory);
        nettyArg.processorFactory(new TProcessorFactory(processor));
        final TServer nettyServer = new TNettyThriftServer(nettyArg);

        TNonblockingServerTransport transport = new TNonblockingServerSocket(9091);
        TThreadedSelectorServer.Args arg = new TThreadedSelectorServer.Args(transport);
        arg.protocolFactory(protFactory);
        arg.processorFactory(new TProcessorFactory(processor));
        arg.maxReadBufferBytes = 1024 * 100;
        arg.workerThreads(200);
        arg.selectorThreads(16);
        final TServer nonblockingServer = new TThreadedSelectorServer(arg);

        TServerSocket serverSocket = new TServerSocket(9092);
        TThreadPoolServer.Args socketArgs = new TThreadPoolServer.Args(serverSocket);
        socketArgs.maxWorkerThreads = 2000;
        socketArgs.minWorkerThreads = 500;
        socketArgs.transportFactory(new TFramedTransport.Factory(1024*100));
        socketArgs.protocolFactory(protFactory);
        socketArgs.processorFactory(new TProcessorFactory(processor));
        final TServer socketServer = new TThreadPoolServer(socketArgs);

        new Thread(new Runnable() {

            @Override
            public void run() {
                log.warn("netty server start on 9090");
                nettyServer.serve();
            }
        }).start();

        new Thread(new Runnable() {

            @Override
            public void run() {
                log.warn("nonblocking server start on 9091");
                nonblockingServer.serve();
            }
        }).start();
        
        new Thread(new Runnable() {

            @Override
            public void run() {
                log.warn("threadpool server start on 9092");
                socketServer.serve();
            }
        }).start();

        try {
            lock.wait();
        } catch (Exception e) {

        }
    }
}
