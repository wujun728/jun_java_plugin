package com.jun.web;


public class GuiBingFa {

	//�鲢������ѡ���÷ֶ���֮��˼�룬���õݹ鷽�����������������
	public static void guibing(int[] list)
	{
		int L=list.length;
		//������ݹ�ֳ�������
		if(L>1)
		{
			//��һ����Ϊǰһ��Ԫ��
			int[] firstHalf=new int[L/2];
			//����arrayCopy��������list�е�ǰһ��Ԫ�ظ��Ƶ�firstHalf֮��
			System.arraycopy(list, 0, firstHalf, 0, L/2);
			//�ݹ飺������������ٷֳ�����
			guibing(firstHalf);
			//�ڶ�����Ϊ��һ��Ԫ�أ���һ�����һ����Ԫ�ظ�����ͬ��
			int[] secondHalf=new int[L-L/2];
			//����arrayCopy��������list�еĺ�һ��Ԫ�ظ��Ƶ�secondHalf֮��
			System.arraycopy(list, L/2, secondHalf, 0, secondHalf.length);
			//�ݹ飺������������ٷֳ�����
			guibing(secondHalf);
			//һ�����еĵݹ��ֶ��Ѿ���������ô���й鲢
			int[]temp=merge(firstHalf,secondHalf);
			//��temp���������Ԫ�ظ��Ƶ�list֮��
			//�����ĺ��壺Դ���飬��������Ŀ�����飬�����������Ƴ���
			System.arraycopy(temp, 0, list, 0, temp.length);
		}
	}
	//�鲢��������
	//��Ϊ���������Ѿ�������ģ�����ֻ�����αȽ�����Ԫ�صĶ�Ӧֵ���ɣ�"˭С��˭��"
	private static int[] merge(int[] list1,int[] list2)
	{
		//�����������鼰����
		int[] tmp=new int[list1.length+list2.length];
		//������������ʼ��
		int current1=0,current2=0,current3=0;
		//�������������û�н�������Ҫ�Ƚ����ǵ�ֵ
		while(current1<list1.length&&current2<list2.length)
		{
			if(list1[current1]<list2[current2])
				tmp[current3++]=list1[current1++];
			else
				tmp[current3++]=list2[current2++];
		}
		//���ֻʣ�µ�һ���������ˣ���ֱ�Ӱ�ʣ�µ�Ԫ�����η�����������֮��
		while(current1<list1.length)
			tmp[current3++]=list1[current1++];
		//���ֻʣ�µڶ����������ˣ���ֱ�Ӱ�ʣ�µ�Ԫ�����η�����������֮��
		while(current2<list2.length)
			tmp[current3++]=list2[current2++];
		//���ˣ��ϲ�������������������
		return tmp;
	}
	public static void main(String[] args) {
		//������������
		int[]a={-5,1,3,2,1,0,2,-2,0};
		int L=a.length;
	    //�鲢����
		guibing(a);
		//������
		for(int element:a)
			System.out.println(element);
		
	}

}
