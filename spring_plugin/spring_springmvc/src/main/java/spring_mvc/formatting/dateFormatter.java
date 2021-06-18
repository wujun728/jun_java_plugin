package spring_mvc.formatting;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import org.springframework.format.Formatter;

public class dateFormatter implements Formatter<Date> {
	private DateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	@Override
	public String print(Date object, Locale locale) {
		// TODO Auto-generated method stub
		System.out.println(locale.getCountry());
		System.out.println(locale.getLanguage());
		return df.format(object);
	}

	@Override
	public Date parse(String text, Locale locale) throws ParseException {
		// TODO Auto-generated method stub
		System.out.println(locale.getCountry());
		System.out.println(locale.getLanguage());
		return df.parse(text);
	}
	
}
