package com.jun.plugin.designpatterns.createPatterns.builder;

public class BuildGames {
	public Game buildGame(BuildInterface bi){
		bi.buildStep1();
		bi.buildStep2();
		return bi.buildEnd();
	}
}
