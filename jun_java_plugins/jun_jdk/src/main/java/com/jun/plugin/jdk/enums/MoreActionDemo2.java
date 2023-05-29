package com.jun.plugin.jdk.enums;

public class MoreActionDemo2 {
	public static void main(String[] args) {
		for (MoreAction2 action : MoreAction2.values()) {
			System.out.printf("%s: %s%n", action, action.getDescription());
		}
	}
}