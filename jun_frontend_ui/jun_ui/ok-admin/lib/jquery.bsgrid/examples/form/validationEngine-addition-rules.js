/**
 * 验证需以字母开头，只可包含字母数字的账号，最小长度1.
 * 
 * @param {jqObject} the field where the validation applies
 * @param {Array[String]} validation rules for this field
 * @param {int} rule index
 * @param {Map} form options
 * @return an error string if validation failed
 */
function checkAccount(field, rules, i, options){
	if(!/^[a-zA-Z]\w*$/.test(field.val())){
		return "* 需以字母开头可含数字";
    }
}
/**
 * 验证中文.
 */
function checkChinese(field, rules, i, options){
	if(!/^[\u4E00-\u9FA5]+$/.test(field.val())){
		return "* 只能填写汉字";
	}
}
/**
 * 验证中文姓名(可以包含中文，英文字母，数字，半角括号，下划线"_"及"-").
 */
function checkChineseName(field, rules, i, options){
	if(!/^[\d\u002d\u005f\u0028\u0029\u4E00-\u9FA5a-zA-Z]+$/.test(field.val())){
		return "* 请填写中文、英文、数字、半角括号、\"_\"、\"-\"";
	}
}
/**
 * 验证18位身份证号码.
 */
function checkChinaId(field, rules, i, options){
	if(!/^[1-9]\d{5}[1-9]\d{3}(((0[13578]|1[02])(0[1-9]|[12]\d|3[0-1]))|((0[469]|11)(0[1-9]|[12]\d|30))|(02(0[1-9]|[12]\d)))(\d{4}|\d{3}[xX])$/.test(field.val())){
		return "* 无效的身份证号码";
	}
}
/**
 * 验证宽松的身份证号码18位或15位.
 */
function checkChinaIdLoose(field, rules, i, options){
	if(!/^(\d{18}|\d{15}|\d{17}[xX])$/.test(field.val())){
		return "* 无效的身份证号码";
	}
}
/**
 * 验证邮政编码.
 */
function checkChinaZip(field, rules, i, options){
	if(!/^\d{6}$/.test(field.val())){
		return "* 无效的邮政编码";
	}
}
/**
 * 验证手机号码.
 */
function checkChinaMobilephone(field, rules, i, options){
	if(!/^1\d{10}$/.test(field.val())){
		return "* 无效的手机号码";
	}
}
/**
 * 验证QQ号码.
 */
function checkQq(field, rules, i, options){
	if(!/^[1-9]\d{4,10}$/.test(field.val())){
		return "* 无效的QQ号码";
	}
}