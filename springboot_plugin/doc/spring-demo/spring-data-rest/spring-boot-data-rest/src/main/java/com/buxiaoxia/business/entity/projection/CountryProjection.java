package com.buxiaoxia.business.entity.projection;

import com.buxiaoxia.business.entity.BigCompany;
import com.buxiaoxia.business.entity.Country;
import com.buxiaoxia.business.entity.President;
import com.buxiaoxia.business.entity.Province;
import org.springframework.data.rest.core.config.Projection;

import java.util.List;

/**
 * Created by xw on 2017/5/10.
 * 2017-05-10 16:17
 */
@Projection(name = "base", types = {Country.class})
interface CountryProjection {
	int getId();

	String getName();
}


@Projection(name = "withPresident", types = {Country.class})
interface CountryWithPresident extends CountryProjection {

	President getPresident();
}


@Projection(name = "withProvinces", types = {Country.class})
interface CountryWithProvinces extends CountryProjection {

	List<Province> getProvinces();
}

@Projection(name = "withProvinces", types = {Country.class})
interface CountryWithCompanies extends CountryProjection {

	List<BigCompany> getCompanies();
}


@Projection(name = "withAll", types = {Country.class})
interface CountryWithAll extends CountryProjection {

	List<BigCompany> getCompanies();

	List<Province> getProvinces();

	President getPresident();
}