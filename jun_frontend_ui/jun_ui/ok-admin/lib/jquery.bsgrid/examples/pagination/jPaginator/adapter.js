/**
 * jPaginator adapter for bsgrid.
 *
 * jQuery.bsgrid v1.37 by @Baishui2004
 * Copyright 2014 Apache v2 License
 * https://github.com/baishui2004/jquery.bsgrid
 */
/**
 * require common.js, grid.js.
 *
 * @author Baishui2004
 * @Date September 2, 2014
 */
$.fn.bsgrid.getCurPage = function (options) {
    return options.curPage;
};

$.fn.bsgrid.refreshPage = function (options) {
    $.fn.bsgrid.getGridObj(options.gridId).page($.fn.bsgrid.getCurPage(options));
};

$.fn.bsgrid.firstPage = function (options) {
    $.fn.bsgrid.getGridObj(options.gridId).page(1);
};

$.fn.bsgrid.prevPage = function (options) {
    var curPage = $.fn.bsgrid.getCurPage(options);
    if (curPage <= 1) {
        if (options.settings.pageIncorrectTurnAlert) {
            alert($.bsgridLanguage.isFirstPage);
        }
        return;
    }
    $.fn.bsgrid.getGridObj(options.gridId).page(curPage - 1);
};

$.fn.bsgrid.nextPage = function (options) {
    var curPage = $.fn.bsgrid.getCurPage(options);
    if (curPage >= options.totalPages) {
        if (options.settings.pageIncorrectTurnAlert) {
            alert($.bsgridLanguage.isLastPage);
        }
        return;
    }
    $.fn.bsgrid.getGridObj(options.gridId).page(curPage + 1);
};

$.fn.bsgrid.lastPage = function (options) {
    $.fn.bsgrid.getGridObj(options.gridId).page(options.totalPages);
};

$.fn.bsgrid.gotoPage = function (options, goPage) {
    if (goPage == undefined) {
        return;
    }
    if ($.trim(goPage) == '' || isNaN(goPage)) {
        if (options.settings.pageIncorrectTurnAlert) {
            alert($.bsgridLanguage.needInteger);
        }
    } else if (parseInt(goPage) < 1 || parseInt(goPage) > options.totalPages) {
        if (options.settings.pageIncorrectTurnAlert) {
            alert($.bsgridLanguage.needRange(1, options.totalPages));
        }
    } else {
        $.fn.bsgrid.getGridObj(options.gridId).page(goPage);
    }
};

$.fn.bsgrid.initPaging = function (options) {
    var pagingSb = new StringBuilder();
    pagingSb.append('<div id="' + options.pagingId + '" class="jPaginator-adapter">');
    pagingSb.append('<nav class="m_left"></nav><nav class="o_left"></nav>');
    pagingSb.append('<div class="paginator_p_wrap"><div class="paginator_p_bloc"></div></div>');
    pagingSb.append('<nav class="o_right"></nav><nav class="m_right"></nav>');
    pagingSb.append('<div class="paginator_slider" class="ui-slider ui-slider-horizontal ui-widget ui-widget-content ui-corner-all">'
        + '<a class="ui-slider-handle ui-state-default ui-corner-all" href="#"></a>'
        + '</div>');
    pagingSb.append('</div>');

    $('#' + options.pagingOutTabId).remove();
    $('#' + options.gridId).after(pagingSb.toString());
};

$.fn.bsgrid.setPagingValues = function (options) {
    $('#' + options.pagingId).jPaginator({
        nbPages: options.totalPages,
        selectedPage: options.curPage,
        overBtnLeft: '#' + options.pagingId + ' .o_left',
        overBtnRight: '#' + options.pagingId + ' .o_right',
        maxBtnLeft: '#' + options.pagingId + ' .m_left',
        maxBtnRight: '#' + options.pagingId + ' .m_right',
        minSlidesForSlider: 0,
        speed: 1,
        onPageClicked: function (a, num) {
            $.fn.bsgrid.getGridObj(options.gridId).page(num);
        }
    });
};