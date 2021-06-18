package com.jun.plugin.mvel;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * 造轮子
 * 四则运算
 * @author xuyuji
 *
 */
public class Wheel {
	public double execute(String expression){
		//简单demo，不做表达式正确性校验
		return executeRPN(toRPN(toList(expression)));
	}
	
	/**
	 * 将表达式拆分成数字和操作符的节点
	 * @param expression
	 * @return
	 */
	private List<String> toList(String expression){
		List<String> list = new ArrayList<String>();
		
		// '('后接'-'是负数，而开头'-'也是负数，为了统一处理最外层添加括号，在函数末尾再去掉括号。
		String str = "(" + expression + ")";
		for(int start = 0, end = 0; end < str.length(); end++){
			String s = str.substring(end, end + 1);
			if(isOperator(s) && !( "-".equals(s) && "(".equals(str.substring(end - 1, end)) )){
				//判断是否是符号，并排除-是负数开头的情况
				
				if(end > start){
					//数字
					list.add(str.substring(start, end));
				}
				list.add(s);
				start = end + 1;
			}
		}
		
		//去除最外层括号
		list.remove(list.size() - 1);
		list.remove(0);
		return list;
	}
	
	/**
	 * 转换成逆波兰式
	 * @param list
	 * @return
	 */
	private List<String> toRPN(List<String> list){
		List<String> listRPN = new ArrayList<String>();
		Stack<String> operator = new Stack<String>();
		
		for(String s : list){
			if(isOperator(s)){
				if("(".equals(s)){
					operator.push(s);
				}else if(")".equals(s)){
					//碰到右括号则将该对括号中全部出栈
					String t;
					while( !(t = operator.pop()).equals("(") ){
						listRPN.add(t);
					}
				}else{
					//1.当前符号和栈顶符号对比优先级
					//2.若当前符号优先级高则当前符号入栈
					//3.反之出栈，再重复1直至达成2
					while(!operator.isEmpty() && getPriority(s) >= getPriority(operator.peek())){
						listRPN.add(operator.pop());
					}
					operator.push(s);
				}
			}else{
				listRPN.add(s);
			}
		}
		if(!operator.isEmpty()){
			for(int i = 0; i < operator.size(); i++){
				listRPN.add(operator.pop());
			}
		}
		
		return listRPN;
	}
	
	/**
	 * 计算逆波兰式
	 * @param listRPN
	 * @return
	 */
	private double executeRPN(List<String> listRPN){
		Stack<Double> values = new Stack<Double>();
		for(String s : listRPN){
			if(isOperator(s)){
				values.push(compute(values.pop(), values.pop(), s));
			}else{
				values.push(Double.parseDouble(s));
			}
		}
		return values.pop();
	}
	
	/**
	 * 判断是否是操作符
	 * @param value
	 * @return
	 */
	private static Boolean isOperator(String value) {
		return "(".equals(value) || ")".equals(value) || "+".equals(value) || "-".equals(value) || "*".equals(value) || "/".equals(value);
	}
	
	/**
	 * 优先级,返回值越小优先级越高
	 * @param s
	 * @return
	 */
	private int getPriority(String s){
		if("+".equals(s) || "-".equals(s)){
			return 2;
		}else if("*".equals(s) || "/".equals(s)){
			return 1;
		}else{
			return 3;
		}
	}
	
	/**
	 * 计算
	 * @param b
	 * @param a
	 * @param operator
	 * @return
	 */
	private double compute(Double b, Double a, String operator){
		if("+".equals(operator)){
			return a + b;
		}else if("-".equals(operator)){
			return a - b;
		}else if("*".equals(operator)){
			return a * b;
		}else if("/".equals(operator)){
			return a / b;
		}else{
			return 1.00;
		}
	}
	
	public static void main(String[] args) {
		String[] expressions = {"-5*(10/(2*4.5-4)+(-3/1.5+4)*(-2))/(-2/1-(-1))+12", 
								"(300*4-5/10)*1.234",
								"1+2+(-3*4)+5+(-6*7)"};
		Wheel wheel = new Wheel();
		for(String expression : expressions){
			double result = wheel.execute(expression);
			System.out.println(expression + " = " + result);
		}
	}
}
