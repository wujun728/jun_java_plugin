package net.jueb.util4j.beta.serializable.nmap;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Map;

import net.jueb.util4j.beta.serializable.nmap.type.NBoolean;
import net.jueb.util4j.beta.serializable.nmap.type.NInteger;
import net.jueb.util4j.beta.serializable.nmap.type.NMap;
import net.jueb.util4j.beta.serializable.nmap.type.NUTF8String;

public class Test {

	public static void main(String[] args) throws Exception {
		build();
		Test t = new Test();
		File f = new File("d:/map2.data");
		long i = System.currentTimeMillis();
		NMap nmap = new NMap().load(f);
		long x = System.currentTimeMillis() - i;
		// System.out.println(nmap);
		System.out.println("解码耗时:" + x);

		i = System.currentTimeMillis();
		nmap.saveTo(new File("d:/map2.data"));
		x = System.currentTimeMillis() - i;
		System.out.println("编码耗时:" + x);
		nmap = nmap.load(new File("d:/map2.data"));
		System.out.println(nmap);
		System.out.println("解码耗时:" + x);

		i = System.currentTimeMillis();
		Map<Object, Object> map = nmap.toMap();
		System.out.println(map.toString());
		x = System.currentTimeMillis() - i;
		System.out.println("nmap转换为map耗时:" + x);

		i = System.currentTimeMillis();
		nmap = nmap.mapConvert.toNMap(map);
		System.out.println(nmap.toString());
		x = System.currentTimeMillis() - i;
		System.out.println("map转换为nmap耗时:" + x);

	}

	public static NMap build() {
		long i = System.currentTimeMillis();
		NUTF8String nameKey = new NUTF8String("name");
		NUTF8String nameValue = new NUTF8String("tom");
		NUTF8String ageKey = new NUTF8String("age");
		NInteger ageValue = new NInteger(18);
		NUTF8String isWorkKey = new NUTF8String("isWork");
		NBoolean isWorkValue = new NBoolean(true);
		NMap nmap = new NMap();
		nmap.put(nameKey, nameValue);
		nmap.put(ageKey, ageValue);
		nmap.put(isWorkKey, isWorkValue);
		NMap nmap2 = new NMap();
		nmap2.put(nameKey, nameValue);
		nmap2.put(ageKey, ageValue);
		nmap2.put(isWorkKey, isWorkValue);
		NMap nmap3 = new NMap();
		nmap3.put(nameKey, nameValue);
		nmap3.put(ageKey, ageValue);
		nmap3.put(isWorkKey, isWorkValue);
		nmap.put(nmap2, nmap3);
		try {
			nmap2.saveTo(new File("d:/map2.data"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return nmap2;
	}

	public static void showMap(NMap nmap) {
		build();
//		byte[] data = nmap.getBytes();
//		System.out.println(nmap.toString());
//		System.out.println(Arrays.toString(data));
	}
}
