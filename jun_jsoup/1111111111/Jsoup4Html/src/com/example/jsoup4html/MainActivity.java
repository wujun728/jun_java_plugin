package com.example.jsoup4html;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.os.AsyncTask;
import android.os.Bundle;


public class MainActivity extends ListActivity {
	private ListView listView;
	private List<Map<String,String>> list;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		listView = this.getListView();
		//调用异步任务
		Loadhtml load = new Loadhtml();
		load.execute(); 
	}

	class Loadhtml extends AsyncTask<String,String,String>{

		ProgressDialog dialog = null;
		Document doc = null;
		
		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub

			try {
				doc = Jsoup.connect(Constans.NetAddress).timeout(5000).get();
				
				Document content = Jsoup.parse(doc.toString());
				//获取页面中的ID为siteNav的div
				Elements divs = content.select("#siteNav");
				//Elements divs = content.select("#new_nav_ul");
				//解析divs标记
				Document divcontions = Jsoup.parse(divs.toString());
				//获取divs中的所有li标记
				Elements elements = divcontions.getElementsByTag("li");
				Log.d("element", elements.toString());
				
				list = new ArrayList<Map<String,String>>();
				
				//获取所有elements中a标记的文本及链接地址，构成map集合，并将map添加list中
				
				for(Element e:elements){
					Map<String,String> map = new HashMap<String,String>();
					
					map.put("title", e.getElementsByTag("a").text());
					map.put("href", Constans.NetAddress+e.getElementsByTag("a").attr("href"));
					list.add(map);
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				Log.d("element", "+++++++++++++parse fail!");
				e.printStackTrace();
			}
			return null;
		}
		
		@Override
		protected void onPostExecute(String result){
			super.onPostExecute(result);
			
			Log.d("doc",doc.toString().trim());
			
			//对话框关闭
			dialog.dismiss();
			//将list数据绑定到listView
			listView.setAdapter(
					new SimpleAdapter(MainActivity.this,list,
					android.R.layout.simple_list_item_2,
					//new String[]{"title","href"},
					new String[]{"title","shape"},
					new int[]{android.R.id.text1,android.R.id.text2}
					));
			
			//将网页标题显示到手机页面标题位置
			MainActivity.this.setTitle(doc.title());
		}
		
		@Override
		protected void onPreExecute(){
			super.onPreExecute();
			//实例对话框
			dialog = new ProgressDialog(MainActivity.this);
			dialog.setMessage("正在加载数据......");
			
			dialog.setIndeterminate(false);
			dialog.setCancelable(false);
			dialog.show();
		}
		
		public boolean onCreateOptionMenu(Menu menu){
			getMenuInflater().inflate(R.menu.main, menu);
			return true;
			
		}
	}
}
