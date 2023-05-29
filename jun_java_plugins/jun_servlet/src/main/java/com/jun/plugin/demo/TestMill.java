package com.jun.plugin.demo;
import java.text.SimpleDateFormat;


	public class TestMill {
	    public static String getTimeMillisSequence(){
	        long nanoTime = System.nanoTime();
	        String preFix="";
	        if (nanoTime<0){
	            preFix="0";//负数补位A保证负数排在正数Z前面,解决正负临界值(如A9223372036854775807至Z0000000000000000000)问题。
	            nanoTime = nanoTime+Long.MAX_VALUE+1;
	        }else{
	            preFix="0";
	        }
	        String nanoTimeStr = String.valueOf(nanoTime);
	        int difBit=String.valueOf(Long.MAX_VALUE).length()-nanoTimeStr.length();
	        for (int i=0;i<difBit;i++){
	            preFix = preFix+"0";
	        }
	        nanoTimeStr = preFix+nanoTimeStr;
	        SimpleDateFormat sdf=new SimpleDateFormat("yyyMMddHHmmssSSS"); //24小时制
	       // String timeMillisSequence=sdf.format(System.currentTimeMillis())+"-"+nanoTimeStr; 
	        String timeMillisSequence=sdf.format(System.currentTimeMillis()); 
	
	        return timeMillisSequence;      
	    }
	    
	    public static void main(String[] args) {
			System.out.println(TestMill.getTimeMillisSequence());
			System.out.println(Long.MAX_VALUE);
		}
}
