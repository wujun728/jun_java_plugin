package com.jun.plugin.aliyunsms.servlet;


import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.RandomStringUtils;

import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;

/**
 * �����ƵĶ�����֤
 * 
 * 
 * ע�⣺��Ҫ����json�Ұ汾����̫��
 * 
 */
@WebServlet("/AliyunServlet")
public class AliyunServlet extends HttpServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			//��ȡ�ֻ���
			String phone = request.getParameter("phone");
			
			//����һ����֤��
			String number=RandomStringUtils.randomNumeric(6);
			System.out.println(number);
			
			//�������ṩ�Ĵ���
			DefaultProfile profile = DefaultProfile.getProfile("cn-hangzhou", "�û�id", "��Կ");
	        IAcsClient client = new DefaultAcsClient(profile);
	        CommonRequest request1 = new CommonRequest();
	        request1.setMethod(MethodType.POST);
	        request1.setDomain("dysmsapi.aliyuncs.com");
	        request1.setVersion("2017-05-25");
	        request1.setAction("SendSms");
	        request1.putQueryParameter("RegionId", "cn-hangzhou");
	        //��Ҫ���͵ĵ绰����
	        request1.putQueryParameter("PhoneNumbers", phone);
	        //ע���ǩ��
	        request1.putQueryParameter("SignName", "ǩ��");
	        //ע���ģ��id
	        request1.putQueryParameter("TemplateCode", "ģ��id");
	        //��֤��
	        request1.putQueryParameter("SmsUpExtendCode", "��֤��");
	        try {
	            CommonResponse response1 = client.getCommonResponse(request1);
	            System.out.println(response1.getData());
	        } catch (ServerException e) {
	            e.printStackTrace();
	        } catch (ClientException e) {
	            e.printStackTrace();
	        }
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
