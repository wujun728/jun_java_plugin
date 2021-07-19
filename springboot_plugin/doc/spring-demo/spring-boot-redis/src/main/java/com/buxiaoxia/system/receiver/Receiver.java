package com.buxiaoxia.system.receiver;

import lombok.extern.slf4j.Slf4j;

/**
 * Created by xw on 2017/3/22.
 * 2017-03-22 22:26
 */
@Slf4j
public class Receiver {

	public void receiveMessage(String message) {
		log.info("Receiver接受者，接收到的消息内容： <" + message + ">");
	}
}
