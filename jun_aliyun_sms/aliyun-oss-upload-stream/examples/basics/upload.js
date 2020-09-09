'use strict';

var ALY = require('aliyun-sdk'),
  fs = require('fs');

var ossStream = require('../../lib/aliyun-oss-upload-stream.js')(new ALY.OSS({
  accessKeyId: '在阿里云OSS申请的 accessKeyId',
  secretAccessKey: '在阿里云OSS申请的 secretAccessKey',
  endpoint: 'http://oss-cn-hangzhou.aliyuncs.com',
  apiVersion: '2013-10-15'
}));

var upload = ossStream.upload({
  Bucket: 'Bucket-Name',
  Key: 'Key-Name'
});

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