layui.define(['element','laypage', 'layer', 'form', 'pagesize'], function (exports) {
	var element = layui.element();
    var $ = layui.jquery,
        layer = layui.layer,
        form = layui.form(),
        laypage = layui.laypage;
    var laypageId = 'pageNav';
    initilData(1, 8);
    /**
     * 初始化数据
     * currentIndex：当前也下标
     * pageSize：页容量（每页显示的条数）
     */
    function initilData(currentIndex, pageSize) {
        var index = layer.load(1);
        $.ajax({
     	    url:'listOrder',
          	async:false,
          	type:'post',
          	dateType:'json',
          	data:{'time':(new Date()).toString(),"pageNo":currentIndex,"pageSize":pageSize},
    		success:function(result){
    			var obj = JSON.parse(result);
    			var count = obj.count;
    			var data  = obj.list;
    			layer.close(index);
	            //计算总页数（一般由后台返回）
	            pages = Math.ceil(count / pageSize);
	            var html = '';
	            //遍历账单集合
	            for (var i = 0; i < data.length; i++) {
	                var item = data[i];
	                html += "<tr>";
	                html += "<td>" + item.orderNo + "</td>";
	                html += "<td>" + item.schoolName +"</td>";
	                html += "<td>" + item.studentName + "</td>";
	                html += "<td>" + item.totlePrice + "</td>";
	                html += "<td>" + item.createTime + "</td>";
	                html += "<td>" + item.paymentTime + "</td>";
	                html += "</tr>";
	            }
	            $('#dataContent').html(html);
	            $('#dataConsole,#dataList').attr('style', 'display:block'); //显示FiledBox
	            laypage({
	                cont: laypageId,
	                pages: pages,
	                groups: 8,
	                skip: true,
	                curr: currentIndex,
	                jump: function (obj, first) {
	                    var currentIndex = obj.curr;
	                    if (!first) {
	                        initilData(currentIndex, pageSize);
	                    }
	                }
	            });
	            /**
	             * 设置每页数量
	             */
	            layui.pagesize(laypageId, pageSize).callback(function (newPageSize) {
	                initilData(1, newPageSize);
	            });
    		}
          });
    }
    exports('datalist', {});
});