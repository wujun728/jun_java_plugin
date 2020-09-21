steal( 'jquery/controller','jquery/view/ejs' )
	.then( './views/init.ejs', function($){

/**
 * @class Plugins.Components.Controller.Select
 */
$.Controller('Plugins.Components.Controller.Select.Default',
/** @Static */
{
	defaults : {}
},
/** @Prototype */
{
	init : function(){
		
	},
	
	show:function(){
		var env = this;
		$(env.element).controller().destroy();
		if(env.options.choose){
			env.options.options.unshift({value:"",name:$.t("common.choose")});
		}
		this.element.html("//plugins/components/controller/select/views/init.ejs",{
			options: env.options.options,
			label:env.options.label,
			defaultValue:env.options.defaultValue,
			css:env.options.css
			},function(){
			env.element.i18n();
		});
		
		$(env.element).click(function(event){  
		    event=event||window.event;
		    event.stopPropagation();  
		});
	},
	
	'#select_content click':function(el,ev){
		var env = this;
		if($(el).attr("disabled")) return;
		
		var currentShow = $(env.element).find("#select_options").is(":visible"); //if show then hide
		$("div[id='select_options']").hide();
		if(currentShow) return;
		
		var parent = el.closest("#timepicker");
		if(parent.length == 0) $("#timepicker").remove();

		var width = parseInt($(env.element).find("#select_content").css("width").split("p")[0]);
		var height = parseInt($(env.element).find("#select_content").css("height").split("p")[0]);
		var y = $(env.element).find("#select_content").position().top;
		var x = $(env.element).find("#select_content").position().left;
		
		var left = parseInt($(el).css("margin-left").replace("px",""));
		$(env.element).find("#select_options").css("top",y+height+2);
		$(env.element).find("#select_options").css("left",x+left).css("width",width+10);
		$(env.element).find("#select_options .option").removeClass("option-selected");
		$(env.element).find("#select_options .option[value='"+$(env.element).find("#select_content p.selected").attr("value").trim()+"']").addClass("option-selected");
		$(env.element).find("#select_options").show();
		$(env.element).find("#select_options").i18n();
				
		$(document).one( "click", function(event) {
			$(env.element).find("#select_options").hide(); 
		});
	},
	
	'div.option click':function(el, ev){
		var parent = el.parent();
		var prev = parent.prev().prev();
		var value = el.attr("value");
		var name = el.text().trim();

		$(prev).find('p.selected').attr("value",value).text(name);
		$(parent).hide();
	},
	
	getValue:function(){
		var env = this;
		return $(env.element).find("#select_content p.selected").attr("value").trim();
	},
	
	getText:function(){
		var env = this;
		return $(env.element).find("#select_content p.selected").text().trim();
	},

	setValue:function(value,text,disabled){
		var env = this;
		$(env.element).find("#select_content").attr("disabled",disabled);
		$(env.element).find("#select_content p.selected").attr("value",value).text(text);
	}
})

});