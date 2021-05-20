package TwoPhaseTermination.Q7;

public class Main {
    public static void main(String[] args) {
        System.out.println("main: BEGIN");
        try {
            // �Ұʰ����
            HanoiThread t = new HanoiThread();
            t.start();

            // ���ݤ@�q�ɶ�
            Thread.sleep(10000);

            // ������e�X�פ�ШD
            System.out.println("main: shutdownRequest");
            t.shutdownRequest();

            // ���ݰ�����
            System.out.println("main: join");
            t.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("main: END");
    }
}
