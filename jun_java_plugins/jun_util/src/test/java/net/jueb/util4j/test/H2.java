package net.jueb.util4j.test;

public class H2 extends HotSwap{
	
@Override
	public void show() {
		super.show();
	}

	protected String getTestStr()
	{
		return "H2:testStr";
	}
	
	@Override
	public boolean isUsing() {
		return false;
	}
	@Override
	public long getVersion() {
		return 2;
	}

	@Override
	public long getUniqueId() {
		return 0;
	}
	
}
