$(function () {
    $('#btn-removeAll').on('click', function (e) {
        $.ajax({
            type: "DELETE",
            url: "/cache/remove",
            success: function (data) {
                show();
                init();
            }
        });
    });
    $('#btn-remove').on('click', function (e) {
        $.ajax({
            type: "DELETE",
            url: "/cache/pre/" + $('#key').text(),
            success: function (data) {
                show();
                init();
            }
        });
    });
    $('#btn-delay').on('click', function (e) {
        if ($('#expireHour').val() == "") {
            alert("Please input the number!");
        }
        if($('#expireTime').text()==""){
            $('#updateModal').modal('hide');
            return;
        }
        $.ajax({
            type: 'PUT',
            url: "/cache/" + $('#key').text(),
            data: "hour=" + $('#expireHour').val(),
            success: function (data) {
                show();
                init();
                $('#updateModal').modal('hide');
            }
        });
    });
    init();
});
function clear() {
    $('#key').empty();
    $('#expireTime').empty();
    $('#desc').empty();
    $('#privew').empty();
}
function show() {
    $('#alert').show();
    setTimeout(function () {
        $("#alert").hide()
    }, 1000);
}
function init() {
    clear();
    $.ajax({
        type: "GET",
        url: "/cache/list",
        success: function (defaultData) {
            var $checkableTree = $('#treeview-checkable').treeview({
                data: defaultData,
                levels: 1,
                showIcon: false,
                onNodeSelected: function (event, node) {
                    clear();
                    if (node.key == "") {
                        $('#key').prepend('<p>' + node.text + '</p>');
                        return;
                    }
                    $('#key').prepend('<p>' + node.key + '</p>');
                    $('#expireTime').prepend('<p>' + node.expireTime + '</p>');
                    $('#desc').prepend('<p>' + node.desc + '</p>');
                    $.ajax({
                        type: "GET",
                        url: "/cache/" + node.key,
                        success: function (data) {
                            var text;
                            try {
                                text = eval("(" + data + ")");
                            } catch (e) {
                                $('#privew').empty();
                                $('#privew').prepend('<p>' + data
                                + '</p>');
                                return;
                            }
                            if (text instanceof Array) {
                                if (text.length > 300) {
                                    $('#privew').empty();
                                    $('#privew')
                                        .prepend('<p>Too huge to preivew</p>');
                                } else {
                                    $("#privew").JSONView(text);
                                }
                            } else if (typeof(text) == "object"
                                && Object.prototype.toString
                                    .call(text).toLowerCase() == "[object object]"
                                && !text.length) {
                                $("#privew").JSONView(text);
                            } else {
                                $('#privew').empty();
                                $('#privew').prepend('<p>' + data
                                + '</p>');
                            }
                        }
                    });
                }
            });
            var findCheckableNodess = function () {
                return $checkableTree.treeview('search', [
                    $('#input-check-node').val(), {
                        ignoreCase: false,
                        exactMatch: false
                    }]);
            };
            var checkableNodes = findCheckableNodess();

            $('#input-check-node').on('keyup', function (e) {
                checkableNodes = findCheckableNodess();
                $('.check-node')
                    .prop('disabled', !(checkableNodes.length >= 1));
            });
        }
    });
}