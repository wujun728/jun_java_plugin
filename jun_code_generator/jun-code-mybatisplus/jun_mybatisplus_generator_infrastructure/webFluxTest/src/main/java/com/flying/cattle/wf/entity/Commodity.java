package com.flying.cattle.wf.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class Commodity extends ParentEntity implements Serializable{

	private static final long serialVersionUID = 6659341305838439447L;
	
	/**
	 *	店铺ID 
	 */
	private Long shopId;
	
	/**
	 *	商品名
	 */
	@NotNull(message = "商品名不能为空")
	@Pattern(regexp = "^.{0,50}$",message = "商品名必须是小于50位字符串")
	private String name;
	
	/**
	 *	商品详情
	 */
	@Pattern(regexp = "^.{0,500}$",message = "商品详情必须是小于500位字符串")
	private String details;
	
	/**
	 *	商品图片地址
	 */
	private String imageUrl;
	
	/**
	 *	商品图片地址
	 */
	private Integer type;
	
	/**
	 *	商品单价
	 */
	@Min(value = 0)
	private BigDecimal price;
	
	/**
	 *	商品库存
	 */
	@Min(value = 0)
	private Integer pcs;
}
