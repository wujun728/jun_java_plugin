<?php $this->adminHeader() ?>
        
  <div class="wrap">
    <h2><?php _e('仪表盘', 'wpomatic') ?></h2>                            
                                          
    <div id="sidebar">  
      <div id="sidebar_logging">                     
        <a href="<?php echo $this->helpurl ?>logging" class="help_link"><?php _e('帮助', 'wpomatic') ?></a>                                                                        
        <h3>&rsaquo; <?php _e('最新的日志条目', 'wpomatic') ?> <a href="<?php echo $this->adminurl ?>&amp;s=logs"><?php _e('(查看全部)', 'wpomatic')?></a></h3>
        <?php if(!$logs): ?>
        <p class="none"><?php echo _e('没有行动显示', 'wpomatic') ?></p>
        <?php else: ?>
        <ul id="logs">
          <?php foreach($logs as $log): ?>                         
          <li><?php echo WPOTools::timezoneMysql('F j, g:i a', $log->created_on) . ' &mdash; <strong>' . attribute_escape($log->message) ?></strong></li>
          <?php endforeach; ?>
        </ul>         
        <?php endif; ?>                                                                               

        <p id="log_status"><?php _e(sprintf('当前记录<strong>%s</strong>', __($logging ? '启用' : '禁用')), 'wpomatic') ?> (<a title="<?php _e('我们建议只有采集新数据时开启.', 'wpomatic') ?>" href="<?php echo $this->adminurl ?>&amp;s=options"><?php _e('改变', 'wpomatic')?></a>).</p>
      </div>
    </div>  
    
    <div id="main">             

      <p><?php _e('数据采集仪表板欢迎您！您可以在这里监控您的采集状态，并且可以配置采集参数.', 'wpomatic') ?></p>       
    
      <h3>下一步处理</h3>
      <?php if(count($nextcampaigns) == 0): ?>
      <p class="none"><?php _e('没有运动显示', 'wpomatic') ?>
      <?php else: ?>
        <ol class="campaignlist">
          <?php foreach($nextcampaigns as $campaign): 
            $cl = $this->getCampaignRemaining($campaign);
            $cl = WPOTools::calcTime($cl, 0, 'd', false);
            
            $timestr = '';
            if($cl['days']) $timestr .= $cl['days'] . __('d', 'wpomatic') . ' ';
            if($cl['hours']) $timestr .= $cl['hours'] . __('h', 'wpomatic') . ' ';
            if($cl['minutes']) $timestr .= $cl['minutes'] . __('m', 'wpomatic') . ' ';      
            if($cl['seconds']) $timestr .= $cl['seconds'] . __('s', 'wpomatic');     
          ?>                         
          <li>
            <span class="details"><?php echo ($timestr) ? $timestr : __('Next!', 'wpomatic') ?></span>
            <a href="<?php echo $this->adminurl ?>&amp;s=list&amp;id=<?php echo $campaign->id ?>"><?php echo attribute_escape($campaign->title) ?></a></li>
          <?php endforeach; ?>  
        </ol>
      <?php endif; ?>
      
      <h3>最新的加工活动</h3>
      <?php if(count($lastcampaigns) == 0): ?>
      <p class="none"><?php _e('没有活动显示', 'wpomatic') ?>
      <?php else: ?>
        <ol class="campaignlist">
          <?php foreach($lastcampaigns as $campaign): ?>                         
          <li>
            <span class="details"><?php echo WPOTools::timezoneMysql('F j, g:i a', $campaign->lastactive) ?></span>
            <a href="<?php echo $this->adminurl ?>&amp;s=list&amp;id=<?php echo $campaign->id ?>"><?php echo attribute_escape($campaign->title) ?></a></li>
          <?php endforeach; ?>  
        </ol>
      <?php endif; ?>
    
      <h3><?php echo _e('你有先的活动', 'wpomatic') ?></h3>
      <?php if(count($campaigns) == 0): ?>
      <p class="none"><?php _e('没有活动显示', 'wpomatic') ?></p>
      <?php else: ?>
      <ol class="campaignlist">
        <?php foreach($campaigns as $campaign): ?>                         
        <li>
          <span class="details"><?php echo $campaign->count ?></span>
          <a href="<?php echo $this->adminurl ?>&amp;s=list&amp;id=<?php echo $campaign->id ?>"><?php echo attribute_escape($campaign->title) ?></a></li>
        <?php endforeach; ?>  
      </ol>       
      <?php endif; ?>
          
    </div>
  </div>
  
<?php $this->adminFooter() ?>