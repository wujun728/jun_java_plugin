steal('jquery/class').then(function($) {
	var memory;
	$.Class("Plugins.Common.User.data", {
		init:function(){
			if (typeof(window.sessionStorage) == "undefined") {
			var doc;
			try{ 
				agent = new ActiveXObject('htmlfile');
				agent.open();
				agent.write('<script>document.w=window;</script><iframe src="/favicon.ico"></frame>');
				agent.close();
				doc = agent.w.frames[0].document;
			}catch(e){
				doc = document;
			}
		    memory = doc.createElement('head');
		    memory.style.display = "none";
		    memory.style.behavior = "url('#default#userData')";
		    doc.appendChild(memory);
		    if(!steal.isRhino) {
		        memory.load("UserDataStorage"); 
		    }
			}
		},
		getItem : function(key){
	        return memory.getAttribute(key);
	    },
	    setItem : function(key, value){
	        memory.setAttribute(key, value);
	        memory.save("UserDataStorage");
	    }
		
	},{});
});