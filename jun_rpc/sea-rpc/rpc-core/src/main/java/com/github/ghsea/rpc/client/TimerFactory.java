package com.github.ghsea.rpc.client;

import io.netty.util.HashedWheelTimer;
import io.netty.util.Timer;

public class TimerFactory {
	private static Timer timer = new HashedWheelTimer();

	public static Timer getTimer() {
		return timer;
	}
}
