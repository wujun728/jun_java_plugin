---
layout: post
title: "Example content"
date: "2013-01-01"
slug: "example_content"
description: "Example content from lanyon. If page description is more than 140 words, it will be shown as post summary on home page and blog index else post excerpt will be shown. Same rule is for html meta description: >140 words in description or first 50 words of posts will be shown as summary. Page excerpt supports markdown formatted summary."
category: 
  - views
  - featured
# tags will also be used as html meta keywords.
tags:
  - examples
  - common_tag
show_meta: true
comments: true
mathjax: true
gistembed: true
published: false
noindex: false
nofollow: false
# hide QR code, permalink block while printing.
hide_printmsg: false
# show post summary or full post in RSS feed.
summaryfeed: false
## for twitter summary card with squared image and page description or page excerpt:
# imagesummary: foo.png
## for twitter card with large image:
# imagefeature: "http://img.youtube.com/vi/VEIrQUXm_hY/0.jpg"
## for twitter video card: (active for this page)
videofeature: "https://www.youtube.com/embed/iG9CE55wbtY"
imagefeature: "http://img.youtube.com/vi/iG9CE55wbtY/0.jpg"
videocredit: tedtalks
---

Howdy! This is an example blog post that shows features supported in **lanyon-plus** theme. See [raw post](https://raw.githubusercontent.com/dyndna/lanyon-plus/master/_posts/2013-01-01-example-content.md) for required YAML header and liquid tag specifications.

<!--more-->

* TOC
{:toc}

### Heading

# H1
{:.no_toc}

## H2
{:.no_toc}

### H3
{:.no_toc}

#### H4
{:.no_toc}

##### H5
{:.no_toc}

###### H6
{:.no_toc}

Vivamus sagittis lacus vel augue rutrum faucibus dolor auctor. Duis mollis, est non commodo luctus, nisi erat porttitor ligula, eget lacinia odio sem nec elit. Morbi leo risus, porta ac consectetur ac, vestibulum at eros.

### Inline HTML elements

HTML defines a long list of available inline tags, a complete list of which can be found on the [Mozilla Developer Network](https://developer.mozilla.org/en-US/docs/Web/HTML/Element).

- **To bold text**, use `<strong>`.
- *To italicize text*, use `<em>`.
- Abbreviations, like <abbr title="HyperText Markup Langage">HTML</abbr> should use `<abbr>`, with an optional `title` attribute for the full phrase.
- Citations, like <cite>&mdash; Mark otto</cite>, should use `<cite>`.
- <del>Deleted</del> text should use `<del>` and <ins>inserted</ins> text should use `<ins>`.
- Superscript <sup>text</sup> uses `<sup>` and subscript <sub>text</sub> uses `<sub>`.

Most of these elements are styled by browsers with few modifications on our part.

### Code

Cum sociis natoque penatibus et magnis dis `code element` montes, nascetur ridiculus mus.

{% highlight js %}
// Example can be run directly in your JavaScript console

// Create a function that takes two arguments and returns the sum of those arguments
var adder = new Function("a", "b", "return a + b");

// Call the function
adder(2, 6);
// > 8
{% endhighlight %}

Aenean lacinia bibendum nulla sed consectetur. Etiam porta sem malesuada magna mollis euismod. Fusce dapibus, tellus ac cursus commodo, tortor mauris condimentum nibh, ut fermentum massa.

#### Alternate code block:

~~~r
library(uuid)
library(stringr)
library(parallel)
library(data.table)
~~~

#### `pygments` based highlighting (deprecated)

{% highlight bash %}
library(uuid)
library(stringr)
library(parallel)
library(data.table)
{% endhighlight %}

### Lists

Cum sociis natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Aenean lacinia bibendum nulla sed consectetur. Etiam porta sem malesuada magna mollis euismod. Fusce dapibus, tellus ac cursus commodo, tortor mauris condimentum nibh, ut fermentum massa justo sit amet risus.

* Praesent commodo cursus magna, vel scelerisque nisl consectetur et.
* Donec id elit non mi porta gravida at eget metus.
* Nulla vitae elit libero, a pharetra augue.

Donec ullamcorper nulla non metus auctor fringilla. Nulla vitae elit libero, a pharetra augue.

1. Vestibulum id ligula porta felis euismod semper.
2. Cum sociis natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus.
3. Maecenas sed diam eget risus varius blandit sit amet non magna.

Cras mattis consectetur purus sit amet fermentum. Sed posuere consectetur est at lobortis.

<dl>
  <dt>HyperText Markup Language (HTML)</dt>
  <dd>The language used to describe and define the content of a Web page</dd>

  <dt>Cascading Style Sheets (CSS)</dt>
  <dd>Used to describe the appearance of Web content</dd>

  <dt>JavaScript (JS)</dt>
  <dd>The programming language used to build advanced Web sites and applications</dd>
</dl>

Integer posuere erat a ante venenatis dapibus posuere velit aliquet. Morbi leo risus, porta ac consectetur ac, vestibulum at eros. Nullam quis risus eget urna mollis ornare vel eu leo.

### Tables

#### markdown formatted:

| Heading 1 | Heading 2 | Heading 3 |
| :--- | --- | ---: |
| Hello | World | <i class="fa fa-twitter"> @foo</i> |

Aenean lacinia bibendum nulla sed consectetur. Lorem ipsum dolor sit amet, consectetur adipiscing elit.

#### html table:

<table>
  <thead>
    <tr>
      <th>Name</th>
      <th>Upvotes</th>
      <th>Downvotes</th>
    </tr>
  </thead>
  <tfoot>
    <tr>
      <td>Totals</td>
      <td>21</td>
      <td>23</td>
    </tr>
  </tfoot>
  <tbody>
    <tr>
      <td>Alice</td>
      <td>10</td>
      <td>11</td>
    </tr>
    <tr>
      <td>Bob</td>
      <td>4</td>
      <td>3</td>
    </tr>
    <tr>
      <td>Charlie</td>
      <td>7</td>
      <td>9</td>
    </tr>
  </tbody>
</table>

### Text highlight:

Nullam id dolor id nibh ultricies vehicula ut id elit. Sed posuere consectetur est at lobortis. Nullam quis risus eget urna mollis ornare vel eu leo. `Nullam id dolor id nibh ultricies vehicula ut id elit. Highlighted color will be removed during prinintg and replaced with underline.`{:.yelhglt}

### Blockquotes

#### stylized

<blockquote class="style1">
Aenean lacinia bibendum nulla sed consectetur. Etiam porta sem malesuada magna mollis euismod.
</blockquote>

#### regular

>When you bring new things into a society, you can either, it's like the balance of the force. You can either use it for good or you can use it for evil. And what happens when something new: People have a tendency to overdo it; they abuse it.

### Embed video

{% include youtube.html %}

### Embed picture

{:.text-center img}
![Toy example]({{ site.urlimg }}/media/apple-icon.png "Toy example")

### Inline css attributes

[MahJax source - link open in new window](http://haixing-hu.github.io/programming/2013/09/20/how-to-use-mathjax-in-jekyll-generated-github-pages/){:target="_blank"}

### MathJax

Let's test some inline math $x$, $y$, $x_1$, $y_1$.

Now a inline math with special character: $\|\psi\rangle$, $x'$, $x^\*$ and $\|\psi_1\rangle = a\|0\rangle + b\|1\rangle$

Test a display math:
$$
   |\psi_1\rangle = a|0\rangle + b|1\rangle
$$
Is it O.K.?

Test a display math with equation number:
\begin{equation}
   |\psi_1\rangle = a|0\rangle + b|1\rangle
\end{equation}
Is it O.K.?

Test a display math with equation number:

$$
  \begin{align}
    |\psi_1\rangle &= a|0\rangle + b|1\rangle \\
    |\psi_2\rangle &= c|0\rangle + d|1\rangle
  \end{align}
$$
Is it O.K.?

And test a display math without equaltion number:
$$
  \begin{align*}
    |\psi_1\rangle &= a|0\rangle + b|1\rangle \\
    |\psi_2\rangle &= c|0\rangle + d|1\rangle
  \end{align*}
$$
Is it O.K.?

Test a display math with equation number:
$$
\begin{align}
    |\psi_1\rangle &= a|0\rangle + b|1\rangle \\
    |\psi_2\rangle &= c|0\rangle + d|1\rangle
\end{align}
$$
Is it O.K.?

And test a display math without equaltion number:
$$
\begin{align*}
    |\psi_1\rangle &= a|0\rangle + b|1\rangle \\
    |\psi_2\rangle &= c|0\rangle + d|1\rangle
\end{align*}
$$

### gist embed

Below is a partial code showing main steps of merge function.

<code data-gist-id="0fe211678316cc53370c" data-gist-file="merge_tables_datatable.R" data-gist-line="50-52,57,65-69,80,88-90,100-106"></code>

### References

Multiple[^1]<sup>,</sup>[^3] and with comments[^2]

Want to see something else added? <a href="https://github.com/dyndna/lanyon-plus/issues/new">Open an issue.</a>

[^1]: [lanyon theme](http://lanyon.getpoole.com)
[^2]: 
    [lanyon-plus theme](https://github.com/dyndna/lanyon-plus "accessed on {{ page.date | date: '%B %d, %Y' }}")

    >Excerpt: Sample post showing enabled features for post: `inline code`, `text highlight`{:.yelhglt}, code block with syntax highlights, embed gist, stylized blockquotes, video and image cards for twitter, markdown tables, inline and code bocks having mathjax support, references, print format, disqus comments, related posts, tags.

[^3]: [About]({{ site.url }}/about)
