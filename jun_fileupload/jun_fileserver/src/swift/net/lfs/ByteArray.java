package swift.net.lfs;

import java.io.EOFException;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;

public class ByteArray {
	/**
	 * The number of valid bytes in the buffer.
	 */
	public int position;
	/**
	 * The buffer where data is stored.
	 */
	public byte[] buf;
	private byte[] tempBuffer = new byte[8];

	/**
	 * Creates a new byte array output stream. The buffer capacity is
	 * initially 32 bytes, though its size increases if necessary.
	 */
	public ByteArray()
	{
		this(32);
	}

	/**
	 * Creates a new byte array output stream, with a buffer capacity of
	 * the specified size, in bytes.
	 *
	 * @param   size   the initial size.
	 * @exception  IllegalArgumentException if size is negative.
	 */
	public ByteArray(int size)
	{
		if (size < 0) {
			size = 32;
		}
		if (size > 0) {
			buf = new byte[size];
		}
	}

	public ByteArray(byte[] b)
	{
		buf = b;
	}

	/**
	 * Increases the capacity if necessary to ensure that it can hold
	 * at least the number of elements specified by the minimum
	 * capacity argument.
	 *
	 * @param minCapacity the desired minimum capacity
	 * @throws OutOfMemoryError if {@code minCapacity < 0}.  This is
	 * interpreted as a request for the unsatisfiably large capacity
	 * {@code (long) Integer.MAX_VALUE + (minCapacity - Integer.MAX_VALUE)}.
	 */
	protected void ensureCapacity(int minCapacity)
	{
		// overflow-conscious code
		if (minCapacity - buf.length > 0)
			grow(minCapacity, true);
	}

	protected void ensureCapacity(int minCapacity, boolean auto)
	{
		// overflow-conscious code
		if (minCapacity - buf.length > 0)
			grow(minCapacity, auto);
	}

	/**
	 * Increases the capacity to ensure that it can hold at least the
	 * number of elements specified by the minimum capacity argument.
	 *
	 * @param minCapacity the desired minimum capacity
	 */
	protected void grow(int minCapacity, boolean auto)
	{
		// overflow-conscious code
		int newCapacity = minCapacity;
		if (auto) {
			newCapacity = buf.length << 1;
			if (newCapacity < minCapacity) {
				newCapacity = minCapacity;
			}
		}
		buf = Arrays.copyOf(buf, newCapacity);
	}

	/**
	 * Writes the specified byte to this byte array output stream.
	 *
	 * @param   b   the byte to be written.
	 */
	public void write(int b)
	{
		ensureCapacity(position + 1);
		buf[position] = (byte) b;
		position += 1;
	}

	public void write(byte[] b)
	{
		write(b, 0, b.length);
	}

	/**
	 * Writes <code>len</code> bytes from the specified byte array
	 * starting at offset <code>off</code> to this byte array output stream.
	 *
	 * @param   b     the data.
	 * @param   off   the start offset in the data.
	 * @param   len   the number of bytes to write.
	 */
	public void write(byte b[], int off, int len)
	{
		if ((off < 0) || (off > b.length) || (len < 0) ||
				((off + len) - b.length > 0)) {
			throw new IndexOutOfBoundsException();
		}
		ensureCapacity(position + len);
		System.arraycopy(b, off, buf, position, len);
		position += len;
	}

	/**
	 * Writes the complete contents of this byte array output stream to
	 * the specified output stream argument, as if by calling the output
	 * stream's write method using <code>out.write(buf, 0, count)</code>.
	 *
	 * @param      out   the output stream to which to write the data.
	 * @exception  IOException  if an I/O error occurs.
	 */
	public void writeTo(OutputStream out) throws IOException
	{
		out.write(buf, 0, position);
	}

	/**
	 * Writes a <code>boolean</code> to the underlying output stream as
	 * a 1-byte value. The value <code>true</code> is written out as the
	 * value <code>(byte)1</code>; the value <code>false</code> is
	 * written out as the value <code>(byte)0</code>. If no exception is
	 * thrown, the counter <code>written</code> is incremented by
	 * <code>1</code>.
	 *
	 * @param      v   a <code>boolean</code> value to be written.
	 * @exception  IOException  if an I/O error occurs.
	 * @see        java.io.FilterOutputStream#out
	 */
	public final void writeBoolean(boolean v)
	{
		write(v ? 1 : 0);
	}

	/**
	 * Writes out a <code>byte</code> to the underlying output stream as
	 * a 1-byte value. If no exception is thrown, the counter
	 * <code>written</code> is incremented by <code>1</code>.
	 *
	 * @param      v   a <code>byte</code> value to be written.
	 * @exception  IOException  if an I/O error occurs.
	 * @see        java.io.FilterOutputStream#out
	 */
	public final void writeByte(int v)
	{
		write(v);
	}

	/**
	 * Writes a <code>short</code> to the underlying output stream as two
	 * bytes, high byte first. If no exception is thrown, the counter
	 * <code>written</code> is incremented by <code>2</code>.
	 *
	 * @param      v   a <code>short</code> to be written.
	 * @exception  IOException  if an I/O error occurs.
	 * @see        java.io.FilterOutputStream#out
	 */
	public final void writeShort(int v)
	{
		tempBuffer[0] = (byte)((v >>> 8) & 0xFF);
		tempBuffer[1] = (byte)((v >>> 0) & 0xFF);
		write(tempBuffer, 0, 2);
	}

	/**
	 * Writes a <code>char</code> to the underlying output stream as a
	 * 2-byte value, high byte first. If no exception is thrown, the
	 * counter <code>written</code> is incremented by <code>2</code>.
	 *
	 * @param      v   a <code>char</code> value to be written.
	 * @exception  IOException  if an I/O error occurs.
	 * @see        java.io.FilterOutputStream#out
	 */
	public final void writeChar(int v)
	{
		tempBuffer[0] = (byte)((v >>> 8) & 0xFF);
		tempBuffer[1] = (byte)((v >>> 0) & 0xFF);
		write(tempBuffer, 0, 2);
	}

	/**
	 * Writes an <code>int</code> to the underlying output stream as four
	 * bytes, high byte first. If no exception is thrown, the counter
	 * <code>written</code> is incremented by <code>4</code>.
	 *
	 * @param      v   an <code>int</code> to be written.
	 * @exception  IOException  if an I/O error occurs.
	 * @see        java.io.FilterOutputStream#out
	 */
	public final void writeInt(int v)
	{
		tempBuffer[0] = (byte)((v >>> 24) & 0xFF);
		tempBuffer[1] = (byte)((v >>> 16) & 0xFF);
		tempBuffer[2] = (byte)((v >>>  8) & 0xFF);
		tempBuffer[3] = (byte)((v >>>  0) & 0xFF);
		write(tempBuffer, 0, 4);
	}

	/**
	 * Writes a <code>long</code> to the underlying output stream as eight
	 * bytes, high byte first. In no exception is thrown, the counter
	 * <code>written</code> is incremented by <code>8</code>.
	 *
	 * @param      v   a <code>long</code> to be written.
	 * @exception  IOException  if an I/O error occurs.
	 * @see        java.io.FilterOutputStream#out
	 */
	public final void writeLong(long v)
	{
		tempBuffer[0] = (byte)(v >>> 56);
		tempBuffer[1] = (byte)(v >>> 48);
		tempBuffer[2] = (byte)(v >>> 40);
		tempBuffer[3] = (byte)(v >>> 32);
		tempBuffer[4] = (byte)(v >>> 24);
		tempBuffer[5] = (byte)(v >>> 16);
		tempBuffer[6] = (byte)(v >>>  8);
		tempBuffer[7] = (byte)(v >>>  0);
		write(tempBuffer, 0, 8);
	}

	/**
	 * Converts the float argument to an <code>int</code> using the
	 * <code>floatToIntBits</code> method in class <code>Float</code>,
	 * and then writes that <code>int</code> value to the underlying
	 * output stream as a 4-byte quantity, high byte first. If no
	 * exception is thrown, the counter <code>written</code> is
	 * incremented by <code>4</code>.
	 *
	 * @param      v   a <code>float</code> value to be written.
	 * @exception  IOException  if an I/O error occurs.
	 * @see        java.io.FilterOutputStream#out
	 * @see        java.lang.Float#floatToIntBits(float)
	 */
	public final void writeFloat(float v)
	{
		writeInt(Float.floatToIntBits(v));
	}

	/**
	 * Converts the double argument to a <code>long</code> using the
	 * <code>doubleToLongBits</code> method in class <code>Double</code>,
	 * and then writes that <code>long</code> value to the underlying
	 * output stream as an 8-byte quantity, high byte first. If no
	 * exception is thrown, the counter <code>written</code> is
	 * incremented by <code>8</code>.
	 *
	 * @param      v   a <code>double</code> value to be written.
	 * @exception  IOException  if an I/O error occurs.
	 * @see        java.io.FilterOutputStream#out
	 * @see        java.lang.Double#doubleToLongBits(double)
	 */
	public final void writeDouble(double v)
	{
		writeLong(Double.doubleToLongBits(v));
	}

	public final int read()
	{
		return buf[position++];
	}

	/**
	 * Reads some number of bytes from the contained input stream and
	 * stores them into the buffer array <code>b</code>. The number of
	 * bytes actually read is returned as an integer. This method blocks
	 * until input data is available, end of file is detected, or an
	 * exception is thrown.
	 *
	 * <p>If <code>b</code> is null, a <code>NullPointerException</code> is
	 * thrown. If the length of <code>b</code> is zero, then no bytes are
	 * read and <code>0</code> is returned; otherwise, there is an attempt
	 * to read at least one byte. If no byte is available because the
	 * stream is at end of file, the value <code>-1</code> is returned;
	 * otherwise, at least one byte is read and stored into <code>b</code>.
	 *
	 * <p>The first byte read is stored into element <code>b[0]</code>, the
	 * next one into <code>b[1]</code>, and so on. The number of bytes read
	 * is, at most, equal to the length of <code>b</code>. Let <code>k</code>
	 * be the number of bytes actually read; these bytes will be stored in
	 * elements <code>b[0]</code> through <code>b[k-1]</code>, leaving
	 * elements <code>b[k]</code> through <code>b[b.length-1]</code>
	 * unaffected.
	 *
	 * <p>The <code>read(b)</code> method has the same effect as:
	 * <blockquote><pre>
	 * read(b, 0, b.length)
	 * </pre></blockquote>
	 *
	 * @param      b   the buffer into which the data is read.
	 * @return     the total number of bytes read into the buffer, or
	 *             <code>-1</code> if there is no more data because the end
	 *             of the stream has been reached.
	 * @exception  IOException if the first byte cannot be read for any reason
	 * other than end of file, the stream has been closed and the underlying
	 * input stream does not support reading after close, or another I/O
	 * error occurs.
	 * @see        java.io.FilterInputStream#in
	 * @see        java.io.InputStream#read(byte[], int, int)
	 */
	public final int read(byte b[]) {
		return read(b, 0, b.length);
	}

	/**
	 * Reads up to <code>len</code> bytes of data from the contained
	 * input stream into an array of bytes.  An attempt is made to read
	 * as many as <code>len</code> bytes, but a smaller number may be read,
	 * possibly zero. The number of bytes actually read is returned as an
	 * integer.
	 *
	 * <p> This method blocks until input data is available, end of file is
	 * detected, or an exception is thrown.
	 *
	 * <p> If <code>len</code> is zero, then no bytes are read and
	 * <code>0</code> is returned; otherwise, there is an attempt to read at
	 * least one byte. If no byte is available because the stream is at end of
	 * file, the value <code>-1</code> is returned; otherwise, at least one
	 * byte is read and stored into <code>b</code>.
	 *
	 * <p> The first byte read is stored into element <code>b[off]</code>, the
	 * next one into <code>b[off+1]</code>, and so on. The number of bytes read
	 * is, at most, equal to <code>len</code>. Let <i>k</i> be the number of
	 * bytes actually read; these bytes will be stored in elements
	 * <code>b[off]</code> through <code>b[off+</code><i>k</i><code>-1]</code>,
	 * leaving elements <code>b[off+</code><i>k</i><code>]</code> through
	 * <code>b[off+len-1]</code> unaffected.
	 *
	 * <p> In every case, elements <code>b[0]</code> through
	 * <code>b[off]</code> and elements <code>b[off+len]</code> through
	 * <code>b[b.length-1]</code> are unaffected.
	 *
	 * @param      b     the buffer into which the data is read.
	 * @param off the start offset in the destination array <code>b</code>
	 * @param      len   the maximum number of bytes read.
	 * @return     the total number of bytes read into the buffer, or
	 *             <code>-1</code> if there is no more data because the end
	 *             of the stream has been reached.
	 * @exception  NullPointerException If <code>b</code> is <code>null</code>.
	 * @exception  IndexOutOfBoundsException If <code>off</code> is negative,
	 * <code>len</code> is negative, or <code>len</code> is greater than
	 * <code>b.length - off</code>
	 * @exception  IOException if the first byte cannot be read for any reason
	 * other than end of file, the stream has been closed and the underlying
	 * input stream does not support reading after close, or another I/O
	 * error occurs.
	 * @see        java.io.FilterInputStream#in
	 * @see        java.io.InputStream#read(byte[], int, int)
	 */
	public final int read(byte b[], int off, int len) {
		if (len > buf.length - position) {
			len = buf.length - position;
		}
		for (int i = 0; i < len; i++) {
			b[off + i] = buf[position++];
		}
		return len;
	}

	/**
	 * See the general contract of the <code>readBoolean</code>
	 * method of <code>DataInput</code>.
	 * <p>
	 * Bytes for this operation are read from the contained
	 * input stream.
	 *
	 * @return     the <code>boolean</code> value read.
	 * @exception  EOFException  if this input stream has reached the end.
	 * @exception  IOException   the stream has been closed and the contained
	 *             input stream does not support reading after close, or
	 *             another I/O error occurs.
	 * @see        java.io.FilterInputStream#in
	 */
	public final boolean readBoolean() {
		int ch = read();
		return (ch != 0);
	}

	/**
	 * See the general contract of the <code>readByte</code>
	 * method of <code>DataInput</code>.
	 * <p>
	 * Bytes
	 * for this operation are read from the contained
	 * input stream.
	 *
	 * @return     the next byte of this input stream as a signed 8-bit
	 *             <code>byte</code>.
	 * @exception  EOFException  if this input stream has reached the end.
	 * @exception  IOException   the stream has been closed and the contained
	 *             input stream does not support reading after close, or
	 *             another I/O error occurs.
	 * @see        java.io.FilterInputStream#in
	 */
	public final byte readByte() {
		int ch = read();
		return (byte)(ch);
	}

	/**
	 * See the general contract of the <code>readUnsignedByte</code>
	 * method of <code>DataInput</code>.
	 * <p>
	 * Bytes
	 * for this operation are read from the contained
	 * input stream.
	 *
	 * @return     the next byte of this input stream, interpreted as an
	 *             unsigned 8-bit number.
	 * @exception  EOFException  if this input stream has reached the end.
	 * @exception  IOException   the stream has been closed and the contained
	 *             input stream does not support reading after close, or
	 *             another I/O error occurs.
	 * @see         java.io.FilterInputStream#in
	 */
	public final int readUnsignedByte() {
		int ch = read() & 0xFF;
		return ch;
	}

	/**
	 * See the general contract of the <code>readShort</code>
	 * method of <code>DataInput</code>.
	 * <p>
	 * Bytes
	 * for this operation are read from the contained
	 * input stream.
	 *
	 * @return     the next two bytes of this input stream, interpreted as a
	 *             signed 16-bit number.
	 * @exception  EOFException  if this input stream reaches the end before
	 *               reading two bytes.
	 * @exception  IOException   the stream has been closed and the contained
	 *             input stream does not support reading after close, or
	 *             another I/O error occurs.
	 * @see        java.io.FilterInputStream#in
	 */
	public final short readShort() {
		int ch1 = read();
		int ch2 = read() & 0xFF;
		return (short)((ch1 << 8) + (ch2 << 0));
	}

	/**
	 * See the general contract of the <code>readUnsignedShort</code>
	 * method of <code>DataInput</code>.
	 * <p>
	 * Bytes
	 * for this operation are read from the contained
	 * input stream.
	 *
	 * @return     the next two bytes of this input stream, interpreted as an
	 *             unsigned 16-bit integer.
	 * @exception  EOFException  if this input stream reaches the end before
	 *             reading two bytes.
	 * @exception  IOException   the stream has been closed and the contained
	 *             input stream does not support reading after close, or
	 *             another I/O error occurs.
	 * @see        java.io.FilterInputStream#in
	 */
	public final int readUnsignedShort() {
		int ch1 = read() & 0xFF;
		int ch2 = read() & 0xFF;
		return (ch1 << 8) + (ch2 << 0);
	}

	/**
	 * See the general contract of the <code>readChar</code>
	 * method of <code>DataInput</code>.
	 * <p>
	 * Bytes
	 * for this operation are read from the contained
	 * input stream.
	 *
	 * @return     the next two bytes of this input stream, interpreted as a
	 *             <code>char</code>.
	 * @exception  EOFException  if this input stream reaches the end before
	 *               reading two bytes.
	 * @exception  IOException   the stream has been closed and the contained
	 *             input stream does not support reading after close, or
	 *             another I/O error occurs.
	 * @see        java.io.FilterInputStream#in
	 */
	public final char readChar() {
		int ch1 = read();
		int ch2 = read() & 0xFF;
		return (char)((ch1 << 8) + (ch2 << 0));
	}

	/**
	 * See the general contract of the <code>readInt</code>
	 * method of <code>DataInput</code>.
	 * <p>
	 * Bytes
	 * for this operation are read from the contained
	 * input stream.
	 *
	 * @return     the next four bytes of this input stream, interpreted as an
	 *             <code>int</code>.
	 * @exception  EOFException  if this input stream reaches the end before
	 *               reading four bytes.
	 * @exception  IOException   the stream has been closed and the contained
	 *             input stream does not support reading after close, or
	 *             another I/O error occurs.
	 * @see        java.io.FilterInputStream#in
	 */
	public final int readInt() {
		int ch1 = read();
		int ch2 = read() & 0xFF;
		int ch3 = read() & 0xFF;
		int ch4 = read() & 0xFF;
		return ((ch1 << 24) + (ch2 << 16) + (ch3 << 8) + (ch4 << 0));
	}

	/**
	 * See the general contract of the <code>readLong</code>
	 * method of <code>DataInput</code>.
	 * <p>
	 * Bytes
	 * for this operation are read from the contained
	 * input stream.
	 *
	 * @return     the next eight bytes of this input stream, interpreted as a
	 *             <code>long</code>.
	 * @exception  EOFException  if this input stream reaches the end before
	 *               reading eight bytes.
	 * @exception  IOException   the stream has been closed and the contained
	 *             input stream does not support reading after close, or
	 *             another I/O error occurs.
	 * @see        java.io.FilterInputStream#in
	 */
	public final long readLong() {
		read(tempBuffer, 0, 8);
		return (((long)tempBuffer[0] << 56) +
				((long)(tempBuffer[1] & 255) << 48) +
				((long)(tempBuffer[2] & 255) << 40) +
				((long)(tempBuffer[3] & 255) << 32) +
				((long)(tempBuffer[4] & 255) << 24) +
				((tempBuffer[5] & 255) << 16) +
				((tempBuffer[6] & 255) <<  8) +
				((tempBuffer[7] & 255) <<  0));
	}

	/**
	 * See the general contract of the <code>readFloat</code>
	 * method of <code>DataInput</code>.
	 * <p>
	 * Bytes
	 * for this operation are read from the contained
	 * input stream.
	 *
	 * @return     the next four bytes of this input stream, interpreted as a
	 *             <code>float</code>.
	 * @exception  EOFException  if this input stream reaches the end before
	 *               reading four bytes.
	 * @exception  IOException   the stream has been closed and the contained
	 *             input stream does not support reading after close, or
	 *             another I/O error occurs.
	 * @see        java.io.DataInputStream#readInt()
	 * @see        java.lang.Float#intBitsToFloat(int)
	 */
	public final float readFloat() {
		return Float.intBitsToFloat(readInt());
	}

	/**
	 * See the general contract of the <code>readDouble</code>
	 * method of <code>DataInput</code>.
	 * <p>
	 * Bytes
	 * for this operation are read from the contained
	 * input stream.
	 *
	 * @return     the next eight bytes of this input stream, interpreted as a
	 *             <code>double</code>.
	 * @exception  EOFException  if this input stream reaches the end before
	 *               reading eight bytes.
	 * @exception  IOException   the stream has been closed and the contained
	 *             input stream does not support reading after close, or
	 *             another I/O error occurs.
	 * @see        java.io.DataInputStream#readLong()
	 * @see        java.lang.Double#longBitsToDouble(long)
	 */
	public final double readDouble() {
		return Double.longBitsToDouble(readLong());
	}

	/**
	 * Creates a newly allocated byte array. Its size is the current
	 * size of this output stream and the valid contents of the buffer
	 * have been copied into it.
	 *
	 * @return  the current contents of this output stream, as a byte array.
	 * @see     java.io.ByteArrayOutputStream#size()
	 */
	public byte[] toByteArray()
	{
		return Arrays.copyOf(buf, position);
	}

	/**
	 * Converts the buffer's contents into a string decoding bytes using the
	 * platform's default character set. The length of the new <tt>String</tt>
	 * is a function of the character set, and hence may not be equal to the
	 * size of the buffer.
	 *
	 * <p> This method always replaces malformed-input and unmappable-character
	 * sequences with the default replacement string for the platform's
	 * default character set. The {@linkplain java.nio.charset.CharsetDecoder}
	 * class should be used when more control over the decoding process is
	 * required.
	 *
	 * @return String decoded from the buffer's contents.
	 * @since  JDK1.1
	 */
	public String toString()
	{
		return new String(buf, 0, position);
	}

	/**
	 * Converts the buffer's contents into a string by decoding the bytes using
	 * the named {@link java.nio.charset.Charset charset}. The length of the new
	 * <tt>String</tt> is a function of the charset, and hence may not be equal
	 * to the length of the byte array.
	 *
	 * <p> This method always replaces malformed-input and unmappable-character
	 * sequences with this charset's default replacement string. The {@link
	 * java.nio.charset.CharsetDecoder} class should be used when more control
	 * over the decoding process is required.
	 *
	 * @param      charsetName  the name of a supported
	 *             {@link java.nio.charset.Charset charset}
	 * @return     String decoded from the buffer's contents.
	 * @exception  UnsupportedEncodingException
	 *             If the named charset is not supported
	 * @since      JDK1.1
	 */
	public String toString(String charsetName) throws UnsupportedEncodingException
	{
		return new String(buf, 0, position, charsetName);
	}

	/**
	 * Returns the current size of the buffer.
	 *
	 * @return  the value of the <code>count</code> field, which is the number
	 *          of valid bytes in this output stream.
	 * @see     java.io.ByteArrayOutputStream#count
	 */
	public int size()
	{
		return position;
	}

	/**
	 * Resets the <code>count</code> field of this byte array output
	 * stream to zero, so that all currently accumulated output in the
	 * output stream is discarded. The output stream can be used again,
	 * reusing the already allocated buffer space.
	 *
	 * @see     java.io.ByteArrayInputStream#count
	 */
	public void reset()
	{
		position = 0;
	}

	public void clear()
	{
		buf = null;
		tempBuffer = null;
	}

}