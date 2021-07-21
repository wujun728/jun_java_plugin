function fix360Auto(e, t) {
    setTimeout(function() {
        $.trim(e.val()).length > 0 && t.hide()
    },
    100)
}
function companyInEvent(e, t) {
    var o = $(".labelTxt", e),
    n = $(".companyInput", e),
    r = $(".clearImg", e);
    o.off("click").on("click",
    function() {
        n.focus()
    }),
    fix360Auto(n, o),
    n.off().on({
        blur: function() {
            var e = $(this).val();
            e && 0 !== $.trim(e).length || (o.show(), t.errorTip(t.errorTxt.E10), r.hide()),
            $(this).removeClass("focus")
        },
        focus: function() {
            $(this).addClass("focus"),
            t.errorTip()
        },
        keyup: function() {
            var e = $(this).val();
            e && 0 !== $.trim(e).length || o.show()
        },
        keydown: function() {
            o.hide(),
            r.show()
        }
    }),
    r.off().on({
        mouseover: function() {
            $(this).addClass("clearImgHover")
        },
        mouseout: function() {
            $(this).removeClass("clearImgHover")
        },
        click: function() {
            n.val(""),
            n.trigger("blur")
        }
    })
}
function qjUanmeInEvent(e, t, o) {
    var n = $(".labelTxt", e),
    r = $(".unameInput", e),
    a = t.qj.$compname,
    i = $(".clearImg", e);
    n.off("click").on("click",
    function() {
        r.focus()
    }),
    fix360Auto(r, n),
    r.off().on({
        blur: function() {
            var e = $(this).val();
            e && 0 !== $.trim(e).length ? $.trim(a.val()).length > 0 && t.qjRenderVcode(o, t) : (n.show(), t.errorTip(t.errorTxt.E1), i.hide()),
            $(this).removeClass("focus")
        },
        focus: function() {
            $(this).addClass("focus"),
            t.errorTip()
        },
        keyup: function() {
            var e = $(this).val();
            e && 0 !== $.trim(e).length || n.show()
        },
        keydown: function() {
            n.hide(),
            i.show()
        }
    }),
    i.off().on({
        mouseover: function() {
            $(this).addClass("clearImgHover")
        },
        mouseout: function() {
            $(this).removeClass("clearImgHover")
        },
        click: function() {
            r.val(""),
            r.trigger("blur")
        }
    })
}
function qjPwdInEvent(e, t, o) {
    var n = $(".labelTxt", e),
    r = $(".pwdInput", e),
    a = t.qj.$userName;
    n.off("click").on("click",
    function() {
        r.focus()
    }),
    fix360Auto(r, n),
    r.off().on({
        blur: function() {
            $(this).removeClass("focus");
            var e = $(this).val();
            return 0 === $.trim(a.val()).length ? (a.trigger("blur"), !1) : (e && 0 !== $.trim(e).length || (n.show(), t.errorTip(t.errorTxt.E2)), $.trim(e).length < 6 && t.errorTip(t.errorTxt.E12), void 0)
        },
        focus: function() {
            $(this).addClass("focus"),
            t.errorTip()
        },
        keyup: function() {
            var e = $(this).val();
            e && 0 !== $.trim(e).length || n.show()
        },
        keydown: function(e) {
            n.hide(),
            13 === e.which && o.trigger("click")
        }
    })
}
function uanmeInEvent(e, t, o) {
    var n = $(".labelTxt", e),
    r = $(".unameInput", e),
    a = $(".clearImg", e);
    n.off("click").on("click",
    function() {
        r.focus()
    }),
    fix360Auto(r, n),
    r.off().on({
        blur: function() {
            var e = $(this).val();
            e && 0 !== $.trim(e).length ? t.renderVcode(o, t) : (n.show(), t.errorTip(t.errorTxt.E1), a.hide()),
            $(this).removeClass("focus")
        },
        focus: function() {
            $(this).addClass("focus"),
            t.errorTip()
        },
        keyup: function() {
            var e = $(this).val();
            e && 0 !== $.trim(e).length || n.show()
        },
        keydown: function() {
            n.hide(),
            a.show()
        }
    }),
    a.off().on({
        mouseover: function() {
            $(this).addClass("clearImgHover")
        },
        mouseout: function() {
            $(this).removeClass("clearImgHover")
        },
        click: function() {
            r.val(""),
            r.trigger("blur")
        }
    })
}
function pwdInEvent(e, t, o) {
    var n = $(".labelTxt", e),
    r = $(".pwdInput", e),
    a = t.cnzz.$userName;
    n.off("click").on("click",
    function() {
        r.focus()
    }),
    fix360Auto(r, n),
    r.off().on({
        blur: function() {
            $(this).removeClass("focus");
            var e = $(this).val();
            return 0 === $.trim(a.val()).length ? (a.trigger("blur"), !1) : e && 0 !== $.trim(e).length ? ($.trim(e).length < 6 && t.errorTip(t.errorTxt.E12), void 0) : (n.show(), t.errorTip(t.errorTxt.E2), !1)
        },
        focus: function() {
            $(this).addClass("focus"),
            t.errorTip()
        },
        keyup: function() {
            var e = $(this).val();
            e && 0 !== $.trim(e).length || n.show()
        },
        keydown: function(e) {
            n.hide(),
            13 === e.which && o.trigger("click")
        }
    })
}
function vCodeInEvent(e, t, o, n) {
    var r = $(".labelTxt", e),
    a = $(".vCodeInput", e),
    i = $(".vCodeImg", e),
    s = $(".htxt", e);
    r.off("click").on("click",
    function() {
        a.focus()
    }),
    a.off().on({
        blur: function() {
            if ($(this).removeClass("focus"), 0 === $.trim(o.val()).length) return o.trigger("blur"),
            !1;
            var e = $(this).val();
            e && 0 !== $.trim(e).length ? t.errorTip() : (r.show(), t.errorTip(t.errorTxt.E4))
        },
        focus: function() {
            $(this).addClass("focus"),
            $.trim(n.val()).length > 0 && $.trim(o.val()).length > 0 && t.errorTip()
        },
        keyup: function() {
            var e = $(this).val();
            e && 0 !== $.trim(e).length || r.show()
        },
        keydown: function() {
            r.hide()
        }
    }),
    i.off("click").on("click",
    function() {
        $(this).attr({
            src: "main.php?c=findpwd&a=number&t=" + (new Date).getTime()
        })
    }),
    s.off("click").on("click",
    function() {
        i.attr({
            src: "main.php?c=findpwd&a=number&t=" + (new Date).getTime()
        })
    })
}
function productSelect(e) {
    var t = $(".productList", e),
    o = $(".txt", e),
    n = $(".pli", t);
    e.off().on("click",
    function(e) {
        0 === $(e.target).parents(".productList").length && t.show()
    }),
    n.off().on({
        mouseover: function() {
            $(this).addClass("hover")
        },
        mouseout: function() {
            $(this).removeClass("hover")
        },
        click: function() {
            o.html($(this).text()),
            o.attr({
                val: $(this).attr("val")
            }),
            t.hide(),
            n.removeClass("hover")
        }
    })
}
function remuser(e) {
    var t = $(".cimg", e),
    o = $(".ctxt", e),
    n = $(".rTip");
    t.off().on({
        mouseover: function() {
            n.show()
        },
        mouseout: function() {
            n.hide()
        },
        click: function() {
            var e = $(this).parent();
            e.hasClass("selected") ? e.removeClass("selected") : t.parent().addClass("selected")
        }
    }),
    o.off().on({
        mouseover: function() {
            n.show()
        },
        mouseout: function() {
            n.hide()
        },
        click: function() {
            var e = $(this).parent();
            e.hasClass("selected") ? e.removeClass("selected") : t.parent().addClass("selected")
        }
    })
}
function Tab(e) {
    var t = this;
    t.login = e,
    t.tabIndex = t.login.tabIndex,
    t.errorTxt = t.login.errorTxt,
    t.$root = $(".tabMainContent", t.login.$root),
    t.$tabTitle = $(".tabTitle", t.$root),
    t.$tabContents = $(".tabContents", t.$root),
    t.cnzz = {},
    t.cnzz.$userName = $("#cnzzUserName"),
    t.cnzz.$cnzzPwd = $("#cnzzPwd"),
    t.cnzz.$cnzzVcode = $("#cnzzVcode"),
    t.cnzz.token = t.login.token,
    t.cnzz.rurl = t.login.rurl,
    t.qj = {},
    t.qj.$compname = $("#qjCompany"),
    t.qj.$userName = $("#qjUserName"),
    t.qj.$qjPwd = $("#qjPwd"),
    t.qj.$qjVcode = $("#qjVcode"),
    t.qj.token = t.login.token,
    t.qj.rurl = t.login.rurl
}
function Logined(e) {
    var t = this;
    t.login = e,
    t.tabIndex = t.login.tabIndex,
    t.$root = t.login.$logined,
    t.init()
}
function ProjectName() {
    this.pNames = ["dplus"]
}
function Login() {
    var e = this;
    e.tabIndex = parseInt($("#tabIndex").val(), 10) || 1,
    e.customStyle = parseInt($("#customStyle").val(), 10) || 1,
    e.ptype = parseInt($("#ptype").val(), 10) || 1,
    e.pname = $("#pname").val() || "",
    e.userName = $("#userName").val(),
    e.companyName = $("#companyName").val(),
    e.protectedlist = $("#protectedlist").val(),
    e.rurl = $("#rurl").val(),
    e.referer = $("#referer").val(),
    e.token = $("#token").val(),
    e.$root = $("#loginRootContent"),
    e.$load = $(".load", e.$root),
    e.$logined = $(".logined", e.$root),
    e.projectName = new ProjectName,
    e.checkParam(),
    e.initErrorTip(),
    e.init()
}
Tab.prototype = {
    init: function() {
        var e = this,
        t = e.tabIndex,
        o = 13 === t || 14 === t || 24 === t;
        e.infoCnzz = "cnzz",
        e.infoQj = "qj",
        e.infoTaobao = "taobao",
        e.$root.show(),
        e.login.hideLoad(),
        o ? e.hideTab() : e.renderTitle()
    },
    hideTab: function() {
        var e = this,
        t = $(".tabContent", e.$tabContents),
        o = $(".tabCnzz", e.$tabContents),
        n = $(".tabQj", e.$tabContents),
        r = $(".tabTaobao", e.$tabContents);
        e.$root.show(),
        e.login.$root.addClass("noTab"),
        t.hide(),
        13 === e.tabIndex ? (o.show(), n.remove(), r.remove(), e.tabCnzz()) : 14 === e.tabIndex ? (n.show(), o.remove(), r.remove(), e.tabQj()) : 24 === e.tabIndex && (r.show(), o.remove(), n.remove(), e.tabTaobao())
    },
    renderTitle: function() {
        var e = this,
        t = (e.$root, []),
        o = e.tabIndex,
        n = e.$tabTitle,
        r = $(".tabContent", e.$tabContents);
        n.removeClass("tab1 tab2 tab3"),
        r.hide();
        var a = "站长会员",
        i = "全景会员",
        s = "淘宝会员",
        l = e.infoCnzz,
        c = e.infoQj,
        u = e.infoTaobao;
        10 === o ? (t.push('<li info="' + l + '" class="tabTitleLi">' + a + "</li>"), t.push('<li info="' + c + '" class="tabTitleLi lastLi">' + i + "</li>"), n.addClass("tab2")) : 11 === o ? (t.push('<li info="' + l + '" class="tabTitleLi">' + a + "</li>"), n.addClass("tab1"), r.eq(0).show()) : 12 === o ? (t.push('<li info="' + c + '" class="tabTitleLi">' + i + "</li>"), n.addClass("tab1"), r.eq(1).show()) : 20 === o ? (t.push('<li info="' + l + '" class="tabTitleLi">' + a + "</li>"), t.push('<li info="' + c + '" class="tabTitleLi">' + i + "</li>"), t.push('<li info="' + u + '" class="tabTitleLi lastLi">' + s + "</li>"), n.addClass("tab3")) : 21 === o ? (t.push('<li info="' + l + '" class="tabTitleLi">' + a + "</li>"), t.push('<li info="' + u + '" class="tabTitleLi lastLi">' + s + "</li>"), n.addClass("tab2")) : 22 === o ? (t.push('<li info="' + c + '" class="tabTitleLi">' + i + "</li>"), t.push('<li info="' + u + '" class="tabTitleLi lastLi">' + s + "</li>"), n.addClass("tab2")) : 23 === o && (t.push('<li info="' + u + '" class="tabTitleLi">' + s + "</li>"), n.addClass("tab1"), r.eq(2).show()),
        n.empty().append(t.join("")),
        e.bindTabTitleEvent(),
        e.tabContentEvent()
    },
    bindTabTitleEvent: function() {
        var e = this,
        t = e.$tabTitle,
        o = $(".tabContent", e.$tabContents),
        n = t.find("li");
        return 1 === n.length ? !1 : (n.off("click").on("click",
        function() {
            var t = $(this).attr("info");
            n.removeClass("selected"),
            $(this).addClass("selected"),
            o.hide(),
            t === e.infoCnzz ? o.eq(0).show() : t === e.infoQj ? o.eq(1).show() : t === e.infoTaobao && o.eq(2).show(),
            $(".errorContainer", e.$tabContents).removeClass("errorTip").removeClass("errorTipTwoLine"),
            $(".remuserArea", e.$tabContents).find(".remuser").removeClass("selected")
        }), n.eq(0).trigger("click"), void 0)
    },
    tabContentEvent: function() {
        var e = this,
        t = $(".tabContent", e.$tabContents);
        t.hasClass("tabCnzz") && e.tabCnzz(),
        t.hasClass("tabQj") && e.tabQj(),
        t.hasClass("tabTaobao") && e.tabTaobao()
    },
    renderProductSelect: function(e) {
        var t, o = this,
        n = $(".productArea", e).find(".pa"),
        r = n.find(".rc"),
        a = $(".productList", r),
        i = $(".txt", r),
        s = o.login.protectedlist,
        l = [];
        return 1 === o.login.ptype ? (n.parent().hide(), !1) : (n.parent().show(), (s || s.length > 0) && (t = $.parseJSON(s), t && t.length > 0 && ($.each(t,
        function(e, t) {
            l.push('<li class="pli" val="' + encodeURIComponent(t.url) + '"><span class="txt1">' + t.key + '</span> <span class="arrow1"></span></li>')
        }), a.empty().append(l.join("")), i.text(t[0].key).attr({
            val: encodeURIComponent(t[0].url)
        }), productSelect(r), o.$root.on("click",
        function(e) {
            0 === $(e.target).parents(".pa").length && a.hide()
        }))), void 0)
    },
    renderVcode: function(e, t) {
        var o = $(".vCodeArea", e);
        UA_Opt.Token = (new Date).getTime() + ":" + Math.random(),
        UA_Opt.reload && UA_Opt.reload(),
        $.post("main.php?c=useropt&a=login&ajax=module=checkcode", {
            compname: "",
            username: t.cnzz.$userName.val(),
            _u_token: t.cnzz.token,
            _ua: window.uavalue
        },
        function(e) {
            e && e.data && e.data.checkcode && e.data.checkcode.data && 1 === parseInt(e.data.checkcode.data, 10) ? o.hide() : (o.show(), vCodeInEvent(o, t, t.cnzz.$cnzzPwd, t.cnzz.$userName))
        })
    },
    qjRenderVcode: function(e, t) {
        var o = $(".vCodeArea", e);
        UA_Opt.Token = (new Date).getTime() + ":" + Math.random(),
        UA_Opt.reload && UA_Opt.reload(),
        $.post("main.php?c=useropt&a=login&ajax=module=checkcode", {
            compname: t.qj.$compname.val(),
            username: t.qj.$userName.val(),
            _u_token: t.qj.token,
            _ua: window.uavalue
        },
        function(e) {
            e && e.data && e.data.checkcode && e.data.checkcode.data && 1 === parseInt(e.data.checkcode.data, 10) ? o.hide() : (o.show(), vCodeInEvent(o, t, t.qj.$qjPwd, t.qj.$userName))
        })
    },
    tabCnzz: function() {
        var e = this,
        t = $(".tabCnzz", e.$tabContents),
        o = $(".userNameArea", t),
        n = $(".pwdArea", t),
        r = $(".submitArea", t),
        a = $(".remuserArea", t);
        if (uanmeInEvent(o, e, t), pwdInEvent(n, e, r), remuser(a), e.renderProductSelect(t), r.off().on({
            mouseover: function() {
                $(this).addClass("btnHover")
            },
            mouseout: function() {
                $(this).removeClass("btnHover")
            },
            click: function() {
                e.cnzzSubmit(e, t, r)
            }
        }), e.login.projectName.isFenxi(e.login.pname)) {
            var i = $(".pQuanjingTxt", t),
            s = $(".userNameQjTipArea", t);
            i.off().on("click",
            function() {
                "none" === s.css("display") ? s.show() : s.hide()
            })
        }
    },
    tabQj: function() {
        var e = this,
        t = $(".tabQj", e.$tabContents),
        o = $(".companyArea", t),
        n = $(".userNameArea", t),
        r = $(".pwdArea", t),
        a = $(".remuserArea", t),
        i = $(".submitArea", t);
        companyInEvent(o, e),
        qjUanmeInEvent(n, e),
        qjPwdInEvent(r, e, i),
        remuser(a),
        i.off().on({
            mouseover: function() {
                $(this).addClass("btnHover")
            },
            mouseout: function() {
                $(this).removeClass("btnHover")
            },
            click: function() {
                e.qjSubmit(e, t, i)
            }
        })
    },
    cnzzSubmit: function(e, t, o) {
        var n = e.cnzz.$userName,
        r = e.cnzz.$cnzzPwd,
        a = e.cnzz.$cnzzVcode;
        if (0 === $.trim(n.val()).length) return n.trigger("blur"),
        !1;
        if (0 === $.trim(r.val()).length || $.trim(r.val()).length < 6) return r.trigger("blur"),
        !1;
        if (0 === $.trim(a.val()).length && "none" !== $(".vCodeArea", t).css("display")) return a.trigger("blur"),
        !1;
        var i = $(".remuserArea", t).find(".remuser"),
        s = i.hasClass("selected") ? 1 : 0,
        l = "";
        2 === e.login.ptype ? (l = $(".productArea", e.$root).find(".rc").find(".txt").attr("val"), l = decodeURIComponent(l)) : l = e.login.rurl,
        o.addClass("btnActive"),
        o.text("正在登录..."),
        UA_Opt.Token = (new Date).getTime() + ":" + Math.random(),
        UA_Opt.reload && UA_Opt.reload(),
        $.post("main.php?c=useropt&a=login&ajax=module=check", {
            compname: "",
            username: n.val(),
            password: r.val(),
            number: a.val(),
            remuser: s,
            jumpurl: l,
            referer: e.login.referer,
            ptype: e.login.ptype,
            _u_token: e.cnzz.token,
            _ua: window.uavalue
        },
        function(t) {
            if (o.removeClass("btnActive"), o.text("登录"), t && t.data && t.data.check) {
                var n = parseInt(t.data.check.status, 10),
                r = parseInt(t.data.check.checkcode, 10),
                a = $(".vCodeArea", $(".tabCnzz", e.$tabContents));
                if (2 === r ? (a.show(), vCodeInEvent(a, e, e.cnzz.$cnzzPwd, e.cnzz.$userName), $("img.vCodeImg", a).trigger("click"), $("input.vCodeInput", a).val(""), $("input.vCodeInput", a).trigger("blur")) : a.hide(), 1 === n) return window.top.location.href = t.data.check.url,
                !0;
                2 === n || 21 === n || -3 === n ? e.errorTip(e.errorTxt.E5) : 20 === n ? e.errorTip(e.errorTxt.E6) : -8 === n ? e.errorTip(e.errorTxt.E7) : -9 === n ? e.errorTip(e.errorTxt.E8) : e.errorTip(e.errorTxt.E9)
            } else e.errorTip(e.errorTxt.E9)
        })
    },
    qjSubmit: function(e, t, o) {
        var n = e.qj.$userName,
        r = e.qj.$qjPwd,
        a = e.qj.$qjVcode,
        i = e.qj.$compname;
        if (0 === $.trim(i.val()).length) return i.trigger("blur"),
        !1;
        if (0 === $.trim(n.val()).length) return n.trigger("blur"),
        !1;
        if (0 === $.trim(r.val()).length || $.trim(r.val()).length < 6) return r.trigger("blur"),
        !1;
        if (0 === $.trim(a.val()).length && "none" !== $(".vCodeArea", t).css("display")) return a.trigger("blur"),
        !1;
        var s = $(".remuserArea", t).find(".remuser"),
        l = s.hasClass("selected") ? 1 : 0;
        o.addClass("btnActive"),
        o.text("正在登录..."),
        UA_Opt.Token = (new Date).getTime() + ":" + Math.random(),
        UA_Opt.reload && UA_Opt.reload(),
        $.post("main.php?c=useropt&a=login&ajax=module=check", {
            compname: i.val(),
            username: n.val(),
            password: r.val(),
            number: a.val(),
            remuser: l,
            referer: e.login.referer,
            jumpurl: e.login.rurl,
            _u_token: e.qj.token,
            _ua: window.uavalue
        },
        function(t) {
            if (o.removeClass("btnActive"), o.text("登录"), t && t.data && t.data.check) {
                var n = parseInt(t.data.check.status, 10),
                r = parseInt(t.data.check.checkcode, 10),
                a = $(".vCodeArea", $(".tabQj", e.$tabContents));
                if (2 === r ? (a.show(), vCodeInEvent(a, e, e.qj.$qjPwd, e.qj.$userName), $("img.vCodeImg", a).trigger("click"), $("input.vCodeInput", a).val(""), $("input.vCodeInput", a).trigger("blur")) : a.hide(), 1 === n) return window.top.location.href = t.data.check.url,
                !0;
                2 === n || 21 === n || 22 === n || -3 === n ? e.errorTip(e.errorTxt.E11) : 20 === n ? e.errorTip(e.errorTxt.E6) : -8 === n ? e.errorTip(e.errorTxt.E7) : -9 === n ? e.errorTip(e.errorTxt.E8) : e.errorTip(e.errorTxt.E9)
            } else e.errorTip(e.errorTxt.E9)
        })
    },
    errorTip: function(e) {
        var t = this,
        o = $(".errorContainer", t.$root),
        n = $(".txt", o);
        o.removeClass("errorTip").removeClass("errorTipTwoLine"),
        e = e || "",
        e && e.length > 0 ? (e.length > 20 ? o.addClass("errorTip errorTipTwoLine") : o.addClass("errorTip"), n.html(e)) : o.removeClass("errorTip").removeClass("errorTipTwoLine")
    },
    tabTaobao: function() {
        function e(e) {
            return encodeURIComponent(e)
        }
        var t = this,
        o = $(".tabTaobao", t.$tabContents),
        n = $(".taobaoIframe", o),
        r = "http://i.cnzz.com/main.php?c=useropt&a=tblogin&rurl=",
        a = "https://login.taobao.com/member/login.jhtml?style=mini&full_redirect=true&redirectURL=";
        t.renderProductSelect(o);
        var i = $(".pli", $(".productList", o));
        i.on({
            click: function() {
                var t = $(this).attr("val");
                n.attr({
                    src: a + e(r + t)
                }).show()
            }
        });
        var s = i.eq(0),
        l = s.attr("val");
        2 !== t.login.ptype && (l = e(t.login.rurl)),
        n.attr({
            src: a + e(r + l)
        }).show()
    }
},
Logined.prototype = {
    init: function() {
        var e = this,
        t = $(".title", e.$root);
        13 === e.tabIndex || 14 === e.tabIndex ? (e.login.$root.addClass("noTab"), t.hide()) : (e.login.$root.removeClass("noTab"), t.show()),
        e.renderUname(),
        e.renderProductSelect(e.$root),
        e.btnEvent()
    },
    renderUname: function() {
        var e = this,
        t = $(".userNameTxt", e.$root),
        o = "",
        n = e.login.userName,
        r = e.login.companyName,
        a = e.login.getAbbr;
        o = r && r.length > 0 ? a(r, 20) + '  <span class="uname">( ' + a(n, 20) + " )</span>": '<span class="uname">' + a(n, 30) + "</span>",
        t.html(o)
    },
    renderProductSelect: function(e) {
        var t = this,
        o = $(".productArea", e).find(".pa"),
        n = o.find(".rc"),
        r = $(".productList", n),
        a = $(".txt", n),
        i = t.login.protectedlist,
        s = [];
        if (1 === t.login.ptype || t.login.companyName || t.login.companyName.length > 0) return o.hide(),
        !1;
        if (o.show(), i || i.length > 0) {
            var l = $.parseJSON(i);
            l && l.length > 0 && ($.each(l,
            function(e, t) {
                s.push('<li class="pli" val="' + encodeURIComponent(t.url) + '"><span class="txt1">' + t.key + '</span> <span class="arrow1"></span></li>')
            }), r.empty().append(s.join("")), a.text(l[0].key).attr({
                val: encodeURIComponent(l[0].url)
            }), productSelect(n), t.$root.on("click",
            function(e) {
                0 === $(e.target).parents(".pa").length && r.hide()
            }))
        }
    },
    btnEvent: function() {
        var e = this,
        t = $(".qbtn", e.$root),
        o = $(".otherLoginBtn", e.$root);
        t.off().on({
            mouseover: function() {
                $(this).addClass("qbtnHover")
            },
            mouseout: function() {
                $(this).removeClass("qbtnHover")
            },
            click: function() {
                var t = e.login.rurl;
                2 !== e.login.ptype || e.login.companyName && 0 !== e.login.companyName.length || (t = $(".productArea", e.$root).find(".rc").find(".txt").attr("val"), t = decodeURIComponent(t)),
                $.post("main.php?c=useropt&a=login&ajax=module=protectedurl", {
                    jumpurl: t,
                    referer: e.login.referer
                },
                function(e) {
                    return e && e.data && e.data.protectedurl ? (window.top.location.href = e.data.protectedurl.url, !0) : void 0
                })
            }
        }),
        o.off().on({
            mouseover: function() {
                $(this).addClass("otherLoginBtnHover")
            },
            mouseout: function() {
                $(this).removeClass("otherLoginBtnHover")
            },
            click: function() {
                $(this).addClass("otherLoginBtnActive"),
                $.post("main.php?c=useropt&a=login&ajax=module=logout", {},
                function(e) {
                    return e && e.data && e.data.logout && e.data.logout.data && 1 === parseInt(e.data.logout.data, 10) ? (location.href = location.href, !0) : void 0
                })
            }
        })
    }
},
ProjectName.prototype = {
    isFenxi: function(e) {
        for (var t = this,
        o = !1,
        n = 0; n < t.pNames.length; n++) if (t.pNames[n] === e) {
            o = !0;
            break
        }
        return o
    }
},
Login.prototype = {
    checkParam: function() {
        var e = this,
        t = e.customStyle,
        o = e.ptype,
        n = e.tabIndex;
        1 !== t && 2 !== t && 3 !== t && (t = 1),
        1 !== o && 2 !== o && (o = 1),
        10 !== n && 11 !== n && 12 != n && 13 != n && 14 != n && 20 !== n && 21 !== n && 22 != n && 23 != n && 24 != n && (n = 10),
        e.customStyle = t,
        e.ptype = o,
        e.tabIndex = n
    },
    initErrorTip: function() {
        var e = this;
        e.errorTxt = {
            E1: "请输入用户名",
            E2: "请输入密码",
            E3: "请输入用户名和密码",
            E4: "请输入验证码！",
            E5: '您输入的用户名和密码不匹配，请重新输入。或者您<a class="rpwd" target="_blank" href="https://i.cnzz.com/main.php?c=findpwd&a=userinfo">忘记了密码?</a>',
            E6: "验证码错误！",
            E7: "您的登录次数过于频繁，请稍后重试",
            E8: "密码错误次数超过10次，请8小时后登录",
            E9: "系统出错，请重试",
            E10: "请输入公司名",
            E11: '您输入的用户名和密码不匹配，请重新输入。或者您<a class="rpwd" target="_blank" href="https://i.cnzz.com/main.php?c=findpwd&a=userinfo&usertype=quanjing">忘记了密码?</a>',
            E12: "密码长度应大于等于6位"
        }
    },
    init: function() {
        var e = this;
        e.$root.addClass("noTopBorder"),
        e.userName && e.userName.length > 0 ? e.logined() : e.logining()
    },
    logining: function() {
        var e = this,
        t = new Tab(e);
        t.init()
    },
    logined: function() {
        var e = this;
        e.hideLoad(),
        e.$logined.show(),
        new Logined(e)
    },
    showLoad: function() {
        this.$load.show()
    },
    hideLoad: function() {
        this.$load.hide()
    },
    getAbbr: function(e, t, o) {
        if (e) {
            for (var n = 0,
            r = "",
            a = 0; a < e.length; a++) n += e.charCodeAt(a) > 256 ? 2 : 1,
            t >= n && (r += e.charAt(a));
            return t >= n ? r: o ? r + " " + o: r + " ..."
        }
        return ""
    }
},
$(function() {
    new Login
});