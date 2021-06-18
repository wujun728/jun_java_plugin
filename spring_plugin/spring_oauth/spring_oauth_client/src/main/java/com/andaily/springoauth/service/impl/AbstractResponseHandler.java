package com.andaily.springoauth.service.impl;

import com.andaily.springoauth.infrastructure.httpclient.HttpResponseHandler;
import com.andaily.springoauth.infrastructure.httpclient.MkkHttpResponse;
import com.andaily.springoauth.infrastructure.json.JsonUtils;
import com.andaily.springoauth.service.dto.AbstractOauthDto;
import org.apache.http.StatusLine;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

/**
 * @author Shengzhao Li
 */
public abstract class AbstractResponseHandler<T extends AbstractOauthDto> implements HttpResponseHandler {

    protected static final String ERROR_DATA_KEY = "<oauth>";


    protected T responseToDto(MkkHttpResponse response, T dto) {
        final String text = response.responseAsString();
        if (text.startsWith(ERROR_DATA_KEY)) {
            dto = parseErrorXML(response, dto);
        } else {
            dto = JsonUtils.textToBean(dto, text);
            dto.setOriginalText(text);
        }
        return dto;
    }

    protected T responseToErrorDto(MkkHttpResponse response, T dto) {
        final StatusLine statusLine = response.httpResponse().getStatusLine();

        dto.setError(String.valueOf(statusLine.getStatusCode()));
        dto.setErrorDescription(statusLine.getReasonPhrase());
        return dto;
    }


    protected T parseErrorXML(MkkHttpResponse response, T obj) {
        try {
            final SAXParser saxParser = SAXParserFactory.newInstance().newSAXParser();
            final ErrorDefaultHandler dh = new ErrorDefaultHandler();
            saxParser.parse(response.httpResponse().getEntity().getContent(), dh);

            obj.setError(dh.error());
            obj.setErrorDescription(dh.errorDescription());
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
        return obj;
    }


    /**
     * Parse Error XML handler
     */
    protected class ErrorDefaultHandler extends DefaultHandler {
        private String qName;

        private String error;
        private String errorDescription;

        protected ErrorDefaultHandler() {
        }

        @Override
        public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
            this.qName = qName;
        }

        @Override
        public void characters(char[] ch, int start, int length) throws SAXException {
            final String text = new String(ch, start, length);

            if ("error_description".equalsIgnoreCase(qName)) {
                this.errorDescription = text;
            } else if ("error".equalsIgnoreCase(qName)) {
                this.error = text;
            }

        }

        public String error() {
            return error;
        }

        public String errorDescription() {
            return errorDescription;
        }
    }


}