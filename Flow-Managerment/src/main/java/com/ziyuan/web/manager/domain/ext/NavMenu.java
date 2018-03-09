package com.ziyuan.web.manager.domain.ext;

import java.util.List;

public class NavMenu {
	private String text;
	private String href;
	private List<NavMenu> menus;
	private boolean divider;
	private String role;
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getHref() {
		return href;
	}
	public void setHref(String href) {
		this.href = href;
	}
	public List<NavMenu> getMenus() {
		return menus;
	}
	public void setMenus(List<NavMenu> menus) {
		this.menus = menus;
	}
	public boolean isDivider() {
		return divider;
	}
	public void setDivider(boolean divider) {
		this.divider = divider;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
}
