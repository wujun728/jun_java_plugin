package cn.itcast.crm.action;

import com.opensymphony.xwork2.ActionSupport;

import cn.itcast.crm.entity.Customer;
import cn.itcast.crm.service.CustomerService;

/**
 * 文件名字:CustomerAction.java<br>
 * 文件作用:Customer的控制层<br>
 */
public class CustomerAction extends ActionSupport {

	private Long custId;

	private Customer customer;

	private CustomerService customerService;

	public void setCustomerService(CustomerService customerService) {
		this.customerService = customerService;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public Long getCustId() {
		return custId;
	}

	public void setCustId(Long custId) {
		this.custId = custId;
	}

	public String findCustomerById() {

		/**
		 * 方法作用: 注意这个地方不需要customer重新new出来 原因是上面已经定义出来了 而且给了set get 方法
		 */
		customer = customerService.findById(custId);

		System.out.println("前端传过来的客户id是：" + custId);

		return SUCCESS;
	}

}
