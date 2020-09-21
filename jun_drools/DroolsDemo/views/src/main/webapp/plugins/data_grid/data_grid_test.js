steal('funcunit').then(function(){

module("Plugins.DataGrid", { 
	setup: function(){
		S.open("//plugins/data_grid/data_grid.html");
	}
});

test("Text Test", function(){
	equals(S("h1").text(), "Plugins.DataGrid Demo","demo text");
});


});