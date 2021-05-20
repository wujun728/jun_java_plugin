package com.jun.base.jdk.enums;

public class EnumDemo2 {
	private enum InnerAction {
		TURN_LEFT, TURN_RIGHT, SHOOT
	};

	public static void main(String[] args) {
		doAction(InnerAction.TURN_RIGHT);
	}

	public static void doAction(InnerAction action) {
		switch (action) {
		case TURN_LEFT:
			System.out.println("向左转");
			break;
		case TURN_RIGHT:
			System.out.println("向右转");
			break;
		case SHOOT:
			System.out.println("射击");
			break;
		}
	}
}