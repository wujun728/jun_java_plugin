package book.applet.tower;

import java.awt.Color;
import java.awt.Graphics;

/**
 * 描述一个塔
 */
public class Tower {
	// 塔的X坐标
	public int xPos;
	// 塔的标签
	public char label;
	// 塔上最多能放的盘数
	public int maxDisks;
	// 塔上盘的数组
	public Disk diskArray[];
	// 塔上当前的盘数 
	public int currentDisksNum;
	
	/**
	 * 构造一个空塔
	 * @param xPos
	 * @param label
	 * @param maxDisks
	 */
	public Tower(int xPos, char label, int maxDisks) {
		this.maxDisks = maxDisks;
		this.xPos = xPos;
		this.label = label;
		// 新建容纳盘的数组
		diskArray = new Disk[maxDisks];
		// 当前塔上还没有盘。
		currentDisksNum = -1;
	}
	/**
	 * 往塔顶上放一个盘
	 * @param disk1
	 */
	public void insertDisk(Disk disk1) {
		diskArray[++currentDisksNum] = disk1;
	}
	/**
	 * 从塔顶上移走一个盘
	 * @return
	 */
	public Disk removeDisk() {
		return diskArray[currentDisksNum--];
	}
	/**
	 * 获得塔顶上的盘，但还留在塔顶上
	 * @return
	 */
	public Disk peekDisk() {
		return diskArray[currentDisksNum];
	}
	/**
	 * 判断塔上是否有盘
	 * @return
	 */
	public boolean isEmpty() {
		return currentDisksNum == -1;
	}

	/**
	 * 判断塔是不是已经放满了
	 * @return
	 */
	public boolean isFull() {
		return currentDisksNum == maxDisks - 1;
	}
	/**
	 * 画塔
	 * @param g
	 * @param mode	画塔的模式
	 * @param opCode	操作模式
	 */
	public void drawTower(Graphics g, int mode, int opCode) {
		// 如果只需要画盘的模式
		if (mode == Constants.ONLY_DISK_DRAWMODE) {
			// 如果是从塔上移走盘，则擦除塔顶的盘
			if (opCode == Constants.DELETEFROM_OP) {
				eraseDisk(g, currentDisksNum + 1);
			} else 	if (opCode == Constants.ADDTO_OP) {
				// 如果是往塔顶上放盘，则画塔顶的盘
				diskArray[currentDisksNum].drawDisk(g, xPos, currentDisksNum);
			}
		} else {
			// 如果是要画盘和塔的模式
			
			// 画宽度为15的塔柱
			g.setColor(Color.black);
			// 求得塔柱左上角的坐标
			int pillarXPos = xPos - Constants.PILLAR_WIDTH/2;
			int pillarYPos = Constants.PILLAR_BOTTOM_YPOS
					- Constants.PILLAR_HEIGHT;
			g.fillRect(pillarXPos, pillarYPos, Constants.PILLAR_WIDTH,
					Constants.PILLAR_HEIGHT);
			g.setColor(Color.white);
			// 画塔标签
			g.drawString(String.valueOf(label), pillarXPos + 4,
					Constants.TOWER_LABEL_YPOS);
			// 画塔上的盘
			for (int index = 0; index <= currentDisksNum; index++) {
				if (diskArray[index] == null){
					break;
				}
				diskArray[index].drawDisk(g, xPos, index);
			}
		}
	}

	/**
	 * 擦除塔顶的盘
	 * @param g
	 * @param index
	 */
	private void eraseDisk(Graphics g, int index) {
		// 画一个大一点的矩形，将盘盖住，盘便看不见了
		int x = xPos - Constants.TOWER_X_DISTANCE / 2;
		int y = Constants.PILLAR_BOTTOM_YPOS - (index + 1)
				* Constants.DISK_HEIGHT;
		g.setColor(Color.lightGray);
		g.fillRect(x, y, Constants.TOWER_X_DISTANCE, Constants.DISK_HEIGHT);
		// 擦去盘是还要把柱子补上
		g.setColor(Color.black);
		int x2 = xPos - Constants.PILLAR_WIDTH / 2;
		g.fillRect(x2, y, Constants.PILLAR_WIDTH, Constants.DISK_HEIGHT);
	}
}