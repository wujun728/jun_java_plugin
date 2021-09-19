package com.jun.base.synchronizedTest;

/**
 *
 *## 如果里面传了别的对象，如字符串等，就不是锁住当前类了。那是锁住什么呢？还是锁不管用了？
 *因为anyString 本身就是虚拟对象。不参与运算。这样做的目的是，让锁不锁住整个对象，可以让别的线程可以访问。
 * 这是虚拟对象来上锁。
 *
 * *****
 * 不过我还没有了解到一般用于什么场景？
 *
 */
public class SyT3 {


    public static void main(String[] args) {
        Sync3 sync3 = new Sync3("cs");

        Sya3 sya3A = new Sya3(sync3,"A");
        sya3A.start();


        Sya3 sya3B = new Sya3(sync3,"B");
        sya3B.start();


    }


}

class Sync3{

    String str = new String();

    public Sync3(String str){
        this.str = str;
    }

    public void set(String name){

        try {
            System.err.println(System.currentTimeMillis()+"=="+name);
            synchronized (str){
                str = name;

                System.out.println("线程名称为：" + Thread.currentThread().getName() + "在" + System.currentTimeMillis() + "进入同步块"+this.str);
                Thread.sleep(3000);
                System.out.println("线程名称为：" + Thread.currentThread().getName() + "在" + System.currentTimeMillis() + "离开同步块"+this.str);

            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}

class Sya3 extends Thread {
    private Sync3 sync3 ;
    private String name;
    public Sya3(Sync3 sync3,String name){
        this.sync3 = sync3;
        this.name = name;
    }
    @Override
    public void run() {
        sync3.set(name);
    }
}

