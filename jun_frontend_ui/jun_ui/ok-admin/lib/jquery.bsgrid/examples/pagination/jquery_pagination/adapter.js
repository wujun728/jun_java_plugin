/**
 * jquery_pagination adapter for bsgrid.
 *
 * jQuery.bsgrid v1.37 by @Baishui2004
 * Copyright 2014 Apache v2 License
 * https://github.com/baishui2004/jquery.bsgrid
 */
/**
 * require common.js, grid.js.
 *
 * @author Baishui2004
 * @Date August 31, 2014
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
    $('#' + options.pagingOutTabId + ' td').append('<div id="' + options.pagingId + '"></div>');
};

$.fn.bsgrid.setPagingValues = function (options) {
    $('#' + options.pagingId).pagination(options.totalRows, {
        current_page: options.curPage - 1,
        num_display_entries: 5,
        num_edge_entries: 2,
        items_per_page: options.settings.pageSize,
        // link_to: "#",
        prev_text: $.bsgridLanguage.pagingToolbar.prevPage,
        next_text: $.bsgridLanguage.pagingToolbar.nextPage,
        // ellipse_text: "...",
        // prev_show_always: true,
        // next_show_always: true,
        // renderer: "defaultRenderer",
        // load_first_page: false,
        callback: function (page_index, jq) {
            $.fn.bsgrid.getGridObj(options.gridId).page(page_index + 1);
            return false;
        }
    });

    // page size select
    if (options.settings.pageSizeSelect) {
        $('#' + options.pagingId + '_pageSize').remove();
        $('#' + options.pagingId + ' .pagination').prepend('<select id="' + options.pagingId + '_pageSize' + '"></select>');
        var optionsSb = new StringBuilder();
        for (var i = 0; i < options.settings.pageSizeForGrid.length; i++) {
            var pageVal = options.settings.pageSizeForGrid[i];
            optionsSb.append('<option value="' + pageVal + '">' + pageVal + '</option>');
        }
        $('#' + options.pagingId + '_pageSize').html(optionsSb.toString()).val(options.settings.pageSize);
        // select change event
        $('#' + options.pagingId + '_pageSize').change(function () {
            options.settings.pageSize = parseInt($(this).val());
            $(this).trigger('blur');
            // if change pageSize, then page first
            if (options.curPage == 1) {
                $.fn.bsgrid.refreshPage(options);
            } else {
                $.fn.bsgrid.gotoPage(options, 1);
            }
        });
    }
};