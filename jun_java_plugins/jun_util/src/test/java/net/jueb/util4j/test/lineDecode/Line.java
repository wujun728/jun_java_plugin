package net.jueb.util4j.test.lineDecode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

/**
 * 满足条件的线
 * @author Administrator
 */
public class Line {
	public static final BonusType king=BonusType.s;
	public static final int[] counts=new int[]{3,4,5};//连续满足次数
	private BonusType type;
	private LineType lineType;
	private int typeCount;
	private ArrayList<LineResult> allResults=new ArrayList<Line.LineResult>();//所有结果
	private ArrayList<LineResult> useFullResults=new ArrayList<Line.LineResult>();//满足条件的结果
	
	public final int getTypeCount()
	{
		return typeCount;
	}
	public final BonusType getType()
	{
		return type;
	}
	
	public  final LineType getLineType()
	{
		return lineType;
	}
	
	/**
	 * 是否有王
	 * @param types
	 * @return
	 */
	public boolean hasKing(BonusType[] types)
	{
		for(int i=0;i<types.length;i++)
		{
			if(types[i]==king)
			{
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 第一个不为王的索引
	 * @param types
	 * @return
	 */
	public int firstUnKingType(BonusType[] types)
	{
		for(int i=0;i<types.length;i++)
		{
			if(types[i]!=king)
			{
				return i;
			}
		}
		return -1;
	}
	
	/**
	 * 类型王翻转
	 * OldBonus:[9, 9, 9, 2, 4]
	 * ResultBonus:[2, 2, 2, 2, 4]
	 * OldBonus:[4, 9, 4, 4, 9]
	 * ResultBonus:[4, 4, 4, 4, 4]
	 * @param types
	 * @return
	 */
	public BonusType[] typesNoKing(BonusType[] types)
	{
		BonusType[] results=new BonusType[types.length];
		BonusType changeType=null;//翻转类型
		for(int i=0;i<types.length;i++)
		{
			if(types[i]==king)
			{//如果为王
				if(i==0)
				{//如果是第一个为王
					changeType=types[firstUnKingType(types)];
				}
				results[i]=changeType;
			}else
			{//如果不为王
				results[i]=types[i];
				changeType=types[i];//更新后面的王改变类型
			}
		}
		return results;
	}
	
	/**
	 * 找出该线类型结果
	 * @param srcBonus
	 * @param line
	 * @return
	 */
	public LineResult findLineResult(BonusType[] srcBonus,LineType line)
	{
		LineResult result=new LineResult(srcBonus,line);
		boolean isAllKing=true;
		for(int i=0;i<srcBonus.length;i++)
		{
			if(srcBonus[i]!=king)
			{
				isAllKing=false;
				break;
			}
		}
		if(isAllKing)
		{//如果全是王
			result.setByLeftCount(counts.clone());
			result.setByRightCount(counts.clone());
			result.setType(king);
			result.setMaxCount(srcBonus.length);
			return result;
		}
		/**
		 * 如果不全是王
		 */
		updateCountType(result);
		return result;
	}
	
	/**
	 * 是否有线
	 * @param bonus
	 * @return
	 */
	public final boolean hasLine(BonusType[] srcBonus)
	{
		boolean has=false;
		for(LineType line:LineType.values())
		{//遍历所有线的组合
			LineResult result=findLineResult(line.findLineTypes(srcBonus), line);
			allResults.add(result);
			if(result.getMaxCount()>0)
			{
				useFullResults.add(result);
				has=true;
			}
		}
		if(has)
		{
			for(LineResult result:useFullResults)
			{
				if(result.getMaxCount()>typeCount)
				{
					lineType=result.getLine();
					type=result.getType();
					typeCount=result.getMaxCount();
				}
			}
		}
		return has;
	}
	
	/**
	 * 翻转数组
	 * @param t 返回翻转的数组
	 * @return
	 */
	public <T> T[]  reverse(T[] t)
	{
		ArrayList<T> tmps=new ArrayList<T>();
		for(int i=0;i<t.length;i++)
		{
			tmps.add(t[i]);
		}
		Collections.reverse(tmps);
		return tmps.toArray(t.clone());
	}
	
	/**
	 * 这些组合里面是否有满足连续次数的
	 * @param bonus
	 * @return
	 */
	public void updateCountType(LineResult result)
	{
		BonusType[] byLeftBonus=result.byLeftNoKing;
		BonusType[] byRightBonus=result.byRightNoKing;
		int[] byLeftCount=new int[]{};
		int[] byRightCount=new int[]{};
		BonusType byLeftType=null;
		BonusType byRightType=null;
		int maxCount=0;
		BonusType maxCountType=null;
		int leftCount=0;//当前连续相等满足个数
		for(int i=0;i<byLeftBonus.length;i++)
		{
			if(i!=0)
			{
				if(byLeftBonus[i]==byLeftBonus[i-1])
				{
					leftCount=i+1;//当前连续相等个数=长度+1
					for(int count:counts)
					{
						if(leftCount==count)
						{//如果是连续3或4或5
							byLeftType=byLeftBonus[i];
							byLeftCount=Arrays.copyOf(byLeftCount, byLeftCount.length+1);
							byLeftCount[byLeftCount.length-1]=count;
							if(count>maxCount)
							{
								maxCount=count;
								maxCountType=byLeftBonus[i];
							}
						}
					}
				}else
				{
					break;
				}
			}
		}
		int rightCount=0;
		for(int i=0;i<byRightBonus.length;i++)
		{
			if(i!=0)
			{
				if(byRightBonus[i]==byRightBonus[i-1])
				{
					rightCount=i+1;//当前连续相等个数=长度+1
					for(int count:counts)
					{
						if(rightCount==count)
						{//如果是连续3或4或5
							byRightType=byRightBonus[i];
							byRightCount=Arrays.copyOf(byRightCount, byRightCount.length+1);
							byRightCount[byRightCount.length-1]=count;
							if(count>maxCount)
							{
								maxCount=count;
								maxCountType=byRightBonus[i];
							}
						}
					}
				}else
				{
					break;
				}
			}
		}
		result.setByLeftCount(byLeftCount);
		result.setByLeftType(byLeftType);
		result.setByRightCount(byRightCount);
		result.setByRightType(byRightType);
		result.setMaxCount(maxCount);
		result.setType(maxCountType);
	}
	
	public LineType getLinType() {
		return lineType;
	}
	public void setLinType(LineType linType) {
		this.lineType = linType;
	}
	public ArrayList<LineResult> getAllResults() {
		return allResults;
	}
	public void setAllResults(ArrayList<LineResult> allResults) {
		this.allResults = allResults;
	}
	public ArrayList<LineResult> getUseFullResults() {
		return useFullResults;
	}
	public void setUseFullResults(ArrayList<LineResult> useFullResults) {
		this.useFullResults = useFullResults;
	}
	public void setType(BonusType type) {
		this.type = type;
	}
	public void setTypeCount(int typeCount) {
		this.typeCount = typeCount;
	}
	class LineResult{
		private final LineType line;//线路类型
		private final BonusType[] src;//目标
		private final BonusType[] byLeft;//左边顺序
		private final BonusType[] byRight;//右边顺序
		private final BonusType[] byLeftNoKing;//左边无王顺序
		private final BonusType[] byRightNoKing;//右边无王顺序
		private int byLeftCount[];//左边满足次数
		private int byRightCount[];//右边满足次数
		private BonusType byLeftType;//左边满足次数的类型
		private BonusType byRightType;//右边满足次数的类型
		private int maxCount;//最大次数
		private BonusType type;//最大次数类型
		
		public LineResult(BonusType[] src,LineType line) {
			this.line=line;
			this.src=src;
			this.byLeft=src.clone();
			this.byRight=reverse(src);
			if(hasKing(byLeft))
			{
				this.byLeftNoKing=typesNoKing(byLeft);
			}else
			{
				this.byLeftNoKing=byRight;
			}
			if(hasKing(byRight))
			{
				this.byRightNoKing=typesNoKing(byRight);
			}else
			{
				this.byRightNoKing=byRight;
			}
		}
		public LineType getLine() {
			return line;
		}
		public BonusType[] getSrc() {
			return src;
		}
		public BonusType[] getByLeft() {
			return byLeft;
		}
		public BonusType[] getByRight() {
			return byRight;
		}
		public BonusType[] getByLeftNoKing() {
			return byLeftNoKing;
		}
		public BonusType[] getByRightNoKing() {
			return byRightNoKing;
		}
		public int[] getByLeftCount() {
			return byLeftCount;
		}
		public void setByLeftCount(int[] byLeftCount) {
			this.byLeftCount = byLeftCount;
		}
		public int[] getByRightCount() {
			return byRightCount;
		}
		public void setByRightCount(int[] byRightCount) {
			this.byRightCount = byRightCount;
		}
		
		public BonusType getByLeftType() {
			return byLeftType;
		}
		public void setByLeftType(BonusType byLeftType) {
			this.byLeftType = byLeftType;
		}
		public BonusType getByRightType() {
			return byRightType;
		}
		public void setByRightType(BonusType byRightType) {
			this.byRightType = byRightType;
		}
		public int getMaxCount() {
			return maxCount;
		}
		public void setMaxCount(int maxCount) {
			this.maxCount = maxCount;
		}
		public BonusType getType() {
			return type;
		}
		public void setType(BonusType type) {
			this.type = type;
		}
		@Override
		public String toString() {
			return "LineResult [line=" + line + ", src=" + Arrays.toString(src)
					+ ", byLeft=" + Arrays.toString(byLeft) + ", byRight="
					+ Arrays.toString(byRight) + ", byLeftNoKing="
					+ Arrays.toString(byLeftNoKing) + ", byRightNoKing="
					+ Arrays.toString(byRightNoKing) + ", byLeftCount="
					+ Arrays.toString(byLeftCount) + ", byRightCount="
					+ Arrays.toString(byRightCount) + ", byLeftType="
					+ byLeftType + ", byRightType=" + byRightType
					+ ", maxCount=" + maxCount + ", type=" + type + "]";
		}
	}

	public static void main(String[] args) {
		BonusType[] testTypes=new BonusType[]{
				BonusType.valueOf(3),BonusType.valueOf(4),BonusType.valueOf(4),BonusType.valueOf(7),BonusType.valueOf(6),
				BonusType.valueOf(6),BonusType.valueOf(9),BonusType.valueOf(9),BonusType.valueOf(4),BonusType.valueOf(4),
				BonusType.valueOf(4),BonusType.valueOf(2),BonusType.valueOf(2),BonusType.valueOf(9),BonusType.valueOf(9)
				};
		Line line=new Line();
		if(line.hasLine(testTypes))
		{
			System.out.println(line.getUseFullResults().toString());
			System.out.println("type:"+line.getType()+",count:"+line.getTypeCount());
		}
	}
}
