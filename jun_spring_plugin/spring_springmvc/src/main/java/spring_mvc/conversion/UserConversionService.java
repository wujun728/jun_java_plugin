package spring_mvc.conversion;


import org.springframework.core.convert.converter.Converter;

import com.fasterxml.jackson.databind.ObjectMapper;

import spring_mvc.model.user;

public class UserConversionService implements Converter<String,user>{

	@Override
	public user convert(String source) {
		// TODO Auto-generated method stub
		
		System.out.println( source );
		
		ObjectMapper mapper=new ObjectMapper();
		user user=null;
		try{
			user=mapper.readValue(source,user.class);
		}catch(Exception e){
			e.printStackTrace();
		}
		return user;
	}
}
