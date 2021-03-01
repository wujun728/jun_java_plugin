import java.applet.Applet; 
import java.awt.*; 
import java.util.*; 

public class CalendarApplet extends Applet{ 

	static final int TOP = 70;  //顶端距离
	static final int CELLWIDTH=50,CELLHEIGHT = 30;  //单元格尺寸
	static final int MARGIN = 3;  //边界距离
	static final int FEBRUARY = 1; 
		
	TextField tfYear = new TextField("2004", 5); //显示年份的文本域
	Choice monthChoice = new Choice();  //月份选择下拉框
	Button btUpdate = new Button("更新");  //更新按钮
	GregorianCalendar calendar=new GregorianCalendar(); //日历对象
	Font smallFont = new Font("TimesRoman", Font.PLAIN, 15);  //显示小字体
	Font bigFont = new Font("TimesRoman", Font.BOLD, 50);  //显示大字体
	String days[] = {"星期日", "星期一", "星期二", "星期三","星期四", "星期五", "星期六"};  
	String months[] = {"一月", "二月", "三月", "四月","五月", "六月", "七月", "八月", "九月","十月", "十一月", "十二月"}; 
	int daysInMonth[] = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31}; //每个月的天数
	int searchMonth,searchYear; //查询的年份及月份

	public void init(){ 
	    setBackground(Color.white);  //设置背景颜色	    
	    searchMonth = calendar.get(Calendar.MONTH); //得到系统年份
	    searchYear = calendar.get(Calendar.YEAR);	//得到系统月份
	    add(new Label(" 年:")); //增加组件到Applet	
	    tfYear.setText(String.valueOf(searchYear)); //设置文本域文字	
	    add(tfYear);  
	    add(new Label(" 月:")); 	
	    monthChoice.setFont(smallFont);  //设置月份选择下拉框的显示字体
	    for (int i = 0; i < 12; i++) {	
	    	monthChoice.addItem(months[i]); //增加下拉框选项
	    }
	    monthChoice.select(searchMonth); //设置下拉框当前选择项
	    add(monthChoice); 	    
	    add(btUpdate); 
	    int componentCount=this.getComponentCount(); //得到Applet中的组件数量
	    for (int i=0;i<componentCount;i++){
	    	getComponent(i).setFont(smallFont); //设置所有组件的显示字体
	    }	    
	}	
	
	public void paint(Graphics g){ 	
	    FontMetrics fontMetric;   //显示字体的FontMetrics对象
	    int fontAscent; 	
	    int dayPos; 	
	    int totalWidth, totalHeight; //总的宽度,高度
	    int numRows;  //行数
	    int xNum, yNum;   //水平和垂直方向单元格数量 
	    int numDays;  	
	    String dayStr;	 //显示天数字符串
	    int margin;        
	    
	    g.setColor(Color.lightGray); //设置当前颜色
	    g.setFont(bigFont); //设置当前使用字体
		g.drawString(searchYear+"年",60,TOP+70); //绘制字符串
		g.drawString((searchMonth+1)+"月",200,TOP+130);	  
	
		g.setColor(Color.black);
		g.setFont(smallFont);
	    fontMetric = g.getFontMetrics(); 	//获取变量初值
	    fontAscent = fontMetric.getAscent(); 	
	    dayPos = TOP + fontAscent / 2; 	   
	    totalWidth = 7 * CELLWIDTH; 	//得到总的表格宽度
	    for (int i = 0; i < 7; i++) {	
	    	g.drawString(days[i], (CELLWIDTH-fontMetric.stringWidth(days[i]))/2 + i*CELLWIDTH,dayPos-20);  //绘制表格标题栏	
		}	
	    numRows = getNumberRows(searchYear, searchMonth); //计算需要的行的数量
	    totalHeight = numRows * CELLHEIGHT; //得到总的表格高度
	    for (int i = 0; i <= totalWidth; i += CELLWIDTH) {
	    	g.drawLine(i, TOP , i, TOP+ totalHeight); //绘制表格线
	    }	
	    for (int i = 0, j = TOP ; i <= numRows; i++, j += CELLHEIGHT) {
		    g.drawLine(0, j, totalWidth, j); //绘制表格线
	    }	
	    xNum = (getFirstDayOfMonth(searchYear, searchMonth) + 1) * CELLWIDTH - MARGIN; 
	    yNum = TOP +  MARGIN + fontAscent; 	    
	    numDays = daysInMonth[searchMonth] + ((calendar.isLeapYear(searchYear) && (searchMonth == FEBRUARY)) ? 1 : 0); 
	    for (int day = 1; day <= numDays; day++) { 	
	    	dayStr = Integer.toString(day); 
	     	g.drawString(dayStr, xNum - fontMetric.stringWidth(dayStr), yNum); 	//绘制字符串
	     	xNum += CELLWIDTH; 	
	     	if (xNum > totalWidth) { 	
	         	xNum = CELLWIDTH - MARGIN; 	
	         	yNum += CELLHEIGHT; 	
	     	} 
     	} 
	 }
	
	
	public boolean action(Event e, Object o){ 	
		int searchYearInt; 	
		if (e.target==btUpdate){ 	
	 		searchMonth = monthChoice.getSelectedIndex();  //得到查询月份
	 		searchYearInt = Integer.parseInt(tfYear.getText(), 10);  //得到查询年份	 
	 		if (searchYearInt > 1581) {	
	 			searchYear = searchYearInt; 
	 		} 	
			repaint();  //重绘屏幕
			return true; 
		 } 	
		 return false; 	
	 } 	
	
	private int getNumberRows(int year, int month) { //得到行数量
		int firstDay; 	
		int numCells;	
		if (year < 1582) { //年份小于1582年，则返回-1
			return (-1); 
		}	
		if ((month < 0) || (month > 11)) {
			return (-1); 	
		}
		firstDay = getFirstDayOfMonth(year, month); //计算月份的第一天
		 	
		if ((month == FEBRUARY) && (firstDay == 0) && !calendar.isLeapYear(year)) {
			return 4;
		}
		numCells = firstDay + daysInMonth[month]; 
		if ((month == FEBRUARY) && (calendar.isLeapYear(year))) {
			numCells++; 
		}
	 	return ((numCells <= 35) ? 5 : 6); 	//返回行数
	 } 	
	
	private int  getFirstDayOfMonth(int year, int month) {  //得到每月的第一天
		int firstDay; 
		int i;	
		if (year < 1582) { //年份小于1582年,返回-1
			return (-1); 
		}
		if ((month < 0) || (month > 11)) { //月份数错误,返回-1
			return (-1);	 
		}
	 	firstDay = getFirstDayOfYear(year);	//得到每年的第一天
	 	for (i = 0; i < month; i++) {
	 		firstDay += daysInMonth[i]; //计算每月的第一天
	 	}	
	 	if ((month > FEBRUARY) && calendar.isLeapYear(year)) {
	 		firstDay++; 
	 	}
		return (firstDay % 7); 			
	 } 		

	private int getFirstDayOfYear(int year){ //计算每年的第一天
		int leapYears; 
		int hundreds;
		int fourHundreds; 	
		int first;
	 	if (year < 1582) { //如果年份小于1582年
	 		return (-1); //返回-1
	 	} 
	 	leapYears = (year - 1581) / 4;
	 	hundreds = (year - 1501) / 100;
	 	leapYears -= hundreds;
	 	fourHundreds = (year - 1201) / 400; 
	 	leapYears += fourHundreds;
	 	first=5 + (year - 1582) + leapYears % 7; //得到每年第一天
	 	return first; 	
	 } 
} 