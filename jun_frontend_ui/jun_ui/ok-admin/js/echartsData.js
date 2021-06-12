function randomData() {
    return Math.round(Math.random() * 500);
}

var mydata = [
    {name: '北京', value: '100'}, {name: '天津', value: randomData()},
    {name: '上海', value: randomData()}, {name: '重庆', value: randomData()},
    {name: '河北', value: randomData()}, {name: '河南', value: randomData()},
    {name: '云南', value: randomData()}, {name: '辽宁', value: randomData()},
    {name: '黑龙江', value: randomData()}, {name: '湖南', value: randomData()},
    {name: '安徽', value: randomData()}, {name: '山东', value: randomData()},
    {name: '新疆', value: randomData()}, {name: '江苏', value: randomData()},
    {name: '浙江', value: randomData()}, {name: '江西', value: randomData()},
    {name: '湖北', value: randomData()}, {name: '广西', value: randomData()},
    {name: '甘肃', value: randomData()}, {name: '山西', value: randomData()},
    {name: '内蒙古', value: randomData()}, {name: '陕西', value: randomData()},
    {name: '吉林', value: randomData()}, {name: '福建', value: randomData()},
    {name: '贵州', value: randomData()}, {name: '广东', value: randomData()},
    {name: '青海', value: randomData()}, {name: '西藏', value: randomData()},
    {name: '四川', value: randomData()}, {name: '宁夏', value: randomData()},
    {name: '海南', value: randomData()}, {name: '台湾', value: randomData()},
    {name: '香港', value: randomData()}, {name: '澳门', value: randomData()}
];

layui.define(function (exports) {

    var mapTree = {
            "title": {
                "text": "用户访问"
            },
            "tooltip": {
                "trigger": "axis",
                "axisPointer": {
                    "type": "cross",
                    "label": {
                        "backgroundColor": "#6a7985"
                    }
                }
            },
            "legend": {
                "data": [
                    "邮件营销",
                    "联盟广告",
                    "视频广告",
                    "直接访问",
                    "搜索引擎"
                ]
            },
            "toolbox": {
                "feature": {
                    "saveAsImage": {}
                }
            },
            "grid": {
                "left": "3%",
                "right": "4%",
                "bottom": "3%",
                "containLabel": true
            },
            "xAxis": [
                {
                    "type": "category",
                    "boundaryGap": false,
                    "data": [
                        "周一",
                        "周二",
                        "周三",
                        "周四",
                        "周五",
                        "周六",
                        "周日"
                    ]
                }
            ],
            "yAxis": [
                {
                    "type": "value"
                }
            ],
            "series": [
                {
                    "name": "邮件营销",
                    "type": "line",
                    "stack": "总量",
                    "areaStyle": {},
                    "data": [
                        120,
                        132,
                        101,
                        134,
                        90,
                        230,
                        210
                    ]
                },
                {
                    "name": "联盟广告",
                    "type": "line",
                    "stack": "总量",
                    "areaStyle": {},
                    "data": [
                        220,
                        182,
                        191,
                        234,
                        290,
                        330,
                        310
                    ]
                },
                {
                    "name": "视频广告",
                    "type": "line",
                    "stack": "总量",
                    "areaStyle": {},
                    "data": [
                        150,
                        232,
                        201,
                        154,
                        190,
                        330,
                        410
                    ]
                },
                {
                    "name": "直接访问",
                    "type": "line",
                    "stack": "总量",
                    "areaStyle": {
                        "normal": {}
                    },
                    "data": [
                        320,
                        332,
                        301,
                        334,
                        390,
                        330,
                        320
                    ]
                },
                {
                    "name": "搜索引擎",
                    "type": "line",
                    "stack": "总量",
                    "label": {
                        "normal": {
                            "show": true,
                            "position": "top"
                        }
                    },
                    "areaStyle": {
                        "normal": {}
                    },
                    "data": [
                        820,
                        932,
                        901,
                        934,
                        1290,
                        1330,
                        1320
                    ]
                }
            ]
        },

        mapCircle = {
            "title": {
                "text": "某站点用户访问来源",
                "subtext": "",
                "x": "center"
            },
            "tooltip": {
                "trigger": "item",
                "formatter": "{a} <br/>{b} : {c} ({d}%)"
            },
            "legend": {
                "orient": "vertical",
                "left": "left",
                "data": [
                    "直接访问",
                    "邮件营销",
                    "联盟广告",
                    "视频广告",
                    "搜索引擎"
                ]
            },
            "series": [
                {
                    "name": "访问来源",
                    "type": "pie",
                    "radius": "55%",
                    "center": [
                        "50%",
                        "60%"
                    ],
                    "data": [
                        {
                            "value": 335,
                            "name": "直接访问"
                        },
                        {
                            "value": 310,
                            "name": "邮件营销"
                        },
                        {
                            "value": 234,
                            "name": "联盟广告"
                        },
                        {
                            "value": 135,
                            "name": "视频广告"
                        },
                        {
                            "value": 1548,
                            "name": "搜索引擎"
                        }
                    ],
                    "itemStyle": {
                        "emphasis": {
                            "shadowBlur": 10,
                            "shadowOffsetX": 0,
                            "shadowColor": "rgba(0, 0, 0, 0.5)"
                        }
                    }
                }
            ]
        },

        mapChina = {
            "title": {
                "text": "用户家庭所在地统计",
                "subtext": "",
                "x": "center"
            },

            "tooltip": {
                "trigger": "item"
            },

            "visualMap": {
                "color": [
                    "#eeeeee"
                ],
                "show": false,
                "x": "left",
                "y": "center",
                "splitList": [
                    {
                        "start": 500,
                        "end": 600
                    },
                    {
                        "start": 400,
                        "end": 500
                    },
                    {
                        "start": 300,
                        "end": 400
                    },
                    {
                        "start": 200,
                        "end": 300
                    },
                    {
                        "start": 100,
                        "end": 200
                    },
                    {
                        "start": 0,
                        "end": 100
                    }
                ]
            },

            "series": [
                {
                    "name": "用户家庭所在地统计",
                    "roam": true,
                    "type": "map",
                    "mapType": "china",
                    "data": [],
                    "itemStyle": {
                        "normal": {
                            "areaColor": "#eeeeee",
                            "borderColor": "#aaaaaa",
                            "borderWidth": 0.5
                        },
                        "emphasis": {
                            "areaColor": "rgba(63,177,227,0.25)",
                            "borderColor": "#3fb1e3",
                            "borderWidth": 1
                        }
                    },
                    "label": {
                        "normal": {
                            "textStyle": {
                                "color": "#000"
                            }
                        },
                        "emphasis": {
                            "textStyle": {
                                "color": "#000"
                            }
                        }
                    }
                }
            ]
        },

        mapChina2 = {
            "title": {
                "text": "用户家庭所在地统计",
                "subtext": "",
                "x": "center"
            },

            "tooltip": {
                "trigger": "item"
            },

            "visualMap": {
                "show": true,//是否显示数据条
                "min": 0,
                "max": 1,
                "left": 10,
                "top": "center",
                "orient": "vertical",
                "text": [
                    "高",
                    "低"
                ],
                "realtime": false,
                "calculable": true,
                "inRange": {
                    "color": [
                        "#E0FFFF",
                        "#BEEFEC",
                        "#6cd2d2",
                        "#6CC8C1",
                    ]
                }
            },

            "series": [
                {
                    "name": "用户家庭所在地统计",
                    "roam": true,
                    "type": "map",
                    "mapType": "china",
                    "data": [],
                    "itemStyle": {
                        "normal": {
                            "areaColor": "#eeeeee",
                            "borderColor": "#aaaaaa",
                            "borderWidth": 0.5
                        },
                        "emphasis": {
                            "areaColor": "rgba(63,177,227,0.25)",
                            "borderColor": "#3fb1e3",
                            "borderWidth": 1
                        }
                    },
                    "label": {
                        "normal": {
                            "textStyle": {
                                "color": "#000"
                            }
                        },
                        "emphasis": {
                            "textStyle": {
                                "color": "#000"
                            }
                        }
                    }
                }
            ]
        },

        mapChina3 = {
            backgroundColor: '#FFFFFF',
            title: {
                text: '全国地图大数据',
                subtext: '',
                x: 'center'
            },
            tooltip: {
                trigger: 'item'
            },

            //左侧小导航图标
            visualMap: {
                show: true,
                x: 'left',
                y: 'center',
                splitList: [
                    {start: 500, end: 600}, {start: 400, end: 500},
                    {start: 300, end: 400}, {start: 200, end: 300},
                    {start: 100, end: 200}, {start: 0, end: 100},
                ],
                color: ['#5475f5', '#9feaa5', '#85daef', '#74e2ca', '#e6ac53', '#9fb5ea']
            },

            //配置属性
            series: [{
                name: '数据统计',
                type: 'map',
                mapType: 'china',
                roam: false,//是否启用鼠标滚轮缩放地图
                label: {
                    normal: {
                        show: true  //省份名称
                    },
                    emphasis: {
                        show: false
                    }
                },
                data: mydata  //数据
            }]
        },

        Address = [
            {
                "name": "北京",
                "value": 100
            },
            {
                "name": "天津",
                "value": 83
            },
            {
                "name": "上海",
                "value": 113
            },
            {
                "name": "重庆",
                "value": 188
            },
            {
                "name": "河北",
                "value": 197
            },
            {
                "name": "河南",
                "value": 327
            },
            {
                "name": "云南",
                "value": 371
            },
            {
                "name": "辽宁",
                "value": 224
            },
            {
                "name": "黑龙江",
                "value": 295
            },
            {
                "name": "湖南",
                "value": 463
            },
            {
                "name": "安徽",
                "value": 7
            },
            {
                "name": "山东",
                "value": 176
            },
            {
                "name": "新疆",
                "value": 0
            },
            {
                "name": "江苏",
                "value": 396
            },
            {
                "name": "浙江",
                "value": 472
            },
            {
                "name": "江西",
                "value": 243
            },
            {
                "name": "湖北",
                "value": 226
            },
            {
                "name": "广西",
                "value": 404
            },
            {
                "name": "甘肃",
                "value": 210
            },
            {
                "name": "山西",
                "value": 451
            },
            {
                "name": "内蒙古",
                "value": 97
            },
            {
                "name": "陕西",
                "value": 369
            },
            {
                "name": "吉林",
                "value": 221
            },
            {
                "name": "福建",
                "value": 216
            },
            {
                "name": "贵州",
                "value": 221
            },
            {
                "name": "广东",
                "value": 85
            },
            {
                "name": "青海",
                "value": 21
            },
            {
                "name": "西藏",
                "value": 414
            },
            {
                "name": "四川",
                "value": 380
            },
            {
                "name": "宁夏",
                "value": 205
            },
            {
                "name": "海南",
                "value": 73
            },
            {
                "name": "台湾",
                "value": 348
            },
            {
                "name": "香港",
                "value": 54
            },
            {
                "name": "澳门",
                "value": 340
            }
        ];

    exports('echartsData', {
        mapTree: mapTree,
        mapCircle: mapCircle,
        mapChina3: mapChina3,
        mapChina2: mapChina2,
        mapChina: mapChina,
        Address: Address
    });
});


