package com.buxiaoxia.business.repository;

import com.buxiaoxia.business.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
}
