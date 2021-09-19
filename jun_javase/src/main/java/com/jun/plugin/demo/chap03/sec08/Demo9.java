package com.jun.plugin.demo.chap03.sec08;

public class Demo9 {

	public static void main(String[] args) {
		String str=" aB232 23 &*( s2  ";
		// ȥ��ǰ��ͺ���Ŀհ�
		String newStr=str.trim();
		System.out.println("str="+str);
		System.out.println("newStr="+newStr);
		
		int yingWen=0;
		int kongGe=0;
		int shuZi=0;
		int qiTa=0;
		
		for(int i=0;i<newStr.length();i++){
			char c=newStr.charAt(i);
			if((c>='a'&&c<='z')||(c>='A'&&c<='Z')){ // �ж�Ӣ���ַ�  
				yingWen++;
				System.out.println("Ӣ���ַ���"+c);
			}else if(c>='0'&&c<='9'){ // �ж�����
				shuZi++;
				System.out.println("���֣�"+c);
			}else if(c==' '){  // �жϿո�
				kongGe++;
				System.out.println("�ո�"+c);
			}else{
				qiTa++;
				System.out.println("�����ַ���"+c);
			}
		}
		System.out.println();
		System.out.println("Ӣ���ַ�������"+yingWen);
		System.out.println("����������"+shuZi);
		System.out.println("�ո�������"+kongGe);
		System.out.println("�����ַ�������"+qiTa);
	}
}
