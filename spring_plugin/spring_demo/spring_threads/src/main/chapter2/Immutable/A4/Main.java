package Immutable.A4;

public class Main {
    public static void main(String[] args) {
        // 制作实体
        UserInfo userinfo = new UserInfo("Alice", "Alaska");

        // 显示
        System.out.println("userinfo = " + userinfo);

        // 状态变更
        StringBuffer info = userinfo.getInfo();
        info.replace(12, 17, "Bobby");  // "Alice"竚琌12..16

        //再度显示
        System.out.println("userinfo = " + userinfo);
    }
}
