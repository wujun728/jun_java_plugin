package com.jd.ssh.sshdemo.bean;

public class EnumManage {
	
	/**
	 * 排序方式
	 */
	public enum OrderType{
		asc, desc
	}
	
	/**
	 * 文档状态
	 */
	public static enum DocumentStateEnum {
		/**
		 * 删除
		 */
		Delete("删除", 1),
		
		/**
		 * 启用
		 */
		Enable("启用", 2),
		
		/**
		 * 停用
		 */
		Disenable("停用", 3);
		
		private final String value;
		private final int key;
		
		private DocumentStateEnum(final String value, final int key) {
			this.value = value;
			this.key = key;
		}
		public String value() {
			return this.value;
		}
		public int key() {
			return this.key;
		}
	}
	
	/**
	 * 快递员活动状态
	 */
	public static enum ActivitiStateEnum {
		/**
		 * 活动中
		 */
		Active("活动中", 1),
		
		/**
		 * 离线
		 */
		Offline("离线", 2);
		
		private final String value;
		private final int key;
		
		private ActivitiStateEnum(final String value, final int key) {
			this.value = value;
			this.key = key;
		}
		public String value() {
			return this.value;
		}
		public int key() {
			return this.key;
		}
	}
	/**
	 * 快递员活动状态
	 */
	public static enum CourierApplyStateEnum {
		/**
		 * 申请中
		 */
		APPLY("申请中", 0),
		
		/**
		 * 申请成功
		 */
		SUCCESS("申请成功", 1),
		
		/**
		 * 申请失败
		 */
		FAIL("申请失败", 2);
		
		private final String value;
		private final int key;
		
		private CourierApplyStateEnum(final String value, final int key) {
			this.value = value;
			this.key = key;
		}
		public String value() {
			return this.value;
		}
		public int key() {
			return this.key;
		}
	}
	
	/**
	 * 性别
	 */
	public static enum SexEnum {
		/**
		 * 男
		 */
		male("男", 1),
		
		/**
		 * 女
		 */
		female("女", 2);
		
		private final String value;
		private final int key;
		
		private SexEnum(final String value, final int key) {
			this.value = value;
			this.key = key;
		}
		public String value() {
			return this.value;
		}
		public int key() {
			return this.key;
		}
	}
	
	/**
	 * 联系人类别
	 */
	public static enum LinkManEnum {
		/**
		 * 全部
		 */
		all("全部", 1),
		
		/**
		 * 发件人
		 */
		sender("发件人", 2),
		
		/**
		 * 收件人
		 */
		receiver("收件人", 3);
		
		private final String value;
		private final int key;
		
		private LinkManEnum(final String value, final int key) {
			this.value = value;
			this.key = key;
		}
		public String value() {
			return this.value;
		}
		public int key() {
			return this.key;
		}
	}
	
	/**
	 * 账户收支类别
	 */
	public static enum FundTypeEnum {
		
		/**
		 * 进款
		 */
		in("进款", 1),
		
		/**
		 * 出款
		 */
		out("出款", 2);
		
		private final String value;
		private final int key;
		
		private FundTypeEnum(final String value, final int key) {
			this.value = value;
			this.key = key;
		}
		public String value() {
			return this.value;
		}
		public int key() {
			return this.key;
		}
	}
	
	/**
	 * 订单发起
	 */
	public static enum CreateTypeEnum {
		/**
		 * APP端
		 */
		app("APP端" , 1),
		
		/**
		 * 网站
		 */
		website("网站", 2),
		
		/**
		 * 客服
		 */
		customer("客服", 3);
		
		private final String value;
		private final int key;
		
		private CreateTypeEnum(final String value, final int key) {
			this.value = value;
			this.key = key;
		}
		public String value() {
			return this.value;
		}
		public int key() {
			return this.key;
		}
	}
	
	/**
	 * 订单类型
	 */
	public static enum OrderCategoryEnum {
		/**
		 * 单程
		 */
		oneway("单程" ,1),
		
		/**
		 * 来回
		 */
		around("来回", 2),
		
		/**
		 * 联程
		 */
		through("联程", 3);
		
		private final String value;
		private final int key;
		
		private OrderCategoryEnum(final String value, final int key) {
			this.value = value;
			this.key = key;
		}
		public String value() {
			return this.value;
		}
		public int key() {
			return this.key;
		}
	}
	
	/**
	 * 物品类别
	 */
	public static enum GoodsCategoryEnum {
		/**
		 * 文件
		 */
		file("文件" ,1),
		
		/**
		 * 物品
		 */
		goods("物品", 2);
		
		private final String value;
		private final int key;
		
		private GoodsCategoryEnum(final String value, final int key) {
			this.value = value;
			this.key = key;
		}
		public String value() {
			return this.value;
		}
		public int key() {
			return this.key;
		}
	}
	
	/**
	 * 配送类别
	 */
	public static enum DeliveryTypeEnum {
		/**
		 * 即时
		 */
		immediately("即时件" ,1),
		
		/**
		 * 预约件
		 */
		everyotherday("预约件", 2);
		
		private final String value;
		private final int key;
		
		private DeliveryTypeEnum(final String value, final int key) {
			this.value = value;
			this.key = key;
		}
		public String value() {
			return this.value;
		}
		public int key() {
			return this.key;
		}
	}
	
	/**
	 * 配送时效
	 */
	public static enum DeliveryLimitEnum {
		/**
		 * 1小时内
		 */
		one("1小时内" ,1),
		
		/**
		 * 2小时内
		 */
		two("2小时内", 2),
		
		/**
		 * 3小时内
		 */
		three("3小时内", 3),
		
		/**
		 * 4小时内
		 */
		four("4小时内", 4),
		
		/**
		 * 5小时内
		 */
		five("5小时内", 5);
		
		private final String value;
		private final int key;
		
		private DeliveryLimitEnum(final String value, final int key) {
			this.value = value;
			this.key = key;
		}
		public String value() {
			return this.value;
		}
		public int key() {
			return this.key;
		}
	}
	
	/**
	 * 支付方式
	 */
	public static enum PayModeEnum {
		
		/**
		 * 现付(取件时付款)
		 */
		spot("现付(由寄件人付款)", 1),
		
		/**
		 * 到付(货到时付款)
		 */
		collect("到付(由收件人付款)" ,2),
		
		/**
		 * 月结(签约客户)
		 */
		monthly("月结(签约合同客户)", 3);
		
		private final String value;
		private final int key;
		
		private PayModeEnum(final String value, final int key) {
			this.value = value;
			this.key = key;
		}
		public String value() {
			return this.value;
		}
		public int key() {
			return this.key;
		}
		
	}
	
	/**
	 * 发票类型
	 */
	public static enum InvoiceTypeEnum {

		/**
		 * 个人
		 */
		personal("个人", 1),
		
		/**
		 * 企业
		 */
		enterprise("企业", 2);
		
//		/**
//		 * 普通
//		 */
//		normal("普通"),
//		
//		/**
//		 * 增值
//		 */
//		added("增值");
		
		private final String value;
		private final int key;
		
		private InvoiceTypeEnum(final String value, final int key) {
			this.value = value;
			this.key = key;
		}
		public String value() {
			return this.value;
		}
		public int key() {
			return this.key;
		}
	}
	
	/**
	 * 订单状态
	 */
	public static enum OrderStateEnum {
		/**
		 * 待审核
		 */
		loading("待审核", 1),
		
		/**
		 * 分派中
		 */
		readytoassign("分派中", 2),
		
		/**
		 * 分派超时无竞标
		 */
		viewoutoftime("分派超时", 3),
		
		/**
		 * 待指派（分派超时待指派）
		 */
		readytoappoint("待指派", 4),
		
		/**
		 * 取件中
		 */
		getting("取件中", 5),
		
		/**
		 * 送件中
		 */
		sending("送件中", 6),
		
		/**
		 * 已完成
		 */
		finished("已完成", 7),
		
		/**
		 * 退件
		 */
		backed("退件", 8),
		
		/**
		 * 取消
		 */
		canceled("取消", 9);
		
		private final String value;
		private final int key;
		
		private OrderStateEnum(final String value, final int key) {
			this.value = value;
			this.key = key;
		}
		public String value() {
			return this.value;
		}
		public int key() {
			return this.key;
		}
	}
	
	/**
	 * 处理事件(节点说明)
	 */
	public static enum OperateCategoryEnum {
		/**
		 * 发布订单
		 */
		release("发布订单", 1),
		
		/**
		 * 修改订单
		 */
		update("修改订单", 2),
		
		/**
		 * 分派
		 */
		assign("分派", 3),
		
		/**
		 * 竞标
		 */
		bidding("竞标", 4),
		
		/**
		 * 放弃竞标
		 */
		giveup("放弃竞标", 5),
		
		/**
		 * 指派
		 */
		point("指派", 6),
		
		/**
		 * 接件
		 */
		send("接件", 7),
		
		/**
		 * 取件
		 */
		get("取件", 8),
		
		/**
		 * 转接
		 */
		change("转接", 9),
		
		/**
		 * 签收
		 */
		sign("签收", 10),
		
		/**
		 * 退件
		 */
		back("退件", 11),
		/**
		 * 取消订单
		 */
		cancel("取消订单", 12);
		
		private final String value;
		private final int key;
		
		private OperateCategoryEnum(final String value, final int key) {
			this.value = value;
			this.key = key;
		}
		public String value() {
			return this.value;
		}
		public int key() {
			return this.key;
		}
	}
	
	/**
	 * 任务单节点
	 */
	public static enum ToDoCategoryEnum {
		/**
		 * 待审核
		 */
		loading("待审核", 1),
		
		/**
		 * 审核通过
		 */
		loaded("审核通过", 2),
		
		/**
		 * 分派
		 */
		assign("分派", 3),
		
		/**
		 * 指派
		 */
		point("指派", 4),
		
		/**
		 * 取件
		 */
		get("取件", 5),
		
		/**
		 * 签收
		 */
		send("签收", 6),
		
		/**
		 * 退件
		 */
		back("退件", 7),
		
		/**
		 * 取消
		 */
		cancel("取消", 8);
		
		private final String value;
		private final int key;
		
		private ToDoCategoryEnum(final String value, final int key) {
			this.value = value;
			this.key = key;
		}
		public String value() {
			return this.value;
		}
		public int key() {
			return this.key;
		}
	}
	
	/**
	 * 订单日志类型
	 */
	public static enum OrderLogTypeEnum {
		/**
		 * 普通描述
		 */
		normal("普通描述" , 1),
		
		/**
		 * 位置描述
		 */
		location("位置描述" , 2);
		
		private final String value;
		private final int key;
		
		private OrderLogTypeEnum(final String value, final int key) {
			this.value = value;
			this.key = key;
		}
		public String value() {
			return this.value;
		}
		public int key() {
			return this.key;
		}
	}
	
	/**
	 * 订单问题类型
	 */
	public static enum ProblemTypeEnum {
		/**
		 * 取消订单
		 */
		cancel("取消订单" , 1),
		
		/**
		 * 拒绝签收
		 */
		back("拒绝签收" , 2);
		
		private final String value;
		private final int key;
		
		private ProblemTypeEnum(final String value, final int key) {
			this.value = value;
			this.key = key;
		}
		public String value() {
			return this.value;
		}
		public int key() {
			return this.key;
		}
	}
	
	/**
	 * 订单问题原因类型
	 */
	public static enum ReasonTypeEnum {
		/**
		 * 寄件人自身原因
		 */
		sender_cancel("寄件人自身原因" , 1),
		
		/**
		 * 快递人自身原因
		 */
		courier_cancel("快递人自身原因" , 2),
		
		/**
		 * 联系不上寄件人
		 */
		notfound_sender("联系不上寄件人" , 3),
		
		/**
		 * 联系不上收件人
		 */
		notfound_receiver("联系不上收件人" , 4),
		
		/**
		 * 货物破损
		 */
		goods_broken("货物破损" , 5),
		
		/**
		 * 货物遗失
		 */
		goods_miss("货物遗失" , 6),
		
		/**
		 * 货物数量问题
		 */
		goods_less("货物数量问题" , 7),
		
		/**
		 * 客户要求延时
		 */
		delayed("客户要求延时" , 8),
		
		/**
		 * 在途晚点
		 */
		late("在途晚点" , 9),
		
		/**
		 * 其他原因
		 */
		other("其他原因" , 10);
		
		private final String value;
		private final int key;
		
		private ReasonTypeEnum(final String value, final int key) {
			this.value = value;
			this.key = key;
		}
		public String value() {
			return this.value;
		}
		public int key() {
			return this.key;
		}
	}
	
	/**
	 * 派送应答
	 */
	public static enum ResponStatusEnum {
		/**
		 * 无应答
		 */
		waiting("无应答" , 1),
		
		/**
		 * 接件
		 */
		yes("接件" , 2),
		
		/**
		 * 不接件
		 */
		no("不接件" , 3);
		
		private final String value;
		private final int key;
		
		private ResponStatusEnum(final String value, final int key) {
			this.value = value;
			this.key = key;
		}
		public String value() {
			return this.value;
		}
		public int key() {
			return this.key;
		}
	}
	
	/**
	 * 账户操作日志类别
	 */
	public static enum AccountLogCategoryEnum {
		/**
		 * 充值
		 */
		topup("充值" , 1),
		
		/**
		 * 服务费
		 */
		service("服务费" , 2),
		
		/**
		 *消费
		 */
		consume("消费" , 4),
		
		/**
		 *订单扣款
		 */
		deduct("订单扣款" , 5),
		
		/**
		 *赔款
		 */
		reparations("赔款" , 6),
		
		/**
		 * 其他
		 */
		other("其他" , 7),
		
		/**
		 * 推荐人收入
		 */
		recommend("推荐人收入" , 8),
		/**
		 * 提现
		 */
		withdraw("用户提现" , 9),
		/**
		 * 订单支付
		 */
		orderpay("订单支付" , 10),
		/**
		 * 订单取消退款
		 */
		cancelback("订单取消退款" , 11),
		/**
		 * 订单取消退款
		 */
		system("系统奖励" , 12);
		private final String value;
		private final int key;
		
		private AccountLogCategoryEnum(final String value, final int key) {
			this.value = value;
			this.key = key;
		}
		public String value() {
			return this.value;
		}
		public int key() {
			return this.key;
		}
	}
	
	/**
	 * 积分操作日志类别
	 */
	public static enum ScoreLogCategoryEnum {
		/**
		 * 加分
		 */
		add("加分" , 1),
		
		/**
		 * 扣分
		 */
		deduct("扣分" , 2),
		
		/**
		 *奖励
		 */
		reward("奖励" , 4),
		
		/**
		 *惩罚
		 */
		penalty("惩罚" , 5),
		
		/**
		 * 其他
		 */
		other("其他" , 6);
		
		private final String value;
		private final int key;
		
		private ScoreLogCategoryEnum(final String value, final int key) {
			this.value = value;
			this.key = key;
		}
		public String value() {
			return this.value;
		}
		public int key() {
			return this.key;
		}
	}
	
	/**
	 * 坐标类型
	 * bd09 百度墨卡托坐标系
	 * bd09ll 百度经纬度坐标系（百度地图）
	 * gcj02 中国国家测绘局坐标系
	 */
	public static enum CoordTypeEnum {
		/**
		 * 百度墨卡托坐标系
		 */
		bd09("百度墨卡托坐标系" , 1),
		
		/**
		 * 百度经纬度坐标系（百度地图）
		 */
		bd09ll("百度经纬度坐标系" , 2),
		
		/**
		 * 中国国家测绘局
		 */
		gcj02("中国国家测绘局" , 3);
		
		private final String value;
		private final int key;
		
		private CoordTypeEnum(final String value, final int key) {
			this.value = value;
			this.key = key;
		}
		public String value() {
			return this.value;
		}
		public int key() {
			return this.key;
		}
	}
	
	/**
	 * 百度定位结果LocType
	 * 61 ： GPS定位结果
	 * 62 ： 扫描整合定位依据失败。此时定位结果无效。
	 * 63 ： 网络异常，没有成功向服务器发起请求。此时定位结果无效。
	 * 65 ： 定位缓存的结果。
	 * 66 ： 离线定位结果。通过requestOfflineLocaiton调用时对应的返回结果
	 * 67 ： 离线定位失败。通过requestOfflineLocaiton调用时对应的返回结果
	 * 68 ： 网络连接失败时，查找本地离线定位时对应的返回结果
	 * 161： 表示网络定位结果
	 * 162~167： 服务端定位失败。 
	 */
	public static enum LocTypeEnum {
		/**
		 * GPS定位结果 
		 */
		GpsLocation("GPS定位结果 " , 61),
		
		/**
		 * 扫描整合定位依据失败。此时定位结果无效
		 */
		bd09ll("扫描整合定位依据失败。此时定位结果无效" , 62),
		
		/**
		 * 网络异常，没有成功向服务器发起请求。此时定位结果无效
		 */
		NetWorkError("网络异常，没有成功向服务器发起请求。此时定位结果无效" , 63),
		
		/**
		 * 定位缓存的结果
		 */
		CacheLocation("定位缓存的结果" , 65),
		
		/**
		 * 离线定位结果。通过requestOfflineLocaiton调用时对应的返回结果
		 */
		OfflineLocaiton("网络异常，离线定位结果。通过requestOfflineLocaiton调用时对应的返回结果" , 66),
		
		/**
		 * 离线定位失败。通过requestOfflineLocaiton调用时对应的返回结果
		 */
		OfflineLocaitonError("离线定位失败。通过requestOfflineLocaiton调用时对应的返回结果" , 67),
		
		/**
		 * 网络连接失败时，查找本地离线定位时对应的返回结果
		 */
		NetConnectErrorReturnOfflineLocaiton("网络连接失败时，查找本地离线定位时对应的返回结果" , 68),
		
		/**
		 * 表示网络定位结果 
		 */
		NetWorkLocation("表示网络定位结果 " , 161);
		
		private final String value;
		private final int key;
		
		private LocTypeEnum(final String value, final int key) {
			this.value = value;
			this.key = key;
		}
		public String value() {
			return this.value;
		}
		public int key() {
			return this.key;
		}
	}
	
	/**
	 * 自定义消息类型
	 */
	public static enum CustomMessageTypeEnum {
		/**
		 * 注册成功
		 */
		RegisterSuccess("注册成功" , 100001),
		
		/**
		 * 修改密码成功
		 */
		UpdatePwdSuccess("修改密码成功" , 100002),
		
		/**
		 * 快递员申请成功
		 */
		CourierApplySuccess("快递员申请成功" , 100003),
		
		/**
		 * 快递员申请失败
		 */
		CourierApplyFailed("快递员申请失败" , 100004),
		
		/**
		 * 订单竞标
		 */
		OrderBidding("订单竞标" , 200001),
		
		/**
		 * 订单过程操作
		 */
		OrderOperate("订单过程操作" , 200002),
		
		/**
		 * 系统消息
		 */
		AdminMessage("系统消息" , 300001),
		
		/**
		 * 用户消息
		 */
		UserMessage("用户消息" , 300002),
		
		/**
		 * 网页链接
		 */
		WebView("网页链接" , 400001);
		
		private final String value;
		private final int key;
		
		private CustomMessageTypeEnum(final String value, final int key) {
			this.value = value;
			this.key = key;
		}
		public String value() {
			return this.value;
		}
		public int key() {
			return this.key;
		}
	}
	
	/**
	 * 推送目标
	 */
	public static enum PushTargetEnum {
		/**
		 * 快递员端
		 */
		CourierApp("快递员端" , 1),
		
		/**
		 * 用户端
		 */
		CustomerApp("用户端" , 2);
		
		private final String value;
		private final int key;
		
		private PushTargetEnum(final String value, final int key) {
			this.value = value;
			this.key = key;
		}
		public String value() {
			return this.value;
		}
		public int key() {
			return this.key;
		}
	}
	
	/**
	 * 推送类别
	 */
	public static enum PushWayEnum {
		/**
		 * 通知
		 */
		Notification("通知" , 1),
		
		/**
		 * 自定义消息
		 */
		CustomMessage("自定义消息" , 2);
		
		private final String value;
		private final int key;
		
		private PushWayEnum(final String value, final int key) {
			this.value = value;
			this.key = key;
		}
		public String value() {
			return this.value;
		}
		public int key() {
			return this.key;
		}
	}
	
	/**
	 * 推送方式
	 */
	public static enum PushTypeEnum {
		/**
		 * 通过key（发送给所有用户）
		 */
		ApplicationKey("通过key" , 1),
		
		/**
		 * 通过IMEI
		 */
		IMEI("通过IMEI" , 2),
		
		/**
		 * 通过标识
		 */
		Alias("通过标识", 3),
		
		/**
		 * 通过特征
		 */
		Tag("通过特征" , 4);
		
		private final String value;
		private final int key;
		
		private PushTypeEnum(final String value, final int key) {
			this.value = value;
			this.key = key;
		}
		public String value() {
			return this.value;
		}
		public int key() {
			return this.key;
		}
	}
	
	/**
	 * 推送错误code
	 */
	public static enum ErrorCodeEnum {
		/**
		 * 没有错误，发送成功
		 */
		NOERROR(0,"没有错误，发送成功"),
		
		/**
		 * 系统内部错误
		 */
		SystemError(10,"系统内部错误"),
		
		/**
		 * 不支持GET请求
		 */
		NotSupportGetMethod(1001,"不支持GET请求"),
		
		/**
		 * 缺少必须参数
		 */
		MissingRequiredParameters(1002,"缺少必须参数"),
		
		/**
		 * 参数值不合法
		 */
		InvalidParameter(1003,"参数值不合法"),
		
		/**
		 * 验证失败
		 */
		ValidateFailed(1004,"验证失败"),
		
		/**
		 * 消息体太大
		 */
		DataTooBig(1005,"消息体太大"),
		
		/**
		 * IMEI不合法
		 */
		InvalidIMEI(1007,"IMEI不合法"),
		
		/**
		 * appkey不合法
		 */
		InvalidAppKey(1008,"appkey不合法"),
		
		/**
		 * msg_content不合法
		 */
		InvalidMsgContent(1010,"msg_content不合法"),
		
		/**
		 * 没有满足条件的推送目标
		 */
		InvalidPush(1011,"没有满足条件的推送目标"),
		
		/**
		 * IOS不支持自定义消息
		 */
		CustomMessgaeNotSupportIOS(1012,"IOS不支持自定义消息");
		
		private final int key;
		private final String value;
		
		private ErrorCodeEnum(final int key,final String value) {
			this.key = key;
			this.value = value;
		}
		public int key() {
			return this.key;
		}
		public String value() {
			return this.value;
		}
	}
	
	/**
	 * 飞米子账户类型
	 */
	public static enum FmAccountTypeEnum {
		/**
		 * alipay（支付宝）
		 */
		alipay("支付宝" , 1),
		
		/**
		 * tenpay 财付通
		 */
		tenpay("财付通" , 2);
		
		private final String value;
		private final int key;
		
		private FmAccountTypeEnum(final String value, final int key) {
			this.value = value;
			this.key = key;
		}
		public String value() {
			return this.value;
		}
		public int key() {
			return this.key;
		}
	}
	/**
	 * 飞米账户流水类型
	 */
	public static enum FmAccountLogTypeEnum {
		/**
		 * 充值
		 */
		topup("充值" , 1),
		
		/**
		 * 服务费
		 */
		service("服务费" , 2),
		
		/**
		 *消费
		 */
		consume("消费" , 4),
		
		/**
		 *订单预扣款
		 */
		deduct("订单预扣款" , 5),
		
		/**
		 *赔款
		 */
		reparations("赔款" , 6),
		
		/**
		 * 其他
		 */
		other("其他" , 7),
		
		/**
		 * 推荐人收入
		 */
		recommend("推荐人收入" , 8),
		/**
		 * 提现
		 */
		withdraw("用户提现" , 9),
		/**
		 * 订单支付
		 */
		orderpay("订单支付" , 10);
		
		private final String value;
		private final int key;
		
		private FmAccountLogTypeEnum(final String value, final int key) {
			this.value = value;
			this.key = key;
		}
		public String value() {
			return this.value;
		}
		public int key() {
			return this.key;
		}
	}
	/**
	 * 个人账户类型，用以每一笔流水或者个人账户
	 */
	public static enum AccountLogStateEnum {
		/**
		 * 
		 */
		normal("正常" , 1),
		
		/**
		 * 
		 */
		freeze("待结算" , 2);
		
		private final String value;
		private final int key;
		
		private AccountLogStateEnum(final String value, final int key) {
			this.value = value;
			this.key = key;
		}
		public String value() {
			return this.value;
		}
		public int key() {
			return this.key;
		}
	}
}
