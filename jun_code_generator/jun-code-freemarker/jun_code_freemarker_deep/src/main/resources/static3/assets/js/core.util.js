/*工具类*/
var CoreUtil = (function () {
    var coreUtil = {};
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
                }else if(res.code==401001){ //凭证过期重新登录
                    layer.msg("凭证过期请重新登录", {time:2000}, function () {
                        top.window.location.href="/login.html"
                    })
                }else if(res.code==401008){ //凭证过期重新登录
                    layer.msg("抱歉！您暂无权限", {time:2000})
                }else if(res.code==500001){ //凭证过期重新登录
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
    }

    //字典数据回显
    coreUtil.selectDictLabel = function (datas, value) { 
        //datas = JSON.parse(datas);
        var label = "";
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
        return label;
    }
     
    

    //字典数据回显
    coreUtil.getSysDict = function (name) {
        var dictTemp = CoreUtil.getLocal("SysDict-"+name);
        if (dictTemp!=undefined && !CoreUtil.isEmpty(dictTemp)) {
            return dictTemp;
        }

        var datas ;
         
		// CoreUtil.sendGet2("/api/sysDict/getType/"+name, null, function (res) {
        //     console.log('res='+res);
        //     datas = res;
	    // }); 
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

/** 获取当前项目的根路径，通过获取layui.js全路径截取assets之前的地址 */
function getProjectUrl() {
    var layuiDir = layui.cache.dir;
    if (!layuiDir) {
        var js = document.scripts, last = js.length - 1, src;
        for (var i = last; i > 0; i--) {
            if (js[i].readyState === 'interactive') {
                src = js[i].src;
                break;
            }
        }
        var jsPath = src || js[last].src;
        layuiDir = jsPath.substring(0, jsPath.lastIndexOf('/') + 1);
    }
    return layuiDir.substring(0, layuiDir.indexOf('assets'));
}
