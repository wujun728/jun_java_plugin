# p6spy-spring-boot-starter
Spring boot application integrates p6spy print logs quickly

## Quick start

- 一、Import dependencies
```
        <dependency>
            <groupId>com.github.klboke</groupId>
            <artifactId>p6spy-spring-boot-starter</artifactId>
            <version>1.0</version>
        </dependency>
```
- 二、Configure the application.properties
Configuration starts with "p6spy.config." compatible with p6spy's system properties configuration
```
spring.datasource.url = jdbc:p6spy:mysql://xxx
spring.datasource.username = xxx
spring.datasource.password = xxx
spring.datasource.driver-class-name = com.p6spy.engine.spy.P6SpyDriver

p6spy.config.appender = com.p6spy.engine.spy.appender.Slf4JLogger
p6spy.config.logMessageFormat = com.p6spy.engine.spy.appender.CustomLineFormat
p6spy.config.customLogMessageFormat = executionTime:%(executionTime)| 执行sql:%(sqlSingleLine)
```
> By default import dependency, p6spy auto assembly will take effect, you can turn it off by "p6spy.config.enabled = false"
## Configuration description
- "Spring-configuration-metadata.json" has been added to the configuration description, and IDEA has good configuration tips
- More configurations are available：https://p6spy.readthedocs.io/en/latest/configandusage.html
