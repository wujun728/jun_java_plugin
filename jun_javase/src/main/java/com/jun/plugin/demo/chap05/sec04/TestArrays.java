package com.jun.plugin.demo.chap05.sec04;

import java.util.Arrays;

public class TestArrays {

	public static void main(String[] args) {
		int arr[]={1,7,3,8,2};
		System.out.println("���ַ�����ʽ������飺"+Arrays.toString(arr));
		Arrays.sort(arr);  // ����������
		System.out.println("���������飺"+Arrays.toString(arr));
		System.out.println(Arrays.binarySearch(arr, 1));
		Arrays.fill(arr, 0); // ��ָ��������䵽������
		System.out.println("����������ַ�����"+Arrays.toString(arr));
	}
}
