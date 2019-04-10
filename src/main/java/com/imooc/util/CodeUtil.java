package com.imooc.util;

import javax.servlet.http.HttpServletRequest;

public class CodeUtil {
	public static boolean checkVerifyCode(HttpServletRequest request) {
		String verifyCodeExpected = (String) request.getSession().getAttribute(com.google.code.kaptcha.Constants.KAPTCHA_SESSION_KEY);
		String verifyCodeActual = HttpServletRequestUtil.getString(request, "varifyCodeActual");
		if (verifyCodeActual == null || !verifyCodeExpected.equals(verifyCodeActual)) {
			return false;
		}
		return true;
	}
}
