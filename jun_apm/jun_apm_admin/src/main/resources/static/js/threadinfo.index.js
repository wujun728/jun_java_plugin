$(function() {

    // base
    $('.select2').select2({
        language:'zh-CN'
    });

    // querytime
    $.datetimepicker.setLocale('ch');
    $('#querytime').datetimepicker({
        format: 'Y-m-d H:i',
        step: 60,
        maxDate: 0  // 0 means today
    });

    // appname
    $( "#appname" ).autocomplete({
        source: function( request, response ) {
            $.ajax({
                url: base_url + "/heartbeat/findAppNameList",
                dataType: "json",
                data: {
                    "appname": request.term
                },
                success: function( data ) {
                    if(data.code == 200){
                        response(data.data);
                    }
                }
            });
        }
    });

    // min
    $('.min').click(function () {
        min = $(this).data('min');
        $('#searchBtn').click();
    });

    // search
    $('#searchBtn').click(function () {

        // querytime
        var querytime_input = $('#querytime').val();
        if (!querytime_input) {
            layer.open({
                title: '系统提示' ,
                btn: [ '确定' ],
                content: '请选择查询时间',
                icon: '2'
            });
            return;
        }

        var time = new Date(querytime_input)
        var y = time.getFullYear();
        var m = time.getMonth() + 1;
        if (m < 10) {
            m = "0" + m
        }
        var d = time.getDate();
        if (d < 10) {
            d = "0" + d
        }
        var h = time.getHours();
        if (h < 10) {
            h = "0" + h
        }
        var querytime = y + "" + m + "" + d + "" + h;
        var appname = $('#appname').val()?$('#appname').val().trim():'';
        var address = $('#address').val()?$('#address').val().trim():'';

        if (!appname) {
            layer.open({
                title: '系统提示' ,
                btn: [ '确定' ],
                content: '请输入应用 AppName',
                icon: '2'
            });
            return;
        }

        // redirct
        var redirct_url = base_url + "/heartbeat/threadinfo?querytime={querytime}&appname={appname}&address={address}&min={min}";
        redirct_url = redirct_url.replace('{querytime}', querytime);
        redirct_url = redirct_url.replace('{appname}', appname);
        redirct_url = redirct_url.replace('{address}', address);
        redirct_url = redirct_url.replace('{min}', min);

        window.location.href = redirct_url;
    });

    // ThreadName change
    $("#ThreadName").bind('input porpertychange',function(){
        threadFilter();
    });
    // ThreadStatus change
    $('#ThreadStatus').on("change", function () {
        threadFilter();
    });
    function threadFilter() {
        var ThreadName = $('#ThreadName').val();
        var ThreadStatus = $('#ThreadStatus').val();
        var showCount = 0;
        $('.threadInfo_item_stack_info').hide();

        $('.threadInfo_item').each(function(){
            var name = $(this).data('name');
            var status = $(this).data('status');

            var isShow = true;
            if (ThreadName && name.indexOf(ThreadName) == -1) {
                isShow = false;
            }
            if (ThreadStatus.length>0 && ThreadStatus.indexOf(status) == -1) {
                isShow = false;
            }

            if (isShow) {
                $(this).show();
                showCount++;
            } else {
                $(this).hide();
            }

        });

        $('#threadInfo-table-tips').html('当前线程数量：' + showCount);
    }

    
});
