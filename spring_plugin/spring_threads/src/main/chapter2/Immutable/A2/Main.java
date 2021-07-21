package Immutable.A2;

public class Main {
    public static void main(String[] args) {
        String s = "BAT";
        String t = s.replace('B', 'C'); // 'B'取代成'C'
        System.out.println("s = " + s); // replace 执行后的 s 
        System.out.println("t = " + t); // replace 的传回值 t
        if (s == t) {
            System.out.println("s == t");
        } else {
            System.out.println("s != t");
        }
    }
}

