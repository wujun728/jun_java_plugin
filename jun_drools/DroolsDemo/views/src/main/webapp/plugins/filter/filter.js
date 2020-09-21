steal( 'jquery/controller','jquery/view/ejs' )
	.then( './views/init.ejs', function($){
/**
 * @class Plugins.Filter
 */
$.Controller('Plugins.Filter',
/** @Static */
{
	defaults : {}
},
/** @Prototype */
{
	init : function(){
		var env=this;
		var key=this.options.key;
		var filterList=this.options.filterList;	
		var selectedFilter=this.options.selectedFilter;
		this.element.html("//plugins/filter/views/init.ejs",{key:key,filterList:filterList,selectedFilter:selectedFilter		
		},function(){		
			$("#filter_blk_"+key).i18n();
			$("#filter_blk_"+key+" ul li").click(function(ev){										
				var typeName=$(this).find("a").html();
				var typeValue=$(this).find("a").attr("filter");						
				env.options.callback(key,typeValue,typeName);
			});
		});
	},	
	
	getSelectTypeValue:function(){
		return this.options.typeValue;
	},
	
	getSelectTypeName:function(){
		return this.options.TypeName;
	},
	
	closeFilter:function(){
		$(".ui-dialog").hide();
	},	
	
	'{document} click':function(el,ev){			
	    this.closeFilter();	
	}
})

});