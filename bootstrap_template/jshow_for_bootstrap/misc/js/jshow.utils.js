/**
 util类
*/
(function($,w){
	w.gUtils = w.gUtils || {};
	var utils = w.gUtils;
 
	$.extend(utils,{
			 /** 渲染简单模板
			  * @param template 模板
			  * @param data 数据
			  * */
		 fRenderTemplate:function(template,data){
			var content='';
			if(data instanceof Array){
				for(var i=0;i<data.length;i++){
					content+=this.fRenderTemplate(template,data[i]);
				}
			}else{
				var t=template;
				for(var attr in data){
					var value=data[attr];
					if(value==null){
						value='';
					}
					t=t.replace(new RegExp("@{"+attr+"}","gm"),value);
				}
				content+=t;
			}
			return content;
		 },
		/**
		 *    弹出确认框 操作成功刷新列表，操作失败提示消息      
		 **/
		fConfirmAndRefreshList:function(config) {	
			gDialog.fConfirm(config.title, config.info, function(rs) {
				if(!rs){
					return ;
				}
	            jQuery.ajax({
	    			url: config.url,
	    			type:'post',
	    			dataType: "json",
	    			data: config.param,
	    			success: function(data, ts) {
                        $('body').modalmanager('hideLoading');
						gDialog.fClose();
						if(data['success']) {
								if(data['message']!=null & data['message']!=''){
									message_box.show(data['message'],'success');
								}
								queryList();			
						 }else{
							var sMessage=data['message'];
							if(sMessage==null){
								sMessage='处理异常，请联系管理员！';
							}
							gDialog.fAlert(sMessage);
						 }
					},beforeSend: function() {
                        $('body').modalmanager('loading');
                    }
				});
			});
		},
        /**
         * ajax form 提交多文件上传支持
         * @param param 表单参数
         * @param validateSet 验证规则
         * @param url URL
         * @param successHandler 操作成功处理
         * @param errorHandler 操作失败处理
         */
        fSubmitFormWithFiles: function(fileElementIds,param, validateSet, url, successHandler, errorHandler) {

            if (form.validate(validateSet)) {
                $('body').modalmanager('loading');
                $.ajaxFileUpload({
                    url: url,
                    type:'post',
                    fileElementId:fileElementIds,
                    dataType: "json",
                    data: param,
                    success: function(data, ts) {
                        $('body').modalmanager('hideLoading');
                        gUtils.fProcessResult(data, successHandler, errorHandler);
                    },
                    error: function (data, status, e){
                        $('body').modalmanager('hideLoading');
                        alert(e);
                    }

            });
            }
        },
		/**
		 * 
		 * @param param 表单参数
		 * @param validateSet 验证规则
		 * @param url URL
		 * @param successHandler 操作成功处理
		 * @param errorHandler 操作失败处理
		 */
		fSubmitForm: function(param, validateSet, url, successHandler, errorHandler) {
            if(validateSet!=null && !form.validate(validateSet)){
                return;
            }
            $.ajax({
                url: url,
                type:'post',
                dataType: "json",
                data: param,
                success: function(data, ts) {
                    gUtils.fProcessResult(data, successHandler, errorHandler);
                },
                beforeSend: function() {
                    $('body').modalmanager('loading');
                }
            });
		},
		fProcessResult: function(data, successHandler, errorHandler) {
            $('body').modalmanager('hideLoading');
			if (data) {
				if (data['success'] && data['success'] == true) {
					successHandler.call(this,data);
				} else {

					if (errorHandler) {
						errorHandler.call(this,data);
					} else {
						var sMessage=data['message'];
				 		if(!sMessage||sMessage==''){
							sMessage='处理异常，请联系管理员！';
						}
				 		gDialog.fAlert(sMessage);
					}

					//$('.btn_submit').attr('disabled', false);
				}
			}
		},
		/** 获取主内容*/
		fGetBody: function(url, param,loadingMsg) {
			this.fGetHtml(url,'main_content' ,param,loadingMsg);
		},
		fGetHtml: function(url, id, param,loadingMsg) {
		//	$('#' + id).html('');
			var jContainer=$("#"+id);
			var timeFlag=(new Date()).getTime();
			jContainer.data('timeFlag',timeFlag);
			$.ajax({
				url:  url,
				dataType: "html",
				type:'post',
				data: param,
				success: function(data, textStatus) {
					var newTimeFlag=jContainer.data('timeFlag');
					if(timeFlag<newTimeFlag){
						//console.log('已经被新的内容更新，丢弃');
						return ;
					}
					jContainer.html(data);
				},
				beforeSend: function() {
					if(loadingMsg!=null &&loadingMsg!=false){
						gUtils.fLoading(id,loadingMsg);
					}
				}
			});
		},
		fLoading: function(id,loadingMsg){
			if(loadingMsg==true){
				loadingMsg='页面加载中，请稍候...';
			}
			$("#"+id).html('<div id="loading" class="data_loading">'+loadingMsg+'</div>');
		},
		fPageSize:function(methodName,dom,event){
			var $this=$(dom);
			var value=$this.val();
			value=value.replace(/[^\d]/g,'');
			$this.val(value);
			//解决浏览器之间的差异问题 
			var keyCode=event.keyCode ? event.keyCode:(event.which?event.which:event.charCode);
			if(keyCode!=13){ 
				return ;
			} 
			if(isNaN(value)){
				return;
			}
			var pageFun=eval(methodName);
			if(pageFun!=null){
				pageFun(1,value);
			}
			
		},
		fPostForm:function(url,param,target){
			target=target?target:'_blank';
			var _jForm = $("<form></form>",{ 
                            'id':'tempForm', 
                            'method':'post', 
                            'action':url, 
                            'target':target, 
                            'style':'display:none' 
                            }).appendTo($("body")); 


    		for(var attr in param){
    			var value=param[attr];
    			if(value!=null&&value!=''){
    				_jForm.append($("<input>",{'type':'hidden','name':attr,'value':value})); 
    			}
    		}
			
    		//触发提交事件 
            _jForm.trigger("submit"); 
            //表单删除 
          	 _jForm.remove(); 
		},
		/**
		 * 更新导航
		 * 
		 * @param id
		 * @param text
		 * @param url
		 */
		fUpdateNav : function(flag, id, text, url) {
			if (id == null) return;
			
			if (text!=null&&text!='') {
				var template='';
				var datas={'menuId':id,'menuName':text,'menuUrl':url};
				if(url==null||url==''){
					template='<li id="@{menuId}"><a href="javascript:;">@{menuName}</a> <span class="divider">/</span></li>';
				}else{
					template='<li id="@{menuId}"><a href="javascript:;" onclick="gUtils.fGotoNavLink(\'@{menuUrl}\',\'@{menuId}\')">@{menuName}</a> <span class="divider">/</span></li>';
				}
				var content=this.fRenderTemplate(template,datas);
				if(flag){
					$('#crumbs').html(content);
				}else{
					$('#crumbs').append(content);
				}
			}else{
				$('#' + id).remove();
			}
		},
		
		/**
		 * 跳转到指定页面，同时删除该导航后面的选项
		 */
		fGotoNavLink : function(url,id){
			var next=$("#"+id).next();
			if(next.length>0){
				$(next).remove();
			}
			this.fGetBody(url);
		}
	});
})(jQuery,window);




 /**
  * This tiny script just helps us demonstrate
  * what the various example callbacks are doing
  */
 window.message_box = (function($) {
     "use strict";

     var elem,
         hideHandler,
         that = {};
     var alertStyles=['alert-info','alert-success','alert-error'];
     
     that.init = function(options) {
     	if(elem){
     		return;
     	}
     	var html='<div id="message_box_info" class="alert alert-info alert-block" style="display:none;width:500px;position:fixed;'+
     	'left:50%;margin-left:-250px;top:30px;z-index:10000;"><button type="button" class="close">&times;</button>'+
     	'<span></span></div>';
     	$("body").append(html);
 		elem=$("#message_box_info");
 		elem.find('button.close').click(function(){
 			that.hide();
 		});
     };
     
     that.show = function(text,type,time) {
     	if(elem==null ||elem==undefined){
     		that.init();
     	}
     	 var alertClass='';
     	 if(type=='info'){
     		alertClass='alert-info';
     	 }else if(type=='success'){
     		alertClass='alert-success'; 
     	 }else if(type=='error'){
     		alertClass='alert-error'; 
     	 }
     	 for ( var i = 0; i < alertStyles.length; i++) {
     		 elem.removeClass(alertStyles[i]);
     	 	}
     	 if(alertClass!=''){
     		elem.addClass(alertClass);
     	 }
     	
         clearTimeout(hideHandler);
         elem.find("span").html(text);
         elem.slideDown();
         if(time==null || isNaN(time)){
         	time=4000;
         }
         
         hideHandler = setTimeout(function() {
             that.hide();
         }, time);
     };

     that.info=function(text,time){
         that.show(text,"info",time);
     };

     that.error=function(text,time){
         that.show(text,"error",time);
     };

     that.warning=function(text,time){
         that.show(text,"waring",time);
     };

     that.success=function(text,time){
         that.show(text,"success",time);
     };

     that.hide = function() {
         elem.fadeOut();
     };

     return that;
 }(jQuery));

/*----------------jquery扩展，讲form序列化为json object----tangzl---------------*/
$.fn.serializeObject = function(removeNullField,fieldsToStr) {
    var o = {};
    var a = this.serializeArray();

    $.each(a, function() {
        if(removeNullField&&!this.value) {
            return true;
        }
        if (o[this.name]) {
            if(fieldsToStr){
                o[this.name] = o[this.name]+","+this.value || '';
            }else{
                if (!o[this.name].push) {
                    o[this.name] = [ o[this.name] ];
                }
                o[this.name].push(this.value || '');
            }

        } else {
            o[this.name] = this.value || '';
        }

    });
    /*for(var field in o){
        alert(field + ":" + o[field] + " ");
    }*/
    return o;
}
