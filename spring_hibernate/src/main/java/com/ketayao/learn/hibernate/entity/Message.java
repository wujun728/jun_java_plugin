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
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * @author Sharath Reddy
 */
@Entity
@Table(name = "test_message")
public class Message implements Serializable {

	private static final long serialVersionUID = 1L;
	private int id;
	private String content;
	private Language language;

	@Id
	@GeneratedValue
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @return the content
	 */
	@Column(length=256)
	public String getContent() {
		return content;
	}

	/**
	 * @param content
	 *            the content to set
	 */
	public void setContent(String content) {
		this.content = content;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "languageId")
	public Language getLanguage() {
		return language;
	}

	public void setLanguage(Language language) {
		this.language = language;
	}

}
