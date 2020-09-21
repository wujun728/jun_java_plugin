package cn.skynethome.redisx.Test;

import cn.skynethome.redisx.PowerfulRedisUtil;

/**
 * 项目名称:[redisx] 
 * 包:[cn.skynethome.redisx] 
 * 文件名称:[TestRedis] 
 * 描述:[一句话描述该文件的作用]
 * 创建人:[陆文斌] 
 * 创建时间:[2017年1月3日 上午11:43:58] 
 * 修改人:[陆文斌] 
 * 修改时间:[2017年1月3日 上午11:43:58]
 * 修改备注:[说明本次修改内容] 
 * 版权所有:luwenbin006@163.com 版本:[v1.0]
 */
public class TestRedis {
	public static void main(String[] args) {

		for (int i = 0; i < 5000; i++) {
			new RedisThread("threadredis" + i).start();

		}
	}
}

class RedisThread extends Thread {
	private String name;

	public RedisThread() {
		// TODO Auto-generated constructor stub
	}

	public RedisThread(String name) {
		this.name = name;
	}

	@Override
	public void run() {
		try {
			for (int i = 0; i < 100; i++) {
				System.out.println(PowerfulRedisUtil.setString(this.name + "xxx" + i, "fdsfsdf" + i) + "=" + i + "="
						+ this.getName() + this.getId());

				sleep(200);

			}

			for (int i = 0; i < 100; i++) {
				System.out.println(PowerfulRedisUtil.del(this.name + "xxx" + i));

			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
