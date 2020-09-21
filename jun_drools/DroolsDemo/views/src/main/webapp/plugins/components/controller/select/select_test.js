steal('funcunit').then(function(){

module("Plugins.Components.Controller.Select", { 
	setup: function(){
		S.open("//plugins/components/controller/select/select.html");
	}
});

test("Text Test", function(){
	equals(S("h1").text(), "Plugins.Components.Controller.Select Demo","demo text");
});


});