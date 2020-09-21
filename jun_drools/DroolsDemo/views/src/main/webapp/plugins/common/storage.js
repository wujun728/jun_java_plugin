steal('jquery/lang/json', 'jquery/class').then(function($) {
    var expiredDays = 30;

    $.Class('Plugins.Common.Storage', {
        //session storage///////////////////////////////////////////////////////////////////
        setCredentials: function(data) {           
        	var permissions=data.user.permissions;
        	if (permissions.length>0){
        	    var comparsionFun=Plugins.Common.Utils.comparsionArray("moduleSequence");
        	    data.user.permissions=permissions.sort(comparsionFun);
        	}        
        	Plugins.Common.Storage.setDefaultProject(data.user.defaultProject);        	
            if (typeof(window.sessionStorage) != "undefined") {
                window.sessionStorage.setItem("credentials", $.toJSON(data));                    
            } else {   
            	Plugins.Common.User.data.setItem("credentials", $.toJSON(data));   
            }
        },
        getCredentials: function() {
            var credentials="{}";
            if (typeof(window.sessionStorage) != "undefined") {
                credentials = window.sessionStorage.getItem("credentials");                 
            } else {            
                credentials = Plugins.Common.User.data.getItem("credentials");                    
            }
            credentials = $.evalJSON(credentials);
            
            return credentials;
        },  
        
        getDefaultProject: function() {
            var defaultProject = null;
            if (typeof(window.sessionStorage) != "undefined") {
            	defaultProject = window.sessionStorage.getItem("default_project");
            	
            } else {
            	defaultProject = Plugins.Common.User.data.getItem("default_project");
            }
            defaultProject=$.evalJSON(defaultProject);
            
            if (defaultProject==null){
            	alert($.t('message.no_project'));
            	location.href='../login/login.html';
            }          
            
            return defaultProject;
        },
        setDefaultProject: function(defaultProject) {
            if (typeof(window.sessionStorage) != "undefined") {
                window.sessionStorage.setItem("default_project", $.toJSON(defaultProject));
            } else {
            	Plugins.Common.User.data.setItem("default_project",$.toJSON(defaultProject));                
            }
        },
        
        getAuthToken: function() {
            var authToken = null;
            if (typeof(window.sessionStorage) != "undefined") {
                authToken = window.sessionStorage.getItem("access_token");
            } else {
                authToken = $.cookie("access_token");
            }
            
            return authToken;
        },
        setAuthToken: function(authToken) {        	
            if (typeof(window.sessionStorage) != "undefined") {
                window.sessionStorage.setItem("access_token", authToken);
            } else {
                $.cookie("access_token", authToken,{
                    path: '/',
                    expires: expiredDays
                });               
            }
        },       

        deleteCookie: function() {
            if (typeof(window.sessionStorage) != "undefined") {
                window.sessionStorage.clear();
            } else {
                $.cookie("credentials", null);
                $.cookie("access_token", null);              
            }
        },

        //local storage////////////////////////////////////////////////////////////////////////////
        setContentLanguage: function(contentLanguage) {
            if (typeof(window.localStorage) != "undefined") {
                window.localStorage.setItem("contentLanguage", contentLanguage);
            } else {
                $.cookie("contentLanguage", contentLanguage, {
                    path: '/',
                    expires: expiredDays
                });
            }
        },
        getContentLanguage: function() {
            var contentLanguage = null;
            if (typeof(window.localStorage) != "undefined") {
                contentLanguage = window.localStorage.getItem("contentLanguage");
            } else {
                contentLanguage = $.cookie("contentLanguage");
            }

            return contentLanguage;
        },
        
        ///////////////////////////////////////////////////////////////////////////////////////////////
        hasPermission:function(subModuleName){
        	var credentials=this.getCredentials();
        	var permissions=credentials.user.permissions;
        	for (var i=0;i<permissions.length;i++){
        		if (permissions[i].subModuleName==subModuleName){
        			return true;
        		}
        	}
        	return false;
        },
        
        getSystemPermissions:function(){
        	var credentials=this.getCredentials();
        	var systemPermissions=[];
        	var permissions=credentials.user.permissions;
        	for (var i=0;i<permissions.length;i++){
        		if ($.inArray(permissions[i].systemName,systemPermissions)==-1){
        			systemPermissions.push(permissions[i].systemName);
        		}
        	}
        	return systemPermissions;
        },
        
        getModulePermissions:function(systemName){
        	var credentials=this.getCredentials();
        	var modulePermissions=[];
        	var permissions=credentials.user.permissions;
        	for (var i=0;i<permissions.length;i++){
        		if ((permissions[i].systemName==systemName || systemName=="") && $.inArray(permissions[i].moduleName,modulePermissions)==-1){
        			modulePermissions.push(permissions[i].moduleName);
        		}
        	}
        	return modulePermissions;
        },
        /**
         * set ProjectConfig values into memory
         * desc: process is array<object>
         */
        setProjectConfigs:function(process){
        	var str = "";
        	if(process!=null) {
        		var projectConfigs = {};
            	for(var i=0;i<process.length;i++){
            		var p = process[i];
            		projectConfigs[p.processType] = p;
            	}
            	str = $.toJSON(projectConfigs);
        	}
        	window.localStorage.setItem("projectConfigs", str);  
        },
        getProjectConfigs:function(){
        	var projectConfigs = $.parseJSON(window.localStorage.getItem("projectConfigs"));
        	var processes = [];
        	if(projectConfigs!=null){
        		$.each(projectConfigs, function(key, value) { 
        			processes.push(value);
        		}); 
        	}
        	return processes;
        },
        isRequiredProjectConfig:function(processType){
        	var projectConfigs = $.parseJSON(window.localStorage.getItem("projectConfigs"));
        	if(projectConfigs!=null && processType in projectConfigs){
        		return projectConfigs[processType].isRequired;
        	}else{
        		return false;
        	}
        }
    },
    {

});
});