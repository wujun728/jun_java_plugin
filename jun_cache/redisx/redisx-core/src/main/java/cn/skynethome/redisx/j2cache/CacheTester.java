/**
 * 
 */
package cn.skynethome.redisx.j2cache;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
  * 项目名称:[redisx-core]
  * 包:[cn.skynethome.redisx.j2cache]    
  * 文件名称:[CacheTester]  
  * 描述:[一句话描述该文件的作用]
  * 创建人:[陆文斌]
  * 创建时间:[2017年1月11日 上午9:40:20]   
  * 修改人:[陆文斌]   
  * 修改时间:[2017年1月11日 上午9:40:20]   
  * 修改备注:[说明本次修改内容]  
  * 版权所有:luwenbin006@163.com
  * 版本:[v1.0]
 */
public class CacheTester {

	public static void main(String[] args) {
		
		System.setProperty("java.net.preferIPv4Stack", "true"); //Disable IPv6 in JVM
		
		CacheChannel cache = J2Cache.getChannel();
		BufferedReader in=new BufferedReader(new InputStreamReader(System.in));

	    do{
	        try {
	            System.out.print("> "); 
	            System.out.flush();
	            
	            String line=in.readLine().trim();
	            if(line.equalsIgnoreCase("quit") || line.equalsIgnoreCase("exit"))
	                break;

	            String[] cmds = line.split(" ");
	            if("get".equalsIgnoreCase(cmds[0])){
	            	CacheObject obj = cache.get(cmds[1], cmds[2]);
	            	System.out.printf("[%s,%s,L%d]=>%s\n", obj.getRegion(), obj.getKey(), obj.getLevel(), obj.getValue());
	            }
	            else
	            if("set".equalsIgnoreCase(cmds[0])){
	            	cache.set(cmds[1], cmds[2],cmds[3]);
	            	System.out.printf("[%s,%s]<=%s\n",cmds[1], cmds[2], cmds[3]);
	            }
	            else
	            if("evict".equalsIgnoreCase(cmds[0])){
	            	cache.evict(cmds[1], cmds[2]);
	            	System.out.printf("[%s,%s]=>null\n",cmds[1], cmds[2]);
	            }
	            else
	            if("clear".equalsIgnoreCase(cmds[0])){
	            	cache.clear(cmds[1]);
	            	System.out.printf("Cache [%s] clear.\n" , cmds[1]);
	            }
	            else
	            if("help".equalsIgnoreCase(cmds[0])){
	            	printHelp();
	            }
	            else{
	            	System.out.println("Unknown command.");
	            	printHelp();
	            }
	        }
	        catch(ArrayIndexOutOfBoundsException e) {
            	System.out.println("Wrong arguments.");
	        	printHelp();
	        }
	        catch(Exception e) {
	        	e.printStackTrace();
	        }
	    }while(true);
	    
	    cache.close();
	    
	    System.exit(0);
	}
	
	private static void printHelp() {
		System.out.println("Usage: [cmd] region key [value]");
		System.out.println("cmd: get/set/evict/quit/exit/help");
	}

}
