$(document).ready(function(){ 
	$(window).resize(function(){
		if ($(window).width()<1569){
		    $("#main .chart").hide();
	    }
		else{
			$("#main .chart").show();
		}
	});    
}) 