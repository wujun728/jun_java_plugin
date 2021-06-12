/**
 * @Date March 17, 2014
 */

/**
 * String startWith.
 *
 * @param string
 * @returns {boolean}
 */
String.prototype.startWith = function (string) {
    if (string == null || string == "" || this.length == 0 || string.length > this.length) {
        return false;
    } else {
        return this.substr(0, string.length) == string;
    }
};

/**
 * String endWith.
 *
 * @param string
 * @returns {boolean}
 */
String.prototype.endWith = function (string) {
    if (string == null || string == "" || this.length == 0 || string.length > this.length) {
        return false;
    } else {
        return this.substring(this.length - string.length) == string;
    }
};

/**
 * String replaceAll.
 *
 * @param string1
 * @param string2
 * @returns {string}
 */
String.prototype.replaceAll = function (string1, string2) {
    return this.replace(new RegExp(string1, "gm"), string2);
};

function StringBuilder() {
    if (arguments.length) {
        this.append.apply(this, arguments);
    }
}
/**
 * StringBuilder.
 * Property: length
 * Method: append,appendFormat,size,toString,valueOf
 *
 * From: http://webreflection.blogspot.com/2008/06/lazy-developers-stack-concept-and.html
 * (C) Andrea Giammarchi - Mit Style License
 * @type {StringBuilder.prototype}
 */
StringBuilder.prototype = function () {
    var join = Array.prototype.join, slice = Array.prototype.slice, RegExp = /\{(\d+)\}/g, toString = function () {
        return join.call(this, "");
    };
    return {
        constructor: StringBuilder,
        length: 0,
        append: Array.prototype.push,
        appendFormat: function (String) {
            var i = 0, args = slice.call(arguments, 1);
            this.append(RegExp.test(String) ? String.replace(RegExp, function (String, i) {
                return args[i];
            }) : String.replace(/\?/g, function () {
                return args[i++];
            }));
            return this;
        },
        size: function () {
            return this.toString().length;
        },
        toString: toString,
        valueOf: toString
    };
}();