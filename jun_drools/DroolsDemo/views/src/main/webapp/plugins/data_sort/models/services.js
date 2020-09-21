steal('jquery/model', function() {
    
	$.Model('Plugins.DataSort.Models.Services',
	/* @Static */
	{
		getDataSortByTableName:function(args){
			args.uri = 'api/custom-query/datasort-tablename/'+args.tableName;
	        args.dataType = 'json';	  
	        return Plugins.Common.Services.aqGet(args);
		}
	},
	/* @Prototype */
	{	
		
	});
});