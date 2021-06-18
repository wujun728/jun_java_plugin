package com.jun.base.jdk.enums;

public class MoreActionDemo {
	public static void main(String[] args) {
		for (MoreAction action : MoreAction.values()) {
			System.out.printf("%s: %s%n", action, action.getDescription());
		}
	}
}