package org.james.common.util.profileLoader;

public class FieldTypeDef {
	Class<?> clazz;
	FieldConsumer consumer;
	public FieldTypeDef(Class<?> clazz, FieldConsumer consumer) {
		this.clazz = clazz;
		this.consumer = consumer;
	}
}
