/**
 * springrain   javascript 封装
 */
(function($){
	$.springrain={
			/**
			 * AJAX  同意设置白名单
			 * @param _url
			 */
			ajaxFireWallHosts:function(_url){
				//白名单
				var _array=new Array();
				_array.push("apis.map.qq.com");
				
				var _temps=_array.join(",");
				if(_url.indexOf(_temps)!=-1){
					return true;
				}
			},
			appendToken:function(_url){
				var _that=this;
				if(!_url)return;
				if(_url.indexOf("springraintoken")!=-1)return _url;
				if(_url.indexOf("?")!=-1){
					return _url+"&springraintoken="+springraintoken;
				}else{
					return _url+"?springraintoken="+springraintoken;
				}
			},
			/**
			 * 根据参数key获取当前URL包含参数对应的值
			 * @param _name 要获取参数值的key
			 */
			getUrlParam:function(_name){
				var _that=this;
				var reg = new RegExp("(^|&)" + _name + "=([^&]*)(&|$)"); //构造一个含有目标参数的正则表达式对象
		        var r = window.location.search.substr(1).match(reg);  //匹配目标参数
		        if (r != null){
		        	$.springrain.token=unescape(r[2]);
		        }
		        else{
		        	$.springrain.token=null;
		        }
		        return $.springrain.token;
			},
			/**
			 * 跳转
			 * @param _url 要跳转的URL
			 */
			goTo:function(_url){
				var _that=this;
				if(!_url)return;
				location.href=this.appendToken(_url);
			},
			/**
			 * 删除
			 * @param _id	要删除的ID
			 * @param _url	删除URL
			 * @param _tips	删除提示，默认“是否删除”
			 * @param _redirect	删除成功，回跳的URL，传NULL默认会刷新当前页面
			 */
			mydelete:function(_id,_url,_tips,_redirect){
				var _that=this;
				if(!_url)return;
				var _pars=null;
				if(_id){
					_pars={"id":_id};
				}
				_tips=_tips?_tips:'是否删除?';
				layer.confirm(_tips, {icon: 3, title:'提示'}, function(index){
					  jQuery.ajax({
						  url:_url,
						  type:"post",
						  data:_pars,
						  dataType:"json",
						  async:false,
						  success:function(data){
							  if(data!=null&&"success"==data.status){
								  layer.msg(data.message==null?'删除成功':data.message, {
									  icon: 1,
									  time: 2000 //2秒关闭（如果不配置，默认是3秒）
									}, function(){
										if(_redirect){
											_that.goTo(_redirect);
										}else{
											window.location.reload();
										}
									}); 
							  }else{
								  layer.msg(data.message, {icon: 1,time: 1000}); 
							  }
						  }
					  });
					  layer.close(index);
				});
			},
			mydeletemore:function(formId,_url,_tips,_redirect){
				var _that=this;
				var arr="";
				var checks = jQuery(":checkbox[name='check_li']");
				checks.each(function(i, _obj) {
					if (_obj.checked) {
						arr += _obj.value+",";
					}
				});
				if (arr=="") {
					layer.confirm("没有选择待删除的数据", {icon: 3, title:'提示'}, function(index){
						  layer.close(index);
					});
					return false;
				}
				_tips=_tips?_tips:'是否删除?';
				layer.confirm(_tips, {icon: 3, title:'提示'}, function(index){
					  jQuery.ajax({
						  url:_url,
						  type:"post",
						  data:{records:arr},
						  dataType:"json",
						  async:false,
						  success:function(data){
							  if(data!=null&&"success"==data.status){
								  layer.msg(data.message==null?'删除成功':data.message, {
									  icon: 1,
									  time: 2000 //2秒关闭（如果不配置，默认是3秒）
									}, function(){
										if(_redirect){
											_that.goTo(_redirect);
										}else{
											window.location.reload();
										}
									}); 
							  }else{
								  layer.msg(data.message, {icon: 1,time: 1000}); 
							  }
						  }
					  });
					  layer.close(index);
				});
			},
			/**
			 * 初始化 验证表单,此方法适合单纯的表单页面，不适合左边数据，右边详情的页面（下面的方法）
			 * 要求：
			 * 表单名为validForm，提交按钮为：smtbtn，还原按钮：rstbtn，提示：.valid-info
			 * @param _before 请求前处理函数 
			 * @param _after 请求后处理函数
			 * @returns
			 *  _form   validform的对象，可手动调用_form.check(false)手动进行表单验证或调用其它API函数
			 */
			initValid:function(_before,_after){
				var index = null;
				var _that=this;
				jQuery("#validForm").append("<input type='hidden' name='springraintoken' value='"+_that.token+"'/>");
				var _form=jQuery("#validForm").Validform({
					btnSubmit:"#smtbtn", 
					btnReset:"#rstbtn",
					tiptype:4, 
					tiptype:function(msg,o,cssctl){
						var _obj=jQuery(o.obj).parents(".layui-form-item").find(".valid-info");
						cssctl(_obj,o.type);
						_obj.text(msg);
					},
					ignoreHidden:false,
					dragonfly:false,
					tipSweep:false,
					label:".label",
					showAllError:true,
					postonce:true,
					ajaxPost:true,
					datatype:{
						"*6-20": /^[^\s]{6,20}$/,
						"z2-4" : /^[\u4E00-\u9FA5\uf900-\ufa2d]{2,4}$/,
						"username":function(gets,obj,curform,regxp){
							//参数gets是获取到的表单元素值，obj为当前表单元素，curform为当前验证的表单，regxp为内置的一些正则表达式的引用;
							var reg1=/^[\w\.]{4,16}$/,
								reg2=/^[\u4E00-\u9FA5\uf900-\ufa2d]{2,8}$/;
				 
							if(reg1.test(gets)){return true;}
							if(reg2.test(gets)){return true;}
							return false;
				 
							//注意return可以返回true 或 false 或 字符串文字，true表示验证通过，返回字符串表示验证失败，字符串作为错误提示显示，返回false则用errmsg或默认的错误提示;
						},
						"email":/(^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\.[a-zA-Z0-9_-]+)+$)|(^$)/,
						"phone":/(^1(3|4|5|7|8)\d{9}$)|(^$)/,
						"phone1":function(){
							// 5.0 版本之后，要实现二选一的验证效果，datatype 的名称 不 需要以 "option_" 开头;	
						},
						"identity":function(gets,obj,curform,regxp){
							return _that.validIdentity(gets);
						}
					},
					usePlugin:{
						swfupload:{},
						datepicker:{},
						passwordstrength:{},
						jqtransform:{
							selector:"select,input"
						}
					},
					beforeCheck:function(curform){
						//在表单提交执行验证之前执行的函数，curform参数是当前表单对象。
						//这里明确return false的话将不会继续执行验证操作;	
					},
					beforeSubmit:function(curform){
						index=layer.load(null, {shade: [0.8, '#393D49'] });
						if(_before!=null &&typeof(_before)=="function"){
							var result = _before();
							if(result == false){
								layer.close(index);
								return false;
							}
						}
						//在验证成功后，表单提交前执行的函数，curform参数是当前表单对象。
						//这里明确return false的话表单将不会提交;	
					},
					callback:function(data){
						if(_after!=null &&typeof(_after)=="function"){
							_after();
						}
						layer.close(index);
						//返回数据data是json对象，{"info":"demo info","status":"y"}
						//info: 输出提示信息;
						//status: 返回提交数据的状态,是否提交成功。如可以用"y"表示提交成功，"n"表示提交失败，在ajax_post.php文件返回数据里自定字符，主要用在callback函数里根据该值执行相应的回调操作;
						//你也可以在ajax_post.php文件返回更多信息在这里获取，进行相应操作；
						//ajax遇到服务端错误时也会执行回调，这时的data是{ status:**, statusText:**, readyState:**, responseText:** }；
						//这里执行回调操作;
						//注意：如果不是ajax方式提交表单，传入callback，这时data参数是当前表单对象，回调函数会在表单验证全部通过后执行，然后判断是否提交表单，如果callback里明确return false，则表单不会提交，如果return true或没有return，则会提交表单。
					}
				});
				return _form;
			},
			/**
			 * 保存
			 * 带validform验证的表单AJAX提交,和方法"initValid"应用不同场景
			 * @param form	表单的ID  不传默认为,"updateForm"
			 * @param listurl 添加成功以后，跳转的URL
			 * @param message 操作成功以后提示的信息，  不传默认为 "操作成功"
			 * @param _id 为了防止，保存弄成修改，把ID清空
			 * @param callback 回调，如果有回调就不跳转listurl，跳转自己处理，调用 springrain.goTo(url)
			 * @returns
			 */
			commonSaveForm:function(form,listurl,message,_id,callback){
				var _that=this;
				if(!form){
					form="updateForm";
				}
				var id="#id";
				if(_id){
					id="#"+_id;
				}
				jQuery(id,jQuery("#"+form)).val("");//把form表单中的id弄空
				var _form=jQuery("#"+form).Validform({
					tiptype:4, 
					tiptype:function(msg,o,cssctl){
						var _obj=jQuery(o.obj).parents(".layui-form-item").find(".valid-info");
						cssctl(_obj,o.type);
						_obj.text(msg);
					}
				});
				if(!_form.check(false)){
					return _form;//把 验证对象返回
				}
				 var pageurl=jQuery("#"+form).attr('action'); 
				var mydata=jQuery("#"+form).serialize();
				_that.ajaxpostonlayer(pageurl,listurl,mydata,message,callback);
				return _form;
			},
			commonSubmit:function(formId){
				var _that=this;
				if(!formId){
					formId="updateForm";
				}
				var _action=jQuery("#"+formId).attr("action");
				jQuery("#"+formId).attr("action",_that.appendToken(_action)).submit();
			},
			/**
			 * 修改
			 * 带validform验证的表单AJAX提交,和方法"initValid"应用不同场景
			 * @param formId
			 * @param listurl
			 * @param message
			 * @param callback 回调，如果有回调就不跳转到Listurl
			 * @returns
			 */
			commonUpdateForm:function (formId,listurl,message,callback) {
				var _that=this;
				if(!formId){
					formId="updateForm";
				}
				var _form=jQuery("#"+formId).Validform({
					tiptype:4, 
					tiptype:function(msg,o,cssctl){
						var _obj=jQuery(o.obj).parents(".layui-form-item").find(".valid-info");
						cssctl(_obj,o.type);
						_obj.text(msg);
					}
				});
				if(!_form.check(false)){
					return _form;
				}
				var pageurl=jQuery("#"+formId).attr('action'); 
				var mydata=jQuery("#"+formId).serialize();
				_that.ajaxpostonlayer(pageurl,listurl,mydata,message,callback);
			},
			/**
			 * 重置表单验证的 验证结果  ，配合“commonSaveForm”使用
			 * @param _form 表单验证对象
			 */
			resetForm:function(_form){
				if(!_form)return;
				try{
					_form.resetForm();
				}catch(e){
					console.log(e);
				}
				jQuery(".valid-info").html('');
			},
			/**
			 * 配合 “commonSaveForm” 提交表单，也可单独使用
			 * @param pageurl 要保存的URL
			 * @param listurl 保存成功后 跳转的URL
			 * @param mydata  保存的数据,为表单的 序列化数据 serialize();
			 * @param msg 保存成功的提示消息
			 * @returns {Boolean}
			 */
			ajaxpostonlayer:function(pageurl,listurl,mydata,msg,callback){
				var _that=this;
				var index = layer.load(null, {shade: [0.8, '#393D49'] });
				if(pageurl==null||pageurl==''){
					layer.alert('提交地址不能为空！', {icon: 5});
					layer.closeAll('loading')
					return false;
				}
				if(!msg){
					msg="操作成功!";
				}
				mydata.springraintoken=_that.token;
				jQuery.ajax({
					url :pageurl, 
				    type :"post",
					data:mydata,
					dataType : "json",
					success:function(ret){
						layer.closeAll('loading')
						if(callback&&typeof callback=="function"){
							callback(ret);
							return;
						}
						if(ret.status=="success"){
							layer.alert(msg, {icon: 1},function(){
								layer.closeAll();
								if(listurl!=null&&listurl!=""){
									_that.goTo(listurl);
								}
							});
							
						}else{
							_that.error('sorry,操作失败了 ...');
							layer.closeAll('loading')
						}
					},
					error:function(){
						layer.closeAll('loading')
						_that.error('sorry,操作失败了 ...');
					}
				});
			},
			msg:function(_msg,_icon,_time,_fun){
				_msg=_msg?_msg:"默认消息";
				_icon=_icon?_icon:"1";
				_time=_time?_time:2000;
				
				layer.msg(_msg, {
					  icon: _icon,
					  time: 2000 //2秒关闭（如果不配置，默认是3秒）
					}, function(){
						if (_fun&&typeof _fun =="function") {
							_fun();
							layer.closeAll();
						}
					}
				);   
			},
			/**
			 * 消息提示
			 * @param message 提示的内容
			 * @param fun 确认后的回调
			 */
			alert:function(_message, _fun){
				var _that=this;
				_that.layerInfo(_message, 1, _fun);
			},
			/**
			 * 错误提示
			 * @param message
			 * @param fun
			 */
			error:function(_message, _fun){
				var _that=this;
				_that.layerInfo(_message, 5, _fun);
			},
			/**
			 * 消息提示
			 * @param message
			 * @param fun
			 */
			info:function(_message, _fun){
				var _that=this;
				_that.layerInfo(_message, 0, _fun);
			},
			/**
			 * 警告
			 * @param message
			 * @param fun
			 */
			warning:function(_message, _fun){
				var _that=this;
				_that.layerInfo(_message, 2, _fun);
			},
			/**
			 * 通用layer提示方法
			 * @param message
			 * @param fun
			 */
			layerInfo:function(_message,_icon,_fun) {
				if (_fun&&typeof _fun =="function") {
					layer.alert(_message, {
						icon : _icon
					}, _fun);
				} else {
					layer.alert(_message, {
						icon : _icon
					});
				}
			},
			/**
			 * 确认提示
			 * @param message
			 * @param fun
			 */
			confirm:function(message, fun){
				layer.confirm(message, {
					title:"确认",
					icon : 3,
					btn : [ '确定', '取消' ]
				// 按钮
				}, function() {
					if (fun&&typeof fun =="function") {
						fun();
						layer.closeAll();
					}
				}, function() {

				});
			},
			/**
			 * 输入框提示
			 * @param message
			 * @param fun
			 */
			prompt:function(message, fun){
				layer.prompt({
					title : message,
					formType : 1
				// prompt风格，支持0-2
				}, function(pass) {
					if (fun&&typeof fun =="function") {
						fun(pass);
					}
				});
			},
			/**
			 * select2  ajax封装
			 * @param _domId  要操作的DOM
			 * @param _url	  AJAX的URL
			 * @param _delay  延迟AJAX 毫秒
			 * @param _idFildName  AJAX返回JSON，对应的ID
			 * @param _textFildName AJAX返回JSON，对应的TEXT
			 * @param _multival 是否是多选
			 */
			select2Remote:function(_domId,_url,_delay,_idFildName,_textFildName,_multival){
				if(!_url)return;
				_delay=_delay?_delay:1000;
				_idFildName=_idFildName?_idFildName:"id";
				_textFildName=_textFildName?_textFildName:"text";
				_multival = _multival || false;
				jQuery("#"+_domId).select2({
				      multiple: _multival,
					  ajax: {
					    url: _url,
					    dataType: 'json',
					    delay: _delay,
					    data: function (params) {return {q: params.term,page: params.page};},
					    processResults: function (data, page) { 
					    	return {results: data}
					    },
					    cache: false
					  },
					  id:function(obj){return obj["'"+_idFildName+"'"]},
					  escapeMarkup: function (markup) { return markup; }, // let our custom formatter work
					  minimumInputLength: 1,
					  templateResult: function(obj){
						  return eval("obj."+_textFildName);
				      },
				      templateSelection:function(obj){
						  return eval("obj."+_textFildName);
					  },
					  language: "zh-CN"
					});
			},
			/**
			 * 阻止冒泡事件 
			 * @param e
			 */
			stopBubble:function (e){
			    if (e && e.stopPropagation){
			    	e.stopPropagation();
			    }else{
			    	window.event.cancelBubble=true
			    }
			},
			/**
			 * 私用，tree外界回调使用。对外无用 
			 */
			openMenu:function(treeId,e){
				if(!treeId)return;
				var _offset=jQuery(event.target).offset();
				jQuery(".add_div").css({'display':'block',"top":_offset.top+12,"left":_offset.left-125});
				jQuery(".add_div a").attr('tid',treeId);
				
			},
			beforeClick:function(_fun){
				_fun();
			},
			/**
			 * ztree的封装，可有点击菜单或无有菜单
			 * 需要引入ztree相关
			 * 如果要layerui的主题，要引入myztree.css
			 * @param opts配置参数【
			 * hasMenu:是否有点击菜单；
			 * id:放ztree的DOM对象ID；
			 * data:JSON对象，id,pid,name；
			 * treeNodeClick:点击ztree元素时的事件；btns:菜单数组，格式为[{'text':'菜单名','callback':'回调方法function（ztree实体，点击的ztrteId）'},{},{},...]】
			 * @returns 返回 ztree的实体,可调用ztree的API方法
			 */
			tree : function(opts) {
				var _that = this;
				var _defauts={
						hasMenu:false,
						btns:null
				}
				jQuery.extend(_defauts,opts);
				jQuery('body').bind('click',function(e){
					if(jQuery(event.target).hasClass("myctr")){
						//阻止事件冒泡
						return;
					}
					jQuery(".add_div").css('display','none');
				});
				var menuTreesetting = {
					view : opts.hasMenu ? {
						showLine : false,//是否显示线
						showIcon : false,//是否显示图标,此入不显示，用自定义的图标
						selectedMulti : false,//不支持多选
						dblClickExpand : false,//不支持双击
						addDiyDom : addDiyDom,//自定义的dom，为模仿layerui的样式
						addHoverDom : addHoverDom,//鼠标放上去，显示菜单按钮
						removeHoverDom : removeHoverDom
					//鼠标移去后，不显示菜单按钮
					} : {
						showLine : false,//是否显示线
						showIcon : false,//是否显示图标,此入不显示，用自定义的图标
						selectedMulti : false,//不支持多选
						dblClickExpand : false,//不支持双击
						addDiyDom : addDiyDom,//自定义的dom，为模仿layerui的样式
					},
					callback : {
						onClick : function(event, treeId, treeNode){
							if(jQuery(event.target).hasClass("myctr")){
								//阻止事件冒泡
								return false;
							}
							jQuery(".add_div a").attr('tid',treeNode.tId);
							opts.treeNodeClick(event, treeId, treeNode);
							jQuery(".add_div").css('display','none');
						}
					//单击事件，显示数据并可修改
					},
					data : {
						key:{
							children:"leaf"
						},
						simpleData : {
							enable : true,
							idKey : "id",//指定id  key
							pIdKey : "pid",//指定 父id key
							rootPId : "null"//根PID 默认为空
						}
					}
				};
				//私有方法
				function addDiyDom(treeId, treeNode) {
					var spaceWidth = 5;
					var switchObj = jQuery("#" + treeNode.tId + "_switch"), icoObj = jQuery("#"
							+ treeNode.tId + "_ico");
					switchObj.remove();
					icoObj.before(switchObj);
					if (treeNode.level > 1) {
						var spaceStr = "<span style='display: inline-block;width:"
								+ (spaceWidth * treeNode.level) + "px'></span>";
						switchObj.before(spaceStr);
					}
				}
				function addHoverDom(treeId, treeNode) {
					var sObj = jQuery("#" + treeNode.tId + "_span");
					if (jQuery("#ctr_" + treeNode.id).length > 0)
						return;
					sObj.after('<a  class="myctr myctrwrap" tid="' + treeNode.tId
							+ '" onclick="springrain.openMenu(\'' + treeNode.tId
							+ '\')" id="ctr_' + treeNode.id + '"></a>');
	
				}
				function removeHoverDom(treeId, treeNode) {
					jQuery("#ctr_" + treeNode.id).unbind().remove();
				}
				var _ztree = jQuery.fn.zTree.init(jQuery("#" + opts.id),
						menuTreesetting, opts.data);
				var _btns = opts.btns;
				if (!opts.hasMenu)
					return _ztree;
				if (_btns == null || _btns.length <= 0)
					return _ztree;
				var _btn = null;
				var _html_wrap = [
						'<div class="add_div" id="addDiv_t_auditlog_list" style="display:none;position:absolute;">',
						'</div>' ].join('');
				var _btn_html = [ '<a id="addBtn"  tid=""  href="#" class="ztree_add">{name}</a>' ]
						.join('');
				jQuery('body').append(_html_wrap);
				var _calls = new Array();
				for (var i = 0; i < _btns.length; i++) {
					_btn = _btn_html.replace('{name}', _btns[i].text);
					jQuery('.add_div').append(_btn);
					_calls.push(_btns[i].callback);
					jQuery(".add_div a").last().get(0).index = i;
					jQuery(".add_div a").last().get(0).onclick = function() {
						var _index = jQuery(this).get(0).index;
						var _tid = jQuery(this).attr('tid');
						var node = _ztree.getNodeByTId(_tid);
						removeHoverDom(_tid, node);
						jQuery("#"+_tid+"_a").addClass("curSelectedNode");
						_calls[_index](_ztree, node);
						jQuery(".add_div").css('display','none');
					};
				}
				return _ztree;
		},
		validIdentity:function(code){
			 var city={11:"北京",12:"天津",13:"河北",14:"山西",15:"内蒙古",21:"辽宁",22:"吉林",23:"黑龙江 ",31:"上海",32:"江苏",33:"浙江",34:"安徽",35:"福建",36:"江西",37:"山东",41:"河南",42:"湖北 ",43:"湖南",44:"广东",45:"广西",46:"海南",50:"重庆",51:"四川",52:"贵州",53:"云南",54:"西藏 ",61:"陕西",62:"甘肃",63:"青海",64:"宁夏",65:"新疆",71:"台湾",81:"香港",82:"澳门",91:"国外 "};
	            var tip = "";
	            var pass= true;
	            if(!code || !/^\d{6}(18|19|20)?\d{2}(0[1-9]|1[012])(0[1-9]|[12]\d|3[01])\d{3}(\d|X)$/i.test(code)){tip = "身份证号格式错误";pass = false;}
	            else if(!city[code.substr(0,2)]){tip = "地址编码错误";pass = false;}
	            else{
	                if(code.length == 18){code = code.split('');var factor = [ 7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2 ];var parity = [ 1, 0, 'X', 9, 8, 7, 6, 5, 4, 3, 2 ];var sum = 0;var ai = 0;var wi = 0;
	                    for (var i = 0; i < 17; i++){ai = code[i];wi = factor[i];sum += ai * wi;
	                    }
	                    var last = parity[sum % 11];
	                    if(parity[sum % 11] != code[17]){tip = "校验位错误";pass =false;}
	                }
	            }
	            //if(!pass) alert(tip);
	            return pass;
		}
	}
//私有方法
	
})(window);