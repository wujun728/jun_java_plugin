var menuOffX=0	//菜单距连接文字最左端距离
var menuOffY=18	//菜单距连接文字顶端距离
var fo_shadows=new Array()
var linkset=new Array()
var IE4=document.all&&navigator.userAgent.indexOf("Opera")==-1
var netscape6=document.getElementById&&!document.all
var netscape4=document.layers

function showmenu(e,vmenu,mod){
	if (!document.all&&!document.getElementById&&!document.layers)
		return
	which=vmenu
	clearhidemenu()
	IE_clearshadow()
	menuobj=IE4? document.all.popmenu : netscape6? document.getElementById("popmenu") : netscape4? document.popmenu : ""
	menuobj.thestyle=(IE4||netscape6)? menuobj.style : menuobj
	
	if (IE4||netscape6)
		menuobj.innerHTML=which
	else{
		menuobj.document.write('<layer name="other" bgColor="#E6E6E6" width="165" onmouseover="clearhidemenu()" onmouseout="hidemenu()">'+which+'</layer>')
		menuobj.document.close()
	}
	menuobj.contentwidth=(IE4||netscape6)? menuobj.offsetWidth : menuobj.document.other.document.width
	menuobj.contentheight=(IE4||netscape6)? menuobj.offsetHeight : menuobj.document.other.document.height
	
	eventX=IE4? event.clientX : netscape6? e.clientX : e.x
	eventY=IE4? event.clientY : netscape6? e.clientY : e.y
	
	var rightedge=IE4? document.body.clientWidth-eventX : window.innerWidth-eventX
	var bottomedge=IE4? document.body.clientHeight-eventY : window.innerHeight-eventY
		if (rightedge<menuobj.contentwidth)
			menuobj.thestyle.left=IE4? document.body.scrollLeft+eventX-menuobj.contentwidth+menuOffX : netscape6? window.pageXOffset+eventX-menuobj.contentwidth : eventX-menuobj.contentwidth
		else
			menuobj.thestyle.left=IE4? IE_x(event.srcElement)+menuOffX : netscape6? window.pageXOffset+eventX : eventX
		
		if (bottomedge<menuobj.contentheight&&mod!=0)
			menuobj.thestyle.top=IE4? document.body.scrollTop+eventY-menuobj.contentheight-event.offsetY+menuOffY-23 : netscape6? window.pageYOffset+eventY-menuobj.contentheight-10 : eventY-menuobj.contentheight
		else
			menuobj.thestyle.top=IE4? IE_y(event.srcElement)+menuOffY : netscape6? window.pageYOffset+eventY+10 : eventY
	menuobj.thestyle.visibility="visible"
	IE_dropshadow(menuobj,"#999999",3)
	return false
}

function IE_y(e){  
	var t=e.offsetTop;  
	while(e=e.offsetParent){  
		t+=e.offsetTop;  
	}  
	return t;  
}  
function IE_x(e){  
	var l=e.offsetLeft;  
	while(e=e.offsetParent){  
		l+=e.offsetLeft;  
	}  
	return l;  
}  
function IE_dropshadow(el, color, size) {
	var i;
	for (i=size; i>0; i--){
		var rect = document.createElement('div');
		var rs = rect.style
		rs.position = 'absolute';
		rs.left = (el.style.posLeft + i) + 'px';
		rs.top = (el.style.posTop + i) + 'px';
		rs.width = el.offsetWidth + 'px';
		rs.height = el.offsetHeight + 'px';
		rs.zIndex = el.style.zIndex - i;
		rs.backgroundColor = color;
		var opacity = 1 - i / (i + 1);
		rs.filter = 'alpha(opacity=' + (100 * opacity) + ')';
		fo_shadows[fo_shadows.length] = rect;
	}
}
function IE_clearshadow(){
	for(var i=0;i<fo_shadows.length;i++){
		if (fo_shadows[i])
			fo_shadows[i].style.display="none"
	}
	fo_shadows=new Array();
}
function hidemenu(){
	if (window.menuobj)
		menuobj.thestyle.visibility=(IE4||netscape6)? "hidden" : "hide"
	IE_clearshadow()
}
function dynamichide(e){
	if (IE4&&!menuobj.contains(e.toElement))
		hidemenu()
	else if (netscape6&&e.currentTarget!= e.relatedTarget&& !contains_netscape6(e.currentTarget, e.relatedTarget))
		hidemenu()
}
function delayhidemenu(){
	if (IE4||netscape6||netscape4)
		delayhide=setTimeout("hidemenu()",500)
}
function clearhidemenu(){
	if (window.delayhide)
		clearTimeout(delayhide)
}
function highlightmenu(e,state){
	if (document.all)
		source_el=event.srcElement
	else if (document.getElementById)
		source_el=e.target
	if (source_el.className=="menuitems"){
		source_el.id=(state=="on")? "mouseoverstyle" : ""
	}
	else{
		while(source_el.id!="popmenu"){
			source_el=document.getElementById? source_el.parentNode : source_el.parentElement
			if (source_el.className=="menuitems"){
				source_el.id=(state=="on")? "mouseoverstyle" : ""
			}
		}
	}
}
//设置菜单背景
function overbg(tdbg){
tdbg.style.background='url(Images/item_over.gif)'
tdbg.style.border=' #9CA6C6 1px solid'
}
function outbg(tdbg){
tdbg.style.background='url(Images/item_out.gif)'
tdbg.style.border=''

}
var sysmenu='<table width=300 height=100><tr><td id=library onMouseOver=overbg(library) onMouseOut=outbg(library)>图书馆信息'+document.all.content+'</td></tr>\
</table>'
var readermenu='<table width=90><tr><td id=readerType onMouseOver=overbg(readerType) onMouseOut=outbg(readerType)><a href=readerType.do?action=readerTypeQuery>读者类型管理</a></td></tr>\
<tr><td id=reader onMouseOver=overbg(reader) onMouseOut=outbg(reader)><a href=reader.do?action=readerQuery>读者档案管理</a></td></tr>\
</table>'
var bookmenu='<table width=90><tr><td id=bookType onMouseOver=overbg(bookType) onMouseOut=outbg(bookType)><a href=bookType.do?action=bookTypeQuery>图书类型设置</a></td></tr>\
<tr><td id=book onMouseOver=overbg(book) onMouseOut=outbg(book)><a href=book.do?action=bookQuery>图书档案管理</a></td></tr>\
</table>'
var borrowmenu='<table width=60><tr><td id=Borrow onMouseOver=overbg(Borrow) onMouseOut=outbg(Borrow)><a href=bookBorrow.jsp>图书借阅</a></td></tr>\
<tr><td id=renew onMouseOver=overbg(renew) onMouseOut=outbg(renew)><a href=bookRenew.jsp>图书续借</a></td></tr>\
<tr><td id=giveback onMouseOver=overbg(giveback) onMouseOut=outbg(giveback)><a href=bookBack.jsp>图书归还</a></td></tr>\
</table>'
var querymenu='<table width=90><tr><td id=bookQuery onMouseOver=overbg(bookQuery) onMouseOut=outbg(bookQuery)><a href=book.do?action=bookifQuery>图书档案查询</a></td></tr>\
<tr><td id=borrowQuery onMouseOver=overbg(borrowQuery) onMouseOut=outbg(borrowQuery)><a href=borrow.do?action=borrowQuery>图书借阅查询</a></td></tr>\
<tr><td id=givebackQuery onMouseOver=overbg(givebackQuery) onMouseOut=outbg(givebackQuery)><a href=borrow.do?action=Bremind>借阅到期提醒</a></td></tr>\
</table>'
var sortmenu='<table width=100><tr><td id=readerSort onMouseOver=overbg(readerSort) onMouseOut=outbg(readerSort)><a href=borrow.do?action=readerBorrowSort>读者借阅排行榜</a></td></tr>\
<tr><td id=bookSort onMouseOver=overbg(bookSort) onMouseOut=outbg(bookSort)><a href=borrow.do?action=bookBorrowSort>图书借阅排行榜</a></td></tr>\
</table>'