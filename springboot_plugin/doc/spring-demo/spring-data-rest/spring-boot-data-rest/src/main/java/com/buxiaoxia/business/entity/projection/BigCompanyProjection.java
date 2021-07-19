package com.buxiaoxia.business.entity.projection;

import com.buxiaoxia.business.entity.BigCompany;
import com.buxiaoxia.business.entity.Country;
import org.springframework.data.rest.core.config.Projection;

import java.util.List;

/**
 * 这个是 company的 projection
 * </br>
 * 以 projection 为参数名 对应的 @Projection 的name作为value 选择不同的projection内容
 * <p>
 * <p>
 * Created by xw on 2017/5/10.
 * 2017-05-10 16:14
 */
@Projection(name = "base", types = {BigCompany.class})
interface BigCompanyProjection {

	int getId();

	String getName();
}

@Projection(name = "withCountry", types = {BigCompany.class})
interface BigCompanyWithCountry extends BigCompanyProjection {

	List<Country> getCountries();
}