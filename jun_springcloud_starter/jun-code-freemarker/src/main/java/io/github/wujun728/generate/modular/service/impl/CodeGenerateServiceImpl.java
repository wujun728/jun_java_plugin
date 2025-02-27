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
package io.github.wujun728.generate.modular.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.log.StaticLog;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.extension.toolkit.SqlRunner;
import io.github.wujun728.common.exception.BusinessException;
import io.github.wujun728.generate.core.ref.PageFactory;
import io.github.wujun728.generate.core.ref.PageResult;
import io.github.wujun728.generate.core.tool.StringDateTool;
import io.github.wujun728.generate.core.consts.GenConstant;
import io.github.wujun728.generate.core.context.XnVelocityContext;
import io.github.wujun728.generate.core.util.Util;
import io.github.wujun728.generate.modular.entity.CodeGenerate;
import io.github.wujun728.generate.modular.entity.SysCodeGenerateConfig;
import io.github.wujun728.generate.modular.mapper.CodeGenerateMapper;
import io.github.wujun728.generate.modular.param.CodeGenerateParam;
import io.github.wujun728.generate.modular.param.SysCodeGenerateConfigParam;
import io.github.wujun728.generate.modular.result.InforMationColumnsResult;
import io.github.wujun728.generate.modular.result.InformationResult;
import io.github.wujun728.generate.modular.service.CodeGenerateService;
import org.apache.commons.io.IOUtils;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.stereotype.Service;
//import vip.xiaonuo.core.exception.ServiceException;
//import vip.xiaonuo.core.factory.PageFactory;
//import vip.xiaonuo.core.pojo.page.PageResult;
import io.github.wujun728.generate.core.vo.XnCodeGenParam;
import io.github.wujun728.generate.modular.service.SysCodeGenerateConfigService;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * 代码生成基础配置service接口实现类
 *
 * @author yubaoshan
 * @date 2020年12月16日21:31:57
 */
@Service
public class CodeGenerateServiceImpl extends ServiceImpl<CodeGenerateMapper, CodeGenerate> implements CodeGenerateService {

    /**
     * 模板后缀
     */
    private static String TEMP_SUFFIX = ".vm";

    /**
     * 转换的编码
     */
    private static String ENCODED = "UTF-8";

    private static String SELECT_SYS_MENU_SQL = "select * from sys_permission where id = {0}";

    /**
     * 转换模板名称所需变量
     */
    private static String FORM_PAGE_NAME = "form.html";
    private static String INDEX_PAGE_NAME = "index.html";
    private static String JAVA_SUFFIX = ".java";
    private static String TEMP_ENTITY_NAME = "entity";
    private static String SQL_NAME = ".sql";

    @Resource
    private SysCodeGenerateConfigService sysCodeGenerateConfigService;

    @Override
    public PageResult<CodeGenerate> page(CodeGenerateParam codeGenerateParam) {
        QueryWrapper<CodeGenerate> queryWrapper = new QueryWrapper<>();
        if (ObjectUtil.isNotNull(codeGenerateParam)) {
            //根据表名模糊查询
            if (ObjectUtil.isNotEmpty(codeGenerateParam.getTableName())) {
                queryWrapper.lambda().like(CodeGenerate::getTableName, codeGenerateParam.getTableName());
            }
        }
        return new PageResult<>(this.page(PageFactory.defaultPage(), queryWrapper));
    }

    @Override
    public void add(CodeGenerateParam codeGenerateParam) {
        CodeGenerate codeGenerate = new CodeGenerate();
        BeanUtil.copyProperties(codeGenerateParam, codeGenerate);
        if (!vldTablePri(codeGenerate.getTableName())) {
            throw new BusinessException("表未定义主键");
        }
        this.save(codeGenerate);

        // 加入配置表中
        codeGenerateParam.setId(codeGenerate.getId());
        this.sysCodeGenerateConfigService.addList(this.getInforMationColumnsResultList(codeGenerateParam), codeGenerate);
    }

    @Override
    public void delete(List<CodeGenerateParam> codeGenerateParamList) {
        codeGenerateParamList.forEach(codeGenerateParam -> {
            this.removeById(codeGenerateParam.getId());
            SysCodeGenerateConfigParam sysCodeGenerateConfigParam = new SysCodeGenerateConfigParam();
            sysCodeGenerateConfigParam.setCodeGenId(codeGenerateParam.getId());
            this.sysCodeGenerateConfigService.delete(sysCodeGenerateConfigParam);
        });
    }

    @Override
    public void edit(CodeGenerateParam codeGenerateParam) {
        CodeGenerate codeGenerate = this.queryCodeGenerate(codeGenerateParam);
        BeanUtil.copyProperties(codeGenerateParam, codeGenerate);
        if (!vldTablePri(codeGenerate.getTableName())) {
            throw new BusinessException("表未设置主键");
        }
        this.updateById(codeGenerate);
    }

    @Override
    public CodeGenerate detail(CodeGenerateParam codeGenerateParam) {
        return this.queryCodeGenerate(codeGenerateParam);
    }

    /**
     * 获取代码生成基础配置
     *
     * @author yubaoshan
     * @date 2020年12月16日21:19:10
     */
    private CodeGenerate queryCodeGenerate(CodeGenerateParam codeGenerateParam) {
        CodeGenerate codeGenerate = this.getById(codeGenerateParam.getId());
        if (ObjectUtil.isNull(codeGenerate)) {
            throw new BusinessException("ID对应数据在数据库不存在");
        }
        return codeGenerate;
    }

    @Override
    public List<InformationResult> InformationTableList () {
        return this.baseMapper.selectInformationTable(Util.getDataBasename());
    }

    @Override
    public void runLocal(CodeGenerateParam codeGenerateParam) {
        XnCodeGenParam xnCodeGenParam = copyParams(codeGenerateParam);
        codeGenLocal(xnCodeGenParam);
    }

    @Override
    public void runDown(CodeGenerateParam codeGenerateParam, HttpServletResponse response) {
        XnCodeGenParam xnCodeGenParam = copyParams(codeGenerateParam);
        downloadCode(xnCodeGenParam, response);
    }

    /**
     * 校验表中是否包含主键
     *
     * @author yubaoshan
     * @date 2020年12月23日 00点32分
     */
    private boolean vldTablePri (String tableName) {
        List<InforMationColumnsResult> inforMationColumnsResultList =  this.baseMapper.selectInformationColumns(Util.getDataBasename(), tableName);
        for (int a = 0; a < inforMationColumnsResultList.size(); a++) {
            if (ObjectUtil.isNotNull(inforMationColumnsResultList.get(a).columnKey)
                    && inforMationColumnsResultList.get(a).columnKey.equals(GenConstant.DB_TABLE_COM_KRY)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 下载方式组装代码基础
     *
     * @author yubaoshan
     * @date 2020年12月23日 00点32分
     */
    private void downloadCode(XnCodeGenParam xnCodeGenParam, HttpServletResponse response) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ZipOutputStream zipOutputStream = new ZipOutputStream(outputStream);
        codeGenDown(xnCodeGenParam, zipOutputStream);
        IOUtils.closeQuietly(zipOutputStream);
        outputStream.toByteArray();
        try {
            Util.DownloadGen(response, outputStream.toByteArray());
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

    /**
     * 获取表中所有字段集合
     *
     * @author yubaoshan
     * @date 2021-02-06 22:36
     */
    private List<InforMationColumnsResult> getInforMationColumnsResultList (CodeGenerateParam codeGenerateParam) {
        CodeGenerate codeGenerate = this.queryCodeGenerate(codeGenerateParam);
        return this.baseMapper.selectInformationColumns(Util.getDataBasename(), codeGenerate.getTableName());
    }

    private XnCodeGenParam copyParams (CodeGenerateParam codeGenerateParam) {
        CodeGenerate codeGenerate = this.queryCodeGenerate(codeGenerateParam);
        SysCodeGenerateConfigParam sysCodeGenerateConfigParam = new SysCodeGenerateConfigParam();
        sysCodeGenerateConfigParam.setCodeGenId(codeGenerateParam.getId());
        List<SysCodeGenerateConfig> configList = this.sysCodeGenerateConfigService.list(sysCodeGenerateConfigParam);
        XnCodeGenParam param = new XnCodeGenParam();
        BeanUtil.copyProperties(codeGenerate, param);
        // 功能名
        param.setFunctionName(codeGenerate.getTableComment());
        param.setConfigList(configList);
        param.setCreateTimeString(StringDateTool.getStringDate());
        if (!codeGenerate.getMenuPid().equals("0")) {
            Map<String, Object> map = null;
            try {
                map = SqlRunner.db().selectOne(SELECT_SYS_MENU_SQL, codeGenerate.getMenuPid());
            } catch (Exception e) {
                if(e.getMessage().contains("doesn't exist")){
                    StaticLog.error("表不存在："+SELECT_SYS_MENU_SQL);
                }
                //throw new RuntimeException(e);
            }
            if(!CollectionUtils.isEmpty(map)){
                param.setMenuPids(map.get("pid").toString());
            }else{
                param.setMenuPids("0");
            }
        }
        return param;
    }

    /**
     * 本地项目生成
     */
    private void codeGenLocal (XnCodeGenParam xnCodeGenParam) {
        XnVelocityContext context = new XnVelocityContext();
        //初始化参数
        Properties properties=new Properties();
        //设置velocity资源加载方式为class
        properties.setProperty("resource.loader", "class");
        //设置velocity资源加载方式为file时的处理类
        properties.setProperty("class.resource.loader.class", "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
        //实例化一个VelocityEngine对象
        VelocityEngine velocityEngine=new VelocityEngine(properties);

        // 取得类名
        String DomainName = xnCodeGenParam.getClassName();
        String domainName = DomainName.substring(0,1).toLowerCase()+DomainName.substring(1);
        String[] filePath = GenConstant.xnCodeGenFilePath(xnCodeGenParam.getBusName(), xnCodeGenParam.getPackageName(), domainName);
        for (int i = 0; i < filePath.length; i++) {
            String templateName = GenConstant.xnCodeGenTempFile[i];

            String fileBaseName = ResetFileBaseName(xnCodeGenParam.getClassName(),
                    templateName.substring(templateName.indexOf(GenConstant.FILE_SEP) + 1, templateName.lastIndexOf(TEMP_SUFFIX)));
            String path = GenConstant.getLocalPath ();

            // sql放根目录
            if (fileBaseName.contains(SQL_NAME)) {
                path = GenConstant.getLocalFrontPath();
            }

            File file = new File(path + filePath[i] + fileBaseName);

            //判断是否覆盖存在的文件
            if(file.exists() && !GenConstant.FLAG){
                continue;
            }

            //获取父目录
            File parentFile = file.getParentFile();
            if(!parentFile.exists()){
                parentFile.mkdirs();
            }
            try {
                Writer writer = new FileWriter(file);
                velocityEngine.mergeTemplate(GenConstant.templatePath + templateName,ENCODED,context.createVelContext(xnCodeGenParam),writer);
                writer.close();
            } catch (Exception e) {
                throw new RuntimeException();
            }
        }
    }

    /**
     * 下载ZIP方式
     */
    private void codeGenDown (XnCodeGenParam xnCodeGenParam,ZipOutputStream zipOutputStream) {
        Util.initVelocity();
        XnVelocityContext context = new XnVelocityContext();

        // 取得类名
        String DomainName = xnCodeGenParam.getClassName();
        String domainName = DomainName.substring(0,1).toLowerCase()+DomainName.substring(1);
        String[] filePath = GenConstant.xnCodeGenFilePath(xnCodeGenParam.getBusName(), xnCodeGenParam.getPackageName(), domainName);
        for (int a = 0; a < filePath.length; a++) {
            String templateName = GenConstant.xnCodeGenTempFile[a];

            String fileBaseName = ResetFileBaseName(xnCodeGenParam.getClassName(),
                    templateName.substring(templateName.indexOf(GenConstant.FILE_SEP) + 1, templateName.lastIndexOf(TEMP_SUFFIX)));
            XnZipOutputStream(context.createVelContext(xnCodeGenParam),
                    GenConstant.templatePath + templateName,
                    filePath[a] + fileBaseName,
                    zipOutputStream);
        }
    }

    /**
     * 重置文件名称
     */
    private static String ResetFileBaseName (String className,String fileName) {
        String fileBaseName = className + fileName;
        // 实体类名称单独处理
        if (fileBaseName.contains(TEMP_ENTITY_NAME)) {
            return className + JAVA_SUFFIX;
        }
        // 表单界面名称
        if (fileBaseName.contains(FORM_PAGE_NAME)) {
            return FORM_PAGE_NAME;
        }
        // 首页html界面
        if (fileBaseName.contains(INDEX_PAGE_NAME)) {
            return INDEX_PAGE_NAME;
        }
        return fileBaseName;
    }

    /**
     * 生成ZIP
     */
    private void XnZipOutputStream (VelocityContext velContext,String tempName, String fileBaseName, ZipOutputStream zipOutputStream) {
        StringWriter sw = new StringWriter();
        Template tpl = Velocity.getTemplate(tempName, ENCODED);
        tpl.merge(velContext, sw);
        try {
            // 添加到zip
            zipOutputStream.putNextEntry(new ZipEntry(fileBaseName));
            IOUtils.write(sw.toString(), zipOutputStream, ENCODED);
            IOUtils.closeQuietly(sw);
            zipOutputStream.flush();
            zipOutputStream.closeEntry();
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }

}
