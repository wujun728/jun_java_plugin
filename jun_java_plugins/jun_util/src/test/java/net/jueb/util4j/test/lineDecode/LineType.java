package net.jueb.util4j.test.lineDecode;

import java.util.ArrayList;
import java.util.Collections;

public enum LineType {

	l1(1),l2(2),l3(3),l4(4),l5(5),l6(6),l7(7),l8(8),l9(9);
	
	private int value;
	
	private LineType(int value) {
		this.value=value;
	}
	
	public int value()
	{
		return value;
	}
	
	public static LineType valueOf(int value)
	{
		LineType type=null;
		for (LineType line:values()) {
			if(line.value==value)
			{
				type=line;
			}
		}
		return type;
	}
	
	/**
	 * 该线的种类顺序
	 * @param bonus
	 * @return
	 */
	public  BonusType[] findLineTypes(BonusType[] bonus)
	{
		switch (this) {
		case l1:
			return new BonusType[]{bonus[5],bonus[6],bonus[7],bonus[8],bonus[9]};
		case l2:
			return new BonusType[]{bonus[0],bonus[1],bonus[2],bonus[3],bonus[4]};
		case l3:
			return new BonusType[]{bonus[10],bonus[11],bonus[12],bonus[13],bonus[14]};
		case l4:
			return new BonusType[]{bonus[0],bonus[6],bonus[12],bonus[8],bonus[4]};
		case l5:
			return new BonusType[]{bonus[10],bonus[6],bonus[2],bonus[8],bonus[14]};
		case l6:
			return new BonusType[]{bonus[0],bonus[1],bonus[7],bonus[3],bonus[4]};
		case l7:
			return new BonusType[]{bonus[10],bonus[11],bonus[7],bonus[13],bonus[14]};
		case l8:
			return new BonusType[]{bonus[5],bonus[1],bonus[2],bonus[3],bonus[9]};
		case l9:
			return new BonusType[]{bonus[5],bonus[11],bonus[12],bonus[13],bonus[9]};
		default:
			break;
		}
		return null;
	}
	
	public  BonusType[] findRightLineTypes(BonusType[] bonus)
	{
		BonusType[] types=findLineTypes(bonus);
		ArrayList<BonusType> tmps=new ArrayList<BonusType>();
		for(int i=0;i<types.length;i++)
		{
			tmps.add(types[i]);
		}
		Collections.reverse(tmps);
		BonusType[] rightTypes=new BonusType[tmps.size()];
		tmps.toArray(rightTypes);
		return rightTypes;
	}
}
