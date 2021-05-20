public class ArrayDemo02{
	public static void main(String args[]){
		int i[] = {1,2,3,4,6,7} ;	// 此时属于静态初始化
		System.out.print("数组开辟之后的内容：") ;
		for(int x=0;x<i.length;x++){
			System.out.print(i[x] + "、") ;
		}
	}
};