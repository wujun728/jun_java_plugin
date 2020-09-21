/*   
 * Project: OSMP
 * FileName: ExecutorWrapper.java
 * version: V1.0
 */
package com.osmp.tools.commons;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * Description:
 * 
 * @author: wangkaiping
 * @date: 2014年9月11日 上午11:05:30
 */

public interface ExecutorWrapper {

	<T extends Runnable> void execute(T... commands);

	void execute(Collection<? extends Runnable> commands);

	<T extends Runnable> void invoke(T... commands) throws InterruptedException, ExecutionException;

	void invoke(Collection<? extends Runnable> commands) throws InterruptedException, ExecutionException;

	<T> List<Future<T>> invokeAll(Callable<T>... commands) throws InterruptedException;

	<T> List<Future<T>> invokeAll(Collection<Callable<T>> commands) throws InterruptedException;

	void printThreadPoolInfo();

}
