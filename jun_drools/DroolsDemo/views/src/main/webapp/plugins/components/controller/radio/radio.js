steal( 'jquery/controller','jquery/view/ejs' )
	.then( './views/init.ejs', function($){

/**
 * @class Plugins.Components.Controller.Radio
 */
$.Controller('Plugins.Components.Controller.Radio.Default',
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
		var groupName = Math.random();
		$(env.element).controller().destroy();
		env.element.html("//plugins/components/controller/radio/views/init.ejs",{
			radios: env.options.radios,
			groupName:groupName,
			defaultValue:env.options.defaultValue
		},function(){
			env.element.i18n();
		});
	},
	
	'label.radio-label-class click':function(el, ev){
		el.prev().click();
	},
	
	'.radio-class change':function(el, ev){
		if($(el).is(":checked")){
			$(env.element).find(".radio-selected:not(:checked)").removeClass("radio-selected");
			$(el).next("label").addClass("radio-selected");
		}
	}
})

});