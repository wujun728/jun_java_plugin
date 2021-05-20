package spring_mvc.formatting;

import java.io.IOException;
import java.text.ParseException;
import java.util.Locale;

import org.springframework.format.Formatter;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import spring_mvc.model.user;

public class userFormatter implements Formatter<user>{
	
	@Override
	public String print(user object, Locale locale) {
		// TODO Auto-generated method stub
		return "userFormatter输出："+object.toString();
	}

	@Override
	public user parse(String text, Locale locale) throws ParseException {
		// TODO Auto-generated method stub
		ObjectMapper mapper=new ObjectMapper();
		user user=null;
		try {
			user=mapper.readValue(text, user.class);
		} catch (JsonParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return user;
	}
	
}
