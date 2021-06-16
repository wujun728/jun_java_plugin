layui.use(['form', 'layer', 'okUtils'], function () {
    var form = layui.form,
        layer = layui.layer,
        $ = layui.jquery,
        util = layui.okUtils;
    $form = $('form');

    //添加验证规则verify
    form.verify({
        pass: [
            /^[\S]{3,16}$/
            , '密码必须3到16位，且不能出现空格'
        ],
        confirmPwd: function (value, item) {
            if ($("#onePwd").val() !== value) {
                return "两次输入密码不一致，请重新输入！";
            }
        },

    });

    //修改密码
    form.on("submit(changePwd)", function (data) {
        //修改操作
        util.ajax("updateSecurity", "post", data.field, true).done(function (data) {
            layer.msg(data, {icon: 1});
        });
        return false; //阻止表单跳转。如果需要表单跳转，去掉这段即可。
    })

});

