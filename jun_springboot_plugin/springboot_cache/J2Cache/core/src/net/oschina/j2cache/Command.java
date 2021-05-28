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

import java.util.Random;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.annotation.JSONField;

/**
 * 命令消息封装
 * 
 * @author Wujun
 */
public class Command {

	private final static int SRC_ID = genRandomSrc(); //命令源标识，随机生成，每个节点都有唯一标识

	public final static byte OPT_JOIN 	   = 0x01;	//加入集群
	public final static byte OPT_EVICT_KEY = 0x02; 	//删除缓存
	public final static byte OPT_CLEAR_KEY = 0x03; 	//清除缓存
	public final static byte OPT_QUIT 	   = 0x04;	//退出集群
	
	private int src = SRC_ID;
	private int operator;
	private String region;
	private String[] keys;
	
	private static int genRandomSrc() {
		long ct = System.currentTimeMillis();
		Random rnd_seed = new Random(ct);
		return (int)(rnd_seed.nextInt(10000) * 1000 + ct % 1000);
	}

	public Command(){}//just for json deserialize , dont remove it.

	public Command(byte o, String r, String...keys){
		this.operator = o;
		this.region = r;
		this.keys = keys;
	}

	/**
	 * 返回本地的专有标识
	 * @return 节点标识
	 */
	public static final int LocalID() {
		return SRC_ID;
	}

	public static Command join() {
		return new Command(OPT_JOIN, null);
	}

	public static Command quit() {
		return new Command(OPT_QUIT, null);
	}

	public String json() {
		return JSON.toJSONString(this);
	}

	public static Command parse(String json) {
		return JSON.parseObject(json, Command.class);
	}

	@JSONField(serialize = false)
	public boolean isLocal() {
		return this.src == SRC_ID;
	}
	
	public int getOperator() {
		return operator;
	}

	public String getRegion() {
		return region;
	}

	public String[] getKeys() {
		return keys;
	}

	public int getSrc() {
		return src;
	}

    public void setSrc(int src) {
        this.src = src;
    }

    public void setOperator(int operator) {
        this.operator = operator;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public void setKeys(String[] keys) {
        this.keys = keys;
    }

    @Override
	public String toString(){
		return json();
	}

}
