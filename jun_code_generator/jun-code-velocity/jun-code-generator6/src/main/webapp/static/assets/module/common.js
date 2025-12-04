;
"use strict";
layui.define(["layer", "jquery"], function (exports) {
    var $ = layui.jquery;
    var obj = {
        resizeTable:function(tableId){
            layui.table.resize(tableId);
        }
        // 字符串格式化(%s )
        ,sprintf: function (str) {
            var args = arguments, flag = true, i = 1;
            str = str.replace(/%s/g, function () {
                var arg = args[i++];
                if (typeof arg === 'undefined') {
                    flag = false;
                    return '';
                }
                return arg;
            });
            return flag ? str : '';
        },
        // 比较两个字符串（大小写敏感）
        equals: function (str, that) {
            return str == that;
        },
        // 比较两个字符串（大小写不敏感）
        equalsIgnoreCase: function (str, that) {
            return String(str).toUpperCase() === String(that).toUpperCase();
        },
        // 判断字符串是否为空
        isEmpty: function (value) {
            if (typeof (value) === "undefined" || value == null || this.trim(value) == "") {
                return true;
            }
            return false;
        },
        // 如果空值返回kong
        formatNullStr: function (o) {
            if (this.isEmpty(o)) {
                return "";
            } else {
                return o;
            }
        },
        // 递归获取json中某个key的值数组
        getJsonArrayValue: function (array, key, keyChecked, keyId) {
            var aa = [];
            for (var a in array) {
                var _item = array[a];
                if (_item[keyChecked]) {
                    aa.push(_item[keyId]);
                }
                if (typeof (_item[key]) != "undefined" && _item[key].length > 0) {
                    var _aa = this.getJsonArrayValue(_item[key], key, keyChecked, keyId);
                    if (_aa != null && _aa.length > 0) {
                        for (var _a in _aa) {
                            aa.push(_aa[_a]);
                        }
                    }
                }
            }
            return aa;
        },
        // 空格截取
        trim: function (value) {
            if (value == null) {
                return "";
            }
            return value.toString().replace(/(^\s*)|(\s*$)|\r|\n/g, "");
        },
        // 指定随机数返回
        random: function (min, max) {
            return Math.floor((Math.random() * max) + min);
        },
        /**
         * 提取形如{role[0]:'1',role[1]:'1'}这样的复选框的值
         * @param json
         * @param name
         */
        getCheckValues: function (name) {
            var _items = $('input:checkbox[name*="' + name + '"]:checked');
            var _itemsStr = "";
            layui.each(_items, function (i, n) {
                _itemsStr += "," + $(n).val();
            });
            if (_itemsStr.length > 0) {
                return _itemsStr.substr(1, _itemsStr.length);
            }
            return "";
        },
        /**
         * 将array中的json对象中的key为key的合并为1,2,3的形式
         * @param array
         * @param key
         */
        joinArray: function (array, key) {
            var _itemsStr = "";
            layui.each(array, function (i, n) {
                _itemsStr += "," + n[key];
            });
            if (_itemsStr.length > 0) {
                return _itemsStr.substr(1, _itemsStr.length);
            }
            return "";
        },
        getDictLabel: function (array, value) {
            var actions = [];
            layui.each(array, function (i, n) {
                if (n.dictValue === value) {
                    actions.push(n.dictLabel);
                }
            });
            return actions.join('');
        },
        ajaxRemove: function (removeUrl, id, cb) {
            if(id=='' || id==undefined){
                layui.layer.alert('请选择删除数据！');
                return;
            }
            var url = this.isEmpty(id) ? removeUrl : removeUrl.replace("{id}", id);
            var msg = (id.length > 0 && id.indexOf(",") > 0) ? "是否确认删除这些项？" : "是否确认删除该项？";
            layer.confirm(msg, function (index) {
                $.ajax({
                    type: "DELETE",
                    url: url,
                    //data:{"_method":"DELETE"},
                    headers: {"Authorization": localStorage.getItem("Authorization"),"_method":"DELETE"},
                    async: true,
                    cache: false,
                    dataType: "json",
                    data: {"ids": id},
                    success: function (res) {
                        if (typeof (cb) === "function") {
                            cb(res);
                        }
                        layer.close(index);
                    }
                });
            });
        },
        ajax: {
            // 提交数据
            submit: function (url, type, dataType, data, cb) {
                var config = {
                    url: url,
                    headers: {"Authorization": localStorage.getItem("Authorization")},
                    type: type,
                    dataType: dataType,
                    data: data,
                    beforeSend: function () {
                        //layer.loading("正在处理中，请稍后...");
                        layer.load(2);
                    },
                    success: function (result) {
                        layer.closeAll('loading');
                        if (typeof (cb) === "function") {
                            cb(result);
                        }
                    }
                };
                $.ajax(config)
            },
            // post请求传输
            post: function (url, data, cb) {
                obj.ajax.submit(url, "post", "json", data, cb);
            },
            // get请求传输
            get: function (url, cb) {
                obj.ajax.submit(url, "get", "json", "", cb);
            }
        },
        verify: {
            roleKey: function (value, item) {
                var msg;
                if (!new RegExp("^[a-zA-Z0-9_\u4e00-\u9fa5\\s·]+$").test(value)) {
                    msg = 'roleKey不能有特殊字符';
                }
                $.ajax({
                    type: "POST",
                    url: "/system/role/checkRoleKeyUnique",
                    headers: {"Authorization": localStorage.getItem("Authorization")},
                    async: false,
                    cache: false,
                    dataType: "json",
                    data: {
                        roleKey: $("[name='roleKey']").val()
                    },
                    success: function (res) {
                        if (res != "0") {
                            msg = "roleKey已存在，请修改！";
                        }
                    },
                    error: function () {
                        msg = "验证roleKey出错！";
                    }
                });
                return msg;
            },
            roleName: function (value, item) {
                var msg;
                if (!new RegExp("^[a-zA-Z0-9_\u4e00-\u9fa5\\s·]+$").test(value)) {
                    msg = 'roleName不能有特殊字符';
                }
                $.ajax({
                    type: "POST",
                    url: "/system/role/checkRoleNameUnique",
                    headers: {"Authorization": localStorage.getItem("Authorization")},
                    async: false,
                    cache: false,
                    dataType: "json",
                    data: {
                        roleName: $("[name='roleName']").val()
                    },
                    success: function (res) {
                        if (res != "0") {
                            msg = "roleName已存在，请修改！";
                        }
                    },
                    error: function () {
                        msg = "验证roleName出错！";
                    }
                });
                return msg;
            }
        }
    };
    exports('common', obj);
});