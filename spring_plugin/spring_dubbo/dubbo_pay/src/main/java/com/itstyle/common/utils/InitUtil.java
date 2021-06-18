package com.itstyle.common.utils;

import javax.annotation.PostConstruct;
import org.springframework.stereotype.Component;
import com.alipay.demo.trade.config.Configs;
import com.itstyle.modules.unionpay.util.SDKConfig;
import com.itstyle.modules.weixinpay.util.ConfigUtil;

/**
 * 初始化参数 
 * 创建者 科帮网 
 * 创建时间 2017年8月7日
 *
 */
@Component
public class InitUtil {
	@PostConstruct
	public void init() {
		// 初始化 支付宝 微信 银联 参数 涉及机密 此文件不提交 请自行配置加载
		Configs.init("zfbinfo.properties");
		ConfigUtil.init("wxinfo.properties");
		SDKConfig.getConfig().loadPropertiesFromSrc();
	}

}
