package com.tfnico.examples.guava;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.junit.Test;

import com.google.common.base.Throwables;
import com.google.common.net.InetAddresses;


public class NetTest {
	
	@Test
	public void iNetAddressIsFixed()
	{
		try {
			
			/**
			 * Unlike InetAddress.getByName(), 
			 * the methods of this class never cause DNS services to be accessed. 
			 * For this reason, you should prefer these methods as much as possible 
			 * over their JDK equivalents whenever you are expecting to handle only 
			 * IP address string literals -- there is no blocking DNS penalty for 
			 * a malformed string.
			 */
			InetAddresses.forString("0.0.0.0");
			
			//Instead of this...
			InetAddress.getByName("0.0.0.0");
			
		} catch (UnknownHostException e) {
			Throwables.propagate(e);
		}
	}
	
	
	
	
	
}
