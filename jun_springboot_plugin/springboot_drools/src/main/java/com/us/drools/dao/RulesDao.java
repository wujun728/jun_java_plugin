package com.us.drools.dao;

import com.us.drools.bean.Rules;
import com.us.drools.config.MyBatisRepository;



/**
 * Created by yangyibo on 16/12/9.
 */
@MyBatisRepository
public interface RulesDao {

     Rules getById (Integer id);
}
