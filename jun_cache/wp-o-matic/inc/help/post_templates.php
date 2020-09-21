<?php $title = __('Post Templates', 'wpomatic') ?>
<h2><?php _e('Post Templates', 'wpomatic') ?></h2>

<h3><?php _e('Basics', 'wpomatic') ?></h3>
<p><?php _e('WP-o-Matic takes the full text of each feed item it encounters, and then uses it as the post content. A post template, if used, allows you to alter that content, by adding extra information, such as text, images, campaign data, etc.', 'wpomatic') ?></p>

<h3><?php _e('Supported tags', 'wpomatic') ?></h3>
<a href="#" class="link_top">&uarr; <?php _e('top', 'wpomatic') ?></a>
<p><?php _e('A tag is a piece of text that gets replaced dynamically when the post is created. Currently, these tags are supported:', 'wpomatic') ?></p>

<ul>
  <li><strong>{content}</strong> <?php _e('The feed item content', 'wpomatic') ?></li>
  <li><strong>{title}</strong> <?php _e('The feed item title', 'wpomatic') ?></li>
  <li><strong>{permalink}</strong> <?php _e('The feed item permalink', 'wpomatic') ?></li>
  <li><strong>{feedurl}</strong> <?php _e('The feed URL', 'wpomatic') ?></li>
  <li><strong>{feedtitle}</strong> <?php _e('The feed title', 'wpomatic') ?></li>
  <li><strong>{feedlogo}</strong> <?php _e('The feed logo image', 'wpomatic') ?></li>
  <li><strong>{campaigntitle}</strong> <?php _e('This campaign title', 'wpomatic') ?></li>
  <li><strong>{campaignid}</strong> <?php _e('This campaign id', 'wpomatic') ?></li>
  <li><strong>{campaignslug}</strong> <?php _e('This campaign slug', 'wpomatic') ?></li>
</ul>

<h3><?php _e('Example', 'wpomatic') ?></h3>
<a href="#" class="link_top">&uarr; <?php _e('top', 'wpomatic') ?></a>
<p><a href="<?php echo $PHP_SELF ?>?item=post_templates_examples" class="link_main"><?php _e('Main article', 'wpomatic')  ?></a></p>
<p><?php _e('If you want to add a link to the source at the bottom of every post, the post template would look like this:') ?></p>
<div class="code"><p>{content}<br />&lt;a href=&quot;{feedurl}&quot;&gt;Go to Source&lt;/a&gt;</p></div>
<p><?php _e('The <em>{content}</em> string gets replaced by the feed content, and then {feedurl} by the url, which makes it a working link.') ?></p>
