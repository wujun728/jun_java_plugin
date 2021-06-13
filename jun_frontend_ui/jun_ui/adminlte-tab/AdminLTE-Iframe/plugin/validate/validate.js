/*
 *
 *  * 版权所有(C) 浙江大道网络科技有限公司2011-2020
 *  * Copyright 2009-2020 Zhejiang GreatTao Factoring Co., Ltd.
 *  *  
 *  * This software is the confidential and proprietary information of
 *  * Zhejiang GreatTao Corporation ("Confidential Information").  You
 *  * shall not disclose such Confidential Information and shall use
 *  * it only in accordance with the terms of the license agreement
 *  * you entered into with Zhejiang GreatTao
 *  
 */

/**
 * Created by Administrator on 2016/7/29.
 */
function doValidForm(formId,language) {
    /*验证*/
    $(formId).find(".warning,.valid,.promimg").remove();
    $(formId).find(".error").removeClass("error");
    $(formId).find(".prombtn").removeClass("prombtn");
    $(formId).find(".prominput").removeClass("prominput");
    var position;
    var dataRequire = "必填";
    var dataFax = "请输入正确的传真格式";
    var dataContact = "请输入正确的联系方式";
    var dataMax = "字符串长度不能超过";
    var dataMin = "字符长度必须大于";
    var dataPositiveInteger = "只能输入正整数";
    var dataPositiveNumber = "只能输入正数";
    var dataNumber = "只能输入数字";
    var dataNohtml = "禁止输入html标签";
    var dataEmail = "请输入正确的邮箱";
    var dataNumericLetters="只能输入数字、字母";
    var dataAlphanumeric="只能输入数字、字母和空格";
    var dataNochinese="不能输入中文";
    var dataChinese="只能输入中文";
    var dataEnglish = "只能输入英文";
    var dataNoLetterChinese="不能输入中英文"
    var dataDigitalAlphabetChinese="只能输入数字、字母和中文"
    var dataDigitalAlphabetChineseBlank="只能输入数字、字母、中文和空格"
    var dataLength = "字符串长度必须为"
    var dataMoney = "请出入正确的金额"

    if("en" == language){
        dataRequire = "This is required";
        dataFax = "Please enter the correct fax";
        dataContact = "Please enter the correct contact";
        dataMax = "String length must be shorter than ";
        dataMin = "String length must be longer than ";
        dataPositiveInteger = "Only positive integers can be entered";
        dataPositiveNumber = "Only positive can be entered";
        dataNumber = "Only number can be entered";
        dataNohtml = "HTML tags are not allowed"
        dataEmail = "Please enter the correct email";
        dataNumericLetters="Only number and letters can be entered"
        dataAlphanumeric="Only numbers, letters and spaces can be entered"
        dataNochinese="Cannot enter Chinese";
        dataChinese="Only Chinese can be entered";
        dataEnglish = "Only English can be entered";
        dataNoLetterChinese="Cannot enter Chinese and English"
        dataDigitalAlphabetChinese="Only numbers, letters and Chinese can be entered"
        dataDigitalAlphabetChineseBlank="Only numbers, letters, Chinese and spaces can be entered"
        dataLength = "String length must be "
    }
    var flag = true;
    /*必填*/
    $(formId).find("input[require]:visible,textarea[require]:visible").each(function () {
        position = setPosition(this);
        var messageRequire = (typeof($(this).attr("data-require")) != "undefined") ? $(this).attr("data-require") : dataRequire;
        if ("" == $(this).val().trim()) {
            $(this).addClass("error");
            $(this).addClass("prominput");
            flag = false;
            $(this).after('<span  class="active promimg fa fa-exclamation-circle '+position+'" data-container="body" data-toggle="popover" data-trigger="focus" data-placement="top" data-content="'+messageRequire+'"></span>');
            //$(this).parent().wrapInner('<div></div>');
        }

    });

    /*必选-包含0*/
    $(formId).find("select[require]:visible").each(function() {
        position = setPosition(this);
        var messageRequire = (typeof($(this).attr("data-require")) != "undefined") ? $(this).attr("data-require") : dataRequire;
        if ("-1" == $(this).val().trim() ||"0" == $(this).val().trim() ||"" == $(this).val().trim()) {
            $(this).addClass("error");
            $(this).addClass("prombtn");
            flag = false;
            $(this).after('<span  class="active promimg fa fa-exclamation-circle '+position+'" data-container="body" data-toggle="popover" data-trigger="focus" data-placement="top" data-content="'+messageRequire+'"></span>');
            //$(this).parent().wrapInner('<div style="position: relative;"></div>');
        }
    });

    /*必选-包含0*/
    $(formId).find("select[requireZero]:visible").each(function() {
        position = setPosition(this);
        var messageRequire = (typeof($(this).attr("data-require")) != "undefined") ? $(this).attr("data-require") : dataRequire;
        if ("" == $(this).val().trim()) {
            $(this).addClass("error");
            $(this).addClass("prombtn");
            flag = false;
            $(this).after('<span  class="active promimg fa fa-exclamation-circle '+position+'" data-container="body" data-toggle="popover" data-trigger="focus" data-placement="top" data-content="'+messageRequire+'"></span>');
            //$(this).parent().wrapInner('<div style="position: relative;"></div>');
        }
    });


    /*最大长度*/
    $(formId).find("input[max]:visible,textarea[max]:visible").each(function () {
        position = setPosition(this);
        var messageMax = (typeof($(this).attr("data-max")) != "undefined") ? $(this).attr("data-max") : dataMax;
        var le = $(this).attr("max");
        if ($(this).val().length > le) {
            $(this).addClass("error");
            flag = false;
            $(this).addClass("prominput");
            $(this).after('<span  class="active promimg fa fa-exclamation-circle '+position+'" data-container="body" data-toggle="popover" data-trigger="focus" data-placement="top" data-content="'+ messageMax + le +'"></span>');
        }
    });

    /*最小长度*/
    $(formId).find("input[min]:visible").each(function () {
        position = setPosition(this);
        var le = $(this).attr("min");
        var messageMin = (typeof($(this).attr("data-min")) != "undefined") ? $(this).attr("data-min") : dataMin;
        if ($(this).val().length < le) {
            $(this).addClass("error");
            flag = false;
            $(this).addClass("prominput");
            $(this).after('<span  class="active promimg fa fa-exclamation-circle '+position+'" data-container="body" data-toggle="popover" data-trigger="focus" data-placement="top" data-content="'+ messageMin + le +'"></span>');
            //$(this).parent().wrapInner('<div></div>');
        }
    });

    /*是否是传真*/
    $(formId).find("input[fax]:visible").each(function () {
        position = setPosition(this);
        var messageFax = (typeof($(this).attr("data-fax")) != "undefined") ? $(this).attr("data-fax") : dataFax;
        if (isNotNull($(this).val()) &&!isFax($(this).val())){
            $(this).addClass("error");
            flag = false;
            $(this).addClass("prominput");
            $(this).after('<span  class="active promimg fa fa-exclamation-circle '+position+'" data-container="body" data-toggle="popover" data-trigger="focus" data-placement="top" data-content="'+ messageFax +'"></span>');
        }
    });

    /*是否是正确的联系方式*/
    $(formId).find("input[contact]:visible").each(function () {
        position = setPosition(this);
        var messageContact = (typeof($(this).attr("data-contact")) != "undefined") ? $(this).attr("data-contact") : dataContact;
        if (isNotNull($(this).val()) &&!isFax($(this).val())){
            $(this).addClass("error");
            flag = false;
            $(this).addClass("prominput");
            $(this).after('<span  class="active promimg fa fa-exclamation-circle '+position+'" data-container="body" data-toggle="popover" data-trigger="focus" data-placement="top" data-content="'+ messageContact +'"></span>');
        }
    });

    /*正整数*/
    $(formId).find("input[plusinteger]:visible").each(function () {
        position = setPosition(this);
        var messagePositiveInteger = (typeof($(this).attr("data-positiveInteger")) != "undefined") ? $(this).attr("data-positiveInteger") : dataPositiveInteger;
        if (isNotNull($(this).val()) && !isPositiveInteger($(this).val()) ) {
            $(this).addClass("error");
            flag = false;
            $(this).addClass("prominput");
            $(this).after('<span  class="active promimg fa fa-exclamation-circle '+position+'" data-container="body" data-toggle="popover" data-trigger="focus" data-placement="top" data-content="'+ messagePositiveInteger +'"></span>');
        }
    });

    /*数字*/
    $(formId).find("input[number]:visible").each(function () {
        position = setPosition(this);
        var messageNumber = (typeof($(this).attr("data-number")) != "undefined") ? $(this).attr("data-number") : dataNumber;
        var num = $(this).val().trim();
        if(num.length > 0 && isNaN(num)){
            $(this).addClass("error");
            flag = false;
            $(this).addClass("prominput");
            $(this).after('<span  class="active promimg fa fa-exclamation-circle '+position+'" data-container="body" data-toggle="popover" data-trigger="focus" data-placement="top" data-content="'+ messageNumber +'"></span>');
        }
    });

    /*是否是邮箱*/
    $(formId).find("input[email]:visible").each(function () {
        position = setPosition(this);
        var messageEmail = (typeof($(this).attr("data-email")) != "undefined") ? $(this).attr("data-email") : dataEmail;
        if (isNotNull($(this).val()) && !isEmail($(this).val())){
            $(this).addClass("error");
            flag = false;
            $(this).addClass("prominput");
            $(this).after('<span  class="active promimg fa fa-exclamation-circle '+position+'" data-container="body" data-toggle="popover" data-trigger="focus" data-placement="top" data-content="'+ messageEmail +'"></span>');
        }
    });

    /*禁止输入html标签*/
    $(formId).find("input[nohtml]:visible,textarea[nohtml]:visible").each(function () {
        position = setPosition(this);
        var messageNohtml = (typeof($(this).attr("data-nohtml")) != "undefined") ? $(this).attr("data-nohtml") : dataNohtml;
        if (isNotNull($(this).val()) &&isHtml($(this).val())){
            $(this).addClass("error");
            flag = false;
            $(this).addClass("prominput");
            $(this).after('<span  class="active promimg fa fa-exclamation-circle '+position+'" data-container="body" data-toggle="popover" data-trigger="focus" data-placement="top" data-content="'+ messageNohtml +'"></span>');
        }
    });

    /*单选框必填 --警告框的位置由label决定*/
    var preName = "";
    var rdos = $(formId).find("input[type = radio][require]:visible");
    if (rdos.size() > 0) {
        $(rdos).each(function (index, dom) {
            var name = dom.name;
            if (preName != name) {
                preName = name;
                if ($(formId).find("input[type = radio][name ='" + name + "']:checked").size() == 0) {
                    var lst = $(formId).find("input[type = radio][name ='" + name + "']:last");
                    $(lst).addClass("error");
                    $(this).addClass("prominput");
                    flag = false;
                    $(lst).after('<span  class="active promimg fa fa-exclamation-circle" data-container="body" data-toggle="popover" data-trigger="focus" data-placement="top" data-content="'+ dataRequire +'"></span>');
                }
            }
        });
        preName = "";
    }

    var preName = "";
    var rdos = $(formId).find("input[type = checkBox][require]:visible");
    if (rdos.size() > 0) {
        $(rdos).each(function (index, dom) {
            var name = dom.name;
            if (preName != name) {
                preName = name;
                if ($(formId).find("input[type = checkBox][name ='" + name + "']:checked").size() == 0) {
                    var lst = $(formId).find("input[type = checkBox][name ='" + name + "']:last");
                    $(lst).addClass("error");
                    $(this).addClass("prominput");
                    flag = false;
                    $(lst).after('<span  class="active promimg fa fa-exclamation-circle" data-container="body" data-toggle="popover" data-trigger="focus" data-placement="top" data-content="'+ dataRequire +'"></span>');
                }
            }
        });
        preName = "";
    }

    /*数字字母*/
    $(formId).find("input[numericLetters]:visible").each(function () {
        position = setPosition(this);
        var messageNumericLetters = (typeof($(this).attr("data-numericLetters")) != "undefined") ? $(this).attr("data-numericLetters") : dataNumericLetters;
        var alp = $(this).val().trim();
        var patrn=/^[a-zA-Z0-9]+$/
        if (alp != "" && !patrn.exec(alp)) {
            $(this).addClass("error");
            flag = false;
            $(this).addClass("prominput");
            $(this).after('<span  class="active promimg fa fa-exclamation-circle '+position+'" data-container="body" data-toggle="popover" data-trigger="focus" data-placement="top" data-content="'+ messageNumericLetters +'"></span>');
        }
    });

    $(formId).find("div[require]:visible").each(function () {
        position = setPosition(this);
        var v = $($(this).children()[1]).val();
        if (v == "0") {
            $(this).addClass("error");
            $(this).addClass("prombtn");
            flag = false;
            $(this).after('<span  class="active promimg fa fa-exclamation-circle '+position+'" data-container="body" data-toggle="popover" data-trigger="focus" data-placement="top" data-content="'+ dataRequire +'"></span>');
        }
    });
    $(formId).find("div[require]:visible").each(function () {
        position = setPosition(this);
        var v = $($(this).children()[0]).val();
        if (v == "0") {
            $(this).addClass("error");
            $(this).addClass("prombtn");
            flag = false;
            $(this).after('<span  class="active promimg fa fa-exclamation-circle '+position+'" data-container="body" data-toggle="popover" data-trigger="focus" data-placement="top" data-content="'+ dataRequire +'"></span>');
        }
    });
    /*数字、字母和空格*/
    $(formId).find("input[alphanumeric]:visible").each(function () {
        position = setPosition(this);
        var messageAlphanumeric=(typeof($(this).attr("data-alphanumeric")) != "undefined") ? $(this).attr("data-alphanumeric") : dataAlphanumeric;
        var alp = $(this).val().trim();
        var patrn=/^[A-Za-z0-9\s]+$/;
        if (!patrn.exec(alp)) {
            $(this).addClass("error");
            flag = false;
            $(this).addClass("prominput");
            $(this).after('<span  class="active promimg fa fa-exclamation-circle '+position+'" data-container="body" data-toggle="popover" data-trigger="focus" data-placement="top" data-content="'+ messageAlphanumeric +'"></span>');
        }
    });

    /*不能输入中文*/
    $(formId).find("input[nochinese]:visible").each(function () {
        position = setPosition(this);
        var messageNochinese=(typeof($(this).attr("data-nochinese")) != "undefined") ? $(this).attr("data-nochinese") : dataNochinese;
        var alp = $(this).val().trim();
        var patrn = /^[^\u4e00-\u9fa5]+$/;//无中文匹配
        if (alp!=""&&!patrn.exec(alp)) {
            $(this).addClass("error");
            flag = false;
            $(this).addClass("prominput");
            $(this).after('<span  class="active promimg fa fa-exclamation-circle '+position+'" data-container="body" data-toggle="popover" data-trigger="focus" data-placement="top" data-content="'+ messageNochinese +'"></span>');
        }
    });

    /*只能输入中文*/
    $(formId).find("input[chinese]:visible").each(function () {
        position = setPosition(this);
        var messageChinese=(typeof($(this).attr("data-chinese")) != "undefined") ? $(this).attr("data-chinese") : dataChinese;
        var alp = $(this).val().trim();
        var patrn = /^[\u4e00-\u9fa5]+$/;//全中文匹配
        if (!patrn.exec(alp)) {
            $(this).addClass("error");
            flag = false;
            $(this).addClass("prominput");
            $(this).after('<span  class="active promimg fa fa-exclamation-circle '+position+'" data-container="body" data-toggle="popover" data-trigger="focus" data-placement="top" data-content="'+ messageChinese +'"></span>');
        }
    });


    /*只能输入英文*/
    $(formId).find("input[onlyEnglish]:visible").each(function () {
        position = setPosition(this);
        var messageEnglish=(typeof($(this).attr("data-english")) != "undefined") ? $(this).attr("data-english") : dataEnglish;
        var alp = $(this).val().trim();
        var patrn = /[\u4e00-\u9fa5]+/;//全英文匹配
        if (patrn.test(alp)) {
            $(this).addClass("error");
            flag = false;
            $(this).addClass("prominput");
            $(this).after('<span  class="active promimg fa fa-exclamation-circle '+position+'" data-container="body" data-toggle="popover" data-trigger="focus" data-placement="top" data-content="'+ messageEnglish +'"></span>');
        }
    });

    /*不能输入中英文*/
    $(formId).find("input[noLetterChinese]:visible").each(function () {
        position = setPosition(this);
        var messageNoLetterChinese=(typeof($(this).attr("data-noLetterChinese")) != "undefined") ? $(this).attr("data-noLetterChinese") : dataNoLetterChinese;
        var alp = $(this).val().trim();
        var patrn = /^[^a-zA-Z\u4e00-\u9fa5]+$/;//无中英文匹配
        if ( alp.length > 0 && !patrn.test(alp)) {
            $(this).addClass("error");
            flag = false;
            $(this).addClass("prominput");
            $(this).after('<span  class="active promimg fa fa-exclamation-circle '+position+'" data-container="body" data-toggle="popover" data-trigger="focus" data-placement="top" data-content="'+ messageNoLetterChinese +'"></span>');
        }
    });

    /*只能输入数字、字母和中文*/
    $(formId).find("input[digitalAlphabetChinese]:visible").each(function () {
        position = setPosition(this);
        var messageDigitalAlphabetChinese=(typeof($(this).attr("data-digitalAlphabetChinese")) != "undefined") ? $(this).attr("data-digitalAlphabetChinese") : dataDigitalAlphabetChinese;
        var alp = $(this).val().trim();
        var patrn=/^[a-zA-Z0-9\u4e00-\u9fa5]+$/
        if (alp != "" && !patrn.exec(alp)) {
            $(this).addClass("error");
            flag = false;
            $(this).addClass("prominput");
            $(this).after('<span  class="active promimg fa fa-exclamation-circle '+position+'" data-container="body" data-toggle="popover" data-trigger="focus" data-placement="top" data-content="'+ messageDigitalAlphabetChinese +'"></span>');
        }
    });
    
    /*只能输入数字、字母、中文和空格*/
    $(formId).find("input[digitalAlphabetChineseBlank]:visible").each(function () {
        position = setPosition(this);
        var messageDigitalAlphabetChineseBlank=(typeof($(this).attr("data-digitalAlphabetChineseBlank")) != "undefined") ? $(this).attr("data-digitalAlphabetChineseBlank") : dataDigitalAlphabetChineseBlank;
        var alp = $(this).val().trim();
        var patrn=/^[a-zA-Z0-9\u4e00-\u9fa5 ]+$/
        if (alp != "" && !patrn.exec(alp)) {
            $(this).addClass("error");
            flag = false;
            $(this).addClass("prominput");
            $(this).after('<span  class="active promimg fa fa-exclamation-circle '+position+'" data-container="body" data-toggle="popover" data-trigger="focus" data-placement="top" data-content="'+ messageDigitalAlphabetChineseBlank +'"></span>');
        }
    });

    /*固定长度*/
    $(formId).find("input[length]:visible,textarea[length]:visible").each(function () {
        if($(this).val()!=""){
            position = setPosition(this);
            var messageLength = (typeof($(this).attr("data-length")) != "undefined") ? $(this).attr("data-length") : dataLength;
            var le = $(this).attr("length");
            if ($(this).val().length != le) {
                $(this).addClass("error");
                flag = false;
                $(this).addClass("prominput");
                $(this).after('<span  class="active promimg fa fa-exclamation-circle '+position+'" data-container="body" data-toggle="popover" data-trigger="focus" data-placement="top" data-content="'+ messageLength + le + '"></span>');
            }
        }
    });

    /*金钱校验*/
    $(formId).find("input[money]:visible").each(function () {
        position = setPosition(this);
        var messageRequire = (typeof($(this).attr("data-money")) != "undefined") ? $(this).attr("data-money") : dataMoney;
        var alp = $(this).val().trim();
        var patrn=/^d*(?:.d{0,2})?$/  ;
        if (alp == 0.00 || !patrn.exec(alp)) {
            $(this).addClass("error");
            $(this).addClass("prominput");
            flag = false;
            $(this).after('<span  class="active promimg fa fa-exclamation-circle '+position+'" data-container="body" data-toggle="popover" data-trigger="focus" data-placement="top" data-content="'+messageRequire+'"></span>');
        }

    });

    /*****************************************************************************************************  常用分界线  ***************************************************************************************************************/

    //不局限于文字的时间比较
    $(formId).find('.end_date').each(function(){
        position = setPosition(this);
        var dateBefore=new Date($('.start_date').val()).getTime();
        var dateAfter=new Date($('.end_date').val()).getTime();
        if(dateAfter<dateBefore){
            $(this).addClass("error");
            $(this).addClass("prombtn");
            flag = false;
            $(this).after('<span  class="active promimg fa fa-exclamation-circle '+position+'" data-container="body" data-toggle="popover" data-trigger="focus" data-placement="top" data-content="结束时间不能小于开始时间"></span>');
        }
    });


    /*必填一项*/
    $(formId).find('input[length-10]:visible').each(function(){
        if($(this).val()!=null&&$(this).val()!=""){
            flag = false;
        }
    })


    //密码比较
    $(formId).find('.pwdCheck').each(function(){
        var prompt = "Password cannot be Chinese";
        if("" != $(this).attr("nochinese")){
            prompt = $(this).attr("nochinese");
        }

        position = setPosition(this);
        var password=$("#password").val().trim();
        var confirmPassword=$("#confirmPassword").val().trim();
        if(!isCorrectPWD(password) || !isCorrectPWD(confirmPassword)){
            $(this).addClass("error");
            $(this).addClass("prombtn");
            flag = false;
            $(this).after('<span  class="active promimg fa fa-exclamation-circle '+position+'" data-container="body" data-toggle="popover" data-trigger="focus" data-placement="top" data-content="Password must consist of 6 to 16 letters, Numbers"></span>');
        }
        var alp = $(this).val().trim();
        var patrn = /^[^\u4e00-\u9fa5]+$/;//无中文匹配
        if (!patrn.exec(alp)) {
            $(this).addClass("error");
            flag = false;
            $(this).addClass("prominput");
            $(this).after('<span  class="active promimg fa fa-exclamation-circle '+position+'" data-container="body" data-toggle="popover" data-trigger="focus" data-placement="top" data-content="'+ prompt +'"></span>');
        }
        if(password != confirmPassword){
            $(this).addClass("error");
            $(this).addClass("prombtn");
            flag = false;
            $(this).after('<span  class="active promimg fa fa-exclamation-circle '+position+'" data-container="body" data-toggle="popover" data-trigger="focus" data-placement="top" data-content="The two passwords you typed do not match"></span>');
        }

    });


    return flag;
}

function isNull(s) {
    return s === null;
}
function isNotNull(s) {
    return !isNull(s) && !isUndefined(s) && !isEmpty(s);
}
function isUndefined(s) {
    return typeof s === "undefined";
}
function isEmpty(s) {
    return /^\s*$/.test(s);
}
function isFax(s){
    return  /^[+]{0,1}(\d){1,3}[ ]?([-]?((\d)|[ ]){1,12})+$/.test(s);
}
function isEmail(s){
    return /^\w+((-\w+)|(\.\w+))*\@[A-Za-z0-9]+((\.|-)[A-Za-z0-9]+)*\.[A-Za-z0-9]+$/.test(s);
}

function isCorrectPWD(s){
    //密码必须由 6-16位字母、数字、特殊符号线组成
    //return /^(?=.*[a-zA-Z])(?=.*\d)(?=.*[~!@#$%^&*()_+`\-={}:";'<>?,.\/]).{6,16}$/.test(s);
    //密码由 6-16位字母、数字、特殊符号线组成
    //return /^(?:\d+|[a-zA-Z]+|[!@#$%^&*]+).{5,15}$/.test(s);
    //密码必须由6-16位字母、数字组合
    return /^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,16}$/.test(s);
}

function isPositiveInteger(s){
    return /^[1-9]\d*$/.test(s)
}

function isHtml(s){
    var  reg = /<[^>]+>/g;
    return reg.test(s);
}


$(document).on('mouseenter', '.promimg', function () {
    $(this).popover('show');
    //$('.popover').css({'height': 'auto', 'line-height': '30px', 'width': 'auto'});
}).on('mouseleave', '.promimg', function () {
    $(this).popover('hide');
});$(document).on('mouseenter', '.promimg1', function () {
    $(this).popover('show');
    //$('.popover').css({'height': 'auto', 'line-height': '30px', 'width': 'auto'});
}).on('mouseleave', '.promimg1', function () {
    $(this).popover('hide');
});

/*读取对象的data-position参数*/
function setPosition(object){
    var position = "";
    if(typeof($(object).attr("data-position")) != "undefined"){
        position=$(object).attr("data-position");
    }
    return position;
}