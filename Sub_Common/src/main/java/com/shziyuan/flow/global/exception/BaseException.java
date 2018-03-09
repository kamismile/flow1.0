/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.shziyuan.flow.global.exception;

/**
 *
 * @author yangyl
 */
public class BaseException extends RuntimeException {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	//数据库操作，insert返回0
	public static final BaseException DB_INSERT_RESULT_0 = new BaseException(10040001, "数据库操作,insert返回0");
	
	//数据库操作，update返回0
	public static final BaseException DB_UPDATE_RESULT_0 = new BaseException(10040002, "数据库操作,update返回0");
	
	//数据操作，selectOne返回null
	public static final BaseException DB_SELECTONE_IS_NULL = new BaseException(10040003, "数据库操作,selectOne返回null");
	
	//数据操作，list返回null
	public static final BaseException DB_LIST_IS_NULL = new BaseException(10040004, "数据库操作,list返回null");
	
	//生成序列异常时
	public static final BaseException DB_GET_SEQ_NEXT_VALUE_ERROR = new BaseException(10040007, "序列生成超时");
	
	//异常码
	private int code;
	//异常信息
	private String msg;
	
	public BaseException(int code, String messageFormat,Object... args) {
		super(String.format(messageFormat, args));
		this.code = code;
		this.msg = String.format(messageFormat, args);
	}
	public BaseException() {
		// TODO Auto-generated constructor stub
		super();
	}
	public BaseException (String message, Throwable cause) {
		super(message, cause);
	}
	public BaseException(Throwable cause) {
		// TODO Auto-generated constructor stub
		super(cause);
	}
	public BaseException(String message) {
		// TODO Auto-generated constructor stub
		super(message);
	}
	public int getCode() {
		return code;
	}
	public String getMsg() {
		return msg;
	}
	
	

}
