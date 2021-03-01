package designpatterns.structurePatterns.adapter.adapter_interface;

public class TestApapterInterface {

	public static void main(String[] args) {
		new SourceSub1().method();
		new SourceSub1().methodNew();
		new SourceSub2().methodNew();
	}

}
