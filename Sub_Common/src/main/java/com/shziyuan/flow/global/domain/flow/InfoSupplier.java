package com.shziyuan.flow.global.domain.flow;

import java.sql.Timestamp;

public class InfoSupplier extends BaseDomain{

	private static final long serialVersionUID = 2778193531771090296L;

	private int id;
	private String name;
	private int report_mode;
	private Timestamp create_time;
	private boolean enabled;
	private Timestamp update_time;
	private String update_user;
	private int process_mode;
	private String if_attribute;
	private String pclass;
	private boolean cache_mode;
	private String platform_mark;
	
	public static final int REPORT_MODE_ACTIVE = 0;
	public static final int REPORT_MODE_PASSIVE = 1;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public Timestamp getCreate_time() {
		return create_time;
	}
	public void setCreate_time(Timestamp create_time) {
		this.create_time = create_time;
	}
	public boolean isEnabled() {
		return enabled;
	}
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	public Timestamp getUpdate_time() {
		return update_time;
	}
	public void setUpdate_time(Timestamp update_time) {
		this.update_time = update_time;
	}
	public String getUpdate_user() {
		return update_user;
	}
	public void setUpdate_user(String update_user) {
		this.update_user = update_user;
	}
	public int getProcess_mode() {
		return process_mode;
	}
	public void setProcess_mode(int process_mode) {
		this.process_mode = process_mode;
	}
	public String getIf_attribute() {
		return if_attribute;
	}
	public void setIf_attribute(String if_attribute) {
		this.if_attribute = if_attribute;
	}
	public String getPclass() {
		return pclass;
	}
	public void setPclass(String pclass) {
		this.pclass = pclass;
	}
	public int getReport_mode() {
		return report_mode;
	}
	public void setReport_mode(int report_mode) {
		this.report_mode = report_mode;
	}
	public boolean isCache_mode() {
		return cache_mode;
	}
	public void setCache_mode(boolean cache_mode) {
		this.cache_mode = cache_mode;
	}
	public String getPlatform_mark() {
		return platform_mark;
	}
	public void setPlatform_mark(String platform_mark) {
		this.platform_mark = platform_mark;
	}
}
