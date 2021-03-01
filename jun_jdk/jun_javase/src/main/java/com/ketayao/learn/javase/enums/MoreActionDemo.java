package com.ketayao.learn.javase.enums;

public class MoreActionDemo {
	public static void main(String[] args) {
		for (MoreAction action : MoreAction.values()) {
			System.out.printf("%s: %s%n", action, action.getDescription());
		}
	}
}