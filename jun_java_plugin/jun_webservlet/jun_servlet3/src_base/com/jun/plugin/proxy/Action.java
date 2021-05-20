package com.jun.plugin.proxy;

public class Action implements DefineAction {

	public Action() {

	}

	public Action(String action) {

	}

	@Override
	public void say() {
		System.out.println("action#say");
	}

}
