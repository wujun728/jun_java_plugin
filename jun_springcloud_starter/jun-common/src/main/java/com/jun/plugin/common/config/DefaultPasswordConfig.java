package com.jun.plugin.common.config;

import com.jun.plugin.common.utils.PwdEncoderUtil;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * 密码加密配置类
 *
 */
public class DefaultPasswordConfig {
	@Bean
	@ConditionalOnMissingBean
	public PasswordEncoder passwordEncoder() {
		return PwdEncoderUtil.getDelegatingPasswordEncoder("bcrypt");
	}
}
