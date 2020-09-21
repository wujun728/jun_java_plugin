package org.itkk.udf.qrcode.utils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;

/**
 * Base64Util
 */
public class Base64Util {

    /**
     * 私有化构造函数
     */
    private Base64Util() {

    }

    /**
     * 转码
     *
     * @param bufferedImage bufferedImage
     * @param imgType       imgType
     * @return String
     * @throws IOException IOException
     */
    public static String encode(BufferedImage bufferedImage, String imgType) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ImageIO.write(bufferedImage, imgType, outputStream);
        return encode(outputStream);
    }

    /**
     * 转码
     *
     * @param outputStream outputStream
     * @return String
     */
    public static String encode(ByteArrayOutputStream outputStream) {
        return Base64.getEncoder().encodeToString(outputStream.toByteArray());
    }
}
