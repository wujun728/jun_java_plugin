package com.ibeetl.admin.core.entity;

import java.util.Map;

import org.beetl.sql.core.TailBean;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;

/**
 * 描述:用于辅助序列化beetlsql 的TailBean
 * @author Wujun
 */

public class BaseEntity extends TailBean implements java.io.Serializable {

	protected final static String ORACLE_CORE_SEQ_NAME="core_seq";
	protected final static String ORACLE_AUDIT_SEQ_NAME="audit_seq";
	protected final static String ORACLE_FILE_SEQ_NAME="core_seq";
	@JsonAnyGetter
    public Map<String, Object> getTails(){
    	return super.getTails();
    }
	
    

  
}
