package com.jun.plugin.demo;
//
//import javax.microedition.midlet.*;
//import javax.microedition.lcdui.*;
//
//public class Memory extends MIDlet implements CommandListener {
//	private Display display;
//	private Form props;
//	private Command exitCommand = new Command("Exit", Command.EXIT, 1);
//
//	public Memory() {
//		display = Display.getDisplay(this);
//	}
//
//	public void startApp() {
//		props = new Form("Runtime Information");
//		// ���ڴ�ռ��С
//		long total = Runtime.getRuntime().totalMemory();
//		// ʣ���ڴ�ռ��С
//		long free = Runtime.getRuntime().freeMemory();
//		props.append("total memory:" + total + "\n");
//		props.append("free memory:" + free + "\n");
//		props.append("used memory:" + (total-free) +"\n");
//
//		props.addCommand(exitCommand);
//		props.setCommandListener(this);
//		display.setCurrent(props);
//	}
//
//	public void commandAction(Command c, Displayable s) {
//		if (c == exitCommand) {
//			destroyApp(false);
//			notifyDestroyed();
//		}
//	}
//
//	public void destroyApp(boolean unconditional) {
//	}
//
//	public void pauseApp() {
//		display.setCurrent(null);
//		props = null;
//	}
//}