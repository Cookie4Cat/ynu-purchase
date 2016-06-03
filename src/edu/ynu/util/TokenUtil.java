package edu.ynu.util;

public class TokenUtil {

	public String getToken(String userId) {
		if (userId.equals("1")) {
			return "1234";
		} else if (userId.equals("2")) {
			return "abcd";
		}
		return "null";
	}

	public boolean validate(String token) {
		return token.equals("abcd") || token.equals("1234");
	}

	public int getUserFormToken(String token) {
		switch (token) {
		case "1234":
			return 1;
		case "abcd":
			return 2;
		default:
			return 0;
		}
	}
}
