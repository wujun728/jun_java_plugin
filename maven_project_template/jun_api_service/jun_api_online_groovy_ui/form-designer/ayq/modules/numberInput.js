layui.define(['jquery','util','layer'],function (exports) {
    var $ = layui.$,
        util = layui.util,
        layer = layui.layer,
        input_elem = 'input[number-input]',
        baseClassName = 'layui-input-number',
        numberInputBtn = [
            '<div class="layui-number-input-btn">',
            '<i class="layui-icon layui-icon-up" lay-click="numberUp"></i>',
            '<i class="layui-icon layui-icon-down" lay-click="numberDown"></i>',
            '</div>',
        ].join(''),
        style = [
            '<style type="text/css">',
            '.layui-number-input-container{position:relative;width:100%}',
            '.'+baseClassName+'::-webkit-outer-spin-button,.'+baseClassName+'::-webkit-inner-spin-button{-webkit-appearance: none;}',
            '.'+baseClassName+'{-moz-appearance: textfield;padding-right:22px}',
            '.layui-number-input-btn{position:absolute;top:0;right:0;width:20px;height:100%;}',
            '.layui-number-input-btn i{position:absolute;width:20px;height:50%;bottom:0;font-size:12px;text-align:center;line-height:16px;cursor: pointer;}',
            '.layui-number-input-btn i:first-child{top:0}',
            '.layui-number-input-btn i:hover{background:#eee;}',
            '</style>'
        ].join('');
    $('head link:last')[0] && $('head link:last').after(style) || $('head').append(style);

    var numberInput = {
        options:{
            elem:input_elem
        },
        render:function (option) {
            var _this = this;
            _this.options = $.extend(_this.options, option);
            $(_this.options.elem).not('[lay-ignore]').addClass(baseClassName).wrap('<div class="layui-number-input-container"></div>');
            $(_this.options.elem).not('[lay-ignore]').after(numberInputBtn);
            _this.listen();
        },
        listen:function () {
            var _this = this;
            $(_this.options.elem).bind('input propertychange',function () {
                var value = $(this).val();
                $(this).val(value.replace(/[^\-?\d.]/g,''));
            });

            $(_this.options.elem).keydown(function (event) {
                var e = event||window.event;
                var k = e.keyCode || e.which;
                switch (k) {
                    case 38:
                        // 按下向上箭头
                        $(this).siblings('.layui-number-input-btn').children('[lay-click="numberUp"]').trigger('click').css('background','#eee');
                        break;
                    case 40:
                        // 按下向上箭头
                        $(this).siblings('.layui-number-input-btn').children('[lay-click="numberDown"]').trigger('click').css('background','#eee');
                        break;
                    default:
                        break;
                }
                return false;
            });
            $(_this.options.elem).keyup(function () {
                var e = event||window.event;
                var k = e.keyCode || e.which;
                switch (k) {
                    case 38:
                        // 按下向上箭头
                        $(this).siblings('.layui-number-input-btn').children('[lay-click="numberUp"]').css('background','');
                        break;
                    case 40:
                        // 按下向上箭头
                        $(this).siblings('.layui-number-input-btn').children('[lay-click="numberDown"]').css('background','');
                        break;
                    default:
                        break;
                }
                return false;
            });
            var timeOut;
            //长按
            $('[lay-click="numberUp"],[lay-click="numberDown"]').mousedown(function () {
                var $this = $(this);
                timeOut = setInterval(function () {
                    $this.trigger('click');
                },200)
            });
            $('[lay-click="numberUp"],[lay-click="numberDown"]').mouseup(function () {
                clearInterval(timeOut);
            });
            $('[lay-click="numberUp"],[lay-click="numberDown"]').mouseout(function () {
                clearInterval(timeOut);
            });
            util.event('lay-click',{
                numberUp:function (othis) {
                    var thisInput = othis.parent().parent().children('.'+baseClassName);
                    var thisInputValue = Number(thisInput.val()||0);
                    var step = thisInput.attr('step')||1;
                    var maxValue = Number(thisInput.attr('max'));
                    if (maxValue!=undefined){
                        if (thisInputValue>=maxValue){
                            layer.tips('最大值'+maxValue,thisInput,{tips:1});
                            return false;
                        }
                    }
                    thisInputValue=_this.add(thisInputValue,parseFloat(step));
                    thisInput.val('').focus().val(thisInputValue);
                },
                numberDown:function(othis){
                    var thisInput = othis.parent().parent().children('.'+baseClassName);
                    var thisInputValue = thisInput.val()||0;
                    var step = thisInput.attr('step')||1;
                    var minValue = thisInput.attr('min');
                    if (minValue!=undefined){
                        if (thisInputValue<=minValue){
                            layer.tips('最小值'+minValue,thisInput,{tips:1});
                            return false;
                        }
                    }
                    thisInputValue = _this.subtraction(thisInputValue,step);
                    thisInput.val('').focus().val(thisInputValue);
                }
            });
        },
        /**
         * 加法
         * @param arg1
         * @param arg2
         * @returns {number}
         */
        add:function (arg1, arg2) {
            var r1,r2,m;
            try{r1=arg1.toString().split(".")[1].length}catch(e){r1=0}
            try{r2=arg2.toString().split(".")[1].length}catch(e){r2=0}
            m=Math.pow(10,Math.max(r1,r2))
            return (this.multiplication(arg1,m)+this.multiplication(arg2,m))/m
        },
        /**
         * 减法
         * @param arg1 被减数
         * @param arg2 减数
         * @returns {string}
         */
        subtraction:function (arg1, arg2) {
            var r1, r2, m, n;
            try {
                r1 = arg1.toString().split(".")[1].length;
            }
            catch (e) {
                r1 = 0;
            }
            try {
                r2 = arg2.toString().split(".")[1].length;
            }
            catch (e) {
                r2 = 0;
            }
            m = Math.pow(10, Math.max(r1, r2));
            //last modify by deeka
            //动态控制精度长度
            n = (r1 >= r2) ? r1 : r2;
            return ((arg1 * m - arg2 * m) / m).toFixed(n);
        },
        /**
         * 乘法
         * @param arg1
         * @param arg2
         * @returns {number}
         */
        multiplication:function (arg1,arg2) {
            var m=0,s1=arg1.toString(),s2=arg2.toString();
            try{m+=s1.split(".")[1].length}catch(e){}
            try{m+=s2.split(".")[1].length}catch(e){}
            return Number(s1.replace(".",""))*Number(s2.replace(".",""))/Math.pow(10,m)
        },
        /**
         * 除法
         * @param arg1
         * @param arg2
         * @returns {*|number}
         */
        division:function (arg1,arg2) {
            var t1 = 0, t2 = 0, r1, r2;
            try {
                t1 = arg1.toString().split(".")[1].length
            } catch (e) {
            }
            try {
                t2 = arg2.toString().split(".")[1].length
            } catch (e) {
            }
            with (Math) {
                r1 = Number(arg1.toString().replace(".", ""))
                r2 = Number(arg2.toString().replace(".", ""))
                return this.multiplication((r1 / r2), pow(10, t2 - t1));
            }
        }
    };
    //外部接口
    var exportApi = {
        render:function(option){
            numberInput.render(option)
        },
    };
    exports('numberInput',exportApi);
});