<?php

// Taken from symfony-project.com. Some slightly modified

/**
 * Constructs an html tag.
 *
 * @param  $name    string  tag name
 * @param  $options array   tag options
 * @param  $open    boolean true to leave tag open
 * @return string
 */
function tag($name, $options = array(), $open = false)
{
  if (!$name)
  {
    return '';
  }

  return '<'.$name._tag_options($options).(($open) ? '>' : ' />');
}

function content_tag($name, $content = '', $options = array())
{
  if (!$name)
  {
    return '';
  }

  return '<'.$name._tag_options($options).'>'.$content.'</'.$name.'>';
}

function cdata_section($content)
{
  return "<![CDATA[$content]]>";
}

/**
 * Escape carrier returns and single and double quotes for Javascript segments.
 */
function escape_javascript($javascript = '')
{
  $javascript = preg_replace('/\r\n|\n|\r/', "\\n", $javascript);
  $javascript = preg_replace('/(["\'])/', '\\\\\1', $javascript);

  return $javascript;
}

/**
 * Escapes an HTML string.
 *
 * @param  string HTML string to escape
 * @return string escaped string
 */
function escape_once($html)
{
  return fix_double_escape(htmlspecialchars($html));
}

/**
 * Fixes double escaped strings.
 *
 * @param  string HTML string to fix
 * @return string escaped string
 */
function fix_double_escape($escaped)
{
  return preg_replace('/&amp;([a-z]+|(#\d+)|(#x[\da-f]+));/i', '&$1;', $escaped);
}

function _tag_options($options = array())
{
  $options = _parse_attributes($options);

  $html = '';
  foreach ($options as $key => $value)
  {
    $html .= ' '.$key.'="'.escape_once($value).'"';
  }

  return $html;
}

function _parse_attributes($string)
{
  return is_array($string) ? $string : WPOTools::stringToArray($string);
}

function _get_option(&$options, $name, $default = null)
{
  if (array_key_exists($name, $options))
  {
    $value = $options[$name];
    unset($options[$name]);
  }
  else
  {
    $value = $default;
  }

  return $value;
}

function _convert_options_to_javascript($html_options, $internal_uri = '')
{
  // confirm
  $confirm = isset($html_options['confirm']) ? $html_options['confirm'] : '';
  unset($html_options['confirm']);

  // popup
  $popup = isset($html_options['popup']) ? $html_options['popup'] : '';
  unset($html_options['popup']);

  // post
  $post = isset($html_options['post']) ? $html_options['post'] : '';
  unset($html_options['post']);

  $onclick = isset($html_options['onclick']) ? $html_options['onclick'] : '';

  if ($popup && $post)
  {}
  else if ($confirm && $popup)
  {
    $html_options['onclick'] = $onclick.'if ('._confirm_javascript_function($confirm).') { '._popup_javascript_function($popup, $internal_uri).' };return false;';
  }
  else if ($confirm && $post)
  {
    $html_options['onclick'] = $onclick.'if ('._confirm_javascript_function($confirm).') { '._post_javascript_function().' };return false;';
  }
  else if ($confirm)
  {
    if ($onclick)
    {
      $html_options['onclick'] = 'if ('._confirm_javascript_function($confirm).') {'.$onclick.'}';
    }
    else
    {
      $html_options['onclick'] = 'return '._confirm_javascript_function($confirm).';';
    }
  }
  else if ($post)
  {
    $html_options['onclick'] = $onclick._post_javascript_function().'return false;';
  }
  else if ($popup)
  {
    $html_options['onclick'] = $onclick._popup_javascript_function($popup, $internal_uri).'return false;';
  }

  return $html_options;
}

function _data_value($arr, $key, $default = false)
{
  if($arr && isset($arr[$key]))
    return escape_once($arr[$key]);
  return escape_once($default);
}
