<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <title>XXXX管理</title>
    <link rel="stylesheet" href="/static/assets/libs/layui/css/layui.css"/>
    <link rel="stylesheet" href="/static/assets/module/admin.css?v=317"/>
    <!--[if lt IE 9]>
    <script src="https://oss.maxcdn.com/html5shiv/3.7.3/html5shiv.min.js"></script>
    <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->
</head>
<body>
<!-- 正文开始 -->
<div class="layui-fluid">
    <div class="layui-card">
        <div class="layui-card-body">
            <!-- 表格工具栏 -->
            <form class="layui-form toolbar">
                <div class="layui-form-item">
                    <#if classInfo.fieldList?exists && classInfo.fieldList?size gt 0>
                        <#list classInfo.fieldList as fieldItem >
                            <#if fieldItem.nullable==false>
                            <div class="layui-inline">
                                <label class="layui-form-label">${fieldItem.fieldComment}:</label>
                                <div class="layui-input-inline">
                                    <input name="${fieldItem.fieldName}" class="layui-input" placeholder="输入${fieldItem.fieldComment}"/>
                                </div>
                            </div>
                              </#if>
                        </#list>
                    </#if>
                    <#--<div class="layui-inline">
                        <label class="layui-form-label">角色名:</label>
                        <div class="layui-input-inline">
                            <input name="roleName" class="layui-input" placeholder="输入角色名"/>
                        </div>
                    </div>
                    <div class="layui-inline">
                        <label class="layui-form-label">角色代码:</label>
                        <div class="layui-input-inline">
                            <input name="roleCode" class="layui-input" placeholder="输入角色代码"/>
                        </div>
                    </div>
                    <div class="layui-inline">
                        <label class="layui-form-label">备&emsp;注:</label>
                        <div class="layui-input-inline">
                            <input name="comments" class="layui-input" placeholder="输入备注"/>
                        </div>
                    </div>-->
                    <div class="layui-inline">&emsp;
                        <button class="layui-btn icon-btn" lay-filter="roleTbSearch" lay-submit>
                            <i class="layui-icon">&#xe615;</i>搜索
                        </button>
                    </div>
                </div>
            </form>
            <!-- 数据表格 -->
            <table id="roleTable" lay-filter="roleTable"></table>
        </div>
    </div>
</div>

<!-- 表格操作列 -->
<script type="text/html" id="roleTbBar">
    <a class="layui-btn layui-btn-primary layui-btn-xs" lay-event="edit">修改</a>
    <a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="del">删除</a>
<#--    <a class="layui-btn layui-btn-warm layui-btn-xs" lay-event="auth">权限分配</a>-->
</script>
<!-- 表单弹窗 -->
<script type="text/html" id="roleEditDialog">
    <form id="roleEditForm" lay-filter="roleEditForm" class="layui-form model-form">
        <input name="roleId" type="hidden"/>
        <#if classInfo.fieldList?exists && classInfo.fieldList?size gt 0>
            <#list classInfo.fieldList as fieldItem >
                <div class="layui-form-item">
                    <label class="layui-form-label layui-form-required">${fieldItem.fieldComment}:</label>
                    <div class="layui-input-block">
                        <input name="${fieldItem.fieldName}" placeholder="请输入${fieldItem.fieldComment}" class="layui-input"
                               lay-verType="tips"  <#if fieldItem.nullable==false>lay-verify="required" required</#if>  />

                    </div>
                </div>
            </#list>
        </#if>

        <#--<div class="layui-form-item">
            <label class="layui-form-label layui-form-required">角色名:</label>
            <div class="layui-input-block">
                <input name="roleName" placeholder="请输入角色名" class="layui-input"
                       lay-verType="tips" lay-verify="required" required/>
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label layui-form-required">角色代码:</label>
            <div class="layui-input-block">
                <input name="roleCode" placeholder="请输入角色代码" class="layui-input"
                       lay-verType="tips" lay-verify="required" required/>
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label">备注:</label>
            <div class="layui-input-block">
                <textarea name="comments" placeholder="请输入备注" class="layui-textarea"></textarea>
            </div>
        </div>-->
        <div class="layui-form-item text-right">
            <button class="layui-btn" lay-filter="roleEditSubmit" lay-submit>保存</button>
            <button class="layui-btn layui-btn-primary" type="button" ew-event="closeDialog">取消</button>
        </div>
    </form>
</script>

<!-- js部分 -->
<script type="text/javascript" src="/static/assets/libs/layui/layui.js"></script>
<script type="text/javascript" src="/static/assets/js/common.js?v=317"></script>
<script type="text/javascript" src="/static/assets/libs/jquery/jquery-3.2.1.min.js"></script>
<script type="text/javascript" src="/static/assets/js/core.util.js"></script>
<script>
    layui.use(['layer', 'form', 'table', 'util', 'admin', 'zTree'], function () {
        var $ = layui.jquery;
        var layer = layui.layer;
        var form = layui.form;
        var table = layui.table;
        var util = layui.util;
        var admin = layui.admin;

        /* 渲染表格 */
        var insTb = table.render({
            elem: '#roleTable',
            method: 'post',
            contentType: 'application/json',
            url: '/${classInfo.className?uncap_first}/list',
            page: true,
            toolbar: ['<p>',
                '<button lay-event="add" class="layui-btn layui-btn-sm icon-btn"><i class="layui-icon">&#xe654;</i>添加</button>&nbsp;',
                '<button lay-event="del" class="layui-btn layui-btn-sm layui-btn-danger icon-btn"><i class="layui-icon">&#xe640;</i>删除</button>',
                '</p>'].join(''),
            cellMinWidth: 100,
            cols: [[
                {type: "checkbox", width: 50, fixed: "left"},
                <#if classInfo.fieldList?exists && classInfo.fieldList?size gt 0>
                <#list classInfo.fieldList as fieldItem >
                {field: '${fieldItem.fieldName}', title: '${fieldItem.fieldComment}', sort: true}, <#if fieldItem_has_next> </#if>
                </#list>
                </#if>
                /* 需要时间请自行解封
                {title: '创建时间', sort: true,templet: "<div>{{layui.util.toDateString(d.createTime, 'yyyy-MM-dd')}}</div>"},
                {title: '修改时间', sort: true,templet: "<div>{{layui.util.toDateString(d.updateTime, 'yyyy-MM-dd')}}</div>"},
                */
                {title: '操作', minWidth: 400, templet: '#roleTbBar', fixed: "right", align: "center"}
            ]],
            limits: [20 , 50 , 100],
            limit: 20,
            page: true
        });

        /* 表格搜索 */
        form.on('submit(roleTbSearch)', function (data) {
            insTb.reload({where: data.field, page: {curr: 1}});
            return false;
        });

        /* 表格工具条点击事件 */
        table.on('tool(roleTable)', function (obj) {
            if (obj.event === 'edit') { // 修改
                showEditModel(obj.data);
            } else if (obj.event === 'del') { // 删除
                //doDel(obj);
                doDel({ids: [obj.data.id]});
            } else if (obj.event === 'auth') {  // 权限管理
                showPermModel(obj.data.roleId);
            }
        });

        /* 表格头工具栏点击事件 */
        table.on('toolbar(roleTable)', function (obj) {
            if (obj.event === 'add') { // 添加
                showEditModel();
            } else if (obj.event === 'del') { // 删除
                var checkRows = table.checkStatus('roleTable');
                if (checkRows.data.length === 0) {
                    layer.msg('请选择要删除的数据', {icon: 2});
                    return;
                }
                var ids = checkRows.data.map(function (d) {
                    return d.id; //TODO
                });
                doDel({ids: ids});
            }
        });

        /* 显示表单弹窗 */
        function showEditModel(mData) {
            admin.open({
                type: 1,
                title: (mData ? '修改' : '添加') + '${classInfo.classComment}'  ,
                content: $('#roleEditDialog').html(),
                success: function (layero, dIndex) {
                    // 回显表单数据
                    form.val('roleEditForm', mData);
                    // 表单提交事件
                    form.on('submit(roleEditSubmit)', function (data) {
                        var loadIndex = layer.load(2);
                        // $.get(mData ? '../../json/ok.json' : '../../json/ok.json', data.field, function (res) {
                        //     layer.close(loadIndex);
                        //     if (200 === res.code) {
                        //         layer.close(dIndex);
                        //         layer.msg(res.msg, {icon: 1});
                        //         insTb.reload({page: {curr: 1}});
                        //     } else {
                        //         layer.msg(res.msg, {icon: 2});
                        //     }
                        // }, 'json');
                        if(data.field.id===undefined || data.field.id===null || data.field.id===""){
                            CoreUtil.sendPost("/${classInfo.className?uncap_first}/add",data.field,function (res) {
                                layer.close(loadIndex);
                                if (0 === res.code) {
                                    layer.close(dIndex);
                                    layer.msg(res.msg, {icon: 1});
                                    insTb.reload({page: {curr: 1}});
                                } else {
                                    layer.msg(res.msg, {icon: 2});
                                }
                            });
                        }else {
                            CoreUtil.sendPut("/${classInfo.className?uncap_first}/update",data.field,function (res) {
                                layer.close(loadIndex);
                                if (0 === res.code) {
                                    layer.close(dIndex);
                                    layer.msg(res.msg, {icon: 1});
                                    insTb.reload({page: {curr: 1}});
                                } else {
                                    layer.msg(res.msg, {icon: 2});
                                }
                            });
                        }
                        return false;
                    });
                }
            });
        }

        /* 删除 */
        function doDel(obj) {
            layer.confirm('确定要删除选中数据吗？', {
                skin: 'layui-layer-admin',
                shade: .1
            }, function (i) {
                layer.close(i);
                CoreUtil.sendDelete("/${classInfo.className?uncap_first}/delete",obj.ids,function (res) {
                    layer.msg(res.msg, {time:1000},function () {
                        layer.msg(res.msg, {icon: 1});
                        insTb.reload({page: {curr: 1}});
                    });
                });
            });
        }


    });
</script>
</body>
</html>