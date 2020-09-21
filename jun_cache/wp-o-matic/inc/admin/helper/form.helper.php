<?php

// Taken from symfony-project.com. Some slightly modified

require_once(dirname(__FILE__) . '/tag.helper.php');

/**
 * Returns a formatted set of <option> tags based on optional <i>$options</i> array variable.
 *
 * The options_for_select helper is usually called in conjunction with the select_tag helper, as it is relatively
 * useless on its own. By passing an array of <i>$options</i>, the helper will automatically generate <option> tags
 * using the array key as the value and the array value as the display title. Additionally the options_for_select tag is
 * smart enough to detect nested arrays as <optgroup> tags.  If the helper detects that the array value is an array itself,
 * it creates an <optgroup> tag with the name of the group being the key and the contents of the <optgroup> being the array.
 *
 * <b>Options:</b>
 * - include_blank  - Includes a blank <option> tag at the beginning of the string with an empty value
 * - include_custom - Includes an <option> tag with a custom display title at the beginning of the string with an empty value
 *
 * <b>Examples:</b>
 * <code>
 *  echo select_tag('person', options_for_select(array(1 => 'Larry', 2 => 'Moe', 3 => 'Curly')));
 * </code>
 *
 * <code>
 *  $card_list = array('VISA' => 'Visa', 'MAST' => 'MasterCard', 'AMEX' => 'American Express', 'DISC' => 'Discover');
 *  echo select_tag('cc_type', options_for_select($card_list, 'AMEX', array('include_custom' => '-- Select Credit Card Type --')));
 * </code>
 *
 * <code>
 *  $optgroup_array = array(1 => 'Joe', 2 => 'Sue', 'Group A' => array(3 => 'Mary', 4 => 'Tom'), 'Group B' => array(5 => 'Bill', 6 =>'Andy'));
 *  echo select_tag('employee', options_for_select($optgroup_array, null, array('include_blank' => true)), array('class' => 'mystyle'));
 * </code>
 *
 * @param  array dataset to create <option> tags and <optgroup> tags from
 * @param  string selected option value
 * @param  array  additional HTML compliant <option> tag parameters
 * @return string populated with <option> tags derived from the <i>$options</i> array variable
 * @see select_tag
 */
function options_for_select($options = array(), $selected = '', $html_options = array())
{
  $html_options = _parse_attributes($html_options);

  if (is_array($selected))
  {
    $selected = array_map('strval', array_values($selected));
  }

  $html = '';

  if ($value = _get_option($html_options, 'include_custom'))
  {
    $html .= content_tag('option', $value, array('value' => ''))."\n";
  }
  else if (_get_option($html_options, 'include_blank'))
  {
    $html .= content_tag('option', '', array('value' => ''))."\n";
  }

  foreach ($options as $key => $value)
  {
    if (is_array($value))
    {
      $html .= content_tag('optgroup', options_for_select($value, $selected, $html_options), array('label' => $key))."\n";
    }
    else
    {
      $option_options = array('value' => $key);

      if (
          (is_array($selected) && in_array(strval($key), $selected, true))
          ||
          (strval($key) == strval($selected))
         )
      {
        $option_options['selected'] = 'selected';
      }

      $html .= content_tag('option', $value, $option_options)."\n";
    }
  }

  return $html;
}

/**
 * Returns an HTML <form> tag that points to a valid action, route or URL as defined by <i>$url_for_options</i>.
 *
 * By default, the form tag is generated in POST format, but can easily be configured along with any additional
 * HTML parameters via the optional <i>$options</i> parameter. If you are using file uploads, be sure to set the 
 * <i>multipart</i> option to true.
 *
 * <b>Options:</b>
 * - multipart - When set to true, enctype is set to "multipart/form-data".
 *
 * <b>Examples:</b>
 *   <code><?php echo form_tag('@myroute'); ?></code>
 *   <code><?php echo form_tag('/module/action', array('name' => 'myformname', 'multipart' => true)); ?></code>
 *
 * @param  string valid action, route or URL
 * @param  array optional HTML parameters for the <form> tag
 * @return string opening HTML <form> tag with options
 */
function form_tag($url_for_options = '', $options = array())
{
  $options = _parse_attributes($options);

  $html_options = $options;
  if (!isset($html_options['method']))
  {
    $html_options['method'] = 'post';
  }

  if (_get_option($html_options, 'multipart'))
  {
    $html_options['enctype'] = 'multipart/form-data';
  }

  $html_options['action'] = url_for($url_for_options);

  return tag('form', $html_options, true);
}

/**
 * Returns a <select> tag, optionally comprised of <option> tags.
 *
 * The select tag does not generate <option> tags by default.  
 * To do so, you must populate the <i>$option_tags</i> parameter with a string of valid HTML compliant <option> tags.
 * Fortunately, Symfony provides a handy helper function to convert an array of data into option tags (see options_for_select). 
 * If you need to create a "multiple" select tag (ability to select multiple options), set the <i>multiple</i> option to true.  
 * Doing so will automatically convert the name field to an array type variable (i.e. name="name" becomes name="name[]").
 * 
 * <b>Options:</b>
 * - multiple - If set to true, the select tag will allow multiple options to be selected at once.
 *
 * <b>Examples:</b>
 * <code>
 *  $person_list = array(1 => 'Larry', 2 => 'Moe', 3 => 'Curly');
 *  echo select_tag('person', options_for_select($person_list, $sf_params->get('person')), array('class' => 'full'));
 * </code>
 *
 * <code>
 *  echo select_tag('department', options_for_select($department_list), array('multiple' => true));
 * </code>
 *
 * <code>
 *  echo select_tag('url', options_for_select($url_list), array('onChange' => 'Javascript:this.form.submit();'));
 * </code>
 *
 * @param  string field name 
 * @param  mixed contains a string of valid <option></option> tags, or an array of options that will be passed to options_for_select
 * @param  array  additional HTML compliant <select> tag parameters
 * @return string <select> tag optionally comprised of <option> tags.
 * @see options_for_select, content_tag
 */
function select_tag($name, $option_tags = null, $options = array())
{
  $options = _convert_options($options);
  $id = $name;
  if (isset($options['multiple']) && $options['multiple'] && substr($name, -2) !== '[]')
  {
    $name .= '[]';
  }
  if (is_array($option_tags))
  {
    $option_tags = options_for_select($option_tags);
  }

  return content_tag('select', $option_tags, array_merge(array('name' => $name, 'id' => get_id_from_name($id)), $options));
}

/**
 * Returns an XHTML compliant <input> tag with type="text".
 *
 * The input_tag helper generates your basic XHTML <input> tag and can utilize any standard <input> tag parameters 
 * passed in the optional <i>$options</i> parameter.
 *
 * <b>Examples:</b>
 * <code>
 *  echo input_tag('name');
 * </code>
 *
 * <code>
 *  echo input_tag('amount', $sf_params->get('amount'), array('size' => 8, 'maxlength' => 8));
 * </code>
 *
 * @param  string field name 
 * @param  string selected field value
 * @param  array  additional HTML compliant <input> tag parameters
 * @return string XHTML compliant <input> tag with type="text"
 */
function input_tag($name, $value = null, $options = array())
{
  return tag('input', array_merge(array('type' => 'text', 'name' => $name, 'id' => get_id_from_name($name, $value), 'value' => $value), _convert_options($options)));
}

/**
 * Returns an XHTML compliant <input> tag with type="hidden".
 *
 * Similar to the input_tag helper, the input_hidden_tag helper generates an XHTML <input> tag and can utilize 
 * any standard <input> tag parameters passed in the optional <i>$options</i> parameter.  The only difference is 
 * that it creates the tag with type="hidden", meaning that is not visible on the page.
 *
 * <b>Examples:</b>
 * <code>
 *  echo input_hidden_tag('id', $id);
 * </code>
 *
 * @param  string field name 
 * @param  string populated field value
 * @param  array  additional HTML compliant <input> tag parameters
 * @return string XHTML compliant <input> tag with type="hidden"
 */
function input_hidden_tag($name, $value = null, $options = array())
{
  $options = _parse_attributes($options);

  $options['type'] = 'hidden';
  return input_tag($name, $value, $options);
}

/**
 * Returns an XHTML compliant <input> tag with type="file".
 *
 * Similar to the input_tag helper, the input_hidden_tag helper generates your basic XHTML <input> tag and can utilize
 * any standard <input> tag parameters passed in the optional <i>$options</i> parameter.  The only difference is that it 
 * creates the tag with type="file", meaning that next to the field will be a "browse" (or similar) button. 
 * This gives the user the ability to choose a file from there computer to upload to the web server.  Remember, if you 
 * plan to upload files to your website, be sure to set the <i>multipart</i> option form_tag helper function to true 
 * or your files will not be properly uploaded to the web server.
 *
 * <b>Examples:</b>
 * <code>
 *  echo input_file_tag('filename', array('size' => 30));
 * </code>
 *
 * @param  string field name 
 * @param  array  additional HTML compliant <input> tag parameters
 * @return string XHTML compliant <input> tag with type="file"
 * @see input_tag, form_tag
 */
function input_file_tag($name, $options = array())
{
  $options = _parse_attributes($options);

  $options['type'] = 'file';
  return input_tag($name, null, $options);
}

/**
 * Returns an XHTML compliant <input> tag with type="password".
 *
 * Similar to the input_tag helper, the input_hidden_tag helper generates your basic XHTML <input> tag and can utilize
 * any standard <input> tag parameters passed in the optional <i>$options</i> parameter.  The only difference is that it 
 * creates the tag with type="password", meaning that the text entered into this field will not be visible to the end user.
 * In most cases it is replaced by  * * * * * * * *.  Even though this text is not readable, it is recommended that you do not 
 * populate the optional <i>$value</i> option with a plain-text password or any other sensitive information, as this is a 
 * potential security risk.
 *
 * <b>Examples:</b>
 * <code>
 *  echo input_password_tag('password');
 *  echo input_password_tag('password_confirm');
 * </code>
 *
 * @param  string field name
 * @param  string populated field value
 * @param  array  additional HTML compliant <input> tag parameters
 * @return string XHTML compliant <input> tag with type="password"
 * @see input_tag
 */
function input_password_tag($name = 'password', $value = null, $options = array())
{
  $options = _parse_attributes($options);

  $options['type'] = 'password';
  return input_tag($name, $value, $options);
}

/**
 * Returns a <textarea> tag, optionally wrapped with an inline rich-text JavaScript editor.
 *
 * The texarea_tag helper generates a standard HTML <textarea> tag and can be manipulated with
 * any number of standard HTML parameters via the <i>$options</i> array variable.  However, the 
 * textarea tag also has the unique capability of being transformed into a WYSIWYG rich-text editor
 * such as TinyMCE (http://tinymce.moxiecode.com) very easily with the use of some specific options:
 *
 * <b>Options:</b>
 *  - rich: A rich text editor class (for example sfRichTextEditorTinyMCE for TinyMCE).
 *
 * <b>Examples:</b>
 * <code>
 *  echo textarea_tag('notes');
 * </code>
 *
 * <code>
 *  echo textarea_tag('description', 'This is a description', array('rows' => 10, 'cols' => 50));
 * </code> 
 *
 * @param  string field name
 * @param  string populated field value
 * @param  array  additional HTML compliant <textarea> tag parameters
 *
 * @return string <textarea> tag optionally wrapped with a rich-text WYSIWYG editor
 */
function textarea_tag($name, $content = null, $options = array())
{
  $options = _parse_attributes($options);

  if ($size = _get_option($options, 'size'))
  {
    list($options['cols'], $options['rows']) = split('x', $size, 2);
  }

  return content_tag('textarea', escape_once((is_object($content)) ? $content->__toString() : $content), array_merge(array('name' => $name, 'id' => get_id_from_name(_get_option($options, 'id', $name), null)), _convert_options($options)));
}

/**
 * Returns an XHTML compliant <input> tag with type="checkbox".
 *
 * When creating multiple checkboxes with the same name, be sure to use an array for the
 * <i>$name</i> parameter (i.e. 'name[]').  The checkbox_tag is smart enough to create unique ID's
 * based on the <i>$value</i> parameter like so:
 *
 * <samp>
 *  <input type="checkbox" name="status[]" id="status_3" value="3" />
 *  <input type="checkbox" name="status[]" id="status_4" value="4" />
 * </samp>
 * 
 * <b>Examples:</b>
 * <code>
 *  echo checkbox_tag('newsletter', 1, $sf_params->get('newsletter'));
 * </code>
 *
 * <code>
 *  echo checkbox_tag('option_a', 'yes', true, array('class' => 'style_a'));
 * </code>
 *
 * <code>
 *  // one request variable with an array of checkbox values
 *  echo checkbox_tag('choice[]', 1);
 *  echo checkbox_tag('choice[]', 2);
 *  echo checkbox_tag('choice[]', 3);
 *  echo checkbox_tag('choice[]', 4); 
 * </code>
 *
 * <code>
 *  // assuming you have Prototype.js enabled, you could do this
 *  echo checkbox_tag('show_tos', 1, false, array('onclick' => "Element.toggle('tos'); return false;"));
 * </code>
 *
 * @param  string field name 
 * @param  string checkbox value (if checked)
 * @param  bool   is the checkbox checked? (1 or 0)
 * @param  array  additional HTML compliant <input> tag parameters
 * @return string XHTML compliant <input> tag with type="checkbox"
 */
function checkbox_tag($name, $value = '1', $checked = false, $options = array())
{
  $html_options = array_merge(array('type' => 'checkbox', 'name' => $name, 'id' => get_id_from_name($name, $value), 'value' => $value), _convert_options($options));

  if ($checked)
  {
    $html_options['checked'] = 'checked';
  }

  return tag('input', $html_options);
}

/**
 * Returns an XHTML compliant <input> tag with type="radio".
 *
 * <b>Examples:</b>
 * <code>
 *  echo ' Yes '.radiobutton_tag('newsletter', 1);
 *  echo ' No '.radiobutton_tag('newsletter', 0); 
 * </code>
 *
 * @param  string field name 
 * @param  string radio button value (if selected)
 * @param  bool   is the radio button selected? (1 or 0)
 * @param  array  additional HTML compliant <input> tag parameters
 * @return string XHTML compliant <input> tag with type="radio"
 */
function radiobutton_tag($name, $value, $checked = false, $options = array())
{
  $html_options = array_merge(array('type' => 'radio', 'name' => $name, 'id' => get_id_from_name($name, $value), 'value' => $value), _convert_options($options));

  if ($checked)
  {
    $html_options['checked'] = 'checked';
  }

  return tag('input', $html_options);
}

/**
 * Returns an XHTML compliant <input> tag with type="submit".
 * 
 * By default, this helper creates a submit tag with a name of <em>commit</em> to avoid
 * conflicts with other parts of the framework.  It is recommended that you do not use the name
 * "submit" for submit tags unless absolutely necessary. Also, the default <i>$value</i> parameter
 * (title of the button) is set to "Save changes", which can be easily overwritten by passing a 
 * <i>$value</i> parameter.
 *
 * <b>Examples:</b>
 * <code>
 *  echo submit_tag();
 * </code>
 *
 * <code>
 *  echo submit_tag('Update Record');
 * </code>
 *
 * @param  string field value (title of submit button)
 * @param  array  additional HTML compliant <input> tag parameters
 * @return string XHTML compliant <input> tag with type="submit"
 */
function submit_tag($value = 'Save changes', $options = array())
{
  return tag('input', array_merge(array('type' => 'submit', 'name' => 'commit', 'value' => $value), _convert_options_to_javascript(_convert_options($options))));
}

/**
 * Returns an XHTML compliant <input> tag with type="reset".
 *
 * By default, this helper creates a submit tag with a name of <em>reset</em>.  Also, the default 
 * <i>$value</i> parameter (title of the button) is set to "Reset" which can be easily overwritten 
 * by passing a <i>$value</i> parameter.
 *
 * <b>Examples:</b>
 * <code>
 *  echo reset_tag();
 * </code>
 *
 * <code>
 *  echo reset_tag('Start Over');
 * </code>
 *
 * @param  string field value (title of reset button)
 * @param  array  additional HTML compliant <input> tag parameters
 * @return string XHTML compliant <input> tag with type="reset"
 */
function reset_tag($value = 'Reset', $options = array())
{
  return tag('input', array_merge(array('type' => 'reset', 'name' => 'reset', 'value' => $value), _convert_options($options)));
}

/**
 * Returns an XHTML compliant <input> tag with type="image".
 *
 * The submit_image_tag is very similar to the submit_tag, the only difference being that it uses an image
 * for the submit button instead of the browser-generated default button. The image is defined by the 
 * <i>$source</i> parameter and must be a valid image, either local or remote (URL). By default, this 
 * helper creates a submit tag with a name of <em>commit</em> to avoid conflicts with other parts of the 
 * framework.  It is recommended that you do not use the name "submit" for submit tags unless absolutely necessary.
 *
 * <b>Examples:</b>
 * <code>
 *  // Assuming your image is in the /web/images/ directory
 *  echo submit_image_tag('my_submit_button.gif');
 * </code>
 *
 * <code>
 *  echo submit_image_tag('http://mydomain.com/my_submit_button.gif');
 * </code>
 *
 * @param  string path to image file
 * @param  array  additional HTML compliant <input> tag parameters
 * @return string XHTML compliant <input> tag with type="image"
 */
function submit_image_tag($source, $options = array())
{
  if (!isset($options['alt']))
  {
    $path_pos = strrpos($source, '/');
    $dot_pos = strrpos($source, '.');
    $begin = $path_pos ? $path_pos + 1 : 0;
    $nb_str = ($dot_pos ? $dot_pos : strlen($source)) - $begin;
    $options['alt'] = ucfirst(substr($source, $begin, $nb_str));
  }

  return tag('input', array_merge(array('type' => 'image', 'name' => 'commit', 'src' => image_path($source)), _convert_options_to_javascript(_convert_options($options))));
}

/**
 * Returns a <label> tag with <i>$label</i> for the specified <i>$id</i> parameter.
 *
 * @param  string id
 * @param  string label or title
 * @param  array  additional HTML compliant <label> tag parameters
 * @return string <label> tag with <i>$label</i> for the specified <i>$id</i> parameter.
 */
function label_for($id, $label, $options = array())
{
  $options = _parse_attributes($options);

  return content_tag('label', $label, array_merge(array('for' => get_id_from_name($id, null)), $options));
}

/**
 * Returns a formatted ID based on the <i>$name</i> parameter and optionally the <i>$value</i> parameter.
 *
 * This function determines the proper form field ID name based on the parameters. If a form field has an
 * array value as a name we need to convert them to proper and unique IDs like so:
 * <samp>
 *  name[] => name (if value == null)
 *  name[] => name_value (if value != null)
 *  name[bob] => name_bob
 *  name[item][total] => name_item_total
 * </samp>
 *
 * <b>Examples:</b>
 * <code>
 *  echo get_id_from_name('status[]', '1');
 * </code>
 *
 * @param  string field name 
 * @param  string field value
 * @return string <select> tag populated with all the languages in the world.
 */
function get_id_from_name($name, $value = null)
{
  // check to see if we have an array variable for a field name
  if (strstr($name, '['))
  {
    $name = str_replace(array('[]', '][', '[', ']'), array((($value != null) ? '_'.$value : ''), '_', '_', ''), $name);
  }

  return $name;
}


/**
 * Converts specific <i>$options</i> to their correct HTML format
 *
 * @param  array options
 * @return array returns properly formatted options 
 */
function _convert_options($options)
{
  $options = _parse_attributes($options);

  foreach (array('disabled', 'readonly', 'multiple') as $attribute)
  {
    if (array_key_exists($attribute, $options))
    {
      if ($options[$attribute])
      {
        $options[$attribute] = $attribute;
      }
      else
      {
        unset($options[$attribute]);
      }
    }
  }

  return $options;
}

function _convert_include_custom_for_select($options, &$select_options)
{
  if (_get_option($options, 'include_blank'))
  {
    $select_options[''] = '';
  }
  else if ($include_custom = _get_option($options, 'include_custom'))
  {
    $select_options[''] = $include_custom;
  }
}


function form_text_value($key, $default)
{
  if(isset($_REQUEST[$key]))
    echo attribute_escape($_REQUEST[$key]);
  else
    echo $default;
}

function form_checkbox_checked($key, $default = true)
{
  if(isset($_REQUEST[$key]))
    echo 'checked="checked"';
  else
    echo ($default) ? 'checked="checked"' : '';
}