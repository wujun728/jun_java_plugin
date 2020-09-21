<?php echo '<?xml version="1.0"?>'; ?>
<opml version="1.1">
  <head>
    <title>WPOMATIC</title>
    <dateCreated><?php echo date('D, d M Y h:i:s e') ?></dateCreated>
  </head>
  <body>
    <?php foreach($campaigns as $campaign): ?>
    <outline text="<?php echo $campaign->title ?>">
      <?php foreach($campaign->feeds as $feed): ?>
      <outline text="<?php echo $feed->title ?>" type="link" xmlURL="<?php echo $feed->url ?>" htmlUrl="" />
      <?php endforeach ?>
    </outline>
    <?php endforeach ?>
  </body>
</opml>
