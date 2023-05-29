package net.jueb.util4j.beta.tools.sbuffer;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.apache.commons.lang.math.RandomUtils;

public class SnapshotBuffer2 extends SnapshotBuffer2Base{
	
	public SnapshotBuffer2() {
		this(null,0);
	}

	public SnapshotBuffer2(SnapshotBuffer2Base parent, int offSet) {
		super(parent, offSet);
	}

	/**
	 * 产生一个快照
	 * @return
	 */
	public SnapshotBuffer2 snapshot()
	{
		readOnly=true;//当此对象建立了快照后,则为只读状态,不可修改
		return new SnapshotBuffer2(this,capacity());
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
			SnapshotBuffer2 array=new SnapshotBuffer2();
			String str="abc";
			byte[] t=str.getBytes();
			for(int i=0;i<t.length;i++)
			{
				array.writeByte(t[i]);
			}
			SnapshotBuffer2 array2=array.snapshot();
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
			SnapshotBuffer2 array=new SnapshotBuffer2();
			byte[] t="abc".getBytes();
			for(int i=0;i<t.length;i++)
			{
				array.writeByte(t[i]);
			}
			SnapshotBuffer2 array2=array.snapshot();
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
			SnapshotBuffer2 array=new SnapshotBuffer2();
			byte[] t="abc".getBytes();
			for(int i=0;i<t.length;i++)
			{
				array.writeByte(t[i]);
			}
			SnapshotBuffer2 array2=array.snapshot();
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
			SnapshotBuffer2 array=new SnapshotBuffer2();
			byte[] t="abc".getBytes();
			for(int i=0;i<t.length;i++)
			{
				array.writeByte(t[i]);
			}
			SnapshotBuffer2 array2=array.snapshot();
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
			SnapshotBuffer2 base=new SnapshotBuffer2();
			byte[] t1="abc".getBytes();
			byte[] t2="123abc".getBytes();
			base.writeBytes(t1);
			SnapshotBuffer2 extend=base.snapshot();
			extend.writeBytes(t2);
			byte[] data1=new byte[base.readableBytes()];
			base.readBytes(data1);
			byte[] data2=new byte[extend.readableBytes()];
			extend.readBytes(data2);
			System.out.println(new String(data1));
			System.out.println(new String(data2));
			SnapshotBuffer2 extend2=extend.snapshot();
			byte[] t3="123xyz".getBytes();
			extend2.writeBytes(t3);
			byte[] data3=new byte[extend2.readableBytes()];
			extend2.readBytes(data3);
			System.out.println(new String(data3));
		}
		
		public void test6()
		{
			SnapshotBuffer2 base=new SnapshotBuffer2();
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
			long t=System.currentTimeMillis();
			System.out.println("数据准备完毕"+mb10.length+"/"+mb20.length);
			ByteArrayOutputStream bos=new ByteArrayOutputStream();
			try {
				bos.write(mb10);
			} catch (IOException e) {
				e.printStackTrace();
			}
			long x1=System.currentTimeMillis()-t;
			System.out.println("bos 写入耗时"+x1);
			
			//写入耗时
			t=System.currentTimeMillis();
			base.writeBytes(mb10);
			long a1=System.currentTimeMillis()-t;
			t=System.currentTimeMillis();
			System.out.println("写入耗时:"+a1);
			//快照写入耗时
			SnapshotBuffer2 buff=base.snapshot();
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
