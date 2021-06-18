package com.jun.plugin.demo;
public class MaxVariables { //java�е�һ�ж����������ʾ
    public static void main(String args[]) { //ÿ�����������main()����

        // ��������
        byte largestByte = Byte.MAX_VALUE;// ����һ��byte���͵ı���
        short largestShort = Short.MAX_VALUE;//����һ��short���͵ı���
        int largestInteger = Integer.MAX_VALUE;//����һ��int���͵ı���
        long largestLong = Long.MAX_VALUE;//����һ��long���͵ı���

        // ʵ������
        float largestFloat = Float.MAX_VALUE;//����һ��float���͵ı���
        double largestDouble = Double.MAX_VALUE;//����һ��double���͵ı���

        // ���������
        char aChar = 'S';//����һ���ַ�
        boolean aBoolean = true;//����һ���������͵ı���

        // ����Ļ����ʾ��Ӧ���͵����ֵ
        System.out.println("����byteֵ�ǣ�" + largestByte);
        System.out.println("����shortֵ�ǣ�" + largestShort);
        System.out.println("����integerֵ�ǣ�" + largestInteger);
        System.out.println("����longֵ�ǣ�" + largestLong);
        System.out.println("����floatֵ�ǣ�" + largestFloat);
        System.out.println("����doubleֵ�ǣ�" + largestDouble);
        if (Character.isUpperCase(aChar)) {//�ж��ַ��Ƿ��д
            System.out.println("�ַ�" + aChar + "�Ǵ�д���ַ�");
        } else {
            System.out.println("�ַ�" + aChar + "��Сд���ַ�");
        }
            System.out.println("�����ͱ�����ֵ�ǣ�" + aBoolean);
    }
}
