/*******************************************************************************
 * 基础函数列表
 ******************************************************************************/
/**
 * 删除首尾空格
 */
function trim(s) {
	return s.replace(/(^\s*)|(\s*$)/g, "");
}

/**
 * 为空
 */
function isEmpty(s) {
	if (s == undefined || s == null) {
		return true;
	}

	if (typeof (s) == 'string' && trim(s).length == 0) {
		return true;
	}

	return false;
}

/**
 * 不为空
 */
function isNotEmpty(s) {
	return !isEmpty(s);
}

/**
 * 是否包含
 */
function isContain(srcStr, containStr) {
	if (isEmpty(srcStr)) return false;
	
	var srcVal = srcStr.split(",");
	for (var i = 0; i < srcVal.length; i++) {
		if (srcVal[i] == containStr) {
			return true;
		}
	}
	
	return false;
}

/**
 * 复制对象
 */
function clone(srcObj) {
	if (typeof(srcObj) != 'object') {
		return srcObj;
	} else {
		var newObj = new Object();
		for (key in srcObj) {
			eval("newObj." + key + " = srcObj." + key);
		}
		
		return newObj;
	}
}

/**
 * 替换所有
 */
String.prototype.replaceAll = function(s1, s2) {
	return this.replace(new RegExp(s1, "gm"), s2);
}

/**
 * 得到字符串的字节长度
 */
String.prototype.getByteLen = function(clen) {
	var len = 0;
	
	for (var i = 0; i < this.length; i++) {
		if (this.charAt(i).match(/[^\x00-\xff]/ig) != null) {
			len += clen;
		} else {
			len++;
		}
	}
	
	return len;
}

/**
 * 计算2个日期的差值；例如，传入参数：D1,D2；D1 = 2011-02-02 D2 = 2011-02-01；返回值为1；
 * 
 * @param D1
 * @param D2
 * @return
 */
function DateDiff(D1, D2) {
	var aDate;
	var oDate1;
	var oDate2;
	var iDays;
	aDate = D1.split("-");
	oDate1 = new Date(aDate[1] + "-" + aDate[2] + "-" + aDate[0]);
	aDate = D2.split("-");
	oDate2 = new Date(aDate[1] + "-" + aDate[2] + "-" + aDate[0]);
	iDays = parseInt(Math.abs(oDate1 - oDate2) / 1000 / 60 / 60 / 24);
	return iDays;
}

/**
 * 计算两日期的差；interval参数：D为返回天数，H为返回小时数，M为返回分钟数，T为返回秒数；
 */
function countTime(interval, date1, date2) {
	var obj = {'D' : 1000 * 60 * 60 * 24, 'H' : 1000 * 60 * 60, 'M' : 1000 * 60, 'S' : 1000, 'T' : 1};
	interval = interval.toUpperCase();
	var dt1 = Date.parse(date1.replace(/-/g, "/"));
	var dt2 = Date.parse(date2.replace(/-/g, "/"));
	return ((dt2 - dt1) / obj[interval]).toFixed(2);
}

/**
 * 将Date转换为指定格式的字符串
 */
Date.prototype.format = function(format) {
	var o = {
		"M+" : this.getMonth() + 1,
		"d+" : this.getDate(),
		"H+" : this.getHours(),
		"m+" : this.getMinutes(),
		"s+" : this.getSeconds()
	}
	
	if (/(y+)/.test(format)) {
		format = format.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
	}
	
	for (var k in o) {
		if (new RegExp("(" + k + ")").test(format)) {
			format = format.replace(RegExp.$1, RegExp.$1.length == 1 ? o[k] : ("00" + o[k]).substr(("" + o[k]).length));
		}
	}
	
	return format;
}
