package book.mutimedia.greadsnake;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Observable;
import java.util.Random;

import javax.swing.JOptionPane;

/**
 * 游戏的Model类，负责所有游戏相关数据及运行
 */
class SnakeModel extends Observable implements Runnable {
    
	// 蛇运行的方向常量
    public static final int UP = 2;
    public static final int DOWN = 4;
    public static final int LEFT = 1;
    public static final int RIGHT = 3;
    
    // 指示位置上有没蛇体或食物
    private boolean[][] matrix; 
    // 蛇体
    private LinkedList nodeArray = new LinkedList(); 
    // 食物
    private Node food;
    // 蛇活动的范围
    private int maxX;
    private int maxY;
    // 蛇运行的方向
    private int direction = UP;
    // 运行状态
    private boolean running = false;
    // 时间间隔，毫秒
    private int timeInterval = 200;  
    // 每次的速度变化率
    private double speedChangeRate = 0.75; 
    // 暂停标志
    private boolean paused = false;

    // 得分
    private int score = 0; 
    // 吃到食物前移动的次数
    private int countMove = 0;
    
    //构造方法
    public SnakeModel(int maxX, int maxY) {
        this.maxX = maxX;
        this.maxY = maxY;
        reset();
    }
    // 重置游戏
    public void reset() {
    	// 蛇运行的方向初始向上
        direction = SnakeModel.UP;
        timeInterval = 200; 
        paused = false;
        score = 0;
        countMove = 0;

        // 初始化蛇活动的范围矩阵, 全部清0
        matrix = new boolean[maxX][maxY];
        for (int i = 0; i < maxX; i++) {
            matrix[i] = new boolean[maxY];
            Arrays.fill(matrix[i], false);
        }

        // 初始化蛇体，如果横向位置超过20个，长度为10，否则为横向位置的一半
        int initArrayLength = maxX > 20 ? 10 : maxX / 2;
        nodeArray.clear();
        // 初始蛇体居中显示
        int x = maxX / 2;
        int y = maxY / 2;
        for (int i = 0; i < initArrayLength; i++) {
            nodeArray.addLast(new Node(x, y));
            matrix[x][y] = true;
            x++;
        }

        // 创建食物
        food = createFood();
        matrix[food.x][food.y] = true;
    }

    // 改变蛇运动的方向
    public void changeDirection(int newDirection) {
        // 改变的方向不能与原来方向同向或反向
        if (direction % 2 != newDirection % 2) {
            direction = newDirection;
        }
    }

    /**
     * 蛇运行一步
     */
    public boolean moveOn() {
    	// 获得蛇头的位置
        Node head = (Node) nodeArray.getFirst();
        int headX = head.x;
        int headY = head.y;

        // 根据运行方向增减坐标值
        switch (direction) {
            case UP:
                headY--;
                break;
            case DOWN:
                headY++;
                break;
            case LEFT:
                headX--;
                break;
            case RIGHT:
                headX++;
                break;
        }

        // 如果新坐标落在有效范围内，则进行处理
        if ((0 <= headX && headX < maxX) && (0 <= headY && headY < maxY)) {
            if (matrix[headX][headY]) { 
                // 如果新坐标的点上有东西（蛇体或者食物）
                if (headX == food.x && headY == food.y) { 
                    // 吃到食物，成功
                    nodeArray.addFirst(food); // 从蛇头赠长

                    // 分数规则，与移动改变方向的次数和速度两个元素有关
                    int scoreGet = (10000 - 200 * countMove) / timeInterval;
                    score += scoreGet > 0 ? scoreGet : 10;
                    countMove = 0;

                    food = createFood(); // 创建新的食物
                    matrix[food.x][food.y] = true; // 设置食物所在位置
                    return true;
                } else {
                    // 吃到蛇体自身，失败
                    return false;
                }
            } else { 
                // 如果新坐标的点上没有东西（蛇体），移动蛇体
                nodeArray.addFirst(new Node(headX, headY));
                matrix[headX][headY] = true;
                head = (Node) nodeArray.removeLast();
                matrix[head.x][head.y] = false;
                countMove++;
                return true;
            }
        }
        // 触到边线，失败
        return false; 
    }

    public void run() {
        running = true;
        while (running) {
            try {
                Thread.sleep(timeInterval);
            } catch (Exception e) {
                break;
            }

            if (!paused) {
                if (moveOn()) {
                	// Model通知View数据已经更新，请更新试图
                    setChanged(); 
                    notifyObservers();
                } else {
                    JOptionPane.showMessageDialog(null, "you failed", 
                    		"Game Over", JOptionPane.INFORMATION_MESSAGE);
                    break;
                }
            }
        }
        if (!running){
            JOptionPane.showMessageDialog(null, "you stoped the game", 
            		"Game Over", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    // 创建食物
    private Node createFood() {
        int x = 0;
        int y = 0;
        // 随机获取一个有效区域内的与蛇体和食物不重叠的位置
        do {
            Random r = new Random();
            x = r.nextInt(maxX);
            y = r.nextInt(maxY);
        } while (matrix[x][y]);

        return new Node(x, y);
    }
    // 加速运行
    public void speedUp() {
        timeInterval *= speedChangeRate;
    }
    // 减速运行
    public void speedDown() {
        timeInterval /= speedChangeRate;
    }
    // 改变暂停状态
    public void changePauseState() {
        paused = !paused;
    }
    /**
     * @return Returns the running.
     */
    public boolean isRunning() {
        return running;
    }
    /**
     * @param running The running to set.
     */
    public void setRunning(boolean running) {
        this.running = running;
    }    
    /**
     * @return Returns the nodeArray.
     */
    public LinkedList getNodeArray() {
        return nodeArray;
    } 
    /**
     * @return Returns the food.
     */
    public Node getFood() {
        return food;
    }
    /**
     * @return Returns the score.
     */
    public int getScore() {
        return score;
    }
    public String toString() {
        String result = "";
        for (int i = 0; i < nodeArray.size(); ++i) {
            Node n = (Node) nodeArray.get(i);
            result += "[" + n.x + "," + n.y + "]";
        }
        return result;
    }
}

// 位置的描述类
class Node {
	int x;
	int y;

	Node(int x, int y) {
		this.x = x;
		this.y = y;
	}
}


