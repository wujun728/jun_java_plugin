steal('funcunit').then(function(){

module("Plugins.Components.Controller.Checkbox", { 
	setup: function(){
		S.open("//plugins/components/controller/checkbox/checkbox.html");
	}
});

test("Text Test", function(){
	equals(S("h1").text(), "Plugins.Components.Controller.Checkbox Demo","demo text");
});


});