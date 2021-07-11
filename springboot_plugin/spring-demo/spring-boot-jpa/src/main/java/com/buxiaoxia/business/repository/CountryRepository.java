package com.buxiaoxia.business.repository;

import com.buxiaoxia.business.entity.Country;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by xw on 2017/2/22.
 * 2017-02-22 18:43
 */
public interface CountryRepository extends JpaRepository<Country, Integer> {

	Country findOneByName(String name);
}
