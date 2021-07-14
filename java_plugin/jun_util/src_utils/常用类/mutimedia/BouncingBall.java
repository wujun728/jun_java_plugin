package book.mutimedia;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.GraphicsConfiguration;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.media.j3d.AmbientLight;
import javax.media.j3d.BoundingSphere;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.Canvas3D;
import javax.media.j3d.DirectionalLight;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.swing.JFrame;
import javax.swing.Timer;
import javax.vecmath.Color3f;
import javax.vecmath.Point3d;
import javax.vecmath.Vector3d;
import javax.vecmath.Vector3f;

import com.sun.j3d.utils.geometry.Sphere;
import com.sun.j3d.utils.universe.SimpleUniverse;

/**
 * 三维弹球动画
 * 
 * 想要制作动画你就要在动画的每一帧间移动物体。你可以使用计时器并且每次经过一个很短的时间移动物体。
 * 你也可以用其它方式修改物体，下面的例子缩放了小球使它看起来在每次碰撞时被压扁。 为了和用户交互，你可以处理按键或按钮点击或其它组件。
 * 有一点需要注意的是你必须通过设置功能属性告诉Java 3D你要移动物体。否则一旦物体被绘制你将不能再移动它们。
 * 下面的例子组合了这些技术。点击按钮使它启动，小球开始上下跳动，然后你可以按下a或s左右移动小球
 */
public class BouncingBall extends JFrame implements ActionListener, KeyListener {

	// 启动按钮
	private Button go = new Button("开 始");
	// 三维变换和变换组
	private TransformGroup objTrans;
	private Transform3D trans = new Transform3D();

	// 球的Y坐标位置，为0表示在中心，为复数表示在下面，正数表示在上面
	private float hPosition = 0.0f;
	// 球的横坐标位置，位0表示处于正中心，为负数表示在左边，正数表示在右边
	private float xPosition = 0.0f;
	// 球上下运动的方向，1表示向上运动，-1表示向下运动
	private float sign = 1.0f;
	// 定时器，不断的将球上下移动
	private Timer timer;

	/**
	 * 构造函数
	 */
	public BouncingBall() {
		this.getContentPane().setLayout(new BorderLayout());
		// 获得宇宙的配置
		GraphicsConfiguration config = SimpleUniverse
				.getPreferredConfiguration();
		// 根据宇宙配置创建一个Canvas3D，3D的画布
		Canvas3D c = new Canvas3D(config);

		this.getContentPane().add("Center", c);
		c.addKeyListener(this);

		// 创建一个定时器
		timer = new Timer(100, this);

		Panel p = new Panel();
		p.add(go);
		this.getContentPane().add("North", p);

		go.addActionListener(this);
		go.addKeyListener(this);

		// 创建一个简单场景附加到虚拟宇宙
		BranchGroup scene = createSceneGraph();
		SimpleUniverse u = new SimpleUniverse(c);
		u.getViewingPlatform().setNominalViewingTransform();
		u.addBranchGraph(scene);
	}
	/**
	 * 创建三维球
	 */
	public BranchGroup createSceneGraph() {

		// 创建容纳球的结构
		BranchGroup objRoot = new BranchGroup();

		// 创建三维球，并将球放置到原点
		Sphere sphere = new Sphere(0.25f);
		objTrans = new TransformGroup();
		objTrans.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
		Transform3D pos1 = new Transform3D();
		pos1.setTranslation(new Vector3f(0.0f, 0.0f, 0.0f));
		objTrans.setTransform(pos1);
		objTrans.addChild(sphere);
		objRoot.addChild(objTrans);

		// 创建定向光源
		BoundingSphere bounds = new BoundingSphere(new Point3d(0.0, 0.0, 0.0),
				1000.0);
		Color3f light1Color = new Color3f(1.0f, 0.0f, 0.2f);
		Vector3f light1Direction = new Vector3f(4.0f, -7.0f, -12.0f);
		DirectionalLight light1 = new DirectionalLight(light1Color,
				light1Direction);
		light1.setInfluencingBounds(bounds);
		objRoot.addChild(light1);

		// 设置环境光
		Color3f ambientColor = new Color3f(1.0f, 1.0f, 1.0f);
		AmbientLight ambientLightNode = new AmbientLight(ambientColor);
		ambientLightNode.setInfluencingBounds(bounds);
		objRoot.addChild(ambientLightNode);

		return objRoot;
	}
	/**
	 * 处理键盘时间
	 */
	public void keyPressed(KeyEvent e) {
		// 按键被按下时调用
		if (e.getKeyChar() == 's') {
			xPosition = xPosition + 0.1f;
		}
		if (e.getKeyChar() == 'a') {
			xPosition = xPosition - 0.1f;
		}
	}

	public void keyTyped(KeyEvent e) {
		// do nothing
	}
	public void keyReleased(KeyEvent e) {
		// do nothing
	}

	/**
	 * 时间处理，包括按钮事件和定时器的事件
	 */
	public void actionPerformed(ActionEvent e) {

		// 按钮按下时启动计时器
		if (e.getSource() == go) {
			if (!timer.isRunning()) {
				timer.start();
			}
		} else {
			// 定时器事件
			hPosition += 0.1 * sign;
			// 到底或者到顶时转变运动方向
			if (Math.abs(hPosition * 2) >= 1) {
				sign = -1.0f * sign;
			}

			// 当球球快触及地面或者刚离开地面时，将球的高度变小，使他看起来被碰扁了。
			// 其他情况用正常大小显示球
			if (hPosition < -0.4f) {
				trans.setScale(new Vector3d(1.0, 0.8, 1.0));
			} else {
				trans.setScale(new Vector3d(1.0, 1.0, 1.0));
			}
			// 将球在变换到指定的位置
			trans.setTranslation(new Vector3f(xPosition, hPosition, 0.0f));
			objTrans.setTransform(trans);
		}
	}

	public static void main(String[] args) {

		System.out.println("Program Started");
		BouncingBall bb = new BouncingBall();
		bb.addKeyListener(bb);
		bb.setSize(500, 400);
		bb.setTitle("三维弹球动画");
		bb.setVisible(true);
		bb.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
