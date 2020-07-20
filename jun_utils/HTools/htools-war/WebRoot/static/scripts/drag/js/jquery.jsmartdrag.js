(function($){
	    var move = false;
	    var cloneDiv = null;
	    var targetObj = null;
	    var currentObj = null;
	    var targetSelected = null;
	    var selected = false;
	    var scrollTop = 0;
	    $.fn.jsmartdrag = function(options){
	        var jQueryObj = this;      
	        var defaults = {
	            sourceClass:"jsmartdrag-source",
	            sourceHoverClass:"jsmartdrag-source-hover",
	            cursorHoverClass:"jsmartdrag-cursor-hover",
	            targetHoverClass:"jsmartdrag-target-hover",
	            canSelect:false,
	            target:".target",          
	            onDrag:    function(){},
	            afterDrag: function(selected,currentObj,targetObj){},
				draging:   function(targetObj){}
	        };
	        options = $.extend(defaults, options);
	         
	        this.each(function(){
	            $("body").css("-moz-user-select","none");
	            if(!defaults.canSeletct){
	                document.ondragstart = function () { return false; }; //禁止浏览器的拖拽行为
	                document.onselectstart = function () {return false; };//禁止浏览器的选中行为
	            }
	            if(options.target!=null){
	                targetObj = $(options.target);
	            }          
	            $(this).addClass(options.sourceClass);
	            $(this).mousedown(function(){
	                currentObj = $(this);
	                move = true;
	                $(this).addClass(options.sourceHoverClass);
	                cloneDiv = $(this).clone();
	                cloneDiv.attr("id","cloneDiv");
	                cloneDiv.addClass(options.sourceHoverClass);
	                scrollTop = $("html,body").scrollTop();//当鼠标点击的时候才计算滚动的高度，是为了防止页面浏览的时候用户改变了浏览器高度
	            });
	            $(document).mousemove(function(event){
	                if(move){
	                    if($("#cloneDiv").length<=0){
	                       $("body").append(cloneDiv);
						}
	                    var dragPos = {x1:0,x2:0,y1:0,y2:0};
	                    var pageX = 0;
	                    var pageY = 0;
	                    //if($.browser.msie){
	                    if(/msie/.test(navigator.userAgent.toLowerCase())){
	                        pageX = event.clientX;
	                        pageY = event.clientY+scrollTop; 
	                    }else{                 
	                        pageX = event.pageX;
	                        pageY = event.pageY;
	                    }
	                    dragPos.x1 =pageX-cloneDiv.innerWidth()/8;
	                    dragPos.y1 = pageY-cloneDiv.innerHeight()/2;
	                    cloneDiv.css({left:dragPos.x1+"px",top:dragPos.y1+"px",position:'absolute',color:'#fff'});
	                    
	                    if(targetObj.length>0){
	                        targetObj.each(function(){                     
	                            if(checkAreaOverride(cloneDiv,$(this))){                               
	                                $(this).addClass(options.targetHoverClass);
	                            }else{
	                                $(this).removeClass(options.targetHoverClass);
	                            }  
	                        });
	                    }  
	                    options.onDrag();
	                }
	            });
	            $(document).mouseup(function(){
	                if(move){
	                    move = false;
	                    if(cloneDiv!=null && targetObj!=null){
	                        if($($("[class$='jsmartdrag-target-hover']")[0]).length>0){
	                            targetSelected =    $($("[class$='jsmartdrag-target-hover']")[0]);
	                            selected = true;
	                        }
	                        options.afterDrag(selected,currentObj,targetSelected);
	                        //恢复初始状态
	                        cloneDiv.remove();
	                        cloneDiv.removeClass(options.cursorHoverClass);
	                        $("[class$='jsmartdrag-target-hover']").each(function(){
	                            $(this).removeClass(options.targetHoverClass);
	                        });                    
	                        currentObj.removeClass(options.sourceHoverClass);                      
	                        currentObj = null;
	                        if(selected == true){
	                            targetSelected.removeClass(options.targetHoverClass);
	                            targetSelected = null;
	                            selected = false;
	                        }                      
	                    }
	                }          
	            });
	        });
	        function checkAreaOverride (_cloneDiv,_targetObj){
	            //这里来判断是否在里面哦！
	            var source_left = _cloneDiv.offset().left;
	            var source_top = _cloneDiv.offset().top;
	            var source_right = _cloneDiv.offset().left+cloneDiv.innerWidth();
	            var source_bottom = _cloneDiv.offset().top+cloneDiv.innerHeight();
	             
	            var target_left = _targetObj.offset().left;
	            var target_top = _targetObj.offset().top;
	            var target_right = _targetObj.offset().left+targetObj.innerWidth();
	            var target_bottom = _targetObj.offset().top+targetObj.innerHeight();
	             
	            var new_area_left = max(source_left,target_left);
	            var new_area_top = max(source_top,target_top);
	            var new_area_right = min(source_right,source_right);
	            var new_area_bottom = min(source_bottom,target_bottom);
	            if(new_area_left<target_right&&new_area_top<target_bottom&&new_area_right>target_left&&new_area_bottom>target_top){
	                $("[class$='jsmartdrag-target-hover']").each(function(){
	                    $(this).removeClass("jsmartdrag-target-hover");
	                });
	                return true;
	            }else{
	                return false;
	            }
	        }
	        function max(_p1,_p2){
	            if(_p1>_p2){
	                return _p1;
	            }else{
	                return _p2;
	            }
	        }
	        function min(_p1,_p2){
	            if(_p1>_p2){
	                return _p2;
	            }else{
	                return _p1;
	            }
	        }
	        return jQueryObj;
	    };
	})(jQuery);