package com.camel.jetty.server;

/**
 * Hello world!
 *
 *
 * from("http://localhost:8080/dbk.manager.web/queryOrgDetailById")
 * 这个是主动向目标http url发起访问。
 *
 * from("jetty:http://0.0.0.0:8282/doHelloWorld")
 * 这个是向外部提供服务，使用jetty开头。
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );
    }
}
