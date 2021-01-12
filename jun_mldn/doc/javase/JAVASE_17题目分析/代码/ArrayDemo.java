class Array{
	private int temp[] = null ;	// 只是声明数组，但是大小未知
	private int foot = 0 ;		// 用于保存下一个的记录点
	public Array(int len){
		if(len>0){
			this.temp = new int[len] ;	// 此时大小由外部决定
		}else{
			this.temp = new int[1] ;	// 至少开辟一个空间
		}
	}
	public boolean add(int i){		// 加入数据操作
		if(this.foot<this.temp.length){	// 还有空位
			this.temp[this.foot] = i ;	// 加入内容
			this.foot++ ;	// 改变长度
			return true ;	// 加入成功返回true
		}else{
			return false ;	// 加入失败
		}
	}
	public int[] getArray(){	// 返回全部的数组
		return this.temp ;
	}
};
class SortArray extends Array{
	public SortArray(int len){
		super(len) ;
	}
	public int[] getArray(){	
		java.util.Arrays.sort(super.getArray()) ;	// 排序操作
		return super.getArray() ;	// 返回的是排序后的内容
	}
};
class ReverseArray extends Array{
	public ReverseArray(int len){
		super(len) ;
	}
	public int[] getArray(){	
		int rt[] = new int[super.getArray().length] ;	// 根据大小开辟新数组
		int count = rt.length-1 ;
		for(int x=0;x<super.getArray().length;x++){
			rt[count] = super.getArray()[x] ;
			count-- ;
		}
		return rt ;
	}
};
public class ArrayDemo{
	public static void main(String args[]){
		ReverseArray arr = new ReverseArray(6) ;
		System.out.println(arr.add(3)) ;
		System.out.println(arr.add(23)) ;
		System.out.println(arr.add(1)) ;
		System.out.println(arr.add(5)) ;
		System.out.println(arr.add(6)) ;
		System.out.println(arr.add(8)) ;
		System.out.println(arr.add(11)) ;
		System.out.println(arr.add(16)) ;
		print(arr.getArray()) ;
	}
	public static void print(int i[]){
		for(int x=0;x<i.length;x++){
			System.out.print(i[x] + "、") ;
		}
	}
};