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
package net.oschina.j2cache.session;

import java.io.Serializable;
import java.text.MessageFormat;
import java.text.ParseException;
import java.util.Random;

/**
 * 命令消息封装
 * 
 * @author Wujun
 */
public class Command implements Serializable {

	private static final String pattern = "SRC:{0},SSN:{1},OPT:{2},KEY:{3}";

	private final static int SRC_ID = genRandomSrc(); //命令源标识，随机生成，每个节点都有唯一标识

	public final static byte OPT_JOIN 	   		= 0x01;	//加入集群
	public final static byte OPT_DELETE_SESSION = 0x03; //清除会话
	public final static byte OPT_QUIT 	   		= 0x04;	//退出集群
	
	private int src = SRC_ID;
	private int operator;
	private String session;
	private String key;
	
	private static int genRandomSrc() {
		long ct = System.currentTimeMillis();
		Random rnd_seed = new Random(ct);
		return (int)(rnd_seed.nextInt(10000) * 1000 + ct % 1000);
	}

	public Command(byte operator, String session, String key) {
		this.operator = operator;
		this.session = session;
		this.key = key;
	}

	/**
	 * 返回本地的专有标识
	 * @return 返回唯一标识
	 */
	public static final int LocalID() {
		return SRC_ID;
	}

	public static Command join() {
		return new Command(OPT_JOIN, null, null);
	}

	public static Command quit() {
		return new Command(OPT_QUIT, null, null);
	}

	public static Command parse(String data) {
		try {
			Object[] results = new MessageFormat(pattern).parse(data);
			Command cmd = new Command(Byte.parseByte((String)results[2]),(String)results[1],(String)results[3]);
			cmd.src = Integer.parseInt((String)results[0]);
			return cmd;
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}
	}

	public boolean isLocal() {
		return this.src == SRC_ID;
	}
	
	public int getOperator() {
		return operator;
	}

	public String getKey() {
		return key;
	}

	public int getSrc() {
		return src;
	}

    public void setOperator(int operator) {
        this.operator = operator;
    }

    public void setKey(String key) {
        this.key = key;
    }

	public String getSession() {
		return session;
	}

	public void setSession(String session) {
		this.session = session;
	}

	@Override
	public String toString(){
		return MessageFormat.format(pattern, String.valueOf(src), session, operator, key);
	}

	public static void main(String[] args) {
		Command cmd = new Command(OPT_JOIN, "aerlkjasldfkjasldkjfas","|1|23");
		System.out.println(cmd);
		System.out.println(Command.parse(cmd.toString()));
	}

}
