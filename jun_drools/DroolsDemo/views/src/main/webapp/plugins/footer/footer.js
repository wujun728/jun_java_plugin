steal( 'jquery/controller','jquery/view/ejs' )
	.then( './views/init.ejs', function($){

/**
 * @class Plugins.Footer
 */
$.Controller('Plugins.Footer',
/** @Static */
{
	defaults : {}
},
/** @Prototype */
{
	init : function(){
		var env=this;
		this.element.html("//plugins/footer/views/init.ejs",{			
		},function(){
			env.element.i18n();
		});
	}
})

});