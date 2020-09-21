package cn.skynethome.redisx.Test;

import cn.skynethome.redisx.RedisClusterUtils;
/**
  * 项目名称:[redisx]
  * 包:[cn.skynethome.redisx]    
  * 文件名称:[TestRedisCluster]  
  * 描述:[一句话描述该文件的作用]
  * 创建人:[陆文斌]
  * 创建时间:[2017年1月5日 上午10:25:19]   
  * 修改人:[陆文斌]   
  * 修改时间:[2017年1月5日 上午10:25:19]   
  * 修改备注:[说明本次修改内容]  
  * 版权所有:luwenbin006@163.com
  * 版本:[v1.0]
 */
public class TestRedisCluster {

	public static void main(String[] args) {
//		Set<HostAndPort> jedisClusterNodes = new HashSet<HostAndPort>();
//		//Jedis Cluster will attempt to discover cluster nodes automatically
//		jedisClusterNodes.add(new HostAndPort("127.0.0.1", 7379));
//		JedisCluster jc = new JedisCluster(jedisClusterNodes);
//		jc.set("foo", "bar");
//		String value = jc.get("foo");
		
		
//		RedisClusterUtils.setObject("", new String("xdfsdf"));
//		
//		System.out.println(RedisClusterUtils.getString(""));
//		
//		System.out.println(RedisClusterUtils.del(""));
	    
	    
	    for(int i=0;i<100;i++)
	    {
	        new RedisClusterThread("RedisClusterThread"+i,100).start();
	    }
	    
	}
	
	
	
}


class RedisClusterThread extends Thread {
    private String name;
    private int size;

    public RedisClusterThread() {
        // TODO Auto-generated constructor stub
    }

    public RedisClusterThread(String name,int size) {
        this.name = name;
        this.size = size;
    }

    @Override
    public void run() {
       // try {
            for (int i = 0; i < this.size; i++) {
                System.out.println(RedisClusterUtils.setString(this.name + "xxx" + i, "fdsfsdf" + i) + "=" + i + "="
                        + this.getName() + this.getId());

                //sleep(200);

            }

            for (int i = 0; i < this.size; i++) {
                System.out.println(RedisClusterUtils.del(this.name + "xxx" + i));

            }
       // } catch (InterruptedException e) {
            // TODO Auto-generated catch block
       //     e.printStackTrace();
      //  }
    }
}
