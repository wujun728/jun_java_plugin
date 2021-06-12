/**
 * require artDialog 5.0.4, artDialog.plugin.override.js.
 *
 * @author Baishui2004
 * @Date July 20, 2014
 */

function alert(message) {
    return $.alert(message);
}

function confirm(message) {
    return $.confirm(message);
}

function prompt(text, defaultText) {
    return $.prompt(text, defaultText);
}

function modifyDialogAndMaskZIndex() {
    // Under IE9, may occur dialog covered by it's d-mask lock screen, These code is to solve it below.
    var mask_index = 0;
    $('.d-mask').each(function () {
        var tmp_index = $(this).css('z-index');
        if (!isNaN(tmp_index) && parseInt(tmp_index) > mask_index) {
            mask_index = parseInt(tmp_index);
        }
    });
    $('div[role=dialog]').parent('div').each(function (i) {
        $(this).css('z-index', mask_index + i + 1);
    });
}

$(function () {
    if ($.browser.msie && $.browser.version == '9.0') {
        // Under IE9, if not set width or set width 'auto', it will cause dialog not display in center. These three line code is to solve it below.
        alert().hidden().time(1);
        confirm('').hidden().time(1);
        prompt('', '').hidden().time(1);

        setInterval(modifyDialogAndMaskZIndex, 500);
    }
});