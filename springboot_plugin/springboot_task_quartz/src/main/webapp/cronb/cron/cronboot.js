/**
 * Created by LinJ on 2015/11/5.
 */

//解析cron表达式到生成器的反向函数
function btnFan() {
    alert("explaing");
}

/**
 * 表达式重置为默认值
 */
function everyTime(dom) {
    var item = $("span[name=v_" + dom.name + "]");
    item.html("*");
    item.change();
}

/**
 * 表达式重置为默认值
 入参为元素的名字
 */
function everyTimeByName(v_name) {
    var item = $("span[name=" +v_name + "]");
    item.html("*");
    item.change();
}


/**
 * 不指定 即重置为？号
 */
function unAppoint(dom) {
    var name = dom.name;
    var val = "?";
    if (name == "year")
        val = "";
    var item = $("span[name=v_" + name + "]");
    item.html(val);
    item.change();
}

/**
 * 不指定 即重置为？号
 入参为元素的名字
 */
function unAppointByName(v_name) {

    var val = "?";
    if (v_name == "year")
        val = "";
    var item = $("span[name=" + v_name + "]");
    item.html(val);
    item.change();
}

/**
 * 书写有前后的公式，包括 1-2 1/2 1#2 等
 */
function writeStartAndEnd(dom,sym) {
    var name = dom.name;
    var ns = $(dom).closest('.radiocheck').find(".numberspinner");
    var start = ns.eq(0).val();
    var end = ns.eq(1).val();
    var item = $("span[name=v_" + name + "]");
    item.html(start + sym + end);
    item.change();
}


//点击div中的内容，对应radio即被选中的功能
function radioCheckByClick(dom){
    var theRadio = $(dom).find(':radio');
    theRadio.eq(0).iCheck('check');
}



//自定义部分的列表监听
$(function(){


});