package com.buxiaoxia.business.service;

import com.buxiaoxia.business.entity.Country;
import org.springframework.stereotype.Service;

/**
 * Created by xw on 2017/5/10.
 * 2017-05-10 21:46
 */
@Service
public class CountryService {


	public void handleCountryCreateBefore(Country country){
		System.out.println("创建country之前：" + country);
	}

	public void handleCountryCreateAfter(Country country){
		System.out.println("创建country之后：" + country);
	}

	public void handleCountrySaveBefore(Country country){
		System.out.println("Save country之前：" + country);
	}

	public void handleCountrySaveAfter(Country country){
		System.out.println("Save country之后：" + country);
	}
}
