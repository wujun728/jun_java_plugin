<?php $this->adminHeader() ?>
  
  <div class="wrap">
    <h2>活动</h2> 
    <?php if(isset($this->forcefetched)): ?>
    <div id="fetched-warning" class="updated">
      <p><?php printf(__("Campaign processed. %s posts fetched", 'wpomatic'), $this->forcefetched) ?></p>
    </div>
    <?php endif; ?>
  
    <table class="widefat"> 
      <thead>
        <tr>
          <th scope="col" style="text-align: center">编号</th>
          <th scope="col"><?php _e('标题', 'wpomatic') ?></th>
          <th style="text-align: center" scope="col"><?php _e('活动', 'wpomatic') ?></th>
      	  <th style="text-align: center" scope="col"><?php _e('文章总数', 'wpomatic') ?></th>
      	  <th scope="col"><?php _e('最后活动', 'wpomatic') ?></th>
      	  <th scope="col" colspan="4" style="text-align: center"><?php _e('操作', 'wpomatic') ?></th>
        </tr>
      </thead>
      
      <tbody id="the-list">            
        <?php if(!$campaigns): ?>
          <tr> 
            <td colspan="5"><?php _e('No campaigns to display', 'wpomatic') ?></td> 
          </tr>  
        <?php else: ?>     
          <?php $class = ''; ?>  
          
          <?php foreach($campaigns as $campaign): ?>
          <?php $class = ('alternate' == $class) ? '' : 'alternate'; ?>             
          <tr id='campaign-<?php echo $campaign->id ?>' class='<?php echo $class ?> <?php if($_REQUEST['id'] == $campaign->id) echo 'highlight'; ?>'> 
            <th scope="row" style="text-align: center"><?php echo $campaign->id ?></th> 
            <td><?php echo attribute_escape($campaign->title) ?></td>          
            <td style="text-align: center"><?php echo _e($campaign->active ? 'Yes' : 'No', 'wpomatic') ?></td>
            <td style="text-align: center"><?php echo $campaign->count ?></td>        
            <td><?php echo $campaign->lastactive != '0000-00-00 00:00:00' ? WPOTools::timezoneMysql('F j, g:i a', $campaign->lastactive) : __('Never', 'wpomatic') ?></td>
            <td><a href="<?php echo $this->adminurl ?>&amp;s=edit&amp;id=<?php echo $campaign->id ?>" class='edit'>Edit</a></td> 
            <td><?php echo "<a href='" . wp_nonce_url($this->adminurl . '&amp;s=forcefetch&amp;id=' . $campaign->id, 'forcefetch-campaign_' . $campaign->id) . "' class='edit' onclick=\"return confirm('". __('Are you sure you want to process all feeds from this campaign?', 'wpomatic') ."')\">" . __('Fetch', 'wpomatic') . "</a>"; ?></td>
            <td><?php echo "<a href='" . wp_nonce_url($this->adminurl . '&amp;s=reset&amp;id=' . $campaign->id, 'reset-campaign_' . $campaign->id) . "' class='delete' onclick=\"return confirm('". __('Are you sure you want to reset this campaign? Resetting does not affect already created wp posts.', 'wpomatic') ."')\">" . __('Reset', 'wpomatic') . "</a>"; ?></td>
            <td><?php echo "<a href='" . wp_nonce_url($this->adminurl . '&amp;s=delete&amp;id=' . $campaign->id, 'delete-campaign_' . $campaign->id) . "' class='delete' onclick=\"return confirm('" . __("You are about to delete the campaign '%s'. This action doesn't remove campaign generated wp posts.\n'OK' to delete, 'Cancel' to stop.") ."')\">" . __('Delete', 'wpomatic') . "</a>"; ?></td>            
          </tr>              
          <?php endforeach; ?>                    
        <?php endif; ?>
      </tbody>
    </table>
    
    <div id="ajax-response"></div>
    
  </div>
  
<?php $this->adminFooter() ?>