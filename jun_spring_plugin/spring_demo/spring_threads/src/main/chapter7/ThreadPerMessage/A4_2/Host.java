package ThreadPerMessage.A4_2;

public class Host {
    private final Helper helper = new Helper();
    public void request(int count, char c) {
        System.out.println("    request(" + count + ", " + c + ") BEGIN");
        new HelperThread(helper, count, c).start();
        System.out.println("    request(" + count + ", " + c + ") END");
    }

    // Inner class
    private class Helper {
        public void handle(int count, char c) {
            System.out.println("        handle(" + count + ", " + c + ") BEGIN");
            for (int i = 0; i < count; i++) {
                slowly();
                System.out.print(c);
            }
            System.out.println("");
            System.out.println("        handle(" + count + ", " + c + ") END");
        }
        private void slowly() {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
            }
        }
    }

    // Inner class
    private class HelperThread extends Thread {
        private final Helper helper;
        private final int count;
        private final char c;
        public HelperThread(Helper helper, int count, char c) {
            this.helper = helper;
            this.count = count;
            this.c = c;
        }
        public void run() {
            helper.handle(count, c);
        }
    }
}
