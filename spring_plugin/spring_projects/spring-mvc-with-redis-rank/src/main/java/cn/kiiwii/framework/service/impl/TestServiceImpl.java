package cn.kiiwii.framework.service.impl;

import cn.kiiwii.framework.dao.IRedisDAO;
import cn.kiiwii.framework.model.User;
import cn.kiiwii.framework.service.ITestService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sun.nio.cs.ext.DoubleByte;

import java.util.*;

/**
 * Created by zhong on 2016/9/19.
 */
@Service("testService")
public class TestServiceImpl implements ITestService {

    Logger  logger = LoggerFactory.getLogger(this.getClass());
    private static String RANK_LIST_NAME = "rankList";
    @Autowired
    private IRedisDAO redisDAO;

    @Override
    public void addCore(int id, int score) {
        this.redisDAO.addScore(RANK_LIST_NAME,id, score);
    }

    @Override
    public Set getTop(int top) {
        return this.redisDAO.getTop(RANK_LIST_NAME,top  );
    }
    @Override
    public Set getTopWithScore(int top) {
        return this.redisDAO.getTopWithScore(RANK_LIST_NAME,top);
    }
    @Override
    public Set getTopWithScore(int start,int limit) {
        return this.redisDAO.getTopWithScore(RANK_LIST_NAME,start,limit);
    }
}
