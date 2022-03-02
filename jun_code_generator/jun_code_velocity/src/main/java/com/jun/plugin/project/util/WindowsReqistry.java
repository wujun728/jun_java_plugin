package com.jun.plugin.project.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;

public class WindowsReqistry {
	/**
     * 
     * @param location path in the registry
     * @param key registry key
     * @return registry value or null if not found
     */
    public static String readRegistry(String location,String key){
        try {
            // Run reg query, then read output with StreamReader (internal class)
            Process process = Runtime.getRuntime().exec("reg query " + 
                    '"'+ location + "\" " + key);
            StreamReader reader = new StreamReader(process.getInputStream());
            reader.start();
            process.waitFor();
            reader.join();
            String output = reader.getResult();

            // Output has the following format:
            // \n<Version information>\n\n<key>\t<registry type>\t<value>
            if(output.contains("\r\n")){
            	String[] parsed = output.split("\r\n");
                return parsed[parsed.length-1];
            }
            return output.toString().replaceAll("\r|\n", "");
        }
        catch (Exception e) {
            return null;
        }

    }

    public static void main(String[] args) {
    	System.out.println(readRegistry("HKEY_CURRENT_USER\\Software\\Microsoft\\Windows\\CurrentVersion\\App Paths\\chrome.exe","/ve"));
	}
    static class StreamReader extends Thread {
        private InputStream is;
        private StringWriter sw= new StringWriter();

        public StreamReader(InputStream is) {
            this.is = is;
        }

        public void run() {
            try {
                int c;
                while ((c = is.read()) != -1)
                    sw.write(c);
            }
            catch (IOException e) { 
        }
        }

        public String getResult() {
            return sw.toString();
        }
    }
}
