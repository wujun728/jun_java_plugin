package com.jun.plugin.json.jackson3.jsonxml;

import net.jpountz.lz4.*;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.time.StopWatch;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * Created by dzy on 2018/9/3
 * lz4:https://github.com/lz4/lz4-java
 * ref : https://blog.csdn.net/hwh22/article/details/78393385
 * lz4 特点：压缩速度特别快，压缩时间大约是zlib的1/3 , 但压缩率会有大约 50%的降低
 */
public class Lz4Util {

  /**
   * @param srcByte 原始数据
   * @return 压缩后的数据
   */
  public static byte[] compressedByte(byte[] srcByte) {
    LZ4Factory factory = LZ4Factory.fastestInstance();
    LZ4Compressor compressor = factory.fastCompressor();
    return compressor.compress(srcByte);
  }

  /**
   * @param compressorByte 压缩后的数据
   * @param srcLength      压缩前的数据长度
   * @return
   */
  public static byte[] decompressorByte(byte[] compressorByte, int srcLength) {
    LZ4Factory factory = LZ4Factory.fastestInstance();
    LZ4FastDecompressor decompressor = factory.fastDecompressor();
    return decompressor.decompress(compressorByte, srcLength);
  }

  /**
   * @param srcByte
   * @param blockSize 一次压缩的大小 取值范围 64 字节-32M之间
   * @return
   * @throws IOException
   */
  public static byte[] lz4Compress(byte[] srcByte, int blockSize) throws IOException {
    LZ4Factory factory = LZ4Factory.fastestInstance();
    ByteArrayOutputStream byteOutput = new ByteArrayOutputStream();
    LZ4Compressor compressor = factory.fastCompressor();
    LZ4BlockOutputStream compressedOutput = new LZ4BlockOutputStream(byteOutput, blockSize, compressor);
    compressedOutput.write(srcByte);
    compressedOutput.close();
    return byteOutput.toByteArray();
  }

  /**
   * @param compressorByte
   * @param blockSize      一次压缩的大小 取值范围 64 字节-32M之间
   * @return
   * @throws IOException
   */
  public static byte[] lz4Decompress(byte[] compressorByte, int blockSize) throws IOException {
    LZ4Factory factory = LZ4Factory.fastestInstance();
    ByteArrayOutputStream baos = new ByteArrayOutputStream(blockSize);
    LZ4FastDecompressor decompresser = factory.fastDecompressor();
    LZ4BlockInputStream lzis = new LZ4BlockInputStream(new ByteArrayInputStream(compressorByte), decompresser);
    int count;
    byte[] buffer = new byte[blockSize];
    while ((count = lzis.read(buffer)) != -1) {
      baos.write(buffer, 0, count);
    }
    lzis.close();
    return baos.toByteArray();
  }

  /**
   * File  to byte[]
   *
   * @param filePath
   * @return
   * @throws IOException
   */
  public static byte[] returnFileByte(String filePath) throws IOException {
    FileInputStream fileInputStream = new FileInputStream(new File(filePath));
    FileChannel channel = fileInputStream.getChannel();
    ByteBuffer byteBuffer = ByteBuffer.allocate((int) channel.size());
    channel.read(byteBuffer);
    return byteBuffer.array();
  }

  /**
   * createFile
   *
   * @param fileByte
   * @param filePath
   */
  public static void createFile(byte[] fileByte, String filePath) {
    BufferedOutputStream bufferedOutputStream;
    FileOutputStream fileOutputStream;
    File file = new File(filePath);
    try {
      fileOutputStream = new FileOutputStream(file);
      bufferedOutputStream = new BufferedOutputStream(fileOutputStream);
      bufferedOutputStream.write(fileByte);
      fileOutputStream.close();
      bufferedOutputStream.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  //test code
  private static void test1(){
    StopWatch sw = new StopWatch();
    try {
      byte[] zipDats = null;
      sw.start();
      byte[] dats = FileUtils.readFileToByteArray(new File("v:\\allinone2.xml"));
      for (int i = 0; i <100 ; i++) {
        zipDats = lz4Compress(dats, 1024 * 1024);
      }
      sw.stop();
      System.out.println("lz4 used Time:"+sw.getTime()); //compress 100 times, used time : 570 ms ,size:76749
//      FileUtils.writeByteArrayToFile(new File("v:\\allinone2.lz4"),zipDats);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public static void main(String[] args) {
    test1();
  }
}
