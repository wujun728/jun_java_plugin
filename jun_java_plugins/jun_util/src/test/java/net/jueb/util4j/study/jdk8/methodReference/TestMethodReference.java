package net.jueb.util4j.study.jdk8.methodReference;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import javax.swing.JButton;
import javax.swing.JFrame;

/**
 *
 * @author benhail
 */
public class TestMethodReference {

    public static void main(String[] args) {

        JFrame frame = new JFrame();
        frame.setLayout(new FlowLayout());
        frame.setVisible(true);
		
        JButton button1 = new JButton("点我!");
        JButton button2 = new JButton("也点我!");
		
        frame.getContentPane().add(button1);
        frame.getContentPane().add(button2);
        //这里addActionListener方法的参数是ActionListener，是一个函数式接口
        //使用lambda表达式方式
        button1.addActionListener(e -> { System.out.println("这里是Lambda实现方式"); });
        //使用方法引用方式
        button2.addActionListener(TestMethodReference::doSomething);
        
    }
    /**
     * 这里是函数式接口ActionListener的实现方法
     * @param e 
     */
    public static void doSomething(ActionEvent e) {
		
        System.out.println("这里是方法引用实现方式");
        
    }
}