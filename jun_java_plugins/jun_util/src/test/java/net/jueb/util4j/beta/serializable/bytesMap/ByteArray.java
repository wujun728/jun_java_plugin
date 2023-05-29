package net.jueb.util4j.beta.serializable.bytesMap;

public class ByteArray {
	public int byteArraySize;
	public byte[] byteArrayData;
	public ByteArray()
	{
		
	}
	public ByteArray(int byteArraySize,byte[] byteArrayData)
	{
		this.byteArraySize=byteArraySize;
		this.byteArrayData=byteArrayData;
	}
	public int getByteArraySize() {
		return byteArraySize;
	}
	public void setByteArraySize(int byteArraySize) {
		this.byteArraySize = byteArraySize;
	}
	public byte[] getByteArrayData() {
		return byteArrayData;
	}
	public void setByteArrayData(byte[] byteArrayData) {
		this.byteArrayData = byteArrayData;
	}
	@Override
	public String toString() {
		return "byteArray,Size:" + byteArraySize;
	}
	
}
