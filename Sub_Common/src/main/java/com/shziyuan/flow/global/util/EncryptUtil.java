package com.shziyuan.flow.global.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class EncryptUtil {
	public static String sha1(String source) {
		String algorithm = "SHA";

        // Calculate hash value
        MessageDigest md = null;
		try {
			md = MessageDigest.getInstance(algorithm);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
        md.update(source.getBytes());
        byte[] bytes = md.digest();

        // Print out value in Base64 encoding
        sun.misc.BASE64Encoder base64encoder = new sun.misc.BASE64Encoder();
        String hash = base64encoder.encode(bytes);
        return '{'+algorithm+'}'+hash;
	}
}
