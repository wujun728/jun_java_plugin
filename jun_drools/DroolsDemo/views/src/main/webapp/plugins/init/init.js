steal('jquery/jquery.js', 'jquery/class','jquery/dom/cookie').then(	
		'plugins/i18next-1.3.1/i18next-1.3.1.js',		
		'plugins/common/utils.js',		
		'plugins/common/form2json.js',
		'plugins/common/uuid.js',
		'plugins/common/exception.js',
		'plugins/common/user_data.js',
		'plugins/common/storage.js',		
        'plugins/common/services.js',
        'plugins/modal/modal.js',
        'plugins/model/services.js',
        'plugins/js/jquery.ba-hashchange.min.js'
).then(function($) {    	
    
    var defaultLanguage="zh_cn";
	$.Class("Plugins.Init", {}, {		
		init : function(callback) {		 
		    if(Plugins.Common.Storage.getContentLanguage() != null){		    
		        languageCode = Plugins.Common.Storage.getContentLanguage();
		    } else {
		        languageCode=defaultLanguage;
		    }		      		     
		    
		    if(!steal.isRhino) {		  
			$.i18n.init({
				useLocalStorage : false,
				dynamicLoad : false,
				fallbackLng : defaultLanguage,
				lowerCaseLng : true,
				resGetPath : '../locales/__lng__/__ns__.json',
				debug : true
			}, function(t) {			
				$.i18n.setLng(languageCode,callback);	
			});
		    }
		}
	});
});
