steal('jquery/model', function() {
    
	$.Model('Plugins.DataCondition.Models.Services',
	/* @Static */
	{
		getCustomQueryCondition:function(args){
			args.uri = 'api/custom-query/customquery-condition/'+args.tableName;
	        args.dataType = 'json';	 
	        return Plugins.Common.Services.aqGet(args);
		},
		getCustomQueryConditionByChange:function(args){
			args.uri = 'api/custom-query/customquery-condition-change/'+args.fieldName+"/"+args.tableName;
	        args.dataType = 'json';	 
	        return Plugins.Common.Services.aqGet(args);
		}
	},
	/* @Prototype */
	{	
		
	});
});