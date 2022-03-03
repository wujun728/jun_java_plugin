package com.jun.plugin.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

import com.erp.model.BackupScheduleConfig;

public class XMLFactory
{
   @SuppressWarnings("unused")
   private XMLFactory(){};

   private  Marshaller marshaller;
   private Unmarshaller unmarshaller;

   /**
    * 参数types为所有需要序列化的Root对象的类型.
    */
   public XMLFactory(Class<?>... types) {
       try {
           JAXBContext jaxbContext = JAXBContext.newInstance(types);
           marshaller = jaxbContext.createMarshaller();
           marshaller.setProperty("jaxb.formatted.output", Boolean.TRUE);
           unmarshaller = jaxbContext.createUnmarshaller();
       } catch (JAXBException e) {
           throw new RuntimeException(e);
       }
   }

   /**
    * Java->Xml
    */
   public  String marshal(Object root) {
       try {
           StringWriter writer = new StringWriter();
           marshaller.marshal(root, writer);
           return writer.toString();
       } catch (JAXBException e) {
           throw new RuntimeException(e);
       }
   }
   public static void main(String[] args )
{
	   XMLFactory sdFactory=new XMLFactory(BackupScheduleConfig.class);
	 // String xmlText=sdFactory.marshal(new BackupScheduleConfig(1, 1, 1, "Y"));
	   try
	{
		BackupScheduleConfig sd = sdFactory.unmarshal(new FileInputStream(new File("D:\\sale.xml")));
		System.out.println(sd.getScheduleEnabled());
	} catch (FileNotFoundException e)
	{
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	  
}
   
   	public void stringXMLToFile(String filePath,String content)
	{
   	 try {
		   OutputFormat format = OutputFormat.createPrettyPrint();
		   format.setEncoding("UTF-8");
		   XMLWriter xmlWriter = new XMLWriter(new FileOutputStream(filePath), format);
		   Document doc = DocumentHelper.parseText(content);
		   xmlWriter.write(doc);
		   xmlWriter.close();
		  } catch (Exception e) {
		   e.printStackTrace();
		  }
	}
   /**
    * Xml->Java
    */
   @SuppressWarnings("unchecked")
   public <T> T unmarshal(String xml) {
       try {
           StringReader reader = new StringReader(xml);
           return (T) unmarshaller.unmarshal(reader);
       } catch (JAXBException e) {
           throw new RuntimeException(e);
       }
   }

   /**
    * Xml->Java
    */
   @SuppressWarnings("unchecked")
   public <T> T unmarshal(InputStream in) {
       BufferedReader br = null;
       try {
           br = new BufferedReader(new InputStreamReader(in, "utf-8"));
       } catch (UnsupportedEncodingException e) {
           throw new RuntimeException(e);
       }

       try {
           return (T) unmarshaller.unmarshal(br);
       } catch (JAXBException e) {
           throw new RuntimeException(e);
       }
   }
}
