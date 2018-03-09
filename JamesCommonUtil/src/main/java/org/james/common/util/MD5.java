package org.james.common.util;

import java.security.*;

/**
 * MD5编码工具
 * @author HUYAO
 *
 */
public class MD5 {
	private final static String[] hexDigits = { "0", "1", "2", "3", "4", "5",
			"6", "7", "8", "9", "a", "b", "c", "d", "e", "f" };

	private static MessageDigest md;
	
	static {
		try {
			md = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 转换字节数组为16进制字串
	 * 
	 * @param b
	 *            字节数组
	 * @return 16进制字串
	 */
	public static String byteArrayToHexString(byte[] b) {
		StringBuffer resultSb = new StringBuffer();
		for (int i = 0; i < b.length; i++) {
			resultSb.append(byteToHexString(b[i]));
		}
		return resultSb.toString();
	}

	private static String byteToHexString(byte b) {
		int n = b;
		if (n < 0)
			n = 256 + n;
		int d1 = n / 16;
		int d2 = n % 16;
		return hexDigits[d1] + hexDigits[d2];
	}

	public static String md5(byte[] data) {
		String resultString = null;
		try {
			resultString = new String();
			resultString = byteArrayToHexString(md.digest(data));
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
		return resultString;
	}
	public static String compile(String origin) {
		return md5(origin.getBytes());
	}
}