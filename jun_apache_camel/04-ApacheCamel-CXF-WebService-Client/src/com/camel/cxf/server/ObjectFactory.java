
package com.camel.cxf.server;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.camel.cxf.server package. 
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

    private final static QName _QueryInfomation_QNAME = new QName("http://server.cxf.camel.com/", "queryInfomation");
    private final static QName _QueryInfomationResponse_QNAME = new QName("http://server.cxf.camel.com/", "queryInfomationResponse");
    private final static QName _SayHello_QNAME = new QName("http://server.cxf.camel.com/", "sayHello");
    private final static QName _SayHelloResponse_QNAME = new QName("http://server.cxf.camel.com/", "sayHelloResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.camel.cxf.server
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link QueryInfomation }
     * 
     */
    public QueryInfomation createQueryInfomation() {
        return new QueryInfomation();
    }

    /**
     * Create an instance of {@link QueryInfomationResponse }
     * 
     */
    public QueryInfomationResponse createQueryInfomationResponse() {
        return new QueryInfomationResponse();
    }

    /**
     * Create an instance of {@link SayHello }
     * 
     */
    public SayHello createSayHello() {
        return new SayHello();
    }

    /**
     * Create an instance of {@link SayHelloResponse }
     * 
     */
    public SayHelloResponse createSayHelloResponse() {
        return new SayHelloResponse();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link QueryInfomation }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://server.cxf.camel.com/", name = "queryInfomation")
    public JAXBElement<QueryInfomation> createQueryInfomation(QueryInfomation value) {
        return new JAXBElement<QueryInfomation>(_QueryInfomation_QNAME, QueryInfomation.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link QueryInfomationResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://server.cxf.camel.com/", name = "queryInfomationResponse")
    public JAXBElement<QueryInfomationResponse> createQueryInfomationResponse(QueryInfomationResponse value) {
        return new JAXBElement<QueryInfomationResponse>(_QueryInfomationResponse_QNAME, QueryInfomationResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SayHello }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://server.cxf.camel.com/", name = "sayHello")
    public JAXBElement<SayHello> createSayHello(SayHello value) {
        return new JAXBElement<SayHello>(_SayHello_QNAME, SayHello.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SayHelloResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://server.cxf.camel.com/", name = "sayHelloResponse")
    public JAXBElement<SayHelloResponse> createSayHelloResponse(SayHelloResponse value) {
        return new JAXBElement<SayHelloResponse>(_SayHelloResponse_QNAME, SayHelloResponse.class, null, value);
    }

}
