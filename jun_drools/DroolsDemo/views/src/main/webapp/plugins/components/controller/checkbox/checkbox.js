steal( 'jquery/controller','jquery/view/ejs' )
	.then( './views/init.ejs', function($){

/**
 * @class Plugins.Components.Controller.Checkbox
 */
$.Controller('Plugins.Components.Controller.Checkbox.Default',
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
		env.element.html("//plugins/components/controller/checkbox/views/init.ejs",{
			checkBoxs: env.options.checkBoxs,
			defaultValues:env.options.defaultValues
		},function(){
			env.element.i18n();
		});
	},
	
	'label click':function(el, ev){
		el.prev().click();
	},
	
	'.checkbox-class change':function(el, ev){
		console.log($(el).is(":checked"));
		if($(el).is(":checked")){
			$(el).next("label").addClass("checkbox-selected").removeClass("checkbox-label-class");
		}else{
			$(el).next("label").addClass("checkbox-label-class").removeClass("checkbox-selected");
		}
	}
})

});