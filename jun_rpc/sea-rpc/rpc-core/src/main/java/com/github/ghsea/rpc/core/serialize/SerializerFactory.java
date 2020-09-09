package com.github.ghsea.rpc.core.serialize;

import java.util.HashMap;
import java.util.Map;

import com.github.ghsea.rpc.server.constants.SerializerType;
import com.google.common.base.Preconditions;

public class SerializerFactory {

	private static Map<SerializerType, Serializer> type2Serializer = new HashMap<SerializerType, Serializer>();

	public static Serializer getSerializer(SerializerType type) {
		Preconditions.checkNotNull(type);
		
		Serializer serializer = type2Serializer.get(type);
		if (serializer == null) {
			synchronized (SerializerFactory.class) {
				if (serializer == null) {
					serializer = newSerializer(type);
					type2Serializer.put(type, serializer);
				}
			}
		}

		return serializer;
	}

	private static Serializer newSerializer(SerializerType type) {
		switch (type) {
		case Hessian:
			return new HessianSerializer();
		case JSON:
			throw new UnsupportedOperationException();
		default:
			throw new UnsupportedOperationException();
		}
	}

}
