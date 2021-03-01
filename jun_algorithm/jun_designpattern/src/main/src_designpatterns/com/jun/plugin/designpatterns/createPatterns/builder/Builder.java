package com.jun.plugin.designpatterns.createPatterns.builder;

public class Builder implements BuildInterface {
	Game game;
	
	public Builder() {
		game = new Game();
	}
	
	@Override
	public void buildStep1() {
		game.setGnameName("gnameName");
		System.err.println(" step 1 ");
	}

	@Override
	public void buildStep2() {
		game.setGameType(" table game ");
		System.err.println(" step 2 ");
	}

	@Override
	public Game buildEnd() {
		game.setGameInfo("game info ");
		return game;
	}
	
}
