package cn.kiiwii.framework.service;

import java.util.Set;

/**
 * Created by zhong on 2016/9/19.
 */
public interface ITestService {
    void addCore(int id, int score);

    Set getTop(int top);

    Set getTopWithScore(int top);

    public Set getTopWithScore(int start,int limit);
}
