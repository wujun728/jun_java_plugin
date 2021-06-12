/**
 * jQuery.bsgrid v1.38 by @Baishui2004
 * Copyright 2014 Apache v2 License
 * https://github.com/baishui2004/jquery.bsgrid
 */
/**
 * require common.js, util.js.
 *
 * @author Baishui2004
 * @Date July 21, 2014
 */
(function ($) {

    $.fn.bsgrid_form = {

        // defaults settings
        defaults: {
        },

        formObjs: {
        },

        /**
         * init form.
         */
        init: function (formId, settings) {
            var options = {
                settings: $.extend(true, {}, $.fn.bsgrid_form.defaults, settings),
                formId: formId,
                jqueryObj: $('#' + formId),
                formType: ''
            };

            var formObj = {
                options: options,
                addAssistShowFormTags: function () {
                    $.fn.bsgrid_form.addAssistShowFormTags(options);
                },
                showForm: function (type) {
                    $.fn.bsgrid_form.showForm(options, type);
                },
                showOrHideRequireSpan: function (type) {
                    $.fn.bsgrid_form.showOrHideRequireSpan(options, type);
                },
                showOrHideAssistForms: function (type) {
                    $.fn.bsgrid_form.showOrHideAssistForms(options, type);
                },
                showOrHideTag: function (type) {
                    $.fn.bsgrid_form.showOrHideTag(options, type);
                }
            };

            // store mapping form id to formObj
            $.fn.bsgrid_form.formObjs[formId] = formObj;

            formObj.addAssistShowFormTags();

            return formObj;
        },

        getFormObj: function (formId) {
            var obj = $.fn.bsgrid_form.formObjs[formId];
            return obj ? obj : null;
        },

        /**
         * add assist show form tags.
         */
        addAssistShowFormTags: function (options) {
            $('.formInput select', options.jqueryObj).each(function () {
                $(this).before('<input type="text" style="display: none;" />');
                var attrs = $(this).get(0).attributes;
                for (var i = 0; i < attrs.length; i++) {
                    var attr = attrs[i].name;
                    if (attr.toLowerCase().endWith('able') && $(this).attr(attr) == 'false') {
                        $(this).prev('input').attr(attr, 'false');
                    }
                }
            });
            $('.formInput textarea', options.jqueryObj).each(function () {
                $(this).before('<div class="assist" style="display: none;"></div>');
            });
        },

        showForm: function (options, type) {
            options.formType = type;

            this.showOrHideRequireSpan(options, type);
            this.showOrHideAssistForms(options, type);
            this.showOrHideTag(options, type);

            if (type.startWith('view')) {
                $('.formInput :input:not(:button,:submit,:reset)', options.jqueryObj).css({'border-width': '0'}).attr('readOnly', 'readOnly');
            } else if (type.startWith('add')) {
                $('.formInput :input:not(:button,:submit,:reset)', options.jqueryObj).css({'border': 'solid 1px #abadb3'}).removeAttr('readOnly');
            } else if (type.startWith('edit')) {
                $('.formInput :input:not(:button,:submit,:reset)', options.jqueryObj).css({'border': 'solid 1px #abadb3'}).removeAttr('readOnly');
                $('.formInput :input[' + type + 'Able=false]', options.jqueryObj).css({'border-width': '0'}).attr('readOnly', 'readOnly');
            }
        },

        /**
         * show or hide require span.
         */
        showOrHideRequireSpan: function (options, type) {
            if (type.startWith('view')) {
                $('.formLabel span.require', options.jqueryObj).hide();
            } else if (type.startWith('edit')) {
                $('.formLabel:has(span.require) ~ .formInput:has(:input[' + type + 'Able=false])', options.jqueryObj).prev().find('span.require').hide();
            } else {
                $('.formLabel span.require', options.jqueryObj).show();
            }
        },

        /**
         * show or hide assist forms.
         */
        showOrHideAssistForms: function (options, type) {
            $('.formInput select', options.jqueryObj).each(function () {
                var in_dis = (type.startWith('view') || (type.startWith('edit') && $(this).attr(type + 'Able') == 'false')) ? 'block' : 'none';
                $(this).prev('input').css('display', in_dis).val($(this).find('option:selected').text());
                var sel_dis = in_dis == 'block' ? 'none' : 'block';
                $(this).css('display', sel_dis);
            });
            $('.formInput textarea', options.jqueryObj).each(function () {
                var div_dis = (type.startWith('view') || (type.startWith('edit') && $(this).attr(type + 'Able') == 'false')) ? 'block' : 'none';
                $(this).prev('div').css('display', div_dis).html($(this).val());
                var text_dis = div_dis == 'block' ? 'none' : 'block';
                $(this).css('display', text_dis);
            });
        },

        /**
         * show or hide tag.
         */
        showOrHideTag: function (options, type) {
            $('*', options.jqueryObj).each(function () {
                var showType = $.trim($(this).attr('showType'));
                if (showType != '') {
                    if ((type.startWith('view') || type.startWith('add') || type.startWith('edit')) && (',' + showType + ',').indexOf(',' + type + ',') > -1) {
                        $(this).show();
                    } else {
                        $(this).hide();
                    }
                }
            });
        }

    };

})(jQuery);