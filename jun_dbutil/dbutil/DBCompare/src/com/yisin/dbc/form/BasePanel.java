package com.yisin.dbc.form;

import java.awt.Dimension;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public class BasePanel extends JPanel {

    protected static BasePanel panel = null;

    private static int width = 800;
    private static int height = 600;

    public BasePanel() {
        setPreferredSize(new Dimension(width, height));
    }

    public BasePanel(int width, int height) {
        BasePanel.width = width;
        BasePanel.height = height;
        setPreferredSize(new Dimension(width, height));
    }

    public static BasePanel getInstance() {
        if (panel == null) {
            panel = new BasePanel();
        }
        return panel;
    }

    public static BasePanel getInstance(int width, int height) {
        BasePanel.width = width;
        BasePanel.height = height;
        if (panel == null) {
            panel = new BasePanel();
        }
        return panel;
    }

    public void showPanel(){
        this.setVisible(true);
    }
    
}
