<?php $title = __('Post Templates Examples', 'wpomatic') ?>
<h2><?php _e('Post Templates Examples', 'wpomatic') ?></h2>

<h3><?php _e('Linking to original source', 'wpomatic') ?></h3>
<p><?php _e('This example adds a link to the source after the content of each post.', 'wpomatic') ?></p>
<div class="code"><p>{content}<br />&lt;a href=&quot;{feedurl}&quot;&gt;Go to Source&lt;/a&gt;</p></div>

<h3><?php _e('Adding Google AdSense', 'wpomatic') ?></h3>
<p><?php _e('This example adds a Google Adsense ad after every post.', 'wpomatic') ?></p>
<div class="code"><p>{content}<br /> <?php echo htmlentities('<script type="text/javascript"><!--
google_ad_client = "pub-0752878588469406";
//Devthought Sidebar
google_ad_slot = "6547978492";
google_ad_width = 160;
google_ad_height = 600;
//--></script>
<script type="text/javascript"
src="http://pagead2.googlesyndication.com/pagead/show_ads.js">
</script>
') ?></p></div>

<h3><?php _e('Manipulating style', 'wpomatic') ?></h3>
<p><?php _e('If you want to manipulate the style of how WP-o-Matic posts are displayed, first add a tag that wraps the content.', 'wpomatic') ?></p>
<div class="code"><p>&lt;div class=&quot;wpomatic-post&quot;&gt;{content}&lt;/div&gt;</p></div>
<p><?php _e('And then add CSS for the wpomatic-post class:', 'wpomatic') ?></p>
<div class="code"><p>.wpomatic-post { color: red }</p></div>