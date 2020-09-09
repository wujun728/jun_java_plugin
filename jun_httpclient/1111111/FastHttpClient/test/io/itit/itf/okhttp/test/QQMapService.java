package io.itit.itf.okhttp.test;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.itit.itf.okhttp.FastHttpClient;
import io.itit.itf.okhttp.Response;

/**
 * http://lbs.qq.com/webservice_v1/guide-gcoder.html
 * @author icecooly
 *
 */
public class QQMapService {
	//
	static Logger logger=LoggerFactory.getLogger(QQMapService.class);
	//
	String key;
	
	public QQMapService() {
		key="ZXRBZ-HX5KJ-M25F3-KZOTJ-BKHAQ-OABTC";
	}
	
	public void searchLocation(String region,String keyword){
		String url="http://apis.map.qq.com/ws/place/v1/suggestion?keyword="+keyword+
				"&key="+key;
		try {
			Response response=FastHttpClient.get().url(url).build().execute();
			if(!response.isSuccessful()){
				throw new IllegalArgumentException("定位失败，请稍后再试");
			}
			logger.info(response.string());
		} catch (IOException e) {
			logger.error(e.getMessage(),e);
		}
	}
	//
	public static void main(String[] args) {
		QQMapService service=new QQMapService();
		service.searchLocation(null,"深圳市南山区飞亚达大厦");
	}
}
