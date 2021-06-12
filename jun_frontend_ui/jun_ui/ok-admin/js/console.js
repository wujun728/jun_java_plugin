"use strict";
layui.use(["okUtils", "table", "okCountUp", "okMock"], function () {
    var countUp = layui.okCountUp;
    var table = layui.table;
    var okUtils = layui.okUtils;
    var okMock = layui.okMock;
    var $ = layui.jquery;

    /**
     * 收入、商品、博客、用户
     */
    function statText() {
        var elem_nums = $(".stat-text");
        elem_nums.each(function (i, j) {
            var ran = parseInt(Math.random() * 99 + 1);
            !new countUp({
                target: j,
                endVal: 20 * ran
            }).start();
        });
    }

    var userSourceOption = {
        "title": {"text": ""},
        "tooltip": {"trigger": "axis", "axisPointer": {"type": "cross", "label": {"backgroundColor": "#6a7985"}}},
        "legend": {"data": ["邮件营销", "联盟广告", "视频广告", "直接访问", "搜索引擎"]},
        "toolbox": {"feature": {"saveAsImage": {}}},
        "grid": {"left": "3%", "right": "4%", "bottom": "3%", "containLabel": true},
        "xAxis": [{"type": "category", "boundaryGap": false, "data": ["周一", "周二", "周三", "周四", "周五", "周六", "周日"]}],
        "yAxis": [{"type": "value"}],
        "series": [
            {"name": "邮件营销", "type": "line", "stack": "总量", "areaStyle": {}, "data": [120, 132, 101, 134, 90, 230, 210]},
            {"name": "联盟广告", "type": "line", "stack": "总量", "areaStyle": {}, "data": [220, 182, 191, 234, 290, 330, 310]},
            {"name": "视频广告", "type": "line", "stack": "总量", "areaStyle": {}, "data": [150, 232, 201, 154, 190, 330, 410]},
            {"name": "直接访问", "type": "line", "stack": "总量", "areaStyle": {"normal": {}}, "data": [320, 332, 301, 334, 390, 330, 320]},
            {"name": "搜索引擎", "type": "line", "stack": "总量", "label": {"normal": {"show": true, "position": "top"}}, "areaStyle": {"normal": {}}, "data": [820, 932, 901, 934, 1290, 1330, 1320]}
        ]
    };

    /**
     * 用户访问
     */
    function userSource() {
        var userSourceMap = echarts.init($("#userSourceMap")[0], "theme");
        userSourceMap.setOption(userSourceOption);
        okUtils.echartsResize([userSourceMap]);
    }

    /**
     * 所有用户
     */
    function userList() {
        table.render({
            method: "get",
            url: okMock.api.user.list,
            elem: '#userData',
            height: 340,
            page: true,
            limit: 7,
            cols: [[
                {field: "id", title: "ID", width: 180},
                {field: "username", title: "账号", width: 100},
                {field: "password", title: "密码", width: 80},
                {field: "email", title: "邮箱", width: 200},
                {field: "createTime", title: "创建时间", width: 180},
                {field: "logins", title: "登录次数", width: 100}
            ]],
//             parseData: function (res) {
//                 res.data.list.forEach(function (i, j) {
//                     var dateTime = new Date(i.u_endtime);
//                     i.u_endtime = dateTime.getFullYear() + "-" + dateTime.getMonth() + "-" + dateTime.getDay();
//                 });
//                 return {
//                     "code": res.code,
//                     "count": res.data.count,
//                     "data": res.data.list
//                 }
//             }
        });
    }

    var userLocationOption = {
        "title": {"text": "用户家庭所在地统计", "subtext": "", "x": "center"},
        "tooltip": {"trigger": "item"},
        "visualMap": {
            "color": ["#eeeeee"], "show": false, "x": "left", "y": "center",
            "splitList": [
                {"start": 500, "end": 600},
                {"start": 400, "end": 500},
                {"start": 300, "end": 400},
                {"start": 200, "end": 300},
                {"start": 100, "end": 200},
                {"start": 0, "end": 100}
            ]
        },
        "series": [
            {
                "name": "用户家庭所在地统计", "roam": true, "type": "map", "mapType": "china", "data": [],
                "itemStyle": {
                    "normal": {"areaColor": "#eeeeee", "borderColor": "#aaaaaa", "borderWidth": 0.5},
                    "emphasis": {"areaColor": "rgba(63,177,227,0.25)", "borderColor": "#3fb1e3", "borderWidth": 1}
                },
                "label": {"normal": {"textStyle": {"color": "#000"}}, "emphasis": {"textStyle": {"color": "#000"}}}
            }
        ]
    };

    /**
     * 用户位置
     */
    function userLocation() {
        var userLocationMap = echarts.init($("#userLocationMap")[0]);
        var data = [{"name":"北京","value":100},{"name":"天津","value":83},{"name":"上海","value":113},{"name":"重庆","value":188},{"name":"河北","value":197},{"name":"河南","value":327},{"name":"云南","value":371},{"name":"辽宁","value":224},{"name":"黑龙江","value":295},{"name":"湖南","value":463},{"name":"安徽","value":7},{"name":"山东","value":176},{"name":"新疆","value":0},{"name":"江苏","value":396},{"name":"浙江","value":472},{"name":"江西","value":243},{"name":"湖北","value":226},{"name":"广西","value":404},{"name":"甘肃","value":210},{"name":"山西","value":451},{"name":"内蒙古","value":97},{"name":"陕西","value":369},{"name":"吉林","value":221},{"name":"福建","value":216},{"name":"贵州","value":221},{"name":"广东","value":85},{"name":"青海","value":21},{"name":"西藏","value":414},{"name":"四川","value":380},{"name":"宁夏","value":205},{"name":"海南","value":73},{"name":"台湾","value":348},{"name":"香港","value":54},{"name":"澳门","value":340}];
        userLocationOption.series.data = data;
        userLocationMap.setOption(userLocationOption);
        okUtils.echartsResize([userLocationMap]);
    }

    statText();
    userSource();
    userList();
    userLocation();
});


