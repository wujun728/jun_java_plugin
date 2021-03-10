layui.define([ 'form', 'laydate', 'table','codeApi'], function(exports) {
	var form = layui.form;
	var codeApi = layui.codeApi;
	var index = layui.index;
	var view = {
			init:function(){
				Lib.initGenrealForm($("#updateForm"),form);
				this.initSubmit();
			},
			initSubmit:function(){
				var that = this;
				toolbar = {
						html:function() { //获取选中数据
							codeApi.previewHtml(function(data){
								that.openCode(data)
							});
						},
						js:function() { //获取选中数目
							codeApi.previewJs(function(data){
								that.openCode(data)
							});
							
						},
						java:function() { //获取选中数目
							codeApi.previewJava(function(data){
								that.openCode(data)
							});
							
						},
						sql:function() { //获取选中数目
							codeApi.previewSql(function(data){
								that.openCode(data)
							});
							
						},
						gen:function(){
							codeApi.gen(function(){
								Common.info("代码生成成功，请刷新工程");
								
							});
						},
						cancel:function(){
							Lib.closeFrame();
						}
						
					};
				$('.ext-toolbar').on('click', function() {
					var type = $(this).data('type');
					toolbar[type] ? toolbar[type].call(this) : '';
				});
				
				$(".addVerify").click(function(){
						var _this = $(this),verifyGroup = _this.parent('.verifyGroup');
		            	layer.open({
		            		  title: '添加校验类型',
		            		  content: '<div class="layui-form">'+
		            		  						'<div class="layui-form-item">'+
												            	'<input type="checkbox" class="verify" title="必填" value="required"/>'+
											            		'<input type="checkbox" class="verify" title="邮箱"value="email" />'+
											            		'<input type="checkbox" class="verify" title="手机" value="phone"/>'+
				            						'</div>'+
				            						'<div class="layui-form-item">'+
									            		'<input type="checkbox" class="verify" title="数字"value="number" />'+
									            		'<input type="checkbox" class="verify" title="日期" value="date"/>'+
                                  						'<input type="checkbox" class="verify" title="日期范围" value="dateRange"/>'+
                                  						'<input type="checkbox" class="verify" title="日期时间" value="datetime"/>'+
                                  						'<input type="checkbox" class="verify" title="日期时间范围" value="datetimeRange"/>'+
									            		'<input type="checkbox" class="verify" title="网址" value="url"/>'+
									            	'</div>'+
									            	'<div class="layui-form-item">'+
									            		'<input type="checkbox" class="verify" title="身份证"value="identity" />'+
									            	'</div>'+
		            						'</div>',
	            			  success: function(layero, index){
	            				   
	            				  //初始化赋值
	            				   var inputs = verifyGroup.find('input');
	            				   var verifys = $('.verify');
	            				   for (var i = 0; i < inputs.length; i++) {
	            				    	for (var j = 0;j< verifys.length;j++) {
											if(inputs[i].value == verifys[j].value){
												$(verifys[j]).attr("checked",true);
											}
										}
									}
	            				   form.render();
	            			  },
	            			  yes: function(index, layero){
	            				    var verifys = $('.verify:checked');
	            				    
	            				    //清空并重新赋值
	            				    verifyGroup.find('.verifybtn').remove();
	            				    for (var i = 0; i < verifys.length; i++) {
	            				    	
	            				    	var name = 'entity.list[' + verifyGroup.data('index')+'].verifyList'+'['+i+'].name';
	            				    	var btn =  '<button class="layui-btn layui-btn-xs verifybtn" type="button" >'+$(verifys[i]).attr('title')+
	            				    							'<input type="text" name="'+name +'"  value="'+$(verifys[i]).val()+'" hidden>'+
	            				    					 '</button>'
	            				    	verifyGroup.append(btn);
									}
	            				    
	            				    layer.close(index);
	            			  }
		            	});
		        });
				
			},
			openCode:function(data){
				var tab=[];
				for(var key in data){
					var title = key;
					var content = this.formatCode(key,data[key]);
					tab.push({"title":title,"content":content}); 
				}
				var index = layer.tab({
					  area: ['600px', '500px'],
					  tab:tab,
					  success: function(layero, index){
						  layui.use('code', function(){ //加载code模块
							  layui.code({
								  about: false
							  }); //引用code方法
						});
					  }
				});
				layer.full(index);
				
			},
			formatCode:function( name,content){
				if(name.indexOf(".html")){
					content =  $('<div/>').text(content).html(); 
				}
				
				content = "<pre class='layui-code'>"+content+"<pre>";
				return content;
			}
				
	}
	
	
	
	 exports('edit',view);
	
});