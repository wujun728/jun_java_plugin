package org.springrain.weixin.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springrain.frame.util.GlobalStatic;
import org.springrain.system.service.BaseSpringrainServiceImpl;
import org.springrain.weixin.entity.WxCpConfig;
import org.springrain.weixin.sdk.common.api.IWxCpConfig;
import org.springrain.weixin.sdk.common.api.IWxCpConfigService;

@Service("wxCpConfigService")
public class WxCpConfigServiceImpl extends BaseSpringrainServiceImpl implements IWxCpConfigService {

	@Override
	public IWxCpConfig expireAccessToken(IWxCpConfig wxcpconfig) {
		wxcpconfig.setAccessTokenExpiresTime(0L);

		// 缓存操作
		updateWxCpConfig(wxcpconfig);

		return wxcpconfig;
	}

	@Override
	public IWxCpConfig updateAccessToken(IWxCpConfig wxcpconfig) {

		// 缓存操作
		updateWxCpConfig(wxcpconfig);

		return wxcpconfig;
	}

	@Override
	public IWxCpConfig expireJsApiTicket(IWxCpConfig wxcpconfig) {
		wxcpconfig.setJsApiTicketExpiresTime(0L);

		// 缓存操作
		updateWxCpConfig(wxcpconfig);

		return wxcpconfig;
	}

	@Override
	public IWxCpConfig updateJsApiTicket(IWxCpConfig wxcpconfig) {

		// 缓存操作
		updateWxCpConfig(wxcpconfig);

		return wxcpconfig;
	}

	@Override
	public IWxCpConfig expireCardapiTicket(IWxCpConfig wxcpconfig) {

		wxcpconfig.setCardApiTicketExpiresTime(0L);
		// 缓存操作
		updateWxCpConfig(wxcpconfig);

		return wxcpconfig;
	}

	@Override
	public IWxCpConfig updateCardapiTicket(IWxCpConfig wxcpconfig) {
		// 缓存操作
		updateWxCpConfig(wxcpconfig);
		return wxcpconfig;

	}

	@Override
	public IWxCpConfig findWxCpConfigById(String id) throws Exception {
		if (StringUtils.isBlank(id)) {
			return null;
		}

		IWxCpConfig wxcpConfig = null;
		try {
			wxcpConfig = super.getByCache(id, GlobalStatic.cpConfigCacheKey, WxCpConfig.class);
		} catch (Exception e) {
			wxcpConfig = null;
			logger.error(e.getMessage(), e);
		}
		if (wxcpConfig != null) {
			return wxcpConfig;
		}

		// 从数据库查询
		wxcpConfig = super.findById(id, WxCpConfig.class);

		super.putByCache(id, GlobalStatic.cpConfigCacheKey, wxcpConfig);

		return wxcpConfig;
	}

	/**
	 * 缓存处理,可以把配置进行缓存更新
	 */
	@Override
	public IWxCpConfig updateWxCpConfig(IWxCpConfig wxcpconfig) {

		String id = wxcpconfig.getId();
		if (StringUtils.isBlank(id)) {
			return null;
		}

		try {
			super.putByCache(id, GlobalStatic.cpConfigCacheKey, wxcpconfig);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}

		return wxcpconfig;
	}

}
