package org.springrain.weixin.sdk.xcx.api;

import java.util.Map;

import org.springrain.weixin.sdk.common.api.IWxXcxConfig;
import org.springrain.weixin.sdk.common.exception.WxErrorException;
import org.springrain.weixin.sdk.xcx.bean.result.sign.request.WxSignRequest;
import org.springrain.weixin.sdk.xcx.bean.result.sign.request.WxSurrenderRequest;
import org.springrain.weixin.sdk.xcx.bean.result.sign.result.WxSignResult;
import org.springrain.weixin.sdk.xcx.bean.result.sign.result.WxSurrenderResult;

/**
 * 用户管理相关操作接口
 *
 * @author springrain
 */
public interface IWxXcxSignService {

	  /**
	   * 微信签约接口
	   * @param wxxcxconfig
	   * @param request
	   * @return
	   * @throws WxErrorException
	   */
	   WxSignResult getWxSignInfo(IWxXcxConfig wxxcxconfig,WxSignRequest request) throws WxErrorException;
	   
	   
	   /**
	   * 微信解约接口
	   * @param wxxcxconfig
	   * @param request
	   * @return
	   * @throws WxErrorException
	   */
	   WxSurrenderResult getWxSurrenderInfo(IWxXcxConfig wxxcxconfig,WxSurrenderRequest request) throws WxErrorException;
	   
	   
	   /**
	    * 微信签约url
	    */
	   String getWxSignUrl(IWxXcxConfig wxxcxconfig,WxSignRequest request) throws WxErrorException;
	   
	   
	   /**
	    * 获取微信签约参数
	    * @param wxxcxconfig
	    * @param request
	    * @return
	    * @throws WxErrorException
	    */
	   Map<String, String> getSignParam(IWxXcxConfig wxxcxconfig,WxSignRequest request) throws WxErrorException;

}
