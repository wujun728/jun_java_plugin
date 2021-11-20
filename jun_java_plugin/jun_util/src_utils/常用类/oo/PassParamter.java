package book.oo;

public class PassParamter {

	public PassParamter() {
	}

	public void methodA(ComplexNumber comNum) {
		// comNum这个引用指向了一个新new出来的对象。
		comNum = new ComplexNumber(1, 2);
	}

	public void methodB(ComplexNumber comNum) {
		// comNum这个引用指向的还是同一个对象，但是，这里修改该对象的值。
		comNum.setRealPart(1);
		comNum.setRealPart(2);
	}

	public void methodC(int num) {
		// 将num的值加1
		num++;
	}

	public static void main(String[] args) {

		PassParamter test = new PassParamter();
		ComplexNumber comNum = new ComplexNumber(5, 5);
		System.out.println("调用methodA方法之前，comNum: " + comNum.toString());
		test.methodA(comNum);
		System.out.println("调用methodA方法之后，comNum: " + comNum.toString());
		System.out.println("调用methodB方法之前，comNum: " + comNum.toString());
		test.methodB(comNum);
		System.out.println("调用methodB方法之后，comNum: " + comNum.toString());
		int num = 0;
		System.out.println("调用methodC方法之前，num: " + num);
		test.methodC(num);
		System.out.println("调用methodC方法之后，num: " + num);

//		 调用methodA方法之前，comNum: 5.0 + 5.0i
//		 调用methodA方法之后，comNum: 5.0 + 5.0i
//		 调用methodB方法之前，comNum: 5.0 + 5.0i
//		 调用methodB方法之后，comNum: 2.0 + 5.0i
//		 调用methodC方法之前，num: 0
//		 调用methodC方法之后，num: 0

		/**
		 * Java的参数传递策略：
		 * 0，所谓传值的意思是：在调用方法时，将参数的值复制一份，方法里面用复件，方法外面用原件，复件改变了，不影响原件；原件改变了，不影响复件。
		 * 1，对于基本数据类型，比如int，long等类型，采用传值的策略。将参数的值复制一份后传给方法，方法可以改变参数值的复件，但不会改变参数值的原件。
		 * 2，对于对象类型，也采用的传值策略，不过，是将参数的引用复制（2个引用指向同一个对象）一份传给方法，方法可以改变参数引用的复件，但不会改变参数引用的原件。
		 * 
		 * 解释三个输出：
		 * 1，调用methodA方法时，首先将comNum对象的引用复制一份，将引用的复件传递给methodA方法，main方法保持引用的原件，此时，引用原件和引用复件都指向comNum对象。
		 * 执行comNum = new ComplexNumber(1,2);语句后，将引用复件指向了另外一个新的对象，此时，引用复件和引用原件指向的是不同的对象。回到main方法时，操作的仍然是引用原件指向的comNum对象。
		 * 2，调用methodB方法时，首先将comNum对象的引用复制一份，将引用的复件传递给methodB方法，main方法保持引用的原件，此时，有引用原件和引用复件都指向comNum对象。
		 * 执行comNum.setRealPart(1);comNum.setRealPart(2);语句后，修改了引用复件所指对象的内容，而此时，引用复件和引用原件指向的是同一个对象，因此，引用原件指向的对象的内容也改变了。回到main方法时，comNum对象也改变了。
		 * 3，调用methodC方法时，首先将num的值复制一份，将值的复件传递给methodC方法，main方法保持值的原件。 执行num
		 * ++;语句后，复件的值会加1。回到main方法时，操作的仍然是原件，值保持不变。
		 */

	}
}
