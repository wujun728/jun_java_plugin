# laydate-theme-bootstrap

#### 介绍
laydate之bootstrap主题


#### 使用教程

1. 引入bootstrap相关css
2. 将bootstrap文件夹拷贝至laydate/theme下
3. 引入laydate/theme/bootstrap/laydate.css
4. 如需组件化，可引入laydate/theme/bootstrap/bootstrap-laydate.js

#### 使用说明
```html
<div class="form-horizontal">
    <div class="row">
        <div class="col-sm-12">
            <div class="section-title"><h3>基础</h3></div>
            <div class="hr-line-dashed"></div>
            <div class="section-content">
                <div class="form-group">
                    <label class="col-sm-2 control-label">年月日：</label>
                    <div class="col-sm-4">
                        <input type="text" data-toggle="laydate" data-date-type="date" value="2018-08-08" class="form-control" placeholder="时间">
                    </div>
                    <label class="col-sm-2 control-label">年月日时分秒：</label>
                    <div class="col-sm-4">
                        <input type="text" data-toggle="laydate" data-date-type="datetime" value="2018-08-08 12:00:00" class="form-control" placeholder="时间">
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-2 control-label">时分秒：</label>
                    <div class="col-sm-4">
                        <input type="text" data-toggle="laydate" data-date-type="time" value="12:00:00" class="form-control" placeholder="时间">
                    </div>
                    <label class="col-sm-2 control-label">年：</label>
                    <div class="col-sm-4">
                        <input type="text" data-toggle="laydate" data-date-type="year" value="2019" class="form-control" placeholder="时间">
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-2 control-label">年月：</label>
                    <div class="col-sm-4">
                        <input type="text" data-toggle="laydate" data-date-type="month" value="2019-06" class="form-control" placeholder="时间">
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-2 control-label">默认当前日期：</label>
                    <div class="col-sm-4">
                        <input type="text" data-toggle="laydate" data-date-type="date" value="" data-date-now="true" class="form-control" placeholder="时间">
                    </div>
                    <label class="col-sm-2 control-label">不默认当前日期：</label>
                    <div class="col-sm-4">
                        <input type="text" data-toggle="laydate" data-date-type="date" value="" class="form-control" placeholder="时间">
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div class="row">
        <div class="col-sm-12">
            <div class="section-title"><h3>时间范围</h3></div>
            <div class="hr-line-dashed"></div>
            <div class="section-content">
                <div class="form-group">
                    <label class="col-sm-2 control-label">年月日：</label>
                    <div class="col-sm-4">
                        <input type="text" data-toggle="laydate" data-date-type="date" data-date-range="true"  value="2019-06-08 - 2019-07-08" class="form-control" placeholder="时间">
                    </div>
                    <label class="col-sm-2 control-label">年月日时分秒：</label>
                    <div class="col-sm-4">
                        <input type="text" data-toggle="laydate" data-date-type="datetime" data-date-range="true"  value="2019-06-08 12:00:00 - 2019-07-08 12:00:00" class="form-control" placeholder="时间">
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-2 control-label">时分秒：</label>
                    <div class="col-sm-4">
                        <input type="text" data-toggle="laydate" data-date-type="time" data-date-range="true"  value="12:00:00 - 17:00:00" class="form-control" placeholder="时间">
                    </div>
                    <label class="col-sm-2 control-label">年：</label>
                    <div class="col-sm-4">
                        <input type="text" data-toggle="laydate" data-date-type="year" data-date-range="true"  value="2019 - 2025" class="form-control" placeholder="时间">
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-2 control-label">年月：</label>
                    <div class="col-sm-4">
                        <input type="text" data-toggle="laydate" data-date-type="month" data-date-range="true"  value="2019-06 - 2019-10" class="form-control" placeholder="时间">
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div class="row">
        <div class="col-sm-12">
            <div class="section-title"><h3>公立节假日</h3></div>
            <div class="hr-line-dashed"></div>
            <div class="section-content">
                <div class="form-group">
                    <label class="col-sm-2 control-label">年月日：</label>
                    <div class="col-sm-4">
                        <input type="text" class="form-control" placeholder="时间" data-toggle="laydate" data-date-type="date" value="2019-06-08" data-date-holiday="true">
                    </div>
                    <label class="col-sm-2 control-label">年月日时分秒：</label>
                    <div class="col-sm-4">
                        <input type="text" class="form-control" placeholder="时间" data-toggle="laydate" data-date-type="datetime" value="2019-08-08 12:00:00" data-date-holiday="true">
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div class="row">
        <div class="col-sm-12">
            <div class="section-title"><h3>自定义重要日期</h3></div>
            <div class="hr-line-dashed"></div>
            <div class="section-content">
                <div class="form-group">
                    <label class="col-sm-2 control-label">每年日期：</label>
                    <div class="col-sm-4">
                        <input type="text" class="form-control" placeholder="时间" data-toggle="laydate" data-date-type="date" value="2019-06-08" data-date-holiday="true" data-date-day='{"0-9-8":"国耻"}'>
                    </div>
                    <label class="col-sm-2 control-label">每月日期：</label>
                    <div class="col-sm-4">
                        <input type="text" class="form-control" placeholder="时间" data-toggle="laydate" data-date-type="datetime" value="2019-09-08 12:00:00" data-date-holiday="true" data-date-day='{"0-0-15":"中旬"}'>
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-2 control-label">特定日期：</label>
                    <div class="col-sm-4">
                        <input type="text" class="form-control" placeholder="时间" data-toggle="laydate" data-date-type="date" value="2019-06-08" data-date-holiday="true" data-date-day='{"2019-7-21":"会议"}'>
                    </div>

                </div>
            </div>
        </div>
    </div>
    <div class="row">
        <div class="col-sm-12">
            <div class="section-title"><h3>最小/大范围</h3></div>
            <div class="hr-line-dashed"></div>
            <div class="section-content">
                <div class="form-group">
                    <label class="col-sm-2 control-label">年月日（前后N天）：</label>
                    <div class="col-sm-4">
                        <input type="text" data-toggle="laydate" data-date-type="date" data-date-min="-7" data-date-max="7" class="form-control" placeholder="时间">
                    </div>
                    <label class="col-sm-2 control-label">年月日时分秒（前后N天）：</label>
                    <div class="col-sm-4">
                        <input type="text" data-toggle="laydate" data-date-type="datetime" data-date-min="-7" data-date-max="7" class="form-control" placeholder="时间">
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-2 control-label">年月日（具体某天）：</label>
                    <div class="col-sm-4">
                        <input type="text" data-toggle="laydate" data-date-type="date" data-date-min="2019-05-05" data-date-max="2020-08-08" class="form-control" placeholder="时间">
                    </div>
                    <label class="col-sm-2 control-label">年月日时分秒（具体某天）：</label>
                    <div class="col-sm-4">
                        <input type="text" data-toggle="laydate" data-date-type="datetime" data-date-min="2019-05-05" data-date-max="2020-08-08" class="form-control" placeholder="时间">
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-2 control-label">年月日（混合使用）：</label>
                    <div class="col-sm-4">
                        <input type="text" data-toggle="laydate" data-date-type="date" data-date-min="-7" data-date-max="2020-08-08" class="form-control" placeholder="时间">
                    </div>
                    <label class="col-sm-2 control-label">年月日时分秒（混合使用）：</label>
                    <div class="col-sm-4">
                        <input type="text" data-toggle="laydate" data-date-type="datetime" data-date-min="2019-05-05" data-date-max="7" class="form-control" placeholder="时间">
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-2 control-label">时分秒：</label>
                    <div class="col-sm-4">
                        <input type="text" data-toggle="laydate" data-date-type="time" value="12:00:00" data-date-min="8:30:00" data-date-max="17:30:00" class="form-control" placeholder="时间">
                    </div>
                    <label class="col-sm-2 control-label">年：</label>
                    <div class="col-sm-4">
                        <p class="form-control-static">不支持</p>
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-2 control-label">年月：</label>
                    <div class="col-sm-4">
                        <p class="form-control-static">不支持</p>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div class="row">
        <div class="col-sm-12">
            <div class="section-title"><h3>自定义格式</h3></div>
            <div class="hr-line-dashed"></div>
            <div class="section-content">
                <div class="form-group">
                    <label class="col-sm-2 control-label">年月日：</label>
                    <div class="col-sm-4">
                        <input type="text" data-toggle="laydate" data-date-type="date" value="2018/08/08" data-date-format="yyyy/MM/dd" class="form-control" placeholder="时间">
                    </div>
                    <label class="col-sm-2 control-label">年月日时分秒：</label>
                    <div class="col-sm-4">
                        <input type="text" data-toggle="laydate" data-date-type="datetime" value="2018/08/08 12:00:00" data-date-format="yyyy/MM/dd HH:mm:ss" class="form-control" placeholder="时间">
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-2 control-label">时分秒：</label>
                    <div class="col-sm-4">
                        <input type="text" data-toggle="laydate" data-date-type="time" value="12时00分00秒" data-date-format="HH时mm分ss秒" class="form-control" placeholder="时间">
                    </div>
                    <label class="col-sm-2 control-label">年：</label>
                    <div class="col-sm-4">
                        <input type="text" data-toggle="laydate" data-date-type="year" value="2019年" data-date-format="yyyy年" class="form-control" placeholder="时间">
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-2 control-label">年月：</label>
                    <div class="col-sm-4">
                        <input type="text" data-toggle="laydate" data-date-type="month" value="2019年06月" data-date-format="yyyy年MM月" class="form-control" placeholder="时间">
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div class="row">
        <div class="col-sm-12">
            <div class="section-title"><h3>特殊用法</h3></div>
            <div class="hr-line-dashed"></div>
            <div class="section-content">
                <div class="form-group">
                    <label class="col-sm-2 control-label">非input元素：</label>
                    <div class="col-sm-4">
                        <span class="form-control" data-toggle="laydate" data-date-type="date" data-date="2018-08-08"></span>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
```


