package book.graphic.jpg;

import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * 本类实现一个对 JPG/JPEG 图像文件进行缩放处理的方法 即给定一个 JPG 文件，可以生成一个该 JPG 文件的缩影图像文件 (JPG 格式 )
 * 提供三种生成缩影图像的方法： 1 、设置缩影文件的宽度，根据设置的宽度和源图像文件的大小来确定新缩影文件的长度来生成缩影图像 2
 * 、设置缩影文件的长度，根据设置的长度和源图像文件的大小来确定新缩影文件的宽度来生成缩影图像 3
 * 、设置缩影文件相对于源图像文件的比例大小，根据源图像文件的大小及设置的比例来确定新缩影文件的大小来生成缩影图像
 * 新生成的缩影图像可以比原图像大，这时即是放大源图像。
 */
public class JPGTransformer {
	//	 对象是否己经初始化
	private boolean isInitFlag = false;

	//	 定义生目标图片的宽度和高度，给其一个就可以了
	private int targetPicWidth = 0;
	private int targetPicHeight = 0;

	//	 定义目标图片的相比原图片的比例
	private double picScale = 0;

	/**
	 * 构造函数
	 */
	public JPGTransformer() {
		this.isInitFlag = false;
	}
	/**
	 * 重置JPG图片缩放器
	 */
	public void resetJPGTransformer() {
		this.picScale = 0;
		this.targetPicWidth = 0;
		this.targetPicHeight = 0;
		this.isInitFlag = false;
	}

	/**
	 * 设置目标图片相对于源图片的缩放比例
	 * @param scale
	 * @throws JPGException
	 */
	public void setPicScale(double scale) throws JPGException {
		if (scale <= 0) {
			throw new JPGException(" 缩放比例不能为0和负数！ ");
		}

		this.resetJPGTransformer();
		this.picScale = scale;
		this.isInitFlag = true;
	}

	/**
	 * 设置目标图片的宽度
	 * @param width
	 * @throws JPGException
	 */
	public void SetSmallWidth(int width) throws JPGException {
		if (width <= 0) {
			throw new JPGException(" 缩影图片的宽度不能为 0 和负数！ ");
		}

		this.resetJPGTransformer();
		this.targetPicWidth = width;
		this.isInitFlag = true;
	}
	
	/**
	 * 设置目标图片的高度
	 * @param height
	 * @throws JPGException
	 */
	public void SetSmallHeight(int height) throws JPGException {
		if (height <= 0) {
			throw new JPGException(" 缩影图片的高度不能为 0 和负数！ ");
		}

		this.resetJPGTransformer();
		this.targetPicHeight = height;
		this.isInitFlag = true;
	}
	
	/**
	 * 开始缩放图片
	 * @param srcPicFileName		源图片的文件名
	 * @param targetPicFileName		生成目标图片的文件名
	 * @throws JPGException
	 */
	public void transform(String srcPicFileName,
			String targetPicFileName) throws JPGException {

		if (!this.isInitFlag) {
			throw new JPGException(" 对象参数没有初始化！ ");
		}
		if (srcPicFileName == null || targetPicFileName == null) {
			throw new JPGException(" 包含文件名的路径为空！ ");
		}
		if ((!srcPicFileName.toLowerCase().endsWith("jpg"))
				&& (!srcPicFileName.toLowerCase().endsWith("jpeg"))) {
			throw new JPGException(" 只能处理 JPG/JPEG 文件！ ");
		}
		if ((!targetPicFileName.toLowerCase().endsWith("jpg"))
				&& !targetPicFileName.toLowerCase().endsWith("jpeg")) {
			throw new JPGException(" 只能处理 JPG/JPEG 文件！ ");
		}

		//	 新建源图片和生成图片的文件对象
		File fin = new File(srcPicFileName);
		File fout = new File(targetPicFileName);

		//	 通过缓冲读入源图片文件
		BufferedImage bSrc = null;
		try {
			// 读取文件生成BufferedImage
			bSrc = ImageIO.read(fin);
		} catch (IOException ex) {
			throw new JPGException(" 读取源图像文件出错！ ");
		}
		//   源图片的宽度和高度
		int srcW = bSrc.getWidth();
		int srcH = bSrc.getHeight();

		//   设置目标图片的实际宽度和高度
		int targetW = 0;
		int targetH = 0;
		if (this.targetPicWidth != 0) {
			// 根据设定的宽度求出长度
			targetW = this.targetPicWidth;
			targetH = (targetW * srcH) / srcW;
		} else if (this.targetPicHeight != 0) {
			// 根据设定的长度求出宽度
			targetH = this.targetPicHeight;
			targetW = (targetH * srcW) / srcH;
		} else if (this.picScale != 0) {
			// 根据设置的缩放比例设置图像的长和宽
			targetW = (int) ((float) srcW * this.picScale);
			targetH = (int) ((float) srcH * this.picScale);
		} else {
			throw new JPGException(" 对象参数初始化不正确！ ");
		}

		System.out.println(" 源图片的分辨率： " + srcW + "×" + srcH);
		System.out.println(" 目标图片的分辨率： " + targetW + "×" + targetH);
		//	目标图像的缓冲对象
		BufferedImage bTarget = new BufferedImage(targetW, targetH,
				BufferedImage.TYPE_3BYTE_BGR);
		
		// 求得目标图片与源图片宽度、高度的比例。
		double sx = (double) targetW / srcW;
		double sy = (double) targetH / srcH;

		//	构造图像变换对象
		AffineTransform transform = new AffineTransform();
		//  设置图像转换的比例
		transform.setToScale(sx, sy);
		
		//	构造图像转换操作对象
		AffineTransformOp ato = new AffineTransformOp(transform, null);
		//	实现转换，将bSrc转换成bTarget
		ato.filter(bSrc, bTarget);

		//	 输出目标图片
		try {
			// 将目标图片的BufferedImage写到文件中去，jpeg为图片的格式
			ImageIO.write(bTarget, "jpeg", fout);
		} catch (IOException ex1) {
			throw new JPGException(" 写入缩略图像文件出错！ ");
		}
	}
	
	public static void main(String[] args) throws JPGException{
		JPGTransformer jpg = new JPGTransformer();
		// 将原图片缩小一半
		jpg.setPicScale(0.5);
		String srcFileName = "C:/temp/scenery.jpg";
		String targetFileName = "C:/temp/scenery_new.jpg";
		jpg.transform(srcFileName, targetFileName);
	}
}