ZEUS_BASE_URL="http://localhost:7500/zeus";

function ajaxRequest(method, url, callback, urlParams, bodyParams) {

    var urlSuffix = "";
    if(typeof urlParams != 'undefined') {
       for(var k in urlParams) {
           if(urlSuffix.length == 0) {
               urlSuffix = "?";
           } else {
               urlSuffix += "&";
           }
           urlSuffix += k + "=" + encodeURIComponent(urlParams[k]);
       }
    }
    
    var data = "";
    if(typeof bodyParams != 'undefined') {
        data = bodyParams;
    }
    
    var header = {};
    var token = $.cookie("zeus_token");
    if(typeof token != "undefined" && token != null) {
        header["Authorization"] = "Bearer " + token;
    }
    $.ajax({
        headers: header,
        dataType: 'json',
        processData: false,
        contentType: 'application/json',
        type: method,
        url:  ZEUS_BASE_URL + url + urlSuffix,
        data:  data,
        success: function(data) {     
            callback(data);
        } ,
        error: function(jqXHR, textStatus, errorThrown){
            var msg = JSON.stringify(jqXHR);
            console.error("连接服务器错误: " + msg +". 请联系管理员后再做尝试" );
        }
      });
}

function ajaxGet(url, callback, urlParams) {
    ajaxRequest("GET", url, callback, urlParams);
}

function checkLogin() {
    var token = $.cookie("zeus_token");
    if(typeof token == "undefined" || token == null) {
        location.href="./login.html";
    }
}

function logout() {
    $.removeCookie("zeus_token");
    location.href="./login.html";
}