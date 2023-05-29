package net.jueb.util4j.beta.serializable.bytesMap;

public class UTF16LEString {

	private String str;
	public UTF16LEString(String str) {
		this.str=str;
	}
	@Override
	public String toString() {
		return this.str;
	}
	
	public String getString()
	{
		return this.str;
	}
	
}
