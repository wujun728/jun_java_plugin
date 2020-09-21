steal( 'jquery/controller','jquery/view/ejs' )
	.then('./views/init.ejs',
			'./views/editsort.ejs',
			'./views/sort_add.ejs',
		  './models/services.js',function($){
	env=this;
	var element;
	var sortCols_;
	var tableName_;
	/**
	 * @class data_select
	 */
	$.Controller('Plugins.DataSort.Default',
	/** @Static */
	{
		defaults : {}
	},{
		init:function(){
		},
		show:function(tableName){
			env=this;
			element=this.element;
			Plugins.DataSort.Models.Services.getDataSortByTableName({
				tableName:tableName,
				success:function(data){
					sortCols_=data.sortCols;
					tableName_=data.tableName;
					element.append("//plugins/data_sort/views/init.ejs",{
						sortCols:sortCols_,
						tableName:tableName_
					},function(){
						element.i18n();
						element.show();
					});
				}
			});
		},
		edit:function(tableName,sorts){
			env=this;
			element=this.element;
			Plugins.DataSort.Models.Services.getDataSortByTableName({
				tableName:tableName,
				success:function(data){
					sortCols_=data.sortCols;
					tableName_=data.tableName;
					element.append("//plugins/data_sort/views/editsort.ejs",{
						sorts:sorts,
						sortCols:sortCols_,
						tableName:tableName_
					},function(){
						element.i18n();
						element.show();
					});
				}
			});
		},
		getSortData:function(){
			var args=new Array();
			$.each($(this.element).eachArrayPlus(),function(i,n){
				if(n.sortName=="0"||n.sortWay=="0"){
					return true;
				}
				var data={
					sortName:n.sortName,
					sortWay:n.sortWay,
					sequence:i
				}
				args.push(data);
			});
			return args;
		},
		//add sort
		'.addsortway click':function(el,ev){
			var dom=el.parent().parent();
			dom.after("//plugins/data_sort/views/sort_add.ejs",{
				sortCols:sortCols_,
				tableName:tableName_
			});
			element.i18n();
		},
		//remove sort
		'.removesortway click':function(el,ev){
			el.parent().parent().remove();
		}
	})
});