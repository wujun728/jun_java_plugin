/*   
 * Project: OSMP
 * FileName: SOAPClientServiceImpl.java
 * version: V1.0
 */
package com.osmp.tools.commons.impl;

import java.io.IOException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.SOAPConnection;
import javax.xml.soap.SOAPConnectionFactory;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPFault;
import javax.xml.soap.SOAPMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import com.osmp.tools.commons.SOAPClientService;

/**
 * Description:SOAP客户端
 * 
 * @author: wangkaiping
 * @date: 2014年9月11日 上午11:06:53
 */

public class SOAPClientServiceImpl implements SOAPClientService {
	private static final Logger logger = LoggerFactory.getLogger(SOAPClientServiceImpl.class);

	@Override
	public <T> T sendRequest(String address, Object jaxbRequest, Class<T> jaxbResponse) throws SOAPException {
		Assert.isTrue(StringUtils.hasText(address), "The web service address must is not empty.");
		Assert.notNull(jaxbRequest, "The request object must is not null when invoke web service.");
		Assert.notNull(jaxbResponse, "No designated response type when invoke web service.");

		logger.info("SOAP Address : " + address);
		logger.info("SOAP request detail : " + jaxbRequest);
		logger.info("SOAP response type : " + jaxbResponse);

		try {
			SOAPMessage request = createRequestMessage(jaxbRequest);
			request.setProperty("Timeout", SOAP_TIMEOUT);
			if (logger.isDebugEnabled()) {
				try {
					request.writeTo(System.out);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			SOAPMessage response = createSoapConnection().call(request, address);
			if (logger.isDebugEnabled()) {
				try {
					response.writeTo(System.out);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (response.getSOAPBody().hasFault()) {
				dealSoapFault(response.getSOAPBody().getFault());
			}

			JAXBContext jaxbContext = JAXBContext.newInstance(jaxbResponse);
			Object instance = jaxbContext.createUnmarshaller().unmarshal(response.getSOAPBody().getFirstChild());
			logger.info("SOAP response detail : " + instance);
			return jaxbResponse.cast(instance);
		} catch (JAXBException e) {
			throw new SOAPException("Marchal object has failed : " + e.getMessage(), e);
		}
	}

	protected SOAPMessage createRequestMessage(Object jaxbRequest) throws SOAPException, JAXBException {
		JAXBContext jaxbContext = JAXBContext.newInstance(jaxbRequest.getClass());
		SOAPMessage request = MessageFactory.newInstance().createMessage();
		jaxbContext.createMarshaller().marshal(jaxbRequest, request.getSOAPBody());
		return request;
	}

	protected SOAPConnection createSoapConnection() throws SOAPException {
		return SOAPConnectionFactory.newInstance().createConnection();
	}

	protected void dealSoapFault(SOAPFault soapFault) throws SOAPException {
		throw new SOAPException(soapFault.getFaultString());
	}
}
