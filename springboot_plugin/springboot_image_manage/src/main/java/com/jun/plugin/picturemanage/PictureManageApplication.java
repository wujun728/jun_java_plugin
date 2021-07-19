package com.jun.plugin.picturemanage;

import lombok.extern.log4j.Log4j2;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author YuChen
 */
@SpringBootApplication
@Log4j2
public class PictureManageApplication {

    public static void main(String[] args) {
        SpringApplication.run(PictureManageApplication.class, args);
        log.info("============>Started Running...<============");
    }

}
