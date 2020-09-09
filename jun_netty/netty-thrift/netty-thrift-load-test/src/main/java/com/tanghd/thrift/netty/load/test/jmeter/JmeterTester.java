package com.tanghd.thrift.netty.load.test.jmeter;

import org.apache.jmeter.config.Arguments;
import org.apache.jmeter.protocol.java.sampler.AbstractJavaSamplerClient;
import org.apache.jmeter.protocol.java.sampler.JavaSamplerContext;
import org.apache.jmeter.samplers.SampleResult;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;

import com.tanghd.thrift.netty.load.test.service.TestService.Client;

public class JmeterTester extends AbstractJavaSamplerClient {

    private String ip;
    private int port;

    private long end;
    private long start;
    
    private SampleResult sr ;

    @Override
    public void teardownTest(JavaSamplerContext context) {
        end = System.nanoTime();
        System.out.println("Used " + (end - start) / 1000000 + " ms");
    }

    @Override
    public Arguments getDefaultParameters() {
        Arguments args = new Arguments();
        args.addArgument("ip", "127.0.0.1");
        args.addArgument("port", "9090");
        ip = "127.0.0.1";
        port = 9090;
        return args;
    }

    @Override
    public void setupTest(JavaSamplerContext context) {
        start = System.nanoTime();
        
        ip = context.getParameter("ip");
        port = Integer.parseInt(context.getParameter("port"));
        
        sr = new SampleResult();
    }

    @Override
    public SampleResult runTest(JavaSamplerContext ctx) {
        TTransport transport = null;
        transport = new TSocket(ip, port,10000);
        try {
            TProtocol protocol = new TBinaryProtocol(new TFramedTransport(transport));
            Client client = new Client(protocol);
            transport.open();
            try {
                sr.sampleStart();
                client.test("What's the metter");
                sr.setSuccessful(true);
            } catch (Exception e) {
                e.printStackTrace();
                sr.setSuccessful(false);
            }
            sr.sampleEnd();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            transport.close();
            
        }
        return sr;
    }

}
