package com.jun.plugin.demo;
//����������Ϸ
public class HanoiTower {
	// ��n���̴�from���Ƶ�to����aux��Ϊ������
	public static void move(int n, char from, char to, char aux) {
		if (n == 1) {
			// ����һ����ʱ��ֱ�Ӵ�from���Ƶ�to��
			System.out.println("��#1�̴� " + from + " �Ƶ� " + to);
		} else {
			// ��n - 1���̴�from���Ƶ�aux����to��Ϊ������
			move(n - 1, from, aux, to);
			// �����µ�Բ�̴�from���Ƶ�to��
			System.out.println("��#" + n + "�̴� " + from + " �Ƶ� " + to);
			// ��n - 1���̴�aux���Ƶ�to����from��Ϊ������
			move(n - 1, aux, to, from);
		}
	}

	public static void main(String[] args) {
		// ��4��Բ�̴�A���Ƶ�C���ƶ�ʱ����B��Ϊ������
		move(3, 'A', 'C', 'B');
	}
}