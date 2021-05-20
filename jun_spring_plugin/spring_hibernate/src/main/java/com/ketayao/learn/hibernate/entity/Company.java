/*
  * Hibernate, Relational Persistence for Idiomatic Java
  *
  * Copyright (c) 2009, Red Hat, Inc. and/or its affiliates or third-
  * party contributors as indicated by the @author tags or express 
  * copyright attribution statements applied by the authors.  
  * All third-party contributions are distributed under license by 
  * Red Hat, Inc.
  *
  * This copyrighted material is made available to anyone wishing to 
  * use, modify, copy, or redistribute it subject to the terms and 
  * conditions of the GNU Lesser General Public License, as published 
  * by the Free Software Foundation.
  *
  * This program is distributed in the hope that it will be useful,
  * but WITHOUT ANY WARRANTY; without even the implied warranty of 
  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU 
  * Lesser General Public License for more details.
  *
  * You should have received a copy of the GNU Lesser General Public 
  * License along with this distribution; if not, write to:
  * 
  * Free Software Foundation, Inc.
  * 51 Franklin Street, Fifth Floor
  * Boston, MA  02110-1301  USA
  */

package com.ketayao.learn.hibernate.entity;
import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * @author Sharath Reddy
 */
@Entity
@Table(name="test_company")
public class Company implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private int id;
	private String q;
	private Person person;
	
	@Id @GeneratedValue
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	/**  
	 * 返回 q 的值   
	 * @return q  
	 */
	@Column(name="q")
	public String getQ() {
		return q;
	}
	
	/**  
	 * 设置 q 的值  
	 * @param q
	 */
	public void setQ(String q) {
		this.q = q;
	}
	
	/**  
	 * 返回 person 的值   
	 * @return person  
	 */
	@ManyToOne
	@JoinColumn(name="personId")
//	@Formula(value="(select * from Company c where c.q='A' and c.personId=personId)")
	@JoinTable(name = "student_teacher", inverseJoinColumns = @JoinColumn(name = "teacher_id"), joinColumns = @JoinColumn(name = "student_id")) 
	public Person getPerson() {
		return person;
	}
	/**  
	 * 设置 person 的值  
	 * @param person
	 */
	public void setPerson(Person person) {
		this.person = person;
	}


	
}
