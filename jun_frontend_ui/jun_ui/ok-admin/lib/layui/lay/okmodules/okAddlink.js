layui.define(['form', 'jquery'], function (exports) {
    var $ = layui.jquery;
    var form = layui.form;
    var $form = $('form');//当前表单元素

    /**
     * 传入需要联动的三个下拉选择元素（顺序：省、市、区）
     * {
        province:'select[name=province]',
        city:'select[name=city]',
        area:'select[name=area]',
     }
     */
    var config = {
        defElm: {
            province: 'select[name=province]',
            city: 'select[name=city]',
            area: 'select[name=area]',
        }
    };
    var addlink = {
        elms: '',
        init: function (elms = "") {
            elms = elms || config.defElm;
            this.elms = elms;
            return this;
        },
        render: function () {
            this.elms = this.elms || config.defElm;
            slect_init(this.elms);
        }
    };

    function slect_init(elms) {
        var el_province = $(elms.province);
        var el_city = $(elms.city);
        var el_area = $(elms.area);

        $.get('../../data/address.json', function (data) {
            loadProvince(data);
        });

        //加载省数据
        function loadProvince(areaData) {
            var proHtml = '';
            for (var i = 0; i < areaData.length; i++) {
                proHtml += '<option value="' + areaData[i].provinceCode + '_' + areaData[i].mallCityList.length + '_' + i + '">' + areaData[i].provinceName + '</option>';
            }
            //初始化省数据
            $form.find(el_province).append(proHtml);
            form.render();
            var _filter = el_province.attr("lay-filter");
            form.on('select(' + _filter + ')', function (data) {
                $form.find(el_city).html('<option value="">请选择县/区</option>');
                var value = data.value;
                var d = value.split('_');
                var code = d[0];
                var count = d[1];
                var index = d[2];
                if (count > 0) {
                    loadCity(areaData[index].mallCityList);
                } else {
                    $form.find(el_city).attr("disabled", "disabled");
                }
            });
        }

        //加载市数据
        function loadCity(citys) {
            var cityHtml = '<option value="">请选择市</option>';
            for (var i = 0; i < citys.length; i++) {
                cityHtml += '<option value="' + citys[i].cityCode + '_' + citys[i].mallAreaList.length + '_' + i + '">' + citys[i].cityName + '</option>';
            }
            $form.find(el_city).html(cityHtml).removeAttr("disabled");
            form.render();
            var _filter = el_city.attr("lay-filter");
            form.on('select(' + _filter + ')', function (data) {
                var value = data.value;
                var d = value.split('_');
                var code = d[0];
                var count = d[1];
                var index = d[2];
                if (count > 0) {
                    loadArea(citys[index].mallAreaList);
                } else {
                    $form.find(el_area).attr("disabled", "disabled");
                }
            });
        }

        //加载县/区数据
        function loadArea(areas) {
            var areaHtml = '<option value="">请选择县/区</option>';
            for (var i = 0; i < areas.length; i++) {
                areaHtml += '<option value="' + areas[i].areaCode + '">' + areas[i].areaName + '</option>';
            }
            $form.find(el_area).html(areaHtml).removeAttr("disabled");
            form.render();
            var _filter = el_city.attr("lay-filter");
            form.on('select(' + _filter + ')', function (data) {
            });
        }
    };

    exports('okAddlink', addlink);
});
