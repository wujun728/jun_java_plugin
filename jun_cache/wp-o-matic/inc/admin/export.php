<?php require_once(WPOTPL . '/helper/form.helper.php' ) ?>

<?php $this->adminHeader() ?>

  <?php if(isset($error)): ?>
  <div id="export-warning" class="error">
    <p><?php echo $error ?></p>
  </div>
  <?php endif; ?>

  <div class="wrap">
    <h2><?php _e('导出', 'wpomatic') ?></h2>
    <p><?php _e('<strong>注意:</strong> 此工具仅出口来源的名单，而不是自定义选项。', 'wpomatic') ?></p>
    
    <form action="" method="post" accept-charset="utf-8">
      <?php if($campaigns): ?>
      <ul id="export_campaigns">
        <?php foreach($campaigns as $campaign): ?>
        <li>
          <?php echo checkbox_tag('export_campaign[]', $campaign->id, isset($_REQUEST['export_campaign']) && _data_value($_REQUEST['export_campaign'], $campaign->id), 'id=export_campaign_' . $campaign->id) ?>
          <?php echo label_for('export_campaign_' . $campaign->id, $campaign->title . ' <strong>' . $campaign->id . '</strong>') ?>
        </li>
        <?php endforeach ?>
      </ul>
    
      <p class="submit"><?php echo submit_tag(__('Export', 'wpomatic')) ?></p>    
      <?php else: ?>
      <p><?php _e('没有显示的活动', 'wpomatic') ?>
      <?php endif ?>
    </form>
  </div>

<?php $this->adminFooter() ?>