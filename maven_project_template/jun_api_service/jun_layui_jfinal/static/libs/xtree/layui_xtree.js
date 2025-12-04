/**
 * layui xtree 带选择框树
 * @Autor QinHaiLin
 * @date 2018-01-24
 * 
 * layui.use('form',function(){
		var form = layui.form;

        //创建tree
		var node=[
			{
	         title:'',
	         value:'',
	         data:[{
	          	title:'',
	         	value:'',
	         	data:[],
	         	}],
			},
			{
			 title:'',
	         value:'',
	         data:[],
			}
		
		];

        var xtree = new layuiXtree({
            elem: 'layui-xtree-demo1' //放xtree的容器（必填）

             , form: form              //layui form对象 （必填）

             , data: node              //数据，结构请参照下面 （必填）

             , isopen: true            //初次加载时全部展开，默认true （选填）

             , color: "#000"           //图标颜色 （选填）

             , icon: {                 //图标样式 （选填）

                 open: "&#xe625;"      //节点打开的图标

                 , close: "&#xe623;"   //节点关闭的图标

                 , end: "&#xe621;"     //末尾节点的图标

             }
        });

        document.getElementById('btn_getCk').onclick = function () {

            var oCks = xtree.GetChecked(); //获取全部选中的末级节点checkbox对象


            for (var i = 0; i < oCks.length; i++) {
                console.log(oCks[i].value);
            }
        } 
	})
 */

//构造
function layuiXtree(options) {
 this._container = document.getElementById(options.elem); //容器

 this._dataJson = options.data;  //数据

 this._form = options.form; //layui from对象

 this._domStr = "";  //结构字符串

 this._isopen = options.isopen != null ? options.isopen : true;
 this._color = options.color != null ? options.color : "#2F4056"; //图标颜色

 if (options.icon == null) options.icon = {};
 this._iconOpen = options.icon.open != null ? options.icon.open : "&#xe625;"; //打开图标

 this._iconClose = options.icon.close != null ? options.icon.close : "&#xe623;"; //关闭图标

 this._iconEnd = options.icon.end != null ? options.icon.end : "&#xe65f;"; //末级图标


 this.dataBind(this._dataJson);
 this.Rendering();
}
var totalData=[],sum=0;
//生产结构

layuiXtree.prototype.dataBind = function (d) {
 var _this = this;
 if (d!=null&&d.length > 0) {
     for (i in d) {
         var xtree_isend = '';
         if(d[i].title==undefined)
        	 continue
         _this._domStr += '<div class="layui-xtree-item">';
         
         if(d[i].isopen!=null){
        	 _this._isopen=d[i].isopen;      	 
        }
         totalData[sum]=_this._isopen;sum++;
         if (d[i].data!=null&&d[i].data.length > 0)
             _this._domStr += '<i class="layui-icon layui-xtree-icon" data-xtree="' + (_this._isopen ? '1' : '0') + '">' + (_this._isopen ? _this._iconOpen : _this._iconClose) + '</i>';
         else {
             _this._domStr += '<i class="layui-icon layui-xtree-icon-null">' + _this._iconEnd + '</i>';
             xtree_isend = 'data-xend="1"';
         }
         _this._domStr += '<input type="checkbox" class="layui-xtree-checkbox" ' + xtree_isend + ' value="' + d[i].value + '" title="' + d[i].title + '" lay-skin="primary" lay-filter="xtreeck">';
         _this.dataBind(d[i].data);
         _this._domStr += '</div>';
     }
 }
}

//渲染呈现

layuiXtree.prototype.Rendering = function () {
 var _this = this;
 _this._container.innerHTML = _this._domStr;
 _this._form.render('checkbox');

 var xtree_items = document.getElementsByClassName('layui-xtree-item');
 var xtree_icons = document.getElementsByClassName('layui-xtree-icon');
 var xtree_nullicons = document.getElementsByClassName('layui-xtree-icon-null');

 for (var i = 0; i < xtree_items.length; i++) {
	 
     if (xtree_items[i].parentNode == _this._container)
         xtree_items[i].style.margin = '5px 0 0 10px';
     else {
         xtree_items[i].style.margin = '5px 0 0 45px';
         if (!totalData[i]) xtree_items[i].style.display = 'none';
     }
 }
 for (var i = 0; i < xtree_icons.length; i++) {
     xtree_icons[i].style.position = "relative";
     xtree_icons[i].style.top = "3px";
     xtree_icons[i].style.margin = "0 5px 0 0";
     xtree_icons[i].style.fontSize = "18px";
     xtree_icons[i].style.color = _this._color;
     xtree_icons[i].style.cursor = "pointer";
     
     var childNodes=xtree_icons[i].parentNode.childNodes;
     if(xtree_icons[i].getAttribute('data-xtree') == 1){
    	 for (var j = 0; j < childNodes.length; j++) {
             if (childNodes[j].getAttribute('class') == 'layui-xtree-item'){
            	 childNodes[j].style.display = 'block';      	 
             }
         }
     }
     
     xtree_icons[i].onclick = function () {
         var xtree_chi = this.parentNode.childNodes;
         if (this.getAttribute('data-xtree') == 1) {
             for (var j = 0; j < xtree_chi.length; j++) {
                 if (xtree_chi[j].getAttribute('class') == 'layui-xtree-item')
                     xtree_chi[j].style.display = 'none';
             }
             this.setAttribute('data-xtree', '0')
             this.innerHTML = _this._iconClose;
         } else {
             for (var j = 0; j < xtree_chi.length; j++) {
                 if (xtree_chi[j].getAttribute('class') == 'layui-xtree-item')
                     xtree_chi[j].style.display = 'block';
             }
             this.setAttribute('data-xtree', '1')
             this.innerHTML = _this._iconOpen;
         }
     }
 }
 for (var i = 0; i < xtree_nullicons.length; i++) {
     xtree_nullicons[i].style.position = "relative";
     xtree_nullicons[i].style.top = "3px";
     xtree_nullicons[i].style.margin = "0 5px 0 0";
     xtree_nullicons[i].style.fontSize = "18px";
     xtree_nullicons[i].style.color = _this._color;
 }

 _this._form.on('checkbox(xtreeck)', function (da) {
     //获取当前点击复选框的容器下面的所有子级容器

     var xtree_chis = da.elem.parentNode.getElementsByClassName('layui-xtree-item');
     //遍历它们，选中状态与它们的父级一致（类似全选功能）

     for (var i = 0; i < xtree_chis.length; i++) {
    	 var disabled=xtree_chis[i].getElementsByClassName('layui-xtree-checkbox')[0].disabled;
    	 if(!disabled){//只读状态不能选择
    		 xtree_chis[i].getElementsByClassName('layui-xtree-checkbox')[0].checked = da.elem.checked;
    		 if (da.elem.checked) xtree_chis[i].getElementsByClassName('layui-xtree-checkbox')[0].nextSibling.classList.add('layui-form-checked');
    		 else xtree_chis[i].getElementsByClassName('layui-xtree-checkbox')[0].nextSibling.classList.remove('layui-form-checked');  		 
    	 }
     }
     _this.ParendCheck(da.elem);
 });
}

//子阶段选中改变，父节点更改自身状态

layuiXtree.prototype.ParendCheck = function (ckelem) {
 var xtree_p = ckelem.parentNode.parentNode;
 if (xtree_p.getAttribute('class') == 'layui-xtree-item') {
     var xtree_all = xtree_p.getElementsByClassName('layui-xtree-item');
     var xtree_count = 0;

     for (var i = 0; i < xtree_all.length; i++) {
         if (xtree_all[i].getElementsByClassName('layui-xtree-checkbox')[0].checked) {
             xtree_count++;
         }
     }

     if (xtree_count <= 0) {
         xtree_p.getElementsByClassName('layui-xtree-checkbox')[0].checked = false;
         xtree_p.getElementsByClassName('layui-xtree-checkbox')[0].nextSibling.classList.remove('layui-form-checked');
     } else {
         xtree_p.getElementsByClassName('layui-xtree-checkbox')[0].checked = true;
         xtree_p.getElementsByClassName('layui-xtree-checkbox')[0].nextSibling.classList.add('layui-form-checked');
     }
     this.ParendCheck(xtree_p.getElementsByClassName('layui-xtree-checkbox')[0]);
 }
}

//获取全部选中的末级checkbox对象

layuiXtree.prototype.GetChecked = function () {
 var arr = new Array();
 var arrIndex = 0;
 var cks = document.getElementsByClassName('layui-xtree-checkbox');
 for (var i = 0; i < cks.length; i++) {
     if (cks[i].checked /*&& cks[i].getAttribute('data-xend') == '1'*/) {
         arr[arrIndex] = cks[i]; arrIndex++;
     }
 }
 return arr;
}
