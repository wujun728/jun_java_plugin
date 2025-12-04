layui.define(['form'], function (exports) {

    var form = layui.form,
        $ = layui.jquery,
        layer = layui.layer,
        index = 0,
		oldId,
    MOD_NAME = 'labelGeneration',
        formField = {
            label: {
                id: '-1',
                tag: "label",
            },
        },
        labelGeneration = {
            set: function (options) {
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
        },
        Class = function (options) {
            var that = this;
            that.config = $.extend({}
                , that.config
                , labelGeneration.config
                , options);
            that.render();
        },
        thisIns = function () {
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
                }
            }
        }

    Class.prototype.config = {
        version: "1.0.0"
        , Author: "谁家没一个小强"
        , generateId: 0
        , data: []
        , isEnter: false
    };

    /* 自动生成ID 当前页面自动排序*/
    Class.prototype.autoId = function (tag) {
        var that = this,
            options = that.config;
        options.generateId = options.generateId + 1;
        return tag + '_' + options.generateId;
    }

    Class.prototype.components = {
        label: {
            render: function (json,options) {
                var _html = '<blockquote class="layui-elem-quote">';
                _html += '<div class="layui-form layui-form-pane layui-form-item">';
                _html += '<label class="layui-form-label">输入标签</label>';
                _html += '<div class="layui-input-inline">';
				if (options.isEnter) {
                _html += '<input type="text" id="{0}"  placeholder="按回车生成标签" autocomplete="off" class="layui-input">'
                    .format(json.id);
				} else {
					_html += '<input type="text" id="{0}" placeholder="通过按钮生成标签" autocomplete="off" class="layui-input">'
                    .format(json.id);
				}
                _html += '</div>';
                if (!options.isEnter) {
                    _html += '<button type="button" id="{0}-button" class="layui-btn layui-btn-normal">确定</button>'.format(json.id);
                }
                _html += '<label class="layui-form-label">颜色选择</label>';
                _html += '<div class="layui-input-inline">';
                _html += '<select lay-filter="{0}-switchTest">'.format(json.id);
                _html += '<option value="" selected>墨绿色</option>';
                _html += '<option value="layui-btn-primary">原始色</option>';
                _html += '<option value="layui-btn-normal">天蓝色</option>';
                _html += '<option value="layui-btn-warm">暖黄色</option>';
                _html += '<option value="layui-btn-danger">红色</option>';
                _html += '</select>';
                _html += '</div>';
                _html += '</div>';
                _html += '<div id="{0}-content" class="layui-btn-container"></div>'.format(json.id);
                _html += '</blockquote>';
                return _html;
            },
            update: function (json) {

            },
            /* 获取对象 */
            jsonData: function (id, that) {
                //分配一个新的ID
                var _json = JSON.parse(JSON.stringify(formField.label));
                _json.id = id == undefined ? that.autoId(_json.tag) : id;
				that.checkId(_json,that);
                return _json;
            }
        }
    };

	/* 判定id是否重复*/
    Class.prototype.checkId = function (json,that) {
        if ($("#" + json.id + "-content").length != 0) {
				json.id = that.autoId(json.tag);
				that.checkId(json);
		} else {
			return;
		}
    }

    Class.prototype.bindGridSortEvent = function (json) {
        var that = this
            , options = that.config;
        var formItemSort = Sortable.create(document.getElementById(json.id + "-content"), {
            group: {
                name: 'group' + json.id
            },
            animation: 1000,
            onEnd: function (evt) {
                var _values = $("#" + json.id + "-content").find("div");
                var ops = [];
                for (var i = 0; i < _values.length; i++) {
                    ops.push({"ngColor": $(_values[i]).attr("ng-color"), "value": $(_values[i]).text()});
                }
                options.data = ops;
            }
        });
    }

    /* 绑定事件*/
    Class.prototype.deleteValue = function (value, ngValue) {
        var that = this
            , options = that.config;
        for (var i = 0; i < options.data.length; i++) {
            if (options.data[i].value === value && options.data[i].ngColor === ngValue) {
                options.data.splice(i, 1);
                break;
            }
        }
    }

    /* 绑定事件*/
    Class.prototype.bindPropertyEvent = function (_json) {
        var that = this
            , options = that.config;
        var colorClass = "";
        if (options.isEnter) {
            $("#" + _json.id).keypress(function (event) {
                if (event.which === 13) {
                    var _value = $(this).val();
                    if (_value === "") {
                        layer.msg('标签值不能为空');
                        return;
                    }
                    index = index + 1;
                    var _html = '<div class="layui-btn {0} none-transition" id="{2}" ng-index="{3}" ng-color="{0}">{1}<i class="layui-icon layui-icon-close"></i></div>'
                        .format(colorClass, _value, _json.id + index, index);
                    $("#" + _json.id + "-content").append(_html);
                    options.data.push({"ngColor": colorClass, "value": _value});
                    $("#" + _json.id + index + " .layui-icon-close").click(function () {
                        that.deleteValue($(this).parent().text(), $(this).parent().attr("ng-color"));
                        $(this).parent().remove();
                    });
                    return false;
                }
            });
        } else {
            $("#" + _json.id + "-button").click(function (event) {
                var _value = $("#" + _json.id).val();
                if (_value === "") {
                    layer.msg('标签值不能为空');
                    return;
                }
                index = index + 1;
                var _html = '<div class="layui-btn {0} none-transition" id="{2}" ng-index="{3}" ng-color="{0}">{1}<i class="layui-icon layui-icon-close"></i></div>'
                    .format(colorClass, _value, _json.id + index, index);
                $("#" + _json.id + "-content").append(_html);
                options.data.push({"ngColor": colorClass, "value": _value});
                $("#" + _json.id + index + " .layui-icon-close").click(function () {
                    that.deleteValue($(this).parent().text(), $(this).parent().attr("ng-color"));
                    $(this).parent().remove();
                });
            });
        }

        form.on('select(' + _json.id + '-switchTest)', function (data) {
            colorClass = data.value;
        });
        for (var i = 0; i < options.data.length; i++) {
            index = index + 1;
            var _html = '<div class="layui-btn {0} none-transition" id="{2}" ng-index="{3}" ng-color="{0}">{1}<i class="layui-icon layui-icon-close"></i></div>'
                .format(options.data[i].ngColor, options.data[i].value, _json.id + index, index);
            $("#" + _json.id + "-content").append(_html);
            $("#" + _json.id + index + " .layui-icon-close").click(function () {
                that.deleteValue($(this).parent().text(), $(this).parent().attr("ng-color"));
                $(this).parent().remove();
            });
        }
    }

    /* 渲染组件 */
    Class.prototype.renderComponents = function () {
        var that = this
            , options = that.config;
        var elem = $(options.elem);
        elem.empty();
        var jsonData = that.components['label'].jsonData(undefined, that);
        elem.append(that.components['label'].render(jsonData,options));
        that.bindPropertyEvent(jsonData);
        that.bindGridSortEvent(jsonData);
        form.render();
    }

    Class.prototype.reload = function (options) {
        var that = this;
        options = options || {};//如果是空的话，就赋值 {}
        that.config = $.extend({}
            , that.config
            , labelGeneration.config
            , options);
        that.render(options);
    }

    //核心入口 初始化一个 regionSelect 类
    labelGeneration.render = function (options) {
        var ins = new Class(options);
        return thisIns.call(ins);
    }
    /**
     * 渲染组件
     */
    Class.prototype.render = function (options) {
        var that = this
            , options = that.config;
        that.renderComponents();
    }

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

    exports(MOD_NAME, labelGeneration);
});