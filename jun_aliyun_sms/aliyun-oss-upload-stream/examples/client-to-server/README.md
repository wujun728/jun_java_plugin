# 案例

## 修改配置信息

```
vim upload.js
```
配置accessKeyId、secretAccessKey、endpoint和Bucket、Key

```
var ossStream = require('../lib/aliyun-oss-upload-stream.js')(new ALY.OSS({
  accessKeyId: '在阿里云OSS申请的 accessKeyId',
  secretAccessKey: '在阿里云OSS申请的 secretAccessKey',
  endpoint: 'http://oss-cn-hangzhou.aliyuncs.com',
  apiVersion: '2013-10-15'
}));

var upload = ossStream.upload({
  Bucket: 'Bucket-Name',
  Key: 'Key-Name'
});
```

## 安装依赖

```
npm install
```

## 运行

```
npm start
```

## 访问

```
http://127.0.0.1:1995
```
在页面上传图片，并查看终端，会在终端打印出上传相关信息