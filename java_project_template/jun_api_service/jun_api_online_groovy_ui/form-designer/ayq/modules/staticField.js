layui.define(['layer'], function (exports) {

    var staticField = {
        lang :  {
            id: "标识",
            label: "标题",
            index: "序号",
            tag: "表单类型",
            tagIcon: '图标',
            width: '宽度',
            height: "高度",
            span: '网格宽度',
            placeholder: "placeholder",
            defaultValue: "默认值",
            dateDefaultValue:'默认时间',
            labelWidth: "文本宽度",
            clearable: "是否清楚",
            prepend: "前缀",
            append: "追加",
            prefixIcon: '前缀图标',
            suffixIcon: '后缀图标',
            maxlength: "最大长度",
            showWordLimit: "是否限制字符",
            readonly: "只读",
            disabled: "禁用",
            required: "必填",
            columns: "列数",
            options: "选项",
            switchValue: "默认值",
            maxValue: "最大值",
            minValue: "最小值",
            dataMaxValue: "最大日期",
            dataMinValue: "最小日期",
            stepValue: "步长",
            dateType: "日期类型",
            dateFormat: "日期格式",
            half: "显示半星",
            theme: "皮肤",
            rateLength: "星星个数",
            interval: "间隔毫秒",
            startIndex: "开始位置",
            full: "是否全屏",
            arrow: "鼠标样式",
            contents: "内容",
            document: '帮助文档',
            input: "输入框",
            select: "下拉",
            checkbox: "多选组",
            radio: "单选组",
            date: "日期",
            editor: "iceEditor编辑器",
            slider: "滑块",
            image: "图片",
            grid: "一行多列",
            colorpicker: "颜色选择器",
            textarea: "多行文本",
            rate: "评分控件",
            switch: "开关",
            password: "密码框",
            carousel: "轮播",
            text: "显示文本",
            uploadUrl: "上传路径",
            expression: "验证",
            file: "文件",
            autoplay: "自动切换",
            anim: "切换方式",
            arrow: "切换箭头",
            tab:"tab选项卡",
            tabHeaders:"tab标题",
            isInput:"显示输入框",
            dateRange:"日期范围",
            dateRangeDefaultValue:"默认范围",
            menu:"头部菜单",
            numberInput:"排序文本框",
            iconPicker:"图标选择器",
            iconPickerSearch:"是否搜索",
            iconPickerPage:"是否分页",
            iconPickerLimit:"显示数量",
            iconPickerCellWidth:"图标宽度",
            cron:"Cron表达式",
            cronUrl:"运行路径",
            labelGeneration:"标签组件",
            isEnter:"是否回车",
            buttonIcon:"按钮图标",
            buttonType:"按钮类型",
            buttonSize:"组件尺寸",
            bottom:"按钮组件",
            buttonVlaue:"按钮文字",
            sign:"sign签名组件",
            hideLabel:"隐藏标签",
            colorSelection:"颜色选择",
            blockquote:"便签信息",
            line:"分割线",
            spacing:"间距",
            whiteSpace:"组件高度",
            textField:"HTML"
        },
        templateFormList : [
             {text: '默认模板',imageUrl: "./ayq/images/11.PNG",
                 value:[
                     {
                         "id": "input_1",
                         "index": 0,
                         "label": "单行文本",
                         "tag": "input",
                         "tagIcon": "input",
                         "placeholder": "请输入",
                         "defaultValue": null,
                         "labelWidth": 110,
                         "width": "100%",
                         "clearable": true,
                         "maxlength": null,
                         "showWordLimit": false,
                         "readonly": false,
                         "disabled": false,
                         "required": true,
                         "expression": "",
                         "document": ""
                     }
                 ]
             }
        ],
        expressions : [{text: '默认', value: ""}
            , {text: '数字', value: 'number'}
            , {text: '邮箱', value: 'email'}
            , {text: '手机', value: 'phone'}
            , {text: '身份证', value: 'identity'}
            , {text: '日期', value: 'date'}
            , {text: '网址', value: 'url'}
            , {text: '密码', value: 'pass'}
        ], anims : [{text: '左右切换', value: 'default'}
            , {text: '上下切换', value: 'updown'}
            , {text: '渐隐渐显切换', value: 'fade'}
        ], arrows : [{text: '悬停显示', value: 'hover'}
            , {text: '始终显示', value: 'always'}
            , {text: '始终不显示', value: 'none'}]
        , dateTypes : [{text: '年选择器', value: 'year'}
            , {text: '年月选择器', value: 'month'}
            , {text: '时间选择器', value: 'time'}
            , {text: '日期选择器', value: 'date'}
            , {text: '日期时间选择器', value: 'datetime'}]
        , buttonTypes : [{text: '原始', value: 'layui-btn-primary'}
            , {text: '默认', value: ""}
            , {text: '百搭', value: 'layui-btn-normal'}
            , {text: '暖色', value: 'layui-btn-warm'}
            , {text: '警告', value: ' layui-btn-danger'}]
        , buttonSizes : [{text: '大型', value: 'layui-btn-lg'}
            , {text: '默认', value: ""}
            , {text: '小型', value: 'layui-btn-sm'}
            , {text: '迷你', value: 'layui-btn-xs'}]
        , dateFormats : ["yyyy年MM月", "yyyy-MM-dd", "dd/MM/yyyy", "yyyyMMdd", "yyyy-MM-dd HH:mm:ss", "yyyy年MM月dd日 HH时mm分ss秒"]
        , iceEditMenus : [
            {value:'backColor',text:'字体背景颜色'},{value:'fontSize',text:'字体大小'},{value:'foreColor',text:'字体颜色'},{value:'bold',text:'粗体'},
            {value:'italic',text:'斜体'},{value:'underline',text:'下划线'},{value:'strikeThrough',text:'删除线'},{value:'justifyLeft',text:'左对齐'},
            {value:'justifyCenter',text:'居中对齐'},{value:'justifyRight',text:'右对齐'},{value:'indent',text:'增加缩进'},{value:'outdent',text:'减少缩进'},
            {value:'insertOrderedList',text:'有序列表'},{value:'insertUnorderedList',text:'无序列表'},{value:'superscript',text:'上标'},{value:'subscript',text:'下标'},
            {value:'createLink',text:'创建连接'},{value:'unlink',text:'取消连接'},{value:'hr',text:'水平线'},{value:'face',text:'表情'},{value:'table',text:'表格'},
            {value:'files',text:'附件'},{value:'music',text:'音乐'},{value:'video',text:'视频'},{value:'insertImage',text:'图片'},
            {value:'removeFormat',text:'格式化样式'},{value:'code',text:'源码'},{value:'line',text:'菜单分割线'}
        ],
        formDesignerHtml : '<div class="layui-layout layui-layout-admin">\n' +
            '    <div class="layui-header">\n' +
            '        <div class="layui-logo">表单设计器</div>\n' +
            '        <!-- 头部区域（可配合layui已有的水平导航） -->\n' +
            '        <ul class="layui-nav layui-layout-left">\n' +
            '            <li class="layui-nav-item"><a href=""></a></li>\n' +
            '        </ul>\n' +
            '        <ul class="layui-nav layui-layout-right">\n' +
            '            <li class="layui-nav-item">\n' +
            '                <a id="saveJson" href="#" class="saveJson">保存</a>\n' +
            '            </li>\n' +
            '            <li class="layui-nav-item">\n' +
            '                <a id="btnImportJson" href="#" class="importJson">导入数据</a>\n' +
            '            </li>\n' +
            '            <li class="layui-nav-item">\n' +
            '                <a id="btnExportJson" href="#" class="exportJson">导出数据</a>\n' +
            '            </li>\n' +
            '            <li class="layui-nav-item">\n' +
            '                <a href="#" class="previewForm">预览</a>\n' +
            '            </li>\n' +
            '            <li class="layui-nav-item">\n' +
            '                <a href="#" class="generateCode">生成代码</a>\n' +
            '            </li>\n' +
            '        </ul>\n' +
            '    </div>\n' +
            '    <div class="layui-side">\n' +
            '        <div class="layui-side-scroll" style="width: 260px;">\n' +
            '            <!-- 左侧导航区域（可配合layui已有的垂直导航） -->\n' +
            '            <div class="layui-tab layui-tab-brief" lay-filter="components-list">\n' +
            '                <ul class="layui-tab-title">\n' +
            '                    <li class="layui-this">组件</li>\n' +
            '                    <li class="">模板</li>\n' +
            '                </ul>\n' +
            '                <div class="layui-tab-content">\n' +
            '                    <div class="layui-tab-item layui-show">\n' +
            '                        <div class="components-list" id="components-form-list">\n' +
            '                        </div>\n' +
            '                    </div>\n' +
            '                    <div class="layui-tab-item" >\n' +
            '                        <div id="template-form-list" class="components-list">\n' +
            '                        </div>\n' +
            '                    </div>\n' +
            '                </div>\n' +
            '            </div>\n' +
            '        </div>\n' +
            '    </div>\n' +
            '    <div class="layui-body">\n' +
            '        <!-- 内容主体区域 -->\n' +
            '        <form class="layui-form layui-form-pane" style="height:98%;">\n' +
            '            <div class="layui-form" id="formDesignerForm" lay-filter="formDesignerForm">\n' +
            '                <div class="layui-row layui-empty">\n' +
            '                    从左侧拖拽控件到此设计区域来添加字段\n' +
            '                </div>\n' +
            '            </div>\n' +
            '            <button type="button" class="layui-btn" style="display: none" lay-submit lay-filter="*">立即提交</button>\n' +
            '        </form>\n' +
            '    </div>\n' +
            '    <div class="layui-side-right">\n' +
            '        <div class="layui-side-scroll" style="width: 350px;">\n' +
            '            <!-- 属性导航 -->\n' +
            '            <form class="layui-form  layui-form-pane">\n' +
            '                <div class="layui-tab layui-tab-brief" lay-filter="form-attr">\n' +
            '                    <ul class="layui-tab-title">\n' +
            '                        <li class="layui-this">字段设置</li>\n' +
            '                        <li>表单设置</li>\n' +
            '                    </ul>\n' +
            '                    <div class="layui-tab-content">\n' +
            '                        <div class="layui-tab-item layui-show" id="columnProperty">\n' +
            '                        </div>\n' +
            '                        <div class="layui-tab-item" id="formProperty">\n' +
            '                            <!--表单ID-->\n' +
            '                            <div class="layui-form-item">\n' +
            '                                <label class="layui-form-label">表单ID</label>\n' +
            '                                <div class="layui-input-block">\n' +
            '                                    <input type="text" name="formId" required="" lay-verify="required"\n' +
            '                                           placeholder="请输入表单ID" autocomplete="off" class="layui-input">\n' +
            '                                </div>\n' +
            '                            </div>\n' +
            '                            <!--//表单ID-->\n' +
            '                            <!--表单名称-->\n' +
            '                            <div class="layui-form-item">\n' +
            '                                <label class="layui-form-label">表单名称</label>\n' +
            '                                <div class="layui-input-block">\n' +
            '                                    <input type="text" name="formName" required="" lay-verify="required"\n' +
            '                                           placeholder="请输入表单名称" autocomplete="off" class="layui-input">\n' +
            '                                </div>\n' +
            '                            </div>\n' +
            '                            <!--//end-->\n' +
            '                        </div>\n' +
            '                    </div>\n' +
            '                </div>\n' +
            '            </form>\n' +
            '        </div>\n' +
            '    </div>\n' +
            '</div>',
        formDefaultButton:'<div class="layui-form-item">\n' +
            '    <div class="layui-input-block">\n' +
            '        <button type="submit" class="layui-btn" lay-submit="" lay-filter="demo1">提交</button>\n' +
            '        <button type="reset" class="layui-btn layui-btn-primary">重置</button>\n' +
            '    </div>\n' +
            '</div>',
        importHtml:'<div class="importjsoncodeview layui-layer-wrap" style="display: none;">\n' +
            '    <textarea class="site-demo-text" id="import-json-code-view"></textarea>\n' +
            '    <a href="javascript:;" class="layui-btn layui-btn-normal" style="margin-right:20px;" id="import-json-code">导入数据</a>\n' +
            '</div>',
        exportHtml:'<div class="htmlcodeview layui-layer-wrap" style="display: none;">\n' +
            '    <textarea class="site-demo-text" id="generate-code-view"></textarea>\n' +
            '    <a href="javascript:;" class="layui-btn layui-btn-normal" style="margin-right:20px;" id="copy-html-code">复制代码</a>\n' +
            '</div>',
        htmlCode: '<html>\n' +
            '<head>\n' +
            '    <meta charset="utf-8">\n' +
            '    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">\n' +
            '    <title>表单设计器代码</title>\n' +
            '    <link rel="stylesheet" href="https://www.layuicdn.com/layui-v2.5.6/css/layui.css"/>\n' +
            '    <link rel="stylesheet" href="./ayq/modules/cron.css" />\n' +
            '    <link rel="stylesheet" href="./ayq/modules/labelGeneration.css" />\n' +
            '    <link rel="stylesheet" href="./ayq/modules/formDesigner.css" />\n' +
            '</head>\n' +
            '<body>\n' +
            '<div id="testdemo" style="margin: 10px 20px;">\n' +
            '    <form class="layui-form" style="height:100%;" id="formPreviewForm">\n' +
            '        {0}\n' +
            '        <div class="layui-form-item">\n' +
            '            <div class="layui-input-block">\n' +
            '                <button type="submit" class="layui-btn" lay-submit="" lay-filter="formPreviewForm">提交</button>\n' +
            '                <button type="reset" class="layui-btn layui-btn-primary">重置</button>\n' +
            '            </div>\n' +
            '        </div>\n' +
            '    </form>\n' +
            '</div>\n' +
            '<script type="text/javascript" src="https://www.layuicdn.com/layui-v2.5.6/layui.js"></script>\n' +
            '<script type="text/javascript" src="./ayq/modules/Sortable/Sortable.js"></script>\n' +
            '<script type="text/javascript" src="./ayq/modules/numberInput/numberInput.js"></script>\n' +
            '<script type="text/javascript" src="./ayq/modules/icon/iconPicker.js"></script>\n' +
            '<script type="text/javascript" src="./ayq/modules/cron/cron.js"></script>\n' +
            '<script type="text/javascript" src="./ayq/modules/iceEditor/iceEditor.js"></script>\n' +
            '<script type="text/javascript" src="./ayq/modules/labelGeneration/labelGeneration.js"></script>\n' +
            '<script type="text/javascript" src="./ayq/js/config.js?v=100"></script>\n' +
            '<script>\n' +
            '    layui.use(["layer", "laytpl", "element", "form", "slider", "laydate", "rate", "colorpicker", "layedit", "carousel", "upload", "formField", "numberInput", "iconPicker", "cron", "labelGeneration"], function () {\n' +
            '        var $ = layui.jquery\n' +
            '            , layer = layui.layer\n' +
            '            , laytpl = layui.laytpl\n' +
            '            , setter = layui.cache\n' +
            '            , element = layui.element\n' +
            '            , slider = layui.slider\n' +
            '            , laydate = layui.laydate\n' +
            '            , rate = layui.rate\n' +
            '            , colorpicker = layui.colorpicker\n' +
            '            , carousel = layui.carousel\n' +
            '            , form = layui.form\n' +
            '            , upload = layui.upload\n' +
            '            , layedit = layui.layedit\n' +
            '            , formField = layui.formField\n' +
            '            , hint = layui.hint\n' +
            '            , numberInput = layui.numberInput\n' +
            '            , iconPicker = layui.iconPicker\n' +
            '            , cron = layui.cron\n' +
            '            , labelGeneration = layui.labelGeneration;\n' +
            '        {1}\n' +
            '    });\n' +
            '</script>\n' +
            '</body>\n' +
            '</html>'
    }
    exports('staticField', staticField);

});