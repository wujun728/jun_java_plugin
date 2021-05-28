/**
 * Copyright (c) 2015-2017, Winter Lau (javayou@gmail.com).
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package net.oschina.j2cache;

import jline.console.ConsoleReader;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 缓存测试入口
 * @author Wujun
 */
public class J2CacheCmd {

	private static long TTL = 0;

	@SuppressWarnings("resource")
	public static void main(String[] args) throws IOException {

		CacheChannel cache = J2Cache.getChannel(); //获取 J2Cache 操作接口
		ConsoleReader reader = new ConsoleReader();

		String line;

		do{
			try {
				line = reader.readLine("> ");

				if(line == null || line.equalsIgnoreCase("quit") || line.equalsIgnoreCase("exit"))
					break;

				String[] cmds = line.split(" ");
				if("get".equalsIgnoreCase(cmds[0])){
					CacheObject obj = cache.get(cmds[1], cmds[2]); //从缓存读取数据
					System.out.printf("[%s,%s,L%d]=>%s(TTL:%d)%n", obj.getRegion(), obj.getKey(), obj.getLevel(), obj.getValue(), TTL);
				}
				else
				if("mget".equalsIgnoreCase(cmds[0])){
					List<String> keys = Arrays.stream(cmds).skip(2).collect(Collectors.toList());
					Map<String, CacheObject> values = cache.get(cmds[1], keys); //批量获取缓存数据
					if(values != null && values.size() > 0)
						values.forEach((key,obj) -> System.out.printf("[%s,%s,L%d]=>%s(TTL:%d)%n", obj.getRegion(), obj.getKey(), obj.getLevel(), obj.getValue(), TTL));
					else
						System.out.println("none!");
				}
				else
				if("set".equalsIgnoreCase(cmds[0])){
					if("null".equalsIgnoreCase(cmds[3]))
						cmds[3] = null;
					cache.set(cmds[1], cmds[2], cmds[3], TTL, true); //数据写入缓存
					System.out.printf("[%s,%s]<=%s(TTL:%d)%n",cmds[1], cmds[2], cmds[3], TTL);
				}
				else
				if("mset".equalsIgnoreCase(cmds[0])){
					String region = cmds[1];
					Map<String, Object> objs = new HashMap<>();
					for(int i=2;i<cmds.length;i++) {
						String[] obj = cmds[i].split(":");
						if("null".equalsIgnoreCase(obj[1]))
							obj[1] = null;
						objs.put(obj[0], obj[1]);
					}
					cache.set(cmds[1], objs, TTL, true); //批量写入数据到缓存
					objs.forEach((k,v)->System.out.printf("[%s,%s]<=%s(TTL:%d)%n",region, k, v, TTL));
				}
				else
				if("evict".equalsIgnoreCase(cmds[0])){
					String[] keys = Arrays.stream(cmds).skip(2).toArray(String[]::new);
					cache.evict(cmds[1], keys); //清除某个缓存数据
					for(String key : keys)
						System.out.printf("[%s,%s]=>null%n",cmds[1], key);
				}
				else
				if("clear".equalsIgnoreCase(cmds[0])){
					cache.clear(cmds[1]);	//清除某个缓存区域的数据
					System.out.printf("Cache [%s] clear.%n" , cmds[1]);
				}
				else
				if("regions".equalsIgnoreCase(cmds[0])){
					System.out.println("Regions:");
					cache.regions().forEach( r -> System.out.println(r));
				}
				else
				if("keys".equalsIgnoreCase(cmds[0])){
					Collection<String> keys = cache.keys(cmds[1]); //获得某个缓存区域的所有 key
					if(keys != null)
						System.out.printf("[%s:keys] => (%s)(TTL:%d)%n" , cmds[1], String.join(",", keys), TTL);
					else
						System.out.println("none!");
				}
				else
				if("ttl".equalsIgnoreCase(cmds[0])){
					if(cmds.length == 1){
						System.out.printf("TTL => %d%n", TTL);
					}
					else {
						TTL = Long.parseLong(cmds[1]);
						System.out.printf("TTL <= %d%n", TTL);
					}
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

		cache.close();	//关闭 J2Cache 缓存
		reader.shutdown();

		System.exit(0);
	}

	private static void printHelp() {
		System.out.println("Usage: [cmd] region key [value]");
		System.out.println("cmd: get/mget/set/mset/evict/regions/keys/clear/ttl/quit/exit/help");
		System.out.println("Examples:");
		System.out.println("\tset region key value");
		System.out.println("\tget region key");
		System.out.println("\tmget region key1 key2 key3");
		System.out.println("\tmset region key1:value1 key2:value2 key3:value3");
		System.out.println("\tkeys region");
		System.out.println("\tttl [seconds]");
		System.out.println("\texit");
	}

}