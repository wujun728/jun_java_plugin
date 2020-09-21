steal('funcunit').then(function(){

module("Plugins.History", { 
	setup: function(){
		S.open("//plugins/history/history.html");
	}
});

test("Text Test", function(){
	equals(S("h1").text(), "Plugins.History Demo","demo text");
});


});