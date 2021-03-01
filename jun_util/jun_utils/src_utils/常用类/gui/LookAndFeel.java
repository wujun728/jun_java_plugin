package book.gui;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.plaf.metal.MetalLookAndFeel;

import com.sun.java.swing.plaf.motif.MotifLookAndFeel;
import com.sun.java.swing.plaf.windows.WindowsLookAndFeel;
/**
 * 更改窗体、组件的外观
 */
public class LookAndFeel {

	public static void main(String[] args) {
		/**
		 * Metal风格——"javax.swing.plaf.metal.MetalLookAndFeel"
		 * Motif风格——"com.sun.java.swing.plaf.motif.MotifLookAndFeel"
		 * Windows风格——"com.sun.java.swing.plaf.windows.WindowsLookAndFeel"
		 */
		Calculator calculator1 = new Calculator();
		calculator1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//3个不同的风格
		/**
		 * LookAndFeel是一个抽象类，除了提供了一些static方法，还定义了一些抽象的个性化设置方法来由子类实现。 
		 从JDK1.1.3开始，Sun提供了三个LookAndFeel的子类
		 */
		String lnfName = "javax.swing.plaf.metal.MetalLookAndFeel";
//		String lnfName = "com.sun.java.swing.plaf.motif.MotifLookAndFeel";
//		String lnfName = "com.sun.java.swing.plaf.windows.WindowsLookAndFeel";
		try {
			// UIManager负责跟踪当前的外观及其默认设置，setLookAndFeel方法将设置它的LookAndFeel的类全名
		    UIManager.setLookAndFeel(lnfName);
		    // setLookAndFeel还可以直接设置设置对象
		    UIManager.setLookAndFeel(new MetalLookAndFeel());
//		    UIManager.setLookAndFeel(new MotifLookAndFeel());
//		    UIManager.setLookAndFeel(new WindowsLookAndFeel());
		    
		    //SwingUtilities是一个Swing的工具类，提供了很多静态方法。
		    //updateComponentTreeUI将更新calculator1中所有组件的外观
		    SwingUtilities.updateComponentTreeUI(calculator1);
		}
		catch (UnsupportedLookAndFeelException ex1) {
		    System.err.println("Unsupported LookAndFeel: " + lnfName);
		}
		catch (ClassNotFoundException ex2) {
		    System.err.println("LookAndFeel class not found: " + lnfName);
		}
		catch (InstantiationException ex3) {
		    System.err.println("Could not load LookAndFeel: " + lnfName);
		}
		catch (IllegalAccessException ex4) {
		    System.err.println("Cannot use LookAndFeel: " + lnfName);
		}
		calculator1.setVisible(true);
	}
}
