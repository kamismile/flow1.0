package com.shziyuan.flow.queue.orderSubmit.service.executor;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import com.shziyuan.flow.global.executor.IExecutorResultServiceFuture;
import com.shziyuan.flow.queue.base.interactive.BaseInterfaceRequestResponse;

@Deprecated
public class SupplierSubmitExecutorService { //extends AbstractCompletionMultiThreadExecutor<BaseInterfaceRequestResponse>{

	private BlockingQueue<BaseInterfaceRequestResponse> queue = new LinkedBlockingQueue<>(100);
	
	private IExecutorResultServiceFuture<BaseInterfaceRequestResponse> future;
	
	private boolean readWhileFlag = true;
	
	public void add(BaseInterfaceRequestResponse wrap) {
		this.queue.add(wrap);
	}
	
	public SupplierSubmitExecutorService(IExecutorResultServiceFuture<BaseInterfaceRequestResponse> future) {
		this.future = future;
	}
	
	@PostConstruct
	public void init() {
		ReadThread rh = new ReadThread();
		rh.start();
	}
	
	@PreDestroy
	public void destroy() {
		this.readWhileFlag = false;
	}
	
	public class ReadThread extends Thread {
		private final TimeUnit defaultUnit = TimeUnit.SECONDS;
		
		public ReadThread() {
			this.setDaemon(true);
		}
		
		@Override
		public void run() {
			while(readWhileFlag) {
				try {
					BaseInterfaceRequestResponse resp = queue.poll(1, defaultUnit);
					if(resp != null) {
						System.out.println("income read thread");
						future.handler(resp);
					}
				} catch (InterruptedException e) {
//					LoggerUtil.error.error(SupplierSubmitExecutorService.class.getName(),e);
				} catch (Exception e) {
					future.onException(e);
				}
			}
		}
	}

//	public SupplierSubmitExecutorService(IExecutorResultServiceFuture<BaseInterfaceRequestResponse> future,int poolThreadNum) {
//		super("供应商提交任务线程池", future, poolThreadNum);
//	}
}
