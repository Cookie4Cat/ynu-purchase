package edu.ynu.util;

import edu.ynu.dao.impl.TokenDaoImpl;
import org.springframework.beans.factory.annotation.Autowired;

public class TokenUtil {

	@Autowired
	private TokenDaoImpl tokenDao;
	public String getToken(String userId) {
		return tokenDao.getToken(userId);
	}

	public String getUserFormToken(String token) {
		return tokenDao.getUserIdFormToken(token);
	}
}
