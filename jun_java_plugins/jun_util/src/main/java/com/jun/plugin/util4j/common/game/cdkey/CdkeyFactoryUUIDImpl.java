package com.jun.plugin.util4j.common.game.cdkey;

import java.util.UUID;

public class CdkeyFactoryUUIDImpl implements CdkeyFactory{

	@Override
	public String build() {
		UUID uuid = UUID.randomUUID();
//		return Long.toUnsignedString(uuid.getMostSignificantBits(), Character.MAX_RADIX);
		return Long.toUnsignedString(uuid.getLeastSignificantBits(), Character.MAX_RADIX);
	}
	
	public static void main(String[] args) {
		CdkeyFactory cf=new CdkeyFactoryUUIDImpl();
		for(int i=0;i<10;i++)
		{
			System.out.println(cf.build());
		}
	}
}
