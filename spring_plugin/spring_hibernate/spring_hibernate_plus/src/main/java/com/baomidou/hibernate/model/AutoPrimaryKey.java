package com.baomidou.hibernate.model;

import com.baomidou.hibernateplus.entity.Convert;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;


/**
 * <p>
 * 统一表主键
 * </p>
 *
 * @author Caratacus
 * @date 2016-10-23
 */
@MappedSuperclass
public class AutoPrimaryKey extends Convert {

	protected Long id;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

}
