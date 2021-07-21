<%@ page contentType="text/html;charset=UTF-8" language="java" %>


<!DOCTYPE html>
<html>
<head>
    <title>图片上传</title>
    <!--支持IE9+ chrome fireFox-->
    <script src="/assets/jquery-2.1.4.min.js"></script>
    <link href="/assets/webuploader.css" rel="stylesheet"/>
    <script src="/assets/webuploader.nolog.min.js"></script>
    <link href="/assets/bootstrap.min.css" rel="stylesheet"/>
    <script src="/assets/bootstrap.min.js"></script>
    <script type="text/javascript">
        $(function () {
            $list = $('#fileList');
            var thumbnailHeight = 150;
            var thumbnailWidth = 150;
            //WebUploader实例
            var uploader = WebUploader.create({


                //设置选完文件后是否自动上传
                auto: false,


                //swf文件路径
                //swf: BASE_URL + '~/FileUpload/Uploader.swf',
                swf: '../FileUpload/Uploader.swf',

                // 文件接收服务端。
                server: '/ImageUpload/ImageUp',

                // 选择文件的按钮。可选。
                // 内部根据当前运行是创建，可能是input元素，也可能是flash.
                pick: '#picker',


                // 不压缩image, 默认如果是jpeg，文件上传前会压缩一把再上传！
                //resize: false

                //选择图片文件
                accept: {
                    title: 'Images',
                    extensions: 'gif,jpg,jpeg,bmp,png',
                    mimeTypes: 'image/*'
                }
            });

            // 当有文件被添加进队列的时候
            uploader.on('fileQueued', function (file) {
                //$list.append('<div id="' + file.id + '" class="item">' +
                //    '<h4 class="info">' + file.name + '<button type="button" fileId="' + file.id + '" class="btn btn-danger btn-delete"><span class="glyphicon glyphicon-trash"></span></button></h4>' +
                //    '<p class="state">等待上传...</p>' +
                //    '</div>');
                var $li = $('<div id="' + file.id + '" class="item">' +
                                '<h4 class="info">' + file.name + '<button type="button" fileId="' + file.id + '" class="btn btn-danger btn-delete"><span class="glyphicon glyphicon-trash"></span></button></h4>' +
                                '<p class="state">等待上传...</p>' +
                                '<img>' + '</div>'),
                        $img = $li.find('img');
                // $list为容器jQuery实例
                $list.append($li);
                //创建缩略图
                uploader.makeThumb(file, function (error, src) {
                    if (error) {
                        $img.replaceWith('<span>不能预览</span>');
                        return;
                    }

                    $img.attr('src', src);
                }, thumbnailWidth, thumbnailHeight);
                //删除要上传的文件
                //每次添加文件都给btn-delete绑定删除方法
                $(".btn-delete").click(function () {
                    //console.log($(this).attr("fileId"));//拿到文件id
                    uploader.removeFile(uploader.getFile($(this).attr("fileId"), true));
                    $(this).parent().parent().fadeOut();//视觉上消失了
                    $(this).parent().parent().remove();//DOM上删除了
                });
            });


            // 文件上传过程中创建进度条实时显示。
            uploader.on('uploadProgress', function (file, percentage) {
                var $li = $('#' + file.id),
                        $percent = $li.find('.progress .progress-bar');

                // 避免重复创建
                if (!$percent.length) {
                    $percent = $('<div class="progress progress-striped active">' +
                            '<div class="progress-bar" role="progressbar" style="width: 0%">' +
                            '</div>' +
                            '</div>').appendTo($li).find('.progress-bar');
                }

                $li.find('p.state').text('上传中');

                $percent.css('width', percentage * 100 + '%');
            });


            uploader.on('uploadSuccess', function (file) {
                $('#' + file.id).find('p.state').text('已上传');
                $('#' + file.id).find(".progress").find(".progress-bar").attr("class", "progress-bar progress-bar-success");
                $('#' + file.id).find(".info").find('.btn').fadeOut('slow');//上传完后删除"删除"按钮
            });

            uploader.on('uploadError', function (file) {
                $('#' + file.id).find('p.state').text('上传出错');
                //上传出错后进度条爆红
                $('#' + file.id).find(".progress").find(".progress-bar").attr("class", "progress-bar progress-bar-danger");
                //添加重试按钮
                //为了防止重复添加重试按钮，做一个判断
                //var retrybutton = $('#' + file.id).find(".btn-retry");
                //$('#' + file.id)
                if ($('#' + file.id).find(".btn-retry").length < 1) {
                    var btn = $('<button type="button" fileid="' + file.id + '" class="btn btn-success btn-retry"><span class="glyphicon glyphicon-refresh"></span></button>');
                    $('#' + file.id).find(".info").append(btn);//.find(".btn-danger")
                }


                $(".btn-retry").click(function () {
                    //console.log($(this).attr("fileId"));//拿到文件id
                    uploader.retry(uploader.getFile($(this).attr("fileId")));

                });

            });

            uploader.on('uploadComplete', function (file) {//上传完成后回调
                //$('#' + file.id).find('.progress').fadeOut();//上传完删除进度条
                //$('#' + file.id + 'btn').fadeOut('slow')//上传完后删除"删除"按钮
            });

            uploader.on('uploadFinished', function () {
                //上传完后的回调方法
                //alert("所有文件上传完毕");
                //提交表单
            });

            $("#UploadBtn").click(function () {
                uploader.upload();//上传
            });

            uploader.on('uploadAccept', function (file, response) {
                //if (uploader.errorCode) {
                //    // 通过return false来告诉组件，此文件上传有错。
                //    return false;
                //}
                if (response._raw == '{"error":true}') {
                    return false;
                }
            });
            //重传指定文件
            //function retry(file) {
            //    uploader.retry(file);
            //}


        });


    </script>

</head>
<body>
<div class="container" style="margin-top: 50px">
    <div id="uploader" class="container">
        <div class="container">
            <div id="fileList" class="uploader-list"></div> <!--存放文件的容器-->
        </div>
        <div class="btns container">
            <div id="picker" class="webuploader-container" style="float: left; margin-right: 10px">
                <div>
                    选择图片
                    <input type="file" name="file" class="webuploader-element-invisible" multiple="multiple">
                </div>
            </div>

            <div id="UploadBtn" class="webuploader-pick" style="float: left">开始上传</div>
        </div>
    </div>
</div>


<%--<form name="input" action="/ImageUpload/ImageUp" method="get">--%>
        <%--Username:--%>
        <%--<input type="text" name="user" />--%>
        <%--<input type="submit" value="Submit" />--%>
<%--</form>--%>


</body>
</html>
