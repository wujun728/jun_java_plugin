package io.github.wujun728.db;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import java.util.Date;

@Data
public class User {

	@Id
	@TableId(value = "id")
	private Long id;
	private String userName;
	@Column(name = "first_adress_")
	private String firstAdress;
	private int age;

	@Column(name = "create_date_")
	@TableField(value = "create_date_")
	private Date createData;
	@TableField(exist = false)
	private String testName;

}
