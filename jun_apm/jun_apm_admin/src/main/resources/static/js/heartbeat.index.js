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
        var redirct_url = base_url + "/heartbeat?querytime={querytime}&appname={appname}&address={address}";
        redirct_url = redirct_url.replace('{querytime}', querytime);
        redirct_url = redirct_url.replace('{appname}', appname);
        redirct_url = redirct_url.replace('{address}', address);

        window.location.href = redirct_url;
    });

    // valid heartbeat data
    if (!heartbeatList) {
        appendTips("暂无指标数据");
        return;
    }

    // heartbeat chart tool
    var baaSample = $('#bar-sample').html();
    var barNo = 1;
    function makeBar(name, xData, yDataItem, yDataUnit){

        var barItemId = 'bar-item-'+(barNo++);
        var fullgcBar = baaSample.replace('{id}', barItemId)

        $('#bar-parent').append(fullgcBar);

        // x data, fill
        var xData2 = [];
        var yData2 = [];
        if (xData.length < 30) {
            for (var min = 0; min < 60; min++) {
                xData2[min] = min;
                yData2[min] = xData.indexOf(min)>-1?yDataItem[min]:0;
            }
        } else {
            for (var index in xData) {
                var min = xData[index];

                xData2.push(min);
                yData2.push( xData.indexOf(min)>-1?yDataItem[min]:0 );
            }
        }

        var barOption = {
            title: {
                text: name
            },
            toolbox: {
                show : true,
                feature : {
                    dataView : {show: true, readOnly: false},
                    magicType : {show: true, type: ['line', 'bar']},
                    restore : {show: true},
                    saveAsImage : {show: true}
                }
            },
            tooltip : {
                trigger: 'axis'
            },
            xAxis: {
                name: 'Min',
                type: 'category',
                data: xData2
            },
            yAxis: {
                name: yDataUnit,
                type: 'value'
            },
            series: [{
                data: yData2,
                type: 'bar'
            }],
            color : ['#c23531','#2f4554', '#61a0a8', '#d48265', '#91c7ae','#749f83',  '#ca8622', '#bda29a','#6e7074', '#546570', '#c4ccd3']
        };

        var barChart = echarts.init(document.getElementById(barItemId));
        barChart.setOption(barOption);
    }

    // tips
    function appendTips(title) {
        var tipsTmp = '<section class="content-header col-md-12"><h1>{title}<small></small></h1></section>'.replace('{title}', title)
        $('#bar-parent').append(tipsTmp);
    }

    // 四舍五入，两位小数
    function toDecimal(x) {
        var f = parseFloat(x);
        f = Math.round(x*100)/100;
        return f;
    }

    // heartbeat chat
    var xData = [];     // [1,3,9]
    var yData = {};     // {1:y1, 3:y3, 9:y9}

    var kb_mb = 1024;
    function setYData(level_1_obj, level_2_arr, min, minData){
        if (!yData[level_1_obj]){
            yData[level_1_obj] = {};
        }
        if (!yData[level_1_obj+''][level_2_arr+'']) {
            yData[level_1_obj+''][level_2_arr+''] = {};
        }

        yData[level_1_obj+''][level_2_arr+''][min] = minData;
    }

    for (var index in heartbeatList) {
        var heartbeat = heartbeatList[index];

        // x-data, ms -> min
        var min = (heartbeat.addtime/(1000*60))%60;
        if (xData.indexOf(min) == -1) {
            xData.push(min);
        }

        // gc, km -> mb
        setYData('young_gc', 'count', min, heartbeat.young_gc.count );
        setYData('young_gc', 'time', min, heartbeat.young_gc.time );
        setYData('full_gc', 'count', min, heartbeat.full_gc.count );
        setYData('full_gc', 'time', min, heartbeat.full_gc.time );
        setYData('unknown_gc', 'count', min, heartbeat.unknown_gc.count );
        setYData('unknown_gc', 'time', min, heartbeat.unknown_gc.time );

        // memory, km -> mb
        setYData('heap_all', 'used_space', min, toDecimal( heartbeat.heap_all.used_space/kb_mb ) );
        setYData('heap_all', 'used_percent', min, toDecimal( heartbeat.heap_all.used_space*100/heartbeat.heap_all.max_space ) );
        setYData('heap_eden_space', 'used_space', min, toDecimal( heartbeat.heap_eden_space.used_space/kb_mb ) );
        setYData('heap_eden_space', 'used_percent', min, toDecimal( heartbeat.heap_eden_space.used_space*100/heartbeat.heap_eden_space.max_space ) );
        setYData('heap_survivor_space', 'used_space', min, toDecimal( heartbeat.heap_survivor_space.used_space/kb_mb ) );
        setYData('heap_survivor_space', 'used_percent', min, toDecimal( heartbeat.heap_survivor_space.used_space*100/heartbeat.heap_survivor_space.max_space ) );
        setYData('heap_old_gen', 'used_space', min, toDecimal( heartbeat.heap_old_gen.used_space/kb_mb ) );
        setYData('heap_old_gen', 'used_percent', min, toDecimal( heartbeat.heap_old_gen.used_space*100/heartbeat.heap_old_gen.max_space ) );

        setYData('non_heap_all', 'used_space', min, toDecimal( heartbeat.non_heap_all.used_space/kb_mb ) );
        setYData('non_heap_all', 'used_percent', min, toDecimal( heartbeat.non_heap_all.used_space*100/heartbeat.non_heap_all.max_space ) );
        setYData('non_heap_code_cache', 'used_space', min, toDecimal( heartbeat.non_heap_code_cache.used_space/kb_mb ) );
        setYData('non_heap_code_cache', 'used_percent', min, toDecimal( heartbeat.non_heap_code_cache.used_space*100/heartbeat.non_heap_code_cache.max_space ) );
        setYData('non_heap_perm_gen', 'used_space', min, toDecimal( heartbeat.non_heap_perm_gen.used_space/kb_mb ) );
        setYData('non_heap_perm_gen', 'used_percent', min, toDecimal( heartbeat.non_heap_perm_gen.used_space*100/heartbeat.non_heap_perm_gen.max_space ) );
        setYData('non_heap_metaspace', 'used_space', min, toDecimal( heartbeat.non_heap_metaspace.used_space/kb_mb ) );
        setYData('non_heap_metaspace', 'used_percent', min, toDecimal( heartbeat.non_heap_metaspace.used_space*100/heartbeat.non_heap_metaspace.max_space ) );

        // thread
        var t_count_ALL = 0;
        var t_count_NEW = 0;
        var t_count_RUNNABLE = 0;
        var t_count_BLOCKED = 0;
        var t_count_WAITING = 0;
        var t_count_TIMED_WAITING = 0;
        var t_count_TERMINATED = 0;
        for (var t_index in heartbeat.thread_list) {
            t_count_ALL++;
            switch (heartbeat.thread_list[t_index].status) {
                case 'NEW':
                    t_count_NEW++;
                    break;
                case 'RUNNABLE':
                    t_count_RUNNABLE++;
                    break;
                case 'BLOCKED':
                    t_count_BLOCKED++;
                    break;
                case 'WAITING':
                    t_count_WAITING++;
                    break;
                case 'TIMED_WAITING':
                    t_count_TIMED_WAITING++;
                    break;
                case 'TERMINATED':
                    t_count_TERMINATED++;
                    break;
            }
        }

        setYData('thread_all', 'count', min, t_count_ALL );
        setYData('thread_new', 'count', min, t_count_NEW );
        setYData('thread_runnable', 'count', min, t_count_RUNNABLE );
        setYData('thread_blocked', 'count', min, t_count_BLOCKED );
        setYData('thread_waiting', 'count', min, t_count_WAITING );
        setYData('thread_timed_waiting', 'count', min, t_count_TIMED_WAITING );
        setYData('thread_terminated', 'count', min, t_count_TERMINATED );

        // class
        setYData('class_info', 'loaded_count', min, heartbeat.class_info.loaded_count );
        setYData('class_info', 'unload_count', min, heartbeat.class_info.unload_count );

        // system
        setYData('system_info', 'committed_virtual_memory', min, toDecimal( heartbeat.system_info.committed_virtual_memory/kb_mb ) );
        setYData('system_info', 'total_swap_space', min, toDecimal( heartbeat.system_info.total_swap_space/kb_mb ) );
        setYData('system_info', 'free_swap_space', min, toDecimal( heartbeat.system_info.free_swap_space/kb_mb ) );
        setYData('system_info', 'total_physical_memory', min, toDecimal( heartbeat.system_info.total_physical_memory/kb_mb ) );
        setYData('system_info', 'free_physical_memory', min, toDecimal( heartbeat.system_info.free_physical_memory/kb_mb ) );

        setYData('system_info', 'process_cpu_time', min, heartbeat.system_info.process_cpu_time );
        setYData('system_info', 'system_cpu_load', min, heartbeat.system_info.system_cpu_load );
        setYData('system_info', 'process_cpu_load', min, heartbeat.system_info.process_cpu_load );

        setYData('system_info', 'cpu_count', min, heartbeat.system_info.cpu_count );
        setYData('system_info', 'cpu_load_average_1min', min, heartbeat.system_info.cpu_load_average_1min );
        setYData('system_info', 'cpu_system_load_percent', min, heartbeat.system_info.cpu_system_load_percent );
        setYData('system_info', 'cpu_jvm_load_percent', min, heartbeat.system_info.cpu_jvm_load_percent );

    }

    xData.sort(function(a,b){
        return a - b;
    });

    // gc bar
    appendTips("Gc Info");
    makeBar('young_gc | count', xData, yData.young_gc.count, "count");
    makeBar('young_gc | time', xData, yData.young_gc.time, "ms");
    makeBar('full_gc | count', xData, yData.full_gc.count, "count");
    makeBar('full_gc | time', xData, yData.full_gc.time, "ms");
    makeBar('unknown_gc | count', xData, yData.unknown_gc.count, "count");
    makeBar('unknown_gc | time', xData, yData.unknown_gc.time, "ms");

    // memory bar
    appendTips("Memory Info");
    makeBar('heap_all | used_space', xData, yData.heap_all.used_space, "MB");
    makeBar('heap_all | used_percent', xData, yData.heap_all.used_percent, "%");
    makeBar('heap_eden_space | used_space', xData, yData.heap_eden_space.used_space, "MB");
    makeBar('heap_eden_space | used_percent', xData, yData.heap_eden_space.used_percent, "%");
    makeBar('heap_survivor_space | used_space', xData, yData.heap_survivor_space.used_space, "MB");
    makeBar('heap_survivor_space | used_percent', xData, yData.heap_survivor_space.used_percent, "%");
    makeBar('heap_old_gen | used_space', xData, yData.heap_old_gen.used_space, "MB");
    makeBar('heap_old_gen | used_percent', xData, yData.heap_old_gen.used_percent, "%");

    makeBar('non_heap_all | used_space', xData, yData.non_heap_all.used_space, "MB");
    makeBar('non_heap_all | used_percent', xData, yData.non_heap_all.used_percent, "%");
    makeBar('non_heap_code_cache | used_space', xData, yData.non_heap_code_cache.used_space, "MB");
    makeBar('non_heap_code_cache | used_percent', xData, yData.non_heap_code_cache.used_percent, "%");
    makeBar('non_heap_perm_gen | used_space', xData, yData.non_heap_perm_gen.used_space, "MB");
    makeBar('non_heap_perm_gen | used_percent', xData, yData.non_heap_perm_gen.used_percent, "%");
    makeBar('non_heap_metaspace | used_space', xData, yData.non_heap_metaspace.used_space, "MB");
    makeBar('non_heap_metaspace | used_percent', xData, yData.non_heap_metaspace.used_percent, "%");

    // thread bar
    appendTips("Thread Info");
    makeBar('thread_all | count', xData, yData.thread_all.count, "count");
    makeBar('thread_new | count', xData, yData.thread_new.count, "count");
    makeBar('thread_runnable | count', xData, yData.thread_runnable.count, "count");
    makeBar('thread_blocked | count', xData, yData.thread_blocked.count, "count");
    makeBar('thread_waiting | count', xData, yData.thread_waiting.count, "count");
    makeBar('thread_timed_waiting | count', xData, yData.thread_timed_waiting.count, "count");
    makeBar('thread_terminated | count', xData, yData.thread_terminated.count, "count");

    // class bar
    appendTips("Class Info");
    makeBar('class_loaded | count', xData, yData.class_info.loaded_count, "count");
    makeBar('class_unload | count', xData, yData.class_info.unload_count, "count");

    // system bar
    appendTips("System Info | Space");
    makeBar('total_swap_space', xData, yData.system_info.total_swap_space, "MB");
    makeBar('free_swap_space', xData, yData.system_info.free_swap_space, "MB");
    makeBar('total_physical_memory', xData, yData.system_info.total_physical_memory, "MB");
    makeBar('free_physical_memory', xData, yData.system_info.free_physical_memory, "MB");
    makeBar('committed_virtual_memory', xData, yData.system_info.committed_virtual_memory, "MB");

    // system bar
    appendTips("System Info | CPU/LOAD");
    makeBar('system_cpu_load', xData, yData.system_info.system_cpu_load, "load");
    makeBar('process_cpu_load', xData, yData.system_info.process_cpu_load, "load");

    makeBar('process_cpu_time', xData, yData.system_info.process_cpu_time, "ms");
    makeBar('cpu_count', xData, yData.system_info.cpu_count, "count");

    makeBar('cpu_system_load_percent', xData, yData.system_info.cpu_system_load_percent, "%");
    makeBar('cpu_jvm_load_percent', xData, yData.system_info.cpu_jvm_load_percent, "%");
    makeBar('cpu_load_average_1min', xData, yData.system_info.cpu_load_average_1min, "load");

});
