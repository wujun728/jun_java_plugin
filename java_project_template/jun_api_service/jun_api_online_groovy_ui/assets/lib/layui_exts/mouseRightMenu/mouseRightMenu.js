layui.define(['jquery','layer'],function(exports){ 
 	var $ = layui.jquery;
	var layer = layui.layer;
	var xx,yy;
	
	function open(data,obj,callback){
		if(obj==false){
			obj={};
		}
		layer.open({
			title: false,
			area: obj.area || '150px',//宽高
			resize: false,
			offset: obj.offset || calc(xx,yy),//坐标，默认鼠标当前
			type: 1,
			anim: -1,
			skin:  obj.skin || 'layer-skin-mouse-right-menu', //样式类名
			closeBtn: 0, //不显示关闭按钮
			shade: obj.shade || ["0.0", '#000'],//遮罩
			shadeClose: obj.shadeClose || true, //开启遮罩关闭
			maxHeight: obj.maxHeight || 240,//自定义高度
			content: obj.content || build_menu_data(data),//支持自定义内容
			success: function(dom, index) {
				$(".mouse-right-menu .enian_menu .text").click(function() {
					if(callback){
						var returnData = $(this).data();
						if($(this).children('a').data('type')==1){
							returnData.data = JSON.parse($(this).children('a').html())
						}else{
							returnData.data = $(this).children('a').html();
						}
						
						if(callback(returnData)==false){
							return;
						}
					}
					layer.close(index)
				})
			}
		});
		
		function calc(x,y){
			if(x>$(window).width() -100){
				x = x-110;
			}
			if(y>$(window).height() -150){
				y = y-150;
			}
			return [y+'px',x+'px'];
		}
	}
	$('body').mousemove(function(e) {     
		xx = e.originalEvent.x || e.originalEvent.layerX || 0;     
		yy = e.originalEvent.y || e.originalEvent.layerY || 0;          
	})
	//生成菜单数据
	function build_menu_data(data){
		var h_son = ''
		for (var i = 0; i < data.length; i++) {
			var dataType = 0;//字符串
			if(typeof(data[i].data)=="object"){
				dataType=1;//对象
				data[i].data = JSON.stringify(data[i].data);
			}
			h_son += '<div class="enian_menu">'
			+'<div class="text" data-type="'+data[i].type+'" data-title="'+data[i].title+'">'
				+'<a style="display:none;" data-type="'+dataType+'">'+data[i].data+'</a>'
				+'<i class="layui-icon '+data[i].icon+'" style="font-size: 16px;padding-right:5px;"></i>'
				+data[i].title
			+'</div></div>'
		}
		return '<div class="mouse-right-menu">'+h_son+'</div>';
	}
	
  var mouseRightMenu = {
    open: open,
   	v:function(){
   		return '0.1.2019-1-2 19:19:29';
   	}
  };
  exports('mouseRightMenu', mouseRightMenu);
});    
