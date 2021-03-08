package com.sky.bluesky;

import com.sky.bluesky.model.*;
import com.sky.bluesky.model.fact.RuleExecutionObject;
import com.sky.bluesky.model.fact.RuleExecutionResult;
import com.sky.bluesky.model.fact.TestRule;
import com.sky.bluesky.service.*;
import com.sky.bluesky.util.JsonUtil;
import com.sky.bluesky.util.SpringContextHolder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
public class BlueskyWebApplicationTests {

    @Resource
    private RuleEntityService ruleEntityService;
    @Resource
    private RuleEntityItemService ruleEntityItemService;
    @Resource
    private RuleSceneService ruleSceneService;
    @Resource
    private RuleVariableService ruleVariableService;
    @Resource
    private RuleSceneEntityRelService ruleSceneEntityRelService;
    @Resource
    private RuleActionService ruleActionService;
    @Resource
    private RuleActionParamService ruleActionParamService;
    @Resource
    private RuleActionRelService ruleActionRelService;
    @Resource
    private RuleActionParamValueService ruleActionParamValueService;
    @Resource
    private RuleConditionService ruleConditionService;
    @Resource
    private RuleInfoService ruleInfoService;
    @Resource
    private DroolsRuleEngineService droolsRuleEngineService;

    /**
     * Date 2017/7/27
     * Author lihao [lihao@sinosoft.com]
     * <p>
     * 方法说明: 测试规则相关查询
     */
    @Test
    public void findBaseRuleEntityInfiListTest() {
        try {

            //场景集合
            List<BaseRuleSceneInfo> ceneList = this.ruleSceneService.findBaseRuleSceneInfiList(null);
            //场景与实体关系
            List<BaseRuleSceneEntityRelInfo> rellist = this.ruleSceneEntityRelService.findBaseRuleSceneEntityRelInfoList(null, null);
            //自定义变量集合
            List<BaseRuleVariableInfo> varList = this.ruleVariableService.findBaseRuleVariableInfoList(null, null);
            //实体集合
            List<BaseRuleEntityInfo> list = this.ruleEntityService.findBaseRuleEntityInfoList(null);
            BaseRuleEntityItemInfo entityItemInfo = new BaseRuleEntityItemInfo();
            entityItemInfo.setEntityId(list.get(0).getEntityId());
            //实体属性集合
            List<BaseRuleEntityItemInfo> itenList = this.ruleEntityItemService.findBaseRuleEntityItemInfoList(entityItemInfo);
            //动作集合
            List<BaseRuleActionInfo> acList = this.ruleActionService.findBaseRuleActionInfoPage(null, null).getList();
            //动作与规则关系集合信息
            List<BaseRuleActionRuleRelInfo> ruleActionRelList = this.ruleActionRelService.findBaseRuleActionRuleRelInfoPage(null, null).getList();
            //动作参数集合
            List<BaseRuleActionParamInfo> paramList = this.ruleActionParamService.findBaseRuleActionParamInfoPage(null, null).getList();
            //动作参数值集合
            List<BaseRuleActionParamValueInfo> paramValueList = this.ruleActionParamValueService.findBaseRuleActionParamValueInfoPage(null, null).getList();
            //规则条件集合
            List<BaseRuleConditionInfo> conditionList = this.ruleConditionService.findBaseRuleConditionInfoPage(null, null).getList();
            //规则集合
            List<BaseRuleInfo> ruleList = this.ruleInfoService.findBaseRuleInfoPage(null, null).getList();
            //规则属性集合
            List<BaseRulePropertyInfo> proList = this.ruleInfoService.findBaseRulePropertyInfoList(null);
            //已经配置的属性
            List<BaseRulePropertyRelInfo> allProList = this.ruleInfoService.findRulePropertyListByRuleId(null);

            DroolsActionService action = SpringContextHolder.getBean("testActionImpl");
            System.out.println(action);

            //打印信息
            System.out.println("==================================================================================================================");
            System.out.println(JsonUtil.toJson(ceneList));
            System.out.println(JsonUtil.toJson(rellist));
            System.out.println("==================================================================================================================");
            System.out.println(JsonUtil.toJson(varList));
            System.out.println("==================================================================================================================");
            System.out.println(JsonUtil.toJson(list));
            System.out.println("=================================================================================================================");
            System.out.println(JsonUtil.toJson(itenList));
            System.out.println(JsonUtil.toJson(acList));
            System.out.println(JsonUtil.toJson(ruleActionRelList));
            System.out.println(JsonUtil.toJson(paramList));
            System.out.println(JsonUtil.toJson(paramValueList));
            System.out.println(JsonUtil.toJson(conditionList));
            System.out.println(JsonUtil.toJson(ruleList));
            System.out.println(JsonUtil.toJson(proList));
            System.out.println(JsonUtil.toJson(allProList));
            System.out.println("=================================================================================================================");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Date 2017/7/27
     * Author lihao [lihao@sinosoft.com]
     * <p>
     * 方法说明:  测试规则
     */
    @Test
    public void executeDroolsTest() throws Exception {
        try {
            RuleExecutionObject object = new RuleExecutionObject();
            TestRule test = new TestRule();
            test.setAmount(100);
            test.setScore(20);
            test.setMessage("lihao");
            object.addFactObject(test);
            RuleExecutionResult result = new RuleExecutionResult();
            object.setGlobal("_result",result);

            this.droolsRuleEngineService.excute(object,"test");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
