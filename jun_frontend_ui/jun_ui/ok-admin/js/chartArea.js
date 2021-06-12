layui.define(function (exports) {
    var option_a = {
        title: {
            text: '重庆市',
            subtext: '',
            x: 'left'
        },
        tooltip: {
            trigger: 'item',
            formatter: '{b}',
            itemSize: '14px'
        },
        legend: {
            orient: 'vertical',
            x: 'center',
            data: ['重庆市区县']
        },
        dataRange: {
            x: 'left',
            y: 'bottom',
            splitList: [
                {start: 1500},
                {start: 900, end: 1500},
                {start: 310, end: 1000},
                {start: 200, end: 300},
                {start: 10, end: 200, label: '10 到 200（火灾数量）'},
                {start: 5, end: 5, label: '5（火灾数量）', color: 'black'},
                {end: 10}
            ],
            color: ['#eee', '#949fb1', '#f3ce85']
        },
        series: [
            {
                name: '重庆市区县',
                type: 'map',
                mapType: '重庆',
                roam: true,
                itemStyle: {
                    normal: {
                        label: {
                            show: false,
                            textStyle: {
                                color: "#000"
                            }
                        }
                    },
                    emphasis: {label: {show: true}}
                },
                data: [
                    {name: '城口县', value: Math.round(Math.random() * 2000)},
                    {name: '开县', value: Math.round(Math.random() * 2000)},
                    {name: '巫溪县', value: Math.round(Math.random() * 2000)},
                    {name: '云阳县', value: Math.round(Math.random() * 2000)},
                    {name: '奉节县', value: Math.round(Math.random() * 2000)},
                    {name: '巫山县', value: Math.round(Math.random() * 2000)},
                    {name: '万州区', value: Math.round(Math.random() * 2000)},
                    {name: '梁平县', value: Math.round(Math.random() * 2000)},
                    {name: '忠县', value: Math.round(Math.random() * 2000)},
                    {name: '垫江县', value: Math.round(Math.random() * 2000)},
                    {name: '石柱土家族自治县', value: Math.round(Math.random() * 2000)},
                    {name: '丰都县', value: Math.round(Math.random() * 2000)},
                    {name: '长寿区', value: Math.round(Math.random() * 2000)},
                    {name: '涪陵区', value: Math.round(Math.random() * 2000)},
                    {name: '合川区', value: Math.round(Math.random() * 2000)},
                    {name: '潼南县', value: Math.round(Math.random() * 2000)},
                    {name: '铜梁县', value: Math.round(Math.random() * 2000)},
                    {name: '渝北区', value: Math.round(Math.random() * 2000)},
                    {name: '璧山县', value: Math.round(Math.random() * 2000)},
                    {name: '沙坪坝县', value: Math.round(Math.random() * 2000)},
                    {name: '江北区', value: Math.round(Math.random() * 2000)},
                    {name: '大足县', value: Math.round(Math.random() * 2000)},
                    {name: '永川区', value: Math.round(Math.random() * 2000)},
                    {name: '綦江县', value: Math.round(Math.random() * 2000)},
                    {name: '南川区', value: Math.round(Math.random() * 2000)},
                    {name: '万盛区', value: Math.round(Math.random() * 2000)},
                    {name: '大渡口区', value: Math.round(Math.random() * 2000)},
                    {name: '南岸区', value: Math.round(Math.random() * 2000)},
                    {name: '武隆县', value: Math.round(Math.random() * 2000)},
                    {name: '九龙坡区', value: Math.round(Math.random() * 2000)},
                    {name: '荣昌县', value: Math.round(Math.random() * 2000)},
                    {name: '秀山土家族苗族自治县', value: Math.round(Math.random() * 2000)},
                    {name: '酉阳土家族苗族自治县', value: Math.round(Math.random() * 2000)},
                    {name: '彭水苗族土家族自治县', value: Math.round(Math.random() * 2000)},
                    {name: '江津区', value: Math.round(Math.random() * 2000)},
                    {name: '北碚区', value: Math.round(Math.random() * 2000)},
                    {name: '巴南区', value: Math.round(Math.random() * 2000)}
                ]
            }
        ]
    };

    var option_b = {
        series: [{
            type: 'map',
            mapType: 'china',
            label: {
                normal: {
                    show: true, //显示省份标签
                    textStyle: {
                        color: "blue"
                    } //省份标签字体颜色
                },
                emphasis: { //对应的鼠标悬浮效果
                    show: false,
                    textStyle: {
                        color: "#800080"
                    }
                }
            },
            aspectScale: 0.75,
            zoom: 1.2,
            itemStyle: {
                normal: {
                    borderWidth: .5, //区域边框宽度
                    borderColor: '#009fe8', //区域边框颜色
                    areaColor: "#ffefd5", //区域颜色
                },
                emphasis: {
                    borderWidth: .5,
                    borderColor: '#4b0082',
                    areaColor: "#ffdead",
                }
            },
            data: [
                {name: '北京', selected: false, value: 1},
                {name: '天津', selected: false, value: 2},
                {name: '上海', selected: false, value: 3},
                {name: '重庆', selected: false, value: 4},
                {name: '河北', selected: false, value: 5},
                {name: '河南', selected: false, value: 6},
                {name: '云南', selected: false, value: 7},
                {name: '辽宁', selected: false, value: 8},
                {name: '黑龙江', selected: false, value: 9},
                {name: '湖南', selected: false, value: 10},
                {name: '安徽', selected: false, value: 11},
                {name: '山东', selected: false, value: 12},
                {name: '新疆', selected: false, value: 13},
                {name: '江苏', selected: false, value: 14},
                {name: '浙江', selected: false, value: 15},
                {name: '江西', selected: false, value: 16},
                {name: '湖北', selected: false, value: 17},
                {name: '广西', selected: false, value: 18},
                {name: '甘肃', selected: false, value: 19},
                {name: '山西', selected: false, value: 20},
                {name: '内蒙古', selected: false, value: 21},
                {name: '陕西', selected: false, value: 22},
                {name: '吉林', selected: false, value: 23},
                {name: '福建', selected: false, value: 24},
                {name: '贵州', selected: false, value: 25},
                {name: '广东', selected: false, value: 26},
                {name: '青海', selected: false, value: 27},
                {name: '西藏', selected: false, value: 28},
                {name: '四川', selected: false, value: 29},
                {name: '宁夏', selected: false, value: 30},
                {name: '海南', selected: false, value: 31},
                {name: '台湾', selected: false, value: 32},
                {name: '香港', selected: false, value: 33},
                {name: '澳门', selected: false, value: 34}
            ] //各省地图颜色数据依赖value
        }],
        dataRange: {
            x: '-1000 px', //图例横轴位置
            y: '-1000 px', //图例纵轴位置
            splitList: [
                {start: 1, end: 1, label: '北京', color: '#cfc5de'},
                {start: 2, end: 2, label: '天津', color: '#f1ebd1'},
                {start: 3, end: 3, label: '上海', color: '#feffdb'},
                {start: 4, end: 4, label: '重庆', color: '#e0cee4'},
                {start: 5, end: 5, label: '河北', color: '#fde8cd'},
                {start: 6, end: 6, label: '河南', color: '#e4f1d7'},
                {start: 7, end: 7, label: '云南', color: '#fffed7'},
                {start: 8, end: 8, label: '辽宁', color: '#e4f1d7'},
                {start: 9, end: 9, label: '黑龙江', color: '#e4f1d7'},
                {start: 10, end: 10, label: '湖南', color: '#fffed7'},
                {start: 11, end: 11, label: '安徽', color: '#fffed8'},
                {start: 12, end: 12, label: '山东', color: '#dccee7'},
                {start: 13, end: 13, label: '新疆', color: '#fffed7'},
                {start: 14, end: 14, label: '江苏', color: '#fce8cd'},
                {start: 15, end: 15, label: '浙江', color: '#ddceeb'},
                {start: 16, end: 16, label: '江西', color: '#e4f1d3'},
                {start: 17, end: 17, label: '湖北', color: '#fde8cd'},
                {start: 18, end: 18, label: '广西', color: '#fde8cd'},
                {start: 19, end: 19, label: '甘肃', color: '#fde8cd'},
                {start: 20, end: 20, label: '山西', color: '#fffdd6'},
                {start: 21, end: 21, label: '内蒙古', color: '#ddcfe6'},
                {start: 22, end: 22, label: '陕西', color: '#fad8e9'},
                {start: 23, end: 23, label: '吉林', color: '#fce8cd'},
                {start: 24, end: 24, label: '福建', color: '#fad8e8'},
                {start: 25, end: 25, label: '贵州', color: '#fad8e8'},
                {start: 26, end: 26, label: '广东', color: '#ddcfe8'},
                {start: 27, end: 27, label: '青海', color: '#fad8e9'},
                {start: 28, end: 28, label: '西藏', color: '#ddcfe6'},
                {start: 29, end: 29, label: '四川', color: '#e4f1d5'},
                {start: 30, end: 30, label: '宁夏', color: '#fefcd5'},
                {start: 31, end: 31, label: '海南', color: '#fad8e9'},
                {start: 32, end: 32, label: '台湾', color: '#fce8cd'},
                {start: 33, end: 33, label: '香港', color: '#dc9bbb'},
                {start: 34, end: 34, label: '澳门', color: '#e0f7cc'}
            ]
        }, //各省地图颜色；start：值域开始值；end：值域结束值；label：图例名称；color：自定义颜色值；
    };

    var option_c = {
        title: {
            text: '四川省',
            subtext: '',
            x: 'left'
        },
        tooltip: {
            trigger: 'item',
            formatter: '{b}',
            itemSize: '14px'
        },
        legend: {
            orient: 'vertical',
            x: 'center',
            data: ['四川省区县']
        },
        series: [
            {
                name: '四川省区县',
                type: 'map',
                mapType: '四川',
                roam: false,
                itemStyle: {
                    normal: {
                        label: {show: false},
                        borderWidth: 1,//省份的边框宽度
                        childBorderWidth: 1,
                        childBorderColor: '#6EA1F4'
                    },
                    emphasis: {label: {show: true}}
                },
                data: [
                    {name: '阿坝藏族羌族自治州', value: 0},
                    {name: '巴中市', value: 20},
                    {name: '成都市', value: 0},
                    {name: '达州市', value: 0},
                    {name: '德阳市', value: 0},
                    {name: '甘孜藏族自治州', value: 0},
                    {name: '广安市', value: 0},
                    {name: '广元市', value: 0},
                    {name: '乐山市', value: 0},
                    {name: '凉山彝族自治州', value: 0},
                    {name: '泸州市', value: 0},
                    {name: '眉山市', value: 0},
                    {name: '绵阳市', value: 0},
                    {name: '内江市', value: 0},
                    {name: '南充市', value: 0},
                    {name: '攀枝花市', value: 0},
                    {name: '遂宁市', value: 0},
                    {name: '雅安市', value: 0},
                    {name: '宜宾市', value: 0},
                    {name: '资阳市', value: 0},
                    {name: '自贡市', value: 0}
                ]
            }
        ]
    };

    var option_d = {
        title: {
            text: '广东省',
            subtext: '',
            x: 'left'
        },
        tooltip: {
            trigger: 'item',
            formatter: '{b}',
            itemSize: '14px'
        },
        legend: {
            orient: 'vertical',
            x: 'center',
            data: ['广东省区县']
        },
        series: [
            {
                name: '广东省区县',
                type: 'map',
                mapType: '广东',
                roam: false,
                itemStyle: {
                    normal: {
                        label: {show: false},
                        borderWidth: 1,//省份的边框宽度
                        childBorderWidth: 1,
                        childBorderColor: '#6EA1F4'
                    },
                    emphasis: {label: {show: true}}
                },
                data: [
                    {name: '广州市', value: 1350},
                    {name: '深圳市', value: 1190},
                    {name: '珠海市', value: 167},
                    {name: '汕头市', value: 555},
                    {name: '佛山市', value: 743},
                    {name: '韶关市', value: 293},
                    {name: '湛江市', value: 724},
                    {name: '肇庆市', value: 405},
                    {name: '江门市', value: 451},
                    {name: '茂名市', value: 608},
                    {name: '惠州市', value: 475},
                    {name: '梅州市', value: 434},
                    {name: '汕尾市', value: 302},
                    {name: '河源市', value: 307},
                    {name: '阳江市', value: 251},
                    {name: '清远市', value: 383},
                    {name: '东莞市', value: 825},
                    {name: '中山市', value: 320},
                    {name: '潮州市', value: 264},
                    {name: '揭阳市', value: 605},
                    {name: '云浮市', value: 246}
                ]
            }
        ]
    };

    exports('chartArea', {
        option_a: option_a,
        option_b: option_b,
        option_c: option_c,
        option_d: option_d
    });
});
