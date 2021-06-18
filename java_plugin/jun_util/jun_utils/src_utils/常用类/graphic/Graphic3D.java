package book.graphic;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GraphicsConfiguration;
import java.awt.Label;

import javax.media.j3d.AmbientLight;
import javax.media.j3d.Appearance;
import javax.media.j3d.BoundingSphere;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.Canvas3D;
import javax.media.j3d.DirectionalLight;
import javax.media.j3d.Material;
import javax.media.j3d.Texture;
import javax.media.j3d.TextureAttributes;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.swing.JFrame;
import javax.vecmath.Color3f;
import javax.vecmath.Color4f;
import javax.vecmath.Point3d;
import javax.vecmath.Vector3f;

import com.sun.j3d.utils.geometry.ColorCube;
import com.sun.j3d.utils.geometry.Cone;
import com.sun.j3d.utils.geometry.Cylinder;
import com.sun.j3d.utils.geometry.Primitive;
import com.sun.j3d.utils.geometry.Sphere;
import com.sun.j3d.utils.image.TextureLoader;
import com.sun.j3d.utils.universe.SimpleUniverse;

/**
 * 去http://java.sun.com/products/java-media/3D/download.html下载Java 3D的API，然后安装。
 * 
 * 本程序将由浅入深的介绍多个Java 3D的例子。
 * 
 */
public class Graphic3D {
	
	/**
	 * 最基本的例子，主要讲解Java 3D程序的5个基本步骤。
	 * 例子将显示了一个发光的立方体，观察者直接注视着其中的红色面，
	 * 实际看到的是一个黑色背景上的红色方块。
	 */
	static class Basic3D{
		public Basic3D(){ 
			// （1）创建一个用来容纳你的场景的虚拟宇宙(Virtual Universe)。
			SimpleUniverse universe = new SimpleUniverse();
			// （2）创建一个用来放置一组物体的数据结构。
			BranchGroup group = new BranchGroup();
			// （3）向组中添加物体。
			group.addChild(new ColorCube(0.3));
			// （4）放置观察者(Viewer)使之面对物体。
			universe.getViewingPlatform().setNominalViewingTransform();
			// （5）将物体组添加至宇宙。
			universe.addBranchGraph(group);
		}
	}
	
	/**
	 * 给宇宙添加点灯光。灯光落到物体上产生的明暗可以帮助我们在3D空间中观察图形。
	 * 这个例子说明怎样显示一个被红光照亮的球。
	 */
	static class LightBall3D {
		public LightBall3D() {
			//	创建宇宙
			SimpleUniverse universe = new SimpleUniverse();
			// 创建容纳物体的结构
			BranchGroup group = new BranchGroup();
			// 创建一个球体（默认是白色的）并加入到物体组。0.5表示球的半径
			Sphere sphere = new Sphere(0.5f);
			group.addChild(sphere);
			
			// 创建一个从原点延伸1000米的红色光源，Color3f的参数分别为红、绿、蓝
			Color3f light1Color = new Color3f(1.8f, 0.1f, 0.1f);
			BoundingSphere bounds = new BoundingSphere(new Point3d(0.0, 0.0,
					0.0), 1000.0);
			// 方向向量：4.0向右，-7.0向下，-12.0向内
			Vector3f light1Direction = new Vector3f(4.0f, -7.0f, -12.0f);
			//定向光源，必须指定光线照射的距离和方向
			DirectionalLight light1 = new DirectionalLight(light1Color,
					light1Direction);
			light1.setInfluencingBounds(bounds);
			
			group.addChild(light1);

			// 放置观察者，注视球体
			universe.getViewingPlatform().setNominalViewingTransform();

			// 添加物体组到宇宙中
			universe.addBranchGraph(group);
		}
	}
	
	/**
	 * 以上两个例子都在宇宙中间这样一个相同的地方建立物体。
	 * 在Java 3D中，位置由x,y,z坐标描述。X轴坐标值沿向右方向增长，Y轴向上，Z轴由屏幕向外。
	 * 
	 * 这个例子在每个坐标轴上显示不同物体
	 */
	static class TransformPositon {
		public TransformPositon() {
			// 创建宇宙
			SimpleUniverse universe = new SimpleUniverse();
			// 创建容纳物体的结构
			BranchGroup group = new BranchGroup();

			// X轴由球体组成
			for (float x = -1.0f; x <= 1.0f; x = x + 0.1f)	{
				// 创建半径为0.05的白色球
				Sphere sphere = new Sphere(0.05f);
				
				// 创建一个变换组
				TransformGroup tg = new TransformGroup();
				// 创建一个变换
				Transform3D transform = new Transform3D();
				// 为球指定位置
				Vector3f vector	= new Vector3f(x, 0.0f, 0.0f);
				// 设置球移动到指定位置
				transform.setTranslation(vector);
				// 将变换添加到变换组中
				tg.setTransform(transform);
				// 将球加入到变换组中
				tg.addChild(sphere);
				// 将变换组加入到容纳物体的结构中
				group.addChild(tg);
			}

			// Y轴由锥体组成
			for (float y = -1.0f; y <= 1.0f; y = y + 0.1f)	{
				// 创建一个锥体，底面半径为0.05，高度为0.1
				Cone cone = new Cone(0.05f, 0.1f);
				// 创建变换组和变换
				TransformGroup tg = new TransformGroup();
				Transform3D transform = new Transform3D();
				// 为锥体指定位置
				Vector3f vector = new Vector3f(0.0f, y, 0.0f);
				// 设置锥体移动到指定位置
				transform.setTranslation(vector);
				// 将变换和球加到变换组中
				tg.setTransform(transform);
				tg.addChild(cone);

				group.addChild(tg);
			}
			
			// Z轴由柱体组成
			for (float z = -1.0f; z <= 1.0f; z = z + 0.1f)	{
				// 创建一个柱体，底面半径为0.05，高度为0.1
				Cylinder cylinder = new Cylinder(0.05f, 0.1f);
				// 将柱体显示在指定位置上
				TransformGroup tg = new TransformGroup();
				Transform3D transform = new Transform3D();
				Vector3f vector = new Vector3f(0.0f, 0.0f, z);
				transform.setTranslation(vector);
				tg.setTransform(transform);
				tg.addChild(cylinder);
				group.addChild(tg);
			}
			
			// 创建绿色光源
			Color3f light1Color = new Color3f(0.1f, 1.4f, 0.1f); // green light
			BoundingSphere bounds =	new BoundingSphere(new Point3d(0.0, 0.0, 0.0), 1000.0);
			Vector3f light1Direction = new Vector3f(4.0f, -7.0f, -12.0f);
			DirectionalLight light1	= new DirectionalLight(light1Color, light1Direction);
			light1.setInfluencingBounds(bounds);
			group.addChild(light1);
			
			//放置观察者
			universe.getViewingPlatform().setNominalViewingTransform();

			// 把物体组加入宇宙
			universe.addBranchGraph(group);
/**
 * 放置一个物体到场景中，你将从点(0,0,0)开始，然后移动物体到你想要的地方。
 * 移动物体被称为“变换(transformation)”,所以你要使用的类是:TransformGroup和Transform3D。
 * 要先把物体和Transform3D对象加入TransformGroup，再把TransformGroup放入场景中。
 */
		}
	}
	
	/**
	 * 本例子设置物体的材质和纹理，使球表面更加丰富，更像一个立体图像，
	 */
	static class PictureBall {
		public PictureBall() {
			//	 创建宇宙
			SimpleUniverse universe = new SimpleUniverse();
			// 创建容纳物体的结构
			BranchGroup group = new BranchGroup();

			// 建立颜色
			Color3f black = new Color3f(0.0f, 0.0f, 0.0f);
			Color3f white = new Color3f(1.0f, 1.0f, 1.0f);
			Color3f red = new Color3f(0.7f, 0.15f, 0.15f);

			// 建立纹理帖图
			
			// 纹理装载器(TextureLoader)类允许装载一个图片作为纹理。图片的尺寸必须是２的幂，例如128像素宽256像素高。
			// 当载入图片后也可以指定想要如何使用图片。例如，RGB使用图片的颜色，LUMINANCE使图片显示为黑白。
			TextureLoader loader = new TextureLoader("C:/temp/scenery.jpg",
					"RGB", new Container());
			Texture texture = loader.getTexture();
			texture.setBoundaryModeS(Texture.WRAP);
			texture.setBoundaryModeT(Texture.WRAP);
			texture.setBoundaryColor(new Color4f(0.0f, 1.0f, 0.0f, 0.0f));

			// 建立纹理属性 

			//可以用REPLACE, BLEND 或 DECAL 代替 MODULATE
			TextureAttributes texAttr = new TextureAttributes();
			texAttr.setTextureMode(TextureAttributes.MODULATE);

			Appearance ap = new Appearance();
			ap.setTexture(texture);
			ap.setTextureAttributes(texAttr);

			//建立材质，五个参数分别是（环境、放射、漫射、镜射、发光）
			ap.setMaterial(new Material(red, red, black, black, 50.0f));

			// 创建一个球来展示纹理
			// 如果使用一个简单物体如球体，则需要通过设置“原始标记”允许纹理化。
			int primflags = Primitive.GENERATE_NORMALS
					+ Primitive.GENERATE_TEXTURE_COORDS;
			Sphere sphere = new Sphere(0.5f, primflags, ap);
			group.addChild(sphere);

			// 创建灯光，定向光源
			Color3f light1Color = new Color3f(1f, 1f, 1f);
			BoundingSphere bounds = new BoundingSphere(new Point3d(0.0, 0.0,
					0.0), 1000.0);
			Vector3f light1Direction = new Vector3f(4.0f, -7.0f, -12.0f);
			DirectionalLight light1 = new DirectionalLight(light1Color,
					light1Direction);
			light1.setInfluencingBounds(bounds);
			group.addChild(light1);

			// 建立一个环境光源
			AmbientLight ambientLight = new AmbientLight(new Color3f(0.5f, 0.5f,
					0.5f));
			ambientLight.setInfluencingBounds(bounds);
			group.addChild(ambientLight);

			// 注视球体
			universe.getViewingPlatform().setNominalViewingTransform();

			// 把物体组加入宇宙
			universe.addBranchGraph(group);
		}
	}
	
	/**
	 * 这个例子将展示3D与Swing的结合。
	 * 在一个上下都有标签(JLabel)的JFrame中创建Canvas3D
	 */
	static class Canvas3DAndSwing extends JFrame{
		public Canvas3DAndSwing(){
			// 设置布局
			this.getContentPane().setLayout(new BorderLayout());
			
			// 获得宇宙的配置
	        GraphicsConfiguration config = SimpleUniverse.getPreferredConfiguration();
	        // 根据宇宙配置创建一个Canvas3D，3D的画布
	        Canvas3D canvas = new Canvas3D(config);  
	        
	        // 添加组件
	        this.getContentPane().add("North",new Label("上面的标签"));
	        this.getContentPane().add("Center", canvas);
	        this.getContentPane().add("South",new Label("下面的标签"));
	        
	        // 在3D画布中画3D物体
	        BranchGroup group = new BranchGroup();
	        Sphere sphere = new Sphere(0.5f);
	        group.addChild(sphere);
	        
			// 创建一个从原点延伸1000米的红色光源，Color3f的参数分别为红、绿、蓝
			Color3f light1Color = new Color3f(1.8f, 0.1f, 0.1f);
			BoundingSphere bounds = new BoundingSphere(new Point3d(0.0, 0.0,
					0.0), 1000.0);
			// 方向向量：4.0向右，-7.0向下，-12.0向内
			Vector3f light1Direction = new Vector3f(4.0f, -7.0f, -12.0f);
			//定向光源，必须指定光线照射的距离和方向
			DirectionalLight light1 = new DirectionalLight(light1Color,
					light1Direction);
			light1.setInfluencingBounds(bounds);
			
			group.addChild(light1);
	        
	        // 创建一个包含3D画布的宇宙
	        SimpleUniverse universe = new SimpleUniverse(canvas);
	        universe.getViewingPlatform().setNominalViewingTransform();
	        universe.addBranchGraph(group); 
	        
	        // 设置窗体属性
	        this.setSize(400, 400);
	        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	        this.setVisible(true);
		}
	} 

	public static void main(String[] args) {
//		Basic3D basic3D = new Basic3D();
//		LightBall3D lightBall3D = new LightBall3D();
//		TransformPositon transformPositon = new TransformPositon();
//		PictureBall pictureBall = new PictureBall();
		Canvas3DAndSwing canvas3DSwingDemo = new Canvas3DAndSwing();
	}
}
