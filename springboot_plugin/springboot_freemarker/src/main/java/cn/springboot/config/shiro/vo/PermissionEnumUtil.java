package cn.springboot.config.shiro.vo;

import java.util.ArrayList;
import java.util.List;

import cn.springboot.common.constants.Constants;
import cn.springboot.model.auth.Permission;

public enum PermissionEnumUtil {

    管理首页("管理首页", "fa-home", "glsy", null, "index", 1, 1), 
    
    站内新闻("站内新闻", "", "znxy", null, null, 1, 10),
    新闻发布("新闻发布", "", "znxy_xwfb", "znxy", "news/add", 2, 11),
    新闻列表("新闻列表", "", "znxy_xwlb", "znxy", "news/list",2, 12),
    默认数据库("默认数据库", "", "znxy_mrsjk", "znxy", "news/list", 2, 13),
    数据库1("数据库1", "", "znxy_sjk1", "znxy", "news/list1", 2, 14),
    数据库2("数据库2", "", "znxy_sjk2", "znxy", "news/list2", 2, 15),
    
    系统配置("系统配置", "fa-gear", "xtpz", null, "view/sysconfig/setconfig" , 1, 20),
    
    电商管理("电商管理", "fa-desktop", "dxgl", null, "view/tenant/tenant-list" , 1, 30),
    
    电商配置("电商配置", "fa-gears", "dspz", null, "view/tenantConfig/list" , 1, 40),
    
    地区城市管理("地区城市管理", "fa-map-marker", "dccsgl", null, null , 1, 50),
    添加城市("添加城市", null, "dccsgl_tjcs", "dccsgl", "view/city/add2" , 2, 51),
    城市列表("城市列表", null, "dccsgl_cslb", "dccsgl", "view/city/list" , 2, 51),
    
    账号管理("账号管理", "fa-user", "chgl", null, "view/user/user_list" , 1, 60),

    楼盘管理("楼盘管理", "fa-building", "lpgl", null, "view/project/list" , 1, 70),
    开通楼盘("开通楼盘", "fa-plus", "lpgl_ktlp", "lpgl", "view/project/openProject" , 2, 71),
    楼盘审核("楼盘审核", "fa-pencil", "lpgl_lpsh", "lpgl", "view/project/list" , 2, 72),
    楼盘管理2("楼盘管理", "fa-building", "lpgl_lpgl", "lpgl", "view/project/manager_list" , 2, 73),
    合作楼盘("合作楼盘", "fa-slideshare", "lpgl_hzlp", "lpgl", "view/project/edit_list" , 2, 74),
    添加楼盘("添加楼盘", "fa-plus", "lpgl_tjlp", "lpgl", "view/project/add" , 2, 75),
    基本信息("基本信息", "fa-file-text-o", "lpgl_tjlp_jbxx", "lpgl_tjlp", "view/project/add" , 3, 76),
    户型管理("户型管理", "fa-puzzle-piece", "lpgl_tjlp_hxgl", "lpgl_tjlp", "view/project/add2" , 3, 77),
    楼盘相册("楼盘相册", "fa-picture-o", "lpgl_tjlp_lpxc", "lpgl_tjlp", "view/project/add3" , 3, 78),
    销控管理("销控管理", "fa-sort-numeric-asc", "lpgl_tjlp_xkgl", "lpgl_tjlp", "view/project/add4" , 3, 79),
    合作信息("合作信息", "fa-slideshare", "lpgl_tjlp_hzxx", "lpgl_tjlp", "view/project/add5" , 3, 80),
    产品维护("产品维护", "fa-share-alt", "lpgl_tjlp_cpwh", "lpgl_tjlp", "view/project/add6" , 3, 81),
    拥金维护("拥金维护", "fa-dollar", "lpgl_tjlp_rjwh", "lpgl_tjlp", "view/project/add7" , 3, 82),
    楼盘动态("楼盘动态", "fa-list-alt", "lpgl_tjlp_lpdt", "lpgl_tjlp", "view/project/add8" , 3, 83),
    优惠活动("优惠活动", "fa-heart", "lpgl_tjlp_yhhd", "lpgl_tjlp", "view/project/add9" , 3, 84),
    
    经济公司_经纪人("经济公司/经纪人", "fa-group", "jjgsjjr", null, null , 1, 90),
    经纪公司维护("经纪公司维护", "fa-vine", "jjgsjjr_jjgswh", "jjgsjjr", "view/firm/list" , 2, 91),
    经纪人管理("经纪人管理", "fa-group", "jjgsjjr_jjrgl", "jjgsjjr", "view/agent/list" , 2, 92),
    经纪人实名认证("经纪人实名认证", "fa-newspaper-o", "jjgsjjr_jjrsmrz", "jjgsjjr", "view/agent/list" , 2, 93),
    经纪人银行卡认证("经纪人银行卡认证", "fa-credit-card", "jjgsjjr_jjryhkrz", "jjgsjjr", "view/agent/list" , 2, 94),
    
    置业顾问管理("置业顾问管理", "fa-user-md", "zygwgl", null, null , 1, 100),
    置业顾问挂靠审核("置业顾问挂靠审核", "fa-stumbleupon", "zygwgl_zygwgksh", "zygwgl", "view/adviserProject/adviserAuditList" , 2, 101),
    置业顾问管理2("置业顾问管理", "fa-user-md", "zygwgl_zygwgl",  "zygwgl", "view/adviser/list" , 2, 102),
    置业顾问实名认证("置业顾问实名认证", "fa-newspaper-o", "zygwgl_zygwsmrz",  "zygwgl", "view/agent/list" , 2, 103),
    置业顾问银行卡认证("置业顾问银行卡认证", "fa-credit-card", "zygwgl_zygwyhkrz",  "zygwgl", "view/agent/list" , 2, 104),
    置业顾问元宝提现("置业顾问元宝提现", "fa-database", "zygwgl_zygwybtx",  "zygwgl", "view/agent/list" , 2, 105),
    
    POS交易管理("POS交易管理", "fa-credit-card", "posjygl",  null, null , 1, 110),
    POS机绑定("POS机绑定", "fa-unlock-alt", "posjygl_posjbd",  "posjygl", "view/pos/bind" , 2, 111),
    POS绑定信息("POS绑定信息", "fa-link", "posjygl_posbdxx",  "posjygl", "view/pos/list" , 2, 112),
    POS交易信息("POS交易信息", "fa-legal", "posjygl_posjyxx",  "posjygl", "view/pos/poslog" , 2, 113),
    
    数据统计("数据统计", "fa-pie-chart", "sjtj",  null, null , 1, 120),
    用户统计("用户统计", "fa-user", "sjtj_yhtj",  "sjtj", "view/data/statIncome_data" , 2, 121),
    客户统计("客户统计", "fa-group", "sjtj_khtj",  "sjtj", "view/data/statIncome_data" , 2, 122),
    收入统计("收入统计", "fa-line-chart", "sjtj_sytj",  "sjtj", "view/data/statIncome_data" , 2, 123),
    佣金统计("佣金统计", "fa-pie-chart", "sjtj_yjtj",  "sjtj", "view/data/statIncome_data" , 2, 124),
    
    合同管理("合同管理", "fa-edit", "htgl",  null , null , 1, 130),
    添加_管理列表("添加/管理列表", "fa-plus", "htgl_tjgllb",  "htgl" , "view/agreement/list" , 2, 131),
    转认购("转认购", "fa-mail-forward", "htgl_zrg",  "htgl" , "view/agreement/rengou" , 2, 132),
    签约("签约", "fa-check-square-o", "htgl_qy",  "htgl" , "view/agreement/qianyue" , 2, 133),
    合同明细_编辑("合同明细/编辑", "fa-edit", "htgl_htmxbj",  "htgl" , "view/agreement/edit" , 2, 134),
    合同详情("合同详情", "fa-building-o", "htgl_htxq",  "htgl" , "view/agreement/view" , 2, 135),
    
    总经理("总经理", "fa-sitemap", "zjl",  null , null , 1, 140),
    客户统计2("客户统计", "fa-group", "zjl_khtj",  "zjl", "view/data/statIncome_data" , 2, 141),
    收入统计2("收入统计", "fa-line-chart", "zjl_sytj",  "zjl", "view/data/statIncome_data" , 2, 143),
    佣金统计2("佣金统计", "fa-pie-chart", "zjl_yjtj",  "zjl", "view/data/statIncome_data" , 2, 144),
    
    经济公司管理员("经济公司管理员", "fa-sitemap", "jjgsgly", null, null , 1, 150),
    合作("合作", "fa-slideshare", "jjgsgly_hz", "jjgsgly", "view/firm/verify" , 2, 151),
    门店列表("门店列表", "fa-slideshare", "jjgsgly_mdlb", "jjgsgly", "view/firm/list2" , 2, 152),
    账号管理1("账号管理", "fa-slideshare", "jjgsgly_zhgl", "jjgsgly", "view/user/user_list" , 2, 153),
    挂靠审核("挂靠审核", "fa-slideshare", "jjgsgly_ghsh", "jjgsgly", "view/firm/verify" , 2, 154),
    对公账号("对公账号", "fa-slideshare", "jjgsgly_dgzh", "jjgsgly", "view/firm/view" , 2, 155),
    经纪人列表("经纪人列表", "fa-slideshare", "jjgsgly_jjrlb", "jjgsgly", "view/agent/list" , 2, 156),
    客户列表("客户列表", "fa-slideshare", "jjgsgly_khlb", "jjgsgly", "view/agent/custAgent" , 2, 157),
    佣金信息("佣金信息", "fa-slideshare", "jjgsgly_yjxx", "jjgsgly", "view/commission/agentCommission" , 2, 158),
    额外佣金("额外佣金", "fa-slideshare", "jjgsgly_ewyj", "jjgsgly", "view/commission/commissionExt" , 2, 159),
    客户统计3("客户统计", "fa-group", "jjgsgly_khtj",  "jjgsgly", "view/data/statIncome_data" , 2, 160),
    收入统计3("收入统计", "fa-line-chart", "jjgsgly_sytj",  "jjgsgly", "view/data/statIncome_data" , 2, 161),
    佣金统计3("佣金统计", "fa-pie-chart", "jjgsgly_yjtj",  "jjgsgly", "view/data/statIncome_data" , 2, 162),
    
    门店管理员("门店管理员", "fa-sitemap", "mdgly",  null, null , 1, 170),
    挂靠审核2("挂靠审核", "fa-slideshare", "mdgly_ghsh", "mdgly", "view/firm/verify" , 2, 171),
    经纪人列表2("经纪人列表", "fa-slideshare", "mdgly_jjrlb", "mdgly", "view/agent/list" , 2, 172),
    客户列表2("客户列表", "fa-slideshare", "mdgly_khlb", "mdgly", "view/agent/custAgent" , 2, 173),
    用户统计2("用户统计", "fa-user", "mdgly_yhtj2",  "mdgly", "view/data/statIncome_data" , 2, 174),
    用户统计3("用户统计", "fa-user", "mdgly_yhtj3",  "mdgly", "view/data/statIncome_data" , 2, 175),
    用户统计4("用户统计", "fa-user", "mdgly_yhtj4",  "mdgly", "view/data/statIncome_data" , 2, 176),
    客户统计4("客户统计", "fa-group", "mdgly_khtj",  "mdgly", "view/data/statIncome_data" , 2, 177),
    
    案场经理("案场经理", "fa-sitemap", "acjl", null , null , 1, 180),
    楼盘管理3("楼盘管理", null , "acjl_lpgl", "acjl" , null , 2, 181),
    楼盘列表("楼盘列表", null , "acjl_lpgl_lpgl", "acjl_lpgl" , "view/project/list" , 3, 182),
    编辑添加("编辑添加", null , "acjl_lpgl_bjtj", "acjl_lpgl" , "view/project/add" , 3, 183),
    置业顾问挂靠审核2("置业顾问挂靠审核", "fa-stumbleupon", "acjl_zygwgksh", "acjl", "view/adviserProject/adviserAuditList" , 2, 184),
    置业顾问管理3("置业顾问管理", "fa-user-md", "acjl_zygwgl",  "acjl", "view/adviser/list" , 2, 185),
    客户审核("客户审核", null , "acjl_khsh",  "acjl", "view/custintent/custAuditList" , 2, 186),
    
    项目总监("项目总监", null , "xmzj",  null , null , 1 , 190),
    客户统计5("客户统计", "fa-group", "xmzj_khtj",  "xmzj", "view/data/statIncome_data" , 2, 191),
    收入统计5("收入统计", "fa-line-chart", "xmzj_sytj",  "xmzj", "view/data/statIncome_data" , 2, 192),
    佣金统计5("佣金统计", "fa-pie-chart", "xmzj_yjtj",  "xmzj", "view/data/statIncome_data" , 2, 193),
    
    项目经理("项目经理", "fa-sitemap", "xmjl",  null , null , 1 , 200),
    开通楼盘2("开通楼盘", "fa-plus", "xmjl_ktlp", "xmjl", "view/project/openProject" , 2, 201),
    账号管理2("账号管理", "fa-slideshare", "xmjl_zhgl", "xmjl", "view/user/user_list" , 2, 202),
    佣金审核("佣金审核", null , "xmjl_yjsh", "xmjl", "view/commission/list" , 2, 203),
    开发商补贴("开发商补贴", null , "xmjl_kfsbt", "xmjl", "view/devpSubsidy/list" , 2, 204),
    推送管理("推送管理", null , "xmjl_tsgl", "xmjl", "view/push/list" , 2, 205),
    客户统计6("客户统计", "fa-group", "xmjl_khtj",  "xmjl", "view/data/statIncome_data" , 2, 206),
    收入统计6("收入统计", "fa-line-chart", "xmjl_sytj",  "xmjl", "view/data/statIncome_data" , 2, 207),
    佣金统计6("佣金统计", "fa-pie-chart", "xmjl_yjtj",  "xmjl", "view/data/statIncome_data" , 2, 208),
    
    项目助理("项目助理", "fa-sitemap", "xmzl",  null , null , 1 , 210),
    确认到访("确认到访", null , "xmzl_qrdf",  "xmzl" , "view/code/code" , 2 , 211),
    发送语音验证码("发送语音验证码", null , "xmzl_fsyyyzm",  "xmzl" , "view/code/telcode" , 2 , 212),
    POS机绑定2("POS机绑定", "fa-unlock-alt", "xmzl_posjbd",  "xmzl", "view/pos/bind" , 2, 213),
    POS交易记录("POS交易记录", "fa-link", "xmzl_posbdxx",  "xmzl", "view/pos/list" , 2, 214),
    客户意向查询("客户意向查询", null , "xmzl_khyxqx" ,  "xmzl", "view/custintent/custAuditList" , 2 , 215),
    合同管理2("合同管理", null , "xmzl_htgl" ,  "xmzl", "view/agreement/list" , 2 , 216),
    
    运营专员("运营专员", "fa-sitemap" , "yyzy" ,  null , null , 1 , 220),
    签到规则配置("签到规则配置", null , "yyzy_qdgzpz" ,  "yyzy" , "view/sysconfig/signin" , 2 , 221),
    楼盘管理4("楼盘管理", "fa-building", "yyzy_lpgl", "yyzy", "view/project/manager_list" , 2, 222),
    城市管理("城市管理", null, "yyzy_tjcs", "yyzy", "view/city/add2" , 2, 223),
    账号管理3("账号管理", "fa-slideshare", "yyzy_zhgl", "yyzy", "view/user/user_list" , 2, 224),
    推送管理2("推送管理", null , "yyzy_tsgl", "yyzy", "view/push/list" , 2, 225),
    广告位管理("广告位管理", null , "yyzy_ggwgl", "yyzy", "view/adv/list" , 2, 226),
    客户意向查询2("客户意向查询", null , "yyzy_khyxqx" ,  "yyzy", "view/custintent/custAuditList" , 2 , 227),
    POS机绑定3("POS机绑定", "fa-unlock-alt", "yyzy_posjbd",  "yyzy", "view/pos/bind" , 2, 228),
    POS交易记录2("POS交易记录", "fa-link", "yyzy_posjyjl",  "yyzy", "view/pos/list" , 2, 229),
    签约经纪公司("签约经纪公司", null , "yyzy_qyjjgs",  "yyzy", "view/pos/list" , 2 , 230),
    经纪人管理2("经纪人管理", "fa-group", "yyzy_jjrgl", "yyzy", "view/agent/list" , 2 , 231),
    用户统计5("用户统计", "fa-user", "yyzy_yhtj",  "yyzy", "view/data/statIncome_data" , 2, 232),
    客户统计7("客户统计", "fa-group", "yyzy_khtj",  "yyzy", "view/data/statIncome_data" , 2, 233),
    收入统计7("收入统计", "fa-line-chart", "yyzy_sytj",  "yyzy", "view/data/statIncome_data" , 2, 234),
    佣金统计7("佣金统计", "fa-pie-chart", "yyzy_yjtj",  "yyzy", "view/data/statIncome_data" , 2, 235),
    
    佣金专员("佣金专员", "fa-sitemap", "yjzy", null , null , 1, 240),
    佣金规则审核("佣金规则审核", null , "yjzy_yjgzsh", "yjzy" , "view/commission/auditList" , 2, 241),
    经纪人实名认证2("经纪人实名认证", "fa-newspaper-o", "yjzy_jjrsmrz", "yjzy", "view/agent/list" , 2, 242),
    经纪人银行卡认证2("经纪人银行卡认证", "fa-credit-card", "yjzy_jjryhkrz", "yjzy", "view/agent/list" , 2, 243),
    经纪人元宝提现("经纪人元宝提现", "fa-credit-card", "yjzy_jjrybtx", "yjzy", "view/agent/grain" , 2, 244),
    置业顾问实名认证2("置业顾问实名认证", "fa-newspaper-o", "yjzy_zygwsmrz",  "yjzy", "view/agent/list" , 2, 245),
    置业顾问银行卡认证2("置业顾问银行卡认证", "fa-credit-card", "yjzy_zygwyhkrz",  "yjzy", "view/agent/list" , 2, 246),
    置业顾问元宝提现2("置业顾问元宝提现", "fa-database", "yjzy_zygwybtx",  "yjzy", "view/agent/list" , 2, 247),
    对公账号审核("对公账号审核", "fa-database", "yjzy_dgzhsh",  "yjzy", "view/firm/account" , 2, 248),
    退款审核("退款审核", null , "yjzy_tksh",  "yjzy", "view/refund/list" , 2, 249),
    佣金审核2("佣金审核", null , "yjzy_yjsh", "yjzy", "view/commission/list" , 2, 250),
    合同管理3("合同管理", null , "yjzy_htgl" ,  "yjzy", "view/agreement/list" , 2 , 251),
    额外佣金审核("额外佣金审核", null , "yjzy_ewyjsh" ,  "yjzy", "view/commission/Ext" , 2 , 252),
    
    出纳专员("出纳专员", "fa-sitemap" , "cnzy" , null , null , 1 , 260),
    经纪人元宝提现2("经纪人元宝提现", "fa-credit-card", "cnzy_jjrybtx", "cnzy", "view/agent/grain" , 2, 261),
    置业顾问元宝提现3("置业顾问元宝提现", "fa-database", "cnzy_zygwybtx",  "cnzy", "view/agent/list" , 2, 262),
    退款审核2("退款审核", null , "cnzy_tksh", "cnzy", "view/refund/list" , 2, 263),
    佣金审核3("佣金审核", null , "cnzy_yjsh", "cnzy", "view/commission/list" , 2, 264),
    额外佣金审核4("额外佣金审核", null , "cnzy_ewyjsh" , "cnzy", "view/commission/Ext" , 2 , 265),
    
    财务专员("财务专员", null , "cwzy" , null , null , 1 , 270),
    经纪人元宝提现3("经纪人元宝提现", "fa-credit-card", "cwzy_jjrybtx", "cwzy", "view/agent/grain" , 2, 271),
    置业顾问元宝提现4("置业顾问元宝提现", "fa-database", "cwzy_zygwybtx",  "cwzy", "view/agent/list" , 2, 272),
    开发商补贴确认("开发商补贴确认", null , "cwzy_kfsbt",  "cwzy", "view/devpSubsidy/list2" , 2, 273),
    退款审核3("退款审核", null , "cwzy_tksh", "cwzy", "view/refund/list" , 2, 274),
    佣金审核4("佣金审核", null , "cwzy_yjsh", "cwzy", "view/commission/list" , 2, 275),
    POS交易记录3("POS交易记录", "fa-link", "cwzy_posbdxx",  "cwzy", "view/pos/list" , 2, 276),
    额外佣金审核5("额外佣金审核", null , "cwzy_ewyjsh" , "cwzy", "view/commission/Ext" , 2 , 277),
    
    经纪服务专员测试("经纪服务专员测试", "fa-sitemap" , "jjfwzycs" , null , null , 1 , 280),
    经纪公司维护2("经纪公司维护", null , "jjfwzycs_jjgswh" , "jjfwzycs" , "view/firm/list2" , 2 , 281),
    
    客服专员("客服专员", "fa-sitemap" , "kfzy" , null , null , 1 , 290),
    客户意向审核("客户意向审核", "fa-sitemap" , "kfzy_khyxsh" , "kfzy" , "view/custintent/custSupport" , 2 , 291),
    ;

    private PermissionEnumUtil(String name, String cssClass, String key, String parentKey, String url, Integer lev, Integer sort) {
        this.name = name;
        this.cssClass = cssClass;
        this.key = key;
        this.lev=lev;
        this.sort = sort;
        this.url = url;
        this.hide = Constants.STATUS_VALID;
        this.parentKey = parentKey;
    }
    
    public static List<Permission> getPermissions(){
        List<Permission> list = new ArrayList<>();
        PermissionEnumUtil[] pers = PermissionEnumUtil.values();
        Permission per = null;
        for (PermissionEnumUtil p : pers) {
            per = new Permission();
            per.setName(p.getName());
            per.setCssClass(p.getCssClass());
            per.setKey(p.getKey());
            per.setHide(p.getHide());
            per.setUrl(p.getUrl());
            per.setLev(p.getLev());
            per.setSort(p.getSort());
            per.setParentKey(p.getParentKey());
            list.add(per);
        }
        return list;
    }

    private String name;
    private String cssClass;
    private String key;
    private Integer hide;
    private String url;
    private Integer lev;
    private Integer sort;
    private String parentKey;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCssClass() {
        return cssClass;
    }

    public void setCssClass(String cssClass) {
        this.cssClass = cssClass;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Integer getHide() {
        return hide;
    }

    public void setHide(Integer hide) {
        this.hide = hide;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getLev() {
        return lev;
    }

    public void setLev(Integer lev) {
        this.lev = lev;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public String getParentKey() {
        return parentKey;
    }

    public void setParentKey(String parentKey) {
        this.parentKey = parentKey;
    }

}
