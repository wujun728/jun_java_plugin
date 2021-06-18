package com.mycompany.myproject.job;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;


@Component
public class MyJob {

    //@Scheduled(cron = "*/1 * * * * *")
    public void execute(){

        System.out.println("MyJob execute");

    }
}
