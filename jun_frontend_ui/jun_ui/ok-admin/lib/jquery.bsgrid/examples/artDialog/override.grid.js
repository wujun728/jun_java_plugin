/**
 * require grid.js, artDialog 5.0.4.
 *
 * @author Baishui2004
 * @Date March 21, 2014
 */

// lock and unlock screen
$.fn.bsgrid.defaults.lockScreen = function (options, xhr) {
    $lockScreenDialog.visible();
    $lockScreenDialog.lock();
};
$.fn.bsgrid.defaults.unlockScreen = function (options, xhr, ts) {
    // delay 0.1s, to make lock screen look better
    setTimeout(function () {
        $lockScreenDialog.unlock();
        $lockScreenDialog.hidden();
    }, 100);
};

var $lockScreenDialog;
$(function () {
    $lockScreenDialog = $.dialog({
        id: '$-lock-screen-dialog',
        width: '205px', // Under IE9, if not set width or set width 'auto', it will cause dialog not display in center.
        title: false,
        cancel: false,
        visible: false,
        padding: '5px 5px 8px 15px',
        content: '<div class="bsgrid loading"><span>&emsp;</span>&nbsp;' + $.bsgridLanguage.loadingDataMessage + '&emsp;</div>'
    });
});