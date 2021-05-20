---
layout: page
title: "Tag Cloud"
permalink: "/tags/"
description: "Browse website by tag based index"
comments: false
sitemap: false
category: base
hide_printmsg: true
---

{:.text-center}
## <i class="fa fa-paperclip" title="Featured"></i> <a href="{{ site.url }}/featured">Featured Posts</a>

<hr class="style17" style="margin:1.0rem 0;">

{% comment %}
  Tag generation is experimental and dynamic size for tag box may need to be adjusted if you have more than 100 posts with one or two frequently occurring tags. Also,all for loop operations will increase site build time.
{% endcomment %}

{% capture site_tags %}{% for tag in site.tags %}{{ tag | first }}{% unless forloop.last %},{% endunless %}{% endfor %}{% endcapture %}
{% assign tags_list = site_tags | split:',' | sort %}

<ul class="slidetags">
  {% for item in (0..site.tags.size) %}{% unless forloop.last %}
    {% capture this_word %}{{ tags_list[item] | strip_newlines }}{% endcapture %}
    <li style="font-size:{{ site.tags[this_word].size | times: 100 | divided_by: site.tags.size | plus: 70 }}%"><a href="#{{ this_word }}">{{ this_word }} <span>{{ site.tags[this_word].size }}</span></a></li>
  {% endunless %}{% endfor %}
</ul>

{% for item in (0..site.tags.size) %}{% unless forloop.last %}
  {% capture this_word %}{{ tags_list[item] | strip_newlines }}{% endcapture %}
<h2 id="{{ this_word }}">{{ this_word }}</h2>
<ul class="post-list">
  {% for post in site.tags[this_word] %}{% if post.title != null %}
  <li><a href="{{ site.url }}{{ post.url }}">{{ post.title }}<span class="entry-date"><time datetime="{{ post.date | date_to_xmlschema }}">{{ post.date | date: "%b %d, %Y" }}</time></span></a></li>
  {% endif %}{% endfor %}
  </ul>
{% endunless %}{% endfor %}

