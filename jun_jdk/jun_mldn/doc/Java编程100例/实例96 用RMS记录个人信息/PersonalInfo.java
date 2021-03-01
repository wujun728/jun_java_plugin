// 用RMS记录个人信息
import javax.microedition.lcdui.*;
import javax.microedition.midlet.*;
import javax.microedition.rms.*;
import java.util.Enumeration;
import java.util.*;

public class PersonalInfo extends MIDlet implements CommandListener {
	RecordStore recordStore =null;
	Display display = null;  // 设备的显示器
	List list = null;	
	TextField nameField;  // 姓名文本域
	TextField phoneField;  //电话号码文本域
	TextField emailField;  //电子邮件文本域
	
	String name, phone, email;
	private int[] recID;
	boolean isAdd;  // 值为true时为增加记录，否则为修改记录
	
	Ticker ticker = new Ticker("个人信息  ");  //实例化Ticker对象
	static final Command EXIT = new Command("退出",Command.STOP,1); //实例化命令
	static final Command BACK = new Command("返回",Command.BACK,2);
	static final Command VIEW = new Command("查看和修改记录",Command.OK,3);
	static final Command ADD = new Command("添加新记录",Command.OK,3);
	static final Command DEL = new Command("删除当前记录",Command.OK,3);
	static final Command DEL_ALL = new Command("删除所有记录",Command.OK,3);
	static final Command SAVE = new Command("保存",Command.OK,3);
	
	public PersonalInfo() {
	}

	// 重载抽象类MIDlet的抽象方法startApp()
	public void startApp() {
		display = Display.getDisplay(this); //获取显示器
		try {
			recordStore = RecordStore.openRecordStore("PersonalInfo", true); //实例化记录存储区
		} catch(Exception e) {
		}
        list = new List("个人信息",Choice.IMPLICIT); //实例化列表
		list.addCommand(EXIT); //增加控制命令
		list.addCommand(ADD);
		list.setCommandListener(this);
		list.setTicker(ticker);        
        list.setCommandListener(this);
		listRec();
	}
	
	// 重载抽象类MIDlet的方法pauseApp()
	public void pauseApp() {
	}
	
	// 重载抽象类MIDlet的方法destroyApp()
	public void destroyApp(boolean unconditional) {
		
	}

	public void listRec() {
        list.deleteAll(); //删除列表元素
        try {
			int numRec = recordStore.getNumRecords(); //获取记录数
			if(numRec > 0) {
				list.addCommand(VIEW);
				list.addCommand(DEL);
				list.addCommand(DEL_ALL);
				recID = new int [numRec];
				RecordEnumeration re = recordStore.enumerateRecords(null,null,true); //记录枚举对象
				int i = 0;
				while(re.hasNextElement()) { //遍历记录
					recID[i] = re.nextRecordId();
					name = PersonalRecord.getName(recordStore.getRecord(recID[i]));  //获取名称
					list.append(name,null); //增加列表元素
					i++;
				}
			} else {
				list.removeCommand(VIEW);
				list.removeCommand(DEL);
				list.removeCommand(DEL_ALL);				
			}
        } catch (RecordStoreException rse) {
        }
		display.setCurrent(list);
	 }
	
	public void viewRecScreen() {  //显示记录信息
		Form view = new Form("个人信息"); //创建表单 
        nameField = new TextField("姓名:",name,20,TextField.ANY);  //创建文本域
        phoneField = new TextField("电话:",phone,20,TextField.NUMERIC);
        emailField = new TextField("E-Mail:",email,20,TextField.EMAILADDR);
        view.append(nameField);  //增加文本域到表单
        view.append(phoneField); 
        view.append(emailField);
        view.addCommand(BACK);
        view.addCommand(SAVE);
        view.setCommandListener(this);
        display.setCurrent(view); //设置当前显示屏幕
	}
	
	public void viewRec() {
    	isAdd = false;
    	try {
    		byte[] b = recordStore.getRecord(recID[list.getSelectedIndex()]);
	    	name = PersonalRecord.getName(b); //获取名称
	        phone = PersonalRecord.getPhone(b); //获取电话
	        email = PersonalRecord.getEmail(b); //获取电子邮件
	    } catch(Exception e) {
	    } 
	    viewRecScreen(); //显示信息
	}
	
	public void addRec() {  //增加记录
    	isAdd = true;
    	name = null;
        phone = null;
        email = null;
	    viewRecScreen();
	}
	
	public void saveRec() { //存储记录
		name = nameField.getString();  //获取用户输入 
		phone = phoneField.getString();
		email = emailField.getString();
       	if (!(name.trim().equals(""))) {       		
	       	byte[] b = PersonalRecord.createRecord(name,phone,email); //创建一条记录
	    	try {
	    		if(isAdd) {
	    			recordStore.addRecord(b,0,b.length); //增加记录
		    	} else {
	        		recordStore.setRecord(recID[list.getSelectedIndex()],b,0,b.length); //修改记录
	        	}
		    } catch(Exception e) {
		    }
		 }
	}
	
	public void delRec(int i) { 
    	try{
	        recordStore.deleteRecord(i); //删除记录
    	} catch(Exception e) {
    	}		
	}
	
	public void delAllRec() {  //删除所有记录
        try {
            String dbName = recordStore.getName();
            recordStore.closeRecordStore();  //关闭记录存储区
            RecordStore.deleteRecordStore(dbName); //删除记录存储区
        } catch (RecordStoreException rsnoe) {
        }
	}
	
	// 实现接口CommandListener的方法
	public void commandAction(Command c, Displayable d) {
        if (c == ADD) {
        	addRec();
        } else if (c == List.SELECT_COMMAND || c == VIEW) {
			viewRec();
        } else if(c == DEL) {
        	delRec(recID[list.getSelectedIndex()]);
        	listRec();
        } else if (c == DEL_ALL) {
	        delAllRec();
        	startApp();
		} else if (c == EXIT) {
        	destroyApp(false);
        	notifyDestroyed();
        } else if (c == BACK) {
            display.setCurrent(list);
        } else if (c == SAVE) { 
        	saveRec();
        	listRec();
        }  
	}	
}