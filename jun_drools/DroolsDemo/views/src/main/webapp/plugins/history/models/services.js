steal('jquery/model', function() {
	
	$.Model('Plugins.History.Models.Services',
	/* @Static */
	{		
		getHistory:function(args){
			args.uri = 'api/logs/history/'+args.objectId+'/'+args.projectId+'/'+args.objectType;
			args.dataType = 'json';			
	        return Plugins.Common.Services.aqGet(args);
		}
	},
	/* @Prototype */
	{	

	});

})