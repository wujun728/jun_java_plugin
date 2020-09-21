steal('jquery/controller', 'jquery/view/ejs').then(			
		'plugins/js/jquery.imgareaselect.js',
		'css/imgareaselect-animated.css', './views/init.ejs', function($) {			
			var selectedImg ;
			
			/**
			 * @class Plugins.ImgSelect
			 */
			$.Controller('Plugins.ImgSelect',
			/** @Static */
			{
				defaults : {
					maxWidth : 800,
					maxHeight : 600,
					minWidth : 108,
					minHeight : 69,		
					aspectRatio:"1:1",
					originalSrc:'',
					originalWidth : 800,
					originalHeight : 600,
					result:{}
				}
			},
			/** @Prototype */
			{
				init : function() {
					
				},
				
				show: function(){
					var env=this;
					var maxWidth=this.options.maxWidth;
					var maxHeight=this.options.maxHeight;	
					var minWidth=this.options.minWidth;
					var minHeight=this.options.minHeight;	
					var aspectRatio=this.options.aspectRatio;	
					var originalSrc=this.options.originalSrc;
					this.element.html("//plugins/img_select/views/init.ejs", {
						originalSrc : originalSrc
					}, function() {						
						selectedImg = $('#srcPhoto').imgAreaSelect({
							classPrefix: 'imgareaselect',
				            movable: true,
				            parent: 'body',
				            resizable: true,
				            resizeMargin: 10,
							maxWidth : maxWidth,
							maxHeight : maxHeight,
							minWidth : minWidth,
							minHeight : minHeight,
							aspectRatio:aspectRatio,
							handles : true,
							instance : true,							
							onSelectEnd : function(img, selection) {
								env.selectArea(selection);
							}
						}); 
						
						env.setOriginalSize();	
						$(".popup-image").i18n();						
					});
				},
				
				selectArea: function(selection){
					if (!selection.width || !selection.height)
						return;

					var newWidth = $('#srcPhoto').width();
					var newHeight = $('#srcPhoto').height();
					var env=this;			
					env.options.result.width=newWidth;
					env.options.result.height=newHeight;
					env.options.result.marginLeft=-selection.x1;
					env.options.result.marginTop=-selection.y1;
								
					var imgWidth=parseInt($("#srcPhoto").width());										
					selection.scale=imgWidth/env.options.originalWidth;
					env.options.selection=selection;
				},
				
				moveImage: function(){
					var env=this;
					var fullWidth=parseInt($(document).width());
					var imgWidth=parseInt($("#srcPhoto").width());
					var imgHeight=parseInt($("#srcPhoto").height());
					var leftWidth=fullWidth/2-imgWidth/2;					
					$(".popup-image").css("left",leftWidth);
					if (parseInt(imgWidth)<700){
						$(".popup-image").css("width",parseInt(imgWidth)+100);							
					}
					else{
						$(".popup-image").css("width",parseInt(imgWidth));							
					}
					
					$("#slider_td").css("width",parseInt(imgWidth)+60);	
					var topX=(imgWidth-env.options.minWidth)/2;
					var topY=(imgHeight-env.options.minHeight)/2;
					if (topY<0){topY=30;}

					selectedImg.setSelection(topX, topY, env.options.minWidth+topX, env.options.minHeight+topY, true);
					selectedImg.setOptions({ show: true });
					selectedImg.update();
					env.selectArea(selectedImg.getSelection());
				},	
				
				resizeImg: function(scale){	
					var env=this;
					if ((env.options.originalWidth * scale>(env.options.minWidth+600) && scale>1) || (env.options.originalWidth * scale<this.options.minWidth && scale<1) || env.options.originalHeight * scale<this.options.minHeight){
						return;
					}					
					selectedImg.cancelSelection();
					selectedImg.update();					
					$('#srcPhoto').css({
						"width" : env.options.originalWidth * scale,
						"height" : env.options.originalHeight * scale
					});			
					env.moveImage();				
				},

				'#increaseBtn click' : function() {					
					var newValue=$("#slider").slider("value")+100;
					$("#slider").slider("value", newValue);
				},

				'#reduceBtn click' : function() {				
					var newValue=$("#slider").slider("value")-100;
					$("#slider").slider("value", newValue);
				},

				setOriginalSize : function() {	
					var env=this;
				    var img = new Image();				    
                    img.onload = function(){
                        env.options.originalWidth = img.width;
					    env.options.originalHeight = img.height;	
					    var imgWidth=parseInt($("#srcPhoto").width());
					    var imgHeight=parseInt($("#srcPhoto").height());
					    var maxSliderValue=imgWidth;
					    var minSliderWidth=env.options.minWidth;
					    if (imgWidth>(env.options.minWidth+600)){
					    	maxSliderValue=env.options.minWidth+600;
					    }
					    $("#slider").slider({	
					    	value:maxSliderValue,
			                min: minSliderWidth,
			                max: env.options.minWidth+600,
			                step: 10,
			                change: function( event, ui ) {	
				                var scale=ui.value/env.options.originalWidth;	
				                env.resizeImg(scale);			                
			                }
		                });
					    while (imgWidth>env.options.minWidth+600){
						    $("#reduceBtn").click();	
						    imgWidth=parseInt($("#srcPhoto").width());
					        imgHeight=parseInt($("#srcPhoto").height());
					    }
					    while (imgWidth<env.options.minWidth){
						    $("#increaseBtn").click();	
						    imgWidth=parseInt($("#srcPhoto").width());
					        imgHeight=parseInt($("#srcPhoto").height());
					    }
					    env.moveImage();						    
                    }  
                    img.src = $("#srcPhoto").attr("src");
				},
				
				'#confirmImgBtn click': function(){		
					var env=this;
				    if (typeof(this.options.selection)=="undefined"){
				    	alert($.t("message.image_area_required"));
				    	return;
				    }
				   
					this.options.callback(this.options.result,this.options.selection);
					selectedImg.cancelSelection();
					selectedImg.update();
					this.element.html("");
					env.clearDiv();
				},
				
				clearDiv: function(){
					$(".imgareaselect-outer").remove();
					$(".imgareaselect-selection").parent().remove();
				}
			})

		});