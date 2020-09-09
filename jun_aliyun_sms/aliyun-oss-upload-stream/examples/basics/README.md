## 修改配置信息

```
vi upload.js
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
node upload.js
```

也可以在配置完信息之后直接运行下面的命令

```
npm run upload
```