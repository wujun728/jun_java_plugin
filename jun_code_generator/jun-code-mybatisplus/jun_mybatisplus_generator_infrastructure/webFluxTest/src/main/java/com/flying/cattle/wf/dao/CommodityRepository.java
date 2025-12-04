package com.flying.cattle.wf.dao;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import com.flying.cattle.wf.entity.Commodity;

public interface CommodityRepository extends ReactiveMongoRepository<Commodity, Long>{

}
