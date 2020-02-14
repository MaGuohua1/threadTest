package com.ma.cap9.assist;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.lang.StringUtils;

public class EncryptUtils {
	
	public static String EncryptByMD5(String strSrc) {
		return EncryptStr(strSrc, "MD5");
	}
	public static String EncryptBySHA1(String strSrc) {
		return EncryptStr(strSrc, "SHA-1");
	}
	public static String EncryptBySHA256(String strSrc) {
		return EncryptStr(strSrc, "SHA-256");
	}

	/**
	 * 
	 * @param strSrc 需要被加密的字符串
	 * @param encName:加密方式，有MD5、SHA-1和SHA-256 这三种加密方式
	 * @return 返回加密后的字符串
	 */
	private static String EncryptStr(String strSrc, String encName) {
		MessageDigest md = null;
		String strDes = null;
		byte[] bs = strSrc.getBytes();
		try {
			if (StringUtils.isEmpty(encName)) {
				encName = "MD5";
			}
			md = MessageDigest.getInstance(encName);
			md.update(bs);
			strDes = byte2Hex(md.digest());
		} catch (NoSuchAlgorithmException e) {
			System.out.println("Invalid algorithm.");
			e.printStackTrace();
		}
		return strDes;
	}

	private static String byte2Hex(byte[] digest) {
		StringBuffer buffer = new StringBuffer();
		for (int i = 0; i < digest.length; i++) {
			String str = Integer.toHexString(digest[i] & 0xFF);
			buffer.append(str.length() == 1 ? "0" + str : str);// 每个字节由两个字符表示，位数不够，高位补0
		}
		return buffer.toString();
	}
}
