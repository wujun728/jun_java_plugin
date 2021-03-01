## Android
### 引入依賴包  
根据对应版本接入即可
```
kapt "com.squareup.moshi:moshi-kotlin-codegen:1.8.0"
implementation "org.jetbrains.kotlinx:kotlinx-coroutines-core:1.1.1"
implementation "org.jetbrains.kotlinx:kotlinx-coroutines-android:1.1.1"
implementation 'com.google.android.material:material:1.0.0'
implementation 'com.google.code.gson:gson:2.8.5'
implementation 'com.squareup.retrofit2:retrofit:2.5.0'
implementation "com.squareup.retrofit2:converter-moshi:2.5.0"
implementation 'com.squareup.okhttp3:logging-interceptor:3.12.1'
implementation "com.squareup.moshi:moshi-kotlin:1.8.0"
implementation "com.jakewharton.retrofit:retrofit2-kotlin-coroutines-adapter:0.9.2"
```


### 使用方式
```
blockPuzzleDialog.show() //"滑动拼图"
wordCaptchaDialog.show()//"字符校验"
```

### 事件

| 参数  | 说明  |
| ------------ | ------------ |
| OnSeekBarChangeListener | 回调函数，可自定义回调内容，根据自己服务定制 |

### 请求处理
```
CommonInterceptor.kt
请求参数和请求结果处理的过滤器
Configuration.kt
网络请求基类
ServerApi.kt
网络请求接口
```
请求接口地址 配置在 ServerApi类中 ,需要更改地址替换`urlDefault`即可

