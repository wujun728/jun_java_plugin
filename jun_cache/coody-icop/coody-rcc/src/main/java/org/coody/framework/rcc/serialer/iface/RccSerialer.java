package org.coody.framework.rcc.serialer.iface;

public interface RccSerialer {

	public byte[] serialize(Object object);

	public <T> T unSerialize(byte[] data);
}
