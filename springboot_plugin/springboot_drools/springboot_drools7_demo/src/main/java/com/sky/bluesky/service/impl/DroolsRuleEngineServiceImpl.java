package com.sky.bluesky.service.impl;

import com.sky.bluesky.model.*;
import com.sky.bluesky.model.fact.RuleExecutionObject;
import com.sky.bluesky.service.*;
import com.sky.bluesky.util.DroolsUtil;
import com.sky.bluesky.util.RuleUtils;
import com.sky.bluesky.util.SpringContextHolder;
import com.sky.bluesky.util.StringUtil;
import org.apache.commons.lang3.ArrayUtils;
import org.drools.core.base.RuleNameStartsWithAgendaFilter;
import org.kie.api.runtime.KieSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * 描述：
 * CLASSPATH: com.sky.bluesky.service.impl.DroolsRuleEngineService
 * VERSION:   1.0
 * Created by lihao
 * DATE:      2017/7/25
 */
@Service
public class DroolsRuleEngineServiceImpl implements DroolsRuleEngineService {

    private Logger logger = LoggerFactory.getLogger(DroolsRuleEngineServiceImpl.class);

    @Resource
    private RuleSceneEntityRelService ruleSceneEntityRelService;
    @Resource
    private RuleInfoService ruleInfoService;
    @Resource
    private RuleActionService ruleActionService;
    @Resource
    private RuleConditionService ruleConditionService;
    @Resource
    private RuleEntityItemService ruleEntityItemService;
    @Resource
    private RuleEntityService ruleEntityService;
    @Resource
    private RuleActionParamService ruleActionParamService;
    @Resource
    private RuleActionParamValueService ruleActionParamValueService;

    //换行符
    private static final String lineSeparator = System.getProperty("line.separator");
    //特殊处理的规则属性(字符串)
    private static final String[] arr = new String[]{"date-effective", "date-expires", "dialect", "activation-group", "agenda-group", "ruleflow-group"};

    /**
     * Date 2017/7/25
     * Author lihao [lihao@sinosoft.com]
     * <p>
     * 方法说明: 规则引擎执行方法
     *
     * @param ruleExecutionObject facr对象信息
     * @param scene               场景
     */
    @Override
    public RuleExecutionObject excute(RuleExecutionObject ruleExecutionObject, final String scene) throws Exception {
        try {
            //获取ksession
            KieSession ksession = DroolsUtil.getInstance().getDrlSessionInCache(scene);
            if (ksession != null) {
                //直接执行
                return executeRuleEngine(ksession, ruleExecutionObject, scene);
            } else {
                //重新编译规则，然后执行
                return compileRule(ruleExecutionObject, scene);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    /**
     * Date 2017/7/26
     * Author lihao [lihao@sinosoft.com]
     * <p>
     * 方法说明: 规则执行方法
     *
     * @param session             会话
     * @param ruleExecutionObject 参数
     * @param scene               场景
     */
    private RuleExecutionObject executeRuleEngine(KieSession session, RuleExecutionObject ruleExecutionObject, final String scene) throws Exception {
        try {
            // 1.插入全局对象
            Map<String, Object> globalMap = ruleExecutionObject.getGlobalMap();
            for (Object glb : globalMap.entrySet()) {
                Map.Entry global = (Map.Entry) glb;
                String key = (String) global.getKey();
                Object value = global.getValue();
                System.out.println("插入Global对象:" + value.getClass().getName());
                session.setGlobal(key, value);
            }
            // 2.插入fact对象
            // 2.1插入业务fact对象
            List<Object> factObjectList = ruleExecutionObject.getFactObjectList();
            for (Object o : factObjectList) {
                System.out.println("插入Fact对象：" + o.getClass().getName());
                session.insert(o);
            }
            // 2.2将 ruleExecutionObject 也插入到规则中，回调使用
            session.insert(ruleExecutionObject);
            // 3.将规则涉及的所有动作实现类插入到规则中(如果没有，则不处理)
            BaseRuleSceneInfo sceneInfo = new BaseRuleSceneInfo();
            sceneInfo.setSceneIdentify(scene);
            //根据场景获取所有的动作信息
            List<BaseRuleActionInfo> actionList = this.ruleActionService.findRuleActionListByScene(sceneInfo);
            for (BaseRuleActionInfo action : actionList) {
                try {
                    //只处理实现类动作
                    if (action.getActionType() == 1) {
                        DroolsActionService actionService = SpringContextHolder.getBean(action.getActionClazzIdentify());
                        session.insert(actionService);
                    }
                } catch (Exception e) {
                    logger.error("解析规则动作对象时出错，请查看{}", action);
                    e.printStackTrace();
                    throw new RuntimeException("构造规则动作对象时出错，请检查！");
                }
            }

            //执行规则
            //1,是否全部执行
            if (ruleExecutionObject.isExecuteAll()) {
                session.fireAllRules();
            } else {
                //2,执行以 ruleExecutionObject里规则名开头的规则
                session.fireAllRules(new RuleNameStartsWithAgendaFilter(ruleExecutionObject.getRuleName()));
            }

            return ruleExecutionObject;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage(), e);
        } finally {
            //释放资源
            session.dispose();
        }
    }


    /**
     * Date 2017/7/26
     * Author lihao [lihao@sinosoft.com]
     * <p>
     * 方法说明: 编译规则脚本，并执行规则
     *
     * @param droolRuleStr        规则脚本
     * @param ruleExecutionObject 参数
     * @param scene               场景
     */
    private RuleExecutionObject compileRuleAndexEcuteRuleEngine(StringBuffer droolRuleStr, RuleExecutionObject ruleExecutionObject, final String scene) throws Exception {
        //KieSession对象
        KieSession session;
        try {
            //编译规则脚本,返回KieSession对象
            session = DroolsUtil.getInstance().getDrlSession(droolRuleStr.toString(), scene);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Drools初始化失败，请检查Drools语句！");
        }
        //执行规则
        return this.executeRuleEngine(session, ruleExecutionObject, scene);
    }


    /**
     * Date 2017/7/26
     * Author lihao [lihao@sinosoft.com]
     * <p>
     * 方法说明: 拼接drools语句
     *
     * @param ruleExecutionObject 参数
     * @param scene               场景
     */
    private RuleExecutionObject compileRule(RuleExecutionObject ruleExecutionObject, final String scene) throws Exception {
        //拼接规则脚本
        StringBuffer droolRuleStr = new StringBuffer();
        logger.info("===================重新拼接规则串======================");
        // 1.引入包路径
        droolRuleStr.append("package com.drools.rule").append(";").append(lineSeparator);
        // 2.引入global全局对象
        // TODO 暂时只设定一个
        droolRuleStr.append("import com.sky.bluesky.model.fact.RuleExecutionResult").append(";").append(lineSeparator);
        droolRuleStr.append("global RuleExecutionResult _result").append(";").append(lineSeparator);
        //将 RuleExecutionObject 也引入进来
        droolRuleStr.append("import com.sky.bluesky.model.fact.RuleExecutionObject").append(";").append(lineSeparator);
        // 3.引入实体信息（根据场景获取相关的实体信息）
        //传参
        BaseRuleSceneInfo sceneInfo = new BaseRuleSceneInfo();
        sceneInfo.setSceneIdentify(scene);
        List<BaseRuleEntityInfo> entityList = this.ruleSceneEntityRelService.findBaseRuleEntityListByScene(sceneInfo);
        logger.info("场景对应的实体个数为:{}", entityList.size());
        // 4.根据场景加载可用的规则信息
        List<BaseRuleInfo> ruleList = this.ruleInfoService.findBaseRuleListByScene(sceneInfo);
        logger.info("场景可用规则个数为:{}", ruleList.size());
        // 5.根据实体信息先组合drools的import语句
        droolRuleStr = this.insertImportInfo(droolRuleStr, entityList, sceneInfo);
        // 6.遍历并拼出每个规则的执行drools串
        for (BaseRuleInfo ruleInfo : ruleList) {
            StringBuffer ruleTemp;
            ruleTemp = this.getDroolsInfoByRule(ruleInfo);
            droolRuleStr.append(ruleTemp);
        }

        logger.info(lineSeparator + "===========================规则串================================" + lineSeparator);
//        logger.info(droolRuleStr.toString());
        System.out.println(droolRuleStr.toString());
        logger.info(lineSeparator + "===========================规则串================================" + lineSeparator);
        // 7.初始化drools，将实体对象扔进引擎
        return this.compileRuleAndexEcuteRuleEngine(droolRuleStr, ruleExecutionObject, scene);
    }

    /**
     * Date 2017/7/26
     * Author lihao [lihao@sinosoft.com]
     * <p>
     * 方法说明: 组装规则信息
     *
     * @param ruleInfo 规则
     */
    private StringBuffer getDroolsInfoByRule(BaseRuleInfo ruleInfo) throws Exception {
        //拼接规则字符串
        StringBuffer sb = new StringBuffer();
        // 1.拼接规则自身属性信息
        sb = this.insertRuleInfo(sb, ruleInfo);
        // 2.拼接条件
        sb = this.insertRuleCondition(sb, ruleInfo);
        // 3.拼接动作
        sb = this.insertRuleActionInfo(sb, ruleInfo);

        return sb;
    }

    /**
     * Date 2017/7/26
     * Author lihao [lihao@sinosoft.com]
     * <p>
     * 方法说明: 根据规则拼接规则自身相关的属性信息
     *
     * @param ruleStr  规则字符串
     * @param ruleInfo 规则
     */
    private StringBuffer insertRuleInfo(StringBuffer ruleStr, BaseRuleInfo ruleInfo) throws Exception {
        // 1.拼接规则名称(默认带双引号)
        ruleStr.append(lineSeparator).append("rule").append(" ").append("\"").append(ruleInfo.getRuleName()).append("\"").append(lineSeparator);
        // 2.拼接自身属性
        List<BaseRulePropertyRelInfo> rulePropertyList = this.ruleInfoService.findRulePropertyListByRuleId(ruleInfo.getRuleId());
        if (StringUtil.listIsNotNull(rulePropertyList)) {
            for (BaseRulePropertyRelInfo pro : rulePropertyList) {
                //如果配置的属性参数是字符串，则单独处理
                if (ArrayUtils.contains(arr, pro.getRulePropertyIdentify())) {
                    ruleStr.append("    ").append(pro.getRulePropertyIdentify()).append(" ").append("\"").append(pro.getRulePropertyValue()).append("\"").append(lineSeparator);
                } else {
                    ruleStr.append("    ").append(pro.getRulePropertyIdentify()).append(" ").append(pro.getRulePropertyValue()).append(lineSeparator);
                }
            }
        }
        return ruleStr;
    }

    /**
     * Date 2017/7/26
     * Author lihao [lihao@sinosoft.com]
     * <p>
     * 方法说明: 拼接规则条件信息
     *
     * @param ruleStr  规则字符串
     * @param ruleInfo 规则
     */
    private StringBuffer insertRuleCondition(StringBuffer ruleStr, BaseRuleInfo ruleInfo) throws Exception {
        // 1.拼接when
        ruleStr.append(lineSeparator).append("when").append(lineSeparator);
        //参数
        ruleStr.append(lineSeparator).append("$fact:RuleExecutionObject()").append(lineSeparator);
        // 2.拼接实现类的动作条件(如果有实现类的动作，此处拼接动作接口)
        Integer count = this.ruleActionService.findRuleActionCountByRuleIdAndActionType(ruleInfo.getRuleId());
        if (count > 0) {
            ruleStr.append("$action").append(":").append("DroolsActionService()").append(lineSeparator);
        }
        // 3.根据规则id获取条件信息
        List<BaseRuleConditionInfo> conList = this.ruleConditionService.findRuleConditionInfoByRuleId(ruleInfo.getRuleId());
        //如果没有找到条件信息，则默认永远满足
        if (StringUtil.listIsNotNull(conList)) {
            ruleStr = this.insertRuleConditionFromList(ruleStr, conList);
        } else {
            ruleStr.append("eval( true )").append(lineSeparator);
        }


        return ruleStr;
    }

    /**
     * Date 2017/7/27
     * Author lihao [lihao@sinosoft.com]
     * <p>
     * 方法说明: 处理条件部分内容
     *
     * @param ruleStr 规则串
     * @param conList 条件集合
     */
    private StringBuffer insertRuleConditionFromList(StringBuffer ruleStr, List<BaseRuleConditionInfo> conList) throws Exception {

        //只保存条件内容
        StringBuilder sb = new StringBuilder();
        //实体id
        Long entityId = null;
        //默认或的关系
        String relation = "&&";
        //TODO 暂时先按照多个条件处理（目前只实现&&关系条件）
        for (int c = 0; c < conList.size(); c++) {
            BaseRuleConditionInfo conditionInfo = conList.get(c);
            //表达式
            String expression = conditionInfo.getConditionExpression();
            //先处理==、>=、<=、>、<、！=后面的变量
            String conditionVariable = RuleUtils.getConditionOfVariable(expression);
            //如果是字符串，则拼接 单引号
            if (!RuleUtils.checkStyleOfString(conditionVariable)) {
                expression = expression.replace(conditionVariable, "'" + conditionVariable + "'");
            }
            // 1.获取条件参数（比如：$21$ ，21 代表实体属性表id）
            List<String> list = RuleUtils.getConditionParamBetweenChar(conditionInfo.getConditionExpression());
            //TODO 目前只会有一条
            for (String itemId : list) {
                // 2.根据itemId获取实体属性信息
                BaseRuleEntityItemInfo itemInfo = this.ruleEntityItemService.findBaseRuleEntityItemInfoById(Long.valueOf(itemId));
                if (null == entityId || !entityId.equals(itemInfo.getEntityId())) {
                    entityId = itemInfo.getEntityId();
                }
                // 3.拼接属性字段（例如：$21$ > 20 替换成 age > 20）
                expression = expression.replace("$" + itemId + "$", itemInfo.getItemIdentify());
            }
            //如果是最后一个，则不拼接条件之间关系
            if (c == conList.size() - 1) {
                relation = "";
            }
            // 4.拼接条件样式 (比如 ： age > 20 && )
            sb.append(expression).append(" ").append(relation).append(" ");

        }

        //获取实体
        BaseRuleEntityInfo entityInfo = this.ruleEntityService.findBaseRuleEntityInfoById(entityId);
        // 5.拼接实体类,完成条件拼接（例如：$User( age > 20 && sex==1) ）
        //TODO 日期格式需要单独处理
        ruleStr.append("$").append(entityInfo.getEntityIdentify()).append(":").append(entityInfo.getEntityClazz()).append("(").append(sb).append(")").append(lineSeparator);

        return ruleStr;
    }

    /**
     * Date 2017/7/27
     * Author lihao [lihao@sinosoft.com]
     * <p>
     * 方法说明: 拼接规则动作部分
     *
     * @param ruleStr  规则串
     * @param ruleInfo 规则
     */
    private StringBuffer insertRuleActionInfo(StringBuffer ruleStr, BaseRuleInfo ruleInfo) throws Exception {
        // 1.拼接then
        ruleStr.append(lineSeparator).append("then").append(lineSeparator);
        // 2.根据规则获取动作信息
        List<BaseRuleActionInfo> actionList = this.ruleActionService.findRuleActionListByRule(ruleInfo.getRuleId());
        //如果没有获取到动作信息，则默认动作部分为空
        if (!StringUtil.listIsNotNull(actionList)) {
            ruleStr.append(lineSeparator).append("end").append(lineSeparator);
            return ruleStr;
        } else {
            //是否有实现类动作
            Boolean implFlag = false;
            //临时动作对象
            BaseRuleActionInfo action;
            //动作参数对象
            BaseRuleActionParamInfo paramInfo;
            //动作参数值
            BaseRuleActionParamValueInfo paramValue;
            // 3.循环处理每一个动作
            for (BaseRuleActionInfo actionTemp : actionList) {
                //动作实体
                action = actionTemp;
                // 4.获取动作参数信息
                List<BaseRuleActionParamInfo> paraList = this.ruleActionParamService.findRuleActionParamByActionId(action.getActionId());
                for (BaseRuleActionParamInfo paramTemp : paraList) {
                    paramInfo = paramTemp;
                    // 5.获取动作参数值信息
                    paramValue = this.ruleActionParamValueService.findRuleParamValueByActionParamId(paramInfo.getActionParamId());
                    /*
                      6.1 动作实现类；
                      如果value值包含##（例如：#3# * 5），那么就认为是： 获取item属性等于3 的实体属性，然后 乘以 5, 然后放进全局map里
                      如果value值只是普通变量（例如：100、"此地不宜久留"），那么就认为是；直接当作value放进全局map里
                      6.2 自身动作类
                      如果value值包含##（例如：#3# * 5），那么就认为是： 获取item属性等于3 的实体属性，然后 乘以 5, 然后set到自身属性上（例如：order.setMoney(order.getMoney() * 5)）
                      如果value值只是普通变量（例如：100、"此地不宜久留"），那么就认为是；直接当作value set到自身属性里 （例如：mes.setMessage("此地不宜久留")）
                     */

                    String realValue = null;
                    //判断value值包含##（例如：#3# * 5），如果包含，首先取出item属性
                    if (RuleUtils.checkContainOfOperator(paramValue.getParamValue(), "#")) {
                        String tempValue = paramValue.getParamValue();
                        //取出#3#之间的值
                        List<String> strList = RuleUtils.getActionParamBetweenChar(tempValue);
                        //定义StringBuilder
                        StringBuilder sb;
                        for (String itemId : strList) {
                            sb = new StringBuilder();
                            //获取item属性
                            BaseRuleEntityItemInfo itemInfo = this.ruleEntityItemService.findBaseRuleEntityItemInfoById(Long.valueOf(itemId));
                            //获取实体
                            BaseRuleEntityInfo entityInfo = this.ruleEntityService.findBaseRuleEntityInfoById(itemInfo.getEntityId());
                            //获取真是value表达式
                            sb.append("$").append(entityInfo.getEntityIdentify()).append(".").append(RuleUtils.getMethodByProperty(itemInfo.getItemIdentify()));
                            //将表达式 #3# * 5 替换成  order.setMoney(order.getMoney() * 5)
                            realValue = tempValue.replace("#" + itemId + "#", sb.toString());
                        }
                    } else {
                        realValue = paramValue.getParamValue();
                        //如果是字符串，则添加双引号
                        if (!RuleUtils.checkStyleOfString(realValue)) {
                            realValue = "\"" + realValue + "\"";
                        }
                    }

                    //如果是实现类动作，则把参数放到全局变量 _result map里
                    if (action.getActionType() == 1) {
                        implFlag = true;
                        ruleStr.append("_result.getMap().put(\"").append(paramInfo.getParamIdentify()).append("\",").append(realValue).append(");").append(lineSeparator);//map.put("key",value)
                    } else {
                        ruleStr.append("$").append(action.getActionClazzIdentify()).append(".").
                                append(RuleUtils.setMethodByProperty(paramInfo.getParamIdentify())).append("(").append(realValue).append(");").append(lineSeparator);
                    }
                }
            }

            // 7.拼接实现类接口
            if (implFlag) {
                ruleStr.append("$action").append(".").append("execute").append("($fact,_result)").append(";").append(lineSeparator);
            }

            //TODO 规则执行记录信息


            // 8.拼装结尾标识
            ruleStr.append("end").append(lineSeparator).append(lineSeparator).append(lineSeparator);
        }


        return ruleStr;
    }

    /**
     * Date 2017/7/26
     * Author lihao [lihao@sinosoft.com]
     * <p>
     * 方法说明: 插入规则表达式的import部分
     *
     * @param droolRuleStr 规则串
     * @param entityList   实体信息
     */
    private StringBuffer insertImportInfo(StringBuffer droolRuleStr, List<BaseRuleEntityInfo> entityList,
                                          BaseRuleSceneInfo sceneInfo) throws Exception {

        // 1.导入场景对应的实体类
        for (BaseRuleEntityInfo entityInfo : entityList) {
            droolRuleStr.append("import").append(" ").append(entityInfo.getPkgName()).append(";").append(lineSeparator);
        }
        // 2.导入基本类
        droolRuleStr.append("import").append(" ").append("java.lang.String").append(";").append(lineSeparator);
        droolRuleStr.append("import").append(" ").append("java.util.Map").append(";").append(lineSeparator);
        droolRuleStr.append("import").append(" ").append("java.util.List").append(";").append(lineSeparator);
        // 3.导入动作类
        //根据场景获取动作类信息
        List<BaseRuleActionInfo> actionList = this.ruleActionService.findRuleActionListByScene(sceneInfo);
        if (StringUtil.listIsNotNull(actionList)) {
            //是否有实现类动作
            Boolean implFlag = false;
            //循环处理
            for (BaseRuleActionInfo actionInfo : actionList) {

                if (!implFlag) {
                    //如果是实现动作类，则先打标记
                    if (actionInfo.getActionType() == 1) {
                        implFlag = true;
                    }
                }
                //拼接动作类
                droolRuleStr.append("import").append(" ").append(actionInfo.getActionClass()).append(";").append(lineSeparator);
            }
            //如果有实现类，则把实现类接口拼装进去
            if (implFlag) {
                droolRuleStr.append("import").append(" ").append("com.sky.bluesky.service.DroolsActionService").append(";").append(lineSeparator);
            }
        }
        return droolRuleStr;
    }

}
