/**
 * jQuery.bsgrid v1.38 by @Baishui2004
 * Copyright 2014 Apache v2 License
 * https://github.com/baishui2004/jquery.bsgrid
 */
/**
 * require common.js.
 *
 * @author Baishui2004
 * @Date March 18, 2014
 */
$.bsgrid = {
    /**
     * Create a encode URL query string of an Array or Object.
     *
     * @param obj {}
     * @param twice option, default false; if set true then encode twice
     * @returns {string}
     */
    param: function (obj, twice) {
        if (twice == undefined) {
            twice = false;
        }
        if (!twice) {
            return $.param(obj);
        }

        var params = new StringBuilder();
        if (obj instanceof Array) {
            $.each(obj, function (i, objVal) {
                params.append('&' + objVal.name + '=');
                params.append(encodeURIComponent(encodeURIComponent(objVal.value)));
            });
        } else {
            for (var key in obj) {
                params.append('&' + key + '=');
                params.append(encodeURIComponent(encodeURIComponent(obj[key])));
            }
        }
        return params.length > 0 ? params.toString().substring(1) : '';
    },

    /**
     * Get keys string of an Array or Object, concat with delimiter.
     *
     * @param obj
     * @param delimiter option, default ','
     * @returns {string}
     */
    getKeysString: function (obj, delimiter) {
        if (delimiter == undefined) {
            delimiter = ',';
        }
        var params = new StringBuilder();
        if (obj instanceof Array) {
            $.each(obj, function (i, objVal) {
                // unique key
                if ((params.toString() + delimiter).indexOf(delimiter + objVal.name + delimiter) == -1) {
                    params.append(delimiter + objVal.name);
                }
            });
        } else {
            for (var key in obj) {
                params.append(delimiter + key);
            }
        }
        return params.length > 0 ? params.toString().substring(1) : '';
    },

    /**
     * Force push property in Object.
     *
     * @param obj
     * @param key
     * @param val
     */
    forcePushPropertyInObject: function (obj, key, val) {
        if (obj.hasOwnProperty(key)) {
            obj[key + '_f'] = val;
        } else {
            obj[key] = val;
        }
    },

    /**
     * Adapt attr() or prop() by jQuery Version.
     *
     * @param obj
     * @param prop
     * @param val
     */
    adaptAttrOrProp: function (obj, prop, val) {
        var version = parseInt($.fn.jquery.substring(0, $.fn.jquery.indexOf('.', 2)).replace('.', ''));
        if (val == undefined) {
            if (version >= 16) {
                return obj.prop(prop);
            } else {
                return obj.attr(prop);
            }
        } else {
            if (version >= 16) {
                obj.prop(prop, val);
            } else {
                obj.attr(prop, val);
            }
        }
    },

    /**
     * alert message.
     *
     * @param msg message
     */
    alert: function (msg) {
        alert(msg);
    }
};