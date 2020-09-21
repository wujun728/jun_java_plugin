<?php require_once(WPOTPL . '/helper/form.helper.php' ) ?>
<?php $this->adminHeader() ?>
      
  <div id="wpo-section-options" class="wrap">
    <h2>选项</h2>     
    
    <?php if(isset($updated)): ?>    
      <div id="added-warning" class="updated"><p><?php _e('Options saved.', 'wpomatic') ?></p></div>
    <?php endif ?>

    <?php if(isset($not_writable)): ?>
      <div class="error"><p><?php _e('Image cache path ' . WPODIR . get_option('wpo_cachepath') . ' is not writable!' ) ?></p></div>
    <?php endif ?>

    <form action="" method="post" accept-charset="utf-8">      
      <input type="hidden" name="update" value="1" />
      
      <ul id="options">
        <li id="options_cron">
          <?php echo label_for('option_unixcron', __('Unix cron 命令', 'wpomatic')) ?>
          <?php echo checkbox_tag('option_unixcron', 1, get_option('wpo_unixcron')) ?>
        
          <h3>Cron 命令:</h3>
          <div id="cron_command" class="command"><?php echo $this->cron_command ?></div>
          
          <h3>WebCron的准备网址:</h3>
          <div id="cron_command" class="command"><?php echo $this->cron_url ?></div>
        
          <p class="note"><?php _e('cron是设立处理抓取。', 'wpomatic') ?> <a href="<?php echo $this->helpurl ?>cron" class="help_link"><?php _e('More', 'wpomatic') ?></a></p>
        </li>

        <li>
          <?php echo label_for('option_logging', __('启用日志记录', 'wpomatic')) ?>
          <?php echo checkbox_tag('option_logging', 1, get_option('wpo_log')) ?>
        
          <p class="note"><?php _e('启用数据库驱动的事件记录。', 'wpomatic') ?> <a href="<?php echo $this->helpurl ?>logging" class="help_link"><?php _e('More', 'wpomatic') ?></a></p>
        </li>
      
        <li>
          <?php echo label_for('option_logging_stdout', __('启用日志记录标准输出', 'wpomatic')) ?>
          <?php echo checkbox_tag('option_logging_stdout', 1, get_option('wpo_log_stdout')) ?>
        
          <p class="note"><?php _e('启用此选项，采集器将试图表明您实时记录创建时使用手册抓取。', 'wpomatic') ?> <a href="<?php echo $this->helpurl ?>logging" class="help_link"><?php _e('更多', 'wpomatic') ?></a></p>
        </li>
      
        <li>
          <?php echo label_for('option_caching', __('缓存图像', 'wpomatic')) ?>
          <?php echo checkbox_tag('option_caching', 1, get_option('wpo_cacheimages')) ?>
        
          <p class="note"><?php _e('此选项将覆盖所有活动特定的设置', 'wpomatic') ?> <a href="<?php echo $this->helpurl ?>image_caching" class="help_link"><?php _e('More', 'wpomatic') ?></a></p>
        </li>
        
        <li>
          <?php echo label_for('option_cachepath', __('缓存图像路径')) ?>
          <?php echo input_tag('option_cachepath', get_option('wpo_cachepath')) ?>           
          
          <p class="note"><?php printf(__('路径 %s 必须存在的，是可写的服务器，通过浏览器访问。', 'wpomatic'), '<span id="cachepath">'. WPODIR . '<span id="cachepath_input">' . get_option('wpo_cachepath') . '</span></span>') ?></p>                 
        </li>
      </ul>     
    
      <p class="submit">
      <?php echo submit_tag(__('保存', 'wpomatic')) ?>
      </p>
    </form>
  </div>
  
<?php $this->adminFooter() ?>