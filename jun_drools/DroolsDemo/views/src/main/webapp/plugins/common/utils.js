steal('jquery/class').then(function($) {

	var imgType=["gif", "jpeg", "jpg", "bmp", "png"];
	var currentDate = new Date(123456789);
	
	if(typeof String.prototype.trim !== 'function') {
        String.prototype.trim = function() {
            return this.replace(/^\s+|\s+$/g, '');
        }
    }
	
	$.fn.center = function() {		
		var windowWidth =  $(window).width();  
		var windowHeight = $(window).height(); 
		var popupHeight = $(this).height();   
		var popupWidth = $(this).width();  	
		var top;
		var left;
		if (popupHeight>=windowHeight){
			top=50;
		}
		else{
			top=(windowHeight-popupHeight)/2;
		}
		if (popupWidth>=windowWidth){
			left=0;
		}
		else{
			left=(windowWidth-popupWidth)/2;
		}
		if (!$(this).parent().is("body") && $(this).attr("id")!="site_map"){
			top=top-150;
			left=left-180;
		}
		$(this).css({   
		    "top": top,  
		    "left": left  
		});  
	}
	
	$.fn.limit = function(el, length) {
		var size = length ? length : 20;
		$(this)
				.find(el)
				.each(
						function() {
							var text = Plugins.Common.Utils.replaceTrim($(this)
									.html());
							if (text.length > size) {
								$(this)
										.html(
												"<span name='origin'>"+text.substring(0, size)
														+ "...</span><span name='flower' class='colorTip hide'>"
														+ text + "</span>");
							}
							$(this).mouseover(function() {
								$(this).find("[name='flower']").show();
								$(this).find("[name='origin']").hide();
							});
							$(this).mouseout(function() {
								$(this).find("[name='flower']").hide();
								$(this).find("[name='origin']").show();
							});
						});
	}	
	
	$.fn.generatePup = function(config,fullpopup,callBack){
		var pupId = Math.uuid();
		var tips = $('.tip:visible');
		var sequence = tips.length;
		var $pup = $("<div class='hide tip' id='"+pupId+"' sequence='"+sequence+"'><div>");
		
		if(fullpopup){
			$pup.addClass("fullpopup");
			if(!$(".main-inner").hasClass("full-width"))
				$(".main-inner").addClass("full-width");
			$("#header_content").hide();
		}
		
		if(sequence > 0)$(".tip[sequence='"+(tips.length - 1)+"']").addClass("auto-tip");
		for(var key in config){
			$pup.attr(key,config[key]);
		}
		if (config.id){
		    $("#"+config.id).remove();
		}
		$(this).append($pup);
		
		if(callBack) callBack(pupId);
		
		return pupId;
	}
	
	$("body").on("click",".close-button,.pop-close",function(ev){		
		var pup = ev.target.closest(".tip");	
		if($(".fullpopup:visible").length == 1 && $(pup).hasClass("fullpopup")){
			$(".main-inner").removeClass("full-width");
			$("#header_content").show();
		}
		$(pup).remove();		
		var tips = $('.tip:visible');
		$(".tip[sequence='"+(tips.length - 1)+"']").removeClass("auto-tip");
		
		if(tips.length == 0) $(".overlay").hide();
	})
	
	$.Class("Plugins.Common.Utils", {	
		/*
		 * defectCode->defect_code
		 */
		property2DbStr:function(propertyName){
			var result="";
			for (var i = 0; i < propertyName.length; i++) {
				var c=propertyName.charAt(i);
				if(c<'A'||c>'Z'){
					result+=c;
				}else{
					result+="_"+c.toLowerCase();
				}
			}
			return result;
		},
		comparsionArray:function(propertyName)
        {
            return function(object1, object2)
            {
                var value1 = object1[propertyName];
                var value2 = object2[propertyName];
                if (value1 < value2)
                {
                    return -1;
                } else if (value1 > value2)
                {
                    return 1;
                } else
                {
                    return 0;
                }
            };
        },
        
        repeatArray : function(array1, array2){
    		var permissions = [];
    		$.each(array1,function(i,item1){
    			$.each(array2,function(j,item2){
    				if(item1 == item2){
    					permissions.push(item1);
    				}
    			});
    		});
    		return permissions.length > 0?permissions:-1;
    	},
    	
    	filterProcessType:function(process,processes){
    		for(var i=0;i < processes.length;i++){
    			if(process == processes[i].processType)
    				return processes[i].isRequired;
    		}
    		return false;
    	},
    	
    	limitStr : function(str,len) {
    		if (str.length<=len){return str;}
    		return str.substring(0, len) + "...";
    	},
    
        getURLParameter:function(paras)
        {         		
            var paraString = location.search.replace("?","").split("&");             
            var paraObj = {} 
            for (i=0; j=paraString[i]; i++){ 
                paraObj[j.substring(0,j.indexOf("=")).toLowerCase()] = j.substring(j.indexOf("=")+1,j.length); 
            } 
            var returnValue = paraObj[paras.toLowerCase()]; 
            if(typeof(returnValue)=="undefined"){ 
                return ""; 
            }else{ 
                return returnValue; 
            } 
        },        

        validEmail:function(email) {
        	var patten = new RegExp(/^[\w-]+(\.[\w-]+)*@([\w-]+\.)+[a-zA-Z]+$/);
        	return patten.test(email);
        },
        checkEmail:function(email,requiredText){
        	if (email==""){
				return $.t(requiredText);
			}
			if (!this.validEmail(email)){
				return $.t('validate.email.invalid_email');
			}
			
			return "";
        },
        validPassword:function(el){
			var input=el.val();
			el.val(input.replace(/[^a-zA-Z0-9\~\!\@\#\$\%\^\&\*\_\-\+\=\`\|\\\(\)\{\}\[\]\:\;\"\'\<\>\,\.\?\/]/g,''));
		},
		checkPassword:function(userName,password,requiredText){
			if (password==""){
				return $.t(requiredText);
			}
			if (password.length<8){
				return $.t('validate.password.required_min');
			}
			if (password.length>26){
				return $.t('validate.password.required_max');
			}
			var combination=0;
			if (password.match(/[A-Z]/)){
				combination=combination+1;
				//return $.t('validate.password.contains_upper');
			}
			if (password.match(/[a-z]/)){
				combination=combination+1;
				//return $.t('validate.password.contains_lower');
			}
			if (password.match(/\d+/)){
				combination=combination+1;
				//return $.t('validate.password.contains_numeric');
			}
			if (password.match(/[\~\!\@\#\$\%\^\&\*\_\-\+\=\`\|\\\(\)\{\}\[\]\:\;\"\'\<\>\,\.\?\/]/)) {
				combination=combination+1;
				//return $.t('validate.password.contains_special');
			}
//			if (userName==password){
//				return $.t('validate.password.differs_email');
//			}
			
			if (combination<2){
				return $.t('validate.password.validation_rule');
			}
			
			return "";
		},

		calculateDate:function(date, days){	
        	var newTime=date.getTime()+days*24*60*60*1000;
        	date.setTime(newTime);
        	var str=Plugins.Common.Utils.dateToString(date);
            return str;
		},
		dateToString:function(date){
			var mm = new String(date.getMonth() + 1);
            var dd = new String(date.getDate());
            var yyyy = date.getFullYear();
            if (mm.length<2) {mm="0"+mm;}
            if (dd.length<2) {dd="0"+dd;}
			return yyyy+"-"+mm+"-"+dd;
		},
		
		stringToDate:function(dateStr){
			var date=new Date();
    		var args=dateStr.split("/");
    		date.setFullYear(args[2]);
    		date.setMonth(parseInt(args[0])-1);
    		date.setDate(args[1]);
    		return date;
		},

		validateNumber:function(value){
    		value=value.replace(/[^\d]/g,'');
    		return value;
    	},
    	validateDecimal:function(value){
    		value=value.replace(/[^\d+(\.\d+)?$]/g,'');
    		return value;
    	},
    	dateToNumber: function(dateStr){
    		var date=new Date();
    		var args=dateStr.split("/");
    		date.setFullYear(args[2]);
    		date.setMonth(parseInt(args[0])-1);
    		date.setDate(args[1]);    		
    		date.setHours(0, 0, 0, 0);    		
    
    		return date.getTime();
    	},
    	
    	dateTimeToNumber: function(dateStr){
    		var date=new Date();
    		var args=dateStr.split("/");
    		date.setFullYear(args[2]);
    		date.setMonth(parseInt(args[0])-1);
    		date.setDate(args[1]);    		
    		date.setHours(args[3], args[4], args[5], 0);    		
    	
    		return date.getTime();
    	},
    	
    	beginDateToNumber: function(dateStr){
    		var date=new Date();
    		var args=dateStr.split("-");
    		date.setFullYear(args[0]);
    		date.setMonth(parseInt(args[1])-1);
    		date.setDate(args[2]);    		
    		date.setHours(0, 0, 1, 0);    		
    		
    		return date.getTime();
    	},
    	
    	endDateToNumber: function(dateStr){
    		var date=new Date();
    		var args=dateStr.split("-");
    		date.setFullYear(args[0]);
    		date.setMonth(parseInt(args[1])-1);
    		date.setDate(args[2]);    		
    		date.setHours(23, 59, 59, 0);    		
    		
    		return date.getTime();
    	},
    	NumberToDate: function(no,split,type){
    		if (!no){return null;}
    		if(!split) split = "/";
    		if(!type) type=0;
    		//var timeZone= new Date().getTimezoneOffset()/60;
    		//no=no-timeZone*3600000;
    		var date=new Date();
            date.setTime(no);
            var mm=date.getMonth()+1;
            if (mm<10){ mm="0"+mm;}
            var dd=date.getDate();
            if (dd<10){ dd="0"+dd;}
            var hh=date.getHours();
            if (hh<10){ hh="0"+hh;}
            var mi=date.getMinutes();
            if (mi<10){ mi="0"+mi;}
            var ss=date.getSeconds();
            if (ss<10){ ss="0"+ss;}
            var str = "";
            switch(type){
            case 0:
            	str=mm+split+dd+split+date.getFullYear();
            	break;
            case 1:
            	str=date.getFullYear()+split+mm+split+dd;
            	break;
            case 2:
            	str=date.getFullYear()+split+mm+split+dd+" "+hh+":"+mi;
            	break;
            case 3:
            	str=date.getFullYear()+split+mm+split+dd+" "+hh+":"+mi+":"+ss;
            	break;
            default:
            	str=mm+split+dd+split+date.getFullYear();
            	break;
            }
    		return str;
    	},
    	NumberToDateTime: function(no){
    		if (!no){return null;}
    		//var timeZone= new Date().getTimezoneOffset()/60;
    		//no=no-timeZone*3600000;
    		var date=new Date();
            date.setTime(no);
            var mm=date.getMonth()+1;
            if (mm<10){ mm="0"+mm;}
            var dd=date.getDate();
            if (dd<10){ dd="0"+dd;}
            var hh=date.getHours();
            var mi=date.getMinutes();
            var str=hh+":"+mi+" "+mm+"/"+dd+"/"+date.getFullYear();
    		return str;
    	},
    	NumberToFullDateTime: function(no){
    		if (!no){return null;}
    		//var timeZone= new Date().getTimezoneOffset()/60;
    		//no=no-timeZone*3600000;
    		var date=new Date();
            date.setTime(no);
            var mm=date.getMonth()+1;
            if (mm<10){ mm="0"+mm;}
            var dd=date.getDate();
            if (dd<10){ dd="0"+dd;}
            var hh=date.getHours();
            var mi=date.getMinutes();
            var ss=date.getSeconds();
            var str=hh+":"+mi+":"+ss+" "+mm+"/"+dd+"/"+date.getFullYear();
    		return str;
    	},
    	getWeekNo : function (yy, mm, dd) { 
    		var date1 = new Date(yy, parseInt(mm) - 1, dd), date2 = new Date(yy, 0, 1), 
    		d = Math.round((date1.valueOf() - date2.valueOf()) / 86400000); 
    		return Math.ceil((d + date2.getDay()) / 7); 
    	},    	
    	compareDate: function(dateStr1,dateStr2){    	
    		var date1 = dateStr1.parseDate();
    		var date2 = dateStr2.parseDate();
    		if(date1.getTime() > date2.getTime()){
    			return true;
    		}
    		return false;
    		/*var args1=dateStr1.split("/");
    		var args2=dateStr2.split("-");
    		if (parseInt(args1[2])==parseInt(args2[0]) && parseInt(args1[1])==parseInt(args2[2]) && parseInt(args1[0])==parseInt(args2[1])){
    			return true;
    		}
    		return false; */   	
    	},
    	getLocalTimeZone: function(){
    		var timeZone= new Date().getTimezoneOffset()/60*-1;
    		if (timeZone>=0){
    			timeZone="+"+timeZone;
    		}
    		return "GMT"+timeZone+":00";
    	},    	
    	validateImage: function(imageName){
    		if (imageName.indexOf(".")==-1){
    			alert($.t('message.invalid_image'));
    			return false;
    		}
    		var args=imageName.split(".");
    		var type=args[1];
    		for (var i=0;i<imgType.length;i++){
    			if (type.toLowerCase()==imgType[i]){
    				return true;
    			}
    		}
    		alert($.t('message.invalid_image'));
			return false;
    	},
    	
    	validateCSV: function(fileName){
    		if (fileName.indexOf(".")==-1){
    			alert($.t('message.invalid_csv'));
    			return false;
    		}
    		var args=fileName.split(".");
    		var type=args[1];
    		if (type.toLowerCase()!="csv"){
    		    alert($.t('message.invalid_csv'));
    		    return false;
    		}
			return true;
    	},
    	
    	loadTemplate: function(){
    		Plugins.Model.Services.loadTemplate({
				success: function(data){		
					var templateName=data.template;
					$("#template_css").attr("href","../css/"+templateName);
				}
			});
    		
    	},
    	
    	getDateRange: function(dateStr,percent,isLast){
    		var date=new Date();
    		var args=dateStr.split("/");
    		date.setFullYear(args[2]);
    		date.setMonth(parseInt(args[0])-1);
    		date.setDate(args[1]);    	
    		var hh=percent*24;
    		hh=hh.toFixed(2);
    		date.setHours(hh, 0, 0, 0);
    		
    		var result=date.getTime();
    		if (isLast){
    			result+=86400000;
    		}
    		return result;
    	},
    	getWeekRange: function(year,weekNo,percent,flag,isLast){
    		var dd=percent*7;
    		dd=dd.toFixed(2);
    		var firstDay= new Date(year, 0, 1);
    		var d=weekNo*7-firstDay.getDay()+parseInt(dd);
    		var time=firstDay.getTime()+86400000*d;    	
    		var date =new Date();    		
    		date.setTime(time);
    		if (flag=="begin"){
    		    date.setHours(0, 0, 0, 0);  
    		}
    		else{
    		    date.setHours(23, 59, 59, 0); 
    		}
    		
    		var result=date.getTime();
    		if (isLast){
    			result+=86400000*7;
    		}
    		return result;
    	},
    	getMonthRange: function(year,month,percent,flag,isLast){
    		var dd=percent*30;
    		dd=dd.toFixed(2);
    		var endDate = new Date(year,month,0); 
    		var endDay=endDate.getDate();
    		if (dd<1){
    			dd=1;
    		}
    		else if (dd>endDay){
    			dd=endDay;
    		}
    		var date=new Date(year,month-1,dd);
    		if (flag=="begin"){
    		    date.setHours(0, 0, 0, 0);  
    		}
    		else{
    		    date.setHours(23, 59, 59, 0); 
    		}  
    		
    		var result=date.getTime();
    		if (isLast){
    			result+=86400000*30;
    		}
    		return result;
    	},
    	
    	preloadImage: function(){		
		    var img;		    	
		    img= new Image();
		    img.src="../img/hs.png";
		    img= new Image();
		    img.src="../img/active.png";
		    img= new Image();
		    img.src="../img/add-members.png";
		    img= new Image();
		    img.src="../img/admin-avatar.png";
		    img= new Image();
		    img.src="../img/alert.png";
		    img= new Image();
		    img.src="../img/alert-active.png";
		    img= new Image();
		    img.src="../img/avatar-default.png";
		    img= new Image();
		    img.src="../img/date-icon.png";
		    img= new Image();
		    img.src="../img/icon-side.png";
		    img= new Image();
		    img.src="../img/level_green.png";
		    img= new Image();
		    img.src="../img/level_red.png";
		    img= new Image();
		    img.src="../img/nav.png";
		    img= new Image();
		    img.src="../img/sprite24.png";
		    img= new Image();
		    img.src="../img/sprite32.png";
		    img= new Image();
		    img.src="../img/sub-head.png";
		    img= new Image();
		    img.src="../img/upload.png";
		    img= new Image();
		    img.src="../img/success.png";
		    img= new Image();
		    img.src="../img/loading-big.gif";
	    },
	    
	    getColorByLevel:function(messageLevel){	    	
	    	if (messageLevel=="INFO"){
	    	    return "basic-span-green";
	    	}
	    	else if (messageLevel=="WARNING"){
	    	    return "basic-span-red";
	    	}
	    	else if (messageLevel=="ERROR"){
	    	    return "basic-span-purple";
	    	}  
	    	else{
	    		return "basic-span-green";
	    	}
	    },
	    
	    getSubArray: function(arrayObj,objPropery,objValue)
        {
            return $.grep(arrayObj, function(cur,i){
                return cur[objPropery]==objValue;
            });
        },
        
        removeSubArray: function(arrayObj,objPropery,objValue)
        {
            return $.grep(arrayObj, function(cur,i){
                return cur[objPropery]!=objValue;
            });
        },
        
        replaceReg: function(str){ 
        	var reg = /\b(\w)|\s(\w)/g; 
            str = str.toLowerCase(); 
            return str.replace(reg,function(m){return m.toUpperCase()}) 
        },
        
        replaceTrim: function(str){
        	var reg = /\s/g;
        	return str.replace(reg,"");
        },
        
        formatMobile: function(str){
        	if (str.indexOf("(")==-1 && str.indexOf(")")==-1 && str.length==3)
		    {
			    str="("+str+")";
		    }
		    if (str.indexOf("-")==-1 && str.length==9){
			    str+="-";
		    }
		    return str;
        },
        
        formatPoints: function(points){
        	var str=new String(points);
        	return str.replace(/(^|[^\w.])(\d{4,})/g, function($0, $1, $2){
                return $1 + $2.replace(/\d(?=(?:\d\d\d)+(?!\d))/g,"$&,");
            });
        },
        
        validatePoints:function(value){
    		value=value.replace(/[^\d]/g,'');
    		
    		return value.replace(/(^|[^\w.])(\d{4,})/g, function($0, $1, $2){
                return $1 + $2.replace(/\d(?=(?:\d\d\d)+(?!\d))/g,"$&,");
            });
    	},
    	
    	restorePoints:function(points){
    		var str=new String(points);
    		str=str.replace(/,/g,"");
    		return parseInt(str);
    	},
    	
    	formatNum: function(inputs){
    		return inputs.replace(/[^\d]/g,'');
        },
        
        stopPropagation:function(e){
    		if (e.stopPropagation) 
    			e.stopPropagation();
    		else
    			e.cancelBubble=true;
    	},
    	
    	getCurrentDate:function(){
    		return currentDate;
    	},
    	
    	showTime:function(date){
    		 currentDate = new Date(parseFloat(date));
    		 Plugins.Common.Utils.run();
    		 INTERVALID = setInterval(Plugins.Common.Utils.run, 1000); 
    	},
    	
    	run:function(){
    		currentDate.setSeconds(currentDate.getSeconds()+1); 
            var time = "";
            var year = currentDate.getFullYear();
            var month = currentDate.getMonth() + 1;
            var day = currentDate.getDate();
            var hour = currentDate.getHours();
            var minute = currentDate.getMinutes();
            var second = currentDate.getSeconds();
            time = $.t("date.todayis")+year+$.t("date.yearname")+month+$.t("date.monthname")+day+$.t("date.dayname")+" ";
            time+=(hour-12)>=0?$.t("date.next_day"):$.t("date.prev_day");
            hour = (hour-12)>0?(hour-12):hour;
            if(hour < 10){
                time += "0" + hour;
            }else{
                time += hour;
            }
            time += $.t("date.time.hour");
            if(minute < 10){
                time += "0" + minute;
            }else{
                time += minute;
            }
            time += $.t("date.time.minute");
            if(second < 10){
                time += "0" + second+$.t("date.time.second");
            }else{
                time += second+$.t("date.time.second");
                }
            $(".nowtime").text(time);
    	},
    	/**
    	 * @param treeEl 树DOC
    	 * @param childrenNodeKey 当前修改节点key
    	 * @param parentNodeKey 父节点key
    	 * @param childrenPrefix 当前修改节点前缀
    	 * @param parentPrefix 父节点前缀
    	 * @param rootAddClass 根节点addClass
    	 * @param rootPrefix 根节点前缀
    	 * @param rootNodeKey 根节点key
    	 * @param sequence 当前修改节点的顺序
    	 * @param newNodeTitle 当前修改节点的标题
    	 * @param newNodeAddClass 当前修改节点的addClass
    	 */
    	updateTree : function(treeEl,childrenNodeKey,parentNodeKey,childrenPrefix,parentPrefix,rootAddClass,rootPrefix,rootNodeKey,sequence,newNodeTitle,newNodeAddClass){
    		sequence=sequence?sequence:1;
    		var node = treeEl.dynatree("getTree").getNodeByKey(childrenPrefix+childrenNodeKey);
			if(node){
				node.setTitle(newNodeTitle);
				var oldpNode = node.getParent();
				if(oldpNode.data.addClass != rootAddClass){
					if(parentNodeKey && parentNodeKey != oldpNode.data.key.replace(parentPrefix,"")){
						var newpNode = treeEl.dynatree("getTree").getNodeByKey(parentPrefix+parentNodeKey);
						Plugins.Common.Utils.moveNode(node,newpNode,sequence);
					} else if(!parentNodeKey){
						var newpNode = treeEl.dynatree("getTree").getNodeByKey(rootPrefix+rootNodeKey);
						Plugins.Common.Utils.moveNode(node,newpNode,sequence);
					} else {
						var pNode = treeEl.dynatree("getTree").getNodeByKey(parentPrefix+parentNodeKey);
						var minBiggerNode = Plugins.Common.Utils.getNextNode(pNode.childList, sequence);
						if(minBiggerNode == -1) {
							node.move(pNode, 'child');
						} else if(minBiggerNode == 0){
							node.move(pNode.childList[pNode.childList.length-1], 'after');
						} else {
							node.move(minBiggerNode, 'before');
						}
					}
				} else if(parentNodeKey){
					var newpNode = treeEl.dynatree("getTree").getNodeByKey(parentPrefix+parentNodeKey);
					Plugins.Common.Utils.moveNode(node,newpNode,sequence);
				} else {
					var pNode = treeEl.dynatree("getTree").getNodeByKey(rootPrefix+rootNodeKey);
					var minBiggerNode = Plugins.Common.Utils.getNextNode(pNode.childList, sequence);
					if(minBiggerNode == -1) {
						node.move(pNode, 'child');
					} else if(minBiggerNode == 0){
						node.move(pNode.childList[pNode.childList.length-1], 'after');
					} else {
						node.move(minBiggerNode, 'before');
					}
				}
				/*if(node.isActive()){
					node.deactivate();
				}
				node.activate();*/
			} else {
				if(parentNodeKey){
					var pNode = treeEl.dynatree("getTree").getNodeByKey(parentPrefix+parentNodeKey);
					if(pNode){
						if(pNode.isExpanded()){
							var newNode={
								key:childrenNodeKey+childrenNodeKey,
								title:newNodeTitle,
								isFolder:false,
								expand:false,
								sequence:sequence,
								isLazy:false,
								addClass:newNodeAddClass,
								children:[]
							};
							var minBiggerNode = Plugins.Common.Utils.getNextNode(pNode.childList, sequence);
							if(minBiggerNode != -1 && minBiggerNode != 0) {
								pNode.addChild(newNode,minBiggerNode);
							} else {
								pNode.addChild(newNode);
							}
						} else {
							if(pNode.data.isFolder){
								pNode.resetLazy();
							} else {
								pNode.data.isLazy=true;
								pNode.data.isFolder=true;
							}
							pNode.expand(true);
						}
						//treeEl.dynatree("getTree").getNodeByKey(childrenPrefix+childrenNodeKey).activate();
					}
				}
			}
    	},
    	
    	moveNode : function(node,pNode,sequence){
    		if(pNode){
				if(pNode.isExpanded()){
					var minBiggerNode = Plugins.Common.Utils.getNextNode(pNode.childList, sequence);
					if(minBiggerNode == -1) {
						node.move(pNode, 'child');
					} else if(minBiggerNode == 0){
						node.move(pNode.childList[pNode.childList.length-1], 'after');
					} else {
						node.move(minBiggerNode, 'before');
					}
				} else {
					if(pNode.data.isFolder){
						pNode.resetLazy();
					} else {
						pNode.data.isLazy=true;
						pNode.data.isFolder=true;
					}
					pNode.expand(true);
					node.remove();
				}
			} else {
				node.remove();
			}
    	},
    	
		getNextNode:function(nodeList, sequence){
			if(nodeList && nodeList.length > 0) {
				var biggerList = new Array();
				for(index in nodeList) {
					if(nodeList[index].data.sequence > sequence) {
						biggerList.push(nodeList[index]);
					}
				}
				if(biggerList.length != 0) {
					minBiggerNode = biggerList[0];
					for(var i=1; i<biggerList.length; i++) {
						if(minBiggerNode.data.sequence > biggerList[i].data.sequence) {
							minBiggerNode = biggerList[i];
						}
					}
					return minBiggerNode;
				} else {
					return 0;
				}
			} else {
				return -1;
			}
		},		
		
		createDepartmentDropdown:function(el,selectedDepartmentId,selectedDepartmentName){
			Plugins.Model.Services.getDepartmentsTree({
			    success:function(data){
			    	el.plugins_dropdown_tree({
			    		selectedId:selectedDepartmentId,
			    		selectedName:selectedDepartmentName,
			    		iniData:data.departments,
			    		loadTreeCallback:function(node){
			    			var key=node.data.key;
			    			Plugins.Model.Services.getDepartmentsChildTree({
			    	  		    departmentId:key,
			    	            success:function(data){
			    	          	  if (data.departments.length>0){
			    	  		          node.addChild(data.departments);
			    	          	  }
			    	          	  else{					            		  
			    	          		  node.setLazyNodeStatus(0);
			    	          	  }
			    	  	      }
			    	  	  });
			    	}});
			    }
			});			
		},
		
		createProjectDropdown:function(el,selectedprojectId,selectedProjectName){
			Plugins.Model.Services.getModelProjectsTree({
		    	searchByUser:true,
		        success:function(data){
		        	el.plugins_dropdown_tree({
			    		selectedId:selectedprojectId,
			    		selectedName:selectedProjectName,
			    		iniData:data.tree,
			    		loadTreeCallback:function(node){
			    	    },
			    	    checkNode:function(node){
			    	    	 var key=node.data.key;
			    	    	 if(key.indexOf("project_") == -1){
			    	    		 alert($.t("message.permission.select_project"));
			    	    		 return false;
			    	    	 }
			    	    	 return true;
			    	    }
			    	    });
		        }
		    });					
		},
		
		createTestBasisDropdown:function(el,versionId,selectedId,selectedName,testBasisId){
			Plugins.Model.Services.getTestBasisTree({
				versionId:versionId,
				testBasisId:testBasisId,
			    success:function(data){
			    	el.plugins_dropdown_tree({
			    		iniData:data.tree,
			    		selectedId:selectedId,
			    		selectedName:selectedName,
			    		loadTreeCallback:function(node){
			    			var key=node.data.key;
			    			key=key.replace("testbasis_","");
			    			Plugins.Model.Services.getTestBasisTreeChild({
			    		    	testBasisTypeOrId:key,
			    		    	testBasisId:testBasisId,
			    		    	nameorcode:"$",
			    		    	versionId:versionId,
			    				success:function(data){
			    					if (data.testbasisNodes.length>0){
			    	  		            node.addChild(data.testbasisNodes);
			    	          	    }
			    	          	    else{					            		  
			    	          		    node.setLazyNodeStatus(DTNodeStatus_Ok);
			    	          	    }
			    				}
			    	  	  });
			    	}});
			    }
			});			
		},
		
		createTestReqDropdown:function(el,projectId,versionId,testProcessOrpTestReqId,loadAll,selectedId,selectedName){
			Plugins.Model.Services.getSelectTestReqTree({
				projectId:projectId,
		    	versionId:versionId,
		    	testProcessOrpTestReqId:testProcessOrpTestReqId,
		    	loadAll:loadAll,
			    success:function(data){
			    	el.plugins_dropdown_tree({
			    		iniData:data.tree,
			    		selectedId:selectedId,
			    		selectedName:selectedName,
			    		loadTreeCallback:function(node){
			    			var key=node.data.key;
			    			key=key.replace("type_","req_");
			    			Plugins.Model.Services.getSelectTestReqTree({
			    				projectId:projectId,
			    		    	versionId:versionId,
			    		    	testProcessOrpTestReqId:key,
			    		    	loadAll:loadAll,
			    				success:function(data){
			    					if (data.tree.length>0){
			    	  		            node.addChild(data.tree);
			    	          	    }
			    	          	    else{					            		  
			    	          		    node.setLazyNodeStatus(DTNodeStatus_Ok);
			    	          	    }
			    				}
			    	  	  });
			    	}});
			    }
			});			
		},
		
		createTestReqNotCustomDropdown:function(el,projectId,versionId,testProcessOrpTestReqId,testreqId,pTestReqId,pTestReqName){
			Plugins.Model.Services.getSelectTestReqTreeNotCustom({
				projectId:projectId,
		    	versionId:versionId,
		    	testProcessOrpTestReqId:testProcessOrpTestReqId,
		    	testreqId:testreqId,
			    success:function(data){
			    	el.plugins_dropdown_tree({
			    		iniData:data.tree,
			    		selectedId:pTestReqId,
			    		selectedName:pTestReqName,
			    		loadTreeCallback:function(node){
			    			var key=node.data.key;
			    			key=key.replace("type_","req_");
			    			Plugins.Model.Services.getSelectTestReqTreeNotCustom({
			    				projectId:projectId,
			    		    	versionId:versionId,
			    		    	testProcessOrpTestReqId:key,
			    		    	testreqId:testreqId,
			    				success:function(data){
			    					if (data.tree.length>0){
			    	  		            node.addChild(data.tree);
			    	          	    }
			    	          	    else{					            		  
			    	          		    node.setLazyNodeStatus(DTNodeStatus_Ok);
			    	          	    }
			    				}
			    	  	  });
			    	}});
			    }
			});			
		}
		
    
	}, {
		init: function() {	
		    
		}
	});
});
