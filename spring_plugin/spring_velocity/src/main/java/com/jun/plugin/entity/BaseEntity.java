package com.jun.plugin.entity;

import java.io.Serializable;

/**
 * @author Wujun
 *
 */
public interface BaseEntity<E extends Serializable> extends Serializable {

	public E getId();

}
