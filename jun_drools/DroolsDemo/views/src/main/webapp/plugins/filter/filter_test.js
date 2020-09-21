steal('funcunit').then(function(){

module("Plugins.Filter", { 
	setup: function(){
		S.open("//plugins/filter/filter.html");
	}
});

test("Text Test", function(){
	equals(S("h1").text(), "Plugins.Filter Demo","demo text");
});


});