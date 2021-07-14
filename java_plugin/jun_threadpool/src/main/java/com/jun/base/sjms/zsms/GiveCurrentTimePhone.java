package com.jun.base.sjms.zsms;

public class GiveCurrentTimePhone extends PhoneDecorate  {

    public GiveCurrentTimePhone(Phone phone) {
        super(phone);
    }

    // 自定义想要实现的功能：给出当前的时间
    public void currentTime() {
        System.out.println("当前的时间是：" + System.currentTimeMillis());
    }

    // 重写要增强的方法
    @Override
    public void call() {
        super.call();
        // 打完电话后通知一下当前时间
        currentTime();
    }
}
