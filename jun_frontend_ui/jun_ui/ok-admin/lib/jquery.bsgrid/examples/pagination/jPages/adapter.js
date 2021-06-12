/**
 * jPages adapter for bsgrid.
 *
 * jQuery.bsgrid v1.37 by @Baishui2004
 * Copyright 2014 Apache v2 License
 * https://github.com/baishui2004/jquery.bsgrid
 */
/**
 * require common.js, grid.js.
 *
 * @author Baishui2004
 * @Date September 1, 2014
 */
$.fn.bsgrid.getCurPage = function (options) {
    return options.curPage;
};

$.fn.bsgrid.refreshPage = function (options) {
    $.fn.bsgrid.getGridObj(options.gridId).page($.fn.bsgrid.getCurPage(options));
};

$.fn.bsgrid.firstPage = function (options) {
    $('#' + options.pagingId).jPages(1);
};

$.fn.bsgrid.prevPage = function (options) {
    var curPage = $.fn.bsgrid.getCurPage(options);
    if (curPage <= 1) {
        if (options.settings.pageIncorrectTurnAlert) {
            alert($.bsgridLanguage.isFirstPage);
        }
        return;
    }
    $('#' + options.pagingId).jPages(curPage - 1);
};

$.fn.bsgrid.nextPage = function (options) {
    var curPage = $.fn.bsgrid.getCurPage(options);
    if (curPage >= options.totalPages) {
        if (options.settings.pageIncorrectTurnAlert) {
            alert($.bsgridLanguage.isLastPage);
        }
        return;
    }
    $('#' + options.pagingId).jPages(curPage + 1);
};

$.fn.bsgrid.lastPage = function (options) {
    $('#' + options.pagingId).jPages(options.totalPages);
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
        $('#' + options.pagingId).jPages(goPage);
    }
};

$.fn.bsgrid.initPaging = function (options) {
    $('#' + options.pagingOutTabId).after('<ul id="' + options.pagingId + '-ul" style="width: 0; height: 0; overflow: hidden;"></ul>');
    $('#' + options.pagingOutTabId + ' td').append('<div id="' + options.pagingId + '" class="holder" style="margin-top: 0px;"></div>');
};

$.fn.bsgrid.setPagingValues = function (options) {
    var ulInnerHtml = new StringBuilder();
    for (var i = 0; i < options.totalRows; i++) {
        ulInnerHtml.append('<li></li>');
    }
    $('#' + options.pagingId + '-ul').html(ulInnerHtml.toString());
    $('#' + options.pagingId).data('bsgrid-curPage', options.curPage);
    $('#' + options.pagingId).jPages({
        containerID: options.pagingId + '-ul',
        perPage: options.settings.pageSize,
        startPage: options.curPage,
        first: $.bsgridLanguage.pagingToolbar.firstPage, // false
        previous: $.bsgridLanguage.pagingToolbar.prevPage, // "← previous"
        next: $.bsgridLanguage.pagingToolbar.nextPage, // "next →"
        last: $.bsgridLanguage.pagingToolbar.lastPage, // false
        midRange: 6,
        startRange: 2,
        endRange: 2,
        callback: function (pages, items) { // function(pages, items) { }
            if (parseInt($.trim($('#' + options.pagingId).data('bsgrid-curPage'))) != pages.current) {
                $.fn.bsgrid.getGridObj(options.gridId).page(pages.current);
            }
        }
    });

    // page size select
    if (options.settings.pageSizeSelect) {
        $('#' + options.pagingId + '_pageSize').remove();
        $('#' + options.pagingId).prepend('<select id="' + options.pagingId + '_pageSize' + '"></select>');
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