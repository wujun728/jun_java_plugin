
package org.service.query;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the org.service.query package. 
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

    private final static QName _QueryCarInfomation_QNAME = new QName("http://query.service.org/", "queryCarInfomation");
    private final static QName _QueryCarInfomationResponse_QNAME = new QName("http://query.service.org/", "queryCarInfomationResponse");
    private final static QName _QueryPersonnelInformation_QNAME = new QName("http://query.service.org/", "queryPersonnelInformation");
    private final static QName _QueryPersonnelInformationResponse_QNAME = new QName("http://query.service.org/", "queryPersonnelInformationResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: org.service.query
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link QueryCarInfomation }
     * 
     */
    public QueryCarInfomation createQueryCarInfomation() {
        return new QueryCarInfomation();
    }

    /**
     * Create an instance of {@link QueryCarInfomationResponse }
     * 
     */
    public QueryCarInfomationResponse createQueryCarInfomationResponse() {
        return new QueryCarInfomationResponse();
    }

    /**
     * Create an instance of {@link QueryPersonnelInformation }
     * 
     */
    public QueryPersonnelInformation createQueryPersonnelInformation() {
        return new QueryPersonnelInformation();
    }

    /**
     * Create an instance of {@link QueryPersonnelInformationResponse }
     * 
     */
    public QueryPersonnelInformationResponse createQueryPersonnelInformationResponse() {
        return new QueryPersonnelInformationResponse();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link QueryCarInfomation }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://query.service.org/", name = "queryCarInfomation")
    public JAXBElement<QueryCarInfomation> createQueryCarInfomation(QueryCarInfomation value) {
        return new JAXBElement<QueryCarInfomation>(_QueryCarInfomation_QNAME, QueryCarInfomation.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link QueryCarInfomationResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://query.service.org/", name = "queryCarInfomationResponse")
    public JAXBElement<QueryCarInfomationResponse> createQueryCarInfomationResponse(QueryCarInfomationResponse value) {
        return new JAXBElement<QueryCarInfomationResponse>(_QueryCarInfomationResponse_QNAME, QueryCarInfomationResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link QueryPersonnelInformation }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://query.service.org/", name = "queryPersonnelInformation")
    public JAXBElement<QueryPersonnelInformation> createQueryPersonnelInformation(QueryPersonnelInformation value) {
        return new JAXBElement<QueryPersonnelInformation>(_QueryPersonnelInformation_QNAME, QueryPersonnelInformation.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link QueryPersonnelInformationResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://query.service.org/", name = "queryPersonnelInformationResponse")
    public JAXBElement<QueryPersonnelInformationResponse> createQueryPersonnelInformationResponse(QueryPersonnelInformationResponse value) {
        return new JAXBElement<QueryPersonnelInformationResponse>(_QueryPersonnelInformationResponse_QNAME, QueryPersonnelInformationResponse.class, null, value);
    }

}
