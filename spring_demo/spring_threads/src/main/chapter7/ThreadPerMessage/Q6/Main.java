package ThreadPerMessage.Q6;

import java.io.IOException;

public class Main {
    public static void main(String args[]) {
        try {
            new MiniServer(8888).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
