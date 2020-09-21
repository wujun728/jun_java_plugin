/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.redis.proxy.common.utils;

/**
 *
 * @author zhanggaofeng
 */
public class CompressUtil {

        private static final int MAGIC_NUMBER = 0x1F;
        private static final int COMPRESS_FLAG = 0x20;

        /**
         * 压缩
         *
         * @param arr
         * @param compressShold 压缩阀值
         * @return
         */
        public static byte[] compress(byte[] arr, int compressShold) {
                byte[] newByte = null;
                if (arr.length < compressShold) {
                        newByte = new byte[arr.length + 1];
                        newByte[0] = MAGIC_NUMBER;
                        System.arraycopy(arr, 0, newByte, 1, arr.length);
                        return newByte;
                }
                // 压缩
                byte[] compressBytes = QuickLZ.compress(arr, 1); // 把要压缩的部分用quicklz压缩
                newByte = new byte[compressBytes.length + 1];
                newByte[0] = MAGIC_NUMBER | COMPRESS_FLAG; // 占位，同时写入压缩位
                System.arraycopy(compressBytes, 0, newByte, 1, compressBytes.length);
                return newByte;
        }

        /**
         * 解压
         *
         * @param arr
         * @return
         */
        public static byte[] decompress(byte[] arr) {
                byte b = arr[0];
                if ((b & MAGIC_NUMBER) != MAGIC_NUMBER) {
                        throw new IllegalArgumentException("要解码的字节流没有使用编码的.");
                }
                byte[] compressBytes = null;
                byte[] result = null;
                if ((b & COMPRESS_FLAG) == COMPRESS_FLAG) { // 使用了压缩
                        compressBytes = new byte[arr.length - 1];
                        System.arraycopy(arr, 1, compressBytes, 0, compressBytes.length);
                        result = QuickLZ.decompress(compressBytes);
                }
                if (result == null) {
                        result = new byte[arr.length - 1];
                        System.arraycopy(arr, 1, result, 0, result.length);
                }
                return result;
        }
}
