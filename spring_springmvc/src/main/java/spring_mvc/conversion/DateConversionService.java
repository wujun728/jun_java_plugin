package spring_mvc.conversion;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.core.convert.converter.Converter;

public class DateConversionService implements Converter<String,Date>{
	private DateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
	@Override
	public Date convert(String source) {
		// TODO Auto-generated method stub
		try {
			
			return dateFormat.parse(source);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

}
