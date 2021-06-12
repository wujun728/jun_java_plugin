/**
 * jqPagination adapter for bsgrid.
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
    return $('#' + options.pagingId + ' input').data('current-page');
};

$.fn.bsgrid.refreshPage = function (options) {
    $.fn.bsgrid.getGridObj(options.gridId).page($.fn.bsgrid.getCurPage(options));
};

$.fn.bsgrid.firstPage = function (options) {
    $('#' + options.pagingId + ' a.first').click();
};

$.fn.bsgrid.prevPage = function (options) {
    $('#' + options.pagingId + ' a.previous').click();
};

$.fn.bsgrid.nextPage = function (options) {
    $('#' + options.pagingId + ' a.next').click();
};

$.fn.bsgrid.lastPage = function (options) {
    $('#' + options.pagingId + ' a.last').click();
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
        $.fn.bsgrid.getGridObj(options.gridId).page(parseInt(goPage));
    }
};

$.fn.bsgrid.initPaging = function (options) {
    var pagingSb = new StringBuilder();
    pagingSb.append('<div id="' + options.pagingId + '" class="pagination">');
    pagingSb.append('<a href="#" class="first" data-action="first">&laquo;</a>');
    pagingSb.append('<a href="#" class="previous" data-action="previous">&lsaquo;</a>');
    pagingSb.append('<input type="text" readonly="readonly" data-max-page="" />');
    pagingSb.append('<a href="#" class="next" data-action="next">&rsaquo;</a>');
    pagingSb.append('<a href="#" class="last" data-action="last">&raquo;</a>');
    pagingSb.append('</div>');
    $('#' + options.pagingOutTabId + ' td').append(pagingSb.toString());

    $('#' + options.pagingId).jqPagination({
        current_page: 1,
        link_string: '#',
        // max_page: null,
        // page_string: 'Page {current_page} of {max_page}',
        /**
         * The paged callback is called when a valid page request has been made, the page variable simply contains the page requested.
         */
        paged: function (page) {
            // do something with the page variable
            $.fn.bsgrid.getGridObj(options.gridId).page(parseInt(page));
        }
    });
};

$.fn.bsgrid.setPagingValues = function (options) {
    var curPage = options.curPage;
    var totalRows = options.totalRows;
    var totalPages = parseInt(totalRows / options.settings.pageSize);
    totalPages = parseInt((totalRows % options.settings.pageSize == 0) ? totalPages : totalPages + 1);

    $('#' + options.pagingId).data('jqPagination').options.current_page = curPage;
    $('#' + options.pagingId).data('jqPagination').options.max_page = totalPages;
    var page_string = $('#' + options.pagingId).data('jqPagination').options.page_string;
    page_string = page_string.replace('{current_page}', curPage).replace('{max_page}', totalPages + '');
    $('#' + options.pagingId + ' input').data('current-page', curPage).data('max-page', totalPages).val(page_string);
    $('#' + options.pagingId).data('jqPagination').setLinks(curPage);

    // page size select
    if (options.settings.pageSizeSelect) {
        $('#' + options.pagingId + '_pageSize').remove();
        $('#' + options.pagingId).prepend('<select id="' + options.pagingId + '_pageSize' + '" style="margin: 1px 0; border-width: 0;"></select>');
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