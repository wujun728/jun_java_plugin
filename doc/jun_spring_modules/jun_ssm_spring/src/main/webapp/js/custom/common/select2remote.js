$.fn.select2Remote = function(options)
{
    var opts = $.extend({},$.fn.select2Remote.defaults, options);
    this.select2({
	    //multiple:true,
        allowClear:true,
        placeholder: opts.blankMsg,
        minimumInputLength:opts.minLength,
        id:function(obj){return obj[opts.valueField]},
        ajax: {
            url: opts.url,
            dataType: 'json',
            quietMillis: opts.delay ,
            data: function (params) {return {q: params.term,page: params.page};},
            processResults: function (data, page) { return {results: data};}
        },
        initSelection: function(element, callback) {
	            var id=$(element).val();
	            if (id!="") {
		             $.ajax(opts.initUrl, {
			              data: { q:id},
				          dataType: "json"
					 }).done(function(data) { 
						callback(data[0]);
					 }); 
		         }
        },
        templateResult: function(obj){return obj[opts.textField]},
        templateSelection:function(obj){return obj[opts.textField]},
        dropdownCssClass: "bigdrop",
        escapeMarkup: function (m) { return m; }
    });
}

$.fn.select2Remote.defaults = {
    blankMsg: "请输入",
    minLength: 2,
    url:'',
    initUrl:'',
    delay:1000,
    valueField:'id',
    textField:'text'
}