(function(){try{var e=W("#longlong-frame").attr("data-citys").split(","),t=hao360.area.get().prov;e.contains(t)&&qboot.cookie.set("qihooToutiaoUser",1,{expires:5184e6})}catch(n){}})();(function(){function n(e){var n=e.getAttribute("data-href");if(n===null||n==="")n=e.href||t;return n}function r(e){var t=location.href.queryUrl(),n=qboot.encodeURIJson(t);n!==""&&(e+="&"+n),qboot.cookie.set("go3600",1,{expires:31536e6}),location.href=e}var e=W(".go-newversion"),t="http://www.3600.com/?f=hao360";e.click(function(){r(n(this))}),window.__goToNewVersion=r})();(function(){var e=window.location,t=function(){var t=e.href.queryUrl("ls");return Object.isString(t)?t.replace(/[^0-9a-z].*$/ig,""):""}(),n="//hao.360.cn/joy.html",r=function(e,t){var n=864e5,r=Math.floor(hao360.todayObj.getTime()/n),i="__netcafe",s=5e3,o="http://s.lianmeng.360.cn/api/hao/channel/"+e+"/"+i+"?t="+r,u=setTimeout(function(){s=0,LogHub.behavior("hao360cn_joy","netError")},s);window[i]=function(e){s&&(clearTimeout(u),t&&t(e))},loadJs(o)},i=function(e,t,n){var i=JSON.parse(qboot.cookie.get("netcafe"))||{};i.ls===e?n&&n(i.type==t):r(e,function(r){try{r.errno?LogHub.behavior("hao360cn_joy","apiError"):(qboot.cookie.set("netcafe",JSON.stringify({ls:e,type:r.type}),{expires:31536e6}),n&&n(r.type==t))}catch(i){n&&n(!1)}})};t&&i(t,1,function(t){isRedirect=qboot.cookie.get("netcafe_redirect"),e.href.indexOf(n)===-1&&t&&!isRedirect&&(window.location=n+e.search)})})();(function(){var e="seeDone";if(!hao360.mid||qboot.cookie.get(e))return;var t=(new AppData("api.hao.360.cn",HAO_CONFIG.search.r)).get("defaultEng");if(t=="so360"||t=="somulti")return;Math.random()<.6&&qboot.jsonp("http://guess.h.qhimg.com/index.php?c=index&a=search&mid="+hao360.mid,function(t){+t===1&&((new AppData("api.hao.360.cn",HAO_CONFIG.search.r)).set("defaultEng","somulti"),LogHub.behavior("hao360_see","mk_change")),qboot.cookie.set(e,1,{expires:2592e6})})})();(function(){var e=hao360.mid;e&&!qboot.cookie.get("logwtb")&&(logSender("http://s.so.360.cn/daohang/m.gif?dhid="+e),logSender("http://reg.hao.360.cn/?dhid="+e),qboot.cookie.set("logwtb",1))})();(function(){W("#imgLogo,#themeLogo,#flashLogo a").click(function(e){var t=this.getAttribute("data-themeKey");if(t==null)return;e.preventDefault(),t==""?W("#toolbar-themetrigger").click():themeView.action.fire("theme:change",{themeKey:t})})})();(function(){var e=qboot.cookie.get("browser360"),t=W("#browser360-tips"),n=W("#browser360-tmpl"),r=W("#doc"),i=t.attr("data-sign"),s=navigator.userAgent,o=Browser.ie&&!hao360.is360se6()&&!hao360.is360se(),u="blank";if(s.indexOf("Windows NT 5.1")>-1||s.indexOf("Windows NT 5.2")>-1||s.indexOf("Windows XP")>-1)u="xp";if(s.indexOf("Windows NT 6.1")>-1||s.indexOf("Windows 7")>-1)u="win7";var a=function(){var e=function(){return function(){var e=browser360_config[u];r.css("top","42px").css("position","relative").css("margin-bottom","42px"),t.html(n.html().format(e.content,e.href,e.button)).show()}}(),i=function(){r.css("top","0").css("position","static").css("margin-bottom","0"),t.hide(),qboot.cookie.set("browser360",1,{expires:15552e7})},s=function(){W("#browser360-tips").delegate("#browser360-close","click",i).delegate("#browser360-btn","click",i)};return{init:function(){e(),s()}}}();o&&browser360_config[u]&&!e&&i=="on"&&a.init()})();