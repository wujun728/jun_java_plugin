

import java.text.DateFormat;
import java.text.NumberFormat;
import java.util.Date;
import java.util.Locale;
import java.util.ResourceBundle;

import org.junit.Test;

public class App {

	// ��ʻ� - ��̬���
	@Test
	public void testI18N() throws Exception {
		
		// �������Ի���
		Locale locale = Locale.US;
		
		// �������������ResourceBundle
		ResourceBundle bundle = ResourceBundle.getBundle("cn.itcast.f_i18n.msg", locale);
		// ���key��ȡ�����ļ��е�ֵ
		System.out.println(bundle.getString("hello"));
		System.out.println(bundle.getString("username"));
		System.out.println(bundle.getString("pwd"));
		
	}
	
	// ��ʻ� - ��̬�ı� - 0. ����
	@Test
	public void testI18N2() throws Exception {
		// ��ʻ�����
		NumberFormat.getCurrencyInstance();
		// ��ʻ�����
		NumberFormat.getNumberInstance();
		// ��ʻ��ٷֱ�
		NumberFormat.getPercentInstance();  
		// ��ʻ�����
		//DateFormat.getDateTimeInstance(dateStyle, timeStyle, aLocale)
	}
	
	// ��ʻ� - ��̬�ı� - 1. ��ʻ�����
	@Test
	public void testI18N3() throws Exception {
		// ģ�����Ի���
		Locale locale = Locale.CHINA;
		// ���׼��
		double number = 100;
		// ������
		NumberFormat nf = NumberFormat.getCurrencyInstance(locale);
		// ��ʻ�����
		String m = nf.format(number);
		// ����
		System.out.println(m);
	}
	
	//�����⣺  ������㣺  $100 * 10  
	@Test
	public void eg() throws Exception {
		String str = "$100";
		int num = 10;
		
		// 1. ����strֵ����һ��ҵĻ���
		Locale us = Locale.US;
		// 2. ��ʻ�������
		NumberFormat nf = NumberFormat.getCurrencyInstance(us);
		// 3. ����str���
		Number n = nf.parse(str);
		
		System.out.println(n.intValue() * num);
	}
	
	// ��ʻ� - ��̬�ı� - 2. ��ʻ���ֵ
	@Test
	public void testI18N4() throws Exception {
		// ģ�����Ի���
		Locale locale = Locale.CHINA;
		NumberFormat nf = NumberFormat.getNumberInstance(Locale.US);
		String str = nf.format(1000000000);
		System.out.println(str);
	}
	
	// ��ʻ� - ��̬�ı� - 3. ��ʻ�����
	/*
	 * ����
	 * 	  FULL   2015��3��4�� ������
	 * 	  LONG   2015��3��4��
	 * 	  FULL   2015��3��4�� ������
	 *    MEDIUM 2015-3-4
	 *    SHORT  15-3-4
	 *    
	 * ʱ��
	 * 	  FULL   ����04ʱ31��59�� CST
	 * 	  LONG   ����04ʱ32��37��
	 *    MEDIUM 16:33:00
	 *    SHORT  ����4:33
	 *    
	 * 
	 */
	@Test
	public void testI18N5() throws Exception {
		
		// ���ڸ�ʽ
		int dateStyle = DateFormat.SHORT;
		// ʱ���ʽ
		int timeStyle = DateFormat.SHORT;
		
		// ������
		DateFormat df = 
			DateFormat.getDateTimeInstance(dateStyle, timeStyle, Locale.CHINA);		
		String date = df.format(new Date());
		
		System.out.println(date);
	}
	
	// ����2�� �뽫ʱ��ֵ��09-11-28 ����10ʱ25��39�� CST�����������һ��date����
	@Test
	public void eg2() throws Exception {
		String str = "09-11-28 ����10ʱ25��39�� CST";
		// ����DateFormat�����࣬��ʻ�����
		DateFormat df = DateFormat.getDateTimeInstance(
				DateFormat.SHORT, DateFormat.FULL, Locale.getDefault());
		Date d = df.parse(str);
		
		System.out.println(d);
	}
	
}







