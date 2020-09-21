steal('funcunit').then(function(){

module("Plugins.SelectList", { 
	setup: function(){
		S.open("//plugins/select_list/select_list.html");
	}
});

test("Text Test", function(){
	equals(S("h1").text(), "Plugins.SelectList Demo","demo text");
});


});