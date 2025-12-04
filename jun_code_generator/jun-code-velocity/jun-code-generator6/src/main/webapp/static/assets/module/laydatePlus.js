layui.define(['layer'],function(exports){
	//提示：模块也可以依赖其它模块，如：layui.define('layer', callback);
	let laydate = layui.laydate;	
	const opts={
		"target":'laydateplus',
		"callback": null
	};
	var laydatePlus = function(options){
		this.settings = $.extend({}, opts, options);
		let me = this;
		$('#'+this.settings.target).click(function(){
			me.init();
		});
	};    
	laydatePlus.prototype = {		
		init: function () {
			var me = this;
			
			let shortcuts = [
				{
					text: "昨天",
					value: function(){					  
						var value = [];
						var date1 = new Date();
						date1.setDate(date1.getDate() - 1);
						date1.setHours(0, 0, 0, 0);
						value.push(date1);
						var date2 = new Date();
						date2.setDate(date2.getDate() - 1);
						date2.setHours(23, 59, 59, 999);
						value.push(new Date(date2));
						return value;
					}()
				},
				{
					text: "今天",
					value: function(){					  
						var value = [];
						var date1 = new Date();
						date1.setDate(date1.getDate());
						date1.setHours(0, 0, 0, 0);
						value.push(date1);
						var date2 = new Date();
						date2.setDate(date2.getDate());
						date2.setHours(23, 59, 59, 999);
						value.push(new Date(date2));
						return value;
					}()
				},
				{
					text: "最近7天",
					value: function(){					  
						var value = [];
						var date1 = new Date();
						date1.setDate(date1.getDate() - 7);
						date1.setHours(0, 0, 0, 0);
						value.push(date1);
						var date2 = new Date();
						date2.setHours(23, 59, 59, 999);
						value.push(new Date(date2));
						return value;
					}()
				},
				{
					text: "最近30天",
					value: function(){					  
						var value = [];
						var date1 = new Date();
						date1.setDate(date1.getDate() - 30);
						date1.setHours(0, 0, 0, 0);
						value.push(date1);
						var date2 = new Date();
						date2.setHours(23, 59, 59, 999);
						value.push(new Date(date2));
						return value;
					}()
				},
				{
					text: "本周",
					value: function(){					  
						var value = [];
						var today = new Date();
						var date1 = new Date(today.setDate(today.getDate() - today.getDay() + (today.getDay() === 0 ? -6 : 1)));
						date1.setHours(0, 0, 0, 0);						
						value.push(date1);
						var date2 = new Date(today.setDate(today.getDate() + 6));
						date2.setHours(23, 59, 59, 999);
						value.push(new Date(date2));
						return value;
					}()
				},
				{
					text: "上周",
					value: function(){					  
						var value = [];
						var today = new Date();
						var lastWeek = new Date(today.getFullYear(), today.getMonth(), today.getDate() - 7);
						var date1 = new Date(lastWeek.getFullYear(), lastWeek.getMonth(), lastWeek.getDate() - lastWeek.getDay() + 1);
						date1.setHours(0, 0, 0, 0);						
						value.push(date1);
						var date2 = new Date(lastWeek.getFullYear(), lastWeek.getMonth(), lastWeek.getDate() - lastWeek.getDay() + 7);
						date2.setHours(23, 59, 59, 999);
						value.push(new Date(date2));
						return value;
					}()
				},
				{
					text: "上个月",
					value: function(){
						var value = [];
						var date1 = new Date();
						date1.setMonth(date1.getMonth() - 1);
						date1.setDate(1);
						date1.setHours(0, 0, 0, 0);
						value.push(date1);
						var date2 = new Date();
						date2.setDate(1);
						date2.setHours(0, 0, 0, 0);
						date2 = date2.getTime() - 1;
						value.push(new Date(date2));
						return value;
					}()
				  },
				  {
					text: "这个月",
					value: function(){
						var value = [];
						var date1 = new Date();
						date1.setDate(1);
						date1.setHours(0, 0, 0, 0);
						value.push(date1);
						var date2 = new Date();
						date2.setMonth(date2.getMonth() + 1);
						date2.setDate(1);
						date2.setHours(0, 0, 0, 0);
						date2 = date2.getTime() - 1;
						value.push(new Date(date2));
						return value;
					}()
				  },
				  {
					text: "下个月",
					value: function(){
						var value = [];
						var date1 = new Date();
						date1.setMonth(date1.getMonth() + 1);
						date1.setDate(1);
						date1.setHours(0, 0, 0, 0);
						value.push(date1);
						var date2 = new Date();
						date2.setMonth(date2.getMonth() + 2);
						date2.setDate(1);
						date2.setHours(0, 0, 0, 0);
						date2 = date2.getTime() - 1;
						value.push(new Date(date2));
						return value;
					}()
				  }
				];
			  // 日期时间范围
			laydate.render({
				elem: '#'+this.settings.target,
				type: 'datetime',
				range: '~',
				trigger: 'none',
				rangeLinked:true,
				shortcuts: shortcuts,
				show:true
			});
		}
	}
	//输出接口
	exports('laydatePlus', laydatePlus);
});   