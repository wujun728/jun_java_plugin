import java.io.FileInputStream;
import java.security.cert.Certificate;
import java.security.cert.*;
//从文件中读取数字证书

public class CertificateExample
{
    public static void main(String[] args)
	{
		try{

			//生成文件输入流,输入文件为c:/mycert.cer
      		FileInputStream fin = new FileInputStream("c:/mycert.cer");

      		//获取一个处理X.509证书的证书工厂
      		CertificateFactory certFactory = CertificateFactory.getInstance("X.509");

      		//获取证书
      		Certificate mycert = certFactory.generateCertificate(fin);

      		fin.close();

      		System.out.println(mycert);
      }catch (Exception ex){ex.printStackTrace();}

	}
}