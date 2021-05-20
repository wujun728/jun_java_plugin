

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;

/**
 * 百度短网址转换.
 */
public class ShortURL {

	/**
	 * 测试.
	 */
	public static void main(String[] args) {
		String shortURL = toShort("http://www.baidu.com/");
		System.out.println("Short URL: " + shortURL);
		System.out.println("Long URL: " + toLongURL(shortURL));
	}

	/**
	 * 转换为短网址.
	 */
	public static String toShort(String longURL) {
		String shortURL = "转换错误";
		try {
			HttpClient httpClient = new HttpClient();
			String url = "http://dwz.cn/create.php";
			PostMethod postMethod = new PostMethod(url);
			// 默认http以iso-8859-1编码传递，中文会有问题
			postMethod.setRequestHeader("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");
			postMethod.setParameter("url", longURL);
			int statusCode = httpClient.executeMethod(postMethod);

			if (statusCode == 200) {
				String soapResponseData = postMethod.getResponseBodyAsString();
				soapResponseData = new String(soapResponseData.getBytes("iso-8859-1"), "UTF-8");
				int index = soapResponseData.indexOf("\"tinyurl\":\"");
				if (index < 0) {
					index = soapResponseData.indexOf("\"err_msg\":\"");
				}
				shortURL = soapResponseData.substring(index + 11, soapResponseData.indexOf("\"", index + 12));
				shortURL = shortURL.replaceAll("\\\\", "");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return shortURL;
	}

	/**
	 * 还原为长网址.
	 */
	public static String toLongURL(String shortURL) {
		String longURL = "还原错误";
		try {
			HttpClient httpClient = new HttpClient();
			String url = "http://dwz.cn/query.php";
			PostMethod postMethod = new PostMethod(url);
			// 默认http以iso-8859-1编码传递，中文会有问题
			postMethod.setRequestHeader("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");
			postMethod.setParameter("tinyurl", shortURL);
			int statusCode = httpClient.executeMethod(postMethod);

			if (statusCode == 200) {
				String soapResponseData = postMethod.getResponseBodyAsString();
				soapResponseData = new String(soapResponseData.getBytes("iso-8859-1"), "UTF-8");
				int index = soapResponseData.indexOf("\"longurl\":\"");
				if (index < 0) {
					index = soapResponseData.indexOf("\"err_msg\":\"");
				}
				longURL = soapResponseData.substring(index + 11, soapResponseData.indexOf("\"", index + 12));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return longURL;
	}

}
