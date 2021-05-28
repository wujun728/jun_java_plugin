---
layout: page
permalink: /cv/
title: Curriculum Vitae
category: base
published: true
description: "Curriculum Vitae / Resume"
tags:
  - cv
  - resume
  - "foo boo"
comments: false
imagesummary: foo.png
modified: "2016-02-13"
sitemap:
    priority: 0.7
    changefreq: 'monthly'
    lastmod: 2016-02-13
style: |
  .container {
        max-width: 48rem;
    } 
---

<span class="social-icons">
<a href="https://twitter.com/share?text=Curriculum Vitae - {{ site.owner.name }}&amp;url={{ site.url }}/cv&amp;via={{ site.owner.twitter }}"  class="social-icons" target="_blank" title="Share on twitter"> <i class="fa fa-twitter meta"></i></a>
<a href="https://plus.google.com/share?url={{ site.url }}/cv"  class="social-icons" target="_blank" title="Share on Google+"> <i class="fa fa-google-plus"></i></a>
<a href="{{ site.owner.linkedin }}" class="social-icons" title="LinkedIn profile"><i class="fa fa-linkedin"></i></a>
<a href="{{ site.url }}/files/cv.pdf" class="social-icons" title="Printer friendly format"><i class="fa fa-print"></i></a>
</span>

{:.text-center}
[Publications]({{ site.url }}/about/publications/) \| [ORCID profile](https://orcid.org/{{ site.owner.orcid }}) \| [Google Scholar profile](https://scholar.google.com/citations?user={{ site.owner.gscholar }}&hl=en) 

<!-- Alternaetly, user html5 embed tag -->
<iframe src="{{ site.url }}/files/cv.pdf" width="100%" style="height: 100vh;"></iframe>
