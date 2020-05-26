package com.jun.base.sjms.zsms;

public class TestRun {

    public static void main(String[] args) {
       /* Phone phone = new IphoneX();
        phone = new GiveCurrentTimePhone(phone);
        phone = new MusicPhone(phone);
*/
        // 先增强听音乐的功能，再增强通知时间的功能
        Phone phone = new GiveCurrentTimePhone(new MusicPhone(new IphoneX()));

        phone.call();

    }
}
