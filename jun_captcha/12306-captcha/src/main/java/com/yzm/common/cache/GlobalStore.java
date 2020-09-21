package com.yzm.common.cache;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.http.concurrent.BasicFuture;

import com.yzm.vo.CheckVo;
import com.yzm.vo.RailDeviceidVo;

public class GlobalStore {

	public static ConcurrentHashMap<String, BasicFuture<String>> checkResponseMap = new ConcurrentHashMap<>();

	public static BlockingQueue<CheckVo> responseQueue = new ArrayBlockingQueue<>(1024);
	
	
	public static ConcurrentHashMap<String, BasicFuture<String>> railDeviceResponseMap = new ConcurrentHashMap<>();

	public static BlockingQueue<RailDeviceidVo> railDeviceResponseQueue = new ArrayBlockingQueue<>(1024);
}
