<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>皮肤动画</title>
        <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
        <link rel="stylesheet" href="../../css/oksub.css">
    </head>
    <body>
        <div class="ok-body">
            <!--form表单-->
            <form class="layui-form ok-form" lay-filter="filter">
                <div class="layui-form-item">
                    <label class="layui-form-label">弹窗皮肤</label>
                    <div class="layui-input-block" id="skin">
                        <input type="radio" name="skin" value="1" title="灰白色">
                        <input type="radio" name="skin" value="2" title="墨绿色">
                        <input type="radio" name="skin" value="3" title="天蓝色">
                        <input type="radio" name="skin" value="4" title="随机" checked>
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label">弹窗动画</label>
                    <div class="layui-input-block" id="anim">
                        <input type="radio" name="anim" value="0" title="平滑放大">
                        <input type="radio" name="anim" value="1" title="从上掉落">
                        <input type="radio" name="anim" value="2" title="从底部往上滑入">
                        <input type="radio" name="anim" value="3" title="从左滑入">
                        <input type="radio" name="anim" value="4" title="从左翻滚">
                        <input type="radio" name="anim" value="5" title="渐显">
                        <input type="radio" name="anim" value="6" title="抖动">
                        <input type="radio" name="anim" value="7" title="随机" checked>
                    </div>
                </div>
                <div class="layui-form-item">
                    <div class="layui-input-block">
                        <button class="layui-btn" lay-submit lay-filter="edit">立即提交</button>
                        <button type="reset" class="layui-btn layui-btn-primary">重置</button>
                    </div>
                </div>
            </form>
        </div>
        <!--js逻辑-->
        <script src="../../lib/layui/layui.js"></script>
        <script>
            layui.use(["element", "form", "okLayer", "okUtils"], function () {
                let form = layui.form;
                let $ = layui.jquery;
                let okLayer = layui.okLayer;
                let okUtils = layui.okUtils;

                // 单选框控件默认选中
                let storage = window.localStorage;
                let skin = storage.getItem("skin");
                let anim = storage.getItem("anim");
                let _skin = okUtils.number.isNumberWith(skin, 1, 4) ? skin : 4;
                $("#skin").find("input").each(function () {
                    let val = $(this).val();
                    if (val == _skin) {
                        $(this).prop("checked", true);
                    }
                });

                let _anim = okUtils.number.isNumberWith(anim, 0, 7) ? anim : 7;
                $("#anim").find("input").each(function () {
                    let val = $(this).val();
                    if (val == _anim) {
                        $(this).prop("checked", true);
                    }
                });

                form.render()

                form.on("submit(edit)", function (data) {
                    // 持久化skin和anim
                    let storage = window.localStorage;
                    storage.skin = data.field.skin;
                    storage.anim = data.field.anim;
                    okLayer.msg.greenTick("设置成功", function () {
                        parent.layer.close(parent.layer.getFrameIndex(window.name));
                    });
                    return false;
                });
            })
        </script>
    </body>
</html>
