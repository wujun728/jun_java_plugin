steal( 'jquery/controller','jquery/view/ejs' )
	.then( './views/init.ejs',
			'./controller/select/select.js',
			'./controller/checkbox/checkbox.js',
			'./controller/radio/radio.js',
			function($){

/**
 * @class Plugins.Components
 */
$.Controller('Plugins.Components',
/** @Static */
{
	defaults : {}
},
/** @Prototype */
{
	init : function(){
		
	},
	
	select:function(){
		var env = this;
		$(env.element).plugins_components_select_default({options:env.options.options,label:env.options.label,
			defaultValue:env.options.defaultValue,choose:env.options.choose,css:env.options.css?env.options.css:""});
		$(env.element).plugins_components_select_default('show');
	},
	
	checkBox:function(){
		var env = this;
		$(env.element).plugins_components_checkbox_default({checkBoxs:env.options.checkBoxs,defaultValues:env.options.defaultValues});
		$(env.element).plugins_components_checkbox_default('show');
	},
	
	radio:function(){
		var env = this;
		$(env.element).plugins_components_radio_default({radios:env.options.radios,defaultValue:env.options.defaultValue});
		$(env.element).plugins_components_radio_default('show');
	}
})

});