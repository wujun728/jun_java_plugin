	//显示历史记录数据
	function createTableRow(id,jsonText){
		if(jsonText){
	    	try{
		    	var arr=JSON.parse(jsonText);
		    	if(arr.length>0)
		    		$("#"+id).children('tr').remove();
				for(var i in arr){
					var disable=arr[i].disable;
					var name=arr[i].name;
					var value=arr[i].value;
					var type=arr[i].type;
					var require=arr[i].require;
					var remark=arr[i].remark;
					
					if(!name)
						continue;
					
					if(id=='body_data_list')
						addBodyDataRow(name,type,require,remark);
					else if(id=='form_data_list')
						addFormDataRow(disable,name,type,value,require,remark);
					else if(id=='headers_data_list')
						addHeadersDataRow(disable,name,type,value,require,remark);
					else if(id=='query_data_list')
						addQueryDataRow(name,type,value,require,remark);
					else if(id=='success_data_list')
						addSuccessDataRow(name,type,remark);
					else if(id=='failuer_data_list')
						addFailuerDataRow(name,type,remark);
				}
	        }catch(e){
				console.log(e)
	        }
	   	}
	}

    $("#body_type").change(function(){
        var opt=$("#body_type").val();
        if(opt=='json'){
            $('.body-type-json').removeClass('layui-hide');
            $('.form-data').addClass('layui-hide');
        }else{
            $('.body-type-json').addClass('layui-hide');
            $('.form-data').removeClass('layui-hide');
        }
    });
    
    //textarea 自适应高度
    function autoHeight(ele, minHeight) {
        minHeight = minHeight || 16;
        if (ele.style.height) {
            ele.style.height = (parseInt(ele.style.height) - minHeight ) + "px";
        }
        ele.style.height = ele.scrollHeight + "px";
    }

    function isSelected(type,num){
    	if(type=='string'&&num==1)
    		return 'selected="selected"';
    	else if(type=='number'&&num==2)
    		return 'selected="selected"';
    	else if(type=='array'&&num==3)
    		return 'selected="selected"';
    	else if(type=='object'&&num==4)
    		return 'selected="selected"';
    	else if(type=='boolean'&&num==5)
    		return 'selected="selected"';
    	else if(type=='0'&&num==6)
        	return 'selected="selected"';
    	else if(type==true&&num==7)
        	return 'checked="checked"';
    	else
    		return '';
    }

    function addBodyDataRow(name,type,require,remark){
    	var selected1=isSelected(type,1);
        var selected2=isSelected(type,2);
        var selected3=isSelected(type,3);
        var	selected4=isSelected(type,4);
        var selected5=isSelected(type,5);
        var selected6=isSelected(require,6);
        var tr='<tr>\n' +
            '                                    <td><input type="text" class="form-data-param input-max"  name="name" value="'+name+'" placeholder="参数名"/></td>\n' +
            '                                    <td class="td-min">\n' +
            '                                        <select name="type" class="form-data-param input-max" lay-ignore>\n' +
            '                                          <option value="string" '+selected1+'>string</option>\n' +
            '                                          <option value="number" '+selected2+'>number</option>\n' +
            '                                          <option value="array" '+selected3+'>array</option>\n' +
            '                                          <option value="object" '+selected4+'>object</option>\n' +
            '                                          <option value="file">file</option>\n' +
            '                                          <option value="int">int</option>\n' +
            '                                          <option value="long">long</option>\n' +
            '                                          <option value="date">date</option>\n' +
            '                                          <option value="boolean" '+selected5+'>boolean</option>\n' +
            '                                        </select>\n' +
            '                                    </td>\n' +
            '                                    <td class="td-min1">\n' +
            '                                        <select name="require" class="form-data-param input-max" lay-ignore>\n' +
            '                                            <option value="1">必填</option>\n' +
            '                                            <option value="0" '+selected6+'>选填</option>\n' +
            '                                        </select>\n' +
            '                                    </td>\n' +
            '                                    <td><input type="text" class="form-data-param input-max"  name="remark" value="'+remark+'" placeholder="选填。参数描述，便于生成文档"/></td>\n' +
            '                                    <td><button class="remove-btn" onclick="javascript:removeTr(this)" type="button"><i class="layui-icon layui-icon-close"></i></button></td>\n' +
            '                                </tr>';
        $("#body_data_list").append(tr);
    }

    function addFormDataRow(disabled,name,type,value,require,remark){
    	var selected1=isSelected(type,1);
        var selected2=isSelected(type,2);
        var selected3=isSelected(type,3);
        var	selected4=isSelected(type,4);
        var selected5=isSelected(type,5);
        var selected6=isSelected(require,6);
        var checked=isSelected(disabled,7)

        var tr='<tr>\n' +
            '                                    <td class="move-row form-data"><input type="checkbox" class="" lay-ignore  name="disable" value="1" '+checked+'/></td>\n' +
            '                                    <td><input type="text" class="form-data-param input-max"  name="name" value="'+name+'" placeholder="参数名"/></td>\n' +
            '                                    <td class="td-min">\n' +
            '                                        <select name="type" class="form-data-param input-max" lay-ignore>\n' +
            '                                          <option value="string" '+selected1+'>string</option>\n' +
            '                                          <option value="number" '+selected2+'>number</option>\n' +
            '                                          <option value="array" '+selected3+'>array</option>\n' +
            '                                          <option value="object" '+selected4+'>object</option>\n' +
            '                                          <option value="file">file</option>\n' +
            '                                          <option value="int">int</option>\n' +
            '                                          <option value="long">long</option>\n' +
            '                                          <option value="date">date</option>\n' +
            '                                          <option value="boolean" '+selected5+'>boolean</option>\n' +
            '                                        </select>\n' +
            '                                    </td>\n' +
            '                                  <td><input type="text" class="form-data-param input-max"  name="value" value="'+value+'" placeholder="参数值"/></td>\n' +
            '                                    <td class="td-min">\n' +
            '                                        <select name="require" class="form-data-param input-max" lay-ignore>\n' +
            '                                            <option value="1">必填</option>\n' +
            '                                            <option value="0" '+selected6+'>选填</option>\n' +
            '                                        </select>\n' +
            '                                    </td>\n' +
            '                                    <td><input type="text" class="form-data-param input-max"  name="remark" value="'+remark+'" placeholder="选填。参数描述，便于生成文档"/></td>\n' +
            '                                    <td><button class="remove-btn" onclick="javascript:removeTr(this)" type="button"><i class="layui-icon layui-icon-close"></i></button></td>\n' +
            '                                </tr>';
        $("#form_data_list").append(tr);
    }

    function addQueryDataRow(name,type,value,require,remark){
    	var selected1=isSelected(type,1);
        var selected2=isSelected(type,2);
        var selected5=isSelected(type,5);
        var selected6=isSelected(require,6);
        var tr='<tr>\n' +
            '                                  <td><input type="text" class="form-data-param input-max" oninput="appendParaUrl()" onporpertychange="appendParaUrl()" name="name" value="'+name+'" placeholder="参数名"/></td>\n' +
            '                                  <td class="td-min">\n' +
            '                                      <select name="type" class="form-data-param input-max" lay-ignore>\n' +
            '                                          <option value="string" '+selected1+'>string</option>\n' +
            '                                          <option value="number" '+selected2+'>number</option>\n' +
            '                                          <option value="int">int</option>\n' +
            '                                          <option value="long">long</option>\n' +
            '                                          <option value="date">date</option>\n' +
            '                                          <option value="boolean" '+selected5+'>boolean</option>\n' +
            '                                      </select>\n' +
            '                                  </td>\n' +
            '                                  <td><input type="text" class="form-data-param input-max" oninput="appendParaUrl()" onporpertychange="appendParaUrl()" name="value" value="'+value+'" placeholder="参数值"/></td>\n' +
            '                                  <td class="td-min">\n' +
            '                                      <select name="require" class="form-data-param input-max" lay-ignore>\n' +
            '                                          <option value="1">必填</option>\n' +
            '                                          <option value="0" '+selected6+'>选填</option>\n' +
            '                                      </select>\n' +
            '                                  </td>\n' +
            '                                  <td><input type="text" class="form-data-param input-max"  name="remark" value="'+remark+'" placeholder="选填。参数描述，便于生成文档"/></td>\n' +
            '                                  <td><button class="remove-btn" onclick="javascript:removeTr(this),appendParaUrl()" type="button"><i class="layui-icon layui-icon-close"></i></button></td>\n' +
            '                              </tr>';
        $("#query_data_list").append(tr);
    }

    function addHeadersDataRow(disabled,name,type,value,require,remark){
    	var selected1=isSelected(type,1);
        var selected2=isSelected(type,2);
        var selected6=isSelected(require,6);
    	var checked=isSelected(disabled,7)
        var tr='<tr>\n' +
            '                                  <td><input type="checkbox" class="" lay-ignore  name="disable" value="1" '+checked+'/></td>\n' +
            '                                  <td><input type="text" class="form-data-param input-max"  name="name" value="'+name+'" placeholder="Headers名"/></td>\n' +
            '                                  <td class="td-min">\n' +
            '                                      <select name="type" class="form-data-param input-max" lay-ignore>\n' +
            '                                          <option value="string" '+selected1+'>string</option>\n' +
            '                                          <option value="number" '+selected2+'>number</option>\n' +
            '                                      </select>\n' +
            '                                  </td>\n' +
            '                                  <td><input type="text" class="form-data-param input-max"  name="value" value="'+value+'" placeholder="Headers值"/></td>\n' +
            '                                  <td class="td-min">\n' +
            '                                      <select name="require" class="form-data-param input-max" lay-ignore>\n' +
            '                                          <option value="1">必填</option>\n' +
            '                                          <option value="0" '+selected6+'>选填</option>\n' +
            '                                      </select>\n' +
            '                                  </td>\n' +
            '                                  <td><input type="text" class="form-data-param input-max"  name="remark" value="'+remark+'" placeholder="选填。Headers描述，便于生成文档"/></td>\n' +
            '                                  <td><button class="remove-btn" onclick="javascript:removeTr(this)" type="button">X</button></td>\n' +
            '                              </tr>';
        $("#headers_data_list").append(tr);
    }

    function addCookiesDataRow(){
        var tr='<tr>\n' +
            '                                  <td><input type="text" class="form-data-param input-max"  name="name" value="" placeholder="cookies名"/></td>\n' +
            '                                  <td><input type="text" class="form-data-param input-max"  name="value" value="" placeholder="cookies值"/></td>\n' +
            '                                  <td><button class="remove-btn" onclick="removeTr(this)"><i class="layui-icon layui-icon-close"></i></button></td>\n' +
            '                              </tr>';
        $("#cookies_data_list").append(tr);
    }

	function createTrHtml(name,type,remark){
		var selected1=isSelected(type,1);
	    var selected2=isSelected(type,2);
	    var selected3=isSelected(type,3);
	    var	selected4=isSelected(type,4);
	    var selected5=isSelected(type,5);
		var tr='<tr>\n' +
	     '                                  <td class="move-row"><i class="layui-icon layui-icon-template-1"></i></td>\n' +
	     '                                  <td><input type="text" class="form-data-param input-max"  name="name" value="'+name+'" placeholder="参数名"/></td>\n' +
	     '                                  <td class="td-min">\n' +
	     '                                      <select name="type" class="form-data-param input-max" lay-ignore>\n' +
	     '                                          <option value="string" '+selected1+'>string</option>\n' +
	     '                                          <option value="number" '+selected2+'>number</option>\n' +
	     '                                          <option value="array" '+selected3+'>array</option>\n' +
	     '                                          <option value="object" '+selected4+'>object</option>\n' +
	     '                                          <option value="int">int</option>\n' +
	     '                                          <option value="long">long</option>\n' +
	     '                                          <option value="date">date</option>\n' +
	     '                                          <option value="boolean" '+selected5+'>boolean</option>\n' +
	     '                                      </select>\n' +
	     '                                  </td>\n' +
	     '                                  <td><input type="text" class="form-data-param input-max"  name="remark" value="'+remark+'" placeholder="选填。参数描述，便于生成文档"/></td>\n' +
	     '                                  <td><button class="remove-btn" onclick="javascript:removeTr(this)" type="button"><i class="layui-icon layui-icon-close"></i></button></td>\n' +
	     '                              </tr>';
	     return tr;
	}

    function addSuccessDataRow(name,type,remark){
        var tr=createTrHtml(name,type,remark);
        $('#success_data_list').append(tr);
    }

    function addFailuerDataRow(name,type,remark){
        var tr=createTrHtml(name,type,remark);
        $("#failuer_data_list").append(tr);
    }

    function removeTr(obj){
        $(obj).parent().parent().remove();
    }

      //鼠标拖拉tr上下移动
    function moveTableTr(tableBodyId) {
        $("#"+tableBodyId).sortable({
            cursor: "move",
            items: "tr",//只是tr可以拖动
            opacity: 1.0,//拖动时，透明度为0.6
            revert: false,//释放时，增加动画
            start: function(event, ui) {//开始移动
                let str = event.srcElement.innerHTML;//鼠标点击移动时获取的html内容
            },
            update: function(event, ui) {//移动之后更新排序之后
                let str = event.srcElement.innerHTML;
            }
        });
        $("#"+tableBodyId).disableSelection();
    }

    /**
     * 发送请求
     */
    function sendRequest(){
        var url=$('input[name=requestUrl]').val();
        var method=$('select[name=requestMode]').val();
        var contentType=$('#body_type').val();
        
        var bodyJson=$('textarea[name=bodyJson]').val();
        //form-data方式发起请求
        var data=getFormDataList();
		//json方式发起请求
		if(contentType=='json')
        	data=bodyJson;
		var startTime;
		var origin=window.location.origin;
		var str=url.split("/");
		var host=str[0]+"//"+str[2];
		if(origin==host){
			 $.ajax({
		            type: method,
		            url: url,
		            data:data,
		            xhrFields: {
		                withCredentials: true,// 允许携带cookie
		            },
		            beforeSend: function(request) {
		            	setRequestCookies();
		                setRequestHeaders(request,contentType);
		                startTime=new Date().getTime();
		            },
		            complete: function(xhr,data){
		            	var endTime=new Date().getTime();
		            	var time=endTime-startTime;
		            	$('.response-info').removeClass('layui-hide');
		            	$('#status').html(xhr.status);
		            	
		            	if(time>1000)
		            		time=time/1000 + ' s';
		            	else
		            		time+=' ms';
		            	$('#time').html(time);

		                $('#responseHeaders').val(xhr.getAllResponseHeaders());

		            	var contentType=xhr.getResponseHeader("content-type");
		                setResponse(xhr.responseText,contentType);
		            }
		        });
		}else{
			var body={
					"url":url,
					"body":bodyJson,
					"queryPara":getFormDataList(),
					"header":getHeaders(),
					"method":method,
					"contentType":contentType,
					"cookies":setRequestCookies()
			};
			 $.ajax({
		            type: 'post',
		            url: _path+'/runapi/sendRequest',
		            data:JSON.stringify(body),
		            dataType:'json',
		            contentType:"application/json",
		            timeout:30000, //设置超时的时间30s
		            beforeSend: function(request) {
		                startTime=new Date().getTime();
		            },
		            complete: function(xhr,data){
		            	var endTime=new Date().getTime();
		            	var time=endTime-startTime;
		            	$('.response-info').removeClass('layui-hide');
		            	$('#status').html(xhr.status);

		            	var json=xhr.responseJSON;
		            	if(json&&'fail'==json.state){
		            		var msg=json.msg;
		            		if(msg.indexOf('FileNotFoundException')!=-1||msg.indexOf('java.net.ConnectException')!=-1
		            				||msg.indexOf('connect timed out'!=-1))
		            			$('#status').html("404");
		            		if(msg.indexOf('Server returned HTTP response code: 500')!=-1)
		            			$('#status').html("500");
		            	}
		            	if(time>1000)
		            		time=time/1000 + ' s';
		            	else
		            		time+=' ms';
		            	$('#time').html(time);

		                $('#responseHeaders').val(xhr.getAllResponseHeaders());

		            	var contentType=xhr.getResponseHeader("content-type");
		                setResponse(xhr.responseText,contentType);
		            }
		        });
		}
    }

    function getFormDataList(){
    	var formData = {};
    	$("#form_data_list").find("tr").each(function(){
	        var tdArr = $(this).children();
	    	var disabled = tdArr.eq(0).find("input:checkbox").prop('checked'); //  是否启用
	        var formDataName = tdArr.eq(1).find("input").val(); //  参数名
	        var formDataValue = tdArr.eq(3).find("input").val();//  参数值
			if(disabled&&formDataName){
				formData[formDataName]=formDataValue;
			}
	    });
	    return formData;
    }
    
    function setRequestCookies(){
		var cookies="";
    	$("#cookies_data_list").find("tr").each(function(){
	        var tdArr = $(this).children();
	        var cookiesName = tdArr.eq(0).find("input").val(); //  参数名
	        var cookiesValue = tdArr.eq(1).find("input").val();//  参数值
			if(cookiesName&&cookiesValue){
				setCookie(cookiesName,cookiesValue);
				cookies+=cookiesName+"="+cookiesValue+";"
			}
	    });
	    return cookies;
    }

   //写cookies
    function setCookie(name,value) {
        var Days = 1;
        var exp = new Date();
        exp.setTime(exp.getTime() + Days*24*60*60*1000);
        document.cookie = name + "="+ escape (value) + ";path=/;expires=" + exp.toGMTString();
    }
    
    function setRequestHeaders(request,contentType){
    	if(contentType=='form-data')
    		request.setRequestHeader("Content-type","multipart/"+contentType+"; charset=utf-8");
    	else
    		request.setRequestHeader("Content-type","application/"+contentType+"; charset=utf-8");
    	$("#headers_data_list").find("tr").each(function(){
	        var tdArr = $(this).children();
	        var disabled = tdArr.eq(0).find("input:checkbox").prop('checked'); //  是否启用
	        var headerName = tdArr.eq(1).find("input").val(); //  参数名
	        var headerValue = tdArr.eq(3).find("input").val();//  参数值
			if(disabled&&headerName&&headerValue){
				 request.setRequestHeader(headerName,headerValue);
			}
	    });

    }
    
    function getHeaders(){
    	var headers={};
    	$("#headers_data_list").find("tr").each(function(){
	        var tdArr = $(this).children();
	        var disabled = tdArr.eq(0).find("input:checkbox").prop('checked'); //  是否启用
	        var headerName = tdArr.eq(1).find("input").val(); //  参数名
	        var headerValue = tdArr.eq(3).find("input").val();//  参数值
			if(disabled&&headerName&&headerValue){
				 headers[headerName]=headerValue;
			}
	    });
    	return headers;
    }

    //处理请求结果
    function setResponse(resText,contentType){
        if(contentType&&contentType.indexOf("application/json")!=-1){
        	var resJson={};
            try{
            	resJson=JSON.parse(resText);
            	$('#responseBody1').hide();
    	       	$('#responseBody1').val(JSON.stringify(resJson,null,4));
    		   	$('#responseBodyJson').show();
    	       	$('#responseBodyJson').jsonViewer(resJson);
    	       	$('#responseBody2').val(resText);
            }catch(e){
            	$('#responseBodyJson').hide();
            	$('#responseBody1').show();
                $('#responseBody1').val(resText);
                $('#responseBody2').val(resText);
            }
	       	
        }else{
        	$('#responseBodyJson').hide();
        	$('#responseBody1').show();
            $('#responseBody1').val(resText);
            $('#responseBody2').val(resText);
        }

        //美化
        autoHeight(document.getElementById('responseBody1'),100)

        //原生
        document.getElementById('responseBody1').style.maxHeight="300px";

     	// 预览
        reviewData()
    }

 	// 预览
	function reviewData(){
		var _iframe = document.getElementById('response_frame').contentWindow;
	    var _div =_iframe.document.getElementById('responseBody3');
		var requestResult=$('textarea[name=requestResult]').val();
		try{
			var resJson=JSON.parse(requestResult);
	    	_div.innerHTML='<pre>'+JSON.stringify(resJson,null,4)+'</pre>';
		}catch(e){
	    	_div.innerHTML=requestResult;
		}

	}

    //追加query参数追加到url中
    function appendParaUrl(){
        var url=$('input[name=requestUrl]').val();
        url=url.split("?")[0];
		$("#query_data_list").find("tr").each(function(){
	        var tdArr = $(this).children();
	        var paraName = tdArr.eq(0).find("input").val(); //  参数名
	        var paraValue = tdArr.eq(2).find("input").val();//  参数值
			if(paraName){
				paraName+="=";
				if(url.indexOf("?")!=-1){
	                url+="&"+paraName+paraValue;
		        }else{
					url+='?';
					url+=paraName+paraValue;
		        }
			}
	    });
		$('input[name=requestUrl]').val(url);
    }

    //从url中输入query参数生成参数列表
    function createQueryDataRow(){
    	var url=$('input[name=requestUrl]').val();
    	if(url.split("?").length>1){
    		$("#query_data_list").children('tr').remove();
	    	var paras=url.split("?")[1];
			var queryParam=paras.split('&');
			for(var i in queryParam){
				var arr=queryParam[i].split('=');
				if(arr[0])
					addQueryDataRow(arr[0],'string',arr[1]||'','','');
			}
        }
    	
    }



  	//美化json数据
    function formatSuccessJson(){
        var jsonText=$('textarea[name=successDataJson]').val();
        try {
            var json=JSON.parse(jsonText);
        }catch(e){
            layer.tips(e, '#formatSuccessJson',{
                time:4000,
                tips:3
            });
        }
        var jsonStr=JSON.stringify(JSON.parse(jsonText),null,4);
        $('textarea[name=successDataJson]').val(jsonStr);
    }

	

	// 美化失败示例的json数据
    function formatFailuerJson(){
        var jsonText=$('textarea[name=failuerDataJson]').val();
        try {
            var json=JSON.parse(jsonText);
        }catch(e){
            layer.tips(e, '#formatFailuerJson',{
                time:4000,
                tips:3
            });
        }
        var jsonStr=JSON.stringify(JSON.parse(jsonText),null,4);
        $('textarea[name=failuerDataJson]').val(jsonStr);
    }

 	// 美化请求body的json数据
    function formatBodyJson(){
        var jsonText=$('textarea[name=bodyJson]').val();
        try {
            var json=JSON.parse(jsonText);
        }catch(e){
            layer.tips(e, '#formatBodyJson',{
                time:4000,
                tips:3
            });
        }

        var jsonStr=JSON.stringify(JSON.parse(jsonText),null,4);
        $('textarea[name=bodyJson]').val(jsonStr);
    }

 	//展示说明信息
    $('#formatBodyJson').hover(function(){
        var index;
        var title="如果下面输入框中是标准的json字符串，则可以点击这里自动格式化（美化）"
            this.index=layer.tips(title, this,{
                tips:1, //还可配置颜色
            });
         },function(){
            layer.close(this.index);
    })

    $('#formatSuccessJson').hover(function(){
        var index;
        var title="如果下面输入框中是标准的json字符串，则可以点击这里自动格式化（美化）"
        this.index=layer.tips(title, this,{
            tips:1, //还可配置颜色
        });
    },function(){
        layer.close(this.index);
    })

    $('#formatFailuerJson').hover(function(){
        var index;
        var title="如果下面输入框中是标准的json字符串，则可以点击这里自动格式化（美化）"
        this.index=layer.tips(title, this,{
            tips:1, //还可配置颜色
        });
    },function(){
        layer.close(this.index);
    })

    $('#createJsonPara').hover(function(){
        var index;
        var title="如果上面面输入框中是标准的json字符串，则可以点击这里自动生成字段说明";
        this.index=layer.tips(title, this,{
            tips:1,
        });
        },function(){
            layer.close(this.index);
    })

    $('#createSuccessPara').hover(function(){
        var index;
        var title="如果上面面输入框中是标准的json字符串，则可以点击这里自动生成参数行";
        this.index=layer.tips(title, this,{
            tips:1,
        });
    },function(){
        layer.close(this.index);
    })

    $('#createFailuerPara').hover(function(){
        var index;
        var title="如果上面面输入框中是标准的json字符串，则可以点击这里自动生成参数行";
        this.index=layer.tips(title, this,{
            tips:1,
        });
    },function(){
        layer.close(this.index);
    })

    $('#copyCode').hover(function(){
        var index;
        var title="从现有响应体快速复制到这里";
        this.index=layer.tips(title, this,{
            tips:1,
        });
    },function(){
        layer.close(this.index);
    })

    $('#copyFailuerCode').hover(function(){
        var index;
        var title="从现有响应体快速复制到这里";
        this.index=layer.tips(title, this,{
            tips:1,
        });
    },function(){
        layer.close(this.index);
    })

    $('#removeSuccessPara').hover(function(){
        var index;
        var title="把下面的返回参数说明一键清空";
        this.index=layer.tips(title, this,{
            tips:1,
        });
    },function(){
        layer.close(this.index);
    })

    $('#removeFailuerPara').hover(function(){
        var index;
        var title="把下面的返回参数说明一键清空";
        this.index=layer.tips(title, this,{
            tips:1,
        });
    },function(){
        layer.close(this.index);
    })

	//复制数据到成功返回示例中
    function copySuccessJson(){
        var json=$('#responseBody1').val();
        $('textarea[name=successDataJson]').val(json);
    }

    //复制数据到失败返回示例中
    function copyFailuerJson(){
        var json=$('#responseBody1').val();
        $('textarea[name=failuerDataJson]').val(json);
    }
    
 	//记录已生成的参数，避免重复生成
    var bodyRow={};
    var successRow={};
    var failueRow={};

    function clearRowData(){
    	bodyRow={};
    	successRow={};
    	failueRow={};
    }
    
 	//生参数列表，回填历史数据 arr历史数据
    function addOrUpdateDataRow(id,arr,key,type){
		var name='';
		var remark='';
		var require=1;
		//查找匹配参数，回显说明信息
    	for(var i in arr){ 
    		name=arr[i].name;           			
			if(name==key){
				remark=arr[i].remark;
				type=arr[i].type;
				require=arr[i].require;
				break;
			}
		}
	
		if(id=='failuer_data_list' && !failueRow[key]){
			addFailuerDataRow(key,type,remark);
			failueRow[key]=true;
		}else if(id=='success_data_list' && !successRow[key]){
			addSuccessDataRow(key,type,remark);
			successRow[key]=true;
	 	}else if(id=='body_data_list' && !bodyRow[key]){
			addBodyDataRow(key,type,require,remark);
			bodyRow[key]=true;	
	    }
 	}

    //    递归解析对象参数,obj当前对象数据，arr历史数据
    function loadObjectPara(obj,parentKey,id,arr){
    	if(obj.constructor==Array){
    		addOrUpdateDataRow(id,arr,parentKey,"array")
			for (var i = 0, l = obj.length; i < l; i++) {
				for(var key in obj[i]){
					addOrUpdateDataRow(id,arr,parentKey+'.'+key,typeof(obj[key]))
					if(obj[i][key] != null && typeof(obj[i][key]) == 'object'){
						loadObjectPara(obj[i][key],parentKey+'.'+key,id,arr);
					}
				}
			}
		}else if(obj.constructor==Object){
			addOrUpdateDataRow(id,arr,parentKey,"object")
			for (var key in obj) {
				addOrUpdateDataRow(id,arr,parentKey+'.'+key,typeof(obj[key]))
				if(obj[key] != null && typeof(obj[key])=='object'){
					loadObjectPara(obj[key],parentKey+'.'+key,id,arr);
				}
			}
		}
    }

    //一键清空
    function removeTrAll(tableBodyId){
        layer.confirm("确认要清空所有返回参数说明吗" ,{icon: 3, title:'提示'},function(index){
            $("#"+tableBodyId).children('tr').remove();
            layer.close(index);
        });
    }

    
    //获取表格行json数据
    function getTableJson(id){
        var arr=[];        
    	$("#"+id).find("tr").each(function(){
	        var tdArr = $(this).children();
	        var rowJson={};
	        for(var i=0;i<tdArr.length;i++){
		        var disabled = tdArr.eq(i).find("input:checkbox").prop('checked'); //  是否有效
	        	var name=tdArr.eq(i).find("input").attr('name');
	        	if(name==undefined)
	        		name=tdArr.eq(i).find("select").attr('name');
	        	
        		var value;
        		
		        if(disabled==true||disabled==false){
		        	value=disabled
			    }else{
		        	value=tdArr.eq(i).find("input").val();
		        	if(value==undefined)
		        		value=tdArr.eq(i).find('select').val();
				}
		        //参数名为空，则不保存该行数据
		        if(name=='name'&&value==''){
		        	rowJson={};
		        	break
		        }
        		if(name)
	        		rowJson[name]=value;
		    }
	        
	        if("{}" != JSON.stringify(rowJson))
	        	arr.push(rowJson)			
	    });
	    return JSON.stringify(arr);

    }