steal('jquery/model', function() {
    
	$.Model('Plugins.DataSelect.Models.Services',
	/* @Static */
	{
		getDataSelectByTableName:function(args){
			args.uri = 'api/custom-query/dataselect-tablename/'+args.tableName;
	        args.dataType = 'json';	  
	        return Plugins.Common.Services.aqGet(args);
		},
		getDataSelectColsById:function(args){
			args.uri = 'api/custom-query/dataselect-cols/'+args.customQueryId;
	        args.dataType = 'json';		   
	        return Plugins.Common.Services.aqGet(args);
		}
	},
	/* @Prototype */
	{	
		
	});
});