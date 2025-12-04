package com.flying.cattle.wf.aid;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * <p>
 * 顶级 Service
 * </p>
 *
 * @author BianPeng
 * @since 2019/6/17
 */
public interface IService<T> {
	
	/**
     * <p>
     * 插入一条记录（选择字段，策略插入）
     * </p>
     *
     * @param entity 实体对象
     */
	Mono<T> insert(T entity);
    
    /**
     * <p>
     * 根据 ID 删除
     * </p>
     * @param id 主键ID
     */
	Mono<Void> deleteById(Long id);
    
    /**
     * <p>
     * 根据 ID 删除
     * </p>
     * @param id 主键ID
     */
	Mono<Void> delete(T entity);
    
    /**
     * <p>
     * 根据 ID 选择修改
     * </p>
     * @param entity 实体对象
     */
	Mono<T> updateById(T entity);
    
    /**
     * <p>
     * 根据 ID 查询
     * </p>
     * @param id 主键ID
     */
	Mono<T> findById(Long id);
    
	/**
     * <p>
     * 查询所有
     * </p>
     */
	Flux<T> findAll();
}
