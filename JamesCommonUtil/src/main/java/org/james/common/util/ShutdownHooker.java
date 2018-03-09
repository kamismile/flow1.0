package org.james.common.util;

import javax.annotation.PostConstruct;

/**
 * 用于程序关闭的钩子 
 * 继承此类,并实现doFinal()方法
 * @author HUYAO
 *
 */
public class ShutdownHooker {
	protected void doFinal() {};
	
	/**
	 * 调用此方法注册关闭事件钩子
	 */
	@PostConstruct
	public final void registerShutdownHooker() {
		Runtime.getRuntime().addShutdownHook(new Thread() {
			@Override
			public void run() {
				doFinal();
			}
		});
	}
}
