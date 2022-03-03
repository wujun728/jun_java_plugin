layui.define(['layer','util'], function(exports){
  var $ = layui.jquery
  ,layer = layui.layer
  ,util=layui.util
  ,device = layui.device();
  //阻止IE7以下访问
  if(device.ie && device.ie < 8){
    layer.alert('最低支持ie8，您当前使用的是IE'+ device.ie + ',请更新您的浏览器！');
  }
//固定Bar
  util.fixbar({
    bar1: false
  });
  


  //窗口scroll
  ;!function(){
    var main = $('.site-tree').parent(), scroll = function(){
      var stop = $(window).scrollTop();
      if($(window).width() <= 750) return;
      var bottom = $('.footer').offset().top - $(window).height();
      if(stop > 61 && stop < bottom){
        if(!main.hasClass('site-fix')){
          main.addClass('site-fix');
        }
        if(main.hasClass('site-fix-footer')){
          main.removeClass('site-fix-footer');
        }
      } else if(stop >= bottom) {
        if(!main.hasClass('site-fix-footer')){
          main.addClass('site-fix site-fix-footer');
        }
      } else {
        if(main.hasClass('site-fix')){
          main.removeClass('site-fix').removeClass('site-fix-footer');
        }
      }
      stop = null;
    };
    scroll();
    $(window).on('scroll', scroll);
  }();
  


  //手机设备的简单适配
  var treeMobile = $('.site-tree-mobile')
  ,shadeMobile = $('.site-mobile-shade')

  treeMobile.on('click', function(){
    $('body').addClass('site-mobile');
  });

  shadeMobile.on('click', function(){
    $('body').removeClass('site-mobile');
  });

  exports('global', {});
});