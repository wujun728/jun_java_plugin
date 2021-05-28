package org.tdcg;

import com.baomidou.mybatisplus.annotations.IdType;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.ConfigBaseEntity;
import com.baomidou.mybatisplus.generator.ConfigGenerator;
import com.baomidou.mybatisplus.generator.ConfigIdType;

/**
 * @Title: Generator
 * @Package: org.tdcg
 * @Description: 类生成工具
 * @Author: 二东 <zwd_1222@126.com>
 * @date: 2016/10/31
 * @Version: V1.0
 */
public class Generator {
    public static void main(String[] args) {
        ConfigGenerator cg = new ConfigGenerator();

        // 配置 MySQL 连接
        cg.setDbDriverName("com.mysql.jdbc.Driver");
        cg.setDbUser("root");
        cg.setDbPassword("root");
        cg.setDbUrl("jdbc:mysql://127.0.0.1:3306/activemq?characterEncoding=utf8&serverTimezone=GMT&userSSL=false");

        // 配置包名
        cg.setEntityPackage("org.tdcg.entity");
        cg.setMapperPackage("org.tdcg.mapper");
        cg.setServiceName("%sService");

        // 生成路径
        cg.setXmlPackage("mapper");
        cg.setServicePackage("org.tdcg.service");
        cg.setServiceImplPackage("org.tdcg.service.impl");
        // 生成表名
        cg.setTableNames(new String[]{"t_goods","t_order","t_order_item","t_user","t_shop"});
        cg.setDbPrefix(true);
        cg.setIdType(IdType.UUID);
        cg.setConfigIdType(ConfigIdType.STRING);

        // 配置保存路径
        cg.setSaveDir("C:\\mybatis-plus-generated");

        // 其他参数请根据上面的参数说明自行配置，当所有配置完善后，运行AutoGenerator.run()方法生成Code
        // 生成代码
        AutoGenerator.run(cg);
    }
}
