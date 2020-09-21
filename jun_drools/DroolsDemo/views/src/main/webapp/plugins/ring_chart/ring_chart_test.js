steal('funcunit').then(function(){

module("Main.Controllers.Home.Controllers.RingChart", { 
	setup: function(){
		S.open("//main/controllers/home/controllers/ring_chart/ring_chart.html");
	}
});

test("Text Test", function(){
	equals(S("h1").text(), "Main.Controllers.Home.Controllers.RingChart Demo","demo text");
});


});