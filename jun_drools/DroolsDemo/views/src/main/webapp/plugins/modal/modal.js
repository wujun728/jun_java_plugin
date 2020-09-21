steal( 'jquery/controller','jquery/view/ejs')
	.then( './views/error.ejs','./views/success.ejs','./views/loading.ejs',function($){
/**
 * @class Plugins.Modal
 */
$.Controller('Plugins.Modal',
/** @Static */
{
	defaults : {modalType:"success",message:""}
},
/** @Prototype */
{
	init : function(){	    
	},
	show:function(){
		var env=this;
		var modalType=this.options.modalType;
	    var message=this.options.message;	  
	    
	    this.element.html("//plugins/modal/views/"+modalType+".ejs",{
	    	message:message	    	
		},function(){	
			$("#"+modalType+"_modal").i18n();
		});	 
	    if (modalType!="loading"){
	        setTimeout(function(){
	    	    env.hide();
	        },3000);
	    }
	},
	hide:function(){
		this.element.html("");
	},	
	
	'{document} click':function(){
		var modalType=this.options.modalType;
		if (modalType!="loading"){
		    this.hide();		
		}
	}
});

});
