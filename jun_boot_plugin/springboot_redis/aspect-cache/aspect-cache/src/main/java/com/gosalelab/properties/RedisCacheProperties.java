package com.gosalelab.properties;

/**
 * @author Wujun
 */
public class RedisCacheProperties {

    private String host = "127.0.0.1";
    private int timeout = 2000;
    private String password;
    private int database = 0;
    private int port = 6379;
    /**
     * connect time out，default value is 2000 milliseconds
     */
    private int maxWaitMillis = 1000;
    /**
     * set max jedis instance can create.
     * if it sets -1, it indicates that has not limit of jedis instance
     */
    private Integer maxTotal = 1000;
    /**
     * set max idle jedis instance
     */
    private Integer maxIdle = 100;

    private Integer minIdle = 20;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getDatabase() {
        return database;
    }

    public void setDatabase(int database) {
        this.database = database;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getTimeout() {
        return timeout;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }

    public Integer getMaxTotal() {
        return maxTotal;
    }

    public void setMaxTotal(Integer maxTotal) {
        this.maxTotal = maxTotal;
    }

    public Integer getMaxIdle() {
        return maxIdle;
    }

    public void setMaxIdle(Integer maxIdle) {
        this.maxIdle = maxIdle;
    }

    public int getMaxWaitMillis() {
        return maxWaitMillis;
    }

    public void setMaxWaitMillis(int maxWaitMillis) {
        this.maxWaitMillis = maxWaitMillis;
    }

    public Integer getMinIdle() {
        return minIdle;
    }

    public void setMinIdle(Integer minIdle) {
        this.minIdle = minIdle;
    }
}
