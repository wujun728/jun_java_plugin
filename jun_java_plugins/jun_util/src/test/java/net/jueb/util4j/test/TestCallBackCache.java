package net.jueb.util4j.test;

import java.util.Optional;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicLong;

import com.jun.plugin.util4j.cache.callBack.CallBack;
import com.jun.plugin.util4j.cache.callBack.impl.CallBackCache;

public class TestCallBackCache {

	public static void main(String[] args) {
		CallBackCache cb=new CallBackCache(Executors.newCachedThreadPool());
		CallBack<AtomicLong> cbs=new CallBack<AtomicLong>() {

			@Override
			public void call(boolean timeOut, Optional<AtomicLong> result) {
				
			}
		};
		System.out.println(cbs);
		long key=cb.put(cbs, 100000);
		AtomicLong a=null;
		Class<AtomicLong> ac=null;
		CallBack<AtomicLong> cc=cb.poll(key);
		System.out.println(cc);
	}
}
