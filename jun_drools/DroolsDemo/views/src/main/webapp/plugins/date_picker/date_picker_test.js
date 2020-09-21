steal('funcunit').then(function(){

module("Plugins.DatePicker", { 
	setup: function(){
		S.open("//plugins/date_picker/date_picker.html");
	}
});

test("Text Test", function(){
	equals(S("h1").text(), "Plugins.DatePicker Demo","demo text");
});


});