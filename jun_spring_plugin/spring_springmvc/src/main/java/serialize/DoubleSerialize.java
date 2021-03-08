package serialize;

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.NumberFormat;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

public class DoubleSerialize extends JsonSerializer<Double>{
	private NumberFormat numberFormat=new DecimalFormat("#,###.##");
	@Override
	public void serialize(Double value, JsonGenerator gen, SerializerProvider serializers)
			throws IOException, JsonProcessingException {
		// TODO Auto-generated method stub
		gen.writeString(numberFormat.format(value));
	}

}
