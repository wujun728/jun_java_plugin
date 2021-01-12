package com.itheima.util;

import java.math.BigInteger;
import java.util.Random;

public class IdGenertor {
	public static String genGUID(){
		return new BigInteger(165, new Random()).toString(36).toUpperCase();
	}
}
