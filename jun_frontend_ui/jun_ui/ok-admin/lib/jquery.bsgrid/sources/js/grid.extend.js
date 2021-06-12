/**
 * jQuery.bsgrid v1.38 by @Baishui2004
 * Copyright 2014 Apache v2 License
 * https://github.com/baishui2004/jquery.bsgrid
 */
/**
 * require common.js, util.js, grid.js.
 *
 * @author Baishui2004
 * @Date May 18, 2015
 */
(function ($) {

    // extend settings
    $.fn.bsgrid.defaults.extend.settings = {
        supportGridEdit: false, // if support extend grid edit
        supportGridEditTriggerEvent: 'rowClick', // values: ''(means no need Trigger), 'rowClick', 'rowDoubleClick', 'cellClick', 'cellDoubleClick'
        supportColumnMove: false, // if support extend column move
        searchConditionsContainerId: '', // simple search conditions's container id
        fixedGridHeader: false, // fixed grid header, auto height scroll
        fixedGridHeight: '320px', // fixed grid height, auto scroll
        gridEditConfigs: {
            text: {
                build: function (edit, value, record, rowIndex, colIndex, tdObj, trObj, options) {
                    return value + '<input class="' + 'bsgrid_editgrid_edit' + '" type="' + edit + '" value="' + value + '"/>';
                },
                val: function (formObj) {
                    return formObj.val();
                }
            },
            checkbox: {
                build: function (edit, value, record, rowIndex, colIndex, tdObj, trObj, options) {
                    return value + '<input class="' + 'bsgrid_editgrid_checkbox' + '" type="' + edit + '" value="' + value + '"/>';
                },
                val: function (formObj) {
                    return formObj.val();
                }
            },
            textarea: {
                build: function (edit, value, record, rowIndex, colIndex, tdObj, trObj, options) {
                    return value + '<textarea class="bsgrid_editgrid_edit">' + value + '</textarea>';
                },
                val: function (formObj) {
                    return formObj.val();
                }
            }
        }
    };
    $.fn.bsgrid.defaults.extend.settings.gridEditConfigs.hidden = $.fn.bsgrid.defaults.extend.settings.gridEditConfigs.text;
    $.fn.bsgrid.defaults.extend.settings.gridEditConfigs.password = $.fn.bsgrid.defaults.extend.settings.gridEditConfigs.text;
    $.fn.bsgrid.defaults.extend.settings.gridEditConfigs.radio = $.fn.bsgrid.defaults.extend.settings.gridEditConfigs.text;
    $.fn.bsgrid.defaults.extend.settings.gridEditConfigs.button = $.fn.bsgrid.defaults.extend.settings.gridEditConfigs.text;

    // config properties's name
    $.extend(true, $.fn.bsgrid.defaults.colsProperties, {
        lineNumberAttr: 'w_num', // line number, value: line, total_line
        checkAttr: 'w_check', // value: true
        editAttr: 'w_edit', // grid edit forms' values: text, hidden, password, radio, button, checkbox, textarea
        aggAttr: 'w_agg' // aggregation, values: count, countNotNone, sum, avg, max, min, concat
    });

    // custom cell edit events(jquery event): change, click, dblclick, focus ......
    $.fn.bsgrid.defaults.event.customCellEditEvents = {}; // method params: formObj, record, rowIndex, colIndex, tdObj, trObj, options

    $.fn.bsgrid.extendInitGrid = {}; // extend init grid
    $.fn.bsgrid.extendBeforeRenderGrid = {}; // extend before render grid
    $.fn.bsgrid.extendRenderPerRow = {}; // extend render per row
    $.fn.bsgrid.extendRenderPerColumn = {}; // extend render per column
    $.fn.bsgrid.extendAfterRenderGrid = {}; // extend after render grid
    $.fn.bsgrid.extendOtherMethods = {}; // extend other methods


    /*************** extend init grid start ***************/
    $.fn.bsgrid.extendInitGrid.initGridExtendOptions = function (gridId, options) {
        var columnsModel = options.columnsModel;
        var colsProperties = options.settings.colsProperties;
        $.fn.bsgrid.getGridHeaderObject(options).each(function (i) {
            columnsModel[i].lineNumber = $.trim($(this).attr(colsProperties.lineNumberAttr));
            columnsModel[i].check = $.trim($(this).attr(colsProperties.checkAttr));
            columnsModel[i].edit = $.trim($(this).attr(colsProperties.editAttr));
        });

        if ($('#' + options.gridId + ' tfoot tr td[' + colsProperties.aggAttr + '!=\'\']').length != 0) {
            $('#' + options.gridId + ' tfoot tr td').each(function (i) {
                columnsModel[i].aggName = '';
                columnsModel[i].aggIndex = '';
                var aggInfo = $.trim($(this).attr(colsProperties.aggAttr));
                if (aggInfo.length != 0) {
                    var aggInfoArray = aggInfo.split(',');
                    columnsModel[i].aggName = aggInfoArray[0].toLocaleLowerCase();
                    columnsModel[i].aggIndex = aggInfoArray.length > 1 ? aggInfoArray[1] : '';
                }
            });
        }

        if ($.fn.bsgrid.getGridHeaderObject(options).filter('[' + colsProperties.lineNumberAttr + '$=\'line\']').length != 0) {
            options.settings.extend.afterRenderGridMethods.renderLineNumber = $.fn.bsgrid.extendAfterRenderGrid.renderLineNumber;
        }
        if ($.fn.bsgrid.getGridHeaderObject(options).filter('[' + colsProperties.checkAttr + '=\'true\']').length != 0) {
            options.settings.extend.initGridMethods.initGridCheck = $.fn.bsgrid.extendInitGrid.initGridCheck;
            options.settings.extend.renderPerColumnMethods.renderCheck = $.fn.bsgrid.extendRenderPerColumn.renderCheck;
            options.settings.extend.afterRenderGridMethods.addCheckChangedEvent = $.fn.bsgrid.extendAfterRenderGrid.addCheckChangedEvent;
        }
        if (options.settings.extend.settings.supportGridEdit) {
            options.settings.extend.renderPerColumnMethods.renderForm = $.fn.bsgrid.extendRenderPerColumn.renderForm;
            options.settings.extend.afterRenderGridMethods.addGridEditEvent = $.fn.bsgrid.extendAfterRenderGrid.addGridEditEvent;
            options.settings.extend.afterRenderGridMethods.setOptionsFirstRowClone = $.fn.bsgrid.extendAfterRenderGrid.setOptionsFirstRowClone;
            var gridObj = $.fn.bsgrid.getGridObj(gridId);
            gridObj.activeGridEditMode = function () {
                return $.fn.bsgrid.defaults.extend.activeGridEditMode(options);
            };
            gridObj.getChangedRowsIndexs = function () {
                return $.fn.bsgrid.defaults.extend.getChangedRowsIndexs(options);
            };
            gridObj.getChangedRowsOldRecords = function () {
                return $.fn.bsgrid.defaults.extend.getChangedRowsOldRecords(options);
            };
            gridObj.getRowsChangedColumnsValue = function () {
                return $.fn.bsgrid.defaults.extend.getRowsChangedColumnsValue(options);
            };
            gridObj.deleteRow = function (row) {
                $.fn.bsgrid.defaults.extend.deleteRow(row, options);
            };
            gridObj.addNewEditRow = function () {
                $.fn.bsgrid.defaults.extend.addNewEditRow(options);
            };
        }
        if (options.settings.extend.settings.supportColumnMove) {
            options.settings.extend.initGridMethods.initColumnMove = $.fn.bsgrid.extendInitGrid.initColumnMove;
        }
        if (options.settings.extend.settings.fixedGridHeader) {
            options.settings.extend.initGridMethods.initFixedHeader = $.fn.bsgrid.extendOtherMethods.initFixedHeader;
            options.settings.extend.afterRenderGridMethods.fixedHeader = function (parseSuccess, gridData, options) {
                $.fn.bsgrid.extendOtherMethods.fixedHeader(false, options);
            };
        }
        if ($.trim(options.settings.extend.settings.searchConditionsContainerId) != '') {
            options.settings.extend.initGridMethods.initSearchConditions = $.fn.bsgrid.extendInitGrid.initSearchConditions;
        }
        if ($('#' + options.gridId + ' tfoot td[' + colsProperties.aggAttr + '!=\'\']').length != 0) {
            options.settings.extend.afterRenderGridMethods.aggregation = $.fn.bsgrid.extendAfterRenderGrid.aggregation;
        }
    };

    // init grid check
    $.fn.bsgrid.extendInitGrid.initGridCheck = function (gridId, options) {
        $.fn.bsgrid.getGridHeaderObject(options).each(function (hi) {
            if (options.columnsModel[hi].check == 'true') {
                if ($.trim($(this).html()) == '') {
                    $(this).html('<input class="bsgrid_editgrid_check" type="checkbox"/>');
                }
                $(this).find('input[type=checkbox]').change(function () {
                    var checked = $.bsgrid.adaptAttrOrProp($(this), 'checked') ? true : false;
                    $.bsgrid.adaptAttrOrProp($.fn.bsgrid.getRows(options).find('td:nth-child(' + (hi + 1) + ')>input[type=checkbox]'), 'checked', checked);
                });
            }
        });

        var gridObj = $.fn.bsgrid.getGridObj(gridId);
        gridObj.getCheckedRowsIndexs = function () {
            return $.fn.bsgrid.defaults.extend.getCheckedRowsIndexs(options);
        };
        gridObj.getCheckedRowsRecords = function () {
            return $.fn.bsgrid.defaults.extend.getCheckedRowsRecords(options);
        };
        gridObj.getCheckedValues = function (index) {
            return $.fn.bsgrid.defaults.extend.getCheckedValues(index, options);
        };
    };

    // init search conditions
    $.fn.bsgrid.extendInitGrid.initSearchConditions = function (gridId, options) {
        var conditionsHtml = new StringBuilder();
        conditionsHtml.append('<select class="bsgrid_conditions_select">');
        var params = {};
        $.fn.bsgrid.getGridHeaderObject(options).each(function (i) {
            var index = options.columnsModel[i].index;
            var text = $.trim($(this).text());
            if (index != '' && text != '' && $.trim(params[index]) == '') {
                params[index] = text;
            }
        });
        for (var key in params) {
            conditionsHtml.append('<option value="' + key + '">' + params[key] + '</option>');
        }
        conditionsHtml.append('</select>');
        conditionsHtml.append('&nbsp;');
        conditionsHtml.append('<input type="text" class="bsgrid_conditions_input" />');
        $('#' + options.settings.extend.settings.searchConditionsContainerId).html(conditionsHtml.toString());
        $('#' + options.settings.extend.settings.searchConditionsContainerId + ' select.bsgrid_conditions_select').change(function () {
            $(this).next('input.bsgrid_conditions_input').attr('name', $(this).val());
        }).trigger('change');
    };

    // init column move
    $.fn.bsgrid.extendInitGrid.initColumnMove = function (gridId, options) {
        if ($('#' + options.gridId + ' thead tr').length != 1) {
            return;
        }
        $('#' + options.gridId).css({'table-layout': 'fixed'});
        var headObj = $.fn.bsgrid.getGridHeaderObject(options);
        var headLen = headObj.length;
        headObj.each(function (i) {
            var obj = this;

            // disable select text when mouse moving
            $(obj).bind('selectstart', function () { // IE/Safari/Chrome
                return false;
            });
            $(obj).css('-moz-user-select', 'none'); // Firefox/Opera

            $(obj).mousedown(function () {
                bindDownData(obj, i, headLen);
            });
            $(obj).mousemove(function (e) {
                e = e || event;
                var left = $(obj).offset().left;
                var nObj = 0, nLeft = 0;
                if (i != headLen - 1) {
                    nObj = $(obj).next();
                    nLeft = nObj.offset().left;
                }
                var mObj = obj;
                if (i != headLen - 1 && e.clientX - nLeft > -10) {
                    mObj = nObj;
                }
                if ((i != 0 && e.clientX - left < 10) || (i != headLen - 1 && e.clientX - nLeft > -10)) {
                    $(obj).css({'cursor': 'e-resize'});
                    if ($.trim($(obj).data('ex_mousedown')) != 'mousedown') {
                        return;
                    }

                    var mWidth = $(mObj).width();
                    var newMWidth = mWidth - e.clientX + $(mObj).offset().left;
                    var preMWidth = $(mObj).prev().width();
                    var preNewMWidth = preMWidth + e.clientX - $(mObj).offset().left;
                    if (parseInt(newMWidth) > 19 && parseInt(preNewMWidth) > 19) {
                        $(mObj).width(newMWidth).prev().width(preNewMWidth);
                    }
                } else {
                    $(mObj).css({'cursor': 'default'});
                    releaseDownData(obj, i, headLen);
                }
            });
            $(obj).mouseup(function () {
                releaseDownData(obj, i, headLen);
            });
            $(obj).mouseout(function (e) {
                e = e || event;
                var objOffect = $(obj).offset();
                if (objOffect.top > e.clientY || objOffect.top + $(obj).height() < e.clientY) {
                    releaseDownData(obj, i, headLen);
                }
            });

            function bindDownData(obj, i, headLen) {
                if (i != 0) {
                    $(obj).prev().data('ex_mousedown', 'mousedown');
                }
                $(obj).data('ex_mousedown', 'mousedown');
                if (i != headLen - 1) {
                    $(obj).next().data('ex_mousedown', 'mousedown');
                }
            }

            function releaseDownData(obj, i, headLen) {
                if (i != 0) {
                    $(obj).prev().data('ex_mousedown', '');
                }
                $(obj).data('ex_mousedown', '');
                if (i != headLen - 1) {
                    $(obj).next().data('ex_mousedown', '');
                }
            }
        });
    };
    /*************** extend init grid end ***************/


    /*************** extend render per column start ***************/
        // render checkbox to check rows
    $.fn.bsgrid.extendRenderPerColumn.renderCheck = function (record, rowIndex, colIndex, tdObj, trObj, options) {
        var columnModel = options.columnsModel[colIndex];
        if (columnModel.check == 'true') {
            var value = $.fn.bsgrid.getRecordIndexValue(record, columnModel.index, options);
            return '<input class="' + 'bsgrid_editgrid_check' + '" type="checkbox" value="' + value + '"/>';
        }
        return false;
    };

    // render form methods: text, hidden, password, radio, button, checkbox, textarea
    $.fn.bsgrid.extendRenderPerColumn.renderForm = function (record, rowIndex, colIndex, tdObj, trObj, options) {
        var columnModel = options.columnsModel[colIndex];
        var edit = columnModel.edit;
        var value = $.fn.bsgrid.getRecordIndexValue(record, columnModel.index, options);
        var tdHtml = '&nbsp;';
        if (edit in options.settings.extend.settings.gridEditConfigs) {
            tdHtml = options.settings.extend.settings.gridEditConfigs[edit].build(edit, value, record, rowIndex, colIndex, tdObj, trObj, options);
        } else {
            return false;
        }
        tdObj.html(tdHtml);
        tdObj.find(':input').addClass('bsgrid_editgrid_hidden');
        for (var key in options.settings.event.customCellEditEvents) {
            tdObj.find(':input').each(function () {
                var formObj = $(this);
                formObj.bind(key, {
                    formObj: formObj,
                    record: record,
                    rowIndex: rowIndex,
                    colIndex: colIndex,
                    tdObj: tdObj,
                    trObj: trObj,
                    options: options
                }, function (event) {
                    options.settings.event.customCellEditEvents[key](event.data.formObj, event.data.record, event.data.rowIndex, event.data.colIndex, event.data.tdObj, event.data.trObj, event.data.options);
                });
            });
        }
        return false;
    };
    /*************** extend render per column end ***************/


    /*************** extend after render grid start ***************/
        // render line number
    $.fn.bsgrid.extendAfterRenderGrid.renderLineNumber = function (parseSuccess, gridData, options) {
        $.fn.bsgrid.getGridHeaderObject(options).each(function (i) {
            var num = options.columnsModel[i].lineNumber;
            if (num == 'line' || num == 'total_line') {
                $.fn.bsgrid.getRows(options).find('td:nth-child(' + (i + 1) + ')').each(function (li) {
                    $(this).html((num == 'line') ? (li + 1) : (li + options.startRow));
                });
            }
        });
    };

    // add check changed event
    $.fn.bsgrid.extendAfterRenderGrid.addCheckChangedEvent = function (parseSuccess, gridData, options) {
        $.fn.bsgrid.getGridHeaderObject(options).each(function (hi) {
            if (options.columnsModel[hi].check == 'true') {
                var checkboxObj = $(this).find('input[type=checkbox]');
                var checkboxObjs = $.fn.bsgrid.getRows(options).find('td:nth-child(' + (hi + 1) + ')>input[type=checkbox]');
                checkboxObjs.change(function () {
                    var allCheckboxObjs = $.fn.bsgrid.getRows(options).find('td:nth-child(' + (hi + 1) + ')>input[type=checkbox]');
                    var checked = $.bsgrid.adaptAttrOrProp(checkboxObj, 'checked') ? true : false;
                    if (!checked && allCheckboxObjs.filter(':checked').length == allCheckboxObjs.length) {
                        $.bsgrid.adaptAttrOrProp(checkboxObj, 'checked', true);
                    } else if (checked && allCheckboxObjs.filter(':checked').length != allCheckboxObjs.length) {
                        $.bsgrid.adaptAttrOrProp(checkboxObj, 'checked', false);
                    }
                });
            }
        });
    };

    // add grid edit event
    $.fn.bsgrid.extendAfterRenderGrid.addGridEditEvent = function (parseSuccess, gridData, options) {
        var gridObj = $.fn.bsgrid.getGridObj(options.gridId);
        $.fn.bsgrid.getRows(options).each(function () {
            var columnsModel = options.columnsModel;
            $(this).find('td').each(function (ci) {
                if (columnsModel[ci].edit != '') {
                    // edit form change event
                    $(this).find(':input').change(function () {
                        var rowObj = $(this).parent('td').parent('tr');
                        var isNew = $.trim(rowObj.data('ex_new'));
                        var value = gridObj.getRecordIndexValue(gridObj.getRowRecord(rowObj), columnsModel[ci].index);
                        value = (isNew == 'true' ? '' : value);
                        if ($.trim($(this).val()) != value) {
                            $(this).addClass('bsgrid_editgrid_change');
                        } else {
                            $(this).removeClass('bsgrid_editgrid_change');
                        }
                        // store change cell number
                        rowObj.data('ex_change', rowObj.find('.bsgrid_editgrid_change').length);
                    });
                }
            });

            if (options.settings.extend.settings.supportGridEditTriggerEvent == '') {
                $(this).find('.bsgrid_editgrid_hidden').each(function () {
                    showCellEdit(this);
                });
            } else if (options.settings.extend.settings.supportGridEditTriggerEvent == 'rowClick') {
                $(this).click(function () {
                    $(this).find('.bsgrid_editgrid_hidden').each(function () {
                        showCellEdit(this);
                    });
                });
            } else if (options.settings.extend.settings.supportGridEditTriggerEvent == 'rowDoubleClick') {
                $(this).dblclick(function () {
                    $(this).find('.bsgrid_editgrid_hidden').each(function () {
                        showCellEdit(this);
                    });
                });
            } else if (options.settings.extend.settings.supportGridEditTriggerEvent == 'cellClick') {
                $(this).find('.bsgrid_editgrid_hidden').each(function () {
                    var formObj = this;
                    $(formObj).parent('td').click(function () {
                        showCellEdit(formObj);
                    });
                });
            } else if (options.settings.extend.settings.supportGridEditTriggerEvent == 'cellDoubleClick') {
                $(this).find('.bsgrid_editgrid_hidden').each(function () {
                    var formObj = this;
                    $(formObj).parent('td').dblclick(function () {
                        showCellEdit(formObj);
                    });
                });
            }
        });

        function showCellEdit(formObj) {
            var cloneObj = $(formObj).removeClass('bsgrid_editgrid_hidden').clone(true);
            $(formObj).parent('td').html(cloneObj);
        }
    };

    // aggregation
    $.fn.bsgrid.extendAfterRenderGrid.aggregation = function (parseSuccess, gridData, options) {
        var gridObj = $.fn.bsgrid.getGridObj(options.gridId);
        var columnsModel = options.columnsModel;
        $('#' + options.gridId + ' tfoot tr td[' + options.settings.colsProperties.aggAttr + '!=\'\']').each(function (i) {
            if (columnsModel[i].aggName != '') {
                var aggName = columnsModel[i].aggName;
                var val = null;
                if (aggName == 'count') {
                    val = options.curPageRowsNum;
                } else if (aggName == 'countnotnone' || aggName == 'sum' || aggName == 'avg' || aggName == 'max' || aggName == 'min' || aggName == 'concat') {
                    if (aggName == 'countnotnone') {
                        val = 0;
                    }
                    var valHtml = new StringBuilder();
                    $.fn.bsgrid.getRows(options).filter(':lt(' + options.curPageRowsNum + ')').each(function (ri) {
                        var rval = gridObj.getColumnValue(ri, columnsModel[i].aggIndex);
                        if (rval == '') {
                        } else if (aggName == 'countnotnone') {
                            val = (val == null ? 0 : val) + 1;
                        } else if (aggName == 'sum' || aggName == 'avg') {
                            if (!isNaN(rval)) {
                                val = (val == null ? 0 : val) + parseFloat(rval);
                            }
                        } else if (aggName == 'max' || aggName == 'min') {
                            if (!isNaN(rval) && (val == null || (aggName == 'max' && parseFloat(rval) > val) || (aggName == 'min' && parseFloat(rval) < val))) {
                                val = parseFloat(rval);
                            }
                        } else if (aggName == 'concat') {
                            valHtml.append(rval);
                        }
                    });
                    if (aggName == 'avg' && val != null) {
                        val = val / options.curPageRowsNum;
                    } else if (aggName == 'concat') {
                        val = valHtml.toString();
                    }
                } else if (aggName == 'custom') {
                    val = eval(columnsModel[i].aggIndex)(gridObj, options);
                }
                val = val == null ? '' : val;
                $(this).html(val);
            }
        });
    };

    $.fn.bsgrid.extendAfterRenderGrid.setOptionsFirstRowClone = function (parseSuccess, gridData, options) {
        options.firstRowClone = $.fn.bsgrid.getRow(0, options).clone(true);
    }
    /*************** extend after render grid end ***************/


    /*************** extend edit methods start ***************/
    /**
     * Gget checked rows indexs, from 0.
     *
     * @param options
     * @returns {Array}
     */
    $.fn.bsgrid.defaults.extend.getCheckedRowsIndexs = function (options) {
        var rowIndexs = [];
        $.fn.bsgrid.getRows(options).each(function (i) {
            if ($(this).find('td>input:checked').length == 1) {
                rowIndexs[rowIndexs.length] = i;
            }
        });
        return rowIndexs;
    };

    /**
     * Get checked rows records.
     *
     * @param options
     * @returns {Array}
     */
    $.fn.bsgrid.defaults.extend.getCheckedRowsRecords = function (options) {
        var records = [];
        $.each($.fn.bsgrid.defaults.extend.getCheckedRowsIndexs(options), function (i, rowIndex) {
            records[records.length] = $.fn.bsgrid.getRecord(rowIndex, options);
        });
        return records;
    };

    /**
     * Get checked values by index.
     *
     * @param index
     * @param options
     * @returns {Array}
     */
    $.fn.bsgrid.defaults.extend.getCheckedValues = function (index, options) {
        var values = [];
        $.each($.fn.bsgrid.defaults.extend.getCheckedRowsRecords(options), function (i, record) {
            values[values.length] = $.fn.bsgrid.getRecordIndexValue(record, index, options);
        });
        return values;
    };

    /**
     * Active grid edit mode.
     *
     * @param options
     */
    $.fn.bsgrid.defaults.extend.activeGridEditMode = function (options) {
        if (!options.settings.extend.settings.supportGridEdit) {
            return;
        }
        $.fn.bsgrid.getRows(options).find('td .bsgrid_editgrid_hidden').each(function () {
            var cloneObj = $(this).removeClass('bsgrid_editgrid_hidden').clone(true);
            $(this).parent('td').html(cloneObj);
        });
    };

    /**
     * Get changed rows indexs, from 0.
     *
     * @param options
     * @returns {Array}
     */
    $.fn.bsgrid.defaults.extend.getChangedRowsIndexs = function (options) {
        var rowIndexs = [];
        $.fn.bsgrid.getRows(options).each(function (i) {
            var cellChangedNumStr = $.trim($(this).data('ex_change'));
            if (!isNaN(cellChangedNumStr) && parseInt(cellChangedNumStr) > 0) {
                rowIndexs[rowIndexs.length] = i;
            }
        });
        return rowIndexs;
    };

    /**
     * Get changed rows old records.
     *
     * @param options
     * @returns {Array}
     */
    $.fn.bsgrid.defaults.extend.getChangedRowsOldRecords = function (options) {
        var records = [];
        $.each($.fn.bsgrid.defaults.extend.getChangedRowsIndexs(options), function (i, rowIndex) {
            records[records.length] = $.fn.bsgrid.getRecord(rowIndex, options);
        });
        return records;
    };

    /**
     * Get rows changed columns value, return Object's key is 'row_'+rowIndex, value is a object.
     *
     * @param options
     * @returns {Object}
     */
    $.fn.bsgrid.defaults.extend.getRowsChangedColumnsValue = function (options) {
        var values = {};
        $.each($.fn.bsgrid.defaults.extend.getChangedRowsIndexs(options), function (i, rowIndex) {
            values['row_' + rowIndex] = {};
            $.fn.bsgrid.getRows(options).filter(':eq(' + rowIndex + ')').find('td').each(function (ci) {
                if ($(this).find('.bsgrid_editgrid_change').length > 0) {
                    values['row_' + rowIndex][options.columnsModel[ci].index] = options.settings.extend.settings.gridEditConfigs[options.columnsModel[ci].edit].val($(this).find('.bsgrid_editgrid_change'));
                }
            })
        });
        return values;
    };

    /**
     * delete row.
     *
     * @param row
     * @param options
     */
    $.fn.bsgrid.defaults.extend.deleteRow = function (row, options) {
        $.fn.bsgrid.getRow(row, options).remove();
    };

    /**
     * add new edit row.
     *
     * @param options
     */
    $.fn.bsgrid.defaults.extend.addNewEditRow = function (options) {
        var gridObj = $.fn.bsgrid.getGridObj(options.gridId);
        $('#' + options.gridId + ' tbody').prepend(options.firstRowClone.clone(true));
        gridObj.getRowCells(0).each(function (colIndex) {
            var columnModel = options.columnsModel[colIndex];
            if (columnModel.render != '') {
                var render_method = eval(columnModel.render);
                var render_html = render_method(null, 0, colIndex, options);
                $(this).html(render_html);
            } else {
                if (columnModel.edit != 'textarea') {
                    $(this).children().val('');
                } else {
                    $(this).children().text('');
                }
                $(this).html($(this).children().removeClass('bsgrid_editgrid_change').clone(true)).removeAttr('title');
            }
        });
        gridObj.getRow(0).data('record', null).data('ex_new', 'true');
    };
    /*************** extend edit methods end ***************/


    /*************** extend other methods start ***************/
    $.fn.bsgrid.extendOtherMethods.fixedHeader = function (iFirst, options) {
        var gridObj = $('#' + options.gridId);
        var gridFixedDivObj = $('#' + options.gridId + '_fixedDiv');
        if ($.trim(gridFixedDivObj.data('ex_fixedGridLock')) == 'lock') {
            return;
        }
        gridFixedDivObj.data('ex_fixedGridLock', 'lock');
        var headTrNum = $('#' + options.gridId + ' thead tr').length;
        if (!iFirst) {
            headTrNum = headTrNum / 2;
            $('#' + options.gridId + ' thead tr:lt(' + headTrNum + ')').remove();
        }
        var fixedGridHeight = getSize(options.settings.extend.settings.fixedGridHeight);
        if (fixedGridHeight < gridObj.height()) {
            gridFixedDivObj.height(fixedGridHeight).animate({scrollTop: '0px'}, 0);
            gridObj.width(gridFixedDivObj.width() - 18);
        } else {
            gridFixedDivObj.height(gridObj.height());
            gridObj.width(gridFixedDivObj.width() - 1);
        }
        $('#' + options.gridId + ' thead tr:lt(' + headTrNum + ')').clone(true).prependTo('#' + options.gridId + ' thead');
        $('#' + options.gridId + ' thead tr:lt(' + headTrNum + ')').css({
            'z-index': 10,
            position: 'fixed'
        }).width($('#' + options.gridId + ' thead tr:last').width());
        $('#' + options.gridId + ' thead tr:lt(' + headTrNum + ')').each(function (i) {
            var position = $('#' + options.gridId + ' thead tr:eq(' + (headTrNum + i) + ')').position();
            $(this).css({top: position.top - getSize($(this).find('th').css('border-top-width')), left: position.left});
        });

        $('#' + options.gridId + ' thead tr:gt(' + (headTrNum - 1) + ')').each(function (ri) {
            $(this).find('th').each(function (i) {
                var thObj = $(this);
                $('#' + options.gridId + ' thead tr:eq(' + ri + ') th:eq(' + i + ')').height(thObj.height() + ((ri == headTrNum - 1) ? 2 : 1) * getSize(thObj.css('border-top-width'))).width(thObj.width() + getSize(thObj.css('border-left-width')));
            });
        });
        gridFixedDivObj.data('ex_fixedGridLock', '');

        function getSize(sizeStr) {
            sizeStr = $.trim(sizeStr).toLowerCase().replace('px', '');
            var sizeNum = parseFloat(sizeStr);
            return isNaN(sizeNum) ? 0 : sizeNum;
        }
    };

    // init fixed header
    $.fn.bsgrid.extendOtherMethods.initFixedHeader = function (gridId, options) {
        $('#' + gridId).wrap('<div id="' + gridId + '_fixedDiv"></div>');
        $('#' + gridId + '_fixedDiv').data('ex_fixedGridLock', '').css({
            padding: 0,
            'border-width': 0,
            width: '98%',
            'overflow-y': 'auto',
            'margin-bottom': '-1px'
        });
        $('#' + gridId).css({width: 'auto'});
        $('#' + gridId + '_pt_outTab').css({'border-top-width': '1px'});
        $.fn.bsgrid.extendOtherMethods.fixedHeader(true, options);
        $(window).resize(function () {
            $.fn.bsgrid.extendOtherMethods.fixedHeader(false, options);
        });
    };
    /*************** extend other methods end ***************/

})(jQuery);