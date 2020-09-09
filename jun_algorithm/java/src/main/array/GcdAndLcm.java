import org.junit.Test;

public class GcdAndLcm {
/**
 * 最大公约数的递推算法
 */
public int gcd01(int m,int n){
	int a=Math.max(m, n);
	int b=Math.min(m, n);
	m=a;
	n=b;
	int r;
	while(m%n!=0){
		r=m%n;
		m=n;
		n=r;
	}
	return n;
}
/**
 * 最大公约数的递归算法
 */
public int gcd02(int m,int n){
	/*int a=Math.max(m, n);
	int b=Math.min(m, n);
	if(a%b==0){
		return b;
	}else{
		return gcd02(b, a%b);
	}*/
	return m>=n?m%n==0?n:gcd02(n, m%n):n%m==0?m:gcd02(m, n%m);
}
/**
 * 最小公倍数
 */
public int lcm(int m,int n){
	return m*n/gcd01(m, n);
}
@Test
public void testGcd01(){
	System.out.println(gcd01(100, 44));
	System.out.println(gcd01(44, 100));
}
@Test
public void testGcd02(){
	System.out.println(gcd02(105,252));
	System.out.println(gcd02(252, 105));
}
@Test
public void testLcm(){
	System.out.println(lcm(60, 24));
}
}
