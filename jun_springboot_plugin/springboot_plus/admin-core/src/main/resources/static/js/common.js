/*一些基础的js方法，基础的业务js方法在lib.js里*/
//日期格式化
Date.prototype.format=function(format){var d=this,o={"M+":d.getMonth()+1,"d+":d.getDate(),"H+":d.getHours(),"m+":d.getMinutes(),"s+":d.getSeconds(),w:["日","一","二","三","四","五","六"][d.getDay()]};if(/(y+)/.test(format)){format=format.replace(RegExp.$1,(d.getFullYear()+"").substr(4-RegExp.$1.length))}for(var k in o){if(new RegExp("("+k+")").test(format)){format=format.replace(RegExp.$1,RegExp.$1.length==1?o[k]:("00"+o[k]).substr((""+o[k]).length))}}return format};

var Common = {
    ctxPath: "",
    version:"",
    log: function (info) {
        console.log(info);
    },
    alert: function (info, iconIndex) {
        parent.layer.msg(info, {
            icon: iconIndex
        });
        
        
    },
    info: function (info) {
        Common.alert(info, 0);
    },
    success: function (info) {
        Common.alert(info, 1);
    },
    error: function (info) {
    		Common.openConfirm(info)
    },
    post: function (url, paras, next) {
    		$.ajax({
    			url:Common.ctxPath+url,
    			type:"POST",
    			data:paras,
    			success:function(rsp){
    				if(rsp.code!=0){
    					Common.error(rsp.msg);
    					
    				}else{
    					//成功
    					if(next!=null){
        					next(rsp.data);
        				}else{
        					Common.success(rsp.responseJSON.msg);
        				}
    				}
    				
    			},
    			error:function(rsp){
    				Common.error(rsp.responseJSON.msg);
    			}
    		})
       
    },
    getOneFromTable:function(layuiTable,tableId){
    		var checkStatus = layuiTable.checkStatus(tableId)
        ,data = checkStatus.data;
        if(data.length==0){
        		Common.info("请选中一条记录");
        }else if(data.length>1){
        		Common.info("只能选中一条记录")
        }else{
        		return  data[0];
        }
    },
    getMoreDataFromTable:function(layuiTable,tableId){
		var checkStatus = layuiTable.checkStatus(tableId)
	    ,data = checkStatus.data;
	    if(data.length==0){
	    		Common.info("请选中记录");
	    }else{
	    		return  data;
	    }
    },
    openDlg:function(url,title){
    		var index = layer.open({  
            type: 2,  
            content: Common.ctxPath+url,  
            title: title,  
            maxmin: false
        });  
		layer.full(index);  
    },
    topOpenDlg:function(url,title){
                   		var index = top.layer.open({
                           type: 2,
                           content: Common.ctxPath+url,
                           title: title,
                           area:['100%', '100%'],
                           maxmin: false
                       });
               		layer.full(index);
                   },
    openConfirm:function(content,callback,callBackNo){
    		var index = layer.confirm(content, {
    		  btn: ['确认','取消'] //按钮
    		}, function(){
    		    if(callback!=null){
    		        callback();
    		    }
    			layer.close(index);
    		}, function(){
    		    if(callBackNo!=null){
    		        callBackNo()
    		    }
    			layer.close(index);
    		});
		
    },
    openPrompt:function(title,defaultValue,callback){
    		layer.prompt({title: title, formType: 0,value:defaultValue}, function(value, index,elem){
    		  layer.close(index);
    		  callback(value);
    		});
    },
    concatBatchId:function(data,idField){
    		var ids = ""
    		var name=idField==null?"id":idField;
    		for(var i=0;i<data.length;i++){
    			var item = data[i];
    			ids=ids+item[name];
    			if(i!=data.length-1){
    				ids=ids+","
    			}
    		}
    		return ids;
    },
    sessionTimeoutRegistry: function () {
        $.ajaxSetup({
            contentType: "application/x-www-form-urlencoded;charset=utf-8",
            complete: function (XMLHttpRequest, textStatus) {
                //通过XMLHttpRequest取得响应头，sessionstatus，
                var sessionstatus = XMLHttpRequest.getResponseHeader("sessionstatus");
                if (sessionstatus == "timeout") {
                    //如果超时就处理 ，指定要跳转的页面
                    window.location = Common.ctxPath + "/global/sessionError";
                }
            }
        });
    },
    initValidator: function(formId,fields){
        $('#' + formId).bootstrapValidator({
            feedbackIcons: {
                valid: 'glyphicon glyphicon-ok',
                invalid: 'glyphicon glyphicon-remove',
                validating: 'glyphicon glyphicon-refresh'
            },
            fields: fields,
            live: 'enabled',
            message: '该字段不能为空'
        });
    },
    getDate:function(date,pattern){
    		if(date==null||date==''){
    			return "";
    		}else{
    			if(pattern){
    				return new Date(date).format(pattern);
    			}else{
    				return date.substring(0,10);
    			}
    			
    		}
    }
    
};



// JQuery方法定制
(function($){
	
    
	$.ajaxSetup({
	  type: 'POST',
      async: true,
      dataType : "json",
      timeout : 30000 
	 });
	
	/**
	 * 获取form表单数据
	 */
	$.fn.getFormData = function (isValid) {
	  var fieldElem = $(this).find('input,select,textarea'); //获取所有表单域
	  var data ={};
	  layui.each(fieldElem, function(index, item){
      if(!item.name) return;
      if(/^checkbox|radio$/.test(item.type) && !item.checked) return;
      var value = item.value;
      if(item.type == "checkbox"){//如果多选
      	if(data[item.name]){
      		value = data[item.name] + "," + value;
      	}
      }
      if(isValid)
      {
    	 //如果为true,只需要处理有数据的值
    	 if(!$.isEmpty(value))
       {
    		 data[item.name] = value;
       }
      }
      else
      {
    	  data[item.name] = value;
      }
    });
    return data;
  };
  
  $.fn.serializeJson = function() {
      var serializeObj = {};
      var array = this.serializeArray();
      var str = this.serialize();
      $(array).each(
              function() {
                  if (serializeObj[this.name]) {
                      if ($.isArray(serializeObj[this.name])) {
                          serializeObj[this.name].push(this.value);
                      } else {
                          serializeObj[this.name] = [
                                  serializeObj[this.name], this.value ];
                      }
                  } else {
                      serializeObj[this.name] = this.value;
                  }
              });
      return serializeObj;
  };
 
  
  $.extend({
  	//非空判断
  	isEmpty: function(value) {
  		if (value === null || value == undefined || value === '') { 
  			return true;
  		}
  		return false;
    },
    //获取对象指
    result: function(object, path, defaultValue) {
    	var value = "";
  		if(!$.isEmpty(object) && $.isObject(object) && !$.isEmpty(path)){
  			var paths = path.split('.');
  			var length = paths.length;
  			$.each(paths,function(i,v){
  				object = object[v];
  				if(length-1 == i){
						value = object;
					}
  				if(!$.isObject(object)){
  					return false;
  				}
  			})
  			
  		}
  		
  		if($.isEmpty(value) && !$.isEmpty(defaultValue)){
  			value = defaultValue;
  		}
  		return value;
    },
    //判断是否obj对象
    isObject : function(value) {
      var type = typeof value;
      return value != null && (type == 'object' || type == 'function');
    },
    //是否以某个字符开头
    startsWith : function(value,target){
    	return value.indexOf(target) == 0;
    },
    //设置sessionStorage
    setSessionStorage:function(key, data){
    	sessionStorage.setItem(key, data);
    },
    //获取sessionStorage
    getSessionStorage:function(key){
    	return sessionStorage.getItem(key) == null ? "" : sessionStorage.getItem(key);
    },
    //删除sessionStorage
    removeSessionStorage:function(key){
    	sessionStorage.removeItem(key);
    },
    //清除sessionStorage
    clearSessionStorage:function(){
    	sessionStorage.clear();
    },
    uuid : function(){
  		return 'xxxxxxxx-xxxx-4xxx-yxxx-xxxxxxxxxxxx'.replace(/[xy]/g, function(c) {
		    var r = Math.random()*16|0, v = c == 'x' ? r : (r&0x3|0x8);
		    return v.toString(16);
  		});
    }
  });

}(jQuery));
