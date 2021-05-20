package com.jun.plugin.demo;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.font.FontRenderContext;
import java.awt.font.TextAttribute;
import java.awt.font.TextLayout;
import java.text.AttributedString;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.PanelUI;

public class DigitalClock extends JPanel
{
    public DigitalClock()
    {
        // ָ��һ��UI����
        this.setUI(DigitalClockPanelUI.createUI(this));
        // �豾panelΪ��͸��
        this.setOpaque(true);
    }

    public static void main(String [] args)
    {
        JFrame frame = new JFrame();
        frame.setLocation(200, 200);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        DigitalClock panel = new DigitalClock();
        frame.getContentPane().add(panel);
        frame.pack();
        frame.setVisible(true);
    }
}

// �̳�PanelUI�࣬����������ƽ̨�϶���ʹ��
class DigitalClockPanelUI extends PanelUI implements ActionListener
{
    protected final static Font clockFont =
                            new Font("Arial", Font.BOLD, 24);
    // ʱ����ʾ�ĸ�ʽ
    protected final static SimpleDateFormat dateFormat =
                            new SimpleDateFormat("hh:mm:ss a");
    protected final static FontRenderContext frc =
                            new FontRenderContext(null, true, true);
    // ����֮��ո�
    protected final static int FUDGE = 6;
    // ����ǰʱ���AttributedString
    protected AttributedString timeString = null;
    protected TextLayout textLayout = null;
    //ÿ��һ�ε�timer�ؼ�
    protected javax.swing.Timer timer =
                        new javax.swing.Timer(1000, this);
    protected DigitalClock panel;

    private DigitalClockPanelUI(DigitalClock panel)
    {
        this.panel = panel;
        panel.setBackground(Color.black);
        actionPerformed(null);
        timer.start();
    }
    
    public static ComponentUI createUI(JComponent component)
    {
        return new DigitalClockPanelUI((DigitalClock) component);
    }
 
    public void paint(Graphics g, JComponent c)
    {
        // û��ʱ�����û��textLayoutʱ���������ػ�
        if (this.timeString == null || this.textLayout == null)
            return;

        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);
        // ��textLayoutָʾ��λ������ʾ�ַ�
        g2.drawString(timeString.getIterator(), 1,
                    (int) (textLayout.getAscent()));
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_DEFAULT);
    }
    
    // �����ʱ�䵽ʱ��timer���Զ����ô˷���
    public void actionPerformed(ActionEvent event)
    {
        // Ϊ�µ�ʱ���ַ���AttributedString
        timeString = new AttributedString(dateFormat.format(new Date()));
        // �趨�������ɫ
        timeString.addAttribute(TextAttribute.FONT, clockFont);
        timeString.addAttribute(TextAttribute.FOREGROUND, Color.red);
        // ���м��ʱ��ָ����趨Ϊ��ɫ
        timeString.addAttribute(TextAttribute.FOREGROUND,
                                Color.yellow, 2, 3);
        timeString.addAttribute(TextAttribute.FOREGROUND,
                                Color.yellow, 5, 6);
        // �����µ�TextLayout
        textLayout = new TextLayout(timeString.getIterator(), frc);
        panel.repaint();
    }
    // ��������ĺ��ʴ�С
    public Dimension getPreferredSize(JComponent c)
    {
        Dimension size = textLayout.getBounds().getBounds().getSize();
        // �ʵ�����߶ȺͿ��
        size.height += FUDGE;
        size.width += (FUDGE + 2);
        return size;
    }
}
