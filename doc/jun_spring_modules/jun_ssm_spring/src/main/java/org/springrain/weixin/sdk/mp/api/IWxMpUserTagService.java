package org.springrain.weixin.sdk.mp.api;

import java.util.List;

import org.springrain.weixin.sdk.common.api.IWxMpConfig;
import org.springrain.weixin.sdk.common.exception.WxErrorException;
import org.springrain.weixin.sdk.mp.bean.tag.WxTagListUser;
import org.springrain.weixin.sdk.mp.bean.tag.WxUserTag;

/**
 * 用户标签管理相关接口
 * Created by springrain on 2017/1/2.
 * @author <a href="http://git.oschina.net/chunanyong/springrain">springrain(springrain)</a>
 *
 */
public interface IWxMpUserTagService {

  /**
   * <pre>
   * 创建标签
   * 一个公众号，最多可以创建100个标签。
   * 详情请见：<a href="http://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1421140837&token=&lang=zh_CN">用户标签管理</a>
   * 接口url格式： https://api.weixin.qq.com/cgi-bin/tags/create?access_token=ACCESS_TOKEN
   * </pre>
   *
   * @param name 标签名字（30个字符以内）
   */
  WxUserTag tagCreate(IWxMpConfig wxmpconfig,String name) throws WxErrorException;

  /**
   * <pre>
   * 获取公众号已创建的标签
   * 详情请见：<a href="http://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1421140837&token=&lang=zh_CN">用户标签管理</a>
   * 接口url格式： https://api.weixin.qq.com/cgi-bin/tags/get?access_token=ACCESS_TOKEN
   * </pre>
   *
   */
  List<WxUserTag> tagGet(IWxMpConfig wxmpconfig) throws WxErrorException;

  /**
   * <pre>
   * 编辑标签
   * 详情请见：<a href="http://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1421140837&token=&lang=zh_CN">用户标签管理</a>
   * 接口url格式： https://api.weixin.qq.com/cgi-bin/tags/update?access_token=ACCESS_TOKEN
   * </pre>
   *
   */
  Boolean tagUpdate(IWxMpConfig wxmpconfig,Long tagId, String name) throws WxErrorException;

  /**
   * <pre>
   * 删除标签
   * 详情请见：<a href="http://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1421140837&token=&lang=zh_CN">用户标签管理</a>
   * 接口url格式： https://api.weixin.qq.com/cgi-bin/tags/delete?access_token=ACCESS_TOKEN
   * </pre>
   *
   */
  Boolean tagDelete(IWxMpConfig wxmpconfig,Long tagId) throws WxErrorException;

  /**
   * <pre>
   * 获取标签下粉丝列表
   * 详情请见：<a href="http://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1421140837&token=&lang=zh_CN">用户标签管理</a>
   * 接口url格式： https://api.weixin.qq.com/cgi-bin/user/tag/get?access_token=ACCESS_TOKEN
   * </pre>
   *
   */
  WxTagListUser tagListUser(IWxMpConfig wxmpconfig,Long tagId, String nextOpenid)
      throws WxErrorException;

  /**
   * <pre>
   * 批量为用户打标签
   * 详情请见：<a href="http://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1421140837&token=&lang=zh_CN">用户标签管理</a>
   * 接口url格式： https://api.weixin.qq.com/cgi-bin/tags/members/batchtagging?access_token=ACCESS_TOKEN
   * </pre>
   *
   */
  boolean batchTagging(IWxMpConfig wxmpconfig,Long tagId, String[] openids) throws WxErrorException;

  /**
   * <pre>
   * 批量为用户取消标签
   * 详情请见：<a href="http://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1421140837&token=&lang=zh_CN">用户标签管理</a>
   * 接口url格式： https://api.weixin.qq.com/cgi-bin/tags/members/batchtagging?access_token=ACCESS_TOKEN
   * </pre>
   *
   */
  boolean batchUntagging(IWxMpConfig wxmpconfig,Long tagId, String[] openids) throws WxErrorException;


  /**
   * <pre>
   * 获取用户身上的标签列表
   * 详情请见：<a href="http://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1421140837&token=&lang=zh_CN">用户标签管理</a>
   * 接口url格式： https://api.weixin.qq.com/cgi-bin/tags/getidlist?access_token=ACCESS_TOKEN
   * </pre>
   * @return 标签Id的列表 
   */
  List<Long> userTagList(IWxMpConfig wxmpconfig,String openid) throws WxErrorException;

}
