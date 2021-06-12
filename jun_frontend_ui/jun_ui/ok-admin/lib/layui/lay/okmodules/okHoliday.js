"use strict";
layui.define(["okUtils"], function (exprots) {
    let okUtils = layui.okUtils;

    var okHoliday = {
        getContent: function () {
            let dateStr = okUtils.dateFormat(new Date(), "yyyy-MM-dd");
            let content = "";
            if (dateStr == "2020-01-01") {
                content = "元旦，一年之始也。元，初，始也；旦，太阳微露地平一线，是为一日之始。值此新年之际，祝诸君快乐安康，如意吉祥！<br/>" +
                    "ok-admin v2.0 祝您元旦节快乐！(^し^)";
            } else if (dateStr == "2020-01-24" || dateStr == "2020-01-25" || dateStr == "2020-01-26" || dateStr == "2020-01-27" || dateStr == "2020-01-28" || dateStr == "2020-01-29" || dateStr == "2020-01-30") {
                content = "鞭炮声声迎新年，妙联横生贴门前。<br/>" +
                    "笑声处处传入耳，美味佳肴上餐桌。<br/>" +
                    "谈天论地成一片，灯光通明照残夜。<br/>" +
                    "稚童新衣相夸耀，旧去新来气象清。<br/>" +
                    "ok-admin v2.0 祝您春节快乐！(^し^)";
            } else if (dateStr == "2020-04-04") {
                content = "4月4日<br/>" +
                    "举国哀悼，愿逝者安息<br/>" +
                    "山河无恙，盼人间皆安"
            } else if (dateStr == "2020-04-05" || dateStr == "2020-04-06") {
                content = "清明时节雨纷纷，路上行人欲断魂。<br/>" +
                    "借问酒家何处有，牧童遥指杏花村。<br/>" +
                    "又是一年春草绿，又是一年清明时。";
            } else if (dateStr == "2020-05-01" || dateStr == "2020-05-02" || dateStr == "2020-05-03" || dateStr == "2020-05-04" || dateStr == "2020-05-05") {
                content = "锄禾日当午，汗滴禾下土。<br/>" +
                    "谁知盘中餐，粒粒皆辛苦。<br/>" +
                    "ok-admin v2.0 祝您劳动节快乐！(^し^)";
            } else if (dateStr == "2020-06-25" || dateStr == "2020-06-26" || dateStr == "2020-06-27") {
                content = "少年佳节倍多情，老去谁知感慨生。<br>" +
                    "不效艾符趋习俗，但祈蒲酒话升平。<br>" +
                    "鬓丝日日添白头，榴锦年年照眼明。<br>" +
                    "千载贤愚同瞬息，几人湮没几垂名。<br>" +
                    "ok-admin v2.0 祝您端午节安康！(^し^)";
            } else if (dateStr == "2020-09-18") {
                content = "铭记九一八，让历史的风云鞭策我们奋进的脚步，让先烈的忠魂聆听我们自强的怒吼;<br/>" +
                    "不忘九一八，让未来的发展见证华夏的崛起，让世界的目光聚焦中国的奇迹。<br/>" +
                    "勿忘国耻，爱我中华，吾辈当自强！";
            } else if (dateStr == "2020-10-01") {
                content = "国庆中秋齐相遇，月圆国圆共欢腾。<br/>" +
                    "万家团圆意相同，普天同众双重喜。<br/>" +
                    "烟花白鸽齐绽放，张灯结彩贺双节。<br/>" +
                    "祝福祖国寿无疆，祝福朋友阖家欢。<br/>" +
                    "ok-admin v2.0 祝您双节快乐！(^し^)";
            } else if (dateStr == "2020-10-02" || dateStr == "2020-10-03" || dateStr == "2020-10-04" || dateStr == "2020-10-05" || dateStr == "2020-10-06" || dateStr == "2020-10-07") {
                content = "龙跃甲子，鸽翱晴空，凤舞九天。<br/>" +
                    "昔关河黍离，列强逐鹿；神州放眼，一鹤冲天。<br/>" +
                    "重振社稷，举中流誓，今看东方盛世还。<br/>" +
                    "黄河血，慨仁人志士，魂祭新篇。<br/>" +
                    "华夏意气峥嵘，傲五湖四海锦绣满。<br/>" +
                    "壮三山五岳，叠古风姿；九经三史，彰现华韵。<br/>" +
                    "豪客泼墨，贤士铺卷，放歌九州富丽妍。<br/>" +
                    "泰山脊，领风骚环宇，有谁堪比？<br/>" +
                    "ok-admin v2.0 祝您国庆节快乐！<br/>祝福伟大的祖国，越来越强大。<br/>祝福强大的祖国，一直屹立于世界东方！(^し^)";
            } else {
                content = "ok-admin v2.0上线啦(^し^)<br/>" +
                    "在此郑重承诺该模板<span style='color:#5cb85c'>永久免费</span>为大家提供<br/>" +
                    "若有更好的建议欢迎<span id='noticeQQ'>加入QQ群</span>一起聊";
            }
            return content;
        }
    }

    exprots("okHoliday", okHoliday);
});
