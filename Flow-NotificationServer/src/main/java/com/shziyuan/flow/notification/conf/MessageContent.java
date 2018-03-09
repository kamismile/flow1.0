package com.shziyuan.flow.notification.conf;

/**
 * 配置 - 消息模板结构
 * @author james.hu
 *
 */
public class MessageContent {
	private String title;		// 模板title
	private String template;	// 模板内容
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getTemplate() {
		return template;
	}
	public void setTemplate(String template) {
		this.template = template;
	}
}
