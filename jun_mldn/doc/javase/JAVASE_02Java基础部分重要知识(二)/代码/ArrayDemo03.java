public class ArrayDemo03{
	public static void main(String args[]){
		int i[][] = {{1,2},{2,3,4},{3,4,5,6,7}} ;	// 此时属于静态初始化
		System.out.print("数组开辟之后的内容：") ;
		for(int x=0;x<i.length;x++){
			for(int y=0;y<i[x].length;y++){
				System.out.print(i[x][y] + "、") ;
			}
			System.out.println("") ;
		}
	}
};