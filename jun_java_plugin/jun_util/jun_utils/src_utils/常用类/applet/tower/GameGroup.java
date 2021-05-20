package book.applet.tower;

import java.awt.Color;
import java.awt.Graphics;
/**
 * 游戏场景，定义游戏的规则，是游戏的主要实现类
 */
public class GameGroup {
	// 提示消息
	private String noteMsg;
	// 表示是否在进行单步演示
	private boolean isStep;
	// 单步执行时的步骤编号
	private int stepActionCode;
	// 刷新游戏场景的模式
	private int drawMode;
	// 标示当前的盘是否已经成功移走
	private boolean diskMoved;
	// 塔数组
	private Tower[] towerArray;
	// 塔上的盘数
	private int disksNum;
	// 存放参数的堆栈
	private java.util.Stack paramsStack;
	// 参数
	private Params theseParams;
	
	// 此次操作要最终要移动的盘数
	private int needMoveDisksNum;
	// 此次操作要把盘从哪个塔上移走
	private int fromTower;
	// 此次操作要把盘移到哪个塔上去
	private int toTower;
	// 此次操作的中间塔是哪个
	private int interTower;
	
	// 标识游戏是否在成功
	private boolean isDoneFlag;
	
	/**
	 * 构造函数，游戏中塔上的盘数
	 * @param disksNum
	 */
	public GameGroup(int disksNum) {
		this.disksNum = disksNum;
		// 盘之间的宽度比例因子
		int widthFactor = Constants.DISK_MAX_WIDTH / disksNum;
		// 构建3个塔
		towerArray = new Tower[3];
		towerArray[0] = new Tower(Constants.TOWER_FIRST_XPOS, 'A', disksNum);
		towerArray[1] = new Tower(Constants.TOWER_FIRST_XPOS + Constants.TOWER_X_DISTANCE, 'B', disksNum);
		towerArray[2] = new Tower(Constants.TOWER_FIRST_XPOS + 2 * Constants.TOWER_X_DISTANCE, 'C', disksNum);
		// 为第A塔添加disksNum个盘
		for (int j = 0; j < disksNum; j++) {
			// 求得盘的宽度，最底下的盘，宽度最大
			int diskWidth = Constants.DISK_MAX_WIDTH - j * widthFactor;
			// 随机生成盘的颜色
			int colorR = 75 + (int) (Math.random() * 180D);
			int colorG = 75 + (int) (Math.random() * 180D);
			int colorB = 75 + (int) (Math.random() * 180D);
			Color color = new Color(colorR, colorG, colorB);
			// 新建一个盘，并且放在A塔的顶上
			Disk disk1 = new Disk(diskWidth, color, String.valueOf(disksNum - j));
			towerArray[0].insertDisk(disk1);
		}

		paramsStack = new java.util.Stack();
		noteMsg = "Press any button, or drag disk to another post";
		diskMoved = false;
		isDoneFlag = false;
		drawMode = Constants.ALL_DRAWMODE;
	}
	/**
	 * 错误信息
	 */
	public void creationError() {
		noteMsg = "Before using New, enter number of disks (1 to 10)";
		drawMode = Constants.ONLY_DISK_DRAWMODE;
	}
	/**
	 * 确认新游戏
	 */
	public void warningNew() {
		noteMsg = "ARE YOU SURE? Press again to reset game";
	}
	/**
	 * 游戏是否成功
	 */
	public boolean isDone() {
		return isDoneFlag;
	}
	/**
	 * 设置游戏是否成功
	 */
	public void setDone(boolean flag) {
		isDoneFlag = flag;
	}

	/**
	 * 开始用鼠标移动一个盘
	 * @param x	鼠标的X坐标
	 * @param y	鼠标的Y坐标
	 */
	public void startDrag(int x, int y) {
		// 此时盘还没有被移动
		diskMoved = false;
		// 根据鼠标坐标获得起始塔的编号
		fromTower = getClosedTower(x, y);
		if (fromTower == -1) {
			noteMsg = "DRAG the CENTER of the disk";
			return;
		}
		// 判断起始塔上是否有盘，没有盘则不能移动
		if (towerArray[fromTower].isEmpty()) {
			noteMsg = "NO DISKS on tower " + towerArray[fromTower].label;
			fromTower = -1;
		} else {
			// 有盘，则移动盘，刷新时只需要画盘就行了，不需要重画塔柱。
			noteMsg = "Dragging from tower " + towerArray[fromTower].label;
			drawMode = Constants.ONLY_DISK_DRAWMODE;
		}
	}
	/**
	 * 结束用鼠标移动盘
	 * @param x	鼠标的X坐标
	 * @param y	鼠标的Y坐标
	 */
	public void endDrag(int x, int y) {
		// 此时盘还没有被移动
		diskMoved = false;
		// 获得目标塔的编号
		toTower = getClosedTower(x, y);
		// 如果鼠标没有选中目标塔，或者没有起始塔，或者起始塔和目标塔一样，则不允许移动盘
		if (fromTower == -1 || toTower == -1 || fromTower == toTower) {
			noteMsg = "Drag a colored DISK to a different black TOWER";
			return;
		}
		// 开始移动盘
		noteMsg = "Dragged to tower " + towerArray[toTower].label;
		// 移动盘时需要注意，目标塔上的盘应该比被移动的盘宽
		if (!towerArray[toTower].isEmpty()
				&& towerArray[fromTower].peekDisk().width > towerArray[toTower]
						.peekDisk().width) {
			noteMsg = "Must put a SMALLER disk ON a LARGER disk";
			return;
		}
		// 从起始塔上移走待移动的盘（塔顶的盘），插入到目标塔中
		Disk disk1 = towerArray[fromTower].removeDisk();
		towerArray[toTower].insertDisk(disk1);
		// 这时盘已经被移动了。
		diskMoved = true;
		noteMsg = "Moved disk " + disk1.label + " from " + towerArray[fromTower].label
				+ " to " + towerArray[toTower].label;
		// 如果塔C上的盘满了，则游戏成功了。
		if (towerArray[2].isFull()) {
			noteMsg = "Congratulations! You moved all the disks!";
		} else {
			// 没成功，则继续刷新，而且刷新时只画盘
			drawMode = Constants.ONLY_DISK_DRAWMODE;
		}
	}

	/**
	 * 获得距离鼠标最近的塔的编号
	 * @param x	鼠标的X坐标
	 * @param y	鼠标的Y坐标
	 * @return
	 */
	public int getClosedTower(int x, int y) {
		// 这里认为只有距离塔中轴线35象素的位置，才属于该塔
		byte range = 35;
		if (Math.abs(x - Constants.TOWER_FIRST_XPOS) < range) {
			// 属于塔A
			return 0;
		} else if (Math.abs(x - Constants.TOWER_FIRST_XPOS
				- Constants.TOWER_X_DISTANCE) < range) {
			// 属于塔B
			return 1;
		}
		// 塔C或者不属于任何塔
		return Math.abs(x - Constants.TOWER_FIRST_XPOS - 2
				* Constants.TOWER_X_DISTANCE) >= range ? -1 : 2;
	}

	/**
	 * 判断当前场景是否可以自动运行
	 * @return
	 */
	public boolean canAutoRun(){
		if (towerArray[0].isEmpty() || !towerArray[1].isEmpty()
				|| !towerArray[2].isEmpty()) {
			noteMsg = "You must begin with ALL DISKS ON TOWER A";
			return false;
		} 
		return true;
	}
	/**
	 * 设置游戏是否继续单步执行
	 * @param step
	 */
	public void setStep(boolean step){
		isStep = step;
	}
	
	/**
	 * 单步执行游戏
	 */
	public void step() {
		// 盘还没有被移动
		diskMoved = false;
		if (!isStep) {
			// 刚开始单步执行游戏
			isStep = true;
			// 执行码为1
			stepActionCode = 1;
		}
		switch (stepActionCode) {
		default:
			break;

		case 1: // 单步游戏的第一步，产生总任务参数
			if (towerArray[0].isEmpty() || !towerArray[1].isEmpty()
					|| !towerArray[2].isEmpty()) {
				// 判断是否满足单步执行的第一步的条件，必须所有盘都在塔A上。
				noteMsg = "You must begin with ALL DISKS ON TOWER A";
				stepActionCode = 1;
			} else {
				// 满足条件时，开始执行单步，将所有的盘从A移到C
				noteMsg = "Will shift all disks from A to C";
				// 构造移动的任务参数，构造函数的参数意义分别是：
				// 要移动的盘数、起始塔号、目标塔号、中转塔号、该参数任务被成功执行时的后续步骤号
				theseParams = new Params(disksNum, 0, 2, 1, 8);
				// 将参数放入堆栈
				paramsStack.push(theseParams);
				// 下一步为第二步
				stepActionCode = 2;
			}
			break;

		case 2: //执行第二步，提取下一个任务的参数，提示下一步做什么
			// 取得栈顶的任务参数，并提取详细信息
			theseParams = (Params)paramsStack.peek();
			needMoveDisksNum = theseParams.num;
			fromTower = theseParams.from;
			toTower = theseParams.to;
			interTower = theseParams.inter;
			// 提示任务信息
			noteMsg = "Entering function with " + needMoveDisksNum + " disks";
			// 下一步为第三步
			stepActionCode = 3;
			break;

		case 3: // 执行第三步，如果起始塔就一个盘，则将该盘移到目标塔，否则，不作处理。
			// 如果需要移动的盘数为1
			if (needMoveDisksNum == 1) {
				// 取得起始塔顶的盘，并将它从塔中删除
				Disk disk1 = towerArray[fromTower].removeDisk();
				// 插入到目标塔顶
				towerArray[toTower].insertDisk(disk1);
				// 此时盘已经被移动了
				diskMoved = true;
				// 因为待移动的盘只有一个，所以移动的是最后一个盘
				noteMsg = "Moved last disk " + disk1.label + " from "
						+ towerArray[fromTower].label + " to "
						+ towerArray[toTower].label;
				// 如果C塔满了，则游戏成功
				if (towerArray[2].isFull()){
					noteMsg = "Congratulations! You moved all the disks!";
				}
				// 下一步执行第7步
				stepActionCode = 7;
			} else {
				// 如果待移动的盘不只1个，则下一步执行第4步
				noteMsg = "More than one disk, will continue";
				stepActionCode = 4;
			}
			break;

		case 4: //执行第4步，将起始塔除最后一个盘以外的所有盘移到目标塔
			// 需要先将前needMoveDisksNum - 1个盘从起始塔移动到目标塔，才能够完成任务
			noteMsg = "Will move top " + (needMoveDisksNum - 1) + " disks from "
					+ towerArray[fromTower].label + " to " + towerArray[interTower].label;
			// 所以分一个子任务参数，将前needMoveDisksNum - 1个盘从起始塔移动到目标塔，该任务执行完后，执行第5步。
			theseParams = new Params(needMoveDisksNum - 1, fromTower, interTower, toTower, 5);
			paramsStack.push(theseParams);
			// 下一步执行第2步
			stepActionCode = 2;
			break;

		case 5: //执行第5步，将起始塔最后一个盘移到目标塔
			// 取得起始塔顶的盘
			Disk disk2 = towerArray[fromTower].removeDisk();
			// 插入到目标塔顶。
			towerArray[toTower].insertDisk(disk2);
			// 盘被移动了
			diskMoved = true;
			// 此时起始塔上的所有盘都被移走了，其中最后一个盘被移到了塔C，其他盘被移动到了塔B
			noteMsg = "Moved remaining disk " + needMoveDisksNum + " from "
					+ towerArray[fromTower].label + " to " + towerArray[toTower].label;
			// 下一步执行第6步
			stepActionCode = 6;
			break;

		case 6: // 执行第6步，将中转塔B上的盘（needMoveDisksNum - 1个）移动到目标塔上
			noteMsg = "Will move top " + (needMoveDisksNum - 1) + " disks from "
					+ towerArray[interTower].label + " to " + towerArray[toTower].label;
			// 新建一个子任务参数，
			theseParams = new Params(needMoveDisksNum - 1, interTower, toTower, fromTower, 7);
			paramsStack.push(theseParams);
			// 下一步执行第2步
			stepActionCode = 2;
			break;

		case 7: // 执行第7步，当一个任务执行完时进入该步
			// 获得该任务移动了的盘数
			int i = needMoveDisksNum;
			// 取得任务的下一步步骤
			stepActionCode = theseParams.actionCode;
			// 将任务参数弹出，该任务已经完成了
			paramsStack.pop();
			if (!paramsStack.isEmpty()) {
				// 如果还有其他任务，则取出栈顶的任务，提取参数
				theseParams = (Params)paramsStack.peek();
				needMoveDisksNum = theseParams.num;
				fromTower = theseParams.from;
				toTower = theseParams.to;
				interTower = theseParams.inter;
			}
			noteMsg = "Returning from function with " + i + " disks";
			break;

		case 8: // 当执行完所有任务时，进入该步。此时游戏成功了
			noteMsg = "Press New to reset";
			isDoneFlag = true;
			stepActionCode = 1;
			break;
		}
		// 对于单步执行游戏，都只需要画盘，不需要画塔
		drawMode = Constants.ONLY_DISK_DRAWMODE;
	}

	/**
	 * 画游戏场景
	 * @param g
	 */
	public void draw(Graphics g) {
		
		// 考虑到刷屏需要代价，所以要尽量的少画东西。
		// 因为不是每次操作了都需要重画所有东西的。所以定义了2种模式。
		
		// 如果要求画所有的盘和塔
		if (drawMode == Constants.ALL_DRAWMODE) {
			// 先画游戏背景
			g.setColor(Color.lightGray);
			g.fillRect(0, 0, Constants.GAME_WIDTH, Constants.GAME_HEIGHT);
			// 画中间的游戏区域
			g.setColor(Color.black);
			g.drawRect(Constants.GAME_DISTRICT_XPOS, Constants.GAME_DISTRICT_YPOS,
					Constants.GAME_DISTRICT_WIDTH, Constants.GAME_DISTRICT_HEIGHT);
			// 画塔
			for (int i = 0; i < 3; i++){
				towerArray[i].drawTower(g, drawMode, 0);
			}
		} else {
			// 如果只是要求画盘，则画盘
			for (int j = 0; j < 3; j++){
				if (diskMoved && j == fromTower) {
					towerArray[j].drawTower(g, drawMode, Constants.DELETEFROM_OP);
				} else if (diskMoved && j == toTower) {
					towerArray[j].drawTower(g, drawMode, Constants.ADDTO_OP);
				}
			}
		}
		// 画游戏上部的按钮部分所在的边框
		g.setColor(Color.lightGray);
		g.fillRect(10, 45, 430, 25);
		// 画提示信息
		g.setColor(Color.black);
		g.drawString(noteMsg, 16, 64);
		drawMode = Constants.ALL_DRAWMODE;
	}
}