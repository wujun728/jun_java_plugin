# aliyun-oss-upload-stream

[![NPM Version](https://img.shields.io/npm/v/aliyun-oss-upload-stream.svg)](https://www.npmjs.com/package/aliyun-oss-upload-stream)
[![NPM Downloads](https://img.shields.io/npm/dm/aliyun-oss-upload-stream.svg)](https://www.npmjs.com/package/aliyun-oss-upload-stream)

用[Aliyun oss](https://github.com/aliyun-UED/aliyun-sdk-js) 的 Multipart upload API 实现的Node.js模块，通过stream的方式上传文件。

***官方指定Nodejs模块~***

## 为什么使用stream？

* 使用stream的方式上传文件可以很大程度上降低服务器内存开销。Aliyun官方SDK并没有对stream进行一个完美的封装，所以通常上传文件（Put Object）的流程是客户端上传文件到服务器，服务器把文件数据缓存到内存，等文件全部上传完毕后，一次性上传到Aliyun Oss服务。这样做一旦瞬间上传文件的请求过多，服务器的内存开销会直线上升。而使用stream的方式上传文件的流程是客户端在上传文件数据到服务器的过程中，服务器同时也在把文件数据往Aliyun Oss服务传送，而不需要在服务器上缓存文件数据。
* 可以上传大文件，根据上传数据方式不同而不同, Put Object 方式 文件最大不能超过 5GB，而使用stream的方式，文件大小不能超过 48.8TB
* 更快的速度，由于传统方式（Put Object方式）是客户端上传完毕文件后，统一上传到Aliyun Oss，而stream的方式基本上客户端上传完毕后，服务器已经把一大半的文件数据上传到Aliyun了，所以速度要快很多 
* 使用更简单，经过封装后，stream的方式使用起来非常的方便，1分钟就可以学会如何使用

## 例子

```javascript

var ALY = require('aliyun-sdk'),
  fs = require('fs');

var ossStream = require('aliyun-oss-upload-stream')(new ALY.OSS({
  accessKeyId: '在阿里云OSS申请的 accessKeyId',
  secretAccessKey: '在阿里云OSS申请的 secretAccessKey',
  endpoint: 'http://oss-cn-hangzhou.aliyuncs.com',
  apiVersion: '2013-10-15'
}));

var upload = ossStream.upload({
  Bucket: 'Bucket',
  Key: 'Key (可以理解为文件名)'
});

// 可选配置
upload.minPartSize(1048576); // 1M，表示每块part大小至少大于1M

upload.on('error', function (error) {
  console.log('error:', error);
});

upload.on('part', function (part) {
  console.log('part:', part);
});

upload.on('uploaded', function (details) {
  var s = (new Date() - startTime) / 1000;
  console.log('details:', details);
  console.log('Completed upload in %d seconds', s);
});

var read = fs.createReadStream('./photo.jpg');
read.pipe(upload);

var startTime = new Date();
```

## 使用

### 初始化

```javascript
var ALY = require('aliyun-sdk');

var ossStream = require('aliyun-oss-upload-stream')(new ALY.OSS({
  accessKeyId: '在阿里云OSS申请的 accessKeyId',
  secretAccessKey: '在阿里云OSS申请的 secretAccessKey',
  endpoint: 'http://oss-cn-hangzhou.aliyuncs.com',
  apiVersion: '2013-10-15'
}));
```

### 上传文件

```javascript
var upload = ossStream.upload({
  Bucket: 'Bucket-Name',
  Key: 'Key-Name'
});

var read = fs.createReadStream('./photo.jpg');
read.pipe(upload);
```

## 操作方法

**upload.minPartSize**

用于调整每次上传一小块数据的大小，不得低于200KB，默认为200KB，如果经常上传大文件，建议用此方法把值调整大一些

```
var upload = ossStream.upload({
  Bucket: 'Bucket-Name',
  Key: 'Key-Name'
});

// 可选配置
upload.minPartSize(1048576); // 1M，表示每块part大小至少大于1M

var read = fs.createReadStream('./photo.jpg');
read.pipe(upload);
```

## 事件

**error**

当上传过程中发生错误，触发error事件，回调函数参数为错误信息

```javascript
upload.on('error', function (error) {
  console.log('error:', error);
});
```
**part**

上传文件的过程中，会触发part事件，回调函数参数为当前分片的信息

```javascript
upload.on('part', function (part) {
  console.log('part:', part);
});
```

**uploaded**

上传成功后触发该事件，回调函数参数为完整的 Object

```javascript
upload.on('uploaded', function (details) {
  console.log('details:', details);
});

```

## 安装

```
npm i --save aliyun-oss-upload-stream
```

PS: 如果大家使用过程中，发现什么问题或者需要添加什么功能，及时通知我哈~ 我会及时更新和发布新版本~

## The MIT License (MIT)

Copyright (c) 2015 Berwin

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
