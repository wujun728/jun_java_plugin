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
        var type = $('#type').val()?$('#type').val().trim():'';

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
        var redirct_url = base_url + "/transaction?querytime={querytime}&appname={appname}&address={address}&type={type}";
        redirct_url = redirct_url.replace('{querytime}', querytime);
        redirct_url = redirct_url.replace('{appname}', appname);
        redirct_url = redirct_url.replace('{address}', address);
        redirct_url = redirct_url.replace('{type}', type);

        window.location.href = redirct_url;
    });


    // valid data
    if (!reportList) {
        return;
    }

    // 四舍五入，4位小数
    function toDecimal(x, fixNum) {
        fixNum = fixNum?fixNum:4;
        var f = parseFloat(x);
        f = Math.round(x*10000)/10000;
        f = f.toFixed(fixNum);
        return f;
    }

    // parse data
    /**
     * event item, for each name
     *
     *  xData = [a, b, c]
     *  nameMapList:
     *  {
     *
     *      'name-x':{
     *          Name: 'xxx',
     *          Total: xxx,
     *          Failure: xxx,
     *          Failure_percent: xxx,
     *          QPS: xxx,
     *          Percent: xxx,
     *          Total_TimeLine: {
     *              index, xx       // {0:y3, 1:y6, 2:y9}
     *          },
     *          Failure_TimeLine: {
     *              index, xx
     *          },
     *          'Total_Distribution'{
     *              'address-x': xx
     *          },
     *          'Failure_Distribution'{
     *              'address-x': xx
     *          }
     *          ,   // for transaction
     *          time_max: xx,
     *          time_avg: xx,
     *          time_tp90: xx,
     *          time_tp95: xx,
     *          time_tp99: xx,
     *          time_tp999: xx,
     *      }
     *  }
     *
     */
    var nameMapList = {};
    var xData = [];     // [0]=3, [1]=6, [2]=9
    var nameMap_all_name = '_All_';

    function getNewNameMap(name) {
        var nameMap = {};
        nameMap.Name = name;
        nameMap.Total = 0;
        nameMap.Failure = 0;
        nameMap.Failure_percent = 0;
        nameMap.QPS = 0;
        nameMap.Percent = 0;

        nameMap.Total_TimeLine = {};
        nameMap.Failure_TimeLine = {};

        nameMap.Total_Distribution = {};
        nameMap.Failure_Distribution = {};

        // for transaction
        nameMap.time_max = 0;
        nameMap.time_avg = 0;
        nameMap.time_tp90 = 0;
        nameMap.time_tp95 = 0;
        nameMap.time_tp99 = 0;
        nameMap.time_tp999 = 0;

        return nameMap;
    }

    for (var index in reportList) {
        var eventReport = reportList[index];

        // x-data, ms -> min
        var min = (eventReport.addtime/(1000*60))%60;
        if (xData.indexOf(min) == -1) {
            xData.push(min);
        }

        // item
        var nameMap_item = nameMapList[eventReport.name+''];
        if (!nameMap_item) {
            nameMap_item = getNewNameMap(eventReport.name);
            nameMapList[eventReport.name+''] = nameMap_item;
        }
        nameMap_item.Total += eventReport.total_count;
        nameMap_item.Failure += eventReport.fail_count;

        nameMap_item.Total_TimeLine[min] = eventReport.total_count;
        nameMap_item.Failure_TimeLine[min] = eventReport.fail_count;

        nameMap_item.Total_Distribution[eventReport.address] = (nameMap_item.Total_Distribution[eventReport.address]?nameMap_item.Total_Distribution[eventReport.address]:0) + eventReport.total_count;
        nameMap_item.Failure_Distribution[eventReport.address] = (nameMap_item.Failure_Distribution[eventReport.address]?nameMap_item.Failure_Distribution[eventReport.address]:0) + eventReport.fail_count;

        // for transaction
        nameMap_item.time_max = eventReport.time_max>=nameMap_item.time_max?eventReport.time_max:nameMap_item.time_max;
        nameMap_item.time_avg += eventReport.time_avg * eventReport.total_count;        // weighted calculate "address+min>>name" start
        nameMap_item.time_tp90 += eventReport.time_tp90 * eventReport.total_count;
        nameMap_item.time_tp95 += eventReport.time_tp95 * eventReport.total_count;
        nameMap_item.time_tp99 += eventReport.time_tp99 * eventReport.total_count;
        nameMap_item.time_tp999 += eventReport.time_tp999 * eventReport.total_count;

        // all
        var nameMap_all = nameMapList[nameMap_all_name];
        if (!nameMap_all) {
            nameMap_all = getNewNameMap(nameMap_all_name);
            nameMapList[nameMap_all_name] = nameMap_all;
        }
        nameMap_all.Total += eventReport.total_count;
        nameMap_all.Failure += eventReport.fail_count;

        nameMap_all.Total_TimeLine[min] = (nameMap_all.Total_TimeLine[min]?nameMap_all.Total_TimeLine[min]:0) + eventReport.total_count;
        nameMap_all.Failure_TimeLine[min] = (nameMap_all.Failure_TimeLine[min]?nameMap_all.Failure_TimeLine[min]:0) + eventReport.fail_count;

        nameMap_all.Total_Distribution[eventReport.address] = (nameMap_all.Total_Distribution[eventReport.address]?nameMap_all.Total_Distribution[eventReport.address]:0) + eventReport.total_count;
        nameMap_all.Failure_Distribution[eventReport.address] = (nameMap_all.Failure_Distribution[eventReport.address]?nameMap_all.Failure_Distribution[eventReport.address]:0) + eventReport.fail_count;

    }

    xData.sort(function(a,b){
        return a - b;
    });

    for(var key in nameMapList) {
        var nameMap = nameMapList[key];
        nameMap.Failure_percent = nameMap.Failure/nameMap.Total;
        nameMap.QPS = nameMap.Total/periodSecond;
        nameMap.Percent = nameMap.Total/nameMapList[nameMap_all_name].Total;

        // for transaction
        if (nameMap.Name != nameMap_all_name) {
            // item
            nameMap.time_avg = toDecimal( nameMap.time_avg/nameMap.Total , 2 );     // weighted calculate "address+min>>name" end
            nameMap.time_tp90 = toDecimal( nameMap.time_tp90/nameMap.Total , 2 );
            nameMap.time_tp95 = toDecimal( nameMap.time_tp95/nameMap.Total , 2 );
            nameMap.time_tp99 = toDecimal( nameMap.time_tp99/nameMap.Total , 2 );
            nameMap.time_tp999 = toDecimal( nameMap.time_tp999/nameMap.Total , 2 );

            // all
            nameMapList[nameMap_all_name].time_max = nameMapList[nameMap_all_name].time_max>=nameMap.time_max?nameMapList[nameMap_all_name].time_max:nameMap.time_max;
            nameMapList[nameMap_all_name].time_avg += nameMap.time_avg * nameMap.Total;     // weighted calculate for "name>>all" start
            nameMapList[nameMap_all_name].time_tp90 += nameMap.time_tp90 * nameMap.Total;
            nameMapList[nameMap_all_name].time_tp95 += nameMap.time_tp95 * nameMap.Total;
            nameMapList[nameMap_all_name].time_tp99 += nameMap.time_tp99 * nameMap.Total;
            nameMapList[nameMap_all_name].time_tp999 += nameMap.time_tp999 * nameMap.Total;
        }

    }

    // for transaction
    nameMapList[nameMap_all_name].time_avg = toDecimal( nameMapList[nameMap_all_name].time_avg/nameMapList[nameMap_all_name].Total , 2 );       // weighted calculate for "name>>all" end
    nameMapList[nameMap_all_name].time_tp90 = toDecimal( nameMapList[nameMap_all_name].time_tp90/nameMapList[nameMap_all_name].Total , 2 );
    nameMapList[nameMap_all_name].time_tp95 = toDecimal( nameMapList[nameMap_all_name].time_tp95/nameMapList[nameMap_all_name].Total , 2 );
    nameMapList[nameMap_all_name].time_tp99 = toDecimal( nameMapList[nameMap_all_name].time_tp99/nameMapList[nameMap_all_name].Total , 2 );
    nameMapList[nameMap_all_name].time_tp999 = toDecimal( nameMapList[nameMap_all_name].time_tp999/nameMapList[nameMap_all_name].Total , 2 );


    // event table
    var TableData = [];
    function addTableData(nameMap){
        var nameMapArr = [];
        nameMapArr[0] = (nameMap.Name==nameMap_all_name)?'<span class="badge bg-gray">All</span>':nameMap.Name;
        nameMapArr[1] = nameMap.Total;
        nameMapArr[2] = '<span style="color: '+ (nameMap.Failure>0?'red':'black') +';">'+ nameMap.Failure +'</span>';
        nameMapArr[3] = '<span style="color: '+ (nameMap.Failure_percent>0?'red':'black') +';">'+ toDecimal( nameMap.Failure_percent*100 ) +'%</span>';
        nameMapArr[4] = toDecimal( nameMap.QPS );
        nameMapArr[5] = toDecimal( nameMap.Percent*100 ) + '%';
        nameMapArr[6] = '<a href="javascript:;" class="TimeLine" data-name="'+ nameMap.Name +'" >TimeLine</a> | ' +
            '<a href="javascript:;" class="Distribution" data-name="'+ nameMap.Name +'" >Distribution</a>';
        nameMapArr[7] = '--';

        // for transaction
        nameMapArr[8] = nameMap.time_max;
        nameMapArr[9] = nameMap.time_avg;
        nameMapArr[10] = nameMap.time_tp90;
        nameMapArr[11] = nameMap.time_tp95;
        nameMapArr[12] = nameMap.time_tp99;
        nameMapArr[13] = nameMap.time_tp999;

        TableData.push(nameMapArr);
    }
    for (var i in nameMapList) {
        addTableData(nameMapList[i]);
    }

    $('#event-table').dataTable( {
        "data": TableData,
        "paging": false,
        "searching": false,
        "order": [[ 1, 'desc' ],[ 0, 'asc' ]],
        "info": false,
        "scrollX": true
    } );

    // TimeLine
    $('#event-table').on('click', '.TimeLine', function () {

        // name
        var name = $(this).data('name');
        if (name == nameMap_all_name) {
            $('#timeLineModal ._name').html('All');
        } else {
            $('#timeLineModal ._name').html('Name：' + name);
        }

        // data fail
        var _xData = [];
        var _yData_All = [];
        var _yData_Fail = [];

        if (xData.length < 30) {
            for (var min = 0; min < 60; min++) {
                _xData[min] = min;
                _yData_All[min] = xData.indexOf(min)>-1?nameMapList[name].Total_TimeLine[min]:0;
                _yData_Fail[min] = xData.indexOf(min)>-1?nameMapList[name].Failure_TimeLine[min]:0;
            }
        } else {
            for (var index in xData) {
                var min = xData[index];

                _xData.push(min);
                _yData_All.push( xData.indexOf(min)>-1?nameMapList[name].Total_TimeLine[min]:0 );
                _yData_Fail.push( xData.indexOf(min)>-1?nameMapList[name].Failure_TimeLine[min]:0 );
            }
        }

        // bar
        var timeLineModal_chart_left = echarts.init(document.getElementById('timeLineModal_chart_left'));
        timeLineModal_chart_left.setOption({
            title: {
                text: 'Total'
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
                data: _xData
            },
            yAxis: {
                name: 'count',
                type: 'value'
            },
            series: [{
                data: _yData_All,
                type: 'bar'
            }]
        });

        var timeLineModal_chart_right = echarts.init(document.getElementById('timeLineModal_chart_right'));
        timeLineModal_chart_right.setOption({
            title: {
                text: 'Failure'
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
                data: _xData
            },
            yAxis: {
                name: 'count',
                type: 'value'
            },
            series: [{
                data: _yData_Fail,
                type: 'bar'
            }]
        });

        $('#timeLineModal').modal('show');

    });


    // Distribution
    $('#event-table').on('click', '.Distribution', function () {

        // name
        var name = $(this).data('name');
        if (name == nameMap_all_name) {
            $('#distributionModal ._name').html('All');
        } else {
            $('#distributionModal ._name').html('Name：' + name);
        }

        // pie data
        var pieNameList_Total = [];
        var pieNameValue_Total = [];
        for (var pieNameItem in nameMapList[name].Total_Distribution) {
            pieNameList_Total.push(pieNameItem);
            pieNameValue_Total.push({
                name    :   pieNameItem,
                value   :   nameMapList[name].Total_Distribution[pieNameItem]
            });
        }

        var pieNameList_Failure = [];
        var pieNameValue_Failure = [];
        for (var pieNameItem in nameMapList[name].Failure_Distribution) {
            pieNameList_Failure.push(pieNameItem);
            pieNameValue_Failure.push({
                name    :   pieNameItem,
                value   :   nameMapList[name].Failure_Distribution[pieNameItem]
            });
        }

        // pie
        var distributionModal_chart_left = echarts.init(document.getElementById('distributionModal_chart_left'));
        distributionModal_chart_left.setOption({
            title: {
                text: 'Total'
            },
            tooltip : {
                trigger: 'item',
                formatter: "{a} <br/>{b} : {c} ({d}%)"
            },
            legend: {
                bottom: 10,
                left: 'center',
                type: 'scroll',
                data: pieNameList_Total
            },
            series : [
                {
                    name: 'Distribution',
                    type: 'pie',
                    radius : '65%',
                    center: ['50%', '50%'],
                    selectedMode: 'single',
                    data:pieNameValue_Total,
                    itemStyle: {
                        emphasis: {
                            shadowBlur: 10,
                            shadowOffsetX: 0,
                            shadowColor: 'rgba(0, 0, 0, 0.5)'
                        }
                    }
                }
            ],
            //color : ['#c23531','#2f4554', '#61a0a8', '#d48265', '#91c7ae','#749f83',  '#ca8622', '#bda29a','#6e7074', '#546570', '#c4ccd3'],
            color:['#61a0a8', '#d48265', '#91c7ae','#749f83',  '#ca8622', '#bda29a','#6e7074', '#546570', '#c4ccd3']
        });

        var distributionModal_chart_right = echarts.init(document.getElementById('distributionModal_chart_right'));
        distributionModal_chart_right.setOption({
            title: {
                text: 'Failure'
            },
            tooltip : {
                trigger: 'item',
                formatter: "{a} <br/>{b} : {c} ({d}%)"
            },
            legend: {
                bottom: 10,
                left: 'center',
                type: 'scroll',
                data: pieNameList_Failure
            },
            series : [
                {
                    name: 'Distribution',
                    type: 'pie',
                    radius : '65%',
                    center: ['50%', '50%'],
                    selectedMode: 'single',
                    data:pieNameValue_Failure,
                    itemStyle: {
                        emphasis: {
                            shadowBlur: 10,
                            shadowOffsetX: 0,
                            shadowColor: 'rgba(0, 0, 0, 0.5)'
                        }
                    }
                }
            ]
        });

        $('#distributionModal').modal('show');
    });

});
