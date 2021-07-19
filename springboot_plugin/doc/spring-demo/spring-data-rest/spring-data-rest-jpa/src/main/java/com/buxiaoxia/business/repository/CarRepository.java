package com.buxiaoxia.business.repository;

import com.buxiaoxia.business.entity.Car;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by xw on 2017/6/5.
 * 2017-06-05 11:38
 */
public interface CarRepository extends JpaRepository<Car, Long> {
}
