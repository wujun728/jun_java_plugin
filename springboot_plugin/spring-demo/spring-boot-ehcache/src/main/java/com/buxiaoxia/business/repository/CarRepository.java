package com.buxiaoxia.business.repository;

import com.buxiaoxia.business.entity.Car;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by xw on 2017/3/16.
 * 2017-03-16 16:29
 */
public interface CarRepository extends JpaRepository<Car, Integer> {

	//这里的单引号不能少，否则会报错，被识别是一个对象;
	@Cacheable(value = "car", key = "'car_'+#brand")
	List<Car> findByBrand(String brand);


	@CacheEvict(value = "car", key = "'car_'+#brand")
	void deleteByBrand(String brand);
}
