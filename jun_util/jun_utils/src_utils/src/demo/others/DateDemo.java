package demo.others;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateDemo {
	public static void main(String[] args) {
		Date d = new Date();
		//输出格式：dow mon dd hh:mm:ss zzz yyyy
		//表示      ：星期  月    日期  时   分   秒    时区 年
		System.out.println(d);
		//Format 是一个用于格式化语言环境敏感的信息（如日期、消息和数字）的抽象基类。
		Format format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss ");
		System.out.println(format.format(d));
	}
}
