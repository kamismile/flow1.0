package org.james.common.spring.convert;

import java.beans.PropertyEditorSupport;
import java.sql.Timestamp;
import org.james.common.util.Util;

public class SqlTimestampPropertyEditor extends PropertyEditorSupport{
	@Override
	public String getAsText() {
		Timestamp value = (Timestamp) getValue();
		return Util.formatFulltime(value);
	}
	
	@Override
	public void setAsText(String text) throws IllegalArgumentException {
//		Date value = UtilDatetime.parseFulltime(text);
//		setValue(new Timestamp(value.getTime()));
	}
}
