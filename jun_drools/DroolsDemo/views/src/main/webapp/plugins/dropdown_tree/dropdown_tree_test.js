steal('funcunit').then(function(){

module("Plugins.DropdownTree", { 
	setup: function(){
		S.open("//plugins/dropdown_tree/dropdown_tree.html");
	}
});

test("Text Test", function(){
	equals(S("h1").text(), "Plugins.DropdownTree Demo","demo text");
});


});