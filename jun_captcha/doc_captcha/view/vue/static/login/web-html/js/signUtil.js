/**
 *desc:请求参数的封装函数
   此处为安吉后端--请求包装  不需要可修改
   如需修改 在verify.js文件  getPictrue  checkPictrue方法中修改
 */ 

function sign(data,token="") {
  let time = new Date().getTime();
  var arr = [];
  for (var item in data) {
    arr.push(item);
  }
  var paramsObj = {};
  for (var i = 0; i < arr.length; i++) {
    var key = arr[i];
    var value = data[key];
    paramsObj[key] = value;
  }
  // 签名校验
  var cr = {
    token: token,
    time: time,
    reqData: JSON.stringify(paramsObj)
  };
  // 排序key
  var keyArr = [];
  for (var item in cr) {
    if (item != "sign") {
      keyArr.push(item);
    }
  }
  keyArr.sort();
  // 生成加密
  var content = "";
  for (var i = 0; i < keyArr.length; i++) {
    var key = keyArr[i];
    var value = cr[key];
    content += key + value;
  }
  var signData = {
    token: token,
    time: time,
    reqData: paramsObj,
    sign: md5(content)
  }
  return signData;
}
