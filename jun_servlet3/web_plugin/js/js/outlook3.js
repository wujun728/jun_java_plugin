$(function() {
    tabClose();
    tabCloseEven();

    $('#navMenu a').click(function() {
        $('#navMenu a').parent().removeClass('selected');
        $(this).parent().addClass('selected');
        var d = _menus[$(this).attr('name')];
        Clearnav();
        addNav(d);
        InitLeftMenu();
    });

    // 导航菜单绑定初始化
    $("#wnav").accordion({
        animate: false
    });

    var firstMenuName = $('#navMenu a:first').attr('name');
    addNav(_menus[firstMenuName]); //首次加载basic 左侧菜单
    InitLeftMenu();
});

function Clearnav() {
    var pp = $('#wnav').accordion('panels');

    $.each(pp, function(i, n) {
        if (n) {
            var t = n.panel('options').title;
            $('#wnav').accordion('remove', t);
        }
    });

    pp = $('#wnav').accordion('getSelected');
    while(pp){
    	var title = pp.panel('options').title;
        $('#wnav').accordion('remove', title);
        pp = $('#wnav').accordion('getSelected');
    }
    
}

function GetMenuList(data, menulist) {
    if (data.menus == null)
        return menulist;
    else {
        menulist += '<ul>';
        $.each(data.menus, function(i, sm) {
            if (sm.url != null) {
                menulist += '<li ><a ref="' + sm.menuid + '" href="#" rel="/bescarcrm/'
					+ sm.url + '" ><span class="nav">' + sm.menuname
					+ '</span></a>'
            }
            else {
                menulist += '<li state="closed"><span class="nav">' + sm.menuname + '</span>'
            }
            menulist = GetMenuList(sm, menulist);
        })
        menulist += '</ul>';
    }
    return menulist;
}
//左侧导航加载
function addNav(data) {	
    $.each(data, function(i, sm) {
        var menulist1 = "";
        menulist1 = GetMenuList(sm, menulist1);
        menulist1 = "<ul id='tt1' class='easyui-tree' animate='true' dnd='true'>" + menulist1.substring(4); 
        
        $('#wnav').accordion('add', {
            title: sm.menuname,
            content: menulist1,
            iconCls: 'icon ' + sm.icon
        });

    });

    var pp = $('#wnav').accordion('panels');
    var t = pp[0].panel('options').title;
    $('#wnav').accordion('select', t);

}

// 初始化左侧
function InitLeftMenu() {

    hoverMenuItem();

    $('#wnav li a').live('click', function() {
        var tabTitle = $(this).children('.nav').text();

        var url = $(this).attr("rel");
        var menuid = $(this).attr("ref");
        var icon = getIcon(menuid, icon);        
        addTab(tabTitle, url, icon);
        $('#wnav li div').removeClass("selected");
        $(this).parent().addClass("selected");
    });

}

/**
* 菜单项鼠标Hover
*/
function hoverMenuItem() {
    $(".easyui-accordion").find('a').hover(function() {
        $(this).parent().addClass("hover");
    }, function() {
        $(this).parent().removeClass("hover");
    });
}

// 获取左侧导航的图标Tab
function getIcon(menuid) {
    var icon = 'icon ';
    $.each(_menus, function(i, n) {
        $.each(n, function(j, o) {
            $.each(o.menus, function(k, m) {
                if (m.menuid == menuid) {
                    icon += m.icon;
                    return false;
                }
            });
        });
    });
    return icon;
}

function addTab(subtitle, url, icon) {	 
    if (!$('#tabs').tabs('exists', subtitle)) {
        //首先获取tabs个数
    	var tabPanels = $('#tabs').tabs('tabs');
    	if(tabPanels.length>5){
    		//关闭最前面的一个
    		 var title = tabPanels[1].panel('options').tab.text();
    	     $('#tabs').tabs('close', title);
    	}
    	$('#tabs').tabs('add', {
            title: subtitle,
            content: createFrame(url),
            closable: true,
            icon: icon
        });
    } else {
        $('#tabs').tabs('select', subtitle);
        $('#mm-tabupdate').click();
    }   
    tabClose();
}

function createFrame(url) {	
    var s = '<iframe scrolling="auto" frameborder="0"  src="' + url + '" style="width:100%;height:100%;"></iframe>';
    return s;
}

function tabClose() {
    /* 双击关闭TAB选项卡 */
    $(".tabs-inner").dblclick(function() {
        var subtitle = $(this).children(".tabs-closable").text();
        $('#tabs').tabs('close', subtitle);
    });
    /* 为选项卡绑定右键 */
    $(".tabs-inner").bind('contextmenu', function(e) {
        $('#mm').menu('show', {
            left: e.pageX,
            top: e.pageY
        });

        var subtitle = $(this).children(".tabs-closable").text();

        $('#mm').data("currtab", subtitle);
        $('#tabs').tabs('select', subtitle);
        return false;
    });
}
// 绑定右键菜单事件
function tabCloseEven() {
    // 刷新
    $('#mm-tabupdate').click(function() {
        var currTab = $('#tabs').tabs('getSelected');
        var url = $(currTab.panel('options').content).attr('src');
        $('#tabs').tabs('update', {
            tab: currTab,
            options: {
                content: createFrame(url)
            }
        });
    });
    // 关闭当前
    $('#mm-tabclose').click(function() {
        var currtab_title = $('#mm').data("currtab");
        $('#tabs').tabs('close', currtab_title);
    });
    // 全部关闭
    $('#mm-tabcloseall').click(function() {
        $('.tabs-inner span').each(function(i, n) {
            var t = $(n).text();
            $('#tabs').tabs('close', t);
        });
    });
    // 关闭除当前之外的TAB
    $('#mm-tabcloseother').click(function() {
        $('#mm-tabcloseright').click();
        $('#mm-tabcloseleft').click();
    });
    // 关闭当前右侧的TAB
    $('#mm-tabcloseright').click(function() {
        var nextall = $('.tabs-selected').nextAll();
        if (nextall.length == 0) {
            // msgShow('系统提示','后边没有啦~~','error');
            alert('后边没有啦~~');
            return false;
        }
        nextall.each(function(i, n) {
            var t = $('a:eq(0) span', $(n)).text();
            $('#tabs').tabs('close', t);
        });
        return false;
    });
    // 关闭当前左侧的TAB
    $('#mm-tabcloseleft').click(function() {
        var prevall = $('.tabs-selected').prevAll();
        if (prevall.length == 0) {
            alert('到头了，前边没有啦~~');
            return false;
        }
        prevall.each(function(i, n) {
            var t = $('a:eq(0) span', $(n)).text();
            $('#tabs').tabs('close', t);
        });
        return false;
    });

    // 退出
    $("#mm-exit").click(function() {
        $('#mm').menu('hide');
    });
}