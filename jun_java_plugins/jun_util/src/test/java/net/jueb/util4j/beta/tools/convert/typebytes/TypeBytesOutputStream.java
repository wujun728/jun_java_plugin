package net.jueb.util4j.beta.tools.convert.typebytes;
import java.io.ByteArrayOutputStream;
import java.io.DataOutput;
import java.io.IOException;
import java.io.UTFDataFormatException;
/**
 * 采用无符号左移方式写入位数值
 * @author Administrator
 *
 */
public final class TypeBytesOutputStream extends ByteArrayOutputStream implements DataOutput {
    
	/**
	 * 是否为小端模式
	 * 默认false是大端模式
	 */
	private boolean isLittleEndian=false;
	

    /**
     * bytearr is initialized on demand by writeUTF
     */
    private byte[] bytearr = null;

    public TypeBytesOutputStream() {
    }
    public TypeBytesOutputStream(boolean isLittleEndian) {
    	this.isLittleEndian=isLittleEndian;
    }
    
    public boolean isLittleEndian()
    {
    	return this.isLittleEndian;
    }
    
    /**
     *将一个 boolean 值以 1-byte 值形式写入基础输出流。
     *值 true 以值 (byte)1 的形式被写出；值 false 以值 (byte)0 的形式被写出。
     *如果没有抛出异常，则计数器 written 增加 1。 
     */
    @Override
	public final void writeBoolean(boolean v) throws IOException {
        write(v ? 1 : 0);
    }

    /**
     * 将一个 byte 值以 1-byte 值形式写出到基础输出流中。如果没有抛出异常，则计数器 written 增加 1。
     */
    @Override
	public final void writeByte(int v) throws IOException {
        write(v);
    }

    /**
     * 将一个 short 值以 2-byte 值形式写入基础输出流中。
     * 如果没有抛出异常，则计数器 written 增加 2。
     */
    @Override
	public final void writeShort(int v) throws IOException {
       if(isLittleEndian)
       {
    	   write((v >>> 0) & 0xFF);
	       write((v >>> 8) & 0xFF);
       }else 
       {
    	   write((v >>> 8) & 0xFF);
	       write((v >>> 0) & 0xFF);  
       }
    }

    /**
     * 将一个 char 值以 2-byte 值形式写入基础输出流中。
     * 如果没有抛出异常，则计数器 written 增加 2。 
     */
    @Override
	public final void writeChar(int v) throws IOException {
    	if(isLittleEndian)
        {
    	   write((v >>> 0) & 0xFF);
   	       write((v >>> 8) & 0xFF);
        }else 
        {
           write((v >>> 8) & 0xFF);
   	       write((v >>> 0) & 0xFF);
        }
    }

    /**
    * 将一个 int 值以 4-byte 值形式写入基础输出流中。如果没有抛出异常，则计数器 written 增加 4。 
    */
    @Override
	public final void writeInt(int v) throws IOException {
    	if(isLittleEndian)
        {
    	   write((v >>> 0) & 0xFF);
   	       write((v >>> 8) & 0xFF);
   	       write((v >>> 16) & 0xFF);
   	       write((v >>> 24) & 0xFF);
        }else 
        {
           write((v >>> 24) & 0xFF);
           write((v >>> 16) & 0xFF);
       	   write((v >>> 8) & 0xFF);
   	       write((v >>> 0) & 0xFF);
        }
    }

    /**
     * 将一个 long 值以 8-byte 值形式写入基础输出流中。如果没有抛出异常，则计数器 written 增加 8。 
     */
    @Override
	public final void writeLong(long v) throws IOException {
    	byte writeBuffer[] = new byte[8];
    	if(isLittleEndian)
        {
    		writeBuffer[0] = (byte)(v >>> 0);
            writeBuffer[1] = (byte)(v >>> 8);
            writeBuffer[2] = (byte)(v >>> 16);
            writeBuffer[3] = (byte)(v >>> 24);
            writeBuffer[4] = (byte)(v >>> 32);
            writeBuffer[5] = (byte)(v >>> 40);
            writeBuffer[6] = (byte)(v >>> 48);
            writeBuffer[7] = (byte)(v >>> 56);
        }else
        {
        	writeBuffer[0] = (byte)(v >>> 56);
            writeBuffer[1] = (byte)(v >>> 48);
            writeBuffer[2] = (byte)(v >>> 40);
            writeBuffer[3] = (byte)(v >>> 32);
            writeBuffer[4] = (byte)(v >>> 24);
            writeBuffer[5] = (byte)(v >>> 16);
            writeBuffer[6] = (byte)(v >>>  8);
            writeBuffer[7] = (byte)(v >>>  0);
        }
        write(writeBuffer, 0, 8);
    }

    /**
     * 使用 Float 类中的 floatToIntBits 方法将 float 参数转换为一个 int 值，
     * 然后将该 int 值以 4-byte 值形式写入基础输出流中。
     * 如果没有抛出异常，则计数器 written 增加 4。 
     */
    @Override
	public final void writeFloat(float v) throws IOException {
        writeInt(Float.floatToIntBits(v));
    }

    /**
     * 使用 Double 类中的 doubleToLongBits 方法将 double 参数转换为一个 long 值，
     * 然后将该 long 值以 8-byte 值形式写入基础输出流中，
     * 如果没有抛出异常，则计数器 written 增加 8。
     */
    @Override
	public final void writeDouble(double v) throws IOException {
        writeLong(Double.doubleToLongBits(v));
    }

    /**
     * 将字符串按字节顺序写出到基础输出流中。
     */
    @Override
	public final void writeBytes(String s) throws IOException {
        int len = s.length();
        for (int i = 0 ; i < len ; i++) {
           write((byte)s.charAt(i));
        }
    }

    /**
     * 将字符串按字符顺序写入基础输出流。
     */
    @Override
	public final void writeChars(String s) throws IOException {
        int len = s.length();
        for (int i = 0 ; i < len ; i++) {
            int v = s.charAt(i);
            write((v >>> 8) & 0xFF);
            write((v >>> 0) & 0xFF);
        }
    }

    /**
     * 以与机器无关方式使用 UTF-8 修改版编码将一个字符串写入基础输出流。 
	 * 首先，通过 writeShort 方法将两个字节写入输出流，表示后跟的字节数。
	 * 该值是实际写出的字节数，不是字符串的长度。
	 * 根据此长度，使用字符的 UTF-8 修改版编码按顺序输出字符串的每个字符。
	 * 如果没有抛出异常，则计数器 written 增加写入输出流的字节总数。
	 * 该值至少是 2 加 str 的长度，最多是 2 加 str 的三倍长度。 
     */
    @Override
	public final void writeUTF(String str) throws IOException {
        writeUTF(str, this);
    }

    
    static int writeUTF(String str, DataOutput out) throws IOException {
        int strlen = str.length();
        int utflen = 0;
        int c, count = 0;

        /* use charAt instead of copying String to char array */
        for (int i = 0; i < strlen; i++) {
            c = str.charAt(i);
            if ((c >= 0x0001) && (c <= 0x007F)) {
                utflen++;
            } else if (c > 0x07FF) {
                utflen += 3;
            } else {
                utflen += 2;
            }
        }

        if (utflen > 65535)
            throw new UTFDataFormatException(
                "encoded string too long: " + utflen + " bytes");

        byte[] bytearr = null;
        if (out instanceof TypeBytesOutputStream) {
            TypeBytesOutputStream dos = (TypeBytesOutputStream)out;
            if(dos.bytearr == null || (dos.bytearr.length < (utflen+2)))
                dos.bytearr = new byte[(utflen*2) + 2];
            bytearr = dos.bytearr;
        } else {
            bytearr = new byte[utflen+2];
        }

        bytearr[count++] = (byte) ((utflen >>> 8) & 0xFF);
        bytearr[count++] = (byte) ((utflen >>> 0) & 0xFF);

        int i=0;
        for (i=0; i<strlen; i++) {
           c = str.charAt(i);
           if (!((c >= 0x0001) && (c <= 0x007F))) break;
           bytearr[count++] = (byte) c;
        }

        for (;i < strlen; i++){
            c = str.charAt(i);
            if ((c >= 0x0001) && (c <= 0x007F)) {
                bytearr[count++] = (byte) c;

            } else if (c > 0x07FF) {
                bytearr[count++] = (byte) (0xE0 | ((c >> 12) & 0x0F));
                bytearr[count++] = (byte) (0x80 | ((c >>  6) & 0x3F));
                bytearr[count++] = (byte) (0x80 | ((c >>  0) & 0x3F));
            } else {
                bytearr[count++] = (byte) (0xC0 | ((c >>  6) & 0x1F));
                bytearr[count++] = (byte) (0x80 | ((c >>  0) & 0x3F));
            }
        }
        out.write(bytearr, 0, utflen+2);
        return utflen + 2;
    }
}
