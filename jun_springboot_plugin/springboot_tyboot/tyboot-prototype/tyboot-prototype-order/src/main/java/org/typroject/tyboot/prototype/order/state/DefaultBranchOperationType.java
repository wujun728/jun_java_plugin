package org.typroject.tyboot.prototype.order.state;

/**
 * 
 * <pre>
 * 
 *  File: DefaultBranchOperationType.java
 * 
 *  Description:
 *  TODO
 * 
 *  Notes:
 *  DefaultBranchOperationType.java  tyrest\magintursh
 * 
 *  Revision History
 *  &lt;Date&gt;,			&lt;Who&gt;,			&lt;What&gt;
 *  2016年9月29日					magintursh				   Initial.
 *
 * </pre>
 */
public enum DefaultBranchOperationType implements BranchOperationType
{
	/**
	 * 结账/支付
	 */
	CHECKOUT("结账","",""), 
	
	/**
	 * 取消，即提前终止流程流转
	 */
	CANCEL("取消","",""),
	
	/**
	 * 退款
	 */
	REFUND("","","");
	
	
	
	
	
	private String operationName;
	private String oprationRuleHandler;
	private String branchHandler;
	
	private DefaultBranchOperationType(String operationName,String oprationRuleHandler,String branchHandler)
	{
		this.operationName = operationName;
		this.oprationRuleHandler = oprationRuleHandler;
		this.branchHandler = branchHandler;
	}
	
	
	

	@Override
	public String getOperationName()
	{
		
		return this.operationName;
	}

	@Override
	public String getOperationCode()
	{
		
		return this.name();
	}

	@Override
	public String getOprationRuleHandler()
	{
		return this.oprationRuleHandler;
	}




	@Override
	public String getBranchHandler()
	{
		return this.branchHandler;
	}

}

/*
*$Log: av-env.bat,v $
*/