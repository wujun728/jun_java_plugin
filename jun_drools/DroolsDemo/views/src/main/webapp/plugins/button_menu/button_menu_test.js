steal('funcunit').then(function(){

module("Plugins.ButtonMenu", { 
	setup: function(){
		S.open("//plugins/button_menu/button_menu.html");
	}
});

test("Text Test", function(){
	equals(S("h1").text(), "Plugins.ButtonMenu Demo","demo text");
});


});