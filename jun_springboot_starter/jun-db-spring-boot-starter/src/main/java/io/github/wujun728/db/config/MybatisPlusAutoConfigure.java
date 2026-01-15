package io.github.wujun728.db.config;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.handler.TenantLineHandler;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import io.github.wujun728.common.datascope.mp.interceptor.DataScopeInnerInterceptor;
import io.github.wujun728.common.datascope.mp.interceptor.EnableQuerySqlLogInnerInterceptor;
import io.github.wujun728.common.datascope.mp.sql.handler.CreatorDataScopeSqlHandler;
import io.github.wujun728.common.datascope.mp.sql.handler.SqlHandler;
import io.github.wujun728.common.properties.DataScopeProperties;
import io.github.wujun728.common.properties.TenantProperties;
import io.github.wujun728.db.interceptor.CustomTenantInterceptor;
import io.github.wujun728.db.properties.MybatisPlusAutoFillProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

/**
 * mybatis-plus自动配置
 *
 */
@EnableConfigurationProperties({MybatisPlusAutoFillProperties.class, DataScopeProperties.class})
public class MybatisPlusAutoConfigure {
    @Autowired
    private TenantLineHandler tenantLineHandler;

    @Autowired
    private TenantProperties tenantProperties;

    @Autowired
    private MybatisPlusAutoFillProperties autoFillProperties;

    @Autowired
    private DataScopeProperties dataScopeProperties;

    @Bean
    @ConditionalOnMissingBean
    public SqlHandler sqlHandler(){
        return new CreatorDataScopeSqlHandler();
    }

    /**
     * 分页插件，自动识别数据库类型
     */
    @Bean
    public MybatisPlusInterceptor paginationInterceptor(SqlHandler sqlHandler) {
        MybatisPlusInterceptor mpInterceptor = new MybatisPlusInterceptor();
        boolean enableTenant = tenantProperties.getEnable();
        //是否开启多租户隔离
        if (enableTenant) {
            CustomTenantInterceptor tenantInterceptor = new CustomTenantInterceptor(
                    tenantLineHandler, tenantProperties.getIgnoreSqls());
            mpInterceptor.addInnerInterceptor(tenantInterceptor);
        }
        if(dataScopeProperties.getEnabled()){
            DataScopeInnerInterceptor dataScopeInnerInterceptor = new DataScopeInnerInterceptor(dataScopeProperties, sqlHandler);
            mpInterceptor.addInnerInterceptor(Boolean.TRUE.equals(dataScopeProperties.getEnabledSqlDebug())
                    ? new EnableQuerySqlLogInnerInterceptor(dataScopeInnerInterceptor): dataScopeInnerInterceptor);
        }
        mpInterceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));
        return mpInterceptor;
    }

    /** 配置分页插件*/
    /*@Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));
        return interceptor;
    }*/

    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnProperty(prefix = "jun.mybatis-plus.auto-fill", name = "enabled", havingValue = "true", matchIfMissing = true)
    public MetaObjectHandler metaObjectHandler() {
        return new DateMetaObjectHandler(autoFillProperties);
    }
}
