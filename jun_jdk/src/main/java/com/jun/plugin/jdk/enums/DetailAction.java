package com.jun.plugin.jdk.enums;

/**
 *定义枚举类型其实就是在定义一个类，只不过很多细节由编译器帮你补齐了，所以，某种程度上enum关键词的作用就像是class或interface.

   当使用enum定义枚举类型时，实际上所定义出来的类型是继承自java.lang.Enum类。而每个被枚举的成员其实就是定义的枚举类型的一个实例，
   它们都被默认为final。无法改变常数名称所设定的值，它们也是public和static的成员，这与接口中的常量限制相同。可以通过类名称直接使用它们。
   如1中所定义的枚举类型Action,TURN_LEFT,TURN_RIGHT,SHOOT都是Action的一个对象实例。因为是对象，所以，对象上自然有一些方法可以调用。
   如从Object继承焉的toString()方法被重新定义了，可以让你直接取得枚举值的字符串描述；values()方法可以让您取得所有的枚举成员实例，
   并以数组方式返回。您可以使用这两个方法来简单的将Action的枚举成员显示出来。静态valueOf()方法可以让您将指定的字符串尝试转换为枚举类型。
   可以用compareTo()方法来比较两个枚举对象在枚举时的顺序。-1之前，0位置相同，1之后。
   
   对于每个枚举成员，使用ordinal()方法，依枚举顺序得到位置索引，默认以0开始。

 * @author Wujun
 * @since   2013年9月9日 下午5:24:43
 */
public enum DetailAction {
	TURN_LEFT, TURN_RIGHT, SHOOT;

	public String getDescription() {
		switch (this.ordinal()) {
		case 0:
			return "向左转";
		case 1:
			return "向右转";
		case 2:
			return "射击";
		default:
			return null;
		}
	}
}