package com.kulv.utils;

public class HexUtils
{
  private static final char[] hex = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };

  public static final byte[] fromHex(String b)
  {
    char[] data = b.toCharArray();
    int l = data.length;

    byte[] out = new byte[l >> 1];
    int i = 0;
    for (int j = 0; j < l; ) {
      int f = Character.digit(data[(j++)], 16) << 4;
      f |= Character.digit(data[(j++)], 16);
      out[i] = (byte)(f & 0xFF);
      i++;
    }

    return out;
  }

  public static final String toHex(byte[] b)
  {
    char[] buf = new char[b.length * 2];
    int j;
    int i = j = 0;
    for (; i < b.length; i++) {
      int k = b[i];
      buf[(j++)] = hex[(k >>> 4 & 0xF)];
      buf[(j++)] = hex[(k & 0xF)];
    }
    return new String(buf);
  }
}