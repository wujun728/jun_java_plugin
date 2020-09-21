steal('funcunit').then(function(){

module("Plugins.Footer", { 
	setup: function(){
		S.open("//plugins/footer/footer.html");
	}
});

test("Text Test", function(){
	equals(S("h1").text(), "Plugins.Footer Demo","demo text");
});


});