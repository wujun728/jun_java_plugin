(function ($) {
    "use strict";

    var formats = {
        "date": "yyyy-MM-dd",
        "datetime": "yyyy-MM-dd HH:mm:ss",
        "time": "HH:mm:ss",
        "year": "yyyy",
        "month": "yyyy-MM"
    };

    var btn_clear = "clear",
        btn_now = "now",
        btn_confirm = "confirm";

    $(function() {
        //初始化laydate
        $("[data-toggle='laydate']").each(function () {
            var type = $(this).data("date-type") || "date";
            var range = $(this).data("date-range") || false;
            var isInitialNow = $(this).data("date-now") || false;
            var initialDate = ($(this).is("input") ? $(this).val() : $(this).data("date")) || (isInitialNow ? new Date() : "");
            var min = $(this).data("date-min") || 86400000;
            var max = $(this).data("date-max") || 4073558400000;
            var format = $(this).data("date-format") || formats[type];
            var calendar = $(this).data("date-holiday") || false;
            var mark = $(this).data("date-day") || "{}";
            var showClear = ($(this).data("date-show-clear") === undefined || $(this).data("date-show-clear")) ? true : false;
            var showNow = ($(this).data("date-show-now") === undefined || $(this).data("date-show-now")) ? true : false;
            var showConfirm = $(this).data("date-show-confirm") === undefined || $(this).data("date-show-confirm") ? true : false;
            var showBottom = ($(this).data("date-show-bottom") === undefined || $(this).data("date-show-bottom")) ? ((showClear || showNow || showConfirm) ? true : false) : false;

            var btns = [btn_clear, btn_now, btn_confirm];

            if (!showClear) btns.splice($.inArray(btn_clear, btns), 1);
            if (!showNow) btns.splice($.inArray(btn_now, btns), 1);
            if (!showConfirm) btns.splice($.inArray(btn_confirm, btns), 1);

            laydate.render({
                elem: this,
                value: initialDate,
                // trigger: "click",
                type: type,
                theme: "bootstrap",
                range: range,
                min: min,
                max: max,
                format: format,
                calendar: calendar,
                mark: mark,
                showBottom: showBottom,
                btns: btns
            });
        });
    })

})(window.jQuery || jQuery);