/**
 * jQuery.bsgrid v1.38 by @Baishui2004
 * Copyright 2014 Apache v2 License
 * https://github.com/baishui2004/jquery.bsgrid
 */
/**
 * require common.js, util.js.
 *
 * @author Baishui2004
 * @Date July 1, 2014
 */
(function ($) {

    $.bsgrid_export = {

        // defaults settings
        defaults: {
            url: '', // export url
            exportFileName: 'export', // export file name, not contains file suffix
            colsProperties: {
                width: 100,
                align: 'center',
                exportAttr: 'w_export',
                indexAttr: 'w_index',
                widthAttr: 'width',
                alignAttr: 'w_align'
            },
            colWidthPercentmultiplier: 14, // if set column width N%, then column width will reset N*14
            // request params name
            requestParamsName: {
                exportFileName: 'exportFileName',
                colNames: 'dataNames',
                colIndexs: 'dataIndexs',
                colWidths: 'dataLengths',
                colAligns: 'dataAligns'
            }
        },

        /**
         * do export.
         *
         * @param exportCols
         * @param exportParams A Search String, A Object or A jquery serialize Array
         * @param settings
         */
        doExport: function (exportCols, exportParams, settings) {
            if (exportParams == undefined) {
                exportParams = {};
            }

            var exportSettings = {};
            if (settings == undefined) {
                settings = {};
            }
            $.extend(true, exportSettings, $.bsgrid_export.defaults, settings);

            var colNames = '', colIndexs = '', colWidths = '', colAligns = '';
            for (var i = 0; i < exportCols.length; i++) {
                if ($.trim(exportCols.eq(i).attr(exportSettings.colsProperties.exportAttr)) != 'false') {
                    // column name, get form column's text(), use jquery
                    colNames = colNames + ',' + $.trim(exportCols.eq(i).text());
                    colIndexs = colIndexs + ',' + $.trim(exportCols.eq(i).attr(exportSettings.colsProperties.indexAttr));

                    var colWidthStr = $.trim(exportCols.eq(i).attr(exportSettings.colsProperties.widthAttr)).toLocaleLowerCase();
                    var colWidth = exportSettings.colsProperties.width;
                    if (isNaN(colWidthStr)) {
                        if (colWidthStr.endWith('px')) {
                            colWidth = parseInt(colWidthStr.replace('px', ''));
                        } else if (colWidthStr.endWith('%')) {
                            colWidthStr = colWidthStr.replace('%', '');
                            if (!isNaN(colWidthStr)) {
                                colWidth = exportSettings.colWidthPercentmultiplier * parseInt(colWidthStr);
                            }
                        }
                    }
                    colWidths = colWidths + ',' + colWidth;

                    var colAlign = $.trim(exportCols.eq(i).attr(exportSettings.colsProperties.alignAttr));
                    if (colAlign == '') {
                        colAlign = exportSettings.colsProperties.align;
                    }
                    colAligns = colAligns + ',' + colAlign;
                }
            }

            var exportParamsStr;
            if ((typeof exportParams).toLowerCase() == 'string' || exportParams instanceof String) {
                exportParamsStr = (exportParams.startWith('&') ? exportParams.substring(1) : exportParams);
            } else {
                exportParamsStr = $.bsgrid.param(exportParams, true);
            }
            document.location.href = exportSettings.url + (exportSettings.url.indexOf('?') < 0 ? '?' : '&')
                + exportSettings.requestParamsName.exportFileName + '=' + encodeURIComponent(encodeURIComponent(exportSettings.exportFileName))
                + '&' + exportSettings.requestParamsName.colNames + '=' + encodeURIComponent(encodeURIComponent(colNames.substring(1)))
                + '&' + exportSettings.requestParamsName.colIndexs + '=' + colIndexs.substring(1)
                + '&' + exportSettings.requestParamsName.colWidths + '=' + colWidths.substring(1)
                + '&' + exportSettings.requestParamsName.colAligns + '=' + colAligns.substring(1)
                + (exportParamsStr.length == 0 ? '' : ('&' + exportParamsStr));
        }

    };

})(jQuery);