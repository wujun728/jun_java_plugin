package cn.springboot.model;

import java.io.Serializable;


/** 
 * @Description 实体基类
 * @author Wujun
 * @date Mar 16, 2017 3:25:15 PM 
 * @param <E> 
 */
public interface BaseEntity<E extends Serializable> extends Serializable {

	public E getId();

}
