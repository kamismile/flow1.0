package com.shziyuan.flow.global.executor;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 默认的线程工厂
 * @author james.hu
 *
 */
public class DefaultThreadFactory implements ThreadFactory {
    private final ThreadGroup group;
    private final AtomicInteger threadNumber = new AtomicInteger(1);
    private final String namePrefix;

    private Thread.UncaughtExceptionHandler exceptionHandler;
    
    public DefaultThreadFactory(String threadGroupName,Thread.UncaughtExceptionHandler exceptionHandler) {
        this.group = new ThreadGroup(threadGroupName);
        
        this.namePrefix = group.getName() + "-thread-";
        
        this.exceptionHandler = exceptionHandler;
    }
    
    public DefaultThreadFactory(ThreadGroup group,String executorName,Thread.UncaughtExceptionHandler exceptionHandler) {
		this.group = group;
		this.namePrefix = group.getName() + "-" + executorName + "-thread-";
   
		this.exceptionHandler = exceptionHandler;
	}

    @Override
	public Thread newThread(Runnable r) {
    	
        Thread t = new Thread(group, r,
                              namePrefix + threadNumber.getAndIncrement(),
                              0);
        if (t.isDaemon())
            t.setDaemon(false);
        if (t.getPriority() != Thread.NORM_PRIORITY)
            t.setPriority(Thread.NORM_PRIORITY);
        
        t.setUncaughtExceptionHandler(exceptionHandler);
        return t;
    }
}