steal('funcunit').then(function(){

module("Plugins.Components", { 
	setup: function(){
		S.open("//plugins/components/components.html");
	}
});

test("Text Test", function(){
	equals(S("h1").text(), "Plugins.Components Demo","demo text");
});


});