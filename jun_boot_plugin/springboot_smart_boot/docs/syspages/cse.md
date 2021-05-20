---
layout: default_minimal
title: "Search"
description: "Search"
permalink: /cse/
sitemap: false
noindex: true
nofollow: true
category: base
---
{:.text-center}
You may also visit [tags]({{ site.url }}/tags) or [archive]({{ site.url }}/archive) page to browse website contents.

{:.text-center}
<a href="javascript:goBack()" class="social-icons" title="Return to previous page"><i class="fa fa-arrow-circle-left fa-2x"></i></a>

<script>
function goBack() {
    window.history.back();
}
</script>

{% if site.google_search %}
<hr class="gh">

<div id="searchbox2" align="center">
<div class="searchcont2">
    <!-- span class="searchicon2"><i class="fa fa-search fa-2x"></i></span -->
    <form role="search" method="get" action="{{ site.url }}/cse/">
        <input id="searchString2" name="searchString2"
               placeholder=" Search" type="text">
    </form>
</div>
</div>

<script>
  (function() {
    var cx = '{{ site.google_search }}';
    var gcse = document.createElement('script');
    gcse.type = 'text/javascript';
    gcse.async = true;
    gcse.src = (document.location.protocol == 'https:' ? 'https:' : 'http:') +
        '//cse.google.com/cse.js?cx=' + cx;
    var s = document.getElementsByTagName('script')[0];
    s.parentNode.insertBefore(gcse, s);
  })();
</script>
<gcse:searchresults-only queryParameterName="searchString2" enableAutoComplete="true" autoCompleteMatchType='any' autoCompleteMaxCompletions="5" autoCompleteMaxPromotions="1"></gcse:searchresults-only>
<!-- <gcse:searchbox-only resultsUrl="{{ site.url }}/cse/" queryParameterName="searchString"></gcse:searchbox-only> -->
{% else %}
This page will serve search results if Google Custom Search key is set in `_config.yml`
{% endif %}
