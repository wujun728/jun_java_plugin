package com.zccoder.java.book2.ch2.start;

import javax.swing.*;
import java.io.File;

/**
 * <br>
 * 标题: 图形化应用程序<br>
 * 描述: 2-2<br>
 * 时间: 2018/10/15<br>
 *
 * @author zc
 */
public class ImageViewer {

    public static void main(String[] args) {
        JFrame frame = new ImageViewerFrame();
        frame.setTitle("图片浏览器");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    /**
     * <br>
     * 标题: 显示图片的面板<br>
     * 描述: 显示图片的面板<br>
     * 时间: 2018/10/15<br>
     *
     * @author zc
     */
    static class ImageViewerFrame extends JFrame {

        private static final long serialVersionUID = -4573129624324431200L;
        private JLabel label;
        private JFileChooser chooser;
        private static final int DEFAULT_WIDTH = 300;
        private static final int DEFAULT_HEIGHT = 400;

        public ImageViewerFrame() {
            setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);

            // 使用label显示图片
            label = new JLabel();
            add(label);

            // 设置文件选择器
            chooser = new JFileChooser();
            chooser.setCurrentDirectory(new File("."));

            // 设置菜单栏
            JMenuBar menuBar = new JMenuBar();
            setJMenuBar(menuBar);

            JMenu menu = new JMenu("File");
            menuBar.add(menu);

            JMenuItem openItem = new JMenuItem("Open");
            menu.add(openItem);
            openItem.addActionListener(event -> {
                // 显示文件选择器对话框
                int result = chooser.showOpenDialog(null);

                // 如果选中了文件，就显示到label上
                if (result == JFileChooser.APPROVE_OPTION) {
                    String name = chooser.getSelectedFile().getPath();
                    label.setIcon(new ImageIcon(name));
                }
            });

            JMenuItem exitItem = new JMenuItem("Exit");
            menu.add(exitItem);
            exitItem.addActionListener(event -> {
                System.exit(0);
            });
        }

    }

}
