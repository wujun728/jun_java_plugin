steal('jquery/class').then(function($) {

	$.Class("Plugins.Common.Exception", {		
		popupError : function(jqXHR, textStatus, errorThrown) {			  
		    var errorCode;
			var errorMessage;
			var status=jqXHR.status;
			var text=jqXHR.responseText;
			if (parseInt(status)==401){
    		    location.href='../login/login.html';   
    		    return;
		    }	
			else{
				var responseJson;
				try
				{
				   responseJson = $.evalJSON(text);
				   if (responseJson.error){
					   errorCode=responseJson.error;
				   }
				   else if (responseJson.errorCode){
					   errorCode=responseJson.errorCode;
				   }							   
				   
				   if (errorCode==undefined){
					   errorMessage=responseJson.message;
				   }
				   else{
					   errorMessage=$.t("backend_error." + errorCode);	
				   }
				}				
				catch(exception)
				{
					steal.dev.log(exception);
					errorMessage=$.t("backend_error.600");				
				}			
			}
			alert(errorMessage);
	    }
    
	}, {
		init: function() {	
		      
		}
	});
});
