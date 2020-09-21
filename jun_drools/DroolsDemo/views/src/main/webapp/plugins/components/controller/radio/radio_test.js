steal('funcunit').then(function(){

module("Plugins.Components.Controller.Radio", { 
	setup: function(){
		S.open("//plugins/components/controller/radio/radio.html");
	}
});

test("Text Test", function(){
	equals(S("h1").text(), "Plugins.Components.Controller.Radio Demo","demo text");
});


});