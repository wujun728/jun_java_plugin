package com.flying.cattle.wfs.controller;

import java.util.concurrent.ConcurrentHashMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.flying.cattle.wfs.entity.WebSocketSender;

@RestController
@RequestMapping("/msg")
public class MsgController {

	@Autowired
	private ConcurrentHashMap<String, WebSocketSender> senderMap;

	@RequestMapping("/send")
	public String sendMessage(@RequestParam String id, @RequestParam String data) {
		WebSocketSender sender = senderMap.get(id);
		if (sender != null) {
			sender.sendData(data);
			return String.format("Message '%s' sent to connection: %s.", data, id);
		} else {
			return String.format("Connection of id '%s' doesn't exist", id);
		}
	}
}
