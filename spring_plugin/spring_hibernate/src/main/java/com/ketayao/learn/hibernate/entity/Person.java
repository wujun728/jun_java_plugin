/*
 * Hibernate, Relational Persistence for Idiomatic Java
 *
 * Copyright (c) 2008, Red Hat Middleware LLC or third-party contributors as
 * indicated by the @author tags or express copyright attribution
 * statements applied by the authors.  All third-party contributions are
 * distributed under license by Red Hat Middleware LLC.
 *
 * This copyrighted material is made available to anyone wishing to use, modify,
 * copy, or redistribute it subject to the terms and conditions of the GNU
 * Lesser General Public License, as published by the Free Software Foundation.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY
 * or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Lesser General Public License
 * for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this distribution; if not, write to:
 * Free Software Foundation, Inc.
 * 51 Franklin Street, Fifth Floor
 * Boston, MA  02110-1301  USA
 */

package com.ketayao.learn.hibernate.entity;
import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import org.hibernate.annotations.Filter;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.FilterDefs;
import org.hibernate.annotations.Filters;
import org.hibernate.annotations.SqlFragmentAlias;

/**
 * @author Sharath Reddy
 */
@Entity
@Table(name="test_person")
@FilterDefs({
	@FilterDef(name="getACompany")})
public class Person implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private int id;
	private String name;
	private List<Company> companies;
	
	@Id @GeneratedValue
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	/**  
	 * 返回 name 的值   
	 * @return name  
	 */
	@Column
	public String getName() {
		return name;
	}
	/**  
	 * 设置 name 的值  
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**  
	 * 返回 companies 的值   
	 * @return companies  
	 */
	@OneToMany(cascade=CascadeType.ALL, mappedBy="person")
	@Filters({
		@Filter(name="getACompany", condition="{c}.q='A'", aliases={@SqlFragmentAlias(alias="c", entity=Company.class)})
	})
	@OrderBy("id desc")
	public List<Company> getCompanies() {
		return companies;
	}
	/**  
	 * 设置 companies 的值  
	 * @param companies
	 */
	public void setCompanies(List<Company> companies) {
		this.companies = companies;
	}

}

