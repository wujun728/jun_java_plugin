<?php 

  require_once( dirname(__FILE__) . '/../../../wp-config.php');
  
  if(! isset($_REQUEST['item']))
    die(__('This file cannot be accessed directly', 'wpomatic'));
  else 
  {
    preg_match('/\w*/', $_REQUEST['item'], $content);
    
    $file = dirname(__FILE__) . '/inc/help/' . $content[0] . '.php';    
    
    ob_start();
    @include($file);      
    $content = ob_get_clean();
  } 
?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN"
  "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
    <meta http-equiv="Content-type" content="text/html; charset=utf-8">
    <title><?php _e('Help') ?> &rsaquo; <?php echo $title ?> </title>
    
    <style type="text/css" media="screen">
      * {
        margin: 0;
        padding: 0;
        font: 0.92em "Lucida Grande", "Verdana";
      }
      
      a {
      	border-bottom: 1px solid #69c;
      	color: #00019b;
      	text-decoration: none;
      }
      
      #header {
        background: #14568A;
        border-bottom: 2px solid #448ABD;
        overflow: hidden;
        height: auto !important;
        height: 1%;
        padding: 0.7em 0.9em;
        margin-bottom: 0.8em;
      }
      
      #header h1 {
        font: 1.65em "Georgia";
        color: #C3DEF1;
        float: left;
      }
      
      #header #link_close {
        display: block;
        float: right;
        color: white;
        text-decoration: none;
      }
      
      #content {
        padding: 0 1em;
      }
      
      h2 {
        font-size: 1.1em;
        font-weight: bold;
        border-bottom: 1px dotted #999;
        padding-bottom: 0.3em;
        color: #666;
        margin-bottom: -0.8em;
      }
      
      h3 {
        font-size: 0.95em;
        font-weight: bold;
        margin-top: 1.5em;
        margin-bottom: 0.5em;
        padding: 0.2em 0;
      }
      
      #content ul {
        margin-left: 20px;
      }
      
      #content ul li {
        margin-bottom: 5px;
        list-style-type: square;
      }
      
      #content p {
        line-height: 1.4em;
        margin-bottom: 0.9em;
      }
      
      #content a.link_top {
        float: right;
        margin-top: -2.2em;
      }
      
      #content a.link_main {
        font-style: oblique;
      }
      
      #content strong {
        font-weight: bold;
        background: #FFFFCC;
      }
          
      #content em {
        font-style: oblique;
      }
      
      #content .code { border: 1px solid #ccc; background: #fafafa; margin-bottom: 0.9em; padding: 0.9em 1em 0; }
    </style>
    
    <script type="text/javascript" charset="utf-8">
      var init = function() {
        document.getElementById('link_close').onclick = function() {
          window.close();
          return false;
        }
      }
      
      window.onload = init;
    </script>
  </head>
  <body id="help">
    
    <div id="header">
      <h1>采集器 <?php _e('帮助', 'wpomatic') ?></h1>
      
      <a href="#" id="link_close"><?php _e('Close window', 'wpomatic') ?></a>
    </div>
    
    <div id="content">
      <?php echo ($content) ? $content : __('Help file not found', 'wpomatic') ?>
    </div>
  </body>
</html>