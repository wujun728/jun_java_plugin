<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>OnLine JAVA IDE</title>
</head>
<script type="text/javascript" src="/webjars/jquery/1.11.3/jquery.js"></script>
<script type="text/javascript" src="/webjars/bootstrap/3.3.7/js/bootstrap.min.js"></script>
<script type="text/javascript" src="/webjars/github-com-sentsin-layer/3.0.3/src/layer.js"></script>
<link rel="stylesheet" type="text/css" href="/webjars/bootstrap/3.3.7/css/bootstrap.css">
<body>
<ol class="breadcrumb">
    <li><a href="#">Home</a></li>
    <li><a href="#">JAVA</a></li>
    <li class="active">IDE</li>
</ol>
<form role="form">
    <div class="form-group" style="padding: 20px">
        <label for="name">编码区</label>
        <textarea id="inputArea" class="form-control" rows="15">public class Main
            {
                public static void main(String[] args){
                    System.out.println("hello world!");
            }
        }
        </textarea>
    </div>
    <div style="padding: 20px">
        <label class="form-inline">
            <input type="checkbox" id="timeLimitCheckBox" value="timeLimit" onclick="timeLimitClick()"> 限时
            <input type="text" id="timeLimitInput" style="display: none" class="form-control"
                   placeholder="1000(单位：毫秒,默认1S)">
        </label>
    </div>
    <div style="padding: 20px">
        <label class="form-inline">
            <input type="checkbox" id="argsCheckBox" onclick="argsCheckBoxClick()"> 输入参数
            <input type="text" id="argsInput" style="display: none" class="form-control"
                   placeholder="在此输入args参数,多个以空格分隔">
        </label>
    </div>

    <button onclick="doSubmit()" type="button" style="width: 200px" class="btn btn-success center-block"
            aria-hidden="true">提交
    </button>
    <br><br><br>

    <div style="padding: 20px">
        <div class="panel panel-info">
            <div class="panel-heading">
                <h3 class="panel-title">运行信息</h3>
            </div>
            <div class="panel-body" id="complieInfoDiv">
            </div>
        </div>
    </div>

    <div style="padding: 20px">
        <div class="panel panel-primary">
            <div class="panel-heading">
                <h3 class="panel-title">运行结果</h3>
            </div>
            <div class="panel-body" id="resultDiv">

            </div>
        </div>
    </div>
</form>
</body>
<script>
    function doSubmit() {
        var url = "/complie";
        var excuteTimeLimit = $("#timeLimitInput").val().trim();
        var excuteArgs = $("#argsInput").val().trim();
        if ($("#timeLimitCheckBox").prop("checked")) {
            if (excuteTimeLimit == "") {
                excuteTimeLimit = 1000;
            }
        } else {
            excuteTimeLimit = null;
        }
        if ($("#argsCheckBox").prop("checked")) {
            if (excuteArgs == "") {
                excuteArgs = null;
            }
        } else {
            excuteArgs = null;
        }
        var data = {"javaSource": $("#inputArea").val(), "excuteTimeLimit": excuteTimeLimit,"excuteArgs": excuteArgs};
        $.post(url, data, function (result) {
            layer.msg("结果:" + result.message);
            //设置执行信息
            $("#complieInfoDiv").html("运行耗时(毫秒)：" + result.excuteDurationTime + "<br>编译状态：" + result.message);
            //设置执行结果
            $("#resultDiv").html(result.excuteResult);
        });
    }

    function timeLimitClick() {
        if ($("#timeLimitCheckBox").prop("checked")) {
            $("#timeLimitInput").show();
        } else {
            $("#timeLimitInput").hide();
        }
    }

    function argsCheckBoxClick() {
        if ($("#argsCheckBox").prop("checked")) {
            $("#argsInput").show();
        } else {
            $("#argsInput").hide();
        }
    }
</script>
</html>