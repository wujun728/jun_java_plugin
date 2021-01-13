(function(window){
	if(window.EventUtil){
		//ignoring duplicate execution
		return ;
	}
	EventUtil = {
		//给对象 element 添加 type 事件的监听
		addHandler: function(element, type, handler){
			if(element==window&& type=='resize'){
				if(ET&&ET.Utils){
					ET.Utils.windowResizeEvents.push(handler);
				}
			}
			
			if (element.addEventListener){
				element.addEventListener(type, handler, false);
			} else if (element.attachEvent){
				element.attachEvent("on" + type, handler);
			} else {
				element["on" + type] = handler;
			}
		},
		removeHandler: function(element, type, handler){
			if (element.removeEventListener){
				element.removeEventListener(type, handler, false);
			} else if (element.detachEvent){
				element.detachEvent("on" + type, handler);
			} else {
				element["on" + type] = null;
			}
		},
		//得到事件event 
	    getEvent: function(event){
				return event ? event : window.event;
		},
		//根据ID 获取dom
		$ID:function(id){
			return document.getElementById(id);
		},
		//得到消息的源
		getTarget: function(event){
			return event.target || event.srcElement;
		},
		
		getButton: function(event){
			if (document.implementation.hasFeature("MouseEvents", "2.0")){
				return event.button;
			} else {
				switch(event.button){
					case 0:
					case 1:
					case 3:
					case 5:
					case 7:
						return 0;
					case 2:
					case 6:
						return 2;
					case 4: return 1;
				}
			}
		},
    
		getCharCode: function(event){
			if (typeof event.charCode == "number"){
				return event.charCode;
			} else {
				return event.keyCode;
			}
		},
    
		getClipboardText: function(event){
			var clipboardData =  (event.clipboardData || window.clipboardData);
			return clipboardData.getData("text");
		},
		getRelatedTarget: function(event){
			if (event.relatedTarget){
				return event.relatedTarget;
			} else if (event.toElement){
				return event.toElement;
			} else if (event.fromElement){
				return event.fromElement;
			} else {
				return null;
			}
		},
		getWheelDelta: function(event){
			if (event.wheelDelta){
				return (EasyTrack.browser.opera && EasyTrack.browser.opera < 9.5 ? -event.wheelDelta : event.wheelDelta);
			} else {
				return -event.detail * 40;
			}
		},
		//阻止事件的默认行为
		preventDefault: function(event){
			if (event.preventDefault){
				event.preventDefault();
			} else {
				event.returnValue = false;
			}
		},
		setClipboardText: function(event, value){
			if (event.clipboardData){
				event.clipboardData.setData("text/plain", value);
			} else if (window.clipboardData){
				window.clipboardData.setData("text", value);
			}
		},
		//阻止事件冒泡
		stopPropagation: function(event){
			if (event.stopPropagation){
				event.stopPropagation();
			} else {
				event.cancelBubble = true;
			}
		}
		
	}
})(window);

(function(window){
	
	if(window.CssUtil){
		return ;
	}
	CssUtil={
		css:function(obj,attr,value){
			 switch (arguments.length){
			 	case 2:
			 		 if (typeof arguments[1] == "object") { 
			 		 	   //批量设置属性
                        for (var i in attr) obj.style[i] = attr[i]
                    }else {    // 读取属性值
					
                        return window.getComputedStyle ? window.getComputedStyle(obj, null)[attr]: obj.currentStyle[attr] ;
                    }
                    break;
                case 3: 
                    //设置属性
                    obj.style[attr] = value;
                    break;
                default:
                    
			 }
			
		},
		hasClass:function(obj,cls){
			var className=obj.className||"";
			if(className.indexOf(cls)!=-1){
				return true;
			}
			return false;
		},
		addClass:function(obj,cls){
			  if (!this.hasClass(obj, cls)) obj.className += " " + cls;
			
		},
		removeClass:function(obj,cls){
			   if(this.hasClass(obj, cls)) {
			   	var className=obj.className||"";
                var reg = new RegExp('(\\s|^)' + cls + '(\\s|$)');
                obj.className = className.replace(reg, ' ');
            }
		}
	}
})(window);

(function(window){
	if(window.DomUtil){
		return ;
	}
	DomUtil={
		//根据名称 在context 下面查找节点
		findByName:function(context,name){
			//IE 8 以上，firefox ,chrome ,safari 都支持！！！
			if(context.querySelector){
				return context.querySelector("[name='"+name+"']");
			}else{
				var child=context.all;
				for(var i=0,j=child.length;i<j;i++){
					if(child[i].name==name){
						return child[i];
					}
				}
				return null;
				
			}
		}
		
	}
})(window);

(function(window){
	if(!window.EasyTrack){
		//ignoring duplicate execution
		EasyTrack={
			//subTypeClass 继承 superTypeClass
			inheritPrototype:function(subTypeClass,superTypeClass){
				function F(){};
				F.prototype=superTypeClass.prototype;
				var subType_prototype=new F();
				subTypeClass.prototype=subType_prototype;
			}
			,throttle:function(method,context){
				clearTimeout(method.timeID);
				var arg=Array.prototype.slice.call(arguments,2);
				method.timeID=setTimeout(function(){
					method.apply(context,arg);
				},50)
			}
			
				//日志
			,log:function(str){
				if(window.console){
					window.console.log(str);
				}
			}
			//批量设置样式
			,setStyle:function(div,styleObj){
				var style=div.style;
				for(key in styleObj){
					style[key]=styleObj[key];
				}
			}
			//重新加载dom 下面的javascript 标签
			,loadScript:function(obj){
				if(typeof obj=='string'){
					this.evalScripts(obj);
				}else{
					var scripts=obj.getElementsByTagName('script');
					if(scripts!=null){
						for(var i=0,j=scripts.length;i<j;i++){
							window.eval(scripts[i].innerHTML);
						}
					}
				}
			}
			,evalScripts:function (text){ 
			    var script=null;
			    var scripts=[]; 
			    var regexp = /<script[^>]*>([\s\S]*?)<\/script>/gi; 
			    while ((script = regexp.exec(text))) scripts.push(script[1]); 
			    scripts = scripts.join('\n'); 
			    if (scripts) (window.execScript) ? window.execScript(scripts) : window.setTimeout(scripts, 0); 
			}
			,browser:function(){
				var ua = navigator.userAgent;
				 var result = {
			        isStrict : document.compatMode == "CSS1Compat"
			        ,isGecko : /gecko/i.test(ua) && !/like gecko/i.test(ua)
			        ,isWebkit: /webkit/i.test(ua)
	   			 };
	   			 
			    switch (true) {
			        case /msie (\d+\.\d+)/i.test(ua) :
			            result.ie = document.documentMode || + RegExp['\x241'];
			            break;
			        case /chrome\/(\d+\.\d+)/i.test(ua) :
			            result.chrome = + RegExp['\x241'];
			            break;
			        case /(\d+\.\d)?(?:\.\d)?\s+safari\/?(\d+\.\d+)?/i.test(ua) && !/chrome/i.test(ua) :
			            result.safari = + (RegExp['\x241'] || RegExp['\x242']);
			            break;
			        case /firefox\/(\d+\.\d+)/i.test(ua) : 
			            result.firefox = + RegExp['\x241'];
			            break;
			        
			        case /opera(?:\/| )(\d+(?:\.\d+)?)(.+?(version\/(\d+(?:\.\d+)?)))?/i.test(ua) :
			            result.opera = + ( RegExp["\x244"] || RegExp["\x241"] );
			            break;
			    }
			    return result;
			}()	
		} ;
		
	}
})(window);

/**
 * 拖动插件
 */
EasyTrack.DragHelper=function(draghepler,config){
	'use_strict';
	this.draghepler =draghepler;
	this.config=config||{};
	var self=this;
	//在拖动开始时回调
	this.startDrag=this.config.startDrag||function(event){
	
	};
	//在拖动结束时回调
	this.endDrag=this.config.endDrag||function(event){
	
	};
	//在拖动中 执行
	this.moveDrag=this.config.moveDrag||function(evnt){
	
	};
	var _initDragEvent=function(event){
		 
			event=event||window.event;
			EventUtil.preventDefault(event);
			// EventUtil.stopPropagation(event);
			////在拖动开始时， 注册 禁止select监听
			EventUtil.addHandler(document.body,'selectstart',_selectStart);
			if(draghepler.setCapture){
				draghepler.setCapture();
			}else{
				window.captureEvents(Event.MOUSEMOVE|Event.MOUSEUP);
			}
			self.startDrag.call(self,event); //调用
		  EventUtil.addHandler(document,'mousemove',_moveEvent);
		  EventUtil.addHandler(document,'mouseup',_upEvent);
	}
	
	var _moveEvent=function(event){
		event=event||window.event;
		EventUtil.stopPropagation(event);
		EventUtil.preventDefault(event);
		emptySelect();
		self.moveDrag.call(self,event); //调用
	}
	var _upEvent=function(event){
		event=event||window.event;
		
		emptySelect();
		//注销document 上面绑定的事件
		EventUtil.removeHandler(document,'mousemove',_moveEvent);
		EventUtil.removeHandler(document,'mouseup',_upEvent);
		//在chrome 上面存在 拖动时有选择的效果，很不好看
		window.setTimeout( function(){
			EventUtil.removeHandler(document.body,'selectstart',_selectStart);
		},500);
		self.releaseCapture();
		self.endDrag.call(self,event); //调用
		//EventUtil.stopPropagation(event);
		EventUtil.preventDefault(event);
	};
	self._upEvent=_upEvent;
	self._initDragEvent=_initDragEvent;
	self._moveEvent=_moveEvent;
	//初始化化拖动
	EventUtil.addHandler(draghepler,'mousedown',_initDragEvent);
	var _selectStart=function(event){
		emptySelect();
		var event=EventUtil.getEvent(event);
		EventUtil.preventDefault(event);
	}
	//清空 选择的内容
	var emptySelect=function(){
		if("getSelection" in window){
			window.getSelection().removeAllRanges();   
		}else{
			document.selection.empty(); 
		}
	};
};
EasyTrack.DragHelper.prototype.releaseCapture=function(){
	var self=this;
	var draghepler=self.draghepler;
	if(draghepler.releaseCapture){
				//拖动释放
		draghepler.releaseCapture();
	}else if(window.releaseEvents){
		window.releaseEvents(Event.MOUSEMOVE|Event.MOUSEUP);
	}
};

EasyTrack.DataTableMove=function(tableID,config){
	//tableID 表格ID
	var table=document.getElementById(tableID);
	this.table=table;
	this.config=config||{};
	
	//表格移动的可变配置
	
	//1.getMoveRowsFun:function(row) row 指向表格的行 此方法返回true 表示该行将要被移动
	//2.noCheckFun:function() 在调移动方法时，发现没有被选择行的回调方法
	//3.afterMove:function(direction,row) 是在direction 方向上移动前，移动后触发的方法
	
	//moveEndRow moveStartRow
};
EasyTrack.DataTableMove.prototype.getMoveRows=function(){
	var self=this;
	var mathFun=self.config.getMoveRowsFun;
	if(mathFun==undefined){
		//默认的计算需要移动的行
		mathFun=function(row){
			return DomUtil.findByName(row,"id")!=null&&DomUtil.findByName(row,"id").checked;
		}
	}
	var table=self.table;
	var rows=table.rows;
	var result=[];
	var moveStartRow=self.config.moveStartRow||table.tHead.rows.length;
	var moveEndRow=self.config.moveEndRow||0;
	for(var i=moveStartRow,j=rows.length-moveEndRow;i<j;i++){
		var row=rows[i];
		var check=mathFun(row);
		if(check){
			result.push(row);
		}
	}
	return result;
};
//调整的方向 支持 top :顶部 ，up:上移 ，down:下移 
EasyTrack.DataTableMove.prototype.moveRow=function(direction){
	var self=this;
	var companyTable=self.config.companyTable;
	
	var table=self.table;
	var result=self.getMoveRows();
	if(result.length==0){
		if(typeof self.config.noMoveRowsFun=='function'){
			self.config.noMoveRowsFun.call(self);
		}
		return ;
	}
	var moveStartRow=self.config.moveStartRow||table.tHead.rows.length;
	var moveEndRow=self.config.moveEndRow||0;
	//调整后调用
	var after=function(_currentDirection){
		if( typeof self.config['afterMove']==='function'){
			self.config['afterMove'].call(self,_currentDirection,result);
		}
	}
	
	if(direction=='up'){
			var topIndex=moveStartRow; //表头的行数
			var row=result[0];
			if(row.rowIndex>topIndex){
				for(var i=0,j=result.length;i<j;i++){
					row=result[i];
					var index_1=row.rowIndex,index_2=row.rowIndex-1;
					self.swapRow(row,table.rows[row.rowIndex-1]);
					if(companyTable!=null){
						self.swapRow(companyTable.rows[index_1],companyTable.rows[index_2]);
					}
				}
			
			}
			after.call(self,direction);
	}else if(direction=='down'){
		var rows=table.rows;
		var row=result[result.length-1];
		if(row.rowIndex<rows.length-1-moveEndRow){
			for(var i=result.length-1;i>=0;i--){
				row=result[i];
				var index_1=row.rowIndex,index_2=row.rowIndex+1;
				self.swapRow(row,table.rows[row.rowIndex+1]);
				if(companyTable!=null){
					self.swapRow(companyTable.rows[index_1],companyTable.rows[index_2]);
				}
			}
		}
		after.call(self,direction);
	}else if(direction=='top'){
		var row=result[0];
		var topIndex=moveStartRow; //表头的行数
		if(row.rowIndex>topIndex){
			var topRow=null,_topRow=null,index=0;
			for (var i= result.length-1;i>=0;i--) {
				topRow=table.rows[topIndex],index=result[i].rowIndex,_topRow=companyTable!=null?companyTable.rows[topIndex]:null;
				topRow.parentNode.insertBefore(result[i],topRow);
				if(companyTable!=null){
					_topRow.parentNode.insertBefore(companyTable.rows[index],_topRow);
				}
			}
		}
		after.call(self,direction);
	}else if(direction=='bottom'){
		var rows=table.rows;
		var row = result[result.length - 1];
		if (row.rowIndex < rows.length - 1-moveEndRow) {
			var bottomIndex = rows.length - 1-moveEndRow;
			var bottomRow=rows[bottomIndex],index=0,_bottomRow=companyTable!=null?companyTable.rows[bottomIndex]:null;
			for (var i = 0,j= result.length; i<j;i++) {
				index=result[i].rowIndex;
				bottomRow.parentNode.insertBefore(result[i],bottomRow.nextSibling);
				if(companyTable!=null){
					_bottomRow.parentNode.insertBefore(companyTable.rows[index],_bottomRow.nextSibling);
				}
			}
		}
		after.call(self,direction);
	}
	
};
EasyTrack.DataTableMove.prototype.swapRow=function(node1,node2){
	//获取父结点
	var _parent1=node1.parentNode;
	var _parent2=node2.parentNode;
	//获取两个结点的相对位置
	var _t1=node1.nextSibling;
	var _t2=node2.nextSibling;
	//将node2插入到原来node1的位置
	if(_t1)_parent1.insertBefore(node2,_t1);
	else _parent1.appendChild(node2);
	
	//将node1插入到原来node2的位置
	if(_t2)_parent2.insertBefore(node1,_t2);
		else _parent2.appendChild(node1);
};

//表格树  组件, 主要是支持树的收缩 移动
EasyTrack.DataTreeTable=function(tableID,config){
	var table=document.getElementById(tableID);
	this.table=table;
	this.config=config||{};
	
	//TreeTable 必须的配置
	
	//tr 上面标识 树的层次的属性的名称
	this.levelAttribute=this.config.levelAttribute||'level';
	//固定的行 ，默认为表头的长度
	var fixedRows=this.config.fixedRows;
	if(typeof fixedRows=='undefined'){
		fixedRows=this.table.tHead.rows.length;
	}
	this.fixedRows=fixedRows;
	
	//标识TreeTable 的行是收缩还是close 的属性的名称 ,如果是true ,这是展开的，否则关闭
	this.expandAttribute=this.config.expandAttribute||'isOpen';
	
};
/**
 * 初始化 TreeTable 的状态
 */
EasyTrack.DataTreeTable.prototype.initRowState=function(){
	var self=this,table=self.table,rows=table.rows;
	var fixedRows=self.fixedRows;
	var levelAttribute=self.levelAttribute,expandAttribute=self.expandAttribute;
	var tempRow=null,tempExpandAttribute=null,isOpen=null,result=null;
	for(var i=fixedRows,j=rows.length;i<j;i++){
		tempRow=rows[i];
		tempExpandAttribute=tempRow.getAttribute(expandAttribute);
		isOpen=typeof tempExpandAttribute=='undefined'?true:tempExpandAttribute;
		if(isOpen==false||isOpen=='false'){
			self.closeRow(tempRow);
		}
	}
};

EasyTrack.DataTreeTable.prototype.expandRow=function(row){
	var self=this,table=self.table,rows=table.rows;
	var levelAttribute=self.levelAttribute,expandAttribute=self.expandAttribute;
	if(typeof row=='number'){
		row=this.table.rows[row];
	}
	var rowIndex=row.rowIndex;
	var level=parseInt(row.getAttribute(levelAttribute));
	var tempRow=null,tempLevel=0,tempExpandAttribute=null,isOpen=false,tempResult=0;
	var rows=this.table.rows;
	
	for(var i=rowIndex+1,j=rows.length;i<j;i++){
		tempRow=rows[i];
		tempLevel=parseInt(tempRow.getAttribute(levelAttribute));
		if(tempLevel==level+1){
			tempRow.style.display="";
		    tempExpandAttribute=tempRow.getAttribute(expandAttribute);
		    isOpen=typeof tempExpandAttribute=='undefined'?true:tempExpandAttribute;
			if(isOpen==true||isOpen=='true'){
				self.expandRow(tempRow);
			}else{
				i=self.closeRow(tempRow)-1;
			}
		}else if(tempLevel<=level){
			break;	
		}
	}
	row.setAttribute(expandAttribute,true);
};
EasyTrack.DataTreeTable.prototype.closeRow=function(row){
	var self=this,table=self.table,rows=table.rows;
	var levelAttribute=self.levelAttribute,expandAttribute=self.expandAttribute;
	if(typeof row=='number'){
		row=table.rows[row];
	}
	var rowIndex=row.rowIndex;
	var result=i;
	var level=parseInt(row.getAttribute(levelAttribute));
	var tempRow=null,tempLevel=0;
	for(var i=rowIndex+1,j=rows.length;i<j;i++){
		 tempRow=rows[i];
		 tempLevel=parseInt(tempRow.getAttribute(levelAttribute));
		if(tempLevel>level){
			tempRow.style.display="none";
		}else{
			result=i;
			break;
		}
	}
	row.setAttribute(expandAttribute,false);
	return result;
};

//移动 TreeTable 行row, 方向为direction ，支持down,bottom,up,top
EasyTrack.DataTreeTable.prototype.moveRow=function(row,direction){
	var direction=direction.toLowerCase();
	if(typeof row=='number'){
		row=this.table.rows[row];
	}
	this[direction+"Row"](row);
};
/**
 * 将 行row (或者rowIndex=row的行下移)
 */
EasyTrack.DataTreeTable.prototype.downRow=function(row){
	var self=this,table=self.table,rows=table.rows,topIndex=self.fixedRows; //表头位置
	var levelAttribute=self.levelAttribute;
	if(typeof row=='number'){
		row=rows[row];
	}
	var tbody=row.parentNode;
	var insertRow=null;
	//1.得到所有的孩子节点
	var rowIndex=row.rowIndex,childRow=null,childLevel=null;
	var level=parseInt(row.getAttribute(levelAttribute));
	var frag=document.createDocumentFragment();
	var swapFrag=document.createDocumentFragment();
	frag.appendChild(row); 
	for(;;){
		//向下查找得到所有的孩子节点
		childRow=rows[rowIndex];
		if(childRow!=null){
			childLevel=parseInt(childRow.getAttribute(levelAttribute));
			if(childLevel>level){
				frag.appendChild(childRow);
			}else{
				break;
			}
		}else{
			break;
		}
	}
	
	//向下 查找兄弟（包括孩子）
	var nbli=null, nbliLevel=0;
	var count=0;
	for(;;){
		nbli=rows[rowIndex];
		if(nbli!=null){
			nbliLevel=parseInt(nbli.getAttribute(levelAttribute));
			if(nbliLevel==level){
				count++;
				if(count!=2){
					swapFrag.appendChild(nbli);
				}else{
					insertRow=nbli;
					break;
				}
			}else if(nbliLevel>level){
				swapFrag.appendChild(nbli);
			}else if(nbliLevel<level){
				insertRow=nbli;
					break;
			}
		}else{
			insertRow=null;
			break;
		}
	}
	tbody.insertBefore(swapFrag,insertRow);
	tbody.insertBefore(frag,insertRow);
	
};
/**
 * 取别名 行下移
 */
EasyTrack.DataTreeTable.prototype.moveDown=EasyTrack.DataTreeTable.prototype.downRow;
/**
 * 将 行row (或者rowIndex=row的行)上移（跟上一个兄弟节点交换）
 */
EasyTrack.DataTreeTable.prototype.upRow=function(row){
	var self=this,table=self.table,rows=table.rows,topIndex=self.fixedRows; //表头位置
	var levelAttribute=self.levelAttribute;
	if(typeof row=='number'){
		row=rows[row];
	}
	var tbody=row.parentNode;
	var insertRow=null;
	//1.得到所有的孩子节点
	var rowIndex=row.rowIndex,childRow=null,childLevel=null;
	var level=parseInt(row.getAttribute(levelAttribute));
	
	var swapFrag=document.createDocumentFragment();
	//1.向上up 查找上一个兄弟 片段
	var sbli=null, upIndex=rowIndex-1,sbliLevel=0;
	for(var i=upIndex;i>topIndex-1;i--){
		//向上查找，得到 最近的兄弟（包括后代）
		sbli=rows[i];
		sbliLevel=parseInt(sbli.getAttribute(levelAttribute));
		if(sbliLevel>level){
			swapFrag.insertBefore(sbli,swapFrag.firstChild);
		}else if(sbliLevel==level){
			swapFrag.insertBefore(sbli,swapFrag.firstChild);
			break;
		}else if(sbliLevel<level){
			break;
		}
	}
	if(swapFrag.hasChildNodes()){
		var frag=document.createDocumentFragment();
		//必须重置rowIndex
		rowIndex=row.rowIndex;
		frag.appendChild(row); 
		for(;;){
			//向下查找得到所有的孩子节点
			childRow=rows[rowIndex];
			if(childRow!=null){
				childLevel=parseInt(childRow.getAttribute(levelAttribute));
				if(childLevel>level){
					frag.appendChild(childRow);
				}else{
					insertRow=childRow;
					break;
				}
			}else{
				break;
			}
		}
		//3.最后重新插入 
		tbody.insertBefore(frag,insertRow);
		tbody.insertBefore(swapFrag,insertRow);
	}
	
};
EasyTrack.DataTreeTable.prototype.moveUp=EasyTrack.DataTreeTable.prototype.upRow;
/**
 * 
 * 将row 所在行移动到同级节点的底部
 */
EasyTrack.DataTreeTable.prototype.bottomRow=function(row){
	var self=this,table=self.table,rows=table.rows,topIndex=self.fixedRows; //表头位置
	var levelAttribute=self.levelAttribute;
	if(typeof row=='number'){
		row=rows[row];
	}
	var tBody=row.parentNode;
	var  level=parseInt(row.getAttribute(levelAttribute));
	var rowIndex=row.rowIndex;
	var nextSblingRow=null;
	var tempRow=null,tempLevel=0, count=0;
	//count 记录有多少个孩子节点
	for(var i=rowIndex+1,j=rows.length;i<j;i++){
		tempRow=rows[i];
		tempLevel=parseInt(tempRow.getAttribute(levelAttribute));
		if(tempLevel<=level-1){
			//查找父节点的兄弟节点
			nextSblingRow=tempRow;
			break;
		}else{
			count++;
		}
	}
	
	if(count!=0){
		var frag=document.createDocumentFragment();
		frag.appendChild(row);
		var childRow=null,childLevel=0;
		for(var i=rowIndex;;){
			childRow=rows[i];
			if(childRow!=null){
				childLevel=parseInt(childRow.getAttribute(levelAttribute));
				if(childLevel>level){
					frag.appendChild(childRow);
				}else{
					break;
				}
			}else{
				break;
			}
			
		}
		tBody.insertBefore(frag,nextSblingRow);
	}
};
EasyTrack.DataTreeTable.prototype.moveBottom=EasyTrack.DataTreeTable.prototype.bottomRow;
/**
 * 将row 所在行移动到同级节点的顶部
 */
EasyTrack.DataTreeTable.prototype.topRow=function(row){
	var self=this,table=self.table,rows=table.rows,topIndex=self.fixedRows; //表头位置
	var levelAttribute=self.levelAttribute;
	if(typeof row=='number'){
		row=rows[row];
	}
	var tBody=row.parentNode;
	var  level=parseInt(row.getAttribute(levelAttribute));
	//1.向上查找上级行
	var rowIndex=row.rowIndex,childRow=null,childLevel=null;
	var parentRow=null,parentIndex=-1;
	var tempRow=null,tempLevel=0;
	for(var i=rowIndex-1;i>topIndex-1;i--){
		tempRow=rows[i];
		tempLevel=parseInt(tempRow.getAttribute(levelAttribute));
		if(tempLevel==level-1){
			parentRow=tempRow;
			parentIndex=i;
			break;
		}
	}
	
	
	//2如果没有上级行 ，或者是需要移动的行处于第一个孩子位置，则不执行移动
	if(parentRow!=null&&parentIndex+1!=rowIndex){
		var frag=document.createDocumentFragment();
		frag.appendChild(row); 
		for(;;){
			//向下查找得到所有的孩子节点
			childRow=rows[rowIndex];
			if(childRow!=null){
				childLevel=parseInt(childRow.getAttribute(levelAttribute));
				if(childLevel>level){
					frag.appendChild(childRow);
				}else{
					break;
				}
			}else{
				break;
			}
		}
		tBody.insertBefore(frag,rows[parentIndex+1]);
	}
};
EasyTrack.DataTreeTable.prototype.moveTop=EasyTrack.DataTreeTable.prototype.topRow;

EasyTrack.DataTable=function(tableID,config){
	'use_strict';
	var table=EventUtil.$ID(tableID);
	this.tableID=tableID;
	
	//以表格的第一行为参考，固定的列数
	this.fixedColumnCount=0;
	// 原来表格初始化前的外层div (这就是表格容器)
	this.container=null; //(relative )
	//初始化后存放原来表格
	this.body_tbody=null;   //这个是(absolute) 出现滚动条的地方
	
	//层次关系为 contaier>body_tbody>table 
	
	//contaier>body_theadContaier>body_thead>原来表格表头的克隆 body_theadTable
	this.body_theadContaier;//(absolute,必须保证width= body_tbody.clientWidth,否则可能盖住竖直滚动条)
	this.body_thead; //(width= body_tbody.scrollWith，这样才能做到表格支持百分比)//absolute 定位使body_thead里的表头跟body_tbody的表头重合
	this.body_theadTable;//固定的表头
	
	
	
	//如果有列固定，则有以下的内容
	this.fix_column ; //这是列固定需要最外层容器（absolute 它的宽度需要根据固定的列计算 ，同时高度要等于 body_tbody.clientHeight，防止盖住body_tbody 的水平滚动条）
	this.column_tbody;//(absolute)//contaier>fix_column>column_tbody
	this.column_thead;//(absolute)//contaier>fix_column>column_thead
	this.column_theadTable ;//column_thead>column_theadTable;
	this.column_tbodyTable ;//原始表格整理的克隆，注意不能用cloneNode(false),这个很影响性能,只能用table.outerHTML
	this.column_Seperator;//分割线（需要计算分割线的位置 left ,height ）
	
	
	this.container=table.parentNode;
	this.table=table;
	//config 是初始化的第二参数，是一个object对象
	this.config=config||{};
	this.autoHeight=this.config.autoHeight!=null?this.config.autoHeight:true; 
	//如果是自动增长高度，则忽略外层的container 的高度
	this.initialTable();
	
	/**
	 * config 可用配置 
	 * 1.autoHeight
	 * 2. heightFun :function(clientHeight)
	 * 3. fixedColumnCount 固定多少列
	 * 4.containerStyle 容器样式style key style的属性，value 为该属性的值
	 * 5.wrapTable 是否自动创建容器
	 * 6.body_tbodyStyle 可滚动的容器的style
	 * 7.windowResizeAble 是否注册 resize 事件到window
	 * 8. mouseEvent 是否注册mouseover mouseoff 来高亮行
	 */
};
//初始化
EasyTrack.DataTable.prototype.initialTable=function(){
	var self=this;
	this.autoHeight=this.config.autoHeight!=null?this.config.autoHeight:true; 
	//如果是自动增长高度，则忽略外层的container 的高度
	var table=self.table;
	var  fixedColumnCount=0;
	//先确定需要固定的列数 默认为0 ，以表头的第一行为参考
	if(table.getAttribute("fixedColumnCount")!=null){
		fixedColumnCount=parseInt(table.getAttribute("fixedColumnCount"));
	}else{
		fixedColumnCount=self.config.fixedColumnCount;
	}
	self.fixedColumnCount=fixedColumnCount;
	var tableID=self.tableID;
	var container=null;
	if(self.config.wrapTable){
		container=document.createElement("div");
		table.parentNode.insertBefore(container,table);
		container.appendChild(table);
	}else{
		container=table.parentNode;
	}
	//容器必须修改 样式和布局
	self.container=container;
	EasyTrack.setStyle(container,{
		'overflowX':'hidden',
		'overflowY':'hidden'
	});
	if( CssUtil.css(container,'position')=='static'){
		container.style.position="relative";
	}
	if(self.config.containerStyle!=null){
		EasyTrack.setStyle(container,self.config.containerStyle);
	}
	
	
	//第一步:表格表头固定
	self.fixedHead();
	
	//第二步:实现列固定
	if(fixedColumnCount>0){
		self.table.style.backgroundColor=self.config.tableBackGrounColor||'white';
		self.fixedColumn(fixedColumnCount);
	}
	//self.dragColumn();
	//事件绑定
	self.bindEvent();
	if(self.config.resize!=false){
		self.resize(true);
	}
	
	
	if(this.config.lazyLoad&&this.config.checkLoad!=false){
//		if (document.addEventListener){   //非ie浏览器
//      		 document.addEventListener("DOMContentLoaded", function(){
//      		 	self.checkNeedloadLazyRow();
//      		 }, false);
//		} else if (document.attachEvent){   //ie浏览器
//  			 document.onreadystatechange=function(){
//		         if(this.readyState=='complete'){
//		         	self.checkNeedloadLazyRow();
//		         }
//  		   }
//		}
		ET.Utils.onloadEvent(function(){
			self.checkNeedloadLazyRow();
		},true);
	}
};
/**
固定表头 （可以重复调用）
**/
EasyTrack.DataTable.prototype.fixedHead=function(){
var self=this;
	var table=self.table;
	var container=self.container;
	var body_tbody=self.body_tbody;
	if(body_tbody==null){
	  body_tbody=container.cloneNode(false);
	  body_tbody.id=self.table.id+"body_tbody";
	  self.body_tbody=body_tbody;
		EasyTrack.setStyle(body_tbody,{
			'position':'absolute',
			'top':'0px',
			'left':'0px',
			'display':'block',
			'bottom':'0px',
			'width':'100%',
			'height':'100%',
			'overflowX':'auto',
			'overflowY':'auto'
		});
		
		container.appendChild(body_tbody);
		if(self.config.body_tbodyStyle!=null){
			EasyTrack.setStyle(body_tbody,self.config.body_tbodyStyle);
		}
		if(self.config.lazyLoad){
			var lazyLoadTable=self.config.lazyLoadTable;
			if(lazyLoadTable!=undefined){
				if(typeof lazyLoadTable=='string'){
					lazyLoadTable=document.getElementById(lazyLoadTable);
					self.hiddenTable=lazyLoadTable;
				}
				body_tbody.appendChild(table);
			}else{
				var realtable=table.cloneNode(false);
				realtable.appendChild(table.tHead);
				realtable.appendChild(table.tBodies[0]);
				table.id=table.id+"hidden";
				self.hiddenTable=table;
				self.table=realtable;
				table.style.display="none";
				table=realtable;
				body_tbody.appendChild(realtable);
			}
		}else{
			body_tbody.appendChild(table);
		}
	}
	var body_thead=self.body_thead;
	var body_theadContainer=self.body_theadContainer;
	if(body_thead==null){
		body_theadContainer=container.cloneNode(false);
		body_theadContainer.id=table.id+"body_theadContainer";
		EasyTrack.setStyle(body_theadContainer,{
			'position':'absolute',
			'display':'block',
			'height':'auto',
			'bottom':'auto',
			'left':'0px',
			'top':'0px',
			'overflowX':'hidden',
			'overflowY':'hidden'
		});
		self.body_theadContainer=body_theadContainer;
//		body_thead=document.createElement('div'); //body_head
//		body_thead.id=table.id+"body_thead";
//		EasyTrack.setStyle(body_thead,{
//			'marging':'0px',
//			'padding':'0px',
//			'width':table.offsetWidth+"px",
//			'overflowX':'hidden',
//			'overflowY':'hidden'
//		});
//		self.body_thead=body_thead;
//		body_theadContainer.appendChild(body_thead);
		container.appendChild(body_theadContainer);
	}
	
	var thead=table.tHead;
	var body_theadTable=self.body_theadTable;
	if(body_theadTable==null){
		//复制原来的表头到body_thead DIV 中
	    body_theadTable=table.cloneNode(false);
		body_theadTable.id=table.id+"body_theadTable";
		body_theadTable.appendChild(thead.cloneNode(true));
		body_theadContainer.appendChild(body_theadTable);
		self.body_theadTable=body_theadTable; //这就是body_tbody 滚动时固定不动的表头
	}
	
//	body_theadContainer.style.width=body_tbody.clientWidth+"px"; 
//	body_thead.style.width=body_tbody.scrollWidth+"px";
	
	//注意必须一直保持 body_theadContainer 等于body_tbody.clientWidth 因为该问题属于
};

//将表格的表头对齐
EasyTrack.DataTable.prototype.alignTableHead=function(){
    var self=this;
	var container=self.container;
	var body_theadTable=self.body_theadTable;
	var body_theadContainer=self.body_theadContainer;
	var body_tbody=self.body_tbody;
	var rect=self.table.getBoundingClientRect();
	var tableWidth=rect.right- rect.left;
	body_theadContainer.style.width=body_tbody.clientWidth+"px";
	
	self.body_theadTable.style.width=tableWidth+"px";
	var scrollLeft=body_tbody.scrollLeft;
	var scrollTop=body_tbody.scrollTop;
	body_theadContainer.scrollLeft=scrollLeft;
	
	if(self.fix_column!=undefined){
		self.column_tbodyTable.style.width=tableWidth+"px";
		self.column_theadTable.style.width=tableWidth+"px";
		self.fix_column.style.height=body_tbody.clientHeight+"px";
		self.caculateColumnWidth();
		self.column_tbody.scrollTop=scrollTop;
	}
};

/**
 * 事件绑定
 */
EasyTrack.DataTable.prototype.bindEvent=function(){
	var self=this;
	var body_tbody=self.body_tbody,
	table=self.table;
	thead=table.tHead,
	body_thead=self.body_thead;
	var _reisizeFun=function(){
		if(self.config.lazyLoad){
			self.checkNeedloadLazyRow();
		}
		self.resize(true);
	}
	self._reisizeFun=_reisizeFun;
	//if(self.config.windowResizeAble!=false){
		var isIE7=EasyTrack.browser.ie==7;
		var isIE8=EasyTrack.browser.ie==8;
		if(isIE7||isIE8){
			EventUtil.addHandler(window,'resize',function(){
				self.alignTableHead();
				EasyTrack.throttle(_reisizeFun,self);
			});
		}else{
			EventUtil.addHandler(window,'resize',function(event){
				_reisizeFun();
			})
		}
	//}
	var scrollEvent=self.config.scrollEvent;//增加页面定义的滚动条事件。
	var isFun=scrollEvent!=null&& typeof(scrollEvent)=='function';
	//竖直方向一起滚动
	//lazyLoad: false/true 是否在滚动的时候动态加载行
	self.currentScrollTop=0;
	EventUtil.addHandler(body_tbody,'scroll',function(event){
		event=event||window.event;
		var scrollLeft=body_tbody.scrollLeft,scrollTop=body_tbody.scrollTop;
		if(self.column_tbody!=null){ 
			//注意必须是引用
			self.column_tbody.scrollTop=scrollTop;
		}
		self.body_theadContainer.scrollLeft=scrollLeft;
		if(isFun){
			scrollEvent.call(self,body_tbody);//调用页面定义的滚动条事件。
		}
		if(self.config.lazyLoad){
			if(self.currentScrollTop!=scrollTop){
				window.clearTimeout(self.scrollTimeID);
				self.scrollTimeID=window.setTimeout(function(){
					self.loadLazyRow(50);
				},60)
				self.currentScrollTop=scrollTop;
			}
		}
	});
	
	if(self.column_tbody!=undefined){
		var eDir=0;
		EventUtil.addHandler(self.column_tbody, window.EasyTrack.browser.firefox?'DOMMouseScroll':'mousewheel',function(event){
			event=event||window.event;
			if(event.wheelDelta){ 
				eDir = event.wheelDelta/120;
			} else if(event.detail) { 
			 	eDir = -event.detail/3; 
			}
			eDir=eDir>0?-40:40;
			self.addScrollTop(eDir);
			var scrollTop=body_tbody.scrollTop;
			if(isFun){
				scrollEvent.call(self,body_tbody);//调用页面定义的滚动条事件。
			}
			if(self.config.lazyLoad){
				if(self.currentScrollTop!=scrollTop){
					window.clearTimeout(self.scrollTimeID);
					self.scrollTimeID=window.setTimeout(function(){
						self.loadLazyRow(50);
					},60)
					self.currentScrollTop=scrollTop;
				}
			}
		});
	}
	self.initMouseEvent();
	var container=self.container;
	var _currentMouseOver=false;
	EventUtil.addHandler(container,'mouseover',function(evnet){
		_currentMouseOver=true;
	});
	EventUtil.addHandler(container,'mouseout',function(evnet){
		_currentMouseOver=false;
	});
	
	keyTimeoutID=null;
	var keyEvent=function(event){
		if(_currentMouseOver){
			if(self.config.enableKeyScroll!=false){
				var e = event || window.event,
		        ek = e.keyCode || e.which;
		        var y=false;var x=false;
		        var value=self.config.keyScrollValue||20;
		        var changeValue=0;
		        switch (ek) {
		        	case 40:
		        		y=true;
		        		changeValue=value;
		        		break;
					case 38:
						y=true;
		        		changeValue=0-value;
		        		break;
		        	case 37:
		        		x=true;
		        		changeValue=0-value;
		        		break;
		        	case 39:
		        		x=true;
		        		changeValue=value;
		        		break;
		        	default:
		        		break;
		        }
		        window.clearTimeout(keyTimeoutID);
		       keyTimeoutID= window.setTimeout(function(){
		        	if(y){
		        		self.body_tbody.scrollTop+=changeValue;
		        	 }else if(x){
		         		self.body_tbody.scrollLeft+=changeValue;
		        	 }
		        	 self.alignTableHead();
		        },self.scrollTimelength)
			}
		}
	};
	EventUtil.addHandler(document,'keydown',keyEvent);
};
EasyTrack.DataTable.prototype.checkNeedloadLazyRow=function(){
	var self=this,table=self.table,rows=table.rows, body_tbody=self.body_tbody;
	var hiddenTable=self.hiddenTable;
	if(hiddenTable.length==0){
		return ;
	}
	var first=true;
	var timeID;
	var loadFun=function(){
		if(body_tbody.offsetWidth>body_tbody.clientWidth&&!first){
			return ;
		}
		first=false;
		flag=self.loadLazyRow();
		if(flag==false&&hiddenTable.rows.length==0){
			return ;
		}else{
			loadFun();
		}
	}
	loadFun();
};
EasyTrack.DataTable.prototype.loadLazyRow=function(loadRows){
	var self=this,config=self.config;
	var table=self.table;
	var hiddenTable=self.hiddenTable ;
	var hiddenTableRows=hiddenTable.rows;
	if(hiddenTableRows.length==0){
		return false ;
	}
	var firstTbody=table.tBodies[0];
	if(loadRows==undefined){
		loadRows=config.lazyBatchSize||100;
	}
	var fixedFrag,fixedTbody;
	var fixed=self.fixedColumnCount>0;
	if(fixed>0){
		fixedFrag=document.createDocumentFragment();
		fixedTbody=self.fixedTable.tBodies[0];
	}
	var count=0, frag=document.createDocumentFragment();
	var tempRow=null;
	while((tempRow=hiddenTableRows[0])!=undefined&&count<loadRows){
		frag.appendChild(tempRow);
		if(fixed>0){
			fixedFrag.appendChild(tempRow.cloneNode(true));
		}
		count++;
	}
	firstTbody.appendChild(frag);
	if(fixed>0){
		fixedTbody.appendChild(fixedFrag);
		self.column_tbodyTable.style.marginTop=-self.body_tbody.scrollTop+"px"; 
	}
	self.alignTableHead();
	return count>0;
	
};
EasyTrack.DataTable.prototype.initMouseEvent=function(){
	var self=this,table=self.table,tbody=self.table.tBodies[0],
	 column_tbodyTable=self.column_tbodyTable ;
	 var _window=window;
	 function getEventContextRow(e,currentTable){
		var targetNode=e.target||e.srcElement;
		var row=targetNode;
		try{
			if(row!=currentTable){
				while( row.parentNode.parentNode!=currentTable){
					row=row.parentNode;
				}
				return row.rowIndex;
			}else{
				return -1;
			}
		}catch(ex){
			return -1;
		}
		
	}
	function onRow(rowIndex){
		var row=table.rows[rowIndex];
		if(row){
			_window.on(row);
			if(self.column_tbodyTable!=null){
				_window.on(self.column_tbodyTable.rows[rowIndex]);
			}
		}
	};
	
	function offRow(rowIndex){
		var row=table.rows[rowIndex];
		if(row){
			_window.off(row);
			if(self.column_tbodyTable!=null){
				_window.off(self.column_tbodyTable.rows[rowIndex]);
			}
		}
	};
	var mouseover_processor = {
	    timeoutId: null,
	    //实际进行处理的方法
	    performProcessor: function(rowIndex) {
			if(lastMouserOverRowIndex!=null){
				offRow(lastMouserOverRowIndex);
			}
			lastMouserOverRowIndex=rowIndex;
			onRow(rowIndex);
	    },
	    process: function(rowIndex) {            //初始处理调用的方法
	        _window.clearTimeout(this.timeoutId);
	        var that = this;
	        this.timeoutId = _window.setTimeout(function(){        //创建定时器
	            that.performProcessor(rowIndex);
	        }, 40);
	    }
	};
	var mouseout_processor={
		timeoutId: null,
	    //实际进行处理的方法
	    performProcessor: function() {
	     if(lastMouserOverRowIndex!=null){
				offRow(lastMouserOverRowIndex);
			}
			lastMouserOverRowIndex=null;
	    },
	    process: function() {            //初始处理调用的方法
	        _window.clearTimeout(this.timeoutId);
	        var that = this;
	        this.timeoutId = _window.setTimeout(function(){        //创建定时器
	            that.performProcessor();
	        }, 40);
	    }
		
	}
	var lazyLoad=self.config.lazyLoad;
	var lastMouserOverRowIndex=null,rowIndex=null;
	if(self.config.mouseEvent!=false){
		EventUtil.addHandler(table,'mouseover',function(e){
			var event=e||window.event;
//			if(self.config.moseEventPropagation!=false){
//				EventUtil.stopPropagation(event);
//			}
			 rowIndex=getEventContextRow(event,table);
			 if(rowIndex>-1){
			 	if(lazyLoad){
			 		mouseover_processor.process(rowIndex);
			 	}else{
			 		mouseover_processor.performProcessor(rowIndex);
			 	}
			 }
		});
		EventUtil.addHandler(table,'mouseout',function(event){
			event=event||_window.event;
//			if(self.config.moseEventPropagation!=false){
//				EventUtil.stopPropagation(event);
//			}
			if(lazyLoad){
				mouseout_processor.process();
			}else{
				mouseout_processor.performProcessor();
			}
			
		});
		if(self.column_tbodyTable!=null){
			
			//var column_tbodyTable_body=column_tbodyTable.tBodies[0];
			EventUtil.addHandler(self.column_tbodyTable,'mouseover',function(event){
				event=event||_window.event;
//				if(self.config.moseEventPropagation!=false){
//					EventUtil.stopPropagation(event);
//				}
				 rowIndex=getEventContextRow(event,self.column_tbodyTable);
				if(rowIndex>-1){
			 		if(lazyLoad){
			 			mouseover_processor.process(rowIndex);
				 	}else{
				 		mouseover_processor.performProcessor(rowIndex);
				 	}
				 }
			});
			EventUtil.addHandler(self.column_tbodyTable,'mouseout',function(event){
				event=event||_window.event;
//				if(self.config.moseEventPropagation!=false){
//					EventUtil.stopPropagation(event);
//				}
				if(lazyLoad){
					mouseout_processor.process();
				}else{
					mouseout_processor.performProcessor();
				}
			});
		}
	}
};
/** 
* 重新计算 宽度 高度
* @ param caculateHeightAgain 是否需要重新计算高度
 */
EasyTrack.DataTable.prototype.resize=function(caculateHeightAgain){
	var self=this;
	if(self.autoHeight&&caculateHeightAgain){
		self.caculateAutoHeight();
	}
	self.alignTableHead();
};
EasyTrack.DataTable.prototype.fixedColumn=function(fixedColumnCount,fireFixedEvent){
    var self=this;
	var table=self.table;
	var container=self.container;
	var oldfixedColumnCount=self.fixedColumnCount;
	self.fixedColumnCount=fixedColumnCount;
	//self.log(fixedColumnCount);
	//列固定的分割线
	var column_Seperator=self.column_Seperator;
	var fix_column=self.fix_column;
	if(fixedColumnCount<=0){
		if(fix_column!=null){
			fix_column.style.display="none";
			column_Seperator.style.display="none";
		}
		return ;
	}else if(fix_column!=null){
		fix_column.style.display="block";
		column_Seperator.style.display="block";
	}
	
	if(fix_column==null){
		fix_column=container.cloneNode(false);
		fix_column.id=table.id+"fix_column";
		EasyTrack.setStyle(fix_column,{
			'position':'absolute',
			'left':'0px',
			'top':'0px',
			'width':'auto',
			'height':'100%',
			'overflowX':'hidden',
			'overflowY':'hidden'
		});
		self.fix_column=fix_column;
		container.appendChild(fix_column);
	}
	var column_tbody=null;
	var column_tbodyTable=null;
	if(column_tbody==null){
		column_tbody=document.createElement('div');
		column_tbody.id=table.id+"column_tbody";
		EasyTrack.setStyle(column_tbody,{
			'position':'absolute',
			'left':'0px',
			'top':'0px',
			'width':'100%',
			'height':'100%',
			'overflowX':'hidden',
			'overflowY':'hidden'
		});
		fix_column.appendChild(column_tbody);
		self.column_tbody=column_tbody;
		
		var column_bodyHTML=self.table.outerHTML;
		column_tbody.innerHTML=column_bodyHTML;
		column_tbodyTable=column_tbody.firstChild;
		column_tbodyTable.id=table.id+"column_tbodyTable";
		self.fixedTable=column_tbodyTable;
		self.column_tbodyTable=column_tbodyTable;
	}
	var column_thead=self.column_thead;
	var column_theadTable=null;
	if(column_thead==null){
		column_thead= self.body_theadContainer.cloneNode(true);// document.createElement('div');
		column_thead.id=table.id+"column_thead";
		column_theadTable=column_thead.children[0];
		column_theadTable.id=table.id+"column_theadTable";
		fix_column.appendChild(column_thead);
		self.column_thead=column_thead;
		self.column_theadTable=column_theadTable;
		EasyTrack.setStyle(column_thead,{
			'position':'absolute',
			'left':'0px',
			'top':'0px',
			'width':'100%',
			'overflowX':'hidden',
			'overflowY':'hidden'
		});
		fix_column.appendChild(column_thead);
		self.column_thead=column_thead;
	}
	if(column_Seperator==null){
		column_Seperator=document.createElement('div');
		column_Seperator.id=table.id+"column_Seperator";
		column_Seperator.className=" column_Seperator";
		container.appendChild(column_Seperator);
		self.column_Seperator=column_Seperator;
	}
	self.dragColumn();//如果新增的column_theadtable 列拖动没有， 初始化需要初始化 
	//对齐
//	column_tbodyTable.style.marginTop=-+self.body_tbody.scrollTop+"px"; 
	if(fireFixedEvent){
		var fixedColumnListener=self.config.fixedColumnChange;
		if(fixedColumnListener!=null&& typeof(fixedColumnListener)=='function'){
			fixedColumnListener.call(self,oldfixedColumnCount,fixedColumnCount);
		}
	}
};
EasyTrack.DataTable.prototype.dragColumn=function(){
	var self=this;
	
	var table=self.table; //最原始表格
	var body_thead=self.body_thead;
	var body_theadTable=self.body_theadTable;//表头固定的表格
	var body_tbody=self.body_tbody;
	var column_theadTable=self.column_theadTable; //列固定的表格头所在的表格
	var column_tbodyTable=self.column_tbodyTable;//列固定的表格主体表格
	var column_thead=self.column_thead;
	var column_tbody=self.column_tbody;
	var fix_column=self.fix_column;
	var fiexdBodyTableWidth=self.fiexdBodyTableWidth;
	function getStyleOrAttributeWidth(td,div,property){
		var width=td.getAttribute(property);
		return width;
	}	
	var startDrag=function(event){
		var dragHelper=this;
		var th=dragHelper.config.th;
		var clientX=event.clientX;
		th.setAttribute("_clientX",clientX);
		th.setAttribute("oldWidth",th.scrollWidth);
	};
	
	var changeThWidth=function(th,newWidth,changeWidth){
		var thTable=th.parentNode.parentNode.parentNode;//row -->thead-->table
		var thDiv=th.firstChild;
		var cellIndex=th.cellIndex;
		var rowIndex=th.parentNode.rowIndex;
		var colSpanCount=th.getAttribute("colSpanCount");
		var _tableColSpanCount=self.colSpanCount;
		var newWidthPx=newWidth+"px";
		thDiv.style.width=newWidthPx;
		if(thTable===body_theadTable){
			var scrollWidth=body_tbody.scrollWidth+"px";
			table.tHead.rows[rowIndex].cells[cellIndex].firstChild.style.width=newWidthPx;
			body_thead.style.width=scrollWidth;
			
			if(self.fixedColumnCount>0){
				column_theadTable.tHead.rows[rowIndex].cells[cellIndex].firstChild.style.width=newWidthPx;
				column_tbodyTable.tHead.rows[rowIndex].cells[cellIndex].firstChild.style.width=newWidthPx;
				column_thead.style.width=scrollWidth;
				column_tbody.style.width=scrollWidth;
				//self.fix_column.style.height=body_tbody.clientHeight+"px";
				self.caculateColumnWidth();
				self.caculateSeprator();
				
			}
		}else if(thTable===column_theadTable){
			body_theadTable.tHead.rows[rowIndex].cells[cellIndex].firstChild.style.width=newWidthPx;
			table.tHead.rows[rowIndex].cells[cellIndex].firstChild.style.width=newWidthPx;
			column_theadTable.tHead.rows[rowIndex].cells[cellIndex].firstChild.style.width=newWidthPx;
			column_tbodyTable.tHead.rows[rowIndex].cells[cellIndex].firstChild.style.width=newWidthPx;
			//self.fix_column.style.height=body_tbody.clientHeight+"px";
			self.caculateColumnWidth();
			self.caculateSeprator();
			body_thead.style.marginLeft=-body_tbody.scrollLeft+"px";	
		}		
		
	};
	var moveDrag=function(event){
		var dragHelper=this;
		var th=dragHelper.config.th;
		var thDIV=th.firstChild;
		var clientX=event.clientX;
		var width=parseInt(th.getAttribute("oldWidth"));
		var changWidth=parseInt(clientX-th.getAttribute("_clientX"));
		var newWidth = width + changWidth ;
		var minWidth=getStyleOrAttributeWidth(th,thDIV,"minWidth")||20;
		var maxWidth=getStyleOrAttributeWidth(th,thDIV,"maxWidth");
		if(newWidth<=minWidth||(maxWidth!=null&&newWidth>=maxWidth)){
			//dragHelper._upEvent.call(dragHelper,event); //调用拖动结束
		}else{
			changeThWidth(th,newWidth,changWidth);
		}
	};
	var endDrag=function(event){
		self.alignTableHead();
		if(self.fixedColumnCount>0){
			self.caculateColumnWidth();
			self.caculateSeprator();
		}
	};
	
	function dragTable(tableObj,zIndex){
		var thead=tableObj.tHead;
		var rows=thead.rows;
		for(var i=0,j=rows.length;i<j;i++){
			var cells=rows[i].cells;
			var colSpanCount=0;
			for(var m=0,n=cells.length;m<n;m++){
				var th=cells[m];
				colSpanCount+=th.colSpan;
				th.setAttribute("colSpanCount",colSpanCount);
				var data_dragable=th.getAttribute("data_dragable");
				var dragHelper=th.getAttribute("dragHelper");
				if(data_dragable&&dragHelper==null){
					//alert(true);
					 dragHelper=new EasyTrack.DragHelper(createDragHelper(th,zIndex),{
						'th':th
						,'startDrag':startDrag
						,'moveDrag':moveDrag
						,'endDrag':endDrag
					});
					th.setAttribute("dragHelper",dragHelper);
				}
			}
		}
	};
	
	function createDragHelper(th,zIndex){
		var div=document.createElement('div');
		div.className="drageHelper";
		div.style.zIndex=zIndex;
		th.firstChild.appendChild(div);
		return div;
	};
	var dragInitial=body_theadTable.getAttribute("dragInitial");
	if(dragInitial==null){
		dragTable(body_theadTable,20 ); //zIndex 大，导致在最上面（不管是否是固定列）
		body_theadTable.setAttribute("dragInitial",true);
	}
	if(column_theadTable!=null){
		dragInitial=column_theadTable.getAttribute("dragInitial");
		if(dragInitial==null){
			dragTable(column_theadTable,30);
		}
	}
};

EasyTrack.DataTable.prototype.caculateSeprator=function(){
	var self=this;
	var column_Seperator=self.column_Seperator;
	var fix_column=self.fix_column;
	var height=Math.min(self.body_tbody.clientHeight,self.table.clientHeight);
	//self.log(height+"px");
	var fixedColumnCount=self.fixedColumnCount;
	if(fixedColumnCount>0){
		column_Seperator.style.display="block";
		EasyTrack.setStyle(column_Seperator,{
			'top':'0px',
			'left':(fix_column.offsetWidth-1)+"px", //这个的计算可能需要考虑border 
			'height':height+"px"
		});
		fix_column.style.height=height+"px";
		
	}else if(column_Seperator!=null){
		column_Seperator.style.display="none";
	}

};
EasyTrack.DataTable.prototype.caculateColumnWidth=function(){
	var self=this;	
	var fix_column=self.fix_column ,column_thead=self.column_thead;
	var fixedColumnCount=self.fixedColumnCount;
	var fiexdBodyTableWidth=0;
	var colSpanCount=0;
	var rows=self.body_theadTable.tHead.rows;
	var row=rows[0]; //取第一行表头
	var cells=row.cells;
	var firstTh=cells[0];
	var lastTh=cells[fixedColumnCount-1];
	var width=lastTh.getBoundingClientRect().right- firstTh.getBoundingClientRect().left ;
	fix_column.style.width=width+"px";
	
	
};
//设置高度自动计算
EasyTrack.DataTable.prototype.caculateAutoHeight=function(){
//	var start=new Date();
	var self=this;
	var container=self.container;
	var minHeight=self.table.tHead.offsetHeight+15; //表头的高度
	var isIE=navigator.userAgent.toLowerCase().indexOf("msie")>0;
	var clientHeight="0px";
	if(document.compatMode=='CSS1Compat'){
		clientHeight=document.documentElement.clientHeight;
	}else{
		clientHeight=document.body.clientHeight;
	}
	var bottomHeight=self.config.bottomHeight;
	if(bottomHeight==undefined||typeof bottomHeight !='number'){
		bottomHeight=40;
	}
	var flag=false;
	if(self.config.lazyLoad){
		flag=true;
	}
	var maxHeight=clientHeight;
	var heightFun=self.config.heightFun;
	if(heightFun!=null&& typeof(heightFun)=='function'){
		maxHeight=heightFun.call(self,clientHeight-container.getBoundingClientRect().top-bottomHeight);
		flag=true;
	}else{
		 maxHeight=clientHeight-container.getBoundingClientRect().top-bottomHeight;
	}
	//确定最大 ，最小高度
	if(maxHeight<minHeight){
		var temp=maxHeight;
		maxHeight=minHeight;
		minHeight=temp;
	}
	if(self.config.rowHeight==undefined){
		self.config.rowHeight=18;
	}
 	var containerHeight=minHeight;
 	if(!flag ){
 		//设置最大高度
		self.container.style.height=maxHeight+"px";
		var actulHeight=self.table.offsetHeight;
		if(self.table.offsetWidth>self.body_tbody.offsetWidth){
			actulHeight+=self.config.rowHeight;
		}
		if(actulHeight>=minHeight&&actulHeight<=maxHeight){
			containerHeight=actulHeight;
		}else if(actulHeight<minHeight){
			containerHeight=minHeight;
		}else if(actulHeight>maxHeight){
			containerHeight=maxHeight;
		}
		self.container.style.height=containerHeight+"px";
	}else{
		containerHeight=maxHeight;
		self.container.style.height=containerHeight+"px";
	}
	
};
/**
 * 设置 表格外层Div 的高度(当container 外层没有)
 */
EasyTrack.DataTable.prototype.setContainerHeight=function(containerHeight){
	var self=this;
	//EasyTrack.log(containerHeight);
	if(typeof(containerHeight)=='number'){
		if(containerHeight<0){
			containerHeight=0;
		}
		containerHeight+="px";
	}
	self.container.style.height=containerHeight;
	self.resize(false);
};

/**
 * 设置表格外层的DIV 的宽度 with 可以是 100px ,或者百分比
 */
EasyTrack.DataTable.prototype.setContainerWidth=function(width){
	var self=this;
	if(typeof(width)=='number'){
		width+="px";
	}
	self.container.style.width=width;
	self.resize(false);
};


EasyTrack.DataTable.prototype.scrollToEnd=function(){
	var self=this;
	self.body_tbody.scrollTop=self.body_tbody.scrollHeight-self.body_tbody.clientHeight;
	self.resize(false);
};

EasyTrack.DataTable.prototype.addScrollTop=function(scrollTop){
	var self=this;
	self.body_tbody.scrollTop=self.body_tbody.scrollTop+scrollTop;
	self.resize(false);
};
EasyTrack.DataTable.prototype.scrollTo=function(scrollTop){
	var self=this;
	self.body_tbody.scrollTop=scrollTop;
	self.resize(false);
};


/**
 * 将一个div 下面的两个表格 做成左右布局，是大多数报表的样子
 */
EasyTrack.FixedTwoTable=function(leftTableID,rightTableID,config){
	//左侧table
	if( typeof leftTableID=='string'){
		this.leftTable=document.getElementById(leftTableID);
	}else{
		this.leftTable=leftTableID;
	}
	//右侧table
	if(typeof rightTableID==='string'){
		this.rightTable=document.getElementById(rightTableID);
	}else{
		this.rightTable=rightTableID;
	}
	this.config=config||{};
	
	//配置
	//高度是否可变
	this.autoHeight=this.config.autoHeight==null?true:this.config.autoHeight;
	//顶层容器，
	this.container; 
	
	//position=relative
	this.containerChild;
	//container>containerChild
	
	//containerChild>leftDIV
	this.leftDIV;
	
	//containerChild>seperateDIV
	this.seperateDIV;
	
	//containerChild>rightDIV
	
	this.rightDIV;
	
	//右侧实现表格 表头固定
	this.rightTableWrapDIV /***原始表格分割后右边边的表格 rightDIV>rightTableWrapDIV> table***/,
	this.rightTableHeadDIV ,
	this.rightTableHeadChildDIV,
	this.rightTableHead /***原始表格分割后右边边的表格 表头克隆产生的表格 ***/;
	/****层次关系rightDIV>rightTableHeadChildDIV>ringtTableHead***/
	
	
	//左侧侧实现表格 表头固定
	this.leftTableWrapDIV /***原始表格分割后右边边的表格 leftDIV>leftTableWrapDIV> leftTable***/,
	this.leftTableHeadDIV ,
	this.leftTableHeadChildDIV,
	this.leftTableHead /***原始表格分割后右边边的表格 表头克隆产生的表格 ***/;
	/****层次关系leftDIV>leftTableHeadChildDIV>leftTableHead***/
	//初始化dom
	

	this.initTable();
	
	
	//config 的可用配置
	// autoHeight ,高度是否在resize 的时候重算
	//heightFun:function(clientHeight) 高度重算的方法
	//containerStyle 容器样式
	//leftTableWidth 左侧初始化的宽度
	//seperateDrag分割处是否可以拖动
	//leftTableMaxWidth 左侧可以变化的最大宽度
	//scrollX ,水平向是否出现滚动条
	//dragHander {startDrag:function(event),moveDrag:function(event),endDrag:function(event)}
	
//对于很大数量的表格 动态加载实现
	//lazyLoad: false/true 是否在滚动的时候动态加载行
	var self=this;
	var checkeTime=self.config.checkeTime||2000;
	if(this.config.lazyLoad&&this.config.checkLoad!=false){
		ET.Utils.addOnloadEvent(function(){
			window.setTimeout(function(){
				self.checkNeedloadLazyRow();
			},checkeTime)
		})
	}

	
};
EasyTrack.FixedTwoTable.prototype.initTable=function(){
	var self=this;
	
	var container=null;
	var config=self.config;
	if(config.container){
		container=config.container;
	}else{
		container=self.rightTable.parentNode;
	}
	self.container=container;
	container.style.overflow="hidden";
	var ie7=EasyTrack.browser.ie==7;
	//EasyTrack.log("ie7=="+ie7);
	if(!ie7&&self.config.tableLayout!=false){
		self.leftTable.style.tableLayout="fixed";
		self.rightTable.style.tableLayout="fixed";
	}
	if(self.leftTable.parentNode!=container){
		container.appendChild(self.leftTable);
	}
	if(config.containerStyle){
		EasyTrack.setStyle(containter,config.containerStyle);
	}
	var cache={
		'leftTableOffsetWidth':self.leftTable.offsetWidth
		,'rightTableOffsetWidth':self.rightTable.offsetWidth
	}
	var previouseElement=container.previousElementSibling;
	if(previouseElement!=null){
		cache['containerTop']=previouseElement.getBoundingClientRect().bottom;
		//container.style.top=cache['containerTop']+"px";
	}else{
		cache['containerTop']=self.container.getBoundingClientRect().top;
	}
	var containerChild=document.getElementById(self.leftTable.id+"__containerChild");
	if(containerChild==null){
		containerChild=document.createElement("div");
		containerChild.id=self.leftTable.id+"__containerChild";
		EasyTrack.setStyle(containerChild,{
			'position':'relative',
			'padding':'0px',
			'margin':'0px',
			'width':'auto',
			'height':'100%'
		});
	};
	
	self.containerChild=containerChild;
	container.appendChild(containerChild);
	var leftDIV=document.getElementById(self.leftTable.id+"__leftDIV");
	if(leftDIV==null){
		leftDIV=document.createElement("div");
		leftDIV.id=self.leftTable.id+"__leftDIV";
		EasyTrack.setStyle(leftDIV,{
			'position':'absolute',
			'top':'0px',
			'left':'0px',
			'bottom':'0px',
			'padding':'0px',
			'height':'100%',
			'margin':'0px',
			'overflow':'hidden'
		});
	};
	containerChild.appendChild(leftDIV);
	self.leftDIV=leftDIV;
	self.cache=cache;
	
	var seperateDIV= document.getElementById(self.leftTable.id+"__seperateDIV"); 
	if(seperateDIV==null){
		seperateDIV=document.createElement("div");
		seperateDIV.id=self.leftTable.id+"__seperateDIV";
		//这里写死了，以后可能需要放开一些东西。。。。
		EasyTrack.setStyle(seperateDIV,{
			'position':'absolute',
			'top':'0px',
			'bottom':'0px',
			'left':cache.leftTableOffsetWidth+"px",
			'padding':'0px',
			'margin':'0px',
			'width':'3px',
			'height':'100%',
			'backgroundColor':'#33a2d5',
			'overflow':'hidden'
		});
	}
	cache['seperateDIVOffsetWidth']=3;
	containerChild.appendChild(seperateDIV);
	self.seperateDIV=seperateDIV;
	
	var rightDIV= document.getElementById(self.leftTable.id+"__rightDIV"); 
	if(rightDIV==null){
		rightDIV=document.createElement("div");
		rightDIV.id=self.leftTable.id+"__rightDIV";
	}
	EasyTrack.setStyle(rightDIV,{
		'position':'absolute',
		'padding':'0px',
		'margin':'0px',
		'left':(cache.leftTableOffsetWidth+cache['seperateDIVOffsetWidth'])+'px',
		'top':'0px',
		'bottom':'0px',
		'height':'100%',
		'overflow':'hidden'
		
	});
	
	containerChild.appendChild(rightDIV);
	self.rightDIV=rightDIV;
	var fixeTableHead=function (table,position,tableDIV){
			var tableWrapDIV=document.createElement("div");
			tableWrapDIV.id=table.id+"tableWrapDIV";
			self[position+"TableWrapDIV"]=tableWrapDIV;
			EasyTrack.setStyle(tableWrapDIV,{
				'position':'absolute',
				'width':'100%',
				'top':'0px',
				'left':'0px',
				'bottom':'0px',
				'height':'100%',
				'overflowX':self.config.scrollX!=false?'scroll':'hidden',
				'overflowY':position==="right"?'auto':'hidden'
			});
			tableDIV.insertBefore(tableWrapDIV,tableDIV.firstChild);
			var tableHeadDIV=document.createElement('div');
			tableHeadDIV.id=table.id+"tableHeadDIV";
			self[position+'TableHeadDIV']=tableHeadDIV;
			var tableClone=table.cloneNode(false);
			tableClone.style.display="";
			tableClone.id=table.id+position;
			var thead=table.tHead.cloneNode(true);
			tableClone.appendChild(thead);
			tableHeadDIV.appendChild(tableClone);
			
			self[position+"TableHead"]=tableClone;
			EasyTrack.setStyle(tableHeadDIV,{
				'position':'absolute',
				'top':'0px',
				'left':'0px',
				'overflow':'hidden',
				'width':'100%'
			});
			tableDIV.appendChild(tableHeadDIV);
			
			if(position=='left'){
				cache['leftTableOffsetWidth']=table.offsetWidth;
				if(self.config.leftTableWidth==undefined){
					self.config.leftTableWidth=cache.leftTableOffsetWidth;
				}
			}
			//如果是延迟加载 则
			if(self.config.lazyLoad){
				if(self.config.splitColumn>0&&position=='left'){
					//则左侧的leftTable
					tableWrapDIV.appendChild(table);
					self['leftHiddenTable']=null;
				}else{
					var cloneTable=table.cloneNode(false);
					cloneTable.style.display="";
					cloneTable.appendChild(table.tHead);
					table.style.display="none";
					cloneTable.appendChild(table.tBodies[0]);
					tableWrapDIV.appendChild(cloneTable);
					self[position+'Table']=cloneTable;
					self[position+'HiddenTable']=table;
					table.id=table.id+"hidden";
				}
			}else{
				tableWrapDIV.appendChild(table);
			}
			
		}
		//var wath2=new EasyTrack.StopWatch();
		fixeTableHead(self.leftTable,"left",leftDIV);
		//wath2.logTime("left==");
		fixeTableHead(self.rightTable,"right",rightDIV);
		//wath2.logTime("right==");
		
	self.resize(true);
	
	if(self.config.seperateDrag){
		self.dragSeperate();
	}
	self.bindEvent();
	if(self.config.mouseEvent!=false){
		self._initMouseEvent();
	}
	//这个是IE 上面很奇怪的问题，不知道为什么第一次老是表头有问题
};
EasyTrack.FixedTwoTable.prototype.getRightTableRows=function(){
	var self=this;
	if(self._allRightTableRows){
		return self._allRightTableRows;
	}
	var rightTableRows=self.rightTable.rows;
	var rightHiddenTableRows=self.rightHiddenTable.rows;
	var result= {
		length:function(){
			return rightTableRows.length+rightHiddenTableRows.length
		}
		,get:function(index){
			if(index<0){
				return undefined;
			}else if(index<rightTableRows.length){
				return rightTableRows[index];
			}else{
				return rightHiddenTableRows[index-rightTableRows.length];
			}
		}
	}
	self._allRightTableRows=result;
	return result;
};
EasyTrack.FixedTwoTable.prototype.getLeftTableRows=function(){
	var self=this;
	
	if(self._allLeftTableRows){
		return self._allLeftTableRows;
	}
	var leftTableRows=self.leftTable.rows,leftHiddenTable=self.leftHiddenTable;
	var leftHiddenTableRows=leftHiddenTable?leftHiddenTable.rows:[];
	var result= {
		length:function(){
			return leftTableRows.length+leftHiddenTableRows.length
		}
		,get:function(index){
			if(index<0){
				return undefined;
			}else if(index<leftTableRows.length){
				return leftTableRows[index];
			}else {
				return leftHiddenTableRows[index-leftTableRows.length];
			}
		}
	}
	self._allLeftTableRows=result;
	return result;
};
/**
 * 给表格加上mouserover row on ,mouseover row off 
 */
EasyTrack.FixedTwoTable.prototype._initMouseEvent=function(){
	var self=this ,leftTable=self.leftTable,rightTable=self.rightTable;
	var lastMouseOverRow=null, _window=window,_body=document.body;
	var onRow=function(rowIndex){
		try{
			_window.on(leftTable.rows[rowIndex]);
			_window.on(rightTable.rows[rowIndex]);
		}catch(ex){
		}
	};
	var offRow=function(rowIndex){
		try{
			_window.off(leftTable.rows[rowIndex]);
			_window.off(rightTable.rows[rowIndex]);
		}catch(ex){
		}
		
	};
	function getEventContextRow(event,currentTable){
		event=event||_window.event;
		var targetNode=event.target||event.srcElement;
		var row=targetNode;
		try{
			if(row!=currentTable){
				while( row.parentNode.parentNode!=currentTable){
					row=row.parentNode;
				}
			}
		}catch(ex){
			row=null;
		}
		return row;
	}
	var mouseover_processor = {
	    timeoutId: null,
	    //实际进行处理的方法
	    performProcessor: function(row,currentTable) {
	      if(lastMouseOverRow!=null){
				offRow(lastMouseOverRow.rowIndex);
			}
			if(row!=null){
				onRow(row.rowIndex);
				lastMouseOverRow=row;
			}
	    },
	    process: function(row,currentTable) {            //初始处理调用的方法
	        _window.clearTimeout(this.timeoutId);
	        var that = this;
	        this.timeoutId = _window.setTimeout(function(){        //创建定时器
	            that.performProcessor(row,currentTable);
	        }, 20);
	    }
	};
	var mouseout_processor={
		timeoutId: null,
	    //实际进行处理的方法
	    performProcessor: function(currentTable) {
	      if(lastMouseOverRow!=null){
				offRow(lastMouseOverRow.rowIndex);
			}
	    },
	    process: function(currentTable) {            //初始处理调用的方法
	        _window.clearTimeout(this.timeoutId);
	        var that = this;
	        this.timeoutId = _window.setTimeout(function(){        //创建定时器
	            that.performProcessor(currentTable);
	        }, 20);
	    }
		
	}
	var ie78=EasyTrack.browser.ie==7||EasyTrack.browser.ie==8;
      //尝试开始执行
	var stopPropration=self.config.stopMousePropration;
	var _mouseEventTable=function(currentTable){
		EventUtil.addHandler(currentTable,'mouseover',function(event){
			event=event||window.event;
			if(stopPropration!=false){
				EventUtil.stopPropagation(event);
			}
			row=getEventContextRow(event,currentTable);
			if(ie78){
				mouseover_processor.process(row,currentTable);
			}else{
				mouseover_processor.performProcessor(row,currentTable);
			}
			
		});
		
		EventUtil.addHandler(currentTable,'mouseout',function(event){
			if(stopPropration!=false){
				EventUtil.stopPropagation(event);
			}
			if(ie78){
				mouseover_processor.process(currentTable);
			}else{
				mouseover_processor.performProcessor(currentTable);
			}
			
		});
		
	};
	_mouseEventTable(leftTable);
	_mouseEventTable(rightTable);
	
};
//使左侧table 可以改变宽度
EasyTrack.FixedTwoTable.prototype.dragSeperate=function(){
	var self=this;
	var seperateDIV=self.seperateDIV;
	seperateDIV.style.cursor='e-resize';
	var dragHander=self.config.dragHander;
	var startDrag=function(event){
		var clientX=event.clientX;
		seperateDIV.setAttribute("_oldWidth",self.leftDIV.offsetWidth);
		seperateDIV.setAttribute("_clientX",clientX);
		if(dragHander!=null&&  typeof dragHander.startDrag ==='function'){
			dragHander.startDrag.call(self,event);
		}
	};
	var endDrag=function(event){
		self.resize(true);
		seperateDIV.removeAttribute("_clientX");
		if(dragHander!=null&&  typeof dragHander.endDrag ==='function'){
			dragHander.endDrag.call(self,event);
		}
	};
	var moveDrag=function(event){
		var minWidth=self.config.leftTableMinWidth||0;
		var maxWidth=self.config.leftTableMaxWidth||self.leftTable.offsetWidth;
		if(maxWidth>self.leftTable.offsetWidth){
			maxWidth=self.leftTable.offsetWidth;
		}
		var clientX=event.clientX;
		var changeWidth=parseInt(clientX-seperateDIV.getAttribute("_clientX"));
		var newWidth=parseInt(seperateDIV.getAttribute("_oldWidth"))+changeWidth;
		if(newWidth<minWidth){
			newWidth=minWidth;
		}
		if(newWidth>maxWidth){
			newWidth=maxWidth;
		}
		self.config.leftTableWidth=newWidth;
		self.resize(false);
		if(dragHander!=null&&  typeof dragHander.moveDrag ==='function'){
			dragHander.moveDrag.call(self,event);
		}
	};
	var dragHelper=new EasyTrack.DragHelper(seperateDIV,{
		'fixedTwoTable':self
		,'startDrag':startDrag
		,'endDrag':endDrag
		,'moveDrag':moveDrag
	});
		
};
//设置容器高度 ，注 param containerHeight 必须是一个整数，或整数加px,注意百分数很可能不正常，当container外面的高度为0或'auto'时
EasyTrack.FixedTwoTable.prototype.setContainerHeight=function(containerHeight){
	var self=this;
	if(typeof(containerHeight)=='number'){
		if(containerHeight<0){
			containerHeight=0;
		}
		containerHeight+="px";
	}
	self.container.style.height=containerHeight;
	//EasyTrack.log(self.container.id+";"+containerHeight);
	self.resize(false);
};
//设置容器宽度 ，可以是整数 ，整数加px ，百分数 ，
EasyTrack.FixedTwoTable.prototype.setContainWidth=function(width){
	var self=this;
	if(typeof(width)=='number'){
		width+="px";
	}
	self.container.style.width=width;
	self.resize(false);
};
EasyTrack.FixedTwoTable.prototype.checkNeedloadLazyRow=function(){
	var self=this;
	var rightHiddenTable=self.rightHiddenTable,rightHiddenRows=rightHiddenTable.rows;
	if(rightHiddenRows.length==0){
		return ;
	}
	var rightScrollDIV=self.rightTableWrapDIV;
	var timeID=null;
	var first=true;
	var loadFun=function(){
		flag=self.loadLazyRow();
	}
	var loadFun=function(){
		if(rightScrollDIV.offsetWidth>rightScrollDIV.clientWidth&&!first){
			return ;
		}
		first=false;
		flag=self.loadLazyRow();
		if(flag==false&&rightHiddenTable.rows.length==0){
			return ;
		}else{
			window.setTimeout(function(){loadFun()},10);
		}
	}
	loadFun();
};
EasyTrack.FixedTwoTable.prototype.bindEvent=function(){
	//宽度重新计算
		var self=this;
		if(self.config.windowResizeAble!=false){
			var _reisizeFun=function(){
				self.resize(true);
				if(self.config.lazyLoad){
					self.checkNeedloadLazyRow();
				};
			}
			self._reisizeFun=_reisizeFun;
			var isIE7=EasyTrack.browser.ie==7;
			var isIE8=EasyTrack.browser.ie==8;
			self.currentScrollTop=0;
			if(isIE7||isIE8){
				EventUtil.addHandler(window,'resize',function(event){
					EasyTrack.throttle(_reisizeFun,self,true);
				});
			}else{
				EventUtil.addHandler(window,'resize',function(event){
					_reisizeFun();
				});
			}
		}
		
		var rightTableScrollEvent=self.config.rightTableScrollEvent;
		var isRightFun=rightTableScrollEvent!=null&&typeof rightTableScrollEvent=='function';
		//竖直方向一起滚动
		//lazyLoad: false/true 是否在滚动的时候动态加载行
		
		
		var lazyLoad=self.config.lazyLoad;
		EventUtil.addHandler(self.rightTableWrapDIV,'scroll',function(event){
			var scrollTop=self.rightTableWrapDIV.scrollTop;
			self.leftTable.style.marginTop=-scrollTop+"px";
			self.rightTableHead.style.marginLeft=-self.rightTableWrapDIV.scrollLeft+"px";
			if(isRightFun){
				rightTableScrollEvent.call(self,self.rightTableWrapDIV);
			}
			if(self.config.lazyLoad){
				window.clearTimeout(self.scrollTimeID);
				self.scrollTimeID=window.setTimeout(function(){
					if(self.currentScrollTop!=scrollTop){
						self.loadLazyRow(50);
						self.currentScrollTop=scrollTop;
					}
				},160);
			}
		});
		var leftTableScrollEvent=self.config.leftTableScrollEvent;
		var isLeftFun=leftTableScrollEvent!=null&&typeof leftTableScrollEvent=='function';
		
		EventUtil.addHandler(self.leftTableWrapDIV,'scroll',function(){
			self.leftTableHead.style.marginLeft=-self.leftTableWrapDIV.scrollLeft+"px";
			if(isLeftFun){
				leftTableScrollEvent.call(self,self.leftTableWrapDIV);
			}
		});
		
		EventUtil.addHandler(self.leftTableWrapDIV,EasyTrack.browser.firefox?'DOMMouseScroll':'mousewheel',function(event){
			event=event||window.event;
			var delta=EventUtil.getWheelDelta(event);
			//EasyTrack.log('delta='+delta);
			var scrollTop=self.rightTableWrapDIV.scrollTop;
			//EasyTrack.log('old scrollTop='+scrollTop);
			scrollTop-=(delta/120)*80;
			self.rightTableWrapDIV.scrollTop=scrollTop;
			self.leftTable.style.marginTop=-(self.rightTableWrapDIV.scrollTop)+"px";
			if(self.config.lazyLoad){
				window.clearTimeout(self.DOMMouseScrollTimeID);
				self.DOMMouseScrollTimeID=window.setTimeout(function(){
					self.loadLazyRow(50);
				},50);
			}
			if(isRightFun){
				rightTableScrollEvent.call(self,self.rightTableWrapDIV);
			}
		});
		if(self.config.disableDrag!=true){
			var drag=new EasyTrack.DragHelper(self.leftTableWrapDIV,{
				moveDrag:function(event){
					EventUtil.preventDefault(event);
				}
			})
		}
		
};
EasyTrack.FixedTwoTable.prototype.resize=function(caculateHeightAgain){
	var self=this;
	if(self.autoHeight&&caculateHeightAgain){
		self.caculateAutoHeight();
	}
	self.containerChild.style.height=self.container.clientHeight+"px";
	self.caculateDIVWidth();
	self.rightTableHeadDIV.style.width=self.rightTableWrapDIV.clientWidth+"px";
	self.leftTableHeadDIV.style.width=self.leftTableWrapDIV.clientWidth+"px";
	
	self.leftTable.style.marginTop=-(self.rightTableWrapDIV.scrollTop)+"px";
	self.rightTableHead.style.marginLeft=-self.rightTableWrapDIV.scrollLeft+"px";
	self.leftTableHead.style.marginLeft=-self.leftTableWrapDIV.scrollLeft+"px";
};
EasyTrack.FixedTwoTable.prototype.caculateDIVWidth=function(){
	var self=this;
	var leftDIVOffsetWidth=self.config.leftTableWidth!=null?self.config.leftTableWidth:self.cache['leftTableOffsetWidth'];
	
	self.leftDIV.style.width=leftDIVOffsetWidth+"px";
	self.seperateDIV.style.left=leftDIVOffsetWidth+"px";
	
	self.rightDIV.style.left=(leftDIVOffsetWidth+self.cache['seperateDIVOffsetWidth'])+"px";
	self.rightDIV.style.right="0px";
};
EasyTrack.FixedTwoTable.prototype.caculateAutoHeight=function(){

	var self=this;
	var container=self.container;
	var minHeight=self.leftTable.tHead.offsetHeight+15; //表头的高度
	//var isIE=EasyTrack.browser.ie!=undefined;
	var clientHeight="0px";
	if(document.compatMode=='CSS1Compat'){
		clientHeight=document.documentElement.clientHeight;
	}else{
		clientHeight=document.body.clientHeight;
	}
	var bottomHeight=self.config.bottomHeight;
	if(typeof bottomHeight =='undefined'){
		bottomHeight=2;
	}
	clientHeight=clientHeight-self.cache['containerTop']-bottomHeight;
	var maxHeight=clientHeight;
	var heightFun=self.config.heightFun;
	if(heightFun!=null&& typeof(heightFun)=='function'){
		maxHeight=heightFun.call(self,clientHeight);
	}
	
	//确定最大 ，最小高度
	if(maxHeight<minHeight){
		var temp=maxHeight;
		maxHeight=minHeight;
		minHeight=temp;
	}
	
	self.setContainerHeight(maxHeight);
};

EasyTrack.FixedTwoTable.prototype.loadLazyRow=function(loadRows){
	var self=this,config=self.config;
	var leftTable=self.leftTable,rightTable=self.rightTable;
	var rightHiddenTable=self.rightHiddenTable ,leftHiddenTable=self.leftHiddenTable;
	var rightHiddenRows=rightHiddenTable.rows,leftHiddenRows=leftHiddenTable!=undefined?leftHiddenTable.rows:null;
	var leftFirstTbody=leftTable.tBodies[0], rightFirstTbody=rightTable.tBodies[0];
	if(rightHiddenRows.length==0){
		return false ;
	}
	if(loadRows==undefined){
		loadRows=config.lazyBatchSize||100;
	}
	var splitColumn=self.splitColumn;
	var splitRowFun=null;
	if(self.splitColumn>0){
		splitRowFun=function(rightRow){
			var colSpanCount=0;
			var cells=rightRow.cells;
			var leftRow=rightRow.cloneNode(false);
			while (colSpanCount<splitColumn) {
				cell=cells[0];
				colSpanCount+=cell.colSpan;
				leftRow.appendChild(cell);
			}
			return leftRow;
		}
	}
	
	var count=0, leftFrag=document.createDocumentFragment(),rightFrag=document.createDocumentFragment();
	var tempLeftRow=null,tempRightRow=null;
	while((tempRightRow=rightHiddenRows[0])!=undefined&&count<loadRows){
		if(splitColumn>0){
			tempLeftRow=splitRowFun(tempRightRow);
		}else{
			tempLeftRow=leftHiddenRows[0];
		}
		leftFrag.appendChild(tempLeftRow);
		rightFrag.appendChild(tempRightRow);
		count++;
	}
	
	rightFirstTbody.appendChild(rightFrag);
	self.leftTable.style.marginTop=-(self.rightTableWrapDIV.scrollTop)+"px";
	leftFirstTbody.appendChild(leftFrag);
	self.rightTableHeadDIV.style.width=self.rightTableWrapDIV.clientWidth+"px";
	return count>0;
};
/*
 * 
 *将一个大的表格分割成左右两个表格，
 用于做报表，列的宽度不支持100%, 表格的宽度不支持百分比	
*/

EasyTrack.SplitDataTable=function(tableID,config){
	var table=document.getElementById(tableID);
	this.splitColumn=config.splitColumn||1;
	var leftTable=this.splitTable(table,this.splitColumn,config);
	EasyTrack.FixedTwoTable.call(this,leftTable,tableID,config);
};
EasyTrack.inheritPrototype(EasyTrack.SplitDataTable,EasyTrack.FixedTwoTable);

EasyTrack.SplitDataTable.prototype.splitTable=function(table,splitColumn,config){
	var self=this;
	//表格宽度不支持百分比，列的宽度也不支持百分比
	var lazyLoad=config.lazyLoad;
	var leftTable=table.cloneNode(false),
		 childs=table.childNodes;
	if(table.id){
		leftTable.id="left"+table.id;
	}
	function cloneTRows (tBody){
		var leftTBody=tBody.cloneNode(false);
		leftTable.appendChild(leftTBody);
		var rows=tBody.rows,row=null;
		for(var m=0,n=rows.length;m<n;m++){
			row=rows[m];
			cloneRow(row,leftTBody,splitColumn);
		}
		return leftTBody;
	}
	var cloneRow=self.splitRow;
	var tBodyIndex=0;
	var splitHeadFun=config.splitHeadFun;
	var frag=null,child=null,tagName='',leftTbody=null,frags=document.createDocumentFragment();
	for(var i=0,j=childs.length;i<j;i++){
		 child=childs[i];
		 if(child.nodeType==1){
		 	tagName=child.tagName.toLowerCase();
			if(tagName=='thead'||tagName=='tbody'||tagName=='tfooter'){
				if(tagName=='tbody'&&lazyLoad){
					tBodyIndex++;
				}
				if(tagName=='tbody'&&tBodyIndex>=2&&lazyLoad){
					leftTbody=child.cloneNode(false);
					leftTable.appendChild(leftTbody);
				}else if(splitHeadFun!=null&& tagName=='thead'){
					var leftThead=table.tHead.cloneNode(false);
					leftTable.appendChild(leftThead);
					splitHeadFun.call(self,table.tHead,leftThead,splitColumn);
				}else {
					frag=document.createDocumentFragment();
					frag.appendChild(cloneTRows(child));
					leftTable.appendChild(frag);
				}
			}
		 }
	}
	return leftTable;
};
EasyTrack.SplitDataTable.prototype.splitRow=function(row,target,splitColumn){
	var leftRow=row.cloneNode(false);
	target.appendChild(leftRow);
	var colSpanCount=0;
	var cells=row.cells ,cell=null;
	while (colSpanCount<splitColumn) {
		cell=cells[0];
		colSpanCount+=cell.colSpan;
		leftRow.appendChild(cell);
	}
	return leftRow;
};
EasyTrack.StopWatch=function(){
	var div=document.getElementById('tiphsdksks');
	if(div==null){
		div=document.createElement('div');
		div.id="tiphsdksks";
		EasyTrack.setStyle(div,{
			'top':0,
			'right':0,
			'position':'absolute',
			'width':'200px'
		});
		document.body.appendChild(div);
	}
	this.div=div;
	this.start=new Date();
//	
};

EasyTrack.StopWatch.prototype.logTime=function( message){
		var end=new Date();
		var log=document.createElement('div');
		log.innerHTML=(message!=null?message:"")+":" +(end.getTime()-this.start.getTime()) ;
		this.div.appendChild(log);
		this.start=end;
};