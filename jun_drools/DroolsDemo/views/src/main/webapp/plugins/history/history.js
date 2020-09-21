steal( 'jquery/controller','jquery/view/ejs' )
	.then( './views/init.ejs',
			'./models/services.js', function($){
		

var env;
/**
 * @class Plugins.History
 */
$.Controller('Plugins.History.Default',
/** @Static */
{
	defaults : {}
},
/** @Prototype */
{
	init : function(){
		this.show();
	},
	
	show:function(){
		env=this;
		$.when(env.callback()).done(function(data){
			env.element.html("//plugins/history/views/init.ejs",{history:data.history},function(){
				env.element.i18n();
			});
		});
	},
	
	callback : function() {
		return Plugins.History.Models.Services.getHistory({
			objectId:env.options.objectId,
			projectId:env.options.projectId,
			objectType:env.options.objectType
		});
	}
})

});