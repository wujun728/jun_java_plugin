/*
Copyright [2020] [https://www.xiaonuo.vip]

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

  http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.

Snowy采用APACHE LICENSE 2.0开源协议，您在使用过程中，需要注意以下几点：

1.请不要删除和修改根目录下的LICENSE文件。
2.请不要删除和修改Snowy源码头部的版权声明。
3.请保留源码和相关描述文件的项目出处，作者声明等。
4.分发源码时候，请注明软件出处 https://gitee.com/xiaonuobase/snowy-layui
5.在修改包名，模块名称，项目代码等时，请注明软件出处 https://gitee.com/xiaonuobase/snowy-layui
6.若您的项目无法满足以上几点，可申请商业授权，获取Snowy商业授权许可，请在官网购买授权，地址为 https://www.xiaonuo.vip
 */
package com.jun.plugin.generate.core.ref;

import cn.hutool.core.convert.Convert;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 分页结果集
 *
 * @author xuyuxiang
 * @date 2020/3/30 15:44
 */
@Data
public class PageResult<T> implements Serializable {

    private static final long serialVersionUID = -1L;

    /**
     * 状态码
     */
    private Integer code = 0;

    /**
     * 消息
     */
    private String msg = "请求成功";

    /**
     * 第几页
     */
    private Integer page = 1;

    /**
     * 每页条数
     */
    private Integer limit = 20;

    /**
     * 总记录数
     */
    private Integer count = 0;

    /**
     * 结果集
     */
    private List<T> data;

    public PageResult() {
    }

    /**
     * 将mybatis-plus的page转成自定义的PageResult
     *
     * @author xuyuxiang
     * @date 2020/4/8 19:20
     */
    public PageResult(Page<T> page) {
        this.setData(page.getRecords());
        this.setCount(Convert.toInt(page.getTotal()));
        this.setPage(Convert.toInt(page.getCurrent()));
        this.setLimit(Convert.toInt(page.getSize()));
    }

    /**
     * 将list转为分页方式（无分页）
     *
     * @author xuyuxiang
     * @date 2020/12/4 15:47
     */
    public PageResult(List<T> t) {
        this.setData(t);
    }

    /**
     * 将mybatis-plus的page转成自定义的PageResult
     * 可单独设置rows
     *
     * @author xuyuxiang
     * @date 2020/4/14 20:55
     */
    public PageResult(Page<T> page, List<T> t) {
        this.setData(t);
        this.setCount(Convert.toInt(page.getTotal()));
        this.setPage(Convert.toInt(page.getCurrent()));
        this.setLimit(Convert.toInt(page.getSize()));
    }
}
