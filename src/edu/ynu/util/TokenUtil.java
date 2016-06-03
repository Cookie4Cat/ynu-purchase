package edu.ynu.util;

import edu.ynu.dao.impl.TokenDaoImpl;

public class TokenUtil {

	private TokenDaoImpl tokenDao;
	public String getToken(String userId) {
		return tokenDao.getToken(userId);
	}

	public String getUserFormToken(String token) {
		return tokenDao.getUserIdFormToken(token);
	}
}
