<link rel="stylesheet" href="<?php echo $this->tplpath ?>/css/admin.css" type="text/css" media="all" title="" />
<?php if($this->newadmin): ?>
<link rel="stylesheet" href="<?php echo $this->tplpath ?>/css/admin-new.css" type="text/css" media="all" title="" />  
<?php endif ?>

<div id="wpomain">

  <div id="wpomenu" class="wrap">   
    <div> 
      <ul>
        <li <?php echo $current['home'] ?>><a id="menu_home" href="<?php echo $this->adminurl ?>&amp;s=home"><?php _e('仪表盘', 'wpomatic') ?></a></li>
        <li <?php echo $current['list'] ?>><a id="menu_list" href="<?php echo $this->adminurl ?>&amp;s=list"><?php _e('活动', 'wpomatic') ?></a></li>
        <li <?php echo $current['add'] ?>><a id="menu_add" href="<?php echo $this->adminurl ?>&amp;s=add"><?php _e('新增活动', 'wpomatic') ?></a></li>
        <li <?php echo $current['options'] ?>><a id="menu_options" href="<?php echo $this->adminurl ?>&amp;s=options"><?php _e('选项', 'wpomatic') ?></a></li>
        <li <?php echo $current['import'] ?>><a id="menu_backup" href="<?php echo $this->adminurl ?>&amp;s=import"><?php _e('导入', 'wpomatic') ?></a></li>
        <li <?php echo $current['export'] ?>><a id="menu_backup" href="<?php echo $this->adminurl ?>&amp;s=export"><?php _e('导出', 'wpomatic') ?></a></li>
      </ul>
    </div>     
  </div>  

  <div id="wpocontent">