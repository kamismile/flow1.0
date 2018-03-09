package com.shziyuan.flow.global.executor;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;

public interface ICompletionExecutorService<RESP> extends ExecutorService {
	
	public void submitCompletionTask(Callable<RESP> task);
	
	public int getActiveCount();
	
	public long getTaskCount();
}
