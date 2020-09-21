Object.extend(String.prototype, {
	toSlug: function() {
    return this.toLowerCase().replace(/\W/g, ' ').replace(/\ +/g, '-').replace(/\-$/g, '').replace(/^\-/g, '');
  },
	
	test: function(regex, params) {
		return ((typeof regex == 'string') ? new RegExp(regex, params) : regex).test(this);		
	}
})

Event.observe( window, 'load', function(){
		
	if($('edit_tabs')) {
		$$('#edit_tabs a').each(function(el){  
			Event.observe(el, 'click', function(event){ 
				Element.removeClassName($$('#edit_tabs .current').first(), 'current');
				Element.addClassName(el.parentNode, 'current')      
				                                                                           
				Element.removeClassName($$('#edit_sections .current').first(), 'current');
				Element.addClassName($('section_' + el.id.replace('tab_', '')), 'current');   
				                           
				Event.stop(event);
			}, false);
		});             
		
		// Basic tab
		Event.observe('campaign_title', 'keyup', function(){
	    $('campaign_slug').value = $F('campaign_title').toSlug();
		});
		       
		// Feeds tab
		
		//- Test feed links
		var check_feed = function(el) {
		  el.className = 'input_text';
      if($F(el).length > 0)
      {
        var oncomplete = function(t) {
          var t = typeof t === 'string' ? t : t.responseText;
          el.className = (t == '1') ? 'ok input_text' : 'err input_text';
        };
        if(typeof jQuery !== 'undefined')
          jQuery.post("admin-ajax.php", {action: "test-feed", url: el.value, 'cookie': encodeURIComponent(document.cookie)}, oncomplete);
        else if(typeof Ajax !== 'undefined')
          new Ajax.Request("admin-ajax.php", { method: "post", parameters: "action=test-feed&url="+el.value+'&cookie=' +  encodeURIComponent(document.cookie), onComplete: oncomplete })
        else
          return false;
          
        el.className = 'load input_text';
      }
		};
		
    var update_feeds = function() {
      $$('#edit_feed div input[type=text]').each(function(el){
        Event.stopObserving(el, 'blur');
        Event.stopObserving(el, 'focus');
        
        Event.observe(el, 'focus', function(e){
          el.className = 'input_text';          
        });
        
        Event.observe(el, 'blur', function(e){
          check_feed(el);
        });
      });
    };
    
    update_feeds();
		
		//- Add feed link
		feed_index = $$('#edit_feed label').length;
		Event.observe('add_feed', 'click', function(){    
		  feed_index++;                 
			var label = $$('#edit_feed label').first().innerHTML;			
			new Insertion.Bottom('edit_feed',  '<div class="inlinetext"><label for="campaign_feed_new_'+feed_index+'">'+ label + '</label> <input type="text" name="campaign_feed[new][]" id="campaign_feed_new_'+feed_index+'" />');																				
			$$('#edit_feed input').last().focus();				
			update_feeds();											
		}, false);                           
		
		Event.observe('test_feeds', 'click', function(e){
		  Event.stop(e);
		  $$('#edit_feed input').each(function(el){ check_feed(el); });
		});
		
		// Categories
		Event.observe('quick_add', 'click', function(){
			new Insertion.Bottom('categories', '<li><input type="checkbox" checked="checked" name="campaign_newcat[]" /> <input type="text" name="campaign_newcatname[]" class="input_text" /></li>');
			$$('#categories input').last().focus();															
		}, false);     
		
		// Rewrite
		var rewrite_index = 2;
		var rewrite_keys = function(){
		  $$('#edit_words .rewrite textarea', '#edit_words .relink textarea').each(function(area){
        var check = '';
        var inputs = $A(area.parentNode.getElementsByTagName('INPUT'));
        inputs.each(function(input){
          if(input.type.toLowerCase() == 'checkbox')
            check = input;
        });
        
        Event.stopObserving(area, 'keyup');
        Event.observe(area, 'keyup', function(){
          check.checked = (area.value.length > 0);
        });
		  });
		};
		
		rewrite_keys();
		
		Event.observe('add_word', 'click', function(e){
		  Event.stop(e);
		  rewrite_index++;
		  var originvar = $('edit_words').getElementsBySelector('.origin label').first().innerHTML;
		  var regexvar = $('edit_words').getElementsBySelector('.origin .regex span').first().innerHTML;
		  var rewritevar = $('edit_words').getElementsBySelector('.rewrite label span').last().innerHTML;
      var relinkvar = $('edit_words').getElementsBySelector('.relink label span').last().innerHTML;
      
      var li = document.createElement('LI');
      li.innerHTML = '<div class="textarea"><label>'+originvar+'</label><textarea name="campaign_word_origin[new'+rewrite_index+']"></textarea><label class="regex"><input type="checkbox" name="campaign_word_option_regex[new'+rewrite_index+']" /> '+regexvar+'</label></div><div class="rewrite textarea"><label><input type="checkbox" value="1" name="campaign_word_option_rewrite[new'+rewrite_index+']" /> '+rewritevar+'</label><textarea name="campaign_word_rewrite[new'+rewrite_index+']"></textarea></div><div class="relink textarea"><label><input type="checkbox" value="1" name="campaign_word_option_relink[new'+rewrite_index+']" /> '+relinkvar+'</label><textarea name="campaign_word_relink[new'+rewrite_index+']"></textarea></div>';
      li.className = 'word';      
      $('edit_words').appendChild(li);

		  rewrite_keys();
		});
		
		// - Options
		Event.observe('campaign_templatechk', 'click', function(){
			if(!$('campaign_templatechk').checked) Element.removeClassName('post_template', 'current') 
			else Element.addClassName('post_template', 'current');	
		}, false);
		
		Event.observe('enlarge_link', 'click', function() {
		  Element.toggleClassName('campaign_template', 'large');
		  return false;
		}, false);
	}  
	   
	$$('a.help_link').each(function(el){
	  Event.observe(el, 'click', function(event){
	    window.open(el.href, 'popup', 'width=450,height=400,top=' + (screen.height - 400)/2 + ',left=' + (screen.width - 450)/2+',scrollbars=1,menubar=0,toolbar=0');
	    Event.stop(event);			
	  }, false);
	});
  
  if($('option_cachepath'))
    Event.observe('option_cachepath', 'keyup', function(){
      $('cachepath_input').innerHTML = $F(this);
    });
	
	$$('.check a').each(function(el){
	  el.checked = true;
	  Event.observe(el, 'click', function(e){
	    Event.stop(e);
	    el.checked = !el.checked;
	    var inputs = $A(el.parentNode.parentNode.getElementsByTagName('INPUT'));
	    inputs.each(function(i){ i.checked = el.checked; });
	  });
	});
	
	// setup steps
	if($('wpo-section-setup'))
	{
	  var stepsnum = $A($('setup_steps').getElementsByTagName('LI')).length;
	  var current = $('setup_steps').getElementsBySelector('.current').first();
	  var current_index = parseInt(current.id.replace('step_', ''));
	  
	  var enable_button = function(input) {
	    var input = $(input);
	    input.disabled = false;
	    Element.removeClassName(input, 'disabled');
	  }
	  
	  var disable_button = function(input) {
	    var input = $(input);
	    input.disabled = 'disabled';
	    Element.addClassName(input, 'disabled');
	  }
	  
	  var update_buttons_status = function() {
	    disable_button('setup_button_submit');
	    disable_button('setup_button_next');
	    disable_button('setup_button_previous');
	    if(current_index > 1) enable_button('setup_button_previous');
	    if(current_index < stepsnum) enable_button('setup_button_next');
	    if(current_index == stepsnum) enable_button('setup_button_submit');
	  }
	  
	  var show_page = function(index)
	  {
	    Element.removeClassName('step_' + current_index, 'current');
	    current_index = index;	    
	    Element.addClassName('step_' + current_index, 'current');
      update_buttons_status();  
      $('current_indicator').innerHTML = index;
	  }
	  
	  Event.observe('setup_button_next', 'click', function(){
	    if(current_index < stepsnum ) show_page(current_index + 1);
	  });
	  
	  Event.observe('setup_button_previous', 'click', function(){
	    if(current_index > 1) show_page(current_index - 1);
	  });
	}
	       
	if($('import_mode_2'))
  	Event.observe('import_custom_campaign', 'change', function(){ $('import_mode_2').checked = true });  	
  	
  if($('import_mode_3'))
	  Event.observe('import_new_campaign', 'keyup', function(){ $('import_mode_3').checked = true });		                                                                  
}, false );