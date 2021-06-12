
(function($){$.fn.compSearchBox=function(){$(function(){$('#click_event').click(function(){if($('#hidden_enent').is(':hidden')){$(this).addClass("more_bg"); $('#hidden_enent').show(); $('#click_event').val('点击隐藏'); }else{$(this).removeClass("more_bg"); $('#hidden_enent').hide(); $('#click_event').val('点击显示'); } }); $('#click_event2').click(function(){if($('#hidden_enent2').is(':hidden')){$(this).addClass("more_bg"); $('#hidden_enent2').show(); $('#click_event2').val('点击隐藏'); }else{$(this).removeClass("more_bg"); $('#hidden_enent2').hide(); $('#click_event2').val('点击显示'); } }); }); } })(jQuery);

$(document).click(function(){$("table :checkbox:first").change(function(){$(this).closest("table").find(":checkbox:not(:first)").prop("checked", this.checked);});
});


// 绩效趋势
(function ($) {
    "use strict";
var cWidth = $("#chartWork").width();
var cHeight = $("#chartWork").height();
// console.log(cHeight);

// 总预算 budget
$("#budget").css("width", cWidth-280).css("height", cHeight-200);
var dom = document.getElementById("budget");
var myChart = echarts.init(dom);
option = {
    tooltip : {
        trigger: 'axis',
        axisPointer : {            // 坐标轴指示器，坐标轴触发有效
            type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
        }
    },
    grid: {
        left: '0%',
        containLabel: true
    },
    xAxis:  {
        type: 'value',
        show:false,
    },
    yAxis: {
        type: 'category',
        show:false,
        data: ['总预算']
    },
    series: [
        {
            name: '固定',
            type: 'bar',
            stack: '总量',
            barWidth: 16,
            label: {
                normal: {
                    show: true,
                    position: 'insideRight'
                }
            },
            data: [987220]
        },
        {
            name: '无形',
            type: 'bar',
            stack: '总量',
            label: {
                normal: {
                    show: true,
                    position: 'insideRight'
                }
            },
            data: [902645]
        },
        {
            name: 'IT金额',
            type: 'bar',
            stack: '总量',
            label: {
                normal: {
                    show: true,
                    position: 'insideRight'
                }
            },
            data: [622220]
        }
    ],
    color:['#30A5FF', '#FABE28','#3dbb2b']
};
if (option && typeof option === "object") {
    myChart.setOption(option, true);
}
// 固定 fixation
$("#fixation").css("width", cWidth-320).css("height", cHeight-200);
var dom = document.getElementById("fixation");
var myChart = echarts.init(dom);
var data = [64];
var xMax = 100;
option = {
    tooltip: {
        show: true,
        formatter: "{b} {c}"
    },
    grid: {
        left: '20px',
        top: '0px',
        bottom: '20px',
        right: '20px'
    },
    xAxis: [{
        max: xMax,
        type: 'value',
        axisTick: {
            show: false,
        },
        axisLine: {
            show: false,
        },
        axisLabel: {
            show: false
        },
        splitLine: {
            show: false
        }
    }],
    yAxis: [{
        type: 'category',
        // data: ['其他'],
        nameTextStyle: {
            color: '#FF0000',
            fontSize: '18px'
        },
        axisTick: {
            show: false,
        },
        axisLine: {
            show: false,
        }
    }],
    series: [{
        name: ' ',
        type: 'bar',
        barWidth: 16,
        silent: true,
        itemStyle: {
            normal: {
                color: '#C6C7C8'
            }
        },
        barGap: '-100%',
        barCategoryGap: '50%',
        data: data.map(function(d) {
            return xMax
        }),
    }, {
        name: ' ',
        type: 'bar',
        barWidth: 16,
        label: {
            normal: {
                show: true,
                position: 'right',
                formatter: '{c}%',
            }
        },
        data: [{
            value: 64,
            itemStyle: {
                normal: {
                    color: '#047AD4'
                }
            }
        }],
        markLine: {
            label: {
                normal: {
                    show: false,
                    position: 'end',
                    formatter: '{b}{c}%'
                }
            }
        }
    }]
};

if (option && typeof option === "object") {
    myChart.setOption(option, true);
}
// 无形 invisible
$("#invisible").css("width", cWidth-320).css("height", cHeight-200);
var dom = document.getElementById("invisible");
var myChart = echarts.init(dom);
var data = [39];
var xMax = 100;
option = {
    tooltip: {
        show: true,
        formatter: "{b} {c}"
    },
    grid: {
        left: '20px',
        top: '0px',
        bottom: '20px',
        right: '20px'
    },
    xAxis: [{
        max: xMax,
        type: 'value',
        axisTick: {
            show: false,
        },
        axisLine: {
            show: false,
        },
        axisLabel: {
            show: false
        },
        splitLine: {
            show: false
        }
    }],
    yAxis: [{
        type: 'category',
        // data: ['其他'],
        nameTextStyle: {
            color: '#FF0000',
            fontSize: '18px'
        },
        axisTick: {
            show: false,
        },
        axisLine: {
            show: false,
        }
    }],
    series: [{
        name: ' ',
        type: 'bar',
        barWidth: 16,
        silent: true,
        itemStyle: {
            normal: {
                color: '#C6C7C8'
            }
        },
        barGap: '-100%',
        barCategoryGap: '50%',
        data: data.map(function(d) {
            return xMax
        }),
    }, {
        name: ' ',
        type: 'bar',
        barWidth: 16,
        label: {
            normal: {
                show: true,
                position: 'right',
                formatter: '{c}%',
            }
        },
        data: [{
            value: 64,
            itemStyle: {
                normal: {
                    color: '#FABE28'
                }
            }
        }],
        markLine: {
            label: {
                normal: {
                    show: false,
                    position: 'end',
                    formatter: '{b}{c}%'
                }
            }
        }
    }]
};
if (option && typeof option === "object") {
    myChart.setOption(option, true);
}
// IT金额 itsum
$("#itsum").css("width", cWidth-320).css("height", cHeight-200);
var dom = document.getElementById("itsum");
var myChart = echarts.init(dom);
var data = [39];
var xMax = 100;
option = {
    tooltip: {
        show: true,
        formatter: "{b} {c}"
    },
    grid: {
        left: '20px',
        top: '0px',
        bottom: '20px',
        right: '20px'
    },
    xAxis: [{
        max: xMax,
        type: 'value',
        axisTick: {
            show: false,
        },
        axisLine: {
            show: false,
        },
        axisLabel: {
            show: false
        },
        splitLine: {
            show: false
        }
    }],
    yAxis: [{
        type: 'category',
        nameTextStyle: {
            color: '#FF0000',
            fontSize: '18px'
        },
        axisTick: {
            show: false,
        },
        axisLine: {
            show: false,
        }
    }],
    series: [{
        name: ' ',
        type: 'bar',
        barWidth: 16,
        silent: true,
        itemStyle: {
            normal: {
                color: '#C6C7C8'
            }
        },
        barGap: '-100%',
        barCategoryGap: '50%',
        data: data.map(function(d) {
            return xMax
        }),
    }, {
        name: ' ',
        type: 'bar',
        barWidth: 16,
        label: {
            normal: {
                show: true,
                position: 'right',
                formatter: '{c}%',
            }
        },
        data: [{
            value: 64,
            itemStyle: {
                normal: {
                    color: '#3dbb2b'
                }
            }
        }],
        markLine: {
            label: {
                normal: {
                    show: false,
                    position: 'end',
                    formatter: '{b}{c}%'
                }
            }
        }
    }]
};
if (option && typeof option === "object") {
    myChart.setOption(option, true);
}

$("#chartWork").css("width", cWidth).css("height", cHeight);
var dom = document.getElementById("chartWork");
var myChart = echarts.init(dom);
    var gain = 0.9;
    var gap = 0;
    var myColor=['#e63810','#047AD4','#FABE28','#3DBB2B'];
    var myBgColor=['rgba(230,56,16,0.4)','rgba(4,122,212,0.4)','rgba(61,187,43,0.4)','rgba(19,181,177,0.4)'];
    //柱子数据
    var data = [8, 15, 10, 6];
    var option = {
    //   backgroundColor:'rgba(0,0,0,0.8)',
            grid: {
                left: '3%',
                top:'10%',
                right: '2%',
                bottom: '5%',
                containLabel: true
            },
            xAxis: [{
                    type: 'category',
                    axisTick: {
                        show: false
                    },
                    axisLine:{
                        lineStyle:{
                            color:'#333333',
                        }
                    },
                    axisLabel:{
                        textStyle:{
                            color:function(param,index){
                                return myColor[index]
                            },
                            fontSize:13*gain,
                        }
                    },
                    data: ['销售一部','销售二部','销售三部','销售四部']
                }, {
                    type: 'category',
                    axisLine: {
                        show: false
                    },
                    axisTick: {
                        show: false
                    },
                    axisLabel: {
                        show: false
                    },
                    splitArea: {
                        show: false
                    },
                    splitLine: {
                        show: false
                    },
                    data: []
                },
            ],
            yAxis: {
                type: 'value',
                name:'单位：亿元',
                nameGap:35+gap,
                nameTextStyle:{
                    color:'#333333',
                    fontSize:16*gain,
                },
                axisTick: {
                    show: false
                },
                axisLine: {
                    show: false
                },
                splitLine: {
                    lineStyle:{
                        color:'rgba(160,160,160,0.2)',
                    }
                },
                axisLabel: {
                    textStyle: {
                        color: 'rgba(255,255,255,0.4)',
                        fontSize:14*gain,
                    }
                }
            },
            series: [{
                    type: 'bar',
                    xAxisIndex: 1,
                    itemStyle: {
                        normal: {
                            show: true,
                            color: function(params) {
                                var num=myBgColor.length;
                                return myBgColor[params.dataIndex%num]
                            },
                            barBorderRadius: 50,
                            borderWidth: 0,
                            borderColor: '#333',
                        }
                    },
                    label:{
                        normal:{
                           show:true,
                           formatter: function(params) {
                            var stuNum = 0;
                            data.forEach(function(value, index, array) {
                                if (params.dataIndex == index) {
                                   stuNum = value;
                                }
                            })
                            return stuNum ;
                          },
                          position: 'top',
                          textStyle:{
                              color:function(params) {
                                var num=myBgColor.length;
                                return myBgColor[params.dataIndex%num]
                            },
                              fontSize:25*gain,
                          }
                        }
                    },
                    barWidth: '25%',
                    data: [33, 33, 33,33]
                }, 
                {
                    type: 'bar',
                    itemStyle: {
                        normal: {
                            show: true,
                            color: function(params) {
                                var num=myColor.length;
                                return myColor[params.dataIndex%num]
                            },
                            barBorderRadius: 50,
                            borderWidth: 0,
                            borderColor: '#333',
                        }
                    },
                    label: {
                        normal: {
                            show: false,

                        }
                    },
                    barWidth: '25%',
                    data: data
                }
            ]
    };
if (option && typeof option === "object") {
    myChart.setOption(option, true);
}
//挣值分析 
var dom = document.getElementById("analysisChart");
var myChart = echarts.init(dom);
option = {
    title: {
        text: '单位(人天)',
        textStyle: {
            fontSize:12
        }
    },
    tooltip: {
        trigger: 'axis'
    },
    legend: {
        data:['计划值', '实际值', '挣值分析']
    },
    grid: {
        left: '3%',
        right: '4%',
        bottom: '3%',
        containLabel: true
    },
    xAxis: {
        type: 'category',
        data: ['2018年2月', '2018年3月', '2018年4月', '2018年5月', '2018年6月', '2018年7月', '2018年8月']
    },
    yAxis: {
        type: 'value',
        name : 'BAC'
    },
    series: [
        {
            name:'计划值',
            type:'line',
            step: 'start',
            data:[90, 150, 169, 154, 188, 178, 189]
        },
        {
            name:'实际值',
            type:'line',
            step: 'middle',
            data:[68, 152, 131, 144, 190, 158, 176]
        },
        {
            name:'挣值分析',
            type:'line',
            step: 'end',
            data:[39, 99, 111, 104, 134, 120, 110]
        }
    ],
    color:['#30A5FF', '#FABE28','#3dbb2b']
};
if (option && typeof option === "object") {
    myChart.setOption(option, true);
}


// table里边进度条 tbProgress
// IT金额 itsum
// $(".tbProgress").css("width", cWidth-320).css("height", cHeight-200);

// tbProgress
for (var i = 1; i <= 5; i++) {
    var myChart = echarts.init(document.getElementById("tbProgress"+i));
    var data = [39];
    var xMax = 100;
    option = {
        tooltip: {
            show: true,
            formatter: "{b} {c}"
        },
        grid: {
            left: '20px',
            top: '10px',
            bottom: '10px',
            right: '20px'
        },
        xAxis: [{
            max: xMax,
            type: 'value',
            axisTick: {
                show: false,
            },
            axisLine: {
                show: false,
            },
            axisLabel: {
                show: false
            },
            splitLine: {
                show: false
            }
        }],
        yAxis: [{
            type: 'category',
            nameTextStyle: {
                color: '#FF0000',
                fontSize: '18px'
            },
            axisTick: {
                show: false,
            },
            axisLine: {
                show: false,
            }
        }],
        series: [{
            name: ' ',
            type: 'bar',
            barWidth: 16,
            silent: true,
            itemStyle: {
                normal: {
                    color: '#C6C7C8'
                }
            },
            barGap: '-100%',
            barCategoryGap: '50%',
            data: [],
        }, {
            name: ' ',
            type: 'bar',
            barWidth: 16,
            label: {
                normal: {
                    show: true,
                    position: 'right',
                    formatter: '{c}%',
                }
            },
            data: [],
            markLine: {
                label: {
                    normal: {
                        show: false,
                        position: 'end',
                        formatter: '{b}{c}%'
                    }
                }
            }
        }]
    };
    if (option && typeof option === "object") {
        myChart.setOption(option, true);
    }
}


for (var i = 1; i <= 5; i++) {
  var myChart_1 = echarts.init(document.getElementById("tbProgress"+i));

  myChart_1.setOption({
        series: [
          {
            type: 'bar',
            data:[100]
          },{
            type: 'bar',
            data:[59]
          }
      ]
    });

}

// 阶段分类
// 
var domWc = document.getElementById("wc-line-chart");
var wcChart = echarts.init(domWc);
option = {
    backgroundColor: '',
    tooltip: {
        trigger: 'axis',
        axisPointer: {
            lineStyle: {
                color: '#57617B'
            }
        }
    },
    grid: {
        left: '30px',
        right: '30px',
        bottom: '30px',
        top: '30px',
        containLabel: true
    },
    xAxis: [
        {
        type: 'category',
        boundaryGap: false,
        axisLine: {
            lineStyle: {
                color: '#333333'
            }
        },
        axisLabel: {
            margin: 10,
            textStyle: {
                fontSize:13,
                color: '#333333'
            }
        },
        axisTick: {
            show: false
        },
        data: ['一月', '二月', '三月', '四月', '五月', '六月', '七月', '八月', '九月', '十月', '十一月', '十二月']
    }, {
        axisPointer: {
            show: false
        },
        axisLine: {
            lineStyle: {
                color: '#0E2A43'
            }
        },
        axisTick: {
            show: false
        },
        position: 'bottom',
        offset: 20
    }],
    yAxis: [{
        type: 'value',
        name: '单位（%）',
        axisTick: {
            show: false
        },
        axisLine: {
            lineStyle: {
                color: '#0E2A43'
            }
        },
        axisLabel: {
            margin: 10,
            textStyle: {
                fontSize: 12,
                color: '#333333'
            }
        },
        splitLine: {
            show: false,
            lineStyle: {
                color: '#57617B'
            }
        }
    }],
    series: [{
        name: '移动',
        type: 'line',
        smooth: true,
        stack: '总量',
        symbol: 'circle',
        symbolSize: 5,
        showSymbol: false,
        animationDelay: 2000,
        animationDuration: 1000,
        markPoint: {
            // symbol: 'image://url',
            data: [
                {type: 'max', name: '最大值'}
            ],
            animationDelay: 3000,
            animationDuration: 1000
        },
        lineStyle: {
            normal: {
                width: 1,
                color: {
                    type: 'linear',
                    x: 0,
                    y: 0,
                    x2: 1,
                    y2: 0,
                    colorStops: [{
                        offset: 0, color: 'red' // 0% 处的颜色
                    }, {
                        offset: 1, color: 'grey' // 100% 处的颜色
                    }],
                    globalCoord: false // 缺省为 false
                },
                opacity: 0.9
            }
        },
        areaStyle: {
            normal: {
                color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [{
                    offset: 0,
                    color: 'rgba(137, 189, 27, 0.3)'
                }, {
                    offset: 0.8,
                    color: 'rgba(137, 189, 27, 0)'
                }], false),
                shadowColor: 'rgba(0, 0, 0, 0.1)',
                shadowBlur: 10
            }
        },
        itemStyle: {
            normal: {
                color: 'rgb(137,189,27)',
                borderColor: 'rgba(137,189,2,0.27)',
                borderWidth: 12

            }
        },
        data: [220, 182, 191, 134, 250, 120, 110, 125, 145, 122, 165, 122]
    }, {
        name: '电信',
        type: 'line',
        smooth: true,
        symbol: 'circle',
        stack: '总量',
        symbolSize: 5,
        animationDelay: 1000,
        animationDuration: 1000,
        markPoint: {
            data: [
                {type: 'max', name: '最大值'}
            ],
            animationDelay: 2000,
            animationDuration: 1000
        },
        showSymbol: false,
        lineStyle: {
            normal: {
                width: 1,
                color: {
                    type: 'linear',
                    x: 0,
                    y: 0,
                    x2: 1,
                    y2: 0,
                    colorStops: [{
                        offset: 0, color: 'red' // 0% 处的颜色
                    }, {
                        offset: 1, color: 'yellow' // 100% 处的颜色
                    }],
                    globalCoord: false // 缺省为 false
                },
                opacity: 0.9
            }
        },
        areaStyle: {
            normal: {
                color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [{
                    offset: 0,
                    color: 'rgba(0, 236, 212, 0.3)'
                }, {
                    offset: 0.8,
                    color: 'rgba(0, 236, 212, 0)'
                }], false),
                shadowColor: 'rgba(0, 0, 0, 0.1)',
                shadowBlur: 10
            }
        },
        itemStyle: {
            normal: {
                color: 'rgb(0,136,212)',
                borderColor: 'rgba(0,136,212,0.2)',
                borderWidth: 12

            }
        },
        data: [120, 110, 125, 145, 122, 165, 122, 220, 282, 191, 134, 150]
    }, {
        name: '联通',
        type: 'line',
        stack: '总量',
        smooth: true,
        symbol: 'circle',
        symbolSize: 5,
        showSymbol: false,
        animationDelay: 0,
        animationDuration: 1000,
        markPoint: {
            data: [
                {type: 'max', name: '最大值'}
            ],
            animationDelay: 1000,
            animationDuration: 1000
        },
        lineStyle: {
            normal: {
                width: 1,
                color: {
                    type: 'linear',
                    x: 0,
                    y: 0,
                    x2: 1,
                    y2: 0,
                    colorStops: [{
                        offset: 0, color: 'red' // 0% 处的颜色
                    }, {
                        offset: 1, color: 'yellowgreen' // 100% 处的颜色
                    }],
                    globalCoord: false // 缺省为 false
                },
                opacity: 0.9
            }
        },
        areaStyle: {
            normal: {
                color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [{
                    offset: 0,
                    color: 'rgba(219, 50, 51, 0.3)'
                }, {
                    offset: 0.8,
                    color: 'rgba(219, 50, 51, 0)'
                }], false),
                shadowColor: 'rgba(0, 0, 0, 0.1)',
                shadowBlur: 10
            }
        },
        itemStyle: {
            normal: {

                color: 'rgb(219,50,51)',
                borderColor: 'rgba(219,50,51,0.2)',
                borderWidth: 12
            }
        },
        data: [220, 182, 325, 145, 122, 191, 134, 150, 120, 110, 165, 122]
    }, ]
};


if (option && typeof option === "object") {
    wcChart.setOption(option, true);
}

// 采购分类
// 

var domWc = document.getElementById("defect-chart");
var dfChart = echarts.init(domWc);
option = {
    backgroundColor: "",
    color: ['#047AD4', '#FABE28', '#3DBB2B'],

    title: [{
        text: '利用率',
        left: '83%',
        top: '6%',
        textAlign: 'center',
        textStyle: {
            color: '#333333'
        }
    }],
    tooltip: {
        trigger: 'axis'
    },
    legend: {
        x: 300,
        top: '7%',
        textStyle: {
            color: '#333333',
        },
        data: ['郑州银行', '仁怀银行', '北京银行']
    },
    grid: {
        left: '1%',
        right: '35%',
        top: '16%',
        bottom: '6%',
        containLabel: true
    },
    toolbox: {
        "show": false,
        feature: {
            saveAsImage: {}
        }
    },
    xAxis: {
        type: 'category',
        "axisLine": {
            lineStyle: {
                color: '#aaaaaa'
            }
        },
        "axisTick": {
            "show": false
        },
        axisLabel: {
            textStyle: {
                color: '#333'
            }
        },
        boundaryGap: false,
        data: ['一月', '二月', '三月', '四月', '五月', '六月', '七月']
    },
    yAxis: {
        "axisLine": {
            lineStyle: {
                color: '#aaaaaa'
            }
        },
        splitLine: {
            show: true,
            lineStyle: {
                color: '#aaaaaa'
            }
        },
        "axisTick": {
            "show": false
        },
        axisLabel: {
            textStyle: {
                color: '#333333'
            }
        },
        type: 'value'
    },
    series: [{
        name: '郑州银行',
        smooth: true,
        type: 'line',
        symbolSize: 8,
        symbol: 'circle',
        data: [90, 50, 39, 50, 120, 82, 80]
    }, {
        name: '仁怀银行',
        smooth: true,
        type: 'line',
        symbolSize: 8,
        symbol: 'circle',
        data: [70, 50, 50, 87, 90, 80, 70]
    }, {
        name: '北京银行',
        smooth: true,
        type: 'line',
        symbolSize: 8,
        symbol: 'circle',
        data: [290, 200,20, 132, 15, 200, 90]
    }, 
    {
        type: 'pie',
        center: ['83%', '33%'],
        radius: ['25%', '30%'],
        label: {
            normal: {
                position: 'center'
            }
        },
        data: [{
            value: 335,
            name: '来源分析',
            itemStyle: {
                normal: {
                    color: '#89BD1B'
                }
            },
            label: {
                normal: {
                    formatter: '{d} %',
                    textStyle: {
                        color: '#333333',
                        fontSize: 20

                    }
                }
            }
        }, {
            value: 180,
            name: '占位',
            tooltip: {
                show: false
            },
            itemStyle: {
                normal: {
                    color: '#C6C7C8'
                }
            },
            label: {
                normal: {
                    textStyle: {
                        color: '#333333',
                    },
                    formatter: '\n计划'
                }
            }
        }]
    },


    {
        type: 'pie',
        center: ['83%', '72%'],
        radius: ['25%', '30%'],
        label: {
            normal: {
                position: 'center'
            }
        },
        data: [{
            value: 435,
            name: '来源分析',
            itemStyle: {
                normal: {
                    color: '#66AEE3'
                }
            },
            label: {
                normal: {
                    formatter: '{d} %',
                    textStyle: {
                        color: '#333333',
                        fontSize: 20

                    }
                }
            }
        }, {
            value: 100,
            name: '占位',
            tooltip: {
                show: false
            },
            itemStyle: {
                normal: {
                    color: '#C6C7C8'


                }
            },
            label: {
                normal: {
                    textStyle: {
                        color: '#333333',
                    },
                    formatter: '\n执行'
                }
            }
        }]
    }]
}
if (option && typeof option === "object") {
    dfChart.setOption(option, true);
}
// --------------------------
}(jQuery));


