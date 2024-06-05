中文 &nbsp; | &nbsp; [English](./README.en.md)

<p align="center">
  <a href="http://www.layui.com">
    <img src="https://sentsin.gitee.io/res/images/layui/layui.png" alt="layui" width="360">
  </a>
</p>
<p align="center">
  Classic modular front-end UI framework
</p>

---

<p align="center">
    <a href="https://www.npmjs.com/package/layui"><img src="https://img.shields.io/npm/v/layui.svg?sanitize=true" alt="Version"></a>
    <a href="./LICENSE"><img src="https://img.shields.io/badge/License-MIT-blue" alt="License MIT"></a>
    <a href="https://gitee.com/ayq947/ayq-layui-form-designer"><img src="https://gitee.com/ayq947/ayq-layui-form-designer/badge/star.svg?theme=dark" alt="Gitee star"></a>
    <a href="https://gitee.com/ayq947/ayq-layui-form-designer"><img src="https://gitee.com/ayq947/ayq-layui-form-designer/badge/fork.svg?theme=dark" alt="Gitee fork"></a>
</p>

<p align="center">
    <a href="https://gitee.com/ayq947/ayq-layui-form-designer">Gitee 仓库</a> &nbsp; | &nbsp; 
    <a href="http://116.62.237.101:8009/" target="_blank">在线体验</a>
</p>

# ayq-layui-form-designer

#### 各位，新的代码已经提交到gitee上，只是最初始的版本，后续的组件还需要大家来创建和维护，我会开发几个重要的容器组件，因为开源上并没有layui相关的容器组件源码，下面的组件开发计划看看各位大佬有谁愿意贡献一下，文档正在路上，有什么问题可以在留言区和issues里说，现在这套代码很适合二次开发，开发和编写文档不易，要求不多，给个Star支持一下，需要一些开发动力，嘿嘿

#### 各位，新的版本已经部署到演示环境了，各位可以测试一下，提出些问题，后续更新更完善的文档，有问题可以私信我或者在评论区留言

#### 版本升级日志
    1. 替换了以前的以文字来展示组件
    2. 合并了主要的js，以前需要维护2份js（设计和视图的js）
    3. 把组件数据单独提出到formField.js，方便组件的维护
    4. 优化了主要js的核心方法，用户只需要关注编写组件的渲染（具体查看下面优化的设计代码），方便用户进行2次开发
    5. 添加了组件模板功能
    6. 添加了可编辑一些全局表单属性的功能（更新后会有）
#### 后续开发计划
    1.  支持layui的扩展组件
    2.  开发表格布局组件，发现这个没有现成的，需要用layui来扩展开发
    3.  方便用户进行二次开发，主要是我参考了其他的表单设计，发现二次开发效果很差，源码改不动。
    4.  支持通过formDesigner对象的方法满足用户使用需求
    5.  支持通过url获取远程数据动态显示组件（如下拉框、编辑器、图片等）
    6.  支持定制布局和背景
#### 后续组件开发计划
    1.  下拉级联组件
    2.  容器-选项卡组件（正在开发）
    3.  容器-子表单组件（正在开发）
    4.  容器-表格组件（正在开发）
    5.  支持通过url获取远程数据动态显示组件（如下拉框、编辑器、图片等）
    6.  其他优秀的扩展组件也可以加入进来

## 演示地址（五一后回归更新状态，后续根据具体情况进行优化，最近会去研究layui-vue，可能后续会做这方面的表单设计器，当然原生的layui也不会丢弃，但是希望广大开源者能够继续完善这个表单设计器）
#### [http://www.anyongqiang.com/](http://116.62.237.101:8009/)

#### 手写签名演示地址
[http://www.anyongqiang.com/HandwrittenSignature/index.html](http://116.62.237.101:8009/HandwrittenSignature/index.html)

#### 介绍
基于layui的表单设计器


#### 使用说明

1. 本项目基于Layui、Jquery、Sortable
2. 项目已经基本实现了拖动布局，父子布局
3. 项目实现了大部分基于Layui的Form表单控件布局，包括输入框、编辑器、下拉、单选、单选组、多选组、日期、滑块、评分、轮播、图片、颜色选择、图片上传、文件上传、日期范围、排序文本框、图标选择器、cron表达式、手写签名组件

#### 更新日志
- 2021-06-15 
    1. 增加输入框layui提供的基本校验规则
    2. 禁用的显示效果优化
    3. 优化表单展示滑块、评分、颜色选择器提交无法获取字段值得问题
- 2021-06-17 
    1. 增加时间范围组件（暂未提交代码）
    2. 页面自适应优化
- 2021-06-22 
    1. 增加时间范围组件
    2. 展示页面提交参数优化
- 2021-06-24 
    1. 引入iceEditor富文本编辑组件
- 2021-06-30 
    1. 加入iceEditor富文本编辑组件
    2. 解决一行多列样式异常问题
    3. 结局一行多列嵌套问题
    4. 优化富文本参数无法获取问题
- 2021-07-01 
    1. 加入排序文本框组件
    2. 加入图标选择器组件
    3. 加入Cron表达式组件
    4. 优化富文本编辑器（菜单编辑本地直接访问会出现跨域问题，放入nginx、tomcat等容器就会正常）
    5. 发布V1.0.0
- 2021-07-23 
    1. 更新版本V1.0.1
    2. 加入标签组件
    3. 加入按钮组件
    4. 优化栅栏格内部拖动是出现样式混乱问题
    5. 优化已放入多个的组件，拖动排序无效问题
    6. 优化标签组件标签过多，组件排序样式出现错位问题
- 2021-08-03 
    1. 加入手写签名组件
- 2021-08-11 
    1. 优化各个组件间的交互
    2. 表单视图新增表单数据的获取与回显
    3. 表单视图新增禁用表单与启用表单
    4. 更新版本V1.1.0
- 2021-08-26 
    1. 下拉，多选，单选，轮播配置多个选项增加拖拽功能
    2. 优化下拉，多选，单选，轮播默认选项（配置和设计页面都可以设置默认值）
    3. 优化宽度体验，改成滑块
    4. 增加修改文本框长度属性,滑块操作
    5. 增加必填项展示加*
    6. 优化生成代码功能
    7. 增加新建窗口中打开展示页面
    8. 更新版本V1.1.5
- 2021-10-11 
    1. 简化引入的样式，回归layui的简洁
    2. 优化一些相关的细节问题
    3. 更新版本V1.1.6
- 2022-06-07 
    1. 更新版本V2.0.0
- 2022-06-09 
    1. 一行多例嵌套后代码生成问题解决
- 2022-06-15 
    1. 修复下拉，单选组，复选组的属性选项无法双向绑定问题
- 2022-09-09 
    1. 修复标识属性不能修改问题
    2. 预览时提交是值未发生变化问题
    3. 优化评分组件，增加颜色控制属性
    4. 增加便签组件，分割线组件，间距组件
- 2022-09-15 
    1. 优化标识修改，让表单操作更加安全


![输入图片说明](ayq/images/1.PNG)
![输入图片说明](ayq/images/2.PNG)
![输入图片说明](ayq/images/3.PNG)
![输入图片说明](ayq/images/4.PNG)

#### 入门案例（现在把设计页面和视图页面的表单对象整合到一起了，全部引用formDesigner.js，维护2份文件太累了）


``` js
var render = formDesigner.render({
                data:[],//表单设计数据
                elem:'#formdesigner'
formData: {"textarea_1":"123",
            "input_2":"123",
            "password_3":"123"}//要回显的表单数据
            , globalDisable:false //全局禁用属性
            , viewOrDesign:false//是渲染设计页面还是视图页面
            , formDefaultButton:true//是否添加默认按钮
            });

//重新渲染数据
render.reload(options)

//获取相关配置信息
render.getOptions() 

//获取表单设计数据
render.getData()
//获取外部编辑器对象
render.geticeEditorObjects()
//重新渲染数据
render.reload(options)

//获取相关配置信息
render.getOptions() 

//获取表单设计数据
render.getData()

//获取外部编辑器对象
render.geticeEditorObjects()

//获取上传图片的id与上传路径
render.getImages()
//数据案例 select 对应文件对象的id uploadUrl对应文件的上传路径
[{select: "imageimage_2",uploadUrl: ""}]

//获取上传文件的id与上传路径
render.getFiles()
//数据案例 select 对应文件对象的id uploadUrl对应文件的上传路径
[{select: ""filefile_1"",uploadUrl: ""}]

//获取表单数据 
**
注意: 当前方法会避开校验规则，最好放在表单提交里面 
form.on('submit(demo1)', function(data){}）；
** 
render.getFormData()

//回显表单数据 
render.setFormData(json)

//全局禁用表单
render.globalDisable()

//全局启用表单
render.globalNoDisable()

 ** 
说明：  这些方法有2个组件不受控制（文件组件和图片组件），
我把这两个组件通过方法单独提出来，因为文件上传的方式比较多，
提出来让使用者自己去定义和实现自己的文件上传方式，
具体的案例在preview.html里面已经写好，你们自己参考
** 
```

#### 优化的设计代码（以后二次开发只需要编写这个组件对象的几个方法和编写一下(formField.js)组件的json数据，具体的开发流程后面会写在开发文档上）


``` js
Class.prototype.components = {
date: {
                 /**
                 * 根据json对象生成html对象
                 * @param {object} json 当前组件的json属性
                 * @param {boolean} selected 是否被选中
                 * @param {object} elem 表单面板jquery对象
                 * @param {object} that 实例对象
                 * */
                render: function (json, selected,elem,that) {
                    if (selected === undefined) {
                        selected = false;
                    }
                    var _disabledClass = json.disabled ? ' layui-disabled' : '';
                    var _disabledStyle = json.disabled ? ' pointer-events: none;' : '';
                    var _required = json.required ? 'required' : '';
                    var _html = '<div id="{0}" class="layui-form-item {2}"  data-id="{0}" data-tag="{1}" data-index="{3}">'.format(json.id, json.tag, selected ? 'active' : '', json.index);
                    _html += '<label class="layui-form-label {0}" style="width: {3}px;"><span style="color:red;">{2}</span>{1}:</label>'.format(json.required ? 'layui-form-required' : '', json.label,json.required ? '*' : '',json.labelWidth);
                    _html += '<div class="layui-input-block" style="width:calc({0} - {1}px);margin-left: {1}px;">'.format(json.width,json.labelWidth);
                    _html += '<input id="{0}" name="{0}" lay-verify="{3}" class="layui-input icon-date widget-date {1}" style="line-height: 40px;{2}"></input>'.format(json.tag + json.id,_disabledClass,_disabledStyle,_required);
                    _html += '</div>';
                    _html += '</div>';
                    elem.append(_html);
                    laydate.render({
                        elem: '#' + json.tag + json.id,
                        btns: ['confirm'],
                        type: json.dateType,
                        format: json.dateFormat,
                        value: json.dateDefaultValue,
                        min: json.dataMinValue,
                        max: json.dataMaxValue,
                    });
                },
                 /**
                 * 根据json对象更新html对象
                 * @param {object} json 变更后的json属性
                 * @param {object} that 实例对象
                 * */
                update: function (json,that) {
                    var _disabledClass = json.disabled ? ' layui-disabled' : '';
                    var _disabledStyle = json.disabled ? ' pointer-events: none;' : '';
                    var _required = json.required ? 'required' : '';
                    var $block = $('#' + json.id + ' .layui-input-block');
                    var $label = $('#' + json.id + ' .layui-form-label');
                    $block.empty();
                    $label.empty();
                    $block.css("margin-left",json.labelWidth);
                    $label.css("width",json.labelWidth);
                    if (json.required) {
                        $label.append('<span style="color:red;">*</span>');
                    }
                    $label.append(json.label + ":");
                    $block.css({width: 'calc({0} - {1}px)'.format(json.width,json.labelWidth)});
                    var _html = '<input id="{0}" name="{0}"  lay-verify="{3}" class="layui-input icon-date widget-date {1}" style="line-height: 40px;{2}"></input>'.format(json.tag + json.id,_disabledClass,_disabledStyle,_required);
                    $block.append(_html);
                    laydate.render({
                        elem: '#' + json.tag + json.id,
                        btns: ['confirm'],
                        type: json.dateType,
                        format: json.dateFormat,
                        value: json.dateDefaultValue,
                        min: json.dataMinValue,
                        max: json.dataMaxValue,
                    });
                },
                 /**
                 * 根据components组件对象获取组件属性
                 * @param {object} id 所属组件id
                 * @param {object} index 所属对象组件索引
                 * @param {object} that 实例对象
                 * */ 
                jsonData: function (id, index, that) {
                    //分配一个新的ID
                    var _json = JSON.parse(JSON.stringify(formField.components.date));
                    _json.id = id;
                    _json.index = index;
                    return _json;

                },
                 /**
                 * 根据 json 对象显示对应的属性
                 * @param {object} json 当前组件的json属性
                 * @param {object} that 实例对象
                 * */
                property: function (json,that) {
                    that.renderCommonProperty(json); //根据 json 对象获取对应的属性的html
                    that.initCommonProperty(json); //初始化 json 对象获取对应的属性
                },
                 /**
                 * 根据json对象生成html文本
                 * @param {object} json 当前组件的json属性
                 * @param {boolean} selected 是否被选中
                 * @param {object} that 实例对象
                 * */
                generateHtml: function (json,selected,that) {
                    if (selected === undefined) {
                        selected = false;
                    }
                    var _disabledClass = json.disabled ? ' layui-disabled' : '';
                    var _disabledStyle = json.disabled ? ' pointer-events: none;' : '';
                    var _required = json.required ? 'required' : '';
                    var _html = '<div id="{0}" class="layui-form-item {2}"  data-id="{0}" data-tag="{1}" data-index="{3}">'.format(json.id, json.tag, selected ? 'active' : '', json.index);
                    _html += '<label class="layui-form-label {0}" style="width: {3}px;"><span style="color:red;">{2}</span>{1}:</label>'.format(json.required ? 'layui-form-required' : '', json.label,json.required ? '*' : '',json.labelWidth);
                    _html += '<div class="layui-input-block" style="width:calc({0} - {1}px);margin-left: {1}px;">'.format(json.width,json.labelWidth);
                    _html += '<input id="{0}" name="{0}" lay-verify="{3}" class="layui-input icon-date widget-date {1}" style="line-height: 40px;{2}"></input>'.format(json.tag + json.id,_disabledClass,_disabledStyle,_required);
                    _html += '</div>';
                    _html += '</div>';
                    return _html;
                },
                 /**
                 * 根据json对象生成js文本
                 * @param {object} json 变更后的json属性
                 * @param {object} that 实例对象
                 * */
                generateScript:function (json,that) {
                    var scriptHtmlCode = '';
                    scriptHtmlCode += ['laydate.render({'
                        , 'elem: "#' + json.tag + json.id + '" ,'
                        , 'type:"' + json.datetype + '",'
                        , 'format:"' + json.dateformat + '",'
                        , 'value:"' + json.dateDefaultValue + '",'
                        , 'min:"' + json.dataMinValue + '",'
                        , 'max:"' + json.dataMaxValue + '"});'].join('');
                    return scriptHtmlCode;
                }
            },
}

formField.js
 var formField = {
        components : {
            date: {
                id:'-1',
                index:'-1',
                label: "日期",
                tag: "date",
                tagIcon: 'date',
                labelWidth: 110,
                width:"100%",
                clearable: true,
                maxlength: null,
                dateDefaultValue: '2021-05-25',
                dateType: "date",//year month date time datetime
                range: false,
                dateFormat: "yyyy-MM-dd",
                isInitValue: false,
                dataMaxValue: "2088-12-31",
                dataMinValue: "1900-01-01",
                trigger: null,//自定义弹出控件的事件
                position: "absolute",//fixed,static,abolute
                theme: "default",
                mark: null,//每年的日期	{'0-9-18': '国耻'}	0 即代表每一年
                showBottom: true,
                disabled: false,
                required: true,
                document: '',
            },
}
}
```

#### 基础参数

| 参数  | 类型  | 说明  |  示例值 |
|---|---|---|---|
|  elem |  String | 指定原始 table 容器的选择器，方法渲染方式必填  | "#elem"  |
|  data |  Array | 直接赋值数据  |  [{},{},...] |
|  formData|  Array | 回显的表单数据  |  [{},{},...] |

#### 组件参数

| 参数  | 类型  | 说明  |  示例值 |
|---|---|---|---|
|  id |  String | 指定组件标识（唯一），表单提交字段name值  | "field"  |
|  label | String  | 文本框标题  |  "姓名" |
|  tag | String  | 表单类型  |  "input" |
|  placeholder | String  | placeholder  |  "请输入" |
|  defaultValue | object  | 组件默认值  |  "姓名" |
|  width | String  | 组件宽度  |  "100%" |
|  labelWidth | String  | 文本框宽度  |  "250px" |
|  readonly | Boolean  | 只读  |  true,false |
|  disabled | Boolean  | 禁用  |  true,false |
|  required | Boolean  | 必填  |  true,false |
|  columns | number  | 栅格布局列数  |  true,false |
|  maxValue | object  | 最大值  |  "" |
|  minValue | object  | 最小值  |  "" |
|  expression | String  | 验证  |  "email" |
|  stepValue | number  | 滑块步长  |  2 |
|  isInput | Boolean  | 滑块显示输入框  |  true,false |
|  datetype | String  | 日期类型  |  "时间选择器" |
|  dateformat | String  | 日期格式  |  "yyyy-MM-dd" |
|  rateLength | number  | 星星个数  |  5 |
|  interval | number  | 轮播间隔毫秒  |  3000 |
|  autoplay | Boolean  | 轮播自动切换  |  true,false |
|  anim | object  | 切换方式  |  {text: '左右切换', value: 'default'} |
|  arrow | object  | 切换箭头  |  {text: '悬停显示', value: 'hover'} |


#### 特技

1.  使用 Readme\_XXX.md 来支持不同的语言，例如 Readme\_en.md, Readme\_zh.md
2.  Gitee 官方博客 [blog.gitee.com](https://blog.gitee.com)
3.  你可以 [https://gitee.com/explore](https://gitee.com/explore) 这个地址来了解 Gitee 上的优秀开源项目
4.  [GVP](https://gitee.com/gvp) 全称是 Gitee 最有价值开源项目，是综合评定出的优秀开源项目
5.  Gitee 官方提供的使用手册 [https://gitee.com/help](https://gitee.com/help)
6.  Gitee 封面人物是一档用来展示 Gitee 会员风采的栏目 [https://gitee.com/gitee-stars/](https://gitee.com/gitee-stars/)
