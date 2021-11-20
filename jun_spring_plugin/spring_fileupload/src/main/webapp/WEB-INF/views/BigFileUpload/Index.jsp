<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>

<html>
<head>
    <title>大文件上传</title>
    <!--支持IE9+ chrome fireFox-->
    <script src="/assets/jquery-2.1.4.min.js"></script>
    <link href="/assets/webuploader.css" rel="stylesheet" />
    <script src="/assets/webuploader.nolog.min.js"></script>
    <link href="/assets/bootstrap.min.css" rel="stylesheet" />
    <script src="/assets/bootstrap.min.js"></script>
    <script type="text/javascript">
        $(function () {
            $list = $('#fileList');
            var uploader = WebUploader.create({


                //设置选完文件后是否自动上传
                auto: false,


                //swf文件路径
                //swf: BASE_URL + '~/FileUpload/Uploader.swf',
                swf: '../FileUpload/Uploader.swf',

                // 文件接收服务端。
                server: '/BigFileUpload/BigFileUp',

                // 选择文件的按钮。可选。
                // 内部根据当前运行是创建，可能是input元素，也可能是flash.
                pick: '#picker',

                chunked: true, //开启分块上传
                chunkSize: 10 * 1024 * 1024,
                chunkRetry: 3,//网络问题上传失败后重试次数
                threads: 5, //上传并发数
                //formData: { guid: WebUploader.guid() },  //一个文件有一个guid，在服务器端合并成一个文件  这里有个问题，多个文件或者上传一个文件成功后不刷新直接添加文件上传生成的guid不变！！！   暂时只能传一个大文件（已解决）
                //fileNumLimit :1,
                fileSizeLimit: 2000 * 1024 * 1024,//最大2GB
                fileSingleSizeLimit: 2000 * 1024 * 1024,


                resize: false//不压缩

                //选择文件类型
                //accept: {
                //    title: 'Images',
                //    extensions: 'gif,jpg,jpeg,bmp,png',
                //    mimeTypes: 'image/*'
                //}
            });

            // 当有文件被添加进队列的时候
            uploader.on('fileQueued', function (file) {
                $list.append('<div id="' + file.id + '" class="item">' +
                        '<h4 class="info">' + file.name + '<button type="button" fileId="' + file.id + '" class="btn btn-danger btn-delete"><span class="glyphicon glyphicon-trash"></span></button></h4>' +
                        '<p class="state">正在计算文件MD5...请等待计算完毕后再点击上传！</p>' +
                        '</div>');
                //删除要上传的文件
                //每次添加文件都给btn-delete绑定删除方法
                $(".btn-delete").click(function () {
                    //console.log($(this).attr("fileId"));//拿到文件id
                    uploader.removeFile(uploader.getFile($(this).attr("fileId"), true));
                    $(this).parent().parent().fadeOut();//视觉上消失了
                    $(this).parent().parent().remove();//DOM上删除了
                });

                uploader.options.formData.guid = WebUploader.guid();//每个文件都附带一个guid，以在服务端确定哪些文件块本来是一个

                uploader.md5File(file)
                        .then(function (fileMd5) { // 完成
                            //var end = +new Date();
                            // console.log("before-send-file  preupload: file.size="+file.size+" file.md5="+fileMd5);
                            //insertLog("<br>" + moment().format("YYYY-MM-DD HH:mm:ss") + " before-send-file  preupload:计算文件(" + file.name + ")MD5完成. 耗时  " + (end - start) + '毫秒  fileMd5: ' + fileMd5);
                            file.wholeMd5 = fileMd5;//获取到了md5
                            uploader.options.formData.md5value = file.wholeMd5;//每个文件都附带一个md5，便于实现秒传

                            $('#' + file.id).find('p.state').text('MD5计算完毕，可以点击上传了');

                            $.ajax({//向服务端发送请求
                                cache: false,
                                type: "post",
                                //dataType: "json",
                                url: "/BigFileUpload/IsMD5Exist",//baseUrl +
                                data: {
                                    fileMd5: fileMd5,
                                    fileName: file.name,
                                    fileID: file.id,
                                    //isShared: $("#isShared").val()
                                },
                                success: function (result) {
                                    console.log(result);
                                    if (result == "this file is exist") {
                                        console.log("服务器上已经有同样的文件了，开始秒传！");

                                        uploader.removeFile(file, true);

                                        $('#' + file.id).find('p.state').text('已上传');
                                        $('#' + file.id).find(".progress").find(".progress-bar").attr("class", "progress-bar progress-bar-success");
                                        $('#' + file.id).find(".info").find('.btn').fadeOut('slow');//上传完后删除"删除"按钮
                                        $("#StopBtn").fadeOut('slow');
                                    } else {
                                        console.log("服务器上没有同样的文件，秒传失败！");
                                    }
                                }
                            });
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
                $('#StopBtn').fadeOut('slow');
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

            $("#StopBtn").click(function () {
                console.log($('#StopBtn').attr("status"));
                var status = $('#StopBtn').attr("status");
                if (status == "suspend") {
                    console.log("当前按钮是暂停，即将变为继续");
                    $("#StopBtn").html("继续上传");
                    $("#StopBtn").attr("status", "continuous");

                    console.log("__________________当前所有的文件_______________________");
                    console.log(uploader.getFiles());
                    console.log("__________________暂停上传_____________________________");
                    uploader.stop(true);
                    console.log("__________________所有当前暂停的文件___________________");
                    console.log(uploader.getFiles("interrupt"));
                } else {
                    console.log("当前按钮是继续，即将变为暂停");
                    $("#StopBtn").html("暂停上传");
                    $("#StopBtn").attr("status", "suspend");

                    console.log("__________________所有当前暂停的文件___________________");
                    console.log(uploader.getFiles("interrupt"));
                    uploader.upload(uploader.getFiles("interrupt"));
                }
            });

            uploader.on('uploadAccept', function (file, response) {
                if (response._raw === '{"error":true}') {
                    return false;
                }

            });
        });
    </script>
</head>
<body>
<div class="container" style="margin-top: 20px">
    <div class="alert alert-info">可以一次上传多个大文件</div>
</div>
<div class="container" style="margin-top: 50px">
    <div id="uploader" class="container">
        <div class="container">
            <div id="fileList" class="uploader-list"></div> <!--存放文件的容器-->
        </div>
        <div class="btns container">
            <div id="picker" class="webuploader-container" style="float: left; margin-right: 10px">
                <div>
                    选择文件
                    <input type="file" name="file" class="webuploader-element-invisible" multiple="multiple">
                </div>
            </div>

            <div id="UploadBtn" class="webuploader-pick" style="float: left; margin-right: 10px">开始上传</div>
            <div id="StopBtn" class="webuploader-pick" style="float: left; margin-right: 10px" status="suspend">暂停上传</div>
        </div>
    </div>
</div>

</body>
</html>
