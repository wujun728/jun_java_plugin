public class ArrayDemo01{
	public static void main(String args[]){
		int i[] = null ;
		// i = new int[10] ;	// 开辟了10个空间大小的数组
		System.out.print("数组开辟之后的内容：") ;
		for(int x=0;x<i.length;x++){
			System.out.print(i[x] + "、") ;
		}
		i[0] = 30 ;	// 为第一个元素赋值
		i[9] = 60 ;	// 为最后一个元素赋值
		System.out.print("\n数组赋值之后的内容：") ;
		for(int x=0;x<i.length;x++){
			System.out.print(i[x] + "、") ;
		}
	}
};