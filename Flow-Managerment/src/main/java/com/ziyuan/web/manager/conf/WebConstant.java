package com.ziyuan.web.manager.conf;


public class WebConstant {
	public enum ROLES {
		ADMIN("ROLE_ADMIN"),
		BASEINFO("ROLE_BASEINFO"),
		OPERATOR("ROLE_OPERATOR"),
		FINANCIAL("ROLE_FINANCIAL"),
		VERIFY("ROLE_VERIFY"),
		MAINTENANCE("ROLE_MAINTENANCE"),
		DISTRIBUTOR("ROLE_DISTRIBUTOR"),
		DISTRIBUTOR_SELFFLOW("ROLE_DIS_SELFFLOW");
		
		public String role;
		
		private ROLES(String role) {
			this.role = role;
		}
	}
	
	public static final String AUTHTYPE_PLATFORM = "platform";
	public static final String AUTHTYPE_INTERFACE = "interface";
	public static final String AUTHTYPE_DISTRIBUTOR = "distributor";
	
	public static String DWI_ORDER_INTERFACE;
	public static String DWI_ORDER_INTERFACE_TEST;
	

}
