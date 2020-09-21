steal('funcunit').then(function(){

module("Plugins.ImgSelect", { 
	setup: function(){
		S.open("//plugins/img_select/img_select.html");
	}
});

test("Text Test", function(){
	equals(S("h1").text(), "Plugins.ImgSelect Demo","demo text");
});


});