package com.jun.web.servlet.subpkg;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

/**
 * ���������ļ�, ��װ���е����mystruts.xml
 * @author Jie.Yuan
 *
 */
public class ActionMappingManager {

	// ����action�ļ���
	private Map<String,ActionMapping> allActions ;
	
	public ActionMappingManager(){
		allActions = new HashMap<String,ActionMapping>();
		// ��ʼ��
		this.init();
	}
	
	/**
	 * ��������·�����ƣ�����Action��ӳ�����
	 * @param actionName   ��ǰ����·��
	 * @return             ���������ļ��д���action�ڵ��AcitonMapping����
	 */
	public ActionMapping getActionMapping(String actionName) {
		if (actionName == null) {
			throw new RuntimeException("�������������鿴struts.xml���õ�·����");
		}
		
		ActionMapping actionMapping = allActions.get(actionName);
		if (actionMapping == null) {
			throw new RuntimeException("·����struts.xml���Ҳ���������");
		}
		return actionMapping;
	}
	
	// ��ʼ��allActions����
	private void init() {
		/********DOM4J��ȡ�����ļ�***********/
		try {
			// 1. �õ�������
			SAXReader reader = new SAXReader();
			// �õ�src/mystruts.xml  �ļ���
			InputStream inStream = this.getClass().getResourceAsStream("/mystruts.xml");
			// 2. �����ļ�
			Document doc = reader.read(inStream);
			
			// 3. ��ȡ��
			Element root = doc.getRootElement();
			
			// 4. �õ�package�ڵ�
			Element ele_package = root.element("package");
			
			// 5. �õ�package�ڵ��£�  ���е�action�ӽڵ�
			List<Element> listAction = ele_package.elements("action");
			
			// 6.���� ����װ
			for (Element ele_action : listAction) {
				// 6.1 ��װһ��ActionMapping����
				ActionMapping actionMapping = new ActionMapping();
				actionMapping.setName(ele_action.attributeValue("name"));
				actionMapping.setClassName(ele_action.attributeValue("class"));
				actionMapping.setMethod(ele_action.attributeValue("method"));
				
				// 6.2 ��װ��ǰaciton�ڵ������еĽ����ͼ
				Map<String,Result> results = new HashMap<String, Result>();
				
				// �õ���ǰaction�ڵ������е�result�ӽڵ�
				 Iterator<Element> it = ele_action.elementIterator("result");
				 while (it.hasNext()) {
					 // ��ǰ������ÿһ��Ԫ�ض��� <result...>
					 Element ele_result = it.next();
					 
					 // ��װ����
					 Result res = new Result();
					 res.setName(ele_result.attributeValue("name"));
					 res.setType(ele_result.attributeValue("type"));
					 res.setPage(ele_result.getTextTrim());
					 
					 // ��ӵ�����
					 results.put(res.getName(), res);
				 }
				
				// ���õ�actionMapping��
				actionMapping.setResults(results);
				
				// 6.x actionMapping��ӵ�map����
				allActions.put(actionMapping.getName(), actionMapping);
			}
		} catch (Exception e) {
			throw new RuntimeException("����ʱ���ʼ������",e);
		}
	}
}














