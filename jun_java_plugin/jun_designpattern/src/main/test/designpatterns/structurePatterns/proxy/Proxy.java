package designpatterns.structurePatterns.proxy;

public class Proxy implements SourceInterface{
	Source source=new Source();
	
	@Override
	public void method() {
		System.err.println("Proxy run start ......");
		source.method();
		System.err.println("Proxy run end ......");
	}

}
