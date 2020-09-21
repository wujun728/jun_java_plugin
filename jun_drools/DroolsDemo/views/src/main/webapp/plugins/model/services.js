steal('jquery/model',function() {
    
	$.Model('Plugins.Model.Services',
	/* @Static */
	{
		logout: function(args){
			args.uri = 'api/logout';
	        args.dataType = 'json';	      	             
	        return Plugins.Common.Services.aqPost(args);
		},
		/*
		 * get dictionaryData by type
		 */
		getDictionaryData : function(args){ 	
			args.uri = 'api/get-dictionary-data/'+args.dictionary_type;
			args.dataType = 'json';	 		
			return Plugins.Common.Services.aqGet(args);
		},
		getDictionaryDataList : function(args){ 	
			args.uri = 'api/dictionary-data/'+args.dictionaryType;
			args.dataType = 'json';	 			
			return Plugins.Common.Services.aqGet(args);
		},
		/*
		 * get dictionaryData by type
		 */
		getEnumData : function(args){ 	
			args.uri = 'api/getEnumData/'+args.enumName;
			args.dataType = 'json';				
			return Plugins.Common.Services.aqGet(args);
		},
		
		getDepartmentsTree: function(args){
			args.uri = 'api/select-departments-tree';
	        args.dataType = 'json';	  	     
	        return Plugins.Common.Services.aqGet(args);
		},
		
		getDepartmentsChildTree:function(args){
			args.uri = 'api/select-departments-child-tree/'+args.departmentId;
	        args.dataType = 'json';		      
	        return Plugins.Common.Services.aqGet(args);
		},
		
		getModelProjectsTree: function(args){
			args.uri = 'api/models-projects-tree/'+args.searchByUser;
	        args.dataType = 'json';	  
	        return Plugins.Common.Services.aqGet(args);
		},
		
		getTestCasesList:function(args){
			args.uri = 'api/test-case-req/'+args.projectId+'/'+args.versionId+'/'+args.testProcess;
	        args.dataType = 'json';	      
	        return Plugins.Common.Services.aqPost(args);
		},
		
		getTestBasisTree: function(args){
			args.uri = '/api/testbasis-nocustom/tree/'+args.versionId+'/'+args.testBasisId;
	        args.dataType = 'json';		      
	        return Plugins.Common.Services.aqGet(args);
		},
		
		getTestBasisTreeChild:function(args){
			args.uri = 'api/testbasis/treechild/'+args.testBasisTypeOrId+'/'+args.testBasisId+'/'+args.nameorcode+'/'+args.versionId;
	        args.dataType = 'json';	 	      
	        return Plugins.Common.Services.aqGet(args);
		},
		
		getTestReqTreeChild:function(args){
			args.uri = 'api/testreq/treechild/'+args.projectId+"/"+args.versionId+"/"+args.testProcessOrpTestReqId+"/"+args.loadAll;
	        args.dataType = 'json';	 
	        return Plugins.Common.Services.aqGet(args);
		},
		
		getTestReqTreeChildNotCustom:function(args){
			args.uri = 'api/testreq/treechild-notcustom/'+args.projectId+'/'+args.versionId+'/'+args.testreqId+'/'+args.testProcessOrpTestReqId;
	        args.dataType = 'json';		       
	        return Plugins.Common.Services.aqGet(args);
		},
		
		getSelectTestReqTree:function(args){
			args.uri = 'api/select-testreq-tree/'+args.projectId+"/"+args.versionId+"/"+args.testProcessOrpTestReqId+"/"+args.loadAll;
	        args.dataType = 'json';	 
	        return Plugins.Common.Services.aqGet(args);
		},
		
		getSelectTestReqTreeNotCustom:function(args){
			args.uri = 'api/select-testreq-tree/notcustom/'+args.projectId+'/'+args.versionId+'/'+args.testreqId+'/'+args.testProcessOrpTestReqId;
	        args.dataType = 'json';		       
	        return Plugins.Common.Services.aqGet(args);
		}
		
	},
	/* @Prototype */
	{	

	});

})