/**
 * 
 */
package com.opensource.nredis.proxy.monitor.zookeeper.constants;

import java.util.regex.Pattern;



/**
 * 常量
 * @author liubing
 *
 */
public class NRedisProxyConstants {
	
	public static final String SEPERATOR_ACCESS_LOG = "|";
    public static final String COMMA_SEPARATOR = ",";
    public static final Pattern COMMA_SPLIT_PATTERN = Pattern.compile("\\s*[,]+\\s*");
    public static final String PROTOCOL_SEPARATOR = ":";
    public static final String PATH_SEPARATOR = "/";
    public static final String REGISTRY_SEPARATOR = "|";
    public static final Pattern REGISTRY_SPLIT_PATTERN = Pattern.compile("\\s*[|;]+\\s*");
    public static final String SEMICOLON_SEPARATOR = ";";
    public static final Pattern SEMICOLON_SPLIT_PATTERN = Pattern.compile("\\s*[;]+\\s*");
    public static final String QUERY_PARAM_SEPARATOR = "&";
    public static final Pattern QUERY_PARAM_PATTERN = Pattern.compile("\\s*[&]+\\s*");
    public static final String EQUAL_SIGN_SEPERATOR = "=";
    public static final Pattern EQUAL_SIGN_PATTERN = Pattern.compile("\\s*[=]\\s*");

    public static final int MILLS = 1;
    public static final int SECOND_MILLS = 1000;
    public static final int MINUTE_MILLS = 60 * SECOND_MILLS;
    public static final String DEFAULT_VALUE = "default";
    public static final int DEFAULT_INT_VALUE = 0;
    public static final String DEFAULT_VERSION = "1.0";
    public static final boolean DEFAULT_THROWS_EXCEPTION = true;
    public static final String DEFAULT_CHARACTER = "utf-8";
    public static final int SLOW_COST = 50; // 50ms
    public static final int STATISTIC_PEROID = 30; // 30 seconds
    
    public static final String KEYS="keys";
    
    public static final String INFO="info";
    
    public static final String ZOOKEEPER_REGISTRY_NAMESPACE = "/nredis-proxy";
    
    
    public static final String ZOOKEEPER_REGISTRY_COMMAND = "/command";
    
    public static final String TOP_PATH="/nredis-proxy/command/server";
    
    public static final String REGISTRY_HEARTBEAT_SWITCHER = "feature.configserver.heartbeat";
    
    public static final String ADDRESS="address";
    
    public static final Integer TIMEOUT=500;
    /**
     * netty channel constants start
     **/
    // netty codec
    public static final short NETTY_MAGIC_TYPE = (short) 0xF1F1;
    // netty header length
    public static final int NETTY_HEADER = 16;
    // netty server max excutor thread
    public static final int NETTY_EXECUTOR_MAX_SIZE = 800;
    // netty thread idle time: 1 mintue
    public static final int NETTY_THREAD_KEEPALIVE_TIME = 60 * 1000;
    // netty client max concurrent request TODO 2W is suitable?
    public static final int NETTY_CLIENT_MAX_REQUEST = 20000;
    // share channel max worker thread
    public static final int NETTY_SHARECHANNEL_MAX_WORKDER = 800;
    // share channel min worker thread
    public static final int NETTY_SHARECHANNEL_MIN_WORKDER = 40;
    // don't share channel max worker thread
    public static final int NETTY_NOT_SHARECHANNEL_MAX_WORKDER = 200;
    // don't share channel min worker thread
    public static final int NETTY_NOT_SHARECHANNEL_MIN_WORKDER = 20;
    public static final int NETTY_TIMEOUT_TIMER_PERIOD = 100;
    public static final byte NETTY_REQUEST_TYPE = 1;
    public static final byte FLAG_REQUEST = 0x00;
    public static final byte FLAG_RESPONSE = 0x01;
    public static final byte FLAG_RESPONSE_VOID = 0x03;
    public static final byte FLAG_RESPONSE_EXCEPTION = 0x05;
    public static final byte FLAG_RESPONSE_ATTACHMENT = 0x07;
    public static final byte FLAG_OTHER = (byte) 0xFF;
    /**
     * heartbeat constants start
     */
    public static final int HEARTBEAT_PERIOD = 500;

    /**
     * 默认的consistent的hash的数量
     */
    public static final int DEFAULT_CONSISTENT_HASH_BASE_LOOP = 1000;
    
    /**
     * 
     */
    public static final String WEIGHT_RULE="weight_rule";//权重规则
    
    public static final String REDIS_PROXY="nredis_proxy_";
    
    public static final char DOLLAR_BYTE = '$';
    public static final char ASTERISK_BYTE = '*';
    public static final char COLON_BYTE = ':';
    public static final char OK_BYTE = '+';
    public static final char ERROR_BYTE = '-';
    public static final char CR_BYTE = '\r';
    public static final char LF_BYTE = '\n';

    private NRedisProxyConstants() {
    	
    }
}	
