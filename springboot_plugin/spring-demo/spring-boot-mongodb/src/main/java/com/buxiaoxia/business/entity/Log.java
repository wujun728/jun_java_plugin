package com.buxiaoxia.business.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;

import java.util.Date;
import java.util.Map;

/**
 * Created by xw on 2017/2/23.
 * 2017-02-23 12:48
 */
@Data
public class Log {

	@Id
	private String id;

	private Date createAt;

	private String content;

	private Map other;

}
