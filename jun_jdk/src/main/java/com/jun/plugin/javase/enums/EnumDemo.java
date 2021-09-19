package com.jun.plugin.javase.enums;

public class EnumDemo {
	public static void main(String[] args) {
		doAction(Action.TURN_RIGHT);
	}

	public static void doAction(Action action) {
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