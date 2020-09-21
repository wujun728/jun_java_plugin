package cn.skynethome.redisx.Test;

import cn.skynethome.redisx.PowerfulRedisUtil;

public class PowerfulRedisUtilTest
{
        public static volatile int j = 0;
        public static void main(String[] args)
        {

            String key="aaaabbbbkey";
            PowerfulRedisUtil.setObject("aaaabbbb", new User("fsdfsdfasdgdfgdsfgdgfd","passwd"));

            for (int i = 0; i < 5000; i++)
            {
                new TimeRedisx(key+i).start();
            }
            
//            PowerfulRedisUtil.setObject("aaaabbbb", new User("fsdfsdfasdgdfgdsfgdgfd","passwd"));
//            PowerfulRedisUtil.setObject("aaaabbbb", new User("fsdfsdfasdgxxdfgdsfgdgfd","pasxxxxswd"));
//            PowerfulRedisUtil.del("aaaabbbb");
//            System.out.println(PowerfulRedisUtil.getObject("aaaabbbb", User.class));
        }
    }

    class TimeRedisx extends Thread
    {

        private String key;
        
        public TimeRedisx()
        {
            
        }
        
        public TimeRedisx(String key)
        {
            this.key = key;
        }
        
        

        @Override
        public void run()
        {
            int i=20;
            while(true)
            {
//                try
//                {
//                    Thread.sleep(200);
//                }
//                catch (InterruptedException e)
//                {
//                    // TODO Auto-generated catch block
//                    e.printStackTrace();
//                }
                

                String k=key+getId()+i;
                PowerfulRedisUtil.setObject(k,new User("fdsfsd","fdsfsd"));
                long start = System.currentTimeMillis();
                User user = PowerfulRedisUtil.getObject(k,User.class);

                long end = System.currentTimeMillis();
                long time = end - start;
                System.out.println("Connection:"+PowerfulRedisUtil.getActiveConnections()+" ***** "+TestRedisReadTimes.j+"==="+this.getId()+"="+(time)+"ms");
                
                if(i<=0)
                {
                    break;
                }

                if(time>=5)

                {
                    TestRedisReadTimes.j++;
                }
                
                
                i--;
            }
          
           
        }
    }