
/**
 * 泛型版本的数组逆置
 */
public class InverseArray<T> {
	public void swap(T[] array,int a,int b){
		T t=array[a];
		array[a]=array[b];
		array[b]=t;
	}
	public void printArray(T[] array){
		for(T i:array){
			System.out.print(i.toString()+" ");
		}
		System.out.println();
	}
	public void inverse(T[] array){
		if(array==null||array.length<=1){
			return;
		}
		for(int i=0,j=array.length-1;i<j;i++,j--){
			swap(array, i, j);
		}
	}
}
