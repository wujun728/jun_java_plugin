package com.jun.plugin.json.jackson3.jsonxml.manytomany;

import java.util.Set;

//import javax.persistence.Cacheable;
//import javax.persistence.Entity;
//import javax.persistence.ManyToMany;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

//dzy @XmlRootElement
//dzy @XmlAccessorType(XmlAccessType.FIELD)
//dzy@Entity
//dzy @Cacheable(false)
public class User extends AbstractMasterEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7101111173404253308L;

	//dzy	@ManyToMany
	private Set<Role> roles;

	//dzy	@ManyToMany
	private Set<Group> groups;

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	public Set<Group> getGroups() {
		return groups;
	}

	public void setGroups(Set<Group> groups) {
		this.groups = groups;
	}	
}
