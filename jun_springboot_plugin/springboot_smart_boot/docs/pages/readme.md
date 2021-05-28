---
layout: page
permalink: /readme/
title: "README - How to set up lanyon-plus jekyll theme"
description: "README for setting up lanyon-plus jekyll theme on github pages or custom domain having static website"
---
{% raw %}
## lanyon-plus

### Based on Jekyll theme: [Lanyon](http://lanyon.getpoole.com) by [Mark Otto](https://github.com/mdo)

*   add-ons by [Samir B. Amin](https://sbamin.com)
*   License: Open sourced under the [MIT license](LICENSE.md). 

### Required edits:

#### _config.yml

*   Edit lines where text string `foo` is present with relevant information. 
*   Add relevant author and owner information
    *   For proper sidebar, meta info below post title, and footer bar, add at least twitter, google plus info under `owner` and `sidebar` section.
    *   Uncomment and add relevant user names/keys to enable features, e.g., google analytics, disqus comments, twitter widget, google custom search.

#### CNAME

*   Read [Using a custom domain with GitHub Pages](https://help.github.com/articles/using-a-custom-domain-with-github-pages/) for set-up details.
*   If you are hosting website on domain other than `github.io`, rename `CNAME.sample` file to `CNAME`, and add your custom domain name, e.g., `example.com` (only one domain is allowed), otherwise remove `CNAME` file if you want to host at default `github.io`. 
*   If you are hosting website on `github.io`, replace `example.com` with `https://<github-username>.github.io/<repository_name>` (for project site) or `https://<github-username>.github.io` (for user site) under `site.url` and `site.urlimg` in `_config.yml` and `_prose.yml` file.

#### .travis.yml
*   See more at [https://travis-ci.org/getting_started](https://travis-ci.org/getting_started)

#### _prose.yml

*   [https://github.com/prose/prose/wiki/Getting-Started](https://github.com/prose/prose/wiki/Getting-Started)
*   Edit `example.com` with your domain name.
*   You may edit names for custom categories.

#### robots.txt

* replace `example.com` with your valid url.
* Edit search engine inclusion/exclusion if desired.

#### page specific edits

*   `_data/socialmedia.html`
    *   Replace user `foo` with appropriate username

*   `_includes/`
    *   Check if file paths for appropriate urls have valid css files, scripts, icons, and images in `head.html` and `head_minimal.html`, else comment html tags which are not being used.
    *   Also, check if variables (twitter, google plus, linkedin, google analytics key and disqus username, etc.) are specified in `_config.yml` located under root path.
    *   You may edit `meta_info.html`, `footer.html` and similar include files to add/remove elements in page meta bar, footer, etc.
    *   For publications page, `mypubs.html` and `myaoi.html` are trimmed outputs from [zot_bib_web](https://github.com/davidswelt/zot_bib_web). Github pages can not dynamically build these pages. Alternately, you may export `bib` format for publications under `/files/` directory which can be parsed dynamically using [bibbase.org](http://bibbase.org)
    *   `_includes/footer.html`: Edit copyright information as needed.
*   `_layouts`
    *   To add/remove/reorder page/post contents, edit `default.html` plus `page.html` or `post.html`.
*   `_posts`
    *   Live blog posts goes here with markdown formatted post. File name format must have following date-title format `yyyy-mm-dd-title.md` for jekyll to render blog post correctly. 
    *   YAML sample header shows all available options. Minimal required elements are: layout, title and date. Date tag overrides date given in post file name.
*   `blog/index.html`
    *   Edit blog title and description.
*   `images/`
    *   Under `icons` directory, keep appropriate sized favicons and thumbnails as specified in `_includes/head.html` and `_includes/head_minimal.html`
    *   Also, keep `favicon.png` and `favicon.ico` in root directory.
    *   Final, `images/icons/` should have following images with exact filenames and image size as specified in respective filenames. These images can be generated using online *favicon generator*. Replace `foo` with your site title or other name if desired.

~~~
example.com/images/icons/apple-touch-icon-precomposed.png
example.com/images/icons/apple-touch-icon-72x72-precomposed.png
example.com/images/icons/apple-touch-icon-114x114-precomposed.png
example.com/images/icons/apple-touch-icon-144x144-precomposed.png
example.com/images/icons/apple-touch-icon-180x180.png
example.com/images/icons/android-icon-192x192.png
~~~

*   `pages/about.md`
    *   YAML variable `imagefeature` shoud have image path relative to `images/` directory, i.e., `foo.png` will link to `example.com/images/foo.png`
    *   Specify `site.owner.avatar` and `site.owner.twitter` along with other variables in `_config.yml`
*   `syspages/`:
    *   Edit page title and description in YAML front matter.
    *   For web search to work, specify [Google Custom Search Engine](https://cse.google.com) API key for `google_search` variable.
    *   Tag generation is experimental and dynamic size for tag box may need to be adjusted if you have more than 100 posts with one or two frequently occurring tags. 
    *   All `{% for ... %}...{% endfor %}` loop operations will increase site build time, and remove such features (tags, meta info, related posts, etc.) under `_includes`, `_layouts` and `syspages` if required.
*   `pages/contact.md`
    *   Edit page title and description.
    *   Edit address, driving direction url, etc.
*   `pages/cv.md`
    *   Edit `_config.yml` to add twitter, google plus, linkedin, google scholar, ORCID profile info under owner heading.   
    *   Add pdf at `{{ site.url }}/cv/cv.pdf` 
*   `pages/publications.md`
    *   Add your publications at `/files/mypubs.bib` and `_includes/mypubs.html`. See above under `_includes` for more.
*   `pages/disclosure.md`
    *   Appreciated if you keep relevant credits in disclosure page.
*   `humans.txt`
    *   Replace `foo` with your name.
*   `rfeed.xml`
    *   Not required unless you are cross-posting about R language on blog aggregation site(s).

END
 {% endraw %}
