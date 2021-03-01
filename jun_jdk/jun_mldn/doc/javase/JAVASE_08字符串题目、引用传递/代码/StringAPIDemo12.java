public class StringAPIDemo12{
	public static void main(String args[]){
		String str = "TOM:89|JERRY:90|TONY:78" ;
		String s1[] = str.split("\\|") ;
		for(int x=0;x<s1.length;x++){
			String s2[] = s1[x].split(":") ;
			System.out.println(s2[0] + " --> " + s2[1]) ;
		}
	}
};
