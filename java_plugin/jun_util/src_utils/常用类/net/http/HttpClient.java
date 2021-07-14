package book.net.http;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * 访问HTTP的客户端。将网页的内容显示在控制台中。这里得到的是一堆HTML代码
 */
public class HttpClient {
	//网址URL 
	String urlString;

	public static void main(String[] args) throws Exception {
		// 第一个参数为网址
		if (args.length != 1) {
			System.out.println("Usage: java book.net.http.HttpClient url");
			System.exit(1);
		}
		HttpClient client = new HttpClient(args[0]);
		client.run();
	}

	public HttpClient(String urlString) {
		this.urlString = urlString;
	}

	public void run() throws Exception {
		//生成一个URL对象 
		URL url = new URL(urlString);
		//打开URL 
		HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
		//打印头信息 
		System.out.println("THE HEADERS");
		System.out.println("-----------");
		for (int i = 1;; ++i) {
			String key;
			String value;
			if ((key = urlConnection.getHeaderFieldKey(i)) == null)
				break;
			if ((value = urlConnection.getHeaderField(i)) == null)
				break;
			System.out.print(key);
			System.out.println(" is: " + value);
		}
		//得到输入流，即获得了网页的内容 
		BufferedReader reader = new BufferedReader(new InputStreamReader(urlConnection
				.getInputStream()));
		String line;
		System.out.println("-----CONTENT------");
		// 读取输入流的数据，并显示
		while ((line = reader.readLine()) != null){
			System.out.println(line);
		}
	}
}