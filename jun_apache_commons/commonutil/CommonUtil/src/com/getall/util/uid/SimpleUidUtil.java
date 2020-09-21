package com.getall.util.uid;

import java.util.UUID;

public class SimpleUidUtil
{
	public static String nextUid()
	{
		return UUID.randomUUID().toString();
	}
}
