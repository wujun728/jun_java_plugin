package net.jueb.util4j.test.lineDecode;

public enum BonusType {
	a(1),b(2),c(3),d(4),e(5),f(6),g(7),h(8),s(9);
	private int value;

	private BonusType(int value){
		this.value=value;
	}
	public int getValue()
	{
		return value;
	}
	
	public static BonusType valueOf(int value)
	{
		for(BonusType type:values())
		{
			if(type.getValue()==value)
			{
				return type;
			}
		}
		return null;
	}
	
	/**
	 * 倍率
	 * @param index 几连  
	 * @return
	 */
	public int odds(int index){
		int odds=1;
		switch(this){
			case a:
				switch(index){
					case 3:
						odds=50;
						break;
					case 4:
						odds=200;
						break;
					case 5:
						odds=1000;
						break;
					case -1:
						odds=2500;
						break;
					default:
						break;
				}
				break;
			case b:
				switch(index){
					case 3:
						odds=20;
						break;
					case 4:
						odds=80;
						break;
					case 5:
						odds=400;
						break;
					case -1:
						odds=1000;
						break;
					default:
						break;
				}
				break;
			case c:
				switch(index){
					case 3:
						odds=15;
						break;
					case 4:
						odds=40;
						break;
					case 5:
						odds=200;
						break;
					case -1:
						odds=500;
						break;
					default:
						break;
				}
				break;
			case d:
				switch(index){
					case 3:
						odds=10;
						break;
					case 4:
						odds=30;
						break;
					case 5:
						odds=160;
						break;
					case -1:
						odds=400;
						break;
					default:
						break;
				}
				break;
			case e:
				switch(index){
					case 3:
						odds=13;
						break;
					case 4:
						odds=20;
						break;
					case 5:
						odds=100;
						break;
					case -1:
						odds=250;
						break;
					default:
						break;
				}
				break;
			case f:
				switch(index){
					case 3:
						odds=5;
						break;
					case 4:
						odds=15;
						break;
					case 5:
						odds=60;
						break;
					case -1:
						odds=150;
						break;
					default:
						break;
				}
				break;
			case g:
				switch(index){
					case 3:
						odds=3;
						break;
					case 4:
						odds=10;
						break;
					case 5:
						odds=40;
						break;
					case -1:
						odds=100;
						break;
					default:
						break;
				}
				break;
			case h:
				switch(index){
					case 3:
						odds=2;
						break;
					case 4:
						odds=5;
						break;
					case 5:
						odds=20;
						break;
					case -1:
						odds=50;
						break;
					default:
						break;
				}
				break;
			case s:
				switch(index){
					case 3:
						odds=50;
						break;
					case 4:
						odds=200;
						break;
					case 5:
						odds=1000;
						break;
					case -1:
						odds=5000;
						break;
					default:
						break;
				}
				break;
			default:
				break;
		}
		return odds;
	}
	@Override
	public String toString() {
		return value+"";
	}
}
