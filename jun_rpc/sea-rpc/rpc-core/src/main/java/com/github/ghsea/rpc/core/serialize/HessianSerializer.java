package com.github.ghsea.rpc.core.serialize;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import com.caucho.hessian.io.HessianInput;
import com.caucho.hessian.io.HessianOutput;

public class HessianSerializer implements Serializer {

	@Override
	public byte[] serialize(Object obj) throws IOException {
		if (obj == null)
			throw new NullPointerException();

		ByteArrayOutputStream os = new ByteArrayOutputStream();
		HessianOutput ho = new HessianOutput(os);
		ho.writeObject(obj);
		return os.toByteArray();
	}

	@Override
	public Object deserialize(byte[] byt) throws IOException {
		ByteArrayInputStream is = new ByteArrayInputStream(byt);
		HessianInput hi = new HessianInput(is);
		return hi.readObject();
	}

}
