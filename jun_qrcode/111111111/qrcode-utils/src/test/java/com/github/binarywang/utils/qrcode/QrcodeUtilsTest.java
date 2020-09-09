package com.github.binarywang.utils.qrcode;

import com.beust.jcommander.internal.Lists;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.BasicConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.*;
import org.testng.annotations.*;

import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

/**
 * <pre>
 * Created by Binary Wang on 2017-01-05.
 * @author <a href="https://github.com/binarywang">binarywang(Binary Wang)</a>
 * </pre>
 */
public class QrcodeUtilsTest {
    private static Logger logger = LoggerFactory.getLogger(QrcodeUtils.class);

    private String content = "abc";
    private List<Path> generatedQrcodePaths = Lists.newArrayList();

    @BeforeTest
    public void setup() {
        BasicConfigurator.configure();
    }

    @Test
    public void testCreateQrcode() throws Exception {
        byte[] bytes = QrcodeUtils.createQrcode(content, 800, null);
        Path path = Files.createTempFile("qrcode_800_", ".jpg");
        generatedQrcodePaths.add(path);
        logger.info("{}", path.toAbsolutePath());
        Files.write(path, bytes);

        bytes = QrcodeUtils.createQrcode(content, null);
        path = Files.createTempFile("qrcode_400_", ".jpg");
        generatedQrcodePaths.add(path);
        logger.info("{}", path.toAbsolutePath());
        Files.write(path, bytes);
    }

    @Test
    public void testCreateQrcodeWithLogo() throws Exception {
        try (InputStream inputStream = ClassLoader.getSystemResourceAsStream("logo.png")) {
            File logoFile = Files.createTempFile("logo_", ".jpg").toFile();
            FileUtils.copyInputStreamToFile(inputStream, logoFile);
            logger.info("{}",logoFile);
            byte[] bytes = QrcodeUtils.createQrcode(content, 800, logoFile);
            Path path = Files.createTempFile("qrcode_with_logo_", ".jpg");
            generatedQrcodePaths.add(path);
            logger.info("{}",path.toAbsolutePath());
            Files.write(path, bytes);
        }
    }

    @Test(dependsOnMethods = {"testCreateQrcode", "testCreateQrcodeWithLogo"})
    public void testDecodeQrcode() throws Exception {
        for (Path path : generatedQrcodePaths) {
            Assert.assertEquals(QrcodeUtils.decodeQrcode(path.toFile()), content);
        }
    }

}
