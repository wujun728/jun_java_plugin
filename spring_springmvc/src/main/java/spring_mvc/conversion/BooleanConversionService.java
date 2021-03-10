package spring_mvc.conversion;

import java.util.HashSet;
import java.util.Set;

import org.springframework.core.convert.converter.Converter;

public class BooleanConversionService implements Converter<String, Boolean>{
	private static Set<String> sets=new HashSet<String>();
	static{
		sets.add("1");
		sets.add("true");
		sets.add("ok");
		sets.add("TRUE");
	}
	@Override
	public Boolean convert(String source) {
		return sets.contains(source);
	}
	
}
