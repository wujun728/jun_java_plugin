package net.jueb.util4j.beta.tools.sbuffer;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public abstract class SnapshotBuffer2Base {
	
	private int chunkSize=512;//区块大小
	private byte[][] array=new byte[][]{};
	private final SnapshotBuffer2Base parent;//
	private final int offSet;//偏移索引
	protected volatile boolean readOnly;
//	private Map<Integer,Byte> override=new LinkedHashMap<Integer, Byte>();//底层数据覆盖
	private SparseArray<Byte> override=new SparseArray<Byte>();//底层数据覆盖
	
	public SnapshotBuffer2Base() {
		this(null,0);
	}
	public SnapshotBuffer2Base(SnapshotBuffer2Base parent,int offSet) {
		this.parent=parent;
		this.offSet=offSet;
	}
	
	void checkIndex(int index, int fieldLength) {
		if (this.isOutOfBounds(index, fieldLength, this.capacity())) {
			throw new IndexOutOfBoundsException(
					String.format("index: %d, length: %d (expected: range(0, %d))", new Object[] {
							Integer.valueOf(index), Integer.valueOf(fieldLength), Integer.valueOf(this.capacity()) }));
		}
	}
	
	void checkDstIndex(int index, int length, int dstIndex, int dstCapacity) {
		this.checkIndex(index, length);
		if (this.isOutOfBounds(dstIndex, length, dstCapacity)) {
			throw new IndexOutOfBoundsException(String.format("dstIndex: %d, length: %d (expected: range(0, %d))",
					new Object[] { Integer.valueOf(dstIndex), Integer.valueOf(length), Integer.valueOf(dstCapacity) }));
		}
	}

	void checkSrcIndex(int index, int length, int srcIndex, int srcCapacity) {
		this.checkIndex(index, length);
		if (this.isOutOfBounds(srcIndex, length, srcCapacity)) {
			throw new IndexOutOfBoundsException(String.format("srcIndex: %d, length: %d (expected: range(0, %d))",
					new Object[] { Integer.valueOf(srcIndex), Integer.valueOf(length), Integer.valueOf(srcCapacity) }));
		}
	}

	boolean isOutOfBounds(int index, int length, int capacity) {
		return (index | length | index + length | capacity - (index + length)) < 0;
	}
	
	/**
	 * 容量(总长度)
	 * @return
	 */
	public int capacity(){
		int len=array.length*chunkSize;
		if(parent!=null)
		{
			len+=parent.capacity();
		}
		return len;
	}

	/**
	 * 获取写索引
	 * 
	 * @return
	 */
	public abstract int getWriteIndex();
	/**
	 * 获取写索引
	 * 
	 * @return
	 */
	public abstract int getReadIndex();	
	
	protected void ensureCapacity(int addBytes) {
		int newCapacity = getWriteIndex() + addBytes;
		int cp=this.capacity();//当前容量
		if (newCapacity > cp) {
			int len=newCapacity;
			len-=offSet;//需要增加的容量
			int addChunkNum=len/chunkSize+1;//多预留一个chunk
			int newLen=array.length+addChunkNum;
			byte[][] newArray = new byte[newLen][chunkSize];
			System.arraycopy(this.array, 0, newArray, 0,this.array.length);
			for(int i=0;i<newArray.length;i++)
			{
				if(newArray[i]==null)
				{
					newArray[i]=new byte[chunkSize];
				}
			}
			this.array = newArray;
		}
	}
	
	protected final byte[] getChunk(int index)
	{
		return array[index];
	}
	
//	protected final byte[] _getBytes(int index,int len)
//	{
//		if(index>=offSet)
//		{
//			int loc=index-offSet;
//			return array[loc/chunkSize][loc%chunkSize];
//		}
//		Byte v=override.get(index);
//		if(v!=null)
//		{//写入层读取
//			return v;
//		}
//		return parent._getByte(index);
//	}
	
	protected final byte _getByte(int index)
	{
		if(index>=offSet)
		{
			int loc=index-offSet;
			return array[loc/chunkSize][loc%chunkSize];
		}
		Byte v=override.get(index);
		if(v!=null)
		{//写入层读取
			return v;
		}
		return parent._getByte(index);
	}
	
	protected final void _setByte(int index, int value) {
		if(readOnly)
		{
			throw new RuntimeException("readOnly");
		}
		if(index>=offSet)
		{//当前层写入
			int loc=index-offSet;
			array[loc/chunkSize][loc%chunkSize]=(byte)value;
		}else
		{//底层数据修改
			if(parent._getByte(index)==value)
			{
				return ;
			}
			override.put(index,(byte)value);
		}
	}
}
