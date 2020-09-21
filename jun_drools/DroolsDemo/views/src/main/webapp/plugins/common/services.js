steal('jquery/class',
function() {
	var prefixUrl="../../hbird-web-core/";
      
    $.Class('Plugins.Common.Services',
    /* @Static */
    {       
        aqGet: function(args) {           
        	var uri = args.uri;
			if (uri.indexOf("?")==-1){
				uri=uri+"?_="+(new Date()).getTime();
			}
			else{
				uri=uri+"&_="+(new Date()).getTime();
			}
			url = String(encodeURIComponent(uri)).toLowerCase();
            
            var context = args.context;
            if (!context) {
                context = $("#message_popup");
            }
            var contentType = args.contentType;
            if (!contentType) {
            	contentType = 'application/json';
            }
            var error = args.error;
            if (!error) {
                error = Plugins.Common.Exception.popupError;
            }
            var async = true;
            if (args.async != undefined) {
                async = args.async;
            }            
            var authToken=Plugins.Common.Storage.getAuthToken();
            
            return $.ajax({
                url: prefixUrl+uri,
                type: 'get',
                async: async,
                dataType: args.dataType,
                contentType: contentType,
                data: args.data,
                success: args.success,
                error: error,
                beforeSend: function(xhr) {
                    xhr.setRequestHeader('Authorization', authToken);
                },
                context: context,
                complete: args.complete
            });
        },

        aqPost: function(args) {
        	var uri = args.uri;
			if (uri.indexOf("?")==-1){
				uri=uri+"?_="+(new Date()).getTime();
			}
			else{
				uri=uri+"&_="+(new Date()).getTime();
			}
			url = String(encodeURIComponent(uri)).toLowerCase();
            
            var context = args.context;
            if (!context) {
                context = $("#message_popup");
            }
            var contentType = args.contentType;
            if (!contentType) {
            	contentType = 'application/json; charset: UTF-8';
            }
            var error = args.error;
            if (!error) {
                error = Plugins.Common.Exception.popupError;
            }
            var async = true;
            if (args.async != undefined) {
                async = args.async;
            }            
            var authToken=Plugins.Common.Storage.getAuthToken();
            
            return $.ajax({
                url: prefixUrl+uri,
                type: 'post',
                async: async,
                dataType: args.dataType,
                contentType: contentType,
                data: args.data,
                success: args.success,
                error: error,
                beforeSend: function(xhr) {
                	xhr.setRequestHeader('Accept', "application/json");
                    xhr.setRequestHeader('Authorization', authToken);
                },
                context: context,
                complete: args.complete
            });

        },

        aqDelete: function(args) {
        	var uri = args.uri;
			if (uri.indexOf("?")==-1){
				uri=uri+"?_="+(new Date()).getTime();
			}
			else{
				uri=uri+"&_="+(new Date()).getTime();
			}
			url = String(encodeURIComponent(uri)).toLowerCase();
            
            var context = args.context;
            if (!context) {
                context = $("#message_popup");
            }
            var contentType = args.contentType;
            if (!contentType) {
            	contentType = 'application/json';
            }
            var error = args.error;
            if (!error) {
                error = Plugins.Common.Exception.popupError;
            }
            var async = true;
            if (args.async != undefined) {
                async = args.async;
            }            
            var authToken=Plugins.Common.Storage.getAuthToken();
            
            return $.ajax({
                url: prefixUrl+uri,
                type: 'delete',
                async: async,
                dataType: args.dataType,
                contentType: contentType,
                data: args.data,
                success: args.success,
                error: error,
                beforeSend: function(xhr) {
                    xhr.setRequestHeader('Authorization', authToken);
                },
                context: context,
                complete: args.complete
            });
        },
        
        aqPut: function(args) {
        	var uri = args.uri;
			if (uri.indexOf("?")==-1){
				uri=uri+"?_="+(new Date()).getTime();
			}
			else{
				uri=uri+"&_="+(new Date()).getTime();
			}
			url = String(encodeURIComponent(uri)).toLowerCase();
            
            var context = args.context;
            if (!context) {
                context = $("#message_popup");
            }
            var contentType = args.contentType;
            if (!contentType) {
            	contentType = 'application/json; charset: UTF-8';
            }
            var error = args.error;
            if (!error) {
                error = Plugins.Common.Exception.popupError;
            }
            var async = true;
            if (args.async != undefined) {
                async = args.async;
            }            
            var authToken=Plugins.Common.Storage.getAuthToken();
            
            return $.ajax({
                url: prefixUrl+uri,
                type: 'put',
                async: async,
                dataType: args.dataType,
                contentType: contentType,
                data: args.data,
                success: args.success,
                error: error,
                beforeSend: function(xhr) {
                    xhr.setRequestHeader('Authorization', authToken);
                },
                context: context,
                complete: args.complete
            });

        },
        
        aqUpload: function(args) {
        	var uri = args.uri;
			if (uri.indexOf("?")==-1){
				uri=uri+"?_="+(new Date()).getTime();
			}
			else{
				uri=uri+"&_="+(new Date()).getTime();
			}
			url = String(encodeURIComponent(uri)).toLowerCase();
            
            var context = args.context;
            if (!context) {
                context = $("#message_popup");
            }           
            var error = args.error;
            if (!error) {
                error = Plugins.Common.Exception.popupError;
            }
            var async = true;
            if (args.async != undefined) {
                async = args.async;
            }            
            var authToken=Plugins.Common.Storage.getAuthToken();
            
            return $.ajax({
                url: prefixUrl+uri,
                type: 'post',
                async: async,
                dataType: args.dataType,
                contentType: false,
                processData:false,
                data: args.data,
                success: args.success,
                error: error,
                beforeSend: function(xhr) {
                    xhr.setRequestHeader('Authorization', authToken);
                },
                context: context,
                complete: args.complete
            });

        }
    },
    /* @Prototype */
    {

});

})