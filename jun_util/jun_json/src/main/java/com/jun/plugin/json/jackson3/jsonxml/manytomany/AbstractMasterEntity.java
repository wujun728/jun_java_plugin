package com.jun.plugin.json.jackson3.jsonxml.manytomany;

//import javax.persistence.GeneratedValue;
//import javax.persistence.Id;
//import javax.persistence.MappedSuperclass;
//import javax.persistence.Version;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

//@MappedSuperclass
@JsonIdentityInfo(
		  generator = ObjectIdGenerators.PropertyGenerator.class, 
		  property = "id")
public abstract class AbstractMasterEntity {

//	@Id
	private String id;
	
	private String descr;

//	@Version
	private long version;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1105516472830451679L;

	public AbstractMasterEntity() {
	}

	@Override
	public String toString() {
		try {
			return new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(this);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			return "{}";
		}
	}
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}

	public String getDescr() {
		return descr;
	}

	public void setDescr(String descr) {
		this.descr = descr;
	}	
	
	public long getVersion() {
		return version;
	}
	
	public void setVersion(long version) {
		this.version = version;
	}
}
