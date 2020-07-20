package klg.common.utils;

import java.io.IOException;

import org.junit.Test;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

public class JacksonTest {
	
	@Test
	public void deserialize() throws JsonParseException, JsonMappingException, IOException{
		
		
		MyPrinter.printJson(JacksonUtil.parseObject("{'name':'test'}", Model.class));
	}	
}
