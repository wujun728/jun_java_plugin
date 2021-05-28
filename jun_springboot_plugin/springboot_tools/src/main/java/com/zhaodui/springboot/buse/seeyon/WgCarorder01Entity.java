package com.zhaodui.springboot.buse.seeyon;
import javax.persistence.*;
import java.util.Date;



public class WgCarorder01Entity implements java.io.Serializable {
	/**主键*/
	private String id;
	/**创建人名称*/
//	@Excel(name="下单人",width=15)
	private String createName;
	/**创建人登录名称*/
	private String createBy;
	/**创建日期*/
	private Date createDate;
	/**更新人名称*/
	private String updateName;
	/**更新人登录名称*/
	private String updateBy;
	/**更新日期*/
	private Date updateDate;
	/**所属部门*/
	private String sysOrgCode;
	/**所属公司*/
	private String sysCompanyCode;
	/**流程状态*/
	private String bpmStatus;
	/**备用01*/
//	@Excel(name="微信ID",width=15)
	private String fxjOut01;
	/**备用02*/
//	@Excel(name="状态",width=15)
	private String fxjOut02;
	/**备用03*/
//	@Excel(name="销售顾问",width=15)
	private String fxjOut03;
	/**备用04*/
//	@Excel(name="区域",width=15)
	private String fxjOut04;
	/**备用05*/
//	@Excel(name="二级网点名称",width=15)
	private String fxjOut05;
	/**备用06*/
//	@Excel(name="二级联系人",width=15)
	private String fxjOut06;
	/**备用07*/
//	@Excel(name="二级联系电话",width=15)
	private String fxjOut07;
	/**备用08*/
//	@Excel(name="业务类型",width=15)
	private String fxjOut08;
	/**备用09*/
//	@Excel(name="品牌",width=15)
	private String fxjOut09;
	/**备用10*/
//	@Excel(name="车系",width=15)
	private String fxjOut10;
	/**备用11*/
//	@Excel(name="车型名称",width=15)
	private String fxjOut11;
	/**备用12*/
//	@Excel(name="车型编码",width=15)
	private String fxjOut12;
	/**备用13*/
//	@Excel(name="配置",width=15)
	private String fxjOut13;
	/**备用14*/
//	@Excel(name="颜色",width=15)
	private String fxjOut14;
	/**备用15*/
//	@Excel(name="内饰色",width=15)
	private String fxjOut15;
	/**备用16*/
//	@Excel(name="选装",width=15)
	private String fxjOut16;
	/**备用17*/
//	@Excel(name="厂家指导价(不含选装)",width=15)
	private String fxjOut17;
	/**备用18*/
//	@Excel(name="优惠金额",width=15)
	private String fxjOut18;
	/**备用19*/
//	@Excel(name="优惠价",width=15)
	private String fxjOut19;
	/**备用20*/
	private String fxjOut20;
	/**备用21*/
//	@Excel(name="备用21",width=15)
	private String fxjOut21;
	/**备用22*/
//	@Excel(name="姓名",width=15)
	private String fxjOut22;
	/**备用23*/
//	@Excel(name="身份证",width=15)
	private String fxjOut23;
	/**备用24*/
//	@Excel(name="申请日期",width=15)
	private String fxjOut24;
	/**备用25*/
//	@Excel(name="联系电话",width=15)
	private String fxjOut25;
	/**备用26*/
//	@Excel(name="审批价",width=15)
	private String fxjOut26;
	/**备用27*/
//	@Excel(name="备注",width=15)
	private String fxjOut27;
	/**备用28*/
//	@Excel(name="驱动模式",width=15)
	private String fxjOut29;
	/**备用29*/
//	@Excel(name="年款",width=15)
	private String fxjOut28;
	/**备用30*/
//	@Excel(name="二级专员",width=15)
	private String fxjOut30;
	/**备用31*/
//	@Excel(name="集团接单员",width=15)
	private String fxjOut31;
	/**备用32*/
//	@Excel(name="传输状态",width=15)
	private String fxjOut32;
	/**备用33*/
//	@Excel(name="备用33",width=15)
	private String fxjOut33;
	/**备用34*/
//	@Excel(name="备用34",width=15)
	private String fxjOut34;
	/**备用35*/
//	@Excel(name="备用35",width=15)
	private String fxjOut35;
	/**备用36*/
//	@Excel(name="备用36",width=15)
	private String fxjOut36;
	/**备用37*/
//	@Excel(name="备用37",width=15)
	private String fxjOut37;
	/**备用38*/
//	@Excel(name="备用38",width=15)
	private String fxjOut38;
	/**备用39*/
//	@Excel(name="备用39",width=15)
	private String fxjOut39;
	/**备用40*/
//	@Excel(name="备用40",width=15)
	private String fxjOut40;
	/**备用41*/
//	@Excel(name="备用41",width=15)
	private String fxjOut41;
	/**备用42*/
//	@Excel(name="备用42",width=15)
	private String fxjOut42;
	/**备用43*/
//	@Excel(name="备用43",width=15)
	private String fxjOut43;
	/**备用44*/
//	@Excel(name="备用44",width=15)
	private String fxjOut44;
	/**备用45*/
//	@Excel(name="备用45",width=15)
	private String fxjOut45;
	/**备用46*/
//	@Excel(name="备用46",width=15)
	private String fxjOut46;
	/**备用47*/
//	@Excel(name="备用47",width=15)
	private String fxjOut47;
	/**备用48*/
//	@Excel(name="备用48",width=15)
	private String fxjOut48;
	/**备用49*/
//	@Excel(name="备用49",width=15)
	private String fxjOut49;
	/**备用50*/
//	@Excel(name="创建人公司",width=15)
	private String fxjOut50;
	/**备用51*/
//	@Excel(name="备用51",width=15)
	private String fxjOut51;
	/**备用52*/
//	@Excel(name="备用52",width=15)
	private String fxjOut52;
	/**备用53*/
//	@Excel(name="备用53",width=15)
	private String fxjOut53;
	/**备用54*/
//	@Excel(name="备用54",width=15)
	private String fxjOut54;
	/**备用55*/
//	@Excel(name="备用55",width=15)
	private String fxjOut55;
	/**备用56*/
//	@Excel(name="备用56",width=15)
	private String fxjOut56;
	/**备用57*/
//	@Excel(name="备用57",width=15)
	private String fxjOut57;
	/**备用58*/
//	@Excel(name="备用58",width=15)
	private String fxjOut58;
	/**备用59*/
//	@Excel(name="备用59",width=15)
	private String fxjOut59;
	/**备用60*/
//	@Excel(name="备用60",width=15)
	private String fxjOut60;

	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  主键
	 */
//	@Id
//	@GeneratedValue(generator = "paymentableGenerator")
//	@GenericGenerator(name = "paymentableGenerator", strategy = "uuid")

	@Column(name ="ID",nullable=false,length=36)
	public String getId(){
		return this.id;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  主键
	 */
	public void setId(String id){
		this.id = id;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  创建人名称
	 */

	@Column(name ="CREATE_NAME",nullable=true,length=50)
	public String getCreateName(){
		return this.createName;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  创建人名称
	 */
	public void setCreateName(String createName){
		this.createName = createName;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  创建人登录名称
	 */

	@Column(name ="CREATE_BY",nullable=true,length=50)
	public String getCreateBy(){
		return this.createBy;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  创建人登录名称
	 */
	public void setCreateBy(String createBy){
		this.createBy = createBy;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  创建日期
	 */

	@Column(name ="CREATE_DATE",nullable=true,length=20)
	public Date getCreateDate(){
		return this.createDate;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  创建日期
	 */
	public void setCreateDate(Date createDate){
		this.createDate = createDate;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  更新人名称
	 */

	@Column(name ="UPDATE_NAME",nullable=true,length=50)
	public String getUpdateName(){
		return this.updateName;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  更新人名称
	 */
	public void setUpdateName(String updateName){
		this.updateName = updateName;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  更新人登录名称
	 */

	@Column(name ="UPDATE_BY",nullable=true,length=50)
	public String getUpdateBy(){
		return this.updateBy;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  更新人登录名称
	 */
	public void setUpdateBy(String updateBy){
		this.updateBy = updateBy;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  更新日期
	 */

	@Column(name ="UPDATE_DATE",nullable=true,length=20)
	public Date getUpdateDate(){
		return this.updateDate;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  更新日期
	 */
	public void setUpdateDate(Date updateDate){
		this.updateDate = updateDate;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  所属部门
	 */

	@Column(name ="SYS_ORG_CODE",nullable=true,length=50)
	public String getSysOrgCode(){
		return this.sysOrgCode;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  所属部门
	 */
	public void setSysOrgCode(String sysOrgCode){
		this.sysOrgCode = sysOrgCode;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  所属公司
	 */

	@Column(name ="SYS_COMPANY_CODE",nullable=true,length=50)
	public String getSysCompanyCode(){
		return this.sysCompanyCode;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  所属公司
	 */
	public void setSysCompanyCode(String sysCompanyCode){
		this.sysCompanyCode = sysCompanyCode;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  流程状态
	 */

	@Column(name ="BPM_STATUS",nullable=true,length=32)
	public String getBpmStatus(){
		return this.bpmStatus;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  流程状态
	 */
	public void setBpmStatus(String bpmStatus){
		this.bpmStatus = bpmStatus;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  备用01
	 */

	@Column(name ="FXJ_OUT01",nullable=true,length=32)
	public String getFxjOut01(){
		return this.fxjOut01;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  备用01
	 */
	public void setFxjOut01(String fxjOut01){
		this.fxjOut01 = fxjOut01;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  备用02
	 */

	@Column(name ="FXJ_OUT02",nullable=true,length=32)
	public String getFxjOut02(){
		return this.fxjOut02;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  备用02
	 */
	public void setFxjOut02(String fxjOut02){
		this.fxjOut02 = fxjOut02;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  备用03
	 */

	@Column(name ="FXJ_OUT03",nullable=true,length=32)
	public String getFxjOut03(){
		return this.fxjOut03;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  备用03
	 */
	public void setFxjOut03(String fxjOut03){
		this.fxjOut03 = fxjOut03;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  备用04
	 */

	@Column(name ="FXJ_OUT04",nullable=true,length=32)
	public String getFxjOut04(){
		return this.fxjOut04;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  备用04
	 */
	public void setFxjOut04(String fxjOut04){
		this.fxjOut04 = fxjOut04;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  备用05
	 */

	@Column(name ="FXJ_OUT05",nullable=true,length=32)
	public String getFxjOut05(){
		return this.fxjOut05;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  备用05
	 */
	public void setFxjOut05(String fxjOut05){
		this.fxjOut05 = fxjOut05;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  备用06
	 */

	@Column(name ="FXJ_OUT06",nullable=true,length=32)
	public String getFxjOut06(){
		return this.fxjOut06;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  备用06
	 */
	public void setFxjOut06(String fxjOut06){
		this.fxjOut06 = fxjOut06;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  备用07
	 */

	@Column(name ="FXJ_OUT07",nullable=true,length=32)
	public String getFxjOut07(){
		return this.fxjOut07;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  备用07
	 */
	public void setFxjOut07(String fxjOut07){
		this.fxjOut07 = fxjOut07;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  备用08
	 */

	@Column(name ="FXJ_OUT08",nullable=true,length=32)
	public String getFxjOut08(){
		return this.fxjOut08;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  备用08
	 */
	public void setFxjOut08(String fxjOut08){
		this.fxjOut08 = fxjOut08;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  备用09
	 */

	@Column(name ="FXJ_OUT09",nullable=true,length=32)
	public String getFxjOut09(){
		return this.fxjOut09;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  备用09
	 */
	public void setFxjOut09(String fxjOut09){
		this.fxjOut09 = fxjOut09;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  备用10
	 */

	@Column(name ="FXJ_OUT10",nullable=true,length=32)
	public String getFxjOut10(){
		return this.fxjOut10;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  备用10
	 */
	public void setFxjOut10(String fxjOut10){
		this.fxjOut10 = fxjOut10;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  备用11
	 */

	@Column(name ="FXJ_OUT11",nullable=true,length=32)
	public String getFxjOut11(){
		return this.fxjOut11;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  备用11
	 */
	public void setFxjOut11(String fxjOut11){
		this.fxjOut11 = fxjOut11;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  备用12
	 */

	@Column(name ="FXJ_OUT12",nullable=true,length=32)
	public String getFxjOut12(){
		return this.fxjOut12;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  备用12
	 */
	public void setFxjOut12(String fxjOut12){
		this.fxjOut12 = fxjOut12;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  备用13
	 */

	@Column(name ="FXJ_OUT13",nullable=true,length=32)
	public String getFxjOut13(){
		return this.fxjOut13;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  备用13
	 */
	public void setFxjOut13(String fxjOut13){
		this.fxjOut13 = fxjOut13;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  备用14
	 */

	@Column(name ="FXJ_OUT14",nullable=true,length=32)
	public String getFxjOut14(){
		return this.fxjOut14;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  备用14
	 */
	public void setFxjOut14(String fxjOut14){
		this.fxjOut14 = fxjOut14;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  备用15
	 */

	@Column(name ="FXJ_OUT15",nullable=true,length=32)
	public String getFxjOut15(){
		return this.fxjOut15;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  备用15
	 */
	public void setFxjOut15(String fxjOut15){
		this.fxjOut15 = fxjOut15;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  备用16
	 */

	@Column(name ="FXJ_OUT16",nullable=true,length=32)
	public String getFxjOut16(){
		return this.fxjOut16;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  备用16
	 */
	public void setFxjOut16(String fxjOut16){
		this.fxjOut16 = fxjOut16;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  备用17
	 */

	@Column(name ="FXJ_OUT17",nullable=true,length=32)
	public String getFxjOut17(){
		return this.fxjOut17;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  备用17
	 */
	public void setFxjOut17(String fxjOut17){
		this.fxjOut17 = fxjOut17;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  备用18
	 */

	@Column(name ="FXJ_OUT18",nullable=true,length=32)
	public String getFxjOut18(){
		return this.fxjOut18;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  备用18
	 */
	public void setFxjOut18(String fxjOut18){
		this.fxjOut18 = fxjOut18;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  备用19
	 */

	@Column(name ="FXJ_OUT19",nullable=true,length=32)
	public String getFxjOut19(){
		return this.fxjOut19;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  备用19
	 */
	public void setFxjOut19(String fxjOut19){
		this.fxjOut19 = fxjOut19;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  备用20
	 */

	@Column(name ="FXJ_OUT20",nullable=true,length=32)
	public String getFxjOut20(){
		return this.fxjOut20;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  备用20
	 */
	public void setFxjOut20(String fxjOut20){
		this.fxjOut20 = fxjOut20;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  备用21
	 */

	@Column(name ="FXJ_OUT21",nullable=true,length=32)
	public String getFxjOut21(){
		return this.fxjOut21;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  备用21
	 */
	public void setFxjOut21(String fxjOut21){
		this.fxjOut21 = fxjOut21;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  备用22
	 */

	@Column(name ="FXJ_OUT22",nullable=true,length=32)
	public String getFxjOut22(){
		return this.fxjOut22;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  备用22
	 */
	public void setFxjOut22(String fxjOut22){
		this.fxjOut22 = fxjOut22;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  备用23
	 */

	@Column(name ="FXJ_OUT23",nullable=true,length=32)
	public String getFxjOut23(){
		return this.fxjOut23;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  备用23
	 */
	public void setFxjOut23(String fxjOut23){
		this.fxjOut23 = fxjOut23;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  备用24
	 */

	@Column(name ="FXJ_OUT24",nullable=true,length=32)
	public String getFxjOut24(){
		return this.fxjOut24;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  备用24
	 */
	public void setFxjOut24(String fxjOut24){
		this.fxjOut24 = fxjOut24;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  备用25
	 */

	@Column(name ="FXJ_OUT25",nullable=true,length=32)
	public String getFxjOut25(){
		return this.fxjOut25;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  备用25
	 */
	public void setFxjOut25(String fxjOut25){
		this.fxjOut25 = fxjOut25;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  备用26
	 */

	@Column(name ="FXJ_OUT26",nullable=true,length=32)
	public String getFxjOut26(){
		return this.fxjOut26;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  备用26
	 */
	public void setFxjOut26(String fxjOut26){
		this.fxjOut26 = fxjOut26;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  备用27
	 */

	@Column(name ="FXJ_OUT27",nullable=true,length=32)
	public String getFxjOut27(){
		return this.fxjOut27;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  备用27
	 */
	public void setFxjOut27(String fxjOut27){
		this.fxjOut27 = fxjOut27;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  备用28
	 */

	@Column(name ="FXJ_OUT29",nullable=true,length=32)
	public String getFxjOut29(){
		return this.fxjOut29;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  备用28
	 */
	public void setFxjOut29(String fxjOut29){
		this.fxjOut29 = fxjOut29;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  备用29
	 */

	@Column(name ="FXJ_OUT28",nullable=true,length=32)
	public String getFxjOut28(){
		return this.fxjOut28;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  备用29
	 */
	public void setFxjOut28(String fxjOut28){
		this.fxjOut28 = fxjOut28;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  备用30
	 */

	@Column(name ="FXJ_OUT30",nullable=true,length=32)
	public String getFxjOut30(){
		return this.fxjOut30;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  备用30
	 */
	public void setFxjOut30(String fxjOut30){
		this.fxjOut30 = fxjOut30;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  备用31
	 */

	@Column(name ="FXJ_OUT31",nullable=true,length=32)
	public String getFxjOut31(){
		return this.fxjOut31;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  备用31
	 */
	public void setFxjOut31(String fxjOut31){
		this.fxjOut31 = fxjOut31;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  备用32
	 */

	@Column(name ="FXJ_OUT32",nullable=true,length=32)
	public String getFxjOut32(){
		return this.fxjOut32;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  备用32
	 */
	public void setFxjOut32(String fxjOut32){
		this.fxjOut32 = fxjOut32;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  备用33
	 */

	@Column(name ="FXJ_OUT33",nullable=true,length=32)
	public String getFxjOut33(){
		return this.fxjOut33;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  备用33
	 */
	public void setFxjOut33(String fxjOut33){
		this.fxjOut33 = fxjOut33;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  备用34
	 */

	@Column(name ="FXJ_OUT34",nullable=true,length=32)
	public String getFxjOut34(){
		return this.fxjOut34;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  备用34
	 */
	public void setFxjOut34(String fxjOut34){
		this.fxjOut34 = fxjOut34;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  备用35
	 */

	@Column(name ="FXJ_OUT35",nullable=true,length=32)
	public String getFxjOut35(){
		return this.fxjOut35;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  备用35
	 */
	public void setFxjOut35(String fxjOut35){
		this.fxjOut35 = fxjOut35;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  备用36
	 */

	@Column(name ="FXJ_OUT36",nullable=true,length=32)
	public String getFxjOut36(){
		return this.fxjOut36;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  备用36
	 */
	public void setFxjOut36(String fxjOut36){
		this.fxjOut36 = fxjOut36;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  备用37
	 */

	@Column(name ="FXJ_OUT37",nullable=true,length=32)
	public String getFxjOut37(){
		return this.fxjOut37;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  备用37
	 */
	public void setFxjOut37(String fxjOut37){
		this.fxjOut37 = fxjOut37;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  备用38
	 */

	@Column(name ="FXJ_OUT38",nullable=true,length=32)
	public String getFxjOut38(){
		return this.fxjOut38;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  备用38
	 */
	public void setFxjOut38(String fxjOut38){
		this.fxjOut38 = fxjOut38;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  备用39
	 */

	@Column(name ="FXJ_OUT39",nullable=true,length=32)
	public String getFxjOut39(){
		return this.fxjOut39;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  备用39
	 */
	public void setFxjOut39(String fxjOut39){
		this.fxjOut39 = fxjOut39;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  备用40
	 */

	@Column(name ="FXJ_OUT40",nullable=true,length=32)
	public String getFxjOut40(){
		return this.fxjOut40;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  备用40
	 */
	public void setFxjOut40(String fxjOut40){
		this.fxjOut40 = fxjOut40;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  备用41
	 */

	@Column(name ="FXJ_OUT41",nullable=true,length=32)
	public String getFxjOut41(){
		return this.fxjOut41;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  备用41
	 */
	public void setFxjOut41(String fxjOut41){
		this.fxjOut41 = fxjOut41;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  备用42
	 */

	@Column(name ="FXJ_OUT42",nullable=true,length=32)
	public String getFxjOut42(){
		return this.fxjOut42;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  备用42
	 */
	public void setFxjOut42(String fxjOut42){
		this.fxjOut42 = fxjOut42;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  备用43
	 */

	@Column(name ="FXJ_OUT43",nullable=true,length=32)
	public String getFxjOut43(){
		return this.fxjOut43;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  备用43
	 */
	public void setFxjOut43(String fxjOut43){
		this.fxjOut43 = fxjOut43;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  备用44
	 */

	@Column(name ="FXJ_OUT44",nullable=true,length=32)
	public String getFxjOut44(){
		return this.fxjOut44;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  备用44
	 */
	public void setFxjOut44(String fxjOut44){
		this.fxjOut44 = fxjOut44;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  备用45
	 */

	@Column(name ="FXJ_OUT45",nullable=true,length=32)
	public String getFxjOut45(){
		return this.fxjOut45;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  备用45
	 */
	public void setFxjOut45(String fxjOut45){
		this.fxjOut45 = fxjOut45;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  备用46
	 */

	@Column(name ="FXJ_OUT46",nullable=true,length=32)
	public String getFxjOut46(){
		return this.fxjOut46;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  备用46
	 */
	public void setFxjOut46(String fxjOut46){
		this.fxjOut46 = fxjOut46;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  备用47
	 */

	@Column(name ="FXJ_OUT47",nullable=true,length=32)
	public String getFxjOut47(){
		return this.fxjOut47;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  备用47
	 */
	public void setFxjOut47(String fxjOut47){
		this.fxjOut47 = fxjOut47;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  备用48
	 */

	@Column(name ="FXJ_OUT48",nullable=true,length=32)
	public String getFxjOut48(){
		return this.fxjOut48;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  备用48
	 */
	public void setFxjOut48(String fxjOut48){
		this.fxjOut48 = fxjOut48;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  备用49
	 */

	@Column(name ="FXJ_OUT49",nullable=true,length=32)
	public String getFxjOut49(){
		return this.fxjOut49;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  备用49
	 */
	public void setFxjOut49(String fxjOut49){
		this.fxjOut49 = fxjOut49;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  备用50
	 */

	@Column(name ="FXJ_OUT50",nullable=true,length=32)
	public String getFxjOut50(){
		return this.fxjOut50;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  备用50
	 */
	public void setFxjOut50(String fxjOut50){
		this.fxjOut50 = fxjOut50;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  备用51
	 */

	@Column(name ="FXJ_OUT51",nullable=true,length=32)
	public String getFxjOut51(){
		return this.fxjOut51;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  备用51
	 */
	public void setFxjOut51(String fxjOut51){
		this.fxjOut51 = fxjOut51;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  备用52
	 */

	@Column(name ="FXJ_OUT52",nullable=true,length=32)
	public String getFxjOut52(){
		return this.fxjOut52;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  备用52
	 */
	public void setFxjOut52(String fxjOut52){
		this.fxjOut52 = fxjOut52;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  备用53
	 */

	@Column(name ="FXJ_OUT53",nullable=true,length=32)
	public String getFxjOut53(){
		return this.fxjOut53;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  备用53
	 */
	public void setFxjOut53(String fxjOut53){
		this.fxjOut53 = fxjOut53;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  备用54
	 */

	@Column(name ="FXJ_OUT54",nullable=true,length=32)
	public String getFxjOut54(){
		return this.fxjOut54;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  备用54
	 */
	public void setFxjOut54(String fxjOut54){
		this.fxjOut54 = fxjOut54;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  备用55
	 */

	@Column(name ="FXJ_OUT55",nullable=true,length=32)
	public String getFxjOut55(){
		return this.fxjOut55;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  备用55
	 */
	public void setFxjOut55(String fxjOut55){
		this.fxjOut55 = fxjOut55;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  备用56
	 */

	@Column(name ="FXJ_OUT56",nullable=true,length=32)
	public String getFxjOut56(){
		return this.fxjOut56;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  备用56
	 */
	public void setFxjOut56(String fxjOut56){
		this.fxjOut56 = fxjOut56;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  备用57
	 */

	@Column(name ="FXJ_OUT57",nullable=true,length=32)
	public String getFxjOut57(){
		return this.fxjOut57;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  备用57
	 */
	public void setFxjOut57(String fxjOut57){
		this.fxjOut57 = fxjOut57;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  备用58
	 */

	@Column(name ="FXJ_OUT58",nullable=true,length=32)
	public String getFxjOut58(){
		return this.fxjOut58;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  备用58
	 */
	public void setFxjOut58(String fxjOut58){
		this.fxjOut58 = fxjOut58;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  备用59
	 */

	@Column(name ="FXJ_OUT59",nullable=true,length=32)
	public String getFxjOut59(){
		return this.fxjOut59;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  备用59
	 */
	public void setFxjOut59(String fxjOut59){
		this.fxjOut59 = fxjOut59;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  备用60
	 */

	@Column(name ="FXJ_OUT60",nullable=true,length=32)
	public String getFxjOut60(){
		return this.fxjOut60;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  备用60
	 */
	public void setFxjOut60(String fxjOut60){
		this.fxjOut60 = fxjOut60;
	}
}