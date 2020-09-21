<?php $this->adminHeader() ?>

  <div class="wrap">
    <h2><?php _e('Logs', 'wpomatic') ?></h2>     
    
    <div class="logs_bar">
      <form action="">
        <?php wp_nonce_field('clean-logs') ?>
        <input type="hidden" name="page" value="wpomatic.php" />
        <input type="hidden" name="s" value="logs" />
        <input type="hidden" name="clean_logs" value="1" />
        
        <p id="clean_logs" class="submit">
          <input type="submit" value="Clean logs" />
        </p>
      </form>
    
      <div id="logs_pages">
        <?php echo $paging ? $paging . ' - ' : '' ?> Displaying <?php echo $total ?> log entries
      </div>
    </div>
    
    <?php if($logs): ?>
    <ul id="logs">
    <?php foreach($logs as $log): ?>
      <li><?php echo WPOTools::timezoneMysql('F j, g:i:s a', $log->created_on) ?> - <?php echo $log->message ?></li>
    <?php endforeach ?>
    </ul>
    <?php else: ?>
    <p><?php _e('No logs to show', 'wpomatic') ?>
    <?php endif ?>
    
  </div>

<?php $this->adminFooter() ?>