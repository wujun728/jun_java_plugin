package org.springrain.weixin.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springrain.frame.util.GlobalStatic;
import org.springrain.system.service.BaseSpringrainServiceImpl;
import org.springrain.weixin.entity.WxXcxConfig;
import org.springrain.weixin.sdk.common.api.IWxXcxConfig;
import org.springrain.weixin.sdk.common.api.IWxXcxConfigService;
import org.springrain.weixin.sdk.common.bean.WxAccessToken;
import org.springrain.weixin.sdk.common.util.crypto.SHA1;

@Service("wxXcxConfigService")
public class WxXcxConfigServiceImpl extends BaseSpringrainServiceImpl implements IWxXcxConfigService {

	@Override
	public IWxXcxConfig expireAccessToken(IWxXcxConfig wxxcxconfig) {
		wxxcxconfig.setAccessTokenExpiresTime(0L);
		// 缓存操作
		updateWxXcxConfig(wxxcxconfig);
		return wxxcxconfig;
	}

	@Override
	public IWxXcxConfig updateAccessToken(IWxXcxConfig wxxcxconfig) {

		// 缓存操作
		updateWxXcxConfig(wxxcxconfig);

		return wxxcxconfig;
	}

	@Override
	public IWxXcxConfig findWxXcxConfigById(String id) {
		if (StringUtils.isBlank(id)) {
			return null;
		}

		IWxXcxConfig wxXcxConfig = null;
		try {
			wxXcxConfig = super.getByCache(id, GlobalStatic.xcxConfigCacheKey, WxXcxConfig.class);
			if (wxXcxConfig == null) {
				wxXcxConfig = super.findById(id, WxXcxConfig.class);
				super.putByCache(id, GlobalStatic.xcxConfigCacheKey, wxXcxConfig);
			}
		} catch (Exception e) {
			wxXcxConfig = null;
			logger.error(e.getMessage(), e);
		}

		return wxXcxConfig;
	}

	/**
	 * 缓存处理,可以把配置进行缓存更新 @
	 */
	@Override
	public IWxXcxConfig updateWxXcxConfig(IWxXcxConfig wxxcxconfig) {

		String id = wxxcxconfig.getId();
		if (StringUtils.isBlank(id)) {
			return null;
		}

		try {
			super.putByCache(id, GlobalStatic.xcxConfigCacheKey, wxxcxconfig);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}

		return wxxcxconfig;
	}

	private String getSignature(String nonceStr, String jsapiTicket, String timestamp, String url) {

		StringBuilder sb = new StringBuilder();
		sb.append("jsapi_ticket=").append(jsapiTicket).append("&noncestr=").append(nonceStr).append("&timestamp=")
				.append(timestamp).append("&url=").append(url);

		return SHA1.gen(sb.toString());

	}

	@Override
	public WxAccessToken getCustomAPIAccessToken(IWxXcxConfig wxxcxconfig) {
		try {

			// 默认命名方式,建议继承这个wxXcxConfigService重写这个方法,新Service请遵循默认的命名规则
			IWxXcxConfigService wxXcxConfigBean = (IWxXcxConfigService) getBean(
					"wxXcxConfigService_" + wxxcxconfig.getId());
			if (wxXcxConfigBean != null) {
				return wxXcxConfigBean.getCustomAPIAccessToken(wxxcxconfig);
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return null;
	}

}
