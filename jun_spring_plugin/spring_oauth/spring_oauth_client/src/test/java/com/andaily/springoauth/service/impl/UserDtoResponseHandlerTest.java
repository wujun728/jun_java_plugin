package com.andaily.springoauth.service.impl;

import com.andaily.springoauth.service.dto.UserDto;
import org.testng.annotations.Test;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.ByteArrayInputStream;

import static org.testng.Assert.assertNotNull;

/**
 * @author Shengzhao Li
 */
public class UserDtoResponseHandlerTest {


    @Test
    public void testXML() throws Exception {

        String text = "<oauth><error_description>Invalid access token: 3420d0e0-ed77-45e1-8370-2b55af0a62e8</error_description><error>invalid_token</error></oauth>";
        assertNotNull(text);


        final SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
        final SAXParser saxParser = saxParserFactory.newSAXParser();

        final UserDto userDto = new UserDto();
        saxParser.parse(new ByteArrayInputStream(text.getBytes()), new DefaultHandler() {

            private String qName;

            @Override
            public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
                System.out.println(uri + ",  " + localName + ",  " + qName);
                this.qName = qName;
            }

            @Override
            public void characters(char[] ch, int start, int length) throws SAXException {
                final String text = new String(ch, start, length);
                System.out.println(text);

                if ("error_description".equalsIgnoreCase(qName)) {
                    userDto.setErrorDescription(text);
                } else if ("error".equalsIgnoreCase(qName)) {
                    userDto.setError(text);
                }

            }

        });

        System.out.println(userDto);
    }

}