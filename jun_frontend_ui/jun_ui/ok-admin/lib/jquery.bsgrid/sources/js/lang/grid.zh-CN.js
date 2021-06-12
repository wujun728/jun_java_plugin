/**
 * jQuery.bsgrid v1.38 by @Baishui2004
 * Copyright 2014 Apache v2 License
 * https://github.com/baishui2004/jquery.bsgrid
 */
/**
 * @author Baishui2004
 * @Date August 31, 2014
 */
(function ($) {

    $.bsgridLanguage = {
        isFirstPage: '已经是第一页！',
        isLastPage: '已经是最后一页！',
        needInteger: '请输入数字！',
        needRange: function (start, end) {
            return '请输入一个在' + start + '到' + end + '之间的数字！';
        },
        errorForRequestData: '请求数据失败！',
        errorForSendOrRequestData: '发送或请求数据失败！',
        noPagingation: function (noPagingationId) {
            return '共:&nbsp;<span id="' + noPagingationId + '"></span>';
        },
        pagingToolbar: {
            pageSizeDisplay: function (pageSizeId, ifLittle) {
                var html = '';
                if (!ifLittle) {
                    html += '每页显示:';
                }
                return html + '&nbsp;<select id="' + pageSizeId + '"></select>';
            },
            currentDisplayRows: function (startRowId, endRowId, ifLittle) {
                var html = '';
                if (!ifLittle) {
                    html += '当前显示:';
                }
                return html + '&nbsp;<span id="' + startRowId + '"></span>&nbsp;-&nbsp;<span id="' + endRowId + '"></span>';
            },
            totalRows: function (totalRowsId) {
                return '共:&nbsp;<span id="' + totalRowsId + '"></span>';
            },
            currentDisplayPageAndTotalPages: function (curPageId, totalPagesId) {
                return '<div><span id="' + curPageId + '"></span>&nbsp;/&nbsp;<span id="' + totalPagesId + '"></span></div>';
            },
            firstPage: '首&nbsp;页',
            prevPage: '上一页',
            nextPage: '下一页',
            lastPage: '末&nbsp;页',
            gotoPage: '跳&nbsp;转',
            refreshPage: '刷&nbsp;新'
        },
        loadingDataMessage: '正在加载数据，请稍候......',
        noDataToDisplay: '没有数据可以用于显示。'
    };

})(jQuery);