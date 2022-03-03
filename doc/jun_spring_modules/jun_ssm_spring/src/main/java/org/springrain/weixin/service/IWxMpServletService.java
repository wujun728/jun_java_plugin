package org.springrain.weixin.service;

import org.springrain.system.service.IBaseSpringrainService;
import org.springrain.weixin.entity.WxMpConfig;

public interface IWxMpServletService extends IBaseSpringrainService{

	void saveWxMpConfig(WxMpConfig wxMpConfig) throws Exception;

	void updateWxMpConfig(WxMpConfig wxMpConfig) throws Exception;

}
