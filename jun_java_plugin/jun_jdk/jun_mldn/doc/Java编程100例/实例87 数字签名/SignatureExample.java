import java.security.*;

public class SignatureExample {
   public static void main(String[] args){
    try{
	    byte[] info ="待签名信息".getBytes();

	    //产生RSA密钥对(myKeyPair)
	    KeyPairGenerator myKeyGen= KeyPairGenerator.getInstance("RSA");
	    myKeyGen.initialize(1024);
	    KeyPair myKeyPair = myKeyGen.generateKeyPair();
	    System.out.println( "得到RSA密钥对" );

		//产生Signature对象,对用私钥对信息(info)签名.
	    Signature mySig = Signature.getInstance("SHA1WithRSA");  //用指定算法产生签名对象
	    mySig.initSign(myKeyPair.getPrivate());  //用私钥初始化签名对象
	    mySig.update(info);  //将待签名的数据传送给签名对象(须在初始化之后)
	    byte[] sigResult = mySig.sign();  //返回签名结果字节数组
	    System.out.println("签名后信息: "+ new String(sigResult) );

		//用公钥验证签名结果
	    mySig.initVerify(myKeyPair.getPublic());  //使用公钥初始化签名对象,用于验证签名
	    mySig.update(info); //更新签名内容
	    boolean verify= mySig.verify(sigResult); //得到验证结果
	    System.out.println( "签名验证结果: " +verify);
    }catch (Exception ex){ex.printStackTrace();}
    }
}
