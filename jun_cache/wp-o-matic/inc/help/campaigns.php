<?php $title = __('Campaigns', 'wpomatic') ?>
<h2><?php _e('Campaigns', 'wpomatic') ?></h2>

<h3><?php _e('Basics', 'wpomatic') ?></h3>
<p><?php _e('WP-o-Matic is designed to provide enough flexibility to administrators to manage dozens of XML feeds easily. It\'d naturally be tedious to handle dozens of them, with so many options and parameters, or to keep track of ad revenue easily.', 'wpomatic') ?></p>
<p><?php _e('That\'s why the campaign concept has been introduced since the 1.0 version. In short, a <strong>campaign</strong> is a group of feeds you define with a shared configuration.</p>', 'wpomatic')?>

<h3><?php _e('Creating a campaign', 'wpomatic') ?></h3>
<a href="#" class="link_top">&uarr; <?php _e('top', 'wpomatic') ?></a>
<p><?php _e('Creating a campaign is as easy as clicking <strong>Add campaign</strong>, filling a campaign name, at least one feed URL and one category. Some more advanced options are described in the related help entries.', 'wpomatic') ?></p>
<p><?php _e('', 'wpomatic') ?>

<h3><?php _e('Campaign feeds', 'wpomatic') ?></h3>
<a href="#" class="link_top">&uarr; <?php _e('top', 'wpomatic') ?></a>
<p><?php _e('WP-o-Matic feed parsing is handled by the marvelous SimplePie library. Currently it supports the <a href="http://simplepie.org/wiki/faq/what_versions_of_rss_or_atom_do_you_support">following specifications</a>:') ?></p>
<ul>
  <li>RSS 0.90</li>
  <li>RSS 0.91 (Netscape)</li>
  <li>RSS 0.91 (Userland)</li>
  <li>RSS 0.92</li>
  <li>RSS 1.0</li>
  <li>RSS 2.0</li>
  <li>Atom 0.3</li>
  <li>Atom 1.0</li>
</ul>

<h3><?php _e('Campaign categories', 'wpomatic') ?></h3>
<a href="#" class="link_top">&uarr; <?php _e('top', 'wpomatic') ?></a>
<p><?php _e('Each campaign has one or more assigned categories. When WP-o-Matic processes a campaign, and gets each post from the feeds, it will add it to all the categories you selected. ', 'wpomatic') ?>
<p><?php _e('The <strong>Quick add</strong> link will let you quickly add new categories without leaving the WP-o-Matic section. Keep in mind, however, that they are all normal Wordpress categories, like the ones you create from the Manage categories section.', 'wpomatic')?>

<h3><?php _e('Campaign rewriting', 'wpomatic') ?></h3>
<a href="#" class="link_top">&uarr; <?php _e('top', 'wpomatic') ?></a>
<p><a href="<?php echo $PHP_SELF ?>?item=campaign_rewrite" class="link_main"><?php _e('Main article', 'wpomatic')  ?></a></p>
<p><?php _e('WP-o-Matic makes it possible to create your own set of rules and filters to customize the content of a post. You can make it change, for example, all the occurrences of the word <em>freeway</em> for the word <em>highway</em>.', 'wpomatic') ?>

<h3><?php _e('Campaign options', 'wpomatic') ?></h3>
<a href="#" class="link_top">&uarr; <?php _e('top', 'wpomatic') ?></a>
<p><?php _e('Under the <strong>options</strong> tab, you\'ll be able to set different options to change the behavior of WP-o-Matic. You can alter each post by defining your own <a href="'. $PHP_SELF . '?item=campaign_templates">'. __('post templates', 'wpomatic') . '</a>, select how often feeds should be pulled or turn on/off <a href="'. $PHP_SELF . '?item=image_caching">'. __('image caching', 'wpomatic') . '</a>.', 'wpomatic') ?></p>
<p><?php _e('You can also customize the Wordpress-specific options of each post. For example, you can tell it to use the <a href="'. $PHP_SELF . '?item=feed_date">'. __('date', 'wpomatic') . '</a> from the feed instead or the date when the post is inserted into the database. If you want to test a feed for a few days, WP-o-Matic lets you make the posts Private, Draft, or just Plubic.', 'wpomatic') ?>