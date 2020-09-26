package com.test.client.choice.choice01;

import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONObject;

public class TestClient {

	public static void main(String[] args) {
		URL url = null;
		HttpURLConnection http = null;

		try {

			url = new URL("http://0.0.0.0:8282/choiceCamel");

			for (int i = 0; i < 1; i++) {
				System.out.println("http post start !!!");
				Long startTime = System.currentTimeMillis();

				http = (HttpURLConnection) url.openConnection();

				// ************************************************************
				JSONObject authorityJson = new JSONObject();
				authorityJson.put("orgId", "cyx");

				JSONObject requestJson = new JSONObject();
				requestJson.put("data", authorityJson);
				requestJson.put("token", "asdaopsd89as0d8as7dasdas-=8a90sd7as6dasd");
				requestJson.put("desc", "");

				// ************************************************************

				StringBuffer sb = new StringBuffer();
				sb.append(requestJson.toString());
				System.out.println(sb.toString());

				String result = HttpClient.doPost(sb.toString(), 30000000, http);

				System.out.println("http post end cost :" + (System.currentTimeMillis() - startTime) + "ms");
				System.out.println(result);

				Thread.sleep(500);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
