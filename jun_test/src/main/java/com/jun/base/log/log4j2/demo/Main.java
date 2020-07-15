package com.jun.base.log.log4j2.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author 许波波
 * @date 2019/1/16
 */
public class Main {
    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        logger.debug("打印debug级别日志");
        logger.info("打印info级别日志");
        logger.warn("打印warn级别日志");
        logger.error("打印error级别日志");
    }
}
