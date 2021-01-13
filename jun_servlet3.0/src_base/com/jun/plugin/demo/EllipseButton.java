package com.jun.plugin.demo;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Stroke;
import java.awt.geom.Arc2D;
import java.awt.geom.Ellipse2D;

import javax.swing.AbstractButton;
import javax.swing.ButtonModel;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.basic.BasicButtonUI;

public class EllipseButton extends JButton
{
    public EllipseButton(String text)
    {
        super(text);
        // �����Լ���UI
        this.setUI(EllipseButtonUI.createUI(this));
        // ȡ��ť�ı߿�
        this.setBorder(null);
        // �������ݴ���ı���
        this.setContentAreaFilled(false);
        // �趨��Ե
        this.setMargin(new Insets(8, 14, 8, 14));
    }

    public static void main(String [] args)
    {
        // ��UIManager�з��ð�ť����
        UIManager.put("Button.font", new Font("Arial", 0, 20));
        JFrame frame = new JFrame();
        frame.setLocation(100, 100);
        frame.setSize(200, 80);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Elliptical Button");
        frame.getContentPane().setLayout(new GridBagLayout());
        EllipseButton button = new EllipseButton("Elliptical");
        frame.getContentPane().add(button);
        frame.getContentPane().setBackground(new Color(230, 230, 150));
        frame.setVisible(true);
    }
}

// ��BasicButtonUI�м̳�
class EllipseButtonUI extends BasicButtonUI
{
    protected static EllipseButtonUI singleton =
                        new EllipseButtonUI();
    //����Stroke���ڻ���ť
    protected static Stroke thickStroke = new BasicStroke(2.0f);
   
    public static ComponentUI createUI(JComponent c)
    {
        return singleton;
    }
  
    public void paint(Graphics g, JComponent c)
    {
        // ���Graphics2D�Ķ��󣬲���������ݴ���
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);
        // �õ���ť�Ĵ�С
        AbstractButton b = (AbstractButton) c;
        Rectangle viewRect = new Rectangle();
        viewRect.x = 0;
        viewRect.y = 0;
        viewRect.width = b.getWidth() - 1;
        viewRect.height = b.getHeight() - 1;
        // ��С��������ʾ�����Ч��
        viewRect.grow(-2, -2);
        // �ڰ�ť�����ڴ�����Բ
        Ellipse2D ellipse = new Ellipse2D.Float();
        ellipse.setFrame(viewRect.getX(), viewRect.getY(),
                    viewRect.getWidth(), viewRect.getHeight());
        // �жϰ�ť���ޱ�����
        ButtonModel model = b.getModel();
        boolean pressed = (model.isArmed() && model.isPressed()) ||
                    model.isSelected();
        // ��ݰ�ť����������û�����ɫ
        if (pressed)
        {
            Color background = UIManager.getColor("Button.select");
            g2.setPaint(background == null ? Color.gray : background);
        }
        else
            g2.setPaint(UIManager.getColor("control"));
        // �����Բ��ť
        g2.fill(ellipse);
        // �����Բ��ť�Ĵ�С���趨�߿��С.
        Arc2D arc = new Arc2D.Float();
        arc.setFrame(viewRect.getX(), viewRect.getY(),
                    viewRect.getWidth(), viewRect.getHeight());
        arc.setArcType(Arc2D.OPEN);
       // �趨�߿�ָ�����ȵ�����
        arc.setAngles(viewRect.getWidth(), 0, 0, viewRect.getHeight());
        g2.setStroke(thickStroke);
        // ��ݰ�ť��������趨���ʵ���ɫ
        g2.setPaint(pressed ?
                UIManager.getColor("controlDkShadow") :
                UIManager.getColor("controlHighlight"));
        g2.draw(arc);
        
        arc.setAngles(0, viewRect.getHeight(), viewRect.getWidth(), 0);
        g2.setPaint(pressed ?
                UIManager.getColor("controlHighlight") :
                UIManager.getColor("controlShadow"));
        g2.draw(arc);
        super.paint(g, c);
        // ��ͼ�������Ļָ�ԭ���Ŀ�������Ե�����
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_OFF);
    }

    public Dimension getPreferredSize(JComponent c)
    {
        AbstractButton b = (AbstractButton) c;
        Dimension dim = super.getPreferredSize(c);
        // ����߶ȺͿ�ȣ�Ϊ�˸�õ���ʾЧ��
        dim.height +=  (b.getMargin().top + b.getMargin().bottom);
        dim.width += (b.getMargin().left + b.getMargin().right);
        return dim;
    }
}
