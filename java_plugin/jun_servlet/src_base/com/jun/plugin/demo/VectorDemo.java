package com.jun.plugin.demo;

import java.util.Enumeration;
import java.util.Iterator;
import java.util.Vector;

public class VectorDemo {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		Vector v = new Vector();
		
		v.addElement("abc1");
		v.addElement("abc2");
		v.addElement("abc3");
		v.addElement("abc4");
		
		Enumeration en = v.elements();
		while(en.hasMoreElements()){
			System.out.println("nextelment:"+en.nextElement());
		}
		
		Iterator it = v.iterator();
		
		while(it.hasNext()){
			System.out.println("next:"+it.next());
		}
		
	}

}
