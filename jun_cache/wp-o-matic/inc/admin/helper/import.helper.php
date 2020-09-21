<?php

  function import_process_tree($opml) {
    import_process_item($opml);
  }                                          
  
  function import_process_item($opml, $campaign = 0) {   
  
    $i = 0;
    
    foreach($opml['OUTLINE'] as $key => $item)  
    {          
      ?>
      <li class="<?php echo $i % 2 ? 'even' : 'odd' ?>">
      <?php
      if(isset($item['OUTLINE'])) 
      {           
        $campaign++;
        ?>
        <?php echo input_hidden_tag('campaign['.$campaign.']', import_get_item_title($item)) ?>
        <div class="check">
          <a href="#">(un)check all</a>
        </div>
        <h4><?php echo import_get_item_title($item) ?></h4>
        <ul>
          <?php import_process_item($item, $campaign) ?>
        </ul>
        <?php   
      } else {
        $url = urlencode(import_get_item_xmlurl($item));
        
        if(!$campaign)
        {
          $campaign++;
          echo input_hidden_tag('campaign['.$campaign.']', import_get_item_title($item));
        }
        
        if($url):
      ?>
        <ul class="import_links">
          <li><?php echo checkbox_tag('feed['.$campaign.']['.$url.']', 1, (isset($_REQUEST['add']) ? (isset($_REQUEST['feed']) ? _data_value($_REQUEST['feed'][$campaign], $url) : false) : true )) ?></li>          
          <li><a class="feed_rss" href="<?php echo import_get_item_xmlurl($item) ?>">RSS</a></li>          
          <?php if(import_get_item_htmlurl($item)): ?>
          <li><a class="feed_link" href="<?php echo import_get_item_htmlurl($item) ?>">Website</a></li>
          <?php endif ?>
        </ul>
        <h4><label for="feed_<?php echo $campaign ?>_<?php echo $url ?>"><?php echo import_get_item_title($item) ?></label></h4>
      <?php 
        endif;
      }       
                                          
      echo '</li>'; 
      $i++;                                 
    }              
  }
  
  function import_get_item_title($item)
  {
    return $item['ATTRIBUTES']['TEXT'];
  }
  
  function import_get_item_xmlurl($item) 
  {
    return $item['ATTRIBUTES']['XMLURL'];
  }

  function import_get_item_htmlurl($item) 
  {
    return $item['ATTRIBUTES']['HTMLURL'];
  }


?>