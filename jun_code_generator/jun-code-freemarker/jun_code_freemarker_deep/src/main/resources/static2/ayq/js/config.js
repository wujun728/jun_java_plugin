/**
 * layui 项目的全局配置
 * 
*/
layui.config({
    //dir: '/res/layui/', //layui.js 所在路径（注意，如果是 script 单独引入 layui.js，无需设定该参数。），一般情况下可以无视
    version: false, //一般用于更新模块缓存，默认不开启。设为 true 即让浏览器不缓存。也可以设为一个固定的值，如：201610
    debug: false, //用于开启调试模式，默认 false，如果设为 true，则JS模块的节点会保留在页面
    base: '/ayq/modules/', //设定扩展的 layui 模块的所在目录，一般用于外部模块扩展
  }).use(['layer'],function(){
    var $ = layui.jquery;
    var layer = layui.layer;  
  });