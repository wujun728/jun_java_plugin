package book.string;

public class IP2Long {
	//将127.0.0.1 形式的IP地址转换成10进制整数，这里没有进行任何错误处理
    public static long ipToLong(String strIP){
         long[] ip=new long[4];
         //先找到IP地址字符串中.的位置
         int position1=strIP.indexOf(".");
         int position2=strIP.indexOf(".",position1+1);
         int position3=strIP.indexOf(".",position2+1);
         //将每个.之间的字符串转换成整型
         ip[0]=Long.parseLong(strIP.substring(0,position1));
         ip[1]=Long.parseLong(strIP.substring(position1+1,position2));
         ip[2]=Long.parseLong(strIP.substring(position2+1,position3));
         ip[3]=Long.parseLong(strIP.substring(position3+1));
         return (ip[0]<<24)+(ip[1]<<16)+(ip[2]<<8)+ip[3]; 
    }

    //将10进制整数形式转换成127.0.0.1形式的IP地址
    public static String longToIP(long longIP){
         StringBuffer sb=new StringBuffer("");
         //直接右移24位
         sb.append(String.valueOf(longIP>>>24));
         sb.append(".");          
         //将高8位置0，然后右移16位
         sb.append(String.valueOf((longIP&0x00FFFFFF)>>>16)); 
         sb.append(".");
         sb.append(String.valueOf((longIP&0x0000FFFF)>>>8));
         sb.append(".");
         sb.append(String.valueOf(longIP&0x000000FF));
         return sb.toString(); 
    } 
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String ipStr = "192.168.0.1";
		long ipLong = IP2Long.ipToLong(ipStr);
		System.out.println("192.168.0.1 的整数形式为: " + ipLong);
		System.out.println("整数" + ipLong + "转化成字符串IP地址: " 
				+ IP2Long.longToIP(ipLong));
		//IP地址转化成二进制形式输出
		System.out.println("192.168.0.1 的二进制形式为: " 
				+ Long.toBinaryString(ipLong));
	}
}
