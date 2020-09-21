steal( 'jquery/controller','jquery/view/ejs' )
	.then( './views/init.ejs',
'css/jquery.ui.datepicker.css',
			function($){
/**
 * @class Plugins.TimePicker
 */
$.Controller('Plugins.TimePicker',
/** @Static */
{
	/** @Static */
	defaults: {
		mode:0,
		minTime:0,
		maxTime:23,
		minMinute:0,
		maxMinute:59,
		defaultHour:"00",
		defaultMinute:"00"
	}
}, {
	/** @Prototype */
	init: function(){
		var env=this;
		var mode = this.options.mode;
		var minTime = this.options.minTime;
		var maxTime = this.options.maxTime;
		var minMinute = this.options.minMinute;
		var maxMinute = this.options.maxMinute;
		var _defaultHour = this.options.defaultHour;
		var _defaultMinute = this.options.defaultMinute;
		
		var selectTimes = [];
		for(var i=0;i<$times.length;i++){
			if(parseInt($times[i])>=minTime && parseInt($times[i])<=maxTime){
				selectTimes.push({value:$times[i],name:$times[i]});
			}
		}
		
		var selectMinutes = [];
		for(var i=0;i<$minutes.length;i++){
			if(parseInt($minutes[i])>=minMinute && parseInt($minutes[i])<=maxMinute){
				if(mode==0){
					selectMinutes.push({value:$minutes[i],name:$minutes[i]});
				}
				 if(mode==1 && minutes[i] % 10 == 0){
					 selectMinutes.push({value:$minutes[i],name:$minutes[i]});
				}
				 if(mode==2 && minutes[i] % 30 == 0){
					 selectMinutes.push({value:$minutes[i],name:$minutes[i]});
				}
			}
		}
		
		env.element.click(function(){
			var time = $(env.element).val();
			var defaultHour=time?time.split(":")[0]:_defaultHour;		
			var defaultMinute=time?time.split(":")[1]:_defaultMinute;
			var width = parseInt($(env.element).css("width").split("p")[0]);
			var height = parseInt($(env.element).css("height").split("p")[0]);
			var y = $(env.element).position().top;
			var x = $(env.element).position().left;
			$("#timepicker").remove();
			$(env.element).parent().append("//plugins/time_picker/views/init.ejs",{},function(){
				
				$("#hour").plugins_components({options:selectTimes,label:"",defaultValue:defaultHour,choose:false,width:"50"});
				$("#hour").controller().select();
				
				$("#minute").plugins_components({options:selectMinutes,label:"",defaultValue:defaultMinute,choose:false,width:"50"});
				$("#minute").controller().select();
				
				$("#timepicker").css("top",y+height+2);
				$("#timepicker").css("left",x+20);
				$("#timepicker").i18n();
				
				$("#timepicker #select_time").click(function(event){  
					$(env.element).val($("#timepicker #hour").controller().getValue()+":"+$("#timepicker #minute").controller().getValue()); 
					$("#timepicker").remove();
				});
				
				$("#timepicker select").click(function(event){  
				    event=event||window.event;  
				    event.stopPropagation();  
				}); 
				
				$("#timepicker").click(function(event){  
				    event=event||window.event;  
				    event.stopPropagation();  
				});
			});
		});
		
		$(document).click(function(event){         
			if($(event.target).is("input.plugins_time_picker")){
				return;
			}
			$("#timepicker").remove(); 
		}); 
	}
});
});