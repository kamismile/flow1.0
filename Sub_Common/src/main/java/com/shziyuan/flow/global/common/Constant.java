package com.shziyuan.flow.global.common;

import java.nio.charset.Charset;

/**
 * 公用常量
 * @author yangyl
 *
 */
public class Constant {

	/**
	 * SKU包装类型
	 * @author james.hu
	 *
	 */
	public enum PKG_TYPE {
		SKU(0),
		CPU(1),
		MPU(2),
		PMPU(3);
		
		public int val;
		private PKG_TYPE(int val) {
			this.val = val;
		}
	}
	
	/**
	 * 渠道提交订单计费类型
	 */
	public enum FEE_TYPE {
		PRESENT(0),
		BANLANCE(1);
		public int val;
		private FEE_TYPE(int val) {
			this.val = val;
		}
	}
	
	/**
	 * 订单队列状态标示
	 * @author james.hu
	 *
	 */
	public enum QUEUE_STATUS {
		WAIT_FOR_PROCESS(0),
		IN_UPDATE(1),
		READED(2),
		CACHE(3);
		
		public int val;
		private QUEUE_STATUS(int val) {
			this.val = val;
		}
	}
	
	public enum SUPPLIER_REPORT_MODE {
		ACTIVE(0),
		PASSIVE(1);
		
		public int val;
		private SUPPLIER_REPORT_MODE(int val) {
			this.val = val;
		}
	}
	
	public enum ORDER_STATE {
		DWI_DISTRIBUTOR_SUBMIT_ORDER(0),
		BEFORESUBMIT(1),
		BEFORESUBMIT_ANALYSIS_QUEUE(2),
		BEFORESUBMIT_GETCONFIG_FAILD(3),
		BEFORESUBMIT_NOMORE_SUPPLIER(4),
		BEFORESUBMIT_NOT_ENOUGH_BANLANCE(5),
		BEFORESUBMIT_DECIDE_SUPPLIER(6),
		BEFORESUBMIT_CACHE(91),
		BEFORESUBMIT_REPORTPUSH_FAIL_IN_CACHE(50),
		READY_SUBMIT_TO_SUPPLIER(7),
		END_SUBMIT_TO_SUPPLIER(8),
		
		SUBMIT_SUPPLIER_RETURN(9),
		
		BEFORE_ACTIVEREPORT(10),
		BEFORE_ACTIVEREPORT_ANALYSIS_QUEUE(11),
		BEFORE_ACTIVEREPORT_GETCONFIG_FAILD(12),
		BEFORE_ACTIVEREPORT_REPORTPUSH_FAILD(51),
		BEFORE_ACTIVEREPORT_REPORTPUSH_SUCCESS(52),
		READY_ACTIVEREPORT_TO_SUPPLIER(13),
		ACTIVEREPORT_SUPPLIER_RETURN(14),
		
		ACTIVEREPORT_SUPPLIER_RETURN_SUCCESS(80),
		ACTIVEREPORT_SUPPLIER_RETURN_FAILD(81),
		ACTIVEREPORT_SUPPLIER_RETURN_HOLD(92),
		
		BEFORE_DISTRIBUTOR_PUSH(15),
		BEFORE_DISTRIBUTOR_ANALYSIS_QUEUE(16),
		BEFORE_DISTRIBUTOR_GETCONFIG_FAILD(17),
		READY_PUSH_TO_DISTRIBUTOR(18),
		PUSH_DISTRIBUTOR_RETURN(19),
		
		PASSIVEREPORT_SUPPLIER_RETURN(20),
		PASSIVE_DISTRIBUTOR_REQUEST(21),
		
		
//		ORDER_SUBMIT_TO_SUPPLIER(1),
//		ORDER_REQUEST_SUPPLIER_REPORT(2),
//		ORDER_SUCCESS_PUSH_REPORT_TO_DISTRIBUTOR(3),
//		ORDER_SUPPLIER_REPORT_WAITING(4),
//		ORDER_SUPPLIER_CACHE(5),
//		ORDER_SUPPLIER_SUBMIT_RETRY(6),
//		ORDER_SUPPLIER_SUBMIT_NEXT_SUPPLIER(7),
//		ORDER_SUPPLIER_REPORT_RETRY(8),
//		ORDER_SUPPLIER_REPORT_HOLD_LIMIT(9),
//		ORDER_SUPPLIER_HOLD_INSERT_ARCHIVE(15),
//		ORDER_SUPPLIER_REPORT_NEXT_SUPPLIER(10),
		
//		DISTRIBUTOR_REPORT_PUSH_ONSUBMIT(11),
//		DISTRIBUTOR_REPORT_PUSH_SUCCESS(12),
//		DISTRIBUTOR_REPORT_PUSH_RETRY(13),
//		DISTRIBUTOR_REPORT_PUSH_FAILD(14),

		
		ORDER_FAILD(99);
		
		
		public int val;
		private ORDER_STATE(int val) {
			this.val = val;
		}
	}
	
	public enum ORDER_STATE_PAYMENT {
		NO_PAY(0),
		ADVANCE(1),
		RETURN(2);
		
		public int val;
		private ORDER_STATE_PAYMENT(int val) {
			this.val = val;
		}
	}
	
	public enum QUEUE_MARK {
		SUPPLIER_REPORT_PUSHSUCCESS("P_SUCCESS"),
		SUPPLIER_REPORT_PUSHFAILD("P_FAILD");
		
		public String msg;
		private QUEUE_MARK(String msg) {
			this.msg = msg;
		}
	}
	
	public static class EurekaServiceName {
		public static final String InfoRelationService = "info-relation-service";
		public static final String OrderService = "order-service";
	}
	
	public enum RedisKey {
		BIND_DIS_KEY("bindDistributor"),
		INFO_DIS_KEY("infoDistributor"),
		INFO_DIS_BYCODE_KEY("infoDistributor_code"),
		INFO_SKU_KEY("infoSku"),
		BIND_SUPPLIER_NORMAL_KEY("bindSupplierNormal"),
		BIND_SUPPLIER_PRESENT_KEY("bindSupplierPresent"),
		INFO_SUPPLIER_KEY("infoSupplier"),
		INFO_SUPPLIER_KEY_ATTRMAP("infoSupplierAttrMap"),
		INFO_SUPPLIER_CODE_KEY("infoSupplierCode"),
		INFO_CITY_SUBMOBILE_KEY("infoCitySubmobile"),
		
		ORDER_CACHED("orderCached"),
		
		ORDER_STATUS_KEY("orderStatus");
				
		public String val;
		
		private RedisKey(String val) {
			this.val = val;
		}
	}
	
	/**
	 * 常量
	 */
	public static final int DEFAULT_CONNECTION_ID = 0;		// 默认队列connection_id
	public static final int DEFAULT_RETRIES_TIMES = 3;		// 默认队列重试次数
	public static final int DEFAULT_RETRIES_SENDTIME_ADDITION = 1;		// 默认重试发送间隔(分)
	public static final String DEFAULT_CONSUMER = "NOC";
	
	public static final String DEFAULT_SYSTEM_USER = "SYSTEM";
	
	public static final int REDIS_INDEX_PLATFORM = 1;
	public static final int REDIS_INDEX_DWI = 2;
	public static final int REDIS_INDEX_MANAGER = 3;
	
	public static final Charset DEFAULT_CHARSET = Charset.forName("utf-8");
}
