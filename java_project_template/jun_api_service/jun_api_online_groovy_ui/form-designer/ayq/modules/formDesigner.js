/**
 +------------------------------------------------------------------------------------+
 + ayq-layui-form-designer(layui表单设计器)
 +------------------------------------------------------------------------------------+
 + ayq-layui-form-designer v2.0.0
 * MIT License By http://116.62.237.101:8009/
 + 作者：谁家没一个小强
 + 官方：
 + 时间：2022-05-23
 +------------------------------------------------------------------------------------+
 + 版权声明：该版权完全归谁家没一个小强所有，可转载使用和学习，但请务必保留版权信息
 +------------------------------------------------------------------------------------+
 + 本项目是一个基于layui表单组件的表单设计器
 + 1.本项目基于Layui、Jquery、Sortable
 + 2.项目已经基本实现了拖动布局，父子布局
 + 3.项目实现了大部分基于Layui的Form表单控件布局，包括输入框、编辑器、下拉、单选、单选组、多选组、日期、滑块、评分、轮播、图片、颜色选择、图片上传、文件上传
 + 4.表单数据的获取与回显,禁用全表单
 +------------------------------------------------------------------------------------+
 */
layui.config({base: './ayq/modules/'}).define(["layer",'flow', "laytpl", "element", "form", "slider", "laydate", "rate", "colorpicker", "layedit", "carousel", "upload", "formField","staticField", "numberInput", "iconPicker", "cron", "labelGeneration"]
    , function (exports) {
        var $ = layui.jquery
            , layer = layui.layer
            , laytpl = layui.laytpl
            , setter = layui.cache
            , element = layui.element
            , slider = layui.slider
            , laydate = layui.laydate
            , rate = layui.rate
            , colorpicker = layui.colorpicker
            , carousel = layui.carousel
            , form = layui.form
            , upload = layui.upload
            , layedit = layui.layedit
            , flow = layui.flow
            , formField = layui.formField
            , staticField = layui.staticField
            , hint = layui.hint
            , numberInput = layui.numberInput
            , iconPicker = layui.iconPicker
            , cron = layui.cron
            , labelGeneration = layui.labelGeneration
            , files = []
            , images = []
            , iceEditorObjects = {}
            , labelGenerationObjects = {}
            , signObjects = {}
            //模块名称常量
            , MOD_NAME = 'formDesigner'
            //外部接口
            , formDesigner = {
                index: layui.formDesigner ? (layui.formDesigner.index + 10000) : 0
                //设置全局项
                , set: function (options) {
                    var that = this;
                    that.config = $.extend({}
                        , that.config
                        , options);
                    return that;
                }
                //事件监听
                , on: function (events
                , callback) {
                    return layui.onevent.call(this
                        , MOD_NAME
                        , events
                        , callback);
                }
            }
            , thisIns = function () {
                var that = this
                    , options = that.config;
                return {
                    reload: function (options) {
                        that.reload.call(that
                            , options);
                    }, getOptions: function () {
                        return options || null;
                    }, getData: function () {
                        return options.data || null;
                    }, geticeEditorObjects: function () {
                        return iceEditorObjects || null;
                    },getImages:function () {
                        return images || null;
                    },getFiles:function () {
                        return files || null;
                    },getFormData:function () {
                        return that.getFormData() || null;
                    },setFormData:function (json) {
                        return that.setFormData(json) || null;
                    },globalDisable:function () {
                        return that.globalDisable() || null;
                    },globalNoDisable:function () {
                        return that.globalNoDisable() || null;
                    },
                }
            }
            , getThisInsConfig = function (id) {
                var config = thisIns.config[id];
                if (!config) {
                    hint.error('在表实例中找不到ID选项');
                }
                return config || null;
            }
            , Class = function (options) {
                var that = this;
                that.index = ++formDesigner.index; //增加实例，index 也是要增加JSON.stringify(options.data, null, 4)
                that.config = $.extend({}
                    , that.config
                    , formDesigner.config
                    , options);
                that.render();
            };


        /* 组件定义 */
        Class.prototype.components = {
            input: {
                /**
                 * 根据json对象生成html对象
                 * @param {object} json 当前组件的json属性
                 * @param {boolean} selected 是否被选中
                 * @param {object} elem 表单面板jquery对象
                 * @param {object} that 实例对象
                 * */
                render: function (json,selected,elem,that) {
                    if (selected === undefined) {
                        selected = false;
                    }
                    var _selected = selected ? 'active' : '';
                    var _disabled = json.disabled ? 'disabled=""' : '';
                    var _disabledClass = json.disabled ? ' layui-disabled' : '';
                    var _readonly = json.readonly ? 'readonly=""' : '';
                    var _required = json.required ? 'required' : '';
                    var _hideLabel = json.hideLabel ? 'display: none;' : '';
                    if (json.expression !== null && json.expression !== undefined ) {
                        if (json.expression !== '') {
                            _required = _required + '|' + json.expression;
                        }
                    }
                    var _html = '<div id="{0}" class="layui-form-item {2}"  data-id="{0}" data-tag="{1}" data-index="{3}">'.format(json.id, json.tag, _selected, json.index);
                    _html += '<label class="layui-form-label {0}" style="width: {3}px;{4}"><span style="color:red;">{2}</span>{1}:</label>'.format(json.required ? 'layui-form-required' : '', json.label,json.required ? '*' : '',json.labelWidth,_hideLabel);
                    if (json.hideLabel) {
                        _html += '<div class="layui-input-block" style="margin-left: 0px">';
                    } else {
                        _html += '<div class="layui-input-block" style="margin-left: {0}px">'.format(json.labelWidth);
                    }
                    _html += '<input name="{0}" value="{1}" placeholder="{3}" class="layui-input{7}" lay-vertype="tips" lay-verify="{6}" {4} {5} style="width:{2}">'
                        .format(json.id, json.defaultValue ? json.defaultValue : '', json.width, json.placeholder, _readonly, _disabled, _required, _disabledClass);
                    _html += '</div>';
                    _html += '</div>';
                    elem.append(_html);
                },
                /**
                 * 根据json对象更新html对象
                 * @param {object} json 变更后的json属性
                 * @param {object} that 实例对象
                 * */
                update: function (json,that) {
                    var _disabled = json.disabled ? 'disabled=""' : '';
                    var _readonly = json.readonly ? 'readonly=""' : '';
                    var _required = json.required ? 'required' : '';
                    var _disabledClass = json.disabled ? ' layui-disabled' : '';
                    if (json.expression !== null && json.expression !== undefined ) {
                        if (json.expression !== '') {
                            _required = 'required' + '|' + json.expression;
                        }
                    }
                    var $block = $('#' + json.id + ' .layui-input-block');
                    var $label = $('#' + json.id + ' .layui-form-label');
                    $block.empty();
                    $label.empty();
                    $label.css("width",json.labelWidth);
                    if (json.required) {
                        $label.append('<span style="color:red;">*</span>');
                    }
                    $label.append(json.label + ":");
                    if (json.hideLabel) {
                        $label.css("display","none");
                        $block.css("margin-left","0px");
                    } else {
                        $label.css("display","block");
                        $block.css("margin-left",json.labelWidth);
                    }
                    var _html = '';
                    //重绘设计区改id下的所有元素
                    _html += '<input name="{0}" value="{1}" placeholder="{3}" class="layui-input{7}" lay-vertype="tips" lay-verify="{6}" {4} {5} style="width:{2}">'
                        .format(json.id, json.defaultValue ? json.defaultValue : '', json.width, json.placeholder, _readonly, _disabled, _required, _disabledClass);
                    $block.append(_html);
                },
                /**
                 * 根据components组件对象获取组件属性
                 * @param {object} id 所属组件id
                 * @param {object} index 所属对象组件索引
                 * @param {object} that 实例对象
                 * */
                jsonData: function (id, index,that) {
                    var _json = JSON.parse(JSON.stringify(formField.components.input));
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
                    var _selected = selected ? 'active' : '';
                    var _disabled = json.disabled ? 'disabled=""' : '';
                    var _disabledClass = json.disabled ? ' layui-disabled' : '';
                    var _readonly = json.readonly ? 'readonly=""' : '';
                    var _required = json.required ? 'required' : '';
                    var _hideLabel = json.hideLabel ? 'display: none;' : '';
                    if (json.expression !== null && json.expression !== undefined ) {
                        if (json.expression !== '') {
                            _required = _required + '|' + json.expression;
                        }
                    }
                    var _html = '<div id="{0}" class="layui-form-item {2}"  data-id="{0}" data-tag="{1}" data-index="{3}">'.format(json.id, json.tag, _selected,json.index);
                    _html += '<label class="layui-form-label {0}" style="width: {3}px;{4}"><span style="color:red;">{2}</span>{1}:</label>'.format(json.required ? 'layui-form-required' : '', json.label,json.required ? '*' : '',json.labelWidth,_hideLabel);
                    if (json.hideLabel) {
                        _html += '<div class="layui-input-block" style="margin-left: 0px">';
                    } else {
                        _html += '<div class="layui-input-block" style="margin-left: {0}px">'.format(json.labelWidth);
                    }
                    _html += '<input name="{0}" value="{1}" placeholder="{3}" class="layui-input{7}" lay-vertype="tips" lay-verify="{6}" {4} {5} style="width:{2}">'
                        .format(json.id, json.defaultValue ? json.defaultValue : '', json.width, json.placeholder, _readonly, _disabled, _required, _disabledClass);
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
                    return '';
                }
            },
            blockquote: {
                /**
                 * 根据json对象生成html对象
                 * @param {object} json 当前组件的json属性
                 * @param {boolean} selected 是否被选中
                 * @param {object} elem 表单面板jquery对象
                 * @param {object} that 实例对象
                 * */
                render: function (json,selected,elem,that) {
                    if (selected === undefined) {
                        selected = false;
                    }
                    var _selected = selected ? 'active' : '';
                    var _html = '<div id="{0}" class="layui-form-item {2}"  data-id="{0}" data-tag="{1}" data-index="{3}">'.format(json.id, json.tag, _selected, json.index);
                    _html += '<blockquote id="{0}" class="layui-elem-quote" style="width:calc({2} - 35px);border-left: 5px solid {3};">{1}</blockquote>'
                        .format(json.id +json.tag, json.defaultValue ? json.defaultValue : '', json.width,json.colorSelection);
                    _html += '</div>';
                    elem.append(_html);
                },
                /**
                 * 根据json对象更新html对象
                 * @param {object} json 变更后的json属性
                 * @param {object} that 实例对象
                 * */
                update: function (json,that) {
                    var $block = $('#' + json.id +json.tag);
                    $block.css("width","calc({0} - 35px)".format(json.width));
                    $block.css("border-left","5px solid {0}".format(json.colorSelection));
                    $block.html(json.defaultValue);
                },
                /**
                 * 根据components组件对象获取组件属性
                 * @param {object} id 所属组件id
                 * @param {object} index 所属对象组件索引
                 * @param {object} that 实例对象
                 * */
                jsonData: function (id, index,that) {
                    var _json = JSON.parse(JSON.stringify(formField.components.blockquote));
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
                    var _selected = selected ? 'active' : '';
                    var _html = '<div id="{0}" class="layui-form-item {2}"  data-id="{0}" data-tag="{1}" data-index="{3}">'.format(json.id, json.tag, _selected, json.index);
                    _html += '<blockquote id="{0}" class="layui-elem-quote" style="width:calc({2} - 35px);border-left: 5px solid {3};">{1}</blockquote>'
                        .format(json.id +json.tag, json.defaultValue ? json.defaultValue : '', json.width,json.colorSelection);
                    _html += '</div>';
                    return _html;
                },
                /**
                 * 根据json对象生成js文本
                 * @param {object} json 变更后的json属性
                 * @param {object} that 实例对象
                 * */
                generateScript:function (json,that) {
                    return '';
                }
            },
            spacing: {
                /**
                 * 根据json对象生成html对象
                 * @param {object} json 当前组件的json属性
                 * @param {boolean} selected 是否被选中
                 * @param {object} elem 表单面板jquery对象
                 * @param {object} that 实例对象
                 * */
                render: function (json,selected,elem,that) {
                    if (selected === undefined) {
                        selected = false;
                    }
                    var _selected = selected ? 'active' : '';
                    var _html = '<div id="{0}" class="layui-form-item {2}"  data-id="{0}" data-tag="{1}" data-index="{3}">'.format(json.id, json.tag, _selected, json.index);
                    _html += '<div id="{0}" style="height:{1}px;"></div>'
                        .format(json.id +json.tag,json.whiteSpace);
                    _html += '</div>';
                    elem.append(_html);
                },
                /**
                 * 根据json对象更新html对象
                 * @param {object} json 变更后的json属性
                 * @param {object} that 实例对象
                 * */
                update: function (json,that) {
                    var $block = $('#' + json.id +json.tag);
                    $block.css("height","{0}px".format(json.whiteSpace));
                },
                /**
                 * 根据components组件对象获取组件属性
                 * @param {object} id 所属组件id
                 * @param {object} index 所属对象组件索引
                 * @param {object} that 实例对象
                 * */
                jsonData: function (id, index,that) {
                    var _json = JSON.parse(JSON.stringify(formField.components.spacing));
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
                    var _selected = selected ? 'active' : '';
                    var _html = '<div id="{0}" class="layui-form-item {2}"  data-id="{0}" data-tag="{1}" data-index="{3}">'.format(json.id, json.tag, _selected, json.index);
                    _html += '<div id="{0}" style="height:{1}px;"></div>'
                        .format(json.id +json.tag,json.whiteSpace);
                    _html += '</div>';
                    return _html;
                },
                /**
                 * 根据json对象生成js文本
                 * @param {object} json 变更后的json属性
                 * @param {object} that 实例对象
                 * */
                generateScript:function (json,that) {
                    return '';
                }
            },
            line: {
                /**
                 * 根据json对象生成html对象
                 * @param {object} json 当前组件的json属性
                 * @param {boolean} selected 是否被选中
                 * @param {object} elem 表单面板jquery对象
                 * @param {object} that 实例对象
                 * */
                render: function (json,selected,elem,that) {
                    if (selected === undefined) {
                        selected = false;
                    }
                    var _selected = selected ? 'active' : '';
                    var _html = '<div id="{0}" class="layui-form-item {2}"  data-id="{0}" data-tag="{1}" data-index="{3}">'.format(json.id, json.tag, _selected, json.index);
                    _html += '<fieldset id="{0}" class="layui-elem-field layui-field-title" style="border-color: {1};width: {2}">'.format(json.id +json.tag,json.colorSelection,json.width);
                    if (json.defaultValue) {
                        _html += '<legend>{0}</legend>'.format(json.defaultValue);
                    }
                    _html += '</fieldset>';
                    elem.append(_html);
                },
                /**
                 * 根据json对象更新html对象
                 * @param {object} json 变更后的json属性
                 * @param {object} that 实例对象
                 * */
                update: function (json,that) {
                    var $block = $('#' + json.id +json.tag);
                    $block.css("width",json.width);
                    $block.css("border-color",json.colorSelection);
                    $block.empty();
                    if (json.defaultValue) {
                        $block.append('<legend>{0}</legend>'.format(json.defaultValue));
                    }
                },
                /**
                 * 根据components组件对象获取组件属性
                 * @param {object} id 所属组件id
                 * @param {object} index 所属对象组件索引
                 * @param {object} that 实例对象
                 * */
                jsonData: function (id, index,that) {
                    var _json = JSON.parse(JSON.stringify(formField.components.line));
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
                    var _selected = selected ? 'active' : '';
                    var _html = '<div id="{0}" class="layui-form-item {2}"  data-id="{0}" data-tag="{1}" data-index="{3}">'.format(json.id, json.tag, _selected, json.index);
                    _html += '<fieldset id="{0}" class="layui-elem-field layui-field-title" style="border-color: {1};width: {2}">'.format(json.id +json.tag,json.colorSelection,json.width);
                    if (json.defaultValue) {
                        _html += '<legend>{0}</legend>'.format(json.defaultValue);
                    }
                    _html += '</fieldset>';
                    return _html;
                },
                /**
                 * 根据json对象生成js文本
                 * @param {object} json 变更后的json属性
                 * @param {object} that 实例对象
                 * */
                generateScript:function (json,that) {
                    return '';
                }
            },
            password: {
                /**
                 * 根据json对象生成html对象
                 * @param {object} json 当前组件的json属性
                 * @param {boolean} selected 是否被选中
                 * @param {object} elem 表单面板jquery对象
                 * @param {object} that 实例对象
                 * */
                render: function (json,selected,elem,that) {
                    if (selected === undefined) {
                        selected = false;
                    }
                    var _disabled = json.disabled ? 'disabled=""' : '';
                    var _readonly = json.readonly ? 'readonly=""' : '';
                    var _required = json.required ? 'lay-verify="required"' : '';
                    var _disabledClass = json.disabled ? ' layui-disabled' : '';
                    var _hideLabel = json.hideLabel ? 'display: none;' : '';
                    var _html = '<div id="{0}" class="layui-form-item {2}"  data-id="{0}" data-tag="{1}" data-index="{3}">'.format(json.id, json.tag, selected ? 'active' : '', json.index);
                    _html += '<label class="layui-form-label {0}" style="width: {3}px;{4}"><span style="color:red;">{2}</span>{1}:</label>'.format(json.required ? 'layui-form-required' : '', json.label,json.required ? '*' : '',json.labelWidth,_hideLabel);
                    if (json.hideLabel) {
                        _html += '<div class="layui-input-block" style="margin-left: 0px">';
                    } else {
                        _html += '<div class="layui-input-block" style="margin-left: {0}px">'.format(json.labelWidth);
                    }
                    _html += '<input type="password" name="{0}" placeholder="{3}" value="{1}" autocomplete="off" style="width:{2}" {4}  {5} {6} class="layui-input{7}">'
                        .format(json.id, json.defaultValue ? json.defaultValue : '', json.width, json.placeholder, _readonly, _disabled, _required, _disabledClass);
                    _html += '</div>';
                    _html += '</div>';
                    elem.append(_html);
                },
                /**
                 * 根据json对象更新html对象
                 * @param {object} json 变更后的json属性
                 * @param {object} that 实例对象
                 * */
                update: function (json,that) {
                    var _disabled = json.disabled ? 'disabled=""' : '';
                    var _readonly = json.readonly ? 'readonly=""' : '';
                    var _required = json.required ? 'lay-verify="required"' : '';
                    var _disabledClass = json.disabled ? ' layui-disabled' : '';
                    var $block = $('#' + json.id + ' .layui-input-block');
                    var $label = $('#' + json.id + ' .layui-form-label');
                    $block.empty();
                    $label.empty();
                    $label.css("width",json.labelWidth);
                    if (json.required) {
                        $label.append('<span style="color:red;">*</span>');
                    }
                    $label.append(json.label + ":");
                    if (json.hideLabel) {
                        $label.css("display","none");
                        $block.css("margin-left","0px");
                    } else {
                        $label.css("display","block");
                        $block.css("margin-left",json.labelWidth);
                    }
                    var _html = '';
                    //重绘设计区改id下的所有元素
                    _html += '<input type="password" name="{0}" placeholder="{3}" value="{1}" autocomplete="off" style="width:{2}" {4} {5} {6} class="layui-input{7}">'
                        .format(json.id, json.defaultValue ? json.defaultValue : '', json.width, json.placeholder, _readonly, _disabled, _required, _disabledClass);
                    $block.append(_html);
                },
                /**
                 * 根据components组件对象获取组件属性
                 * @param {object} id 所属组件id
                 * @param {object} index 所属对象组件索引
                 * @param {object} that 实例对象
                 * */
                jsonData: function (id, index, that) {
                    //分配一个新的ID
                    var _json = JSON.parse(JSON.stringify(formField.components.password));
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
                    var _disabled = json.disabled ? 'disabled=""' : '';
                    var _readonly = json.readonly ? 'readonly=""' : '';
                    var _required = json.required ? 'lay-verify="required"' : '';
                    var _disabledClass = json.disabled ? ' layui-disabled' : '';
                    var _hideLabel = json.hideLabel ? 'display: none;' : '';
                    var _html = '<div id="{0}" class="layui-form-item {2}"  data-id="{0}" data-tag="{1}" data-index="{3}">'.format(json.id, json.tag, selected ? 'active' : '', json.index);
                    _html += '<label class="layui-form-label {0}" style="width: {3}px;{4}"><span style="color:red;">{2}</span>{1}:</label>'.format(json.required ? 'layui-form-required' : '', json.label,json.required ? '*' : '',json.labelWidth,_hideLabel);
                    if (json.hideLabel) {
                        _html += '<div class="layui-input-block" style="margin-left: 0px">';
                    } else {
                        _html += '<div class="layui-input-block" style="margin-left: {0}px">'.format(json.labelWidth);
                    }
                    _html += '<input type="password" name="{0}" placeholder="{3}" value="{1}" autocomplete="off" style="width:{2}" {4}  {5} {6} class="layui-input{7}">'
                        .format(json.id, json.defaultValue ? json.defaultValue : '', json.width, json.placeholder, _readonly, _disabled, _required, _disabledClass);
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
                    return '';
                }
            },
            textarea: {
                /**
                 * 根据json对象生成html对象
                 * @param {object} json 当前组件的json属性
                 * @param {boolean} selected 是否被选中
                 * @param {object} elem 表单面板jquery对象
                 * @param {object} that 实例对象
                 * */
                render: function (json,selected,elem,that) {
                    if (selected === undefined) {
                        selected = false;
                    }
                    var _disabled = json.disabled ? 'disabled=""' : '';
                    var _required = json.required ? 'lay-verify="required"' : '';
                    var _readonly = json.readonly ? 'readonly=""' : '';
                    var _disabledClass = json.disabled ? ' layui-disabled' : '';
                    var _hideLabel = json.hideLabel ? 'display: none;' : '';
                    var _html = '<div id="{0}" class="layui-form-item layui-form-text {2}"  data-id="{0}" data-tag="{1}" data-index="{3}">'.format(json.id, json.tag, selected ? 'active' : '', json.index);
                    _html += '<label class="layui-form-label {0}" style="width: {3};{4}"><span style="color:red;">{2}</span>{1}:</label>'.format(json.required ? 'layui-form-required' : '', json.label,json.required ? '*' : '',json.width,_hideLabel);
                    _html += '<div class="layui-input-block"  style="width: {0}">'.format(json.width);
                    _html += '<textarea name="{0}" placeholder="{3}" width="{2}" class="layui-textarea{6}" {4} {5} {7}>{1}</textarea>'
                        .format(json.id, json.defaultValue ? json.defaultValue : '', json.width, json.placeholder, _disabled, _required, _disabledClass, _readonly);
                    _html += '</div>';
                    _html += '</div>';
                    elem.append(_html);
                },
                /**
                 * 根据json对象更新html对象
                 * @param {object} json 变更后的json属性
                 * @param {object} that 实例对象
                 * */
                update: function (json,that) {
                    var _disabled = json.disabled ? 'disabled=""' : '';
                    var _required = json.required ? 'lay-verify="required"' : '';
                    var _disabledClass = json.disabled ? ' layui-disabled' : '';
                    var _readonly = json.readonly ? 'readonly=""' : '';
                    var $block = $('#' + json.id + ' .layui-input-block');
                    var $label = $('#' + json.id + ' .layui-form-label');
                    $block.empty();
                    $label.empty();
                    if (json.hideLabel) {
                        $label.css("display","none");
                    } else {
                        $label.css("display","block");
                    }
                    var _html = '';
                    _html += '<textarea name="{0}" placeholder="{3}" width="{2}" class="layui-textarea{6}" {4} {5} {7}>{1}</textarea>'
                        .format(json.id, json.defaultValue ? json.defaultValue : '', json.width, json.placeholder, _disabled, _required, _disabledClass, _readonly);
                    $('#' + json.id + ' .layui-input-block').append(_html);
                    $label.css({width: '{0}'.format(json.width)});
                    $block.css({width: '{0}'.format(json.width)});
                    if (json.required) {
                        $label.append('<span style="color:red;">*</span>');
                    }
                    $label.append(json.label + ":");
                },
                /**
                 * 根据components组件对象获取组件属性
                 * @param {object} id 所属组件id
                 * @param {object} index 所属对象组件索引
                 * @param {object} that 实例对象
                 * */
                jsonData: function (id, index, that) {
                    //分配一个新的ID
                    var _json = JSON.parse(JSON.stringify(formField.components.textarea));
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
                    var _disabled = json.disabled ? 'disabled=""' : '';
                    var _required = json.required ? 'lay-verify="required"' : '';
                    var _readonly = json.readonly ? 'readonly=""' : '';
                    var _disabledClass = json.disabled ? ' layui-disabled' : '';
                    var _hideLabel = json.hideLabel ? 'display: none;' : '';
                    var _html = '<div id="{0}" class="layui-form-item layui-form-text {2}"  data-id="{0}" data-tag="{1}" data-index="{3}">'.format(json.id, json.tag, selected ? 'active' : '', json.index);
                    _html += '<label class="layui-form-label {0}" style="width: {3};{4}"><span style="color:red;">{2}</span>{1}:</label>'.format(json.required ? 'layui-form-required' : '', json.label,json.required ? '*' : '',json.width,_hideLabel);
                    _html += '<div class="layui-input-block"  style="width: {0}">'.format(json.width);
                    _html += '<textarea name="{0}" placeholder="{3}" width="{2}" class="layui-textarea{6}" {4} {5} {7}>{1}</textarea>'
                        .format(json.id, json.defaultValue ? json.defaultValue : '', json.width, json.placeholder, _disabled, _required, _disabledClass, _readonly);
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
                    return '';
                }
            },
            select: {
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
                    var _disabled = json.disabled ? 'disabled=""' : '';
                    var _required = json.required ? 'required' : '';
                    var _hideLabel = json.hideLabel ? 'display: none;' : '';
                    var _html = '<div id="{0}" class="layui-form-item {2}"  data-id="{0}" data-tag="{1}" data-index="{3}">'.format(json.id, json.tag, selected ? 'active' : '', json.index);
                    _html += '<label class="layui-form-label {0}" style="width: {3}px;{4}"><span style="color:red;">{2}</span>{1}:</label>'.format(json.required ? 'layui-form-required' : '', json.label,json.required ? '*' : '',json.labelWidth,_hideLabel);
                    if (json.hideLabel) {
                        _html += '<div class="layui-input-block layui-form" lay-filter="{0}" style="margin-left: 0px">'.format(json.id);
                    } else {
                        _html += '<div class="layui-input-block layui-form" lay-filter="{0}" style="margin-left: {1}px">'.format(json.id,json.labelWidth);
                    }
                    _html += '<select name="{0}" lay-verify="{2}" {1} >'.format(json.id, _disabled,_required);
                    /*if (json.defaultValue === undefined) {
                        _html += '<option value="{0}" selected="">{1}</option>'.format('', '请选择');
                    }*/
                    for (var i = 0; i < json.options.length; i++) {
                        if (json.options[i].checked) {
                            _html += '<option value="{0}" selected="">{1}</option>'.format(json.options[i].value, json.options[i].text);
                        } else {
                            _html += '<option value="{0}">{1}</option>'.format(json.options[i].value, json.options[i].text);
                        }
                    }
                    _html += '</select>'
                    _html += '</div>';
                    _html += '</div>';
                    elem.append(_html);
                },
                /**
                 * 根据json对象更新html对象
                 * @param {object} json 变更后的json属性
                 * @param {object} that 实例对象
                 * */
                update: function (json,that) {
                    var _disabled = json.disabled ? 'disabled=""' : '';
                    var _required = json.required ? 'required' : '';
                    var $block = $('#' + json.id + ' .layui-input-block');
                    var $label = $('#' + json.id + ' .layui-form-label');
                    $block.empty();
                    $label.empty();
                    var _html = '';
                    _html += '<select name="{0}" lay-verify="{2}" {1}>'.format(json.id, _disabled,_required);
                    //重绘设计区改id下的所有元素
                    /* if (json.defaultValue === undefined) {
                         _html += '<option value="{0}" selected="">{1}</option>'.format('', '请选择');
                     }*/
                    for (var i = 0; i < json.options.length; i++) {
                        if (json.options[i].checked) {
                            _html += '<option value="{0}" selected="">{1}</option>'.format(json.options[i].value, json.options[i].text);
                        } else {
                            _html += '<option value="{0}">{1}</option>'.format(json.options[i].value, json.options[i].text);
                        }
                    }
                    _html += '</select>'
                    $('#' + json.id + ' .layui-input-block').append(_html);
                    $label.css("width",json.labelWidth);
                    if (json.required) {
                        $label.append('<span style="color:red;">*</span>');
                    }
                    $label.append(json.label + ":");
                    form.render('select',json.id);
                    $('#' + json.id + ' .layui-input-block div.layui-unselect.layui-form-select').css({width: '{0}'.format(json.width)});
                    if (json.hideLabel) {
                        $label.css("display","none");
                        $block.css("margin-left","0px");
                        $block.css({width: 'calc({0})'.format(json.width)});
                    } else {
                        $label.css("display","block");
                        $block.css("margin-left",json.labelWidth);
                        $block.css({width: 'calc({0} - {1}px)'.format(json.width,json.labelWidth)});
                    }
                },
                /**
                 * 根据components组件对象获取组件属性
                 * @param {object} id 所属组件id
                 * @param {object} index 所属对象组件索引
                 * @param {object} that 实例对象
                 * */
                jsonData: function (id, index, that) {
                    //分配一个新的ID
                    var _json = JSON.parse(JSON.stringify(formField.components.select));
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
                    var _disabled = json.disabled ? 'disabled=""' : '';
                    var _required = json.required ? 'required' : '';
                    var _hideLabel = json.hideLabel ? 'display: none;' : '';
                    var _html = '<div id="{0}" class="layui-form-item {2}"  data-id="{0}" data-tag="{1}" data-index="{3}">'.format(json.id, json.tag, selected ? 'active' : '', json.index);
                    _html += '<label class="layui-form-label {0}" style="width: {3}px;"><span style="color:red;">{2}</span>{1}:</label>'.format(json.required ? 'layui-form-required' : '', json.label,json.required ? '*' : '',json.labelWidth,_hideLabel);
                    if (json.hideLabel) {
                        _html += '<div class="layui-input-block layui-form" lay-filter="{0}" style="margin-left: 0px">'.format(json.id);
                    } else {
                        _html += '<div class="layui-input-block layui-form" lay-filter="{0}" style="margin-left: {1}px">'.format(json.id,json.labelWidth);
                    }
                    _html += '<select name="{0}" lay-verify="{2}" {1} >'.format(json.id, _disabled,_required);
                    /*if (json.defaultValue === undefined) {
                        _html += '<option value="{0}" selected="">{1}</option>'.format('', '请选择');
                    }*/
                    for (var i = 0; i < json.options.length; i++) {
                        if (json.options[i].checked) {
                            _html += '<option value="{0}" selected="">{1}</option>'.format(json.options[i].value, json.options[i].text);
                        } else {
                            _html += '<option value="{0}">{1}</option>'.format(json.options[i].value, json.options[i].text);
                        }
                    }
                    _html += '</select>'
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
                    return '';
                }
            },
            radio: {
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
                    var _disabled = json.disabled ? 'disabled=""' : '';
                    var _hideLabel = json.hideLabel ? 'display: none;' : '';
                    var _html = '<div id="{0}" class="layui-form-item {2}"  data-id="{0}" data-tag="{1}" data-index="{3}">'.format(json.id, json.tag, selected ? 'active' : '', json.index);
                    _html += '<label class="layui-form-label" style="width: {1}px;{2}">{0}:</label>'.format(json.label,json.labelWidth,_hideLabel);
                    if (json.hideLabel) {
                        _html += '<div class="layui-input-block" style="margin-left: 0px">';
                    } else {
                        _html += '<div class="layui-input-block" style="margin-left: {0}px">'.format(json.labelWidth);
                    }
                    for (var i = 0; i < json.options.length; i++) {
                        if (json.options[i].checked) {
                            _html += '<input type="radio" name="{0}" value="{1}" title="{2}" checked="" {3}>'.format(json.id, json.options[i].value, json.options[i].text, _disabled);
                        } else {
                            _html += '<input type="radio" name="{0}" value="{1}" title="{2}" {3}>'.format(json.id, json.options[i].value, json.options[i].text, _disabled);
                        }
                    }
                    _html += '</div>';
                    _html += '</div>';
                    elem.append(_html);
                },
                /**
                 * 根据json对象更新html对象
                 * @param {object} json 变更后的json属性
                 * @param {object} that 实例对象
                 * */
                update: function (json,that) {
                    var _disabled = json.disabled ? 'disabled=""' : '';
                    var $block = $('#' + json.id + ' .layui-input-block');
                    var $label = $('#' + json.id + ' .layui-form-label');
                    $block.empty();
                    $label.empty();
                    $label.css("width",json.labelWidth);
                    $label.append(json.label + ":");
                    if (json.hideLabel) {
                        $label.css("display","none");
                        $block.css("margin-left","0px");
                        $block.css({width: 'calc({0} - {1}px)'.format(json.width,json.labelWidth)});
                    } else {
                        $label.css("display","block");
                        $block.css("margin-left",json.labelWidth);
                        $block.css({width: 'calc({0} - {1}px)'.format(json.width,json.labelWidth)});
                    }
                    var _html = '';
                    //重绘设计区改id下的所有元素
                    for (var i = 0; i < json.options.length; i++) {
                        if (json.options[i].checked) {
                            _html += '<input type="radio" name="{0}" value="{1}" title="{2}" checked="" {3}>'.format(json.id, json.options[i].value, json.options[i].text, _disabled);
                        } else {
                            _html += '<input type="radio" name="{0}" value="{1}" title="{2}" {3}>'.format(json.id, json.options[i].value, json.options[i].text, _disabled);
                        }
                    }
                    $block.append(_html);
                    form.render('radio');
                },
                /**
                 * 根据components组件对象获取组件属性
                 * @param {object} id 所属组件id
                 * @param {object} index 所属对象组件索引
                 * @param {object} that 实例对象
                 * */
                jsonData: function (id, index, that) {
                    //分配一个新的ID
                    var _json = JSON.parse(JSON.stringify(formField.components.radio));
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
                    var _disabled = json.disabled ? 'disabled=""' : '';
                    var _hideLabel = json.hideLabel ? 'display: none;' : '';
                    var _html = '<div id="{0}" class="layui-form-item {2}"  data-id="{0}" data-tag="{1}" data-index="{3}">'.format(json.id, json.tag, selected ? 'active' : '', json.index);
                    _html += '<label class="layui-form-label" style="width: {1}px;{2}">{0}:</label>'.format(json.label,json.labelWidth,_hideLabel);
                    if (json.hideLabel) {
                        _html += '<div class="layui-input-block" style="margin-left: 0px">';
                    } else {
                        _html += '<div class="layui-input-block" style="margin-left: {0}px">'.format(json.labelWidth);
                    }
                    for (var i = 0; i < json.options.length; i++) {
                        if (json.options[i].checked) {
                            _html += '<input type="radio" name="{0}" value="{1}" title="{2}" checked="" {3}>'.format(json.id, json.options[i].value, json.options[i].text, _disabled);
                        } else {
                            _html += '<input type="radio" name="{0}" value="{1}" title="{2}" {3}>'.format(json.id, json.options[i].value, json.options[i].text, _disabled);
                        }
                    }
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
                    return '';
                }
            },
            checkbox: {
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
                    var _disabled = json.disabled ? 'disabled=""' : '';
                    var _required = json.required ? 'lay-verify="otherReq"' : '';
                    var _hideLabel = json.hideLabel ? 'display: none;' : '';
                    var _html = '<div id="{0}" class="layui-form-item {2}"  data-id="{0}" data-tag="{1}" data-index="{3}">'.format(json.id, json.tag, selected ? 'active' : '', json.index);
                    _html += '<label class="layui-form-label {0}" style="width: {3}px;{4}"><span style="color:red;">{2}</span>{1}:</label>'.format(json.required ? 'layui-form-required' : '', json.label,json.required ? '*' : '',json.labelWidth,_hideLabel);
                    if (json.hideLabel) {
                        _html += '<div class="layui-input-block" style="margin-left: 0px;">';
                    } else {
                        _html += '<div class="layui-input-block" style="margin-left: {0}px;">'.format(json.labelWidth);
                    }
                    for (var i = 0; i < json.options.length; i++) {
                        if (json.options[i].checked) {
                            _html += '<input type="checkbox" name="{0}[{1}]" title="{2}" checked="" {3} {4}>'.format(json.id, json.options[i].value, json.options[i].text, _disabled,_required);
                        } else {
                            _html += '<input type="checkbox" name="{0}[{1}]" title="{2}" {3} {4}>'.format(json.id, json.options[i].value, json.options[i].text, _disabled,_required);
                        }
                    }
                    _html += '</div>';
                    _html += '</div>';
                    elem.append(_html);
                },
                /**
                 * 根据json对象更新html对象
                 * @param {object} json 变更后的json属性
                 * @param {object} that 实例对象
                 * */
                update: function (json,that) {
                    var _disabled = json.disabled ? 'disabled=""' : '';
                    var _required = json.required ? 'lay-verify="otherReq"' : '';
                    var $block = $('#' + json.id + ' .layui-input-block');
                    var $label = $('#' + json.id + ' .layui-form-label');
                    $block.empty();
                    $label.empty();
                    var _html = '';
                    //重绘设计区改id下的所有元素
                    for (var i = 0; i < json.options.length; i++) {
                        if (json.options[i].checked) {
                            _html += '<input type="checkbox" name="{0}[{1}]" title="{2}" checked="" {3} {4}>'.format(json.id, json.options[i].value, json.options[i].text, _disabled,_required);
                        } else {
                            _html += '<input type="checkbox" name="{0}[{1}]" title="{2}" {3} {4}>'.format(json.id, json.options[i].value, json.options[i].text, _disabled,_required);
                        }
                    }
                    $block.append(_html);
                    $label.css("width",json.labelWidth);
                    if (json.hideLabel) {
                        $label.css("display","none");
                        $block.css("margin-left","0px");
                    } else {
                        $label.css("display","block");
                        $block.css("margin-left",json.labelWidth);
                    }
                    if (json.required) {
                        $label.append('<span style="color:red;">*</span>');
                    }
                    $label.append(json.label + ":");
                    form.render('checkbox');
                },
                /**
                 * 根据components组件对象获取组件属性
                 * @param {object} id 所属组件id
                 * @param {object} index 所属对象组件索引
                 * @param {object} that 实例对象
                 * */
                jsonData: function (id, index, that) {
                    //分配一个新的ID
                    var _json = JSON.parse(JSON.stringify(formField.components.checkbox));
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
                    var _disabled = json.disabled ? 'disabled=""' : '';
                    var _required = json.required ? 'lay-verify="otherReq"' : '';
                    var _hideLabel = json.hideLabel ? 'display: none;' : '';
                    var _html = '<div id="{0}" class="layui-form-item {2}"  data-id="{0}" data-tag="{1}" data-index="{3}">'.format(json.id, json.tag, selected ? 'active' : '', json.index);
                    _html += '<label class="layui-form-label {0}" style="width: {3}px;{4}"><span style="color:red;">{2}</span>{1}:</label>'.format(json.required ? 'layui-form-required' : '', json.label,json.required ? '*' : '',json.labelWidth,_hideLabel);
                    if (json.hideLabel) {
                        _html += '<div class="layui-input-block" style="margin-left: 0px;">';
                    } else {
                        _html += '<div class="layui-input-block" style="margin-left: {0}px;">'.format(json.labelWidth);
                    }
                    for (var i = 0; i < json.options.length; i++) {
                        if (json.options[i].checked) {
                            _html += '<input type="checkbox" name="{0}[{1}]" title="{2}" checked="" {3} {4}>'.format(json.id, json.options[i].value, json.options[i].text, _disabled,_required);
                        } else {
                            _html += '<input type="checkbox" name="{0}[{1}]" title="{2}" {3} {4}>'.format(json.id, json.options[i].value, json.options[i].text, _disabled,_required);
                        }
                    }
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
                    return '';
                }
            },
            switch: {
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
                    var _disabled = json.disabled ? 'disabled=""' : '';
                    var _disabledClass = json.disabled ? ' layui-disabled' : '';
                    var _hideLabel = json.hideLabel ? 'display: none;' : '';
                    var _html = '<div id="{0}" class="layui-form-item {2}"  data-id="{0}" data-tag="{1}" data-index="{3}">'.format(json.id, json.tag, selected ? 'active' : '', json.index);
                    _html += '<label class="layui-form-label" style="width: {1}px;{2}">{0}:</label>'.format(json.label,json.labelWidth,_hideLabel);
                    if (json.hideLabel) {
                        _html += '<div class="layui-input-block" style="width:calc({0});border: 0px solid #d2d2d2;margin-left: 0px">'.format(json.width,json.labelWidth);
                    } else {
                        _html += '<div class="layui-input-block" style="width:calc({0} - {1}px);border: 1px solid #d2d2d2;border-left: 0px;margin-left: {1}px">'.format(json.width,json.labelWidth);
                    }
                    _html += '<input type="checkbox" name="{0}" lay-skin="switch" lay-text="ON|OFF" {1} class="{2}" {3}>'.format(json.id, _disabled, _disabledClass, json.switchValue ? 'checked' : '');
                    _html += '</div>';
                    _html += '</div>';
                    elem.append(_html);
                },
                /**
                 * 根据json对象更新html对象
                 * @param {object} json 变更后的json属性
                 * @param {object} that 实例对象
                 * */
                update: function (json,that) {
                    var _disabled = json.disabled ? 'disabled=""' : '';
                    var _disabledClass = json.disabled ? ' layui-disabled' : '';
                    var $block = $('#' + json.id + ' .layui-input-block');
                    var $label = $('#' + json.id + ' .layui-form-label');
                    $block.empty();
                    $label.empty();
                    var _html = '';
                    _html += '<input type="checkbox" name="{0}" lay-skin="switch" lay-text="ON|OFF" {1} class="{2}" {3}>'.format(json.tag, _disabled, _disabledClass, json.switchValue ? 'checked' : '');
                    $block.append(_html);
                    $label.append(json.label + ":");
                    if (json.hideLabel) {
                        $label.css("display","none");
                        $block.css("margin-left","0px");
                        $block.css("border","0px");
                        $block.css({width: 'calc({0})'.format(json.width)});
                    } else {
                        $label.css("display","block");
                        $block.css("margin-left",json.labelWidth);
                        $block.css({width: 'calc({0} - {1}px)'.format(json.width,json.labelWidth)});
                    }
                    $label.css("width",json.labelWidth);
                    form.render('checkbox');
                },
                /**
                 * 根据components组件对象获取组件属性
                 * @param {object} id 所属组件id
                 * @param {object} index 所属对象组件索引
                 * @param {object} that 实例对象
                 * */
                jsonData: function (id, index, that) {
                    var _json = JSON.parse(JSON.stringify(formField.components.switch));
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
                    var _disabled = json.disabled ? 'disabled=""' : '';
                    var _disabledClass = json.disabled ? ' layui-disabled' : '';
                    var _hideLabel = json.hideLabel ? 'display: none;' : '';
                    var _html = '<div id="{0}" class="layui-form-item {2}"  data-id="{0}" data-tag="{1}" data-index="{3}">'.format(json.id, json.tag, selected ? 'active' : '', json.index);
                    _html += '<label class="layui-form-label" style="width: {1}px;{2}">{0}:</label>'.format(json.label,json.labelWidth,_hideLabel);
                    if (json.hideLabel) {
                        _html += '<div class="layui-input-block" style="width:calc({0});border: 0px solid #d2d2d2;margin-left: 0px">'.format(json.width,json.labelWidth);
                    } else {
                        _html += '<div class="layui-input-block" style="width:calc({0} - {1}px);border: 1px solid #d2d2d2;border-left: 0px;margin-left: {1}px">'.format(json.width,json.labelWidth);
                    }
                    _html += '<input type="checkbox" name="{0}" lay-skin="switch" lay-text="ON|OFF" {1} class="{2}" {3}>'.format(json.id, _disabled, _disabledClass, json.switchValue ? 'checked' : '');
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
                    return '';
                }
            },
            slider: {
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
                    var _disabled = json.disabled ? 'disabled=""' : '';
                    var _hideLabel = json.hideLabel ? 'display: none;' : '';
                    var _html = '<div id="{0}" class="layui-form-item {2}"  data-id="{0}" data-tag="{1}" data-index="{3}">'.format(json.id, json.tag, selected ? 'active' : '', json.index);
                    _html += '<label class="layui-form-label" style="width: {1}px;{2}">{0}:</label>'.format(json.label,json.labelWidth,_hideLabel);
                    if (json.hideLabel) {
                        _html += '<div class="layui-input-block layui-form" style="width:calc({0});margin-left: 0px">'.format(json.width);
                    } else {
                        _html += '<div class="layui-input-block layui-form" style="width:calc({0} - {1}px);margin-left: {1}px">'.format(json.width,json.labelWidth);
                    }
                    _html += '<div id="{0}" class="widget-slider"></div>'.format(json.tag + json.id);
                    _html += '<input name="{0}" type="hidden" value="{1}"></input>'.format(json.id,json.defaultValue);
                    _html += '</div>';
                    _html += '</div>';
                    elem.append(_html);
                    slider.render({
                        elem: '#' + json.tag + json.id,
                        value: json.defaultValue, //初始值
                        min: json.minValue,
                        max: json.maxValue,
                        step: json.stepValue,
                        disabled: json.disabled,
                        input:json.isInput,
                    });
                },
                /**
                 * 根据json对象更新html对象
                 * @param {object} json 变更后的json属性
                 * @param {object} that 实例对象
                 * */
                update: function (json,that) {
                    var $block = $('#' + json.id + ' .layui-input-block');
                    var $label = $('#' + json.id + ' .layui-form-label');
                    $label.empty();
                    $label.css("width",json.labelWidth);
                    $label.append(json.label + ":");
                    if (json.hideLabel) {
                        $label.css("display","none");
                        $block.css("margin-left","0px");
                        $block.css({width: 'calc({0})'.format(json.width)});
                    } else {
                        $label.css("display","block");
                        $block.css("margin-left",json.labelWidth);
                        $block.css({width: 'calc({0} - {1}px)'.format(json.width,json.labelWidth)});
                    }
                    slider.render({
                        elem: '#' + json.tag + json.id,
                        value: json.defaultValue, //初始值
                        min: json.minValue,
                        max: json.maxValue,
                        step: json.stepValue,
                        disabled: json.disabled,
                        input:json.isInput,
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
                    var _json = JSON.parse(JSON.stringify(formField.components.slider));
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
                    var _disabled = json.disabled ? 'disabled=""' : '';
                    var _hideLabel = json.hideLabel ? 'display: none;' : '';
                    var _html = '<div id="{0}" class="layui-form-item {2}"  data-id="{0}" data-tag="{1}" data-index="{3}">'.format(json.id, json.tag, selected ? 'active' : '', json.index);
                    _html += '<label class="layui-form-label" style="width: {1}px;{2}">{0}:</label>'.format(json.label,json.labelWidth,_hideLabel);
                    if (json.hideLabel) {
                        _html += '<div class="layui-input-block layui-form" style="width:calc({0});margin-left: 0px">'.format(json.width);
                    } else {
                        _html += '<div class="layui-input-block layui-form" style="width:calc({0} - {1}px);margin-left: {1}px">'.format(json.width,json.labelWidth);
                    }
                    _html += '<div id="{0}" class="widget-slider"></div>'.format(json.tag + json.id);
                    _html += '<input name="{0}" type="hidden" value="{1}"></input>'.format(json.id,json.defaultValue);
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
                    scriptHtmlCode += ['slider.render({',
                        , 'elem: "#' + json.tag + json.id + '" ,'
                        , 'value: ' + json.defaultValue + ','
                        , 'min: ' + json.minValue + ','
                        , 'max: ' + json.maxValue + ','
                        , 'step: ' + json.stepValue + ','
                        , 'input:' + json.isInput + ','
                        , 'change: function(value){'
                        , '$("#' + json.id + '").find("input[name="' + json.id + '"]").val(value);'
                        , ' }'
                        , '});'].join('');
                    return scriptHtmlCode;
                }
            },
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
                    var _hideLabel = json.hideLabel ? 'display: none;' : '';
                    var _html = '<div id="{0}" class="layui-form-item {2}"  data-id="{0}" data-tag="{1}" data-index="{3}">'.format(json.id, json.tag, selected ? 'active' : '', json.index);
                    _html += '<label class="layui-form-label {0}" style="width: {3}px;{4}"><span style="color:red;">{2}</span>{1}:</label>'.format(json.required ? 'layui-form-required' : '', json.label,json.required ? '*' : '',json.labelWidth,_hideLabel);
                    if (json.hideLabel) {
                        _html += '<div class="layui-input-block" style="width:calc({0});margin-left: 0px;">'.format(json.width);
                    } else {
                        _html += '<div class="layui-input-block" style="width:calc({0} - {1}px);margin-left: {1}px;">'.format(json.width,json.labelWidth);
                    }
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
                    if (json.hideLabel) {
                        $label.css("display","none");
                        $block.css("margin-left","0px");
                        $block.css({width: 'calc({0})'.format(json.width)});
                    } else {
                        $label.css("display","block");
                        $block.css("margin-left",json.labelWidth);
                        $block.css({width: 'calc({0} - {1}px)'.format(json.width,json.labelWidth)});
                    }
                    $label.css("width",json.labelWidth);
                    if (json.required) {
                        $label.append('<span style="color:red;">*</span>');
                    }
                    $label.append(json.label + ":");
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
                    var _hideLabel = json.hideLabel ? 'display: none;' : '';
                    var _html = '<div id="{0}" class="layui-form-item {2}"  data-id="{0}" data-tag="{1}" data-index="{3}">'.format(json.id, json.tag, selected ? 'active' : '', json.index);
                    _html += '<label class="layui-form-label {0}" style="width: {3}px;{4}"><span style="color:red;">{2}</span>{1}:</label>'.format(json.required ? 'layui-form-required' : '', json.label,json.required ? '*' : '',json.labelWidth,_hideLabel);
                    if (json.hideLabel) {
                        _html += '<div class="layui-input-block" style="width:calc({0});margin-left: 0px;">'.format(json.width);
                    } else {
                        _html += '<div class="layui-input-block" style="width:calc({0} - {1}px);margin-left: {1}px;">'.format(json.width,json.labelWidth);
                    }
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
            rate: {
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
                    var _readonly = json.readonly ? 'readonly=""' : '';
                    var _required = json.required ? 'required=""' : '';
                    var _hideLabel = json.hideLabel ? 'display: none;' : '';
                    var _html = '<div id="{0}" class="layui-form-item {2}"  data-id="{0}" data-tag="{1}" data-index="{3}">'.format(json.id, json.tag, selected ? 'active' : '', json.index);
                    _html += '<label class="layui-form-label {0}" style="width:{2}px;{3}">{1}:</label>'.format(json.required ? 'layui-form-required' : '', json.label,json.labelWidth,_hideLabel);
                    if (json.hideLabel) {
                        _html += '<div class="layui-input-block" style="margin-left: 0px;">';
                    } else {
                        _html += '<div class="layui-input-block" style="margin-left: {0}px;">'.format(json.labelWidth + 30);
                    }
                    _html += '<div id="{0}" class="widget-rate"></div>'.format(json.tag + json.id);
                    _html += '<input name="{0}" type="hidden" value="{1}"></input>'.format(json.id,json.defaultValue);
                    _html += '</div>';
                    _html += '</div>';
                    elem.append(_html);
                    rate.render({
                        elem: '#' + json.tag + json.id,
                        value: json.defaultValue,
                        text: json.text,
                        length: json.rateLength,
                        half: json.half,
                        readonly: json.readonly,
                        theme: json.colorSelection,
                        choose: function(value){
                            $("#" + json.id).find("input[name=" + json.id + "]").val(value);
                        }
                    });
                },
                /**
                 * 根据json对象更新html对象
                 * @param {object} json 变更后的json属性
                 * @param {object} that 实例对象
                 * */
                update: function (json,that) {
                    var $block = $('#' + json.id + ' .layui-input-block');
                    var $label = $('#' + json.id + ' .layui-form-label');
                    $label.empty();
                    if (json.hideLabel) {
                        $label.css("display","none");
                        $block.css("margin-left","0px");
                    } else {
                        $label.css("display","block");
                        $block.css("margin-left",json.labelWidth + 30);
                    }
                    $label.append(json.label + ":");
                    rate.render({
                        elem: '#' + json.tag + json.id,
                        value: json.defaultValue,
                        text: json.text,
                        length: json.rateLength,
                        half: json.half,
                        readonly: json.readonly,
                        theme: json.colorSelection,
                        choose: function(value){
                            $("#" + json.id).find("input[name=" + json.id + "]").val(value);
                        }
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
                    var _json = JSON.parse(JSON.stringify(formField.components.rate));
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
                    var _hideLabel = json.hideLabel ? 'display: none;' : '';
                    var _html = '<div id="{0}" class="layui-form-item {2}"  data-id="{0}" data-tag="{1}" data-index="{3}">'.format(json.id, json.tag, selected ? 'active' : '', json.index);
                    _html += '<label class="layui-form-label {0}" style="width: {3}px;{4}"><span style="color:red;">{2}</span>{1}:</label>'.format(json.required ? 'layui-form-required' : '', json.label,json.required ? '*' : '',json.labelWidth,_hideLabel);
                    if (json.hideLabel) {
                        _html += '<div class="layui-input-block" style="margin-left: 0px;">';
                    } else {
                        _html += '<div class="layui-input-block" style="margin-left: {0}px;">'.format(json.labelWidth + 30);
                    }
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
                    scriptHtmlCode += ['rate.render({'
                        , 'elem: "#' + json.tag + json.id + '" ,'
                        , 'value: ' + json.defaultValue + ','
                        , 'text: ' + json.text + ','
                        , 'length: ' + json.rateLength + ','
                        , 'half: ' + json.half + ','
                        , 'readonly: ' + json.readonly + ','
                        , 'theme: ' + json.colorSelection + ','
                        , 'choose: function(value){'
                        , '$("#' + json.id + '").find("input[name="' + json.id + '"]").val(value);'
                        , '}'
                        , '});'].join('');
                    return scriptHtmlCode;
                }
            },
            carousel: {
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
                    var _html = '<div id="{0}" class="layui-form-item {2}"  data-id="{0}" data-tag="{1}" data-index="{3}">'.format(json.id, json.tag, selected ? 'active' : '', json.index);
                    _html += '<div class="layui-carousel" id="{0}">'.format(json.tag + json.id);
                    _html += '<div carousel-item class="carousel-item">';
                    for (var i = 0; i < json.options.length; i++) {
                        _html += '<div><img src="{0}" /></div>'.format(json.options[i].value);
                    }
                    _html += '</div>';//end for div carousel-item
                    _html += '</div>';//end for class=layui-carousel
                    _html += '</div>';
                    elem.append(_html);
                    carousel.render({
                        elem: '#' + json.tag + json.id,
                        width: json.width,//设置容器宽度
                        height: json.height,//设置容器宽度
                        arrow: json.arrow, //始终显示箭头
                        index: json.startIndex,
                        interval: json.interval,
                        anim: json.anim,
                        autoplay: json.autoplay,
                    });
                },
                /**
                 * 根据json对象更新html对象
                 * @param {object} json 变更后的json属性
                 * @param {object} that 实例对象
                 * */
                update: function (json,that) {
                    $('#' + json.tag + json.id).remove();
                    var _html = '';
                    _html += '<div class="layui-carousel" id="{0}">'.format(json.tag + json.id);
                    _html += '<div carousel-item class="carousel-item">';
                    for (var i = 0; i < json.options.length; i++) {
                        _html += '<div><img src="{0}" /></div>'.format(json.options[i].value);
                    }
                    _html += '</div>';//end for div carousel-item
                    _html += '</div>';//end for class=layui-carousel
                    $('#' + json.id).append(_html);
                    carousel.render({
                        elem: '#' + json.tag + json.id,
                        width: json.width,//设置容器宽度
                        height: json.height,//设置容器宽度
                        arrow: json.arrow, //始终显示箭头
                        index: json.startIndex,
                        interval: json.interval,
                        anim: json.anim,
                        autoplay: json.autoplay
                        //anim: 'updown' //切换动画方式
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
                    var _json = JSON.parse(JSON.stringify(formField.components.carousel));
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
                    var _html = '<div id="{0}" class="layui-form-item {2}"  data-id="{0}" data-tag="{1}" data-index="{3}">'.format(json.id, json.tag, selected ? 'active' : '', json.index);
                    _html += '<div class="layui-carousel" id="{0}">'.format(json.tag + json.id);
                    _html += '<div carousel-item class="carousel-item">';
                    for (var i = 0; i < json.options.length; i++) {
                        _html += '<div><img src="{0}" /></div>'.format(json.options[i].value);
                    }
                    _html += '</div>';//end for div carousel-item
                    _html += '</div>';//end for class=layui-carousel
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
                    scriptHtmlCode += ['carousel.render({'
                        , 'elem: "#' + json.tag + json.id + '" '
                        , ',width: "' + json.width + '"'
                        , ',height: "' + json.height + '"'
                        , ',arrow: "' + json.arrow + '"'
                        , ',interval: "' + json.interval + '"'
                        , ',anim: "' + json.anim + '"'
                        , ',autoplay: "' + json.autoplay + '"'
                        , '});'].join('');
                    return scriptHtmlCode;
                }
            },
            colorpicker: {
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
                    var _hideLabel = json.hideLabel ? 'display: none;' : '';
                    var _html = '<div id="{0}" class="layui-form-item {2}"  data-id="{0}" data-tag="{1}" data-index="{3}">'.format(json.id, json.tag, selected ? 'active' : '', json.index);
                    _html += '<label class="layui-form-label {0}" style="width: {3}px;{4}"><span style="color:red;">{2}</span>{1}:</label>'.format(json.required ? 'layui-form-required' : '', json.label,json.required ? '*' : '',json.labelWidth,_hideLabel);
                    if (json.hideLabel) {
                        _html += '<div class="layui-input-block" style="margin-left: 0px">';
                    } else {
                        _html += '<div class="layui-input-block" style="margin-left: {0}px">'.format(json.labelWidth);
                    }
                    if (json.disabled) {
                        _html += '<div class="iceEditor-disabled" style="width: 10%;height: 89%;"></div>';
                    }
                    _html += '<div id="{0}" class="widget-rate"></div>'.format(json.tag + json.id);
                    _html += '<input name="{0}" type="hidden" value="{1}"></input>'.format(json.id,json.defaultValue);
                    _html += '</div>';
                    _html += '</div>';
                    elem.append(_html);
                    colorpicker.render({
                        elem: '#' + json.tag + json.id
                        ,color: json.defaultValue
                        ,format: 'rgb'
                        ,predefine: true
                        ,alpha: true
                        ,done: function (color) {
                            $("#" + json.id).find("input[name=" + json.id + "]").val(color);
                        }
                    });
                },
                /**
                 * 根据json对象更新html对象
                 * @param {object} json 变更后的json属性
                 * @param {object} that 实例对象
                 * */
                update: function (json,that) {
                    var $block = $('#' + json.id + ' .layui-input-block');
                    var $label = $('#' + json.id + ' .layui-form-label');
                    $label.empty();
                    if (json.hideLabel) {
                        $label.css("display","none");
                        $block.css("margin-left","0px");
                    } else {
                        $label.css("display","block");
                        $block.css("margin-left",json.labelWidth);
                    }                    $label.css("width",json.labelWidth);
                    $label.append(json.label + ":");
                    if (json.disabled) {
                        $("#" + json.id).find(".layui-input-block").append('<div class="iceEditor-disabled" style="width: 10%;height: 89%;"></div>');
                    } else {
                        $("#" + json.id).find(".iceEditor-disabled").remove();
                    }
                    colorpicker.render({
                        elem: '#' + json.tag + json.id
                        ,color: json.defaultValue
                        ,format: 'rgb'
                        ,predefine: true
                        ,alpha: true
                        ,done: function (color) {
                            $("#" + json.id).find("input[name=" + json.id + "]").val(color);
                        }
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
                    var _json = JSON.parse(JSON.stringify(formField.components.colorpicker));
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
                    var _hideLabel = json.hideLabel ? 'display: none;' : '';
                    var _html = '<div id="{0}" class="layui-form-item {2}"  data-id="{0}" data-tag="{1}" data-index="{3}">'.format(json.id, json.tag, selected ? 'active' : '', json.index);
                    _html += '<label class="layui-form-label {0}" style="width: {3}px;{4}"><span style="color:red;">{2}</span>{1}:</label>'.format(json.required ? 'layui-form-required' : '', json.label,json.required ? '*' : '',json.labelWidth,_hideLabel);
                    if (json.hideLabel) {
                        _html += '<div class="layui-input-block" style="margin-left: 0px">';
                    } else {
                        _html += '<div class="layui-input-block" style="margin-left: {0}px">'.format(json.labelWidth);
                    }
                    if (json.disabled) {
                        _html += '<div class="iceEditor-disabled" style="width: 10%;height: 89%;"></div>';
                    }
                    _html += '<div id="{0}" class="widget-rate"></div>'.format(json.tag + json.id);
                    _html += '<input name="{0}" type="hidden" value="{1}"></input>'.format(json.id,json.defaultValue);
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
                    scriptHtmlCode += ['colorpicker.render({'
                        , 'elem: "#' + json.tag + json.id + '"'
                        , ',format: \'rgb\''
                        , ',predefine: true'
                        , ',alpha: true'
                        , ',done: function (color) {'
                        , '$("#' + json.id + '").find("input[name="'+ json.id + '"]").val(color);'
                        , '}'
                        , '});'].join('');
                    return scriptHtmlCode;
                }
            },
            image: {
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
                    var _html = '<div id="{0}" class="layui-form-item {2}"  data-id="{0}" data-tag="{1}" data-index="{3}">'.format(json.id, json.tag, selected ? 'active' : '', json.index);
                    _html += '<label class="layui-form-label {0}">{1}:</label>'.format(json.required ? 'layui-form-required' : '', json.label);
                    _html += '<div class="layui-input-block">';
                    _html += '<div class="layui-upload">';
                    _html += '<button type="button" class="layui-btn" id="{0}">多图片上传</button>'.format(json.tag + json.id);
                    _html += '<blockquote class="layui-elem-quote layui-quote-nm" style="margin-top: 10px;width: 88%">预览图：';
                    _html += '<div class="layui-upload-list uploader-list" style="overflow: auto;" id="uploader-list-{0}">'.format(json.id);
                    _html += '</div>';
                    _html += '</blockquote>';
                    _html += '</div>';
                    _html += '</div>';
                    _html += '</div>';
                    elem.append(_html);
                    if (that.config.viewOrDesign) {
                        var data = {"select":json.tag + json.id,"uploadUrl": json.uploadUrl};
                        images.push(data);
                    }
                },
                /**
                 * 根据json对象更新html对象
                 * @param {object} json 变更后的json属性
                 * @param {object} that 实例对象
                 * */
                update: function (json,that) {

                },
                /**
                 * 根据components组件对象获取组件属性
                 * @param {object} id 所属组件id
                 * @param {object} index 所属对象组件索引
                 * @param {object} that 实例对象
                 * */
                jsonData: function (id, index, that) {
                    //分配一个新的ID
                    var _json = JSON.parse(JSON.stringify(formField.components.image));
                    _json.id = id;
                    _json.index = index;
                    layer.msg('上传组件请在组件内部自行编写代码，或者根据demo在外部编写代码', {
                        time: 2000
                    })
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
                    var _html = '<div id="{0}" class="layui-form-item {2}"  data-id="{0}" data-tag="{1}" data-index="{3}">'.format(json.id, json.tag, selected ? 'active' : '', json.index);
                    _html += '<label class="layui-form-label {0}">{1}:</label>'.format(json.required ? 'layui-form-required' : '', json.label);
                    _html += '<div class="layui-input-block">';
                    _html += '<div class="layui-upload">';
                    _html += '<button type="button" class="layui-btn" id="{0}">多图片上传</button>'.format(json.tag + json.id);
                    _html += '<blockquote class="layui-elem-quote layui-quote-nm" style="margin-top: 10px;width: 88%">预览图：';
                    _html += '<div class="layui-upload-list uploader-list" style="overflow: auto;" id="uploader-list-{0}">'.format(json.id);
                    _html += '</div>';
                    _html += '</blockquote>';
                    _html += '</div>';
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
                    scriptHtmlCode += ['upload.render({'
                        , 'elem: "#' + json.tag + json.id + '" '
                        , ', url: "' + json.uploadUrl + '"'
                        , ', multiple: true'
                        , ', before: function (obj) {'
                        , 'layer.msg("图片上传中...", {'
                        , 'icon: 16,'
                        , 'shade: 0.01,'
                        , 'time: 0'
                        , '})'
                        , '}'
                        , ', done: function (res) {'
                        , 'layer.close(layer.msg());'
                        , '$("#uploader-list-' + json.id + '").append('
                        , '\'<div class="file-iteme"><div class="handle"><i class="layui-icon layui-icon-delete"></i></div><img style="width: 100px;height: 100px;" src="\'+ res.data.src + \'">'
                        , '<div class="info">\'+ res.data.title+\'</div>'
                        , '</div>\''
                        , ');'
                        , '}'
                        , '});'].join('');
                    return scriptHtmlCode;
                }
            },
            file: {
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
                    var _html = '<div id="{0}" class="layui-form-item {2}"  data-id="{0}" data-tag="{1}" data-index="{3}">'.format(json.id, json.tag, selected ? 'active' : '', json.index);
                    _html += '<label class="layui-form-label {0}">{1}:</label>'.format(json.required ? 'layui-form-required' : '', json.label);
                    _html += '<div class="layui-input-block">';
                    _html += '<div class="layui-upload">';
                    _html += '<button type="button" class="layui-btn layui-btn-normal" id="{0}">选择多文件</button> '.format(json.tag + json.id);
                    _html += ' <div class="layui-upload-list" style="max-width: 1000px;"><table class="layui-table">';
                    _html += '<colgroup><col><col width="150"><col width="260"><col width="150"></colgroup>';
                    _html += '<thead><tr><th>文件名</th><th>大小</th><th>上传进度</th><th>操作</th></tr></thead>';
                    _html += '<tbody id="list-{0}"></tbody></table></div>'.format(json.id);
                    _html += '<button type="button" class="layui-btn" id="listAction-{0}">开始上传</button>'.format(json.id);
                    _html += '</div>';
                    _html += '</blockquote>';
                    _html += '</div>';
                    _html += '</div>';
                    _html += '</div>';
                    elem.append(_html);
                    if (that.config.viewOrDesign) {
                        var data = {"select":json.tag + json.id,"uploadUrl": json.uploadUrl};
                        files.push(data);
                    }
                },
                /**
                 * 根据json对象更新html对象
                 * @param {object} json 变更后的json属性
                 * @param {object} that 实例对象
                 * */
                update: function (json,that) {

                },
                /**
                 * 根据components组件对象获取组件属性
                 * @param {object} id 所属组件id
                 * @param {object} index 所属对象组件索引
                 * @param {object} that 实例对象
                 * */
                jsonData: function (id, index, that) {
                    //分配一个新的ID
                    var _json = JSON.parse(JSON.stringify(formField.components.file));
                    _json.id = id;
                    _json.index = index;
                    layer.msg('上传组件请在组件内部自行编写代码，或者根据demo在外部编写代码', {
                        time: 2000
                    })
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
                    var _html = '<div id="{0}" class="layui-form-item {2}"  data-id="{0}" data-tag="{1}" data-index="{3}">'.format(json.id, json.tag, selected ? 'active' : '', json.index);
                    _html += '<label class="layui-form-label {0}">{1}:</label>'.format(json.required ? 'layui-form-required' : '', json.label);
                    _html += '<div class="layui-input-block">';
                    _html += '<div class="layui-upload">';
                    _html += '<button type="button" class="layui-btn layui-btn-normal" id="{0}">选择多文件</button> '.format(json.tag + json.id);
                    _html += ' <div class="layui-upload-list" style="max-width: 1000px;"><table class="layui-table">';
                    _html += '<colgroup><col><col width="150"><col width="260"><col width="150"></colgroup>';
                    _html += '<thead><tr><th>文件名</th><th>大小</th><th>上传进度</th><th>操作</th></tr></thead>';
                    _html += '<tbody id="list-{0}"></tbody></table></div>'.format(json.id);
                    _html += '<button type="button" class="layui-btn" id="listAction-{0}">开始上传</button>'.format(json.id);
                    _html += '</div>';
                    _html += '</blockquote>';
                    _html += '</div>';
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
                    scriptHtmlCode += ['upload.render({'
                        , 'elem: "#' + json.tag + json.id + '" '
                        , ', url: "' + json.uploadUrl + '"'
                        , ' ,elemList: $("#list-' + json.id + '")'
                        , ',accept: "file"'
                        , ',multiple: true'
                        , ',number: 3'
                        , ',auto: false'
                        , ',bindAction: "#listAction-' + json.id + '"'
                        , ',choose: function(obj){'
                        , 'var that = this;'
                        , 'var files = this.files = obj.pushFile();'
                        , 'obj.preview(function(index, file, result){'
                        , 'var tr = $(["<tr id="upload-"+ index +"">"'
                        , ',"<td>"+ file.name +"</td>"'
                        , ',"<td>"+ (file.size/1014).toFixed(1) +"kb</td>"'
                        , ',"<td><div class="layui-progress" lay-filter="progress-demo-"+ index +""><div class="layui-progress-bar" lay-percent=""></div></div></td>"'
                        , ',"<td>","<button class="layui-btn layui-btn-xs demo-reload layui-hide">重传</button>","<button class="layui-btn layui-btn-xs layui-btn-danger demo-delete">删除</button>","</td>"'
                        , ',"</tr>"].join(""));'
                        , 'tr.find(".demo-reload").on("click", function(){obj.upload(index, file);});'
                        , 'tr.find(".demo-delete").on("click", function(){delete files[index];tr.remove();uploadListIns.config.elem.next()[0].value = ""; });'
                        , 'that.elemList.append(tr);'
                        , 'element.render("progress");}'
                        , ',done: function(res, index, upload)'
                        , '{var that = this;if(res.code == 0){var tr = that.elemList.find("tr#upload-"+ index),tds = tr.children();tds.eq(3).html("");delete this.files[index];return;}this.error(index, upload);}'
                        , ',allDone: function(obj){console.log(obj)}'
                        , ',error: function(index, upload){var that = this;var tr = that.elemList.find("tr#upload-"+ index),'
                        , 'tds = tr.children();tds.eq(3).find(".demo-reload").removeClass("layui-hide");}'
                        , ',progress: function(n, elem, e, index){element.progress("progress-demo-"+ index, n + "%");}'
                        , '});'].join('');
                    return scriptHtmlCode;
                }
            },
            dateRange: {
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
                    var _hideLabel = json.hideLabel ? 'display: none;' : '';
                    var _html = '<div id="{0}" class="layui-form-item {2}"  data-id="{0}" data-tag="{1}" data-index="{3}">'.format(json.id, json.tag, selected ? 'active' : '', json.index);
                    _html += '<div class="layui-inline">';
                    _html += '<label class="layui-form-label {0}" style="width: {3}px;{4}"><span style="color:red;">{2}</span>{1}:</label>'.format(json.required ? 'layui-form-required' : '', json.label,json.required ? '*' : '',json.labelWidth,_hideLabel);
                    _html += '<div class="layui-inline" id="{0}" style="line-height: 40px;{1}">'.format(json.tag + json.id,_disabledStyle);
                    _html += '<div class="layui-input-inline">';
                    _html += '<input id="start-{0}" lay-verify="{3}" name="start{2}" class="layui-input {1}" autocomplete="off" placeholder="开始日期"></input>'.format(json.tag + json.id,_disabledClass,json.id,_required);
                    _html += '</div>';
                    _html += '<div class="layui-form-mid">-</div>';
                    _html += '<div class="layui-input-inline">';
                    _html += '<input id="end-{0}" lay-verify="{3}" name="end{2}" class="layui-input {1}" autocomplete="off" placeholder="结束日期"></input>'.format(json.tag + json.id,_disabledClass,json.id,_required);
                    _html += '</div>';
                    _html += '</div>';
                    _html += '</div>';
                    _html += '</div>';
                    elem.append(_html);
                    laydate.render({
                        elem: '#' + json.tag + json.id,
                        type: json.dateType,
                        format: json.dateFormat,
                        min: json.dataMinValue,
                        max: json.dataMaxValue,
                        range: ['#start-' + json.tag + json.id, '#end-' + json.tag + json.id]
                    });
                    if (json.dateRangeDefaultValue !== null && json.dateRangeDefaultValue !== ""
                        && json.dateRangeDefaultValue !== undefined) {
                        var split = json.dateRangeDefaultValue.split(" - ");
                        $('#start-' + json.tag + json.id).val(split[0]);
                        $('#end-' + json.tag + json.id).val(split[1]);
                    }
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
                    var _hideLabel = json.hideLabel ? 'display: none;' : '';
                    var _html = '<div class="layui-inline">';
                    _html += '<label class="layui-form-label {0}" style="width: {3}px;{4}"><span style="color:red;">{2}</span>{1}:</label>'.format(json.required ? 'layui-form-required' : '', json.label,json.required ? '*' : '',json.labelWidth,_hideLabel);
                    _html += '<div class="layui-inline" id="{0}" style="line-height: 40px;{1}">'.format(json.tag + json.id,_disabledStyle);
                    _html += '<div class="layui-input-inline">';
                    _html += '<input id="start-{0}" lay-verify="{3}" name="start{2}" class="layui-input {1}" autocomplete="off" placeholder="开始日期" ></input>'.format(json.tag + json.id,_disabledClass,json.id,_required);
                    _html += '</div>';
                    _html += '<div class="layui-form-mid">-</div>';
                    _html += '<div class="layui-input-inline">';
                    _html += '<input id="end-{0}" lay-verify="{3}" name="end{2}" class="layui-input {1}" autocomplete="off" placeholder="结束日期"></input>'.format(json.tag + json.id,_disabledClass,json.id,_required);
                    _html += '</div>';
                    _html += '</div>';
                    _html += '</div>';
                    $('#' + json.id + ' .layui-inline').remove();
                    $('#' + json.id).append(_html);
                    laydate.render({
                        elem: '#' + json.tag + json.id,
                        type: json.dateType,
                        format: json.dateFormat,
                        //value: item.dateDefaultValue,
                        min: json.dataMinValue,
                        max: json.dataMaxValue,
                        range: ['#start-' + json.tag + json.id, '#end-' + json.tag + json.id]
                    });
                    if (json.dateRangeDefaultValue !== null && json.dateRangeDefaultValue !== ""
                        && json.dateRangeDefaultValue !== undefined) {
                        var split = json.dateRangeDefaultValue.split(" - ");
                        $('#start-' + json.tag + json.id).val(split[0]);
                        $('#end-' + json.tag + json.id).val(split[1]);
                    }
                },
                /**
                 * 根据components组件对象获取组件属性
                 * @param {object} id 所属组件id
                 * @param {object} index 所属对象组件索引
                 * @param {object} that 实例对象
                 * */
                jsonData: function (id, index, that) {
                    var _json = JSON.parse(JSON.stringify(formField.components.dateRange));
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
                    var _hideLabel = json.hideLabel ? 'display: none;' : '';
                    var _html = '<div id="{0}" class="layui-form-item {2}"  data-id="{0}" data-tag="{1}" data-index="{3}">'.format(json.id, json.tag, selected ? 'active' : '', json.index);
                    _html += '<div class="layui-inline">';
                    _html += '<label class="layui-form-label {0}" style="width: {3}px;{4}"><span style="color:red;">{2}</span>{1}:</label>'.format(json.required ? 'layui-form-required' : '', json.label,json.required ? '*' : '',json.labelWidth,_hideLabel);
                    _html += '<div class="layui-inline" id="{0}" style="line-height: 40px;{1}">'.format(json.tag + json.id,_disabledStyle);
                    _html += '<div class="layui-input-inline">';
                    _html += '<input id="start-{0}" lay-verify="{3}" name="start{2}" class="layui-input {1}" autocomplete="off" placeholder="开始日期"></input>'.format(json.tag + json.id,_disabledClass,json.id,_required);
                    _html += '</div>';
                    _html += '<div class="layui-form-mid">-</div>';
                    _html += '<div class="layui-input-inline">';
                    _html += '<input id="end-{0}" lay-verify="{3}" name="end{2}" class="layui-input {1}" autocomplete="off" placeholder="结束日期"></input>'.format(json.tag + json.id,_disabledClass,json.id,_required);
                    _html += '</div>';
                    _html += '</div>';
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
                        ,'elem:"#' + json.tag + json.id + '",'
                        ,'type:' + json.datetype + ','
                        ,'format:' + json.dateformat + ''
                        ,'min:' + json.dataMinValue + ''
                        ,'max:' + json.dataMaxValue + ''
                        ,'range:["#start-' + json.tag + json.id + '", "#end-' + json.tag + json.id + '"]'
                        ,'});'].join('');
                    return scriptHtmlCode;
                }
            },
            bottom: {
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
                    var _coustomCss = "";
                    if (json.buttonSize === "layui-btn-lg") {
                        _coustomCss = "custom-lg";
                    }
                    if (json.buttonSize === "") {
                        _coustomCss = "custom-zc";
                    }
                    if (json.buttonSize === "layui-btn-sm") {
                        _coustomCss = "custom-sm";
                    }
                    if (json.buttonSize === "layui-btn-xs") {
                        _coustomCss = "custom-xs";
                    }
                    var _html = '<div id="{0}" class="layui-form-item {2}"  data-id="{0}" data-tag="{1}" data-index="{3}">'.format(json.id, json.tag, selected ? 'active' : '', json.index);
                    if (!json.hideLabel) {
                        _html += '<label class="layui-form-label" style="width: {1}px;">{0}:</label>'.format(json.label,json.labelWidth);
                    }
                    _html += '<div class="layui-input-block" style="margin-left: 0px;">';
                    if (json.disabled) {
                        _html += '<button id="{0}" type="button" class="layui-btn {1} layui-btn-disabled {2}"><i class="layui-icon {3}"></i> {4}</button>'.format(json.id + json.tag, json.buttonSize,_coustomCss ,json.buttonIcon,json.buttonVlaue);
                    }else {
                        _html += '<button id="{0}" type="button" class="layui-btn {1} {2} {3}"><i class="layui-icon {4}"></i> {5}</button>'.format(json.id + json.tag, json.buttonSize, json.buttonType,_coustomCss ,json.buttonIcon,json.buttonVlaue);
                    }
                    _html += '</div>';
                    _html += '</div>';
                    elem.append(_html);
                },
                /**
                 * 根据json对象更新html对象
                 * @param {object} json 变更后的json属性
                 * @param {object} that 实例对象
                 * */
                update: function (json,that) {
                    var $block = $('#' + json.id + ' .layui-input-block');
                    var $label = $('#' + json.id + ' .layui-form-label');
                    if (json.hideLabel) {
                        $label.remove();
                        $block.css("margin-left","0px");
                    }else {
                        if ($('#' + json.id).find("label").length === 0) {
                            $('#' + json.id).prepend('<label class="layui-form-label" style="width: {1}px;">{0}:</label>'.format(json.label,json.labelWidth));
                        }else {
                            $label.css("width",json.labelWidth + "px");
                        }
                        $label.empty();
                        $label.append(json.label + ":");
                    }
                    $block.empty();
                    var _html = '';
                    var _coustomCss = "";
                    if (json.buttonSize === "layui-btn-lg") {
                        _coustomCss = "custom-lg";
                    }
                    if (json.buttonSize === "") {
                        _coustomCss = "custom-zc";
                    }
                    if (json.buttonSize === "layui-btn-sm") {
                        _coustomCss = "custom-sm";
                    }
                    if (json.buttonSize === "layui-btn-xs") {
                        _coustomCss = "custom-xs";
                    }
                    //重绘设计区改id下的所有元素
                    if (json.disabled) {
                        _html += '<button id="{0}" type="button" class="layui-btn {1} layui-btn-disabled {2}"><i class="layui-icon {3}"></i> {4}</button>'.format(json.id + json.tag, json.buttonSize,_coustomCss ,json.buttonIcon,json.buttonVlaue);
                    }else {
                        _html += '<button id="{0}" type="button" class="layui-btn {1} {2} {3}"><i class="layui-icon {4}"></i> {5}</button>'.format(json.id + json.tag, json.buttonSize, json.buttonType,_coustomCss ,json.buttonIcon,json.buttonVlaue);
                    }
                    $block.append(_html);
                },
                /**
                 * 根据components组件对象获取组件属性
                 * @param {object} id 所属组件id
                 * @param {object} index 所属对象组件索引
                 * @param {object} that 实例对象
                 * */
                jsonData: function (id, index, that) {
                    //分配一个新的ID
                    var _json = JSON.parse(JSON.stringify(formField.components.bottom));
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
                    var _coustomCss = "";
                    if (json.buttonSize === "layui-btn-lg") {
                        _coustomCss = "custom-lg";
                    }
                    if (json.buttonSize === "") {
                        _coustomCss = "custom-zc";
                    }
                    if (json.buttonSize === "layui-btn-sm") {
                        _coustomCss = "custom-sm";
                    }
                    if (json.buttonSize === "layui-btn-xs") {
                        _coustomCss = "custom-xs";
                    }
                    var _html = '<div id="{0}" class="layui-form-item {2}"  data-id="{0}" data-tag="{1}" data-index="{3}">'.format(json.id, json.tag, selected ? 'active' : '', json.index);
                    if (!json.hideLabel) {
                        _html += '<label class="layui-form-label" style="width: {1}px;">{0}:</label>'.format(json.label,json.labelWidth);
                    }
                    _html += '<div class="layui-input-block" style="margin-left: 0px;">';
                    if (json.disabled) {
                        _html += '<button id="{0}" type="button" class="layui-btn {1} layui-btn-disabled {2}"><i class="layui-icon {3}"></i> {4}</button>'.format(json.id + json.tag, json.buttonSize,_coustomCss ,json.buttonIcon,json.buttonVlaue);
                    }else {
                        _html += '<button id="{0}" type="button" class="layui-btn {1} {2} {3}"><i class="layui-icon {4}"></i> {5}</button>'.format(json.id + json.tag, json.buttonSize, json.buttonType,_coustomCss ,json.buttonIcon,json.buttonVlaue);
                    }
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
                    return '';
                }
            },
            numberInput: {
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
                    var _disabled = json.disabled ? 'disabled=""' : '';
                    var _disabledClass = json.disabled ? ' layui-disabled' : '';
                    var _required = json.required ? 'required' : '';
                    var _hideLabel = json.hideLabel ? 'display: none;' : '';
                    var _html = '<div id="{0}" class="layui-form-item {2}"  data-id="{0}" data-tag="{1}" data-index="{3}">'.format(json.id, json.tag, selected ? 'active' : '', json.index);
                    _html += '<label class="layui-form-label" style="width: {1};{2}">{0}:</label>'.format( json.label,json.labelWidth,_hideLabel);
                    if (json.hideLabel) {
                        _html += '<div class="layui-input-block" style="width:calc({0});margin-left: 0px;">'.format(json.width);
                    } else {
                        _html += '<div class="layui-input-block" style="width:calc({0} - {1}px);margin-left: {1}px;">'.format(json.width,json.labelWidth);
                    }
                    _html += '<input name="{0}" id="{9}" value="{1}" placeholder="{3}" class="layui-input{5}" lay-vertype="tips" min="{6}" max="{7}" step="{8}"  {4} style="width:{2}">'
                        .format(json.id, json.defaultValue ? json.defaultValue : '0', json.width, json.placeholder, _disabled , _disabledClass,json.minValue,json.maxValue,json.stepValue,json.tag + json.id);
                    _html += '</div>';
                    _html += '</div>';
                    elem.append(_html);
                    //定义初始值
                    numberInput.render({
                        elem:'#' + json.tag + json.id
                    });
                    var _width = json.width.replace(/[^\d]/g,'');
                    if(''!=_width){
                        _width = 100 - parseInt(_width);
                    }
                    $('#' + json.id + ' .layui-input-block .layui-number-input-btn').css("right",_width + "%");
                    if (json.disabled) {
                        $('#' + json.id + ' .layui-input-block .layui-number-input-btn').css("z-index","-1");
                    }
                },
                /**
                 * 根据json对象更新html对象
                 * @param {object} json 变更后的json属性
                 * @param {object} that 实例对象
                 * */
                update: function (json,that) {
                    var _disabled = json.disabled ? 'disabled=""' : '';
                    var _disabledClass = json.disabled ? ' layui-disabled' : '';
                    var $block = $('#' + json.id + ' .layui-input-block');
                    var $label = $('#' + json.id + ' .layui-form-label');
                    $block.empty();
                    $label.empty();
                    if (json.hideLabel) {
                        $label.css("display","none");
                        $block.css("margin-left","0px");
                        $block.css({width: 'calc({0})'.format(json.width)});
                    } else {
                        $label.css("display","block");
                        $block.css("margin-left",json.labelWidth);
                        $block.css({width: 'calc({0} - {1}px)'.format(json.width,json.labelWidth)});
                    }
                    $label.css("width",json.labelWidth);
                    $label.append(json.label + ":");
                    var _html = '';
                    //重绘设计区改id下的所有元素
                    _html += '<input name="{0}" id="{9}" value="{1}" placeholder="{3}" class="layui-input{5}" lay-vertype="tips" min="{6}" max="{7}" step="{8}"  {4} style="width:{2}">'
                        .format(json.id, json.defaultValue ? json.defaultValue : '0', json.width, json.placeholder, _disabled , _disabledClass,json.minValue,json.maxValue,json.stepValue,json.tag + json.id);
                    $block.append(_html);
                    numberInput.render({
                        elem:'#' + json.tag + json.id
                    });
                    var _width = json.width.replace(/[^\d]/g,'');
                    if(''!=_width){
                        _width = 100 - parseInt(_width);
                    }
                    $('#' + json.id + ' .layui-input-block .layui-number-input-btn').css("right",_width + "%");
                    if (json.disabled) {
                        $('#' + json.id + ' .layui-input-block .layui-number-input-btn').css("z-index","-1");
                    }
                },
                /**
                 * 根据components组件对象获取组件属性
                 * @param {object} id 所属组件id
                 * @param {object} index 所属对象组件索引
                 * @param {object} that 实例对象
                 * */
                jsonData: function (id, index, that) {
                    var _json = JSON.parse(JSON.stringify(formField.components.numberInput));
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
                    var _disabled = json.disabled ? 'disabled=""' : '';
                    var _disabledClass = json.disabled ? ' layui-disabled' : '';
                    var _required = json.required ? 'required' : '';
                    var _hideLabel = json.hideLabel ? 'display: none;' : '';
                    var _html = '<div id="{0}" class="layui-form-item {2}"  data-id="{0}" data-tag="{1}" data-index="{3}">'.format(json.id, json.tag, selected ? 'active' : '', json.index);
                    _html += '<label class="layui-form-label" style="width: {1};{2}">{0}:</label>'.format( json.label,json.labelWidth,_hideLabel);
                    if (json.hideLabel) {
                        _html += '<div class="layui-input-block" style="width:calc({0});margin-left: 0px;">'.format(json.width);
                    } else {
                        _html += '<div class="layui-input-block" style="width:calc({0} - {1}px);margin-left: {1}px;">'.format(json.width,json.labelWidth);
                    }
                    _html += '<input name="{0}" id="{9}" value="{1}" placeholder="{3}" class="layui-input{5}" lay-vertype="tips" min="{6}" max="{7}" step="{8}"  {4} style="width:{2}">'
                        .format(json.id, json.defaultValue ? json.defaultValue : '0', json.width, json.placeholder, _disabled , _disabledClass,json.minValue,json.maxValue,json.stepValue,json.tag + json.id);
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
                    scriptHtmlCode += [' numberInput.render({'
                        ,'elem:"#' + json.tag + json.id + '"'
                        ,'});var _width = ' + json.width.replace(/[^\d]/g,'') + ';'
                        ,'if(""!=_width){_width = 100 - parseInt(_width);}'
                        ,'$("#' + json.id + ' .layui-input-block .layui-number-input-btn").css("right",_width + "%");'
                        ,'if (item.disabled) { $("#' + json.id + ' .layui-input-block .layui-number-input-btn").css("z-index","-1");}'].join('');
                    return scriptHtmlCode;
                }
            },
            iconPicker: {
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
                    var _disabled = json.disabled ? 'disabled=""' : '';
                    var _disabledClass = json.disabled ? ' layui-disabled' : '';
                    var _hideLabel = json.hideLabel ? 'display: none;' : '';
                    var _html = '<div id="{0}" class="layui-form-item {2}"  data-id="{0}" data-tag="{1}" data-index="{3}">'.format(json.id, json.tag, selected ? 'active' : '', json.index);
                    _html += '<label class="layui-form-label" style="width: {1}px;{2}">{0}:</label>'.format( json.label,json.labelWidth,_hideLabel);
                    if (json.hideLabel) {
                        _html += '<div class="layui-input-block" style="margin-left: 0px">';
                    } else {
                        _html += '<div class="layui-input-block" style="margin-left: {0}px">'.format(json.labelWidth);
                    }
                    if (json.disabled) {
                        _html += '<div class="iceEditor-disabled"></div>';
                    }
                    _html += '<input name="{0}" id="{6}" value="{1}" placeholder="{3}" class="layui-input{5}" lay-filter="{6}" lay-vertype="tips" {4} style="width:{2}">'
                        .format(json.id, json.defaultValue ? json.defaultValue : '', json.width, json.placeholder, _disabled, _disabledClass,json.tag + json.id);
                    _html += '</div>';
                    _html += '</div>';
                    elem.append(_html);
                    iconPicker.render({
                        // 选择器，推荐使用input
                        elem: '#' + json.tag + json.id,
                        // 数据类型：fontClass/unicode，推荐使用fontClass
                        type: 'fontClass',
                        // 是否开启搜索：true/false，默认true
                        search: json.iconPickerSearch,
                        // 是否开启分页：true/false，默认true
                        page: json.iconPickerPage,
                        // 每页显示数量，默认12
                        limit: json.iconPickerLimit,
                        // 每个图标格子的宽度：'43px'或'20%'
                        cellWidth: json.iconPickerCellWidth,
                        // 点击回调
                        click: function (data) {
                            //console.log(data);
                        },
                        // 渲染成功后的回调
                        success: function(d) {
                            //console.log(d);
                        }
                    });
                    iconPicker.checkIcon(json.tag + json.id, '');
                },
                /**
                 * 根据json对象更新html对象
                 * @param {object} json 变更后的json属性
                 * @param {object} that 实例对象
                 * */
                update: function (json,that) {
                    var _disabled = json.disabled ? 'disabled=""' : '';
                    var _disabledClass = json.disabled ? ' layui-disabled' : '';
                    var $block = $('#' + json.id + ' .layui-input-block');
                    var $label = $('#' + json.id + ' .layui-form-label');
                    $label.empty();
                    $block.empty();
                    if (json.hideLabel) {
                        $label.css("display","none");
                        $block.css("margin-left","0px");
                    } else {
                        $label.css("display","block");
                        $block.css("margin-left",json.labelWidth);
                    }
                    $label.css("width",json.labelWidth);
                    $label.append(json.label + ":");
                    var _html = '';
                    //重绘设计区改id下的所有元素
                    _html += '<input name="{0}" value="{1}" id="{6}" placeholder="{3}" class="layui-input{5}" lay-filter="{6}" lay-vertype="tips" {4} style="width:{2}">'
                        .format(json.id, json.defaultValue ? json.defaultValue : '', json.width, json.placeholder, _disabled, _disabledClass,json.tag + json.id);
                    $('#' + json.id + ' .layui-input-block').append(_html);
                    iconPicker.render({
                        // 选择器，推荐使用input
                        elem: '#' + json.tag + json.id,
                        // 数据类型：fontClass/unicode，推荐使用fontClass
                        type: 'fontClass',
                        // 是否开启搜索：true/false，默认true
                        search: json.iconPickerSearch,
                        // 是否开启分页：true/false，默认true
                        page: json.iconPickerPage,
                        // 每页显示数量，默认12
                        limit: json.iconPickerLimit,
                        // 每个图标格子的宽度：'43px'或'20%'
                        cellWidth: json.iconPickerCellWidth,
                        // 点击回调
                        click: function (data) {
                            //console.log(data);
                        },
                        // 渲染成功后的回调
                        success: function(d) {
                            //console.log(d);
                        }
                    });
                    iconPicker.checkIcon(json.tag + json.id, json.defaultValue);
                    if (json.disabled) {
                        $("#" + json.id).find(".layui-input-block").append('<div class="iceEditor-disabled"></div>');
                    } else {
                        $("#" + json.id).find(".iceEditor-disabled").remove();
                    }
                },
                /**
                 * 根据components组件对象获取组件属性
                 * @param {object} id 所属组件id
                 * @param {object} index 所属对象组件索引
                 * @param {object} that 实例对象
                 * */
                jsonData: function (id, index, that) {
                    //分配一个新的ID
                    var _json = JSON.parse(JSON.stringify(formField.components.iconPicker));
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
                    var _disabled = json.disabled ? 'disabled=""' : '';
                    var _disabledClass = json.disabled ? ' layui-disabled' : '';
                    var _hideLabel = json.hideLabel ? 'display: none;' : '';
                    var _html = '<div id="{0}" class="layui-form-item {2}"  data-id="{0}" data-tag="{1}" data-index="{3}">'.format(json.id, json.tag, selected ? 'active' : '', json.index);
                    _html += '<label class="layui-form-label" style="width: {1}px;{2}">{0}:</label>'.format( json.label,json.labelWidth,_hideLabel);
                    if (json.hideLabel) {
                        _html += '<div class="layui-input-block" style="margin-left: 0px">';
                    } else {
                        _html += '<div class="layui-input-block" style="margin-left: {0}px">'.format(json.labelWidth);
                    }
                    if (json.disabled) {
                        _html += '<div class="iceEditor-disabled"></div>';
                    }
                    _html += '<input name="{0}" id="{6}" value="{1}" placeholder="{3}" class="layui-input{5}" lay-filter="{6}" lay-vertype="tips" {4} style="width:{2}">'
                        .format(json.id, json.defaultValue ? json.defaultValue : '', json.width, json.placeholder, _disabled, _disabledClass,json.tag + json.id);
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
                    scriptHtmlCode += ['iconPicker.render({'
                        ,'elem:"#' + json.tag + json.id + '",'
                        ,'type:"fontClass",'
                        ,'search:' + json.iconPickerSearch + ''
                        ,'page:' + json.iconPickerPage + ''
                        ,'limit:' + json.iconPickerLimit + ''
                        ,'cellWidth:' + json.iconPickerCellWidth + ''
                        ,' click: function (data) {},'
                        ,'success: function(d) {}'
                        ,'});'
                        ,'iconPicker.checkIcon(' + json.tag + json.id + ',"");'].join('');
                    return scriptHtmlCode;
                }
            },
            cron: {
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
                    var _disabled = json.disabled ? 'disabled=""' : '';
                    var _disabledClass = json.disabled ? ' layui-disabled' : '';
                    var _required = json.required ? 'required' : '';
                    var _hideLabel = json.hideLabel ? 'display: none;' : '';
                    var _width = json.width.replace(/[^\d]/g,'');
                    if(''!=_width){
                        _width = 100 - parseInt(_width);
                    }
                    var _html = '<div id="{0}" class="layui-form-item {2}"  data-id="{0}" data-tag="{1}" data-index="{3}">'.format(json.id, json.tag, selected ? 'active' : '', json.index);
                    _html += '<label class="layui-form-label {0}" style="width: {3}px;{4}"><span style="color:red;">{2}</span>{1}:</label>'.format(json.required ? 'layui-form-required' : '', json.label,json.required ? '*' : '',json.labelWidth,_hideLabel);
                    if (json.hideLabel) {
                        _html += '<div class="layui-input-block" style="width:calc({0});margin-left: 0px;">'.format(json.width);
                    } else {
                        _html += '<div class="layui-input-block" style="width:calc({0} - {1}px);margin-left: {1}px;">'.format(json.width,json.labelWidth);
                    }
                    _html += '<input type="cronExpression" name="{0}" id="{5}" value="{1}" lay-verify="{6}" placeholder="{2}" class="layui-input{4}" lay-filter="iconPicker" lay-vertype="tips" {3}>'
                        .format(json.id, json.defaultValue ? json.defaultValue : '', json.placeholder, _disabled, _disabledClass,json.tag + json.id,_required);
                    if (!json.disabled) {
                        _html += '<button id="{0}-button" style="position: absolute;top: 0;right: 0px;cursor: pointer;" type="button" class="layui-btn">生成</button>'.format(json.tag + json.id);
                    }
                    _html += '</div>';
                    _html += '</div>';
                    elem.append(_html);
                    cron.render({
                        elem: "#" + json.tag + json.id + "-button", // 绑定元素
                        url: json.cronUrl, // 获取最近运行时间的接口
                        done: function (cronStr) {
                            $("#" + json.tag + json.id).val(cronStr);
                        },
                    });
                },
                /**
                 * 根据json对象更新html对象
                 * @param {object} json 变更后的json属性
                 * @param {object} that 实例对象
                 * */
                update: function (json,that) {
                    var _disabled = json.disabled ? 'disabled=""' : '';
                    var _disabledClass = json.disabled ? ' layui-disabled' : '';
                    var _required = json.required ? 'required' : '';
                    var $block = $('#' + json.id + ' .layui-input-block');
                    var $label = $('#' + json.id + ' .layui-form-label');
                    $block.empty();
                    $label.empty();
                    if (json.hideLabel) {
                        $label.css("display","none");
                        $block.css("margin-left","0px");
                        $block.css({width: 'calc({0})'.format(json.width)});
                    } else {
                        $label.css("display","block");
                        $block.css("margin-left",json.labelWidth);
                        $block.css({width: 'calc({0} - {1}px)'.format(json.width,json.labelWidth)});
                    }
                    $label.css("width",json.labelWidth);
                    if (json.required) {
                        $label.append('<span style="color:red;">*</span>');
                    }
                    $label.append(json.label + ":");
                    var _html = '';
                    //重绘设计区改id下的所有元素
                    _html += '<input name="{0}" value="{1}" id="{5}" placeholder="{2}" class="layui-input{4}" lay-verify="{6}" lay-filter="iconPicker" lay-vertype="tips" {3}>'
                        .format(json.id, json.defaultValue ? json.defaultValue : '', json.placeholder, _disabled, _disabledClass,json.tag + json.id,_required);
                    if (!json.disabled) {
                        var _width = json.width.replace(/[^\d]/g,'');
                        if(''!=_width){
                            _width = 100 - parseInt(_width);
                        }
                        _html += '<button id="{0}-button" style="position: absolute;top: 0;right: 0px;cursor: pointer;" type="button" class="layui-btn">生成</button>'.format(json.tag + json.id);
                        $block.append(_html);
                        cron.render({
                            elem: "#" + json.tag + json.id + "-button", // 绑定元素
                            url: json.cronUrl, // 获取最近运行时间的接口
                            done: function (cronStr) {
                                $("#" + json.tag + json.id).val(cronStr);
                            },
                        });
                    } else {
                        $block.append(_html);
                    }
                },
                /**
                 * 根据components组件对象获取组件属性
                 * @param {object} id 所属组件id
                 * @param {object} index 所属对象组件索引
                 * @param {object} that 实例对象
                 * */
                jsonData: function (id, index, that) {
                    var _json = JSON.parse(JSON.stringify(formField.components.cron));
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
                    var _disabled = json.disabled ? 'disabled=""' : '';
                    var _disabledClass = json.disabled ? ' layui-disabled' : '';
                    var _required = json.required ? 'required' : '';
                    var _hideLabel = json.hideLabel ? 'display: none;' : '';
                    var _width = json.width.replace(/[^\d]/g,'');
                    if(''!=_width){
                        _width = 100 - parseInt(_width);
                    }
                    var _html = '<div id="{0}" class="layui-form-item {2}"  data-id="{0}" data-tag="{1}" data-index="{3}">'.format(json.id, json.tag, selected ? 'active' : '', json.index);
                    _html += '<label class="layui-form-label {0}" style="width: {3}px;{4}"><span style="color:red;">{2}</span>{1}:</label>'.format(json.required ? 'layui-form-required' : '', json.label,json.required ? '*' : '',json.labelWidth,_hideLabel);
                    if (json.hideLabel) {
                        _html += '<div class="layui-input-block" style="width:calc({0});margin-left: 0px;">'.format(json.width);
                    } else {
                        _html += '<div class="layui-input-block" style="width:calc({0} - {1}px);margin-left: {1}px;">'.format(json.width,json.labelWidth);
                    }
                    _html += '<input type="cronExpression" name="{0}" id="{5}" value="{1}" lay-verify="{6}" placeholder="{2}" class="layui-input{4}" lay-filter="iconPicker" lay-vertype="tips" {3}>'
                        .format(json.id, json.defaultValue ? json.defaultValue : '', json.placeholder, _disabled, _disabledClass,json.tag + json.id,_required);
                    if (!json.disabled) {
                        _html += '<button id="{0}-button" style="position: absolute;top: 0;right: 0px;cursor: pointer;" type="button" class="layui-btn">生成</button>'.format(json.tag + json.id);
                    }
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
                    scriptHtmlCode += ['cron.render({'
                        ,'elem:"#' + json.tag + json.id + '-button",'
                        ,'url:' + json.cronUrl + ','
                        ,'done: function (cronStr) {'
                        ,'$("#' +  json.tag + json.id + '").val(cronStr);'
                        ,'},'
                        ,'});'].join('');
                    return scriptHtmlCode;
                }
            },
            sign: {
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
                    var _hideLabel = json.hideLabel ? 'display: none;' : '';
                    var _html = '<div id="{0}" class="layui-form-item {2}"  data-id="{0}" data-tag="{1}" data-index="{3}">'.format(json.id, json.tag, selected ? 'active' : '', json.index);
                    _html += '<label class="layui-form-label" style="width: {1}px;{2}">{0}:</label>'.format(json.label,json.labelWidth,_hideLabel);
                    if (json.hideLabel) {
                        _html += '<div class="layui-input-block" style="margin-left: 0px">';
                    } else {
                        _html += '<div class="layui-input-block" style="margin-left: {0}px">'.format(json.labelWidth);
                    }
                    if (json.disabled) {
                        _html += '<button id="{0}" type="button" class="layui-btn layui-btn-normal layui-btn-disabled custom-zc"><i class="layui-icon {1}"></i> {2}</button>'.format(json.id + json.tag ,json.buttonIcon,json.buttonVlaue);
                    }else {
                        _html += '<button id="{0}" type="button" class="layui-btn  layui-btn-normal custom-zc"><i class="layui-icon {1}"></i> {2}</button>'.format(json.id + json.tag ,json.buttonIcon,json.buttonVlaue);
                    }
                    if (json.data !== "") {
                        _html += '<div class="signImg"><img src="{0}"></div>'.format(json.data);
                    }
                    _html += '</div>';
                    _html += '</div>';
                    elem.append(_html);
                    $('#' + json.id + json.tag).click(function () {
                        layer.open({
                            type: 2,
                            title: '手写签名',
                            btn: ['保存','关闭'], //可以无限个按钮
                            yes: function(index, layero){
                                //do something
                                var iframe = window['layui-layer-iframe' + index];
                                var data = iframe.getCanvasData();
                                json.data = data;
                                that.components[json.tag].update(json,that);
                                layer.close(index); //如果设定了yes回调，需进行手工关闭
                            },
                            btn2: function (index, layero) {
                                layer.close(index);
                            },
                            closeBtn: 1, //不显示关闭按钮
                            shade: [0],
                            area: ['60%', '60%'],
                            offset: 'auto', //右下角弹出
                            anim: 2,
                            content: ['./handwrittenSignature.html', 'yes'], //iframe的url，no代表不显示滚动条
                            success:function (layero,index) {
                            }
                        });
                    });
                },
                /**
                 * 根据json对象更新html对象
                 * @param {object} json 变更后的json属性
                 * @param {object} that 实例对象
                 * */
                update: function (json,that) {
                    var $block = $('#' + json.id + ' .layui-input-block');
                    var $label = $('#' + json.id + ' .layui-form-label');
                    $block.empty();
                    $label.empty();
                    if (json.hideLabel) {
                        $label.css("display","none");
                        $block.css("margin-left","0px");
                        $block.css({width: 'calc({0})'.format(json.width)});
                    } else {
                        $label.css("display","block");
                        $block.css("margin-left",json.labelWidth);
                        $block.css({width: 'calc({0} - {1}px)'.format(json.width,json.labelWidth)});
                    }
                    $label.css("width",json.labelWidth);
                    $label.append(json.label + ":");
                    var _html = '';
                    //重绘设计区改id下的所有元素
                    if (json.disabled) {
                        _html += '<button id="{0}" type="button" class="layui-btn layui-btn-normal layui-btn-disabled custom-zc"><i class="layui-icon {1}"></i> {2}</button>'.format(json.id + json.tag ,json.buttonIcon,json.buttonVlaue);
                    }else {
                        _html += '<button id="{0}" type="button" class="layui-btn  layui-btn-normal custom-zc"><i class="layui-icon {1}"></i> {2}</button>'.format(json.id + json.tag ,json.buttonIcon,json.buttonVlaue);
                    }
                    if (json.data !== "") {
                        _html += '<div class="signImg"><img src="{0}"></div>'.format(json.data);
                    }
                    $block.append(_html);
                },
                /**
                 * 根据components组件对象获取组件属性
                 * @param {object} id 所属组件id
                 * @param {object} index 所属对象组件索引
                 * @param {object} that 实例对象
                 * */
                jsonData: function (id, index, that) {
                    //分配一个新的ID
                    var _json = JSON.parse(JSON.stringify(formField.components.sign));
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
                    var _hideLabel = json.hideLabel ? 'display: none;' : '';
                    var _html = '<div id="{0}" class="layui-form-item {2}"  data-id="{0}" data-tag="{1}" data-index="{3}">'.format(json.id, json.tag, selected ? 'active' : '', json.index);
                    _html += '<label class="layui-form-label" style="width: {1}px;{2}">{0}:</label>'.format(json.label,json.labelWidth,_hideLabel);
                    if (json.hideLabel) {
                        _html += '<div class="layui-input-block" style="margin-left: 0px">';
                    } else {
                        _html += '<div class="layui-input-block" style="margin-left: {0}px">'.format(json.labelWidth);
                    }
                    if (json.disabled) {
                        _html += '<button id="{0}" type="button" class="layui-btn layui-btn-normal layui-btn-disabled custom-zc"><i class="layui-icon {1}"></i> {2}</button>'.format(json.id + json.tag ,json.buttonIcon,json.buttonVlaue);
                    }else {
                        _html += '<button id="{0}" type="button" class="layui-btn  layui-btn-normal custom-zc"><i class="layui-icon {1}"></i> {2}</button>'.format(json.id + json.tag ,json.buttonIcon,json.buttonVlaue);
                    }
                    if (json.data !== "") {
                        _html += '<div class="signImg"><img src="{0}"></div>'.format(json.data);
                    }
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
                    return scriptHtmlCode;
                }
            },
            editor: {
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
                    var _hideLabel = json.hideLabel ? 'display: none;' : '';
                    var _html = '<div id="{0}" class="layui-form-item layui-form-text {2}" style="width: {4}"  data-id="{0}" data-tag="{1}" data-index="{3}">'.format(json.id, json.tag, selected ? 'active' : '', json.index,json.width);
                    _html += '<label class="layui-form-label" style="width: {1}px;{2}">{0}:</label>'.format(json.label,json.width,_hideLabel);
                    _html += '<div class="layui-input-block">';
                    _html += '<div id="{0}"></div>'.format(json.tag + json.id);
                    _html += '</div>';
                    _html += '</div>';
                    elem.append(_html);
                    var e = new ice.editor(json.tag + json.id);
                    e.width=json.width;   //宽度
                    e.height=json.height;  //高度
                    e.uploadUrl=json.uploadUrl; //上传文件路径
                    e.disabled=json.disabled;
                    e.menu = json.menu;
                    e.create();
                    e.setValue(json.defaultValue);
                    if (that.config.viewOrDesign) {
                        iceEditorObjects[json.id] = e;
                    }
                },
                /**
                 * 根据json对象更新html对象
                 * @param {object} json 变更后的json属性
                 * @param {object} that 实例对象
                 * */
                update: function (json,that) {
                    var $label = $('#' + json.id + ' .layui-form-label');
                    $label.empty();
                    $label.css("width",json.width);
                    $label.append(json.label + ":");
                    if (json.hideLabel) {
                        $label.css("display","none");
                    } else {
                        $label.css("display","block");
                    }
                    if (that.config.viewOrDesign) {
                        var e = iceEditorObjects[json.id];
                        e.setValue(json.defaultValue);
                    } else {
                        var $block = $('#' + json.id + ' .layui-input-block');
                        $block.empty();
                        var _html = '<div id="{0}"></div>'.format(json.tag + json.id);
                        $block.append(_html);
                        var e = new ice.editor(json.tag + json.id);
                        e.width=json.width;   //宽度
                        e.height=json.height;  //高度
                        e.uploadUrl=json.uploadUrl; //上传文件路径
                        e.disabled=json.disabled;
                        e.menu = json.menu;
                        e.create();
                        e.setValue(json.defaultValue);
                    }
                },
                /**
                 * 根据components组件对象获取组件属性
                 * @param {object} id 所属组件id
                 * @param {object} index 所属对象组件索引
                 * @param {object} that 实例对象
                 * */
                jsonData: function (id, index, that) {
                    //分配一个新的ID
                    var _json = JSON.parse(JSON.stringify(formField.components.editor));
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
                    var _hideLabel = json.hideLabel ? 'display: none;' : '';
                    var _html = '<div id="{0}" class="layui-form-item layui-form-text {2}" style="width: {4}"  data-id="{0}" data-tag="{1}" data-index="{3}">'.format(json.id, json.tag, selected ? 'active' : '', json.index,json.width);
                    _html += '<label class="layui-form-label" style="width: {1}px;{2}">{0}:</label>'.format(json.label,json.width,_hideLabel);
                    _html += '<div class="layui-input-block">';
                    _html += '<div id="{0}"></div>'.format(json.tag + json.id);
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
                    scriptHtmlCode += ['var e = new ice.editor('+  json.tag + json.id +');'
                        ,'e.width=' + json.width + ';   //宽度'
                        ,'e.height=' + json.height + ';  //高度'
                        ,'e.uploadUrl=' + json.uploadUrl + '; //上传文件路径'
                        ,'e.disabled=' + json.disabled + ';'
                        ,'e.menu = ' + json.menu + ';'
                        ,'e.create();'].join('');
                    return scriptHtmlCode;
                }
            },
            grid: {
                /**
                 * 根据json对象生成html对象
                 * @param {object} json 当前组件的json属性
                 * @param {boolean} selected 是否被选中
                 * @param {object} elem 表单面板jquery对象
                 * @param {object} that 当前实例模块对象
                 * */
                render: function (json, selected,elem,that) {
                    if (selected === undefined) {
                        selected = false;
                    }
                    var _selected = selected ? 'active' : '';
                    var _html = '<div id="{0}" class="layui-form-item layui-row grid {2}"  data-id="{0}" data-tag="{1}" data-index="{3}" >'.format(json.id, json.tag, selected ? 'active' : '', json.index);
                    var colClass = 'layui-col-md6';
                    if (json.columns.length == 3) {
                        colClass = 'layui-col-md4';
                    } else if (json.columns.length == 4) {
                        colClass = 'layui-col-md3';
                    }
                    for (var i = 0; i < json.columns.length; i++) {
                        _html += '<div class="{2} widget-col-list column{3}{0}" data-index="{0}" data-parentindex="{1}">'.format(i, json.index, colClass,json.id);
                        //some html
                        _html += '</div>';
                    }
                    _html += '</div>';
                    elem.append(_html);
                    that.bindGridSortEvent(json);

                },
                /**
                 * 根据json对象更新html对象
                 * @param {object} json 变更后的json属性
                 * @param {object} that 实例对象
                 * */
                update: function (json,that) {
                    var $block = $('#' + json.id);
                    $block.empty();
                    var colClass = 'layui-col-md6';
                    if (json.columns.length == 3) {
                        colClass = 'layui-col-md4';
                    } else if (json.columns.length == 4) {
                        colClass = 'layui-col-md3';
                    }
                    var _html = '';
                    for (var i = 0; i < json.columns.length; i++) {
                        _html += '<div class="{2} widget-col-list column{3}{0}" data-index="{0}" data-parentindex="{1}">'.format(i, json.index, colClass,json.id);
                        //some html
                        _html += '</div>';
                    }
                    $block.append(_html);
                    that.renderForm();
                },
                /**
                 * 根据components组件对象获取组件属性
                 * @param {object} id 所属组件id
                 * @param {object} index 所属对象组件索引
                 * @param {object} that 实例对象
                 * */
                jsonData: function (id, index,that) {
                    //分配一个新的ID 默认是一个一行两列的布局对象
                    var _json = JSON.parse(JSON.stringify(formField.components.grid));
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
                    var _selected = selected ? 'active' : '';
                    var _html = '<div id="{0}" class="layui-form-item layui-row grid {2}"  data-id="{0}" data-tag="{1}" data-index="{3}" >'.format(json.id, json.tag, _selected ? 'active' : '', json.index);
                    var colClass = 'layui-col-md6';
                    if (json.columns.length == 3) {
                        colClass = 'layui-col-md4';
                    } else if (json.columns.length == 4) {
                        colClass = 'layui-col-md3';
                    }
                    for (var i = 0; i < json.columns.length; i++) {
                        _html += '<div class="{2} widget-col-list column{3}{0}" data-index="{0}" data-parentindex="{1}">'.format(i, json.index, colClass,json.id);
                        //some html
                        _html += '</div>';
                    }
                    _html += '</div>';
                    return _html;
                },
                /**
                 * 根据json对象生成js文本
                 * @param {object} json 变更后的json属性
                 * @param {object} that 实例对象
                 * */
                generateScript:function (json,that) {
                    return '';
                }
            },
        }

        /* 初始化 json 对象获取对应的属性*/
        Class.prototype.initCommonProperty = function (json) {
            var that = this
                , options = that.config;
            for (var key in json) {
                if (key === 'index') {
                    continue;
                }
                switch (key) {
                    case 'id':
                        $('#id').click(function () {
                            //例子2
                            layer.prompt({
                                formType: 0,
                                value: options.selectItem.id,
                                title: '请输入更新后的ID',
                            }, function(value, index, elem){
                                var _checkid = that.findJsonItem(options.data, value);
                                if (_checkid === undefined) {
                                    var findJsonItem = that.findJsonItem(options.data, options.selectItem.id);
                                    findJsonItem.id = value;
                                    that.renderForm();
                                } else {
                                    //提示层
                                    layer.msg('ID已经存在');
                                }
                                layer.close(index);
                            });
                        });
                        $('#id').mouseover(function(){
                            layer.tips('请点击修改id', '#id',);
                        });
                        break;
                    case 'labelWidth':
                        //定义初始值
                        slider.render({
                            elem: '#labelWidth',
                            value: json.labelWidth, //初始值
                            min: 80,
                            max: 300,
                            step: 1,
                            input:true,
                            change: function(value){
                                json.labelWidth = value;
                                that.components[json.tag].update(json,that);
                            }
                        });
                        break;
                    case 'width':
                        //定义初始值
                        slider.render({
                            elem: '#width',
                            value: json.width.replace("%",""), //初始值
                            min: 20,
                            max: 100,
                            step: 1,
                            input:true,
                            change: function(value){
                                json.width = value + "%";
                                that.components[json.tag].update(json,that);
                            }
                        });
                        break;
                    case 'options':
                        var sortable = Sortable.create(document.getElementById(options.selectItem.tag), {
                            group: {
                                name: 'propertygroup',
                            },
                            ghostClass: "ghost",
                            handle: '.select-option-drag',
                            dataIdAttr: 'data-index',
                            animation: 150,
                            onEnd: function (evt) {
                                if (options.selectItem === undefined) {
                                    return;
                                }
                                var indexArray = sortable.toArray();
                                var newJson = [];
                                for (var i = 0; i < indexArray.length; i++) {
                                    newJson.push(options.selectItem.options[indexArray[i]]);
                                }
                                options.selectItem.options = newJson;
                                var $select = $('#' + options.selectItem.tag);
                                $select.empty();
                                that.components[options.selectItem.tag].update(options.selectItem,that);
                                that.components[options.selectItem.tag].property(options.selectItem,that);
                                form.render('select');
                                form.render('radio');
                                form.render('checkbox');
                            }
                        });
                        that.addOptionEvent(json);
                        break;
                    case 'dataMaxValue':
                        laydate.render({
                            elem: '#dataMaxValue' + json.tag + json.id,
                            format: 'yyyy-MM-dd',
                            btns: ['now','confirm'],
                            value: json.dataMaxValue,
                            done: function(value, date, endDate){
                                json.dataMaxValue = value;
                                that.components[json.tag].update(json,that);
                            }
                        });
                        break;
                    case 'dataMinValue':
                        laydate.render({
                            elem: '#dataMinValue' + json.tag + json.id,
                            format: 'yyyy-MM-dd',
                            btns: ['now','confirm'],
                            value: json.dataMinValue,
                            done: function(value, date, endDate){
                                json.dataMinValue = value;
                                that.components[json.tag].update(json,that);
                            }
                        });
                        break;
                    case 'dateDefaultValue':
                        laydate.render({
                            elem: '#dateDefaultValue' + json.tag + json.id,
                            type: json.dateType,
                            format: json.dateFormat,
                            value: json.dateDefaultValue,
                            done: function(value, date, endDate){
                                json.dateDefaultValue = value;
                                that.components[json.tag].update(json,that);
                            }
                        });
                        break;
                    case 'dateRangeDefaultValue':
                        laydate.render({
                            elem: '#dateRangeDefaultValue' + json.tag + json.id,
                            type: json.dateType,
                            format: json.dateFormat,
                            value: json.dateRangeDefaultValue,
                            range:"-",
                            done: function(value, date, endDate){
                                json.dateRangeDefaultValue = value;
                                that.components[json.tag].update(json,that);
                            }
                        });
                        break;
                    case 'buttonIcon':
                        iconPicker.render({
                            // 选择器，推荐使用input
                            elem: '#' + json.tag + json.id + "property",
                            // 数据类型：fontClass/unicode，推荐使用fontClass
                            type: 'fontClass',
                            // 是否开启搜索：true/false，默认true
                            search: true,
                            // 是否开启分页：true/false，默认true
                            page: true,
                            // 每页显示数量，默认12
                            limit: 12,
                            // 每个图标格子的宽度：'43px'或'20%'
                            cellWidth: '43px',
                            // 点击回调
                            click: function (data) {
                                json.buttonIcon = data.icon;
                                that.components[json.tag].update(json,that);
                            },
                            // 渲染成功后的回调
                            success: function(d) {
                                //console.log(d);
                            }
                        });
                        break;
                    case 'whiteSpace':
                        slider.render({
                            elem:'#' + json.tag + json.id + "property",
                            value: json.whiteSpace, //初始值
                            min: 30,
                            max: 500,
                            step: 1,
                            input:true,
                            change: function(value){
                                json.whiteSpace = value;
                                that.components[json.tag].update(json,that);
                            }
                        });
                        break;
                    case 'colorSelection':
                        colorpicker.render({
                            elem: '#' + json.tag + json.id + "property"
                            ,color: json.colorSelection
                            ,format: 'rgb'
                            ,predefine: true
                            ,alpha: true
                            ,done: function (color) {
                                json.colorSelection = color;
                                that.components[json.tag].update(json,that);
                            }
                        });
                        break;
                    case 'menu':
                        $('#menu').click(function () {
                            layer.open({
                                type: 2,
                                title: '头部菜单',
                                btn: ['保存','关闭'], //可以无限个按钮
                                yes: function(index, layero){
                                    //do something
                                    var iframe = window['layui-layer-iframe' + index];
                                    var checkData = iframe.getCheckData();
                                    json.menu = checkData;
                                    that.components[json.tag].update(json,that);
                                    layer.close(index); //如果设定了yes回调，需进行手工关闭
                                },
                                btn2: function (index, layero) {
                                    layer.close(index);
                                },
                                closeBtn: 1, //不显示关闭按钮
                                shade: [0],
                                area: ['80%', '80%'],
                                offset: 'auto', //右下角弹出
                                anim: 2,
                                content: ['./editorMenu.html', 'yes'], //iframe的url，no代表不显示滚动条
                                success:function (layero,index) {
                                    var iframe = window['layui-layer-iframe' + index];
                                    iframe.child(staticField.iceEditMenus)
                                }
                            });
                        });
                        break;
                    default:
                        break;
                }
            }
        }

        /* 添加选项事件*/
        Class.prototype.addOptionEvent = function (json) {
            var that = this
                , options = that.config;
            $('#select-option-add').on('click', function () {
                //添加html
                json.options.splice(json.options.length + 1, 0, {text: 'option', value: 'value', checked: false});
                var _htmloption = '';
                _htmloption += '<div class="layui-form-item option select-options" data-index="{0}">'.format(json.options.length - 1);
                _htmloption += '  <div class="layui-inline" style="width: 30px; margin-right: 0px;">';
                if (json.tag === 'checkbox') {
                    _htmloption += '   <input type="checkbox" name="{0}" lay-skin="primary" title="">'.format(json.tag);
                } else {
                    _htmloption += '   <input type="radio" name="{0}" >'.format(json.tag);
                }
                _htmloption += '  </div>';
                _htmloption += '  <div class="layui-inline" style="margin-right: 0px;width:110px;">';
                _htmloption += '   <input type="text" name="{0}-text"  autocomplete="off" value="{1}" class="layui-input">'.format(json.tag, 'option');
                _htmloption += '  </div>';
                _htmloption += '  <div class="layui-inline" style="margin-right: 0px;width:110px;">';
                _htmloption += '   <input type="text" name="{0}-value"  autocomplete="off" value="{1}" class="layui-input">'.format(json.tag, 'value');
                _htmloption += '  </div>';
                _htmloption += '  <div class="layui-inline" style="margin-right: 0px;">';
                _htmloption += '   <i class="layui-icon layui-icon-slider select-option-drag" style="color:blue;font-size:20px;"></i>';
                _htmloption += '   <i class="layui-icon layui-icon-delete select-option-delete" style="color:red;font-size:20px;"></i>';
                _htmloption += '  </div>';
                _htmloption += '</div>';
                $('#columnProperty .select-options').last().after(_htmloption);
                //更新设计区节点
                that.components[json.tag].update(json,that);
                if (json.tag === 'checkbox') {
                    form.render('checkbox');
                } else if (json.tag === 'radio' || json.tag == 'carousel') {
                    form.render('radio');
                } else if (json.tag == 'select') {
                    form.render('select');
                    form.render('radio');
                }
            });
            //委托监听先关闭在增加 click
            $(document).off('click', '#columnProperty .select-option-delete').on('click', '#columnProperty .select-option-delete', function (e) {
                e.preventDefault();
                e.stopPropagation();
                //从数据源 options.data 中删除节点
                if (json.options.length <= 1) {
                    layer.msg('已达到最低选项，不能继续删除');
                    return;
                }
                var _index = $(this).closest('.layui-form-item')[0].dataset.index;
                if (_index !== undefined) {
                    json.options.splice(_index, 1);//删除此节点
                }
                var checkedDefual = true;
                for (var i = 0; i < json.options.length; i++) {
                    if (json.options[i].checked) {
                        checkedDefual = false
                    }
                }
                if (checkedDefual) {
                    json.options[0].checked = true;
                }
                $('#' + json.tag).empty();
                var _html = '';
                for (var i = 0; i < json.options.length; i++) {
                    _html += '<div class="layui-form-item option select-options" data-index="{0}">'.format(i);
                    _html += '  <div class="layui-inline" style="width: 30px; margin-right: 0px;">';
                    if (json.tag === 'checkbox') {
                        if (json.options[i].checked) {
                            _html += '   <input type="checkbox" name="{0}" lay-skin="primary" title="" checked>'.format(json.tag);
                        } else {
                            _html += '   <input type="checkbox" name="{0}" lay-skin="primary" title="">'.format(json.tag);
                        }
                    } else {
                        if (json.options[i].checked) {
                            _html += '   <input type="radio" name="{0}" checked>'.format(json.tag);
                        } else {
                            _html += '   <input type="radio" name="{0}">'.format(json.tag);
                        }
                    }
                    _html += '  </div>';
                    _html += '  <div class="layui-inline" style="margin-right: 0px;width:110px;">';
                    _html += '   <input type="text" name="{0}-text"  autocomplete="off" value="{1}" class="layui-input">'.format(json.tag, json.options[i].text);
                    _html += '  </div>';
                    _html += '  <div class="layui-inline" style="margin-right: 0px;width:110px;">';
                    _html += '   <input type="text" name="{0}-value"  autocomplete="off" value="{1}" class="layui-input">'.format(json.tag, json.options[i].value);
                    _html += '  </div>';
                    _html += '  <div class="layui-inline" style="margin-right: 0px;">';
                    _html += '   <i class="layui-icon layui-icon-slider select-option-drag" style="color:blue;font-size:20px;"></i>';
                    _html += '   <i class="layui-icon layui-icon-delete select-option-delete" style="color:red;font-size:20px;"></i>';
                    _html += '  </div>';
                    _html += '</div>';
                }
                $('#' + json.tag).append(_html);
                //更新设计区节点
                that.components[json.tag].update(json,that);
                if (json.tag === 'checkbox') {
                    form.render('checkbox');
                } else if (json.tag === 'radio') {
                    form.render('radio');
                } else if (json.tag == 'select') {
                    form.render('select');
                    form.render('radio');
                }
            });
        }

        /* 根据 json 对象获取对应的属性的html*/
        Class.prototype.renderCommonProperty = function (json) {
            $('#columnProperty').empty();
            var _html = '';
            for (var key in json) {
                if (key === 'index') {
                    continue;
                }
                switch (key) {
                    case 'tag':
                    case 'uploadUrl':
                    case 'document':
                    case 'interval':
                    case 'cronUrl':
                        _html += '<div class="layui-form-item" >';
                        _html += '  <label class="layui-form-label">{0}</label>'.format(staticField.lang[key]);
                        _html += '  <div class="layui-input-block">';
                        if (key === 'tag') {
                            _html += '    <input type="text" readonly id="{0}" name="{0}" value="{1}" required lay-verify="required" placeholder="请输入内容" autocomplete="off" class="layui-input">'
                                .format(key, json[key] == undefined ? '' : json[key]);
                        } else {
                            _html += '    <input type="text" id="{0}" name="{0}" value="{1}" required lay-verify="required" placeholder="请输入内容" autocomplete="off" class="layui-input">'
                                .format(key, json[key] == undefined ? '' : json[key]);
                        }
                        _html += '  </div>';
                        _html += '</div>';
                        break;
                    case 'readonly':
                        var yes = "只读";
                        var no = "可写";
                        _html += '<div class="layui-form-item" >';
                        _html += '  <label class="layui-form-label">{0}</label>'.format(staticField.lang[key]);
                        _html += '  <div class="layui-input-block">';
                        _html += '    <input type="checkbox" id="{1}" {0} name="{1}" lay-skin="switch" lay-text="{2}|{3}">'.format(json[key] ? 'checked' : '', key, yes, no);
                        _html += '  </div>';
                        _html += '</div>';
                        break;
                    case 'disabled':
                    case 'hideLabel':
                        var yes = "隐藏";
                        var no = "显示";
                        _html += '<div class="layui-form-item" >';
                        _html += '  <label class="layui-form-label">{0}</label>'.format(staticField.lang[key]);
                        _html += '  <div class="layui-input-block">';
                        _html += '    <input type="checkbox" id="{1}" {0} name="{1}" lay-skin="switch" lay-text="{2}|{3}">'.format(json[key] ? 'checked' : '', key, yes, no);
                        _html += '  </div>';
                        _html += '</div>';
                        break;
                    case 'required':
                        var yes = "必填";
                        var no = "可选";
                        _html += '<div class="layui-form-item" >';
                        _html += '  <label class="layui-form-label">{0}</label>'.format(staticField.lang[key]);
                        _html += '  <div class="layui-input-block">';
                        _html += '    <input type="checkbox" id="{1}" {0} name="{1}" lay-skin="switch" lay-text="{2}|{3}">'.format(json[key] ? 'checked' : '', key, yes, no);
                        _html += '  </div>';
                        _html += '</div>';
                        break;
                    case 'expression':
                        _html += '<div class="layui-form-item" >';
                        _html += '  <label class="layui-form-label">{0}</label>'.format(staticField.lang[key]);
                        _html += '  <div class="layui-input-block">';
                        _html += '<select name="{0}" lay-verify="required">'.format(key);
                        for (var i = 0; i < staticField.expressions.length; i++) {
                            if (staticField.expressions[i].value === json.expression) {
                                _html += '<option value="{0}" selected="">{1}</option>'.format(staticField.expressions[i].value, staticField.expressions[i].text);
                            } else {
                                _html += '<option value="{0}">{1}</option>'.format(staticField.expressions[i].value, staticField.expressions[i].text);
                            }
                        }
                        _html += '</select>'
                        _html += '  </div>';
                        _html += '</div>';
                        break;
                    case 'autoplay':
                    case 'iconPickerSearch':
                    case 'iconPickerPage':
                    case 'isInput':
                        var yes = "是";
                        var no = "否";
                        _html += '<div class="layui-form-item" >';
                        _html += '  <label class="layui-form-label">{0}</label>'.format(staticField.lang[key]);
                        _html += '  <div class="layui-input-block">';
                        _html += '    <input type="checkbox" id="{1}" {0} name="{1}" lay-skin="switch" lay-text="{2}|{3}">'.format(json[key] ? 'checked' : '', key, yes, no);
                        _html += '  </div>';
                        _html += '</div>';
                        break;
                    case 'defaultValue':
                    case 'label':
                    case 'height':
                    case 'placeholder':
                    case 'document':
                    case 'minValue':
                    case 'maxValue':
                    case 'stepValue':
                    case 'rateLength':
                    case 'iconPickerLimit':
                    case 'iconPickerCellWidth':
                    case 'buttonVlaue':
                        _html += '<div class="layui-form-item" >';
                        _html += '  <label class="layui-form-label">{0}</label>'.format(staticField.lang[key]);
                        _html += '  <div class="layui-input-block">';
                        _html += '    <input type="text" id="{0}" name="{0}" value="{1}" required lay-verify="required" placeholder="请输入{2}" autocomplete="off" class="layui-input">'
                            .format(key, json[key] == undefined ? '' : json[key], staticField.lang[key]);
                        _html += '  </div>';
                        _html += '</div>';
                        break;
                    case 'width':
                    case 'labelWidth':
                        _html += '<div class="layui-form-item" >';
                        _html += '  <label class="layui-form-label">{0}</label>'.format(staticField.lang[key]);
                        _html += '  <div class="layui-input-block">';
                        _html += '<div id="{0}" class="widget-slider" style="top: 16px;"></div>'.format(key);
                        _html += '    <input type="hidden" id="{0}"  name="{0}" value="{1}" readonly="readonly" style="background:#eeeeee!important;width: 100%;" placeholder="请选择{2}" autocomplete="off" class="layui-input">'
                            .format(key + "-value", '', staticField.lang[key]);
                        _html += '  </div>';
                        _html += '</div>';
                        break;
                    case 'menu':
                        _html += '<div class="layui-form-item" >';
                        _html += '  <label class="layui-form-label">{0}</label>'.format(staticField.lang[key]);
                        _html += '  <div class="layui-input-block">';
                        _html += '    <input type="text" id="{0}" name="{0}" value="{1}" readonly="readonly" style="background:#eeeeee!important;width: 100%;" placeholder="{2}" autocomplete="off" class="layui-input">'
                            .format(key, '', staticField.lang[key]);
                        _html += '  </div>';
                        _html += '</div>';
                        break;
                    case 'id':
                        _html += '<div class="layui-form-item" >';
                        _html += '  <label class="layui-form-label">{0}</label>'.format(staticField.lang[key]);
                        _html += '  <div class="layui-input-block">';
                        _html += '    <input type="text" id="{0}" name="{0}" value="{1}" readonly="readonly" style="background:#eeeeee!important;width: 100%;" placeholder="{2}" autocomplete="off" class="layui-input">'
                            .format(key, '', json[key]);
                        _html += '  </div>';
                        _html += '</div>';
                        break;
                    case 'switchValue':
                        _html += '<div class="layui-form-item" >';
                        _html += '  <label class="layui-form-label">{0}</label>'.format(staticField.lang[key]);
                        _html += '  <div class="layui-input-block">';
                        _html += '     <input type="checkbox" name="{0}" {1} lay-skin="switch" lay-text="ON|OFF">'
                            .format(key, json[key] ? 'checked' : '');
                        _html += '  </div>';
                        _html += '</div>';
                        break;
                    case 'dateType':
                        _html += '<div class="layui-form-item" >';
                        _html += '  <label class="layui-form-label">{0}</label>'.format(staticField.lang[key]);
                        _html += '  <div class="layui-input-block">';
                        _html += '<select name="{0}" lay-verify="required">'.format(key);
                        for (var i = 0; i < staticField.dateTypes.length; i++) {
                            if (staticField.dateTypes[i].value === json.dateType) {
                                _html += '<option value="{0}" selected="">{1}</option>'.format(staticField.dateTypes[i].value,staticField.dateTypes[i].text);
                            } else {
                                _html += '<option value="{0}">{1}</option>'.format(staticField.dateTypes[i].value,staticField.dateTypes[i].text);
                            }
                        }
                        _html += '</select>'
                        _html += '  </div>';
                        _html += '</div>';
                        break;
                    case 'dateFormat':
                        _html += '<div class="layui-form-item" >';
                        _html += '  <label class="layui-form-label">{0}</label>'.format(staticField.lang[key]);
                        _html += '  <div class="layui-input-block">';
                        _html += '<select name="{0}" lay-verify="required">'.format(key);
                        for (var i = 0; i < staticField.dateFormats.length; i++) {
                            if (staticField.dateFormats[i] === json.dateFormat) {
                                _html += '<option value="{0}" selected="">{0}</option>'.format(staticField.dateFormats[i]);
                            } else {
                                _html += '<option value="{0}">{0}</option>'.format(staticField.dateFormats[i]);
                            }
                        }
                        _html += '</select>'
                        _html += '  </div>';
                        _html += '</div>';
                        break;
                    case 'contents':
                        //处理
                        _html += '<div class="layui-form-item" >';
                        _html += '  <label class="layui-form-label">{0}</label>'.format(staticField.lang[key]);
                        _html += '  <div class="layui-input-block">';
                        _html += '    <button style="margin: 5px 0px 0px 30px;" type="button" id="select-option-add" class="layui-btn layui-btn-primary layui-btn-sm"><i class="layui-icon layui-icon-addition"></i>增加选项</button>'
                        //_html += '    <button type="button" id="select-option-datasource" class="layui-btn layui-btn-primary layui-btn-sm"><i class="layui-icon layui-icon-addition"></i>设置数据源</button>'
                        _html += '  </div>';
                        _html += '</div>';
                        _html += '<div id="{0}">'.format(json.tag);
                        //选项
                        for (var i = 0; i < json.contents.length; i++) {
                            _html += '<div class="layui-form-item option contents-options" data-index="{0}">'.format(i);

                            _html += '  <div class="layui-inline" style="margin-right: 0px;width:220px; margin-left:30px;">';
                            _html += '   <input type="text" name="{0}-text"  autocomplete="off" value="{1}" class="layui-input">'.format(json.tag, json.contents[i]);
                            _html += '  </div>';
                            _html += '  <div class="layui-inline" style="margin-right: 0px;">';
                            _html += '   <i class="layui-icon layui-icon-slider contents-option-drag" style="color:blue;font-size:20px;"></i>';
                            _html += '   <i class="layui-icon layui-icon-delete contents-option-delete" style="color:red;font-size:20px;"></i>';
                            _html += '  </div>';
                            _html += '</div>';
                            //向 .option .layui-inline 添加drag事件并且必须设在 contents-option-drag 中才能拖动
                        }
                        _html += '</div>';
                        break;
                    case 'options':
                        //处理
                        _html += '<div class="layui-form-item" >';
                        _html += '  <label class="layui-form-label">{0}</label>'.format(staticField.lang[key]);
                        _html += '  <div class="layui-input-block">';
                        _html += '    <button style="margin: 5px 0px 0px 30px;" type="button" id="select-option-add" class="layui-btn layui-btn-primary layui-btn-sm"><i class="layui-icon layui-icon-addition"></i>增加选项</button>'
                        //_html += '    <button type="button" id="select-option-datasource" class="layui-btn layui-btn-primary layui-btn-sm"><i class="layui-icon layui-icon-addition"></i>设置数据源</button>'
                        _html += '  </div>';
                        _html += '</div>';
                        _html += '<div id="{0}">'.format(json.tag);
                        //选项
                        for (var i = 0; i < json.options.length; i++) {
                            _html += '<div class="layui-form-item option select-options" data-index="{0}">'.format(i);
                            _html += '  <div class="layui-inline" style="width: 30px; margin-right: 0px;">';
                            if (json.tag === 'checkbox') {
                                if (json.options[i].checked) {
                                    _html += '    <input type="checkbox" name="{0}" lay-skin="primary" title="" checked="">'.format(json.tag);
                                } else {
                                    _html += '    <input type="checkbox" name="{0}" lay-skin="primary" title="">'.format(json.tag);
                                }
                            } else {
                                if (json.options[i].checked) {
                                    _html += '   <input type="radio" name="{0}"  checked="">'.format(json.tag);
                                } else {
                                    _html += '   <input type="radio" name="{0}">'.format(json.tag);
                                }

                            }
                            _html += '  </div>';
                            _html += '  <div class="layui-inline" style="margin-right: 0px;width:110px;">';
                            _html += '   <input type="text" name="{0}-text"  autocomplete="off" value="{1}" class="layui-input">'.format(json.tag, json.options[i].text);
                            _html += '  </div>';
                            _html += '  <div class="layui-inline" style="margin-right: 0px;width:110px;">';
                            _html += '   <input type="text" name="{0}-value"  autocomplete="off" value="{1}" class="layui-input">'.format(json.tag, json.options[i].value);
                            _html += '  </div>';
                            _html += '  <div class="layui-inline" style="margin-right: 0px;">';
                            _html += '   <i class="layui-icon layui-icon-slider select-option-drag" style="color:blue;font-size:20px;"></i>';
                            _html += '   <i class="layui-icon layui-icon-delete select-option-delete" style="color:red;font-size:20px;"></i>';
                            _html += '  </div>';
                            _html += '</div>';
                            //向 .option .layui-inline 添加drag事件并且必须设在 select-option-drag 中才能拖动
                        }
                        _html += '</div>';
                        break;
                    case 'anim':
                        //处理
                        _html += '<div class="layui-form-item" >';
                        _html += '  <label class="layui-form-label">{0}</label>'.format(staticField.lang[key]);
                        _html += '  <div class="layui-input-block">';
                        _html += '<select name="{0}" lay-verify="required">'.format(key);
                        for (var i = 0; i < staticField.anims.length; i++) {
                            if (staticField.anims[i].value === json.anim) {
                                _html += ' <option value="{1}" selected="">{0}</option>'.format(staticField.anims[i].text, staticField.anims[i].value);
                            } else {
                                _html += ' <option value="{1}">{0}</option>'.format(staticField.anims[i].text, staticField.anims[i].value);
                            }
                        }
                        _html += '</select>'
                        _html += '</div>';
                        _html += '</div>';
                        break;
                    case 'arrow':
                        //处理
                        _html += '<div class="layui-form-item" >';
                        _html += '  <label class="layui-form-label">{0}</label>'.format(staticField.lang[key]);
                        _html += '  <div class="layui-input-block">';
                        _html += '<select name="{0}" lay-verify="required">'.format(key);
                        for (var i = 0; i < staticField.arrows.length; i++) {
                            if (staticField.arrows[i].value === json.arrow) {
                                _html += ' <option value="{1}" selected="">{0}</option>'.format(staticField.arrows[i].text, staticField.arrows[i].value);
                            }else {
                                _html += ' <option value="{1}">{0}</option>'.format(staticField.arrows[i].text, staticField.arrows[i].value);
                            }
                        }
                        _html += '</select>'
                        _html += '</div>';
                        _html += '</div>';
                        break;
                    case 'buttonType':
                        //处理
                        _html += '<div class="layui-form-item" >';
                        _html += '  <label class="layui-form-label">{0}</label>'.format(staticField.lang[key]);
                        _html += '  <div class="layui-input-block">';
                        _html += '<select name="{0}" lay-verify="required">'.format(key);
                        for (var i = 0; i < staticField.buttonTypes.length; i++) {
                            if (staticField.buttonTypes[i].value === json.buttonType) {
                                if (staticField.buttonTypes[i].value === "") {
                                    _html += ' <option selected="">{0}</option>'.format(staticField.buttonTypes[i].text);
                                }else {
                                    _html += ' <option value="{1}" selected="">{0}</option>'.format(staticField.buttonTypes[i].text, staticField.buttonTypes[i].value);
                                }
                            }else {
                                _html += ' <option value="{1}">{0}</option>'.format(staticField.buttonTypes[i].text, staticField.buttonTypes[i].value);
                            }
                        }
                        _html += '</select>'
                        _html += '</div>';
                        _html += '</div>';
                        break;
                    case 'buttonSize':
                        //处理
                        _html += '<div class="layui-form-item" >';
                        _html += '  <label class="layui-form-label">{0}</label>'.format(staticField.lang[key]);
                        _html += '  <div class="layui-input-block">';
                        _html += '<select name="{0}" lay-verify="required">'.format(key);
                        for (var i = 0; i < staticField.buttonSizes.length; i++) {
                            if (staticField.buttonSizes[i].value === json.buttonSize) {
                                if (staticField.buttonSizes[i].value === "") {
                                    _html += ' <option selected="">{0}</option>'.format(staticField.buttonSizes[i].text);
                                }else {
                                    _html += ' <option value="{1}" selected="">{0}</option>'.format(staticField.buttonSizes[i].text, staticField.buttonSizes[i].value);
                                }
                            }else {
                                _html += ' <option value="{1}">{0}</option>'.format(staticField.buttonSizes[i].text, staticField.buttonSizes[i].value);
                            }
                        }
                        _html += '</select>'
                        _html += '</div>';
                        _html += '</div>';
                        break;
                    case 'dataMaxValue':
                    case 'dataMinValue':
                        _html += '<div id="{0}" class="layui-form-item">'.format(key + json.id);
                        _html += '<label class="layui-form-label">{0}:</label>'.format(staticField.lang[key]);
                        _html += '<div class="layui-input-block"">';
                        _html += '<div id="{0}" class="layui-input icon-date widget-date" style="line-height: 40px;"></div>'.format(key + json.tag + json.id);
                        _html += '</div>';
                        _html += '</div>';
                        break;
                    case 'dateDefaultValue':
                        _html += '<div id="{0}" class="layui-form-item">'.format(key + json.id);
                        _html += '<label class="layui-form-label">{0}:</label>'.format(staticField.lang[key]);
                        _html += '<div class="layui-input-block"">';
                        _html += '<div id="{0}" class="layui-input icon-date widget-date" style="line-height: 40px;"></div>'.format(key + json.tag + json.id);
                        _html += '</div>';
                        _html += '</div>';
                        break;
                    case 'dateRangeDefaultValue':
                        _html += '<div id="{0}" class="layui-form-item">'.format(key + json.id);
                        _html += '<label class="layui-form-label">{0}:</label>'.format(staticField.lang[key]);
                        _html += '<div class="layui-input-block"">';
                        _html += '<div id="{0}" class="layui-input icon-date widget-date" style="line-height: 40px;"></div>'.format(key + json.tag + json.id);
                        _html += '</div>';
                        _html += '</div>';
                        break;
                    case 'buttonIcon':
                        _html += '<div id="{0}" class="layui-form-item" ">'.format(key + json.id);
                        _html += '<label class="layui-form-label">{0}:</label>'.format(staticField.lang[key]);
                        _html += '<div class="layui-input-block"">';
                        _html += '<div id="{0}" class="layui-input icon-date widget-date" style="line-height: 40px;"></div>'.format(json.tag + json.id + "property");
                        _html += '</div>';
                        _html += '</div>';
                        break;
                    case 'whiteSpace':
                        _html += '<div id="{0}" class="layui-form-item" ">'.format(key + json.id);
                        _html += '<label class="layui-form-label">{0}:</label>'.format(staticField.lang[key]);
                        _html += '<div class="layui-input-block"">';
                        _html += '<div id="{0}" class="widget-slider" style="top: 16px;"></div>'
                            .format(json.tag + json.id + "property");
                        _html += '</div>';
                        _html += '</div>';
                        break;
                    case 'colorSelection':
                        _html += '<div id="{0}" class="layui-form-item" ">'.format(key + json.id);
                        _html += '<label class="layui-form-label">{0}:</label>'.format(staticField.lang[key]);
                        _html += '<div class="layui-input-block"">';
                        _html += '<div id="{0}" class="widget-rate"></div>'.format(json.tag + json.id + "property");
                        _html += '</div>';
                        _html += '</div>';
                        break;
                    case 'columns':
                        var columnCount = 2;
                        columnCount = json[key].length;
                        //处理
                        _html += '<div class="layui-form-item" >';
                        _html += '  <label class="layui-form-label">{0}</label>'.format(staticField.lang[key]);
                        _html += '  <div class="layui-input-block">';
                        _html += '<select name="{0}" lay-verify="required">'.format(key);
                        for (var i = 2; i <= 4; i++) {
                            if (i === columnCount) {
                                _html += '<option value="{0}" selected="">{0}</option>'.format(i);
                            } else {
                                _html += '<option value="{0}">{0}</option>'.format(i);
                            }
                        }
                        _html += '</select>'
                        _html += '</div>';
                        _html += '</div>';
                        break;
                    default:
                        break;
                }
            }
            $('#columnProperty').append(_html);
        }



        /* 给字段属性绑定事件 实现双向绑定*/
        Class.prototype.bindPropertyEvent = function (_json) {
            var that = this
                , options = that.config;
            if (options.data === undefined) {
                return;
            }
            if (typeof (options.data) === 'string') {
                options.data = JSON.parse(options.data);
            }
            //没有可以选择的
            if (_json === undefined) {
                return;
            }
            //全局下拉绑定
            form.on('select', function (data) {
                var _key = data.elem.name;
                var _value = parseInt(data.value);
                var _json = options.selectItem;
                switch (_key) {
                    case 'columns':
                        var columnCount = _json[_key].length;
                        var nullJson = {
                            span: 12,
                            list: []
                        };
                        if (_value > columnCount) {
                            for (var i = columnCount + 1; i <= _value; i++) {
                                _json[_key].splice(i, 0, nullJson);
                            }
                        } else {
                            _json[_key].splice(_value, columnCount);
                        }
                        that.components[_json.tag].update(_json,that);
                        break;
                    case 'dateFormat':
                        if (_json.tag === 'date') {
                            var _html = '<div id="{0}" class="layui-input icon-date widget-date" style="line-height: 40px;"></div>'.format('dateDefaultValue' + _json.tag + _json.id);
                            $('#dateDefaultValue' + _json.id + ' .layui-input-block').empty();
                            $('#dateDefaultValue' + _json.id + ' .layui-input-block').append(_html);
                            _json.dateFormat = data.value;
                            var dateClass = laydate.render({
                                elem: '#dateDefaultValue' + _json.tag + _json.id,
                                type: _json.datetype,
                                format: _json.dateFormat,
                                value: new Date(),
                                done: function(value, date, endDate){
                                    _json.dateDefaultValue = value;
                                    that.components[_json.tag].update(_json,that);
                                }
                            });
                            _json.dateDefaultValue = dateClass.config.elem[0].innerText;
                            that.components[_json.tag].update(_json,that);
                        }
                        if (_json.tag === 'dateRange') {
                            var _html = '<div id="{0}" class="layui-input icon-date widget-date"></div>'.format('dateDefaultValue' + _json.tag + _json.id);
                            $('#dateDefaultValue' + _json.id + ' .layui-input-block').empty();
                            $('#dateDefaultValue' + _json.id + ' .layui-input-block').append(_html);
                            _json.dateFormat = data.value;
                            var _v = layui.util.toDateString(new Date(), _json.dateFormat) + " - " + layui.util.toDateString(new Date(), _json.dateFormat);
                            laydate.render({
                                elem: '#dateRangeDefaultValue' + _json.tag + _json.id,
                                type: _json.dateType,
                                format: _json.dateFormat,
                                value:  _v,
                                range:"-",
                                done: function(value, date, endDate){
                                    _json.dateRangeDefaultValue = value;
                                    that.components[_json.tag].update(_json,that);
                                }
                            });
                            _json.dateRangeDefaultValue = _v;
                            that.components[_json.tag].update(_json,that);
                        }
                        break;
                    case 'dateType':
                        if (_json.tag === 'date') {
                            var _html = '<div id="{0}" class="layui-input icon-date widget-date" style="line-height: 40px;"></div>'.format('dateDefaultValue' + _json.tag + _json.id);
                            $('#dateDefaultValue' + _json.id + ' .layui-input-block').empty();
                            $('#dateDefaultValue' + _json.id + ' .layui-input-block').append(_html);
                            _json.dateType = data.value;
                            var dateClass = laydate.render({
                                elem: '#dateDefaultValue' + _json.tag + _json.id,
                                type: _json.dateType,
                                format: _json.dateFormat,
                                value: new Date(),
                                done: function(value, date, endDate){
                                    _json.dateDefaultValue = value;
                                    that.components[_json.tag].update(_json,that);
                                }
                            });
                            _json.dateDefaultValue = dateClass.config.elem[0].innerText;
                            that.components[_json.tag].update(_json,that);
                        }
                        if (_json.tag === 'dateRange') {
                            var _html = '<div id="{0}" class="layui-input icon-date widget-date"></div>'.format('dateDefaultValue' + _json.tag + _json.id);
                            $('#dateDefaultValue' + _json.id + ' .layui-input-block').empty();
                            $('#dateDefaultValue' + _json.id + ' .layui-input-block').append(_html);
                            _json.dateType = data.value;
                            laydate.render({
                                elem: '#dateRangeDefaultValue' + _json.tag + _json.id,
                                type: _json.dateType,
                                format: _json.dateFormat,
                                value: _json.dateRangeDefaultValue,
                                range:"-",
                                done: function(value, date, endDate){
                                    _json.dateRangeDefaultValue = value;
                                    that.components[_json.tag].update(_json,that);
                                }
                            });
                            that.components[_json.tag].update(_json,that);
                        }
                        break;
                    case 'anim':
                    case 'arrow':
                    case 'buttonType':
                    case 'buttonSize':
                        _json[data.elem.name] = data.value;
                        that.components[_json.tag].update(_json,that);
                        break;
                    default:
                        break;
                }
            });
            //全局input框绑定
            $(document).off('keyup', '#columnProperty .layui-input').on('keyup', '#columnProperty .layui-input', function () {
                var _key = $(this).attr("name");
                var _value = $(this).val();
                var _json = options.selectItem;
                switch (_key) {
                    case 'label':
                    case 'width':
                    case 'interval':
                    case 'iconPickerLimit':
                    case 'iconPickerCellWidth':
                    case 'buttonVlaue':
                    case 'placeholder':
                    case 'rateLength':
                    case 'height':
                    case 'iconPickerLimit':
                        _json[_key] = _value;
                        that.components[_json.tag].update(_json,that);
                        break;
                    case 'defaultValue':
                        if (_json.tag === 'slider') {
                            var resultNumber = that.replaceNumber(_value);
                            _json[_key] = resultNumber;
                            $(this).val(resultNumber);
                            slider.render({
                                elem: '#' + _json.tag + _json.id,
                                value: _json.defaultValue, //初始值
                                min: _json.minValue,
                                max: _json.maxValue,
                                step: _json.stepValue,
                                disabled: _json.disabled
                            });
                        } else if (_json.tag == 'numberInput') {
                            var resultNumber = that.replaceNumber(_value);
                            _json[_key] = resultNumber;
                            $(this).val(resultNumber);
                            that.components[_json.tag].update(_json,that);//局部更新
                        } else {
                            _json[_key] = _value;
                            that.components[_json.tag].update(_json,that);
                        }
                        break;
                    case 'minValue':
                    case 'maxValue':
                    case 'stepValue':
                        var resultNumber = that.replaceNumber(_value);
                        _json[_key] = resultNumber;
                        $(this).val(resultNumber);
                        if (_json.tag === 'slider') {
                            slider.render({
                                elem: '#' + _json.tag + _json.id,
                                value: _json.defaultValue, //初始值
                                min: _json.minValue,
                                max: _json.maxValue,
                                step: _json.stepValue,
                                disabled: _json.disabled
                            });
                        }else if (_json.tag == 'numberInput') {
                            that.components[_json.tag].update(_json,that);//局部更新
                        }
                        break;
                    case 'carousel-text':
                    case 'carousel-value':
                        var _options = [];
                        $('#columnProperty .select-options').each(function () {
                            _options.push({
                                text: $(this).find('input[name=carousel-text]').val(),
                                value: $(this).find('input[name=carousel-value]').val(),
                                checked: $(this).find('input[name=carousel]').hasAttribute("checked")
                            });
                        });
                        _json.options = JSON.parse(JSON.stringify(_options));
                        that.components[_json.tag].update(_json,that);//局部更新
                        break;
                    case 'select-text':
                    case 'select-value':
                    case 'radio-text':
                    case 'radio-value':
                    case 'checkbox-text':
                    case 'checkbox-value':
                        //找到 id=key 下的 option值
                        var _index = parseInt($(this).parent().parent().attr("data-index"));
                        if (_key === 'select-text' || _key === 'radio-text' || _key === 'checkbox-text') {
                            _json.options[_index].text = $(this).val();
                        } else {
                            _json.options[_index].value = $(this).val();
                        }
                        that.components[_json.tag].update(_json,that);//局部更新
                        break;
                    default:
                        break;
                }
            });
            //全局开关绑定
            form.on('switch', function (data) {
                var _key = data.elem.name;
                var _value = data.elem.checked ? true : false;
                var _json = options.selectItem;
                switch (_key) {
                    case 'readonly':
                    case 'disabled':
                    case 'required':
                    case 'half':
                    case 'text':
                    case 'switchValue':
                    case 'isInput':
                    case 'iconPickerSearch':
                    case 'iconPickerPage':
                    case 'isEnter':
                    case 'isLabel':
                    case 'autoplay':
                    case 'hideLabel':
                        _json[_key] = _value;
                        that.components[_json.tag].update(_json,that);
                        break;
                    default:
                        break;
                }
            });

            form.on('radio', function (data) {
                var _json = options.selectItem;
                switch (_json.tag) {
                    case 'radio':
                        var _index = parseInt($("#" + _json.id + " .layui-input-block div.layui-form-radio").index(data.othis[0]));
                        if ($(data.othis[0]).parent().parent().parent().attr("id") === 'radio') {
                            _index = parseInt($(data.othis[0]).parent().parent().attr("data-index"));
                        }
                        for (var i = 0; i < _json.options.length; i++) {
                            if (i === _index) {
                                _json.options[i].checked = true;
                                continue;
                            }
                            _json.options[i].checked = false;
                        }
                        that.components[_json.tag].update(_json,that);
                        break;
                    case 'select':
                    case 'carousel':
                        var _index = parseInt(data.elem.closest('.layui-form-item').dataset.index);
                        for (var i = 0; i < _json.options.length; i++) {
                            if (i === _index) {
                                _json.options[i].checked = true;
                                _json.startIndex = i;
                                continue;
                            }
                            _json.options[i].checked = false;
                        }
                        that.components[_json.tag].update(_json,that);
                        break;
                    default:
                        break;
                }
            });

            form.on('checkbox', function (data) {
                var _json = options.selectItem;
                switch (_json.tag) {
                    case 'checkbox':
                        var _index = parseInt($("#" + _json.id + " .layui-input-block div.layui-form-checkbox").index(data.othis[0]));
                        if ($(data.othis[0]).parent().parent().parent().attr("id") === 'checkbox') {
                            _index = parseInt($(data.othis[0]).parent().parent().attr("data-index"));
                        }
                        for (var i = 0; i < _json.options.length; i++) {
                            if (i === _index) {
                                _json.options[i].checked = data.elem.checked;
                                break;
                            }
                        }
                        that.components[_json.tag].update(_json,that);
                        break;
                    default:
                        break;
                }
            });

        }

        /*--------------------------------------------------以下属于非修改内容---------------------------------------------------------*/
        /*--------------------------------------------------以下属于非修改内容---------------------------------------------------------*/

        Class.prototype.reload = function (id
            , options) {
            var that = this;
            options = options || {};//如果是空的话，就赋值 {}
            that.render();
        }


        //核心入口 初始化一个 regionSelect 类
        formDesigner.render = function (options) {
            var ins = new Class(options);
            return thisIns.call(ins);
        };

        /**
         * data 表示设计区数据
         * dataSource 表示数据源即一个控件的数据来源
         *
         */
        Class.prototype.config = {
            version: "1.0.0"
            , formName: "表单示例"
            , Author: "谁家没一个小强"
            , formId: "formPreviewForm"
            , generateId: 0
            , data: []
            , dataSource: {}
            , formData:{}
            , globalDisable:false
            , viewOrDesign:false
            , formDefaultButton:true
            , formProperty: {}
            , selectItem: undefined
        };

        /* 自动生成ID 当前页面自动排序*/
        Class.prototype.autoId = function (tag) {
            var that = this,
                options = that.config;
            options.generateId = options.generateId + 1;
            return tag + '_' + options.generateId;
        }

        //渲染视图
        Class.prototype.render = function () {
            var that = this
                , options = that.config;
            if (options.viewOrDesign) {
                var that = this
                    , options = that.config;
                options.elem = $(options.elem);
                options.id = options.id || options.elem.attr('id') || that.index;
                options.elem.html('<form  class="layui-form  layui-form-pane" style="height:100%;" id="{0}" lay-filter="{0}"></form>'.format(options.formId));
                that.renderViewForm();
            } else {
                options.elem = $(options.elem);
                options.id = options.id || options.elem.attr('id') || that.index;
                options.elem.html(staticField.formDesignerHtml);
                /* 根据componentsLang渲染组件*/
                var componentsLangHtml = ""
                $.each(formField.componentsLang
                    , function (index
                        , item) {
                        componentsLangHtml += '<div class="components-title">{0} </div>'.format(item.name);
                        componentsLangHtml += '<div class="components-draggable" id="{0}">'.format(item.component);
                        $.each(item.list
                            , function (index1
                                , item1) {
                                componentsLangHtml += '<ol data-tag="{0}"><div class="icon"><i class="{2}"></i></div><div class="name">{1}</div></ol>'.format(item1.key
                                    , staticField.lang[item1.key], item1.icon);
                            });
                        componentsLangHtml += '</div>';
                    });
                $('#components-form-list').append(componentsLangHtml);

                /* 根据templateFormList渲染组件 默认显示5条*/
                var templateFormListHtml = ""
                $.each(staticField.templateFormList
                    , function (index
                        , item) {
                        if (index < 5) {
                            templateFormListHtml += '<div class="item-body"><div class="item-img">';
                            templateFormListHtml += '<img src="{0}" lay-image-hover="" data-size="635,500" alt="" id="tip{1}"></div>'.format(item.imageUrl,index);
                            templateFormListHtml += '<div class="item-desc"><span class="item-title">{1}</span><button type="button" class="right-button button--text" data-id="{0}"><span> 加载此模板</span></button></div></div>'.format(index,item.text);
                            templateFormListHtml += '</div>';
                            $('#template-form-list').append(templateFormListHtml);
                        }
                    });

                // 加载模板事件注册
                $('body').on('click', '.button--text', function (e) {
                    var id = $(this).data('id');
                    layer.confirm('是否加载这个模板？加载后会覆盖设计器当前表单', function (index) {
                        options.data = [];
                        Object.assign(options.data, staticField.templateFormList[id].value)
                        that.renderForm();
                        layer.close(index);
                    })
                });
                //加载更多模板
                flow.load({
                    elem: '#template-form-list'
                    ,done: function(page, next){
                        console.log(page);
                        //模拟插入
                        setTimeout(function(){
                            var list = [];
                            next(list.join(''), page < 2); //假设总页数为 6
                        }, 500);
                    }
                });
                //排序事件注册
                $.each(formField.componentsLang
                    , function (index
                        , item) {
                        var sortable1 = Sortable.create(document.getElementById(item.component), {
                            group: {
                                name: 'formgroup',
                                pull: 'clone', //克隆本区域元素到其他区域
                                put: false, //禁止本区域实现拖动或拖入
                            },
                            ghostClass: "ghost",
                            sort: false,
                            animation: 150,
                            onEnd: function (evt) {
                                // console.log('onEnd.foo:', [evt.item, evt.from]);
                                // console.log(evt.oldIndex);
                                // console.log(evt.newIndex);
                                var itemEl = evt.item;
                                // console.log(itemEl);
                            }
                        });
                    });
                //表单事件注册
                var formItemSort = Sortable.create(document.getElementById("formDesignerForm"), {
                    group: {
                        name: 'formgroup'
                    },
                    handle: '.widget-view-drag',
                    ghostClass: "ghost",
                    animation: 200,
                    onAdd: function (evt) {
                        if (options.data === undefined) {
                            return;
                        }
                        if (typeof (options.data) === 'string') {
                            options.data = JSON.parse(options.data);
                        }
                        //注意这里的一个bug，newIndex 第一次拖动也是1 第二次拖动也是1
                        if (options.data.length === 0) {
                            evt.newIndex = 0;
                        }
                        if (evt.item.dataset.id !== undefined) {
                            /*根据id的新算法 复制一份副本 删除json中的节点 再插入节点*/
                            var _item = that.findJsonItem(options.data, evt.item.dataset.id);
                            options.selectItem = _item;
                            that.deleteJsonItem(options.data, evt.item.dataset.id);
                            options.data.splice(evt.newIndex + 1, 0, _item);
                        } else {
                            var _id = that.autoId(evt.item.dataset.tag);
                            /* 向现有的表单数据中增加新的数组元素 splice */
                            var _newitem = that.components[evt.item.dataset.tag].jsonData(_id, evt.newIndex,that);
                            //如果是 grid 呢，需要知道几列
                            options.selectItem = _newitem;
                            options.data.splice(evt.newIndex, 0, _newitem);
                        }
                        //局部更新 只要更新 designer 设计区部分
                        that.renderForm();
                    },
                    onEnd: function (evt) {
                        var newIndex = evt.newIndex;
                        var oldIndex = evt.oldIndex;
                        //只有当to的目标容器是 formDesignerForm
                        if (evt.to.id === 'formDesignerForm') {
                            that.moveItem(evt.oldIndex, evt.newIndex);
                        }
                    }
                });
            }
            //注册预览事件
            $('.previewForm').on('click', function () {
                window.localStorage.setItem('layui_form_json', JSON.stringify(options.data));
                layer.confirm('请选择你要预览页面的方式？', {
                    btn: ['弹窗','新页面'] //按钮
                }, function(){
                    //iframe窗
                    layer.open({
                        type: 2,
                        title: '表单预览',
                        btn: ['关闭'], //可以无限个按钮
                        btn1: function (index, layero) {
                            layer.close(index);
                        },
                        closeBtn: 1, //不显示关闭按钮
                        shade: [0],
                        area: ['100%', '100%'],
                        offset: 'auto', //右下角弹出
                        anim: 2,
                        content: ['./preview.html', 'yes'], //iframe的url，no代表不显示滚动条
                        end: function () { //此处用于演示
                            //加载结束
                        }
                    });
                }, function(){
                    window.open("./preview.html");
                });

            });
            $('body').append(staticField.importHtml);
            $('body').append(staticField.exportHtml);
            //注册导入数据
            $('.importJson').on('click', function () {
                document.getElementById('import-json-code').value = JSON.stringify(options.data, null, 4);
                layer.open({
                    type: 1
                    , title: 'JSON模板数据导入'
                    , id: 'Lay_layer_importjsoncodeview'
                    , content: $('.importjsoncodeview')
                    , area: ['800px', '640px']
                    , shade: false
                    , resize: false
                    , success: function (layero, index) {
                    }
                    , end: function () {
                        $('.importjsoncodeview').css("display","none")
                    }
                });
            });
            $('#copy-html-code').on('click', function () {
                var Url2 = document.getElementById("generate-code-view");
                Url2.select(); // 选择对象
                document.execCommand("Copy"); // 执行
                layer.msg('复制成功');
            });
            $('#import-json-code').on('click', function () {
                var _value = document.getElementById("import-json-code-view").value;
                options.data = JSON.parse(_value);
                that.renderForm();
                layer.closeAll();
                layer.msg('导入成功');
            });
            //注册导出数据
            $('.exportJson').on('click', function () {
                document.getElementById('generate-code-view').value = JSON.stringify(options.data, null, 4);

                layer.open({
                    type: 1
                    , title: 'JSON 数据导出'
                    , id: 'Lay_layer_htmlcodeview'
                    , content: $('.htmlcodeview')
                    , area: ['800px', '640px']
                    , shade: false
                    , resize: false
                    , success: function (layero, index) {

                    }
                    , end: function () {
                        $('.htmlcodeview').css("display","none")
                    }
                });
            });

            $('.generateCode').on('click', function () {
                var _html = $('<div style="height:100%;width:100%;"></div>');
                var _script = $('<div style="height:100%;width:100%;"></div>');;
                that.generateHtml(options.data, _html,_script);
                var _htmlCode = staticField.htmlCode.format(_html.html(),_script.html());
                document.getElementById('generate-code-view').value = style_html(_htmlCode, 4, ' ', 400);
                layer.open({
                    type: 1
                    , title: 'HTML代码'
                    , id: 'Lay_layer_htmlcodeview'
                    , content: $('.htmlcodeview')
                    , area: ['800px', '640px']
                    , shade: false
                    , resize: false
                    , success: function (layero, index) {
                        layer.style(index, {
                            marginLeft: -220
                        });
                    }
                    , end: function () {
                        $('.htmlcodeview').css("display","none")
                    }
                });

            });
            that.renderForm();
        }

        //递归赋值
        Class.prototype.replaceNumber = function (value) {
            value = value.replace(/[^\d]/g,'');
            if(''!=value){
                value = parseInt(value);
            }
            return value;
        }

        /* 触发 grid 的 sortablejs 事件*/
        Class.prototype.bindGridSortEvent = function (json) {
            var that = this
                , options = that.config;
            var objs = $('#' + json.id + ' .widget-col-list');
            //遍历他下面的节点
            for (var i = 0; i < objs.length; i++) {
                var el = objs[i];
                var ops = {
                    group: {
                        name: 'formgroup'
                    },
                    handle: '.widget-view-drag',
                    ghostClass: "ghost",
                    animation: 150,
                    onAdd: function (evt) {
                        var parentItem = JSON.parse(JSON.stringify(that.findJsonItem(options.data, evt.item.parentElement.parentElement.dataset.id)));
                        var index = evt.newIndex;
                        var colIndex = evt.item.parentElement.dataset.index;
                        if (evt.item.dataset.id != undefined) {
                            //表示从其他地方移动过来
                            var _fromItem = JSON.parse(JSON.stringify(that.findJsonItem(options.data, evt.item.dataset.id)));
                            var _oldid = _fromItem.id;
                            _fromItem.id = that.autoId(_fromItem.tag);
                            _fromItem.index = index;
                            parentItem.columns[colIndex].list.splice(index + 1, 0, _fromItem);
                            that.findAndCopyJson(options.data,parentItem,evt.item.parentElement.parentElement.dataset.id);
                            that.deleteJsonItem(options.data, _oldid);
                        } else {
                            /* 向指定目标放入数据 splice */
                            var tag = evt.item.dataset.tag;
                            _id = that.autoId(tag);
                            var _newitem = that.components[tag].jsonData(_id, evt.newIndex,that);
                            _newitem.index = index;
                            parentItem.columns[colIndex].list.splice(index + 1, 0, _newitem);
                            that.findAndCopyJson(options.data,parentItem,evt.item.parentElement.parentElement.dataset.id);
                            options.selectItem = _newitem;
                        }
                        that.renderForm();
                    },
                    //拖动结束
                    onEnd: function (evt) {
                        //console.log(evt);
                    }};
                var gridSortable = Sortable.create(el,ops);
            }
        };

        /* 重新渲染视图区*/
        Class.prototype.renderViewForm = function () {
            var that = this
                , options = that.config;
            var elem = $('#' + options.formId);
            //清空
            elem.empty();
            that.renderComponents(options.data, elem);
            if (options.formDefaultButton) {
                elem.append(staticField.formDefaultButton);
            }
            that.setFormData(options.formData);
            form.render();//一次性渲染表单
        }

        /* 生成 Html 代码 */
        Class.prototype.generateHtml = function (jsondata, elem, scriptHtml) {
            var that = this
                , options = that.config;
            $.each(jsondata, function (index, item) {
                elem.append(that.components[item.tag].generateHtml(item, that));
                if (item.tag === 'grid') {
                    $.each(item.columns, function (index2, item2) {
                        //获取当前的 DOM 对象
                        if (item2.list.length > 0) {
                            var elem2 = elem.find('#' + item.id + ' .widget-col-list.column' + item.id + index2);
                            that.generateHtml(item2.list, elem2,scriptHtml);
                        }
                    });
                } else {
                    scriptHtml.append(that.components[item.tag].generateScript(item, that));
                }
            });
        }

        //获取表单数据回调
        Class.prototype.setFormData = function (json) {
            var that = this,
                options = that.config;
            //获取表单区域所有值
            for(let key  in json){
                if (key.indexOf("[")!= -1 && key.indexOf("]")!= -1) {
                    var check = key.substring(0,key.indexOf("["));
                    var item = that.findJsonItem(options.data,check);
                    if (item === undefined) {
                        continue;
                    }
                    that.components[item.tag].update(item,that);
                    continue;
                }
                var item = that.findJsonItem(options.data,key);
                if (item === undefined) {
                    continue;
                }
                switch (item.tag) {
                    case 'editor':
                    case 'rate':
                    case 'slider':
                    case 'labelGeneration':
                    case 'iconPicker':
                    case 'cron':
                    case 'colorpicker':
                        item.defaultValue = json[key];
                        that.components[item.tag].update(item,that);
                        break;
                    case 'sign':
                        item.data = json[key];
                        that.components[item.tag].update(item,that);
                        break;
                    default:
                        break;
                }
            }
            if (options.viewOrDesign) {
                form.val(options.formId,json);
            } else {
                form.val("formPreviewForm",json);
            }
            options.formData = json;
            return json;
        }

        //获取表单数据回调
        Class.prototype.getFormData = function () {
            var that = this,
                options = that.config;
            //获取表单区域所有值
            var json = form.val(that.config.formId);
            for(let key  in iceEditorObjects){
                json[key] = iceEditorObjects[key].getHTML();
            }
            for(let key  in labelGenerationObjects){
                json[key] = labelGenerationObjects[key].getData();
            }
            for(let key  in signObjects){
                json[key] = signObjects[key];
            }
            return json;
        }

        //递归改变禁用属性
        Class.prototype.findItemToDisable = function (jsondata) {
            var that = this
                , options = that.config;
            $.each(jsondata, function (index, item) {
                if (item.tag === 'grid') {
                    $.each(item.columns, function (index2, item2) {
                        //获取当前的 DOM 对象
                        if (item2.list.length > 0) {
                            that.findItemToDisable(item2.list);
                        }
                    });
                } else {
                    item.disabled = true;
                    item.readonly = true;
                }
            });
        }

        //递归改变禁用属性
        Class.prototype.findItemToNoDisable = function (jsondata) {
            var that = this
                , options = that.config;
            $.each(jsondata, function (index, item) {
                if (item.tag === 'grid') {
                    $.each(item.columns, function (index2, item2) {
                        //获取当前的 DOM 对象
                        if (item2.list.length > 0) {
                            that.findItemToNoDisable(item2.list);
                        }
                    });
                } else {
                    item.disabled = false;
                    item.readonly = false;
                }
            });
        }

        //全局禁用
        Class.prototype.globalDisable = function () {
            var that = this
                , options = that.config;
            that.findItemToDisable(options.data);
            that.renderForm();
        }

        //全局取消禁用
        Class.prototype.globalNoDisable = function () {
            var that = this
                , options = that.config;
            that.findItemToNoDisable(options.data);
            that.renderForm();
        }

        /* 重新渲染设计区*/
        Class.prototype.renderForm = function () {
            var that = this
                , options = that.config;
            if (options.viewOrDesign) {
                var that = this
                    , options = that.config;
                options.elem = $(options.elem);
                options.id = options.id || options.elem.attr('id') || that.index;
                options.elem.html('<form  class="layui-form  layui-form-pane" style="height:100%;" id="{0}" lay-filter="{0}"></form>'.format(options.formId));
                that.renderViewForm();
            } else {
                var elem = $('#formDesignerForm');
                //清空
                elem.empty();
                //渲染组件
                that.renderComponents(options.data, elem);
                //选中的节点只有一个
                if (options.selectItem !== undefined) {
                    var _draghtml = '<div class="widget-view-action"  id="widget-view-action"><i class="layui-icon layui-icon-file"></i><i class="layui-icon layui-icon-delete"></i></div><div class="widget-view-drag" id="widget-view-drag"><i class="layui-icon layui-icon-screen-full"></i></div>';
                    var len = $('#' + options.selectItem.id).children().length;
                    if ($('#widget-view-action')) {//已存在
                        $('#widget-view-action').remove();
                    }
                    $('#' + options.selectItem.id).children(len - 1).after(_draghtml);
                    $('#formDesignerForm .layui-form-item').removeClass('active');
                    //给当前元素增加class
                    $('#' + options.selectItem.id).addClass('active');
                    that.bindPropertyEvent(options.selectItem);
                }
                /* 向 节点点击添加 click 动作 */
                that.addClick();
                /* 向 拷贝 删除 按钮添加 click 动作 */
                that.addCopyDeleteClick();
                form.render();
            }
        }

        /* 递归渲染组件 */
        Class.prototype.renderComponents = function (jsondata, elem) {
            var that = this
                , options = that.config;
            $.each(jsondata, function (index, item) {
                item.index = index;//设置index 仅仅为了传递给render对象，如果存在下级子节点那么 子节点的也要变动
                if (options.selectItem === undefined) {
                    that.components[item.tag].render(item, false,elem,that);
                } else {
                    if (options.selectItem.id === item.id) {
                        that.components[item.tag].render(item, true,elem,that);
                        //显示当前的属性
                        that.components[item.tag].property(item,that);
                        that.bindPropertyEvent(item);
                    } else {
                        that.components[item.tag].render(item, false,elem,that);
                    }
                }
                if (item.tag === 'grid') {
                    that.bindGridSortEvent(item);
                    $.each(item.columns, function (index2, item2) {
                        //获取当前的 DOM 对象
                        if (item2.list.length > 0) {
                            var elem2 = elem.find('#' + item.id + ' .widget-col-list.column' + item.id + index2);
                            that.renderComponents(item2.list, elem2);
                        }
                    });
                }
            });
        }

        /* 加入copy选项删除 */
        Class.prototype.addCopyDeleteClick = function () {
            var that = this
                , options = that.config;
            if (options.data === undefined) {
                return;
            }
            if (typeof (options.data) === 'string') {
                options.data = JSON.parse(options.data);
            }
            //复制当前节点
            $('#formDesignerForm  .layui-form-item .widget-view-action .layui-icon-file').on('click', function (e) {
                e.stopPropagation();
                //在json中插入
                if (options.data === undefined) {
                    return;
                }
                if (typeof (options.data) === 'string') {
                    options.data = JSON.parse(options.data);
                }
                var _id = document.elementFromPoint(e.pageX, e.pageY).parentElement.parentElement.dataset.id;
                if (_id !== undefined) {
                    options.selectItem = that.copyJsonAfterItem(options.data, _id);
                }
                that.renderForm();
            });
            $('#formDesignerForm  .layui-form-item .layui-icon-delete').on('click', function (e) {
                e.stopPropagation();
                //获取当前组件的组件id
                var _id = document.elementFromPoint(e.pageX, e.pageY).parentElement.parentElement.dataset.id;
                if (_id !== undefined) {
                    options.selectItem = that.deleteJsonItem(options.data, _id);
                }
                that.renderForm();
            });
        };

        /* 如果是grid布局控件 就显示不一样的样式 */
        Class.prototype.addClick = function (evt) {
            var that = this
                , options = that.config;
            $("#formDesignerForm .layui-form-item").on('click', function (e) {
                //当 div 为嵌套关系的时候 阻止事件冒泡
                e.preventDefault();
                e.stopPropagation();
                var index = parseInt($(this)[0].dataset.index);
                var _id = $(this)[0].dataset.id;
                options.selectItem = that.findJsonItem(options.data, _id);
                var tag = $(this)[0].dataset.tag;

                //显示当前的属性
                that.components[tag].property(options.selectItem,that);
                that.bindPropertyEvent();
                //移除 #formDesignerForm .layui-form-item 下所有的 active
                $('#formDesignerForm .layui-form-item').removeClass('active');
                //给当前元素增加class
                $(this).addClass('active');
                var _draghtml = '<div class="widget-view-action"  id="widget-view-action"><i class="layui-icon layui-icon-file"></i><i class="layui-icon layui-icon-delete"></i></div><div class="widget-view-drag" id="widget-view-drag"><i class="layui-icon layui-icon-screen-full"></i></div>';
                var len = $(this).children().length;
                if (len <= 12) {
                    //先删除元素
                    $("#formDesignerForm .layui-form-item .widget-view-action").remove();
                    $("#formDesignerForm .layui-form-item .widget-view-drag").remove();
                    if ($('#widget-view-action')) {//已存在
                        $('#widget-view-action').remove();
                    }
                    $(this).children(len - 1).after(_draghtml);
                }
                /* 向 拷贝 删除 按钮添加 click 动作 */
                that.addCopyDeleteClick();
                //重新渲染
                form.render();
            });
        };

        //移动视图
        Class.prototype.moveItem = function (oldIndex,newIndex) {
            var that = this
                , options = that.config;
            var newData = options.data[newIndex];
            var oldData = options.data[oldIndex];
            options.data[newIndex] = oldData;
            options.data[oldIndex] = newData;
        }

        /* 根据id返回json中的节点*/
        Class.prototype.findJsonItem = function (json, id) {
            var that = this,
                options = that.config;
            for (var i = 0; i < json.length; i++) {
                if (json[i].id === id) {
                    return json[i];
                } else {
                    if (json[i].tag === 'grid') {
                        for (var j = 0; j < json[i].columns.length; j++) {
                            if (json[i].columns[j].list.length > 0) {
                                var _item = that.findJsonItem(json[i].columns[j].list, id);
                                if (_item) {
                                    return _item;
                                }
                            }
                        }
                    }
                }
            }
            return undefined;
        }



        /* 复制json中的节点并返回上一个节点*/
        Class.prototype.copyJsonAfterItem = function (json, id) {
            var that = this,
                options = that.config;
            for (var i = 0; i < json.length; i++) {
                if (json[i].id === id) {
                    var _newjson = JSON.parse(JSON.stringify(json[i]));
                    _newjson.id = that.autoId(_newjson.tag);
                    json.splice(i + 1, 0, _newjson);
                    return json[i];
                } else {
                    if (json[i].tag === 'grid') {
                        for (var j = 0; j < json[i].columns.length; j++) {
                            if (json[i].columns[j].list.length > 0) {
                                var _item = that.copyJsonAfterItem(json[i].columns[j].list, id);
                                if (_item) {
                                    return _item;
                                }
                            }
                        }
                    }
                }

            }
            return undefined;
        };

        /* 删除json中的节点并返回上一个节点*/
        Class.prototype.deleteJsonItem = function (json, id) {
            var that = this,
                options = that.config;
            for (var i = 0; i < json.length; i++) {
                if (json[i].id === id) {
                    json.splice(i, 1);
                    if (i > 0) {
                        return json[i - 1];
                    }
                    break;
                }else {
                    if (json[i].tag === 'grid') {
                        for (var j = 0; j < json[i].columns.length; j++) {
                            if (json[i].columns[j].list.length > 0) {
                                that.deleteJsonItem(json[i].columns[j].list, id);
                            }
                        }
                    }
                }


            }
            return undefined;
        };

        /* 自动生成ID 当前页面自动排序*/
        Class.prototype.autoId = function (tag) {
            var that = this,
                options = that.config;
            options.generateId = options.generateId + 1;
            var findJsonItem = that.findJsonItem(options.data,tag + '_' + options.generateId);
            if (findJsonItem != undefined) {
                return that.autoId(tag);
            } else {
                return tag + '_' + options.generateId;
            }
        }

        //递归赋值
        Class.prototype.findAndCopyJson = function (json,parentItem,id) {
            var that = this;
            for (var i = 0; i < json.length; i++) {
                if (json[i].id === id) {
                    json[i] = parentItem;
                } else {
                    if (json[i].tag === 'grid') {
                        for (var j = 0; j < json[i].columns.length; j++) {
                            if (json[i].columns[j].list.length > 0) {
                                that.findAndCopyJson(json[i].columns[j].list,parentItem,id);
                            }
                        }
                    }
                }
            }
        }


        /* 此方法最后一道 commom.js 中 */
        String.prototype.format = function (args) {
            var result = this;
            if (arguments.length > 0) {
                if (arguments.length == 1 && typeof (args) == "object") {
                    for (var key in args) {
                        if (args[key] != undefined) {
                            var reg = new RegExp("({" + key + "})"
                                , "g");
                            result = result.replace(reg
                                , args[key]);
                        }
                    }
                } else {
                    for (var i = 0; i < arguments.length; i++) {
                        if (arguments[i] != undefined) {
                            var reg = new RegExp("({[" + i + "]})"
                                , "g");
                            result = result.replace(reg
                                , arguments[i]);
                        }
                    }
                }
            }
            return result;
        }

        exports('formDesigner'
            , formDesigner);
    });