package com.shziyuan.flow.global.executor;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.util.Assert;

import com.shziyuan.flow.global.util.LoggerUtil;

/**
 * 包装含有线程池 , 结果缓存 , 结果读取的线程池
 * @author james.hu
 *
 * @param <T>
 */
public class CompletionExecutorService<RESP> implements ICompletionExecutorService<RESP>,DisposableBean{
	public static final byte MODE_SINGLE = 0x00;
	public static final byte MODE_MULTI = 0x01;
	
	private ExecutorService pool;						// 处理线程池
	private ThreadPoolExecutor poolAlias;				// 多线程线程池的别名
	private CompletionService<RESP> completionPool;		// 线程池结果缓存
	private ScheduledExecutorService resultService;		// 线程池结果读取
	
	private byte mode;
	
	
	private int resultScheduleRunInitDelay = 3000;		// 结果处理器启动延迟 单位毫秒
	private int resultSchedulePeriod = 10;				// 每次提取任务延迟间隔 单位毫秒
	private int resultFutureWaitTime = 5000;			// 获取结果线程最长等待超时时间
	
	public CompletionExecutorService(ThreadFactory threadFactory,IExecutorResultServiceFuture<RESP> future) {
		this.pool = Executors.newSingleThreadExecutor(threadFactory);
		this.completionPool = new ExecutorCompletionService<RESP>(this.pool);
		this.resultService = Executors.newSingleThreadScheduledExecutor(threadFactory);
		
		this.mode = MODE_SINGLE;
		
		LoggerUtil.console.info("生成[单线程]自读取结果处理线程池");
		
		registerResultFuture(future);
	}
	
	public CompletionExecutorService(ThreadFactory threadFactory,int threadNum,IExecutorResultServiceFuture<RESP> future) {
		this.pool = Executors.newFixedThreadPool(threadNum, threadFactory);
		this.poolAlias = (ThreadPoolExecutor) pool;
		this.completionPool = new ExecutorCompletionService<RESP>(this.pool);
		this.resultService = Executors.newScheduledThreadPool(threadNum,threadFactory);
		
		this.mode = MODE_MULTI;
		
		LoggerUtil.console.info("生成[多线程]自读取结果处理线程池 处理线程:{}",threadNum);
		registerResultFuture(future);
	}
	
	protected void registerResultFuture(IExecutorResultServiceFuture<RESP> future) {
		HttpSubmitResultTask singleSubmitResultTask = new HttpSubmitResultTask(future);
		// 启动供应商提交结果处理器
		this.resultService.scheduleAtFixedRate(singleSubmitResultTask, resultScheduleRunInitDelay, resultSchedulePeriod, TimeUnit.MILLISECONDS);
		LoggerUtil.console.info("自读取结果处理线程池 注册结果处理器 {} (启动延迟:{},间隔:{})",future.getClass().getSimpleName(),resultScheduleRunInitDelay,resultSchedulePeriod);
	}
	
	@Override
	public void submitCompletionTask(Callable<RESP> task) {
		this.completionPool.submit(task);
	}
	
	public void destroy() throws Exception {
		this.pool.shutdown();
		this.resultService.shutdown();
		
		LoggerUtil.console.info("销毁自读取结果处理线程池");
	}
	
	
	public int getActiveCount() {
		Assert.isTrue(mode == MODE_MULTI, "方法只支持多线程池");
		return poolAlias.getActiveCount();
	}
	
	public long getTaskCount() {
		Assert.isTrue(mode == MODE_MULTI, "方法只支持多线程池");
		return poolAlias.getTaskCount();
	}
	
	private class HttpSubmitResultTask implements Runnable {
		// 指定的返回数据处理器
		private IExecutorResultServiceFuture<RESP> resultService;
		
		public HttpSubmitResultTask(IExecutorResultServiceFuture<RESP> resultService) {
			this.resultService = resultService;
		}
		
		@Override
		public void run() {
			RESP resp;
			try {
				// 获取缓存的结果
				Future<RESP> future = completionPool.poll(resultFutureWaitTime,TimeUnit.MILLISECONDS);
				// 每5秒超时,进入下一个任务
				if(future != null) {
					resp = future.get();
					// 由外部处理器处理返回数据
					resultService.handler(resp);
				}
			} catch (InterruptedException | ExecutionException e) {
				// 处理异常
				resultService.onException(e);
			}
		}
	}

	public int getResultScheduleRunInitDelay() {
		return resultScheduleRunInitDelay;
	}

	public void setResultScheduleRunInitDelay(int resultScheduleRunInitDelay) {
		this.resultScheduleRunInitDelay = resultScheduleRunInitDelay;
	}

	public int getResultSchedulePeriod() {
		return resultSchedulePeriod;
	}

	public void setResultSchedulePeriod(int resultSchedulePeriod) {
		this.resultSchedulePeriod = resultSchedulePeriod;
	}

	public int getResultFutureWaitTime() {
		return resultFutureWaitTime;
	}

	public void setResultFutureWaitTime(int resultFutureWaitTime) {
		this.resultFutureWaitTime = resultFutureWaitTime;
	}

	@Override
	public void shutdown() {
		pool.shutdown();
	}

	@Override
	public List<Runnable> shutdownNow() {
		return pool.shutdownNow();
	}

	@Override
	public boolean isShutdown() {
		return pool.isShutdown();
	}

	@Override
	public boolean isTerminated() {
		return pool.isTerminated();
	}

	@Override
	public boolean awaitTermination(long timeout, TimeUnit unit) throws InterruptedException {
		return pool.awaitTermination(timeout, unit);
	}

	@Override
	public <V> Future<V> submit(Runnable task, V result) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Future<?> submit(Runnable task) {
		throw new UnsupportedOperationException();
	}

	@Override
	public <V> List<Future<V>> invokeAll(Collection<? extends Callable<V>> tasks) throws InterruptedException {
		throw new UnsupportedOperationException();
	}

	@Override
	public <V> List<Future<V>> invokeAll(Collection<? extends Callable<V>> tasks, long timeout, TimeUnit unit)
			throws InterruptedException {
		throw new UnsupportedOperationException();
	}

	@Override
	public <V> V invokeAny(Collection<? extends Callable<V>> tasks) throws InterruptedException, ExecutionException {
		throw new UnsupportedOperationException();
	}

	@Override
	public <V> V invokeAny(Collection<? extends Callable<V>> tasks, long timeout, TimeUnit unit)
			throws InterruptedException, ExecutionException, TimeoutException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void execute(Runnable command) {
		throw new UnsupportedOperationException();
	}

	@Override
	public <V> Future<V> submit(Callable<V> task) {
		throw new UnsupportedOperationException();
	}

	public byte getMode() {
		return mode;
	}

	public void setMode(byte mode) {
		this.mode = mode;
	}

}
