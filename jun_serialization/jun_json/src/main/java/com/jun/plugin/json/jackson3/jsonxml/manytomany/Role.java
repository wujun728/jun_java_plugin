package com.jun.plugin.json.jackson3.jsonxml.manytomany;

import java.util.Set;

//import javax.persistence.Cacheable;
//import javax.persistence.Entity;
//import javax.persistence.ManyToMany;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonIgnore;

//dzy @XmlRootElement
//dzy @XmlAccessorType(XmlAccessType.FIELD)
//dzy @Entity
//dzy @Cacheable(false)
public class Role extends AbstractMasterEntity {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3650640336695648089L;

	//dzy 	@ManyToMany(mappedBy="roles")
	private Set<Group> groups;

	//dzy 	@ManyToMany(mappedBy="roles")
	@JsonIgnore
	private Set<User> users;
	
	public Set<Group> getGroups() {
		return groups;
	}
	
	public void setGroups(Set<Group> groups) {
		this.groups = groups;
	}

	public Set<User> getUsers() {
		return users;
	}

	public void setUsers(Set<User> users) {
		this.users = users;
	}
}
