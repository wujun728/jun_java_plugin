package com.jun.base.synchronizedTest;

/**
 *
 * 静态方法 static
 * synchronized(){} 只能传对象.class
 *
 */
public class SyT4 {
    public static void main(String[] args) {
        Sync4 sync4 = new Sync4("cs");

        Sya4 sya4A = new Sya4(sync4,"A");
        sya4A.start();

        Sya4 sya4B = new Sya4(sync4,"B");
        sya4B.start();


    }

}

class Sync4{

   public static String str ;

    public Sync4(String str){
        this.str = str;
    }

    public static void set(String name){

        try {
            System.err.println(System.currentTimeMillis()+"=="+name);
            synchronized (Sync4.class){//设置 this  编译不通过 ，只能传值 对象.class
                str = name;
                System.out.println("线程名称为：" + Thread.currentThread().getName() + "在" + System.currentTimeMillis() + "进入同步块"+str);
                Thread.sleep(3000);
                System.out.println("线程名称为：" + Thread.currentThread().getName() + "在" + System.currentTimeMillis() + "离开同步块"+str);

            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
class Sya4 extends Thread {
    private Sync4 sync4 ;
    private String name;
    public Sya4(Sync4 sync4,String name){
        this.sync4 = sync4;
        this.name = name;
    }
    @Override
    public void run() {
        sync4.set(name);
    }
}