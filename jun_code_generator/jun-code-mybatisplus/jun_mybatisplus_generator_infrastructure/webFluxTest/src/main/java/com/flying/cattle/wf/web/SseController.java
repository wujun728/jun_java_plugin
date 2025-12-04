package com.flying.cattle.wf.web;

import java.time.Duration;

import org.springframework.http.MediaType;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.util.function.Tuples;

@RestController
@RequestMapping("/sse")
public class SseController {

	private int count_down_sec=3*60*60;
	
	@GetMapping(value="/countDown",produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	public Flux<ServerSentEvent<Object>> countDown() {
		
		return Flux.interval(Duration.ofSeconds(1))
			.map(seq -> Tuples.of(seq, getCountDownSec()))
			.map(data -> ServerSentEvent.<Object>builder()
					.event("countDown")
					.id(Long.toString(data.getT1()))
					.data(data.getT2().toString())
					.build());
	}
	
	private String getCountDownSec() {
		if (count_down_sec>0) {
			int h = count_down_sec/(60*60);
			int m = (count_down_sec%(60*60))/60;
			int s = (count_down_sec%(60*60))%60;
			count_down_sec--;
			return "活动倒计时："+h+" 小时 "+m+" 分钟 "+s+" 秒";
		}
		return "活动倒计时：0 小时 0 分钟 0 秒";
	}
}
