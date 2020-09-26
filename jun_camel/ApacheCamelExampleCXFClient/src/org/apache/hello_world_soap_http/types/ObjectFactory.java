
package org.apache.hello_world_soap_http.types;

import javax.xml.bind.annotation.XmlRegistry;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the org.apache.hello_world_soap_http.types package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {


    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: org.apache.hello_world_soap_http.types
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link SayHi }
     * 
     */
    public SayHi createSayHi() {
        return new SayHi();
    }

    /**
     * Create an instance of {@link SayHiResponse }
     * 
     */
    public SayHiResponse createSayHiResponse() {
        return new SayHiResponse();
    }

    /**
     * Create an instance of {@link GreetMe }
     * 
     */
    public GreetMe createGreetMe() {
        return new GreetMe();
    }

    /**
     * Create an instance of {@link GreetMeResponse }
     * 
     */
    public GreetMeResponse createGreetMeResponse() {
        return new GreetMeResponse();
    }

    /**
     * Create an instance of {@link GreetMeOneWay }
     * 
     */
    public GreetMeOneWay createGreetMeOneWay() {
        return new GreetMeOneWay();
    }

    /**
     * Create an instance of {@link PingMe }
     * 
     */
    public PingMe createPingMe() {
        return new PingMe();
    }

    /**
     * Create an instance of {@link PingMeResponse }
     * 
     */
    public PingMeResponse createPingMeResponse() {
        return new PingMeResponse();
    }

    /**
     * Create an instance of {@link FaultDetail }
     * 
     */
    public FaultDetail createFaultDetail() {
        return new FaultDetail();
    }

}
