package com.jun.plugin.drools.dao;

import com.jun.plugin.drools.bean.Rules;
import com.jun.plugin.drools.config.MyBatisRepository;



/**
 * Created by yangyibo on 16/12/9.
 */
@MyBatisRepository
public interface RulesDao {

     Rules getById (Integer id);
}
