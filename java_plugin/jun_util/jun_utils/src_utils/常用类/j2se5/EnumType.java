package book.j2se5;
/**
 * J2SE 5.0的枚举类型
 * Enum作为Sun全新引进的一个关键字，看起来很象是特殊的class, 它也可以有自己的变量，
 * 可以定义自己的方法，可以实现一个或者多个接口。具有如下特点：
 * （1）不能有public的构造函数，这样做可以保证客户代码没有办法新建一个enum的实例。
 * （2）所有枚举值都是public , static , final的。注意这一点只是针对于枚举值，
 * 可以和在普通类里面定义变量一样定义其它任何类型的非枚举变量，这些变量可以用任何修饰符。
 * （3）变量和方法定义必须放在所有枚举值定义的后面。
 */
public class EnumType {

	/**
	 * 人Person的枚举类型
	 */
	enum Person{
		CHINESE,   // 中国人
		AMERICAN,  // 美国人
		ENGLISHMAN;// 英国人
	}

	public static void main(String[] args) {
		System.out.println("Person枚举值的数目：" + Person.values().length);
		// 遍历枚举类型中所有的值
		System.out.println("Person枚举值如下：");
		Person[] ps = Person.values();
		for (Person p : ps){
			System.out.print(p + "   ");
		}
		System.out.println();
		Person p = Person.CHINESE;
		// 比较枚举值
		if (p == Person.CHINESE){
			System.out.println("p'value equals Person.CHINESE");
		}
		// 使用valueOf获得字符串描述的枚举值
		 p = Person.valueOf("AMERICAN");
		// 在Switch中使用枚举值
		switch(p){
		case CHINESE: 
			System.out.println("p is Chinese");
			break;
		case AMERICAN: 
			System.out.println("p is American");
			break;
		case ENGLISHMAN: 
			System.out.println("p is Englishman");
			break;
		}
		
		// 取得枚举值在枚举类型中声明的顺序
		System.out.println("AMERICAN的序号：" + Person.AMERICAN.ordinal());
		System.out.println("CHINESE的序号：" + Person.CHINESE.ordinal());
		System.out.println("ENGLISHMAN的序号：" + Person.ENGLISHMAN.ordinal());
		System.out.println();
		
		// 使用更复杂的枚举类型ComplexPerson
		ComplexPerson cp = ComplexPerson.CHINESE;
		// 因为为CHINESE枚举值覆盖了toString方法，所以调用的是CHINESE的toString方法
		System.out.println("cp.toString(): "+ cp);
		cp = ComplexPerson.AMERICAN;
		// 因为没有为AMERICAN枚举值覆盖toString方法，所以调用默认的toString方法
		System.out.println("cp.toString(): "+ cp);
		// 调用枚举值的方法
		cp = ComplexPerson.OTHER;
		System.out.println("cp.getValue(): "+ cp.getValue());
	}
	/**
	 * 一个更加复杂的枚举类型
	 */
	enum ComplexPerson{
		// 枚举值
		// CHINESE的value属性为"中国人"
		CHINESE("中国人"){
			public String toString(){
				return "这是个中国人";
			}},
		AMERICAN("美国人"), 
		ENGLISHMAN("英国人"){
			public String toString(){
				return "这是个英国人";
		}},
		OTHER{
			public String toString(){
				return "这是个其他国家的人";
		}};
		
		// 枚举值的value属性，只能声明在枚举值的后面
		private String value = null;
		// 默认构造方法
		ComplexPerson(){
			value = "其他人";
		}
		// 带参数的构造方法
		ComplexPerson(String value){
			this.value = value;
		}
		// 获取value属性
		public String getValue(){
			return this.value;
		}
	}
}