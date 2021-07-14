package com.jun.plugin.javase.oneday;

public class Star {
	public static void main(String[] args) {
		for(int i=0;i<6;i++){
			for(int j=0;j<6;j++){
				System.out.print("*");
			}
			System.out.println();
		}

		
		for(int i=1;i<=6;i++){
			for(int j=1;j<=6-i;j++){
				System.out.print(" ");
			}//����ո�
			for(int k=1;k<=6;k++){
				System.out.print("*");
			}//�������
			System.out.println();
		}	
//		j<=6-i��ʼ�Ŀո��������һ�п�5�����ڶ��п�4���������п�3�����Դ�����
		
		for(int i=0;i<5;i++){
			for(int j=0;j<=5-i;j++){
				System.out.print(" ");
			}
			
			for(int k=0;k<=i*2;k++){
				System.out.print("*");				
			}
			System.out.println();
		}
		
		for(int i=0;i<=5;i++){
			for(int j=0;j<=5-i;j++){
				System.out.print("*");
			}
			
			for(int k=0;k<=i;k++){
				System.out.print(" ");				
			}
			System.out.println();

		}
		
		
		for(int i=0;i<=3;i++){
			for(int j=0;j<=i;j++){
				for(int k=0;k<=j;k++){
					for(int m=0;m<=k;m++){
						for(int n=0;n<=m;n++){
							System.out.print("$");
						}
					}
				}
			}
			System.out.println(" ");
		}
		
		
		for(int i=0;i<=5;i++){
			for(int j=0;j<=5-i;j++){
				System.out.print(" ");
			}
			for(int k=0;k<=i*2;k++){
				System.out.print("*");
			}
			System.out.println(" ");
		}
		for(int i=0;i<=4;i++){
			for(int j=0;j<=i+1;j++){
				System.out.print(" ");
			}
			for(int k=8;k>=i*2;k--){
				System.out.print("*");
			}
			System.out.println(" ");
		}
	}
}
