package messfairy;

import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class DatabaseConfig {
    protected void configureDataSource(BasicDataSource dataSource) {
        dataSource.setMaxActive(20);
        dataSource.setMaxIdle(8);
        dataSource.setMinIdle(8);
        dataSource.setTestOnBorrow(false);
        dataSource.setTestOnReturn(false);
    }
    @Bean
    public DataSource dataSource() {
        // instantiate, configure and return DataSource
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
//        dataSource.setUrl("jdbc:mysql://localhost/myblog?characterEncoding=utf-8");
//        dataSource.setUsername("root");
//        dataSource.setPassword("root");
        dataSource.setUrl("jdbc:mysql://10.4.12.173:3306/dd984836e9dde4d35b01ab87ec11b14f1?characterEncoding=utf-8");
        dataSource.setUsername("umKFSjZ742Zih");
        dataSource.setPassword("pTF2665xEkjWY");
        dataSource.setValidationQuery("SELECT 1");
        configureDataSource(dataSource);
        return dataSource;
    }
}