steal('jquery/model', function() {
    
	$.Model('Plugins.Customquery.Models.Services',
	/* @Static */
	{
		saveCustomQueryCondition:function(args){
			args.uri = 'api/custom-query/new';
	        args.dataType = 'json';		       
	        return Plugins.Common.Services.aqPost(args);
		},
		getCustomQueryConditionMenu:function(args){
			args.uri = 'api/custom-query/menu/'+args.tableName;
	        args.dataType = 'json';	 	      
	        args.async=false;
	        return Plugins.Common.Services.aqGet(args);
		},
		deleteCustomQuery:function(args){
			args.uri = 'api/custom-query/'+args.customQueryId;
	        args.dataType = 'json';	 
	        return Plugins.Common.Services.aqDelete(args);
		},
		getSelectedDataSelectByTableName:function(args){
			args.uri = 'api/custom-query/dataselected-tablename-customqueryid/'+args.tableName+"/"+args.customQueryId;
	        args.dataType = 'json';	  
	        return Plugins.Common.Services.aqGet(args);
		},
		saveCustomQueryConditionEdit:function(args){
			args.uri = 'api/custom-query';
	        args.dataType = 'json';		     
	        return Plugins.Common.Services.aqPut(args);
		}
	},
	/* @Prototype */
	{	
		
	});
});