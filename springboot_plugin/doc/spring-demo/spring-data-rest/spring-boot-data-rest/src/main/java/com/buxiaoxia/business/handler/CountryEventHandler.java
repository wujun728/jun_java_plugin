package com.buxiaoxia.business.handler;


import com.buxiaoxia.business.entity.Country;
import com.buxiaoxia.business.service.CountryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.core.annotation.HandleAfterCreate;
import org.springframework.data.rest.core.annotation.HandleBeforeCreate;
import org.springframework.data.rest.core.annotation.HandleBeforeSave;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;
import org.springframework.stereotype.Component;

/**
 * 用于指定对应的ENTITY操作的事件前后
 * <p>
 * <p>
 * Created by xw on 2017/5/10.
 * 2017-05-10 22:00
 */
@Component
@RepositoryEventHandler
public class CountryEventHandler {

	@Autowired
	private CountryService countryService;


	// 定义创建之前的动作
	@HandleBeforeCreate
	public void handleCountryCreateBefore(Country country) {
		countryService.handleCountryCreateBefore(country);
	}

	// 定义创建之后的动作
	@HandleAfterCreate
	public void handleCountryCreateAfter(Country country) {
		countryService.handleCountryCreateAfter(country);
	}

	// 定义save之前的动作
	@HandleBeforeSave
	public void handleCountrySaveBefore(Country country) {
		countryService.handleCountrySaveBefore(country);
	}

	// 定义save之后的动作
	@HandleBeforeSave
	public void handleCountrySaveAfter(Country country) {
		countryService.handleCountrySaveAfter(country);
	}


}