/*
 * GetMacAddress .java
 *
 * description:get Mac addreess
 *
 * @author hadeslee
 *
 * Created on 2007-9-27, 9:11:15
 *
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jun.plugin.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
*
*/
public class GetMacAddress {
	public static String getMACAddress() {
		String address = "";
		String os = System.getProperty("os.name");
		java.util.Properties p2=System.getProperties();
//		System.out.println(p2.values());
//		System.out.println(os);
//		System.out.println(p2.toString());
//		System.out.println(os);
		if (os != null && os.startsWith("Windows")) {
			try {
				ProcessBuilder pb = new ProcessBuilder("ipconfig", "/all");
				Process p = pb.start();
				BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
				String line;
				while ((line = br.readLine()) != null) {
					if (line.indexOf("Physical Address") != -1) {
						int index = line.indexOf(":");
						address = line.substring(index + 1);
						System.out.println(address);
						break;
					}
				}
				System.out.println(line);
				System.out.println(br.readLine());
				br.close();
				return address.trim();
			} catch (IOException e) {
			}
		}
		return address;
	}

	public static void main(String[] args) {
		System.out.println("" + GetMacAddress.getMACAddress());
	}
}