package com.jun.plugin.httpclient;



import java.io.File;  
import java.io.FileInputStream;  
import java.io.IOException;  
import java.io.UnsupportedEncodingException;  
import java.security.KeyManagementException;  
import java.security.KeyStore;  
import java.security.KeyStoreException;  
import java.security.NoSuchAlgorithmException;  
import java.security.cert.CertificateException;  
import java.util.ArrayList;  
import java.util.List;  
 
import javax.net.ssl.SSLContext;  
 
import org.apache.http.HttpEntity;  
import org.apache.http.NameValuePair;  
import org.apache.http.ParseException;  
import org.apache.http.client.ClientProtocolException;  
import org.apache.http.client.entity.UrlEncodedFormEntity;  
import org.apache.http.client.methods.CloseableHttpResponse;  
import org.apache.http.client.methods.HttpGet;  
import org.apache.http.client.methods.HttpPost;  
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;  
import org.apache.http.conn.ssl.SSLContexts;  
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;  
import org.apache.http.entity.ContentType;  
import org.apache.http.entity.mime.MultipartEntityBuilder;  
import org.apache.http.entity.mime.content.FileBody;  
import org.apache.http.entity.mime.content.StringBody;  
import org.apache.http.impl.client.CloseableHttpClient;  
import org.apache.http.impl.client.HttpClients;  
import org.apache.http.message.BasicNameValuePair;  
import org.apache.http.util.EntityUtils;  
import org.junit.Test;  
 
public class HttpClientTest {  
 
   @Test  
   public void jUnitTest() {  
       get();  
   }  
 
   /** 
    * HttpClient����SSL 
    */  
   public void ssl() {  
       CloseableHttpClient httpclient = null;  
       try {  
           KeyStore trustStore = KeyStore.getInstance(KeyStore.getDefaultType());  
           FileInputStream instream = new FileInputStream(new File("d:\\tomcat.keystore"));  
           try {  
               // ����keyStore d:\\tomcat.keystore    
               trustStore.load(instream, "123456".toCharArray());  
           } catch (CertificateException e) {  
               e.printStackTrace();  
           } finally {  
               try {  
                   instream.close();  
               } catch (Exception ignore) {  
               }  
           }  
           // �����Լ���CA��������ǩ����֤��  
           SSLContext sslcontext = SSLContexts.custom().loadTrustMaterial(trustStore, new TrustSelfSignedStrategy()).build();  
           // ֻ����ʹ��TLSv1Э��  
           SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslcontext, new String[] { "TLSv1" }, null,  
                   SSLConnectionSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);  
           httpclient = HttpClients.custom().setSSLSocketFactory(sslsf).build();  
           // ����http����(get��ʽ)  
           HttpGet httpget = new HttpGet("https://localhost:8443/myDemo/Ajax/serivceJ.action");  
           System.out.println("executing request" + httpget.getRequestLine());  
           CloseableHttpResponse response = httpclient.execute(httpget);  
           try {  
               HttpEntity entity = response.getEntity();  
               System.out.println("----------------------------------------");  
               System.out.println(response.getStatusLine());  
               if (entity != null) {  
                   System.out.println("Response content length: " + entity.getContentLength());  
                   System.out.println(EntityUtils.toString(entity));  
                   EntityUtils.consume(entity);  
               }  
           } finally {  
               response.close();  
           }  
       } catch (ParseException e) {  
           e.printStackTrace();  
       } catch (IOException e) {  
           e.printStackTrace();  
       } catch (KeyManagementException e) {  
           e.printStackTrace();  
       } catch (NoSuchAlgorithmException e) {  
           e.printStackTrace();  
       } catch (KeyStoreException e) {  
           e.printStackTrace();  
       } finally {  
           if (httpclient != null) {  
               try {  
                   httpclient.close();  
               } catch (IOException e) {  
                   e.printStackTrace();  
               }  
           }  
       }  
   }  
 
   /** 
    * post��ʽ�ύ����ģ���û���¼���� 
    */  
   public void postForm() {  
       // ����Ĭ�ϵ�httpClientʵ��.    
       CloseableHttpClient httpclient = HttpClients.createDefault();  
       // ����httppost    
       HttpPost httppost = new HttpPost("http://localhost:8080/myDemo/Ajax/serivceJ.action");  
       // ������������    
       List formparams = new ArrayList();  
       formparams.add(new BasicNameValuePair("username", "admin"));  
       formparams.add(new BasicNameValuePair("password", "123456"));  
       UrlEncodedFormEntity uefEntity;  
       try {  
           uefEntity = new UrlEncodedFormEntity(formparams, "UTF-8");  
           httppost.setEntity(uefEntity);  
           System.out.println("executing request " + httppost.getURI());  
           CloseableHttpResponse response = httpclient.execute(httppost);  
           try {  
               HttpEntity entity = response.getEntity();  
               if (entity != null) {  
                   System.out.println("--------------------------------------");  
                   System.out.println("Response content: " + EntityUtils.toString(entity, "UTF-8"));  
                   System.out.println("--------------------------------------");  
               }  
           } finally {  
               response.close();  
           }  
       } catch (ClientProtocolException e) {  
           e.printStackTrace();  
       } catch (UnsupportedEncodingException e1) {  
           e1.printStackTrace();  
       } catch (IOException e) {  
           e.printStackTrace();  
       } finally {  
           // �ر�����,�ͷ���Դ    
           try {  
               httpclient.close();  
           } catch (IOException e) {  
               e.printStackTrace();  
           }  
       }  
   }  
 
   /** 
    * ���� post������ʱ���Ӧ�ò����ݴ��ݲ�����ͬ���ز�ͬ��� 
    */  
   public void post() {  
       CloseableHttpClient httpclient = HttpClients.createDefault();  
       HttpPost httppost = new HttpPost("http://localhost:8080/myDemo/Ajax/serivceJ.action");  
       List formparams = new ArrayList();  
       formparams.add(new BasicNameValuePair("type", "house"));  
       UrlEncodedFormEntity uefEntity;  
       try {  
           uefEntity = new UrlEncodedFormEntity(formparams, "UTF-8");  
           httppost.setEntity(uefEntity);  
           System.out.println("executing request " + httppost.getURI());  
           CloseableHttpResponse response = httpclient.execute(httppost);  
           try {  
               HttpEntity entity = response.getEntity();  
               if (entity != null) {  
                   System.out.println("--------------------------------------");  
                   System.out.println("Response content: " + EntityUtils.toString(entity, "UTF-8"));  
                   System.out.println("--------------------------------------");  
               }  
           } finally {  
               response.close();  
           }  
       } catch (ClientProtocolException e) {  
           e.printStackTrace();  
       } catch (UnsupportedEncodingException e1) {  
           e1.printStackTrace();  
       } catch (IOException e) {  
           e.printStackTrace();  
       } finally {  
           // �ر�����,�ͷ���Դ    
           try {  
               httpclient.close();  
           } catch (IOException e) {  
               e.printStackTrace();  
           }  
       }  
   }  
 
   /** 
    * ���� get���� 
    */  
   public void get() {  
       CloseableHttpClient httpclient = HttpClients.createDefault();  
       try {  
           // ����httpget.    
           HttpGet httpget = new HttpGet("http://www.baidu.com/");  
           System.out.println("executing request " + httpget.getURI());  
           // ִ��get����.    
           CloseableHttpResponse response = httpclient.execute(httpget);  
           try {  
               // ��ȡ��Ӧʵ��    
               HttpEntity entity = response.getEntity();  
               System.out.println("--------------------------------------");  
               // ��ӡ��Ӧ״̬    
               System.out.println(response.getStatusLine());  
               if (entity != null) {  
                   // ��ӡ��Ӧ���ݳ���    
                   System.out.println("Response content length: " + entity.getContentLength());  
                   // ��ӡ��Ӧ����    
                   System.out.println("Response content: " + EntityUtils.toString(entity));  
               }  
               System.out.println("------------------------------------");  
           } finally {  
               response.close();  
           }  
       } catch (ClientProtocolException e) {  
           e.printStackTrace();  
       } catch (ParseException e) {  
           e.printStackTrace();  
       } catch (IOException e) {  
           e.printStackTrace();  
       } finally {  
           // �ر�����,�ͷ���Դ    
           try {  
               httpclient.close();  
           } catch (IOException e) {  
               e.printStackTrace();  
           }  
       }  
   }  
 
   /** 
    * �ϴ��ļ� 
    */  
   public void upload() {  
       CloseableHttpClient httpclient = HttpClients.createDefault();  
       try {  
           HttpPost httppost = new HttpPost("http://localhost:8080/myDemo/Ajax/serivceFile.action");  
 
           FileBody bin = new FileBody(new File("F:\\image\\sendpix0.jpg"));  
           StringBody comment = new StringBody("A binary file of some kind", ContentType.TEXT_PLAIN);  
 
           HttpEntity reqEntity = MultipartEntityBuilder.create().addPart("bin", bin).addPart("comment", comment).build();  
 
           httppost.setEntity(reqEntity);  
 
           System.out.println("executing request " + httppost.getRequestLine());  
           CloseableHttpResponse response = httpclient.execute(httppost);  
           try {  
               System.out.println("----------------------------------------");  
               System.out.println(response.getStatusLine());  
               HttpEntity resEntity = response.getEntity();  
               if (resEntity != null) {  
                   System.out.println("Response content length: " + resEntity.getContentLength());  
               }  
               EntityUtils.consume(resEntity);  
           } finally {  
               response.close();  
           }  
       } catch (ClientProtocolException e) {  
           e.printStackTrace();  
       } catch (IOException e) {  
           e.printStackTrace();  
       } finally {  
           try {  
               httpclient.close();  
           } catch (IOException e) {  
               e.printStackTrace();  
           }  
       }  
   }  
} 