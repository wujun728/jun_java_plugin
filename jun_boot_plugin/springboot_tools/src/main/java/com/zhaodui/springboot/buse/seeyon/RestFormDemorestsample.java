package com.zhaodui.springboot.buse.seeyon;

import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import com.seeyon.client.CTPRestClient;

public   class RestFormDemorestsample  {

//���������̱����ݽӿ�(Since:56)
public static void testExportBusinessFormData() {
   CTPRestClient client = ClientResource.getInstance().resouresClent();
   String result = client.get("form/export/2020011901?beginDateTime=2011-01-09%2000:00:59&endDateTime=2020-09-09%2000:00:59", String.class);
   System.out.println("getlist="+result);
}

//���������̱��ӿ�ʵ��(Since:56)
public static String testImportBusinessFormData(String xmlin) {
	String xml =  xmlin;
   Map res = new HashMap();
   res.put("loginName", "dydemo");//loginName:OA��Ա��¼��(String��)
   res.put("dataXml", xml);//dataXml�������̱�XML��Ϣ(String��)����ο�BPM���ɵ�XML��ʽ����������XML���ֻ���� ������<forms version="2.1"> ����������<forms version="2.0">��������һ��

   CTPRestClient client = ClientResource.getInstance().resouresClent();
   String result = client.post("form/import/2020011901", res, String.class);
   return result;
}

//���������̱����ݽӿ�ʵ��(Since:V6.0SP1)
/**dataXmlʾ��
<?xml version="1.0" encoding="utf-8"?>
<forms version="2.1">
	<formImport>
	<summary   name="formmain_3164"/>
	<definitions></definitions>
	<values>
		<column name="field0001">
			<value>2019-01-01</value>
		</column>
		<column name="field00021">
			<value>11</value>
		</column>
	</values>

</formImport>
</forms>
**/
public static void testupdateBusinessFormData() {
   Map res = new HashMap();
   String xml = " <?xml version=\"1.0\" encoding=\"utf-8\"?>\n" + 
   		"<forms version=\"2.1\"> <formExport>\n" + 
   		"    <summary id=\"3716541404671528945\" name=\"formmain_3164\"/>\n" + 
   		"    <definitions>\n" + 
   		"      <column id=\"field0002\" type=\"0\" name=\"�������\" isNullable=\"false\" length=\"40\"/>\n" + 
   		"    </definitions>\n" + 
   		"    <values>\n" + 
   		"      <column name=\"�������\">\n" + 
   		"        <value>111111></value>\n" + 
   		"      </column>\n" + 
   		"    </values>\n" + 
   		"  </formExport>\n" + 
   		"</forms>";
   res.put("templateCode", "2020011901");//�����̱�ģ����(String��)
   res.put("moduleId", "3716541404671528945");//����ID(String��)
   res.put("loginName", "dydemo");//OA��Ա��¼��(String��)
   res.put("dataXml", xml);//ֻ��Ҫ��д�����ֶ�XML��Ϣ(String��)����μ�����ʾ��

   CTPRestClient client = ClientResource.getInstance().resouresClent();
   String result = client.put("form/update", res, String.class);
   System.out.println(result);
}

public static void main(String args[]){
//  testupdateBusinessFormData();

//   testImportBusinessFormData();
   testExportBusinessFormData();

}
}