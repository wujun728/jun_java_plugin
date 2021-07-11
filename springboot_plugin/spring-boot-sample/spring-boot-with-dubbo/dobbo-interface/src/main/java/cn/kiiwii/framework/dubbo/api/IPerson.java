package cn.kiiwii.framework.dubbo.api;

/**
 * Created by zhong on 2016/11/22.
 */
public interface IPerson {
    String getFullName(String name);
    String getNickName(int id);
}
