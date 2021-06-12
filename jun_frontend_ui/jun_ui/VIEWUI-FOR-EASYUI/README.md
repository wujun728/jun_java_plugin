新鲜出炉基于EasyUI开发的「VIEWUI FOR EASYUI 管理系统UI界面」，分开源基础版和商业版；可应用于各种企业级管理系统、和非企业级管理系统、OA、ERP、CRM等等，欢迎意见或加入我们共同发展，														
大佬请轻拍：https://github.com/View-UI/VIEWUI-FOR-EASYUI											
 VIEWUI FOR EASYUI使用了EasyUI 1.5.1正式版、font awesome图标库、jQuery 2.11、echartsjs、HTML、HTML5、DIV、CSS、CSS3等技术，全新UI定制、组件UI美化、拓展。 有空点个Star 鼓励下哦。
 



一、开源版UI

视图仪表板
[![Example](https://github.com/View-UI/VIEWUI-FOR-EASYUI/blob/master/doc/VIEW_UI_EASYUI.png)](https://github.com/View-UI/VIEWUI-FOR-EASYUI/blob/master/doc/VIEW_UI_EASYUI.png)

二、商业版UI                                                                              、、 

视图仪表板                                                                               
[![Example](https://github.com/View-UI/VIEWUI-FOR-EASYUI/blob/master/doc/VIEW_UI_EASYUI2.png)](https://github.com/View-UI/VIEWUI-FOR-EASYUI/blob/master/doc/VIEW_UI_EASYUI2.png)
[![Example](https://github.com/View-UI/VIEWUI-FOR-EASYUI/blob/master/doc/VIEW_UI_EASYUI1.png)](https://github.com/View-UI/VIEWUI-FOR-EASYUI/blob/master/doc/VIEW_UI_EASYUI1.png)

更多界面：                                                                               
界面一：http://www.uedna.com/user/33963/                                                                                
界面二：http://www.zcool.com.cn/work/ZMjc3MTI4MDQ=.html

更多模板                                                                               
https://shop155629335.taobao.com/?spm=a230r.7195193.1997079397.2.diL9ud                                                                

了解更多                                                                        						       
[![Example](https://github.com/View-UI/VIEWUI-FOR-EASYUI/blob/master/assets/default/images/hc-code.jpg)](https://github.com/View-UI/VIEWUI-FOR-EASYUI/blob/master/assets/default/images/hc-code.jpg)

可淘宝搜索“店铺”——“酷设网”查看更多模板组件												


技术交流Q群：149663025                                                                               
微信公众平台：SoSoIT                                                                               

栏目分类：                                                                               
首页 驾驶舱、视图、仪表板

案例 弹窗表单一、弹窗表单二、创新表单一、创新表单二、一列表单、一列面板表单、两列表单、两列面板表单、组合案例、成功案例

插件                                                                                
基础：加载、分页、进度条、搜索框、拖拽、调整大小， 
布局：面板、布局、选项卡、手风琴； 
表单：下拉框、微调、滑动条、提示、列表组、树、组合树、日历、日期框、验证框， 
窗口：模态框、弹出框，表格：编辑表格、树网络表格、组合表格、分页表格

组件                                                                                
表单：按钮、表格、文本框、文件框、下拉框、标签、单多选、菜单， 
元素：SVG图标库、提示条

拓展 机构人员部门插件、敏捷看板面板

文档 文档管理原型、我的分享原型

管理                                                                                
管理：菜单管理、用户设置、角色管理、系统日志， 
特性：日志、定制、购买、更多

其他 界面：登录界面、404、500等等

<!DOCTYPE html>
<html lang="en">
<head id="Head1">
<meta charset="utf-8">
<meta content="width=device-width, initial-scale=1.0" name="viewport" />
<meta http-equiv="x-ua-compatible" content="ie=edge">

<title>VIEW_UI_EASYUI</title>
<meta name="KEYWords" contect="VIEWUI,VIEW_UI_EASYUI,EasyUI,后台管理系统,酷设网">
<meta name="description" contect="viewUI基于EasyUI定制的主题皮肤">
<meta name="author" contect="djcbpl@163.com">
<meta property="og:title" content="EasyUI">
<meta property="og:description" content="HTML, CSS, JS">

<!-- Meta -->

<link rel="Bookmark" href="assets/default/images/logoIco.ico" />
<link rel="Shortcut Icon" href="assets/default/images/logoIco.ico" />
<link href="assets/css/reset.css" rel="stylesheet" type="text/css" />
<link href="assets/js/themes/default/easyui.css" rel="stylesheet" type="text/css" />
<link href="assets/css/font-awesome.min.css" rel="stylesheet" type="text/css" />
<link href="assets/css/layout.css" rel="stylesheet" type="text/css" />


</head>
<body class="easyui-layout vui-easyui" scroll="no">
<noscript>
    <div class="bowerPrompt" class="bowerPrompt">
        <img src="assets/images/noscript.gif" alt='抱歉，请开启脚本支持！' />
    </div>
</noscript>
<!-- 头部 -->
<div data-options="region:'north',split:false,border:false,border:false" class="viewui-navheader">
	<!-- header start -->
	<div class="sys-logo">
		<a href="javascript:;" class="logoicon">logo图标</a>
		<a href="javascript:;" class="logo_title">logo名称</a>
		<a class="line"></a>
		<a href="javascript:;" class="e">logo副标题</a>
	</div>
	<!-- 菜单横栏 -->
	<ul class="viewui-navmenu"></ul>
	<div class="viewui-user">
        <div class="user-photo">
            <i class="fa fa-user-circle-o"></i>
        </div>
        <h4 class="user-name ellipsis">Admin</h4>
        <i class="fa fa-angle-down xiala"></i>

        <div class="viewui-userdrop-down">
            <ul class="user-opt">
              <li>
                <a href="javascript:;">
                    <i class="fa fa-user"></i>
                    <span class="opt-name">用户信息</span>
                </a>
              </li>
              <li>
                	<a href="https://shop155629335.taobao.com/?spm=a230r.7195193.1997079397.2.diL9ud" target="_blank">
                    <i class="fa fa-cart-plus"></i>
                    <span class="opt-name">商城购买</span>
                </a>
              </li>
              <li class="modify-pwd">
                    <a href="javascript:;" id="editpass">
                        <i class="fa fa-edit"></i>
                        <span class="opt-name">修改密码</span>
                    </a>
              </li>
              <li class="logout">
                    <a href="javascript:;" id="loginOut">
                        <i class="fa fa-power-off"></i>
                        <span class="opt-name">退出</span>
                    </a>
              </li>
            </ul>
        </div>
    </div>
    <div class="viewui-notice">
		<i class="fa fa-volume-up"></i>
		<div class="notice-box ellipsis" onmouseout="marqueeInterval[0]=setInterval('startMarquee()',marqueeDelay)" onmouseover="clearInterval(marqueeInterval[0])">
		</div>
		<div class="notice-opt">
			<a href="javascript:;" class="fa fa-caret-up"></a>
			<a href="javascript:;" class="fa fa-caret-down"></a>
		</div>
    </div>
</div>
<!-- // 头部 -->

<!-- 版权 -->
<div data-options="region:'south',split:false,border:false" class="copyright">
    <div class="footer">
        <span class="pull-left"> 微信平台：SoSoIT，交流Q群：149663025，客服邮箱：<a href="mailto:djcbpl@163.com?subject=咨询：body=源地址：">djcbpl@163.com</a>  &copy; Copyright ©2016 ~ <font id="timeYear">2019</font>. View UI. All Rights Reserved.</span>
        <span class="pull-right">
            <a href="javascript:;"><i class="fa fa-download"></i> 下载管理</a>
            <a href="javascript:;"><i class="fa fa-volume-up"></i> 消息</a>
        </span>
    </div>
</div>
<!-- // 版权 -->
<!-- 左侧菜单 -->
<div data-options="region:'west',hide:true,split:false,border:false" title="导航菜单" class="LeftMenu" id="west">
    <div id="nav" class="easyui-accordion" data-options="fit:true,border:false"></div>
</div>
<!-- // 左侧菜单 -->

<!-- home -->
<div data-options="region:'center'" id="mainPanle" class="home-panel">
	<div id="layout_center_plan" class="easyui-panel"  data-options="fit:true,style:'{overflow:hidden}',closed:false,closable:true,
	tools:[{
				iconCls:'refresh-panel fa fa-refresh ',
				handler:function(){firstrefresh()}
			}]"
	 style="overflow:hidden">
	</div>

</div>
<!-- // home -->

<!--修改密码窗口-->
<div data-options="collapsible:false,minimizable:false,maximizable:false" id="updatePwd" class="easyui-window updatePwd" title="修改密码">
    <div class="row"> 
      <label for="txtNewPass">新密码：</label>   
      <input class="easyui-validatebox txt01" id="txtNewPass" type="Password" name="name" />   
    </div>   
    <div class="row">   
      <label for="txtRePass">确认密码:</label>   
      <input class="easyui-validatebox txt01" id="txtRePass" type="Password" name="Password" />
    </div>
    <div data-options="region:'south',border:false" class="pwdbtn">
        <a id="btnEp" class="easyui-linkbutton " href="javascript:;" >确定</a> 
        <a id="btnCancel" class="easyui-linkbutton btnDefault" href="javascript:;">取消</a>
    </div>
</div>


<script src="assets/js/jquery2.1.1.js" type="text/javascript"></script>
<script src="assets/js/jquery.easyui.min.js" type="text/javascript"></script>
<script src='assets/js/index2.js' type="text/javascript"></script>
<script src='assets/js/system.menu2.js' type="text/javascript"></script>
<script type="text/javascript">


//绑定 div 的鼠标事件
$('.navmenu-item a').click(function(){
  $('.navmenu-item a').removeClass("active");//清空已经选择的元素
  $(this).addClass("active");
});
    var marqueeContent= [];   //滚动主题
            
    marqueeContent[0]='<a href="javascript:;" class="notice-item ellipsis" target="_blank">新版系统界面正式上线</a>';
    marqueeContent[1]='<a href="javascript:;" class="notice-item ellipsis" target="_blank">欢迎访问淘宝商城酷设设计</a>';
    marqueeContent[2]='<a href="javascript:;" class="notice-item ellipsis" target="_blank">新版上线优惠多多</a>';
    marqueeContent[3]='<a href="javascript:;" class="notice-item ellipsis" target="_blank">主题定制开发咨询</a>';
    marqueeContent[4]='<a href="javascript:;" class="notice-item ellipsis" target="_blank">商城购买该主题呢!</a>';

    var marqueeInterval=[];  //定义一些常用而且要经常用到的变量
    var marqueeId=0;
    var marqueeDelay=4000;
    var marqueeHeight=20;
    function initMarquee() {
     var str=marqueeContent[0];
     $('.notice-box').html('<div>'+str+'</div>');
     marqueeBox = $('.notice-box')[0];
     marqueeId++;
     marqueeInterval[0]=setInterval(startMarquee,marqueeDelay);
     }
    function startMarquee() {
     var str=marqueeContent[marqueeId];
      marqueeId++;
     if(marqueeId>=marqueeContent.length) marqueeId=0;
     if(marqueeBox.childNodes.length==1) {
      var nextLine=document.createElement('DIV');
      nextLine.innerHTML=str;
      marqueeBox.appendChild(nextLine);
      }
     else {
      marqueeBox.childNodes[0].innerHTML=str;
      marqueeBox.appendChild(marqueeBox.childNodes[0]);
      marqueeBox.scrollTop=0;
      }
     clearInterval(marqueeInterval[1]);
     marqueeInterval[1]=setInterval(scrollMarquee,10);
     }
    function scrollMarquee() {
     marqueeBox.scrollTop++;
     if(marqueeBox.scrollTop%marqueeHeight==marqueeHeight){
      clearInterval(marqueeInterval[1]);
      }
     }
    initMarquee();

</script>
</body>
</html>


<script type="text/javascript">
var _menus_oneLeve=[{"menuid":"0","menuname":"首页","icon":"fa-home"},{"menuid":"1","menuname":"成功案例","icon":"fa-trophy"},{"menuid":"2","menuname":"特效组件","icon":"fa-inbox"},{"menuid":"3","menuname":"文档帮助","icon":"fa-suitcase"},{"menuid":"4","menuname":"系统管理","icon":"fa-dropbox"}];
var _menus=[
    {"menuid":"00","icon":"fa-trophy","menuname":"成功案例管理",parentMenu:'0',
        "menus":[{"menuid":"000","menuname":"视图","icon":"fa-dashboard","url":"kanban.html"},
                {"menuid":"001","menuname":"仪表盘","icon":"fa-delicious","url":"dashboard.html"}
            ]},{
     "menuid":"01","icon":"fa-television","menuname":"表单成功案例",parentMenu:'0',
        "menus":[{"menuid":"010","menuname":"代办事项","icon":"fa-tty","url":"https://item.taobao.com/item.htm?id=545823027227"},
                {"menuid":"011","menuname":"公告通知","icon":"fa-volume-up","url":""}
            ]
    },
	{"menuid":"11","icon":"fa-trophy","menuname":"成功案例管理",parentMenu:'1',
		"menus":[{"menuid":"110","menuname":"弹窗表单一","icon":"fa-window-restore","url":"demo/form-success-popup1.html"},
                {"menuid":"111","menuname":"弹窗表单二","icon":"fa-window-restore","url":"demo/form-success-popup2.html"},
                {"menuid":"112","menuname":"创新表单一","icon":"fa-window-maximize","url":"demo/form-success1.html"},
                {"menuid":"113","menuname":"创新表单二","icon":"fa-window-maximize","url":"demo/form-success2.html"},
                {"menuid":"114","menuname":"一列表单","icon":"fa-align-center","url":"demo/form1.html"},
                {"menuid":"115","menuname":"一列面板表单","icon":"fa-align-center","url":"demo/form2.html"},
                {"menuid":"116","menuname":"两列表单","icon":"fa-columns","url":"demo/form2-column.html"},
                {"menuid":"117","menuname":"两列面板表单","icon":"fa-columns","url":"demo/form2-column2.html"},
			]},{
     "menuid":"12","icon":"fa-television","menuname":"表单成功案例",parentMenu:'1',
        "menus":[{"menuid":"120","menuname":"网站作品案例","icon":"fa-globe","url":"https://item.taobao.com/item.htm?id=545823027227"},
                {"menuid":"121","menuname":"设计作品","icon":"fa-laptop","url":"http://www.uimaker.com/member/index.php?uid=poya"},
                {"menuid":"122","menuname":"成功作品","icon":"fa-laptop","url":"http://www.uedna.com/user/33963/"},
                {"menuid":"122","menuname":"金典案例","icon":"fa-laptop","url":"http://www.zcool.com.cn/u/16062070"}
            ]
    },
	{"menuid":"21","icon":"fa-plug","menuname":"系统插件管理",parentMenu:'2',
		"menus":[
            {"menuid":"210","menuname":"下拉按钮","icon":"fa-toggle-down","url":"demo/msgTip.html"},
            {"menuid":"211","menuname":"提示条","icon":"fa-puzzle-piece","url":"demo/msgTip.html"},
            {"menuid":"212","menuname":"模态框","icon":"fa-desktop","url":"demo/msgTip.html"},
            {"menuid":"213","menuname":"弹出框","icon":"fa-window-restore","url":"demo/msgTip.html"},
            {"menuid":"214","menuname":"日历","icon":"fa-calendar","url":"demo/msgTip.html"},
            {"menuid":"215","menuname":"下拉框","icon":"fa-chevron-circle-down","url":"demo/msgTip.html"},
            {"menuid":"216","menuname":"选项卡","icon":"fa-laptop","url":"demo/msgTip.html"},
            {"menuid":"217","menuname":"按钮","icon":"fa-square","url":"demo/msgTip.html"},
            {"menuid":"218","menuname":"表格","icon":"fa-th","url":"demo/msgTip.html"},
            {"menuid":"219","menuname":"EasyUI表格","icon":"fa-th","url":"demo/msgTip.html"}
		]
	},
	{"menuid":"22","icon":"fa-cubes","menuname":"系统组件管理",parentMenu:'2',
		"menus":[{"menuid":"220","menuname":"SVG图标库","icon":"fa-dot-circle-o","url":"demo/inonSvg.html"},
			{"menuid":"221","menuname":"输入框类","icon":"fa-sticky-note","url":"demo/msgTip.html"},
            {"menuid":"222","menuname":"手风琴","icon":"fa-window-minimize","url":"demo/msgTip.html"},
            {"menuid":"223","menuname":"分页","icon":"fa-sort-numeric-asc","url":"demo/msgTip.html"},
            {"menuid":"224","menuname":"标签","icon":"fa-bookmark","url":"demo/msgTip.html"},
            {"menuid":"225","menuname":"缩略图","icon":"fa-image","url":"demo/msgTip.html"},
            {"menuid":"226","menuname":"警告框","icon":"fa-warning","url":"demo/msgTip.html"},
            {"menuid":"227","menuname":"进度条","icon":"fa-sliders","url":"demo/msgTip.html"},
            {"menuid":"228","menuname":"列表组","icon":"fa-navicon","url":"demo/msgTip.html"},
            {"menuid":"229","menuname":"面版","icon":"fa-th-large","url":"demo/msgTip.html"},
            {"menuid":"2201","menuname":"树","icon":"fa-sitemap","url":"comp/msgTip.html"}
		]
	},
    {"menuid":"31","icon":"fa-suitcase","menuname":"文档管理",parentMenu:'3',
    	"menus":[{"menuid":"311","menuname":"全部文档","icon":"fa-inbox","url":"demo/msgTip.html"},
    			{"menuid":"312","menuname":"我的分享","icon":"fa-share-alt-square","url":"demo/msgTip.html"}
    		]
    	},
	{"menuid":"41","icon":"fa-dashboard","menuname":"系统仪表板",parentMenu:'4',
		"menus":[
			{"menuid":"411","menuname":"系统管理","icon":"fa-id-card","url":"demo/resource.html"},
			{"menuid":"412","menuname":"表单示例","icon":"fa-users","url":"demo/msgTip.html"},
			{"menuid":"413","menuname":"角色管理","icon":"fa-address-card","url":"demo/msgTip.html"},
			{"menuid":"414","menuname":"权限设置","icon":"fa-user-plus","url":"demo/msgTip.html"},
			{"menuid":"415","menuname":"升级日志","icon":"fa-list","url":"https://blog.csdn.net/DJCBPL/article/details/80281869"}
		]
	}
		
];

    //设置登录窗口
    function openPwd() {$('#updatePwd').window({title: '修改密码', width: 300, modal: true, shadow: true, closed: true, height: 160, resizable:false }); }
    //关闭登录窗口
    function closePwd() {$('#updatePwd').window('close');}

    //修改密码
    function serverLogin() {
        var $newpass = $('#txtNewPass');
        var $rePass = $('#txtRePass');

        if ($newpass.val() == '') {
            msgShow('系统提示', '请输入密码！', 'admin');
            return false;
        }
        if ($rePass.val() == '') {
            msgShow('系统提示', '请在一次输入密码！', 'admin');
            return false;
        }

        if ($newpass.val() != $rePass.val()) {
            msgShow('系统提示', '两次密码不一至！请重新输入', 'admin');
            return false;
        }

        $.post('/ajax=' + $newpass.val(), function(msg) {
            msgShow('系统提示', '恭喜，密码修改成功！<br>您的新密码为：' + msg, 'info');
            $newpass.val('');
            $rePass.val('');
            close();
        })
        
    }

    $(function() {
        openPwd();

        $('#editpass').click(function(){$('#updatePwd').window('open');});

        $('#btnEp').click(function(){serverLogin();});

		$('#btnCancel').click(function(){closePwd();});

        $('#loginOut').click(function() {
            $.messager.confirm('系统提示', '您确定要退出本次登录吗?', function(r) {

                if (r) {
                    location.href = 'login.html';
                }
            });
        })
    });

$(function(){var mydate = new Date(); var tm=mydate.getFullYear(); $("#timeYear").text(tm); });
</script>
