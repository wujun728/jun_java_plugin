    	function show(){
    		window.alert("嘻嘻");
    	} 
    	window.onload=function(){
    		var buttonElement = document.myform.mybtn;
    		buttonElement.onclick=show;
    	}