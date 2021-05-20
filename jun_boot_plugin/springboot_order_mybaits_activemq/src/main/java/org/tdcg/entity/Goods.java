package org.tdcg.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotations.IdType;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

/**
 *
 * 
 *
 */
@TableName("t_goods")
public class Goods implements Serializable {

	@TableField(exist = false)
	private static final long serialVersionUID = 1L;

	/**  */
	@TableId(type = IdType.UUID)
	private String id;

	/**  */
	@TableField(value = "goods_name")
	private String goodsName;

	/**  */
	private Integer inventory;

    public Goods() {
    }

    public Goods(String id, String goodsName, Integer inventory) {
        this.id = id;
        this.goodsName = goodsName;
        this.inventory = inventory;
    }

    public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getGoodsName() {
		return this.goodsName;
	}

	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}

	public Integer getInventory() {
		return this.inventory;
	}

	public void setInventory(Integer inventory) {
		this.inventory = inventory;
	}

}
