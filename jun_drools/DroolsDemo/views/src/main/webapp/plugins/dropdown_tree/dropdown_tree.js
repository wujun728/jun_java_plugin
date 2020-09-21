steal( 'jquery/controller','jquery/view/ejs' )
	.then( './views/init.ejs', function($){

/**
 * @class Plugins.DropdownTree
 */
$.Controller('Plugins.DropdownTree',
/** @Static */
{
	defaults : {selectedId:"0",selectedName:"",iniData:[],checkNode:function(node){return true;}}
},
/** @Prototype */
{
	init : function(){
		var env=this;
		$("div[name='dropdown_tree_popup']").parent(".ui-dialog").remove();
		var selectedName=this.options.selectedName;			
		this.element.html("//plugins/dropdown_tree/views/init.ejs",{
		},function(){
			env.find("[name='dropdown_tree_input']").val(selectedName);			
			env.show();				
		});
	},
	
	show: function(){			
		var env=this;	
    	var dropdownTree=env.find("[name='dropdown_tree']");
		dropdownTree.dynatree({
			clickFolderMode:1,
			children: env.options.iniData,
			onActivate: function(node) {	    	 
				 env.selectNode(node);
			},
			onLazyRead: function(node){
				 env.callback(node);					    	  
			}
	    });	    
	},
	
	reset:function(iniData){
		var env = this;
		env.options.iniData=iniData;
		env.options.selectedName="";
		env.options.selectedId="0";
		env.init();
		env.show();
	},
	
	callback:function(node){
		var env=this;		
		env.options.loadTreeCallback(node);
	},
	
	selectNode: function(node){
	  var env=this;
	  var key=node.data.key;		  
	  var canChoose = node.data.canChoose;
	  if (!canChoose){
			alert($.t("message.re_select"));						
			return;
		}
	  
	  env.options.selectedId=key.replace("model_","");	
	  env.options.selectedId=key.replace("project_","");	
	  env.options.selectedName=node.data.title;	 
	  
	  if (env.options.checkNode(node)){
	      env.close();
	      env.find("[name='dropdown_tree_input']").val(env.options.selectedName);
	  }
	},
	
	getSelectedId: function(){
		return this.options.selectedId;
	},
	
	getSelectedName: function(){
		return this.options.selectedName;
	},
	
	'.dfinput click':function(el,ev){			
		Plugins.Common.Utils.stopPropagation(ev);
		$("div[name='dropdown_tree_popup']").dialog({resizable: false,position: { my: "right top", at: "right bottom", of: el}});		
		$("div[name='dropdown_tree_popup']").parent().find(".ui-dialog-titlebar").remove();
		var width=el.parent().parent().width()+13;
		$("div[name='dropdown_tree_popup']").parent(".ui-widget-content").css({"border":"0px","background":"none","width":width+"px"});	
		
		var left=$("div[name='dropdown_tree_popup']").parent(".ui-widget-content").position().left;	
        if (left>0){	
		    $("div[name='dropdown_tree_popup']").parent(".ui-widget-content").css({"left":el.offset().left+"px"});
        }
        
        var top=$("div[name='dropdown_tree_popup']").parent(".ui-widget-content").position().top;	
        console.log("top="+top);
	    
        $("div[name='dropdown_tree_popup']").show();
        $("div[name='dropdown_tree_popup']").parent().show();
	},	
	
	close:function(){
		$("div[name='dropdown_tree_popup']").hide();
	},
	
	'{document} click':function(){
		this.close();
	}
})

});