/**
 * @filename:CollectionRouteController 2019年6月12日
 * @project wallet-sign  V1.0
 * Copyright(c) 2020 BianPeng Co. Ltd. 
 * All right reserved. 
 */
package com.flying.cattle.wf.aid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.flying.cattle.wf.entity.ParentEntity;
import com.flying.cattle.wf.service.impl.RedisGenerateId;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**   
 * <p>自动生成工具：mybatis-dsc-generator</p>
 * 
 * <p>说明： 资金归集API接口层</P>
 * @version: V1.0
 * @author: BianPeng
 *
 */
public class AbstractController<S extends IService<T>,T extends ParentEntity>{
	
	@Autowired
	private RedisGenerateId redisGenerateId;

	@Autowired
    protected S baseService;
	
	@GetMapping("/getId")
	public Long getAutoIncrementId(T entity){
		return redisGenerateId.generate(entity.getClass().getName());
	}
	
	@PostMapping("/save")
	public Mono<T> save(T entity) {
		entity.setId(getAutoIncrementId(entity));
		return baseService.insert(entity);
	}

	@PostMapping("/deleteById")
	public Mono<String> deleteById(Long id) {
		// TODO Auto-generated method stub
		return baseService.deleteById(id)
				.then(Mono.just("ok"))
				.defaultIfEmpty("not found");
	}

	@PostMapping("/delete")
	public Mono<String> delete(T entity) {
		// TODO Auto-generated method stub
		return baseService.delete(entity)
				.then(Mono.just("ok"))
				.defaultIfEmpty("not found");
	}

	@PostMapping("/updateById")
	public Mono<T> updateById(T entity) {
		// TODO Auto-generated method stub
		return baseService.updateById(entity);
	}

	@GetMapping("/getById/{id}")
	public Mono<T> findById(@PathVariable("id") Long id) {
		// TODO Auto-generated method stub
		return baseService.findById(id);
	}

	@GetMapping(value="/findAll",produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
	public Flux<T> findAll() {
		// TODO Auto-generated method stub
		return baseService.findAll();
	}
}
