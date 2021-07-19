package cn.kiiwii.framework.dao;

import cn.kiiwii.framework.model.Test;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.List;

/**
 * Created by zhong on 2016/11/24.
 */
@Repository
@Table(name="test")
@Qualifier("testRepository")
public interface TestRepository extends JpaRepository<Test,Integer> {

    @Query(" from Test t where t.testName=:testName")
    List<Test> findUser(@Param("testName") String testName);
}
