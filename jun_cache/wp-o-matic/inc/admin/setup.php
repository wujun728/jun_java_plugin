<?php $this->adminHeader() ?>

  <form action="<?php echo $this->adminurl ?>&amp;s=setup" method="post">
    <input type="hidden" name="dosetup" value="1" id="dosetup" />
    
    <div id="wpo-section-setup" class="wrap">
      <h2><?php _e('Setup', 'wpomatic') ?></h2>     
    
      <p><?php _e('Please follow the next few steps to make sure WP-o-Matic works perfectly for you.', 'wpomatic') ?></p>
    
      <ol id="setup_steps">
        <li id="step_1" class="current">
          <p><?php _e('First of all, make sure <a href="http://simplepie.org" target="_blank">SimplePie</a>, the feed parsing engine that empowers WP-o-Matic is compatible with your server setup.', 'wpomatic') ?></p>          
          <p><?php printf(__('To do so, please run <a href="%s" target="_blank">this test</a> and evaluate the results. Typically, <em>You have everything you need to run SimplePie properly! Congratulations!</em> is a result you\'d be happy with.', 'wpomatic'), $this->pluginpath . '/inc/simplepie/simplepie.tests.php') ?></p>
          <p><?php _e('Even though WP-o-Matic is bundled with the latest SimplePie version at the time it was released, we encourage you to install the <a href="http://wordpress.org/extend/plugins/simplepie-core/">SimplePie Core Wordpress</a> plugin. It is automatically used in place of the bundled version, and it allows you to update SimplePie easily for all the plugins that use it.', 'wpomatic') ?></p>
        </li>
      
        <li id="step_2">
          <p><?php _e('Timing is a key aspect of this type of feed aggregating software.', 'wpomatic') ?></p>
          <p><?php printf(__('For WP-o-Matic to work properly, you have to make sure server time is accurate, and that the correct timezone is configured <a href="%s">here</a> (hint: <strong>Date and Time</strong> subsection)', 'wpomatic'), $this->optionsurl) ?></p>
          <p><?php _e('Make sure the following settings are correct:', 'wpomatic') ?></p>
          <div class="command">
             <strong><?php _e('UTC time:', 'wpomatic') ?></strong> <?php echo gmdate('d F, Y H:i:s', current_time('timestamp', true)) ?> <br />
             <strong><?php _e('Your time:', 'wpomatic') ?></strong> <?php echo gmdate('d F, Y H:i:s', current_time('timestamp')) ?>
          </div>
          <p><?php _e('Do not proceed unless time is configured properly.', 'wpomatic') ?></p>
        </li>
      
        <li id="step_3">
          <p><?php _e('If you want to go automated, and let WP-o-Matic do the nasty job of pulling rss feeds and creating posts, you need to set up a <strong>cron job</strong>. For performance reasons, it\'s highly recommended that you do so, but you can also let WP-o-Matic handle it in browser requests.', 'wpomatic') ?></p>
        
          <p><?php printf(__('Add this line to your crontab, or use your web panel of choice interface. %s', 'wpomatic'), ($nophp) ? __('<strong>Warning!</strong> WP-o-Matic has been unable to detect the location of the wget command. This means you\'ll have to check it exists from the command line or find it by your own. In the case it\'s not installed, you can alternative use the ftp command, or lynx -dump', 'wpomatic') : '') ?></p>
        
          <div class="command"><?php echo $command ?></div>
        
          <p><?php _e('If you decide not to enable cron, a pseudo-cron web-based approach takes place, which relies on web requests. Disadvantages? It can harm user experience (the page won\'t load for the visitor until feeds are parsed), and will only take place if someone visits your site, which is not good for sites that may not receive visits in days (intranet sites may be an example).', 'wpomatic') ?></p>
        
          <p><?php _e('There\'s, however, an option in the middle. It\'s called <a href="http://webcron.org/index.php?&lang=en">WebCron</a>, a service that will request a webpage you specify at the time you specify, just like cron. Disadvantages? We don\'t know about its reliability. But if you can\'t run cron, this might be a great option for you. Set it up for every hour and point it to this URL: ', 'wpomatic') ?>
                
          <div class="command"><?php echo $url ?></div>
        
          <p><input type="checkbox" name="option_unixcron" checked="checked" id="option_unixcron" /> <label for="option_unixcron"><?php _e('I\'ll be using a cron job (for Unix-like cron or WebCron, uncheck if you want pseudo-cron functionality)', 'wpomatic') ?></label></p>
        </li>
      
        <?php if($safe_mode): ?>
        <li id="step_4">
          <p><?php _e('It appears that you\'re running Wordpress in a <strong>Safe Mode</strong> environment. If you\'re <strong>not going</strong> to use cron, or when you process feeds manually from your browser, you may experience problems with the execution time.', 'wpomatic') ?></p>
        
          <p><?php _e('PHP sets a limit (that you hosting provider can tweak) for execution time of scripts, except when running from command line. WP-o-Matic tries to override it, but is unable to do so when safe_mode directive is enabled, like in this case.', 'wpomatic') ?></p>
        
          <p><?php _e('The solution typically involves contacting your hosting support, or switching to a new host (d\'uh)', 'wpomatic') ?></p>
        </li>
        <?php endif ?>
      
        <li id="step_<?php echo ($safe_mode) ? 5 : 4 ?>">
          <p><?php _e('And you\'re done!', 'wpomatic') ?></p>
          <p><?php _e('Remember that these settings can be edited from the Options tab in the future.') ?></p>
          <p><strong><?php _e('Hit Submit to complete the installation.') ?></strong></p>
        </li>
      </ol>
    
      <div id="setup_buttons" class="submit">      
        <input id="setup_button_submit" class="disabled" type="submit" value="<?php _e('Submit', 'wpomatic') ?>" disabled="disabled" />      
        <input id="setup_button_previous" class="disabled" type="button" name="next" value="Previous" disabled="disabled" />
        <input id="setup_button_next" type="button" name="next" value="Next" /> <span id="current_indicator">1</span> / <?php echo ($safe_mode) ? 5 : 4 ?>
      </div>
    
    </div>
  </form>

<?php $this->adminFooter() ?>