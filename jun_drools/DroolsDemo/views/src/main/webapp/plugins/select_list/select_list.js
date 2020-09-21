steal( 'jquery/controller','jquery/view/ejs' )
	.then( './views/init.ejs', function($){

/**
 * @class Plugins.SelectList
 */
$.Controller('Plugins.SelectList',
/** @Static */
{
	defaults : {
		defaultItem:"",
		listData:[]
	}
},
/** @Prototype */
{
	init : function(){
		
	},
	
	show:function(){
		var defaultItem=this.options.defaultItem;
		var listData=this.options.listData;	
		var env=this;
		this.element.html("//plugins/select_list/views/init.ejs",{			
			defaultItem: defaultItem,
			listData:listData
		},function(){
			env.element.find("select").i18n();
		});
	},
	
	getSelectedId: function(){
		return this.element.find("select").val();
	},
	
	getSelectedName: function(){
		return this.element.find("select option:selected").text();
	}
})

});