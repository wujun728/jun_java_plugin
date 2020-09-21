package com.yzm.common.checkthread;

import org.apache.http.concurrent.BasicFuture;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.yzm.common.cache.GlobalStore;
import com.yzm.vo.CheckVo;

@Component

public class CheckThread extends BaseThread {

	private static final Logger logger = LoggerFactory.getLogger(CheckThread.class);

	@Override
	public void beforeRun() {
		logger.info("启动getCheck socket监听线程...");
	}

	@Override
	public void process() {
		try {
			CheckVo responseCheckBo = GlobalStore.responseQueue.take();
			BasicFuture<String> bf = GlobalStore.checkResponseMap.remove(responseCheckBo.getToken());
			if (bf != null) {
				bf.completed(responseCheckBo.getCheck());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public void afterRun() {
		logger.error("getCheck socket线程已经停止");
	}

}
