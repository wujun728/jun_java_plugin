/**
 * @author <a href="https://mirrentools.org">MirrenTools</a>
 * @version 1.0.1
 *
 * 请求示例:
 * <script src="XhrRequest.js"></script>
 * 通用请求方式
 * XhrRequest.execute({options});
 * get请求方式
 * XhrRequest.get(url, successHandler, errorHandler, options);
 * post或put或delete
 * XhrRequest.post(url,data, successHandler, errorHandler, options);
 *
 * options配置说明:
 * url=请求连接
 * method或type=请求方法,默认GET
 * async=是否异步,默认true
 * contentType=请求类型,post时默认为:urlencoded
 * dataType=响应的数据类型,默认为json
 * headers=请求头Header参数,Json格式
 * data=请求的数据,接收字符串或json对象
 * timeout=请求的数据,接收字符串或json对象
 * successHandler=成功处理器,接收参数(result)
 * errorHandler=异常处理器,接收参数(XHR)
 * timeoutHandler=超时处理器();
 * requestFormatHandler=请求内容格式化处理器,接收参数(data),需要返回处理结果;
 * resultFormatHandler=返回结果内容格式化处理器,接收参数(dataType,XHR.responseText)
 *
 */
var XhrRequest = (function () {
  var Request = function () {
  };
  /**
   * 执行请求
   * @param options
   */
  Request.prototype.execute = function (options) {
    options = options || {};
    var url = options.url;
    var method = (options.method || options.type || "GET").toUpperCase();
    var async = options.async || true;
    var contentType = options.contentType || 'application/x-www-form-urlencoded; charset=UTF-8';
    var dataType = options.dataType || 'json';
    var data = options.data;
    var xhr;
    if (window.XMLHttpRequest) {
      xhr = new XMLHttpRequest()
    } else {
      xhr = new ActiveXObject("Microsoft.XMLHTTP")
    }
    //设置请求超时
    if (xhr.timeout) {
      if (isNaN(options.timeout) && options.timeout !== 0) {
        xhr.timeout = timeout;
      }
    }
    if (xhr.ontimeout && options.timeoutHandler) {
      xhr.ontimeout = options.timeoutHandler;
    }


    //加载请求数据
    if (options.requestFormatHandler) {
      data = options.requestFormatHandler(data);
    } else {
      if (options.data && typeof (options.data) == 'object') {
        try {
          JSON.stringify(options.data)
        } catch (e) {
          console.log(e);
          console.log('仅支持Json与字符串,如果非JSON类型请转换为字符串');
          throw new Error('Invalid JSON :' + options.data);
        }
      }
      if (method === 'GET') {
        if (data && typeof (data) == 'object') {
          var arr = [];
          for (var key in data) {
            arr.push(key + "=" + data[key]);
          }
          if (url.indexOf('?') === -1) {
            url += '?' + arr.join('&');
          } else {
            url += '&' + arr.join('&');
          }
        }
      } else {
        if (contentType && contentType.indexOf('urlencoded') !== -1 || contentType.indexOf('URLENCODED') !== -1) {
          if (data && typeof (data) == 'object') {
            var arr = [];
            for (var key in data) {
              arr.push(encodeURIComponent(key) + "=" + encodeURIComponent(data[key]));
            }
            data = arr.join('&');
          }
        } else {
          if (data && typeof (data) == 'object') {
            data = JSON.stringify(data);
          }
        }
      }
    }

    xhr.open(method, url, async);
    if (method !== 'GET') {
      xhr.setRequestHeader("Content-Type", contentType);
    }
    //添加请求Herders
    if (options.headers) {
      for (var key in options.headers) {
        xhr.setRequestHeader(key, options.headers[key]);
      }
    }
    xhr.send(data);
    xhr.onreadystatechange = function () {
      if (xhr.readyState === 4) {
        var status = xhr.status;
        if (status >= 200 && status < 300 || status === 304) {
          if (options.resultFormatHandler) {
            options.successHandler && options.successHandler(options.resultFormatHandler(dataType, xhr.responseText));
          } else {
            options.successHandler && options.successHandler((dataType && dataType === 'json') ? JSON.parse(xhr.responseText) : xhr.responseText);
          }
        } else {
          options.errorHandler && options.errorHandler(xhr)
        }
      }
    };
  };

  /**
   * 通过GET方式请求数据
   * @param url 请求路径
   * @param successHandler 成功处理器
   * @param errorHandler  失败处理器
   * @param options 配置信息
   */
  Request.prototype.get = function (url, successHandler, errorHandler, options) {
    options = options || {};
    options.url = url;
    options.successHandler = successHandler;
    options.errorHandler = errorHandler;
    this.execute(options);
  };
  /**
   * 通过post方式请求数据
   * @param url 请求路径
   * @param data 请求的数据
   * @param successHandler 成功处理器
   * @param errorHandler  失败处理器
   * @param options 配置信息
   */
  Request.prototype.post = function (url, data, successHandler, errorHandler, options) {
    options = options || {};
    options.url = url;
    options.method = 'POST';
    options.data = data;
    options.successHandler = successHandler;
    options.errorHandler = errorHandler;
    this.execute(options);
  };
  /**
   * 通过put方式请求数据
   * @param url 请求路径
   * @param data 请求的数据
   * @param successHandler 成功处理器
   * @param errorHandler  失败处理器
   * @param options 配置信息
   */
  Request.prototype.put = function (url,data, successHandler, errorHandler, options) {
    options = options || {};
    options.url = url;
    options.method = 'PUT';
    options.data = data;
    options.successHandler = successHandler;
    options.errorHandler = errorHandler;
    this.execute(options);
  };
  /**
   * 通过delete方式请求数据
   * @param url 请求路径
   * @param data 请求的数据
   * @param successHandler 成功处理器
   * @param errorHandler  失败处理器
   * @param options 配置信息
   */
  Request.prototype.deletes = function (url,data,  successHandler, errorHandler, options) {
    options = options || {};
    options.url = url;
    options.method = 'DELETE';
    options.data = data;
    options.successHandler = successHandler;
    options.errorHandler = errorHandler;
    this.execute(options);
  };


  if (Request.flag) {
    return Request.flag;
  }
  Request.flag = new Request();
  return Request.flag;
})();

