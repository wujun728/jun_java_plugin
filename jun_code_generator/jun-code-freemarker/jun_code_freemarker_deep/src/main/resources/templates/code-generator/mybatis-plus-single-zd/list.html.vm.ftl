<!DOCTYPE html>
<html lang="en" xmlns:shiro="http://www.pollix.at/thymeleaf/shiro"
      xmlns:th="http://www.thymeleaf.org">
<head>
  <meta charset="UTF-8">
  <title>Title</title>
  <link rel="stylesheet" href="/layui/css/layui.css">
  <link rel="stylesheet" href="/css/custom.form.css">
  <link rel="stylesheet" href="/css/public.css" media="all">
  <style>
		element.style { width: 180px; }
		.layui-form-label.layui-required:after{
	        content: '*';
		    color: red;
		    position: absolute;
		    margin-left: 4px;
		    font-weight: bold;
		    line-height: 1.8em;
		    top: 6px;
		    right: 5px;
	    }
  </style>
</head>
<body>
<!-- 新增修改的DIV页面-begin,默认hidden -->
<div class="panel panel-default operation" hidden>
<!--   <div class="panel-heading title"></div> -->
<div class="layui-card-body">
#*
<fieldset class="layui-elem-field layui-field-title" style="margin-top: 20px;">
    <legend>基本信息</legend>
</fieldset>
*#
<form class="layui-form " action="" lay-filter="info" style="#*width: 700px;*#margin-top: 10px">
###遍历新增修改表单
#foreach($column in $columns)
  #if($column.columnName == $pk.columnName)
    <input name="${column.attrname}" id="${column.attrname}" hidden/>
  #else
##跳过循环，新增及修改时间字段，注解支持，不展示
#if($column.columnName.toString().contains("creat")  || $column.columnName.toString().contains("deleted")  || $column.columnName.toString().contains("update")) 
	#break
#end

<div class="layui-form-item" #if($column.columnName == $pk.columnName) ,hide:true #end  #if($column.comments=='') style="display:none" #end>

#if($column.dataType != 'text') <div class="layui-inline"> #end
      <label class="layui-form-label #if($column.isNull == 'NO') layui-required #end " >${column.comments}</label>
      <div class="#if($column.dataType == 'text') layui-input-block #else layui-input-inline #end"> 
##注解支持，不展示
#if($column.dataType == 'datetime')
	<input type="${column.attrname}" name="${column.attrname}"  id="${column.attrname}" lay-verify="#if($column.isNull == 'NO')required|#end date" #if($column.isNull == 'NO') placeholder="yyyy-MM-dd" #end autocomplete="off" class="layui-input">
#elseif($column.columnName.toString().contains("dict"))
	<select id="${column.attrname}" name="${column.attrname}" lay-filter="${column.attrname}"  th:with="type=${@sysDictService.getType('${column.columnName}')}"  >
        <option value="">请选择</option>
        <option th:each="dict : ${type}" th:text="${dict.label}" th:value="${dict.value}"></option> 
    </select>
#elseif($column.dataType == 'text')
	    <textarea  #if($column.isNull == 'NO') placeholder="请输入内容" #end class="layui-textarea" name="${column.attrname}"  id="${column.attrname}"></textarea>
#else
	#if($column.isNull == 'NO') 
	<input type="${column.attrname}" name="${column.attrname}"  id="${column.attrname}"  lay-verify="required|${column.attrname}" lay-max="${column.maxLength}" #if($column.isNull == 'NO') placeholder="请输入${column.comments}" #end  autocomplete="off" class="layui-input">
	#else
	<input type="text" name="${column.attrname}"  id="${column.attrname}"  #if($column.isNull == 'NO') placeholder="请输入${column.comments}" #end autocomplete="off" class="layui-input">
	#end
#end
    </div>
  #if($column.dataType != 'text') </div> #end
 
 </div>
#end
#end
<div class="layui-form-item" id="buttonSave">
  <div class="layui-input-block">
    <button type="submit" class="layui-btn" lay-submit="" lay-filter="submit">保存</button>
    <button  class="layui-btn layui-btn-primary" id="btn_cancel">返回</button>
  </div>
</div>
</form>
</div>
</div>
<!-- 新增修改的DIV页面-end -->

<!-- 查询列表的DIV页面-start -->
<div class="layuimini-container">
<div class="layuimini-main">
<div class="table_div">
   <blockquote class="layui-elem-quote layui-text">
      在此你可以对<span class="label-info"><strong>${comments}</strong></span>进行编辑!若有操作及使用问题及常见“问题”：请查看<a href="#">操作手册</a>！
   </blockquote>
  <div id="searchParam"  shiro:hasPermission="${classname}:list">
    <div class="layui-form-item">
###遍历查询条件       
#foreach($column in $columns)
#if($column.columnName != $pk.columnName && $column.isNull == 'NO') 
##跳过循环，新增及修改时间字段，注解支持，不展示
#if($column.columnName.toString().contains("create")  || $column.columnName.toString().contains("deleted")   || $column.columnName.toString().contains("update")) 
	#break
#end
#if($column.dataType == 'datetime')
      <div class="layui-input-inline">
	<input type="${column.attrname}" name="${column.attrname}Condition"  id="${column.attrname}Condition" placeholder="yyyy-MM-dd" autocomplete="off" class="layui-input">
      </div>
#else
      <div class="layui-input-inline">
    <input type="${column.attrname}" name="${column.attrname}Condition"  id="${column.attrname}Condition" class="layui-input"  autocomplete="off" placeholder="请输入${column.comments}">
      </div>
#end
#end
#end
      <div class="layui-input-inline ">
        <button class="layui-btn" onclick="search()"  id="search">查询</button>
        <button class="layui-btn"   id="export">导出全部</button>
      </div>
    </div>
  </div>
  <!-- 渲染列表，上面的是工具栏 -->
  <table class="layui-table" id="showTable" lay-filter="showTable" ></table>
</div>
</div>
</div>
<!-- 查询列表的DIV页面-end -->
<script type="text/html" id="toolbar">
  <div class="layui-btn-container">
    <button class="layui-btn layui-btn-sm" lay-event="add"  shiro:hasPermission="${classname}:add">添加</button>
    <button class="layui-btn layui-btn-sm  layui-btn-danger " lay-event="batchDeleted" shiro:hasPermission="${classname}:delete">删除</button>
    <!-- 
    <button class="layui-btn layui-btn-sm" lay-event="submit" shiro:hasPermission="${classname}:delete">提交</button>
     -->
  </div>
</script>
<script type="text/html" id="tool">
  {{#  if(d.orderState == 0){ }}
  <a class="layui-btn layui-btn-xs" lay-event="view" shiro:hasPermission="bizCustomerTest:update">查看</a>
  {{#  }else if(d.orderState == 1 &&  d.isOwner != 1){ }}
  <a class="layui-btn layui-btn-xs" lay-event="view" shiro:hasPermission="bizCustomerTest:update">查看</a>
  {{#  }else if(d.orderState == 1 &&  d.isOwner == 1){ }}
  <a class="layui-btn layui-btn-xs" lay-event="edit" shiro:hasPermission="pjProject:update">编辑</a>
  <a class="layui-btn layui-btn-xs" lay-event="file" shiro:hasPermission="bizCustomerTest:update">附件</a>
  {{#  }else { }}
  <a class="layui-btn layui-btn-xs" lay-event="edit" shiro:hasPermission="pjProject:update">编辑</a>
  <a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="del" shiro:hasPermission="pjProject:delete">删除</a>
  <a class="layui-btn layui-btn-xs" lay-event="file" shiro:hasPermission="bizCustomerTest:update">附件</a>
  {{#  } }}
</script>

</body>
</html>
<script src="/layui/layui.all.js"></script>
<script src="/layui-ext/tableSelect.js"></script>
<script src="/lib/lay-module/xmSelect/xm-select.js"></script>
<script src="/js/core.util.js"></script>
<script src="/js/jquery.js"></script>
<!-- 流程flow-mark001,新增流程查看表单详情功能 -->
<script th:inline="javascript">
    var pkCode = [[${pkCode}]];
    var flagType = [[${flagType}]];
    var id = [[${id}]];
//     console.log(flagType);
//     console.log(id);
    if("view"==flagType){
	     $(".table_div").hide();
	     $(".operation").show();
	     $("#buttonSave").hide();
	     var layer = layui.layer;
	     var $ = jQuery = layui.jquery;
	     var form = layui.form;
	     var element = layui.element;
	     $(function () {
	         CoreUtil.sendPost("/${classname}/findOne", {id : id}, function (res) {
	             if (res.data != null) {
	            	 form.val("info", res.data );
	                 form.render(); //更新全部
	             }
	         });
	     });
    }
</script>
<script>
  //获取token
  var token = CoreUtil.getData("access_token");
  //地址栏转义token中的#号
  var tokenQuery = token==null?"":token.replace("#", "%23");
  var tableIns1;
  var table = layui.table;
  var form = layui.form;
  var layer = layui.layer;
  var layedit = layui.layedit;
  var $ = jQuery = layui.jquery;
  var laydate = layui.laydate;
  var numberInput = layui.numberInput;
  var tableSelect = layui.tableSelect;
  var layerAdd;
  //定义lay全局对象

  layui.config({
        base: '/layui-ext/'
    }).extend({
        treetable: 'treetable-lay/treetable',
        iconPicker: 'icon/iconPicker',
        numberInput: 'numberInput/js/index'
    }).use(['numberInput','table', 'layer', 'laydate' ,'form', 'layedit'], function () {
	numberInput = layui.numberInput;
	
    //加载table,数据表格
    tableIns1 = table.render({
      elem: '#showTable'
      , contentType: 'application/json'
      , headers: {"authorization": token}
      , page: true //开启分页
      , url: '/${classname}/listByPage' //数据接口
      , method: 'POST'
      ,size: 'sm' //小尺寸的表格
      , parseData: function (res) { //将原始数据解析成 table 组件所规定的数据
	        return {
	          "code": res.code, //解析接口状态
	          "msg": res.msg, //解析提示文本
	          "count": CoreUtil.isEmpty(res.data) ? 0 : res.data.total, //解析数据长度
	          "data": CoreUtil.isEmpty(res.data) ? null : res.data.records //解析数据列表
	        }
      }
      , cols: [
        [
          {type: 'checkbox', fixed: 'left'}, //{type:'radio'}  
#foreach($column in $columns)
##跳过循环，新增及修改时间字段，注解支持，不展示
#if($column.columnName.toString().contains("create")  || $column.columnName.toString().contains("deleted")  || $column.columnName.toString().contains("update")) 
	#break
#end
          {field: '${column.attrname}', title: '${column.comments}', sort: true ##
#if($column.columnName == $pk.columnName || $column.isNull != 'NO') 
,hide: true ##
#end
#if($column.columnName.toString().contains("dict_"))
, templet: function (item) { ##
	            //获取类型对应的字典label
	            var datas = "[[${@sysDictService.getType('${column.columnName}')}]]".replace(/&quot;/g,"\"");
	            if(item.${column.attrname}%2==0){
		            return '<span class="layui-btn layui-btn-normal layui-btn-xs">'+CoreUtil.selectDictLabel(datas, item.${column.attrname})+'</span>';
          		}else{
		            return '<span class="layui-btn layui-btn-warm layui-btn-xs">'+CoreUtil.selectDictLabel(datas, item.${column.attrname})+'</span>';
          		}
	            // return CoreUtil.selectDictLabel(datas, item.${column.attrname});
            }
#end 
		  }, 
#end
			//   流程新增字段   beggin    flow_test1  <!-- 流程flow-mark001,新增流程列表字段，查询流程状态及处理人信息 -->
          { title: '流程状态', field: 'orderState', align: 'center',width:90, templet: function (d) {
                  if (d.orderState == 0) {
                      return '<span style="color: #eb7350;">审批完成</span>'
                  }else if (d.orderState == 1) {
	                  return '<span style="color: #00b487;">审批中</span>'
                  }else{
	                  return '<span style="color: blue;">草稿</span>'
                  }
              }
          },
          { title: '当前节点', field: 'taskName', align: 'center' ,width:105},
          { title: '当前处理人', field: 'taskOperatorName', align: 'center' ,width:90},{field: 'isOwner', title: 'isOwner',hide:true },
          //   流程新增字段   end  <!-- 流程flow-mark001,新增流程列表字段，查询流程状态及处理人信息 -->
         {field: 'isOwner',hide: true  }, {width: 180, toolbar: "#tool", title: '操作', fixed: 'right'}
        ]
      ]
      , toolbar: '#toolbar'
    });


    //表头工具
    table.on('toolbar(showTable)', function(obj){
    	var checkStatus11 = table.checkStatus(obj.config.id);
        var data11 = checkStatus11.data;
        if(data11.length==0){
          	layer.msg("请选择要操作的数据列！");
        }else {
	          var ids11 = [];
	          $(data11).each(function (index,item) {
	        	  if(item.orderState==0 || item.orderState==1){
	        		  ids11.push(item.orderState);
	        	  }
	          });
	          if(ids11.length>0){
	        	  layer.msg("请选择未发起流程中的数据(禁止操作流程中的数据)！");
        		  return;
	          }
        }
      switch(obj.event){
        case 'batchDeleted':
          var checkStatus = table.checkStatus(obj.config.id);
          var data = checkStatus.data;
          if(data.length==0){
            layer.msg("请选择要批量删除的列");
          }else {
            var ids = [];
            $(data).each(function (index,item) {
              ids.push(item.id);
            });
            tipDialog(ids);
          }
          break;
        //<!-- 流程flow-mark001,新增流程提交方案的submit的function -->
        case 'submit':
          var checkStatus = table.checkStatus(obj.config.id);
          var data = checkStatus.data;
          if(data.length==0){
            layer.msg("请选择要提交的记录");
          }else if(data.length > 1){
            layer.msg("请选择要提交的记录（one row each times）");
          }else {
//         	  window.checkPromission(data[0].createId);
        	  data[0]['processName'] = "submit";
        	  //<!-- 流程flow-mark001,新增流程,指定流程的类型 -->
        	  CoreUtil.setData("data_doSubmit",data[0]);
        	  console.log("data="+data);
              layer.open({
                  type: 2,
                  skin: 'layui-layer-admin',
                  title: "提交流程",
                  shadeClose : false,
                  area: ['880px', '500px'], //宽高
                  shade: 0.6, //遮罩透明度
                  maxmin: true, //允许全屏最小化
                  anim: 1, //0-6的动画形式，-1不开启
                  content:  '/table/doSubmit.html',
                  end: function(){
                	  search();
                  }
              });
          }
          break;
        case 'add':
//           $(".table_div").hide();
//           $(".operation").show();
//           $(".title").html("新增");
#foreach($column in $columns)
##跳过循环，新增及修改时间字段，注解支持，不展示
#if($column.columnName.toString().contains("creat")  || $column.columnName.toString().contains("deleted")   || $column.columnName.toString().contains("update")) 
	#break
#end 
#if($column.columnName.toString().contains("dict_"))
          $(".operation select[name=${column.attrname}]").val("");
#elseif(($column.dataType == 'text'))
          $(".operation textarea[name=${column.attrname}]").val("");
#else
          $(".operation input[name=${column.attrname}]").val("");
#end
#end
		  layerAdd = layer.open({
              type: 1,
              skin: 'layui-layer-admin',
              area: ['80%', '80%'],
              shift: 1,
              shadeClose: true,
              scrollbar: true,
              maxmin: true,
              shade: false,
              title:  ['新增${comments}' , false],
              content: $(".operation"),
          });
          break;
      };
    });
    //列操作
    table.on('tool(showTable)',function (obj) {
      var data = obj.data;
      switch (obj.event) {
        case 'del':
          var ids=[];
          ids.push(data.id);
          tipDialog(ids);
          break;
        case 'edit':
//           $(".table_div").hide();
//           $(".operation").show();
//           $(".title").html("编辑");
#foreach($column in $columns)
##跳过循环，新增及修改时间字段，注解支持，不展示
#if($column.columnName.toString().contains("creat")  || $column.columnName.toString().contains("deleted") || $column.columnName.toString().contains("update")) 
	#break
#end
#if($column.columnName.toString().contains("dict_"))
          $(".operation select[name=${column.attrname}]").val(data.${column.attrname});
#elseif(($column.dataType == 'text'))
          $(".operation textarea[name=${column.attrname}]").val(data.${column.attrname});
#else
          $(".operation input[name=${column.attrname}]").val(data.${column.attrname});
#end
#end
		  form.render();
		  layerAdd = layer.open({
              type: 1,
              area: ['80%', '80%'],
              shift: 2,
              skin: 'layui-layer-admin',
              shadeClose: true,
              scrollbar: true,
              maxmin: true,
              shade: false,
              title:  ['修改${comments}' , false],
              content: $(".operation"),
          });
          break;
        case 'file':
          data['dictBiztype']='${comments}';
          CoreUtil.setData("biz_rowdata",data);
          var index = layer.open({
              title: '上传附件',
              type: 2,
              skin: 'layui-layer-admin',
              shade: 0.2,
              maxmin:true,
              shadeClose: true,
              scrollbar: true,
              area: ['80%', '80%'],
              content: '/index/sysFilesByUser',
          });
          $(window).on("resize", function () {
              layer.full(index);
          });
          return false;
      }
    });
    
    
//遍历列，并渲染各种表单组件
#foreach($column in $columns) 
#if($column.columnName != $pk.columnName)
##跳过循环，新增及修改时间字段，注解支持，不展示
#if($column.columnName.toString().contains("create")  || $column.columnName.toString().contains("deleted")   || $column.columnName.toString().contains("update")) 
	#break
#end
#if($column.dataType == 'datetime')
 	//日期+时间
	laydate.render({
	    elem: '#${column.attrname}'
	    ,type: 'datetime'
    });
##elseif($column.columnName.toString().contains("dict"))

#elseif($column.dataType == 'date' )
	 	//日期
	laydate.render({
	    elem: '#${column.attrname}'
	    ,type: 'date'
    });
#elseif($column.dataType == 'decimal' )
	numberInput.render("#${column.attrname}", {
	    autoSelcet: false,
	    max: 10000000000,
	    min: 0,
		step: 1,
	    precision: 2
	});
#elseif($column.dataType == 'int')
	numberInput.render("#${column.attrname}", {
	    autoSelcet: false,
	    max: 10000000,
	    min: 0,
		step: 1,
	    precision: 0
	});
#end
#if($column.columnName.toString().contains("ref_"))
	tableSelect.render({
		elem: '#${column.attrname}',
		checkedKey: 'id',
		table: {
			url: '/${classname}/listBySelect?authorization='+tokenQuery,
			method: 'POST',
			contentType: 'application/json',
			 parseData: function (res) { //将原始数据解析成 table 组件所规定的数据
		        return {
		          "code": res.code, //解析接口状态
		          "msg": res.msg, //解析提示文本
		          "count": CoreUtil.isEmpty(res.data) ? 0 : res.data.total, //解析数据长度
		          "data": CoreUtil.isEmpty(res.data) ? null : res.data.records //解析数据列表
		        }
		      },
			cols: [ [
					{ type: 'radio' },
					{ field: 'id', title: 'ID' ,hide:true},
					{ field: 'XXXcusname', title: '标题' },
					{ field: 'XXXfullname', title: '名称' }
				] ]
		},
		done: function (elem, data) {
			var NEWJSON = []
			layui.each(data.data, function (index, item) {
				NEWJSON.push(item.XXXcusname);
				$("#refId").val(item.XXXid);
				console.log('info');  
			})
			elem.val(NEWJSON.join(","))
		}
	});
#end
#end
#end

    //导出
    $('#export').on('click', function () {
      //原先分页limit
      var exportParams = {
        limit: 10000,
        key: $("#key").val()
      };
      CoreUtil.sendPost("/${classname}/listByPage", exportParams, function (res) {
        //初始化渲染数据
        if (res.data != null && res.data.records != null) {
          table.exportFile(tableIns1.config.id, res.data.records, 'xls');
        }
      });
    });

    //删除
    var tipDialog=function (ids) {
      layer.open({
          skin: 'layui-layer-admin',
        content: "确定要删除么?",
        yes: function(index, layero){
          layer.close(index); //如果设定了yes回调，需进行手工关闭
          CoreUtil.sendDelete("/${classname}/delete",ids,function (res) {
            layer.msg(res.msg, {time:1000},function () {
              search();
            });
          });
        }
      });
    };

    //返回
    $("#btn_cancel").click(function() {
    	layer.close(layerAdd);
//       $(".table_div").show();
//       $(".operation").hide();
      return false;
    });

    //监听保存
    form.on('submit(submit)', function(data){
      
      if(data.field.id===undefined || data.field.id===null || data.field.id===""){
        CoreUtil.sendPost("/${classname}/add",data.field,function (res) {
          if(res.code == "0"){
	          //$(".table_div").show();
	          //$(".operation").hide();
	          layer.close(layerAdd);
	          search();
        	}else{
        		layer.msg(res.msg);
        	}
        });
      }else {
        CoreUtil.sendPut("/${classname}/update",data.field,function (res) {
          //$(".table_div").show();
          //$(".operation").hide();
          layer.close(layerAdd);
          search();
        });
      }
      return false;
    });
  });

  //执行查询
  function search() {
    //这里以搜索为例
    tableIns1.reload({
      where: { //设定异步数据接口的额外参数，任意设
        '1':'1'
        //,key: $("#key").val()
###遍历查询条件       
#foreach($column in $columns)
#if($column.columnName != $pk.columnName && $column.isNull == 'NO') 
		,${column.attrname}: $("#${column.attrname}Condition").val()
#end
#end
      }
      ,page: {
        curr: 1 //重新从第 1 页开始
      }
    });
  };
  
  //根据数据库字段长度及是否必填属性生成校验表单字段长度
  //lay-verify="required|account" lay-min="6" 
  // https://blog.csdn.net/m0_48373030/article/details/108783184
    form.verify({
#foreach($column in $columns) 
#if($column.columnName != $pk.columnName)
##跳过循环，新增及修改时间字段，注解支持，不展示
#if($column.columnName.toString().contains("creat")  || $column.columnName.toString().contains("deleted") || $column.columnName.toString().contains("update")) 
	#break
#end
${column.attrname}: function(value, item){
            var max = item.getAttribute('lay-max');
            if(value.length > max){
                return '${column.comments}不能大于'+max+'个字符的长度！';
            }
        } #if($columns.size() != ${velocityCount}) ,  #end
#end
#end
    });
</script>