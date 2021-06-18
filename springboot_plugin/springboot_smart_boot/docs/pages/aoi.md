---
layout: publ
category: myaoi
permalink: /about/aoi/
title: "Articles of interest"
published: true
description: "Sample page showing bookmarked papers"
tags:
  - papers
  - articles
  - research
  - starred
comments: true
modified: "2016-02-13"
bibtex: "/files/myaoi.bib"
#bibtex: "http://foo-alternate.com/files/myaoi.bib"
show_meta: true
noindex: false
nofollow: true
sitemap:
    priority: 0.5
    changefreq: 'monthly'
    lastmod: 2016-02-13
style: |
  .container {
        max-width: 48rem;
    } 
---

{% comment %}
<!-- bibbase.org should work with following code unless you are hosting domain over https. --> 

{% if page.bibtex %}
 {% if page.bibtex contains 'http' %}
  {% assign domain = '' %}
  {% else %}
  {% assign domain = site.url %}
 {% endif %}
 {% capture biburl %}{{ domain }}{{ page.bibtex }}{% endcapture %}
<script src="http://bibbase.org/show?bib={{ biburl | cgi_escape }}&amp;jsonp=1&amp;authorFirst=1"></script>
{% endif %}

{% endcomment %}

If category is *myaoi*, then html content from bibtex file at `_includes/myaoi.html` will be shown below.

