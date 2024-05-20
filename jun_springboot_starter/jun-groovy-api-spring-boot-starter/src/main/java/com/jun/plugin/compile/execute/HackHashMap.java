package com.jun.plugin.compile.execute;

import java.util.HashMap;

public class HackHashMap extends HashMap {
	public HackHashMap(int initialCapacity, float loadFactor) {
		super(5,loadFactor);
	}
	public  HackHashMap(){
		this(5,0.75F);
	}
}
