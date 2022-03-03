package test;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.jsoup.Connection;
import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;
import org.junit.Test;
import org.springrain.frame.util.HttpClientUtils;

public class TestHttpClient {

	//@Test
 public void	test1() throws IOException{
		String url="https://mp.weixin.qq.com/intp/invoice/usertitlewxa?action=list_auth&invoice_key=SVJHbERPMGN1SUtLTENGQlpiajVXJj9TUFxUWTpXKnokeFxVKm0-NE5ZQjdOQi1Mdk9QXFR-dVVlb3s&pass_ticket=I10j0naqo3nfH%2BYQ7iASWq%2F5vw%2BPTaw579Lt%2BGRApwk%3D";
	
		Connection content= Jsoup.connect(url);
		Response execute = content.execute();
		System.out.println(execute.body());
	}
	
	@Test
 public void	test12() throws IOException{
		String url="http://114igo.cn/pingtai/system/order/getOrder";
		Map<String,String> map=new HashMap<String,String>();
		map.put("orderId", "1232131232");
		map.put("chepai", "我是中文123");
		map.put("amount", "1");
		
		HttpClientUtils.sendHttpPost(url, map);
	
	}
	
	
}
