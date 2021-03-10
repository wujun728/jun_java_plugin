package com.zhaodui.springboot.buse.seeyon;

import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import com.seeyon.client.CTPRestClient;

public class RestFlowDemorestsample  {

//ȡ��ָ�������̵�ǰ������Ա��Ϣ(Since:V61)
public static void testGetFlowDostaff() {
   CTPRestClient client = ClientResource.getInstance().resouresClent();
   String result = client.get("flow/dostaff/{summaryId}", String.class);
   System.out.println(result);
}

//����SummaryID��AffairID��ȡ������(Since:V61)
public static void getFormData() { 
   CTPRestClient client = ClientResource.getInstance().resouresClent();
   String result = client.get("form/getformdata/{SummaryID/AffairID}", String.class);
   System.out.println(result);
}

//��ȡָ��Эͬ�����б�(Since:V61)
public static void testAttachments() {//0������,2��������ĵ�
   CTPRestClient client = ClientResource.getInstance().resouresClent();
   String result = client.get("coll/attachments/{SummaryID}/{AffairID}/{0|2}", String.class);
   System.out.println(result);
}

//��ȡ��Ա��Ȩ��ģ��ID�б�(Since:V61)
public static void testTemplates() {//1-Эͬģ�壻2-��ģ�壬4-����ģ��
   CTPRestClient client = ClientResource.getInstance().resouresClent();
   String result = client.get("template/templateidlist/{loginName}/{1,2,4}", String.class);
   System.out.println(result);
}

public static void main(String args[]){
   testGetFlowDostaff();
   getFormData();
   testAttachments();
   testTemplates();
}
}