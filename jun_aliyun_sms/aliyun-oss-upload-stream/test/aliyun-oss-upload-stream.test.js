/**!
 * Aliyun-oss-upload-stream - test/aliyun-oss-upload-stream.test.js
 *
 * Copyright(c) Berwin and other contributors.
 * MIT Licensed
 *
 * Authors:
 *   Berwin <liubowen.niubi@gmail.com> (https://github.com/berwin)
 */

'use strict';

var ALY = require('aliyun-sdk');
var fs = require('fs');

var ossConfig = {
  accessKeyId: '在阿里云OSS申请的 accessKeyId',
  secretAccessKey: '在阿里云OSS申请的 secretAccessKey',
  endpoint: 'http://oss-cn-hangzhou.aliyuncs.com',
  apiVersion: '2013-10-15'
};

var uploadConfig = {
  "Bucket": "Test-Bucket-Name",
  "Key": "Test-Key-Name"
};

describe('Piping data into the writable upload stream', function () {
  var ossStream = require('../lib/aliyun-oss-upload-stream.js')(new ALY.OSS(ossConfig));
  var uploadStream = ossStream.upload(uploadConfig);

  before(function (done) {
    uploadStream.on('error', function () {
      throw "Did not expect to receive an error";
    });

    done();
  });

  it('should emit valid part and uploaded events', function (done) {
    var file = fs.createReadStream(process.cwd() + '/examples/basics/photo.jpg');
    var part = false, uploaded = false;

    uploadStream.on('part', function (details) {
      part = true;
      if (part && uploaded) done();
    });

    uploadStream.on('uploaded', function (details) {
      uploaded = true;
      if (part && uploaded) done();
    });

    file.pipe(uploadStream);
    file.on('error', function () {
      throw 'Error! Unable to open the file for reading';
    });
  });
});

describe('OSS Error catching', function () {
  describe('Bucket Not correct', function () {
    var ossStream = require('../lib/aliyun-oss-upload-stream.js')(new ALY.OSS(ossConfig));
    var uploadStream = ossStream.upload({"Bucket": "NotcorrectBucket", "Key": "test.photo.jpg"});

    it('should emit an error', function (done) {
      var file = fs.createReadStream(process.cwd() + '/examples/basics/photo.jpg');

      uploadStream.on('error', function (err) {
        done();
      });

      file.pipe(uploadStream);
    });
  });

  describe('Config Not correct', function () {
    var ossStream = require('../lib/aliyun-oss-upload-stream.js')(new ALY.OSS({
      accessKeyId: 'NotcorrectAccessKeyId',
      secretAccessKey: 'NotcorrectSecretAccessKey',
      endpoint: 'http://oss-cn-hangzhou.aliyuncs.com',
      apiVersion: '2013-10-15'
    }));
    var uploadStream = ossStream.upload(uploadConfig);

    it('should emit an error', function (done) {
      var file = fs.createReadStream(process.cwd() + '/examples/basics/photo.jpg');

      uploadStream.on('error', function (err) {
        done();
      });

      file.pipe(uploadStream);
    });
  });
});