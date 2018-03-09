/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ziyuan.web.manager.utils;

/**
 *
 * @author yangyl
 */
public class ManagermentException extends Exception {

    private int code;

    private ManagermentException() {
    }

    public ManagermentException(String ex) {
    	super(ex);
    }

    private ManagermentException(Throwable ex) {
        throw new RuntimeException("Unsupported");
    }

    public ManagermentException(String ex, int code) {
        super(ex);
        this.code = code;
    }

    public ManagermentException(Throwable ex, int code) {
        super(ex);
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
