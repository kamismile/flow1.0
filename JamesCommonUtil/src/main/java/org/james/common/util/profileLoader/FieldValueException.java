package org.james.common.util.profileLoader;

public class FieldValueException extends RuntimeException{
	private static final long serialVersionUID = 1L;

	public FieldValueException() {
	
	}
	
	public FieldValueException(Throwable e) {
		super(e);
	}
	
	public FieldValueException(String message) {
		super(message);
	}
	
	public FieldValueException(String message,Throwable e) {
		super(message,e);
	}
}
