var ok_load_options = {
    time: 1000,
    content: "ok-admin..."
};

!function (content, options) {
    var load_config = localStorage.getItem("okConfig") || window.okConfig || {};

    if (typeof load_config == "string") {
        load_config = JSON.parse(load_config);
    }

    function templateFun(options) {
        return "<div class='ok-loading ${config.theme}'>" +
            "<div class='ball-loader'>" +
            "<span></span><span></span><span></span><span></span>" +
            "</div>" +
            "</div>";
    }

    function headerInit(content, options) {
        options = options || {};
        if (typeof content == "string") {
            options["content"] = content || ok_load_options.content;
        } else if (typeof content == "object") {
            options = content;
        }
        options.time = options.time || ok_load_options.time;
        options.content = options.content || ok_load_options.content;
        return options;
    }

    ok_load_options = headerInit(content, options);
    var template = templateFun(ok_load_options);
    document.writeln(template);
}();

var okLoading = {
    close: function (time, dom) {
        time = time || ok_load_options.time;
        dom = dom || document.getElementsByClassName("ok-loading")[0];
        var setTime1 = setTimeout(function () {
            clearTimeout(setTime1);
            dom.classList.add("close");
            var setTime2 = setTimeout(function () {
                clearTimeout(setTime2);
                // 删除当前节点
                dom.parentNode.removeChild(dom);
            }, 800);
        }, time);
    }
};
