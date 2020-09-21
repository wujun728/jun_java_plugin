<?php require_once(WPOTPL . '/helper/form.helper.php' ) ?>
<?php require_once(WPOTPL . '/helper/import.helper.php' ) ?>

<?php $this->adminHeader() ?>
      
  <div class="wrap">
    <h2><?php _e('导入', 'wpomatic') ?></h2>     
    
    <?php if(isset($import_error)): ?>
    <div id="import-warning" class="error">
      <?php if(isset($fromurl)): ?>
      <p><?php _e("所提供的URL不能被解析。请检查URL或尝试手动上传文件。", 'wpomatic') ?></p>
      <?php else: ?>
      <p><?php _e("所提供的文件不能被解析。确保它是一个有效的XML OPML文件，然后重试。", 'wpomatic') ?></p>
      <?php endif ?>
    </div>
    <?php endif; ?>        

    <?php if(isset($add_error)): ?>      
    <div id="import-warning" class="error">
      <p><?php echo $add_error ?></p>
    </div>
    <?php endif ?>

    <?php if(isset($this->add_success)): ?>
    <div id="import-warning" class="updated">
      <p><?php echo $this->add_success ?></p>
    </div>
    <?php endif ?>
                          
    <?php if(isset($success) && !isset($this->add_success)): ?>    
    <?php if(isset($title)): ?> 
      <?php if($title == 'WPOMATIC'): ?>
      <h3><?php _e('从采集器安装导入', 'wpomatic') ?></h3>
      <?php else: ?>
      <h3><?php echo $title ?></h3>
      <?php endif ?>
    <?php endif ?>
    
    <form action="" method="post">
      <?php wp_nonce_field('import-campaign') ?>
      <?php echo input_hidden_tag('add', 1) ?>
      
      <ul id="importtree">                
        <?php import_process_tree($opml); ?>  
      </ul>                  
    
      <div id="importoptions">
        <h4>导入选项</h4>
      
        <ul>
          <li><?php echo radiobutton_tag('import_mode', 1, !_data_value($_REQUEST, 'import_mode') || _data_value($_REQUEST, 'import_mode') == 1, 'id=import_mode_1') ?>
          <?php echo label_for('import_mode_1', 'Make a campaign per category') ?></li>
      
          <?php if(count($campaigns)): ?>
          <li>
            <?php echo radiobutton_tag('import_mode', 2, _data_value($_REQUEST, 'import_mode') == 2, 'id=import_mode_2') ?>
            <?php echo label_for('import_mode_2', 'Insert feeds into campaign:') ?>
            <?php echo select_tag('import_custom_campaign', options_for_select($campaigns, _data_value($_REQUEST, 'import_custom_campaign'))) ?>
          </li>
          <?php endif ?>

          <li>
            <?php echo radiobutton_tag('import_mode', 3, _data_value($_REQUEST, 'import_mode') == 3, 'id=import_mode_3') ?>
            <?php echo label_for('import_mode_3', 'Insert feeds in a new campaign called') ?>        
            <?php echo input_tag('import_new_campaign', attribute_escape(_data_value($_REQUEST, 'import_new_campaign'))) ?>
          </li>
        </ul>        
      </div>
    
      <p class="submit"><?php echo submit_tag('Process') ?></p>
    </form>
    
    <?php else: ?>             
    <div id="import_desc">
      <p><?php _e('采集器使您可以导入 <strong>OPML</strong> 文件.  <a href="http://en.wikipedia.org/wiki/OPML">OPML</a>OPML格式提供了一个简单的方法来交换来源的名单。', 'wpomatic') ?> <a href="<?php echo $this->helpurl ?>import" class="help_link"><?php _e('帮助', 'wpomatic') ?></a></p>
    </div>       
                          
    <form action="" class="import_submit" method="post" enctype="multipart/form-data">
      <?php wp_nonce_field('import-campaign') ?>
      <input type="file" class="input_text" name="importfile" />
      <input type="submit" class="button submit" value="上传 &rarr;"> 
    </form>  
    
    <form action="" class="import_submit" method="post" enctype="multipart/form-data">
      <?php wp_nonce_field('import-campaign') ?>
      <input type="text" class="input_text" value="<?php form_text_value('importurl', 'http://') ?>" name="importurl" id="importurl" />
      <input type="submit" class="button submit" value="上传 &rarr;" /> 
    </form>  
    <?php endif; ?>
    
  </div>
  
<?php $this->adminFooter() ?>