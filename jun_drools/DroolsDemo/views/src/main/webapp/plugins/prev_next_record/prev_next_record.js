steal( 'jquery/controller','jquery/view/ejs' )
	.then( './views/init.ejs',
			function($){
		var currentId = "";
		var env;
/**
 * @class Plugins.PrevNextRecord
 */
$.Controller('Plugins.PrevNextRecord',
/** @Static */
{
	/** @Static */
	defaults: {
		table:"",
		id:"",
		deleteCallBack:false,
		editCallBack:false,
		callBack:false,
		buttons:[]
	}
}, {
	/** @Prototype */
	init: function(){
		env=this;
		var table = this.options.table;
		currentId = this.options.id;
		var deleteCallBack = env.options.deleteCallBack;
		var editCallBack = env.options.editCallBack;
		var callBack = env.options.callBack;
		var buttons = env.options.buttons;
		$(env.element).html("//plugins/prev_next_record/views/init.ejs",{
			deleteCallBack:deleteCallBack,
			editCallBack:editCallBack,
			/*buttons:buttons,*/
			callBack:callBack
		},function(){
			env.element.i18n();
			env.initPagin(table, currentId);
			
			/*for(var i=0;i<buttons.length;i++){
				$("#"+buttons[i].type).bind("click",function(el, ev){
					buttons[i].callBack(currentId, el);
				});
			}*/
		})
	},
	
	initPagin:function(table, id){
		if(!env.nextRecord(table, id)){
			$(env.element).find("#next_record").removeClass("btn-blue").addClass("btn-gray").attr("disabled",true);
		}else{
			$(env.element).find("#next_record").removeClass("btn-gray").addClass("btn-blue").attr("disabled",false);
		}
		if(!env.prevRecord(table, id)){
			$(env.element).find("#prev_record").removeClass("btn-blue").addClass("btn-gray").attr("disabled",true);
		}else{
			$(env.element).find("#prev_record").removeClass("btn-gray").addClass("btn-blue").attr("disabled",false);
		}
	},
	
	nextRecord:function(table, id, flag){
		var next = $(table).find("tr[value='"+id+"']").next();
		if(next.length > 0){
			return next.attr("value");
		}else{
			var pagin = $(table).find("table.pagin .paginList");
			if(pagin.length > 0){
				var page = $(table).controller().getCurrentPageNo();
				var pages = $(table).controller().getTotalPages();
				if(page == pages){
					return false;
				}else{
					if(flag){
						$(pagin).find(".pagenxt-blue").click();
						return $(table).find(".tablelist tr:eq(1)").attr("value");
					}
					return true;
				}
			}else{
				return false;
			}
		}
	},
	
	prevRecord:function(table, id, flag){
		var prev = $(table).find("tr[value='"+id+"']").prev();
		if(prev.length > 0){
			return prev.attr("value");
		}else{
			var pagin = $(table).find("table.pagin .paginList");
			if(pagin.length > 0){
				var page = $(table).controller().getCurrentPageNo();
				if(page == 1){
					return false;
				}else{
					if(flag){
						$(pagin).find(".pagepre-blue").click();
						return $(table).find(".tablelist tr:last").attr("value");
					}
					return true;
				}
			}else{
				return false;
			}
		}
	},
	
	'#prev_record click':function(el, ev){
		currentId = env.prevRecord(env.options.table, currentId, true);
		env.options.callBack(currentId, el);
		env.initPagin(env.options.table,currentId);
	},
	
	'#next_record click':function(el, ev){
		currentId = env.nextRecord(env.options.table, currentId, true);
		env.options.callBack(currentId, el);
		env.initPagin(env.options.table,currentId);
	},
	
	'#delete_record click':function(el, ev){
		env.options.deleteCallBack(currentId, el);
	},
	
	'#edit_record click':function(el, ev){
		env.options.editCallBack(currentId, el);
	}
	
	
});
});