package designpatterns.createPatterns.builder;

public class Test {

	public static void main(String[] args) {
		BuildGames bd=new BuildGames();
		System.err.println(bd.buildGame(new Builder()).getGameInfo());
	}

}
