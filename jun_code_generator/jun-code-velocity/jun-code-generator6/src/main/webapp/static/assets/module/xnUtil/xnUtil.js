/**
 * Snowy工具集
 */
layui.define(['jquery', 'admin', 'formX', 'xmSelect', 'notice'], function (exports) {
    var $ = layui.$;
    var admin = layui.admin;
    var formX = layui.formX;
    var xmSelect = layui.xmSelect;
    var notice = layui.notice;

    //获取当前登录用户
    var loginUser = layui.data(admin.setter.tableName).loginUser;

    // 权限列表
    var authList = [];

    if(loginUser !== null && loginUser !== undefined ) {
        authList = loginUser.permissions;
    }

    var xnUtil = {

        /**
         * 判断是否有权限
         */
        hasPerm: function (p) {
            if(loginUser !== null && loginUser !== undefined ) {
                if (authList) for (var i = 0; i < authList.length; i++) if (p === authList[i]) return true;
            }
            return false;
        },

        /**
         * 移除没有权限的元素
         */
        renderPerm: function () {
            // 如果loginUser不为空
            if(loginUser !== null && loginUser !== undefined ) {
                if(loginUser.adminType !== 1) {
                    $('[perm-show]').each(function () {
                        // TODO 暂时先放开权限
                        //if (!xnUtil.hasPerm($(this).attr('perm-show'))) $(this).remove();
                    });
                }
            }
        },

        /**
         * 缓存字典信息
         */
        cacheDictData: function () {
            // 获取字典数据
            // admin.req(getProjectUrl() + 'sysDictType/tree', function(res){
            admin.req(getProjectUrl() + 'dev-api/qixing/dictTree', function(res){
                // 并存储
                layui.data(admin.setter.tableName, {key: 'dictData', value: res.data});
            });
        },

        /**
         * 渲染字典信息
         *
         * 字典值编码，字典类型编码
         */
        rendDataTableDict: function (dictCode, dictTypeCode) {
            var result = '未知';
            var dictData = layui.data(admin.setter.tableName).dictData;
            if(dictData === null || dictData === undefined) {
                return result;
            }
            dictData.forEach(function (item) {
                if(dictTypeCode.toString() === item.code) {
                    item.children.forEach(function (childrenItem) {
                        if(dictCode.toString() === childrenItem.code) {
                            result = childrenItem.name;
                        }
                    });
                }
            });
            return result;
        },

        /**
         * 根据字典类型编码获取子项
         */
        getDictDataByDictTypeCode: function (dictTypeCode, exclude) {
            var result = [];
            var dictData = layui.data(admin.setter.tableName).dictData;
            if(dictData === null || dictData === undefined) {
                return result;
            }
            dictData.forEach(function (item) {
                if(dictTypeCode.toString() === item.code) {
                    if(exclude !== null && exclude !== undefined && exclude.length !== 0) {
                        for(var i=0; i<item.children.length; i++) {
                            var indexOf = exclude.indexOf(item.children[i].name);
                            if(indexOf !== -1) {
                                item.children.splice(i, 1);
                            }
                        }
                    }
                    result = item.children;
                }
            });
            return result;
        },
        getDictDataByDictTypeCodeV2: function (dictTypeCode, exclude) {
            var result = [];
            admin.req(getProjectUrl() + '/dev-api/system/dict/data/type/'+dictTypeCode, function(res){
                // 并存储
                layui.data(admin.setter.tableName, {key: 'dictData', value: res.data});
            });
            // var dictData = layui.data(admin.setter.tableName).dictData;
            // if(dictData === null || dictData === undefined) {
            //     return result;
            // }
            // dictData.forEach(function (item) {
            //     if(dictTypeCode.toString() === item.code) {
            //         if(exclude !== null && exclude !== undefined && exclude.length !== 0) {
            //             for(var i=0; i<item.children.length; i++) {
            //                 var indexOf = exclude.indexOf(item.children[i].name);
            //                 if(indexOf !== -1) {
            //                     item.children.splice(i, 1);
            //                 }
            //             }
            //         }
            //         result = item.children;
            //     }
            // });
            return result;
        },

        /**
         * 渲染字典下拉
         *
         * 表单filter名，字段名，字典类型编码，占位文字，需排除文字
         */
        rendDictDropDown: function (filterName, fieldName, dictTypeCode, placeholderName, exclude) {
            var elem;
            if(filterName !== null && filterName !== undefined) {
                elem = '[lay-filter="'+ filterName + '"] select[name="' + fieldName + '"]';
            } else {
                elem = 'select[name="' + fieldName + '"]';
            }
            return formX.renderSelect({
                elem: elem,
                data: xnUtil.getDictDataByDictTypeCode(dictTypeCode, exclude),
                name: 'name',
                value: 'code',
                hint: placeholderName
            });
        },

        /**
         * 渲染远程数据下拉
         *
         * 元素id，字段名，远程地址，是否单选，占位文字
         */
        rendRemoteDataDropDown: function (elem, fieldName, name, value, remoteUrl, isSingleSelect, placeholderName) {
            var remoteDataRenderIns = {};
            admin.req(remoteUrl, function(res){
                remoteDataRenderIns = xmSelect.render({
                    el: elem,
                    name: fieldName,
                    data: res.data,
                    layVerify: 'required',
                    layVerType: 'tips',
                    radio: isSingleSelect,
                    prop: {
                        name: name,
                        value: value
                    },
                    hint: placeholderName
                });
            }, {async: false});
            return remoteDataRenderIns;
        },

        /**
         * 表格渲染之后的回调
         */
        tableDone: function(insTb, res, curr, count) {
            if((res.data && res.data.length === 0 && count !== 0) 
                || (res.rows && res.rows.length === 0 && count !== 0)) {
                if(curr !== 1) {
                    insTb.reload({page: {curr: curr - 1}});
                } else {
                    console.log(" tableDone event insTb.reload ignore ")
                    //insTb.reload({page: {curr: 1}});
                }
            }
            xnUtil.renderPerm();
        },

        /**
         * 递归处理没有子节点的ztree数据
         */
        handleNoChildrenZtreeData: function (data) {
            if(data != undefined && data != null ){
                data.forEach(function (value) {
                    if(value.children && value.children.length === 0) {
                        delete value.children;
                    } else {
                        xnUtil.handleNoChildrenZtreeData(value.children);
                    }
                })
                return data;
            }
        },

        /**
         * 递归处理将ztree数据展开，spread方式
         */
        handleZtreeDataSpread: function (data) {
            data.forEach(function (value) {
                if(value.children && value.children.length !== 0) {
                    value.spread = true;
                    xnUtil.handleZtreeDataSpread(value.children);
                }
            })
            return data;
        },

        /**
         * 递归处理将ztree数据展开，open方式
         */
        handleZtreeDataOpen: function (data) {
            data.forEach(function (value) {
                if(value.children && value.children.length !== 0) {
                    value.open = true;
                    xnUtil.handleZtreeDataOpen(value.children);
                }
            })
            return data;
        },

        /**
         * 构造树顶级节点
         */
        handleZtreeDataRootNode: function (data) {
            var resultData = [];
            var rootData = {};
            rootData.id = 0;
            rootData.parentId = -1;
            rootData.title = '顶级';
            rootData.value = '0';
            rootData.weight = 100;
            rootData.children = data;
            resultData.push(rootData);
            return resultData;
        },

        /**
         * 处理时间范围选择结果，要返回的数据，时间范围选择器元素，开始时间字段，结束时间字段
         */
        handleRangeDateSelectResult: function (data, elem, startTime, endTime) {
            var tempStartTime = '';
            var tempEndTime = '';
            var tempStartEndTime = $(elem).val();
            if(tempStartEndTime === ''){
                tempStartTime = '';
                tempEndTime = '';
            }else{
                tempStartTime = tempStartEndTime.split("~")[0].trim();
                tempEndTime = tempStartEndTime.split("~")[1].trim();
                if(tempStartTime === tempEndTime) {
                    tempStartTime += " 00:00:00";
                    tempEndTime += " 23:59:59";
                } else {
                    tempStartTime += " 00:00:00";
                    tempEndTime += " 00:00:00";
                }
            }
            data.field[startTime] = tempStartTime;
            data.field[endTime] = tempEndTime;
        },

        /**
         * 判断数据是否json
         */
        isJson: function(obj) {
            var isJson = typeof(obj) == "object" && Object.prototype.toString.call(obj).toLowerCase() == "[object object]" && !obj.length;
            return isJson;
        },

        /**
         * 开启websocket
         */
        openWebSocket: function () {
            var ws;
            if ("WebSocket" in window) {
                //当前用户不为空时才开启
                if(loginUser !== null && loginUser !== undefined ) {
                    //如果已经有连接，则断开并重新连接
                    if(ws != null){
                        ws.close();
                        ws = null;
                    }
                    // 创建一个 websocket
                    ws = new WebSocket(getProjectUrl().replace("https","wss").replace("http","ws") + "webSocket/" + loginUser.id);
                    ws.onopen = function() { console.log('WebSocket连接已建立') };
                    ws.onmessage = function (evt) {
                        var received_msg = evt.data;
                        notice.show({
                            title: '消息通知',
                            message: received_msg
                        });
                    };
                    ws.onclose = function() { console.log('WebSocket连接已关闭') };
                }
            } else {
                // 浏览器不支持 WebSocket
                notice.msg('您的浏览器不支持 WebSocket!', {icon: 2});
            }
        }
    };

    exports('xnUtil', xnUtil);
});