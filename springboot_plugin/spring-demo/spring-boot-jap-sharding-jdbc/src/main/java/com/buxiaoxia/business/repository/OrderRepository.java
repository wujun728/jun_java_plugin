package com.buxiaoxia.business.repository;

import com.buxiaoxia.business.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author xiaw
 * @date 2018/4/19 10:57
 * Description:
 */
public interface OrderRepository extends JpaRepository<Order, Long> {
}
