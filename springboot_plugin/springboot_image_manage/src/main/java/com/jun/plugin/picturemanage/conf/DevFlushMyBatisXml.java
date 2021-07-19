package com.jun.plugin.picturemanage.conf;

import com.baomidou.mybatisplus.extension.spring.MybatisMapperRefresh;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileUrlResource;
import org.springframework.core.io.Resource;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author PuaChen
 * @Create 2018-12-20 15:38
 */

@SpringBootConfiguration
@ConditionalOnProperty(name = "mybatis-plus.global-config.refresh-mapper", havingValue = "true")
public class DevFlushMyBatisXml {
    /**
     * 热部署
     *
     * @param sqlSessionFactory
     * @return
     */
    @Bean
    public MybatisMapperRefresh mapperRefresh(@Autowired SqlSessionFactory sqlSessionFactory) throws IOException {
        System.out.println("  __  __                                           ____            __                       _     \n" +
                " |  \\/  |   __ _   _ __    _ __     ___   _ __    |  _ \\    ___   / _|  _ __    ___   ___  | |__  \n" +
                " | |\\/| |  / _` | | '_ \\  | '_ \\   / _ \\ | '__|   | |_) |  / _ \\ | |_  | '__|  / _ \\ / __| | '_ \\ \n" +
                " | |  | | | (_| | | |_) | | |_) | |  __/ | |      |  _ <  |  __/ |  _| | |    |  __/ \\__ \\ | | | |\n" +
                " |_|  |_|  \\__,_| | .__/  | .__/   \\___| |_|      |_| \\_\\  \\___| |_|   |_|     \\___| |___/ |_| |_|\n" +
                "                  |_|     |_|                                                                     \n");
        ClassPathResource resource = new ClassPathResource("mapper");
        File root = resource.getFile();

        List<File> fileList = new ArrayList<>();

        addXmlFile(fileList, root);

        Resource[] res = new Resource[fileList.size()];
        for (int i = 0; i < fileList.size(); i++) {
            res[i] = new FileUrlResource(fileList.get(i).getAbsolutePath());
        }
        MybatisMapperRefresh refresh = new MybatisMapperRefresh(res, sqlSessionFactory, 5, 5, true);
        return refresh;
    }


    public void addXmlFile(List<File> list, File root) {
        for (File file : root.listFiles()) {
            if (file.isDirectory()) {
                addXmlFile(list, file);
            } else {
                if (file.getName().endsWith(".xml")) {
                    list.add(file);
                }
            }
        }
    }

}
