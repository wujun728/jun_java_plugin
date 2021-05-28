---
layout: page
title: 归档
description: "Archive"
permalink: /archive/
category: base
hide_printmsg: true
---

<section id="archive">
  <h3>今年发布</h3>
  {%for post in site.posts %}
    {% unless post.next %}
      <ul class="post-list">
    {% else %}
      {% capture year %}{{ post.date | date: '%Y' }}{% endcapture %}
      {% capture nyear %}{{ post.next.date | date: '%Y' }}{% endcapture %}
      {% if year != nyear %}
        </ul>
        <h3>{{ post.date | date: '%Y' }}</h3>
        <ul class="post-list">
      {% endif %}
    {% endunless %}
      <li><a href="{{ site.url }}{{ post.url }}">{{ post.title }}<span class="entry-date"><time datetime="{{ post.date | date_to_xmlschema }}">{{ post.date | date: "%b %d, %Y" }}</time></span></a></li>
  {% endfor %}
  </ul>
</section>
