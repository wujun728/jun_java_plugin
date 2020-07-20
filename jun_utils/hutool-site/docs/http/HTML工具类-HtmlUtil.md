HTML工具类-HtmlUtil
===

## 由来

针对Http请求中返回的Http内容，Hutool使用此工具类来处理一些HTML页面相关的事情。

比如我们在使用爬虫爬取HTML页面后，需要对返回页面的HTML内容做一定处理，比如去掉指定标签（例如广告栏等）、去除JS、去掉样式等等，这些操作都可以使用**HtmlUtil**完成。

## 方法

### `HtmlUtil.escape` 

转义HTML特殊字符，包括：

1. `'` 替换为 `&#039;`
2. `"` 替换为 `&quot;`
3. `&` 替换为 `&amp;`
4. `<` 替换为 `&lt;`
5. `>` 替换为 `&gt;`

```java
String html = "<html><body>123'123'</body></html>";
// 结果为：&lt;html&gt;&lt;body&gt;123&#039;123&#039;&lt;/body&gt;&lt;/html&gt;
String escape = HtmlUtil.escape(html);
```


###  `HtmlUtil.unescape` 

还原被转义的HTML特殊字符

```java
String escape = "&lt;html&gt;&lt;body&gt;123&#039;123&#039;&lt;/body&gt;&lt;/html&gt;";
// 结果为：<html><body>123'123'</body></html>
String unescape = HtmlUtil.unescape(escape);
```

###  `HtmlUtil.removeHtmlTag` 

清除指定HTML标签和被标签包围的内容

```java
String str = "pre<img src=\"xxx/dfdsfds/test.jpg\">";
// 结果为：pre
String result = HtmlUtil.removeHtmlTag(str, "img");
```

###  `HtmlUtil.cleanHtmlTag` 

清除所有HTML标签，但是保留标签内的内容

```java
String str = "pre<div class=\"test_div\">\r\n\t\tdfdsfdsfdsf\r\n</div><div class=\"test_div\">BBBB</div>";
// 结果为：pre\r\n\t\tdfdsfdsfdsf\r\nBBBB
String result = HtmlUtil.cleanHtmlTag(str);
```

###  `HtmlUtil.unwrapHtmlTag` 

清除指定HTML标签，不包括内容

```java
String str = "pre<div class=\"test_div\">abc</div>";
// 结果为：preabc
String result = HtmlUtil.unwrapHtmlTag(str, "div");
```

###  `HtmlUtil.removeHtmlAttr` 

去除HTML标签中的指定属性，如果多个标签有相同属性，都去除

```java
String html = "<div class=\"test_div\"></div><span class=\"test_div\"></span>";
// 结果为：<div></div><span></span>
String result = HtmlUtil.removeHtmlAttr(html, "class");
```

###  `HtmlUtil.removeAllHtmlAttr` 

去除指定标签的所有属性

```java
String html = "<div class=\"test_div\" width=\"120\"></div>";
// 结果为：<div></div>
String result = HtmlUtil.removeAllHtmlAttr(html, "div");
```

###  `HtmlUtil.filter` 过滤HTML文本，防止XSS攻击

```java
String html = "<alert></alert>";
// 结果为：""
String filter = HtmlUtil.filter(html);
```
