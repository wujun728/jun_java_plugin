package designpatterns.structurePatterns.adapter.adapter_object;

public class Test2 {

	public static void main(String[] args) {
		new Adapter2(new Source()).method();
		new Adapter2(new Source()).methodNew();
	}

}
