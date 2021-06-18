import java.awt.*;
import java.applet.*;

public class Calculator extends Applet{
    TextField tfAnswer; //显示输入和结果的文本域
  	Button bPoint,bEqual,bPlus,bMinus,bClear,bMulti,bDivision; //运算符按钮
	Button[] b=new Button[10];  //数字按钮
  
  	String currentOp,preOp; //当前操作和上一步操作
	String foreText,backText; //当前输入和上一次输入
  	boolean isFloat = false; //运算类型标志
  
  	public void init() {
	  	Panel panel1=new Panel();  //实例化面板
		Panel panel2=new Panel();
		Panel panel3=new Panel();
		
	    currentOp = new String(""); //实例化各组件
	    preOp = new String("");
	    foreText = new String("");
	    backText = new String("");
	    tfAnswer = new TextField(8);
	    setBackground(Color.lightGray); //设置Applet背景色
	    setForeground(Color.blue);   //设置Applet前景色  	    
	    
		for(int i=9;i>=0;i--){ 
			b[i]=new Button(Integer.toString(i)); //实例化数字按钮
			panel2.add(b[i]); //增加按钮到面板
		}        
	 
	    bPoint = new Button("."); //实例化按钮
	    bEqual = new Button("=");
	    bEqual.setForeground(Color.red);   //设置按钮前景色
	    bClear = new Button("清除");
	    bClear.setForeground(Color.red);
	    bDivision = new Button("/");
	    bDivision.setForeground(Color.red);
	    bMulti = new Button("*");
	    bMulti.setForeground(Color.red);
	    bMinus = new Button("-");
	    bMinus.setForeground(Color.red);    
	    bPlus = new Button("+");
	    bPlus.setForeground(Color.red);    
	
		setLayout(new FlowLayout()); //设置布局管理器
		panel1.setLayout(new FlowLayout());
		panel2.setLayout(new GridLayout(4,3));
		panel3.setLayout(new GridLayout(4,1));
	    panel1.add(tfAnswer); //增加组件到面板
		panel1.add(bClear);
		panel2.add(bPoint);
		panel2.add(bEqual);
		panel3.add(bPlus);
		panel3.add(bMinus);
		panel3.add(bMulti);
		panel3.add(bDivision);
		add(panel1); //增加组件到Applet
		add(panel2);
		add(panel3);	
  	}
  
  	public boolean action(Event e, Object o) { //事件处理
    	String s = new String("");
     	for(int i=0;i<10;i++){
			if(e.target==b[i]||e.target==bPoint){ //按钮事件来自于数字按钮和点按钮
        		if(e.target != bPoint) {
          			s = (String)o;
          			doForeText(s); //处理输入
        		}
        		if((e.target == bPoint)&&(!isFloat)){ //浮点数输入
          			isFloat = true; //设置运算标志
         			s = (String)o;
          			if(foreText.equals("")){
           	 			foreText += "0.";  //增加小数点前面0
          			}
          			else{
            			doForeText(s);
          			}
        		}
      		}
      	}      
      	if(e.target == bClear) {
        	doClear();  //清除输入
      	}      
      	if((e.target == bMulti)||(e.target == bDivision)|| (e.target == bPlus)||(e.target == bMinus)) { //处理运算
        	if(foreText != ""){
         		currentOp = ((String)o);
          		doOperator();  //处理运算
        	}
        	else {
          		preOp = ((String)o);
          	}
      	}      
      	if(e.target == bEqual) { //等于按钮事件处理
        	doOperator(); //处理运算
      	}  
		return true;  
	}
  
  	public void doOperator(){
    	double dFore,dBack;
    	Double d;
    
    	if(preOp.equals("")) {
      		backText = foreText;
      		foreText = "";
      		tfAnswer.setText(backText); //显示文本
    	}
    	else {
      		dFore = (new Double(foreText)).doubleValue(); //得到第一输入
      		dBack = (new Double(backText)).doubleValue(); //得到第二输入
      		foreText = "";
      		backText = tfAnswer.getText(); 
      
	      	if(preOp.equals("+")) { //加运算处理
	        	d = new Double((dBack + dFore)); //得到运算结果
	        	tfAnswer.setText(d.toString());  //显示运算结果
	        	backText = d.toString();Double.parseDouble(
	      	}
	      	if(preOp.equals("-")) {
	        	d = new Double((dBack - dFore));//得到运算结果
	        	tfAnswer.setText(d.toString()); //显示运算结果
	        	backText = d.toString();
	      	}
	      	if(preOp.equals("*")) {
	        	d = new Double((dBack * dFore));//得到运算结果
	        	tfAnswer.setText(d.toString());//显示运算结果
	        	backText = d.toString();
	      	}
	      	if(preOp.equals("/")) {
	      		if (dFore==0){
	      			tfAnswer.setText("除数不能为0"); //显示出错信息
	      			return;
	      		}
	       		d = new Double((dBack / dFore));//得到运算结果
	        	tfAnswer.setText(d.toString());//显示运算结果
	        	backText = d.toString();
	      	}
    	}Math.sin();
    	preOp = currentOp;
  	}
  
  	public void doForeText(String s) {
    	foreText += s;
    	tfAnswer.setText(foreText); //显示输入
  	}
  
  	public void doBackText(String s){
    	backText = foreText;
    	foreText = "";
    	tfAnswer.setText(foreText); //显示输入
  	}
  
 	public void doClear() { //清除输入
    	currentOp = "";
    	preOp = "";
    	foreText = "";
    	backText = "";
    	isFloat = false;
    	tfAnswer.setText(""); 
  	}
}