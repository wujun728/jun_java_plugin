package com.jun.plugin.codegenerator.admin.core.config;

import cn.hutool.extra.spring.SpringUtil;
import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.apache.ibatis.session.ExecutorType;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

/**
 * mybatis plus config
 *
 * @author wujun
 * @version V1.0
 * @date 2020年3月18日
 */
@Configuration
public class MyBatisPlusConfig {
    /**
     * 配置mybatis-plus 分页查件
     */

//    @Autowired
//    DataSource dataSource;

    /** 配置分页插件*/
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));
        return interceptor;
    }


    @Value("${mybatis-plus.mapper-locations}")
    private String mapperLocations;
    //配置FactoryBean
//    @Bean(name = "sqlSessionFactoryBean")
//    public SqlSessionFactoryBean sqlSessionFactoryBean() {
//        SqlSessionFactoryBean sqlSessionFactoryBean = null;
//        try {
//            // 加载JNDI配置
//            Context context = new InitialContext();
//            // 实例SessionFactory
//            sqlSessionFactoryBean = new SqlSessionFactoryBean();
//
////            DataSource dataSource = SpringUtil.getBean(DataSource.class);
//            // 配置数据源
////            sqlSessionFactoryBean.setDataSource(dataSource());
//            sqlSessionFactoryBean.setDataSource(dataSource);
//            // 加载MyBatis配置文件
//            PathMatchingResourcePatternResolver resourcePatternResolver = new PathMatchingResourcePatternResolver();
//            // 能加载多个，所以可以配置通配符(如：classpath*:mapper/**/*.xml)
//            sqlSessionFactoryBean.setMapperLocations(resourcePatternResolver.getResources(mapperLocations));
//            // 配置mybatis的config文件(我目前用不上)
//            // sqlSessionFactoryBean.setConfigLocation("mybatis-config.xml");
//        } catch (Exception e) {
//            System.out.println("创建SqlSession连接工厂错误：{}");
//        }
//        return sqlSessionFactoryBean;
//    }
//    @Bean
//    public SqlSessionTemplate sqlSessionTemplate() throws Exception {
//        SqlSessionTemplate sqlSessionTemplate=new SqlSessionTemplate(sqlSessionFactoryBean().getObject(), ExecutorType.BATCH);
//        return sqlSessionTemplate;
//    }


}