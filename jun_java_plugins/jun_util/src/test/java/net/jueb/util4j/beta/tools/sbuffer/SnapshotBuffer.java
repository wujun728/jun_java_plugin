package net.jueb.util4j.beta.tools.sbuffer;

import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.lang.math.RandomUtils;

public class SnapshotBuffer {
	
	private byte[] array=new byte[]{};
	private final SnapshotBuffer parent;//
	private final int offSet;//偏移索引
	private volatile boolean readOnly;
	private Map<Integer,Byte> override=new TreeMap<Integer, Byte>();//底层数据覆盖
	
	public SnapshotBuffer() {
		this(null,0);
	}
	public SnapshotBuffer(SnapshotBuffer parent,int offSet) {
		this.parent=parent;
		this.offSet=offSet;
	}
	
	/**
	 * 产生一个快照
	 * @return
	 */
	public SnapshotBuffer snapshot()
	{
		readOnly=true;//当此对象建立了快照后,则为只读状态,不可修改
		return new SnapshotBuffer(this,capacity());
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
	
	private void checkReadableBytesUnsafe(int minimumReadableBytes) {
		if (this.readIndex > this.writeIndex - minimumReadableBytes) {
			throw new IndexOutOfBoundsException(
					String.format("readerIndex(%d) + length(%d) exceeds writerIndex(%d): %s",
							new Object[] { Integer.valueOf(this.readIndex), Integer.valueOf(minimumReadableBytes),
									Integer.valueOf(this.writeIndex), this }));
		}
	}

	private void checkReadableBytes(int minimumReadableBytes) {
		if (minimumReadableBytes < 0) {
			throw new IllegalArgumentException("minimumReadableBytes: " + minimumReadableBytes + " (expected: >= 0)");
		} else {
			this.checkReadableBytesUnsafe(minimumReadableBytes);
		}
	}
	
	/**
	 * 容量(总长度)
	 * @return
	 */
	public int capacity(){
		int len=array.length;
		if(parent!=null)
		{
			len+=parent.capacity();
		}
		return len;
	}

	protected void ensureCapacity(int addBytes) {
		int newCapacity = getWriteIndex() + addBytes;
		if (newCapacity > this.capacity()) {
			int len=newCapacity * 3 / 2;
			len-=offSet;
			byte[] tmp = new byte[len];
//			System.arraycopy(this.array, 0, tmp, 0, getWriteIndex()-offSet);
			System.arraycopy(this.array, 0, tmp, 0,this.array.length);
			this.array = tmp;
		}
	}
	
	protected final byte _getByte(int index)
	{
		if(index>=offSet)
		{
			int loc=index-offSet;
			return array[loc];
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
			array[loc]=(byte)value;
		}else
		{//底层数据修改
			if(parent._getByte(index)==value)
			{
				return ;
			}
			override.put(index,(byte)value);
		}
	}
	
	public void getBytes(int index, byte[] dst, int dstIndex, int length) {
		checkDstIndex(index, length, dstIndex, dst.length);
		int x=index;
		for(int i=0;i<length;i++)
		{
			dst[dstIndex+i]=_getByte(x++);
		}
	}
	public void getBytes(int index,byte[] dst) {
		checkIndex(index,dst.length);
		getBytes(index, dst, 0, dst.length);
	}
	public void setBytes(int index, byte[] src) {
		setBytes(index,src, 0, src.length);
	}
	public void setBytes(int index, byte[] src, int srcIndex, int length) {
		checkSrcIndex(index, length, srcIndex, src.length);
		int x=index;
		for(int i=0;i<length;i++)
		{
			_setByte(x++, src[srcIndex+i]);
		}
	}

	private int readIndex;
	private int writeIndex;
	public int getReadIndex() {
		return readIndex;
	}
	public void setReadIndex(int readIndex) {
		this.readIndex = readIndex;
	}
	public int getWriteIndex() {
		return writeIndex;
	}
	public void setWriteIndex(int writeIndex) {
		this.writeIndex = writeIndex;
	}
	
	public int readableBytes() {
		return this.writeIndex - this.readIndex;
	}

	public int writableBytes() {
		return this.capacity() - this.writeIndex;
	}
	
	public void writeByte(int value)
	{
		ensureCapacity(1);
		_setByte(writeIndex, value);
		writeIndex++;
	}
	
	public byte readByte()
	{
		return _getByte(readIndex++);
	}
	
	public void writeBytes(byte[] src) {
		writeBytes(src, 0, src.length);
	}

	public void writeBytes(byte[] src, int srcIndex, int length) {
		ensureCapacity(length);
		setBytes(writeIndex, src, srcIndex, length);
		writeIndex += length;
	}
	
	public void readBytes(byte[] dst) {
		this.readBytes(dst, 0, dst.length);
	}

	public void readBytes(byte[] dst, int dstIndex, int length) {
		this.checkReadableBytes(length);
		this.getBytes(readIndex, dst, dstIndex, length);
		this.readIndex += length;
	}
	
	public static class Test{

		public void test1()
		{
			SnapshotBuffer array=new SnapshotBuffer();
			String str="abc";
			byte[] t=str.getBytes();
			for(int i=0;i<t.length;i++)
			{
				array.writeByte(t[i]);
			}
			SnapshotBuffer array2=array.snapshot();
			for(int i=0;i<t.length;i++)
			{
				array2.writeByte(t[i]);
			}
			int len=array2.readableBytes();
			byte[] b=new byte[len];
			for(int i=0;i<len;i++)
			{
				b[i]=array2.readByte();
			}
			System.out.println(new String(b));
		}
		
		public void test2()
		{
			SnapshotBuffer array=new SnapshotBuffer();
			byte[] t="abc".getBytes();
			for(int i=0;i<t.length;i++)
			{
				array.writeByte(t[i]);
			}
			SnapshotBuffer array2=array.snapshot();
			t="123".getBytes();
			for(int i=0;i<t.length;i++)
			{
				array2.writeByte(t[i]);
			}
			int len=array2.readableBytes();
			byte[] b=new byte[len];
			for(int i=0;i<len;i++)
			{
				b[i]=array2.readByte();
			}
			System.out.println(new String(b));
		}
		
		public void test3()
		{
			SnapshotBuffer array=new SnapshotBuffer();
			byte[] t="abc".getBytes();
			for(int i=0;i<t.length;i++)
			{
				array.writeByte(t[i]);
			}
			SnapshotBuffer array2=array.snapshot();
			t="abc123".getBytes();
			for(int i=0;i<t.length;i++)
			{
				array2.writeByte(t[i]);
			}
			int len=array2.readableBytes();
			byte[] b=new byte[len];
			for(int i=0;i<len;i++)
			{
				b[i]=array2.readByte();
			}
			System.out.println(new String(b));
		}
		
		public void test4()
		{
			SnapshotBuffer array=new SnapshotBuffer();
			byte[] t="abc".getBytes();
			for(int i=0;i<t.length;i++)
			{
				array.writeByte(t[i]);
			}
			SnapshotBuffer array2=array.snapshot();
			t="123456".getBytes();
			for(int i=0;i<t.length;i++)
			{
				array2.writeByte(t[i]);
			}
			int len=array2.readableBytes();
			byte[] b=new byte[len];
			for(int i=0;i<len;i++)
			{
				b[i]=array2.readByte();
			}
			System.out.println(new String(b));
		}
		
		public void test5()
		{
			SnapshotBuffer base=new SnapshotBuffer();
			byte[] t1="abc".getBytes();
			byte[] t2="123abc".getBytes();
			base.writeBytes(t1);
			SnapshotBuffer extend=base.snapshot();
			extend.writeBytes(t2);
			byte[] data1=new byte[base.readableBytes()];
			base.readBytes(data1);
			byte[] data2=new byte[extend.readableBytes()];
			extend.readBytes(data2);
			System.out.println(new String(data1));
			System.out.println(new String(data2));
			SnapshotBuffer extend2=extend.snapshot();
			byte[] t3="123xyz".getBytes();
			extend2.writeBytes(t3);
			byte[] data3=new byte[extend2.readableBytes()];
			extend2.readBytes(data3);
			System.out.println(new String(data3));
		}
		
		public void test6()
		{
			SnapshotBuffer base=new SnapshotBuffer();
			byte[] mb10=new byte[1024*1024*10];
			for(int i=0;i<mb10.length;i++)
			{
				mb10[i]=(byte) RandomUtils.nextInt(255);
			}
			byte[] mb20=new byte[1024*1024*10];
			for(int i=0;i<mb20.length;i++)
			{
				mb20[i]=(byte) RandomUtils.nextInt(255);
			}
			System.out.println("数据准备完毕"+mb10.length+"/"+mb20.length);
			//写入耗时
			long t=System.currentTimeMillis();
			base.writeBytes(mb10);
			long a1=System.currentTimeMillis()-t;
			t=System.currentTimeMillis();
			System.out.println("写入耗时:"+a1);
			//快照写入耗时
			SnapshotBuffer buff=base.snapshot();
			buff.writeBytes(mb20);
			long b1=System.currentTimeMillis()-t;
			t=System.currentTimeMillis();
			System.out.println("快照写入耗时:"+b1);
			//读取耗时
			byte[] data1=new byte[base.readableBytes()];
			base.readBytes(data1);
			long a2=System.currentTimeMillis()-t;
			t=System.currentTimeMillis();
			System.out.println("读取耗时:"+a2);
			//快照读取耗时
			byte[] data2=new byte[buff.readableBytes()];
			buff.readBytes(data2);
			long b2=System.currentTimeMillis()-t;
			System.out.println("快照读取耗时:"+b2);
			System.out.println(a1+","+a2+","+b1+","+b2);
		}
	}
	
	public static void main(String[] args) {
		new Test().test6();
		new Test().test6();
	}
}
