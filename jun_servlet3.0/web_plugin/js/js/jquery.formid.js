// JavaScript Document
/**
 * edatagrid - jQuery EasyUI
 * 
 * Licensed under the GPL:
 *   http://www.gnu.org/licenses/gpl.txt
 *
 * Copyright 2011 stworthy [ stworthy@gmail.com ] 
 * 
 * Dependencies:
 *   datagrid
 *   messager
 * 
 */
(function($){
	
	$.fn.formid = function(options, param){
		switch(options){
			case 'loadit':
				return this.each(function(){
					loadid(this, param);
				});
		}
			
	};

	
	/**
	 * load form data
	 * if data is a URL string type load from remote site, 
	 * otherwise load from local data object. 
	 */
	function loadid(target, data){
		if (typeof data == 'string'){
			$.ajax({
				url: data,
				dataType: 'json',
				success: function(data){
					_load(data);
				}
			});
		} else {
			_load(data);
		}
		
		function _load(data){
			var form = $(target);
			//var combobox = "easyui-combobox";
			for(var name in data){
				var val = data[name];
				try{
					if(!!$('#'+name).combobox("options")){
						$('#'+name).combobox('setValue',val);
					}
				}catch(e){
					try{
						if(!!$('#'+name).combotree("options")){
							$('#'+name).combotree('setValue',val);
						}
					}catch(e){
						try{
							if(!!$('#'+name).datebox("options")){
								$('#'+name).datebox('setValue',val);
							}
						}catch(e){
							$('#'+name).val(val);
						}
					}
				}
			}
		}
	}
	
	$.fn.formid.defaults = $.extend({}, $.fn.form.defaults, {});
})(jQuery);