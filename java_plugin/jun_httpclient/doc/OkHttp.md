# FastHttpClient
封装OkHttp(jdk8以上)

======
powered by icecooly(icecooly.du@gmail.com).

Usage
==============
1.synchronized get
```java
FastHttpClient.get().
		url(url).
		addParams("userName", "icecool").
		addParams("password", "111111").
		build().
		execute();
```
		
2.synchronized post
```java
FastHttpClient.post().
		url(url).
		addParams("userName", "icecool").
		addParams("password", "111111").
		build().
		execute();
```

3.asynchronized get
```java
FastHttpClient.get().
			url(url).
			addParams("userName","icecool").
			addParams("password", "111111").
			build().
			executeAsync(new Callback() {
			@Override
			public void onFailure(Call call, Exception e, int id) {
				//TODO
			}

			@Override
			public void onResponse(Call call,Response response, int id) {
				try {
					System.out.println(response.body().string());
				} catch (IOException e) {
				}
			}
		});
```

4.asynchronized post
```java
FastHttpClient.post().
			url(url).
			addParams("userName","icecool").
			addParams("password", "111111").
			build().
			executeAsync(new Callback() {
			@Override
			public void onFailure(Call call, Exception e, int id) {
				//TODO
			}

			@Override
			public void onResponse(Call call,Response response, int id) {
				try {
					System.out.println(response.body().string());
				} catch (IOException e) {
				}
			}
		});
```

5.download file aynsc
```java
FastHttpClient.get().
		url("http://e.hiphotos.baidu.com/image/pic/item/faedab64034f78f0b31a05a671310a55b3191c55.jpg").
		build().addNetworkInterceptor(new DownloadFileInterceptor(){
			@Override
			public void updateProgress(long downloadLenth, long totalLength, boolean isFinish) {
				System.out.println("updateProgress downloadLenth:"+downloadLenth+
						",totalLength:"+totalLength+",isFinish:"+isFinish);
			}
		}).
		executeAsync(new DownloadFileCallback("/tmp/tmp.jpg") {//save file to /tmp/tmp.jpg
				@Override
				public void onFailure(Call call, Exception e, int id) {
					e.printStackTrace();
				}
				@Override
				public void onSuccess(Call call, File file, int id) {
					super.onSuccess(call, file, id);
					System.out.println("filePath:"+file.getAbsolutePath());
				}
				@Override
				public void onSuccess(Call call, InputStream fileStream, int id) {
					System.out.println("onSuccessWithInputStream");
				}
		});
Thread.sleep(50000);
```

6.upload file
```java
byte[] imageContent=FileUtil.getBytes("/tmp/test.png");
		response = FastHttpClient.post().
				url(url).
				addFile("file1", "a.txt", "123").
				addFile("file2", "b.jpg", imageContent).
				build().
				connTimeOut(10000).
				execute();
System.out.println(response.body().string());
```

7.https get
```java
Response response = FastHttpClient.get().url("https://kyfw.12306.cn/otn/").
				build()
				.execute();
System.out.println(response.body().string());
```

8.https post
```java
Response response = FastHttpClient.post().url("https://kyfw.12306.cn/otn/").
				build()
				.execute();
System.out.println(response.body().string());
```

9.make jar
Eclipse->Window->Show View->Ant
build.xml taget ->make jar
make all jars into one jar(default release/fasthttpclient-1.0.jar)