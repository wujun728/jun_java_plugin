package book.string;

/**
 * 字符串对齐器。可以指定对齐格式和一行的最大字符数
 * 对齐格式有左对齐、居中、又对齐
 */
public class StringAlign {

	/** 左对齐格式 */
	public static final int JUST_LEFT = 0;
	/** 居中格式*/
	public static final int JUST_CENTER = 1;
	/** 右对齐格式 */
	public static final int JUST_RIGHT = 2;
	
	/** 当前对齐格式 */
	private int just;
	/** 一行的最大长度 */
	private int maxChars;

	/**
	 * 默认构造函数
	 */
	public StringAlign(){
		//默认为居中对齐
		this.just = JUST_CENTER;
		//默认一行的最大长度为80
		this.maxChars = 80;
	}
	/** 
	 * 构造一个字符串对齐器，需要传入一行的最大长度和对齐的格式。
	 * @param maxChars
	 * @param just
	 */
	public StringAlign(int maxChars, int just) {
		//首先构造一个默认字符串对齐器
		this();
		//根据传入参数修改字符串对齐器的属性
		this.setJust(just);
		this.setMaxChars(maxChars);
	}

	/** 对齐一个字符串   
	 * @param obj 	待对齐的字符串
	 */
	public String format(String s) {
		StringBuffer where = new StringBuffer();
		//从待对齐的字符串中取出一段子字符串，子串的长度为行最大长度和s长度的较小值
		int wantedLength = Math.min(s.length(), this.maxChars);
		String wanted = s.substring(0, wantedLength);
		// 根据对齐模式，将空格放在合适的位置
		switch (this.just) {
		case JUST_RIGHT:
			//如果是右对齐，那么将缺少的的字符用空格代替放在左边
			pad(where, maxChars - wantedLength);
			//将字符串添加在右边
			where.append(wanted);
			break;
		case JUST_CENTER:
			//居中对齐，将空格字符平均分在字符串两边。
			int startPos = where.length();
			pad(where, (maxChars - wantedLength) / 2);
			where.append(wanted);
			pad(where, (maxChars - wantedLength) / 2);
			//调整舍入误差
			pad(where, maxChars - (where.length() - startPos));
			break;
		case JUST_LEFT:
			//右对齐，将空格字符放在字符串右边。
			where.append(wanted);
			pad(where, maxChars - wantedLength);
			break;
		}
		//如果原字符串的长度大于一行的最大长度，则将余下部分放入下一行
		if (s.length() > wantedLength){
			String remainStr = s.substring(wantedLength);
			where.append("\n" + this.format(remainStr));
		}
		return where.toString();
	}

	/**
	 * 在to的后面append howMany个空格字符。
	 * @param to
	 * @param howMany
	 */
	protected final void pad(StringBuffer to, int howMany) {
		for (int i = 0; i < howMany; i++)
			to.append(" ");
	}
	
    public int getJust() {
		return just;
	}
    /**
     * 设置字符串对齐器的对齐格式
     * @param just
     */
	public void setJust(int just) {
		switch (just) {
		case JUST_LEFT:
		case JUST_CENTER:
		case JUST_RIGHT:
			this.just = just;
			break;
		default:
			System.out.println("invalid justification arg.");
		}
	}
	public int getMaxChars() {
		return maxChars;
	}
	/**
	 * 设置字符串对齐器的一行最大字符数
	 * @param maxChars
	 */
	public void setMaxChars(int maxChars) {
		if (maxChars < 0) {
			System.out.println("maxChars must be positive.");
		} else {
			this.maxChars = maxChars;
		}
	}
	public static void main(String[] args) {
    	//一行最多70个字符，居中显示。
        StringAlign formatter = new StringAlign(20, StringAlign.JUST_CENTER);
        // 比如显示页码
        System.out.println(formatter.format("- i -"));
        System.out.println(formatter.format(Integer.toString(444)));
        System.out.println(formatter.format("kkkkkkkkkkkkkkkkkkkkkkkkkkkk"));
        //左对齐
        System.out.println();
        formatter = new StringAlign(20, StringAlign.JUST_LEFT);
        System.out.println(formatter.format("- i -"));
        System.out.println(formatter.format(Integer.toString(444)));
        System.out.println(formatter.format("kkkkkkkkkkkkkkkkkkkkkkkkkkkk"));
        //右对齐
        System.out.println();
        formatter = new StringAlign(20, StringAlign.JUST_RIGHT);
        System.out.println(formatter.format("- i -"));
        System.out.println(formatter.format(Integer.toString(444)));
        System.out.println(formatter.format("kkkkkkkkkkkkkkkkkkkkkkkkkkkk"));
    }
}
