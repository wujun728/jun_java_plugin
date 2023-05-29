package com.jun.plugin.util4j.bytesStream.bytes;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Formatter;

public class HexUtil {

	 	private static final String[] BYTE2HEX_PAD = new String[256];
	 	private static final String[] BYTE2HEX_NOPAD = new String[256];
	
	 	public static final String EMPTY_STRING = "";
		public static final String NEWLINE;
        private static final char[] BYTE2CHAR = new char[256];
        private static final char[] HEXDUMP_TABLE = new char[256 * 4];
        private static final String[] HEXPADDING = new String[16];
        private static final String[] HEXDUMP_ROWPREFIXES = new String[65536 >>> 4];
        private static final String[] BYTE2HEX = new String[256];
        private static final String[] BYTEPADDING = new String[16];

        static {
        	String newLine;
            Formatter formatter = new Formatter();
            try {
                newLine = formatter.format("%n").toString();
            } catch (Exception e) {
                newLine = "\n";
            } finally {
                formatter.close();
            }
            NEWLINE = newLine;
            final char[] DIGITS = "0123456789abcdef".toCharArray();
            for (int i = 0; i < 256; i ++) {
                HEXDUMP_TABLE[ i << 1     ] = DIGITS[i >>> 4 & 0x0F];
                HEXDUMP_TABLE[(i << 1) + 1] = DIGITS[i       & 0x0F];
            }
            
            int i;

         // Generate the lookup table that converts a byte into a 2-digit hexadecimal integer.
          for (i = 0; i < 10; i ++) {
              StringBuilder buf = new StringBuilder(2);
              buf.append('0');
              buf.append(i);
              BYTE2HEX_PAD[i] = buf.toString();
              BYTE2HEX_NOPAD[i] = String.valueOf(i);
          }
          for (; i < 16; i ++) {
              StringBuilder buf = new StringBuilder(2);
              char c = (char) ('a' + i - 10);
              buf.append('0');
              buf.append(c);
              BYTE2HEX_PAD[i] = buf.toString();
              BYTE2HEX_NOPAD[i] = String.valueOf(c);
          }
          for (; i < BYTE2HEX_PAD.length; i ++) {
              StringBuilder buf = new StringBuilder(2);
              buf.append(Integer.toHexString(i));
              String str = buf.toString();
              BYTE2HEX_PAD[i] = str;
              BYTE2HEX_NOPAD[i] = str;
          }
            
            // Generate the lookup table for hex dump paddings
            for (i = 0; i < HEXPADDING.length; i ++) {
                int padding = HEXPADDING.length - i;
                StringBuilder buf = new StringBuilder(padding * 3);
                for (int j = 0; j < padding; j ++) {
                    buf.append("   ");
                }
                HEXPADDING[i] = buf.toString();
            }
            // Generate the lookup table for the start-offset header in each row (up to 64KiB).
            for (i = 0; i < HEXDUMP_ROWPREFIXES.length; i ++) {
                StringBuilder buf = new StringBuilder(12);
                buf.append(NEWLINE);
                buf.append(Long.toHexString(i << 4 & 0xFFFFFFFFL | 0x100000000L));
                buf.setCharAt(buf.length() - 9, '|');
                buf.append('|');
                HEXDUMP_ROWPREFIXES[i] = buf.toString();
            }

            // Generate the lookup table for byte-to-hex-dump conversion
            for (i = 0; i < BYTE2HEX.length; i ++) {
                BYTE2HEX[i] = ' ' + byteToHexStringPadded(i);
            }

            // Generate the lookup table for byte dump paddings
            for (i = 0; i < BYTEPADDING.length; i ++) {
                int padding = BYTEPADDING.length - i;
                StringBuilder buf = new StringBuilder(padding);
                for (int j = 0; j < padding; j ++) {
                    buf.append(' ');
                }
                BYTEPADDING[i] = buf.toString();
            }

            // Generate the lookup table for byte-to-char conversion
            for (i = 0; i < BYTE2CHAR.length; i ++) {
                if (i <= 0x1f || i >= 0x7f) {
                    BYTE2CHAR[i] = '.';
                } else {
                    BYTE2CHAR[i] = (char) i;
                }
            }
            
        }
       
        /**
         * 获取无符号byte值
         * @param value
         * @return
         */
        public static short getUnsignedByte(int value) {
            return (short) (value & 0xFF);
        }
        public static String byteToHexString(int value) {
            return BYTE2HEX_NOPAD[value & 0xff];
        }
        public static String byteToHexStringPadded(int value) {
            return BYTE2HEX_PAD[value & 0xff];
        }
        
        /**
         * 返回ASCII字符
         * @param chr
         * @return
         */
		public static final char byteToChar(byte chr)
		{
			// return (chr > 0x20 && chr < 0x7F) ? (char) chr : '.';
			return BYTE2CHAR[getUnsignedByte(chr)];
		}
		
		public static String hexDump(byte[] array) {
			return hexDump(array, 0, array.length);
		}
		/**
         * 格式化为16进制字符串
         * @param array
         * @param offset
         * @param length
         * @return
         */
        public static String hexDump(byte[] array, int offset, int length) {
            if (length < 0) {
              throw new IllegalArgumentException("length: " + length);
            }
            if (length == 0) {
                return EMPTY_STRING;
            }
            int endIndex = offset + length;
            char[] buf = new char[length << 1];

            int srcIdx = offset;
            int dstIdx = 0;
            for (; srcIdx < endIndex; srcIdx ++, dstIdx += 2) {
                System.arraycopy(
                    HEXDUMP_TABLE, (array[srcIdx] & 0xFF) << 1,
                    buf, dstIdx, 2);
            }
            return new String(buf);
        }

        public static String prettyHexDump(byte[] buffer) {
        	return prettyHexDump(buffer,0, buffer.length);
        }
        
        /**
         * <pre>
         * 格式化为如下样式
		 *         +-------------------------------------------------+
		 *         |  0  1  2  3  4  5  6  7  8  9  a  b  c  d  e  f |
		 *+--------+-------------------------------------------------+----------------+
		 *|00000000| 4d 49 47 66 4d 41 30 47 43 53 71 47 53 49 62 33 |MIGfMA0GCSqGSIb3|
		 *|00000010| 44 51 45 42 41 51 55 41 41 34 47 4e 41 44 43 42 |DQEBAQUAA4GNADCB|
		 *|00000020| 69 51 4b 42 67 51 43 39 32 55 54 4f 61 51 48 55 |iQKBgQC92UTOaQHU|
		 *|00000030| 6d 4c 4f 2f 31 2b 73 43 6b 70 66 76 52 47 68 6d |mLO/1+sCkpfvRGhm|
		 *|00000040| 71 38 70 30 66 33 5a 79 42 71 6b 41 72 69 4d 6b |q8p0f3ZyBqkAriMk|
		 *|000000d0| 31 51 49 44 41 51 41 42                         |1QIDAQAB        |
		 *+--------+-------------------------------------------------+----------------+
		 * </pre>
         * @param buffer
         * @param offset
         * @param length
         * @return
         */
        public static String prettyHexDump(byte[] buffer, int offset, int length) {
        	 if (length < 0) {
                 throw new IllegalArgumentException("length: " + length);
               }
		    if (length == 0) {
			      return EMPTY_STRING;
			    } else {
			        int rows = length / 16 + (length % 15 == 0? 0 : 1) + 4;
			        StringBuilder buf = new StringBuilder(rows * 80);
			        appendPrettyHexDump(buf, buffer, offset, length);
			        return buf.toString();
			    }
			}
		private static void appendPrettyHexDump(StringBuilder dump,byte[] array, int offset, int length) {
            if (isOutOfBounds(offset, length,array.length)) {
                throw new IndexOutOfBoundsException(
                        "expected: " + "0 <= offset(" + offset + ") <= offset + length(" + length
                                                    + ") <= " + "buf.capacity(" + array.length + ')');
            }
            if (length == 0) {
                return;
            }
            dump.append(
                              "         +-------------------------------------------------+" +
                    NEWLINE + "         |  0  1  2  3  4  5  6  7  8  9  a  b  c  d  e  f |" +
                    NEWLINE + "+--------+-------------------------------------------------+----------------+");

            final int startIndex = offset;
            final int fullRows = length >>> 4;
            final int remainder = length & 0xF;

            // Dump the rows which have 16 bytes.
            for (int row = 0; row < fullRows; row ++) {
                int rowStartIndex = (row << 4) + startIndex;

                // Per-row prefix.
                appendHexDumpRowPrefix(dump, row, rowStartIndex);

                // Hex dump
                int rowEndIndex = rowStartIndex + 16;
                for (int j = rowStartIndex; j < rowEndIndex; j ++) {
                    dump.append(BYTE2HEX[getUnsignedByte(array[j])]);
                }
                dump.append(" |");

                // ASCII dump
                for (int j = rowStartIndex; j < rowEndIndex; j ++) {
                    dump.append(BYTE2CHAR[getUnsignedByte(array[j])]);
                }
                dump.append('|');
            }

            // Dump the last row which has less than 16 bytes.
            if (remainder != 0) {
                int rowStartIndex = (fullRows << 4) + startIndex;
                appendHexDumpRowPrefix(dump, fullRows, rowStartIndex);

                // Hex dump
                int rowEndIndex = rowStartIndex + remainder;
                for (int j = rowStartIndex; j < rowEndIndex; j ++) {
                    dump.append(BYTE2HEX[getUnsignedByte(array[j])]);
                }
                dump.append(HEXPADDING[remainder]);
                dump.append(" |");

                // Ascii dump
                for (int j = rowStartIndex; j < rowEndIndex; j ++) {
                    dump.append(BYTE2CHAR[getUnsignedByte(array[j])]);
                }
                dump.append(BYTEPADDING[remainder]);
                dump.append('|');
            }

            dump.append(NEWLINE +
                        "+--------+-------------------------------------------------+----------------+");
        }

        private static void appendHexDumpRowPrefix(StringBuilder dump, int row, int rowStartIndex) {
            if (row < HEXDUMP_ROWPREFIXES.length) {
                dump.append(HEXDUMP_ROWPREFIXES[row]);
            } else {
                dump.append(NEWLINE);
                dump.append(Long.toHexString(rowStartIndex & 0xFFFFFFFFL | 0x100000000L));
                dump.setCharAt(dump.length() - 9, '|');
                dump.append('|');
            }
        }
        
        public static boolean isOutOfBounds(int index, int length, int capacity) {
            return (index | length | (index + length) | (capacity - (index + length))) < 0;
        }
       
        public static String SimplePrettyHexDump(byte bytes[])
        {
        	return SimplePrettyHexDump(bytes, 0, bytes.length);
        }
        /**
    	 * 将字节转换成十六进制字符串
    	 * @param bytes 数据
    	 * @param offset 起始位置
    	 * @param length 从起始位置开始的长度
    	 * @return
    	 */
    	public static String SimplePrettyHexDump(byte bytes[], int offset, int length)
    	{
    		ByteArrayOutputStream bos = new ByteArrayOutputStream();
    		PrintStream ps = new PrintStream(bos);
    		int rows = length / 16;//打印的行数
    		int ac = length % 16;//剩余的字节数
    		for (int i = 0; i < rows; ++i)
    			ps.printf("%02X %02X %02X %02X %02X %02X %02X %02X %02X %02X %02X %02X %02X %02X %02X %02X %c%c%c%c%c%c%c%c%c%c%c%c%c%c%c%c\n", //
    					bytes[offset + (16 * i) + 0], //
    					bytes[offset + (16 * i) + 1], //
    					bytes[offset + (16 * i) + 2], //
    					bytes[offset + (16 * i) + 3], //
    					bytes[offset + (16 * i) + 4], //
    					bytes[offset + (16 * i) + 5], //
    					bytes[offset + (16 * i) + 6], //
    					bytes[offset + (16 * i) + 7], //
    					bytes[offset + (16 * i) + 8], //
    					bytes[offset + (16 * i) + 9], //
    					bytes[offset + (16 * i) + 10], //
    					bytes[offset + (16 * i) + 11], //
    					bytes[offset + (16 * i) + 12], //
    					bytes[offset + (16 * i) + 13], //
    					bytes[offset + (16 * i) + 14], //
    					bytes[offset + (16 * i) + 15], //
    					byteToChar(bytes[offset + (16 * i) + 0]), //
    					byteToChar(bytes[offset + (16 * i) + 1]), //
    					byteToChar(bytes[offset + (16 * i) + 2]), //
    					byteToChar(bytes[offset + (16 * i) + 3]), //
    					byteToChar(bytes[offset + (16 * i) + 4]), //
    					byteToChar(bytes[offset + (16 * i) + 5]), //
    					byteToChar(bytes[offset + (16 * i) + 6]), //
    					byteToChar(bytes[offset + (16 * i) + 7]), //
    					byteToChar(bytes[offset + (16 * i) + 8]), //
    					byteToChar(bytes[offset + (16 * i) + 9]), //
    					byteToChar(bytes[offset + (16 * i) + 10]), //
    					byteToChar(bytes[offset + (16 * i) + 11]), //
    					byteToChar(bytes[offset + (16 * i) + 12]), //
    					byteToChar(bytes[offset + (16 * i) + 13]), //
    					byteToChar(bytes[offset + (16 * i) + 14]), //
    					byteToChar(bytes[offset + (16 * i) + 15]));
    		for (int i = 0; i < ac; i++)
    			ps.printf("%02X ", bytes[offset + rows * 16 + i]);
    		for (int i = 0; ac > 0 && i < 16 - ac; i++)
    			ps.printf("%s", "   ");
    		for (int i = 0; i < ac; i++)
    			ps.printf("%c", byteToChar(bytes[offset + rows * 16 + i]));
    		return bos.toString();
    	}
    	
    	public static void main(String[] args) {
        	String str="MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQC92UTOaQHUmLO/1+sCkpfvRGhmq8p0f3ZyBqkAriMkrG73p+To7Q2+jmTZDHLZERbfpUFBpA/so4qLKXYCXdOsTacCXjpu3lnnfqwBDid+vt++0dKoXpXbO1GBQ1eXdvV0SdcyIEkCIn+U8/0+hi5C8jowAg3gbpk7qe4MIDAN1QIDAQAB";
        	byte[] data=str.getBytes();
        	System.out.println(hexDump(str.getBytes(),0, data.length));
        	System.out.println(SimplePrettyHexDump(data, 0, data.length));
        	
        	String str2="123sd12s1nisan啊大大我的期望的";
        	System.out.println(SimplePrettyHexDump(str2.getBytes()));
        	System.out.println(prettyHexDump(str2.getBytes()));
		}
}