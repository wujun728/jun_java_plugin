package com.jun.plugin.multi.datasource.jpa.repository.second;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jun.plugin.multi.datasource.jpa.entity.second.SecondMultiTable;

/**
 * <p>
 * 多数据源测试 repo
 * </p>
 *
 * @package: com.xkcoding.multi.datasource.jpa.repository.second
 * @description: 多数据源测试 repo
 * @author: yangkai.shen
 * @date: Created in 2019-01-18 10:11
 * @copyright: Copyright (c) 2019
 * @version: V1.0
 * @modified: yangkai.shen
 */
@Repository
public interface SecondMultiTableRepository extends JpaRepository<SecondMultiTable, Long> {
}
