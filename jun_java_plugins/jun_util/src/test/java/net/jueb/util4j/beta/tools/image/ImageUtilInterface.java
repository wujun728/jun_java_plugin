package net.jueb.util4j.beta.tools.image;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.InputStream;

import javax.imageio.stream.ImageInputStream;

public interface ImageUtilInterface {
	
	BufferedImage getImage(File file);
	BufferedImage getImage(String filePath);
	BufferedImage getImage(InputStream in);
	BufferedImage getImage(ImageInputStream in);
}
