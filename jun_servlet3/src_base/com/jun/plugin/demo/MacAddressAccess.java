package com.jun.plugin.demo;
//import java.io.BufferedReader;
//import java.io.InputStreamReader;
//public class MacAddressAccess {
//	public MacAddressAccess() { 
//	}
//	private static String getMyMac() {
//		String s = "";
//		try {
//			String s1 = "ipconfig /all";
//			// �൱������������ֱ��ʹ��ipconfig /all
//			Process process = Runtime.getRuntime().exec(s1); 
//			BufferedReader bufferedreader = new BufferedReader(new 
//                       InputStreamReader(process.getInputStream()));
//			String line = bufferedreader.readLine();
//			// ɸѡ��mac��ַ
//			for(;line != null;) {
//				String nextLine = bufferedreader.readLine();
//				if(line.indexOf("Physical Address") > 0){ 
//					int i = line.indexOf("Physical Address") + 36; 
//					s = line.substring(i); 
//					break; 
//				}
//				line = nextLine; 
//			}
//			bufferedreader.close();
//			process.waitFor();
//		} catch(Exception exception) {
//			s = "";
//		}
//		return s.trim();
//	}
//	public static void main(String[] args){ 
//		// ��ʾMac��ַ�������н���
//		System.out.println(MacAddressAccess.getMyMac());
//	}
//}
