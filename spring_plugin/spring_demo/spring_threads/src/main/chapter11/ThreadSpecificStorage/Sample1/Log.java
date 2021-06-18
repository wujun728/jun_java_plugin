package ThreadSpecificStorage.Sample1;

import java.io.PrintWriter;
import java.io.FileWriter;
import java.io.IOException;

public class Log {
    private static PrintWriter writer = null;

    // 初始化writer字段
    static {
        try {
            writer = new PrintWriter(new FileWriter("log.txt"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 加入一笔log
    public static void println(String s) {
        writer.println(s);
    }

    // 关闭log
    public static void close() {
        writer.println("==== End of log ====");
        writer.close();
    }
}
