package observer;

public class TestObserver {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Reader reader = new Reader();
		reader.setName("heihei");
		
		Reader reader2 = new Reader();
		reader2.setName("gg");
		
		NewsPaper newsPaper = new NewsPaper();
		
		newsPaper.addObserver(reader);
		newsPaper.addObserver(reader2);
		
		newsPaper.setContent("新报纸");
	}

}
