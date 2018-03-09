package com.shziyuan.flow.global.util.classloader;

import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Consumer;

import com.shziyuan.flow.global.util.LoggerUtil;

/**
 * 用于缓存管理动态加载类的支持
 * 如果由Spring管理实例,需要将此类注册为单例
 * 非Spring环境,自行保证单例
 * @author james.hu
 *
 */
public class DynamicClassManager<T> {
	
	/**
	 * 存储动态加载类的缓存
	 * 名称 - 信息类
	 */
	private final Map<String, ObjectStru<? extends T>> clsMap;
	
	private Lock lock = new ReentrantLock(false);		// 线程锁
	private Condition loadCondition = lock.newCondition();		// 加载锁条件
	
	private boolean isLoadNewClass = false;		// 重新加载标记
	
	private final String basePath;		// 外部加载路径
	
	public DynamicClassManager(String basePath) {
		this.clsMap = new HashMap<>();
		this.basePath = basePath;
	}
	
	/**
	 * 注册一个动态实例
	 * @param name		键名,自定义
	 * @param fullClassname		被注册动态类的全限定名
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws ClassNotFoundException
	 */
	public <G extends T> void register(String name,String fullClassname) throws InstantiationException, IllegalAccessException, ClassNotFoundException {
		ObjectStru<G> os = new ObjectStru<>(fullClassname);
		this.clsMap.put(name, os);
	}
	
	/**
	 * 注册一个动态实例
	 * @param name		键名,自定义
	 * @param fullClassname		被注册动态类的全限定名
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws ClassNotFoundException
	 */
	public <G extends T> void register(String name,String fullClassname,Consumer<G> doInit) throws InstantiationException, IllegalAccessException, ClassNotFoundException {
		ObjectStru<G> os = new ObjectStru<>(fullClassname,doInit);
		this.clsMap.put(name, os);
	}
	
	/**
	 * 获取缓存对象
	 * @param name
	 * @return
	 * @throws InterruptedException
	 */
	public T get(String name) throws InterruptedException {
		lock.lock();
		
		try {
			if(isLoadNewClass) {
				loadCondition.await();
			}
			
			ObjectStru<? extends T> os = clsMap.get(name);
			return os.instance;
		} finally {
			lock.unlock();
		}
	}
	
	/**
	 * 重新加载缓存对象
	 */
	public void reload() {
		lock.lock();
		
		try {
			this.isLoadNewClass = true;
			
			for(ObjectStru<? extends T> os : clsMap.values()) {
				try {
					os.resetInstance();
				} catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
					LoggerUtil.error.error(e.getMessage(),e);
				}
			}
			loadCondition.signalAll();
		} finally {
			this.isLoadNewClass = false;
			
			lock.unlock();
		}
	}
	
	/**
	 * 缓存内部对象信息类
	 * 标识被缓存对象信息
	 * @author james.hu
	 *
	 * @param <G>
	 */
	private class ObjectStru<G extends T> {
		G instance;		// 缓存对象的实例
		Class<G> clazz;		// 缓存对象的Class定义
		String fullClassname;		// 缓存对象的全限定名
		Consumer<G> doInit;		// 用于初始化的回调函数
		
		public ObjectStru(String fullname) throws InstantiationException, IllegalAccessException, ClassNotFoundException {
			this.fullClassname = fullname;
			this.resetInstance();
		}
		public ObjectStru(String fullname,Consumer<G> doInit) throws InstantiationException, IllegalAccessException, ClassNotFoundException {
			this.fullClassname = fullname;
			this.doInit = doInit;
			this.resetInstance();
		}
		
		void resetInstance() throws ClassNotFoundException, InstantiationException, IllegalAccessException {
			DynamicClassLoader dynamicClassLoader = new DynamicClassLoader(basePath);
			Class<G> clazz = (Class<G>) dynamicClassLoader.loadClass(fullClassname);
			G instance = clazz.newInstance();
			
			this.clazz = clazz;
			if(this.doInit != null)
				this.doInit.accept(instance);
			this.instance = instance;
		}
	}
}
