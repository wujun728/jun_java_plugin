package com.ky26.collectionn;

public class ComparableByName implements Comparable {


	public int compareTo(Object o1,Object o2) {
		Person1 p1=(Person1) o1;
		Person1 p2=(Person1) o2;
		int result=p1.getAge()>p2.getAge()?1:(p2.getAge()==p1.getAge()?0:-1);
		return result;
	}

	@Override
	public int compareTo(Object o) {
		// TODO Auto-generated method stub
		return 0;
	}
	
}
