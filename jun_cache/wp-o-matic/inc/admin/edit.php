<?php require_once(WPOTPL . '/helper/form.helper.php' ) ?>
<?php require_once(WPOTPL . '/helper/edit.helper.php' ) ?>
<?php $this->adminHeader() ?>
  
  <div class="wrap">  
    <?php if(isset($campaign_add)): ?>
    <h2>新增活动</h2>
    <?php else: ?>
    <h2>修改活动</h2>
    <?php endif;?>
    
    <?php if(isset($this->errno) && $this->errno): ?>
    <div id="edit-warning" class="error">
      <p><strong><?php _e('The following errors have been encountered:', 'wpomatic') ?></strong></p>
      <ul>
        <?php foreach($this->errors as $section => $errs): ?>
          <?php if($errs): ?>
          <li>
            <?php echo ucfirst($section) ?>
            <ul class="errors">
              <?php foreach($errs as $error): ?>
              <li><?php echo $error ?></li>
              <?php endforeach ?>
            </ul>
          </li>
          <?php endif?>
        <?php endforeach; ?>
      </ul>
    </div>
    <?php else: ?>
      <?php if(isset($addedid)): ?>
      <div id="added-warning" class="updated"><p><?php printf(__('活动添加成功. 你可以 <a href="%s">修改</a> 或者 <a href="%s">现在把它拿来</a>', 'wpomatic'), $this->adminurl . '&s=edit&id=' . $addedid, wp_nonce_url($this->adminurl . '&amp;s=forcefetch&amp;id=' . $addedid, 'forcefetch-campaign_' . $addedid)) ?></p></div>
      <?php elseif(isset($this->tool_success)): ?>
      <div id="added-warning" class="updated"><p><?php echo $this->tool_success ?></p></div>
      <?php elseif(isset($edited)): ?>    
      <div id="added-warning" class="updated"><p><?php _e('活动修改成功.', 'wpomatic') ?></p></div>
      <?php endif ?>  
    <?php endif; ?>
                  
    <form id="edit_campaign" action="" method="post" accept-charset="utf-8">
      <?php wp_nonce_field('wpomatic-edit-campaign') ?>

      <?php if(isset($campaign_add)): ?>
        <?php echo input_hidden_tag('campaign_add', 1) ?>
      <?php else: ?>
        <?php echo input_hidden_tag('campaign_edit', $id) ?>
      <?php endif; ?>
                              
      <ul id="edit_buttons" class="submit">                       
        <li><a href="<?php echo $this->helpurl ?>campaigns" class="help_link"><?php _e('帮助', 'wpomatic') ?></a></li>                                                                             
        <li><input type="submit" name="edit_submit" value="提交" id="edit_submit" /></li>
      </ul>
            
      <ul id="edit_tabs">
        <li class="current"><a href="#" id="tab_basic"><?php _e('基础', 'wpomatic') ?></a></li>
        <li><a href="#" id="tab_feeds"><?php _e('来源', 'wpomatic') ?></a></li>
        <li><a href="#" id="tab_categories"><?php _e('分类目录', 'wpomatic') ?></a></li>
        <li><a href="#" id="tab_rewrite"><?php _e('改写', 'wpomatic') ?></a></li>
        <li><a href="#" id="tab_options"><?php _e('选项', 'wpomatic') ?></a></li>   
        <?php if(isset($campaign_edit)): ?>        
        <li><a href="#" id="tab_tools"><?php _e('工具', 'wpomatic') ?></a></li>
        <?php endif ?>
      </ul>                 
      
      <div id="edit_sections">                          
        <!-- Basic section -->
        <div class="section current" id="section_basic">
          <div class="longtext required">
            <?php echo label_for('campaign_title', __('标题', 'wpomatic')) ?>
            <?php echo input_tag('campaign_title', _data_value($data['main'], 'title')) ?>
            <p class="note"><?php _e('提示：选择一个名称，一般是所有活动的来源（如：帕里斯·希尔顿）', 'wpomatic' ) ?></p>
          </div>
            
          <div class="checkbox required">
            <?php echo label_for('campaign_active', __('是不是文章', 'wpomatic')) ?>
            <?php echo checkbox_tag('campaign_active', 1, _data_value($data['main'], 'active', true)) ?>
            <p class="note"><?php _e('如果无效，解析器将忽略这些来源', 'wpomatic' ) ?></p>
          </div> 
          
          <div class="text">
            <?php echo label_for('campaign_slug', __('活动块', 'wpomatic')) ?>
            <?php echo input_tag('campaign_slug', _data_value($data['main'], 'slug')) ?>
            <p class="note"><?php _e('或者，你可以设置一个标识符为这次活动。有用的详细跟踪您的分成只能看谷歌的脸色。', 'wpomatic' ) ?></p>
          </div>
        </div>
        
        <!-- Feeds section -->
        <div class="section" id="section_feeds">
          <p><?php _e('请填写至少有一个。如果你不能确定确切的来源网址，只需输入域名，将自动识别来源', 'wpomatic') ?></p>
          
          <div id="edit_feed">
            <?php if(isset($data['feeds']['edit'])): ?>
              <?php foreach($data['feeds']['edit'] as $id => $feed): ?>
              <div class="inlinetext required">
                <?php echo label_for('campaign_feed_edit_' . $id, __('来源 URL', 'wpomatic')) ?>
                <?php echo input_tag('campaign_feed[edit]['. $id .']', $feed, 'disabled=disabled class=input_text id=campaign_feed_edit_' . $id) ?>       
                <?php echo checkbox_tag('campaign_feed[delete]['.$id.']', 1, (isset($data['feeds']['delete']) && _data_value($data['feeds']['delete'], $id)), 'id=campaign_feed_delete_' . $id) ?> <label for="campaign_feed_delete_<?php echo $id ?>" class="delete_label">Delete ?</label>
              </div>
              <?php endforeach ?>
            <?php endif ?>
            
            <?php if(isset($data['feeds']['new'])): ?>                  
              <?php foreach($data['feeds']['new'] as $i => $feed): ?>
              <div class="inlinetext required">
                <?php echo label_for('campaign_feed_new_' . $i, __('来源 URL', 'wpomatic')) ?>
                <?php echo input_tag('campaign_feed[new]['.$i.']', $feed, 'class=input_text id=campaign_feed_new_' . $i) ?>
              </div>                           
              <?php endforeach ?>
            <?php else: ?>
              <?php for($i = 0; $i < 4; $i++): ?>
              <div class="inlinetext required">
                <?php echo label_for('campaign_feed_new_' . $i, __('来源 URL', 'wpomatic')) ?>
                <?php echo input_tag('campaign_feed[new][]', null, 'class=input_text id=campaign_feed_new_' . $i) ?>
              </div>                           
              <?php endfor ?>
            <?php endif ?>
          </div>
          
          <a href="#add_feed" id="add_feed"><?php _e('添加一个', 'wpomatic') ?></a> | <a href="#" id="test_feeds"><?php _e('全选', 'wpomatic') ?></a>
        </div>
         
        <!-- Categories section -->
        <div class="section" id="section_categories"> 
          <p><?php _e('这些类别的目录将被创建后，他们从来源中获取。', 'wpomatic') ?></p><p><?php _e('你必须至少选择一个。', 'wpomatic') ?></p>
           
          <ul id="categories">
            <?php $this->adminEditCategories($data) ?>
            
            <?php if(isset($data['categories']['new'])): ?>
              <?php foreach($data['categories']['new'] as $i => $catname): ?>
              <li>
                <?php echo checkbox_tag('campaign_newcat[]', 1, true, 'id=campaign_newcat_' . $i) ?>
                <?php echo input_tag('campaign_newcatname[]', $catname, 'class=input_text id=campaign_newcatname_' . $i) ?>
              </li>
              <?php endforeach ?>
            <?php endif ?>
          </ul>          
          
          <a href="#quick_add" id="quick_add"><?php _e('快速添加', 'wpomatic') ?></a>
        </div>
          
        <!-- Rewrite section -->
        <div class="section" id="section_rewrite">
          <p><?php _e('要转换成另一个字吗？或连结到一些网站的特定字吗？', 'wpomatic') ?> <?php printf(__('<a href="%s" class="help_link">更多</a>', 'wpomatic'), $this->helpurl . 'campaign_rewrite') ?></p>  
          
          <ul id="edit_words">
            <?php if(isset($data['rewrites']) && count($data['rewrites'])): ?>   
              <?php foreach($data['rewrites'] as $i => $rewrite): ?>
                <li class="word">
                  <div class="origin textarea">
                    <?php echo label_for('campaign_word_origin_' . $i, __('原点', 'wpomatic')) ?>
                    <?php echo textarea_tag('campaign_word_origin['.$i . ']', $rewrite['origin']['search'], 'id=campaign_word_origin_' . $rewrite->id) ?>
                    <label class="regex">
                      <?php echo checkbox_tag('campaign_word_option_regex['. $i .']', 1, $rewrite['origin']['regex']) ?>
                      <span><?php _e('正则表达式', 'wpomatic') ?></span>
                    </label>
                  </div>               
                  
                  <div class="rewrite textarea">
                    <label>
                      <?php echo checkbox_tag('campaign_word_option_rewrite['. $i .']', 1, isset($rewrite['rewrite'])) ?>
                      <span><?php _e('改写成:', 'wpomatic') ?></span>
                    </label>
                    <?php echo textarea_tag('campaign_word_rewrite['. $i .']', _data_value($rewrite, 'rewrite')) ?>
                  </div>            
                  
                  <div class="relink textarea">
                    <label>
                      <?php echo checkbox_tag('campaign_word_option_relink['. $i .']', 1, isset($rewrite['relink'])) ?>
                      <span><?php _e('重新链接到:', 'wpomatic') ?></span>
                    </label>
                    <?php echo textarea_tag('campaign_word_relink['. $i .']', _data_value($rewrite, 'relink')) ?>
                  </div>           
                </li>
              <?php endforeach ?>
            <?php else: ?>
            <li class="word">
              <div class="origin textarea">
                <label for="campaign_word_origin_new1"><?php _e('原点', 'wpomatic') ?></label>
                <textarea name="campaign_word_origin[new1]" id="campaign_word_origin_new1"></textarea> 
                <label class="regex"><input type="checkbox" name="campaign_word_option_regex[new1]" /> <span><?php _e('正则表达式', 'wpomatic') ?></span></label>
              </div>               
              <div class="rewrite textarea">
                <label><input type="checkbox" value="1" name="campaign_word_option_rewrite[new1]" /> <span><?php _e('改写成:', 'wpomatic') ?></span></label>
                <textarea name="campaign_word_rewrite[new1]"></textarea>
              </div>            
              <div class="relink textarea">
                <label><input type="checkbox" value="1" name="campaign_word_option_relink[new1]" /> <span><?php _e('重新链接到:', 'wpomatic') ?></span></label>
                <textarea name="campaign_word_relink[new1]"></textarea>
              </div>           
            </li>
            <?php endif ?>
          </ul>     
          
          <a href="#add_word" id="add_word"><?php _e('添加更多的', 'wpomatic') ?></a>
        </div>
                                           
        <!-- Options -->
        <div class="section" id="section_options">
          <?php if(isset($campaign_edit)): ?>
          <div class="section_warn">
            <img src="<?php echo $this->tplpath ?>/images/icon_alert.gif" alt="<?php _e('警告', 'wpomatic') ?>" class="icon" />
            <h3><?php _e('请记住，', 'wpomatic') ?></h3>
            <p><?php _e('更改这些选项仅影响后，下一次来源内容解析。', 'wpomatic') ?></p>
            <p><?php _e('如果您需要修改现有的规则，你可以通过使用选项“工具”选项卡修改', 'wpomatic') ?></p>
          </div>
          <?php endif ?>
          
          <div class="checkbox">
            <label for="campaign_templatechk"><?php _e('自订文章范本', 'wpomatic') ?></label> 
            <?php echo checkbox_tag('campaign_templatechk', 1, _data_value($data['main'], 'template')) ?>
            
            <div id="post_template" class="textarea <?php if(_data_value($data['main'], 'template', '{content}') !== '{content}') echo 'current' ?>">
              <?php echo textarea_tag('campaign_template', _data_value($data['main'], 'template', '{content}')) ?>
              <a href="#" id="enlarge_link"><?php _e('缩放', 'wpomatic') ?></a>
              
              <p class="note" id="tags_note">
                <?php _e('有效的标签:', 'wpomatic') ?>
              </p>
              <p id="tags_list">
                <span class="tag">{content}</span>, <span class="tag">{title}</span>, <span class="tag">{permalink}</span>, <span class="tag">{feedurl}</span>, <span class="tag">{feedtitle}</span>, <span class="tag">{feedlogo}</span>,<br /> <span class="tag">{campaigntitle}</span>, <span class="tag">{campaignid}</span>, <span class="tag">{campaignslug}</span>
              </p>
            </div>               
            
            <p class="note"><?php printf(__('阅读关于 <a href="%s" class="help_link">发布模板</a>，或检查一些 <a href="%s" class="help_link">例子</a>', 'wpomatic'), $this->helpurl . 'post_templates', $this->helpurl . 'post_templates_examples') ?></p>            
          </div>
          
          <div class="multipletext">
            <?php 
              $f = _data_value($data['main'], 'frequency');

              if($f) {
                $frequency = WPOTools::calcTime($f);                
              }                
              else
                $frequency = array();
            ?>
            
            <label><?php _e('频率', 'wpomatic') ?></label>                                      

            <?php echo input_tag('campaign_frequency_d', _data_value($frequency, 'days', 1), 'size=2 maxlength=3')?>
            <?php _e('d', 'wpomatic') ?> 
            
            <?php echo input_tag('campaign_frequency_h', _data_value($frequency, 'hours', 5), 'size=2 maxlength=2')?>            
            <?php _e('h', 'wpomatic') ?> 
             
            <?php echo input_tag('campaign_frequency_m', _data_value($frequency, 'minutes', 0), 'size=2 maxlength=2')?>            
            <?php _e('m', 'wpomatic') ?> 
                
            <p class="note"><?php _e('应该多久对来源进行检查？ （天，小时和分钟）', 'wpomatic') ?></p>
          </div>       
  
          <div class="checkbox">
            <?php echo label_for('campaign_cacheimages', __('缓存图像', 'wpomatic')) ?>
            <?php echo checkbox_tag('campaign_cacheimages', 1, _data_value($data['main'], 'cacheimages', is_writable($this->cachepath))) ?>            
            <p class="note"><?php _e('图像将被保存在您的服务器，而不是从原始网站的盗链。', 'wpomatic') ?> <a href="<?php echo $this->helpurl ?>image_caching" class="help_link"><?php _e('更多', 'wpomatic') ?></a></p>
          </div>   
                                             
          <div class="checkbox">
            <?php echo label_for('campaign_feeddate', __('使用来源日期', 'wpomatic')) ?>
            <?php echo checkbox_tag('campaign_feeddate', 1, _data_value($data['main'], 'feeddate', false)) ?>
            <p class="note"><?php _e('取消后由采集器创建时间，而不是使用后从原来的日期。', 'wpomatic') ?> <a href="<?php echo $this->helpurl ?>feed_date_option" class="help_link"><?php _e('更多', 'wpomatic') ?></a></p>
          </div>    
          
          <div class="checkbox">
            <?php echo label_for('campaign_dopingbacks', __('执行包括引用', 'wpomatic')) ?>
            <?php echo checkbox_tag('campaign_dopingbacks', 1, _data_value($data['main'], 'dopingbacks', false)) ?>
          </div>         
          
          <div class="radio">
            <label class="main"><?php _e('采集后后创建的类型', 'wpomatic')?></label>
              
            <?php echo radiobutton_tag('campaign_posttype', 'publish', !isset($data['main']['posttype']) || _data_value($data['main'], 'posttype') == 'publish', 'id=type_published') ?>
            <?php echo label_for('type_published', __('发布', 'wpomatic')) ?>
            
            <?php echo radiobutton_tag('campaign_posttype', 'private', _data_value($data['main'], 'posttype') == 'private', 'id=type_private') ?>
            <?php echo label_for('type_private', __('私人', 'wpomatic')) ?>
            
            <?php echo radiobutton_tag('campaign_posttype', 'draft', _data_value($data['main'], 'posttype') == 'draft', 'id=type_draft') ?>
            <?php echo label_for('type_draft', __('草稿', 'wpomatic')) ?>
          </div>
          
          <div class="text">
            <?php echo label_for('campaign_author', __('作者:', 'wpomatic')) ?>
            <?php echo select_tag('campaign_author', options_for_select($author_usernames, _data_value($data['main'], 'author', 'admin'))) ?>
            <p class="note"><?php _e("将被分配到该作者的目录。", 'wpomatic') ?></p>
          </div>
          
          <div class="text required">
            <?php echo label_for('campaign_max', __('对每个创建获取的最大项目', 'wpomatic')) ?>
            <?php echo input_tag('campaign_max', _data_value($data['main'], 'max', '10'), 'size=2 maxlength=3') ?>
            <p class="note"><?php _e("它设置为0无限。如果设置的值，只有最后一个X项目将被选中，忽略了旧的。", 'wpomatic') ?></p>
          </div>
          
          <div class="checkbox">            
            <?php echo label_for('campaign_linktosource', __('文章标题链接到源？', 'wpomatic')) ?>
            <?php echo checkbox_tag('campaign_linktosource', 1, _data_value($data['main'], 'linktosource', false)) ?>
          </div>
          
          <div class="radio">
            <label class="main"><?php _e('讨论的选项：', 'wpomatic')?></label>
            
            <?php echo select_tag('campaign_commentstatus', 
                        options_for_select(
                          array('open' => __('打开', 'wpomatic'), 
                                'closed' => __('关闭', 'wpomatic'), 
                                'registered_only' => __('只登记', 'registered_only')
                                ), _data_value($data['main'], 'comment_status', 'open'))) ?>
            
            <?php echo checkbox_tag('campaign_allowpings', 1, _data_value($data['main'], 'allowpings', true)) ?>
            <?php echo label_for('campaign_allowpings', __('允许评', 'wpomatic')) ?>
          </div>
        </div>   
              
        <?php if(isset($campaign_edit)): ?>              
        <!-- Tools -->
        <div class="section" id="section_tools">    
          <div class="buttons">
            <h3><?php _e('文章动作', 'wpomatic') ?></h3>
            <p class="note"><?php _e("选定的操作适用于这项活动的所有栏目", 'wpomatic') ?></p>
            
            <ul>
              <li>
                <div class="btn">
                  <input type="submit" name="tool_removeall" value="<?php _e('全部删除', 'wpomatic') ?>" />
                </div>
              </li>
              <li>
                <div class="radio">
                  <label class="main"><?php _e('改变状态到:', 'wpomatic')?></label>

                  <input type="radio" name="campaign_tool_changetype" value="publish" id="changetype_published" checked="checked" /> <label for="changetype_published"><?php _e('发布', 'wpomatic') ?></label>
                  <input type="radio" name="campaign_tool_changetype" value="private" id="changetype_private" /> <label for="changetype_private"><?php _e('私人', 'wpomatic') ?></label>
                  <input type="radio" name="campaign_tool_changetype" value="draft" id="changetype_draft" /> <label for="changetype_draft"><?php _e('草稿', 'wpomatic') ?></label>
                  <input type="submit" name="tool_changetype" value="<?php _e('改变', 'wpomatic') ?>" />
                </div>
              </li>
              <li>
                <div class="text">
                  <label for="campaign_tool_changeauthor"><?php _e('作者用户名更改为:', 'wpomatic')?></label>
                  <?php echo select_tag('campaign_tool_changeauthor', options_for_select($author_usernames, _data_value($data['main'], 'author', 'admin'))) ?>
                  
                  <input type="submit" name="tool_changeauthor" value="<?php _e('改变', 'wpomatic') ?>" />
                </div>
              </li>
            </ul>
          </div>    
          
          <!-- 
          <div class="btn">   
            <label><?php _e('Test all feeds', 'wpomatic') ?></label>
            <input type="button" name="campaign_tool_testall_btn" value="Test" />
            <p class="note"><?php _e('This option creates one draft from each feed you added.', 'wpomatic') ?></p>
          </div>
          -->
        </div>
        <?php endif ?>
      </div>  
    </form>          
  </div>
  
<?php $this->adminFooter() ?>