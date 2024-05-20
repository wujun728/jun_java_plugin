package io.github.wujun728.uidgenerator.config;

import io.github.wujun728.uidgenerator.UidGenerator;
import io.github.wujun728.uidgenerator.impl.CachedUidGenerator;
import io.github.wujun728.uidgenerator.impl.DefaultUidGenerator;
import io.github.wujun728.uidgenerator.worker.WorkerIdAssigner;
import org.apache.commons.lang.StringUtils;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import javax.annotation.Resource;

@Configuration
//@Import({UidGeneratorProperties.class, UidGenerator.class, CachedUidGenerator.class, DefaultUidGenerator.class, UidDbConfiguration.class,})
@ComponentScan(basePackages = {"io.github.wujun728.uidgenerator"})
@EnableConfigurationProperties(UidGeneratorProperties.class)
public class UidGeneratorConfig {

    @Resource
    UidGeneratorProperties uidGeneratorProperties;

    @Bean
    public CachedUidGenerator cachedUidGenerator(WorkerIdAssigner disposableWorkerIdAssigner) {
        CachedUidGenerator cachedUidGenerator = new CachedUidGenerator();
        cachedUidGenerator.setWorkerIdAssigner(disposableWorkerIdAssigner);
        setBaseProp(cachedUidGenerator);
        setCachedProp(cachedUidGenerator);
        return cachedUidGenerator;
    }

    void setBaseProp(DefaultUidGenerator defaultUidGenerator) {
        if (uidGeneratorProperties == null) return;
        if (uidGeneratorProperties.getTimeBits() != null) defaultUidGenerator.setTimeBits(uidGeneratorProperties.getTimeBits());
        if (uidGeneratorProperties.getWorkerBits() != null) defaultUidGenerator.setWorkerBits(uidGeneratorProperties.getWorkerBits());
        if (uidGeneratorProperties.getSeqBits() != null) defaultUidGenerator.setSeqBits(uidGeneratorProperties.getSeqBits());
        if (StringUtils.isNotBlank(uidGeneratorProperties.getEpochStr())) defaultUidGenerator.setEpochStr(uidGeneratorProperties.getEpochStr());
    }
    void setCachedProp(CachedUidGenerator cachedUidGenerator) {
        if (uidGeneratorProperties == null) return;
        if (uidGeneratorProperties.getBoostPower() != null) cachedUidGenerator.setBoostPower(uidGeneratorProperties.getBoostPower());
        if (uidGeneratorProperties.getScheduleInterval() != null) cachedUidGenerator.setScheduleInterval(uidGeneratorProperties.getScheduleInterval());
    }
}

