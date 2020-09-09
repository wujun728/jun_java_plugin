package com.github.ghsea.rpc.core;

import io.netty.util.Signal;
import io.netty.util.internal.ThrowableUtil;

import java.util.Date;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
import java.util.concurrent.locks.ReentrantLock;

import com.github.ghsea.rpc.core.message.RpcResponseMsg;
import com.google.common.base.Preconditions;

/**
 * @see io.netty.util.concurrent.DefaultPromise
 * @author ghsea 2017-4-16下午8:16:41
 */
public class DefaultRpcResponseFuture implements RpcResponseFuture {

	private Date createTime;

	private volatile Object result;
	private static volatile AtomicReferenceFieldUpdater<DefaultRpcResponseFuture, Object> RESULT_UPDATER = AtomicReferenceFieldUpdater
			.newUpdater(DefaultRpcResponseFuture.class, Object.class, "result");

	private static final Signal SUCCESS = Signal.valueOf(DefaultRpcResponseFuture.class, "SUCCESS");
	private static final Signal UNCANCELLABLE = Signal.valueOf(DefaultRpcResponseFuture.class, "UNCANCELLABLE");

	private static final CauseHolder CANCELLATION_CAUSE_HOLDER = new CauseHolder(ThrowableUtil.unknownStackTrace(
			new CancellationException(), DefaultRpcResponseFuture.class, "cancel(...)"));

	private AtomicBoolean callbackSetted = new AtomicBoolean(false);
	private volatile RpcResponseFutureCallback callback;
	private volatile Executor callbackExecutor;

	private ReentrantLock lock = new ReentrantLock();

	public DefaultRpcResponseFuture() {
		this.createTime = new Date();
	}

	@Override
	public boolean cancel(boolean mayInterruptIfRunning) {
		if (RESULT_UPDATER.compareAndSet(this, null, CANCELLATION_CAUSE_HOLDER)) {
			return true;
		}
		return false;
	}

	@Override
	public boolean isCancelled() {
		return isCancelled0(result);
	}

	private static boolean isCancelled0(Object result) {
		return result instanceof CauseHolder && ((CauseHolder) result).cause instanceof CancellationException;
	}

	@Override
	public boolean isDone() {
		return isDone0(result);
	}

	private boolean isDone0(Object result) {
		return result != null && result != UNCANCELLABLE;
	}

	@Override
	public Object get() throws InterruptedException, ExecutionException {
		await();
		return getNow();
	}

	@Override
	public Object get(long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
		if (await(timeout, unit)) {
			Throwable cause = cause();
			if (cause == null) {
				return getNow();
			}
		}

		throw new TimeoutException();
	}

	private Object getNow() throws ExecutionException {
		Throwable cause = cause();
		if (cause != null) {
			if (cause instanceof CancellationException) {
				throw (CancellationException) cause;
			}
			throw new ExecutionException(cause);
		}

		return this.result;
	}

	@Override
	public Date getCreateTime() {
		return this.createTime;
	}

	@Override
	public void operationComplete(final RpcResponseMsg response) {
		boolean isLocked = lock.tryLock();
		if (isLocked) {
			try {
				if (response.getCause() != null) {
					this.setFailure(response.getCause());
					this.cancel(true);
				} else {
					this.setSuccess(response.getResult());
				}
			} finally {
				lock.unlock();
			}
		} else {
			new IllegalStateException("is already executing operationComplete.");
		}
	}

	@Override
	public void addListener(RpcResponseFutureCallback callback, Executor executor) {
		if (callbackSetted.compareAndSet(false, true)) {
			Preconditions.checkNotNull(callback);
			Preconditions.checkNotNull(executor);
			this.callback = callback;
			this.callbackExecutor = executor;
			return;
		}

		throw new IllegalStateException("Listener is already setted and it can be setted only once.");
	}

	private void notifyListener() {
		boolean isLocked = lock.tryLock();
		if (isLocked) {
			try {
				if (callbackExecutor != null && callback != null) {
					final RpcResponseFutureCallback callback = this.callback;
					if (this.isSuccess()) {
						this.callbackExecutor.execute(new Runnable() {
							@Override
							public void run() {
								callback.onSuccess();
							}
						});
					} else {
						this.callbackExecutor.execute(new Runnable() {
							@Override
							public void run() {
								callback.onFail();
							}
						});
					}
				}
			} finally {
				lock.unlock();
			}
		}
	}

	private void await() throws InterruptedException {
		if (isDone()) {
			return;
		}

		if (Thread.interrupted()) {
			throw new InterruptedException(toString());
		}

		// checkDeadLock();

		synchronized (this) {
			while (!isDone()) {
				this.wait();
			}
		}
	}

	private boolean await(long timeout, TimeUnit unit) throws InterruptedException {
		return await0(unit.toNanos(timeout), true);
	}

	private boolean await0(long timeoutNanos, boolean interruptable) throws InterruptedException {
		if (isDone()) {
			return true;
		}

		if (timeoutNanos <= 0) {
			return isDone();
		}

		if (interruptable && Thread.interrupted()) {
			throw new InterruptedException(toString());
		}

		long startTime = System.nanoTime();
		long waitTime = timeoutNanos;
		boolean interrupted = false;
		try {
			while (true) {
				synchronized (this) {
					if (isDone()) {
						return true;
					}
					try {
						wait(waitTime / 1000000, (int) (waitTime % 1000000));
					} catch (InterruptedException e) {
						if (interruptable) {
							throw e;
						} else {
							interrupted = true;
						}
					}
				}
				if (isDone()) {
					return true;
				} else {
					waitTime = timeoutNanos - (System.nanoTime() - startTime);
					if (waitTime <= 0) {
						return isDone();
					}
				}
			}
		} finally {
			if (interrupted) {
				Thread.currentThread().interrupt();
			}
		}
	}

	public void setSuccess(Object result) {
		if (setSuccess0(result)) {
			notifyListener();
			return;
		}

		throw new IllegalStateException("complete already: " + this);
	}

	private boolean setSuccess0(Object result) {
		return setValue0(result == null ? SUCCESS : result);
	}

	private boolean setValue0(Object objResult) {
		if (RESULT_UPDATER.compareAndSet(this, null, objResult)
				|| RESULT_UPDATER.compareAndSet(this, UNCANCELLABLE, objResult)) {
			checkNotifyWaiters();
			return true;
		}
		return false;
	}

	private boolean isSuccess() {
		Object result = this.result;
		return result != null && result != UNCANCELLABLE && !(result instanceof CauseHolder);
	}

	private void checkNotifyWaiters() {
		synchronized (this) {
			notifyAll();
		}
	}

	public void setFailure(Throwable cause) {
		if (setFailure0(cause)) {
			notifyListener();
			return;
		}
		throw new IllegalStateException("complete already: " + this, cause);
	}

	private boolean setFailure0(Throwable cause) {
		return setValue0(cause);
	}

	private Throwable cause() {
		Object result = this.result;
		return (result instanceof Throwable) ? (Throwable) result : null;
	}

	private static final class CauseHolder {
		final Throwable cause;

		CauseHolder(Throwable cause) {
			this.cause = cause;
		}
	}

}