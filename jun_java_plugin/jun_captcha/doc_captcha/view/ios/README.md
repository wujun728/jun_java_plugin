## iOS
### 引入pod  
根据对应版本接入即可
```
pod 'CryptoSwift', '~> 1.0'
pod 'Alamofire', '~>4.7.2'
pod 'SwiftyJSON', '~> 4.1.0'
pod 'HandyJSON', '~> 5.0.0-beta.1'
```


### 使用方式
```
// case puzzle     = 0 //"滑动拼图"
// case clickword   = 1 //"字符校验"
let type = CaptchaType(rawValue: sender.tag) ?? .puzzle
CaptchaView.show(type) { (v) in
    print(v)
}
```

### 事件

| 参数  | 说明  |
| ------------ | ------------ |
| completeBlock | 回调函数，可自定义回调内容，根据自己服务定制 |

### 请求处理
```
CaptchaRequest.swift
请求接口及处理
AJBaseRequest.swift
网络请求基类
```
请求接口地址 配置在 AJBaseRequest类中 ,需要更改地址替换`kServerBaseUrl`即可

