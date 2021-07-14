package book.mutimedia.greadsnake;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * 贪吃蛇的视图，MVC中的View
 */
public class SnakeView extends JFrame implements Observer{
    
	// 蛇运行范围格子的宽度和高度
	public static final int nodeWidth = 10;
    public static final int nodeHeight = 10;
    
    // 游戏画面的宽度和高度
    private int canvasWidth;
    private int canvasHeight;
    // 游戏画面左上角的位置
    private int startX = 0;
    private int startY = 0;
    
    // 显示分数的标签
    JLabel labelScore;
    // 画蛇和格子的画布
    Canvas paintCanvas;
    
    // 默认构造方法
    public SnakeView(){
        this(30, 40, 0, 0);
    }
    // 带参数的构造方法
    public SnakeView(int maxX, int maxY){
        this(maxX, maxY, 0, 0);
    }
    // 带参数的构造方法
    public SnakeView(int maxX, int maxY, int startX, int startY){
        this.canvasWidth = maxX * nodeWidth;
        this.canvasHeight = maxY * nodeHeight;
        this.startX = startX;
        this.startY = startY;
        init();
    }
    
    // 初始化视图
    private void init(){
        
        this.setName("贪吃蛇");
        this.setLocation(startX, startY);
        Container cp = this.getContentPane();

        // 创建顶部的分数显示
        labelScore = new JLabel("Score:");
        cp.add(labelScore, BorderLayout.NORTH);

        // 创建中间的游戏显示区域
        paintCanvas = new Canvas();
        paintCanvas.setSize(canvasWidth + 1, canvasHeight + 1);
        cp.add(paintCanvas, BorderLayout.CENTER);

        // 创建底下的帮助栏
        JPanel panelBottom = new JPanel();
        panelBottom.setLayout(new BorderLayout());
        JPanel panel1 = new JPanel();
        panel1.setLayout(new BorderLayout());
        JLabel labelHelp;
        labelHelp = new JLabel("PageUp, PageDown for speed", 
        		JLabel.CENTER);
        panel1.add(labelHelp, BorderLayout.NORTH);
        labelHelp = new JLabel("ENTER or R or S for start", 
        		JLabel.CENTER);
        panel1.add(labelHelp, BorderLayout.CENTER);
        panelBottom.add(panel1, BorderLayout.NORTH);
        
        labelHelp = new JLabel("SPACE or P for pause", JLabel.CENTER);
        panelBottom.add(labelHelp);
        labelHelp = new JLabel("q for stop", JLabel.CENTER);
        panelBottom.add(labelHelp);
        
        cp.add(panelBottom, BorderLayout.SOUTH);

        this.pack();
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }
    
    // 实现Observer接口定义的update方法
    public void update(Observable o, Object arg){
        
    	// 获取被监控的模型
        SnakeModel model = (SnakeModel)o;
        // 获得画笔
        Graphics g = paintCanvas.getGraphics();

        // 画背景
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, canvasWidth, canvasHeight);

        // 画蛇
        g.setColor(Color.BLACK);
        LinkedList na = model.getNodeArray();
        Iterator it = na.iterator();
        while (it.hasNext()) {
            Node n = (Node) it.next();
            drawNode(g, n);
        }

        // 画食物
        g.setColor(Color.RED);
        Node n = model.getFood();
        drawNode(g, n);
        // 更新分数
        this.updateScore(model.getScore());
    }
    
    // 画格子
    private void drawNode(Graphics g, Node n) {
        g.fillRect(n.x * nodeWidth, n.y * nodeHeight, 
        		nodeWidth - 1, nodeHeight - 1);
    }

    public void updateScore(int score) {
        String s = "Score: " + score;
        labelScore.setText(s);
    }

}
