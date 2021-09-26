package com.util;

public class TokenUtil {

	public static String generateToken() {
		StringBuffer sb = new StringBuffer();
		int k = 0;
		String data = "abcdrfghijklmnopqrstuvwxyz0123456789&%$!ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		for (int i = 1; i <= 16; i++) {
			k = (int) (Math.random() * data.length());
			sb.append(data.charAt(k));
		}
		return sb.toString();
	}

//	public static void main(String[] args) {
//		System.out.println(generateToken());
//	}
}
