package com.jun.base.sjms.zsms;
// 装饰器，实现接口
public abstract class PhoneDecorate  implements  Phone{

    // 以组合的方式来获取默认实现类
    private Phone phone;
    public PhoneDecorate(Phone phone) {
        this.phone = phone;
    }

    @Override
    public void call() {
        phone.call();
    }

}
