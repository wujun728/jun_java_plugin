jQuery.bsgrid - 简单实用、功能丰富、易扩展的jQuery Grid插件
=======================================================

<a href="http://thebestofyouth.com/bsgrid/" target="_blank">jQuery.bsgrid</a>，支持json、xml数据格式，皮肤丰富并且容易定制，支持表格编辑、本地数据、导出参数构建等实用便捷的功能，容易扩展，更拥有丰富的示例以及问题反馈的及时响应。

源码：[Github](https://github.com/baishui2004/jquery.bsgrid/)&emsp;&emsp;&emsp;&emsp;&emsp;演示：<a href="http://bsgrid.daoapp.io/documention/themes.html" target="_blank">皮肤</a>&emsp;<a href="http://bsgrid.daoapp.io/examples/zh-CN.html" target="_blank">示例</a>&emsp;<a href="http://bsgrid.daoapp.io/documention/documention.zh-CN.html" target="_blank">文档</a>
版本：1.38-preview&emsp;&emsp;&nbsp;协议：Apache Licence 2&emsp;&emsp;&nbsp;更新：2016-01-21  
依赖：jQuery 1.4.4 ~~ jQuery 1.12.2&emsp;&emsp;&nbsp;支持：IE6+、Chrome、Firefox等

QQ群交流：254754154&emsp;&emsp;&nbsp;&nbsp;捐助：<a href="http://bsgrid.daoapp.io/donate.html" target="_blank" style="text-decoration: none;">支持长远发展，感谢您的认可！</a>

### 皮肤效果 ###
内置多套皮肤(点击图片查看示例页面)，并可非常容易的定制皮肤[示例：<a href="http://bsgrid.daoapp.io/examples/zh-CN.html#href=examples/grid/themes/custom.html" target="_blank">Custom Blue Style</a>]
<a href="http://bsgrid.daoapp.io/examples/grid/simple.html" target="_blank"><img title="点击图片查看示例页面" src="http://git.oschina.net/bs2004/jquery.bsgrid/raw/v1.37/documention/images/themes.jpg" /></a>

### bsgrid的由来 ###
&emsp;&emsp;首先，解释插件名称为何叫bsgrid，是因为作者常用bs开头的字符做英文账号的缘故。bsgrid的诞生因为主流插件、框架的grid使用或扩展比较复杂，而本插件作者力图开发一款使用简单、功能实用、容易扩展的grid控件，目前已初步实现此目标。

### bsgrid的特点 ###
&emsp;&emsp;1，简单、轻量，基于jQuery及HTML Table，<a href="http://bsgrid.daoapp.io/examples/zh-CN.html#href=examples/grid/simple.html" target="_blank">简单的表格</a>只需数十行代码，支持<a href="http://bsgrid.daoapp.io/examples/zh-CN.html#href=examples/grid/load-time-test.html" target="_blank">大数据量表格</a>；
&emsp;&emsp;2，内置<a href="http://bsgrid.daoapp.io/examples/zh-CN.html#href=examples/grid/simple.html" target="_blank">多套经典皮肤</a>，且非常容易<a href="http://bsgrid.daoapp.io/examples/zh-CN.html#href=examples/grid/themes/custom.html" target="_blank">定制</a>，字体定制只需要修改两处CSS代码即可；
&emsp;&emsp;3，实用便捷的功能：<a href="http://bsgrid.daoapp.io/examples/zh-CN.html#href=examples/grid/edit.html" target="_blank">表格编辑</a>、<a href="http://bsgrid.daoapp.io/examples/zh-CN.html#href=examples/grid/foot.html" target="_blank">表底聚合</a>、<a href="http://bsgrid.daoapp.io/examples/zh-CN.html#href=examples/grid/no-pagation.html" target="_blank">不分页</a>、<a href="http://bsgrid.daoapp.io/examples/zh-CN.html#href=examples/grid/multi-header.html" target="_blank">多行表头</a>、<a href="http://bsgrid.daoapp.io/examples/zh-CN.html#href=examples/grid/multi-sort.html" target="_blank">多字段排序</a>、<a href="http://bsgrid.daoapp.io/examples/zh-CN.html#href=examples/grid/local/json.html" target="_blank">本地数据</a>、<a href="http://bsgrid.daoapp.io/examples/zh-CN.html#href=examples/grid/userdata.html" target="_blank">处理Userdata</a>、<a href="http://bsgrid.daoapp.io/examples/zh-CN.html#href=examples/grid/move-column-extend.html" target="_blank">拖动列宽</a>、<a href="http://bsgrid.daoapp.io/examples/zh-CN.html#href=examples/grid/fixed-header/fixed-header-extend.html" target="_blank">滚动表格数据</a>等；
&emsp;&emsp;4，易与其他插件集成使用，示例展示了集成<a href="http://bsgrid.daoapp.io/examples/zh-CN.html#href=examples/layui/layer.html" target="_blank">Layui</a>、<a href="http://bsgrid.daoapp.io/examples/zh-CN.html#href=examples/artDialog/gridAndForm.html" target="_blank">ArtDialog</a>、<a href="http://bsgrid.daoapp.io/examples/zh-CN.html#href=examples/form/validation.html" target="_blank">jquery.validationEngine</a>、第三方分页工具条等的使用；
&emsp;&emsp;5，扩展性好，插件有特别好的扩展性，易于对插件本身进行局部甚至较大的修改，易于改变展现样式；插件放开了属性及方法的全局修改权限，所有方法都可在外部进行全局重写，而无需修改插件本身的代码；  
&emsp;&emsp;6，模块化JS、CSS代码，可按需加载，代码精致简洁，对于阅读、修改、扩展非常容易。

### 关于主流Grid ###
<b>整体评论主流grid：</b>  
&emsp;&emsp;1，比较适用于内部系统，对于外部系统适用而言，想要改变皮肤样式，字体大小等都非常困难；  
&emsp;&emsp;2，过度封装，造成了其扩展性能不是很好，并且其methods、properties很多，上手不容易；  
&emsp;&emsp;3，大多数不提供多行表头、表格聚合、不分页表格、本地数据等实用却强大的功能。

<b>分别评论几个主流grid，个人见解：</b>  
&emsp;&emsp;1，<a href="http://www.sencha.com/products/extjs/" target="_blank">ExtJS</a>，功能丰富，封装好，但属重量级产品，需要加载大体积文件，且响应速度较慢，需商业授权，一般用于内部系统；  
&emsp;&emsp;2，<a href="http://dhtmlx.com/" target="_blank">DHtmlx</a>，同样功能丰富，封装好，不过其可以根据所需要的模块进行加载，速度方面快于ExtJS，需商业授权，由于其样式不易修改，同样一般用于内部系统；  
&emsp;&emsp;3，<a href="http://www.jeasyui.com/" target="_blank">EasyUI</a>，基于jQuery，语法使用jQuery，却部分地方像ExtJS的写法，在不需其源码的情况下无需商业授权，因无源码而不方便按需模块化加载，也很难改变皮肤样式；  
&emsp;&emsp;4，<a href="http://www.jqgrid.com/" target="_blank">jQGrid</a>，基于jQuery，开源免费且功能特别强大，但同样其样式不易修改；  
&emsp;&emsp;5，<a href="http://www.flexigrid.info/" target="_blank">Flexigrid</a>，基于jQuery，功能逊色，但轻量级，methods、properties较少，不失为想用ExtJS Grid或EasyUI Grid却难以上手这两者的另外一个选择。