package org.springrain.frame.task;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springrain.frame.util.GlobalStatic;
import org.springrain.frame.util.LuceneUtils;

public class LuceneTask implements Runnable {
	
	private final  Logger logger = LoggerFactory.getLogger(getClass());
	
	public final static String deleteDocument = "delete";
	public final static String updateDocument = "update";
	public final static String saveDocument = "save";

	private Object entity;

	private String oper;
	
	@SuppressWarnings("rawtypes")
	public Class clazz;
	String rootdir=null;

	public LuceneTask() {
		rootdir=GlobalStatic.rootDir+"/lucene/index";
	}

	public LuceneTask(Object entity, String oper) {
		this();
		this.oper = oper;
		this.entity = entity;
	}
	//删除专用
	@SuppressWarnings("rawtypes")
	public LuceneTask(Object id, Class clazz) {
		this.entity = id;
		this.clazz = clazz;
		this.oper="delete";
	}
	

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void run() {
		try {
			
			
			if (deleteDocument.equals(oper)) {
				if(entity instanceof List){
					LuceneUtils.deleteListDocument(rootdir,clazz,(List)entity);
				}else{
					LuceneUtils.deleteDocumentById(rootdir,clazz,entity.toString());
				}
				
			}else if (updateDocument.equals(oper))  {
				if(entity instanceof List){
					 LuceneUtils.updateListDocument(rootdir,(List)entity);
				}else{
				   LuceneUtils.updateDocument(rootdir,entity);
				}
			}else if (saveDocument.equals(oper))  {
				if(entity instanceof List){
					LuceneUtils.saveListDocument(rootdir,(List)entity);
				}else{
					LuceneUtils.saveDocument(rootdir,entity);
				}
				
			}
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
		}

	}

}
