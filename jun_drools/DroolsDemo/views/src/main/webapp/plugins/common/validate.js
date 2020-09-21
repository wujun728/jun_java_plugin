/**
 * @author 廖广安 liaoguangan@hankedata.com:
 * @version 创建时间：2016年1月13日 上午15:32:09
 * @company 四川汉科计算机信息技术有限公司
 * @description validate form
 */

$(function() {

	String.prototype.replaceAll = function(tag) {
		var result = "";
		$.each(this, function(i, e) {
			result += e.replace(tag, "");
		});
		return result;
	}

	String.prototype.specialChar = function() {
		var pattern = new RegExp(
				"[`~!%#$^&*()=|{}':;',　\\[\\]<>/? \\；：%……+￥（）【】‘”“'。—，、？]");
		return !pattern.test(this.toString());
	}

	$.fn.validate = function() {
		var result = true;
		$(this).find("input[type!='button'],select, textarea").each(
				function(index, element) {
					var type = $(this).attr("validate");
					var value = $(this).val().replaceAll(" ");
					var required = $(this).attr("required");
					var label = $(this).parent().prev().find("label").eq(0)
							.text();
					if("*" == label.trim()){
						label = $(this).parent().prev().find("label").eq(1).text();
					}
					if(label===""){
						var _attr_label_title = $(this).parent().parent().find("label").eq(0);
						if(_attr_label_title){
							label = _attr_label_title.text();
							if(label!=null && label.lastIndexOf("(")>1){
								label = label.substring(0,label.lastIndexOf("("));
							}
						}
					}
					
					var length = value.length;
					if (type == "username" && !value.specialChar()) {
						alert(label + $.t("validate.not_special_char"));
						result = false;
						return false;
					}
					if (required && (!value || $.t("common.choose") == value)) {
						alert(label + $.t("validate.not_empty"));
						//focus到input
						var _attr_p = $(this).parents("div[tab]").prev();
						if(_attr_p){
							_attr_p.click();
							$(this).focus();
						}
						
						result = false;
						return false;
					}
					switch (type) {
					default:
					case "username":
					case "password": {
						var maxl = $(this).attr("maxlength")?parseInt($(this).attr("maxlength")):null;
						var minl = $(this).attr("minlength")?parseInt($(this).attr("minlength")):null;
						if (!maxl && !minl)
							return true;
						if(maxl && length > maxl){
							alert(label + $.t("validate.out_maxlength") +  maxl);
							result = false;
							return false;
						}
						if(minl && length < minl){
							alert(label + $.t("validate.low_minlength") +  minl);
							result = false;
							return false;
						}
						break;
					}
					case "phonenum":
						var pattern = /^([0-9]{3,4}-)?[0-9]{7,8}$/;
						if (!pattern.test(value)) {
							alert(label + $.t("validate.format_error"));
							result = false;
							return false;
						}
						break;
					case "number": {
						if (isNaN(value)) {
							alert(label + $.t("validate.format_error"));
							result = false;
							return false;
						}
						value = parseFloat(value);
						var maxn = $(this).attr("maxnum")?parseFloat($(this).attr("maxnum")):null;
						var minn = $(this).attr("minnum")?parseFloat($(this).attr("minnum")):null;
						if (!maxn && !minn)
							return true;
						if(maxn!=null && (value > maxn)){
							alert(label + $.t("validate.out_max")+maxn);
							result = false;
							return false;
						}
						if(minn!=null && (value < minn)){
							alert(label + $.t("validate.low_min")+minn);
							result = false;
							return false;
						}
						break;
					}
					case "intnumber": {
						if (parseInt(value) != value) {
							alert(label + $.t("validate.format_error"));
							result = false;
							return false;
						}
						value = parseInt(value);
						var maxn = $(this).attr("maxnum")?parseFloat($(this).attr("maxnum")):null;
						var minn = $(this).attr("minnum")?parseFloat($(this).attr("minnum")):null;
						if(maxn && value > maxn){
							alert(label + $.t("validate.out_max")+maxn);
							result = false;
							return false;
						}
						if(minn && value < minn){
							alert(label + $.t("validate.low_min")+minn);
							result = false;
							return false;
						}
						break;
					}
					case "email": {
						var pattern = /^[\w\-\.]+@[\w\-\.]+(\.\w+)+$/;
						if (!pattern.test(value)) {
							alert(label + $.t("validate.format_error"));
							result = false;
							return false;
						}
						break;
					}
					}
				});
		return result;
	}
});