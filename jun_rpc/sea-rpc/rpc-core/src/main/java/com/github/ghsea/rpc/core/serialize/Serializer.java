package com.github.ghsea.rpc.core.serialize;

import java.io.IOException;

/**
 * 序列化反序列化接口
 * 
 * @author ghsea 2017-2-10下午9:36:55
 */
public interface Serializer {
	byte[] serialize(Object obj) throws IOException;

	Object deserialize(byte[] stream) throws IOException;
}
