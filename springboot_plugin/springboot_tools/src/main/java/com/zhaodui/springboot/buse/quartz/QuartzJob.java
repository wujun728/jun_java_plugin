package com.zhaodui.springboot.buse.quartz;

import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSONObject;
import com.zhaodui.springboot.baseutil.DateUtils;
import com.zhaodui.springboot.buse.seeyon.RestFormDemorestsample;
import com.zhaodui.springboot.buse.seeyon.WgCarorder01Entity;
import lombok.extern.slf4j.Slf4j;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.PersistJobDataAfterExecution;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.util.unit.DataUnit;

import java.util.Date;
import java.util.List;

@DisallowConcurrentExecution    //相同定义的jobDetail不能并发执行
@PersistJobDataAfterExecution   //jobDataMap数据保存
@Slf4j
public class QuartzJob extends QuartzJobBean {

    public QuartzJob() {

    }

    /**
     * 具体任务
     *
     * @param jobExecutionContext
     * @throws JobExecutionException
     */
    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        log.info("springboot整合定时任务Quartz:" + new Date());
         String dataStr  = DateUtils.getDate("yyyy-mm-dd");
        String jsonStr = HttpUtil.get("http://47.113.88.244/fxj380/rest/wgCarorder01Controller/listall/admin?pageNumber=1&pageSize=9999999&fxjOut24="+dataStr);
        JSONObject jsonObject = JSONObject.parseObject(jsonStr);
        System.out.println(jsonObject.get("data").toString());
        List<WgCarorder01Entity> listall = JSONObject.parseArray(jsonObject.get("data").toString(), WgCarorder01Entity.class);
        for (WgCarorder01Entity t : listall) {
            if("1".equals(t.getFxjOut33())){
                continue;
            }
            try {
                String xml = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
                        "<forms version=\"2.1\">\n" +
                        "	<formExport>\n" +
                        "	<summary   name=\"formmain_3164\"/>\n" +
                        "	<definitions>" +
                        "      <column id=\"field0001\" type=\"2\" name=\"订单时间\" isNullable=\"false\" length=\"255\"/>\n" +
                        "      <column id=\"field0002\" type=\"0\" name=\"订单编号\" isNullable=\"false\" length=\"40\"/>\n" +
                        "      <column id=\"field0004\" type=\"0\" name=\"OBJID\" isNullable=\"false\" length=\"40\"/>\n" +
                        "      <column id=\"field0005\" type=\"0\" name=\"微信ID\" isNullable=\"false\" length=\"40\"/>\n" +
                        "      <column id=\"field0006\" type=\"0\" name=\"状态\" isNullable=\"false\" length=\"40\"/>\n" +
                        "      <column id=\"field0007\" type=\"0\" name=\"分公司\" isNullable=\"false\" length=\"60\"/>\n" +
                        "      <column id=\"field0008\" type=\"0\" name=\"门店\" isNullable=\"false\" length=\"60\"/>\n" +
                        "      <column id=\"field0009\" type=\"0\" name=\"区域\" isNullable=\"false\" length=\"60\"/>\n" +
                        "      <column id=\"field0010\" type=\"0\" name=\"二级网点名称\" isNullable=\"false\" length=\"60\"/>\n" +
                        "      <column id=\"field0011\" type=\"0\" name=\"二级联系人\" isNullable=\"false\" length=\"60\"/>\n" +
                        "      <column id=\"field0012\" type=\"0\" name=\"二级联系电话\" isNullable=\"false\" length=\"60\"/>\n" +
                        "      <column id=\"field0013\" type=\"0\" name=\"业务类型\" isNullable=\"false\" length=\"60\"/>\n" +
                        "      <column id=\"field0014\" type=\"0\" name=\"品牌\" isNullable=\"false\" length=\"60\"/>\n" +
                        "      <column id=\"field0015\" type=\"0\" name=\"车系\" isNullable=\"false\" length=\"60\"/>\n" +
                        "      <column id=\"field0016\" type=\"0\" name=\"车型名称\" isNullable=\"false\" length=\"60\"/>\n" +
                        "      <column id=\"field0017\" type=\"0\" name=\"车型编码\" isNullable=\"false\" length=\"60\"/>\n" +
                        "      <column id=\"field0018\" type=\"0\" name=\"配置\" isNullable=\"false\" length=\"60\"/>\n" +
                        "      <column id=\"field0019\" type=\"0\" name=\"颜色\" isNullable=\"false\" length=\"60\"/>\n" +
                        "      <column id=\"field0020\" type=\"0\" name=\"内饰色\" isNullable=\"false\" length=\"60\"/>\n" +
                        "      <column id=\"field0021\" type=\"0\" name=\"选装\" isNullable=\"false\" length=\"255\"/>\n" +
                        "      <column id=\"field0022\" type=\"0\" name=\"厂家指导价\" isNullable=\"false\" length=\"20\"/>\n" +
                        "      <column id=\"field0023\" type=\"0\" name=\"优惠金额\" isNullable=\"false\" length=\"20\"/>\n" +
                        "      <column id=\"field0024\" type=\"0\" name=\"优惠价\" isNullable=\"false\" length=\"20\"/>\n" +
                        "      <column id=\"field0025\" type=\"0\" name=\"姓名\" isNullable=\"false\" length=\"60\"/>\n" +
                        "      <column id=\"field0026\" type=\"0\" name=\"身份证\" isNullable=\"false\" length=\"60\"/>\n" +
                        "      <column id=\"field0027\" type=\"2\" name=\"申请日期\" isNullable=\"false\" length=\"255\"/>\n" +
                        "      <column id=\"field0028\" type=\"0\" name=\"联系电话\" isNullable=\"false\" length=\"60\"/>\n" +
                        "      <column id=\"field0029\" type=\"0\" name=\"审批价\" isNullable=\"false\" length=\"20\"/>\n" +
                        "      <column id=\"field0030\" type=\"0\" name=\"备注\" isNullable=\"false\" length=\"500\"/>\n" +
                        "      <column id=\"field0031\" type=\"0\" name=\"驱动模式\" isNullable=\"false\" length=\"60\"/>\n" +
                        "      <column id=\"field0032\" type=\"0\" name=\"年款\" isNullable=\"false\" length=\"60\"/>\n" +
                        "      <column id=\"field0033\" type=\"0\" name=\"二级专员\" isNullable=\"false\" length=\"60\"/>\n" +
                        "      <column id=\"field0034\" type=\"0\" name=\"集团接单员\" isNullable=\"false\" length=\"60\"/>" +
                        "</definitions>\n" +
                        "	<values>\n" +
                        "     <column name=\"订单时间\">\n" +
                        "        <value>" + t.getFxjOut24() +
                        "</value>\n" +
                        "      </column>\n" +
                        "      <column name=\"订单编号\">\n" +
                        "        <value>" + t.getId() +
                        "</value>\n" +
                        "      </column>\n" +
                        "      <column name=\"OBJID\">\n" +
                        "        <value>" + t.getId() +
                        "</value>\n" +
                        "      </column>\n" +
                        "      <column name=\"微信ID\">\n" +
                        "        <value>" + t.getFxjOut01() +
                        "</value>\n" +
                        "      </column>\n" +
                        "      <column name=\"状态\">\n" +
                        "        <value>" + t.getFxjOut32() +
                        "</value>\n" +
                        "      </column>\n" +
                        "      <column name=\"分公司\">\n" +
                        "        <value>" +
                        "</value>\n" +
                        "      </column>\n" +
                        "      <column name=\"门店\">\n" +
                        "        <value>" + t.getFxjOut04() +
                        "</value>\n" +
                        "      </column>\n" +
                        "      <column name=\"区域\">\n" +
                        "        <value></value>\n" +
                        "      </column>\n" +
                        "      <column name=\"二级网点名称\">\n" +
                        "        <value>" + t.getFxjOut05() +
                        "</value>\n" +
                        "      </column>\n" +
                        "      <column name=\"二级联系人\">\n" +
                        "        <value>" + t.getFxjOut06() +
                        "</value>\n" +
                        "      </column>\n" +
                        "      <column name=\"二级联系电话\">\n" +
                        "        <value>" + t.getFxjOut07() +
                        "</value>\n" +
                        "      </column>\n" +
                        "      <column name=\"业务类型\">\n" +
                        "        <value>" + t.getFxjOut08() +
                        "</value>\n" +
                        "      </column>\n" +
                        "      <column name=\"品牌\">\n" +
                        "        <value>" + t.getFxjOut09() +
                        "</value>\n" +
                        "      </column>\n" +
                        "      <column name=\"车系\">\n" +
                        "        <value>" + t.getFxjOut10() +
                        "</value>\n" +
                        "      </column>\n" +
                        "      <column name=\"车型名称\">\n" +
                        "        <value>" + t.getFxjOut11() +
                        "</value>\n" +
                        "      </column>\n" +
                        "      <column name=\"车型编码\">\n" +
                        "        <value>" + t.getFxjOut12() +
                        "</value>\n" +
                        "      </column>\n" +
                        "      <column name=\"配置\">\n" +
                        "        <value>" + t.getFxjOut13() +
                        "</value>\n" +
                        "      </column>\n" +
                        "      <column name=\"颜色\">\n" +
                        "        <value>" + t.getFxjOut14() +
                        "</value>\n" +
                        "      </column>\n" +
                        "      <column name=\"内饰色\">\n" +
                        "        <value>" + t.getFxjOut15() +
                        "</value>\n" +
                        "      </column>\n" +
                        "      <column name=\"选装\">\n" +
                        "        <value>" + t.getFxjOut16() +
                        "</value>\n" +
                        "      </column>\n" +
                        "      <column name=\"厂家指导价\">\n" +
                        "        <value>" + t.getFxjOut17() +
                        "</value>\n" +
                        "      </column>\n" +
                        "      <column name=\"优惠金额\">\n" +
                        "        <value>" + t.getFxjOut18() +
                        "</value>\n" +
                        "      </column>\n" +
                        "      <column name=\"优惠价\">\n" +
                        "        <value>" + t.getFxjOut19() +
                        "</value>\n" +
                        "      </column>\n" +
                        "      <column name=\"姓名\">\n" +
                        "        <value>" + t.getFxjOut22() +
                        "</value>\n" +
                        "      </column>\n" +
                        "      <column name=\"身份证\">\n" +
                        "        <value>" + t.getFxjOut23() +
                        "</value>\n" +
                        "      </column>\n" +
                        "      <column name=\"申请日期\">\n" +
                        "        <value>" + t.getFxjOut24() +
                        "</value>\n" +
                        "      </column>\n" +
                        "      <column name=\"联系电话\">\n" +
                        "        <value>" + t.getFxjOut25() +
                        "</value>\n" +
                        "      </column>\n" +
                        "      <column name=\"审批价\">\n" +
                        "        <value>" + t.getFxjOut26() +
                        "</value>\n" +
                        "      </column>\n" +
                        "      <column name=\"备注\">\n" +
                        "        <value>" + t.getFxjOut27() +
                        "</value>\n" +
                        "      </column>\n" +
                        "      <column name=\"驱动模式\">\n" +
                        "        <value>" + t.getFxjOut29() +
                        "</value>\n" +
                        "      </column>\n" +
                        "      <column name=\"年款\">\n" +
                        "        <value>" + t.getFxjOut28() +
                        "</value>\n" +
                        "      </column>\n" +
                        "      <column name=\"二级专员\">\n" +
                        "        <value>" + t.getFxjOut30() +
                        "</value>\n" +
                        "      </column>\n" +
                        "      <column name=\"集团接单员\">\n" +
                        "        <value>" + t.getFxjOut31() +
                        "</value>\n" +
                        "      </column>" +
                        "	</values>\n" +
                        "\n" +
                        "</formExport>\n" +
                        "</forms>";


                System.out.println("xml=" + xml);
            String result = RestFormDemorestsample.testImportBusinessFormData(xml);
                System.out.println("result=" + result);
                HttpUtil.get("http://47.113.88.244/fxj380/rest/wgCarorder01Controller/updateoa/"+t.getId()+"?fxjOut33="+result);
            }catch(Exception e){

            }
            }



    }
}

