steal('funcunit').then(function(){

module("Plugins.Modal", { 
	setup: function(){
		S.open("//plugins/modal/modal.html");
	}
});

test("Text Test", function(){
	equals(S("h1").text(), "Plugins.Modal Demo","demo text");
});


});