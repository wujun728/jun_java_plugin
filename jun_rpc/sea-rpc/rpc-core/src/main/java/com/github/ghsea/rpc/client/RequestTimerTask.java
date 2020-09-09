package com.github.ghsea.rpc.client;

import io.netty.util.Timeout;
import io.netty.util.TimerTask;

public class RequestTimerTask implements TimerTask {

	private String requestId;

	private NettyClient client;

	public RequestTimerTask(final NettyClient client, String requestId) {
		this.requestId = requestId;
		this.client = client;
	}

	@Override
	public void run(Timeout timeout) throws Exception {
		client.timeout(requestId);
		timeout.cancel();
	}

}
