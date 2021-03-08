package com.abc.demo.util;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class SchedulerTask {
	private static final SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
	@Scheduled(fixedRate=2000)
	private void process() {
		System.out.println("scheduler task runing 现在时间："+sdf.format(new Date()));
		System.out.println("==========================================");
	}
}
