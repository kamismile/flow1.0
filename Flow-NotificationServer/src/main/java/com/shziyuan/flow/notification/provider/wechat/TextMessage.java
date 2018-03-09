package com.shziyuan.flow.notification.provider.wechat;

public class TextMessage {
	private String touser;
	private String toparty;
	private String totag;
	private String msgtype = "text";
	private int agentid;
	private MessageContent text;
	private Integer safe;
	public String getTouser() {
		return touser;
	}
	public void setTouser(String touser) {
		this.touser = touser;
	}
	public String getToparty() {
		return toparty;
	}
	public void setToparty(String toparty) {
		this.toparty = toparty;
	}
	public String getTotag() {
		return totag;
	}
	public void setTotag(String totag) {
		this.totag = totag;
	}
	public String getMsgtype() {
		return msgtype;
	}
	public void setMsgtype(String msgtype) {
		this.msgtype = msgtype;
	}
	public int getAgentid() {
		return agentid;
	}
	public void setAgentid(int agentid) {
		this.agentid = agentid;
	}
	public MessageContent getText() {
		return text;
	}
	public void setText(MessageContent text) {
		this.text = text;
	}
	public Integer getSafe() {
		return safe;
	}
	public void setSafe(Integer safe) {
		this.safe = safe;
	}
	public void setContent(String content) {
		this.text = new MessageContent();
		this.text.setContent(content);
	}
}
