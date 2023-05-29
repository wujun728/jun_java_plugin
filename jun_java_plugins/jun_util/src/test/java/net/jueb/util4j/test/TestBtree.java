package net.jueb.util4j.test;

import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;

import com.jun.plugin.util4j.cache.map.btree.BTree;
import com.jun.plugin.util4j.cache.map.btree.BTree.MaskEnum;

public class TestBtree {

	
	public void test(MaskEnum mask)
	{
		BTree<Byte> mtree=new BTree<>(mask);
		long t=System.currentTimeMillis();
		for(int i=0;i<5000000;i++)
		{
			mtree.write(i,(byte) (i+100));
		}
		long t1=System.currentTimeMillis()-t;
		final AtomicInteger i=new AtomicInteger(0);
		t=System.currentTimeMillis();
		mtree.forEach((k,v)->{
			i.incrementAndGet();
//			System.out.println(k+":"+v);
		});
		long t2=System.currentTimeMillis()-t;
		System.out.println(t1+","+t2+","+i.get());
	}
	
	public static void main(String[] args) {
		TestBtree tb=new TestBtree();
		tb.test(MaskEnum.MASK_1111_1111);
		Scanner sc=new Scanner(System.in);
		sc.nextLine();
	}
}
