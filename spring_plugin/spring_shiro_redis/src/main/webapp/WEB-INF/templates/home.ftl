<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>
        Spring+Shiro+Redis集成
    </title>
    <script>
      var cpath = '${cpath}';
    </script>
    <!-- 新 Bootstrap 核心 CSS 文件 -->
    <link rel="stylesheet" href="//cdn.bootcss.com/bootstrap/3.3.5/css/bootstrap.min.css">

    <!-- 可选的Bootstrap主题文件（一般不用引入） -->
    <link rel="stylesheet" href="//cdn.bootcss.com/bootstrap/3.3.5/css/bootstrap-theme.min.css">
    <link rel="stylesheet" href="${cpath}/lib/bootstrap_table/bootstrap-table.css">
    <!-- jQuery文件。务必在bootstrap.min.js 之前引入 -->
    <script src="//cdn.bootcss.com/jquery/1.11.3/jquery.min.js"></script>

    <!-- 最新的 Bootstrap 核心 JavaScript 文件 -->
    <script src="//cdn.bootcss.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
    <script src="${cpath}/lib/bootstrap_table/bootstrap-table.js"></script>
    <script src="${cpath}/lib/bootstrap_table/locale/bootstrap-table-zh-CN.js"></script>
    <script src="${cpath}/lib/login.js" type="text/javascript"></script>
    <script>
        $(function(){
            var $table = $('#zhmtable'), $remove = $('#batch-delete');
            $('#zhmtable').bootstrapTable();
            $('#btn-logout').click(function(){
                LOGIN.logout();
            });
            $table.on('check.bs.table uncheck.bs.table check-all.bs.table uncheck-all.bs.table', function () {
                $remove.prop('disabled', !$table.bootstrapTable('getSelections').length);
            });
            $remove.click(function () {
                var ids = $.map($table.bootstrapTable('getSelections'), function (row) {
                    return row.id
                });
                removeUserInfo(ids);
                $remove.prop('disabled', true);
            });
        });
        function nameFormatter(value, row) {
            var icon = row.id % 2 === 0 ? 'glyphicon-star' : 'glyphicon-star-empty'
            return '<i class="glyphicon ' + icon + '"></i> ' + value;
        }
        function actionFormatter(value, row, index) {
            return [
                '<button class="btn btn-primary removeUser">删除</button>'
            ].join('');
        }

        window.actionEvents = {
            'click .removeUser': function (e, value, row, index) {
                removeUserInfo([row.id]);
            }
        };
        function removeUserInfo(uids){
            var allSucessflag = true;
            var ajaxMsg = "";
            $.each(uids, function(i,userid){
                $.ajax({
                    type:'POST',
                    url:'${cpath}/user/edit/del.html',
                    async:false,
                    data:{'id':userid},
                    error:function(msg){},
                    success:function(msg){
                        if(msg!='success'){
                            allSucessflag=false;
                            ajaxMsg = msg;
                        }
                    }
                });
                if(!allSucessflag){
                    return false;
                }
            });
            if(!allSucessflag){
                if("nologin"==ajaxMsg){
                    location.href='${cpath}/login.html';
                    return false;
                }
                else{
                    alert(ajaxMsg);
                    return false;
                }
            }
            location.reload();
        }
    </script>
</head>
<body role="document">
<div class="container">
    <div class="header clearfix">
        <nav>
            <ul class="nav nav-pills pull-right">
                <li role="presentation" class="active"><a href="${cpath}/home/main.html">主页</a></li>
                <li role="presentation"><a id="btn-logout" href="javascript:void(0)">退出登录</a></li>
            </ul>
        </nav>
        <h3 class="text-muted"><#if currUser?exists>${currUser.username}<#else>${username?default("")}</#if>,欢迎您登录。</h3>
    </div>
    <div id="toolbar" class="fixed-table-toolbar" style="margin-bottom: -40px">
        <button id="batch-delete" class="btn btn-danger remove" disabled>
            批量删除
        </button>
    </div>
    <table id="zhmtable" data-classes="table table-hover  table-bordered table-condensed"
           data-url="${cpath}/user/listAllUserJson.html"
           data-page-size="2" data-height="500"
           data-side-pagination="server" data-pagination="true"
           data-page-list="[2,5, 10, 20, 50, 100, 200]" data-search="true">
        <thead>
        <tr data-class="active">
            <th data-field="state" data-checkbox="true"></th>
            <th data-field="id" data-align="left" data-sortable="false" data-width="10%">ID</th>
            <th data-field="username" data-align="left" data-sortable="false" data-width="30%" data-formatter="nameFormatter">用户名</th>
            <th data-field="mobile" data-align="left" data-sortable="false" data-width="30%">手机号</th>
            <th data-field="email" data-align="left" data-sortable="false" data-width="30%">邮箱</th>
            <th data-field="action" data-formatter="actionFormatter" data-events="actionEvents">操作</th>
        </tr>
        </thead>
    </table>
</div>
</body>
</html>