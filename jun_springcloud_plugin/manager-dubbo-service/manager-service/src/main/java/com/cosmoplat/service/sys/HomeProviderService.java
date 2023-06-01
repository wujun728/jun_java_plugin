package com.cosmoplat.service.sys;


import com.cosmoplat.entity.sys.vo.resp.HomeRespVO;

/**
 * 首页
 *
 * @author wenbin
 * @version V1.0
 * @date 2020年3月18日
 */
public interface HomeProviderService {

    HomeRespVO getHomeInfo(String userId);
}
