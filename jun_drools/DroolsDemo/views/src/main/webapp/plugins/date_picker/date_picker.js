steal( 'jquery/controller','jquery/view/ejs' )
	.then( './views/init.ejs',
/*'plugins/jqueryui/ui/jquery-ui-timepicker-addon.js', */
'plugins/jqueryui/ui/jquery.ui.datepicker.js', 
/*'plugins/jqueryui/ui/jquery-ui-sliderAccess.js',*/
'css/jquery.ui.datepicker.css',
			function($){
/**
 * @class Plugins.DatePicker
 */
$.Controller('Plugins.DatePicker',
/** @Static */
{
	/** @Static */
	defaults: {	
		defaultValue:"",
		changeMonth: true,
        changeYear: true,
        yearRange:'c-10:c+10',
        pickerType:"datepicker",
        model:"",
        isFormDate:false
	}
}, {
	/** @Prototype */
	init: function(){
		var env=this;
		var defaultValue=this.options.defaultValue;		
		var changeMonth=this.options.changeMonth;
		var changeYear=this.options.changeYear;
		var callback=this.options.callback;
		var yearRange=this.options.yearRange;
		var pickerType = this.options.pickerType;
		var model = this.options.model;
		$.datepicker.regional['zh-CN'] = {
			//	closeText: $.t("button.close"),
				prevText: '&#x3C;'+$.t("date.prev_month"),
				nextText: $.t("date.next_month")+'&#x3E;',
			//	currentText: $.t("date.today"),
				monthNames: [$.t("date.month.jan"),$.t("date.month.feb"),$.t("date.month.mar"),$.t("date.month.apr"),$.t("date.month.may"),$.t("date.month.jun"),
				             $.t("date.month.jul"),$.t("date.month.aug"),$.t("date.month.sep"),$.t("date.month.oct"),$.t("date.month.nov"),$.t("date.month.dec")],
				monthNamesShort: [$.t("date.month.jan"),$.t("date.month.feb"),$.t("date.month.mar"),$.t("date.month.apr"),$.t("date.month.may"),$.t("date.month.jun"),
						             $.t("date.month.jul"),$.t("date.month.aug"),$.t("date.month.sep"),$.t("date.month.oct"),$.t("date.month.nov"),$.t("date.month.dec")],
				dayNames: [$.t("date.week.sun"),$.t("date.week.mon"),$.t("date.week.tue"),$.t("date.week.wed"),$.t("date.week.thu"),$.t("date.week.fri"),$.t("date.week.sat")],
				dayNamesShort: [$.t("date.week.sun"),$.t("date.week.mon"),$.t("date.week.tue"),$.t("date.week.wed"),$.t("date.week.thu"),$.t("date.week.fri"),$.t("date.week.sat")],
				dayNamesMin: [$.t("date.week.sun"),$.t("date.week.mon"),$.t("date.week.tue"),$.t("date.week.wed"),$.t("date.week.thu"),$.t("date.week.fri"),$.t("date.week.sat")],
				weekHeader: $.t("date.weekname"),
				dateFormat: 'yy-mm-dd',
				firstDay: 1,
				isRTL: false,
				showMonthAfterYear: true,
				yearSuffix: $.t("date.yearname")};
		$.datepicker.setDefaults($.datepicker.regional['zh-CN']);
		
		/*$.timepicker.regional['zh-CN'] = {
				closeText: $.t("button.close"),
				currentText: $.t("date.today"),
				timeOnlyTitle:$.t("date.choose_time"),
				timeFormat: "HH:mm",
				hourText:$.t("date.time.hour"),
				minuteText:$.t("date.time.minute"),
				secondText:$.t("date.time.second"),
				isRTL: false};
		$.timepicker.setDefaults($.timepicker.regional['zh-CN']);*/
		
		this.element.html("//plugins/date_picker/views/init.ejs", {
			name:this.options.name,
			model:model
		}, function(){
			/*if("datetimepicker" == pickerType){
				env.find(".datepicker").datetimepicker({
					buttonImage: "../img/date-icon-25.png",
					showOn: "both",			
				    changeMonth: changeMonth,
	                changeYear: changeYear,
	                yearRange: yearRange,
	                showTime:false,
	                showButtonPanel:false
	            });	
			}else*/ if("datepicker" == pickerType){
				env.find(".datepicker").datepicker({
					buttonImage: "../image/pop_up_windows/date_icon.png",
					showOn: "both",			
				    changeMonth: changeMonth,
	                changeYear: changeYear,
	                yearRange: yearRange
	            });	
			}/*else if("timepicker" == pickerType){
				env.find(".datepicker").timepicker({	
	                showTime:false,
	                showButtonPanel:false
	            });	
			}*/
						
			//env.find(".datepicker").datepicker( "option", $.datepicker.regional[ "zh-CN" ] );
			env.find(".datepicker").attr("value",defaultValue);
			env.find(".datepicker").i18n();
			if (env.options.isFormDate){
				env.find(".datepicker").addClass("form-date");
			}
		});
	},
	
	getDate: function(){
		return this.find(".datepicker").attr("value");
	},
	
	setDate: function(date){
		var defaultValue=this.options.defaultValue;
		if (!date){
			date=defaultValue;
		}
		return this.find(".datepicker").attr("value",date);
	},
	
	'.datepicker keydown':function(el, ev){
		if(8==ev.keyCode){  //BackSpace 
			el.val("");
		}
		return false;
	}
});
});