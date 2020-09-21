package cn.rjzjh.commons.util.thread;

/****
 * 如果线程被池给取消需要做的事情，如果提交给池的线程实现这个接口，那么当池拒绝这个线程时会调用些接口
 * 
 * @author Administrator
 * 
 */
public interface ICancelHandle {
	public void doCancle();
}
