package com.shziyuan.flow.notification.provider.wechat;

public class AccessToken {
	private int errcode;
	private String errmsg;
	private String access_token;
	private int expires_in;
	
	private long expiresTimestamp;

	public int getErrcode() {
		return errcode;
	}

	public void setErrcode(int errcode) {
		this.errcode = errcode;
	}

	public String getErrmsg() {
		return errmsg;
	}

	public void setErrmsg(String errmsg) {
		this.errmsg = errmsg;
	}

	public String getAccess_token() {
		return access_token;
	}

	public void setAccess_token(String access_token) {
		this.access_token = access_token;
	}

	public int getExpires_in() {
		return expires_in;
	}

	public void setExpires_in(int expires_in) {
		this.expires_in = expires_in;
		long now = System.currentTimeMillis();
		setExpiresTimestamp(now + (expires_in * 1000) - 1000);
	}

	public long getExpiresTimestamp() {
		return expiresTimestamp;
	}

	public void setExpiresTimestamp(long expiresTimestamp) {
		this.expiresTimestamp = expiresTimestamp;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("AccessToken [errcode=").append(errcode).append(", errmsg=").append(errmsg)
				.append(", access_token=").append(access_token).append(", expires_in=").append(expires_in)
				.append(", expiresTimestamp=").append(expiresTimestamp).append("]");
		return builder.toString();
	}
}
