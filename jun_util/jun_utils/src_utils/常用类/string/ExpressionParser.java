package book.string;

/**
 * 一个简单的表达式解析器，这个解析器可以计算由数字、运算符和括号组成的表达式的值，并能处理变量，
 * 为了处理简单，本解析器只支持一个字母的变量，不区分变量字母的大小写。因此，最多只能存储26个变量。
 * 如果用户的变量名长度大于一个字母，则只取第一个字母当作变量。
 */
public class ExpressionParser {

	// 4种标记类型
	/**	标记为空或者结束符	*/
	public static final int NONE_TOKEN = 0;
	/**	标记为分隔符*/
	public static final int DELIMITER_TOKEN = 1;
	/**	标记为变量*/
	public static final int VARIABLE_TOKEN = 2;
	/**	标记为数字*/
	public static final int NUMBER_TOKEN = 3;

	// 4种错误类型
	/**	语法错误	*/
	public static final int SYNTAX_ERROR = 0;
	/**	括号没有结束错误	*/
	public static final int UNBALPARENS_ERROR = 1;
	/**	表达式为空错误	*/
	public static final int NOEXP_ERROR = 2;
	/**	被0除错误	*/
	public static final int DIVBYZERO_ERROR = 3;
	//针对四种错误类型，定义的四个错误提示
	public static final String[] ERROR_MESSAGES = { "Syntax Error", "Unbalanced Parentheses",
			"No Expression Present", "Division by Zero" };
	/**	表达式的结束标记*/
	public static final String EOE = "\0";
	
	/**	表达式字符串*/
	private String exp;  
	/**	解析器当前指针在表达式中的位置*/
	private int expIndex;   
	/**	解析器当前处理的标记*/
	private String token;  
	/**	解析器当前处理标记的类型*/
	private int tokenType; 

	/**	变量的数组*/  
	private double vars[] = new double[26];

	/**
	 * 解析一个表达式，返回表达式的值。
	 * @param expStr	表达式字符串
	 * @return
	 * @throws Exception
	 */  
	public double evaluate(String expStr) throws Exception {
		double result;
		this.exp = expStr;
		this.expIndex = 0;

		//获取第一个标记
		this.getToken();
		if (this.token.equals(EOE)){
			//没有表达式异常
			this.handleError(NOEXP_ERROR); // no expression present
		}

		//处理赋值语句
		result = this.parseAssign();
		//处理完赋值语句，应该就是表达式结束符，如果不是，则返回异常
		if (!this.token.equals(EOE)){  
			this.handleError(SYNTAX_ERROR);
		}

		return result;
	}

	/**
	 * 处理赋值语句
	 */  
	private double parseAssign() throws Exception {
		double result;//结果
		int varIndex;//变量下标
		String oldToken;//旧标记
		int oldTokenType;//旧标记的类型

		//如果标记类型是变量
		if (this.tokenType == VARIABLE_TOKEN) {
			// 保存当前标记  
			oldToken = new String(this.token);
			oldTokenType = this.tokenType;
			// 取得变量的索引，本解析器只支持一个字目的变量，
			//如果用户的变量字母长度大于1，则取第一个字母当作变量  
			varIndex = Character.toUpperCase(this.token.charAt(0)) - 'A';
			
			//获得下一个标记
			this.getToken();
			//如果当前标记不是等号=
			if (!this.token.equals("=")) {
				//回滚
				this.putBack();  
				// 不是一个赋值语句，将标记恢复到上一个标记  
				this.token = new String(oldToken);
				this.tokenType = oldTokenType;
			} else {
				//如果当前标记是等号=，即给变量赋值，形式如a = 3 + 5;
				//则计算等号后面表达式的值，然后将得到的值赋给变量
				this.getToken();
				//因为加减法的优先级最低，所以计算加减法表达式。
				result = this.parseAddOrSub();
				//将表达式的值赋给变量，并存在实例变量vars中。
				this.vars[varIndex] = result;
				//返回表达式的值
				return result;
			}
		}
		//如果当前标记类型不是变量，或者不是赋值语句，则用加减法计算表达式的值。
		return this.parseAddOrSub();
	}

	/**
	 * 计算加减法表达式
	 */ 
	private double parseAddOrSub() throws Exception {
		char op;//操作符
		double result;//结果
		double partialResult;//子表达式的结果
		//用乘除法计算当前子表达式的值
		result = this.parseMulOrDiv();
		//如果当前标记的第一个字母是加减号，则继续进行加减法运算。
		while ((op = this.token.charAt(0)) == '+' || op == '-') {
			//取下一个标记
			this.getToken();
			//用乘除法计算当前子表达式的值
			partialResult = this.parseMulOrDiv();
			switch (op) {
			case '-':
				//如果是减法，则用已处理的子表达式的值减去当前子表达式的值
				result = result - partialResult;
				break;
			case '+':
				//如果是加法，用已处理的子表达式的值加上当前子表达式的值
				result = result + partialResult;
				break;
			}
		}
		return result;
	}

	/**
	 * 计算乘除法表达式，包括取模运算
	 */ 
	private double parseMulOrDiv() throws Exception {
		char op;//操作符
		double result;//结果
		double partialResult;//子表达式的结果
		//用指数运算计算当前子表达式的值
		result = this.parseExponent();
		//如果当前标记的第一个字母是乘、除或者取模运算符，则继续进行乘除法运算。
		while ((op = this.token.charAt(0)) == '*' || op == '/' || op == '%') {
			//取下一个标记
			this.getToken();
			//用指数运算计算当前子表达式的值
			partialResult = this.parseExponent();
			switch (op) {
			case '*':
				//如果是乘法，则用已处理子表达式的值乘以当前子表达式的值
				result = result * partialResult;
				break;
			case '/':
				//如果是除法，先判断当前子表达式的值是否为0，如果为0，则抛出被0除异常
				//除数不能为0
				if (partialResult == 0.0){
					this.handleError(DIVBYZERO_ERROR);
				}
				//除数不为0，则进行除法运算
				result = result / partialResult;
				break;
			case '%':
				//如果是取模运算，也要判断当前子表达式的值是否为0
				//如果为0，则抛出被0除异常
				if (partialResult == 0.0){
					this.handleError(DIVBYZERO_ERROR);
				}
				//进行取模运算
				result = result % partialResult;
				break;
			}
		}
		return result;
	}

	/**
	 * 计算指数表达式
	 * @throws Exception
	 */  
	private double parseExponent() throws Exception {
		double result;//结果
		double partialResult;//子表达式的值
		double ex;//指数的底数
		int t;//指数的幂

		//用一元运算计算当前子表达式的值（底数）
		result = this.parseUnaryOperator();
		//如果当前标记为"^"运算符，则为指数计算
		if (this.token.equals("^")) {
			//获取下一个标记，即获取指数的幂
			this.getToken();
			partialResult = this.parseExponent();
			ex = result;
			if (partialResult == 0.0) {
				//如果指数的幂为0，则指数的值为1
				result = 1.0;
			} else {
				//否则，指数的值为个数为指数幂的底数相乘的结果。
				for (t = (int) partialResult - 1; t > 0; t--){
					result = result * ex;
				}
			}
		}
		return result;
	}

	/**
	 * 计算一元运算，+，-，表示正数和复数
	 */  
	private double parseUnaryOperator() throws Exception {
		double result;//结果
		String op;//操作符

		op = "";
		//如果当前标记类型为分隔符，而且分隔符的值等于+或者-。
		if ((this.tokenType == DELIMITER_TOKEN) && 
				this.token.equals("+") || this.token.equals("-")) {
			op = this.token;
			this.getToken();
		}
		//用括号运算计算当前子表达式的值
		result = this.parseBracket();
		if (op.equals("-")){
			//如果操作符为-，则表示负数，将子表达式的值变为负数
			result = -result;
		}

		return result;
	}

	/**
	 * 计算括号运算
	 */  
	private double parseBracket() throws Exception {
		double result;//结果
		//如果当前标记为左括号，则表示是一个括号运算
		if (this.token.equals("(")) {
			//取下一个标记
			this.getToken();
			//用加减法运算计算子表达式的值
			result = this.parseAddOrSub();
			//如果当前标记不等于右括号，抛出括号不匹配异常
			if (!this.token.equals(")")){
				this.handleError(UNBALPARENS_ERROR);
			}
			//否则取下一个标记
			this.getToken();
		} else {
			//如果当前标记不是左括号，表示不是一个括号运算，则用原子元素运算计算子表达式的值
			result = this.parseAtomElement();
		}

		return result;
	}

	/**
	 * 计算原子元素运算，包括变量和数字
	 * @return
	 * @throws Exception
	 */  
	private double parseAtomElement() throws Exception {
		double result = 0.0;//结果
		
		switch (this.tokenType) {
		case NUMBER_TOKEN:
			//如果当前标记类型为数字
			try {
				//将数字的字符串转换成数字值
				result = Double.parseDouble(this.token);
			} catch (NumberFormatException exc) {
				this.handleError(SYNTAX_ERROR);
			}
			//取下一个标记
			this.getToken();
			break;
		case VARIABLE_TOKEN:
			//如果当前标记类型是变量，则取变量的值
			result = this.findVar(token);
			this.getToken();
			break;
		default:
			this.handleError(SYNTAX_ERROR);
			break;
		}
		return result;
	}

	/**
	 * 根据变量名获取变量的值，如果变量名长度大于1，则只取变量的第一个字符
	 * @param vname	变量名
	 * @throws Exception
	 */ 
	private double findVar(String vname) throws Exception {
		//如果变量的第一个字符不是字母，则抛出语法异常
		if (!Character.isLetter(vname.charAt(0))) {
			handleError(SYNTAX_ERROR);
			return 0.0;
		}
		//从实例变量数组vars中取出该变量的值
		return vars[Character.toUpperCase(vname.charAt(0)) - 'A'];
	}

	/**
	 * 回滚，将解析器当前指针往前移到当前标记位置
	 */
	private void putBack() {
		if (this.token == EOE){
			return;
		}
		//解析器当前指针往前移动
		for (int i = 0; i < this.token.length(); i++) {
			this.expIndex--;
		}
	}

	/**
	 * 处理异常情况
	 * @param errorType	错误类型
	 * @throws Exception
	 */
	private void handleError(int errorType) throws Exception {
		//遇到异常情况时，根据错误类型，取得异常提示信息，将提示信息封装在异常中抛出
		//可以考虑用自定义异常，而不用Exception
		throw new Exception(ERROR_MESSAGES[errorType]);
	}

	/**
	 * 获取下一个标记
	 */  
	private void getToken() {
		//初始值
		this.tokenType = NONE_TOKEN;
		this.token = "";

		// 检查表达式是否结束
		// 如果解析器当前指针已经到达了字符串的长度，则表明表达式已经结束，置当前标记的置为EOE
		if (this.expIndex == this.exp.length()) {
			this.token = EOE;
			return;
		}

		// 跳过表达式中的空白符 
		while (this.expIndex < this.exp.length()
				&& Character.isWhitespace(this.exp.charAt(this.expIndex))) {
			++this.expIndex;
		}

		// 再次检查表达式是否结束 
		if (this.expIndex == this.exp.length()) {
			this.token = EOE;
			return;
		}
		
		//取得解析器当前指针指向的字符
		char currentChar = this.exp.charAt(this.expIndex);
		//如果当前字符是一个分隔符，则认为这是一个分隔符标记，给当前标记和标记类型赋值，并将指针后移
		if (isDelim(currentChar)) {  
			this.token += currentChar;
			this.expIndex++;
			this.tokenType = DELIMITER_TOKEN;
			
		} else if (Character.isLetter(currentChar)) {
			//如果当前字符是一个字母，则认为是一个变量标记。
			//将解析器指针往后移，直到遇到一个分隔符，之间的字符都是变量的组成部分
			while (!isDelim(currentChar)) {
				this.token += currentChar;
				this.expIndex++;
				if (this.expIndex >= this.exp.length()) {
					break;
				} else {
					currentChar = this.exp.charAt(this.expIndex);
				}
			}
			//设置标记类型为变量
			this.tokenType = VARIABLE_TOKEN;
			
		} else if (Character.isDigit(currentChar)) { 
			// 如果当前字符是一个数字，则认为当前标记的类型为数字
			// 将解析器指针往后移，直到遇到一个分隔符，之间的字符都是该数字的组成部分
			while (!isDelim(currentChar)) {
				this.token += currentChar;
				this.expIndex++;
				if (this.expIndex >= this.exp.length()){
					break;
				} else {
					currentChar = this.exp.charAt(this.expIndex);
				}
			}
			//设置标记类型为数字
			this.tokenType = NUMBER_TOKEN;
		} else { 
			//无法识别的字符，则认为表达式结束
			this.token = EOE;
			return;
		}
	}

	/**
	 * 判断一个字符是否为分隔符。
	 * 表达式中的字符包括: 
	 * 加"+"、减"-"、乘"*"、除"/"、取模"%"、指数"^"、赋值"="、左括号"("、右括号")"
	 * @param c
	 * @return
	 */ 
	private boolean isDelim(char c) {
		if ((" +-/*%^=()".indexOf(c) != -1))
			return true;
		return false;
	}

	public static void main(String[] args) throws Exception {
		ExpressionParser test = new ExpressionParser();
		String exp1 = "a = 5.0";
		System.out.println("exp1(\"a = 4.0\") = " + test.evaluate(exp1));
		String exp2 = "b = 3.0";
		System.out.println("exp2(\"b = 3.0\") = " + test.evaluate(exp2));

		String exp3 = "(a+b) * (a-b)";
		System.out.println("exp3(\"(a+b) * (a-b)\") = " + test.evaluate(exp3));

		String exp4 = "3*5-4/2";
		System.out.println("exp4(\"3*5-4/2\") = " + test.evaluate(exp4));

		String exp5 = "(4-2)*((a+b)/(a-b))";
		System.out.println("exp5(\"(4-2)*((a+b)/(a-b))\") = " + test.evaluate(exp5));
		
		String exp6 = "5 % 2";
		System.out.println("exp6(\"5%2\") = " + test.evaluate(exp6));
		String exp7 = "3^2 * 5 + 4";
		System.out.println("exp7(\"3^2 * 5 + 4\") = " + test.evaluate(exp7));
		
		/**
		 * 一个简单的表达式根据运算时的优先级从高到底为：
		 * （1）原子元素表达式，包括数字和变量；
		 * （2）括号表达式；
		 * （3）一元表达式，取数的负数；
		 * （4）指数表达式；
		 * （5）乘、除、取模表达式；
		 * （6）加、减表达式
		 * （7）赋值表达式；
		 * 因此，在计算一个表达式的值时，应该按优先级从高到底进行运算。
		 * 在本程序中，每个优先级的表达式的运算都用一个私有方法实现。在私有方法中，首先计算更高优先级的表达式。
		 * 即采用了类似递归调用的方式，尽管在evaluate方法中最先调用的是优先级最低的表达式的值，
		 * 但在实质上却是优先级最高的表达式的私有方法最先被执行完。这就保证了表达式的运算是按照优先级从高到底的顺序执行的。
		 */
	}
}
