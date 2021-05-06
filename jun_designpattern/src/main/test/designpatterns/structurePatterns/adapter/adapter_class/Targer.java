package designpatterns.structurePatterns.adapter.adapter_class;

public class Targer extends Adapter implements TargerInterface {

	@Override
	public void methodNew() {
		System.err.println("target run ... ");
	}
}
