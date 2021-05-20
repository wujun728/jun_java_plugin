---
layout: default
permalink: index.html
title: Smart Boot
imagearch: ./images/arch.png
hide_printmsg: true
description: "Blogging on ...."
---

## [smart boot](http://git.oschina.net/smartboot/smart-boot)

对`spring boot`进行轻度封装的一个开源项目，更确切的说应该是一个项目脚手架。`smart boot`在集成`spring boot`的同时，约定了一个项目的模块化结构。

#### smart boot适合哪些人
* 个人，对spring boot、微服务架构有浓厚兴趣的朋友
* 创业团队，创业团队正处于从0到1的过程，可以考虑`smart boot`，因为该框架正式从创业团队中历练出来的
* 大公司，可以考虑选用。这样的团队基本有一个成熟的业务架构，所以仅在进行重构或者新业务搭建时推荐使用`smart boot`

#### 为什么开源smart boot
- `smart boot`是作者从事架构工作以来第一份任务的产物，因此也显得格外有意义，非常希望能够分享给同行朋友。
- 作为一款新生的作品，`smart boot`迫切需要去经历更多实际业务检验才能得以成长。但是，作者并不希望因此导致`smart boot`变得臃肿、复杂，保持简单就好。
- 回馈开源社区，步入这一行以来享受了太多开源项目带来的便利，目前也希望能够给予他人一些帮助

更详细的内容参见《[使用手册]({{site.url}}/reference/)》

---

<div class="posts">
  {% for post in site.categories.featured limit:2 %}
  <div class="post">
    <h1 class="post-title">
      <a href="{{ site.url }}{{ post.url }}">
        {{ post.title }}
      </a>
    </h1>

  {% if post.modified.size > 2 %}<span class="post-date indexpg" itemprop="dateModified" content="{{ post.modified | date: "%Y-%m-%d" }}"><i class="fa fa-edit" title="Last updated"> {{ post.modified | date_to_string }}</i> <a href="{{ site.url }}/featured" title="Featured posts"><i class="fa fa-paperclip" title="Featured" class="social-icons"></i></a></span>{% else %}<span class="post-date indexpg" itemprop="datePublished" content="{{ post.date | date: "%Y-%m-%d" }}"><i class="fa fa-calendar" title="Date published"> {{ post.date | date_to_string }}</i> <a href="{{ site.url }}/featured" title="Featured posts"><i class="fa fa-paperclip" title="Featured" class="social-icons"></i></a></span>{% endif %}

 {% if post.description.size > 140 %}{{ post.description | markdownify | remove: '<p>' | remove: '</p>' }}{% else %}{{ post.excerpt | markdownify | remove: '<p>' | remove: '</p>' }}{% endif %} <a href="{{ site.url }}{{ post.url }}" title="Read more"><strong>Read more...</strong></a>
  </div>
  <hr class="transp">
  {% endfor %}
</div>

<div class="posts">
  {% for post in site.posts limit:2 %}
  {% unless post.category contains "featured" %}
  <div class="post">
    <h1 class="post-title">
      <a href="{{ site.url }}{{ post.url }}">
        {{ post.title }}
      </a>
    </h1>

  {% if post.modified.size > 2 %}<span class="post-date indexpg" itemprop="dateModified" content="{{ post.modified | date: "%Y-%m-%d" }}"><i class="fa fa-edit" title="Last updated"> {{ post.modified | date_to_string }}</i></span>{% else %}<span class="post-date indexpg" itemprop="datePublished" content="{{ post.date | date: "%Y-%m-%d" }}"><i class="fa fa-calendar" title="Date published"> {{ post.date | date_to_string }}</i></span>{% endif %}

 {% if post.description.size > 140 %}{{ post.description | markdownify | remove: '<p>' | remove: '</p>' }}{% else %}{{ post.excerpt | markdownify | remove: '<p>' | remove: '</p>' }}{% endif %} <a href="{{ site.url }}{{ post.url }}" title="Read more"><strong>Read more...</strong></a>
  </div>
  {% unless forloop.last %}<hr class="transp">{% endunless %}
  {% endunless %}
  {% endfor %}
</div>
<h3 class="post-title">
<div class="pagination" style="margin: 0.5rem;">
    <a class="pagination-item older" href="{{ site.url }}/blog"><i class="fa fa-edit"> Blog</i></a>
    <a class="pagination-item newer" href="{{ site.url }}/tags"><i class="fa fa-tags"> Tags</i></a>
</div>
</h3>
