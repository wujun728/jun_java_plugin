package net.jueb.util4j.test;

public class H1 extends HotSwap{
	
	H2 h2=new H2();
	
	@Override
	public void show() {
		super.show();
		h2.show();
		System.out.println(h2.getClass().getClassLoader().toString());
	}

	protected String getTestStr()
	{
		return "testStr";
	}
	
	@Override
	public boolean isUsing() {
		return false;
	}
	@Override
	public long getVersion() {
		return 2;
	}

	@Override
	public long getUniqueId() {
		return 0;
	}
	public static void main(String[] args) {
		long[] a=null;//1KW
		long[] b=null;//5KW
		byte[][] mark=new byte[][]{};
		for(int i=0;i<11;i++)
		{
			byte[] n=new byte[9];
			for(int j=0;j<10;j++)
			{
				n[i]=0;
			}
			mark[i]=n;
		}
		for(long n:a)
		{//分解1KW为树节点
			int w1=0;//号码第一位
			int w2=0;//好嘛第二位
			int w3=0;//好嘛第三位
			//省略
			mark[1][w1]=1;//1表示占位
			mark[2][w1]=1;
			mark[3][w1]=1;
			//省略
		}
		for(long n:b)
		{//寻找5KW内有相同树节点的,这里可多线程,mark数组不存在并发问题
			int w1=0;//号码第一位
			int w2=0;//好嘛第二位
			int w3=0;//好嘛第三位
			if(mark[1][w1]==1 && mark[2][w2]==1 && mark[3][w3]==1)
			{//存在相同手机号码
				
			}
		}
		
	}
	
}
