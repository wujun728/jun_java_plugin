package org.springrain.weixin.sdk.common.api;

public interface IWxCpConfigService {

	/**
	 * 根据ID查找微信配置,可以进行缓存处理
	 * 
	 * @param id
	 * @return
	 */
	IWxCpConfig findWxCpConfigById(String id) throws Exception;

	/**
	 * 更新WxCpConfig,可以进行缓存处理
	 * 
	 * @param wxcpconfig
	 * @return
	 */
	IWxCpConfig updateWxCpConfig(IWxCpConfig wxcpconfig);

	/**
	 * 更新 expireAccessToken
	 * 
	 * @param wxMpConfig
	 * @return
	 */
	IWxCpConfig expireAccessToken(IWxCpConfig wxcpconfig);

	/**
	 * 更新 updateAccessToken
	 * 
	 * @param wxMpConfig
	 * @return
	 */

	IWxCpConfig updateAccessToken(IWxCpConfig wxcpconfig);

	/**
	 * 更新 expireJsApiTicket
	 * 
	 * @param wxMpConfig
	 * @return
	 */
	IWxCpConfig expireJsApiTicket(IWxCpConfig wxcpconfig);

	/**
	 * 更新 updateJsApiTicket
	 * 
	 * @param wxMpConfig
	 * @return
	 */
	IWxCpConfig updateJsApiTicket(IWxCpConfig wxcpconfig);

	/**
	 * 更新 expireCardapiTicket
	 * 
	 * @param wxMpConfig
	 * @return
	 */
	IWxCpConfig expireCardapiTicket(IWxCpConfig wxcpconfig);

	/**
	 * 更新 updateCardapiTicket
	 * 
	 * @param wxMpConfig
	 * @return
	 */
	IWxCpConfig updateCardapiTicket(IWxCpConfig wxcpconfig);

}
