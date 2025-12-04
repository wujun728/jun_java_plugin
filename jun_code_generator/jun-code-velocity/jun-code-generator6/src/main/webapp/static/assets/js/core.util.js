/*工具类*/
var CoreUtil = (function () {
    var coreUtil = {};
    //var token = layui.data('LocalData')["Authorization"];
    var token = localStorage.getItem("Authorization");
    var datas ;

    /*GET*/
    coreUtil.sendGet = function(url, params, ft){
        this.sendAJAX(url, params, ft, "GET")
    }
    /*GET*/
    coreUtil.sendGet2 = function(url, params, ft){
        this.sendAJAX2(url, params, ft, "GET")
    }
    
    /*POST*/
    coreUtil.sendPost = function(url, params, ft){
        this.sendAJAX(url, JSON.stringify(params), ft, "POST")
    }
    /*PUT*/
    coreUtil.sendPut = function(url, params, ft){
        this.sendAJAX(url, JSON.stringify(params), ft, "PUT")
    }
    /*DELETE*/
    coreUtil.sendDelete = function(url, params, ft){
        this.sendAJAX(url, JSON.stringify(params), ft, "DELETE")
    }


    /*ajax*/
    coreUtil.sendAJAX = function(url, params, ft, method){
        var loadIndex = top.layer.load(0, {shade: false});
        $.ajax({
            url: url,
            cache: false,
            async: true,
            data: params,
            headers: {"authorization": token}, 
            type: method,
            contentType: 'application/json; charset=UTF-8',
            dataType: "json",
            beforeSend: function(request) {
                request.setRequestHeader("authorization", CoreUtil.getData("access_token"));
            },
            success: function (res) {
                top.layer.close(loadIndex);
                if (res.code==0 || res.code==200){
                    if(ft!=null&&ft!=undefined){
                        ft(res);
                    }
                }else if(res.code==401){ //凭证过期重新登录
                    layer.msg("凭证过期请重新登录", {time:2000}, function () {
                        top.window.location.href="/login.html"
                    })
                }else if(res.code==401){ //凭证过期重新登录
                    layer.msg("抱歉！您暂无权限", {time:2000})
                }else if(res.code==500){ //凭证过期重新登录
                    layer.msg(res.msg+",请联系管理员!", {time:2000});
                    return;
                } else {
                    layer.msg(res.msg, {time:2000});
                }
            },
            error:function (XMLHttpRequest, textStatus, errorThrown) {
                top.layer.close(loadIndex);
                if(XMLHttpRequest.status==404){
                    top.window.location.href="/404.html";
                }else{
                    layer.msg("服务器好像除了点问题！请稍后试试");
                }
            }
        })
    };


    /*ajax*/
    coreUtil.sendAJAX2 = function(url, params, ft, method){
        $.ajax({
            url: url,
            cache: false,
            async: false,
            data: params,
            type: method,
            headers: {"authorization": token}, 
            contentType: 'application/json; charset=UTF-8',
            dataType: "json",
            beforeSend: function(request) {
                request.setRequestHeader("authorization", CoreUtil.getData("access_token"));
            },
            success: function (res) {
                if (res.code==0){
                    if(ft!=null&&ft!=undefined){
                        ft(res);
                    }
                }else if(res.code==401001){ //凭证过期重新登录
                    layer.msg("凭证过期请重新登录", {time:2000}, function () {
                        top.window.location.href="/login.html"
                    })
                }else if(res.code==401008){ //凭证过期重新登录
                    layer.msg("抱歉！您暂无权限", {time:2000})
                } else {
                    layer.msg(res.msg);
                }
            },
            error:function (XMLHttpRequest, textStatus, errorThrown) {
                if(XMLHttpRequest.status==404){
                    top.window.location.href="/404.html";
                }else{
                    layer.msg("服务器好像除了点问题！请稍后试试");
                }
            }
        })
    };


    /*存入本地缓存*/
    coreUtil.setData = function(key, value){
        layui.data('LocalData',{
            key :key,
            value: value
        })
    };
    /*从本地缓存拿数据*/
    coreUtil.getData = function(key){
        var localData = layui.data('LocalData');
        return localData[key];
    };

    coreUtil.setLocal = function(key, value){
        layui.data('LocalData1',{
            key :key,
            value: value
        })
    };
    /*从本地缓存拿数据*/
    coreUtil.getLocal = function(key){
        var localData = layui.data('LocalData1');
        return localData[key];
    };

    //判断字符是否为空的方法
    coreUtil.isEmpty = function(obj){
        if(typeof obj == "undefined" || obj == null || obj == ""){
            return true;
        }else{
            return false;
        }
    }; 

    /*表单数据封装成 json String*/
    coreUtil.formJson = function (frm) {  //frm：form表单的id
        var o = {};
        var a = $("#" + frm).serializeArray();
        $.each(a, function () {
            if (o[this.name] !== undefined) {
                if (!o[this.name].push) {
                    o[this.name] = [o[this.name]];
                }
                o[this.name].push(this.value || '');
            } else {
                o[this.name] = this.value || '';
            }
        });
        return JSON.stringify(o);
    }; 

    coreUtil.formattime = function (val) {
        var date = new Date(val);
        var year = date.getFullYear();
        var month = date.getMonth() + 1;
        month = month > 9 ? month : ('0' + month);
        var day = date.getDate();
        day = day > 9 ? day : ('0' + day);
        var hh = date.getHours();
        hh = hh > 9 ? hh : ('0' + hh);
        var mm = date.getMinutes();
        mm = mm > 9 ? mm : ('0' + mm);
        var ss = date.getSeconds();
        ss = ss > 9 ? ss : ('0' + ss);
        var time = year + '-' + month + '-' + day + ' ' + hh + ':' + mm + ':' + ss;
        return time;
    }; 

    /*ajax请求*/
    coreUtil.sendAjaxOld = function (url, params, ft, method, headers, noAuthorityFt, contentType, async) {
        var roleSaveLoading = top.layer.msg('数据提交中，请稍候', {icon: 16, time: false, shade: 0.8});
        $.ajax({
            url: url,
            cache: false,
            async: async == undefined ? true : async,
            data: params,
            type: method == undefined ? "POST" : method,
            contentType: contentType == undefined ? 'application/json; charset=UTF-8' : contentType,
            dataType: "json",
            beforeSend: function (request) {
                if (headers == undefined) {

                } else if (headers) {
                    request.setRequestHeader("authorization", "Bearer " + CoreUtil.getData("access_token"));
                    request.setRequestHeader("refresh_token", "Bearer " + CoreUtil.getData("refresh_token"));
                } else {
                    request.setRequestHeader("authorization", "Bearer " + CoreUtil.getData("access_token"));
                }

            },
            success: function (res) {
                top.layer.close(roleSaveLoading);
                if (typeof ft == "function") {
                    if (res.code == 401001) { //凭证过期重新登录
                        layer.msg("凭证过期请重新登录")
                        top.window.location.href = "/index/login"
                    } else if (res.code == 401002) {  //根据后端提示刷新token
                        /*记录要重复刷新的参数*/
                        var reUrl = url;
                        var reParams = params;
                        var reFt = ft;
                        var reMethod = method;
                        var reHeaders = headers;
                        var reNoAuthorityFt = noAuthorityFt;
                        var reContentType = contentType;
                        var reAsync = async;
                        /*刷新token  然后存入缓存*/
                        CoreUtil.sendAjax("/sys/user/token", null, function (res) {
                            if (res.code == 0) {
                                CoreUtil.setData("access_token", res.data);
                                /*刷新成功后继续重复请求*/
                                CoreUtil.sendAjax(reUrl, reParams, reFt, reMethod, reHeaders, reNoAuthorityFt, reContentType, reAsync);
                            } else {
                                layer.msg("凭证过期请重新登录");
                                top.window.location.href = "/index/login"
                            }
                        }, "GET", true)
                    } else if (res.code == 0) {
                        if (ft != null && ft != undefined) {
                            ft(res);
                        }

                    } else if (res.code == 401008) {//无权限响应
                        if (ft != null && ft != undefined) {
                            noAuthorityFt(res);
                        }

                    } else {
                        layer.msg(res.msg)
                    }

                }
            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                top.layer.close(roleSaveLoading);
                if (XMLHttpRequest.status == 404) {
                    top.window.location.href = "/index/404";
                } else {
                    layer.msg("服务器好像除了点问题！请稍后试试");
                }
            }
        });
    }; 

    //字典数据回显
    coreUtil.selectDictLabel = function (datas, value) { 
        //datas = JSON.parse(datas);
        var label = "";
        if(datas.code != 500){
            $.each(datas, function(index, dict) {
                if (dict.value == ('' + value)) {
                    label = dict.label;
                    return false;
                }
            });
            //匹配不到，返回未知
            if (CoreUtil.isEmpty(label)) {
                return "未知";
            }
        }
        return label;
    };

    coreUtil.getPageName = function () { 
        var pageName = window.location.pathname;
        var index = pageName.lastIndexOf("/") + 1;
        var fileName = pageName.substr(index);
        //document.getElementById("pageName").textContent = fileName;
        return fileName;
    };

    coreUtil.getCheckValues = function () { 
        var _items = $('input:checkbox[name*="' + name + '"]:checked');
        var _itemsStr = "";
        layui.each(_items, function (i, n) {
            _itemsStr += "," + $(n).val();
        });
        if (_itemsStr.length > 0) {
            return _itemsStr.substr(1, _itemsStr.length);
        }
        return "";
    };
    

    //字典数据回显
    coreUtil.getSysDict = function (name) {
        var dictTemp = CoreUtil.getLocal("SysDict-"+name);
        if (dictTemp!=undefined && !CoreUtil.isEmpty(dictTemp)) {
            return dictTemp;
        }
        var datas ;
        $.ajax({
            url: "/api/sysDict/getType/"+name,
            cache: false,
            async: false,
            data: {"abc":1},
            type: "POST",
            contentType: 'application/json; charset=UTF-8',
            dataType: "json",
            beforeSend: function(request) {
                request.setRequestHeader("authorization", CoreUtil.getData("access_token"));
            },
            success: function (res) {
                if (res.code==0){
                    if(ft!=null&&ft!=undefined){
                        ft(res);
                    }
                }else if(res.code==401001){ //凭证过期重新登录
                    layer.msg("凭证过期请重新登录", {time:2000}, function () {
                        top.window.location.href="/login.html"
                    })
                }else if(res.code==401008){ //凭证过期重新登录
                    layer.msg("抱歉！您暂无权限", {time:2000})
                } else {
                    layer.msg(res.msg);
                }
                console.log('res='+res);
                datas = res;
            },
            error:function (XMLHttpRequest, textStatus, errorThrown) {
                if(XMLHttpRequest.status==404){
                    top.window.location.href="/404.html";
                }else{
                    layer.msg("服务器好像除了点问题！请稍后试试");
                }
            }
        });

        //匹配不到，返回未知
        if(CoreUtil.isEmpty(datas) ||  datas==undefined) {
            console.log('error:'+11111);
        }else{
            console.log(datas.length); 
        } 
        CoreUtil.setLocal("SysDict-"+name,datas);
        return datas;

    }

    return coreUtil;
})(CoreUtil, window);

if (!String.prototype.includes) {
  String.prototype.includes = function(search, start) {
    'use strict';
    if (typeof start !== 'number') {
      start = 0;
    }

    if (start + search.length > this.length) {
      return false;
    } else {
      return this.indexOf(search, start) !== -1;
    }
  };
}

 

function getPageName() {
    var pageName = window.location.pathname;
    var index = pageName.lastIndexOf("/") + 1;
    var fileName = pageName.substr(index);
    //document.getElementById("pageName").textContent = fileName;
}

// var map = getMap();
// map.put("395","12,21,52,89,35");
// map.put("396","121111,2222221,5333332,8444449,3555555");
// alert(map.get("395"));//输出：12,21,52,89,35
// alert(map.keyset()); //输出：395,396
function getMap()
{
    //初始化map_,给map_对象增加方法，使map_像Map
        var map_ = new Object();
        map_.put = function(key, value) {
        map_[key+'_'] = value;
    };

    map_.get = function(key) {
        return map_[key+'_'];
    };

    map_.remove = function(key) {
        delete map_[key+'_'];
    };

    map_.keyset = function() {
        var ret = "";
        for(var p in map_) {
            if(typeof p == 'string' && p.substring(p.length-1) == "_") {
                ret += ",";
                ret += p.substring(0,p.length-1);
            }
        }
        if(ret == "") {
            return ret.split(",");
        } else {
            return ret.substring(1).split(",");
        }
    };
    return map_;
}

function getSelectDict(dictCode) {
    var dictTemp = CoreUtil.getLocal("SysDict-"+dictCode);
    if (dictTemp!=undefined && !CoreUtil.isEmpty(dictTemp)) {
        return dictTemp;
    }else{
        var param = {};
        CoreUtil.sendGet2("/dev-api/system/dict/data/type/"+dictCode, param, function (res) {
            dictTemp = res;
            CoreUtil.setLocal("SysDict-"+dictCode,res);
            return dictTemp;
        });
    }
}


 /**
	* 文件下载
	* @param fileUrl		文件url
	* @param fileName		下载文件显示名称
*/
function downLoadFile(fileUrl, fileName){
    var index = location.href.indexOf("index.html");
    var pathName = location.href.substring(0, index);
    var url = pathName + "rest/download/downloadfileByName?imageUrl="+fileUrl+"&filename="+fileName;
    //调用后台接口，下载文件
    window.open(url, "_self");
};

 
function downLoadFileBlob(fileUrl, fileName){
    downLoadFileBlob(fileUrl, fileName, {});
}
function downLoadFileBlob(fileUrl, fileName, params){
    downLoadFileBlob(fileUrl, fileName, params,'POST');
}
function downLoadFileBlob(fileUrl, fileName, params,method){
    //var fileName = 'test1.xlsx';
    //var params = {xxx:111}; 
    var url = fileUrl;//;
    var xhr = new XMLHttpRequest(); 
    xhr.open(method, url, true); // 也可以使用POST方式，根据接口 
    xhr.setRequestHeader( "Authorization",localStorage.getItem('Authorization')); // 设置一个自定义的头部信息
    xhr.setRequestHeader('Content-Type', 'application/json'); // 如果需要覆盖默认的头部信息，比如 'Content-Type'
    xhr.responseType = 'blob'; // 返回类型blob 
    xhr.onload = function () { 
            var respType = xhr.getResponseHeader("Content-type");
            var contentDesc = xhr.getResponseHeader('Content-Disposition');
            if(contentDesc){
                //fileName = contentDesc.split('filename=')[1];
            }
            if (respType.indexOf("application/json") != -1) {
                let reader = new FileReader();
                reader.addEventListener('loadend', function (e) {
                    let data = JSON.parse(e.target.result);
                    layer.msg("提示：系统出错，错误信息为：" + data.msg + ",请将该信息提供给代维人员寻求帮助");
                });
                reader.readAsText(xhr.response);
            } else {
                let url = window.URL.createObjectURL(xhr.response);
                let a = document.createElement("a");
                a.href = url;
                a.style.display = 'none'
                a.download = fileName;
                //document.body.appendChild(a); 
                a.click();
                window.URL.revokeObjectURL(url);
                a.remove();
            }
    }; 
    xhr.send(params);// 发送ajax请求 
    //xhr.send(JSON.stringify({ key: 'value' }));// 发送数据

};
    
//时间戳转化成时间格式
function timeFormat(timestamp) {
    //timestamp是整数，否则要parseInt转换,不会出现少个0的情况
    //如果timestamp是10位数的需要 timestamp* 1000
    var time = new Date(timestamp);
    var year = time.getFullYear();
    var month = time.getMonth() + 1;
    var date = time.getDate();
    var hours = time.getHours();
    var minutes = time.getMinutes();
    var seconds = time.getSeconds();
    return year + '-' + add0(month) + '-' + add0(date) + ' ' + add0(hours) + ':' + add0(minutes) + ':' + add0(seconds);
}
function add0(m) {
    return m < 10 ? '0' + m : m
};

function toTree(data) {
    let result = []
    if (!Array.isArray(data)) {
      return result
    }
    data.forEach(item => {
        delete item.children;
    });
    let map = {};
    data.forEach(item => {
        map[item.id] = item;
    });
    data.forEach(item => {
      let parent = map[item.pid];
      if (parent) {
          (parent.children || (parent.children = [])).push(item);
      } else {
          result.push(item);
      }
    });
    return result;
  }