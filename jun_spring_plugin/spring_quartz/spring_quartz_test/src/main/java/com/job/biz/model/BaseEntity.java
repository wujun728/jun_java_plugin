package com.job.biz.model;

import java.io.Serializable;

/**
 * @author Wujun
 *
 */
public interface BaseEntity<E extends Serializable> extends Serializable {

	public E getId();

}
