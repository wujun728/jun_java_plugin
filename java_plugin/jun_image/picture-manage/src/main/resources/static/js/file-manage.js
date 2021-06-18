"use strict";
var lay;
layui.use(["okUtils", "table", "form", 'okLayer', 'upload', 'element', 'util'], function () {
    var table = layui.table;
    var okUtils = layui.okUtils;
    var layer = layui.okLayer;
    var $ = layui.jquery;
    var form = layui.form;
    var upload = layui.upload;
    var element = layui.element;
    lay = layui;

    layui.util.fixbar({
        top: true
    });

    //常见图片格式
    let imageType = ["jpg", "jpeg", "gif", "png", "bmp", "svg", "webp"];

    table.render({
        elem: '#file-table'
        , id: "fileTable"
        , url: okUtils.baseUrl + 'api/file/list' //数据接口
        , method: "post"
        , where: {
            path: vm.currentPath
        }
        , parseData: function (res) {
            beforeRender(res);
            return {
                "code": res.errorCode, //解析接口状态
                "msg": res.errorMessage, //解析提示文本
                "count": res.result.files.length, //解析数据长度
                "data": res.result.files //解析数据列表
            };
        }
        , done: function () {
            $(".thumbnail").parent().addClass("img-col-plus");
        }
        , page: false
        , cols: [[ //表头
            {type: "checkbox"},
            {
                field: 'fileName', title: '文件名', templet: function (data) {
                    let iconClass;
                    if (data.type == "FOLDER") {
                        //文件夹
                        iconClass = "<i class='ok-icon ok-icon-file'></i>";
                    } else {
                        //普通文件
                        if (imageType.indexOf(data.fileType) != -1) {
                            //图片
                            iconClass = "<i class='ok-icon ok-icon-pic'></i>";
                            if (vm.thumbnail) {
                                return '<img onclick="call(\'onClickHandler\',\'' + data.fileName + '\')"  class="thumbnail" src="' + data.url + '"/>';
                            }
                        } else {
                            iconClass = "<i class='layui-icon layui-icon-file'></i>";
                        }
                    }

                    return iconClass + ' <span class="filename" onclick="call(\'onClickHandler\',\'' + data.fileName + '\')">' + data.fileName + '</span>';
                }
            }
            , {field: 'updateDate', title: '修改日期', sort: true, align: "center"}
            , {field: 'fileType', title: '文件格式', sort: true, align: "center"}
            , {
                field: 'size', title: '大小', sort: true, align: "center", templet: function (data) {
                    if (data.type == "FOLDER" && data.size == null) {
                        return '<a style="color: red;" href="javaScript:;" onclick="computerSize(\'' + data.fileName + '\',this)">点击计算</a>'
                    }
                    return data.size;
                }
            }
            , {
                field: 'url', title: 'URL', templet: function (data) {
                    if (data.url == null) {
                        return "";
                    }
                    return '<span class="copyUrl" data-clipboard-text="' + data.url + '">' + data.url + '</span>'
                }
            }
            , {
                field: 'action', width: "18%", title: '操作', align: "left", templet: function (data) {
                    let html = '<button type="button" onclick="call(\'deleteFile\',\'' + data.fileName + '\')" class="layui-btn layui-btn-danger layui-btn-sm">删除</button>'
                        + '<button type="button" onclick="call(\'rename\',\'' + data.fileName + '\')" class="layui-btn layui-btn-sm">重命名</button>';
                    if (data.type != 'FOLDER') {
                        html += '<button type="button" onclick="call(\'download\',\'' + data.fileName + '\')" class="layui-btn layui-btn-normal layui-btn-sm">下载</button>';
                    }
                    return html;
                }
            }
        ]]
    });


    /**
     * 渲染之前的方法
     * @param data
     */
    function beforeRender(data) {
        if (data.errorCode == 0) {
            vm.currentFiles = data.result.files;
            vm.pathList = data.result.currentPath;
            vm.fileSize = data.result.files.length;
            //设置当前路径
            let path = vm.pathList[vm.pathList.length - 1].path;
            console.log("设置的路径是:" + path);
            vm.currentPath = path;
            vm.showActionBtn = false;
        } else {
            vm.currentPath = "/";
            layer.msg.redCry(data.errorMessage);
        }
    }


    /**
     * 路径跳转
     */
    form.on("submit(stepPath)", function (data) {
        let inputPath = data.field.path;
        vm.currentPath = inputPath;
        vm.refreshTable();
        return false;
    });

    /**
     * 新建文件夹 进行弹框
     */
    form.on("submit(createFolder)", function (data) {
        let html = '<div style="padding: 10px;">' +
            '<form class="layui-form layui-form-pane" action="">' +
            '<div class="layui-form-item" pane>' +
            '    <label class="layui-form-label">文件夹名称</label>' +
            '    <div class="layui-input-block">' +
            '      <input type="text" name="folderName" required  lay-verify="required" placeholder="请输入文件夹名称" autocomplete="off" class="layui-input">' +
            '    </div>' +
            '  </div>' +
            '<div class="layui-form-item">' +
            '    <div class="layui-input-block">' +
            '      <button class="layui-btn" lay-submit lay-filter="createFolderFilter">创建</button>' +
            '    </div>' +
            '  </div>' +
            '</form>' +
            '</div>';

        layer.open("信息", html, "auto", "auto", null, null, 1);
        return false;
    });


    /**
     * 创建文件提交表单
     */
    form.on("submit(createFolderFilter)", function (data) {
        okUtils.ajax("api/file/createFolder", "post", {
            path: vm.currentPath,
            folderName: data.field.folderName
        }, true).done((result) => {
            layer.msg.greenTick("创建成功");
            vm.refreshTable();
            layer.layer.closeAll();
        });
        return false;
    });

    //执行实例
    let uploadInst = upload.render({
        elem: '#upload' //绑定元素
        , url: okUtils.baseUrl + 'api/file/upload' //上传接口
        , data: {
            path: function () {
                return vm.currentPath;
            }
        }
        , number: 1
        , accept: "file"
        , before: function (obj) {
            //上传之前的回调
            //打开一个对话框
            vm.showProgress = true;
        }
        , progress: function (n) {
            let percent = n + '%'; //获取进度百分比
            element.progress('up-progress', percent); //可配合 layui 进度条元素使用
        }
        , done: function (res) {
            //上传完毕回调
            if (res.errorCode == 0) {
                layer.msg.greenTick("文件上传成功");
                vm.refreshTable();
            } else {
                layer.msg.redCry(res.errorMessage)
            }
            vm.showProgress = false;
        }
        , error: function () {
            //请求异常回调
            layer.msg.redCross("服务器异常")
            vm.showProgress = false;
        }
    });

    //监听表格复选框选择事件
    table.on('checkbox(file-table)', function (obj) {
        if (table.checkStatus('fileTable').data.length > 0) {
            //有选择数据
            vm.showActionBtn = true;
        } else {
            vm.showActionBtn = false;
        }
    });


    /**
     * 监听开启缩略图模式
     */
    form.on("switch(mode-thumbnail)", function (data) {
        vm.thumbnail = data.elem.checked;
        vm.refreshTable();
        return false;
    });
});

/**
 * 反射调用
 * @param fn
 * @param data
 */
function call(fn, data) {
    eval("vm." + fn + "('" + data + "')");
}

function computerSize(name, dom) {
    vm.computerFolderSize(name, dom);
}

let cl = new ClipboardJS('.copyUrl');
cl.on("success", function (e) {
    // 复制成功
    lay.layer.msg("已复制好,快去粘贴吧!");
    e.clearSelection();
});

var vm = new Vue({
    el: "#app",
    data() {
        return {
            pathList: [],
            fileSize: 0,
            currentFiles: [],
            currentPath: "/",
            showActionBtn: false,
            showCopyBtn: false,
            cutFileList: [],  //剪切文件记录
            showProgress: false,
            thumbnail: false   //缩略图模式
        }
    },
    methods: {
        pathClick(data) {
            this.currentPath = data.path;
            this.refreshTable();
        },
        /**
         * 文件的点击事件
         * @param name
         */
        onClickHandler(name) {
            //查找
            for (let i = 0; i < this.currentFiles.length; i++) {
                let data = this.currentFiles[i];
                if (data.fileName == name) {
                    //判断是文件夹就打开文件夹
                    if (data.type == "FOLDER") {
                        this.currentPath += "/" + data.fileName;
                        this.refreshTable();
                    } else {
                        lay.layer.open({
                            type: 2,
                            title: '预览',
                            shadeClose: true,
                            shade: false,
                            maxmin: true, //开启最大化最小化按钮
                            area: ['90%', '90%'],
                            content: data.url
                        });
                    }
                    break;
                }
            }
        },
        /**
         * 刷新表格数据
         */
        refreshTable() {
            lay.table.reload('fileTable', {
                where: {
                    path: this.currentPath
                }
            });
        },
        /**
         * 上一级
         */
        backStep() {
            if (this.pathList.length > 1 && this.pathList.length - 2 >= 0) {
                let item = this.pathList[this.pathList.length - 2];
                this.currentPath = item.path;
                this.refreshTable();
            }
        },
        /**
         * 计算文件夹的大小
         * @param name
         */
        computerFolderSize(name, dom) {
            lay.okUtils.ajax("api/file/computerFolderSize", "post", {
                path: this.currentPath + "/" + name
            }).done((response) => {
                let parentDom = lay.$(dom).parent();
                lay.$(dom).remove();
                lay.$(parentDom).append(response);
            })
        },
        /**
         * 删除一个文件
         * @param name
         */
        deleteFile(name) {
            let _this = this;
            lay.layer.confirm("您是否确认删除这个文件,这个操作是不可逆的", function () {
                lay.okUtils.ajax("api/file/delete", "post", JSON.stringify([_this.currentPath + "/" + name]), false, true).done((response) => {
                    lay.layer.msg("删除成功");
                    _this.refreshTable();
                })
            });
        },
        /**
         * 点击剪切按钮
         */
        cutFiles() {
            //获取选择的行
            let list = lay.table.checkStatus('fileTable').data;
            this.cutFileList.length = 0;
            list.forEach((item, index) => {
                this.cutFileList.push(this.currentPath + "/" + item.fileName);
            });
            this.showCopyBtn = true;
            lay.layer.msg("剪切成功,请到对应文件夹粘贴");
        },
        /**
         * 点击粘贴按钮
         */
        copyThis() {
            console.log(this.cutFileList);
            lay.okUtils.ajax("api/file/move", "post", JSON.stringify({
                files: this.cutFileList,
                targetPath: this.currentPath
            }), true, true).done((response) => {
                lay.layer.msg("移动成功");
                this.refreshTable();
            });
            this.showCopyBtn = false;
        },
        download(name) {
            //获取Path
            for (let i = 0; i < this.currentFiles.length; i++) {
                let data = this.currentFiles[i];
                if (data.fileName == name) {
                    window.open(data.url);
                    break;
                }
            }
        },
        /**
         * 重命名
         * @param name
         */
        rename(name) {
            let _this = this;
            lay.layer.prompt({title: '输入新文件名，并确认', formType: 0, value: name}, function (text, index) {
                //检查是否有重命的文件
                let result = false;
                for (let i = 0; i < _this.currentFiles.length; i++) {
                    let data = _this.currentFiles[i];
                    if (data.fileName == text) {
                        result = true;
                        break;
                    }
                }
                if (!result) {
                    //检查完毕 提交服务器
                    lay.okUtils.ajax("api/file/rename", "post", {
                        name: text,
                        path: _this.currentPath + "/" + name
                    }).done((response) => {
                        lay.layer.msg("重命名成功");
                        _this.refreshTable();
                    })
                } else {
                    lay.layer.msg('文件有重名', {
                        icon: 5,
                        time: 2000
                    })
                }
                lay.layer.close(index);
            });
        },
        /**
         * 批量删除
         */
        batchDelete() {
            let list = lay.table.checkStatus('fileTable').data;
            let pathList = [];
            list.forEach((item, index) => {
                pathList.push(this.currentPath + "/" + item.fileName);
            });
            let _this = this;
            lay.layer.confirm("您是否确认删除这些文件,这个操作是不可逆的", function () {
                lay.okUtils.ajax("api/file/delete", "post", JSON.stringify(pathList), false, true).done((response) => {
                    lay.layer.msg("删除成功");
                    _this.refreshTable();
                })
            });
        }
    }
});