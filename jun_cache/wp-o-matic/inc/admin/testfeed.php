<?php if($works): ?>
<?php printf(__('The feed %s has been parsed successfully.', 'wpomatic'), $url) ?>
<?php else: ?>
<?php printf(__('The feed %s cannot be parsed. Simplepie said: %s', 'wpomatic'), $url, $works) ?>
<?php endif ?>