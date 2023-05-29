package net.jueb.util4j.study.jdk8.stream;

import java.util.Collection;
import java.util.HashSet;
import java.util.stream.Stream;

public class StreamTest2 {

	Collection<Integer> c1=new HashSet<>();
	Collection<Integer> c2=new HashSet<>();
	
	/**
	 * 流合并
	 */
	public void test1(){
		c1.add(1);
		c1.add(2);
		c1.add(3);
		c2.add(3);
		c2.add(4);
		c2.add(5);
		Stream<Integer> s=Stream.concat(c1.stream(), c2.stream());
		s.forEach(a->{
			System.out.println(a);
		});;
	}
	/**
	 * 删除不影响流
	 */
	public void test2(){
		c1.add(1);
		c1.add(2);
		c1.add(3);
		c2.add(3);
		c2.add(4);
		c2.add(5);
		Stream<Integer> s=Stream.concat(c1.stream(), c2.stream());
		c1.remove(1);
		s.forEach(a->{
			System.out.println(a);
		});;
	}
	
	public static void main(String[] args) {
		new StreamTest2().test1();
	}
}
