package book.applet.tower;


public class Constants {

	public static final int INIT_DISK_NUMS = 4;
	/***整个游戏的区域的宽度和高度***/
	public static final int GAME_WIDTH = 440;
	public static final int GAME_HEIGHT = 325;
	
	/***游戏上部和下部的高度***/
	public static final int GAME_BOTTOM_HEIGHT = 25;
	public static final int GAME_TOP_HEIGHT = 71;
	
	/***中间游戏区域的坐标、宽度、高度 ***/
	public static final int GAME_DISTRICT_XPOS = 2;
	public static final int GAME_DISTRICT_YPOS = GAME_TOP_HEIGHT;
	public static final int GAME_DISTRICT_WIDTH = GAME_WIDTH 
			- 2	* GAME_DISTRICT_XPOS;
	public static final int GAME_DISTRICT_HEIGHT = GAME_HEIGHT
			- GAME_BOTTOM_HEIGHT - GAME_DISTRICT_YPOS;
	
	/***画塔的模式**/
	// ONLY_DISK_DRAWMODE表示仅仅只需要画盘
	public static final int ONLY_DISK_DRAWMODE = 1;
	// ALL_DRAWMODE表示需要画塔柱子和盘
	public static final int ALL_DRAWMODE = 2;
	
	/***画盘的操作模式***/
	// DELETEFROM_OP表示移走塔上的盘
	public static final int DELETEFROM_OP = 1;
	// ADDTO_OP表示给塔放盘
	public static final int ADDTO_OP = 2;
	
	/***盘的高度和最大宽度***/
	public static final int DISK_HEIGHT = 20;
	public static final int DISK_MAX_WIDTH = 120;
	public static final int DISK_CORNER_WIDTH = 10;
	
	// 塔之间在X方向上的距离
	public static final int TOWER_X_DISTANCE = 140;
	// 第一个塔的X坐标
	public static final int TOWER_FIRST_XPOS = 80;
	
	/***塔柱的宽度和高度，以及塔底的Y坐标***/
	public static final int PILLAR_WIDTH = 15;
	public static final int PILLAR_HEIGHT = 220;
	public static final int PILLAR_BOTTOM_YPOS = GAME_HEIGHT
			- GAME_BOTTOM_HEIGHT;
	
	// 塔标签的Y坐标
	public static final int TOWER_LABEL_YPOS = 95;
}
