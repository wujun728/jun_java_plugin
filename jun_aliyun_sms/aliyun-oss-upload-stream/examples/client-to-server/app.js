'use strict';

var http = require('http');
var Busboy = require('busboy');
var ALY = require('aliyun-sdk');

var ossStream = require('../../lib/aliyun-oss-upload-stream.js')(new ALY.OSS({
  accessKeyId: '在阿里云OSS申请的 accessKeyId',
  secretAccessKey: '在阿里云OSS申请的 secretAccessKey',
  endpoint: 'http://oss-cn-hangzhou.aliyuncs.com',
  apiVersion: '2013-10-15'
}));

http.createServer(function(req, res) {
  if (req.method === 'POST') {
    var busboy = new Busboy({ headers: req.headers });
    busboy.on('file', function(fieldname, file, filename, encoding, mimetype) {
      var upload = ossStream.upload({
        Bucket: 'Bucket',
        Key: filename
      });

      upload.on('error', function (error) {
        console.log('error:', error);
      });

      upload.on('part', function (part) {
        console.log('part:', part);
      });

      upload.on('uploaded', function (details) {
        console.log('details:', details);
        res.writeHead(303, { Connection: 'close', Location: '/' });
        res.end();
      });

      file.pipe(upload);
    });
    
    req.pipe(busboy);
  } else if (req.method === 'GET') {
    res.writeHead(200, { Connection: 'close' });
    res.end('<html><head></head><body>\
      <form method="POST" enctype="multipart/form-data">\
        <input type="text" name="textfield"><br />\
        <input type="file" name="filefield"><br />\
        <input type="submit">\
      </form>\
    </body></html>');
  }
}).listen(1995, function() {
  console.log('Listening for requests');
});