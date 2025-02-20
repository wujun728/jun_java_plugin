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
package io.github.wujun728.generate.modular.controller;

import io.github.wujun728.generate.core.ref.PageResult;
import io.github.wujun728.generate.core.ref.ResponseData;
import io.github.wujun728.generate.core.ref.SuccessResponseData;
import io.github.wujun728.generate.modular.entity.SysCodeGenerateConfig;
import io.github.wujun728.generate.modular.param.SysCodeGenerateConfigParam;
import io.github.wujun728.generate.modular.service.SysCodeGenerateConfigService;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 代码生成详细配置控制器
 *
 * @author yubaoshan
 * @date 2021-02-06 20:19:49
 */
@Controller
@RequestMapping({"/","/api"})
public class SysCodeGenerateConfigController {

    private static String PATH_PREFIX = "generate/";

    @Resource
    private SysCodeGenerateConfigService sysCodeGenerateConfigService;

    /**
     * 字段配置界面
     *
     * @auther yubaoshan
     * @date 12/15/20 11:20 PM
     */
    @GetMapping("/codeGenerate/indexConfig")
    public String index() {
        return PATH_PREFIX + "indexConfig.html";
    }

    /**
     * 编辑代码生成详细配置
     *
     * @author yubaoshan
     * @date 2021-02-06 20:19:49
     */
    //@Permission
    @ResponseBody
    @PostMapping("/sysCodeGenerateConfig/edit")
    //@BusinessLog(title = "代码生成详细配置_编辑", opType = LogAnnotionOpTypeEnum.EDIT)
    public ResponseData edit(@RequestBody @Validated(SysCodeGenerateConfigParam.edit.class) SysCodeGenerateConfigParam sysCodeGenerateConfigParam) {
            sysCodeGenerateConfigService.edit(sysCodeGenerateConfigParam.getSysCodeGenerateConfigParamList());
        return new SuccessResponseData();
    }

    /**
     * 查看代码生成详细配置
     *
     * @author yubaoshan
     * @date 2021-02-06 20:19:49
     */
    //@Permission
    @ResponseBody
    @GetMapping("/sysCodeGenerateConfig/detail")
    //@BusinessLog(title = "代码生成详细配置_查看", opType = LogAnnotionOpTypeEnum.DETAIL)
    public ResponseData detail(@Validated(SysCodeGenerateConfigParam.detail.class) SysCodeGenerateConfigParam sysCodeGenerateConfigParam) {
        return new SuccessResponseData(sysCodeGenerateConfigService.detail(sysCodeGenerateConfigParam));
    }

    /**
     * 代码生成详细配置列表
     *
     * @author yubaoshan
     * @date 2021-02-06 20:19:49
     */
    //@Permission
    @ResponseBody
    @GetMapping("/sysCodeGenerateConfig/list")
    //@BusinessLog(title = "代码生成详细配置_列表", opType = LogAnnotionOpTypeEnum.QUERY)
    public PageResult<SysCodeGenerateConfig> list(SysCodeGenerateConfigParam sysCodeGenerateConfigParam) {
        return sysCodeGenerateConfigService.page(sysCodeGenerateConfigParam);
    }

}
