/**
 * zznode公共工具类
 * @author wangkaiping
 */

var zznode = {
	version : '1.0',
	discropity : '通用工具类'
}

zznode.util = {
	discropity : '所有工具在此,包括字符串处理工具，格式化工具',
	/**
	 * 对日期进行格式化 格式 format:yyyy-MM-dd hh:mm:ss yyyyMMddmmhhss
	 * @param {Object} date 日期格式 如果报错请 new Date(val) 构建后再传
	 * @param {Object} format
	 * @return {TypeName} 
	 */
	dateFormatter : function(date, format) {
		var o = {
			"M+" : date.getMonth() + 1, //month
			"d+" : date.getDate(), //day
			"h+" : date.getHours(), //hour
			"m+" : date.getMinutes(), //minute
			"s+" : date.getSeconds(), //second
			"q+" : Math.floor((date.getMonth() + 3) / 3), //quarter
			"S" : date.getMilliseconds()
		//millisecond
		}
		if (/(y+)/.test(format))
			format = format.replace(RegExp.$1, (date.getFullYear() + "")
					.substr(4 - RegExp.$1.length));
		for ( var k in o)
			if (new RegExp("(" + k + ")").test(format))
				format = format.replace(RegExp.$1,
						RegExp.$1.length == 1 ? o[k] : ("00" + o[k])
								.substr(("" + o[k]).length));
		return format;
	},
	
	/*****
	 * 
	 * 将日期字符串yyyy-MM-dd hh:mm:ss 格式化成Date类型
	 */
	toDate : function (time){
		var date;
		var reg = /^\d{4}-\d{2}-\d{2} \d{2}:\d{2}:\d{2}$/;
		if(reg.test(time)){
			date = new Date(time.replace(/-/g,"/"));
		}else{
			alert('日期格式不正确');
			return false;
		}
		return date;
	},
	
	/**
	 * 接收光功率，发送光功率转换工具
	 * @param {Object} power
	 * @return {TypeName} 
	 */
	passRxTxPower : function (power) {
		if(power == null || power == ''){
	    	return '--';
	    }else if(power == 0){
	    	return "--";
	    }else if(-65535 < power < 65535){
	    	var temp = power * 0.0001;
	    	var result = Math.log(temp)/Math.log(10);
	    	result =(result*10).toFixed(2);
	    	return result + "dbm";
	    }else {
	    	return "--";
	    }
	},
	
	/**
	 * 温度转换工具
	 * @param {Object} temperature
	 * @return {TypeName} 
	 */
	passTemperature:function (temperature){
		if(temperature == null || temperature == ''){
			return '--';
		}else if(temperature >=0){
			return Math.round(temperature/256)+"°C";
		}else{
			return '--';
		}
	},
	
	/**
	 * 电压转换工具
	 * @param {Object} temperature
	 * @return {TypeName} 
	 */
	changeVoltage:function (voltage){
		if(voltage == null || voltage == ''){
			return '--';
		}else if(voltage >=0){
			return Math.round(voltage/100)/100+"V";
		}else{
			return '--';
		}
	},
	
	/**
	 * 电流转换工具
	 * @param {Object} temperature
	 * @return {TypeName} 
	 */
	changeBiasCurrent:function (biasCurrent){
		if(biasCurrent == null || biasCurrent == ''){
			return '--';
		}else if(biasCurrent >=0){
			return Math.round(biasCurrent/500*100)/100+"mA";
		}else{
			return '--';
		}
	},
	
	/******
	 * VOIP注册失败原因转换
	 * @param {Object} temperature
	 * @return {TypeName} 
	 */
	changeReason:function(value){
		if(value == '1'){
			return 'IAD模块错误';
		}else if('2' == value){
			return '访问路由不通';
		}else if('3' == value){
			return '访问服务器无反应';
		}else if('4' == value){
			return '帐号密码错误';
		}else if('5' == value){
			return '未知错误';
		}else{
			return '';
		}
	},
	
	/****
	 * 验证输入框是否是数字
	 */
	isNumber:function(value){
		if(/^\d+$/.test(value)){
			return true;
		}
		return false;
	}
}

var timeFormat = function(value,rowData,index){
	if(value){
		var startData = new Date(value);
		var formatData = zznode.util.dateFormatter(startData,'yyyy-MM-dd hh:mm:ss');
		return formatData;
	}else{
		return "";
	}
}

