package com.buxiaoxia.business.entity;

import lombok.Data;

/**
 * Created by xw on 2017/1/5.
 * 2017-01-05 17:18
 */
@Data
public class AccessToken {

	private String access_token;
	private String token_type;
	private long expires_in;
}
