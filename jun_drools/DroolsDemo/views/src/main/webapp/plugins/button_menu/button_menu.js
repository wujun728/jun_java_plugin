steal( 'jquery/controller','jquery/view/ejs' )
	.then( './views/init.ejs', function($){

/**
 * @class Plugins.ButtonMenu
 */
$.Controller('Plugins.ButtonMenu',
/** @Static */
{
	defaults : {}
},
/** @Prototype */
{
	init : function(){
		var env=this;
		var actions=this.options.actions;
		this.element.html("//plugins/button_menu/views/init.ejs",{actions:actions			
		},function(){
			env.element.i18n();
			env.find("button[name='rerun']").html($.t("button.operate"));
			var childrens = $(env.element).find("ul li");
			if(childrens.length == 0){
				$(env.element).remove();
				return;
			}
			for (var i=0;i<actions.length;i++){					
				(function(x){					
				    env.find("li[value='"+actions[x].type+"']").click(function(){				    
				    	var callback=actions[x].operation;	
					    var id=$(this).closest("tr").attr("value");							   
					    callback(id,$(this));					
				    });
				})(i)
			}
			env.find("button[name='rerun']" )
			.button()
			.click(function() {
				$("[name='grid_button'] .ui-menu").hide();
				$(".ui-dialog").hide();
				var menu = $( this ).parent().next().show().position({
					my: "left top",
					at: "left bottom",
					of: this
				});
				$( document ).one( "click", function() {
					menu.hide();
					$(".ui-dialog").hide();
				});
				return false;
			})
			.next()
				.button({
					text: false,
					icons: {
						primary: "ui-icon-triangle-1-s"
					}
				})
				.click(function() {
					$("[name='grid_button'] .ui-menu").hide();
					$(".ui-dialog").hide();
					var menu = $( this ).parent().next().show().position({
						my: "left top",
						at: "left bottom",
						of: this
					});
					$( document ).one( "click", function() {
						menu.hide();
						$(".ui-dialog").hide();
					});
					return false;
				})
				.parent()
					.buttonset()
					.next()
						.hide()
						.menu();

		});
	}
})

});