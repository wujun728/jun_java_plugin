package com.diamond.wm.accesstoken.config;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties
public class AutoTokenConfig {
	Logger log = LoggerFactory.getLogger(getClass());
	
	private long rate;
	private long flushTime;
	
	public long getRate() {
		return rate;
	}
	public void setRate(long rate) {
		this.rate = rate;
	}
	public long getFlushTime() {
		return flushTime;
	}
	public void setFlushTime(long flushTime) {
		this.flushTime = flushTime;
	} 
	
	@PostConstruct
	public void postConstruct(){
		log.info("rate millisecond: " +rate);
		log.info("flushtime millisecond: "+flushTime);
	}
}
