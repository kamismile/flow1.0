package com.shziyuan.flow.global.util.classloader;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

/**
 * DynamicClassLoader 动态类加载器
 *
 */
public class DynamicClassLoader extends ClassLoader {

	private String basePath;
	
    public DynamicClassLoader(String basePath) {
        super(Thread.currentThread().getContextClassLoader());
        this.basePath = basePath;
    }

    @Override
    public Class<?> loadClass(String name) throws ClassNotFoundException {
        // 判断当前加载的类是否是需要动态重新加载的类，
        // 如果指定类存在于basePath路径下,由动态加载器载入
        // 假如不是就调用父ClassLoader默认加载
    	String filePath = getClassPath(name);
    	File file = new File(filePath);
    	if(file.exists())
    		return findClass(file,name);
    	else
    		return super.loadClass(name, false);
    }

    /**
     * 根据类名查找class
     *
     * @param fullClassPath 类全路径（包）
     * @return
     * @throws ClassNotFoundException
     */
    protected Class<?> findClass(File classfile,String fullClassPath)
            throws ClassNotFoundException {
        byte[] raw = readClassBytes(classfile);
        // definClass方法参数说明：name：类包的全路径如com.lkb.sb.client.shanghaiC.ShangHaiLoginClient
        //                         b：读取的class文件的byte数组
        //                         off：从byte数组中读取的索引
        //                         len：从byte数组中读取的长度
        // 注：假如此类中有引入别的class类，如com.lkb.sb.client.BaseClient，循环执行findClass方法
        Class<?> clazz = defineClass(fullClassPath, raw, 0, raw.length);
        // 连接class
        resolveClass(clazz);
        return clazz;
    }

    /**
     * 读取class
     *
     * @param fullClassPath
     * @return
     */
    private byte[] readClassBytes(File classfile) {
        byte[] raw = null;
        InputStream stream = null;
        try {
            stream = new FileInputStream(classfile);
            raw = new byte[(int) classfile.length()];
            stream.read(raw);
        } catch (Exception e) {

        } finally {
            try {
                stream.close();
            } catch (Exception e) {
            }
        }
        return raw;
    }
    
    private String getClassPath(String classname) {
    	StringBuilder sb = new StringBuilder();
    	sb.append(basePath);
    	if(!basePath.endsWith(File.separator))
    		sb.append(File.separatorChar);
    	sb.append(classname.replace('.', File.separatorChar)).append(".class");
    	return sb.toString();
    }

}