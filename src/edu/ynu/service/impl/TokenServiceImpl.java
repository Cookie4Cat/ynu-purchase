package edu.ynu.service.impl;

import edu.ynu.service.TokenService;
import edu.ynu.dao.TokenDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public class TokenServiceImpl implements TokenService{
    @Autowired
    private TokenDao tokenDao;
    @Override
    public String getToken(String userId,String userName) {
        return tokenDao.getToken(userId,userName);
    }
    @Override
    public String getUserFormToken(String token) {
        return tokenDao.getUserIdFormToken(token);
    }
}
